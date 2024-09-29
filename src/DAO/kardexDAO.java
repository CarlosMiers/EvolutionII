/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.kardex;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
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
public class kardexDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<kardex> MostrarxFechaAnterior(int nsuc, Date d1, String cProd) throws SQLException {
        ArrayList<kardex> lista = new ArrayList<kardex>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        Double nSaldo = 0.00;

        String cSql = "SELECT prod.codigo,"
                + "prod.nombre,"
                + "COALESCE(cm.compras,0) AS compras,"
                + "COALESCE(vn.ventas,0) AS ventas,"
                + "COALESCE(en.ingresos,0) AS ingresos,"
                + "COALESCE(sa.egresos,0) AS egresos,"
                + "COALESCE(ts.tsalida,0) AS tsalida,"
                + "COALESCE(te.tentrada,0) AS tentrada "
                + "FROM productos prod "
                + "LEFT JOIN (SELECT detalle_compras.codprod,SUM(detalle_compras.cantidad) AS compras "
                + "FROM cabecera_compras,detalle_compras "
                + "WHERE cabecera_compras.creferencia=detalle_compras.dreferencia "
                + "AND cabecera_compras.fecha<'" + d1 + "' "
                + "AND cabecera_compras.sucursal=" + nsuc
                + " GROUP BY detalle_compras.codprod) cm ON cm.codprod=prod.codigo "
                + "LEFT JOIN (SELECT detalle_ventas.codprod,SUM(detalle_ventas.cantidad) AS ventas "
                + "FROM cabecera_ventas,detalle_ventas "
                + "WHERE cabecera_ventas.creferencia=detalle_ventas.dreferencia "
                + "AND cabecera_ventas.fecha<'" + d1 + "' "
                + "AND cabecera_ventas.sucursal=" + nsuc
                + " GROUP BY detalle_ventas.codprod) vn ON vn.codprod=prod.codigo "
                + "LEFT JOIN (SELECT detalle_ajuste_mercaderias.producto,SUM(detalle_ajuste_mercaderias.cantidad) AS ingresos "
                + "FROM ajuste_mercaderias,detalle_ajuste_mercaderias "
                + "WHERE ajuste_mercaderias.idreferencia=detalle_ajuste_mercaderias.dreferencia "
                + "AND ajuste_mercaderias.fecha<'" + d1 + "' "
                + "AND ajuste_mercaderias.sucursal=" + nsuc
                + " GROUP BY detalle_ajuste_mercaderias.producto) en ON en.producto=prod.codigo "
                + "LEFT JOIN (SELECT detalle_salida_mercaderias.producto,SUM(detalle_salida_mercaderias.cantidad) AS egresos "
                + "FROM cabecera_salida_mercaderias,detalle_salida_mercaderias "
                + "WHERE cabecera_salida_mercaderias.idreferencia=detalle_salida_mercaderias.dreferencia "
                + "AND cabecera_salida_mercaderias.fecha<'" + d1 + "' "
                + "AND cabecera_salida_mercaderias.sucursal=" + nsuc
                + " GROUP BY detalle_salida_mercaderias.producto) sa ON sa.producto=prod.codigo "
                + "LEFT JOIN (SELECT detalle_transferencias.producto,SUM(detalle_transferencias.cantidad) AS tsalida "
                + "FROM transferencias,detalle_transferencias "
                + "WHERE transferencias.idtransferencia=detalle_transferencias.dreferencia "
                + "AND transferencias.fecha<'" + d1 + "' "
                + " AND transferencias.origen=" + nsuc
                + " GROUP BY detalle_transferencias.producto) ts ON ts.producto=prod.codigo "
                + "LEFT JOIN (SELECT detalle_transferencias.producto,SUM(detalle_transferencias.cantidad) AS tentrada "
                + "FROM transferencias,detalle_transferencias "
                + "WHERE transferencias.idtransferencia=detalle_transferencias.dreferencia "
                + "AND transferencias.fecha<'" + d1 + "' "
                + "AND transferencias.destino=" + nsuc
                + " GROUP BY detalle_transferencias.producto) te ON te.producto=prod.codigo "
                + " WHERE prod.codigo='" + cProd + "' "
                + "ORDER BY prod.codigo ";
        System.out.println(cSql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                kardex a = new kardex();
                a.setNombrecomprobante("SALDO ANTERIOR");
                a.setCompras(rs.getDouble("compras"));
                a.setVentas(rs.getDouble("ventas"));
                a.setEntrada(rs.getDouble("ingresos"));
                a.setSalida(rs.getDouble("egresos"));
                a.setTentrada(rs.getDouble("tentrada"));
                a.setTsalida(rs.getDouble("tsalida"));

                if (a.getCompras() == null) {
                    a.setCompras(nSaldo);
                }
                if (a.getVentas() == null) {
                    a.setVentas(nSaldo);
                }
                if (a.getEntrada() == null) {
                    a.setEntrada(nSaldo);
                }
                if (a.getSalida() == null) {
                    a.setSalida(nSaldo);
                }
                if (a.getTentrada() == null) {
                    a.setTentrada(nSaldo);
                }
                if (a.getTsalida() == null) {
                    a.setTsalida(nSaldo);
                }
                a.setCompras((a.getCompras() + a.getEntrada() + a.getTentrada()) - (a.getVentas() + a.getSalida() + a.getTsalida()));

                lista.add(a);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        conn.close();
        st.close();
        return lista;
    }

    public ArrayList<kardex> MostrarxFecha(Date dCompraI, Date dCompraf, int nSucCo, String cProdCo) throws SQLException {
        ArrayList<kardex> lista = new ArrayList<kardex>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String cSql = "SELECT sucursal,numero,fecha,comprobante,nombrecomprobante,producto,nombreproducto,"
                + "costo,rubro,marca,ubicacion,medida,entrada,salida,precio"
                + " FROM kardex_compras "
                + " WHERE fecha BETWEEN " + "'" + dCompraI + "'" + " AND " + "'" + dCompraf + "'" + " and sucursal=" + nSucCo + " and producto= '" + cProdCo + "'"
                + " UNION "
                + "SELECT sucursal,numero,fecha,comprobante,nombrecomprobante,producto,nombreproducto,"
                + "costo,rubro,marca,ubicacion,medida,entrada,salida,precio"
                + " FROM kardex_ventas "
                + " WHERE fecha BETWEEN " + "'" + dCompraI + "'" + " AND " + "'" + dCompraf + "'" + " and sucursal=" + nSucCo + " and producto= '" + cProdCo + "'"
                + " UNION "
                + "SELECT sucursal,numero,fecha,comprobante,nombrecomprobante,producto,nombreproducto,"
                + "costo,rubro,marca,ubicacion,medida,entrada,salida,precio"
                + " FROM kardex_entrada "
                + " WHERE fecha BETWEEN " + "'" + dCompraI + "'" + " AND " + "'" + dCompraf + "'" + " and sucursal=" + nSucCo + " and producto= '" + cProdCo + "'"
                + " UNION "
                + "SELECT sucursal,numero,fecha,comprobante,nombrecomprobante,producto,nombreproducto,"
                + "costo,rubro,marca,ubicacion,medida,entrada,salida,precio"
                + " FROM kardex_salida "
                + " WHERE fecha BETWEEN " + "'" + dCompraI + "'" + " AND " + "'" + dCompraf + "'" + " and sucursal=" + nSucCo + " and producto= '" + cProdCo + "'"
                + " UNION "
                + "SELECT sucursal,numero,fecha,comprobante,nombrecomprobante,producto,nombreproducto,"
                + "costo,rubro,marca,ubicacion,medida,entrada,salida,precio"
                + " FROM kardex_transf_entrada "
                + " WHERE fecha BETWEEN " + "'" + dCompraI + "'" + " AND " + "'" + dCompraf + "'" + " and sucursal=" + nSucCo + " and producto= '" + cProdCo + "'"
                + " UNION "
                + "SELECT sucursal,numero,fecha,comprobante,nombrecomprobante,producto,nombreproducto,"
                + "costo,rubro,marca,ubicacion,medida,entrada,salida,precio"
                + " FROM kardex_transf_salida "
                + " WHERE fecha BETWEEN " + "'" + dCompraI + "'" + " AND " + "'" + dCompraf + "'" + " and sucursal=" + nSucCo + " and producto= '" + cProdCo + "'"
                + " ORDER BY fecha";

        System.out.println(cSql);

        try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                kardex k = new kardex();
                k.setNumero(rs.getBigDecimal("numero"));
                k.setFecha(rs.getDate("fecha"));
                k.setNombrecomprobante(rs.getString("nombrecomprobante"));
                k.setSalida(rs.getDouble("salida"));
                k.setCosto(rs.getBigDecimal("costo"));
                k.setEntrada(rs.getDouble("entrada"));
                k.setPrecio(rs.getDouble("precio"));
                lista.add(k);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        conn.close();
        st.close();
        return lista;
    }

    public ArrayList<kardex> MostrarxFechaNuevaVersion(Date dFechaI, Date dFechaF, int nSuc, String cProd) throws SQLException {
        ArrayList<kardex> lista = new ArrayList<kardex>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String cSql = "SELECT "
                + "cabecera_compras.sucursal    AS sucursal,"
                + "cabecera_compras.nrofactura  AS numero,"
                + "cabecera_compras.fecha       AS fecha,"
                + "cabecera_compras.comprobante AS comprobante,"
                + "comprobantes.nombre          AS nombrecomprobante,"
                + "detalle_compras.codprod      AS producto,"
                + "productos.nombre             AS nombreproducto,"
                + "detalle_compras.prcosto      AS costo,"
                + "productos.rubro              AS rubro,"
                + "productos.marca              AS marca,"
                + "productos.ubicacion          AS ubicacion,"
                + "productos.medida             AS medida,"
                + "productos.kit                AS kit,"
                + "  SUM(detalle_compras.cantidad) AS entrada,"
                + "  0.00                             AS salida,"
                + "  0.00                             AS precio "
                + "FROM cabecera_compras "
                + "     JOIN detalle_compras "
                + "       ON cabecera_compras.creferencia = detalle_compras.dreferencia "
                + "    JOIN comprobantes "
                + "      ON cabecera_compras.comprobante = comprobantes.codigo "
                + "   JOIN productos "
                + "     ON detalle_compras.codprod = productos.codigo "
                + "WHERE fecha BETWEEN '"+dFechaI+"' AND '"+dFechaF+"' "
                + "AND codprod='"+cProd+"' "
                + "AND sucursal="+nSuc
                + " GROUP BY cabecera_compras.creferencia,detalle_compras.codprod,cabecera_compras.fecha "
                + " UNION "
                + "SELECT "
                + "  cabecera_ventas.sucursal    AS sucursal,"
                + "  cabecera_ventas.factura     AS numero,"
                + "  cabecera_ventas.fecha       AS fecha,"
                + "  cabecera_ventas.comprobante AS comprobante,"
                + "  comprobantes.nombre         AS nombrecomprobante,"
                + "  detalle_ventas.codprod      AS producto,"
                + "  productos.nombre            AS nombreproducto,"
                + "  0.00                            AS costo,"
                + "  productos.rubro             AS rubro,"
                + "  productos.marca             AS marca,"
                + "  productos.ubicacion         AS ubicacion,"
                + "  productos.medida            AS medida,"
                + "  productos.kit               AS kit,"
                + "  0.00                            AS entrada,"
                + "  SUM(detalle_ventas.cantidad) AS salida,"
                + "  detalle_ventas.precio       AS precio "
                + "FROM cabecera_ventas "
                + "     JOIN detalle_ventas "
                + "       ON cabecera_ventas.creferencia = detalle_ventas.dreferencia "
                + "    JOIN comprobantes "
                + "      ON cabecera_ventas.comprobante = comprobantes.codigo "
                + "   JOIN productos "
                + "     ON detalle_ventas.codprod = productos.codigo "
                + "WHERE fecha BETWEEN '"+dFechaI+"' AND '"+dFechaF+"' "
                + "AND codprod='"+cProd+"' "
                + "AND sucursal="+nSuc
                + " GROUP BY cabecera_ventas.creferencia,detalle_ventas.codprod,cabecera_ventas.fecha "
                + " UNION "
                + "SELECT "
                + "  ajuste_mercaderias.sucursal         AS sucursal,"
                + "  ajuste_mercaderias.numero           AS numero,"
                + "  ajuste_mercaderias.fecha            AS fecha,"
                + "  ajuste_mercaderias.tipo             AS comprobante,"
                + "  comprobantes.nombre                 AS nombrecomprobante,"
                + "  detalle_ajuste_mercaderias.producto AS producto,"
                + "  productos.nombre                    AS nombreproducto,"
                + "  detalle_ajuste_mercaderias.costo    AS costo,"
                + "  productos.rubro                     AS rubro,"
                + "  productos.marca                     AS marca,"
                + "  productos.ubicacion                 AS ubicacion,"
                + "  productos.medida                    AS medida,"
                + "  productos.kit                       AS kit,"
                + "  SUM(detalle_ajuste_mercaderias.cantidad) AS entrada,"
                + "  0.00                                    AS salida,"
                + "  0.00                                    AS precio "
                + "FROM ajuste_mercaderias "
                + "     JOIN detalle_ajuste_mercaderias "
                + "       ON ajuste_mercaderias.idreferencia = detalle_ajuste_mercaderias.dreferencia "
                + "    JOIN comprobantes "
                + "      ON ajuste_mercaderias.tipo = comprobantes.codigo "
                + "   JOIN productos "
                + "     ON detalle_ajuste_mercaderias.producto = productos.codigo "
                + "WHERE fecha BETWEEN '"+dFechaI+"' AND '"+dFechaF+"' "
                + "AND producto='"+cProd+"' "
                + "AND sucursal="+nSuc
                + " GROUP BY ajuste_mercaderias.idreferencia,detalle_ajuste_mercaderias.producto,ajuste_mercaderias.fecha "
                + " UNION "
                + "SELECT "
                + "  cabecera_salida_mercaderias.sucursal AS sucursal,"
                + "  cabecera_salida_mercaderias.numero   AS numero,"
                + "  cabecera_salida_mercaderias.fecha    AS fecha,"
                + "  cabecera_salida_mercaderias.tipo     AS comprobante,"
                + "  comprobantes.nombre                  AS nombrecomprobante,"
                + "  detalle_salida_mercaderias.producto  AS producto,"
                + "  productos.nombre                     AS nombreproducto,"
                + "  0.00                                     AS costo,"
                + "  productos.rubro                      AS rubro,"
                + "  productos.marca                      AS marca,"
                + "  productos.ubicacion                  AS ubicacion,"
                + "  productos.medida                     AS medida,"
                + "  productos.kit                        AS kit,"
                + "  0.00                                     AS entrada,"
                + "  SUM(detalle_salida_mercaderias.cantidad) AS salida,"
                + "  detalle_salida_mercaderias.costo     AS precio "
                + " FROM cabecera_salida_mercaderias "
                + "     JOIN detalle_salida_mercaderias "
                + "       ON cabecera_salida_mercaderias.idreferencia = detalle_salida_mercaderias.dreferencia "
                + "    JOIN comprobantes "
                + "      ON cabecera_salida_mercaderias.tipo = comprobantes.codigo "
                + "   JOIN productos "
                + "     ON detalle_salida_mercaderias.producto = productos.codigo "
                + "WHERE fecha BETWEEN '"+dFechaI+"' AND '"+dFechaF+"' "
                + "AND producto='"+cProd+"' "
                + "AND sucursal="+nSuc
                + " GROUP BY cabecera_salida_mercaderias.idreferencia,detalle_salida_mercaderias.producto,cabecera_salida_mercaderias.fecha "
                + " UNION "
                + "SELECT "
                + "transferencias.destino          AS sucursal,"
                + "transferencias.numero           AS numero,"
                + "transferencias.fecha            AS fecha,"
                + "transferencias.tipo             AS comprobante,"
                + "comprobantes.nombre             AS nombrecomprobante,"
                + "detalle_transferencias.producto AS producto,"
                + "productos.nombre                AS nombreproducto,"
                + "detalle_transferencias.costo    AS costo,"
                + "productos.rubro                 AS rubro,"
                + "productos.marca                 AS marca,"
                + "productos.ubicacion             AS ubicacion,"
                + "productos.medida                AS medida,"
                + "productos.kit                   AS kit,"
                + "  SUM(detalle_transferencias.cantidad) AS entrada,"
                + "  0.00                                AS salida,"
                + "  0.00                                AS precio "
                + "FROM transferencias "
                + "     JOIN detalle_transferencias "
                + "       ON transferencias.idtransferencia = detalle_transferencias.dreferencia "
                + "    JOIN comprobantes "
                + "      ON transferencias.tipo = comprobantes.codigo "
                + "   JOIN productos "
                + "     ON detalle_transferencias.producto = productos.codigo "
                + "WHERE fecha BETWEEN '"+dFechaI+"' AND '"+dFechaF+"' "
                + "AND producto='"+cProd+"' "
                + " AND transferencias.destino="+nSuc
                + " GROUP BY transferencias.idtransferencia,detalle_transferencias.producto,transferencias.fecha "
                + " UNION "
                + "SELECT "
                + "  transferencias.origen           AS sucursal,"
                + "  transferencias.numero           AS numero,"
                + "  transferencias.fecha            AS fecha,"
                + "  transferencias.tipo             AS comprobante,"
                + "  comprobantes.nombre             AS nombrecomprobante,"
                + "  detalle_transferencias.producto AS producto,"
                + "  productos.nombre                AS nombreproducto,"
                + "  0.00                                AS costo,"
                + "  productos.rubro                 AS rubro,"
                + "  productos.marca                 AS marca,"
                + "  productos.ubicacion             AS ubicacion,"
                + "  productos.medida                AS medida,"
                + "  productos.kit                   AS kit,"
                + "  0.00                                AS entrada,"
                + "  SUM(detalle_transferencias.cantidad) AS salida,"
                + "  detalle_transferencias.costo    AS precio "
                + "FROM transferencias "
                + "     JOIN detalle_transferencias "
                + "       ON transferencias.idtransferencia = detalle_transferencias.dreferencia "
                + "    JOIN comprobantes "
                + "      ON transferencias.tipo = comprobantes.codigo "
                + "   JOIN productos "
                + "     ON detalle_transferencias.producto = productos.codigo "
                + "WHERE fecha BETWEEN '"+dFechaI+"' AND '"+dFechaF+"' "
                + "AND producto='"+cProd+"' "
                + " AND transferencias.origen="+nSuc
                + " GROUP BY transferencias.idtransferencia,detalle_transferencias.producto,transferencias.fecha "
                + " ORDER BY sucursal,producto,fecha ";

        System.out.println(cSql);

        try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                kardex k = new kardex();
                k.setNumero(rs.getBigDecimal("numero"));
                k.setFecha(rs.getDate("fecha"));
                k.setNombrecomprobante(rs.getString("nombrecomprobante"));
                k.setSalida(rs.getDouble("salida"));
                k.setCosto(rs.getBigDecimal("costo"));
                k.setEntrada(rs.getDouble("entrada"));
                k.setPrecio(rs.getDouble("precio"));
                lista.add(k);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        conn.close();
        st.close();
        return lista;
    }

}

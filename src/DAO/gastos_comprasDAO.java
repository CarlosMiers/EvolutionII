/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.banco;
import Modelo.comprobante;
import Modelo.gastos_compras;
import Modelo.moneda;
import Modelo.plan;
import Modelo.proveedor;
import Modelo.rubro_compra;
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
 * @author Pc_Server
 */
public class gastos_comprasDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<gastos_compras> MostrarDetalleGastos(Double id) throws SQLException {
        ArrayList<gastos_compras> lista = new ArrayList<gastos_compras>();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT \n"
                    + "    gastos_compras.fondofijo, -- \n"
                    + "    gastos_compras.formatofactura, -- 0\n"
                    + "    gastos_compras.nrofactura, -- 0\n"
                    + "    gastos_compras.fecha, -- 1\n"
                    + "    gastos_compras.primer_vence, -- 2\n"
                    + "    gastos_compras.comprobante, -- 4\n"
                    + "    comprobantes.nombre as nombrecomprobante, -- 5\n"
                    + "    rubro_compras.nombre as nombrerubro, -- 7\n"
                    + "    gastos_compras.moneda, -- 8\n"
                    + "    monedas.nombre as nombremoneda, -- 9\n"
                    + "    gastos_compras.moneda as cotizacion, -- 10\n"
                    + "    gastos_compras.timbrado,  -- 14\n"
                    + "    gastos_compras.vencetimbrado,  -- 15\n"
                    + "    gastos_compras.proveedor, -- 16\n"
                    + "    proveedores.nombre as nombreproveedor, -- 17\n"
                    + "    gastos_compras.exentas, -- 18\n"
                    + "    gastos_compras.gravadas10, -- 19\n"
                    + "    gastos_compras.iva10, -- 20\n"
                    + "    gastos_compras.gravadas5, -- 21 \n"
                    + "    gastos_compras.iva5, -- 22\n"
                    + "    gastos_compras.totalneto as totalneto, -- 23\n"
                    + "    gastos_compras.creferencia creferencia, -- 23\n"
                    + "    gastos_compras.concepto as concepto, -- 24\n"
                    + "    gastos_compras.observacion observacion -- 24\n"
                    + " FROM gastos_compras \n"
                    + "    LEFT JOIN comprobantes ON gastos_compras.comprobante=comprobantes.codigo\n"
                    + "    LEFT JOIN rubro_compras on gastos_compras.concepto=rubro_compras.codigo\n"
                    + "    LEFT JOIN monedas on gastos_compras.moneda=monedas.codigo\n"
                    + "    LEFT JOIN proveedores on gastos_compras.proveedor = proveedores.codigo\n"
                    + " WHERE gastos_compras.fondofijo=? ORDER BY fondofijo";
            //System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    gastos_compras gu = new gastos_compras();
                    comprobante comprobante = new comprobante();
                    rubro_compra rubro = new rubro_compra();
                    moneda moneda = new moneda();
                    proveedor proveedor = new proveedor();

                    gu.setFondofijo(rs.getDouble("fondofijo"));
                    gu.setFormatofactura(rs.getString("formatofactura"));
                    gu.setNrofactura(rs.getString("nrofactura"));
                    gu.setFechafactura(rs.getDate("fecha"));
                    gu.setVencimiento(rs.getDate("primer_vence"));
                    comprobante.setCodigo(rs.getInt("comprobante"));
                    comprobante.setNombre(rs.getString("nombrecomprobante"));
                    gu.setComprobante(comprobante);

                    rubro.setCodigo(rs.getInt("concepto"));
                    rubro.setNombre(rs.getString("nombrerubro"));
                    gu.setConcepto(rubro);

                    moneda.setCodigo(rs.getInt("moneda"));
                    moneda.setNombre(rs.getString("nombremoneda"));
                    gu.setMoneda(moneda);

                    gu.setTimbrado(rs.getString("timbrado"));
                    gu.setVencimientotimbrado(rs.getDate("vencetimbrado"));

                    proveedor.setCodigo(rs.getInt("proveedor"));
                    proveedor.setNombre(rs.getString("nombreproveedor"));
                    gu.setProveedor(proveedor);

                    gu.setExentas(rs.getDouble("exentas"));
                    gu.setGravadas10(rs.getDouble("gravadas10"));
                    gu.setIva10(rs.getDouble("iva10"));
                    gu.setGravadas5(rs.getDouble("gravadas5"));
                    gu.setIva5(rs.getDouble("iva5"));
                    gu.setTotalneto(rs.getDouble("totalneto"));
                    gu.setObservacion(rs.getString("observacion"));

                    gu.setCreferencia(rs.getString("creferencia"));
                    gu.setCotizacion(rs.getDouble("cotizacion"));

                    lista.add(gu);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<gastos_compras> MostrarDetalleGastosContable(Double id) throws SQLException {
        ArrayList<gastos_compras> lista = new ArrayList<gastos_compras>();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT \n"
                    + "    gastos_compras.fondofijo, -- \n"
                    + "    gastos_compras.formatofactura, -- 0\n"
                    + "    gastos_compras.nrofactura, -- 0\n"
                    + "    gastos_compras.fecha, -- 1\n"
                    + "    gastos_compras.primer_vence, -- 2\n"
                    + "    gastos_compras.comprobante, -- 4\n"
                    + "    comprobantes.nombre as nombrecomprobante, -- 5\n"
                    + "    plan.nombre as nombrecuenta, -- 7\n"
                    + "    gastos_compras.moneda, -- 8\n"
                    + "    monedas.nombre as nombremoneda, -- 9\n"
                    + "    gastos_compras.moneda as cotizacion, -- 10\n"
                    + "    gastos_compras.timbrado,  -- 14\n"
                    + "    gastos_compras.vencetimbrado,  -- 15\n"
                    + "    gastos_compras.proveedor, -- 16\n"
                    + "    proveedores.nombre as nombreproveedor, -- 17\n"
                    + "    gastos_compras.exentas, -- 18\n"
                    + "    gastos_compras.gravadas10, -- 19\n"
                    + "    gastos_compras.iva10, -- 20\n"
                    + "    gastos_compras.gravadas5, -- 21 \n"
                    + "    gastos_compras.iva5, -- 22\n"
                    + "    gastos_compras.totalneto as totalneto, -- 23\n"
                    + "    gastos_compras.creferencia creferencia, -- 23\n"
                    + "    gastos_compras.idcta as idcta, -- 24\n"
                    + "    gastos_compras.observacion observacion -- 24\n"
                    + " FROM gastos_compras \n"
                    + "    LEFT JOIN comprobantes ON gastos_compras.comprobante=comprobantes.codigo\n"
                    + "    LEFT JOIN plan on gastos_compras.idcta=plan.codigo\n"
                    + "    LEFT JOIN monedas on gastos_compras.moneda=monedas.codigo\n"
                    + "    LEFT JOIN proveedores on gastos_compras.proveedor = proveedores.codigo\n"
                    + " WHERE gastos_compras.fondofijo=? ORDER BY fondofijo";
            //System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    gastos_compras gu = new gastos_compras();
                    comprobante comprobante = new comprobante();
                    plan rubro = new plan();
                    moneda moneda = new moneda();
                    proveedor proveedor = new proveedor();
                    gu.setFormatofactura(rs.getString("formatofactura"));
                    gu.setFondofijo(rs.getDouble("fondofijo"));
                    gu.setNrofactura(rs.getString("nrofactura"));
                    gu.setFechafactura(rs.getDate("fecha"));
                    gu.setVencimiento(rs.getDate("primer_vence"));
                    comprobante.setCodigo(rs.getInt("comprobante"));
                    comprobante.setNombre(rs.getString("nombrecomprobante"));
                    gu.setComprobante(comprobante);

                    rubro.setCodigo(rs.getString("idcta"));
                    rubro.setNombre(rs.getString("nombrecuenta"));
                    gu.setIdca(rubro);;

                    moneda.setCodigo(rs.getInt("moneda"));
                    moneda.setNombre(rs.getString("nombremoneda"));
                    gu.setMoneda(moneda);

                    gu.setTimbrado(rs.getString("timbrado"));
                    gu.setVencimientotimbrado(rs.getDate("vencetimbrado"));

                    proveedor.setCodigo(rs.getInt("proveedor"));
                    proveedor.setNombre(rs.getString("nombreproveedor"));
                    gu.setProveedor(proveedor);

                    gu.setExentas(rs.getDouble("exentas"));
                    gu.setGravadas10(rs.getDouble("gravadas10"));
                    gu.setIva10(rs.getDouble("iva10"));
                    gu.setGravadas5(rs.getDouble("gravadas5"));
                    gu.setIva5(rs.getDouble("iva5"));
                    gu.setTotalneto(rs.getDouble("totalneto"));
                    gu.setObservacion(rs.getString("observacion"));

                    gu.setCreferencia(rs.getString("creferencia"));
                    gu.setCotizacion(rs.getDouble("cotizacion"));

                    lista.add(gu);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public boolean borrarDetalleGasto(Double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM gastos_compras WHERE fondofijo=?");
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

    public boolean guardarItemGasto(String detalle) throws SQLException {
        boolean guardado = true;
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);

        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalle);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {

                    //  dnumero,nrofactura,unidad,fechafactura,concepto,monto,tipo,idcta
                    String sql = "INSERT INTO  gastos_compras(\n"
                            + "fondofijo,\n" //1
                            + "formatofactura,\n" //2
                            + "nrofactura,\n" //2
                            + "fecha,\n" //3
                            + "primer_vence,\n" //4
                            + "comprobante,\n" //6
                            + "concepto,\n" //
                            + "moneda,\n" //8
                            + "timbrado,\n" //10
                            + "vencetimbrado,\n" //11
                            + "proveedor,\n" //
                            + "exentas,\n" //13
                            + "gravadas10,\n" //14
                            + "iva10,\n" //15
                            + "gravadas5,\n" //16
                            + "iva5,\n" //17
                            + "totalneto,\n" //18
                            + "observacion,\n" //19
                            + "cotizacion,\n" //20
                            + "sucursal,\n" //20
                            + "creferencia\n" //21
                            + ") \n"
                            + "values(?, ?, ?, ?, ?, "
                            + "?, ?, ?, ?, ?, "
                            + "?, ?, ?, ?, ?, "
                            + "?, ?, ?, ?,?,?)";

                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setDouble(1, 999);
                        ps.setString(2, obj.get("formatofactura").getAsString());
                        ps.setString(3, obj.get("nrofactura").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("primer_vence").getAsString());
                        ps.setInt(6, Integer.valueOf(obj.get("comprobante").getAsString()));
                        ps.setInt(7, Integer.valueOf(obj.get("concepto").getAsString()));
                        ps.setInt(8, Integer.valueOf(obj.get("moneda").getAsString()));
                        ps.setString(9, obj.get("timbrado").getAsString());
                        ps.setString(10, obj.get("vencetimbrado").getAsString());
                        ps.setInt(11, Integer.valueOf(obj.get("proveedor").getAsString()));
                        ps.setDouble(12, Double.valueOf(obj.get("exentas").getAsString()));
                        ps.setDouble(13, Double.valueOf(obj.get("gravadas10").getAsString()));
                        ps.setDouble(14, Double.valueOf(obj.get("iva10").getAsString()));
                        ps.setDouble(15, Double.valueOf(obj.get("gravadas5").getAsString()));
                        ps.setDouble(16, Double.valueOf(obj.get("iva5").getAsString()));
                        ps.setDouble(17, Double.valueOf(obj.get("totalneto").getAsString()));
                        ps.setString(18, obj.get("observacion").getAsString());
                        ps.setDouble(19, Double.valueOf(obj.get("cotizacion").getAsString()));
                        ps.setInt(20, Integer.valueOf(obj.get("sucursal").getAsString()));
                        ps.setString(21, obj.get("creferencia").getAsString());
                        //System.out.println(ps.toString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardado = false;
                    break;
                }
            }

            if (guardado) {
                try {
                    conn.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardado = false;
        }
        conn.close();
        return guardado;
    }

    public ArrayList<gastos_compras> MostrarDetalleGastosSinPago(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<gastos_compras> lista = new ArrayList<gastos_compras>();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT "
                    + "    gastos_compras.fondofijo, "
                    + "    gastos_compras.formatofactura,"
                    + "    gastos_compras.nrofactura, "
                    + "    gastos_compras.fecha, "
                    + "    gastos_compras.primer_vence,"
                    + "    gastos_compras.comprobante,"
                    + "    comprobantes.nombre as nombrecomprobante, "
                    + "    rubro_compras.nombre as nombrerubro, "
                    + "    gastos_compras.moneda,"
                    + "    monedas.nombre as nombremoneda, "
                    + "    gastos_compras.moneda as cotizacion,"
                    + "    gastos_compras.timbrado, "
                    + "    gastos_compras.vencetimbrado,"
                    + "    gastos_compras.proveedor,"
                    + "    proveedores.nombre as nombreproveedor,"
                    + "    gastos_compras.exentas,"
                    + "    gastos_compras.gravadas10,"
                    + "    gastos_compras.iva10,"
                    + "    gastos_compras.gravadas5,"
                    + "    gastos_compras.iva5, "
                    + "    gastos_compras.totalneto as totalneto,"
                    + "    gastos_compras.creferencia creferencia,"
                    + "    gastos_compras.concepto as concepto,"
                    + "    gastos_compras.observacion "
                    + " FROM gastos_compras "
                    + "    LEFT JOIN comprobantes ON gastos_compras.comprobante=comprobantes.codigo\n"
                    + "    LEFT JOIN rubro_compras on gastos_compras.concepto=rubro_compras.codigo\n"
                    + "    LEFT JOIN monedas on gastos_compras.moneda=monedas.codigo\n"
                    + "    LEFT JOIN proveedores on gastos_compras.proveedor = proveedores.codigo\n"
                    + " WHERE gastos_compras.pagado=0"
                    + " AND gastos_compras.fondofijo=999 "
                    + "AND gastos_compras.fecha BETWEEN ?  AND ? "
                    + " ORDER BY gastos_compras.nrofactura";
            //System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    gastos_compras gu = new gastos_compras();
                    comprobante comprobante = new comprobante();
                    rubro_compra rubro = new rubro_compra();
                    moneda moneda = new moneda();
                    proveedor proveedor = new proveedor();

                    gu.setFondofijo(rs.getDouble("fondofijo"));
                    gu.setFormatofactura(rs.getString("formatofactura"));
                    gu.setNrofactura(rs.getString("nrofactura"));
                    gu.setFechafactura(rs.getDate("fecha"));
                    gu.setVencimiento(rs.getDate("primer_vence"));
                    comprobante.setCodigo(rs.getInt("comprobante"));
                    comprobante.setNombre(rs.getString("nombrecomprobante"));
                    gu.setComprobante(comprobante);

                    rubro.setCodigo(rs.getInt("concepto"));
                    rubro.setNombre(rs.getString("nombrerubro"));
                    gu.setConcepto(rubro);

                    moneda.setCodigo(rs.getInt("moneda"));
                    moneda.setNombre(rs.getString("nombremoneda"));
                    gu.setMoneda(moneda);

                    gu.setTimbrado(rs.getString("timbrado"));
                    gu.setVencimientotimbrado(rs.getDate("vencetimbrado"));

                    proveedor.setCodigo(rs.getInt("proveedor"));
                    proveedor.setNombre(rs.getString("nombreproveedor"));
                    gu.setProveedor(proveedor);

                    gu.setExentas(rs.getDouble("exentas"));
                    gu.setGravadas10(rs.getDouble("gravadas10"));
                    gu.setIva10(rs.getDouble("iva10"));
                    gu.setGravadas5(rs.getDouble("gravadas5"));
                    gu.setIva5(rs.getDouble("iva5"));
                    gu.setTotalneto(rs.getDouble("totalneto"));
                    gu.setObservacion(rs.getString("observacion"));

                    gu.setCreferencia(rs.getString("creferencia"));
                    gu.setCotizacion(rs.getDouble("cotizacion"));

                    lista.add(gu);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public boolean ActualizarGastosCierre(String ref) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        System.out.println("entre a grabar estado en gastos " + ref);
        ps = st.getConnection().prepareStatement("UPDATE gastos_compras SET pagado=1 WHERE creferencia='" + ref + "'");
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

    public ArrayList<gastos_compras> MostrarDetalleGastosxFecha(Date fechaini, Date fechafin, int nmoneda) throws SQLException {
        ArrayList<gastos_compras> lista = new ArrayList<gastos_compras>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT gastos_compras.formatofactura,gastos_compras.fecha,gastos_compras.proveedor,"
                    + "proveedores.nombre AS nombreproveedor,"
                    + "proveedores.ruc,"
                    +"gastos_compras.moneda,monedas.nombre as nombremoneda,"
                    + "concepto,rubro_compras.nombre AS nombrerubro,"
                    + "totalneto,gastos_compras.observacion "
                    + "FROM gastos_compras "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=gastos_compras.proveedor "
                    + "LEFT JOIN rubro_compras "
                    + "ON rubro_compras.codigo=gastos_compras.concepto " 
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=gastos_compras.moneda " 
                    + " WHERE  gastos_compras.fecha BETWEEN ?  AND ?  "
                    + " AND gastos_compras.moneda=? "
                    + " ORDER BY gastos_compras.concepto,gastos_compras.fecha";
            //System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nmoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    gastos_compras gu = new gastos_compras();
                    rubro_compra rubro = new rubro_compra();
                    moneda moneda = new moneda();
                    proveedor proveedor = new proveedor();

                    gu.setFormatofactura(rs.getString("formatofactura"));
                    gu.setFechafactura(rs.getDate("fecha"));

                    rubro.setCodigo(rs.getInt("concepto"));
                    rubro.setNombre(rs.getString("nombrerubro"));
                    gu.setConcepto(rubro);

                    moneda.setCodigo(rs.getInt("moneda"));
                    moneda.setNombre(rs.getString("nombremoneda"));
                    gu.setMoneda(moneda);

                    proveedor.setCodigo(rs.getInt("proveedor"));
                    proveedor.setNombre(rs.getString("nombreproveedor"));
                    proveedor.setRuc(rs.getString("ruc"));
                    gu.setProveedor(proveedor);

                    gu.setTotalneto(rs.getDouble("totalneto"));
                    gu.setObservacion(rs.getString("observacion"));

                    lista.add(gu);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.comprobante;
import Modelo.detallepago;
import Modelo.moneda;
import Modelo.pago;
import Modelo.proveedor;
import Modelo.sucursal;
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
public class pagoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<pago> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<pago> lista = new ArrayList<pago>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT pagos.idpagos,pagos.numero,pagos.sucursal,pagos.recibo,pagos.fecha,pagos.proveedor,pagos.moneda,pagos.cotizacionmoneda,"
                    + "pagos.valores,pagos.totalpago,pagos.observacion,pagos.asiento,pagos.cierre,pagos.codusuario,"
                    + "monedas.nombre AS nombremoneda,proveedores.nombre AS nombreproveedor,sucursales.nombre as nombresucursal "
                    + "FROM pagos "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=pagos.moneda "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=pagos.proveedor "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=pagos.sucursal "
                    + "WHERE pagos.fecha between ? AND ? "
                    + " ORDER BY pagos.numero ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();

                    pago opago = new pago();

                    opago.setProveedor(proveedor);
                    opago.setMoneda(moneda);
                    opago.setSucursal(sucursal);

                    opago.setIdpagos(rs.getString("idpagos"));
                    opago.setFecha(rs.getDate("fecha"));
                    opago.getSucursal().setCodigo(rs.getInt("sucursal"));
                    opago.getSucursal().setNombre(rs.getString("nombresucursal"));
                    opago.getProveedor().setCodigo(rs.getInt("proveedor"));
                    opago.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    opago.setNumero(rs.getInt("numero"));
                    opago.getMoneda().setCodigo(rs.getInt("moneda"));
                    opago.getMoneda().setNombre(rs.getString("nombremoneda"));
                    opago.setValores(rs.getDouble("valores"));
                    opago.setTotalpago(rs.getDouble("totalpago"));
                    opago.setCierre(rs.getInt("cierre"));
                    opago.setAsiento(rs.getInt("asiento"));
                    opago.setCodusuario(rs.getInt("codusuario"));
                    opago.setObservacion(rs.getString("observacion"));
                    lista.add(opago);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public pago MostrarxPago(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        pago opago = new pago();
        try {

            String sql = "SELECT pagos.idpagos,pagos.numero,pagos.sucursal,pagos.recibo,pagos.fecha,pagos.proveedor,pagos.moneda,pagos.cotizacionmoneda,"
                    + "pagos.valores,pagos.totalpago,pagos.observacion,pagos.asiento,pagos.cierre,pagos.codusuario,proveedores.ruc,"
                    + "monedas.nombre AS nombremoneda,proveedores.nombre AS nombreproveedor,sucursales.nombre as nombresucursal "
                    + "FROM pagos "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=pagos.moneda "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=pagos.proveedor "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=pagos.sucursal "
                    + "WHERE pagos.numero=?  "
                    + " ORDER BY pagos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();

                    opago.setProveedor(proveedor);
                    opago.setMoneda(moneda);
                    opago.setSucursal(sucursal);

                    opago.setIdpagos(rs.getString("idpagos"));
                    opago.setFecha(rs.getDate("fecha"));
                    opago.setRecibo(rs.getString("recibo"));
                    opago.getSucursal().setCodigo(rs.getInt("sucursal"));
                    opago.getSucursal().setNombre(rs.getString("nombresucursal"));
                    opago.getProveedor().setCodigo(rs.getInt("proveedor"));
                    opago.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    opago.getProveedor().setRuc(rs.getString("ruc"));
                    opago.setNumero(rs.getInt("numero"));
                    opago.getMoneda().setCodigo(rs.getInt("moneda"));
                    opago.getMoneda().setNombre(rs.getString("nombremoneda"));
                    opago.setCotizacionmoneda(rs.getDouble("cotizacionmoneda"));
                    opago.setValores(rs.getDouble("valores"));
                    opago.setTotalpago(rs.getDouble("totalpago"));
                    opago.setCierre(rs.getInt("cierre"));
                    opago.setAsiento(rs.getInt("asiento"));
                    opago.setCodusuario(rs.getInt("codusuario"));
                    opago.setObservacion(rs.getString("observacion"));
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return opago;
    }

    public ArrayList<pago> MostrarxProveedor(int nproveedor, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<pago> lista = new ArrayList<pago>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT pagos.idpagos,pagos.numero,pagos.sucursal,pagos.recibo,pagos.fecha,pagos.proveedor,pagos.moneda,pagos.cotizacionmoneda,"
                + "pagos.valores,pagos.totalpago,pagos.observacion,pagos.asiento,pagos.cierre,pagos.codusuario,"
                + "monedas.nombre AS nombremoneda,proveedores.nombre AS nombreproveedor,sucursales.nombre as nombresucursal "
                + "FROM pagos "
                + "LEFT JOIN monedas "
                + "ON monedas.codigo=pagos.moneda "
                + "LEFT JOIN proveedores "
                + "ON proveedores.codigo=pagos.proveedor "
                + "LEFT JOIN sucursales "
                + "ON sucursales.codigo=pagos.sucursal "
                + "WHERE pagos.proveedor=? AND pagos.fecha between ? AND ? "
                + " ORDER BY pagos.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, nproveedor);
            ps.setDate(2, fechaini);
            ps.setDate(3, fechafin);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                while (rs.next()) {

                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();

                    pago opago = new pago();

                    opago.setProveedor(proveedor);
                    opago.setMoneda(moneda);
                    opago.setSucursal(sucursal);

                    opago.setIdpagos(rs.getString("idpagos"));
                    opago.setFecha(rs.getDate("fecha"));
                    opago.getSucursal().setCodigo(rs.getInt("sucursal"));
                    opago.getSucursal().setNombre(rs.getString("nombresucursal"));
                    opago.getProveedor().setCodigo(rs.getInt("proveedor"));
                    opago.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    opago.setNumero(rs.getInt("numero"));
                    opago.setRecibo(rs.getString("recibo"));
                    opago.getMoneda().setCodigo(rs.getInt("moneda"));
                    opago.getMoneda().setNombre(rs.getString("nombremoneda"));
                    opago.setValores(rs.getDouble("valores"));
                    opago.setTotalpago(rs.getDouble("totalpago"));
                    opago.setCierre(rs.getInt("cierre"));
                    opago.setAsiento(rs.getInt("asiento"));
                    opago.setCodusuario(rs.getInt("codusuario"));
                    opago.setObservacion(rs.getString("observacion"));
                    lista.add(opago);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public pago insertarPagos(pago pag, String detalle, String detalleformapago, String detallebanco) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardadoforma = false;
        boolean guardabanco = false;

        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO pagos (idpagos,sucursal,recibo,fecha,proveedor,moneda,totalpago,observacion,valores,cotizacionmoneda,codusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, pag.getIdpagos());
        ps.setInt(2, pag.getSucursal().getCodigo());
        ps.setString(3, pag.getRecibo());
        ps.setDate(4, pag.getFecha());
        ps.setInt(5, pag.getProveedor().getCodigo());
        ps.setInt(6, pag.getMoneda().getCodigo());
        ps.setDouble(7, pag.getTotalpago());
        ps.setString(8, pag.getObservacion());
        ps.setDouble(9, pag.getValores());
        ps.setDouble(10, pag.getCotizacionmoneda());
        ps.setInt(11, pag.getCodusuario());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarDetalle(pag.getIdpagos(), detalle, con);
            guardadoforma = guardarFormaPago(pag.getIdpagos(), detalleformapago, con);
            guardabanco = guardarDebitoCredito(pag.getIdpagos(), detallebanco, con);
        }
        st.close();
        ps.close();
        return pag;
    }

    public boolean borrarPagos(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM pagos WHERE idpagos=?");
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

    public pago ActualizarPagos(pago pag, String detalle, String detalleformapago, String detallebanco) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardadoforma = false;
        boolean guardabanco = false;
        String id = null;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE pagos SET sucursal=?,recibo=?,fecha=?,proveedor=?,moneda=?,totalpago=?,observacion=?,valores=?,cotizacionmoneda=?,codusuario=? WHERE idpagos= '" + pag.getIdpagos() + "'");
        ps.setInt(1, pag.getSucursal().getCodigo());
        ps.setString(2, pag.getRecibo());
        ps.setDate(3, pag.getFecha());
        ps.setInt(4, pag.getProveedor().getCodigo());
        ps.setInt(5, pag.getMoneda().getCodigo());
        ps.setDouble(6, pag.getTotalpago());
        ps.setString(7, pag.getObservacion());
        ps.setDouble(8, pag.getValores());
        ps.setDouble(9, pag.getCotizacionmoneda());
        ps.setInt(10, pag.getCodusuario());
        id = pag.getIdpagos();
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarDetalle(id, detalle, con);
            guardadoforma = guardarFormaPago(id, detalleformapago, con);
            guardabanco = guardarDebitoCredito(id, detallebanco, con);
        }
        st.close();
        ps.close();
        return pag;
    }

    public boolean guardarDetalle(String id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement psdetalle = null;
        psdetalle = st.getConnection().prepareStatement("DELETE FROM detallepagos WHERE iddetalle=?");
        psdetalle.setString(1, id);
        int rowsUpdated = psdetalle.executeUpdate();
        
        
        
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalle);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO detallepagos("
                            + "iddetalle,"
                            + "idfactura,"
                            + "nrofactura,"
                            + "emision,"
                            + "comprobante,"
                            + "pago,"
                            + "numerocuota,"
                            + "cuota,"
                            + "vencecuota"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("idfactura").getAsString());
                        ps.setString(3, obj.get("nrofactura").getAsString());
                        ps.setString(4, obj.get("emision").getAsString());
                        ps.setString(5, obj.get("comprobante").getAsString());
                        ps.setDouble(6, obj.get("pago").getAsDouble());
                        ps.setInt(7, obj.get("numerocuota").getAsInt());
                        ps.setInt(8, obj.get("cuota").getAsInt());
                        ps.setString(9, obj.get("vencecuota").getAsString());
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
        // conn.close();
        return guardado;
    }

    public boolean guardarFormaPago(String id, String detalleformapago, Conexion conexion) throws SQLException {
        boolean guardadoforma = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalleformapago);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO detalle_forma_pago("
                            + "idmovimiento,"
                            + "forma,"
                            + "banco,"
                            + "nrocheque,"
                            + "confirmacion,"
                            + "netocobrado"
                            + ") "
                            + "values(?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("forma").getAsString());
                        ps.setString(3, obj.get("banco").getAsString());
                        ps.setString(4, obj.get("nrocheque").getAsString());
                        ps.setString(5, obj.get("confirmacion").getAsString());
                        ps.setDouble(6, obj.get("netocobrado").getAsDouble());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardadoforma = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardadoforma = false;
                    break;
                }
            }

            if (guardadoforma) {
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
            guardadoforma = false;
        }
        //st.close();
        //  conn.close();
        return guardadoforma;
    }

    public boolean guardarDebitoCredito(String id, String detallebanco, Conexion conexion) throws SQLException {
        boolean guardabanco = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detallebanco);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO extracciones("
                            + "idmovimiento,"
                            + "documento,"
                            + "proveedor,"
                            + "fecha,"
                            + "sucursal,"
                            + "banco,"
                            + "moneda,"
                            + "cotizacion,"
                            + "importe,"
                            + "chequenro,"
                            + "observaciones,"
                            +"tipo,"
                            + "vencimiento"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("documento").getAsString());
                        ps.setString(3, obj.get("proveedor").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("sucursal").getAsString());
                        ps.setString(6, obj.get("banco").getAsString());
                        ps.setString(7, obj.get("moneda").getAsString());
                        ps.setString(8, obj.get("cotizacion").getAsString());
                        ps.setString(9, obj.get("importe").getAsString());
                        ps.setString(10, obj.get("chequenro").getAsString());
                        ps.setString(11, obj.get("observaciones").getAsString());
                        ps.setString(12, obj.get("tipo").getAsString());
                        ps.setString(13, obj.get("vencimiento").getAsString());

                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardabanco = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardabanco = false;
                    break;
                }
            }

            if (guardabanco) {
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
            guardabanco = false;
        }
        st.close();
        conn.close();
        return guardabanco;
    }

    public ArrayList<pago> Resumenpagoxsucursal(Date fechaini, Date fechafin, int nSucursal, int nSuc, int nMoneda) throws SQLException {
        ArrayList<pago> lista = new ArrayList<pago>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "select pagos.numero,pagos.recibo,pagos.fecha,pagos.proveedor,proveedores.nombre AS nombrepro,"
                    + " pagos.totalpago,pagos.moneda,monedas.nombre AS nombremoneda,pagos.sucursal,sucursales.nombre AS nombresucursal"
                    + " FROM pagos "
                    + " LEFT JOIN proveedores "
                    + " ON proveedores.codigo=pagos.proveedor "
                    + " LEFT JOIN monedas "
                    + " on monedas.codigo=pagos.moneda "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=pagos.sucursal "
                    + " WHERE pagos.fecha BETWEEN ? AND ?  "
                    + " AND IF(?<>0,pagos.sucursal=?,TRUE) "
                    + " AND pagos.moneda=?  "
                    + " ORDER BY pagos.sucursal,pagos.fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();

                    pago pa = new pago();

                    pa.setProveedor(proveedor);
                    pa.setMoneda(moneda);
                    pa.setSucursal(sucursal);

                    pa.setNumero(rs.getInt("numero"));
                    pa.setRecibo(rs.getString("recibo"));
                    pa.setFecha(rs.getDate("fecha"));
                    pa.getProveedor().setCodigo(rs.getInt("proveedor"));
                    pa.getProveedor().setNombre(rs.getString("nombrepro"));
                    pa.setTotalpago(rs.getDouble("totalpago"));
                    pa.getMoneda().setCodigo(rs.getInt("moneda"));
                    pa.getMoneda().setNombre(rs.getString("nombremoneda"));
                    pa.getSucursal().setCodigo(rs.getInt("sucursal"));
                    pa.getSucursal().setNombre(rs.getString("nombresucursal"));

                    lista.add(pa);
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

    public ArrayList<pago> Resumenpagoxproveedor(Date fechaini, Date fechafin, int nProveedor, int nPro, int nMoneda) throws SQLException {
        ArrayList<pago> lista = new ArrayList<pago>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "select pagos.numero,pagos.recibo,pagos.fecha,pagos.proveedor,proveedores.nombre AS nombrepro,"
                    + " pagos.totalpago,pagos.moneda,monedas.nombre AS nombremoneda,pagos.sucursal,sucursales.nombre AS nombresucursal"
                    + " FROM pagos "
                    + " LEFT JOIN proveedores "
                    + " ON proveedores.codigo=pagos.proveedor "
                    + " LEFT JOIN monedas "
                    + " on monedas.codigo=pagos.moneda "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=pagos.sucursal "
                    + " WHERE pagos.fecha BETWEEN ? AND ?  "
                    + " AND IF(?<>0,pagos.proveedor=?,TRUE) "
                    + " AND pagos.moneda=?  "
                    + " ORDER BY pagos.proveedor,pagos.fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nProveedor);
                ps.setInt(4, nPro);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();

                    pago pa = new pago();

                    pa.setProveedor(proveedor);
                    pa.setMoneda(moneda);
                    pa.setSucursal(sucursal);

                    pa.setNumero(rs.getInt("numero"));
                    pa.setRecibo(rs.getString("recibo"));
                    pa.setFecha(rs.getDate("fecha"));
                    pa.getProveedor().setCodigo(rs.getInt("proveedor"));
                    pa.getProveedor().setNombre(rs.getString("nombrepro"));
                    pa.setTotalpago(rs.getDouble("totalpago"));
                    pa.getMoneda().setCodigo(rs.getInt("moneda"));
                    pa.getMoneda().setNombre(rs.getString("nombremoneda"));
                    pa.getSucursal().setCodigo(rs.getInt("sucursal"));
                    pa.getSucursal().setNombre(rs.getString("nombresucursal"));
                    lista.add(pa);
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

    public ArrayList<pago> Detallepagoxsucursal(Date fechaini, Date fechafin, int nSucursal, int nSuc, int nMoneda) throws SQLException {
        ArrayList<pago> lista = new ArrayList<pago>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = " SELECT pagos.numero,detallepagos.nrofactura,pagos.recibo,pagos.fecha,detallepagos.emision,comprobantes.nombre AS nombrecomprobante,"
                    + " proveedores.nombre AS nombrepro,detallepagos.pago AS totalpago,monedas.nombre AS nombremoneda,pagos.sucursal,sucursales.nombre AS nombresucursal"
                    + " FROM pagos "
                    + " LEFT JOIN detallepagos "
                    + " ON detallepagos.iddetalle=pagos.idpagos "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=detallepagos.comprobante "
                    + " LEFT JOIN proveedores "
                    + " ON proveedores.codigo=pagos.proveedor "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=pagos.moneda "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=pagos.sucursal "
                    + " WHERE pagos.fecha BETWEEN ? AND ?  "
                    + " AND IF(?<>0,pagos.sucursal=?,TRUE) "
                    + " AND pagos.moneda=?  "
                    + " ORDER BY pagos.sucursal,pagos.fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();
                    detallepago detallepago = new detallepago();
                    comprobante cm = new comprobante();
                    pago pa = new pago();

                    pa.setProveedor(proveedor);
                    pa.setMoneda(moneda);
                    pa.setSucursal(sucursal);
                    pa.setDetallepago(detallepago);
                    pa.getDetallepago().setComprobante(cm);
                    pa.setNumero(rs.getInt("numero"));
                    pa.getDetallepago().setNrofactura(rs.getDouble("nrofactura"));
                    pa.setRecibo(rs.getString("recibo"));
                    pa.setFecha(rs.getDate("fecha"));
                    pa.getDetallepago().setEmision(rs.getDate("emision"));
                    pa.getDetallepago().getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    pa.getProveedor().setNombre(rs.getString("nombrepro"));
                    pa.getDetallepago().setPago(rs.getDouble("totalpago"));
                    pa.getMoneda().setNombre(rs.getString("nombremoneda"));
                    pa.getSucursal().setCodigo(rs.getInt("sucursal"));
                    pa.getSucursal().setNombre(rs.getString("nombresucursal"));
                    lista.add(pa);
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

    public ArrayList<pago> Detallepagoxproveedor(Date fechaini, Date fechafin, int nProveedor, int nPro, int nMoneda) throws SQLException {
        ArrayList<pago> lista = new ArrayList<pago>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = " SELECT pagos.numero,detallepagos.nrofactura,pagos.recibo,pagos.fecha,detallepagos.emision,comprobantes.nombre AS nombrecomprobante,"
                    + " proveedores.nombre AS nombrepro,detallepagos.pago AS totalpago,monedas.nombre AS nombremoneda,pagos.sucursal,sucursales.nombre AS nombresucursal"
                    + " FROM pagos "
                    + " LEFT JOIN detallepagos "
                    + " ON detallepagos.iddetalle=pagos.idpagos "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=detallepagos.comprobante "
                    + " LEFT JOIN proveedores "
                    + " ON proveedores.codigo=pagos.proveedor "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=pagos.moneda "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=pagos.sucursal "
                    + " WHERE pagos.fecha BETWEEN ? AND ?  "
                    + " AND IF(?<>0,pagos.proveedor=?,TRUE) "
                    + " AND pagos.moneda=?  "
                    + " ORDER BY pagos.proveedor,pagos.fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nProveedor);
                ps.setInt(4, nPro);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();
                    detallepago detallepago = new detallepago();
                    comprobante com = new comprobante();
                    pago pa = new pago();

                    pa.setProveedor(proveedor);
                    pa.setMoneda(moneda);
                    pa.setSucursal(sucursal);

                    pa.setDetallepago(detallepago);
                    pa.getDetallepago().setComprobante(com);

                    pa.setNumero(rs.getInt("numero"));
                    pa.getDetallepago().setNrofactura(rs.getDouble("nrofactura"));
                    pa.setRecibo(rs.getString("recibo"));
                    pa.setFecha(rs.getDate("fecha"));
                    pa.getDetallepago().setEmision(rs.getDate("emision"));
                    pa.getDetallepago().getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    pa.getProveedor().setNombre(rs.getString("nombrepro"));
                    pa.getDetallepago().setPago(rs.getDouble("totalpago"));

                    pa.getMoneda().setNombre(rs.getString("nombremoneda"));
                    pa.getSucursal().setCodigo(rs.getInt("sucursal"));
                    pa.getSucursal().setNombre(rs.getString("nombresucursal"));

                    lista.add(pa);
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

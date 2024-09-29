/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.caja;
import Modelo.cliente;
import Modelo.cobrador;
import Modelo.cobranza;
import Modelo.cobranzavencxpropietario;
import Modelo.cobranzaxpropietario;
import Modelo.comprobante;
import Modelo.cuenta_clientes;
import Modelo.detalle_cobranza;
import Modelo.detalle_forma_cobro;
import Modelo.edificio;
import Modelo.inmueble;
import Modelo.moneda;
import Modelo.propietario;
import Modelo.sucursal;
import Modelo.usuario;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.math.BigDecimal;
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
public class cobranzaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cobranza> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cobranza> lista = new ArrayList<cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {
            String sql = "SELECT cobranzas.idpagos,cobranzas.numero,cobranzas.fecha,cobranzas.cobrador,cobranzas.moneda,cobranzas.sucursal,cobranzas.cliente,"
                    + "cobranzas.cotizacionmoneda,cobranzas.totalpago,cobranzas.observacion,cobranzas.asiento,cobranzas.sucambio,cobranzas.descuentos,cobranzas.totalcobrado,"
                    + "cobranzas.nrofactura,cobranzas.cobrar_visita,cobranzas.codusuario,cobranzas.enviocobrador,cobranzas.caja,cobranzas.turno,cobranzas.estado,"
                    + "cobradores.nombre AS nombrecobrador,monedas.nombre AS nombremoneda,sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,"
                    + "cajas.nombre AS nombrecaja,usuarios.first_name as nombreusuario,cobranzas.aporte "
                    + "FROM cobranzas "
                    + "LEFT JOIN cobradores "
                    + "ON cobradores.codigo=cobranzas.cobrador "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=cobranzas.moneda "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cobranzas.sucursal "
                    + "LEFT JOIN cajas "
                    + "ON cajas.codigo=cobranzas.caja "
                    + "LEFT JOIN usuarios "
                    + " ON usuarios.employee_id=cobranzas.codusuario "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=cobranzas.cliente "
                    + "WHERE cobranzas.fecha between '" + fechaini + "' AND '" + fechafin + "' "
                    + " ORDER BY cobranzas.numero ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    System.out.println("NOMBRECLIENTE");
                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();
                    caja caja = new caja();
                    moneda moneda = new moneda();
                    cobrador cobrador = new cobrador();
                    usuario usuario = new usuario();
                    cobranza cobro = new cobranza();

                    cobro.setCodusuario(usuario);
                    cobro.setCliente(cliente);
                    cobro.setSucursal(sucursal);
                    cobro.setMoneda(moneda);
                    cobro.setCaja(caja);
                    cobro.setCobrador(cobrador);

                    cobro.setIdpagos(rs.getString("idpagos"));
                    cobro.setFecha(rs.getDate("fecha"));
                    cobro.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cobro.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cobro.getMoneda().setCodigo(rs.getInt("moneda"));
                    cobro.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cobro.getCliente().setCodigo(rs.getInt("cliente"));
                    cobro.getCliente().setNombre(rs.getString("nombrecliente"));
                    cobro.getCaja().setCodigo(rs.getInt("caja"));
                    cobro.getCaja().setNombre(rs.getString("nombrecaja"));
                    cobro.getCobrador().setCodigo(rs.getInt("cobrador"));
                    cobro.getCobrador().setNombre(rs.getString("nombrecobrador"));
                    cobro.getCodusuario().setEmployee_id(rs.getInt("codusuario"));
                    cobro.getCodusuario().setFirst_name(rs.getString("nombreusuario"));
                    cobro.setNumero(rs.getBigDecimal("numero"));
                    cobro.setAsiento(rs.getBigDecimal("asiento"));
                    cobro.setCotizacionmoneda(rs.getBigDecimal("cotizacionmoneda"));
                    cobro.setEstado(rs.getString("estado"));
                    cobro.setObservacion(rs.getString("observacion"));
                    cobro.setTotalpago(rs.getBigDecimal("totalpago"));
                    cobro.setAporte(rs.getBigDecimal("aporte"));
                    lista.add(cobro);
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

    public cobranza insertarCobranza(cobranza ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        String id = "";
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cobranzas  (idpagos,numero,fecha,cliente,moneda,totalpago,observacion,valores,cobrador,sucursal,cotizacionmoneda,descuentos,enviocobrador,codusuario,caja,turno,aporte) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdpagos());
        ps.setBigDecimal(2, ocr.getNumero());
        ps.setDate(3, ocr.getFecha());
        ps.setInt(4, ocr.getCliente().getCodigo());
        ps.setInt(5, ocr.getMoneda().getCodigo());
        ps.setBigDecimal(6, ocr.getTotalpago());
        ps.setString(7, ocr.getObservacion());
        ps.setBigDecimal(8, ocr.getValores());
        ps.setInt(9, ocr.getCobrador().getCodigo());
        ps.setInt(10, ocr.getSucursal().getCodigo());
        ps.setBigDecimal(11, ocr.getCotizacionmoneda());
        ps.setBigDecimal(12, ocr.getDescuentos());
        ps.setInt(13, ocr.getEnviocobrador());
        ps.setInt(14, ocr.getCodusuario().getEmployee_id());
        ps.setInt(15, ocr.getCaja().getCodigo());
        ps.setInt(16, ocr.getTurno());
        ps.setBigDecimal(17, ocr.getAporte());
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            id = ocr.getIdpagos();
            guardado = guardarDetalleCobranza(id, detalle, con);
            ActualizarCaja(ocr.getCaja().getCodigo(), ocr.getNumero());
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public boolean borrarCobranza(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cobranzas WHERE idpagos=?");
        ps.setInt(1, id);
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        st.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    
    public boolean AnularCobranza(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cobranzas SET estado='ANULADO' WHERE idpagos=?");
        ps.setString(1, id);
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        st.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    
    
    public boolean ActualizarCaja(int caja, BigDecimal recibo) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE cajas SET recibo=? WHERE codigo=?");
        ps.setBigDecimal(1, recibo);
        ps.setInt(2, caja);
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

    public boolean borrarDetalleCobranza(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_cobranzas WHERE iddetalle=?");
        ps.setString(1, referencia);
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

    public cobranza buscarId(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        cobranza cobranza = new cobranza();

        try {

            String sql = "SELECT idpagos,numero,totalpago "
                    + "FROM cobranzas "
                    + "WHERE cobranzas.idpagos=? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cobranza.setIdpagos(rs.getString("idpagos"));
                    cobranza.setNumero(rs.getBigDecimal("numero"));
                    cobranza.setTotalpago(rs.getBigDecimal("totalpago"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return cobranza;
    }

    public boolean guardarDetalleCobranza(String id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
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

                    String sql = "INSERT INTO detalle_cobranzas("
                            + "iddetalle,"
                            + "idfactura,"
                            + "nrofactura,"
                            + "emision,"
                            + "comprobante,"
                            + "pago,"
                            + "capital,"
                            + "diamora,"
                            + "mora,"
                            + "gastos_cobranzas,"
                            + "moneda,"
                            + "amortiza,"
                            + "minteres,"
                            + "vence,"
                            + "acreedor,"
                            + "cuota,"
                            + "numerocuota,"
                            + "importe_iva,"
                            + "punitorio,"
                            + "fechacobro"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddetalle").getAsString());
                        ps.setString(2, obj.get("idfactura").getAsString());
                        ps.setString(3, obj.get("nrofactura").getAsString());
                        ps.setString(4, obj.get("emision").getAsString());
                        ps.setString(5, obj.get("comprobante").getAsString());
                        ps.setString(6, obj.get("pago").getAsString());
                        ps.setString(7, obj.get("capital").getAsString());
                        ps.setString(8, obj.get("diamora").getAsString());
                        ps.setString(9, obj.get("mora").getAsString());
                        ps.setString(10, obj.get("gastos_cobranzas").getAsString());
                        ps.setString(11, obj.get("moneda").getAsString());
                        ps.setString(12, obj.get("amortiza").getAsString());
                        ps.setString(13, obj.get("minteres").getAsString());
                        ps.setString(14, obj.get("vence").getAsString());
                        ps.setString(15, obj.get("acreedor").getAsString());
                        ps.setString(16, obj.get("cuota").getAsString());
                        ps.setString(17, obj.get("numerocuota").getAsString());
                        ps.setString(18, obj.get("importe_iva").getAsString());
                        ps.setString(19, obj.get("punitorio").getAsString());
                        ps.setString(20, obj.get("fechacobro").getAsString());
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
        st.close();
        conn.close();
        return guardado;
    }

    public ArrayList<cobranza> MostrarxFechaxCaja(Date fechaini, Date fechafin, int ncaja1, int ncaja2, int nmoneda1, int nmoneda2) throws SQLException {
        ArrayList<cobranza> lista = new ArrayList<cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {
            String sql = "SELECT cobranzas.idpagos,cobranzas.numero,cobranzas.fecha,cobranzas.cliente,cobranzas.caja,cobranzas.moneda,cobranzas.sucursal,"
                    + "detalle_cobranzas.nrofactura,detalle_cobranzas.pago,detalle_cobranzas.amortiza,detalle_cobranzas.minteres,detalle_cobranzas.gastos_cobranzas,"
                    + "detalle_cobranzas.mora,detalle_cobranzas.punitorio,detalle_cobranzas.numerocuota,detalle_cobranzas.cuota,"
                    + "detalle_cobranzas.comprobante,sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,"
                    + "monedas.etiqueta AS nombremoneda,cajas.nombre AS nombrecaja,cobranzas.cobrador,comprobantes.nombre as nombrecomprobante "
                    + " FROM cobranzas"
                    + " INNER JOIN detalle_cobranzas "
                    + " ON cobranzas.idpagos=detalle_cobranzas.iddetalle"
                    + " LEFT JOIN sucursales"
                    + " ON sucursales.codigo=cobranzas.sucursal"
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=cobranzas.cliente"
                    + " LEFT JOIN monedas"
                    + " ON monedas.codigo=cobranzas.moneda"
                    + " LEFT JOIN comprobantes"
                    + " ON comprobantes.codigo=detalle_cobranzas.comprobante"
                    + " LEFT JOIN cajas"
                    + " ON cajas.codigo=cobranzas.caja"
                    + " WHERE cobranzas.fecha between ? AND ? "
                    + " AND IF(?<>0,cobranzas.caja=?,TRUE) "
                    + " AND IF(?<>0,cobranzas.moneda=?,TRUE) "
                    + " ORDER BY cobranzas.caja,cobranzas.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, ncaja1);
                ps.setInt(4, ncaja2);
                ps.setInt(5, nmoneda1);
                ps.setInt(6, nmoneda2);

                System.out.println(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();
                    caja caja = new caja();
                    moneda moneda = new moneda();

                    cobranza cobro = new cobranza();
                    cobro.setCliente(cliente);
                    cobro.setSucursal(sucursal);
                    cobro.setMoneda(moneda);
                    cobro.setCaja(caja);

                    cobro.setIdpagos(rs.getString("idpagos"));
                    cobro.setNrofactura(rs.getBigDecimal("nrofactura"));
                    cobro.setFecha(rs.getDate("fecha"));
                    cobro.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cobro.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cobro.getMoneda().setCodigo(rs.getInt("moneda"));
                    cobro.getMoneda().setEtiqueta(rs.getString("nombremoneda"));
                    cobro.getCliente().setCodigo(rs.getInt("cliente"));
                    cobro.getCliente().setNombre(rs.getString("nombrecliente"));
                    cobro.getCaja().setCodigo(rs.getInt("caja"));
                    cobro.getCaja().setNombre(rs.getString("nombrecaja"));
                    cobro.setNumero(rs.getBigDecimal("numero"));
                    cobro.setPago(rs.getBigDecimal("pago"));
                    cobro.setAmortiza(rs.getBigDecimal("amortiza"));
                    cobro.setMinteres(rs.getBigDecimal("minteres"));
                    cobro.setGastos_cobranzas(rs.getBigDecimal("gastos_cobranzas"));
                    cobro.setMora(rs.getBigDecimal("mora"));
                    cobro.setPunitorio(rs.getBigDecimal("punitorio"));
                    cobro.setNumerocuota(rs.getInt("numerocuota"));
                    cobro.setCuota(rs.getInt("cuota"));
                    cobro.setNombrecomprobante(rs.getString("nombrecomprobante"));
                    lista.add(cobro);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<cobranza> MostrarxFechaxUsuario(Date fechaini, Date fechafin, int nusua1, int nmoneda1) throws SQLException {
        ArrayList<cobranza> lista = new ArrayList<cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT cobranzas.idpagos,cobranzas.numero,cobranzas.fecha,cobranzas.cliente,cobranzas.caja,cobranzas.codusuario,cobranzas.moneda,cobranzas.sucursal,"
                    + "detalle_cobranzas.nrofactura,detalle_cobranzas.pago,detalle_cobranzas.amortiza,detalle_cobranzas.minteres,detalle_cobranzas.gastos_cobranzas,"
                    + "detalle_cobranzas.mora,detalle_cobranzas.punitorio,detalle_cobranzas.numerocuota,detalle_cobranzas.cuota,"
                    + "detalle_cobranzas.comprobante,sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,"
                    + "monedas.etiqueta AS nombremoneda,usuarios.first_name AS nombreusuario,cobranzas.cobrador,comprobantes.nombre as nombrecomprobante "
                    + " FROM cobranzas"
                    + " INNER JOIN detalle_cobranzas "
                    + " ON cobranzas.idpagos=detalle_cobranzas.iddetalle"
                    + " LEFT JOIN sucursales"
                    + " ON sucursales.codigo=cobranzas.sucursal"
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=cobranzas.cliente"
                    + " LEFT JOIN monedas"
                    + " ON monedas.codigo=cobranzas.moneda"
                    + " LEFT JOIN comprobantes"
                    + " ON comprobantes.codigo=detalle_cobranzas.comprobante"
                    + " LEFT JOIN usuarios "
                    + " ON usuarios.employee_id=cobranzas.codusuario"
                    + " WHERE cobranzas.fecha between'" + fechaini + "' AND  '" + fechafin + "'"
                    + " AND cobranzas.codusuario= " + nusua1
                    + " AND cobranzas.moneda= " + nmoneda1
                    + " ORDER BY cobranzas.codusuario,cobranzas.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

                System.out.println(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();
                    moneda moneda = new moneda();
                    cobranza cobro = new cobranza();
                    usuario usu = new usuario();

                    cobro.setCliente(cliente);
                    cobro.setSucursal(sucursal);
                    cobro.setMoneda(moneda);
                    cobro.setCodusuario(usu);

                    cobro.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cobro.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cobro.getCodusuario().setEmployee_id(rs.getInt("codusuario"));
                    cobro.getCodusuario().setFirst_name(rs.getString("nombreusuario"));

                    cobro.setIdpagos(rs.getString("idpagos"));
                    cobro.setNrofactura(rs.getBigDecimal("nrofactura"));
                    cobro.setFecha(rs.getDate("fecha"));
                    cobro.getMoneda().setCodigo(rs.getInt("moneda"));
                    cobro.getMoneda().setEtiqueta(rs.getString("nombremoneda"));
                    cobro.getCliente().setCodigo(rs.getInt("cliente"));
                    cobro.getCliente().setNombre(rs.getString("nombrecliente"));
                    cobro.setNumero(rs.getBigDecimal("numero"));
                    cobro.setPago(rs.getBigDecimal("pago"));
                    cobro.setAmortiza(rs.getBigDecimal("amortiza"));
                    cobro.setMinteres(rs.getBigDecimal("minteres"));
                    cobro.setGastos_cobranzas(rs.getBigDecimal("gastos_cobranzas"));
                    cobro.setMora(rs.getBigDecimal("mora"));
                    cobro.setPunitorio(rs.getBigDecimal("punitorio"));
                    cobro.setNumerocuota(rs.getInt("numerocuota"));
                    cobro.setCuota(rs.getInt("cuota"));
                    cobro.setNombrecomprobante(rs.getString("nombrecomprobante"));
                    lista.add(cobro);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<cobranza> MostrarxDiaCobro(Date fechaini, Date fechafin, int ntipo) throws SQLException {
        ArrayList<cobranza> lista = new ArrayList<cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {
            String sql = "SELECT cobranzas.idpagos,cobranzas.numero,cobranzas.fecha,cobranzas.cliente,cobranzas.caja,"
                    + "cobranzas.codusuario,cobranzas.moneda,cobranzas.sucursal,cobranzas.formatofactura,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,cobranzas.asiento,"
                    + "monedas.etiqueta AS nombremoneda,usuarios.first_name AS nombreusuario,cobranzas.cobrador, "
                    + "cobranzas.cotizacionmoneda,cajas.nombre as nombrecaja,cobradores.nombre as nombrecobrador, cobranzas.estado,"
                    + "cobranzas.totalpago "
                    + " FROM cobranzas "
                    + " LEFT JOIN cajas "
                    + " ON cajas.codigo=cobranzas.sucursal "
                    + " LEFT JOIN sucursales"
                    + " ON sucursales.codigo=cobranzas.sucursal "
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=cobranzas.cliente "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=cobranzas.moneda "
                    + " LEFT JOIN usuarios "
                    + " ON usuarios.employee_id=cobranzas.codusuario"
                    + " LEFT JOIN cobradores "
                    + " ON cobradores.codigo=cobranzas.cobrador "
                    + " WHERE cobranzas.fecha between ? AND ? "
                    + " AND cobranzas.tipo=? "
                    + " ORDER BY cobranzas.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, ntipo);
                System.out.println(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();
                    caja caja = new caja();
                    moneda moneda = new moneda();
                    cobranza cobro = new cobranza();
                    usuario usu = new usuario();
                    cobrador cob = new cobrador();
                    cobro.setCliente(cliente);
                    cobro.setSucursal(sucursal);
                    cobro.setMoneda(moneda);
                    cobro.setCaja(caja);
                    cobro.setCodusuario(usu);
                    cobro.setCobrador(cob);

                    cobro.setIdpagos(rs.getString("idpagos"));
                    cobro.setFecha(rs.getDate("fecha"));
                    cobro.setFormatofactura(rs.getString("formatofactura"));
                    cobro.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cobro.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cobro.getMoneda().setCodigo(rs.getInt("moneda"));
                    cobro.getMoneda().setEtiqueta(rs.getString("nombremoneda"));
                    cobro.getCliente().setCodigo(rs.getInt("cliente"));
                    cobro.getCliente().setNombre(rs.getString("nombrecliente"));
                    cobro.getCaja().setCodigo(rs.getInt("caja"));
                    cobro.getCaja().setNombre(rs.getString("nombrecaja"));
                    cobro.getCodusuario().setEmployee_id(rs.getInt("codusuario"));
                    cobro.getCodusuario().setFirst_name(rs.getString("nombreusuario"));
                    cobro.getCobrador().setCodigo(rs.getInt("cobrador"));
                    cobro.getCobrador().setNombre(rs.getString("nombrecobrador"));
                    cobro.setNumero(rs.getBigDecimal("numero"));
                    cobro.setAsiento(rs.getBigDecimal("asiento"));
                    cobro.setCotizacionmoneda(rs.getBigDecimal("cotizacionmoneda"));
                    cobro.setTotalpago(rs.getBigDecimal("totalpago"));
                    cobro.setEstado(rs.getString("estado"));
                    lista.add(cobro);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }


    public ArrayList<cobranza> MostrarxDiaCobroUsuario(Date fechaini, Date fechafin, int ntipo,int nusuario) throws SQLException {
        ArrayList<cobranza> lista = new ArrayList<cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {
            String sql = "SELECT cobranzas.idpagos,cobranzas.numero,cobranzas.fecha,cobranzas.cliente,cobranzas.caja,"
                    + "cobranzas.codusuario,cobranzas.moneda,cobranzas.sucursal,cobranzas.formatofactura,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,cobranzas.asiento,"
                    + "monedas.etiqueta AS nombremoneda,usuarios.first_name AS nombreusuario,cobranzas.cobrador, "
                    + "cobranzas.cotizacionmoneda,cajas.nombre as nombrecaja,cobradores.nombre as nombrecobrador, cobranzas.estado,"
                    + "cobranzas.totalpago "
                    + " FROM cobranzas "
                    + " LEFT JOIN cajas "
                    + " ON cajas.codigo=cobranzas.sucursal "
                    + " LEFT JOIN sucursales"
                    + " ON sucursales.codigo=cobranzas.sucursal "
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=cobranzas.cliente "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=cobranzas.moneda "
                    + " LEFT JOIN usuarios "
                    + " ON usuarios.employee_id=cobranzas.codusuario"
                    + " LEFT JOIN cobradores "
                    + " ON cobradores.codigo=cobranzas.cobrador "
                    + " WHERE cobranzas.fecha between ? AND ? "
                    + " AND cobranzas.tipo=? "
                    + " AND cobranzas.codusuario=? "
                    + " ORDER BY cobranzas.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, ntipo);
                ps.setInt(4, nusuario);
                System.out.println(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();
                    caja caja = new caja();
                    moneda moneda = new moneda();
                    cobranza cobro = new cobranza();
                    usuario usu = new usuario();
                    cobrador cob = new cobrador();
                    cobro.setCliente(cliente);
                    cobro.setSucursal(sucursal);
                    cobro.setMoneda(moneda);
                    cobro.setCaja(caja);
                    cobro.setCodusuario(usu);
                    cobro.setCobrador(cob);

                    cobro.setIdpagos(rs.getString("idpagos"));
                    cobro.setFecha(rs.getDate("fecha"));
                    cobro.setFormatofactura(rs.getString("formatofactura"));
                    cobro.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cobro.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cobro.getMoneda().setCodigo(rs.getInt("moneda"));
                    cobro.getMoneda().setEtiqueta(rs.getString("nombremoneda"));
                    cobro.getCliente().setCodigo(rs.getInt("cliente"));
                    cobro.getCliente().setNombre(rs.getString("nombrecliente"));
                    cobro.getCaja().setCodigo(rs.getInt("caja"));
                    cobro.getCaja().setNombre(rs.getString("nombrecaja"));
                    cobro.getCodusuario().setEmployee_id(rs.getInt("codusuario"));
                    cobro.getCodusuario().setFirst_name(rs.getString("nombreusuario"));
                    cobro.getCobrador().setCodigo(rs.getInt("cobrador"));
                    cobro.getCobrador().setNombre(rs.getString("nombrecobrador"));
                    cobro.setNumero(rs.getBigDecimal("numero"));
                    cobro.setAsiento(rs.getBigDecimal("asiento"));
                    cobro.setCotizacionmoneda(rs.getBigDecimal("cotizacionmoneda"));
                    cobro.setTotalpago(rs.getBigDecimal("totalpago"));
                    cobro.setEstado(rs.getString("estado"));
                    lista.add(cobro);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }


    public cobranza insertarCobrosInmobiliaria(cobranza ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        String id = "";
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cobranzas  (idpagos,numero,fecha,cliente,moneda,totalpago,observacion,valores,cobrador,sucursal,cotizacionmoneda,descuentos,enviocobrador,codusuario,caja,turno,formatofactura,tipo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdpagos());
        ps.setBigDecimal(2, ocr.getNumero());
        ps.setDate(3, ocr.getFecha());
        ps.setInt(4, ocr.getCliente().getCodigo());
        ps.setInt(5, ocr.getMoneda().getCodigo());
        ps.setBigDecimal(6, ocr.getTotalpago());
        ps.setString(7, ocr.getObservacion());
        ps.setBigDecimal(8, ocr.getValores());
        ps.setInt(9, ocr.getCobrador().getCodigo());
        ps.setInt(10, ocr.getSucursal().getCodigo());
        ps.setBigDecimal(11, ocr.getCotizacionmoneda());
        ps.setBigDecimal(12, ocr.getDescuentos());
        ps.setInt(13, ocr.getEnviocobrador());
        ps.setInt(14, ocr.getCodusuario().getEmployee_id());
        ps.setInt(15, ocr.getCaja().getCodigo());
        ps.setInt(16, ocr.getTurno());
        ps.setString(17, ocr.getFormatofactura());
        ps.setInt(18, ocr.getTipo());
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            id = ocr.getIdpagos();
            guardado = guardarDetalleCobrosInmo(id, detalle, con);
            ActualizarCaja(ocr.getCaja().getCodigo(), ocr.getNumero());
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public boolean guardarDetalleCobrosInmo(String id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
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
                    String sql = "INSERT INTO detalle_cobranzas("
                            + "iddetalle,"
                            + "idfactura,"
                            + "nrofactura,"
                            + "emision,"
                            + "comprobante,"
                            + "pago,"
                            + "alquiler,"
                            + "multa,"
                            + "expensa,"
                            + "moneda,"
                            + "saldo,"
                            + "comision,"
                            + "vence,"
                            + "garage,"
                            + "cuota,"
                            + "numerocuota,"
                            + "ivaalquiler,"
                            + "ivamulta,"
                            + "ivagarage,"
                            + "ivacomision,"
                            + "ivaexpensa,"
                            + "garantia,"
                            + "fondo,"
                            + "llave,"
                            + "otros,"
                            + "idunidad,"
                            + "fechacobro "
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddetalle").getAsString());
                        ps.setString(2, obj.get("idfactura").getAsString());
                        ps.setString(3, obj.get("nrofactura").getAsString());
                        ps.setString(4, obj.get("emision").getAsString());
                        ps.setString(5, obj.get("comprobante").getAsString());
                        ps.setString(6, obj.get("pago").getAsString());
                        ps.setString(7, obj.get("alquiler").getAsString());
                        ps.setString(8, obj.get("multa").getAsString());
                        ps.setString(9, obj.get("expensa").getAsString());
                        ps.setString(10, obj.get("moneda").getAsString());
                        ps.setString(11, obj.get("saldo").getAsString());
                        ps.setString(12, obj.get("comision").getAsString());
                        ps.setString(13, obj.get("vence").getAsString());
                        ps.setString(14, obj.get("garage").getAsString());
                        ps.setString(15, obj.get("cuota").getAsString());
                        ps.setString(16, obj.get("numerocuota").getAsString());
                        ps.setString(17, obj.get("ivaalquiler").getAsString());
                        ps.setString(18, obj.get("ivamulta").getAsString());
                        ps.setString(19, obj.get("ivagarage").getAsString());
                        ps.setString(20, obj.get("ivacomision").getAsString());
                        ps.setString(21, obj.get("ivaexpensa").getAsString());
                        ps.setString(22, obj.get("garantia").getAsString());
                        ps.setString(23, obj.get("fondo").getAsString());
                        ps.setString(24, obj.get("llave").getAsString());
                        ps.setString(25, obj.get("otros").getAsString());
                        ps.setString(26, obj.get("idunidad").getAsString());
                        ps.setString(27, obj.get("fechacobro").getAsString());
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
        st.close();
        conn.close();
        return guardado;
    }

    public ArrayList<cobranza> Cobranzasxpropietarios(Date fechaini, Date fechafin, int nPropietario, int nProp) throws SQLException {
        ArrayList<cobranza> lista = new ArrayList<cobranza>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cobranzas.idpagos,cobranzas.formatofactura,cobranzas.fecha,clientes.ruc AS ruccliente,clientes.nombre AS locatario,COALESCE(inmuebles.nomedif,'SD') AS nomedif,COALESCE(propietarios.codpro,'0') AS codpro,COALESCE(CONCAT(propietarios.nombre,' ', propietarios.apellido),'SD') AS nombreprop,"
                    + " COALESCE(detalle_cobranzas.alquiler,'0') AS alquiler,COALESCE(detalle_cobranzas.garage,'0') AS garage,COALESCE(detalle_cobranzas.expensa,'0') AS expensa,COALESCE(detalle_cobranzas.comision,'0') AS comision,COALESCE(detalle_cobranzas.multa,'0') AS multa"
                    + " FROM cobranzas "
                    + "LEFT JOIN detalle_cobranzas "
                    + "ON detalle_cobranzas.iddetalle=cobranzas.idpagos "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cobranzas.cliente "
                    + "LEFT JOIN edificios "
                    + "ON edificios.idunidad=detalle_cobranzas.idunidad "
                    + "LEFT JOIN inmuebles "
                    + "ON edificios.inmueble=inmuebles.idinmueble "
                    + "LEFT JOIN propietarios "
                    + "ON propietarios.codpro=inmuebles.codpro "
                    + " WHERE cobranzas.fecha between ? AND ? "
                    + " AND IF(?<>0,propietarios.codpro=?,TRUE) "
                    + " GROUP BY propietarios.codpro "
                    + " ORDER BY propietarios.codpro ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nPropietario);
                ps.setInt(4, nProp);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cobranza cob = new cobranza();
                    detalle_cobranza detcob = new detalle_cobranza();
                    cliente cli = new cliente();
                    edificio ed = new edificio();
                    inmueble inm = new inmueble();
                    propietario pro = new propietario();

                    cob.setDetalle_cobranza(detcob);
                    cob.setCliente(cli);
                    cob.setEdificio(ed);
                    cob.setInmueble(inm);
                    cob.setPropietario(pro);

                    cob.setIdpagos(rs.getString("idpagos"));
                    cob.setFormatofactura(rs.getString("formatofactura"));
                    cob.setFecha(rs.getDate("fecha"));
                    cob.getCliente().setRuc(rs.getString("ruccliente"));
                    cob.getCliente().setNombre(rs.getString("locatario"));
                    cob.getInmueble().setNomedif(rs.getString("nomedif"));
                    cob.getPropietario().setCodpro(rs.getInt("codpro"));
                    cob.getPropietario().setNombre(rs.getString("nombreprop"));
                    cob.getDetalle_cobranza().setAlquiler(rs.getDouble("alquiler"));
                    cob.getDetalle_cobranza().setGarage(rs.getDouble("garage"));
                    cob.getDetalle_cobranza().setExpensa(rs.getDouble("expensa"));
                    cob.getDetalle_cobranza().setComision(rs.getDouble("comision"));
                    cob.getDetalle_cobranza().setMulta(rs.getDouble("multa"));

                    lista.add(cob);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public cobranza insertarCobro(cobranza ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        String id = "";
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cobranzas  (idpagos,numero,fecha,cliente,moneda,totalpago,"
                + "observacion,valores,cobrador,sucursal,cotizacionmoneda,codusuario,caja,turno,tipo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdpagos());
        ps.setBigDecimal(2, ocr.getNumero());
        ps.setDate(3, ocr.getFecha());
        ps.setInt(4, ocr.getCliente().getCodigo());
        ps.setInt(5, ocr.getMoneda().getCodigo());
        ps.setBigDecimal(6, ocr.getTotalpago());
        ps.setString(7, ocr.getObservacion());
        ps.setBigDecimal(8, ocr.getValores());
        ps.setInt(9, ocr.getCobrador().getCodigo());
        ps.setInt(10, ocr.getSucursal().getCodigo());
        ps.setBigDecimal(11, ocr.getCotizacionmoneda());
        ps.setInt(12, ocr.getCodusuario().getEmployee_id());
        ps.setInt(13, ocr.getCaja().getCodigo());
        ps.setInt(14, ocr.getTurno());
        ps.setInt(15, ocr.getTipo());

        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            id = ocr.getIdpagos();
            guardado = guardarDetalleCobro(id, detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public cobranza ActualizarCobroStandard(cobranza ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        String id = "";
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cobranzas set fecha=?,cliente=?,"
                + "moneda=?,totalpago=?,observacion=?,valores=?,"
                + "cobrador=?,sucursal=?,cotizacionmoneda=?,"
                + "codusuario=?,caja=?,"
                + "turno=?,tipo=?, numero=? where idpagos='" + ocr.getIdpagos() + "'");
        ps.setDate(1, ocr.getFecha());
        ps.setInt(2, ocr.getCliente().getCodigo());
        ps.setInt(3, ocr.getMoneda().getCodigo());
        ps.setBigDecimal(4, ocr.getTotalpago());
        ps.setString(5, ocr.getObservacion());
        ps.setBigDecimal(6, ocr.getValores());
        ps.setInt(7, ocr.getCobrador().getCodigo());
        ps.setInt(8, ocr.getSucursal().getCodigo());
        ps.setBigDecimal(9, ocr.getCotizacionmoneda());
        ps.setInt(10, ocr.getCodusuario().getEmployee_id());
        ps.setInt(11, ocr.getCaja().getCodigo());
        ps.setInt(12, ocr.getTurno());
        ps.setInt(13, ocr.getTipo());
        ps.setBigDecimal(14, ocr.getNumero());
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            id = ocr.getIdpagos();
            guardado = guardarDetalleCobro(id, detalle, con);
        }

        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public boolean guardarDetalleCobro(String id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement psdetalle = null;

        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_cobranzas WHERE iddetalle=?");
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

                    String sql = "INSERT INTO detalle_cobranzas("
                            + "iddetalle,"
                            + "idfactura,"
                            + "nrofactura,"
                            + "emision,"
                            + "comprobante,"
                            + "saldo,"
                            + "pago,"
                            + "moneda,"
                            + "vence,"
                            + "cuota,"
                            + "numerocuota,"
                            + "fechacobro"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?)";

                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddetalle").getAsString());
                        ps.setString(2, obj.get("idfactura").getAsString());
                        ps.setString(3, obj.get("nrofactura").getAsString());
                        ps.setString(4, obj.get("emision").getAsString());
                        ps.setString(5, obj.get("comprobante").getAsString());
                        ps.setString(6, obj.get("saldo").getAsString());
                        ps.setString(7, obj.get("pago").getAsString());
                        ps.setString(8, obj.get("moneda").getAsString());
                        ps.setString(9, obj.get("vence").getAsString());
                        ps.setString(10, obj.get("cuota").getAsString());
                        ps.setString(11, obj.get("numerocuota").getAsString());
                        ps.setString(12, obj.get("fechacobro").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("DETALLE COBROS--->" + ex.getLocalizedMessage());
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
        st.close();
        conn.close();
        return guardado;
    }

    public ArrayList<cobranza> ResumenCobranzaxSucursal(Date fechaini, Date fechafin, int nSucursal, int nSuc, int nMoneda) throws SQLException {
        ArrayList<cobranza> lista = new ArrayList<cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cobranzas.numero,cobranzas.fecha,sucursales.nombre AS nombresucursal,"
                    + "clientes.codigo AS cuenta, clientes.nombre AS nombrecliente,monedas.nombre AS nombremoneda,detalle_forma_cobro.netocobrado "
                    + "FROM cobranzas "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cobranzas.sucursal "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cobranzas.cliente "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=cobranzas.moneda "
                    + "LEFT JOIN detalle_forma_cobro "
                    + "ON detalle_forma_cobro.idmovimiento=cobranzas.idpagos "
                    + "WHERE cobranzas.fecha BETWEEN ? AND ?  "
                    + "AND IF(?<>0,cobranzas.sucursal=?,TRUE) "
                    + "AND cobranzas.moneda=?  "
                    + "AND detalle_forma_cobro.netocobrado<>0  "
                    + "GROUP BY cobranzas.idpagos "
                    + "ORDER BY cobranzas.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();
                    detalle_forma_cobro dtc = new detalle_forma_cobro();

                    cobranza vta = new cobranza();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);
                    vta.setDetalle_forma_cobro(dtc);

                    vta.setNumero(rs.getBigDecimal("numero"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.getCliente().setCodigo(rs.getInt("cuenta"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));
                    vta.getDetalle_forma_cobro().setNetocobrado(rs.getDouble("netocobrado"));

                    lista.add(vta);
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

    public ArrayList<cobranza> MostrarxFechaxCategoriaCliente(Date fechaini, Date fechafin, int ncaja1, int ncaja2, int nmoneda1, int nmoneda2) throws SQLException {
        ArrayList<cobranza> lista = new ArrayList<cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {
            String sql = "SELECT cobranzas.idpagos,cobranzas.numero,cobranzas.fecha,cobranzas.cliente,clientes.categoria as caja,cobranzas.moneda,cobranzas.sucursal,"
                    + "detalle_cobranzas.nrofactura,detalle_cobranzas.pago,detalle_cobranzas.amortiza,detalle_cobranzas.minteres,detalle_cobranzas.gastos_cobranzas,"
                    + "detalle_cobranzas.mora,detalle_cobranzas.punitorio,detalle_cobranzas.numerocuota,detalle_cobranzas.cuota,"
                    + "detalle_cobranzas.comprobante,sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,"
                    + "monedas.etiqueta AS nombremoneda,categoria_clientes.nombre AS nombrecaja,cobranzas.cobrador,comprobantes.nombre as nombrecomprobante "
                    + " FROM cobranzas"
                    + " INNER JOIN detalle_cobranzas "
                    + " ON cobranzas.idpagos=detalle_cobranzas.iddetalle"
                    + " LEFT JOIN sucursales"
                    + " ON sucursales.codigo=cobranzas.sucursal"
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=cobranzas.cliente"
                    + " LEFT JOIN categoria_clientes "
                    + " ON categoria_clientes.codigo=clientes.categoria "
                    + " LEFT JOIN monedas"
                    + " ON monedas.codigo=cobranzas.moneda"
                    + " LEFT JOIN comprobantes"
                    + " ON comprobantes.codigo=detalle_cobranzas.comprobante"
                    + " WHERE cobranzas.fecha between ? AND ? "
                    + " AND IF(?<>0,clientes.categoria=?,TRUE) "
                    + " AND IF(?<>0,cobranzas.moneda=?,TRUE) "
                    + " ORDER BY clientes.categoria,cobranzas.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, ncaja1);
                ps.setInt(4, ncaja2);
                ps.setInt(5, nmoneda1);
                ps.setInt(6, nmoneda2);

                System.out.println(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();
                    caja caja = new caja();
                    moneda moneda = new moneda();

                    cobranza cobro = new cobranza();
                    cobro.setCliente(cliente);
                    cobro.setSucursal(sucursal);
                    cobro.setMoneda(moneda);
                    cobro.setCaja(caja);

                    cobro.setIdpagos(rs.getString("idpagos"));
                    cobro.setNrofactura(rs.getBigDecimal("nrofactura"));
                    cobro.setFecha(rs.getDate("fecha"));
                    cobro.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cobro.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cobro.getMoneda().setCodigo(rs.getInt("moneda"));
                    cobro.getMoneda().setEtiqueta(rs.getString("nombremoneda"));
                    cobro.getCliente().setCodigo(rs.getInt("cliente"));
                    cobro.getCliente().setNombre(rs.getString("nombrecliente"));
                    cobro.getCaja().setCodigo(rs.getInt("caja"));
                    cobro.getCaja().setNombre(rs.getString("nombrecaja"));
                    cobro.setNumero(rs.getBigDecimal("numero"));
                    cobro.setPago(rs.getBigDecimal("pago"));
                    cobro.setAmortiza(rs.getBigDecimal("amortiza"));
                    cobro.setMinteres(rs.getBigDecimal("minteres"));
                    cobro.setGastos_cobranzas(rs.getBigDecimal("gastos_cobranzas"));
                    cobro.setMora(rs.getBigDecimal("mora"));
                    cobro.setPunitorio(rs.getBigDecimal("punitorio"));
                    cobro.setNumerocuota(rs.getInt("numerocuota"));
                    cobro.setCuota(rs.getInt("cuota"));
                    cobro.setNombrecomprobante(rs.getString("nombrecomprobante"));
                    lista.add(cobro);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public cobranza BuscarCobro(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        cobranza cobro = new cobranza();

        try {
            String sql = "SELECT cobranzas.idpagos,cobranzas.numero,cobranzas.fecha,cobranzas.cobrador,cobranzas.moneda,cobranzas.sucursal,cobranzas.cliente,"
                    + "cobranzas.cotizacionmoneda,cobranzas.totalpago,cobranzas.observacion,cobranzas.asiento,cobranzas.sucambio,cobranzas.descuentos,cobranzas.totalcobrado,"
                    + "cobranzas.nrofactura,cobranzas.cobrar_visita,cobranzas.codusuario,cobranzas.enviocobrador,cobranzas.caja,cobranzas.turno,cobranzas.estado,"
                    + "cobradores.nombre AS nombrecobrador,monedas.nombre AS nombremoneda,sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,"
                    + "clientes.direccion,clientes.ruc,cajas.nombre AS nombrecaja,"
                    + "usuarios.first_name as nombreusuario,cobranzas.aporte "
                    + "FROM cobranzas "
                    + "LEFT JOIN cobradores "
                    + "ON cobradores.codigo=cobranzas.cobrador "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=cobranzas.moneda "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cobranzas.sucursal "
                    + "LEFT JOIN cajas "
                    + "ON cajas.codigo=cobranzas.caja "
                    + "LEFT JOIN usuarios "
                    + " ON usuarios.employee_id=cobranzas.codusuario "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=cobranzas.cliente "
                    + "WHERE cobranzas.idpagos='" + id + "'"
                    + " ORDER BY cobranzas.idpagos ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();
                    caja caja = new caja();
                    moneda moneda = new moneda();
                    cobrador cobrador = new cobrador();
                    usuario usuario = new usuario();

                    cobro.setCodusuario(usuario);
                    cobro.setCliente(cliente);
                    cobro.setSucursal(sucursal);
                    cobro.setMoneda(moneda);
                    cobro.setCaja(caja);
                    cobro.setCobrador(cobrador);

                    cobro.setIdpagos(rs.getString("idpagos"));
                    cobro.setFecha(rs.getDate("fecha"));
                    cobro.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cobro.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cobro.getMoneda().setCodigo(rs.getInt("moneda"));
                    cobro.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cobro.getCliente().setCodigo(rs.getInt("cliente"));
                    cobro.getCliente().setNombre(rs.getString("nombrecliente"));
                    cobro.getCliente().setDireccion(rs.getString("direccion"));
                    cobro.getCliente().setRuc(rs.getString("ruc"));
                    cobro.getCaja().setCodigo(rs.getInt("caja"));
                    cobro.getCaja().setNombre(rs.getString("nombrecaja"));
                    cobro.getCobrador().setCodigo(rs.getInt("cobrador"));
                    cobro.getCobrador().setNombre(rs.getString("nombrecobrador"));
                    cobro.getCodusuario().setEmployee_id(rs.getInt("codusuario"));
                    cobro.getCodusuario().setFirst_name(rs.getString("nombreusuario"));
                    cobro.setNumero(rs.getBigDecimal("numero"));
                    cobro.setAsiento(rs.getBigDecimal("asiento"));
                    cobro.setCotizacionmoneda(rs.getBigDecimal("cotizacionmoneda"));
                    cobro.setEstado(rs.getString("estado"));
                    cobro.setObservacion(rs.getString("observacion"));
                    cobro.setTotalpago(rs.getBigDecimal("totalpago"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return cobro;
    }

    public ArrayList<cobranza> MostrarxFechaxCobrador(Date fechaini, Date fechafin, int ncob, int nmoneda1) throws SQLException {
        ArrayList<cobranza> lista = new ArrayList<cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT cobranzas.idpagos,cobranzas.numero,cobranzas.fecha,cobranzas.cliente,cobranzas.caja,cobranzas.codusuario,cobranzas.moneda,cobranzas.sucursal,"
                    + "detalle_cobranzas.nrofactura,detalle_cobranzas.pago,detalle_cobranzas.amortiza,detalle_cobranzas.minteres,detalle_cobranzas.gastos_cobranzas,"
                    + "detalle_cobranzas.mora,detalle_cobranzas.punitorio,detalle_cobranzas.numerocuota,detalle_cobranzas.cuota,"
                    + "detalle_cobranzas.comprobante,sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,"
                    + "monedas.etiqueta AS nombremoneda,cobradores.nombre AS nombrecobrador,cobranzas.cobrador,comprobantes.nombre as nombrecomprobante "
                    + " FROM cobranzas"
                    + " INNER JOIN detalle_cobranzas "
                    + " ON cobranzas.idpagos=detalle_cobranzas.iddetalle"
                    + " LEFT JOIN sucursales"
                    + " ON sucursales.codigo=cobranzas.sucursal"
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=cobranzas.cliente"
                    + " LEFT JOIN monedas"
                    + " ON monedas.codigo=cobranzas.moneda"
                    + " LEFT JOIN comprobantes"
                    + " ON comprobantes.codigo=detalle_cobranzas.comprobante"
                    + " LEFT JOIN cobradores "
                    + " ON cobradores.codigo=cobranzas.cobrador"
                    + " WHERE cobranzas.fecha between'" + fechaini + "' AND  '" + fechafin + "'"
                    + " AND cobranzas.cobrador= " + ncob
                    + " AND cobranzas.moneda= " + nmoneda1
                    + " ORDER BY cobranzas.cobrador,cobranzas.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

                System.out.println(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();
                    moneda moneda = new moneda();
                    cobranza cobro = new cobranza();
                    cobrador cob = new cobrador();

                    cobro.setCliente(cliente);
                    cobro.setSucursal(sucursal);
                    cobro.setMoneda(moneda);
                    cobro.setCobrador(cob);

                    cobro.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cobro.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cobro.getCobrador().setCodigo(rs.getInt("cobrador"));
                    cobro.getCobrador().setNombre(rs.getString("nombrecobrador"));

                    cobro.setIdpagos(rs.getString("idpagos"));
                    cobro.setNrofactura(rs.getBigDecimal("nrofactura"));
                    cobro.setFecha(rs.getDate("fecha"));
                    cobro.getMoneda().setCodigo(rs.getInt("moneda"));
                    cobro.getMoneda().setEtiqueta(rs.getString("nombremoneda"));
                    cobro.getCliente().setCodigo(rs.getInt("cliente"));
                    cobro.getCliente().setNombre(rs.getString("nombrecliente"));
                    cobro.setNumero(rs.getBigDecimal("numero"));
                    cobro.setPago(rs.getBigDecimal("pago"));
                    cobro.setAmortiza(rs.getBigDecimal("amortiza"));
                    cobro.setMinteres(rs.getBigDecimal("minteres"));
                    cobro.setGastos_cobranzas(rs.getBigDecimal("gastos_cobranzas"));
                    cobro.setMora(rs.getBigDecimal("mora"));
                    cobro.setPunitorio(rs.getBigDecimal("punitorio"));
                    cobro.setNumerocuota(rs.getInt("numerocuota"));
                    cobro.setCuota(rs.getInt("cuota"));
                    cobro.setNombrecomprobante(rs.getString("nombrecomprobante"));
                    lista.add(cobro);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public cobranza insertarCobroFais(cobranza ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        String id = "";
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cobranzas  (idpagos,numero,fecha,cliente,moneda,totalpago,"
                + "observacion,valores,cobrador,sucursal,cotizacionmoneda,codusuario,caja,turno,tipo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdpagos());
        ps.setBigDecimal(2, ocr.getNumero());
        ps.setDate(3, ocr.getFecha());
        ps.setInt(4, ocr.getCliente().getCodigo());
        ps.setInt(5, ocr.getMoneda().getCodigo());
        ps.setBigDecimal(6, ocr.getTotalpago());
        ps.setString(7, ocr.getObservacion());
        ps.setBigDecimal(8, ocr.getValores());
        ps.setInt(9, ocr.getCobrador().getCodigo());
        ps.setInt(10, ocr.getSucursal().getCodigo());
        ps.setBigDecimal(11, ocr.getCotizacionmoneda());
        ps.setInt(12, ocr.getCodusuario().getEmployee_id());
        ps.setInt(13, ocr.getCaja().getCodigo());
        ps.setInt(14, ocr.getTurno());
        ps.setInt(15, ocr.getTipo());

        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            id = ocr.getIdpagos();
            guardado = guardarDetalleCobro(id, detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public ArrayList<cobranza> CobranzasxComprobantes(Date fechaini, Date fechafin, int nComprobante) throws SQLException {
        ArrayList<cobranza> lista = new ArrayList<cobranza>();
        con = new Conexion();
        st = con.conectar();
        try {
            String Sql = "SELECT cobranzas.idpagos,cobranzas.formatofactura,cobranzas.fecha,"
                    + "cobranzas.numero,detalle_cobranzas.nrofactura,"
                    + "cobranzas.cliente,clientes.ruc AS ruccliente,clientes.nombre AS nombrecliente,"
                    + "detalle_cobranzas.pago,detalle_cobranzas.comprobante,"
                    + "comprobantes.nombre AS nombrecomprobante,detalle_cobranzas.numerocuota,"
                    + "detalle_cobranzas.cuota,detalle_cobranzas.emision,detalle_cobranzas.vence "
                    + " FROM cobranzas "
                    + " LEFT JOIN detalle_cobranzas "
                    + " ON detalle_cobranzas.iddetalle=cobranzas.idpagos "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=cobranzas.cliente "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=detalle_cobranzas.comprobante "
                    + " WHERE cobranzas.fecha BETWEEN'" + fechaini + "' AND '" + fechafin +"'"
                    + " AND IF(" + nComprobante + "<>0,comprobantes.codigo=" + nComprobante + ",TRUE)"
                    + " ORDER BY comprobantes.codigo,cobranzas.numero";
                    
            
            try (PreparedStatement ps = st.getConnection().prepareStatement(Sql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cobranza cob = new cobranza();
                    detalle_cobranza detcob = new detalle_cobranza();
                    cliente cli = new cliente();

                    cob.setDetalle_cobranza(detcob);
                    cob.setCliente(cli);
                    cob.setIdpagos(rs.getString("idpagos"));
                    cob.setFormatofactura(rs.getString("formatofactura"));
                    cob.setNumero(rs.getBigDecimal("numero"));
                    cob.setFecha(rs.getDate("fecha"));
                    cob.getCliente().setRuc(rs.getString("ruccliente"));
                    cob.getCliente().setCodigo(rs.getInt("cliente"));
                    cob.getCliente().setNombre(rs.getString("nombrecliente"));
                    cob.setComprobante(rs.getInt("comprobante"));
                    cob.setNombrecomprobante(rs.getString("nombrecomprobante"));
                    cob.setNumerocuota(rs.getInt("numerocuota"));
                    cob.setCuota(rs.getInt("cuota"));
                    cob.getDetalle_cobranza().setPago(rs.getDouble("pago"));
                    cob.getDetalle_cobranza().setVence(rs.getDate("vence"));
                    cob.getDetalle_cobranza().setEmision(rs.getDate("emision"));
                    cob.getDetalle_cobranza().setNrofactura(rs.getString("nrofactura"));
                    lista.add(cob);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cobranza> Resumen_cobranzas(Date fechaini, Date fechafin,int nMoneda) throws SQLException {
        ArrayList<cobranza> lista = new ArrayList<cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cobranzas.numero,cobranzas.fecha,sucursales.nombre AS nombresucursal,"
                    + "clientes.codigo AS cuenta, clientes.nombre AS nombrecliente,"
                    + "monedas.nombre AS nombremoneda,cobranzas.totalpago,cobranzas.estado "
                    + "FROM cobranzas "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cobranzas.sucursal "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cobranzas.cliente "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=cobranzas.moneda "
                    + "WHERE cobranzas.fecha BETWEEN ? AND ?  "
                    + "AND cobranzas.moneda=?  "
                    + "ORDER BY cobranzas.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();

                    cobranza vta = new cobranza();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);

                    vta.setNumero(rs.getBigDecimal("numero"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.getCliente().setCodigo(rs.getInt("cuenta"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));
                    vta.setTotalpago(rs.getBigDecimal("totalpago"));
                    vta.setEstado(rs.getString("estado"));
                    lista.add(vta);
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

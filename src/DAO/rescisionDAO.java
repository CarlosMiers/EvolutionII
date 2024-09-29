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
import Modelo.rescision;
import Modelo.cuenta_clientes;
import Modelo.detalle_rescision;
import Modelo.moneda;
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
public class rescisionDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<rescision> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<rescision> lista = new ArrayList<rescision>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {
            String sql = "SELECT rescisiones.idpagos,rescisiones.numero,rescisiones.fecha,rescisiones.cobrador,rescisiones.moneda,rescisiones.sucursal,rescisiones.cliente,"
                    + "rescisiones.cotizacionmoneda,rescisiones.totalpago,rescisiones.observacion,rescisiones.asiento,rescisiones.sucambio,rescisiones.descuentos,rescisiones.totalcobrado,"
                    + "rescisiones.nrofactura,rescisiones.cobrar_visita,rescisiones.codusuario,rescisiones.enviocobrador,rescisiones.caja,rescisiones.turno,rescisiones.estado,"
                    + "cobradores.nombre AS nombrecobrador,monedas.nombre AS nombremoneda,sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,"
                    + "cajas.nombre AS nombrecaja,usuarios.first_name as nombreusuario,rescisiones.aporte "
                    + "FROM rescisiones "
                    + "LEFT JOIN cobradores "
                    + "ON cobradores.codigo=rescisiones.cobrador "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=rescisiones.moneda "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=rescisiones.sucursal "
                    + "LEFT JOIN cajas "
                    + "ON cajas.codigo=rescisiones.caja "
                    + "LEFT JOIN usuarios "
                    + " ON usuarios.employee_id=rescisiones.codusuario "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=rescisiones.cliente "
                    + "WHERE rescisiones.fecha between '" + fechaini + "' AND '" + fechafin + "' "
                    + " ORDER BY rescisiones.numero ";

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
                    rescision cobro = new rescision();

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

    public rescision insertarCobranza(rescision ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        String id = "";
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO rescisiones  (idpagos,fecha,cliente,moneda,totalpago,observacion,valores,cobrador,sucursal,cotizacionmoneda,descuentos,enviocobrador,codusuario,caja,turno,aporte) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdpagos());
        ps.setDate(2, ocr.getFecha());
        ps.setInt(3, ocr.getCliente().getCodigo());
        ps.setInt(4, ocr.getMoneda().getCodigo());
        ps.setBigDecimal(5, ocr.getTotalpago());
        ps.setString(6, ocr.getObservacion());
        ps.setBigDecimal(7, ocr.getValores());
        ps.setInt(8, ocr.getCobrador().getCodigo());
        ps.setInt(9, ocr.getSucursal().getCodigo());
        ps.setBigDecimal(10, ocr.getCotizacionmoneda());
        ps.setBigDecimal(11, ocr.getDescuentos());
        ps.setInt(12, ocr.getEnviocobrador());
        ps.setInt(13, ocr.getCodusuario().getEmployee_id());
        ps.setInt(14, ocr.getCaja().getCodigo());
        ps.setInt(15, ocr.getTurno());
        ps.setBigDecimal(16, ocr.getAporte());
        int rowsUpdated = ps.executeUpdate();
         if (rowsUpdated > 0) {
            id = ocr.getIdpagos();
            guardado = guardarDetalleCobranza(id, detalle, con);
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

        ps = st.getConnection().prepareStatement("DELETE FROM rescisiones WHERE idpagos=?");
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


    public rescision buscarId(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        rescision cobranza = new rescision();

        try {

            String sql = "SELECT idpagos,numero,totalpago "
                    + "FROM rescisiones "
                    + "WHERE rescisiones.idpagos=? ";

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

                    String sql = "INSERT INTO detalle_rescisiones("
                            + "iddetalle,"
                            + "idfactura,"
                            + "nrofactura,"
                            + "emision,"
                            + "comprobante,"
                            + "pago,"
                            + "capital,"
                            + "diamora,"
                            + "mora,"
                            + "gastos_rescisiones,"
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
                        ps.setString(10, obj.get("gastos_rescisiones").getAsString());
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

    public ArrayList<rescision> MostrarxFechaxCaja(Date fechaini, Date fechafin, int ncaja1, int ncaja2, int nmoneda1, int nmoneda2) throws SQLException {
        ArrayList<rescision> lista = new ArrayList<rescision>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {
            String sql = "SELECT rescisiones.idpagos,rescisiones.numero,rescisiones.fecha,rescisiones.cliente,rescisiones.caja,rescisiones.moneda,rescisiones.sucursal,"
                    + "detalle_rescisiones.nrofactura,detalle_rescisiones.pago,detalle_rescisiones.amortiza,detalle_rescisiones.minteres,detalle_rescisiones.gastos_rescisiones,"
                    + "detalle_rescisiones.mora,detalle_rescisiones.punitorio,detalle_rescisiones.numerocuota,detalle_rescisiones.cuota,"
                    + "detalle_rescisiones.comprobante,sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,"
                    + "monedas.etiqueta AS nombremoneda,cajas.nombre AS nombrecaja,rescisiones.cobrador,comprobantes.nombre as nombrecomprobante "
                    + " FROM rescisiones"
                    + " INNER JOIN detalle_rescisiones "
                    + " ON rescisiones.idpagos=detalle_rescisiones.iddetalle"
                    + " LEFT JOIN sucursales"
                    + " ON sucursales.codigo=rescisiones.sucursal"
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=rescisiones.cliente"
                    + " LEFT JOIN monedas"
                    + " ON monedas.codigo=rescisiones.moneda"
                    + " LEFT JOIN comprobantes"
                    + " ON comprobantes.codigo=detalle_rescisiones.comprobante"
                    + " LEFT JOIN cajas"
                    + " ON cajas.codigo=rescisiones.caja"
                    + " WHERE rescisiones.fecha between ? AND ? "
                    + " AND IF(?<>0,rescisiones.caja=?,TRUE) "
                    + " AND IF(?<>0,rescisiones.moneda=?,TRUE) "
                    + " ORDER BY rescisiones.caja,rescisiones.fecha";

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

                    rescision cobro = new rescision();
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

    public ArrayList<rescision> MostrarxFechaxUsuario(Date fechaini, Date fechafin, int nusua1, int nmoneda1) throws SQLException {
        ArrayList<rescision> lista = new ArrayList<rescision>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT rescisiones.idpagos,rescisiones.numero,rescisiones.fecha,rescisiones.cliente,rescisiones.caja,rescisiones.codusuario,rescisiones.moneda,rescisiones.sucursal,"
                    + "detalle_rescisiones.nrofactura,detalle_rescisiones.pago,detalle_rescisiones.amortiza,detalle_rescisiones.minteres,detalle_rescisiones.gastos_rescisiones,"
                    + "detalle_rescisiones.mora,detalle_rescisiones.punitorio,detalle_rescisiones.numerocuota,detalle_rescisiones.cuota,"
                    + "detalle_rescisiones.comprobante,sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,"
                    + "monedas.etiqueta AS nombremoneda,usuarios.first_name AS nombreusuario,rescisiones.cobrador,comprobantes.nombre as nombrecomprobante "
                    + " FROM rescisiones"
                    + " INNER JOIN detalle_rescisiones "
                    + " ON rescisiones.idpagos=detalle_rescisiones.iddetalle"
                    + " LEFT JOIN sucursales"
                    + " ON sucursales.codigo=rescisiones.sucursal"
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=rescisiones.cliente"
                    + " LEFT JOIN monedas"
                    + " ON monedas.codigo=rescisiones.moneda"
                    + " LEFT JOIN comprobantes"
                    + " ON comprobantes.codigo=detalle_rescisiones.comprobante"
                    + " LEFT JOIN usuarios "
                    + " ON usuarios.employee_id=rescisiones.codusuario"
                    + " WHERE rescisiones.fecha between'" + fechaini + "' AND  '" + fechafin + "'"
                    + " AND rescisiones.codusuario= " + nusua1
                    + " AND rescisiones.moneda= " + nmoneda1
                    + " ORDER BY rescisiones.codusuario,rescisiones.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

                System.out.println(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();
                    moneda moneda = new moneda();
                    rescision cobro = new rescision();
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

    public ArrayList<rescision> MostrarxDiaCobro(Date fechaini, Date fechafin, int ntipo) throws SQLException {
        ArrayList<rescision> lista = new ArrayList<rescision>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {
            String sql = "SELECT rescisiones.idpagos,rescisiones.numero,rescisiones.fecha,rescisiones.cliente,rescisiones.caja,"
                    + "rescisiones.codusuario,rescisiones.moneda,rescisiones.sucursal,rescisiones.formatofactura,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,rescisiones.asiento,"
                    + "monedas.etiqueta AS nombremoneda,usuarios.first_name AS nombreusuario,rescisiones.cobrador, "
                    + "rescisiones.cotizacionmoneda,cajas.nombre as nombrecaja,cobradores.nombre as nombrecobrador, "
                    + "rescisiones.totalpago "
                    + " FROM rescisiones "
                    + " LEFT JOIN cajas "
                    + " ON cajas.codigo=rescisiones.sucursal "
                    + " LEFT JOIN sucursales"
                    + " ON sucursales.codigo=rescisiones.sucursal "
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=rescisiones.cliente "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=rescisiones.moneda "
                    + " LEFT JOIN usuarios "
                    + " ON usuarios.employee_id=rescisiones.codusuario"
                    + " LEFT JOIN cobradores "
                    + " ON cobradores.codigo=rescisiones.cobrador "
                    + " WHERE rescisiones.fecha between ? AND ? "
                    + " AND rescisiones.tipo=? "
                    + " ORDER BY rescisiones.moneda,rescisiones.fecha";

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
                    rescision cobro = new rescision();
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


    public rescision insertarCobro(rescision ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        String id = "";
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO rescisiones "
                + "(idpagos,fecha,cliente,"
                + "moneda,totalpago,observacion,"
                + "valores,cobrador,sucursal,"
                + "cotizacionmoneda,codusuario,"
                + "caja,turno,tipo) VALUES (?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdpagos());
        ps.setDate(2, ocr.getFecha());
        ps.setInt(3, ocr.getCliente().getCodigo());
        ps.setInt(4, ocr.getMoneda().getCodigo());
        ps.setBigDecimal(5, ocr.getTotalpago());
        ps.setString(6, ocr.getObservacion());
        ps.setBigDecimal(7, ocr.getValores());
        ps.setInt(8, ocr.getCobrador().getCodigo());
        ps.setInt(9, ocr.getSucursal().getCodigo());
        ps.setBigDecimal(10, ocr.getCotizacionmoneda());
        ps.setInt(11, ocr.getCodusuario().getEmployee_id());
        ps.setInt(12, ocr.getCaja().getCodigo());
        ps.setInt(13, ocr.getTurno());
        ps.setInt(14, ocr.getTipo());

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

    public rescision ActualizarCobroStandard(rescision ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        String id = "";
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE rescisiones set fecha=?,cliente=?,"
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

        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_rescisiones WHERE iddetalle=?");
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

                    String sql = "INSERT INTO detalle_rescisiones("
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

    public rescision BuscarCobro(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        rescision cobro = new rescision();

        try {
            String sql = "SELECT rescisiones.idpagos,rescisiones.numero,rescisiones.fecha,rescisiones.cobrador,rescisiones.moneda,rescisiones.sucursal,rescisiones.cliente,"
                    + "rescisiones.cotizacionmoneda,rescisiones.totalpago,rescisiones.observacion,rescisiones.asiento,rescisiones.sucambio,rescisiones.descuentos,rescisiones.totalcobrado,"
                    + "rescisiones.nrofactura,rescisiones.cobrar_visita,rescisiones.codusuario,rescisiones.enviocobrador,rescisiones.caja,rescisiones.turno,rescisiones.estado,"
                    + "cobradores.nombre AS nombrecobrador,monedas.nombre AS nombremoneda,sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,"
                    + "clientes.direccion,clientes.ruc,cajas.nombre AS nombrecaja,"
                    + "usuarios.first_name as nombreusuario,rescisiones.aporte "
                    + "FROM rescisiones "
                    + "LEFT JOIN cobradores "
                    + "ON cobradores.codigo=rescisiones.cobrador "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=rescisiones.moneda "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=rescisiones.sucursal "
                    + "LEFT JOIN cajas "
                    + "ON cajas.codigo=rescisiones.caja "
                    + "LEFT JOIN usuarios "
                    + " ON usuarios.employee_id=rescisiones.codusuario "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=rescisiones.cliente "
                    + "WHERE rescisiones.idpagos='" + id + "'"
                    + " ORDER BY rescisiones.idpagos ";

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

    
    
        public ArrayList<rescision> MostrarxFechaxCobrador(Date fechaini, Date fechafin, int ncob, int nmoneda1) throws SQLException {
        ArrayList<rescision> lista = new ArrayList<rescision>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT rescisiones.idpagos,rescisiones.numero,rescisiones.fecha,rescisiones.cliente,rescisiones.caja,rescisiones.codusuario,rescisiones.moneda,rescisiones.sucursal,"
                    + "detalle_rescisiones.nrofactura,detalle_rescisiones.pago,detalle_rescisiones.amortiza,detalle_rescisiones.minteres,detalle_rescisiones.gastos_rescisiones,"
                    + "detalle_rescisiones.mora,detalle_rescisiones.punitorio,detalle_rescisiones.numerocuota,detalle_rescisiones.cuota,"
                    + "detalle_rescisiones.comprobante,sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,"
                    + "monedas.etiqueta AS nombremoneda,cobradores.nombre AS nombrecobrador,rescisiones.cobrador,comprobantes.nombre as nombrecomprobante "
                    + " FROM rescisiones"
                    + " INNER JOIN detalle_rescisiones "
                    + " ON rescisiones.idpagos=detalle_rescisiones.iddetalle"
                    + " LEFT JOIN sucursales"
                    + " ON sucursales.codigo=rescisiones.sucursal"
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=rescisiones.cliente"
                    + " LEFT JOIN monedas"
                    + " ON monedas.codigo=rescisiones.moneda"
                    + " LEFT JOIN comprobantes"
                    + " ON comprobantes.codigo=detalle_rescisiones.comprobante"
                    + " LEFT JOIN cobradores "
                    + " ON cobradores.codigo=rescisiones.cobrador"
                    + " WHERE rescisiones.fecha between'" + fechaini + "' AND  '" + fechafin + "'"
                    + " AND rescisiones.cobrador= " + ncob
                    + " AND rescisiones.moneda= " + nmoneda1
                    + " ORDER BY rescisiones.cobrador,rescisiones.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

                System.out.println(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();
                    moneda moneda = new moneda();
                    rescision cobro = new rescision();
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

}

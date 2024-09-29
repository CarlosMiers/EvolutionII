/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.casa;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.contrato;
import Modelo.giraduria;
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
public class contratoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<contrato> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<contrato> lista = new ArrayList<contrato>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT idordencompra,contratos.sucursal,numero,contratos.giraduria,comercio,moneda,socio,contratos.garante,contratos.tipo,contratos.plazo,contratos.importe,"
                    + "monto_cuota,primer_vence,cierre,asiento,usuarionulo,usuarioalta,fechaalta,usuariomod,fecha,fechanulo,fechamod,"
                    + "giradurias.nombre AS nombregiraduria,casas.nombre AS nombrecomercio,clientes.nombre AS nombrecliente,clientes.direccion,"
                    + "clientes.telefono,sucursales.nombre as nombresucursal,comprobantes.nombre as nombrecomprobante "
                    + "FROM contratos "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=contratos.giraduria "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=contratos.tipo "
                    + "INNER JOIN casas "
                    + "ON casas.codigo=contratos.comercio "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=contratos.socio "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=contratos.sucursal "
                    + "WHERE contratos.fecha between ? AND ? "
                    + " ORDER BY contratos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    contrato cont = new contrato();
                    sucursal suc = new sucursal();
                    cliente cl = new cliente();
                    giraduria gi = new giraduria();
                    casa ca = new casa();
                    comprobante tipo = new comprobante();

                    cont.setSucursal(suc);
                    cont.setSocio(cl);
                    cont.setGiraduria(gi);
                    cont.setComercio(ca);
                    cont.setTipo(tipo);

                    cont.setIdordencompra(rs.getString("idordencompra"));
                    cont.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cont.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cont.setNumero(rs.getInt("numero"));
                    cont.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cont.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cont.setFecha(rs.getDate("fecha"));
                    cont.getComercio().setCodigo(rs.getInt("comercio"));
                    cont.getComercio().setNombre(rs.getString("nombrecomercio"));
                    cont.setMoneda(rs.getInt("moneda"));
                    cont.getSocio().setCodigo(rs.getInt("socio"));
                    cont.getSocio().setNombre(rs.getString("nombrecliente"));
                    cont.setGarante(rs.getInt("garante"));
                    cont.getTipo().setCodigo(rs.getInt("tipo"));
                    cont.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    cont.setPlazo(rs.getInt("plazo"));
                    cont.setImporte(rs.getBigDecimal("importe"));
                    cont.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    cont.setPrimer_vence(rs.getDate("primer_vence"));
                    cont.setCierre(rs.getInt("cierre"));
                    cont.setAsiento(rs.getInt("asiento"));
                    cont.setUsuarionulo(rs.getInt("usuarionulo"));
                    cont.setUsuarioalta(rs.getInt("usuarioalta"));
                    cont.setFechaalta(rs.getDate("fechaalta"));
                    cont.setUsuarionulo(rs.getInt("usuarionulo"));
                    cont.setFechamod(rs.getDate("fechamod"));
                    cont.setFechanulo(rs.getDate("fechanulo"));
                    lista.add(cont);
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

    public contrato insertarContrato(contrato cont, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO contratos (idordencompra,sucursal,fecha,giraduria,comercio,socio,tipo,plazo,importe,monto_cuota,primer_vence,usuarioalta,fechaalta,observacion) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, cont.getIdordencompra());
        ps.setInt(2, cont.getSucursal().getCodigo());
        ps.setDate(3, cont.getFecha());
        ps.setInt(4, cont.getGiraduria().getCodigo());
        ps.setInt(5, cont.getComercio().getCodigo());
        ps.setInt(6, cont.getSocio().getCodigo());
        ps.setInt(7, cont.getTipo().getCodigo());
        ps.setInt(8, cont.getPlazo());
        ps.setBigDecimal(9, cont.getImporte());
        ps.setBigDecimal(10, cont.getMonto_cuota());
        ps.setDate(11, cont.getPrimer_vence());
        ps.setInt(12, cont.getUsuarioalta());
        ps.setDate(13, cont.getFechaalta());
        ps.setString(14, cont.getObservacion());

        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarDetalle(id, detalle, con);
        }
        st.close();
        ps.close();
        conn.close();
        return cont;
    }

    public boolean borrarContrato(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM contratos WHERE numero=?");
        ps.setInt(1, id);
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

    public boolean borrarDetalleContrato(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuenta_clientes WHERE creferencia=?");
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

    public contrato buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        contrato cont = new contrato();

        try {
            String sql = "SELECT idordencompra,sucursal,numero,contratos.giraduria,comercio,moneda,socio,contratos.garante,contratos.tipo,contratos.plazo,contratos.importe,"
                    + "monto_cuota,primer_vence,cierre,asiento,usuarionulo,usuarioalta,fechaalta,usuariomod,fecha,fechanulo,fechamod,"
                    + "giradurias.nombre AS nombregiraduria,casas.nombre AS nombrecomercio,clientes.nombre AS nombrecliente,clientes.direccion,"
                    + "clientes.telefono,sucursales.nombre as nombresucursal,comprobantes.nombre as nombrecomprobante,contratos.observacion "
                    + "FROM contratos "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=contratos.giraduria "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=contratos.tipo "
                    + "INNER JOIN casas "
                    + "ON casas.codigo=contratos.comercio "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=contratos.socio "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=contratos.sucursal "
                    + "WHERE contratos.numero= ? "
                    + " ORDER BY contratos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal suc = new sucursal();
                    cliente cl = new cliente();
                    giraduria gi = new giraduria();
                    casa ca = new casa();
                    comprobante tipo = new comprobante();

                    cont.setSucursal(suc);
                    cont.setSocio(cl);
                    cont.setGiraduria(gi);
                    cont.setComercio(ca);
                    cont.setTipo(tipo);

                    cont.setIdordencompra(rs.getString("idordencompra"));
                    cont.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cont.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cont.setNumero(rs.getInt("numero"));
                    cont.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cont.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cont.setFecha(rs.getDate("fecha"));
                    cont.getComercio().setCodigo(rs.getInt("comercio"));
                    cont.getComercio().setNombre(rs.getString("nombrecomercio"));
                    cont.setMoneda(rs.getInt("moneda"));
                    cont.getSocio().setCodigo(rs.getInt("socio"));
                    cont.getSocio().setNombre(rs.getString("nombrecliente"));
                    cont.setGarante(rs.getInt("garante"));
                    cont.getTipo().setCodigo(rs.getInt("tipo"));
                    cont.getTipo().setNombre(rs.getString("tipo"));
                    cont.setPlazo(rs.getInt("plazo"));
                    cont.setImporte(rs.getBigDecimal("importe"));
                    cont.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    cont.setPrimer_vence(rs.getDate("primer_vence"));
                    cont.setCierre(rs.getInt("cierre"));
                    cont.setAsiento(rs.getInt("asiento"));
                    cont.setUsuarionulo(rs.getInt("usuarionulo"));
                    cont.setUsuarioalta(rs.getInt("usuarioalta"));
                    cont.setFechaalta(rs.getDate("fechaalta"));
                    cont.setUsuarionulo(rs.getInt("usuarionulo"));
                    cont.setFechamod(rs.getDate("fechamod"));
                    cont.setFechanulo(rs.getDate("fechanulo"));
                    cont.setObservacion(rs.getString("observacion"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return cont;
    }

    public contrato ActualizarContrato(contrato cont, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  contratos "
                + "SET sucursal=?,giraduria=?,"
                + "fecha=?,comercio=?,"
                + "socio=?,tipo=?,plazo=?,"
                + "importe=?,monto_cuota=?,"
                + "primer_vence=?,usuariomod=?,"
                + "fechamod=?,observacion=?"
                + " WHERE numero= " + cont.getNumero());
        ps.setInt(1, cont.getSucursal().getCodigo());
        ps.setInt(2, cont.getGiraduria().getCodigo());
        ps.setDate(3, cont.getFecha());
        ps.setInt(4, cont.getComercio().getCodigo());
        ps.setInt(5, cont.getSocio().getCodigo());
        ps.setInt(6, cont.getTipo().getCodigo());
        ps.setInt(7, cont.getPlazo());
        ps.setBigDecimal(8, cont.getImporte());
        ps.setBigDecimal(9, cont.getMonto_cuota());
        ps.setDate(10, cont.getPrimer_vence());
        ps.setInt(11, cont.getUsuariomod());
        ps.setDate(12, cont.getFechamod());
        ps.setString(13, cont.getObservacion());
        id = cont.getNumero();
        ps.executeUpdate();

        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarDetalle(id, detalle, con);
        }
        st.close();
        ps.close();
        conn.close();
        return cont;
    }

    public boolean guardarDetalle(int id, String detalle, Conexion conexion) throws SQLException {
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
                    String sql = "INSERT INTO cuenta_clientes("
                            + "iddocumento,"
                            + "creferencia,"
                            + "documento,"
                            + "fecha,"
                            + "vencimiento,"
                            + "cliente,"
                            + "sucursal,"
                            + "comprobante,"
                            + "importe,"
                            + "giraduria,"
                            + "numerocuota,"
                            + "cuota,"
                            + "saldo,"
                            + "comercial"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddocumento").getAsString());
                        ps.setString(2, obj.get("creferencia").getAsString());
                        ps.setInt(3, id);
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("vencimiento").getAsString());
                        ps.setInt(6, obj.get("cliente").getAsInt());
                        ps.setInt(7, obj.get("sucursal").getAsInt());
                        ps.setInt(8, obj.get("comprobante").getAsInt());
                        ps.setString(9, obj.get("importe").getAsString());
                        ps.setInt(10, obj.get("giraduria").getAsInt());
                        ps.setInt(11, obj.get("numerocuota").getAsInt());
                        ps.setInt(12, obj.get("cuota").getAsInt());
                        ps.setString(13, obj.get("saldo").getAsString());
                        ps.setInt(14, obj.get("comercial").getAsInt());
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

    public ArrayList<contrato> MostrarxFechaComercio(Date fechaini, Date fechafin, int ncasa1, int ncasa2) throws SQLException {
        ArrayList<contrato> lista = new ArrayList<contrato>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT idordencompra,contratos.sucursal,numero,contratos.giraduria,comercio,moneda,socio,contratos.garante,contratos.tipo,contratos.plazo,contratos.importe,"
                    + "monto_cuota,primer_vence,cierre,asiento,usuarionulo,usuarioalta,fechaalta,usuariomod,fecha,fechanulo,fechamod,"
                    + "giradurias.nombre AS nombregiraduria,casas.nombre AS nombrecomercio,clientes.nombre AS nombrecliente,clientes.direccion,"
                    + "clientes.telefono,comprobantes.nombre as nombrecomprobante,contratos.observacion "
                    + "FROM contratos "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=contratos.giraduria "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=contratos.tipo "
                    + "LEFT JOIN casas "
                    + "ON casas.codigo=contratos.comercio "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=contratos.socio "
                    + " WHERE contratos.fecha between ? AND ? "
                    + " AND IF(?<>0,contratos.comercio=?,TRUE) "
                    + " ORDER BY contratos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, ncasa1);
                ps.setInt(4, ncasa2);
                
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    contrato cont = new contrato();
                    cliente cl = new cliente();
                    giraduria gi = new giraduria();
                    casa ca = new casa();
                    comprobante tipo = new comprobante();

                    cont.setSocio(cl);
                    cont.setGiraduria(gi);
                    cont.setComercio(ca);
                    cont.setTipo(tipo);

                    cont.setIdordencompra(rs.getString("idordencompra"));
                    cont.setNumero(rs.getInt("numero"));
                    cont.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cont.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cont.setFecha(rs.getDate("fecha"));
                    cont.getComercio().setCodigo(rs.getInt("comercio"));
                    cont.getComercio().setNombre(rs.getString("nombrecomercio"));
                    cont.setMoneda(rs.getInt("moneda"));
                    cont.getSocio().setCodigo(rs.getInt("socio"));
                    cont.getSocio().setNombre(rs.getString("nombrecliente"));
                    cont.setGarante(rs.getInt("garante"));
                    cont.getTipo().setCodigo(rs.getInt("tipo"));
                    cont.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    cont.setPlazo(rs.getInt("plazo"));
                    cont.setImporte(rs.getBigDecimal("importe"));
                    cont.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    cont.setPrimer_vence(rs.getDate("primer_vence"));
                    cont.setCierre(rs.getInt("cierre"));
                    cont.setAsiento(rs.getInt("asiento"));
                    cont.setUsuarionulo(rs.getInt("usuarionulo"));
                    cont.setUsuarioalta(rs.getInt("usuarioalta"));
                    cont.setFechaalta(rs.getDate("fechaalta"));
                    cont.setUsuarionulo(rs.getInt("usuarionulo"));
                    cont.setFechamod(rs.getDate("fechamod"));
                    cont.setFechanulo(rs.getDate("fechanulo"));
                    cont.setObservacion(rs.getString("observacion"));
                    lista.add(cont);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Clases.Config;
import Modelo.casa;
import Modelo.cliente;
import Modelo.giraduria;
import Modelo.ordencompra;
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
public class ordencompraDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<ordencompra> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<ordencompra> lista = new ArrayList<ordencompra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idordencompra,sucursal,numero,fecha,ordenes_de_compras.giraduria,comercio,socio,ordenes_de_compras.garante,ordenes_de_compras.tipo,ordenes_de_compras.plazo,importe,"
                    + "monto_cuota,primer_vence,cierre,asiento,usuarioalta,usuarionulo,fechaalta,usuariomod,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,giradurias.nombre AS nombregiraduria,"
                    + "casas.nombre AS nombrecasas,ordenes_de_compras.estado "
                    + "FROM ordenes_de_compras "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=ordenes_de_compras.giraduria "
                    + "INNER JOIN casas "
                    + "ON casas.codigo=ordenes_de_compras.comercio "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=ordenes_de_compras.socio "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=ordenes_de_compras.sucursal "
                    + "WHERE ordenes_de_compras.fecha between ? AND ? "
                    + " ORDER BY ordenes_de_compras.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    casa casa = new casa();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();

                    ordencompra ocredito = new ordencompra();

                    ocredito.setGiraduria(giraduria);
                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);
                    ocredito.setSucursal(sucursal);

                    ocredito.setIdordencompra(rs.getString("idordencompra"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ocredito.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ocredito.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    ocredito.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecasas"));
                    ocredito.setGarante(rs.getInt("garante"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setAsiento(rs.getInt("asiento"));
                    ocredito.setPrimer_vence(rs.getDate("primer_vence"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setEstado(rs.getString("estado"));
                    lista.add(ocredito);
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

    public ArrayList<ordencompra> MostrarxFechaCondicional(Date fechaini, Date fechafin, int nGiraduria, int nGir, int nCliente, int nCli, int nCa, int nCasa) throws SQLException {
        ArrayList<ordencompra> lista = new ArrayList<ordencompra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idordencompra,sucursal,numero,fecha,ordenes_de_compras.giraduria,comercio,socio,ordenes_de_compras.garante,ordenes_de_compras.tipo,ordenes_de_compras.plazo,importe,"
                    + "monto_cuota,primer_vence,cierre,asiento,usuarioalta,usuarionulo,fechaalta,usuariomod,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,giradurias.nombre AS nombregiraduria,"
                    + "casas.nombre AS nombrecasas,ordenes_de_compras.estado "
                    + "FROM ordenes_de_compras "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=ordenes_de_compras.giraduria "
                    + "INNER JOIN casas "
                    + "ON casas.codigo=ordenes_de_compras.comercio "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=ordenes_de_compras.socio "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=ordenes_de_compras.sucursal "
                    + "WHERE ordenes_de_compras.fecha between ? AND ? "
                    + " AND IF(?<>0,ordenes_de_compras.giraduria=?,TRUE) "
                    + " AND IF(?<>0,ordenes_de_compras.socio=?,TRUE) "
                    + " AND IF(?<>0,ordenes_de_compras.comercio=?,TRUE) "
                    + " ORDER BY ordenes_de_compras.giraduria,ordenes_de_compras.numero ";
            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nGiraduria);
                ps.setInt(4, nGir);
                ps.setInt(5, nCliente);
                ps.setInt(6, nCli);
                ps.setInt(7, nCa);
                ps.setInt(8, nCasa);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    casa casa = new casa();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();

                    ordencompra ocredito = new ordencompra();

                    ocredito.setGiraduria(giraduria);
                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);
                    ocredito.setSucursal(sucursal);

                    ocredito.setIdordencompra(rs.getString("idordencompra"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ocredito.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ocredito.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    ocredito.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecasas"));
                    ocredito.setGarante(rs.getInt("garante"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setAsiento(rs.getInt("asiento"));
                    ocredito.setPrimer_vence(rs.getDate("primer_vence"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setEstado(rs.getString("estado"));
                    lista.add(ocredito);
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

    public ordencompra insertarOrdenCompra(String token, ordencompra ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO ordenes_de_compras (idordencompra,sucursal,fecha,giraduria,comercio,socio,tipo,plazo,importe,monto_cuota,primer_vence,asiento,usuarioalta,garante,fechaalta) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdordencompra());
        ps.setInt(2, ocr.getSucursal().getCodigo());
        ps.setDate(3, ocr.getFecha());
        ps.setInt(4, ocr.getGiraduria().getCodigo());
        ps.setInt(5, ocr.getCasas().getCodigo());
        ps.setInt(6, ocr.getCliente().getCodigo());
        ps.setInt(7, ocr.getTipo().getCodigo());
        ps.setInt(8, ocr.getPlazo());
        ps.setBigDecimal(9, ocr.getImporte());
        ps.setBigDecimal(10, ocr.getMonto_cuota());
        ps.setDate(11, ocr.getPrimer_vence());
        ps.setInt(12, ocr.getAsiento());
        ps.setInt(13, ocr.getUsuarioalta());
        ps.setInt(14, ocr.getGarante());
        ps.setDate(15, ocr.getFechaalta());
        if (Config.cToken == token) {
            ps.executeUpdate();
            ResultSet keyset = ps.getGeneratedKeys();
            if (keyset.next()) {
                id = keyset.getInt(1);
                guardado = guardarDetalle(id, detalle, con);
            }
        } else {
            System.out.println("USUARIO NO AUTORIZADO");
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;

    }

    public boolean borrarOrdenCompra(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM ordenes_de_compras WHERE numero=?");
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

    public ordencompra AnularOrdenCompra(ordencompra ocr, int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE ordenes_de_compras SET estado=?,usuarionulo=? WHERE numero=" + id);
        ps.setString(1, ocr.getEstado());
        ps.setInt(2, ocr.getUsuarionulo());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return ocr;
    }

    public boolean borrarDetalleOrdenCompra(String referencia) throws SQLException {
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

    public ordencompra buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        ordencompra ocredito = new ordencompra();

        try {

            String sql = "SELECT idordencompra,sucursal,numero,fecha,ordenes_de_compras.giraduria,comercio,socio,ordenes_de_compras.garante,ordenes_de_compras.tipo,ordenes_de_compras.plazo,importe,"
                    + "monto_cuota,primer_vence,cierre,asiento,usuarioalta,usuarionulo,fechaalta,usuariomod,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,clientes.direccion as direccioncliente,giradurias.nombre AS nombregiraduria,"
                    + "casas.nombre AS nombrecasas,ordenes_de_compras.estado,"
                    + "(SELECT nombre FROM clientes WHERE clientes.codigo=ordenes_de_compras.garante) nombregarante "
                    + " FROM ordenes_de_compras "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=ordenes_de_compras.giraduria "
                    + "INNER JOIN casas "
                    + "ON casas.codigo=ordenes_de_compras.comercio "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=ordenes_de_compras.socio "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=ordenes_de_compras.sucursal "
                    + "WHERE ordenes_de_compras.numero= ? "
                    + " ORDER BY ordenes_de_compras.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    giraduria giraduria = new giraduria();
                    casa casa = new casa();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();

                    ocredito.setGiraduria(giraduria);
                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);
                    ocredito.setSucursal(sucursal);

                    ocredito.setIdordencompra(rs.getString("idordencompra"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ocredito.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ocredito.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    ocredito.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecasas"));
                    ocredito.setGarante(rs.getInt("garante"));
                    ocredito.setNombregarante(rs.getString("nombregarante"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.getCliente().setDireccion(rs.getString("direccioncliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setAsiento(rs.getInt("asiento"));
                    ocredito.setPrimer_vence(rs.getDate("primer_vence"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setEstado("estado");
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return ocredito;
    }

    public ordencompra ActualizarOrdenCompra(String token, ordencompra ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  ordenes_de_compras SET sucursal=?,fecha=?,giraduria=?,comercio=?,socio=?,tipo=?,plazo=?,importe=?,monto_cuota=?,primer_vence=?,asiento=?,usuariomod=?,garante=?,fechamod=? WHERE numero= " + ocr.getNumero());
        ps.setInt(1, ocr.getSucursal().getCodigo());
        ps.setDate(2, ocr.getFecha());
        ps.setInt(3, ocr.getGiraduria().getCodigo());
        ps.setInt(4, ocr.getCasas().getCodigo());
        ps.setInt(5, ocr.getCliente().getCodigo());
        ps.setInt(6, ocr.getTipo().getCodigo());
        ps.setInt(7, ocr.getPlazo());
        ps.setBigDecimal(8, ocr.getImporte());
        ps.setBigDecimal(9, ocr.getMonto_cuota());
        ps.setDate(10, ocr.getPrimer_vence());
        ps.setInt(11, ocr.getAsiento());
        ps.setInt(12, ocr.getUsuariomod());
        ps.setInt(13, ocr.getGarante());
        ps.setDate(14, ocr.getFechaalta());
        id = ocr.getNumero();
        if (Config.cToken == token) {
            ps.executeUpdate();
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                guardado = guardarDetalle(id, detalle, con);
            }
        } else {
            System.out.println("USUARIO AUTORIZADO");
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
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
                            + "moneda,"
                            + "comprobante,"
                            + "importe,"
                            + "giraduria,"
                            + "numerocuota,"
                            + "cuota,"
                            + "saldo,"
                            + "comercial"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddocumento").getAsString());
                        ps.setString(2, obj.get("creferencia").getAsString());
                        ps.setInt(3, id);
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("vencimiento").getAsString());
                        ps.setInt(6, obj.get("cliente").getAsInt());
                        ps.setInt(7, obj.get("sucursal").getAsInt());
                        ps.setInt(8, obj.get("moneda").getAsInt());
                        ps.setInt(9, obj.get("comprobante").getAsInt());
                        ps.setString(10, obj.get("importe").getAsString());
                        ps.setInt(11, obj.get("giraduria").getAsInt());
                        ps.setInt(12, obj.get("numerocuota").getAsInt());
                        ps.setInt(13, obj.get("cuota").getAsInt());
                        ps.setString(14, obj.get("saldo").getAsString());
                        ps.setInt(15, obj.get("comercial").getAsInt());
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

    public ArrayList<ordencompra> Auditar(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<ordencompra> lista = new ArrayList<ordencompra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sqldatos = "CREATE TEMPORARY TABLE auditoria ("
                    + "SELECT DISTINCT creferencia,documento,fecha,cliente "
                    + " FROM cuenta_clientes "
                    + " WHERE cuenta_clientes.fecha "
                    + " BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND comprobante=4 "
                    + " ORDER BY creferencia)";

            PreparedStatement psdatos = conn.prepareStatement(sqldatos);
            psdatos.executeUpdate(sqldatos);

            String sql = "SELECT idordencompra,sucursal,numero,fecha,ordenes_de_compras.giraduria,comercio,socio,ordenes_de_compras.garante,ordenes_de_compras.tipo,ordenes_de_compras.plazo,importe,"
                    + "monto_cuota,primer_vence,cierre,asiento,usuarioalta,usuarionulo,fechaalta,usuariomod,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,giradurias.nombre AS nombregiraduria,"
                    + "casas.nombre AS nombrecasas,ordenes_de_compras.estado "
                    + "FROM ordenes_de_compras "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=ordenes_de_compras.giraduria "
                    + "LEFT JOIN casas "
                    + "ON casas.codigo=ordenes_de_compras.comercio "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=ordenes_de_compras.socio "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=ordenes_de_compras.sucursal "
                    + " WHERE ordenes_de_compras.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND NOT EXISTS(select creferencia "
                    + " FROM auditoria "
                    + " WHERE ordenes_de_compras.idordencompra=auditoria.creferencia) "
                    + " ORDER BY ordenes_de_compras.idordencompra ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    casa casa = new casa();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();

                    ordencompra ocredito = new ordencompra();

                    ocredito.setGiraduria(giraduria);
                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);
                    ocredito.setSucursal(sucursal);

                    ocredito.setIdordencompra(rs.getString("idordencompra"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ocredito.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ocredito.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    ocredito.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecasas"));
                    ocredito.setGarante(rs.getInt("garante"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setAsiento(rs.getInt("asiento"));
                    ocredito.setPrimer_vence(rs.getDate("primer_vence"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setEstado(rs.getString("estado"));
                    lista.add(ocredito);
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

    public ArrayList<ordencompra> PagareOCO(Date fechaini, Date fechafin, int nsocioini, int nsociofin) throws SQLException {
        ArrayList<ordencompra> lista = new ArrayList<ordencompra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT ordenes_de_compras.idordencompra,numero,cuenta_clientes.fecha,comercio,socio,ordenes_de_compras.importe,monto_cuota,ordenes_de_compras.tipo,ordenes_de_compras.plazo,primer_vence,clientes.nombre AS nombrecliente,"
                    + "casas.nombre AS nombrecomercio,cuenta_clientes.saldo,cuota,cuenta_clientes.vencimiento,ordenes_de_compras.usuarioalta,ordenes_de_compras.estado "
                    + " FROM ordenes_de_compras "
                    + " INNER JOIN clientes "
                    + " ON ordenes_de_compras.socio=clientes.codigo "
                    + " INNER JOIN casas "
                    + " ON ordenes_de_compras.comercio=casas.codigo "
                    + " INNER JOIN cuenta_clientes "
                    + " ON ordenes_de_compras.idordencompra=cuenta_clientes.creferencia "
                    + " WHERE cuenta_clientes.vencimiento BETWEEN ? AND ? "
                    + " AND IF(?<>0,ordenes_de_compras.socio=?,TRUE) "
                    + " AND ordenes_de_compras.plazo=cuenta_clientes.cuota "
                    + " ORDER BY cuenta_clientes.cliente,cuenta_clientes.vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nsocioini);
                ps.setInt(4, nsociofin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    casa casa = new casa();
                    cliente cliente = new cliente();

                    ordencompra ocredito = new ordencompra();

                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);

                    ocredito.setIdordencompra(rs.getString("idordencompra"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.setPrimer_vence(rs.getDate("vencimiento"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecomercio"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setEstado(rs.getString("estado"));
                    ocredito.setSaldo(rs.getBigDecimal("saldo"));
                    lista.add(ocredito);
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

    public ArrayList<ordencompra> MostrarxFechaProceso(Date fechaini, Date fechafin, int nGiraduria, int nGir, int nCliente, int nCli, int nCa, int nCasa) throws SQLException {
        ArrayList<ordencompra> lista = new ArrayList<ordencompra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idordencompra,sucursal,numero,fecha,ordenes_de_compras.giraduria,comercio,socio,ordenes_de_compras.garante,ordenes_de_compras.tipo,ordenes_de_compras.plazo,importe,"
                    + "monto_cuota,primer_vence,cierre,asiento,usuarioalta,usuarionulo,fechaalta,usuariomod,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,giradurias.nombre AS nombregiraduria,"
                    + "casas.nombre AS nombrecasas,ordenes_de_compras.estado "
                    + "FROM ordenes_de_compras "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=ordenes_de_compras.giraduria "
                    + "INNER JOIN casas "
                    + "ON casas.codigo=ordenes_de_compras.comercio "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=ordenes_de_compras.socio "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=ordenes_de_compras.sucursal "
                    + " WHERE SUBSTRING(ordenes_de_compras.fechaalta,1,10) BETWEEN ? AND ? "
                    + " AND IF(?<>0,ordenes_de_compras.giraduria=?,TRUE) "
                    + " AND IF(?<>0,ordenes_de_compras.socio=?,TRUE) "
                    + " AND IF(?<>0,ordenes_de_compras.comercio=?,TRUE) "
                    + " ORDER BY ordenes_de_compras.giraduria,ordenes_de_compras.numero ";
            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nGiraduria);
                ps.setInt(4, nGir);
                ps.setInt(5, nCliente);
                ps.setInt(6, nCli);
                ps.setInt(7, nCa);
                ps.setInt(8, nCasa);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    casa casa = new casa();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();

                    ordencompra ocredito = new ordencompra();

                    ocredito.setGiraduria(giraduria);
                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);
                    ocredito.setSucursal(sucursal);

                    ocredito.setIdordencompra(rs.getString("idordencompra"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ocredito.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ocredito.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    ocredito.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecasas"));
                    ocredito.setGarante(rs.getInt("garante"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setAsiento(rs.getInt("asiento"));
                    ocredito.setPrimer_vence(rs.getDate("primer_vence"));
                    ocredito.setFechaalta(rs.getDate("fechaalta"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setEstado(rs.getString("estado"));
                    lista.add(ocredito);
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

    public ArrayList<ordencompra> MostrarxFechaActivos(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<ordencompra> lista = new ArrayList<ordencompra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idordencompra,sucursal,numero,fecha,ordenes_de_compras.giraduria,comercio,socio,ordenes_de_compras.garante,ordenes_de_compras.tipo,ordenes_de_compras.plazo,importe,"
                    + "monto_cuota,primer_vence,cierre,asiento,usuarioalta,usuarionulo,fechaalta,usuariomod,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,giradurias.nombre AS nombregiraduria,"
                    + "casas.nombre AS nombrecasas,ordenes_de_compras.estado "
                    + "FROM ordenes_de_compras "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=ordenes_de_compras.giraduria "
                    + "INNER JOIN casas "
                    + "ON casas.codigo=ordenes_de_compras.comercio "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=ordenes_de_compras.socio "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=ordenes_de_compras.sucursal "
                    + "WHERE ordenes_de_compras.fecha between ? AND ? "
                    + " AND ordenes_de_compras.pagado=0 "
                    + " ORDER BY ordenes_de_compras.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    casa casa = new casa();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();

                    ordencompra ocredito = new ordencompra();

                    ocredito.setGiraduria(giraduria);
                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);
                    ocredito.setSucursal(sucursal);

                    ocredito.setIdordencompra(rs.getString("idordencompra"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ocredito.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ocredito.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    ocredito.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecasas"));
                    ocredito.setGarante(rs.getInt("garante"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setAsiento(rs.getInt("asiento"));
                    ocredito.setPrimer_vence(rs.getDate("primer_vence"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setEstado(rs.getString("estado"));
                    lista.add(ocredito);
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


    public boolean ActualizarPagoAnticipo(int num) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE ordenes_de_compras SET pagado=1 WHERE numero="+num);
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

}

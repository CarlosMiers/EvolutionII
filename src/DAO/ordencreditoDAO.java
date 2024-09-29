/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.banco;
import Modelo.casa;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.giraduria;
import Modelo.ordencredito;
import Modelo.sucursal;
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
public class ordencreditoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<ordencredito> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<ordencredito> lista = new ArrayList<ordencredito>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idordencredito,sucursal,numero,nroorden,fecha,ordenes_de_credito.giraduria,comercio,socio,ordenes_de_credito.garante,ordenes_de_credito.tipo,ordenes_de_credito.plazo,tasa,importe,"
                    + "monto_cuota,descuentos,primer_vence,cierre,asiento,usuarioalta,usuarionulo,fechaalta,usuariomod,interesordinario,montoentregar,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,giradurias.nombre AS nombregiraduria,"
                    + "casas.nombre AS nombrecasas,aprobado,cargobanco,nrocheque,emisioncheque,ordenes_de_credito.estado "
                    + "FROM ordenes_de_credito "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=ordenes_de_credito.giraduria "
                    + "LEFT JOIN casas "
                    + "ON casas.codigo=ordenes_de_credito.comercio "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=ordenes_de_credito.socio "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=ordenes_de_credito.sucursal "
                    + "WHERE ordenes_de_credito.fecha between ? AND ? "
                    + " ORDER BY ordenes_de_credito.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    casa casa = new casa();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();

                    ordencredito ocredito = new ordencredito();

                    ocredito.setGiraduria(giraduria);
                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);
                    ocredito.setSucursal(sucursal);

                    ocredito.setIdordencredito(rs.getString("idordencredito"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ocredito.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ocredito.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    ocredito.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.setNrooden(rs.getString("nroorden"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecasas"));
                    ocredito.setGarante(rs.getInt("garante"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setTasa(rs.getBigDecimal("tasa"));
                    ocredito.setDescuentos(rs.getBigDecimal("descuentos"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setAsiento(rs.getInt("asiento"));
                    ocredito.setPrimer_vence(rs.getDate("primer_vence"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setInteresordinario(rs.getBigDecimal("interesordinario"));
                    ocredito.setMontoentregar(rs.getBigDecimal("montoentregar"));
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

    public ArrayList<ordencredito> MostrarxGiraduria(int ngiraduria, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<ordencredito> lista = new ArrayList<ordencredito>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idordencredito,sucursal,numero,nroorden,fecha,ordenes_de_credito.giraduria,comercio,socio,ordenes_de_credito.garante,ordenes_de_credito.tipo,ordenes_de_credito.plazo,tasa,importe,"
                    + "monto_cuota,descuentos,primer_vence,cierre,asiento,usuarioalta,usuarionulo,fechaalta,usuariomod,interesordinario,montoentregar,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,giradurias.nombre AS nombregiraduria,"
                    + "casas.nombre AS nombrecasas,aprobado,cargobanco,nrocheque,emisioncheque "
                    + "FROM ordenes_de_credito "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=ordenes_de_credito.giraduria "
                    + "LEFT JOIN casas "
                    + "ON casas.codigo=ordenes_de_credito.comercio "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=ordenes_de_credito.socio "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=ordenes_de_credito.sucursal "
                    + "WHERE ordenes_de_credito.giraduria=? AND ordenes_de_credito.fecha between ? AND ? "
                    + " ORDER BY ordenes_de_credito.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, ngiraduria);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    giraduria giraduria = new giraduria();
                    casa casa = new casa();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();

                    ordencredito ocredito = new ordencredito();

                    ocredito.setGiraduria(giraduria);
                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);
                    ocredito.setSucursal(sucursal);

                    ocredito.setIdordencredito(rs.getString("idordencredito"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ocredito.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ocredito.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    ocredito.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.setNrooden(rs.getString("nroorden"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecasas"));
                    ocredito.setGarante(rs.getInt("garante"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setTasa(rs.getBigDecimal("tasa"));
                    ocredito.setDescuentos(rs.getBigDecimal("descuentos"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setAsiento(rs.getInt("asiento"));
                    ocredito.setPrimer_vence(rs.getDate("primer_vence"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setInteresordinario(rs.getBigDecimal("interesordinario"));
                    ocredito.setMontoentregar(rs.getBigDecimal("montoentregar"));
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

    public ArrayList<ordencredito> MostrarxFechaCondicional(Date fechaini, Date fechafin, int nGiraduria, int nGir, int nCliente, int nCli, int nCa, int nCasa) throws SQLException {
        ArrayList<ordencredito> lista = new ArrayList<ordencredito>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idordencredito,sucursal,numero,nroorden,fecha,ordenes_de_credito.giraduria,comercio,socio,ordenes_de_credito.garante,ordenes_de_credito.tipo,ordenes_de_credito.plazo,tasa,importe,"
                    + "monto_cuota,descuentos,primer_vence,cierre,asiento,usuarioalta,usuarionulo,fechaalta,usuariomod,interesordinario,montoentregar,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,giradurias.nombre AS nombregiraduria,"
                    + "casas.nombre AS nombrecasas,aprobado,cargobanco,nrocheque,emisioncheque "
                    + "FROM ordenes_de_credito "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=ordenes_de_credito.giraduria "
                    + "LEFT JOIN casas "
                    + "ON casas.codigo=ordenes_de_credito.comercio "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=ordenes_de_credito.socio "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=ordenes_de_credito.sucursal "
                    + " WHERE ordenes_de_credito.fecha between ? AND ? "
                    + " AND IF(?<>0,ordenes_de_credito.giraduria=?,TRUE) "
                    + " AND IF(?<>0,ordenes_de_credito.socio=?,TRUE) "
                    + " AND IF(?<>0,ordenes_de_credito.comercio=?,TRUE) "
                    + " ORDER BY ordenes_de_credito.giraduria,ordenes_de_credito.numero ";

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
                    comprobante com = new comprobante();
                    ordencredito ocredito = new ordencredito();

                    ocredito.setGiraduria(giraduria);
                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);
                    ocredito.setSucursal(sucursal);
                    ocredito.setTipo(com);

                    ocredito.setIdordencredito(rs.getString("idordencredito"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ocredito.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ocredito.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    ocredito.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    ocredito.getTipo().setNombre(rs.getString("tipo"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.setNrooden(rs.getString("nroorden"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecasas"));
                    ocredito.setGarante(rs.getInt("garante"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setTasa(rs.getBigDecimal("tasa"));
                    ocredito.setDescuentos(rs.getBigDecimal("descuentos"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setAsiento(rs.getInt("asiento"));
                    ocredito.setPrimer_vence(rs.getDate("primer_vence"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setInteresordinario(rs.getBigDecimal("interesordinario"));
                    ocredito.setMontoentregar(rs.getBigDecimal("montoentregar"));
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

    public ordencredito insertarOrdenCredito(ordencredito ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO ordenes_de_credito (idordencredito,sucursal,nroorden,fecha,giraduria,comercio,socio,tipo,plazo,tasa,importe,monto_cuota,descuentos,primer_vence,asiento,usuarioalta,interesordinario,montoentregar,garante,retenciones,capital,ivainteres,gastos) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdordencredito());
        ps.setInt(2, ocr.getSucursal().getCodigo());
        ps.setString(3, ocr.getNrooden());
        ps.setDate(4, ocr.getFecha());
        ps.setInt(5, ocr.getGiraduria().getCodigo());
        ps.setInt(6, ocr.getCasas().getCodigo());
        ps.setInt(7, ocr.getCliente().getCodigo());
        ps.setInt(8, ocr.getTipo().getCodigo());
        ps.setInt(9, ocr.getPlazo());
        ps.setBigDecimal(10, ocr.getTasa());
        ps.setBigDecimal(11, ocr.getImporte());
        ps.setBigDecimal(12, ocr.getMonto_cuota());
        ps.setBigDecimal(13, ocr.getDescuentos());
        ps.setDate(14, ocr.getPrimer_vence());
        ps.setInt(15, ocr.getAsiento());
        ps.setInt(16, ocr.getUsuarioalta());
        ps.setBigDecimal(17, ocr.getInteresordinario());
        ps.setBigDecimal(18, ocr.getMontoentregar());
        ps.setInt(19, ocr.getGarante());
        ps.setBigDecimal(20, ocr.getRetenciones());
        ps.setDouble(21, ocr.getCapital());
        ps.setDouble(22, ocr.getIvainteres());
        ps.setDouble(23, ocr.getGastos());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarDetalle(id, detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public boolean borrarOrdenCredito(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM ordenes_de_credito WHERE numero=?");
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

    public ordencredito AnularOrdenCredito(ordencredito ocr, int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE ordenes_de_credito SET estado=?,usuarionulo=? WHERE numero=" + id);
        ps.setString(1, ocr.getEstado());
        ps.setInt(2, ocr.getUsuarionulo());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return ocr;
    }

    public boolean borrarDetalleOrdenCredito(String referencia) throws SQLException {
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

    public ordencredito buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        ordencredito ocredito = new ordencredito();

        try {

            String sql = "SELECT idordencredito,sucursal,numero,nroorden,fecha,ordenes_de_credito.giraduria,comercio,socio,ordenes_de_credito.garante,ordenes_de_credito.tipo,ordenes_de_credito.plazo,ordenes_de_credito.tasa,ordenes_de_credito.importe,"
                    + "monto_cuota,descuentos,primer_vence,cierre,asiento,usuarioalta,usuarionulo,fechaalta,usuariomod,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,giradurias.nombre AS nombregiraduria,clientes.direccion as direccioncliente,ordenes_de_credito.estado,"
                    + "casas.nombre AS nombrecasas,aprobado,cargobanco,nrocheque,emisioncheque,interesordinario,montoentregar,retenciones, "
                    + "bancos.nombre as nombrebanco,capital,ivainteres,gastos, "
                    + "(SELECT nombre FROM clientes WHERE clientes.codigo=ordenes_de_credito.garante) nombregarante "
                    + "FROM ordenes_de_credito "
                    + "INNER JOIN giradurias "
                    + "ON giradurias.codigo=ordenes_de_credito.giraduria "
                    + "INNER JOIN casas "
                    + "ON casas.codigo=ordenes_de_credito.comercio "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=ordenes_de_credito.socio "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=ordenes_de_credito.sucursal "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=ordenes_de_credito.cargobanco "
                    + "WHERE ordenes_de_credito.numero= ? "
                    + " ORDER BY ordenes_de_credito.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    giraduria giraduria = new giraduria();
                    casa casa = new casa();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    banco banco = new banco();

                    ocredito.setGiraduria(giraduria);
                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);
                    ocredito.setSucursal(sucursal);
                    ocredito.setCargobanco(banco);

                    ocredito.setIdordencredito(rs.getString("idordencredito"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ocredito.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ocredito.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    ocredito.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.setNrooden(rs.getString("nroorden"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecasas"));
                    ocredito.setGarante(rs.getInt("garante"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.getCliente().setDireccion(rs.getString("direccioncliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setTasa(rs.getBigDecimal("tasa"));
                    ocredito.setDescuentos(rs.getBigDecimal("descuentos"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setAsiento(rs.getInt("asiento"));
                    ocredito.setPrimer_vence(rs.getDate("primer_vence"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setInteresordinario(rs.getBigDecimal("interesordinario"));
                    ocredito.setMontoentregar(rs.getBigDecimal("montoentregar"));
                    ocredito.getCargobanco().setCodigo(rs.getInt("cargobanco"));
                    ocredito.getCargobanco().setNombre(rs.getString("nombrebanco"));
                    ocredito.setNrocheque(rs.getString("nrocheque"));
                    ocredito.setEmisioncheque(rs.getDate("emisioncheque"));
                    ocredito.setNombregarante(rs.getString("nombregarante"));
                    ocredito.setRetenciones(rs.getBigDecimal("retenciones"));
                    ocredito.setEstado(rs.getString("estado"));
                    ocredito.setCapital(rs.getDouble("capital"));
                    ocredito.setIvainteres(rs.getDouble("ivainteres"));
                    ocredito.setGastos(rs.getDouble("gastos"));
                    if (ocredito.getDescuentos() != null) {
                        ocredito.setNetoentregado(ocredito.getMontoentregar().subtract(ocredito.getDescuentos()));
                    } else {
                        ocredito.setNetoentregado(ocredito.getMontoentregar());
                    }
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

    public ordencredito ActualizarOrdenCredito(ordencredito ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  ordenes_de_credito SET sucursal=?,nroorden=?,fecha=?,"
                + "giraduria=?,comercio=?,socio=?,tipo=?,plazo=?,tasa=?,importe=?,monto_cuota=?,descuentos=?,"
                + "primer_vence=?,asiento=?,usuarioalta=?,interesordinario=?,"
                + "montoentregar=?,garante=?,retenciones=?,capital=?,ivainteres=?,gastos=? WHERE numero= " + ocr.getNumero());
        ps.setInt(1, ocr.getSucursal().getCodigo());
        ps.setString(2, ocr.getNrooden());
        ps.setDate(3, ocr.getFecha());
        ps.setInt(4, ocr.getGiraduria().getCodigo());
        ps.setInt(5, ocr.getCasas().getCodigo());
        ps.setInt(6, ocr.getCliente().getCodigo());
        ps.setInt(7, ocr.getTipo().getCodigo());
        ps.setInt(8, ocr.getPlazo());
        ps.setBigDecimal(9, ocr.getTasa());
        ps.setBigDecimal(10, ocr.getImporte());
        ps.setBigDecimal(11, ocr.getMonto_cuota());
        ps.setBigDecimal(12, ocr.getDescuentos());
        ps.setDate(13, ocr.getPrimer_vence());
        ps.setInt(14, ocr.getAsiento());
        ps.setInt(15, ocr.getUsuarioalta());
        ps.setBigDecimal(16, ocr.getInteresordinario());
        ps.setBigDecimal(17, ocr.getMontoentregar());
        ps.setInt(18, ocr.getGarante());
        ps.setBigDecimal(19, ocr.getRetenciones());
        ps.setDouble(20, ocr.getCapital());
        ps.setDouble(21, ocr.getIvainteres());
        ps.setDouble(22, ocr.getGastos());
        id = ocr.getNumero();
        ps.executeUpdate();

        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarDetalle(id, detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public ordencredito ActualizarDesembolso(ordencredito ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  ordenes_de_credito SET cargobanco=?,nrocheque=?,emisioncheque=?,importecheque=? WHERE numero= " + ocr.getNumero());
        ps.setInt(1, ocr.getCargobanco().getCodigo());
        ps.setString(2, ocr.getNrocheque());
        ps.setDate(3, ocr.getEmisioncheque());
        ps.setBigDecimal(4, ocr.getImportecheque());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
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
                            + "comercial,"
                            + "amortiza,"
                            + "minteres,"
                            + "tasaoperativa,"
                            + "autorizacion"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
                        ps.setString(16, obj.get("amortiza").getAsString());
                        ps.setString(17, obj.get("minteres").getAsString());
                        ps.setString(18, obj.get("tasaoperativa").getAsString());
                        ps.setString(19, obj.get("autorizacion").getAsString());
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

    public ArrayList<ordencredito> Auditar(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<ordencredito> lista = new ArrayList<ordencredito>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sqldatos = "CREATE TEMPORARY TABLE auditoria ("
                    + "SELECT DISTINCT creferencia,documento,fecha,cliente "
                    + " FROM cuenta_clientes "
                    + " WHERE cuenta_clientes.fecha "
                    + " BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND comprobante=5 "
                    + " ORDER BY creferencia)";

            PreparedStatement psdatos = conn.prepareStatement(sqldatos);
            psdatos.executeUpdate(sqldatos);

            String sql = "SELECT idordencredito,sucursal,numero,nroorden,fecha,ordenes_de_credito.giraduria,comercio,socio,ordenes_de_credito.garante,ordenes_de_credito.tipo,ordenes_de_credito.plazo,tasa,importe,"
                    + "monto_cuota,descuentos,primer_vence,cierre,asiento,usuarioalta,usuarionulo,fechaalta,usuariomod,interesordinario,montoentregar,"
                    + "sucursales.nombre AS nombresucursal,clientes.nombre AS nombrecliente,giradurias.nombre AS nombregiraduria,"
                    + "casas.nombre AS nombrecasas,aprobado,cargobanco,nrocheque,emisioncheque,ordenes_de_credito.estado "
                    + "FROM ordenes_de_credito "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=ordenes_de_credito.giraduria "
                    + "LEFT JOIN casas "
                    + "ON casas.codigo=ordenes_de_credito.comercio "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=ordenes_de_credito.socio "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=ordenes_de_credito.sucursal "
                    + "WHERE ordenes_de_credito.fecha  BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND NOT EXISTS(SELECT creferencia "
                    + " FROM auditoria "
                    + "  WHERE ordenes_de_credito.idordencredito=auditoria.creferencia ) "
                    + " ORDER BY ordenes_de_credito.idordencredito";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    casa casa = new casa();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();

                    ordencredito ocredito = new ordencredito();

                    ocredito.setGiraduria(giraduria);
                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);
                    ocredito.setSucursal(sucursal);

                    ocredito.setIdordencredito(rs.getString("idordencredito"));
                    ocredito.setFecha(rs.getDate("fecha"));
                    ocredito.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ocredito.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ocredito.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    ocredito.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    ocredito.setNumero(rs.getInt("numero"));
                    ocredito.setNrooden(rs.getString("nroorden"));
                    ocredito.getCasas().setCodigo(rs.getInt("comercio"));
                    ocredito.getCasas().setNombre(rs.getString("nombrecasas"));
                    ocredito.setGarante(rs.getInt("garante"));
                    ocredito.getCliente().setCodigo(rs.getInt("socio"));
                    ocredito.getCliente().setNombre(rs.getString("nombrecliente"));
                    ocredito.setImporte(rs.getBigDecimal("importe"));
                    ocredito.setPlazo(rs.getInt("plazo"));
                    ocredito.setTasa(rs.getBigDecimal("tasa"));
                    ocredito.setDescuentos(rs.getBigDecimal("descuentos"));
                    ocredito.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    ocredito.setAsiento(rs.getInt("asiento"));
                    ocredito.setPrimer_vence(rs.getDate("primer_vence"));
                    ocredito.setUsuarioalta(rs.getInt("usuarioalta"));
                    ocredito.setInteresordinario(rs.getBigDecimal("interesordinario"));
                    ocredito.setMontoentregar(rs.getBigDecimal("montoentregar"));
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

    public ArrayList<ordencredito> PagareOCR(Date fechaini, Date fechafin, int nsocioini, int nsociofin) throws SQLException {
        ArrayList<ordencredito> lista = new ArrayList<ordencredito>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT ordenes_de_credito.idordencredito,numero,cuenta_clientes.fecha,comercio,socio,ordenes_de_credito.importe,monto_cuota,ordenes_de_credito.tipo,ordenes_de_credito.plazo,primer_vence,clientes.nombre AS nombrecliente,"
                    + "casas.nombre AS nombrecomercio,cuenta_clientes.saldo,cuota,cuenta_clientes.vencimiento,ordenes_de_credito.usuarioalta,ordenes_de_credito.estado "
                    + " FROM ordenes_de_credito "
                    + " INNER JOIN clientes "
                    + " ON ordenes_de_credito.socio=clientes.codigo "
                    + " INNER JOIN casas "
                    + " ON ordenes_de_credito.comercio=casas.codigo "
                    + " INNER JOIN cuenta_clientes "
                    + " ON ordenes_de_credito.idordencredito=cuenta_clientes.creferencia "
                    + " WHERE cuenta_clientes.vencimiento BETWEEN ? AND ? "
                    + " AND IF(?<>0,ordenes_de_credito.socio=?,TRUE) "
                    + " AND ordenes_de_credito.plazo=cuenta_clientes.cuota "
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

                    ordencredito ocredito = new ordencredito();

                    ocredito.setCasas(casa);
                    ocredito.setCliente(cliente);

                    ocredito.setIdordencredito(rs.getString("idordencredito"));
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

    public ordencredito insertarOrdenCreditoFerremax(ordencredito ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO ordenes_de_credito (idordencredito,sucursal,nroorden,fecha,giraduria,comercio,socio,tipo,plazo,tasa,importe,monto_cuota,descuentos,primer_vence,asiento,usuarioalta,interesordinario,montoentregar,garante,retenciones,capital,ivainteres,gastos) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdordencredito());
        ps.setInt(2, ocr.getSucursal().getCodigo());
        ps.setString(3, ocr.getNrooden());
        ps.setDate(4, ocr.getFecha());
        ps.setInt(5, ocr.getGiraduria().getCodigo());
        ps.setInt(6, ocr.getCasas().getCodigo());
        ps.setInt(7, ocr.getCliente().getCodigo());
        ps.setInt(8, ocr.getTipo().getCodigo());
        ps.setInt(9, ocr.getPlazo());
        ps.setBigDecimal(10, ocr.getTasa());
        ps.setBigDecimal(11, ocr.getImporte());
        ps.setBigDecimal(12, ocr.getMonto_cuota());
        ps.setBigDecimal(13, ocr.getDescuentos());
        ps.setDate(14, ocr.getPrimer_vence());
        ps.setInt(15, ocr.getAsiento());
        ps.setInt(16, ocr.getUsuarioalta());
        ps.setBigDecimal(17, ocr.getInteresordinario());
        ps.setBigDecimal(18, ocr.getMontoentregar());
        ps.setInt(19, ocr.getGarante());
        ps.setBigDecimal(20, ocr.getRetenciones());
        ps.setDouble(21, ocr.getCapital());
        ps.setDouble(22, ocr.getIvainteres());
        ps.setDouble(23, ocr.getGastos());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarDetalleFerremax(id, detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public ordencredito ActualizarOrdenCreditoFerremax(ordencredito ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  ordenes_de_credito SET sucursal=?,nroorden=?,fecha=?,"
                + "giraduria=?,comercio=?,socio=?,tipo=?,plazo=?,tasa=?,importe=?,monto_cuota=?,descuentos=?,"
                + "primer_vence=?,asiento=?,usuarioalta=?,interesordinario=?,"
                + "montoentregar=?,garante=?,retenciones=?,capital=?,ivainteres=?,gastos=? WHERE numero= " + ocr.getNumero());
        ps.setInt(1, ocr.getSucursal().getCodigo());
        ps.setString(2, ocr.getNrooden());
        ps.setDate(3, ocr.getFecha());
        ps.setInt(4, ocr.getGiraduria().getCodigo());
        ps.setInt(5, ocr.getCasas().getCodigo());
        ps.setInt(6, ocr.getCliente().getCodigo());
        ps.setInt(7, ocr.getTipo().getCodigo());
        ps.setInt(8, ocr.getPlazo());
        ps.setBigDecimal(9, ocr.getTasa());
        ps.setBigDecimal(10, ocr.getImporte());
        ps.setBigDecimal(11, ocr.getMonto_cuota());
        ps.setBigDecimal(12, ocr.getDescuentos());
        ps.setDate(13, ocr.getPrimer_vence());
        ps.setInt(14, ocr.getAsiento());
        ps.setInt(15, ocr.getUsuarioalta());
        ps.setBigDecimal(16, ocr.getInteresordinario());
        ps.setBigDecimal(17, ocr.getMontoentregar());
        ps.setInt(18, ocr.getGarante());
        ps.setBigDecimal(19, ocr.getRetenciones());
        ps.setDouble(20, ocr.getCapital());
        ps.setDouble(21, ocr.getIvainteres());
        ps.setDouble(22, ocr.getGastos());
        id = ocr.getNumero();
        ps.executeUpdate();

        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarDetalleFerremax(id, detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public boolean guardarDetalleFerremax(int id, String detalle, Conexion conexion) throws SQLException {
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
                            + "comercial,"
                            + "amortiza,"
                            + "minteres,"
                            + "tasaoperativa,"
                            + "autorizacion"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddocumento").getAsString());
                        ps.setString(2, obj.get("creferencia").getAsString());
                        ps.setString(3, obj.get("documento").getAsString());
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
                        ps.setString(16, obj.get("amortiza").getAsString());
                        ps.setString(17, obj.get("minteres").getAsString());
                        ps.setString(18, obj.get("tasaoperativa").getAsString());
                        ps.setString(19, obj.get("autorizacion").getAsString());
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

}

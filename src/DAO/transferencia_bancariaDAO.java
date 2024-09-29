/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.banco;
import Modelo.moneda;
import Modelo.sucursal;
import Modelo.transferencia_bancaria;
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
public class transferencia_bancariaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<transferencia_bancaria> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT reposicion_fondo_fijo.numero,reposicion_fondo_fijo.creferencia,"
                    + "reposicion_fondo_fijo.fecha,reposicion_fondo_fijo.sucursal,reposicion_fondo_fijo.origenfondo,"
                    + "reposicion_fondo_fijo.importe,reposicion_fondo_fijo.moneda,reposicion_fondo_fijo.nrocheque,"
                    + "reposicion_fondo_fijo.cotizacion,reposicion_fondo_fijo.destinofondo,"
                    + "reposicion_fondo_fijo.usuarioalta,reposicion_fondo_fijo.fechaalta,"
                    + "reposicion_fondo_fijo.entregadopor,reposicion_fondo_fijo.recibidopor,"
                    + "reposicion_fondo_fijo.cierre,reposicion_fondo_fijo.asiento,reposicion_fondo_fijo.observacion,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "monedas.nombre AS nombremoneda,"
                    + "usuarios.first_name AS nombreusuario,"
                    + "COALESCE(bo.nombrebanco_origen,'') AS nombrebanco_origen,"
                    + "COALESCE(bd.nombrebanco_destino,'') AS nombrebanco_destino "
                    + "FROM reposicion_fondo_fijo "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=reposicion_fondo_fijo.sucursal "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=reposicion_fondo_fijo.moneda "
                    + "LEFT JOIN usuarios "
                    + "ON usuarios.employee_id=reposicion_fondo_fijo.usuarioalta "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombrebanco_origen "
                    + "FROM bancos ORDER BY codigo) bo ON bo.codigo=reposicion_fondo_fijo.origenfondo "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombrebanco_destino "
                    + "FROM bancos ORDER BY codigo) bd ON bd.codigo=reposicion_fondo_fijo.destinofondo "
                    + "WHERE reposicion_fondo_fijo.fecha between'"+fechaini+"' AND  '"+fechafin+"'"
                    + "ORDER BY reposicion_fondo_fijo.numero ";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    usuario usuario = new usuario();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();
                    transferencia_bancaria ext = new transferencia_bancaria();
                    ext.setSucursal(sucursal);
                    ext.setMoneda(moneda);
                    ext.setUsuarioalta(usuario);
                    ext.setCreferencia(rs.getString("creferencia"));
                    ext.setNumero(rs.getDouble("numero"));
                    ext.setFecha(rs.getDate("fecha"));
                    ext.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ext.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ext.getUsuarioalta().setEmployee_id(rs.getInt("usuarioalta"));
                    ext.getUsuarioalta().setFirst_name(rs.getString("nombreusuario"));
                    ext.getMoneda().setCodigo(rs.getInt("moneda"));
                    ext.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ext.setCotizacion(rs.getDouble("cotizacion"));
                    ext.setImporte(rs.getDouble("importe"));
                    ext.setObservacion(rs.getString("observacion"));
                    ext.setNrocheque(rs.getString("nrocheque"));
                    ext.setAsiento(rs.getDouble("asiento"));
                    ext.setOrigenfondo(rs.getInt("origenfondo"));
                    ext.setDestinofondo(rs.getInt("destinofondo"));
                    ext.setNombrebanco_origen(rs.getString("nombrebanco_origen"));
                    ext.setNombrebanco_destino(rs.getString("nombrebanco_destino"));
                    ext.setEntregadopor(rs.getString("entregadopor"));
                    ext.setRecibidopor(rs.getString("recibidopor"));
                    lista.add(ext);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public transferencia_bancaria BuscarId(Double nNum) throws SQLException {
        usuario usuario = new usuario();
        moneda moneda = new moneda();
        sucursal sucursal = new sucursal();
        transferencia_bancaria ext = new transferencia_bancaria();

        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT reposicion_fondo_fijo.numero,reposicion_fondo_fijo.creferencia,"
                    + "reposicion_fondo_fijo.fecha,reposicion_fondo_fijo.sucursal,reposicion_fondo_fijo.origenfondo,"
                    + "reposicion_fondo_fijo.importe,reposicion_fondo_fijo.moneda,reposicion_fondo_fijo.nrocheque,"
                    + "reposicion_fondo_fijo.cotizacion,reposicion_fondo_fijo.destinofondo,"
                    + "reposicion_fondo_fijo.usuarioalta,reposicion_fondo_fijo.fechaalta,"
                    + "reposicion_fondo_fijo.entregadopor,reposicion_fondo_fijo.recibidopor,"
                    + "reposicion_fondo_fijo.cierre,reposicion_fondo_fijo.asiento,reposicion_fondo_fijo.observacion,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "monedas.nombre AS nombremoneda,"
                    + "usuarios.first_name AS nombreusuario,"
                    + "COALESCE(bo.nombrebanco_origen,'') AS nombrebanco_origen,"
                    + "COALESCE(bd.nombrebanco_destino,'') AS nombrebanco_destino "
                    + "FROM reposicion_fondo_fijo "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=reposicion_fondo_fijo.sucursal "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=reposicion_fondo_fijo.moneda "
                    + "LEFT JOIN usuarios "
                    + "ON usuarios.employee_id=reposicion_fondo_fijo.usuarioalta "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombrebanco_origen "
                    + "FROM bancos ORDER BY codigo) bo ON bo.codigo=reposicion_fondo_fijo.origenfondo "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombrebanco_destino "
                    + "FROM bancos ORDER BY codigo) bd ON bd.codigo=reposicion_fondo_fijo.destinofondo "
                    + "WHERE reposicion_fondo_fijo.numero= "+nNum
                    + " ORDER BY reposicion_fondo_fijo.numero ";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    ext.setSucursal(sucursal);
                    ext.setMoneda(moneda);
                    ext.setUsuarioalta(usuario);
                    ext.setCreferencia(rs.getString("creferencia"));
                    ext.setNumero(rs.getDouble("numero"));
                    ext.setFecha(rs.getDate("fecha"));
                    ext.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ext.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ext.getUsuarioalta().setEmployee_id(rs.getInt("usuarioalta"));
                    ext.getUsuarioalta().setFirst_name(rs.getString("nombreusuario"));
                    ext.getMoneda().setCodigo(rs.getInt("moneda"));
                    ext.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ext.setCotizacion(rs.getDouble("cotizacion"));
                    ext.setImporte(rs.getDouble("importe"));
                    ext.setObservacion(rs.getString("observacion"));
                    ext.setNrocheque(rs.getString("nrocheque"));
                    ext.setAsiento(rs.getDouble("asiento"));
                    ext.setOrigenfondo(rs.getInt("origenfondo"));
                    ext.setDestinofondo(rs.getInt("destinofondo"));
                    ext.setNombrebanco_origen(rs.getString("nombrebanco_origen"));
                    ext.setNombrebanco_destino(rs.getString("nombrebanco_destino"));
                    ext.setEntregadopor(rs.getString("entregadopor"));
                    ext.setRecibidopor(rs.getString("recibidopor"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return ext;
    }

    public transferencia_bancaria insertarMovimiento(transferencia_bancaria mov, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO reposicion_fondo_fijo (creferencia,fecha,sucursal,origenfondo,importe,moneda,"
                + "nrocheque,cotizacion,destinofondo,entregadopor,\n"
                + "recibidopor,observacion,usuarioalta) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, mov.getCreferencia());
        ps.setDate(2, mov.getFecha());
        ps.setInt(3, mov.getSucursal().getCodigo());
        ps.setInt(4, mov.getOrigenfondo());
        ps.setDouble(5, mov.getImporte());
        ps.setInt(6, mov.getMoneda().getCodigo());
        ps.setString(7, mov.getNrocheque());
        ps.setDouble(8, mov.getCotizacion());
        ps.setInt(9, mov.getDestinofondo());
        ps.setString(10, mov.getEntregadopor());
        ps.setString(11, mov.getRecibidopor());
        ps.setString(12, mov.getObservacion());
        ps.setInt(13, mov.getUsuarioalta().getEmployee_id());
        ps.executeUpdate();

        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarDebitoCredito(mov.getCreferencia(), detalle, con);
        }

        st.close();
        ps.close();
        return mov;
    }

    public boolean borrarTransferencia(Double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM reposicion_fondo_fijo WHERE numero=?");
        ps.setDouble(1, id);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean borrarExtraccion(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM extracciones WHERE idmovimiento=?");
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

    
        public transferencia_bancaria ActualizarTransferencia(transferencia_bancaria mov, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE reposicion_fondo_fijo SET fecha=?,sucursal=?,origenfondo=?,importe=?,moneda=?,"
                + "nrocheque=?,cotizacion=?,destinofondo=?,entregadopor=?,\n"
                + "recibidopor=?,observacion=?,usuarioalta=? WHERE numero= " + mov.getNumero());
        ps.setDate(1, mov.getFecha());
        ps.setInt(2, mov.getSucursal().getCodigo());
        ps.setInt(3, mov.getOrigenfondo());
        ps.setDouble(4, mov.getImporte());
        ps.setInt(5, mov.getMoneda().getCodigo());
        ps.setString(6, mov.getNrocheque());
        ps.setDouble(7, mov.getCotizacion());
        ps.setInt(8, mov.getDestinofondo());
        ps.setString(9, mov.getEntregadopor());
        ps.setString(10, mov.getRecibidopor());
        ps.setString(11, mov.getObservacion());
        ps.setInt(12, mov.getUsuarioalta().getEmployee_id());
        id = mov.getNumero().intValue();
        ps.executeUpdate();

        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarDebitoCredito(mov.getCreferencia(), detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return mov;
    }

    
    public boolean guardarDebitoCredito(String id, String detallebanco, Conexion conexion) throws SQLException {
        boolean guardado = true;
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
                            + "tipo,"
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

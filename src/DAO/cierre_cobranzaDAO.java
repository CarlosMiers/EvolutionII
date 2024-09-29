/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.UUID;
import Conexion.Conexion;
import Modelo.casa;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.cierre_cobranza;
import Modelo.configuracion;
import Modelo.giraduria;
import Modelo.moneda;
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
 * @author SERVIDOR
 */
public class cierre_cobranzaDAO {

    String iddocumento;
    String creferencia;
    int ndocumento;
    String fecha;
    String vencimiento;
    int cliente;
    int sucursal;
    int moneda;
    int comprobante;
    int vendedor;
    int caja;
    double importe;
    int giraduria;
    int numerocuota;
    int cuota;
    double saldo;
    int comercial;

    Conexion con = null;
    Statement st = null;
    Connection conn = null;

    public ArrayList<cierre_cobranza> MostrarxSocio(int cliente) throws SQLException {
        ArrayList<cierre_cobranza> lista = new ArrayList<cierre_cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cierre_cobranzas.creferencia,cierre_cobranzas.iddocumento,cierre_cobranzas.documento,cierre_cobranzas.fecha,";
            cSql = cSql + "cierre_cobranzas.vencimiento,cierre_cobranzas.giraduria,cierre_cobranzas.comprobante,cierre_cobranzas.comercial";
            cSql = cSql + "cierre_cobranzas.enviado,cierre_cobranzas.cliente,cierre_cobranzas.saldo,casas.nombre as nombrecomercio,";
            cSql = cSql + "cierre_cobranzas.autorizacion,cierre_cobranzas.cobrado,cierre_cobranzas.cuota,cierre_cobranzas.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cierre_cobranzas.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
            cSql = cSql + "giradurias.nombre as nombregiraduria ";
            cSql = cSql + "FROM cierre_cobranzas ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cierre_cobranzas.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cierre_cobranzas.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cierre_cobranzas.giraduria ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cierre_cobranzas.comercial ";
            cSql = cSql + " WHERE AND cierre_cobranzas.cliente= ? ";
            cSql = cSql + " ORDER by cierre_cobranzas.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cierre_cobranza cc = new cierre_cobranza();
                    casa casa = new casa();

                    cc.setComercial(casa);
                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setEnviado(rs.getDouble("enviado"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setCobrado(rs.getDouble("cobrado"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercio"));
                    lista.add(cc);
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

    public ArrayList<cierre_cobranza> MostrarxGiraduriaxVence(int ngiraduria, Date fechaini, Date fechafin, int ncomprobante) throws SQLException {
        ArrayList<cierre_cobranza> lista = new ArrayList<cierre_cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT cierre_cobranzas.creferencia,cierre_cobranzas.iddocumento,cierre_cobranzas.documento,cierre_cobranzas.fecha,";
            cSql = cSql + "cierre_cobranzas.vencimiento,cierre_cobranzas.giraduria,cierre_cobranzas.comprobante,cierre_cobranzas.comercial,";
            cSql = cSql + "cierre_cobranzas.enviado,cierre_cobranzas.cliente,cierre_cobranzas.saldo,casas.nombre as nombrecomercio,";
            cSql = cSql + "cierre_cobranzas.autorizacion,cierre_cobranzas.cobrado,cierre_cobranzas.cuota,cierre_cobranzas.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.ruc,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cierre_cobranzas.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
            cSql = cSql + "giradurias.nombre as nombregiraduria ";
            cSql = cSql + "FROM cierre_cobranzas ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cierre_cobranzas.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cierre_cobranzas.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cierre_cobranzas.giraduria ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cierre_cobranzas.comercial ";
            cSql = cSql + " WHERE cierre_cobranzas.giraduria= ? ";
            cSql = cSql + " AND cierre_cobranzas.fecha_cobro between ? AND ? AND cierre_cobranzas.comprobante=? ";
            cSql = cSql + " ORDER by cierre_cobranzas.cliente";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ngiraduria);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);
                ps.setInt(4, ncomprobante);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cierre_cobranza cc = new cierre_cobranza();
                    casa casa = new casa();

                    cc.setComercial(casa);
                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setEnviado(rs.getDouble("enviado"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setCobrado(rs.getDouble("cobrado"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercio"));
                    lista.add(cc);
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

    public ArrayList<cierre_cobranza> MostrarxGiraduriaxFecobro(int ngiraduria, Date fechai, Date fechaf) throws SQLException {
        ArrayList<cierre_cobranza> lista = new ArrayList<cierre_cobranza>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cierre_cobranzas.creferencia,cierre_cobranzas.iddocumento,cierre_cobranzas.documento,cierre_cobranzas.fecha,";
            cSql = cSql + "cierre_cobranzas.vencimiento,cierre_cobranzas.giraduria,cierre_cobranzas.comprobante,cierre_cobranzas.fecha_pago,";
            cSql = cSql + "cierre_cobranzas.enviado,cierre_cobranzas.cliente,cierre_cobranzas.saldo,cierre_cobranzas.tasaoperativa,";
            cSql = cSql + "cierre_cobranzas.autorizacion,cierre_cobranzas.cobrado,cierre_cobranzas.cuota,cierre_cobranzas.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cierre_cobranzas.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
            cSql = cSql + "giradurias.nombre as nombregiraduria ";
            cSql = cSql + "FROM cierre_cobranzas ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cierre_cobranzas.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cierre_cobranzas.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cierre_cobranzas.giraduria ";
            cSql = cSql + " WHERE cierre_cobranzas.saldo>0 ";
            cSql = cSql + " AND cierre_cobranzas.giraduria= ? ";
            cSql = cSql + " AND cierre_cobranzas.fecha_cobro between ? AND ? ";
            cSql = cSql + " ORDER by cierre_cobranzas.cliente,cierre_cobranzas.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ngiraduria);
                ps.setDate(2, fechai);
                ps.setDate(3, fechaf);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cierre_cobranza cc = new cierre_cobranza();
                    casa casa = new casa();

                    cc.setComercial(casa);
                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setEnviado(rs.getDouble("enviado"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setCobrado(rs.getDouble("cobrado"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercio"));
                    lista.add(cc);
                    lista.add(cc);
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

    public cierre_cobranza VerificarControl(int nplanilla) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        cierre_cobranza cc = new cierre_cobranza();

        try {

            String cSql = "SELECT SUM(cierre_cobranzas.cobrado) as tcobrado";
            cSql = cSql + " FROM cierre_cobranzas ";
            cSql = cSql + " WHERE cierre_cobranzas.iddetalle=? ";
            cSql = cSql + " GROUP BY cierre_cobranzas.iddetalle ";
            cSql = cSql + " ORDER by cierre_cobranzas.iddetalle ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nplanilla);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cc.setCobrado(rs.getDouble("tcobrado"));
                }
                System.out.println("--> " + cc.getCobrado());
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return cc;
    }

    public boolean guardarCobrosxGiraduria(String detallecuota) throws SQLException {
        boolean guardacuota = true;
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detallecuota);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO cierre_cobranzas("
                            + "iddetalle,"
                            + "iddocumento,"
                            + "fecha,"
                            + "vencimiento,"
                            + "documento,"
                            + "fecha_cobro,"
                            + "enviado,"
                            + "autorizacion,"
                            + "cliente,"
                            + "sucursal,"
                            + "moneda,"
                            + "comprobante,"
                            + "giraduria,"
                            + "numerocuota,"
                            + "creferencia,"
                            + "comercial,"
                            + "amortiza,"
                            + "minteres,"
                            + "cuota,"
                            + "cobrado"
                            + ") "
                            + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddetalle").getAsString());
                        ps.setString(2, obj.get("iddocumento").getAsString());
                        ps.setString(3, obj.get("fecha").getAsString());
                        ps.setString(4, obj.get("vencimiento").getAsString());
                        ps.setString(5, obj.get("documento").getAsString());
                        ps.setString(6, obj.get("fecha_cobro").getAsString());
                        ps.setString(7, obj.get("enviado").getAsString());
                        ps.setString(8, obj.get("autorizacion").getAsString());
                        ps.setString(9, obj.get("cliente").getAsString());
                        ps.setString(10, obj.get("sucursal").getAsString());
                        ps.setString(11, obj.get("moneda").getAsString());
                        ps.setString(12, obj.get("comprobante").getAsString());
                        ps.setString(13, obj.get("giraduria").getAsString());
                        ps.setString(14, obj.get("numerocuota").getAsString());
                        ps.setString(15, obj.get("creferencia").getAsString());
                        ps.setString(16, obj.get("comercial").getAsString());
                        ps.setString(17, obj.get("amortiza").getAsString());
                        ps.setString(18, obj.get("minteres").getAsString());
                        ps.setString(19, obj.get("cuota").getAsString());
                        ps.setString(20, obj.get("cobrado").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardacuota = false;
                            break;
                        }

                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardacuota = false;
                    break;
                }
            }

            if (guardacuota) {
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
            guardacuota = false;
        }
        st.close();
        conn.close();
        return guardacuota;
    }

    public cierre_cobranza GenerarInteres(Double id) throws SQLException {
        cierre_cobranza p = new cierre_cobranza();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = new configuracion();
        config = configDAO.configCierre();
        UUID UUI = new UUID();

        config.getPorcentajeivainteres();
        con = new Conexion();
        st = con.conectar();
        conn = st.getConnection();

        try {

            String sql = "SELECT documento,creferencia,cliente,fecha_cobro,giraduria,"
                    + "DATE_ADD(fecha_cobro,INTERVAL 1 MONTH) AS proximo_vencimiento,"
                    + "SUM(enviado-cobrado) AS saldonocobrado "
                    + "FROM gestion_cobros "
                    + "INNER JOIN comprobantes "
                    + "ON gestion_cobros.comprobante=comprobantes.codigo "
                    + " WHERE iddetalle=? "
                    + " AND comprobantes.interes=1 "
                    + " GROUP BY cliente "
                    + " HAVING(saldonocobrado > 0) "
                    + " ORDER BY iddetalle,cliente ";
 
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    creferencia = UUID.crearUUID();;
                    creferencia = creferencia.substring(1, 25);
                    iddocumento = UUID.crearUUID();
                    iddocumento =  iddocumento.substring(1, 25);
                    ndocumento = rs.getInt("cliente");
                    fecha = rs.getString("fecha_cobro");
                    giraduria = rs.getInt("giraduria");
                    cliente = rs.getInt("cliente");
                    vencimiento = rs.getString("proximo_vencimiento");
                    moneda = 1;
                    comprobante = config.getCodigointeres();
                    importe = Math.round((rs.getDouble("saldonocobrado") * config.getInteresmora() / 100)
                            + (rs.getDouble("saldonocobrado") * config.getInteresmora() / 100)
                            * config.getPorcentajeivainteres() / 100);
                    saldo = importe;
                    vendedor = 1;
                    sucursal = 1;
                    numerocuota = 1;
                    cuota = 1;
                    comercial = 128;
                    caja = 1;
                    //SE ENVIA A CREAR REGISTRO
                    this.AgregarInteres();
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }

        st.close();

        conn.close();
        return p;

    }

    public void AgregarInteres() throws SQLException {
        String sql = "INSERT INTO cuenta_clientes ("
                + "iddocumento,creferencia,"
                + "documento,fecha,"
                + "vencimiento,cliente,"
                + "sucursal,moneda,"
                + "comprobante,vendedor,"
                + "caja,importe,"
                + "giraduria,numerocuota,"
                + "cuota,saldo,comercial)"
                + "VALUES(?,?,?,?,?"
                + ",?,?,?,?,?"
                + ",?,?,?,?,?"
                + ",?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, iddocumento);
        ps.setString(2, creferencia);
        ps.setDouble(3, ndocumento);
        ps.setString(4, fecha);
        ps.setString(5, vencimiento);
        ps.setInt(6, cliente);
        ps.setInt(7, sucursal);
        ps.setInt(8, moneda);
        ps.setInt(9, comprobante);
        ps.setInt(10, vendedor);
        ps.setInt(11, caja);
        ps.setDouble(12, saldo);
        ps.setInt(13, giraduria);
        ps.setInt(14, numerocuota);
        ps.setInt(15, cuota);
        ps.setDouble(16, saldo);
        ps.setInt(17, comercial);
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> GRABANDO INTERESES POR REBOTADOS " + ex.getLocalizedMessage());
        }
    }

}

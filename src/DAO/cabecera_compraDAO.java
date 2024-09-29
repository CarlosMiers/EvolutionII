package DAO;

import Modelo.cabecera_compra;
import Conexion.Conexion;
import Modelo.comprobante;
import Modelo.marca;
import Modelo.moneda;
import Modelo.proveedor;
import Modelo.rubro;
import Modelo.sucursal;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author SERVIDOR
 */
public class cabecera_compraDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cabecera_compra> MostrarxFecha(Date fechaini, Date fechafin, int tipo) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_compras.formatofactura,cabecera_compras.creferencia,cabecera_compras.nrofactura,cabecera_compras.sucursal,cabecera_compras.fecha,cabecera_compras.proveedor,";
            cSql = cSql + "cabecera_compras.exentas,cabecera_compras.gravadas10,cabecera_compras.gravadas5,cabecera_compras.totalneto,cabecera_compras.moneda,";
            cSql = cSql + "cabecera_compras.timbrado,cabecera_compras.vencetimbrado,cabecera_compras.cotizacion,cabecera_compras.observacion,cabecera_compras.primer_vence,";
            cSql = cSql + "cabecera_compras.cierre,cabecera_compras.comprobante,cabecera_compras.pagos,cabecera_compras.financiado,cabecera_compras.enviarcta,cabecera_compras.generarasiento,";
            cSql = cSql + "cabecera_compras.cuotas,cabecera_compras.usuarioalta,cabecera_compras.fechaalta,cabecera_compras.usuarioupdate,cabecera_compras.fechaupdate,";
            cSql = cSql + "cabecera_compras.tipo_gasto,cabecera_compras.retencion,cabecera_compras.importado,cabecera_compras.ordencompra,cabecera_compras.asiento,";
            cSql = cSql + "proveedores.nombre AS nombreproveedor,";
            cSql = cSql + "comprobantes.nombre AS nombrecomprobante,";
            cSql = cSql + "sucursales.nombre AS nombresucursal,";
            cSql = cSql + "monedas.nombre  AS nombremoneda,";
            cSql = cSql + "monedas.etiqueta  AS etiqueta ";
            cSql = cSql + " FROM cabecera_compras ";
            cSql = cSql + " LEFT JOIN proveedores ";
            cSql = cSql + " ON proveedores.codigo=cabecera_compras.proveedor ";
            cSql = cSql + " LEFT JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=cabecera_compras.comprobante ";
            cSql = cSql + " LEFT JOIN sucursales  ";
            cSql = cSql + " ON sucursales.codigo=cabecera_compras.sucursal ";
            cSql = cSql + " LEFT JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cabecera_compras.moneda";
            cSql = cSql + " WHERE cabecera_compras.fecha BETWEEN ?  AND ? ";
            cSql = cSql + " AND IF(?>0,cabecera_compras.totalneto>0,cabecera_compras.totalneto<0) ";
            cSql = cSql + " ORDER BY cabecera_compras.fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, tipo);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal sucursal = new sucursal();
                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();
                    comprobante comprobante = new comprobante();

                    cabecera_compra com = new cabecera_compra();

                    com.setSucursal(sucursal);
                    com.setProveedor(proveedor);
                    com.setMoneda(moneda);
                    com.setComprobante(comprobante);

                    com.setCreferencia(rs.getString("creferencia"));
                    com.setFormatofactura(rs.getString("formatofactura"));
                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.getSucursal().setCodigo(rs.getInt("sucursal"));
                    com.getSucursal().setNombre(rs.getString("nombresucursal"));
                    com.setFecha(rs.getDate("fecha"));
                    com.getProveedor().setCodigo(rs.getInt("proveedor"));
                    com.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    //   com.getProveedor().setRuc(rs.getString("ruc"));
                    com.setExentas(rs.getBigDecimal("exentas"));
                    com.setGravadas10(rs.getBigDecimal("gravadas10"));
                    com.setGravadas5(rs.getBigDecimal("gravadas5"));
                    com.setTotalneto(rs.getBigDecimal("totalneto"));
                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));
                    com.getMoneda().setEtiqueta(rs.getString("etiqueta"));
                    com.setTimbrado(rs.getInt("timbrado"));
                    com.setVencetimbrado(rs.getDate("vencetimbrado"));
                    com.setCotizacion(rs.getBigDecimal("cotizacion"));
                    com.setObservacion(rs.getString("observacion"));
                    com.setPrimer_vence(rs.getDate("primer_vence"));
                    com.setCierre(rs.getInt("cierre"));
                    com.getComprobante().setCodigo(rs.getInt("comprobante"));
                    com.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    com.setPagos(rs.getBigDecimal("pagos"));
                    com.setFinanciado(rs.getBigDecimal("financiado"));
                    com.setEnviarcta(rs.getInt("enviarcta"));
                    com.setGenerarasiento(rs.getInt("generarasiento"));
                    com.setAsiento(rs.getInt("asiento"));
                    com.setCuotas(rs.getInt("cuotas"));
                    com.setUsuarioalta(rs.getInt("usuarioalta"));
                    com.setFechaalta(rs.getDate("fechaalta"));
                    com.setUsuarioupdate(rs.getInt("usuarioupdate"));
                    com.setFechaupdate(rs.getDate("fechaupdate"));
                    com.setTipo_gasto(rs.getInt("tipo_gasto"));
                    com.setRetencion(rs.getBigDecimal("retencion"));
                    com.setImportado(rs.getInt("importado"));
                    com.setOrdencompra(rs.getInt("ordencompra"));
                    lista.add(com);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public cabecera_compra buscarId(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        cabecera_compra com = new cabecera_compra();
        try {

            String sql = "SELECT cabecera_compras.formatofactura,cabecera_compras.creferencia,cabecera_compras.nrofactura,cabecera_compras.sucursal,cabecera_compras.fecha,cabecera_compras.proveedor,"
                    + "cabecera_compras.exentas,cabecera_compras.gravadas10,cabecera_compras.gravadas5,cabecera_compras.totalneto,cabecera_compras.moneda,"
                    + "cabecera_compras.timbrado,cabecera_compras.vencetimbrado,cabecera_compras.cotizacion,cabecera_compras.observacion,cabecera_compras.primer_vence,"
                    + "cabecera_compras.cierre,cabecera_compras.comprobante,cabecera_compras.pagos,cabecera_compras.financiado,cabecera_compras.enviarcta,cabecera_compras.generarasiento,"
                    + "cabecera_compras.cuotas,cabecera_compras.usuarioalta,cabecera_compras.fechaalta,cabecera_compras.usuarioupdate,cabecera_compras.fechaupdate,"
                    + "cabecera_compras.tipo_gasto,cabecera_compras.retencion,cabecera_compras.importado,cabecera_compras.ordencompra,cabecera_compras.asiento,"
                    + "proveedores.nombre AS nombreproveedor,"
                    + "proveedores.ruc,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "monedas.nombre  AS nombremoneda,"
                    + "monedas.etiqueta  AS etiqueta "
                    + " FROM cabecera_compras "
                    + " LEFT JOIN proveedores "
                    + " ON proveedores.codigo=cabecera_compras.proveedor "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=cabecera_compras.comprobante "
                    + " LEFT JOIN sucursales  "
                    + " ON sucursales.codigo=cabecera_compras.sucursal "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=cabecera_compras.moneda"
                    + " WHERE cabecera_compras.creferencia= ? "
                    + " ORDER BY cabecera_compras.creferencia ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();
                    comprobante comprobante = new comprobante();
                    com.setSucursal(sucursal);
                    com.setProveedor(proveedor);
                    com.setMoneda(moneda);
                    com.setComprobante(comprobante);
                    com.setCreferencia(rs.getString("creferencia"));
                    com.setFormatofactura(rs.getString("formatofactura"));
                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.getSucursal().setCodigo(rs.getInt("sucursal"));
                    com.getSucursal().setNombre(rs.getString("nombresucursal"));
                    com.setFecha(rs.getDate("fecha"));
                    com.getProveedor().setCodigo(rs.getInt("proveedor"));
                    com.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    com.getProveedor().setRuc(rs.getString("ruc"));
                    com.setExentas(rs.getBigDecimal("exentas"));
                    com.setGravadas10(rs.getBigDecimal("gravadas10"));
                    com.setGravadas5(rs.getBigDecimal("gravadas5"));
                    com.setTotalneto(rs.getBigDecimal("totalneto"));
                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));
                    com.getMoneda().setEtiqueta(rs.getString("etiqueta"));
                    com.setTimbrado(rs.getInt("timbrado"));
                    com.setVencetimbrado(rs.getDate("vencetimbrado"));
                    com.setCotizacion(rs.getBigDecimal("cotizacion"));
                    com.setObservacion(rs.getString("observacion"));
                    com.setPrimer_vence(rs.getDate("primer_vence"));
                    com.setCierre(rs.getInt("cierre"));
                    com.getComprobante().setCodigo(rs.getInt("comprobante"));
                    com.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    com.setPagos(rs.getBigDecimal("pagos"));
                    com.setFinanciado(rs.getBigDecimal("financiado"));
                    com.setEnviarcta(rs.getInt("enviarcta"));
                    com.setGenerarasiento(rs.getInt("generarasiento"));
                    com.setAsiento(rs.getInt("asiento"));
                    com.setCuotas(rs.getInt("cuotas"));
                    com.setUsuarioalta(rs.getInt("usuarioalta"));
                    com.setFechaalta(rs.getDate("fechaalta"));
                    com.setUsuarioupdate(rs.getInt("usuarioupdate"));
                    com.setFechaupdate(rs.getDate("fechaupdate"));
                    com.setTipo_gasto(rs.getInt("tipo_gasto"));
                    com.setRetencion(rs.getBigDecimal("retencion"));
                    com.setImportado(rs.getInt("importado"));
                    com.setOrdencompra(rs.getInt("ordencompra"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return com;

    }

    public cabecera_compra AgregarFacturaCompra(cabecera_compra c, String detalle, String detalleformapago, String detallebanco) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        boolean guardadoforma = false;
        boolean guardabanco = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_compras (creferencia,nrofactura,sucursal,"
                + "fecha,proveedor,exentas,gravadas10,gravadas5,totalneto,timbrado,vencetimbrado,"
                + "moneda,cotizacion,observacion,primer_vence,comprobante,pagos,"
                + "financiado,cuotas,usuarioalta,fechaalta,formatofactura)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getCreferencia());
        ps.setDouble(2, c.getNrofactura());
        ps.setInt(3, c.getSucursal().getCodigo());
        ps.setDate(4, c.getFecha());
        ps.setInt(5, c.getProveedor().getCodigo());
        ps.setBigDecimal(6, c.getExentas());
        ps.setBigDecimal(7, c.getGravadas10());
        ps.setBigDecimal(8, c.getGravadas5());
        ps.setBigDecimal(9, c.getTotalneto());
        ps.setInt(10, c.getTimbrado());
        ps.setDate(11, c.getVencetimbrado());
        ps.setInt(12, c.getMoneda().getCodigo());
        ps.setBigDecimal(13, c.getCotizacion());
        ps.setString(14, c.getObservacion());
        ps.setDate(15, c.getPrimer_vence());
        ps.setInt(16, c.getComprobante().getCodigo());
        ps.setBigDecimal(17, c.getPagos());
        ps.setBigDecimal(18, c.getFinanciado());
        ps.setInt(19, c.getCuotas());
        ps.setInt(20, c.getUsuarioalta());
        ps.setDate(21, c.getFechaalta());
        ps.setString(22, c.getFormatofactura());
        ps.executeUpdate();
        guardarItemFactura(c.getCreferencia(), detalle, con);
        if (c.getPagos().doubleValue() > 0) {
            guardadoforma = guardarFormaPago(c.getCreferencia(), detalleformapago, con);
            guardabanco = guardarDebitoCredito(c.getCreferencia(), detallebanco, con);
        }
        st.close();
        ps.close();
        conn.close();
        return c;
    }

    public cabecera_compra ActualizarCompra(cabecera_compra c, String detalle, String detalleformapago, String detallebanco) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardacuota = false;
        boolean guardadoforma = false;
        boolean guardabanco = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  cabecera_compras SET creferencia=?,nrofactura=?,sucursal=?,fecha=?,proveedor=?,exentas=?,gravadas10=?,gravadas5=?,totalneto=?,timbrado=?,vencetimbrado=?,moneda=?,cotizacion=?,observacion=?,primer_vence=?,comprobante=?,pagos=?,financiado=?,cuotas=?,usuarioalta=?,fechaalta=?,formatofactura=? WHERE creferencia= '" + c.getCreferencia() + "'");
        ps.setString(1, c.getCreferencia());
        ps.setDouble(2, c.getNrofactura());
        ps.setInt(3, c.getSucursal().getCodigo());
        ps.setDate(4, c.getFecha());
        ps.setInt(5, c.getProveedor().getCodigo());
        ps.setBigDecimal(6, c.getExentas());
        ps.setBigDecimal(7, c.getGravadas10());
        ps.setBigDecimal(8, c.getGravadas5());
        ps.setBigDecimal(9, c.getTotalneto());
        ps.setInt(10, c.getTimbrado());
        ps.setDate(11, c.getVencetimbrado());
        ps.setInt(12, c.getMoneda().getCodigo());
        ps.setBigDecimal(13, c.getCotizacion());
        ps.setString(14, c.getObservacion());
        ps.setDate(15, c.getPrimer_vence());
        ps.setInt(16, c.getComprobante().getCodigo());
        ps.setBigDecimal(17, c.getPagos());
        ps.setBigDecimal(18, c.getFinanciado());
        ps.setInt(19, c.getCuotas());
        ps.setInt(20, c.getUsuarioalta());
        ps.setDate(21, c.getFechaalta());
        ps.setString(22, c.getFormatofactura());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemFactura(c.getCreferencia(), detalle, con);
        }
        if (c.getPagos().doubleValue() > 0) {
            guardadoforma = guardarFormaPago(c.getCreferencia(), detalleformapago, con);
            guardabanco = guardarDebitoCredito(c.getCreferencia(), detallebanco, con);
        }
        st.close();
        ps.close();
        return c;
    }

    public boolean guardarItemFactura(String id, String detalle, Conexion conexion) throws SQLException {

        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement psdetalle = null;

        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_compras WHERE dreferencia=?");
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
                    String sql = "INSERT INTO detalle_compras("
                            + "dreferencia,"
                            + "codprod,"
                            + "cantidad,"
                            + "prcosto,"
                            + "monto,"
                            + "impiva,"
                            + "porcentaje,"
                            + "utilidad1,"
                            + "utilidad2,"
                            + "precioviejo1,"
                            + "precioviejo2,"
                            + "precio1,"
                            + "precio2,"
                            + "suc"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("codprod").getAsString());
                        ps.setString(3, obj.get("cantidad").getAsString());
                        ps.setString(4, obj.get("prcosto").getAsString());
                        ps.setString(5, obj.get("monto").getAsString());
                        ps.setString(6, obj.get("impiva").getAsString());
                        ps.setString(7, obj.get("porcentaje").getAsString());
                        ps.setString(8, obj.get("utilidad1").getAsString());
                        ps.setString(9, obj.get("utilidad2").getAsString());
                        ps.setString(10, obj.get("precioviejo1").getAsString());
                        ps.setString(11, obj.get("precioviejo2").getAsString());
                        ps.setString(12, obj.get("precio1").getAsString());
                        ps.setString(13, obj.get("precio2").getAsString());
                        ps.setString(14, obj.get("suc").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    conn.rollback();
                    guardado = false;
                    return guardado;

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
        //  conn.close();
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
                        ps.setInt(6, obj.get("netocobrado").getAsInt());
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

    public boolean borrarDetalleCuenta(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuenta_proveedores WHERE idmovimiento=?");
        ps.setString(1, referencia);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean borrarCompra(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM cabecera_compras WHERE creferencia=?");
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

    public ArrayList<cabecera_compra> librocompraconsolidado(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cabecera_compras.formatofactura,cabecera_compras.nrofactura  AS nrofactura,"
                    + "1 AS ntipo,"
                    + "cabecera_compras.timbrado AS timbrado,"
                    + "cabecera_compras.sucursal AS sucursal,"
                    + "cabecera_compras.fecha AS fecha,"
                    + "cabecera_compras.proveedor   AS proveedor,"
                    + "cabecera_compras.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_compras.exentas * cabecera_compras.cotizacion),0),0) AS totalexentas,"
                    + "IFNULL(ROUND((cabecera_compras.gravadas10 * cabecera_compras.cotizacion),0),0) AS totalgravadas10,"
                    + "IFNULL(ROUND((cabecera_compras.gravadas5 * cabecera_compras.cotizacion),0),0) AS totalgravadas5,"
                    + "IFNULL(ROUND((cabecera_compras.totalneto * cabecera_compras.cotizacion),0),0) AS total,"
                    + "proveedores.nombre AS nombreproveedor,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "proveedores.ruc AS ruc "
                    + "FROM cabecera_compras "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_compras.sucursal "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=cabecera_compras.proveedor "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_compras.comprobante "
                    + "WHERE cabecera_compras.fecha BETWEEN '"+fechaini+"' AND '"+fechafin+"'"
                    + " AND cabecera_compras.totalneto>0 "
                    + " AND comprobantes.libros=1 "
                    + " UNION ALL "
                    + "SELECT gastos_compras.formatofactura,gastos_compras.nrofactura  AS nrofactura, "
                    + "1 AS ntipo,"
                    + "gastos_compras.timbrado AS timbrado,"
                    + "gastos_compras.sucursal AS sucursal, "
                    + "gastos_compras.fecha AS fecha, "
                    + "gastos_compras.proveedor AS proveedor, "
                    + "gastos_compras.comprobante AS comprobante,"
                    + "IFNULL(ROUND((gastos_compras.exentas * gastos_compras.cotizacion),0),0) AS totalexentas,"
                    + "IFNULL(ROUND((gastos_compras.gravadas10 * gastos_compras.cotizacion),0),0) AS totalgravadas10,"
                    + "IFNULL(ROUND((gastos_compras.gravadas5 * gastos_compras.cotizacion),0),0) AS totalgravadas5,"
                    + "IFNULL(ROUND((gastos_compras.totalneto * gastos_compras.cotizacion),0),0) AS total,"
                    + "proveedores.nombre AS nombreproveedor,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "proveedores.ruc AS ruc "
                    + "FROM gastos_compras "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=gastos_compras.sucursal "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=gastos_compras.proveedor "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=gastos_compras.comprobante "
                    + "WHERE gastos_compras.fecha BETWEEN '"+fechaini+"' AND '"+fechafin+"'"
                    + " AND comprobantes.libros=1 "
                    + " UNION ALL "
                    + "SELECT cabecera_ventas.formatofactura,cabecera_ventas.factura  AS nrofactura,"
                    + "2 AS ntipo,"
                    + "cabecera_ventas.nrotimbrado AS timbrado,"
                    + "cabecera_ventas.sucursal AS sucursal,"
                    + "cabecera_ventas.fecha AS fecha,"
                    + "cabecera_ventas.cliente   AS proveedor,"
                    + "cabecera_ventas.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_ventas.exentas * cabecera_ventas.cotizacion),0),0) AS totalexentas,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion),0),0) AS totalgravadas10,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion),0),0) AS totalgravadas5,"
                    + "IFNULL(ROUND((cabecera_ventas.totalneto * cabecera_ventas.cotizacion),0),0) AS total,"
                    + "clientes.nombre AS nombreproveedor,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "clientes.ruc AS ruc "
                    + "FROM cabecera_ventas "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_ventas.sucursal "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cabecera_ventas.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                    + "WHERE fecha BETWEEN '"+fechaini+"' AND '"+fechafin+"'"
                    + " AND comprobantes.libros=1 "
                    + " AND cabecera_ventas.totalneto<0"
                    + " ORDER BY ntipo,comprobante,fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    proveedor proveedor = new proveedor();
                    cabecera_compra com = new cabecera_compra();
                    com.setSucursal(sucursal);
                    com.setComprobante(comprobante);
                    com.setProveedor(proveedor);
                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.setFormatofactura(rs.getString("formatofactura"));
                    com.getSucursal().setCodigo(rs.getInt("sucursal"));
                    com.setFecha(rs.getDate("fecha"));
                    com.getProveedor().setCodigo(rs.getInt("proveedor"));
                    com.getComprobante().setCodigo(rs.getInt("comprobante"));
                    com.setExentas(rs.getBigDecimal("totalexentas"));
                    com.setGravadas10(rs.getBigDecimal("totalgravadas10"));
                    com.setGravadas5(rs.getBigDecimal("totalgravadas5"));
                    com.setTotalneto(rs.getBigDecimal("total"));
                    com.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    com.getProveedor().setRuc(rs.getString("ruc"));
                    com.getSucursal().setNombre(rs.getString("nombresucursal"));
                    com.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    com.setTimbrado(rs.getInt("timbrado"));
                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cabecera_compra> librocompraxsucursal(int suc, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cabecera_compras.formatofactura,cabecera_compras.nrofactura  AS nrofactura,"
                    + "1 AS ntipo,"
                    + "cabecera_compras.timbrado AS timbrado,"
                    + "cabecera_compras.sucursal AS sucursal,"
                    + "cabecera_compras.fecha AS fecha,"
                    + "cabecera_compras.proveedor   AS proveedor,"
                    + "cabecera_compras.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_compras.exentas * cabecera_compras.cotizacion),0),0) AS totalexentas,"
                    + "IFNULL(ROUND((cabecera_compras.gravadas10 * cabecera_compras.cotizacion),0),0) AS totalgravadas10,"
                    + "IFNULL(ROUND((cabecera_compras.gravadas5 * cabecera_compras.cotizacion),0),0) AS totalgravadas5,"
                    + "IFNULL(ROUND((cabecera_compras.totalneto * cabecera_compras.cotizacion),0),0) AS total,"
                    + "proveedores.nombre AS nombreproveedor,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "proveedores.ruc AS ruc "
                    + "FROM cabecera_compras "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_compras.sucursal "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=cabecera_compras.proveedor "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_compras.comprobante "
                    + "WHERE cabecera_compras.sucursal="+suc+" AND fecha BETWEEN '"+fechaini+"' AND '"+fechafin+"'"
                    + " AND cabecera_compras.totalneto>0 "
                    + " AND comprobantes.libros=1 "
                    + " UNION ALL "
                    + "SELECT gastos_compras.formatofactura,gastos_compras.nrofactura  AS nrofactura, "
                    + "1 AS ntipo,"
                    + "gastos_compras.timbrado AS timbrado,"
                    + "gastos_compras.sucursal AS sucursal, "
                    + "gastos_compras.fecha AS fecha, "
                    + "gastos_compras.proveedor AS proveedor, "
                    + "gastos_compras.comprobante AS comprobante,"
                    + "IFNULL(ROUND((gastos_compras.exentas * gastos_compras.cotizacion),0),0) AS totalexentas,"
                    + "IFNULL(ROUND((gastos_compras.gravadas10 * gastos_compras.cotizacion),0),0) AS totalgravadas10,"
                    + "IFNULL(ROUND((gastos_compras.gravadas5 * gastos_compras.cotizacion),0),0) AS totalgravadas5,"
                    + "IFNULL(ROUND((gastos_compras.totalneto * gastos_compras.cotizacion),0),0) AS total,"
                    + " proveedores.nombre AS nombreproveedor,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "proveedores.ruc AS ruc "
                    + "FROM gastos_compras "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=gastos_compras.sucursal "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=gastos_compras.proveedor "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=gastos_compras.comprobante "
                    + "WHERE gastos_compras.sucursal="+suc+" AND fecha BETWEEN '"+fechaini+"' AND '"+fechafin+"'"
                    + " AND comprobantes.libros=1 "
                    + " UNION ALL "
                    + "SELECT cabecera_ventas.formatofactura,cabecera_ventas.factura  AS nrofactura,"
                    + "2 AS ntipo,"
                    + "cabecera_ventas.nrotimbrado AS timbrado,"
                    + "cabecera_ventas.sucursal AS sucursal,"
                    + "cabecera_ventas.fecha AS fecha,"
                    + "cabecera_ventas.cliente   AS proveedor,"
                    + "cabecera_ventas.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_ventas.exentas * cabecera_ventas.cotizacion),0),0) AS totalexentas,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion),0),0) AS totalgravadas10,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion),0),0) AS totalgravadas5,"
                    + "IFNULL(ROUND((cabecera_ventas.totalneto * cabecera_ventas.cotizacion),0),0) AS total,"
                    + "clientes.nombre AS nombreproveedor,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "clientes.ruc AS ruc "
                    + "FROM cabecera_ventas "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_ventas.sucursal "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cabecera_ventas.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                    + "WHERE cabecera_ventas.sucursal="+suc+" AND fecha BETWEEN '"+fechaini+"' AND '"+fechafin+"'"
                    + " AND comprobantes.libros=1 "
                    + " AND cabecera_ventas.totalneto<0"
                    + " ORDER BY ntipo,comprobante,fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    proveedor proveedor = new proveedor();
                    cabecera_compra com = new cabecera_compra();
                    com.setSucursal(sucursal);
                    com.setComprobante(comprobante);
                    com.setProveedor(proveedor);
                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.setFormatofactura(rs.getString("formatofactura"));
                    com.getSucursal().setCodigo(rs.getInt("sucursal"));
                    com.setFecha(rs.getDate("fecha"));
                    com.getProveedor().setCodigo(rs.getInt("proveedor"));
                    com.getComprobante().setCodigo(rs.getInt("comprobante"));
                    com.setExentas(rs.getBigDecimal("totalexentas"));
                    com.setGravadas10(rs.getBigDecimal("totalgravadas10"));
                    com.setGravadas5(rs.getBigDecimal("totalgravadas5"));
                    com.setTotalneto(rs.getBigDecimal("total"));
                    com.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    com.getProveedor().setRuc(rs.getString("ruc"));
                    com.getSucursal().setNombre(rs.getString("nombresucursal"));
                    com.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    com.setTimbrado(rs.getInt("timbrado"));
                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cabecera_compra> librocompraxproveedor(int proini, Date fechaini, Date fechafin, int profin, Date fechagastoini, Date fechagastofin) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cabecera_compras.formatofactura,cabecera_compras.nrofactura  AS nrofactura,"
                    + "cabecera_compras.timbrado AS timbrado,"
                    + "cabecera_compras.sucursal AS sucursal,"
                    + "cabecera_compras.fecha AS fecha,"
                    + "cabecera_compras.proveedor   AS proveedor,"
                    + "cabecera_compras.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_compras.exentas * cabecera_compras.cotizacion),0),0) AS totalexentas,"
                    + "IFNULL(ROUND((cabecera_compras.gravadas10 * cabecera_compras.cotizacion),0),0) AS totalgravadas10,"
                    + "IFNULL(ROUND((cabecera_compras.gravadas5 * cabecera_compras.cotizacion),0),0) AS totalgravadas5,"
                    + "IFNULL(ROUND((cabecera_compras.totalneto * cabecera_compras.cotizacion),0),0) AS total,"
                    + "proveedores.nombre AS nombreproveedor,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "proveedores.ruc AS ruc "
                    + "FROM cabecera_compras "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_compras.sucursal "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=cabecera_compras.proveedor "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_compras.comprobante "
                    + "WHERE cabecera_compras.proveedor=? AND fecha BETWEEN ? AND ? "
                    + " AND comprobantes.libros=1 "
                    + " UNION ALL "
                    + "SELECT gastos_compras.formatofactura,gastos_compras.nrofactura  AS nrofactura, "
                    + "gastos_compras.timbrado AS timbrado,"
                    + "gastos_compras.sucursal AS sucursal, "
                    + "gastos_compras.fecha AS fecha, "
                    + "gastos_compras.proveedor AS proveedor, "
                    + "gastos_compras.comprobante AS comprobante,"
                    + "IFNULL(ROUND((gastos_compras.exentas * gastos_compras.cotizacion),0),0) AS totalexentas,"
                    + "IFNULL(ROUND((gastos_compras.gravadas10 * gastos_compras.cotizacion),0),0) AS totalgravadas10,"
                    + "IFNULL(ROUND((gastos_compras.gravadas5 * gastos_compras.cotizacion),0),0) AS totalgravadas5,"
                    + "IFNULL(ROUND((gastos_compras.totalneto * gastos_compras.cotizacion),0),0) AS total,"
                    + " proveedores.nombre AS nombreproveedor,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "proveedores.ruc AS ruc "
                    + "FROM gastos_compras "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=gastos_compras.sucursal "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=gastos_compras.proveedor "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=gastos_compras.comprobante "
                    + "WHERE gastos_compras.proveedor=? AND fecha BETWEEN ? AND ? "
                    + " AND comprobantes.libros=1 "
                    + "ORDER BY comprobante,fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, proini);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);
                ps.setInt(4, profin);
                ps.setDate(5, fechagastoini);
                ps.setDate(6, fechagastofin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    proveedor proveedor = new proveedor();
                    cabecera_compra com = new cabecera_compra();
                    com.setSucursal(sucursal);
                    com.setComprobante(comprobante);
                    com.setProveedor(proveedor);
                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.setFormatofactura(rs.getString("formatofactura"));
                    com.getSucursal().setCodigo(rs.getInt("sucursal"));
                    com.setFecha(rs.getDate("fecha"));
                    com.getProveedor().setCodigo(rs.getInt("proveedor"));
                    com.getComprobante().setCodigo(rs.getInt("comprobante"));
                    com.setExentas(rs.getBigDecimal("totalexentas"));
                    com.setGravadas10(rs.getBigDecimal("totalgravadas10"));
                    com.setGravadas5(rs.getBigDecimal("totalgravadas5"));
                    com.setTotalneto(rs.getBigDecimal("total"));
                    com.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    com.getProveedor().setRuc(rs.getString("ruc"));
                    com.getSucursal().setNombre(rs.getString("nombresucursal"));
                    com.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    com.setTimbrado(rs.getInt("timbrado"));
                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cabecera_compra> ResumenCompraxSucursal(Date fechaini, Date fechafin, int nSucursal, int nSuc, int nMoneda) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT sucursal,codprod,nombreproducto,SUM(cantidad) as tcantidad,AVG(prcosto) as promedio,"
                    + " prcosto,SUM(monto) as totalventa,nombrerubro,codigo,moneda,nombremoneda,nombresucursal,fecha"
                    + " FROM vista_detalle_compras "
                    + " WHERE vista_detalle_compras.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_compras.sucursal=?,TRUE) "
                    + " AND vista_detalle_compras.moneda=? "
                    + " GROUP BY sucursal,codprod "
                    + " ORDER BY sucursal,codprod";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    moneda moneda = new moneda();

                    cabecera_compra com = new cabecera_compra();

                    com.setSucursal(sucursal);
                    com.setMoneda(moneda);

                    com.setCodprodcompra(rs.getString("codprod"));
                    com.setNombreproductocompra(rs.getString("nombreproducto"));
                    com.setCantidadcompra(rs.getBigDecimal("tcantidad"));
                    com.setPromedio(rs.getBigDecimal("promedio"));
                    com.setPreciocompra(rs.getBigDecimal("prcosto"));
                    com.setTotalneto(rs.getBigDecimal("totalventa"));
                    com.setCodrubrocompra(rs.getInt("codigo"));
                    com.setNombrerubrocompra(rs.getString("nombrerubro"));

                    com.getSucursal().setCodigo(rs.getInt("sucursal"));
                    com.getSucursal().setNombre(rs.getString("nombresucursal"));

                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));
                    com.setFecha(rs.getDate("fecha"));

                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cabecera_compra> ResumenCompraxRubro(Date fechaini, Date fechafin, int nRubro, int nRu, int nMoneda) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT sucursal,codprod,nombreproducto,SUM(cantidad) as tcantidad,AVG(prcosto) as promedio,"
                    + " prcosto,SUM(monto) as totalventa,codigo,nombrerubro,moneda,nombremoneda,nombresucursal,fecha"
                    + " FROM vista_detalle_compras "
                    + " WHERE vista_detalle_compras.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_compras.codigo=?,TRUE) "
                    + " AND vista_detalle_compras.moneda=? "
                    + " GROUP BY codigo,codprod "
                    + " ORDER BY codigo,codprod ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nRubro);
                ps.setInt(4, nRu);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    moneda moneda = new moneda();

                    cabecera_compra com = new cabecera_compra();
                    com.setMoneda(moneda);

                    com.setCodprodcompra(rs.getString("codprod"));
                    com.setNombreproductocompra(rs.getString("nombreproducto"));
                    com.setCantidadcompra(rs.getBigDecimal("tcantidad"));
                    com.setPromedio(rs.getBigDecimal("promedio"));
                    com.setPreciocompra(rs.getBigDecimal("prcosto"));

                    com.setTotalneto(rs.getBigDecimal("totalventa"));
                    com.setCodrubrocompra(rs.getInt("codigo"));
                    com.setNombrerubrocompra(rs.getString("nombrerubro"));

                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));
                    com.setFecha(rs.getDate("fecha"));

                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cabecera_compra> ResumenCompraxProveedor(Date fechaini, Date fechafin, int nProveedor, int nPro, int nMoneda) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT sucursal,codprod,nombreproducto,SUM(cantidad) as tcantidad,AVG(prcosto) as promedio,"
                    + " prcosto,SUM(monto) as totalventa,nombrerubro,codigo,moneda,nombremoneda,nombresucursal,proveedor,nombreproveedor,fecha"
                    + " FROM vista_detalle_compras "
                    + " WHERE vista_detalle_compras.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_compras.proveedor=?,TRUE) "
                    + " AND vista_detalle_compras.moneda=? "
                    + " GROUP BY proveedor,codprod "
                    + " ORDER BY proveedor,codprod ";

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

                    cabecera_compra com = new cabecera_compra();

                    com.setProveedor(proveedor);
                    com.setMoneda(moneda);

                    com.setCodprodcompra(rs.getString("codprod"));
                    com.setNombreproductocompra(rs.getString("nombreproducto"));
                    com.setCantidadcompra(rs.getBigDecimal("tcantidad"));
                    com.setPromedio(rs.getBigDecimal("promedio"));
                    com.setPreciocompra(rs.getBigDecimal("prcosto"));
                    com.setTotalneto(rs.getBigDecimal("totalventa"));
                    com.setCodrubrocompra(rs.getInt("codigo"));
                    com.setNombrerubrocompra(rs.getString("nombrerubro"));

                    com.getProveedor().setCodigo(rs.getInt("proveedor"));
                    com.getProveedor().setNombre(rs.getString("nombreproveedor"));

                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));
                    com.setFecha(rs.getDate("fecha"));

                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cabecera_compra> ResumenCompraxMarca(Date fechaini, Date fechafin, int nMarca, int nMc, int nMoneda) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT sucursal,codprod,nombreproducto,SUM(cantidad) as tcantidad,AVG(prcosto) as promedio,"
                    + " prcosto,SUM(monto) as totalventa,codigo,nombrerubro,moneda,nombremoneda,nombresucursal,marca,nombremarca,fecha"
                    + " FROM vista_detalle_compras "
                    + " WHERE vista_detalle_compras.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_compras.marca=?,TRUE) "
                    + " AND vista_detalle_compras.moneda=? "
                    + " GROUP BY marca,codprod "
                    + " ORDER BY marca,codprod ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nMarca);
                ps.setInt(4, nMc);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    marca marca = new marca();
                    moneda moneda = new moneda();

                    cabecera_compra com = new cabecera_compra();

                    com.setMarca(marca);
                    com.setMoneda(moneda);

                    com.setCodprodcompra(rs.getString("codprod"));
                    com.setNombreproductocompra(rs.getString("nombreproducto"));
                    com.setCantidadcompra(rs.getBigDecimal("tcantidad"));
                    com.setPromedio(rs.getBigDecimal("promedio"));
                    com.setPreciocompra(rs.getBigDecimal("prcosto"));
                    com.setTotalneto(rs.getBigDecimal("totalventa"));
                    com.setCodrubrocompra(rs.getInt("codigo"));
                    com.setNombrerubrocompra(rs.getString("nombrerubro"));

                    com.getMarca().setCodigo(rs.getInt("marca"));
                    com.getMarca().setNombre(rs.getString("nombremarca"));

                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));
                    com.setFecha(rs.getDate("fecha"));

                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cabecera_compra> DetalleCompraxSucursal(Date fechaini, Date fechafin, int nSucursal, int nSuc, int nMoneda) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT codigo,nrofactura,codprod,nombreproducto,cantidad,prcosto,monto,codigo,nombrerubro, "
                    + "fecha,sucursal,nombresucursal,moneda,nombremoneda, nombreproveedor "
                    + " FROM vista_detalle_compras "
                    + " WHERE vista_detalle_compras.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_compras.sucursal=?,TRUE) "
                    + " AND vista_detalle_compras.moneda=? "
                    + " ORDER BY vista_detalle_compras.codigo,vista_detalle_compras.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    moneda moneda = new moneda();

                    cabecera_compra com = new cabecera_compra();

                    com.setSucursal(sucursal);
                    com.setMoneda(moneda);

                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.setCodprodcompra(rs.getString("codprod"));
                    com.setNombreproductocompra(rs.getString("nombreproducto"));
                    com.setCantidadcompra(rs.getBigDecimal("cantidad"));
                    com.setPreciocompra(rs.getBigDecimal("prcosto"));
                    com.setTotalneto(rs.getBigDecimal("monto"));
                    com.setCodrubrocompra(rs.getInt("codigo"));
                    com.setNombrerubrocompra(rs.getString("nombrerubro"));

                    com.setFecha(rs.getDate("fecha"));
                    com.getSucursal().setCodigo(rs.getInt("sucursal"));
                    com.getSucursal().setNombre(rs.getString("nombresucursal"));

                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));

                    com.setNombreproveedor(rs.getString("nombreproveedor"));

                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cabecera_compra> DetalleCompraxRubro(Date fechaini, Date fechafin, int nRubro, int nRu, int nMoneda) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT codigo,nrofactura,codprod,nombreproducto,cantidad,prcosto,monto, "
                    + "fecha,codigo,nombrerubro,moneda,nombremoneda,nombreproveedor "
                    + " FROM vista_detalle_compras "
                    + " WHERE vista_detalle_compras.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_compras.codigo=?,TRUE) "
                    + " AND vista_detalle_compras.moneda=? "
                    + " ORDER BY vista_detalle_compras.codigo,vista_detalle_compras.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nRubro);
                ps.setInt(4, nRu);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    rubro rubro = new rubro();
                    moneda moneda = new moneda();

                    cabecera_compra com = new cabecera_compra();

                    com.setRubro(rubro);
                    com.setMoneda(moneda);

                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.setCodprodcompra(rs.getString("codprod"));
                    com.setNombreproductocompra(rs.getString("nombreproducto"));
                    com.setCantidadcompra(rs.getBigDecimal("cantidad"));
                    com.setPreciocompra(rs.getBigDecimal("prcosto"));
                    com.setTotalneto(rs.getBigDecimal("monto"));
                    com.setCodrubrocompra(rs.getInt("codigo"));
                    com.setNombrerubrocompra(rs.getString("nombrerubro"));

                    com.setFecha(rs.getDate("fecha"));
                    com.getRubro().setCodigo(rs.getInt("codigo"));
                    com.getRubro().setNombre(rs.getString("nombrerubro"));

                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));

                    com.setNombreproveedor(rs.getString("nombreproveedor"));

                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cabecera_compra> DetalleCompraxProveedor(Date fechaini, Date fechafin, int nProveedor, int nPro, int nMoneda) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT codigo,nrofactura,codprod,nombreproducto,cantidad,prcosto,monto,codigo,nombrerubro, "
                    + "fecha,proveedor,nombreproveedor,moneda,nombremoneda,nombresucursal "
                    + " FROM vista_detalle_compras "
                    + " WHERE vista_detalle_compras.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_compras.proveedor=?,TRUE) "
                    + " AND vista_detalle_compras.moneda=? "
                    + " ORDER BY vista_detalle_compras.proveedor,vista_detalle_compras.nrofactura";

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

                    cabecera_compra com = new cabecera_compra();

                    com.setProveedor(proveedor);
                    com.setMoneda(moneda);

                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.setCodprodcompra(rs.getString("codprod"));
                    com.setNombreproductocompra(rs.getString("nombreproducto"));
                    com.setCantidadcompra(rs.getBigDecimal("cantidad"));
                    com.setPreciocompra(rs.getBigDecimal("prcosto"));
                    com.setTotalneto(rs.getBigDecimal("monto"));
                    com.setCodrubrocompra(rs.getInt("codigo"));
                    com.setNombrerubrocompra(rs.getString("nombrerubro"));

                    com.setFecha(rs.getDate("fecha"));
                    com.getProveedor().setCodigo(rs.getInt("proveedor"));
                    com.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    com.setNombresucursal(rs.getString("nombresucursal"));

                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));

                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cabecera_compra> DetalleCompraxMarca(Date fechaini, Date fechafin, int nMarca, int nMar, int nMoneda) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT codigo,nrofactura,codprod,nombreproducto,cantidad,prcosto,monto,codigo,nombrerubro, "
                    + "fecha,marca,nombremarca,moneda,nombremoneda,nombreproveedor "
                    + " FROM vista_detalle_compras "
                    + " WHERE vista_detalle_compras.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_compras.marca=?,TRUE) "
                    + " AND vista_detalle_compras.moneda=? "
                    + " ORDER BY vista_detalle_compras.codigo,vista_detalle_compras.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nMarca);
                ps.setInt(4, nMar);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    marca marca = new marca();
                    moneda moneda = new moneda();

                    cabecera_compra com = new cabecera_compra();

                    com.setMarca(marca);
                    com.setMoneda(moneda);

                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.setCodprodcompra(rs.getString("codprod"));
                    com.setNombreproductocompra(rs.getString("nombreproducto"));
                    com.setCantidadcompra(rs.getBigDecimal("cantidad"));
                    com.setPreciocompra(rs.getBigDecimal("prcosto"));
                    com.setTotalneto(rs.getBigDecimal("monto"));
                    com.setCodrubrocompra(rs.getInt("codigo"));
                    com.setNombrerubrocompra(rs.getString("nombrerubro"));

                    com.setFecha(rs.getDate("fecha"));
                    com.getMarca().setCodigo(rs.getInt("marca"));
                    com.getMarca().setNombre(rs.getString("nombremarca"));

                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));

                    com.setNombreproveedor(rs.getString("nombreproveedor"));

                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cabecera_compra> DetalleCompraxProducto(Date fechaini, Date fechafin, String nProducto, int nMoneda) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT codigo,nrofactura,codprod,nombreproducto,cantidad,prcosto,monto,codigo,nombrerubro, "
                    + "fecha,marca,nombremarca,moneda,nombremoneda,proveedor,nombreproveedor"
                    + " FROM vista_detalle_compras "
                    + " WHERE vista_detalle_compras.fecha between '" + fechaini + "' AND '" + fechafin + "'";
            if (!nProducto.isEmpty()) {
                cSql = cSql + " AND vista_detalle_compras.codprod='" + nProducto + "'";
            }
            cSql = cSql + " AND vista_detalle_compras.moneda= " + nMoneda;
            cSql = cSql + " ORDER BY vista_detalle_compras.codigo,vista_detalle_compras.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                System.out.println(cSql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    moneda moneda = new moneda();
                    proveedor proveedor = new proveedor();

                    cabecera_compra com = new cabecera_compra();

                    com.setProveedor(proveedor);
                    com.setMoneda(moneda);

                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.setCodprodcompra(rs.getString("codprod"));
                    com.setNombreproductocompra(rs.getString("nombreproducto"));
                    com.setCantidadcompra(rs.getBigDecimal("cantidad"));
                    com.setPreciocompra(rs.getBigDecimal("prcosto"));
                    com.setTotalneto(rs.getBigDecimal("monto"));
                    com.setCodrubrocompra(rs.getInt("codigo"));
                    com.setNombrerubrocompra(rs.getString("nombrerubro"));

                    com.setFecha(rs.getDate("fecha"));

                    com.getProveedor().setCodigo(rs.getInt("proveedor"));
                    com.getProveedor().setNombre(rs.getString("nombreproveedor"));

                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));

                    com.setCodmarca(rs.getInt("marca"));
                    com.setNombremarca(rs.getString("nombremarca"));

                    lista.add(com);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public cabecera_compra buscarCompraServer(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        cabecera_compra com = new cabecera_compra();

        try {

            String sql = "SELECT cabecera_compras.creferencia "
                    + " FROM cabecera_compras "
                    + " WHERE cabecera_compras.creferencia= ? "
                    + " ORDER BY cabecera_compras.creferencia ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    com.setCreferencia(rs.getString("creferencia"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return com;
    }

    public cabecera_compra AgregarCompraRemota(cabecera_compra c) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_compras (creferencia,nrofactura,sucursal,fecha,proveedor,exentas,gravadas10,gravadas5,totalneto,timbrado,vencetimbrado,moneda,cotizacion,observacion,primer_vence,comprobante,pagos,financiado,cuotas,usuarioalta,fechaalta) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getCreferencia());
        ps.setDouble(2, c.getNrofactura());
        ps.setInt(3, c.getSucursal().getCodigo());
        ps.setDate(4, c.getFecha());
        ps.setInt(5, c.getProveedor().getCodigo());
        ps.setBigDecimal(6, c.getExentas());
        ps.setBigDecimal(7, c.getGravadas10());
        ps.setBigDecimal(8, c.getGravadas5());
        ps.setBigDecimal(9, c.getTotalneto());
        ps.setInt(10, c.getTimbrado());
        ps.setDate(11, c.getVencetimbrado());
        ps.setInt(12, c.getMoneda().getCodigo());
        ps.setBigDecimal(13, c.getCotizacion());
        ps.setString(14, c.getObservacion());
        ps.setDate(15, c.getPrimer_vence());
        ps.setInt(16, c.getComprobante().getCodigo());
        ps.setBigDecimal(17, c.getPagos());
        ps.setBigDecimal(18, c.getFinanciado());
        ps.setInt(19, c.getCuotas());
        ps.setInt(20, c.getUsuarioalta());
        ps.setDate(21, c.getFechaalta());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return c;
    }

    public boolean guardarItemCompraRemota(String detalle) throws SQLException {
        boolean guardadetalle = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectadetalle = st.getConnection();
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalle);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO detalle_compras("
                            + "dreferencia,"
                            + "codprod,"
                            + "cantidad,"
                            + "prcosto,"
                            + "monto,"
                            + "impiva,"
                            + "porcentaje,"
                            + "suc"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("dreferencia").getAsString());
                        ps.setString(2, obj.get("codprod").getAsString());
                        ps.setString(3, obj.get("cantidad").getAsString());
                        ps.setString(4, obj.get("prcosto").getAsString());
                        ps.setString(5, obj.get("monto").getAsString());
                        ps.setString(6, obj.get("impiva").getAsString());
                        ps.setString(7, obj.get("porcentaje").getAsString());
                        ps.setString(8, obj.get("suc").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardadetalle = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardadetalle = false;
                    break;
                }
            }

        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardadetalle = false;
        }
        conectadetalle.close();
        return guardadetalle;
    }

    public cabecera_compra AgregarNotaCreditoCompra(cabecera_compra c, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_compras (creferencia,nrofactura,sucursal,"
                + "fecha,proveedor,exentas,gravadas10,gravadas5,totalneto,timbrado,vencetimbrado,"
                + "moneda,cotizacion,observacion,primer_vence,comprobante,pagos,"
                + "financiado,cuotas,usuarioalta,fechaalta,formatofactura)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getCreferencia());
        ps.setDouble(2, c.getNrofactura());
        ps.setInt(3, c.getSucursal().getCodigo());
        ps.setDate(4, c.getFecha());
        ps.setInt(5, c.getProveedor().getCodigo());
        ps.setBigDecimal(6, c.getExentas());
        ps.setBigDecimal(7, c.getGravadas10());
        ps.setBigDecimal(8, c.getGravadas5());
        ps.setBigDecimal(9, c.getTotalneto());
        ps.setInt(10, c.getTimbrado());
        ps.setDate(11, c.getVencetimbrado());
        ps.setInt(12, c.getMoneda().getCodigo());
        ps.setBigDecimal(13, c.getCotizacion());
        ps.setString(14, c.getObservacion());
        ps.setDate(15, c.getPrimer_vence());
        ps.setInt(16, c.getComprobante().getCodigo());
        ps.setBigDecimal(17, c.getPagos());
        ps.setBigDecimal(18, c.getFinanciado());
        ps.setInt(19, c.getCuotas());
        ps.setInt(20, c.getUsuarioalta());
        ps.setDate(21, c.getFechaalta());
        ps.setString(22, c.getFormatofactura());
        ps.executeUpdate();
        guardarItemNotaCredito(c.getCreferencia(), detalle, con);
        st.close();
        ps.close();
        conn.close();
        return c;
    }

    public cabecera_compra ActualizarNotaCreditoCompra(cabecera_compra c, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  cabecera_compras SET creferencia=?,nrofactura=?,"
                + "sucursal=?,fecha=?,proveedor=?,exentas=?,gravadas10=?,"
                + "gravadas5=?,totalneto=?,timbrado=?,vencetimbrado=?,"
                + "moneda=?,cotizacion=?,observacion=?,"
                + "primer_vence=?,comprobante=?,pagos=?,"
                + "financiado=?,cuotas=?,usuarioalta=?,fechaalta=?,formatofactura=? WHERE creferencia= '" + c.getCreferencia() + "'");
        ps.setString(1, c.getCreferencia());
        ps.setDouble(2, c.getNrofactura());
        ps.setInt(3, c.getSucursal().getCodigo());
        ps.setDate(4, c.getFecha());
        ps.setInt(5, c.getProveedor().getCodigo());
        ps.setBigDecimal(6, c.getExentas());
        ps.setBigDecimal(7, c.getGravadas10());
        ps.setBigDecimal(8, c.getGravadas5());
        ps.setBigDecimal(9, c.getTotalneto());
        ps.setInt(10, c.getTimbrado());
        ps.setDate(11, c.getVencetimbrado());
        ps.setInt(12, c.getMoneda().getCodigo());
        ps.setBigDecimal(13, c.getCotizacion());
        ps.setString(14, c.getObservacion());
        ps.setDate(15, c.getPrimer_vence());
        ps.setInt(16, c.getComprobante().getCodigo());
        ps.setBigDecimal(17, c.getPagos());
        ps.setBigDecimal(18, c.getFinanciado());
        ps.setInt(19, c.getCuotas());
        ps.setInt(20, c.getUsuarioalta());
        ps.setDate(21, c.getFechaalta());
        ps.setString(22, c.getFormatofactura());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemNotaCredito(c.getCreferencia(), detalle, con);
        }
        st.close();
        ps.close();
        return c;
    }

    public boolean guardarItemNotaCredito(String id, String detalle, Conexion conexion) throws SQLException {
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
                    String sql = "INSERT INTO detalle_compras("
                            + "dreferencia,"
                            + "codprod,"
                            + "cantidad,"
                            + "prcosto,"
                            + "monto,"
                            + "impiva,"
                            + "porcentaje,"
                            + "suc"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("codprod").getAsString());
                        ps.setString(3, obj.get("cantidad").getAsString());
                        ps.setString(4, obj.get("prcosto").getAsString());
                        ps.setString(5, obj.get("monto").getAsString());
                        ps.setString(6, obj.get("impiva").getAsString());
                        ps.setString(7, obj.get("porcentaje").getAsString());
                        ps.setString(8, obj.get("suc").getAsString());
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
        //  conn.close();
        return guardado;

    }

    public cabecera_compra buscarIdReferencia(String id, int proveedor) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        cabecera_compra vta = new cabecera_compra();

        try {

            String sql = "SELECT creferencia,cabecera_compras.nrofactura,timbrado,"
                    + "cabecera_compras.formatofactura "
                    + " FROM cabecera_compras "
                    + " WHERE cabecera_compras.formatofactura='" + id + "'"
                    + " AND cabecera_compras.proveedor=" + proveedor
                    + " ORDER BY cabecera_compras.formatofactura ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    vta.setCreferencia(rs.getString("creferencia"));
                    vta.setNrofactura(rs.getDouble("nrofactura"));
                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setTimbrado(rs.getInt("timbrado"));
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return vta;
    }

    public ArrayList<cabecera_compra> librocomprares90Excel(int suc, Date fechaini, Date fechafin) throws SQLException, IOException {
        String nombrearchivo = "";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            nombrearchivo = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(nombrearchivo);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sqlregistro = "CREATE TEMPORARY TABLE compraresolucion ("
                + "codigoregistro INT(1) DEFAULT 2,"
                + "codigoidentidicadorcomprador INT(2) DEFAULT 11,"
                + "numeroidentifacion CHAR(20),"
                + "razonsocial CHAR(250),"
                + "codigocomprobante int(3),"
                + "fechaemision CHAR(10),"
                + "timbrado DECIMAL(8,0) DEFAULT 0,"
                + "numerocomprobante CHAR(20),"
                + "montogravado10 DECIMAL (20,0) DEFAULT 0,"
                + "montogravado5 DECIMAL (20,0) DEFAULT 0,"
                + "montoexento DECIMAL (20,0) DEFAULT 0,"
                + "montototal DECIMAL (20,0) DEFAULT 0,"
                + "condicionventa INT(1) DEFAULT 1,"
                + "monedaextranjera CHAR(1) DEFAULT 'N',"
                + "imputaaliva CHAR(1) DEFAULT 'S',"
                + "imputaalire CHAR(1) DEFAULT 'N',"
                + "imputaalirprsp CHAR(1) DEFAULT 'N',"
                + "noimputa CHAR(1) DEFAULT 'N',"
                + "comprobanteasociado CHAR(20)DEFAULT '',"
                + "timbradoasociado  DECIMAL(8,0) DEFAULT 0)";

        PreparedStatement psregistro = conn.prepareStatement(sqlregistro);
        psregistro.executeUpdate(sqlregistro);

        String sqlGrabar = "INSERT INTO compraresolucion"
                + "(numeroidentifacion,razonsocial,codigocomprobante,fechaemision,timbrado,numerocomprobante,"
                + "montogravado10,montogravado5,montoexento,montototal,condicionventa) "
                + " SELECT SUBSTRING(proveedores.ruc FROM 1 FOR CHAR_LENGTH(proveedores.ruc) - 2) AS r, "
                + "proveedores.nombre, "
                + "CASE "
                + " WHEN cabecera_compras.totalneto>0 THEN 109 "
                + " WHEN cabecera_compras.totalneto<0 THEN 110 "
                + " END AS codigocomprobante, "
                + " DATE_FORMAT(cabecera_compras.fecha, '%d/%m/%Y') AS fecha,"
                + " cabecera_compras.timbrado,"
                + " cabecera_compras.formatofactura,"
                + " cabecera_compras.gravadas10,"
                + " cabecera_compras.gravadas5,"
                + " cabecera_compras.exentas,"
                + " cabecera_compras.totalneto, "
                + " CASE "
                + " WHEN cabecera_compras.comprobante=1 AND cabecera_compras.totalneto>0 THEN 1 "
                + " WHEN cabecera_compras.comprobante<>1 AND cabecera_compras.totalneto>0 THEN 2 "
                + " WHEN cabecera_compras.comprobante<>1 AND cabecera_compras.totalneto<1 THEN 1 "
                + " END AS condicionventa "
                + " FROM cabecera_compras "
                + " LEFT JOIN proveedores "
                + " ON proveedores.codigo=cabecera_compras.proveedor "
                + " LEFT JOIN comprobantes "
                + " ON comprobantes.codigo=cabecera_compras.comprobante "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cabecera_compras.sucursal=" + suc
                + " AND comprobantes.libros=1 "
                + " UNION ALL "
                + " SELECT SUBSTRING(proveedores.ruc FROM 1 FOR CHAR_LENGTH(proveedores.ruc) - 2) AS r, "
                + "proveedores.nombre,"
                + " CASE "
                + " WHEN gastos_compras.totalneto>0 THEN 109 "
                + " WHEN gastos_compras.totalneto<0 THEN 110 "
                + " END AS codigocomprobante, "
                + " DATE_FORMAT(gastos_compras.fecha, '%d/%m/%Y') AS fecha,"
                + " gastos_compras.timbrado,"
                + " gastos_compras.formatofactura,"
                + " gastos_compras.gravadas10,"
                + " gastos_compras.gravadas5,"
                + " gastos_compras.exentas,"
                + " gastos_compras.totalneto, "
                + " CASE "
                + " WHEN gastos_compras.comprobante=1 AND gastos_compras.totalneto>0 THEN 1 "
                + " WHEN gastos_compras.comprobante<>1 AND gastos_compras.totalneto>0 THEN 2 "
                + " WHEN gastos_compras.comprobante<>1 AND gastos_compras.totalneto<1 THEN 1 "
                + " END AS condicionventa "
                + " FROM gastos_compras "
                + " LEFT JOIN proveedores "
                + " ON proveedores.codigo=gastos_compras.proveedor "
                + " LEFT JOIN comprobantes "
                + " ON comprobantes.codigo=gastos_compras.comprobante "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND gastos_compras.sucursal=" + suc
                + " AND comprobantes.libros=1  and comprobantes.codold=1 ORDER BY codigocomprobante, fecha";

        PreparedStatement psplandatos = conn.prepareStatement(sqlGrabar);
        psplandatos.executeUpdate(sqlGrabar);

        ///AGREGAR NOTA DE CREDITO DESDE VENTAS
        String sqlGrabarNotas = "INSERT INTO compraresolucion"
                + "(codigoidentidicadorcomprador,numeroidentifacion,"
                + "razonsocial,codigocomprobante,"
                + "fechaemision,timbrado,numerocomprobante,"
                + "montogravado10,montogravado5,montoexento,montototal,"
                + "condicionventa,comprobanteasociado,timbradoasociado) "
                + " SELECT CASE "
                + "WHEN cabecera_ventas.cliente=1 THEN 15 "
                + "WHEN cabecera_ventas.cliente<>1 THEN 11 "
                + "END AS codigoidentidicadorcomprador, "
                + " SUBSTRING(clientes.ruc FROM 1 FOR CHAR_LENGTH(clientes.ruc) - 2) AS r, "
                + "clientes.nombre,"
                + " CASE "
                + "WHEN cabecera_ventas.totalneto>0 THEN 109 "
                + "WHEN cabecera_ventas.totalneto<0 THEN 110 "
                + "END AS codigocomprobante, "
                + "DATE_FORMAT(cabecera_ventas.fecha, '%d/%m/%Y') AS fecha,"
                + "cabecera_ventas.nrotimbrado,"
                + "cabecera_ventas.formatofactura,"
                + "ABS(cabecera_ventas.gravadas10),"
                + "ABS(cabecera_ventas.gravadas5),"
                + "ABS(cabecera_ventas.exentas),"
                + "ABS(cabecera_ventas.totalneto), "
                + "1 AS condicionventa, "
                + " nota_credito.nrofactura,"
                + " nota_credito.timbradoasociado "
                + " FROM cabecera_ventas "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=cabecera_ventas.cliente "
                + "LEFT JOIN comprobantes "
                + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                + " LEFT JOIN nota_credito "
                + " ON nota_credito.idnotacredito=cabecera_ventas.creferencia "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cabecera_ventas.sucursal=" + suc
                + " AND cabecera_ventas.totalneto<0 "
                + " AND comprobantes.libros=1  and comprobantes.codold=1 ORDER BY comprobante,factura ";

        PreparedStatement pspnotas = conn.prepareStatement(sqlGrabarNotas);
        pspnotas.executeUpdate(sqlGrabarNotas);

        String sqlventares = "SELECT *"
                + " FROM compraresolucion ";

        PreparedStatement psventares = conn.prepareStatement(sqlventares);
        ResultSet res = psventares.executeQuery(sqlventares);

        try {

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Código Registro"); //INT
            rowhead.createCell((short) 1).setCellValue("Código Identificador Comprador"); //INT
            rowhead.createCell((short) 2).setCellValue("Número Identificación");//String
            rowhead.createCell((short) 3).setCellValue("Razón Social");//String
            rowhead.createCell((short) 4).setCellValue("Código Comprobante "); //int
            rowhead.createCell((short) 5).setCellValue("Fecha Emisión "); //String
            rowhead.createCell((short) 6).setCellValue("N° Timbrado "); //double
            rowhead.createCell((short) 7).setCellValue("N° Comprobante "); //String
            rowhead.createCell((short) 8).setCellValue("Monto Gravado 10 "); //double
            rowhead.createCell((short) 9).setCellValue("Monto Gravado 5 "); //double
            rowhead.createCell((short) 10).setCellValue("Monto Exento "); //double
            rowhead.createCell((short) 11).setCellValue("Monto Total "); //double
            rowhead.createCell((short) 12).setCellValue("Condición Venta"); //int
            rowhead.createCell((short) 13).setCellValue("Moneda Extranjera"); //String
            rowhead.createCell((short) 14).setCellValue("Imputa al IVA"); //String
            rowhead.createCell((short) 15).setCellValue("Imputa al IRE"); //String
            rowhead.createCell((short) 16).setCellValue("Imputa al IRP-RSP"); //String
            rowhead.createCell((short) 17).setCellValue("No Imputa"); //String
            rowhead.createCell((short) 18).setCellValue("Comprobante Asociado"); //String
            rowhead.createCell((short) 19).setCellValue("timbradoasociado"); //double

            int index = 1;
            while (res.next()) {

                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(res.getInt(1));
                row.createCell((short) 1).setCellValue(res.getInt(2));
                row.createCell((short) 2).setCellValue(res.getString(3));
                row.createCell((short) 3).setCellValue(res.getString(4));
                row.createCell((short) 4).setCellValue(res.getInt(5));
                row.createCell((short) 5).setCellValue(res.getString(6));
                row.createCell((short) 6).setCellValue(res.getDouble(7));
                row.createCell((short) 7).setCellValue(res.getString(8));
                row.createCell((short) 8).setCellValue(res.getDouble(9));
                row.createCell((short) 9).setCellValue(res.getDouble(10));
                row.createCell((short) 10).setCellValue(res.getDouble(11));
                row.createCell((short) 11).setCellValue(res.getDouble(12));
                row.createCell((short) 12).setCellValue(res.getInt(13));
                row.createCell((short) 13).setCellValue(res.getString(14));
                row.createCell((short) 14).setCellValue(res.getString(15));
                row.createCell((short) 15).setCellValue(res.getString(16));
                row.createCell((short) 16).setCellValue(res.getString(17));
                row.createCell((short) 17).setCellValue(res.getString(18));
                row.createCell((short) 18).setCellValue(res.getString(19));
                row.createCell((short) 19).setCellValue(res.getDouble(20));
                index++;
            }
            //FileOutputStream fileOut = new FileOutputStream("c:\\Resolucion\\excelFile.xls");
            FileOutputStream fileOut = new FileOutputStream(nombrearchivo);
            System.out.println("archivo " + nombrearchivo + " fue creado");

            wb.write(fileOut);
            fileOut.close();
            res.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }

    public ArrayList<cabecera_compra> librocomprares90ExcelConsolidado(Date fechaini, Date fechafin) throws SQLException, IOException {
        String nombrearchivo = "";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            nombrearchivo = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(nombrearchivo);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sqlregistro = "CREATE TEMPORARY TABLE compraresolucion ("
                + "codigoregistro INT(1) DEFAULT 2,"
                + "codigoidentidicadorcomprador INT(2) DEFAULT 11,"
                + "numeroidentifacion CHAR(20),"
                + "razonsocial CHAR(250),"
                + "codigocomprobante int(3),"
                + "fechaemision CHAR(10),"
                + "timbrado DECIMAL(8,0) DEFAULT 0,"
                + "numerocomprobante CHAR(20),"
                + "montogravado10 DECIMAL (20,0) DEFAULT 0,"
                + "montogravado5 DECIMAL (20,0) DEFAULT 0,"
                + "montoexento DECIMAL (20,0) DEFAULT 0,"
                + "montototal DECIMAL (20,0) DEFAULT 0,"
                + "condicionventa INT(1) DEFAULT 1,"
                + "monedaextranjera CHAR(1) DEFAULT 'N',"
                + "imputaaliva CHAR(1) DEFAULT 'S',"
                + "imputaalire CHAR(1) DEFAULT 'N',"
                + "imputaalirprsp CHAR(1) DEFAULT 'N',"
                + "noimputa CHAR(1) DEFAULT 'N',"
                + "comprobanteasociado CHAR(20)DEFAULT '',"
                + "timbradoasociado  DECIMAL(8,0) DEFAULT 0)";

        PreparedStatement psregistro = conn.prepareStatement(sqlregistro);
        psregistro.executeUpdate(sqlregistro);

        String sqlGrabar = "INSERT INTO compraresolucion"
                + "(numeroidentifacion,razonsocial,codigocomprobante,fechaemision,timbrado,numerocomprobante,"
                + "montogravado10,montogravado5,montoexento,montototal,condicionventa) "
                + " SELECT SUBSTRING(proveedores.ruc FROM 1 FOR CHAR_LENGTH(proveedores.ruc) - 2) AS r, "
                + "proveedores.nombre, "
                + "CASE "
                + " WHEN cabecera_compras.totalneto>0 THEN 109 "
                + " WHEN cabecera_compras.totalneto<0 THEN 110 "
                + " END AS codigocomprobante, "
                + " DATE_FORMAT(cabecera_compras.fecha, '%d/%m/%Y') AS fecha,"
                + " cabecera_compras.timbrado,"
                + " cabecera_compras.formatofactura,"
                + " cabecera_compras.gravadas10,"
                + " cabecera_compras.gravadas5,"
                + " cabecera_compras.exentas,"
                + " cabecera_compras.totalneto, "
                + " CASE "
                + " WHEN cabecera_compras.cuotas=0  THEN 1 "
                + " WHEN cabecera_compras.cuotas=1 THEN 2 "
                + " END AS condicionventa "
                + " FROM cabecera_compras "
                + " LEFT JOIN proveedores "
                + " ON proveedores.codigo=cabecera_compras.proveedor "
                + " LEFT JOIN comprobantes "
                + " ON comprobantes.codigo=cabecera_compras.comprobante "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cabecera_compras.totalneto>0 AND comprobantes.libros=1  and comprobantes.codold=1 "
                + " UNION ALL "
                + " SELECT SUBSTRING(proveedores.ruc FROM 1 FOR CHAR_LENGTH(proveedores.ruc) - 2) AS r, "
                + "proveedores.nombre,"
                + " CASE "
                + " WHEN gastos_compras.totalneto>0 THEN 109 "
                + " WHEN gastos_compras.totalneto<0 THEN 110 "
                + " END AS codigocomprobante, "
                + " DATE_FORMAT(gastos_compras.fecha, '%d/%m/%Y') AS fecha,"
                + " gastos_compras.timbrado,"
                + " gastos_compras.formatofactura,"
                + " gastos_compras.gravadas10,"
                + " gastos_compras.gravadas5,"
                + " gastos_compras.exentas,"
                + " gastos_compras.totalneto, "
                + " CASE "
                + " WHEN gastos_compras.comprobante=1 AND gastos_compras.totalneto>0 THEN 1 "
                + " WHEN gastos_compras.comprobante<>1 AND gastos_compras.totalneto>0 THEN 2 "
                + " WHEN gastos_compras.comprobante<>1 AND gastos_compras.totalneto<1 THEN 1 "
                + " END AS condicionventa "
                + " FROM gastos_compras "
                + " LEFT JOIN proveedores "
                + " ON proveedores.codigo=gastos_compras.proveedor "
                + " LEFT JOIN comprobantes "
                + " ON comprobantes.codigo=gastos_compras.comprobante "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND comprobantes.libros=1  and comprobantes.codold=1 ORDER BY codigocomprobante, fecha";

        PreparedStatement psplandatos = conn.prepareStatement(sqlGrabar);
        psplandatos.executeUpdate(sqlGrabar);

        ///AGREGAR NOTA DE CREDITO DESDE VENTAS
        String sqlGrabarNotas = "INSERT INTO compraresolucion"
                + "(codigoidentidicadorcomprador,numeroidentifacion,"
                + "razonsocial,codigocomprobante,"
                + "fechaemision,timbrado,numerocomprobante,"
                + "montogravado10,montogravado5,montoexento,montototal,"
                + "condicionventa,comprobanteasociado,timbradoasociado) "
                + " SELECT CASE "
                + "WHEN cabecera_ventas.cliente=1 THEN 15 "
                + "WHEN cabecera_ventas.cliente<>1 THEN 11 "
                + "END AS codigoidentidicadorcomprador, "
                + " SUBSTRING(clientes.ruc FROM 1 FOR CHAR_LENGTH(clientes.ruc) - 2) AS r, "
                + "clientes.nombre,"
                + " CASE "
                + "WHEN cabecera_ventas.totalneto>0 THEN 109 "
                + "WHEN cabecera_ventas.totalneto<0 THEN 110 "
                + "END AS codigocomprobante, "
                + "DATE_FORMAT(cabecera_ventas.fecha, '%d/%m/%Y') AS fecha,"
                + "cabecera_ventas.nrotimbrado,"
                + "cabecera_ventas.formatofactura,"
                + "ABS(cabecera_ventas.gravadas10),"
                + "ABS(cabecera_ventas.gravadas5),"
                + "ABS(cabecera_ventas.exentas),"
                + "ABS(cabecera_ventas.totalneto), "
                + "1  AS condicionventa, "
                + " nota_credito.nrofactura,"
                + " nota_credito.timbradoasociado "
                + " FROM cabecera_ventas "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=cabecera_ventas.cliente "
                + "LEFT JOIN comprobantes "
                + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                + " LEFT JOIN nota_credito "
                + " ON nota_credito.idnotacredito=cabecera_ventas.creferencia "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cabecera_ventas.totalneto<0 "
                + " AND comprobantes.libros=1 and comprobantes.codold=1 ORDER BY comprobante,factura ";

        PreparedStatement pspnotas = conn.prepareStatement(sqlGrabarNotas);
        pspnotas.executeUpdate(sqlGrabarNotas);

        String sqlventares = "SELECT *"
                + " FROM compraresolucion ";

        PreparedStatement psventares = conn.prepareStatement(sqlventares);
        ResultSet res = psventares.executeQuery(sqlventares);

        try {

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Código Registro"); //INT
            rowhead.createCell((short) 1).setCellValue("Código Identificador Comprador"); //INT
            rowhead.createCell((short) 2).setCellValue("Número Identificación");//String
            rowhead.createCell((short) 3).setCellValue("Razón Social");//String
            rowhead.createCell((short) 4).setCellValue("Código Comprobante "); //int
            rowhead.createCell((short) 5).setCellValue("Fecha Emisión "); //String
            rowhead.createCell((short) 6).setCellValue("N° Timbrado "); //double
            rowhead.createCell((short) 7).setCellValue("N° Comprobante "); //String
            rowhead.createCell((short) 8).setCellValue("Monto Gravado 10 "); //double
            rowhead.createCell((short) 9).setCellValue("Monto Gravado 5 "); //double
            rowhead.createCell((short) 10).setCellValue("Monto Exento "); //double
            rowhead.createCell((short) 11).setCellValue("Monto Total "); //double
            rowhead.createCell((short) 12).setCellValue("Condición Venta"); //int
            rowhead.createCell((short) 13).setCellValue("Moneda Extranjera"); //String
            rowhead.createCell((short) 14).setCellValue("Imputa al IVA"); //String
            rowhead.createCell((short) 15).setCellValue("Imputa al IRE"); //String
            rowhead.createCell((short) 16).setCellValue("Imputa al IRP-RSP"); //String
            rowhead.createCell((short) 17).setCellValue("No Imputa"); //String
            rowhead.createCell((short) 18).setCellValue("Comprobante Asociado"); //String
            rowhead.createCell((short) 19).setCellValue("timbradoasociado"); //double

            int index = 1;
            while (res.next()) {

                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(res.getInt(1));
                row.createCell((short) 1).setCellValue(res.getInt(2));
                row.createCell((short) 2).setCellValue(res.getString(3));
                row.createCell((short) 3).setCellValue(res.getString(4));
                row.createCell((short) 4).setCellValue(res.getInt(5));
                row.createCell((short) 5).setCellValue(res.getString(6));
                row.createCell((short) 6).setCellValue(res.getDouble(7));
                row.createCell((short) 7).setCellValue(res.getString(8));
                row.createCell((short) 8).setCellValue(res.getDouble(9));
                row.createCell((short) 9).setCellValue(res.getDouble(10));
                row.createCell((short) 10).setCellValue(res.getDouble(11));
                row.createCell((short) 11).setCellValue(res.getDouble(12));
                row.createCell((short) 12).setCellValue(res.getInt(13));
                row.createCell((short) 13).setCellValue(res.getString(14));
                row.createCell((short) 14).setCellValue(res.getString(15));
                row.createCell((short) 15).setCellValue(res.getString(16));
                row.createCell((short) 16).setCellValue(res.getString(17));
                row.createCell((short) 17).setCellValue(res.getString(18));
                row.createCell((short) 18).setCellValue(res.getString(19));
                row.createCell((short) 19).setCellValue(res.getDouble(20));
                index++;
            }
            //FileOutputStream fileOut = new FileOutputStream("c:\\Resolucion\\excelFile.xls");
            FileOutputStream fileOut = new FileOutputStream(nombrearchivo);
            System.out.println("archivo " + nombrearchivo + " fue creado");

            wb.write(fileOut);
            fileOut.close();
            res.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }

    public ArrayList<cabecera_compra> librocomprares55ExcelConsolidado(Date fechaini, Date fechafin) throws SQLException, IOException {
        String nombrearchivo = "";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            nombrearchivo = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(nombrearchivo);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sqlregistro = "CREATE TEMPORARY TABLE compraresolucion ("
                + "tipoidentificacion CHAR(15) DEFAULT 'RUC',"
                + "numeroidentifacion CHAR(20),"
                + "razonsocial CHAR(250),"
                + "tipocomprobante CHAR(15) DEFAULT 'FACTURA',"
                + "timbrado DECIMAL(8,0) DEFAULT 0,"
                + "numerocomprobante CHAR(20),"
                + "condicion CHAR(7),"
                + "fechaemision CHAR(10),"
                + "montototal DECIMAL (20,0) DEFAULT 0,"
                + "montoexento DECIMAL (20,0) DEFAULT 0,"
                + "montogravado5 DECIMAL (20,0) DEFAULT 0,"
                + "montoiva5 DECIMAL (20,0) DEFAULT 0,"
                + "montogravado10 DECIMAL (20,0) DEFAULT 0,"
                + "montoiva10 DECIMAL (20,0) DEFAULT 0,"
                + "irp CHAR(1) DEFAULT 'N',"
                + "irpsimple CHAR(1) DEFAULT 'N')";

        PreparedStatement psregistro = conn.prepareStatement(sqlregistro);
        psregistro.executeUpdate(sqlregistro);

        String sqlGrabar = "INSERT INTO compraresolucion"
                + "(numeroidentifacion,razonsocial,timbrado,numerocomprobante,condicion,fechaemision,"
                + "montototal,montoexento,montogravado5,montoiva5,montogravado10,montoiva10) "
                + " SELECT proveedores.ruc, "
                + "proveedores.nombre, "
                + "cabecera_compras.timbrado, "
                + "cabecera_compras.formatofactura, "
                + "CASE "
                + " WHEN cabecera_compras.cuotas=0 THEN 'CONTADO' "
                + " WHEN cabecera_compras.cuotas>0 THEN 'CRÉDITO' "
                + " END AS condicion, "
                + "DATE_FORMAT(cabecera_compras.fecha, '%d/%m/%Y') AS fecha,"
                + "cabecera_compras.totalneto, "
                + "cabecera_compras.exentas, "
                + "cabecera_compras.gravadas5, "
                + "ROUND(cabecera_compras.gravadas5/21) AS montoiva5,"
                + "cabecera_compras.gravadas10, "
                + "ROUND(cabecera_compras.gravadas10/11) AS montoiva10 "
                + " FROM cabecera_compras "
                + " LEFT JOIN proveedores "
                + " ON proveedores.codigo=cabecera_compras.proveedor "
                + " LEFT JOIN comprobantes "
                + " ON comprobantes.codigo=cabecera_compras.comprobante "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cabecera_compras.totalneto>0 AND comprobantes.libros=1 "
                + " UNION ALL "
                + " SELECT proveedores.ruc, "
                + "proveedores.nombre, "
                + "gastos_compras.timbrado, "
                + "gastos_compras.formatofactura, "
                + "CASE "
                + " WHEN gastos_compras.cuotas=0 THEN 'CONTADO' "
                + " WHEN gastos_compras.cuotas>1 THEN 'CRÉDITO' "
                + " END AS condicion, "
                + "DATE_FORMAT(gastos_compras.fecha, '%d/%m/%Y') AS fecha,"
                + "gastos_compras.totalneto, "
                + "gastos_compras.exentas, "
                + "gastos_compras.gravadas5, "
                + "ROUND(gastos_compras.gravadas5/21) AS montoiva5,"
                + "gastos_compras.gravadas10, "
                + "ROUND(gastos_compras.gravadas10/11) AS montoiva10 "
                + " FROM gastos_compras "
                + " LEFT JOIN proveedores "
                + " ON proveedores.codigo=gastos_compras.proveedor "
                + " LEFT JOIN comprobantes "
                + " ON comprobantes.codigo=gastos_compras.comprobante "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND comprobantes.libros=1 ORDER BY condicion, fecha";

        PreparedStatement psplandatos = conn.prepareStatement(sqlGrabar);
        psplandatos.executeUpdate(sqlGrabar);

        String sqlventares = "SELECT *"
                + " FROM compraresolucion ";

        PreparedStatement psventares = conn.prepareStatement(sqlventares);
        ResultSet res = psventares.executeQuery(sqlventares);

        try {

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Tipo Identificación"); //String
            rowhead.createCell((short) 1).setCellValue("RUC"); //String
            rowhead.createCell((short) 2).setCellValue("Razón Social"); //String
            rowhead.createCell((short) 3).setCellValue("Tipo Comprobante");//String
            rowhead.createCell((short) 4).setCellValue("Timbrado");//int
            rowhead.createCell((short) 5).setCellValue("N° Comprobante "); //String
            rowhead.createCell((short) 6).setCellValue("Condición "); //String
            rowhead.createCell((short) 7).setCellValue("Fecha Emisión"); //String
            rowhead.createCell((short) 8).setCellValue("Importe Total "); //double
            rowhead.createCell((short) 9).setCellValue("Importe Exento "); //double
            rowhead.createCell((short) 10).setCellValue("Gravadas 5% "); //double
            rowhead.createCell((short) 11).setCellValue("IVA 5% "); //double
            rowhead.createCell((short) 12).setCellValue("Gravadas 10%"); //double
            rowhead.createCell((short) 13).setCellValue("IVA 10%"); //double
            rowhead.createCell((short) 14).setCellValue("IRP"); //String
            rowhead.createCell((short) 15).setCellValue("IRP Simple"); //String

            int index = 1;
            while (res.next()) {

                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(res.getString(1));
                row.createCell((short) 1).setCellValue(res.getString(2));
                row.createCell((short) 2).setCellValue(res.getString(3));
                row.createCell((short) 3).setCellValue(res.getString(4));
                row.createCell((short) 4).setCellValue(res.getInt(5));
                row.createCell((short) 5).setCellValue(res.getString(6));
                row.createCell((short) 6).setCellValue(res.getString(7));
                row.createCell((short) 7).setCellValue(res.getString(8));
                row.createCell((short) 8).setCellValue(res.getDouble(9));
                row.createCell((short) 9).setCellValue(res.getDouble(10));
                row.createCell((short) 10).setCellValue(res.getDouble(11));
                row.createCell((short) 11).setCellValue(res.getDouble(12));
                row.createCell((short) 12).setCellValue(res.getDouble(13));
                row.createCell((short) 13).setCellValue(res.getDouble(14));
                row.createCell((short) 14).setCellValue(res.getString(15));
                row.createCell((short) 15).setCellValue(res.getString(16));
                index++;
            }
            //FileOutputStream fileOut = new FileOutputStream("c:\\Resolucion\\excelFile.xls");
            FileOutputStream fileOut = new FileOutputStream(nombrearchivo);
            System.out.println("archivo " + nombrearchivo + " fue creado");

            wb.write(fileOut);
            fileOut.close();
            res.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }

    public ArrayList<cabecera_compra> buscarIdProducto(String id) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cabecera_compras.formatofactura,"
                    + "cabecera_compras.fecha,"
                    + "proveedores.nombre AS nombreproveedor,"
                    + "detalle_compras.prcosto "
                    + "FROM cabecera_compras "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=cabecera_compras.proveedor "
                    + "LEFT JOIN detalle_compras "
                    + "ON detalle_compras.dreferencia=cabecera_compras.creferencia "
                    + "WHERE detalle_compras.codprod='" + id + "'"
                    + " ORDER BY cabecera_compras.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cabecera_compra com = new cabecera_compra();
                    proveedor proveedor = new proveedor();
                    com.setProveedor(proveedor);
                    com.setFormatofactura(rs.getString("formatofactura"));
                    com.setFecha(rs.getDate("fecha"));
                    com.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    com.setPreciocompra(rs.getBigDecimal("prcosto"));
                    lista.add(com);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;

    }



    public cabecera_compra buscarIdDuplicado(String cFormatoFactura,int nProveedor, int nComprobante ) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        cabecera_compra com = new cabecera_compra();
        try {

            String sql = "SELECT cabecera_compras.creferencia,cabecera_compras.formatofactura,"
                    + "cabecera_compras.fecha,cabecera_compras.proveedor,"
                    + "cabecera_compras.comprobante "
                    + " FROM cabecera_compras "
                    + " WHERE cabecera_compras.formatofactura= ? "
                    + " AND cabecera_compras.proveedor=? "
                    + " AND cabecera_compras.comprobante=? "
                    + " ORDER BY cabecera_compras.formatofactura ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, cFormatoFactura);
                ps.setInt(2, nProveedor);
                ps.setInt(3, nComprobante);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    com.setFormatofactura(rs.getString("formatofactura"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return com;
    }



    
    
        public cabecera_compra AgregarFacturaCompraOpel(cabecera_compra c, String detalle, String detalleformapago, String detallebanco) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        boolean guardadoforma = false;
        boolean guardabanco = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_compras (creferencia,nrofactura,sucursal,"
                + "fecha,proveedor,exentas,gravadas10,gravadas5,totalneto,timbrado,vencetimbrado,"
                + "moneda,cotizacion,observacion,primer_vence,comprobante,pagos,"
                + "financiado,cuotas,usuarioalta,fechaalta,formatofactura,ordencompra,obra)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getCreferencia());
        ps.setDouble(2, c.getNrofactura());
        ps.setInt(3, c.getSucursal().getCodigo());
        ps.setDate(4, c.getFecha());
        ps.setInt(5, c.getProveedor().getCodigo());
        ps.setBigDecimal(6, c.getExentas());
        ps.setBigDecimal(7, c.getGravadas10());
        ps.setBigDecimal(8, c.getGravadas5());
        ps.setBigDecimal(9, c.getTotalneto());
        ps.setInt(10, c.getTimbrado());
        ps.setDate(11, c.getVencetimbrado());
        ps.setInt(12, c.getMoneda().getCodigo());
        ps.setBigDecimal(13, c.getCotizacion());
        ps.setString(14, c.getObservacion());
        ps.setDate(15, c.getPrimer_vence());
        ps.setInt(16, c.getComprobante().getCodigo());
        ps.setBigDecimal(17, c.getPagos());
        ps.setBigDecimal(18, c.getFinanciado());
        ps.setInt(19, c.getCuotas());
        ps.setInt(20, c.getUsuarioalta());
        ps.setDate(21, c.getFechaalta());
        ps.setString(22, c.getFormatofactura());
        ps.setInt(23,c.getOrdencompra());
        ps.setInt(24, c.getObra());
        ps.executeUpdate();
        guardarItemFactura(c.getCreferencia(), detalle, con);
        if (c.getPagos().doubleValue() > 0) {
            guardadoforma = guardarFormaPago(c.getCreferencia(), detalleformapago, con);
            guardabanco = guardarDebitoCredito(c.getCreferencia(), detallebanco, con);
        }
        st.close();
        ps.close();
        conn.close();
        return c;
    }

    public cabecera_compra ActualizarCompraOpel(cabecera_compra c, String detalle, String detalleformapago, String detallebanco) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardacuota = false;
        boolean guardadoforma = false;
        boolean guardabanco = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  cabecera_compras SET creferencia=?,nrofactura=?,"
                + "sucursal=?,fecha=?,proveedor=?,exentas=?,gravadas10=?,gravadas5=?,totalneto=?,"
                + "timbrado=?,vencetimbrado=?,moneda=?,cotizacion=?,observacion=?,primer_vence=?,"
                + "comprobante=?,pagos=?,financiado=?,cuotas=?,usuarioalta=?,fechaalta=?,formatofactura=?,ordencompra=?,obra=?"
                + " WHERE creferencia= '" + c.getCreferencia() + "'");
        ps.setString(1, c.getCreferencia());
        ps.setDouble(2, c.getNrofactura());
        ps.setInt(3, c.getSucursal().getCodigo());
        ps.setDate(4, c.getFecha());
        ps.setInt(5, c.getProveedor().getCodigo());
        ps.setBigDecimal(6, c.getExentas());
        ps.setBigDecimal(7, c.getGravadas10());
        ps.setBigDecimal(8, c.getGravadas5());
        ps.setBigDecimal(9, c.getTotalneto());
        ps.setInt(10, c.getTimbrado());
        ps.setDate(11, c.getVencetimbrado());
        ps.setInt(12, c.getMoneda().getCodigo());
        ps.setBigDecimal(13, c.getCotizacion());
        ps.setString(14, c.getObservacion());
        ps.setDate(15, c.getPrimer_vence());
        ps.setInt(16, c.getComprobante().getCodigo());
        ps.setBigDecimal(17, c.getPagos());
        ps.setBigDecimal(18, c.getFinanciado());
        ps.setInt(19, c.getCuotas());
        ps.setInt(20, c.getUsuarioalta());
        ps.setDate(21, c.getFechaalta());
        ps.setString(22, c.getFormatofactura());
        ps.setInt(23,c.getOrdencompra());
        ps.setInt(24, c.getObra());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemFactura(c.getCreferencia(), detalle, con);
        }
        if (c.getPagos().doubleValue() > 0) {
            guardadoforma = guardarFormaPago(c.getCreferencia(), detalleformapago, con);
            guardabanco = guardarDebitoCredito(c.getCreferencia(), detallebanco, con);
        }
        st.close();
        ps.close();
        return c;
    }

    



    public cabecera_compra buscarIdOpel(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        cabecera_compra com = new cabecera_compra();
        try {

            String sql = "SELECT cabecera_compras.formatofactura,cabecera_compras.creferencia,cabecera_compras.nrofactura,cabecera_compras.sucursal,cabecera_compras.fecha,cabecera_compras.proveedor,"
                    + "cabecera_compras.exentas,cabecera_compras.gravadas10,cabecera_compras.gravadas5,cabecera_compras.totalneto,cabecera_compras.moneda,"
                    + "cabecera_compras.timbrado,cabecera_compras.vencetimbrado,cabecera_compras.cotizacion,cabecera_compras.observacion,cabecera_compras.primer_vence,"
                    + "cabecera_compras.cierre,cabecera_compras.comprobante,cabecera_compras.pagos,cabecera_compras.financiado,cabecera_compras.enviarcta,cabecera_compras.generarasiento,"
                    + "cabecera_compras.cuotas,cabecera_compras.usuarioalta,cabecera_compras.fechaalta,cabecera_compras.usuarioupdate,cabecera_compras.fechaupdate,"
                    + "cabecera_compras.tipo_gasto,cabecera_compras.retencion,cabecera_compras.importado,cabecera_compras.ordencompra,cabecera_compras.asiento,"
                    + "proveedores.nombre AS nombreproveedor,cabecera_compras.obra,obras.nombre as nombreobra,"
                    + "proveedores.ruc,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "monedas.nombre  AS nombremoneda,"
                    + "monedas.etiqueta  AS etiqueta "
                    + " FROM cabecera_compras "
                    + " LEFT JOIN proveedores "
                    + " ON proveedores.codigo=cabecera_compras.proveedor "
                    + " LEFT JOIN obras  "
                    + " ON obras.codigo=cabecera_compras.obra "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=cabecera_compras.comprobante "
                    + " LEFT JOIN sucursales  "
                    + " ON sucursales.codigo=cabecera_compras.sucursal "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=cabecera_compras.moneda"
                    + " WHERE cabecera_compras.creferencia= ? "
                    + " ORDER BY cabecera_compras.creferencia ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();
                    comprobante comprobante = new comprobante();
                    com.setObra(rs.getInt("obra"));
                    com.setNombreobra(rs.getString("nombreobra"));
                    com.setSucursal(sucursal);
                    com.setProveedor(proveedor);
                    com.setMoneda(moneda);
                    com.setComprobante(comprobante);
                    com.setCreferencia(rs.getString("creferencia"));
                    com.setFormatofactura(rs.getString("formatofactura"));
                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.getSucursal().setCodigo(rs.getInt("sucursal"));
                    com.getSucursal().setNombre(rs.getString("nombresucursal"));
                    com.setFecha(rs.getDate("fecha"));
                    com.getProveedor().setCodigo(rs.getInt("proveedor"));
                    com.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    com.getProveedor().setRuc(rs.getString("ruc"));
                    com.setExentas(rs.getBigDecimal("exentas"));
                    com.setGravadas10(rs.getBigDecimal("gravadas10"));
                    com.setGravadas5(rs.getBigDecimal("gravadas5"));
                    com.setTotalneto(rs.getBigDecimal("totalneto"));
                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setNombre(rs.getString("nombremoneda"));
                    com.getMoneda().setEtiqueta(rs.getString("etiqueta"));
                    com.setTimbrado(rs.getInt("timbrado"));
                    com.setVencetimbrado(rs.getDate("vencetimbrado"));
                    com.setCotizacion(rs.getBigDecimal("cotizacion"));
                    com.setObservacion(rs.getString("observacion"));
                    com.setPrimer_vence(rs.getDate("primer_vence"));
                    com.setCierre(rs.getInt("cierre"));
                    com.getComprobante().setCodigo(rs.getInt("comprobante"));
                    com.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    com.setPagos(rs.getBigDecimal("pagos"));
                    com.setFinanciado(rs.getBigDecimal("financiado"));
                    com.setEnviarcta(rs.getInt("enviarcta"));
                    com.setGenerarasiento(rs.getInt("generarasiento"));
                    com.setAsiento(rs.getInt("asiento"));
                    com.setCuotas(rs.getInt("cuotas"));
                    com.setUsuarioalta(rs.getInt("usuarioalta"));
                    com.setFechaalta(rs.getDate("fechaalta"));
                    com.setUsuarioupdate(rs.getInt("usuarioupdate"));
                    com.setFechaupdate(rs.getDate("fechaupdate"));
                    com.setTipo_gasto(rs.getInt("tipo_gasto"));
                    com.setRetencion(rs.getBigDecimal("retencion"));
                    com.setImportado(rs.getInt("importado"));
                    com.setOrdencompra(rs.getInt("ordencompra"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return com;

    }


    
}

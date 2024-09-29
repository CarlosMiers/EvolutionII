/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import Conexion.ConexionEspejo;
import Modelo.venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author Pc_Server
 */
public class ventaVpnDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.194";
    String ip3 = "45.180.183.194";

    public venta AgregarFacturaVentaFerremax(venta v, String detalle,Integer nsuc) throws SQLException {

        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("INSERT INTO cabecera_ventas"
                + "(creferencia,fecha,factura,"
                + "vencimiento,cliente,sucursal,"
                + "moneda,giraduria,comprobante,"
                + "cotizacion,vendedor,caja,"
                + "exentas,gravadas10,gravadas5,"
                + "totalneto,cuotas,financiado,"
                + "observacion,supago,idusuario,"
                + "preventa,vencimientotimbrado,sucambio,"
                + "nrotimbrado,turno,formatofactura,"
                + "centro) "
                + "VALUES (?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getFactura());
        ps.setDate(4, v.getVencimiento());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setInt(8, v.getGiraduria().getCodigo());
        ps.setInt(9, v.getComprobante().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getVendedor().getCodigo());
        ps.setInt(12, v.getCaja().getCodigo());
        ps.setBigDecimal(13, v.getExentas());
        ps.setBigDecimal(14, v.getGravadas10());
        ps.setBigDecimal(15, v.getGravadas5());
        ps.setBigDecimal(16, v.getTotalneto());
        ps.setInt(17, v.getCuotas());
        ps.setBigDecimal(18, v.getFinanciado());
        ps.setString(19, v.getObservacion());
        ps.setBigDecimal(20, v.getSupago());
        ps.setInt(21, v.getIdusuario());
        ps.setInt(22, v.getPreventa());
        ps.setDate(23, v.getVencimientotimbrado());
        ps.setBigDecimal(24, v.getSucambio());
        ps.setInt(25, v.getNrotimbrado());
        ps.setInt(26, v.getTurno());
        ps.setString(27, v.getFormatofactura());
        ps.setInt(28, v.getCentro());
        ps.executeUpdate();
        guardarItemFactura(v.getCreferencia(), detalle, conEsp);
        System.out.println("CABECERA VENTAS");
        stEspejo.close();
        ps.close();
        conne.close();
        return v;

    }

    public boolean guardarItemFactura(String id, String detalle, ConexionEspejo conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = stEspejo.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement psdetalle = null;

        psdetalle = stEspejo.getConnection().prepareStatement("DELETE FROM detalle_ventas WHERE dreferencia=?");
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
                    String sql = "INSERT INTO detalle_ventas("
                            + "dreferencia,"
                            + "codprod,"
                            + "prcosto,"
                            + "cantidad,"
                            + "precio,"
                            + "monto,"
                            + "impiva,"
                            + "porcentaje,"
                            + "suc"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("codprod").getAsString());
                        ps.setBigDecimal(3, obj.get("prcosto").getAsBigDecimal());
                        ps.setBigDecimal(4, obj.get("cantidad").getAsBigDecimal());
                        ps.setBigDecimal(5, obj.get("precio").getAsBigDecimal());
                        ps.setBigDecimal(6, obj.get("monto").getAsBigDecimal());
                        ps.setBigDecimal(7, obj.get("impiva").getAsBigDecimal());
                        ps.setBigDecimal(8, obj.get("porcentaje").getAsBigDecimal());
                        ps.setString(9, obj.get("suc").getAsString());
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
        conn.close();
        return guardado;
    }

}

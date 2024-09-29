/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import Conexion.ConexionEspejo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Pc_Server
 */
public class detalleformacobroVpnDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;

    String ip2 = "45.180.183.194";
    String ip3 = "45.180.183.194";

    public boolean guardarFormaPagoVpn(String detalleformapago,Integer nsuc) throws SQLException {
        boolean guardadoforma = true;
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conectaformapago = stEspejo.getConnection();
        conectaformapago.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalleformapago);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO detalle_forma_cobro("
                            + "idmovimiento,"
                            + "forma,"
                            + "banco,"
                            + "nrocheque,"
                            + "confirmacion,"
                            + "netocobrado"
                            + ") "
                            + "values(?,?,?,?,?,?)";
                    try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("idmovimiento").getAsString());
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
                    conectaformapago.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
                    conectaformapago.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardadoforma = false;
        }
        stEspejo.close();
        conectaformapago.close();
        return guardadoforma;
    }

}

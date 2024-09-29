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
public class cuentaVpnclienteDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.194";
    String ip3 = "45.180.183.194";

    public boolean guardarCuentaVpnFerremax(String detallecuota, Integer nsuc) throws SQLException {
        boolean guardacuota = true;

        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }

        Connection conectacuota = stEspejo.getConnection();
        conectacuota.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detallecuota);
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
                            + "vendedor,"
                            + "idedificio,"
                            + "importe,"
                            + "numerocuota,"
                            + "cuota,"
                            + "saldo"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddocumento").getAsString());
                        ps.setString(2, obj.get("creferencia").getAsString());
                        ps.setString(3, obj.get("documento").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("vencimiento").getAsString());
                        ps.setInt(6, obj.get("cliente").getAsInt());
                        ps.setInt(7, obj.get("sucursal").getAsInt());
                        ps.setInt(8, obj.get("moneda").getAsInt());
                        ps.setInt(9, obj.get("comprobante").getAsInt());
                        ps.setInt(10, obj.get("vendedor").getAsInt());
                        ps.setString(11, obj.get("idedificio").getAsString());
                        ps.setString(12, obj.get("importe").getAsString());
                        ps.setInt(13, obj.get("numerocuota").getAsInt());
                        ps.setInt(14, obj.get("cuota").getAsInt());
                        ps.setString(15, obj.get("saldo").getAsString());
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
                    conectacuota.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
                    conectacuota.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardacuota = false;
        }
        stEspejo.close();
        conectacuota.close();
        return guardacuota;
    }

}

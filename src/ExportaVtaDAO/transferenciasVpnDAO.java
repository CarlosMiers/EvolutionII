/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import Conexion.Conexion;
import Conexion.ConexionEspejo;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.sucursal;
import Modelo.transferencia;
import Modelo.venta;
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
public class transferenciasVpnDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public transferencia insertarmercaderia(transferencia ocr, String detalle, int nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("INSERT INTO transferencias (idtransferencia,fecha,origen,destino,tipo) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdtransferencia());
        ps.setDate(2, ocr.getFecha());
        ps.setInt(3, ocr.getOrigen());
        ps.setInt(4, ocr.getDestino());
        ps.setInt(5, ocr.getTipo().getCodigo());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarDetalle(ocr.getIdtransferencia(), detalle,  conEsp);
        }
        stEspejo.close();
        ps.close();
        return ocr;
    }

    public boolean actualizarSalida(transferencia aj, String detalle, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        boolean guardado = false;
        PreparedStatement ps = null;
        int id = 0;

        ps = stEspejo.getConnection().prepareStatement("UPDATE transferencias SET fecha=?,origen=?,destino=?,tipo=? WHERE idtransferencia='" + aj.getIdtransferencia() + "'");
        ps.setDate(1, aj.getFecha());
        ps.setInt(2, aj.getOrigen());
        ps.setInt(3, aj.getDestino());
        ps.setInt(4, aj.getTipo().getCodigo());

        int rowsUpdated = ps.executeUpdate();
        guardado = guardarDetalle(aj.getIdtransferencia(), detalle, conEsp);
        stEspejo.close();
        ps.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
    }

    public boolean guardarDetalle(String id, String detalle,  ConexionEspejo conexion) throws SQLException {

        boolean guardado = true;
        Connection conn = stEspejo.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement psdetalle = null;
        psdetalle = stEspejo.getConnection().prepareStatement("DELETE FROM detalle_transferencias WHERE dreferencia=?");
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
                    String sql = "insert into detalle_transferencias("
                            + "dreferencia,"
                            + "producto,"
                            + "cantidad,"
                            + "costo,"
                            + "suc_salida,"
                            + "suc_entrada"
                            + ") "
                            + "values(?,?,?,?,?,?)";
                    try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("dreferencia").getAsString());
                        ps.setString(2, obj.get("producto").getAsString());
                        ps.setString(3, obj.get("cantidad").getAsString());
                        ps.setString(4, obj.get("costo").getAsString());
                        ps.setString(5, obj.get("suc_salida").getAsString());
                        ps.setString(6, obj.get("suc_entrada").getAsString());
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
        stEspejo.close();
        conn.close();
        return guardado;
    }

    public boolean borrarAjustes(String referencia,Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("DELETE FROM transferencias WHERE idtransferencia=?");
        ps.setString(1, referencia);
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
}

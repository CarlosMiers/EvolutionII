/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
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
public class cuenta_segurosDAO {

    Conexion con = null;
    Statement st = null;

    public boolean guardarCuentaSegurosxGiraduria(String detallecuota) throws SQLException {

        boolean guardacuota = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectacuota = st.getConnection();
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
                    String sql = "INSERT INTO cuenta_seguros("
                            + "iddocumento,"
                            + "creferencia,"
                            + "documento,"
                            + "fecha,"
                            + "vencimiento,"
                            + "giraduria,"
                            + "cliente,"
                            + "sucursal,"
                            + "moneda,"
                            + "comprobante,"
                            + "vendedor,"
                            + "importe,"
                            + "numerocuota,"
                            + "cuota,"
                            + "saldo,"
                            + "importeseguro"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddocumento").getAsString());
                        ps.setString(2, obj.get("creferencia").getAsString());
                        ps.setString(3, obj.get("documento").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("vencimiento").getAsString());
                        ps.setInt(6, obj.get("giraduria").getAsInt());
                        ps.setInt(7, obj.get("cliente").getAsInt());
                        ps.setInt(8, obj.get("sucursal").getAsInt());
                        ps.setInt(9, obj.get("moneda").getAsInt());
                        ps.setInt(10, obj.get("comprobante").getAsInt());
                        ps.setInt(11, obj.get("vendedor").getAsInt());
                        ps.setString(12, obj.get("importe").getAsString());
                        ps.setInt(13, obj.get("numerocuota").getAsInt());
                        ps.setInt(14, obj.get("cuota").getAsInt());
                        ps.setString(15, obj.get("saldo").getAsString());
                        ps.setString(16, obj.get("importeseguro").getAsString());
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
        st.close();
        conectacuota.close();
        return guardacuota;
    }

    
    public boolean borrarSegurosxGiraduria(int giraduria, int mes, int anual, int comprobante) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuenta_seguros WHERE giraduria=? and month(vencimiento)=? and year(vencimiento)=? and comprobante=?");
        ps.setInt(1, giraduria);
        ps.setInt(2, mes);
        ps.setInt(3, anual);
        ps.setInt(4, comprobante);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.deudas;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Webmaster
 */
public class deudaDAO {

    Conexion con = null;
    Statement st = null;

    ///VERIFICA LOS PAGOS Y ANULADOS
    public ArrayList<deudas> todos() throws SQLException {
        ArrayList<deudas> lista = new ArrayList<deudas>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT iddocumento,cliente  "
                + " FROM t_deudas "
                + " WHERE estado IN ('AC','RE') AND verificado=0 ORDER BY cliente  ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                deudas ba = new deudas();
                ba.setIddocumento(rs.getString("iddocumento"));
                ba.setCliente(rs.getInt("cliente"));
                lista.add(ba);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public boolean guardarCuentaWeb(String detalle) throws SQLException {

        boolean guardacuota = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectacuota = st.getConnection();
        conectacuota.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalle);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO t_deudas ("
                            + "cedula_ruc,"
                            + "cliente,"
                            + "nombre_apellido,"
                            + "numero_operacion,"
                            + "descripcion_operacion,"
                            + "numero_factura,"
                            + "nro_cuota,"
                            + "moneda,"
                            + "monto,"
                            + "fecha_venc,"
                            + "estado,"
                            + "iddocumento,"
                            + "creferencia,"
                            + "gastos,"
                            + "mora,"
                            + "punitorio,"
                            + "tipoop)"
                            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("cedula_ruc").getAsString());
                        ps.setString(2, obj.get("cliente").getAsString());
                        ps.setString(3, obj.get("nombre_apellido").getAsString());
                        ps.setString(4, obj.get("numero_operacion").getAsString());
                        ps.setString(5, obj.get("descripcion_operacion").getAsString());
                        ps.setString(6, obj.get("numero_factura").getAsString());
                        ps.setString(7, obj.get("nro_cuota").getAsString());
                        ps.setString(8, obj.get("moneda").getAsString());
                        ps.setString(9, obj.get("monto").getAsString());
                        ps.setString(10, obj.get("fecha_venc").getAsString());
                        ps.setString(11, obj.get("estado").getAsString());
                        ps.setString(12, obj.get("iddocumento").getAsString());
                        ps.setString(13, obj.get("creferencia").getAsString());
                        ps.setString(14, obj.get("gastos").getAsString());
                        ps.setString(15, obj.get("mora").getAsString());
                        ps.setString(16, obj.get("punitorio").getAsString());
                        ps.setString(17, obj.get("tipoop").getAsString());
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

    public boolean borrarDeudaWeb(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM t_deudas  WHERE cliente=? AND estado='PE' ");
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

    public boolean borrarDeudaWebCancelados() throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM t_deudas  WHERE estado='AC' AND verificado=1");
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

    public boolean VerificarBorrados(deudas d) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE t_deudas SET verificado=1 WHERE iddocumento='"+d.getIddocumento()+"'");
     //   ps.setString(1, d.getIddocumento());
        System.out.print("VINE A VERIFICAR LAS CUENTAS");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.bancoplaza;
import Modelo.detalle_forma_cobro;
import Modelo.formapago;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class detalle_forma_cobroDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_forma_cobro> MostrarDetalle(String id) throws SQLException {
        ArrayList<detalle_forma_cobro> lista = new ArrayList<detalle_forma_cobro>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT detalle_forma_cobro.idmovimiento,"
                    + "detalle_forma_cobro.forma,detalle_forma_cobro.banco,"
                    + "detalle_forma_cobro.nrocheque,"
                    + "detalle_forma_cobro.importepago,detalle_forma_cobro.netocobrado,"
                    + "bancos_plaza.nombre AS nombrebanco,formaspago.nombre AS nombreformapago,"
                    + "detalle_forma_cobro.confirmacion "
                    + " FROM detalle_forma_cobro "
                    + " LEFT JOIN bancos_plaza "
                    + " ON bancos_plaza.codigo=detalle_forma_cobro.banco "
                    + " LEFT JOIN formaspago "
                    + " ON formaspago.codigo=detalle_forma_cobro.forma "
                    + " WHERE detalle_forma_cobro.idmovimiento='"+id+"'";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_forma_cobro dc = new detalle_forma_cobro();
                    bancoplaza bco = new bancoplaza();
                    formapago frp = new formapago();

                    dc.setIdmovimiento(rs.getString("idmovimiento"));
                    dc.setBanco(bco);
                    dc.setForma(frp);

                    dc.getForma().setCodigo(rs.getInt("forma"));
                    dc.getForma().setNombre(rs.getString("nombreformapago"));
                    dc.getBanco().setCodigo(rs.getInt("banco"));
                    dc.getBanco().setNombre(rs.getString("nombrebanco"));
                    dc.setNrocheque(rs.getString("nrocheque"));
                    dc.setConfirmacion(rs.getDate("confirmacion"));
                    dc.setNetocobrado(rs.getDouble("netocobrado"));
                    System.out.println("cobrado "+dc.getNetocobrado());
                    System.out.println(dc.getIdmovimiento());
                    lista.add(dc);
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarDetalleFormaPago(String creferencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_forma_cobro WHERE idmovimiento=?");
        ps.setString(1, creferencia);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean guardarFormaPago(String detalleformapago) throws SQLException {
        boolean guardadoforma = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectaformapago = st.getConnection();
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
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
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
        st.close();
        conectaformapago.close();
        return guardadoforma;
    }

    public boolean guardarFormaCobranza(String referencia,String detalleformapago) throws SQLException {
        boolean guardadoforma = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectaformapago = st.getConnection();
        conectaformapago.setAutoCommit(false);

        PreparedStatement psdetalle = null;

        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_forma_cobro WHERE idmovimiento='"+referencia+"'");
        int rowsUpdated = psdetalle.executeUpdate();

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
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("idmovimiento").getAsString());
                        ps.setString(2, obj.get("forma").getAsString());
                        ps.setString(3, obj.get("banco").getAsString());
                        ps.setString(4, obj.get("nrocheque").getAsString());
                        ps.setString(5, obj.get("confirmacion").getAsString());
                        ps.setDouble(6, obj.get("netocobrado").getAsDouble());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardadoforma = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("DETALLE FORMA DE COBRO--->" + ex.getLocalizedMessage());
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
        st.close();
        conectaformapago.close();
        return guardadoforma;
    }

    public boolean borrarDetalleFormaCobro(String creferencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_forma_cobranzas WHERE idmovimiento=?");
        ps.setString(1, creferencia);
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

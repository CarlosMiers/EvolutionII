/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.bancoplaza;
import Modelo.cobroitem;
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
public class cobroitemDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cobroitem> MostrarDetalleItem(String id) throws SQLException {
        ArrayList<cobroitem> lista = new ArrayList<cobroitem>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT cobrositem.idpagos,cobrositem.iditem,cobrositem.supago "
                    + " FROM cobrositem "
                    + " WHERE cobrositem.idpagos='"+id+"'";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cobroitem dc = new cobroitem();

                    dc.setIdpagos(rs.getString("idpagos"));
                    dc.setIditem(rs.getDouble("iditem"));
                    dc.setPago(rs.getDouble("pago"));
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

    public boolean borrarDetalleItem(String creferencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cobroitem WHERE idpagos=?");
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

    public boolean guardarItem(String referencia,String detalleitem) throws SQLException {
        boolean guardadoforma = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectaformapago = st.getConnection();
        conectaformapago.setAutoCommit(false);

        PreparedStatement psdetalle = null;

        psdetalle = st.getConnection().prepareStatement("DELETE FROM cobrositem WHERE idpagos='"+referencia+"'");
        int rowsUpdated = psdetalle.executeUpdate();

        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalleitem);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO cobrositem("
                            + "idpagos,"
                            + "iditem,"
                            + "pago"
                            + ") "
                            + "values(?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("idpagos").getAsString());
                        ps.setString(2, obj.get("iditem").getAsString());
                        ps.setDouble(3, obj.get("pago").getAsDouble());
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
   

}

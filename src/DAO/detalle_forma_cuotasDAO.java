package DAO;

import Conexion.Conexion;
import Modelo.detalle_forma_cuotas;
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

public class detalle_forma_cuotasDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_forma_cuotas> MostrarDetalle(String id) throws SQLException {
        ArrayList<detalle_forma_cuotas> lista = new ArrayList<detalle_forma_cuotas>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT iddetalle,autorizacion,monto,ncuotas,montocuota,primeracuota "
                    + " FROM detalle_forma_cuotas "
                    + " WHERE iddetalle=? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_forma_cuotas dc = new detalle_forma_cuotas();
            
                    dc.setIddetalle(rs.getString("iddetalle"));
                    dc.setAutorizacion(rs.getInt("autorizacion"));
                    dc.setMonto(rs.getDouble("monto"));
                    dc.setNcuotas(rs.getInt("ncuotas"));
                    dc.setMontocuota(rs.getDouble("montocuota"));
                    dc.setPrimeracuota(rs.getDate("primeracuota"));
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

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_forma_cuotas WHERE iddetalle=?");
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

    public boolean guardarFormaCuotas(String detalleformacuotas) throws SQLException {
        boolean guardadoforma = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectaformapago = st.getConnection();
        conectaformapago.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalleformacuotas);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO detalle_forma_cuotas("
                            + "iddetalle,"
                            + "autorizacion,"
                            + "monto,"
                            + "ncuotas,"
                            + "montocuota,"
                            + "primeracuota"
                            + ") "
                            + "values(?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddetalle").getAsString());
                        ps.setString(2, obj.get("autorizacion").getAsString());
                        ps.setString(3, obj.get("monto").getAsString());
                        ps.setString(4, obj.get("ncuotas").getAsString());
                        ps.setString(5, obj.get("montocuota").getAsString());
                        ps.setString(6, obj.get("primeracuota").getAsString());
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
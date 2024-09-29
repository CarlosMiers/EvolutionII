/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

/**
 *
 * @author notebook
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Conexion.Conexion;
import Conexion.ConexionEspejo;
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
public class ExportaFormaCobroDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public ArrayList<detalle_forma_cobro> MostrarDetalle(String id, Integer nsuc) throws SQLException {
        ArrayList<detalle_forma_cobro> lista = new ArrayList<detalle_forma_cobro>();
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }

        Connection conne = stEspejo.getConnection();

        try {

            String sql = "SELECT idmovimiento,forma,codmoneda,"
                    + "banco,nrocheque,confirmacion,netocobrado "
                    + "FROM detalle_forma_cobro "
                    + "INNER JOIN cabecera_ventas "
                    + "ON cabecera_ventas.creferencia=detalle_forma_cobro.idmovimiento "
                    + " WHERE detalle_forma_cobro.idmovimiento='" + id + "'"
                    + "  ORDER BY cabecera_ventas.creferencia";

            try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
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
                    System.out.println(dc.getIdmovimiento());
                    lista.add(dc);
                }
                ps.close();
                stEspejo.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarDetalleFormaPago(String creferencia, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }

        Connection conne = stEspejo.getConnection();
        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("DELETE FROM detalle_forma_cobro WHERE idmovimiento=?");
        ps.setString(1, creferencia);
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean guardarFormaPagoRemoto(String detalleformapago, Integer nsuc) throws SQLException {
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

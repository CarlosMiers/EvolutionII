/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.giraduria;
import Modelo.planilla;
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
public class planillaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<planilla> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<planilla> lista = new ArrayList<planilla>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT idnumero,fecha,vence,giraduria,total,porcentajebonif,importebonif,"
                    + "importeiva,subtotal,retencion,netoacobrar,saldoplanilla,giradurias.nombre "
                    + " FROM planilla "
                    + " INNER JOIN giradurias "
                    + " ON giradurias.codigo=planilla.giraduria "
                    + " WHERE planilla.fecha between ? AND ? "
                    + " ORDER BY planilla.idnumero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    planilla pl = new planilla();
                    pl.setGiraduria(giraduria);

                    pl.setIdnumero(rs.getDouble("idnumero"));
                    pl.setFecha(rs.getDate("fecha"));
                    pl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    pl.getGiraduria().setNombre(rs.getString("nombre"));
                    pl.setVence(rs.getDate("vence"));
                    pl.setTotal(rs.getBigDecimal("total"));
                    pl.setPorcentajebonif(rs.getBigDecimal("porcentajebonif"));
                    pl.setImportebonif(rs.getBigDecimal("importebonif"));
                    pl.setImporteiva(rs.getBigDecimal("importeiva"));
                    pl.setSubtotal(rs.getBigDecimal("subtotal"));
                    pl.setRetencion(rs.getBigDecimal("retencion"));
                    pl.setNetoacobrar(rs.getBigDecimal("netoacobrar"));
                    pl.setSaldoplanilla(rs.getBigDecimal("saldoplanilla"));
                    lista.add(pl);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public planilla insertarPlanilla(planilla pla, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO planilla (fecha,vence,giraduria,total,porcentajebonif,importebonif,importeiva,subtotal,retencion,netoacobrar,saldoplanilla) VALUES (?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, pla.getFecha());
        ps.setDate(2, pla.getVence());
        ps.setInt(3, pla.getGiraduria().getCodigo());
        ps.setBigDecimal(4, pla.getTotal());
        ps.setBigDecimal(5, pla.getPorcentajebonif());
        ps.setBigDecimal(6, pla.getImportebonif());
        ps.setBigDecimal(7, pla.getImporteiva());
        ps.setBigDecimal(8, pla.getSubtotal());
        ps.setBigDecimal(9, pla.getRetencion());
        ps.setBigDecimal(10, pla.getNetoacobrar());
        ps.setBigDecimal(11, pla.getSaldoplanilla());

        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarDetalle(id, detalle, con);
        }
        st.close();
        ps.close();
        return pla;
    }

    public boolean borrarPlanilla(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM planilla WHERE idnumero=?");
        ps.setInt(1, id);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean borrarDetallePlanilla(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM gestion_cobros WHERE iddetalle=?");
        ps.setInt(1, id);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public planilla buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        planilla pl = new planilla();

        try {

            String sql = "SELECT idnumero,fecha,vence,giraduria,total,porcentajebonif,importebonif,"
                    + "importeiva,subtotal,retencion,netoacobrar,saldoplanilla,giradurias.nombre, "
                    + "(SELECT SUM(importe) "
                    + "FROM cobroplanilla "
                    + "WHERE cobroplanilla.idplanilla=planilla.idnumero) pagos "
                    + " FROM planilla "
                    + " INNER JOIN giradurias "
                    + " ON giradurias.codigo=planilla.giraduria "
                    + " WHERE planilla.idnumero= ? "
                    + " ORDER BY planilla.idnumero ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    giraduria giraduria = new giraduria();
                    pl.setGiraduria(giraduria);
                    pl.setIdnumero(rs.getDouble("idnumero"));
                    pl.setFecha(rs.getDate("fecha"));
                    pl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    pl.getGiraduria().setNombre(rs.getString("nombre"));
                    pl.setVence(rs.getDate("vence"));
                    pl.setTotal(rs.getBigDecimal("total"));
                    pl.setPorcentajebonif(rs.getBigDecimal("porcentajebonif"));
                    pl.setImportebonif(rs.getBigDecimal("importebonif"));
                    pl.setImporteiva(rs.getBigDecimal("importeiva"));
                    pl.setSubtotal(rs.getBigDecimal("subtotal"));
                    pl.setRetencion(rs.getBigDecimal("retencion"));
                    pl.setNetoacobrar(rs.getBigDecimal("netoacobrar"));
                    pl.setSaldoplanilla(rs.getBigDecimal("saldoplanilla"));
                    pl.setPagos(rs.getBigDecimal("pagos"));
                    if (pl.getPagos() != null) {
                        pl.setSaldoplanilla(pl.getSubtotal().subtract(pl.getPagos()));
                    }
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return pl;
    }

    public boolean guardarDetalle(int id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalle);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO gestion_cobros("
                            + "iddetalle,"
                            + "iddocumento,"
                            + "fecha,"
                            + "vencimiento,"
                            + "documento,"
                            + "fecha_cobro,"
                            + "enviado,"
                            + "autorizacion,"
                            + "cliente,"
                            + "sucursal,"
                            + "moneda,"
                            + "comprobante,"
                            + "giraduria,"
                            + "numerocuota,"
                            + "creferencia,"
                            + "comercial,"
                            + "amortiza,"
                            + "minteres,"
                            + "cuota,"
                            + "cobrado"
                            + ") "
                            + "values (?,?,?,?,?,"
                            + "?,?,?,?,?,"
                            + "?,?,?,?,?,"
                            + "?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("iddocumento").getAsString());
                        ps.setString(3, obj.get("fecha").getAsString());
                        ps.setString(4, obj.get("vencimiento").getAsString());
                        ps.setString(5, obj.get("documento").getAsString());
                        ps.setString(6, obj.get("fecha_cobro").getAsString());
                        ps.setString(7, obj.get("enviado").getAsString());
                        ps.setString(8, obj.get("autorizacion").getAsString());
                        ps.setString(9, obj.get("cliente").getAsString());
                        ps.setString(10, obj.get("sucursal").getAsString());
                        ps.setString(11, obj.get("moneda").getAsString());
                        ps.setString(12, obj.get("comprobante").getAsString());
                        ps.setString(13, obj.get("giraduria").getAsString());
                        ps.setString(14, obj.get("numerocuota").getAsString());
                        ps.setString(15, obj.get("creferencia").getAsString());
                        ps.setString(16, obj.get("comercial").getAsString());
                        ps.setString(17, obj.get("amortiza").getAsString());
                        ps.setString(18, obj.get("minteres").getAsString());
                        ps.setString(19, obj.get("cuota").getAsString());
                        ps.setString(20, obj.get("cobrado").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("---> GUARDAR EN GESTION DE COBROS" + ex.getLocalizedMessage());
                    guardado = false;
                    break;
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
        st.close();
        conn.close();
        return guardado;
    }
}

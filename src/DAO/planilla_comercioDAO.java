/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.banco;
import Modelo.casa;
import Modelo.comprobante;
import Modelo.planilla_comercio;
import Modelo.sucursal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Pc_Server
 */
public class planilla_comercioDAO {

    Conexion con = null;
    Statement st = null;

    public boolean ActualizarPlanilla(planilla_comercio v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        boolean guardado = false;
        int id = 0;

        ps = st.getConnection().prepareStatement("UPDATE planilla_comercios SET sucursal=?,moneda=?,"
                + "casa,servicio=?,fecha=?,vencimiento=?,"
                + "totales=?,usuariomodi=?,fechamodi,creferencia WHERE numero=" + v.getNumero());
        ps.setInt(1, v.getSucursal().getCodigo());
        ps.setInt(2, v.getMoneda().getCodigo());
        ps.setInt(3, v.getCasa().getCodigo());
        ps.setInt(4, v.getServicio().getCodigo());
        ps.setDate(5, v.getFecha());
        ps.setDate(6, v.getVencimiento());
        ps.setDouble(7, v.getTotales());
        ps.setDouble(8, v.getUsuariomodi());
        ps.setDate(9, v.getFechamodi());
        ps.setString(10, v.getCreferencia());
        id = (int) v.getNumero();
        int rowsUpdated = ps.executeUpdate();
        guardado = guardarDetalle(id, detalle, con);
        st.close();
        ps.close();
        conn.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
    }

    public planilla_comercio InsertarPlanilla(planilla_comercio v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO planilla_comercios (sucursal,moneda,casa,"
                + "servicio,fecha,vencimiento,"
                + "totales,usuarioalta,fechaalta,creferencia) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, v.getSucursal().getCodigo());
        ps.setInt(2, v.getMoneda().getCodigo());
        ps.setInt(3, v.getCasa().getCodigo());
        ps.setInt(4, v.getServicio().getCodigo());
        ps.setDate(5, v.getFecha());
        ps.setDate(6, v.getVencimiento());
        ps.setDouble(7, v.getTotales());
        ps.setDouble(8, v.getUsuarioalta());
        ps.setDate(9, v.getFechaalta());
        ps.setString(10, v.getCreferencia());

        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarDetalle(id, detalle, con);
        }
        st.close();
        ps.close();
        conn.close();
        JOptionPane.showMessageDialog(null, "N° de Planilla Generado " + id);
        return v;
    }

    public boolean guardarDetalle(int id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement psdetalle = null;
        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_planilla_comercios WHERE dnumero=?");
        psdetalle.setInt(1, id);
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

                    String sql = "insert into  detalle_planilla_comercios("
                            + "dnumero,"
                            + "iddocumento,"
                            + "socio,"
                            + "numeroorden,"
                            + "vence_cuota,"
                            + "monto,"
                            + "numerocuota,"
                            + "cuota,"
                            + "emision_orden "
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("iddocumento").getAsString());
                        ps.setString(3, obj.get("socio").getAsString());
                        ps.setString(4, obj.get("numeroorden").getAsString());
                        ps.setString(5, obj.get("vence_cuota").getAsString());
                        ps.setString(6, obj.get("monto").getAsString());
                        ps.setString(7, obj.get("numerocuota").getAsString());
                        ps.setString(8, obj.get("cuota").getAsString());
                        ps.setString(9, obj.get("emision_orden").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
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

    public planilla_comercio buscarId(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        planilla_comercio vta = new planilla_comercio();

        try {

            String sql = "SELECT planilla_comercios.numero,planilla_comercios.sucursal,"
                    + "planilla_comercios.fecha,planilla_comercios.casa,"
                    + "planilla_comercios.servicio,planilla_comercios.nrocheque,"
                    + "planilla_comercios.fechapago,planilla_comercios.banco,"
                    + "planilla_comercios.vencimiento,planilla_comercios.totales,"
                    + "planilla_comercios.asiento,planilla_comercios.comision,"
                    + "planilla_comercios.monto_comision,planilla_comercios.iva_comision,"
                    + "planilla_comercios.totalcomision,planilla_comercios.saldo_a_pagar,"
                    + "planilla_comercios.monto_iva,"
                    + "bancos.nombre AS nombrecuenta,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "casas.nombre AS nombrecasa,"
                    + "comprobantes.nombre AS nombreservicio "
                    + "FROM planilla_comercios "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo = planilla_comercios.banco "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=planilla_comercios.sucursal "
                    + "LEFT JOIN casas "
                    + "ON casas.codigo=planilla_comercios.casa "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=planilla_comercios.servicio "
                    + " WHERE planilla_comercios.numero= ? "
                    + " ORDER BY planilla_comercios.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal suc = new sucursal();
                    comprobante com = new comprobante();
                    banco bco = new banco();
                    casa casa = new casa();

                    vta.setSucursal(suc);
                    vta.setServicio(com);
                    vta.setBanco(bco);
                    vta.setCasa(casa);

                    vta.setNumero(rs.getDouble("numero"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.setVencimiento(rs.getDate("vencimiento"));

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));

                    vta.getBanco().setCodigo(rs.getInt("banco"));
                    vta.getBanco().setNombre(rs.getString("nombrecuenta"));

                    vta.getServicio().setCodigo(rs.getInt("servicio"));
                    vta.getServicio().setNombre(rs.getString("nombreservicio"));

                    vta.getCasa().setCodigo(rs.getInt("casa"));
                    vta.getCasa().setNombre(rs.getString("nombrecasa"));
                    vta.setTotales(rs.getDouble("totales"));
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return vta;
    }

    public ArrayList<planilla_comercio> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<planilla_comercio> lista = new ArrayList<planilla_comercio>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT planilla_comercios.numero,planilla_comercios.sucursal,"
                    + "planilla_comercios.fecha,planilla_comercios.casa,"
                    + "planilla_comercios.servicio,planilla_comercios.nrocheque,"
                    + "planilla_comercios.fechapago,planilla_comercios.banco,"
                    + "planilla_comercios.vencimiento,planilla_comercios.totales,"
                    + "planilla_comercios.asiento,planilla_comercios.comision,"
                    + "planilla_comercios.monto_comision,planilla_comercios.iva_comision,"
                    + "planilla_comercios.totalcomision,planilla_comercios.saldo_a_pagar,"
                    + "planilla_comercios.monto_iva,"
                    + "bancos.nombre AS nombrecuenta,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "casas.nombre AS nombrecasa,"
                    + "comprobantes.nombre AS nombreservicio "
                    + "FROM planilla_comercios "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo = planilla_comercios.banco "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=planilla_comercios.sucursal "
                    + "LEFT JOIN casas "
                    + "ON casas.codigo=planilla_comercios.casa "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=planilla_comercios.servicio "
                    + " WHERE planilla_comercios.fecha between ? AND ? "
                    + " ORDER BY planilla_comercios.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    planilla_comercio vta = new planilla_comercio();
                    sucursal suc = new sucursal();
                    comprobante com = new comprobante();
                    banco bco = new banco();
                    casa casa = new casa();

                    vta.setSucursal(suc);
                    vta.setServicio(com);
                    vta.setBanco(bco);
                    vta.setCasa(casa);

                    vta.setNumero(rs.getDouble("numero"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.setVencimiento(rs.getDate("vencimiento"));

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));

                    vta.getBanco().setCodigo(rs.getInt("banco"));
                    vta.getBanco().setNombre(rs.getString("nombrecuenta"));

                    vta.getServicio().setCodigo(rs.getInt("servicio"));
                    vta.getServicio().setNombre(rs.getString("nombreservicio"));

                    vta.getCasa().setCodigo(rs.getInt("casa"));
                    vta.getCasa().setNombre(rs.getString("nombrecasa"));
                    vta.setTotales(rs.getDouble("totales"));

                    lista.add(vta);
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean EliminarPlanilla(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM planilla_comercios WHERE numero=?");
        ps.setDouble(1, cod);
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

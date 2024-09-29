/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.comprobante;
import Modelo.descuentos_varios;
import Modelo.moneda;
import Modelo.sucursal;
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
 * @author Pc_Server
 */
public class descuentos_variosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<descuentos_varios> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<descuentos_varios> lista = new ArrayList<descuentos_varios>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT descuentos_varios.iddescuentosvarios,descuentos_varios.numero,descuentos_varios.fecha,descuentos_varios.sucursal,"
                    + "descuentos_varios.moneda,descuentos_varios.servicio,descuentos_varios.vencimiento,descuentos_varios.importe,"
                    + "descuentos_varios.totales,descuentos_varios.asiento,descuentos_varios.fechaalta,descuentos_varios.fechamodi,"
                    + "descuentos_varios.usuarioalta,descuentos_varios.usuariomodi,"
                    + "sucursales.nombre AS nombresucursal,monedas.nombre AS nombremoneda,comprobantes.nombre AS nombrecomprobante"
                    + " FROM descuentos_varios "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=descuentos_varios.sucursal "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=descuentos_varios.moneda "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=descuentos_varios.servicio "
                    + " WHERE descuentos_varios.fecha between ? AND ? "
                    + " ORDER BY descuentos_varios.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    descuentos_varios ajuste = new descuentos_varios();
                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    moneda moneda = new moneda();

                    ajuste.setSucursal(sucursal);
                    ajuste.setServicio(comprobante);
                    ajuste.setMoneda(moneda);

                    ajuste.setIddescuentosvarios(rs.getString("iddescuentosvarios"));
                    ajuste.setNumero(rs.getInt("numero"));
                    ajuste.setFecha(rs.getDate("fecha"));
                    ajuste.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ajuste.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ajuste.getServicio().setCodigo(rs.getInt("servicio"));
                    ajuste.getServicio().setNombre(rs.getString("nombrecomprobante"));
                    ajuste.getMoneda().setCodigo(rs.getInt("moneda"));
                    ajuste.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ajuste.setTotales(rs.getDouble("totales"));
                    ajuste.setUsuarioalta(rs.getInt("usuarioalta"));
                    ajuste.setUsuariomodi(rs.getInt("usuariomodi"));
                    ajuste.setVencimiento(rs.getDate("vencimiento"));
                    lista.add(ajuste);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public descuentos_varios buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        descuentos_varios ajuste = new descuentos_varios();

        try {

            String sql = "SELECT iddescuentosvarios,descuentos_varios.numero,descuentos_varios.fecha,descuentos_varios.sucursal,"
                    + "descuentos_varios.moneda,descuentos_varios.servicio,descuentos_varios.vencimiento,descuentos_varios.importe,"
                    + "descuentos_varios.totales,descuentos_varios.asiento,descuentos_varios.fechaalta,descuentos_varios.fechamodi,"
                    + "descuentos_varios.usuarioalta,descuentos_varios.usuariomodi,"
                    + "sucursales.nombre AS nombresucursal,monedas.nombre AS nombremoneda,comprobantes.nombre AS nombrecomprobante"
                    + " FROM descuentos_varios "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=descuentos_varios.sucursal "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=descuentos_varios.moneda "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=descuentos_varios.servicio "
                    + " WHERE descuentos_varios.numero = ? "
                    + " ORDER BY descuentos_varios.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    moneda moneda = new moneda();

                    ajuste.setSucursal(sucursal);
                    ajuste.setServicio(comprobante);
                    ajuste.setMoneda(moneda);

                    ajuste.setIddescuentosvarios(rs.getString("iddescuentosvarios"));
                    ajuste.setNumero(rs.getInt("numero"));
                    ajuste.setFecha(rs.getDate("fecha"));
                    ajuste.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ajuste.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ajuste.getMoneda().setCodigo(rs.getInt("moneda"));
                    ajuste.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ajuste.getServicio().setCodigo(rs.getInt("servicio"));
                    ajuste.getServicio().setNombre(rs.getString("nombrecomprobante"));
                    ajuste.setVencimiento(rs.getDate("vencimiento"));
                    ajuste.setImporte(rs.getDouble("importe"));
                    ajuste.setTotales(rs.getDouble("totales"));
                    ajuste.setAsiento(rs.getDouble("asiento"));
                    ajuste.setUsuarioalta(rs.getInt("usuarioalta"));
                    ajuste.setUsuariomodi(rs.getInt("usuariomodi"));
                    
                    
                    
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return ajuste;
    }

    public descuentos_varios insertardescuento(descuentos_varios ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO descuentos_varios (iddescuentosvarios,fecha,sucursal,moneda,servicio,vencimiento,totales,usuarioalta) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIddescuentosvarios());
        ps.setDate(2, ocr.getFecha());
        ps.setInt(3, ocr.getSucursal().getCodigo());
        ps.setInt(4, ocr.getMoneda().getCodigo());
        ps.setInt(5, ocr.getServicio().getCodigo());
        ps.setDate(6, ocr.getVencimiento());
        ps.setDouble(7, ocr.getTotales());
        ps.setInt(8, ocr.getUsuarioalta());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            guardado = guardarDetalle(ocr.getIddescuentosvarios(), detalle, con);
        }
        st.close();
        ps.close();
        return ocr;
    }

    public boolean actualizarAjusteMercaderia(descuentos_varios aj, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        boolean guardado = false;

        ps = st.getConnection().prepareStatement("UPDATE descuentos_varios SET fecha=?,sucursal=?,moneda=?,servicio=?,vencimiento=?,totales=?,usuarioalta=? WHERE iddescuentosvarios='" + aj.getIddescuentosvarios() + "'");
        ps.setDate(1, aj.getFecha());
        ps.setInt(2, aj.getSucursal().getCodigo());
        ps.setInt(3, aj.getMoneda().getCodigo());
        ps.setInt(4, aj.getServicio().getCodigo());
        ps.setDate(5, aj.getVencimiento());
        ps.setDouble(6, aj.getTotales());
        ps.setInt(7, aj.getUsuarioalta());

        int rowsUpdated = ps.executeUpdate();
        guardado = guardarDetalle(aj.getIddescuentosvarios(), detalle, con);
        st.close();
        ps.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
    }

    public boolean guardarDetalle(String id, String detalle, Conexion conexion) throws SQLException {
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
                    String sql = "insert into detalle_descuentos_varios("
                            + "id_detalle,"
                            + "socio,"
                            + "descuento"
                            + ")"
                            + "values(?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("socio").getAsString());
                        ps.setString(3, obj.get("descuento").getAsString());
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

    public boolean borrarAjustes(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM descuentos_varios WHERE iddescuentosvarios=?");
        ps.setString(1, referencia);
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

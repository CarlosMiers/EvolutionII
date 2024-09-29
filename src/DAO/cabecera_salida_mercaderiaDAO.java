/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cabecera_salida_mercaderia;
import Modelo.comprobante;
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
 * @author Usuario
 */
public class cabecera_salida_mercaderiaDAO {
    Conexion con = null;
    Statement st = null;

    public ArrayList<cabecera_salida_mercaderia> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cabecera_salida_mercaderia> lista = new ArrayList<cabecera_salida_mercaderia>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT cabecera_salida_mercaderias.idreferencia,cabecera_salida_mercaderias.numero,cabecera_salida_mercaderias.fecha,cabecera_salida_mercaderias.sucursal,cabecera_salida_mercaderias.cierre,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,cabecera_salida_mercaderias.tipo,"
                    + "cabecera_salida_mercaderias.observacion,cabecera_salida_mercaderias.asiento  "
                    + "FROM cabecera_salida_mercaderias "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_salida_mercaderias.sucursal "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_salida_mercaderias.tipo "
                    + "WHERE cabecera_salida_mercaderias.fecha BETWEEN ? AND ? "
                    + " ORDER BY cabecera_salida_mercaderias.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cabecera_salida_mercaderia ajuste = new cabecera_salida_mercaderia();

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();

                    ajuste.setSucursal(sucursal);
                    ajuste.setTipo(comprobante);

                    ajuste.setIdreferencia(rs.getString("idreferencia"));
                    ajuste.setNumero(rs.getInt("numero"));
                    ajuste.setFecha(rs.getDate("fecha"));
                    ajuste.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ajuste.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ajuste.getTipo().setCodigo(rs.getInt("tipo"));
                    ajuste.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    ajuste.setObservacion(rs.getString("observacion"));
                    ajuste.setCierre(rs.getInt("cierre"));
                    ajuste.setAsiento(rs.getDouble("asiento"));
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

    public cabecera_salida_mercaderia buscarString(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        cabecera_salida_mercaderia ajuste = new cabecera_salida_mercaderia();

        try {

            String sql = "SELECT cabecera_salida_mercaderias.idreferencia,cabecera_salida_mercaderias.numero,cabecera_salida_mercaderias.fecha,cabecera_salida_mercaderias.sucursal,cabecera_salida_mercaderias.cierre,"
                    + "sucursales.nombre AS nombresucursal,comprobantes.nombre AS nombrecomprobante,cabecera_salida_mercaderias.tipo,cabecera_salida_mercaderias.observacion "
                    + "FROM cabecera_salida_mercaderias "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_salida_mercaderias.sucursal "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_salida_mercaderias.tipo "
                    + "WHERE cabecera_salida_mercaderias.idreferencia= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    comprobante comprobante = new comprobante();
                    sucursal sucursal = new sucursal();

                    ajuste.setSucursal(sucursal);
                    ajuste.setTipo(comprobante);

                    ajuste.setIdreferencia(rs.getString("idreferencia"));
                    ajuste.setNumero(rs.getInt("numero"));
                    ajuste.setFecha(rs.getDate("fecha"));
                    ajuste.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ajuste.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ajuste.getTipo().setCodigo(rs.getInt("tipo"));
                    ajuste.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    ajuste.setObservacion(rs.getString("observacion"));
                    ajuste.setCierre(rs.getInt("cierre"));
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return ajuste;
    }

    public cabecera_salida_mercaderia insertarmercaderia(cabecera_salida_mercaderia ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_salida_mercaderias (idreferencia,fecha,sucursal,tipo,observacion) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdreferencia());
        ps.setDate(2, ocr.getFecha());
        ps.setInt(3, ocr.getSucursal().getCodigo());
        ps.setInt(4, ocr.getTipo().getCodigo());
        ps.setString(5, ocr.getObservacion());

        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarDetalle(id, detalle, con);
        }
        st.close();
        ps.close();
        return ocr;
    }

    public boolean actualizarSalida(cabecera_salida_mercaderia aj, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        boolean guardado = false;
        int id = 0;

        ps = st.getConnection().prepareStatement("UPDATE cabecera_salida_mercaderias SET fecha=?,sucursal=?,tipo=?,observacion=? WHERE idreferencia='" + aj.getIdreferencia() + "'");
        ps.setDate(1, aj.getFecha());
        ps.setInt(2, aj.getSucursal().getCodigo());
        ps.setInt(3, aj.getTipo().getCodigo());
        ps.setString(4, aj.getObservacion());

        int rowsUpdated = ps.executeUpdate();
        guardado = guardarDetalle(id, detalle, con);
        st.close();
        ps.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
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
                    String sql = "insert into detalle_salida_mercaderias("
                            + "dreferencia,"
                            + "producto,"
                            + "cantidad,"
                            + "costo,"
                            + "suc"
                            + ") "
                            + "values(?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("dreferencia").getAsString());
                        ps.setString(2, obj.get("producto").getAsString());
                        ps.setString(3, obj.get("cantidad").getAsString());
                        ps.setString(4, obj.get("costo").getAsString());
                        ps.setString(5, obj.get("suc").getAsString());
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

        ps = st.getConnection().prepareStatement("DELETE FROM cabecera_salida_mercaderias WHERE idreferencia=?");
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

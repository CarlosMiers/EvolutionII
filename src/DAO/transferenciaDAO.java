/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.sucursal;
import Modelo.transferencia;
import Modelo.venta;
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
public class transferenciaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<transferencia> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<transferencia> lista = new ArrayList<transferencia>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT transferencias.idtransferencia,transferencias.numero,transferencias.fecha,"
                    + "transferencias.origen,transferencias.destino,transferencias.tipo, "
                    + "transferencias.cierre,transferencias.codusuario,transferencias.horagrabado, "
                    + "comprobantes.nombre AS nombrecomprobante,transferencias.codusuario, "
                    + "(SELECT nombre FROM sucursales WHERE origen=sucursales.codigo) nombreorigen, "
                    + "(SELECT nombre FROM sucursales WHERE destino=sucursales.codigo) nombredestino "
                    + "FROM transferencias "
                    + "LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=transferencias.tipo "
                    + " WHERE transferencias.fecha between ? AND ? "
                    + " ORDER BY transferencias.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    transferencia tr = new transferencia();
                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    tr.setTipo(comprobante);
                    tr.setNumero(rs.getInt("numero"));
                    tr.setFecha(rs.getDate("fecha"));
                    tr.setOrigen(rs.getInt("origen"));
                    tr.setCierre(rs.getInt("cierre"));
                    tr.setCodusuario(rs.getInt("codusuario"));
                    tr.setHoragrabado(rs.getTime("horagrabado"));
                    tr.setIdtransferencia(rs.getString("idtransferencia"));
                    tr.setNumero(rs.getInt("numero"));
                    tr.getTipo().setCodigo(rs.getInt("tipo"));
                    tr.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    tr.setDestino(rs.getInt("destino"));
                    tr.setNombredestino(rs.getString("nombredestino"));
                    tr.setNombreorigen(rs.getString("nombreorigen"));
                    lista.add(tr);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public transferencia buscarString(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        transferencia tr = new transferencia();
        try {
            String sql = "SELECT transferencias.idtransferencia,transferencias.numero,transferencias.fecha,"
                    + "transferencias.origen,transferencias.destino,transferencias.tipo,transferencias.horagrabado,"
                    + "transferencias.cierre,transferencias.codusuario,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "(SELECT nombre FROM sucursales WHERE origen=sucursales.codigo) nombreorigen, "
                    + "(SELECT nombre FROM sucursales WHERE destino=sucursales.codigo) nombredestino "
                    + " FROM transferencias "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=transferencias.tipo "
                    +" WHERE transferencias.idtransferencia=?";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    tr.setTipo(comprobante);
                    tr.setNumero(rs.getInt("numero"));
                    tr.setFecha(rs.getDate("fecha"));
                    tr.setCierre(rs.getInt("cierre"));
                    tr.setCodusuario(rs.getInt("codusuario"));
                    tr.setHoragrabado(rs.getTime("horagrabado"));
                    tr.setIdtransferencia(rs.getString("idtransferencia"));
                    tr.setNumero(rs.getInt("numero"));
                    tr.getTipo().setCodigo(rs.getInt("tipo"));
                    tr.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    tr.setDestino(rs.getInt("destino"));
                    tr.setNombredestino(rs.getString("nombredestino"));
                    tr.setOrigen(rs.getInt("origen"));
                    tr.setNombreorigen(rs.getString("nombreorigen"));
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return tr;
    }

    public transferencia insertarmercaderia(transferencia ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO transferencias (idtransferencia,fecha,origen,destino,tipo) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdtransferencia());
        ps.setDate(2, ocr.getFecha());
        ps.setInt(3, ocr.getOrigen());
        ps.setInt(4, ocr.getDestino());
        ps.setInt(5, ocr.getTipo().getCodigo());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarDetalle(ocr.getIdtransferencia(), detalle, con);
        }
        st.close();
        ps.close();
        return ocr;
    }

    public boolean actualizarSalida(transferencia aj, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        boolean guardado = false;
        int id = 0;

        ps = st.getConnection().prepareStatement("UPDATE transferencias SET fecha=?,origen=?,destino=?,tipo=? WHERE idtransferencia='" + aj.getIdtransferencia() + "'");
        ps.setDate(1, aj.getFecha());
        ps.setInt(2, aj.getOrigen());
        ps.setInt(3, aj.getDestino());
        ps.setInt(4, aj.getTipo().getCodigo());

        int rowsUpdated = ps.executeUpdate();
        guardado = guardarDetalle(aj.getIdtransferencia(), detalle, con);
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

        PreparedStatement psdetalle = null;
        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_transferencias WHERE dreferencia=?");
        psdetalle.setString(1, id);
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
                    String sql = "insert into detalle_transferencias("
                            + "dreferencia,"
                            + "producto,"
                            + "cantidad,"
                            + "costo,"
                            + "suc_salida,"
                            + "suc_entrada"
                            + ") "
                            + "values(?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("dreferencia").getAsString());
                        ps.setString(2, obj.get("producto").getAsString());
                        ps.setString(3, obj.get("cantidad").getAsString());
                        ps.setString(4, obj.get("costo").getAsString());
                        ps.setString(5, obj.get("suc_salida").getAsString());
                        ps.setString(6, obj.get("suc_entrada").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    conn.rollback();
                    guardado = false;
                    return guardado;
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

        ps = st.getConnection().prepareStatement("DELETE FROM transferencias WHERE idtransferencia=?");
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



        public ArrayList<transferencia> MostrarxFechaFerremax(Date fechaini, Date fechafin, Integer nsuc ) throws SQLException {
        ArrayList<transferencia> lista = new ArrayList<transferencia>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT transferencias.idtransferencia,transferencias.numero,transferencias.fecha,"
                    + "transferencias.origen,transferencias.destino,transferencias.tipo, "
                    + "transferencias.cierre,transferencias.codusuario,transferencias.horagrabado, "
                    + "comprobantes.nombre AS nombrecomprobante,transferencias.codusuario,enviado,recibido,"
                    + "(SELECT nombre FROM sucursales WHERE origen=sucursales.codigo) nombreorigen, "
                    + "(SELECT nombre FROM sucursales WHERE destino=sucursales.codigo) nombredestino "
                    + "FROM transferencias "
                    + "LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=transferencias.tipo "
                    + " WHERE IF(" + nsuc + "<>0,transferencias.destino=" + nsuc+ ",TRUE)" 
                    + "AND transferencias.fecha between'"+fechaini+"' AND '"+fechafin+"'"
                    + " ORDER BY transferencias.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    transferencia tr = new transferencia();
                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    tr.setTipo(comprobante);
                    tr.setNumero(rs.getInt("numero"));
                    tr.setFecha(rs.getDate("fecha"));
                    tr.setOrigen(rs.getInt("origen"));
                    tr.setCierre(rs.getInt("cierre"));
                    tr.setCodusuario(rs.getInt("codusuario"));
                    tr.setHoragrabado(rs.getTime("horagrabado"));
                    tr.setIdtransferencia(rs.getString("idtransferencia"));
                    tr.setNumero(rs.getInt("numero"));
                    tr.getTipo().setCodigo(rs.getInt("tipo"));
                    tr.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    tr.setDestino(rs.getInt("destino"));
                    tr.setNombredestino(rs.getString("nombredestino"));
                    tr.setNombreorigen(rs.getString("nombreorigen"));
                    tr.setEnviado(rs.getInt("enviado"));
                    tr.setRecibido(rs.getInt("recibido"));
                    lista.add(tr);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    
    
    public transferencia buscarStringFerremax(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        transferencia tr = new transferencia();
        try {
            String sql = "SELECT transferencias.idtransferencia,transferencias.numero,transferencias.fecha,"
                    + "transferencias.origen,transferencias.destino,transferencias.tipo,transferencias.horagrabado,"
                    + "transferencias.cierre,transferencias.codusuario,"
                    + "comprobantes.nombre AS nombrecomprobante,transferencias.idpedido,"
                    + "transferencias.enviado,transferencias.recibido,"
                    + "(SELECT nombre FROM sucursales WHERE origen=sucursales.codigo) nombreorigen, "
                    + "(SELECT nombre FROM sucursales WHERE destino=sucursales.codigo) nombredestino "
                    + " FROM transferencias "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=transferencias.tipo "
                    +" WHERE transferencias.idtransferencia=?";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    tr.setTipo(comprobante);
                    tr.setNumero(rs.getInt("numero"));
                    tr.setIdpedido(rs.getDouble("idpedido"));
                    tr.setFecha(rs.getDate("fecha"));
                    tr.setCierre(rs.getInt("cierre"));
                    tr.setCodusuario(rs.getInt("codusuario"));
                    tr.setHoragrabado(rs.getTime("horagrabado"));
                    tr.setIdtransferencia(rs.getString("idtransferencia"));
                    tr.setNumero(rs.getInt("numero"));
                    tr.getTipo().setCodigo(rs.getInt("tipo"));
                    tr.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    tr.setDestino(rs.getInt("destino"));
                    tr.setNombredestino(rs.getString("nombredestino"));
                    tr.setOrigen(rs.getInt("origen"));
                    tr.setNombreorigen(rs.getString("nombreorigen"));
                    tr.setEnviado(rs.getInt("enviado"));
                    tr.setRecibido(rs.getInt("recibido"));
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return tr;
    }


        public transferencia insertarmercaderiaFerremax(transferencia ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO transferencias (idtransferencia,"
                + "fecha,origen,destino,tipo,idpedido,enviado) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdtransferencia());
        ps.setDate(2, ocr.getFecha());
        ps.setInt(3, ocr.getOrigen());
        ps.setInt(4, ocr.getDestino());
        ps.setInt(5, ocr.getTipo().getCodigo());
        ps.setDouble(6, ocr.getIdpedido());
        ps.setInt(7, ocr.getEnviado());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarDetalle(ocr.getIdtransferencia(), detalle, con);
        }
        st.close();
        ps.close();
        return ocr;
    }

public boolean actualizarTransferenciaFerremax(transferencia aj, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        boolean guardado = false;
        int id = 0;

        ps = st.getConnection().prepareStatement("UPDATE transferencias SET fecha=?,"
                + "origen=?,destino=?,tipo=?,idpedido=?,enviado=? WHERE idtransferencia='" + aj.getIdtransferencia() + "'");
        ps.setDate(1, aj.getFecha());
        ps.setInt(2, aj.getOrigen());
        ps.setInt(3, aj.getDestino());
        ps.setInt(4, aj.getTipo().getCodigo());
        ps.setDouble (5, aj.getIdpedido());
        ps.setInt(6, aj.getEnviado());
        

        int rowsUpdated = ps.executeUpdate();
        guardado = guardarDetalle(aj.getIdtransferencia(), detalle, con);
        st.close();
        ps.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
    }
    
    
}

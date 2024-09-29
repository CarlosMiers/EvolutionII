/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.saldo_aporte;
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
public class saldo_aporteDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<saldo_aporte> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<saldo_aporte> lista = new ArrayList<saldo_aporte>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT saldos_aportes.referencia,saldos_aportes.documento,saldos_aportes.socio,clientes.nombre AS nombresocio,saldos_aportes.fecha,"
                    + "saldos_aportes.comprobante,comprobantes.nombre AS nombrecomprobante,saldos_aportes.retencion,saldos_aportes.descuentos,"
                    + " saldos_aportes.importe, saldos_aportes.saldo"
                    + " FROM saldos_aportes "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=saldos_aportes.socio "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=saldos_aportes.comprobante "
                    + " WHERE saldos_aportes.fecha BETWEEN ? AND ?  "
                    + " ORDER BY saldos_aportes.fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();

                    saldo_aporte ap = new saldo_aporte();

                    ap.setSocio(cliente);
                    ap.setComprobante(comprobante);

                    ap.setReferencia(rs.getString("referencia"));
                    ap.setDocumento(rs.getDouble("documento"));
                    ap.getSocio().setCodigo(rs.getInt("socio"));
                    ap.getSocio().setNombre(rs.getString("nombresocio"));
                    ap.setFecha(rs.getDate("fecha"));
                    ap.getComprobante().setCodigo(rs.getInt("comprobante"));
                    ap.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    ap.setRetencion(rs.getBigDecimal("retencion"));
                    ap.setDescuentos(rs.getBigDecimal("descuentos"));
                    ap.setImporte(rs.getBigDecimal("importe"));
                    ap.setSaldo(rs.getBigDecimal("saldo"));
                    lista.add(ap);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public saldo_aporte insertarAporte(saldo_aporte ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO saldos_aportes (referencia,documento,socio,fecha,comprobante,importe,retencion,descuentos,saldo) VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getReferencia());
        ps.setDouble(2, ocr.getDocumento());
        ps.setInt(3, ocr.getSocio().getCodigo());
        ps.setDate(4, ocr.getFecha());
        ps.setInt(5, ocr.getComprobante().getCodigo());
        ps.setBigDecimal(6, ocr.getImporte());
        ps.setBigDecimal(7, ocr.getRetencion());
        ps.setBigDecimal(8, ocr.getDescuentos());
        ps.setBigDecimal(9, ocr.getSaldo());
        int rowsUpdated = ps.executeUpdate();

        st.close();
        ps.close();
        return ocr;
    }

    public saldo_aporte actualizarAporte(saldo_aporte ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE saldos_aportes"
                + " SET socio=?,fecha=?,comprobante=?,"
                + "importe=?,retencion=?,descuentos=?,"
                + "saldo=? WHERE documento= " + ocr.getDocumento());
        ps.setInt(1, ocr.getSocio().getCodigo());
        ps.setDate(2, ocr.getFecha());
        ps.setInt(3, ocr.getComprobante().getCodigo());
        ps.setBigDecimal(4, ocr.getImporte());
        ps.setBigDecimal(5, ocr.getRetencion());
        ps.setBigDecimal(6, ocr.getDescuentos());
        ps.setBigDecimal(7, ocr.getSaldo());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        return ocr;
    }

    public boolean borrarAporte(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM saldos_aportes WHERE documento=?");
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

    public saldo_aporte buscarAporte(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        saldo_aporte ap = new saldo_aporte();

        try {
            String sql = "SELECT saldos_aportes.referencia,saldos_aportes.documento,saldos_aportes.socio,clientes.nombre AS nombresocio,saldos_aportes.fecha,"
                    + "saldos_aportes.comprobante,comprobantes.nombre AS nombrecomprobante,saldos_aportes.retencion,saldos_aportes.descuentos,"
                    + " saldos_aportes.importe, saldos_aportes.saldo"
                    + " FROM saldos_aportes "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=saldos_aportes.socio "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=saldos_aportes.comprobante "
                    + " WHERE saldos_aportes.documento= ?"
                    + " ORDER BY saldos_aportes.documento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    ap.setSocio(cliente);
                    ap.setComprobante(comprobante);

                    ap.setReferencia(rs.getString("referencia"));
                    ap.setDocumento(rs.getDouble("documento"));
                    ap.getSocio().setCodigo(rs.getInt("socio"));
                    ap.getSocio().setNombre(rs.getString("nombresocio"));
                    ap.setFecha(rs.getDate("fecha"));
                    ap.getComprobante().setCodigo(rs.getInt("comprobante"));
                    ap.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    ap.setRetencion(rs.getBigDecimal("retencion"));
                    ap.setDescuentos(rs.getBigDecimal("descuentos"));
                    ap.setImporte(rs.getBigDecimal("importe"));
                    ap.setSaldo(rs.getBigDecimal("saldo"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return ap;
    }

    public ArrayList<saldo_aporte> MostrarSaldoxFecha(int nSocio1, int nSocio2, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<saldo_aporte> lista = new ArrayList<saldo_aporte>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT saldos_aportes.referencia,saldos_aportes.documento,saldos_aportes.socio,clientes.nombre AS nombresocio,saldos_aportes.fecha,"
                    + "saldos_aportes.comprobante,comprobantes.nombre AS nombrecomprobante,saldos_aportes.retencion,saldos_aportes.descuentos,"
                    + " saldos_aportes.importe, saldos_aportes.saldo"
                    + " FROM saldos_aportes "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=saldos_aportes.socio "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=saldos_aportes.comprobante "
                    + " WHERE saldos_aportes.fecha BETWEEN ? AND ? and saldos_aportes.importe<>0 "
                    + " ORDER BY saldos_aportes.fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();

                    saldo_aporte ap = new saldo_aporte();

                    ap.setSocio(cliente);
                    ap.setComprobante(comprobante);

                    ap.setReferencia(rs.getString("referencia"));
                    ap.setDocumento(rs.getDouble("documento"));
                    ap.getSocio().setCodigo(rs.getInt("socio"));
                    ap.getSocio().setNombre(rs.getString("nombresocio"));
                    ap.setFecha(rs.getDate("fecha"));
                    ap.getComprobante().setCodigo(rs.getInt("comprobante"));
                    ap.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    ap.setRetencion(rs.getBigDecimal("retencion"));
                    ap.setDescuentos(rs.getBigDecimal("descuentos"));
                    ap.setImporte(rs.getBigDecimal("importe"));
                    ap.setSaldo(rs.getBigDecimal("saldo"));
                    lista.add(ap);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<saldo_aporte> MostrarSaldoaunaFecha(int nSocio1, int nSocio2, Date fechaini) throws SQLException {
        ArrayList<saldo_aporte> lista = new ArrayList<saldo_aporte>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT saldos_aportes.referencia,saldos_aportes.documento,saldos_aportes.socio,clientes.nombre AS nombresocio,saldos_aportes.fecha,"
                    + "saldos_aportes.comprobante,comprobantes.nombre AS nombrecomprobante,saldos_aportes.retencion,saldos_aportes.descuentos,"
                    + "saldos_aportes.importe, SUM(saldos_aportes.importe) AS totalaporte "
                    + " FROM saldos_aportes "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=saldos_aportes.socio "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=saldos_aportes.comprobante "
                    + " WHERE IF(?<>0,saldos_aportes.socio=?,TRUE) "
                    + " AND saldos_aportes.fecha<= ? "
                    + " GROUP BY saldos_aportes.socio "
                    + " ORDER BY saldos_aportes.socio ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nSocio1);
                ps.setInt(2, nSocio2);
                ps.setDate(3, fechaini);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();

                    saldo_aporte ap = new saldo_aporte();

                    ap.setSocio(cliente);
                    ap.setComprobante(comprobante);

                    ap.setReferencia(rs.getString("referencia"));
                    ap.setDocumento(rs.getDouble("documento"));
                    ap.getSocio().setCodigo(rs.getInt("socio"));
                    ap.getSocio().setNombre(rs.getString("nombresocio"));
                    ap.setFecha(rs.getDate("fecha"));
                    ap.getComprobante().setCodigo(rs.getInt("comprobante"));
                    ap.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    ap.setRetencion(rs.getBigDecimal("retencion"));
                    ap.setDescuentos(rs.getBigDecimal("descuentos"));
                    ap.setImporte(rs.getBigDecimal("importe"));
                    ap.setSaldo(rs.getBigDecimal("totalaporte"));
                    lista.add(ap);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

}

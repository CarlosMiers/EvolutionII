/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.Config;
import Conexion.Conexion;
import Modelo.caja;
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
public class cajaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<caja> todos() throws SQLException {
        ArrayList<caja> lista = new ArrayList<caja>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT cajas.codigo,cajas.nombre,cajas.expedicion,"
                + "cajas.responsable,cajas.factura,cajas.factura,"
                + "cajas.timbrado,cajas.vencetimbrado,cajas.estado, "
                + "cajas.recibo,cajas.iniciotimbrado "
                + " FROM cajas "
                + " ORDER BY cajas.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                caja caj = new caja();
                caj.setCodigo(rs.getInt("codigo"));
                caj.setNombre(rs.getString("nombre"));
                caj.setResponsable(rs.getString("responsable"));
                caj.setFactura(rs.getDouble("factura"));
                caj.setTimbrado(rs.getInt("timbrado"));
                caj.setVencetimbrado(rs.getDate("vencetimbrado"));
                caj.setEstado(rs.getInt("estado"));
                caj.setRecibo(rs.getDouble("recibo"));
                caj.setIniciotimbrado(rs.getDate("iniciotimbrado"));
                caj.setExpedicion(rs.getString("expedicion"));
                lista.add(caj);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }


    public ArrayList<caja> todosUsuario(int ncaja) throws SQLException {
        ArrayList<caja> lista = new ArrayList<caja>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT cajas.codigo,cajas.nombre,cajas.expedicion,"
                + "cajas.responsable,cajas.factura,cajas.factura,"
                + "cajas.timbrado,cajas.vencetimbrado,cajas.estado, "
                + "cajas.recibo,cajas.iniciotimbrado "
                + " FROM cajas "
                + " WHERE cajas.codigo="+ncaja
                + " ORDER BY cajas.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                caja caj = new caja();
                caj.setCodigo(rs.getInt("codigo"));
                caj.setNombre(rs.getString("nombre"));
                caj.setResponsable(rs.getString("responsable"));
                caj.setFactura(rs.getDouble("factura"));
                caj.setTimbrado(rs.getInt("timbrado"));
                caj.setVencetimbrado(rs.getDate("vencetimbrado"));
                caj.setEstado(rs.getInt("estado"));
                caj.setRecibo(rs.getDouble("recibo"));
                caj.setIniciotimbrado(rs.getDate("iniciotimbrado"));
                caj.setExpedicion(rs.getString("expedicion"));
                lista.add(caj);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }




    public caja buscarId(int id) throws SQLException {
        caja caj = new caja();
        caj.setCodigo(0);
        caj.setNombre("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "select * "
                    + "from cajas "
                    + "where cajas.codigo = ? "
                    + "order by cajas.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    caj.setCodigo(rs.getInt("codigo"));
                    caj.setNombre(rs.getString("nombre"));
                    caj.setResponsable(rs.getString("responsable"));
                    caj.setFactura(rs.getDouble("factura"));
                    caj.setTimbrado(rs.getInt("timbrado"));
                    caj.setVencetimbrado(rs.getDate("vencetimbrado"));
                    caj.setEstado(rs.getInt("estado"));
                    caj.setRecibo(rs.getDouble("recibo"));
                    caj.setIniciotimbrado(rs.getDate("iniciotimbrado"));
                    caj.setExpedicion(rs.getString("expedicion"));
                    caj.setNombrefactura(rs.getString("nombrefactura"));
                    caj.setImpresoracaja(rs.getString("impresoracaja"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return caj;
    }

    public caja buscarIdUsuario(int id) throws SQLException {
        int nCajero = Integer.valueOf(Config.cMenuImportaciones);
        caja caj = new caja();
        caj.setCodigo(0);
        caj.setNombre("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "select * "
                    + "from cajas "
                    + "where cajas.codigo = ? "
                    + " AND cajas.codigo in ("+ nCajero+")"
                    + "order by cajas.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    caj.setCodigo(rs.getInt("codigo"));
                    caj.setNombre(rs.getString("nombre"));
                    caj.setResponsable(rs.getString("responsable"));
                    caj.setFactura(rs.getDouble("factura"));
                    caj.setTimbrado(rs.getInt("timbrado"));
                    caj.setVencetimbrado(rs.getDate("vencetimbrado"));
                    caj.setEstado(rs.getInt("estado"));
                    caj.setRecibo(rs.getDouble("recibo"));
                    caj.setIniciotimbrado(rs.getDate("iniciotimbrado"));
                    caj.setExpedicion(rs.getString("expedicion"));
                    caj.setNombrefactura(rs.getString("nombrefactura"));
                    caj.setImpresoracaja(rs.getString("impresoracaja"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return caj;
    }


    public caja insertarCaja(caja g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cajas (nombre,responsable,factura,timbrado,iniciotimbrado,vencetimbrado,ipcaja,recibo,estado,expedicion) VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, g.getNombre());
        ps.setString(2, g.getResponsable());
        ps.setDouble(3, g.getFactura());
        ps.setDouble(4, g.getTimbrado());
        ps.setDate(5, g.getIniciotimbrado());
        ps.setDate(6, g.getVencetimbrado());
        ps.setString(7, g.getIpcaja());
        ps.setDouble(8, g.getRecibo());
        ps.setInt(9, g.getEstado());
        ps.setString(10, g.getExpedicion());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return g;
    }

    public boolean actualizarCaja(caja g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cajas SET nombre=?,"
                + "responsable=?,factura=?,timbrado=?,"
                + "iniciotimbrado=?,vencetimbrado=?,ipcaja=?,"
                + "recibo=?,estado=?,expedicion=? WHERE codigo=" + g.getCodigo());
        ps.setString(1, g.getNombre());
        ps.setString(2, g.getResponsable());
        ps.setDouble(3, g.getFactura());
        ps.setDouble(4, g.getTimbrado());
        ps.setDate(5, g.getIniciotimbrado());
        ps.setDate(6, g.getVencetimbrado());
        ps.setString(7, g.getIpcaja());
        ps.setDouble(8, g.getRecibo());
        ps.setInt(9, g.getEstado());
        ps.setString(10, g.getExpedicion());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarCaja(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cajas WHERE codigo=?");
        ps.setInt(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ActualizarFacturaCaja(int caja, Double factura) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE cajas SET factura=? WHERE codigo=?");
        ps.setDouble(1, factura);
        ps.setInt(2, caja);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ActualizarReciboCaja(int caja, Double recibo) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE cajas SET recibo=? WHERE codigo=?");
        ps.setDouble(1, recibo);
        ps.setInt(2, caja);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean GrabarConfiguracionImpresora(caja caj) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cajas "
                + " SET nombrefactura=?,"
                + "impresoracaja=? WHERE codigo= " + caj.getCodigo());
        ps.setString(1, caj.getNombrefactura());
        ps.setString(2, caj.getImpresoracaja());
        System.out.println("PASE POR AQUI");
        int rowsUpdated = ps.executeUpdate();

        try {
            st.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Falla--> " + ex.getLocalizedMessage());
        }

        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

}

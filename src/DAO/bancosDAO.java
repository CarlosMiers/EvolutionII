/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.banco;
import Modelo.extraccion;
import Modelo.moneda;
import Modelo.plan;
import Modelo.sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class bancosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<extraccion> MostrarxFecha(Date fechaini, Date fechafin, int tipo) throws SQLException {
        ArrayList<extraccion> lista = new ArrayList<extraccion>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT extracciones.idcontrol,extracciones.documento,extracciones.fecha,";
            cSql = cSql + "extracciones.sucursal,extracciones.banco,extracciones.tipo,extracciones.moneda,extracciones.importe,";
            cSql = cSql + "extracciones.observaciones,extracciones.chequenro,extracciones.vencimiento ";
            cSql = cSql + " FROM extracciones ";
            cSql = cSql + " LEFT JOIN sucursales";
            cSql = cSql + " ON sucursales.codigo=extracciones.sucursal";
            cSql = cSql + " LEFT JOIN bancos";
            cSql = cSql + " ON bancos.codigo=extracciones.banco ";
            cSql = cSql + " WHERE exracciones.fecha BETWEEN ? AND ? ";
            cSql = cSql + " ORDER BY extracciones.idcontrol ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechafin);
                ps.setDate(2, fechafin);
                ps.setInt(3, tipo);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    extraccion ext = new extraccion();
                    moneda moneda = new moneda();

                    ext.setSucursal(sucursal);
                    ext.setMoneda(moneda);

                    ext.setIdcontrol(rs.getInt("idcontrol"));
                    ext.setDocumento(rs.getString("documento"));
                    ext.setFecha(rs.getDate("fecha"));
                    ext.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ext.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ext.getBanco().setCodigo(rs.getInt("banco"));
                    ext.getBanco().setNombre(rs.getString("nombrebanco"));
                    ext.setTipo(rs.getString("tipo"));
                    ext.getMoneda().setCodigo(rs.getInt("moneda"));
                    ext.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ext.setImporte(rs.getBigDecimal("importe"));
                    ext.setObservaciones(rs.getString("observaciones"));
                    ext.setChequenro(rs.getString("chequenro"));
                    ext.setVencimiento(rs.getDate("vencimiento"));
                    lista.add(ext);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<banco> todos() throws SQLException {
        ArrayList<banco> lista = new ArrayList<banco>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT bancos.codigo,bancos.nombre,bancos.nrocuenta,bancos.contacto,bancos.direccion,"
                + "bancos.telefono,bancos.mail,bancos.idcuenta,bancos.reporte,bancos.tipo,bancos.estado,"
                + "bancos.ctapropia,plan.nombre AS nombrecuenta "
                + " FROM bancos "
                + " LEFT JOIN plan "
                + " ON plan.codigo=bancos.idcuenta "
                + " ORDER BY bancos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                banco ba = new banco();
                plan pl = new plan();
                ba.setIdcuenta(pl);

                ba.setCodigo(rs.getInt("codigo"));
                ba.setNombre(rs.getString("nombre"));
                ba.setDireccion(rs.getString("direccion"));
                ba.setTelefono(rs.getString("telefono"));
                ba.setNrocuenta(rs.getString("nrocuenta"));
                ba.getIdcuenta().setCodigo(rs.getString("idcuenta"));
                ba.getIdcuenta().setNombre(rs.getString("nombrecuenta"));
                lista.add(ba);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public banco buscarId(int id) throws SQLException {

        banco b = new banco();
        b.setCodigo(0);
        b.setNombre("");
        b.setContacto("");
        b.setDireccion("");
        b.setTelefono("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT bancos.codigo,bancos.nombre,bancos.nrocuenta,bancos.contacto,bancos.direccion,"
                    + "bancos.telefono,bancos.mail,bancos.idcuenta,bancos.reporte,bancos.tipo,bancos.estado,"
                    + "bancos.ctapropia,plan.nombre AS nombrecuenta "
                    + " FROM bancos "
                    + " LEFT JOIN plan "
                    + " ON plan.codigo=bancos.idcuenta "
                    + " WHERE bancos.codigo=?  "
                    + " ORDER BY bancos.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    plan pl = new plan();
                    b.setIdcuenta(pl);
                    b.setCodigo(rs.getInt("codigo"));
                    b.setNombre(rs.getString("nombre"));
                    b.setDireccion(rs.getString("direccion"));
                    b.setTelefono(rs.getString("telefono"));
                    b.getIdcuenta().setCodigo(rs.getString("idcuenta"));
                    b.getIdcuenta().setNombre(rs.getString("nombrecuenta"));
                    b.setContacto(rs.getString("contacto"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return b;
    }

    public ArrayList<extraccion> ResumenExtracto(Date fechaini, Date fechafin, int nBanco, int nBan, String cTipo) throws SQLException {
        ArrayList<extraccion> lista = new ArrayList<extraccion>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT extracciones.idcontrol,extracciones.documento,extracciones.fecha,"
                    + "extracciones.sucursal,sucursales.nombre AS nombresucursal, extracciones.banco,"
                    + "bancos.nombre AS nombrebanco,extracciones.tipo,extracciones.moneda,monedas.nombre AS nombremoneda,"
                    + "extracciones.importe,extracciones.observaciones,extracciones.chequenro,extracciones.vencimiento "
                    + " FROM extracciones "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=extracciones.sucursal "
                    + " LEFT JOIN bancos "
                    + " ON bancos.codigo=extracciones.banco "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=extracciones.moneda "
                    + " WHERE extracciones.fecha BETWEEN ? AND ? "
                    + " AND IF(?<>0,extracciones.banco=?,TRUE) "
                    + " AND extracciones.tipo=? "
                    + " ORDER BY extracciones.idcontrol ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nBanco);
                ps.setInt(4, nBan);
                ps.setString(5, cTipo);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    moneda moneda = new moneda();
                    banco ban = new banco();

                    extraccion ext = new extraccion();

                    ext.setSucursal(sucursal);
                    ext.setMoneda(moneda);
                    ext.setBanco(ban);

                    ext.setIdcontrol(rs.getInt("idcontrol"));
                    ext.setDocumento(rs.getString("documento"));
                    ext.setFecha(rs.getDate("fecha"));
                    ext.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ext.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ext.getBanco().setCodigo(rs.getInt("banco"));
                    ext.getBanco().setNombre(rs.getString("nombrebanco"));
                    ext.setTipo(rs.getString("tipo"));
                    ext.getMoneda().setCodigo(rs.getInt("moneda"));
                    ext.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ext.setImporte(rs.getBigDecimal("importe"));
                    ext.setObservaciones(rs.getString("observaciones"));
                    ext.setChequenro(rs.getString("chequenro"));
                    ext.setVencimiento(rs.getDate("vencimiento"));
                    lista.add(ext);
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public boolean EnlaceContableBanco(banco g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE bancos SET idcuenta=? WHERE codigo=" + g.getCodigo());
        ps.setString(1, g.getIdcuenta().getCodigo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

        public boolean borrarMovimiento(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM extracciones WHERE idmovimiento=?");
        ps.setString(1,id);
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

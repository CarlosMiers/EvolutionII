/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.banco;
import Modelo.cliente;
import Modelo.disponibilidad;
import Modelo.moneda;
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
public class disponibilidadDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<disponibilidad> todos(String cTipo, Date dFechaIni, Date dFechaFin) throws SQLException {
        ArrayList<disponibilidad> lista = new ArrayList<disponibilidad>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT numero,disponibilidades.fecha,destino,cliente,"
                + "moneda,totales,cotizacionmoneda,observaciones,"
                + "asiento,referencia,usuario,"
                + "bancos.nombre AS nombrebanco,"
                + "clientes.nombre AS nombrecliente, "
                + "monedas.nombre AS nombremoneda "
                + "FROM disponibilidades "
                + "LEFT JOIN bancos "
                + "ON bancos.codigo=disponibilidades.destino "
                + "LEFT JOIN monedas "
                + "ON monedas.codigo=disponibilidades.moneda "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=disponibilidades.cliente "
                + " WHERE disponibilidades.tipo=? "
                + " AND disponibilidades.fecha between ? AND ? "
                + " ORDER BY disponibilidades.numero ";

        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setString(1, cTipo);
            ps.setDate(2, dFechaIni);
            ps.setDate(3, dFechaFin);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                disponibilidad va = new disponibilidad();
                banco ba = new banco();
                cliente cl = new cliente();
                moneda mon = new moneda();
                va.setDestino(ba);
                va.setCliente(cl);
                va.setMoneda(mon);
                va.setNumero(rs.getDouble("numero"));
                va.setFecha(rs.getDate("fecha"));
                va.setTotales(rs.getDouble("totales"));
                va.setCotizacionmoneda(rs.getDouble("cotizacionmoneda"));
                va.setObservaciones(rs.getString("observaciones"));
                va.setAsiento(rs.getDouble("asiento"));
                va.getDestino().setCodigo(rs.getInt("destino"));
                va.getDestino().setNombre(rs.getString("nombrebanco"));
                va.getCliente().setCodigo(rs.getInt("cliente"));
                va.getCliente().setNombre(rs.getString("nombrecliente"));
                va.getMoneda().setCodigo(rs.getInt("moneda"));
                va.getMoneda().setNombre(rs.getString("nombremoneda"));
                lista.add(va);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public disponibilidad buscarId(double id) throws SQLException {
        disponibilidad va = new disponibilidad();
        banco ba = new banco();
        cliente cl = new cliente();
        moneda mon = new moneda();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT numero,disponibilidades.fecha,destino,cliente,"
                    + "moneda,totales,cotizacionmoneda,observaciones,"
                    + "asiento,referencia,usuario,"
                    + "bancos.nombre AS nombrebanco,"
                    + "clientes.nombre AS nombrecliente, "
                    + "monedas.nombre AS nombremoneda "
                    + "FROM disponibilidades "
                    + " LEFT JOIN bancos "
                    + " ON bancos.codigo=disponibilidades.destino "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=disponibilidades.moneda "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=disponibilidades.cliente "
                    + " WHERE disponibilidades.numero= ? "
                    + " ORDER BY disponibilidades.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    va.setDestino(ba);
                    va.setCliente(cl);
                    va.setMoneda(mon);
                    va.setNumero(rs.getDouble("numero"));
                    va.setFecha(rs.getDate("fecha"));
                    va.setTotales(rs.getDouble("totales"));
                    va.setCotizacionmoneda(rs.getDouble("cotizacionmoneda"));
                    va.setObservaciones(rs.getString("observaciones"));
                    va.setAsiento(rs.getDouble("asiento"));
                    va.getDestino().setCodigo(rs.getInt("destino"));
                    va.getDestino().setNombre(rs.getString("nombrebanco"));
                    va.getCliente().setCodigo(rs.getInt("cliente"));
                    va.getCliente().setNombre(rs.getString("nombrecliente"));
                    va.getMoneda().setCodigo(rs.getInt("moneda"));
                    va.getMoneda().setNombre(rs.getString("nombremoneda"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return va;
    }

    public disponibilidad insertar(disponibilidad ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO disponibilidades "
                + "(fecha,destino,cliente,"
                + "moneda,totales,cotizacionmoneda,"
                + "observaciones,usuario,tipo)"
                + "VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setDate(1, ca.getFecha());
        ps.setInt(2, ca.getDestino().getCodigo());
        ps.setInt(3, ca.getCliente().getCodigo());
        ps.setInt(4, ca.getMoneda().getCodigo());
        ps.setDouble(5, ca.getTotales());
        ps.setDouble(6, ca.getCotizacionmoneda());
        ps.setString(7, ca.getObservaciones());
        ps.setInt(8, ca.getUsuario());
        ps.setString(9, ca.getTipo());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizar(disponibilidad ca) throws SQLException {
        int rowsUpdated = 0;
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE disponibilidades "
                + " SET fecha=?,destino=?,cliente=?,"
                + "moneda=?,totales=?,cotizacionmoneda=?,"
                + "observaciones=?,usuario=? WHERE numero=" + ca.getNumero());
        ps.setDate(1, ca.getFecha());
        ps.setInt(2, ca.getDestino().getCodigo());
        ps.setInt(3, ca.getCliente().getCodigo());
        ps.setInt(4, ca.getMoneda().getCodigo());
        ps.setDouble(5, ca.getTotales());
        ps.setDouble(6, ca.getCotizacionmoneda());
        ps.setString(7, ca.getObservaciones());
        ps.setInt(8, ca.getUsuario());
        try {
            rowsUpdated = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminar(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM disponibilidades WHERE numero=?");
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

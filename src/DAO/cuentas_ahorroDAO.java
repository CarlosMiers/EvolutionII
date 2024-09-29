/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.cuentas_ahorro;
import Modelo.moneda;
import Modelo.tipo_ahorro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class cuentas_ahorroDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cuentas_ahorro> todos() throws SQLException {
        ArrayList<cuentas_ahorro> lista = new ArrayList<cuentas_ahorro>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT cuenta,socio,tipoahorro,"
                + "tipocuenta,moneda,fechaapertura"
                + "enviocorreo,firmas,"
                + "caucion,sobregiro,"
                + "estado,tipo_ahorros.nombre AS nombreahorro,"
                + "clientes.nombre AS nombresocio,"
                + "monedas.nombre AS nombremoneda "
                + "FROM cuentas_ahorro "
                + "LEFT JOIN tipo_ahorros "
                + "ON tipo_ahorros.codigo=cuentas_ahorro.tipoahorro "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=cuentas_ahorro.socio "
                + "LEFT JOIN monedas "
                + "ON monedas.codigo=cuentas_ahorro.moneda "
                + "ORDER BY cuentas_ahorro.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cuentas_ahorro ca = new cuentas_ahorro();
                cliente cl = new cliente();
                moneda mn = new moneda();
                tipo_ahorro tp = new tipo_ahorro();

                ca.setSocio(cl);
                ca.setMoneda(mn);
                ca.setTipoahorro(tp);
                ca.setCuenta(rs.getInt("cuenta"));
                ca.setFechaapertura(rs.getDate("fechaapertura"));
                ca.setTipocuenta(rs.getInt("tipocuenta"));
                ca.setTipocuenta(rs.getInt("tipocuenta"));
                ca.setFirmas(rs.getInt("firmas"));
                ca.setEnviocorreo(rs.getInt("enviocorreo"));
                ca.setSobregiro(rs.getDouble("sobregiro"));
                ca.setCaucion(rs.getDouble("caucion"));
                ca.setEstado(rs.getInt("estado"));
                ca.getSocio().setCodigo(rs.getInt("socio"));
                ca.getSocio().setNombre(rs.getString("nombresocio"));
                ca.getMoneda().setCodigo(rs.getInt("moneda"));
                ca.getMoneda().setNombre(rs.getString("nombremoneda"));
                ca.getTipoahorro().setCodigo(rs.getInt("tipoahorro"));
                ca.getTipoahorro().setNombre(rs.getString("nombreahorro"));
                lista.add(ca);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public cuentas_ahorro buscarId(int id) throws SQLException {

        cuentas_ahorro ca = new cuentas_ahorro();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT cuenta,socio,tipoahorro,"
                    + "tipocuenta,moneda,fechaapertura"
                    + "enviocorreo,firmas,"
                    + "caucion,sobregiro,"
                    + "estado,tipo_ahorros.nombre AS nombreahorro,"
                    + "clientes.nombre AS nombresocio,"
                    + "monedas.nombre AS nombremoneda "
                    + "FROM cuentas_ahorro "
                    + "LEFT JOIN tipo_ahorros "
                    + "ON tipo_ahorros.codigo=cuentas_ahorro.tipoahorro "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cuentas_ahorro.socio "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=cuentas_ahorro.moneda "
                    + "WHERE cuentas_ahorro.cuenta = ? "
                    + "ORDER BY cuentas_ahorro.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente cl = new cliente();
                    moneda mn = new moneda();
                    tipo_ahorro tp = new tipo_ahorro();

                    ca.setSocio(cl);
                    ca.setMoneda(mn);
                    ca.setTipoahorro(tp);
                    ca.setCuenta(rs.getInt("cuenta"));
                    ca.setFechaapertura(rs.getDate("fechaapertura"));
                    ca.setTipocuenta(rs.getInt("tipocuenta"));
                    ca.setTipocuenta(rs.getInt("tipocuenta"));
                    ca.setFirmas(rs.getInt("firmas"));
                    ca.setEnviocorreo(rs.getInt("enviocorreo"));
                    ca.setSobregiro(rs.getDouble("sobregiro"));
                    ca.setCaucion(rs.getDouble("caucion"));
                    ca.setEstado(rs.getInt("estado"));
                    ca.getSocio().setCodigo(rs.getInt("socio"));
                    ca.getSocio().setNombre(rs.getString("nombresocio"));
                    ca.getMoneda().setCodigo(rs.getInt("moneda"));
                    ca.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ca.getTipoahorro().setCodigo(rs.getInt("tipoahorro"));
                    ca.getTipoahorro().setNombre(rs.getString("nombreahorro"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return ca;
    }

    public cuentas_ahorro insertarCuenta(cuentas_ahorro ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cuentas_ahorro "
                + " (socio,tipoahorro,fechaapertura,tipocuenta,"
                + "moneda,enviocorreo,firmas,"
                + "caucion,sobregiro,estado) VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, ca.getSocio().getCodigo());
        ps.setInt(2, ca.getTipoahorro().getCodigo());
        ps.setDate(3, ca.getFechaapertura());
        ps.setInt(4, ca.getTipocuenta());
        ps.setInt(5, ca.getMoneda().getCodigo());
        ps.setInt(6, ca.getEnviocorreo());
        ps.setInt(7, ca.getFirmas());
        ps.setDouble(8, ca.getCaucion());
        ps.setDouble(9, ca.getSobregiro());
        ps.setInt(10, ca.getEstado());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarCuentas(cuentas_ahorro ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cuentas_ahorro"
                + " SET cuenta=?,socio=?,tipoahorro=?,fechaapertura=?,"
                + "tipocuenta=?,moneda=?,enviocorreo=?,firmas=?,"
                + "caucion=?,sobregiro=?,estado=? WHERE cuenta=" + ca.getCuenta());
        ps.setInt(1, ca.getSocio().getCodigo());
        ps.setInt(2, ca.getTipoahorro().getCodigo());
        ps.setDate(3, ca.getFechaapertura());
        ps.setInt(4, ca.getTipocuenta());
        ps.setInt(5, ca.getMoneda().getCodigo());
        ps.setInt(6, ca.getEnviocorreo());
        ps.setInt(7, ca.getFirmas());
        ps.setDouble(8, ca.getCaucion());
        ps.setDouble(9, ca.getSobregiro());
        ps.setInt(10, ca.getEstado());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarCuenta(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuentas_ahorro WHERE cuenta=?");
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

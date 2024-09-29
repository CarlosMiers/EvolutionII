/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.casa;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.cuenta_casascomerciales;
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
public class cuenta_casacomercialDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cuenta_casascomerciales> VencexCasas(int ncasa, Date fechai, Date fechaf) throws SQLException {
        ArrayList<cuenta_casascomerciales> lista = new ArrayList<cuenta_casascomerciales>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.documento,casas.nombre AS nombrecomercio,cuenta_clientes.fecha,cuenta_clientes.vencimiento,";
            cSql = cSql + "cuenta_clientes.numerocuota,cuenta_clientes.cuota,cuenta_clientes.comprobante,";
            cSql = cSql + "cuenta_clientes.comercial,cuenta_clientes.importe,comprobantes.nombre AS nombrecomprobante";
            cSql = cSql + " FROM cuenta_clientes ";
            cSql = cSql + " LEFT JOIN casas ";
            cSql = cSql + " ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + " LEFT JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + " WHERE cuenta_clientes.comercial=? ";
            cSql = cSql + " AND cuenta_clientes.vencimiento BETWEEN ? AND ?  ";
            cSql = cSql + " ORDER BY casas.codigo,cuenta_clientes.vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ncasa);
                ps.setDate(2, fechai);
                ps.setDate(3, fechaf);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    casa c = new casa();
                    comprobante m = new comprobante();
                    cuenta_casascomerciales cc = new cuenta_casascomerciales();
                    cc.setComercial(c);
                    cc.setComprobante(m);
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getDouble("importe"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercio"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }
    
        public ArrayList<cuenta_casascomerciales> Vencimientos(int ncasa,int ntipo,int anual,int mensual) throws SQLException {
        ArrayList<cuenta_casascomerciales> lista = new ArrayList<cuenta_casascomerciales>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.documento,casas.nombre AS nombrecomercio,"
                    + "cuenta_clientes.fecha,cuenta_clientes.vencimiento,";
            cSql = cSql + "cuenta_clientes.numerocuota,cuenta_clientes.cuota,"
                    + "cuenta_clientes.comprobante,clientes.nombre as nombrecliente,cuenta_clientes.cliente,";
            cSql = cSql + "cuenta_clientes.comercial,cuenta_clientes.importe,"
                    + "comprobantes.nombre AS nombrecomprobante,cuenta_clientes.iddocumento ";
            cSql = cSql + " FROM cuenta_clientes ";
            cSql = cSql + " LEFT JOIN casas ";
            cSql = cSql + " ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + " LEFT JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + " LEFT JOIN clientes ";
            cSql = cSql + " ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + " WHERE cuenta_clientes.comercial=? ";
            cSql = cSql + " AND cuenta_clientes.comprobante=? ";
            cSql = cSql + " AND YEAR(cuenta_clientes.vencimiento)=? ";
            cSql = cSql + " AND MONTH(cuenta_clientes.vencimiento)=? ";
            cSql = cSql + " ORDER BY cuenta_clientes.documento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ncasa);
                ps.setInt(2, ntipo);
                ps.setInt(3, anual);
                ps.setInt(4, mensual);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    casa c = new casa();
                    comprobante m = new comprobante();
                    cliente cl = new cliente();
                    cuenta_casascomerciales cc = new cuenta_casascomerciales();
                    cc.setComercial(c);
                    cc.setComprobante(m);
                    cc.setCliente(cl);
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getDouble("importe"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercio"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }
    

    public ArrayList<cuenta_casascomerciales> ResumenVencexCasas(int ncomprobante, Date fechai, Date fechaf) throws SQLException {
        ArrayList<cuenta_casascomerciales> lista = new ArrayList<cuenta_casascomerciales>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.documento,casas.nombre AS nombrecomercio,cuenta_clientes.fecha,cuenta_clientes.vencimiento,";
            cSql = cSql + "cuenta_clientes.numerocuota,cuenta_clientes.cuota,cuenta_clientes.comprobante,";
            cSql = cSql + "cuenta_clientes.comercial,SUM(cuenta_clientes.importe) AS totaldeuda,comprobantes.nombre AS nombrecomprobante";
            cSql = cSql + " FROM cuenta_clientes ";
            cSql = cSql + " LEFT JOIN casas ";
            cSql = cSql + " ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + " LEFT JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + " WHERE cuenta_clientes.comprobante=? ";
            cSql = cSql + " AND cuenta_clientes.vencimiento BETWEEN ? AND ?  ";
            cSql = cSql + " GROUP BY cuenta_clientes.comercial ";
            cSql = cSql + " ORDER BY casas.codigo ";

            System.out.println("--> " +cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ncomprobante);
                ps.setDate(2, fechai);
                ps.setDate(3, fechaf);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    casa c = new casa();
                    comprobante m = new comprobante();
                    cuenta_casascomerciales cc = new cuenta_casascomerciales();
                    cc.setComercial(c);
                    cc.setComprobante(m);
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getDouble("totaldeuda"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercio"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }
        
        
        
}

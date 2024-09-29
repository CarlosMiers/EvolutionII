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
import Modelo.gestioncobro;
import Modelo.giraduria;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class gestioncobroDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<gestioncobro> MostrarxCliente(int cliente) throws SQLException {
        ArrayList<gestioncobro> lista = new ArrayList<gestioncobro>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT gestion_cobros.iddetalle,gestion_cobros.iddocumento,gestion_cobros.creferencia,";
            cSql = cSql + "gestion_cobros.documento,gestion_cobros.fecha,gestion_cobros.vencimiento,gestion_cobros.fecha_cobro,";
            cSql = cSql + "gestion_cobros.autorizacion,gestion_cobros.cliente,gestion_cobros.sucursal,";
            cSql = cSql + "gestion_cobros.comprobante,gestion_cobros.enviado,gestion_cobros.cobrado,";
            cSql = cSql + "gestion_cobros.giraduria,gestion_cobros.numerocuota,gestion_cobros.cuota,";
            cSql = cSql + "gestion_cobros.amortiza,gestion_cobros.minteres,gestion_cobros.nrocuenta,";
            cSql = cSql + "gestion_cobros.comercial,clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,gestion_cobros.fecha_cobro,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,";
            cSql = cSql + "giradurias.nombre as nombregiraduria ";
            cSql = cSql + "FROM gestion_cobros ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=gestion_cobros.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=gestion_cobros.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=gestion_cobros.giraduria ";
            cSql = cSql + " WHERE AND gestion_cobros.cliente= ? ";
            cSql = cSql + " ORDER by gestion_cobros.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    gestioncobro cc = new gestioncobro();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getDouble("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setFecha_cobro(rs.getDate("fecha_cobro"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setEnviado(rs.getBigDecimal("enviado"));
                    cc.setCobrado(rs.getBigDecimal("cobrado"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
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

    public ArrayList<gestioncobro> MostrarxGiraduria(int ngiraduria, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<gestioncobro> lista = new ArrayList<gestioncobro>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT gestion_cobros.iddetalle,gestion_cobros.iddocumento,gestion_cobros.creferencia,";
            cSql = cSql + "gestion_cobros.documento,gestion_cobros.fecha,gestion_cobros.vencimiento,gestion_cobros.fecha_cobro,";
            cSql = cSql + "gestion_cobros.autorizacion,gestion_cobros.cliente,gestion_cobros.sucursal,";
            cSql = cSql + "gestion_cobros.comprobante,gestion_cobros.enviado,gestion_cobros.cobrado,";
            cSql = cSql + "gestion_cobros.giraduria,gestion_cobros.numerocuota,gestion_cobros.cuota,";
            cSql = cSql + "gestion_cobros.amortiza,gestion_cobros.minteres,gestion_cobros.nrocuenta,";
            cSql = cSql + "gestion_cobros.comercial,clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,gestion_cobros.fecha_cobro,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,";
            cSql = cSql + "giradurias.nombre as nombregiraduria ";
            cSql = cSql + "FROM gestion_cobros ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=gestion_cobros.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=gestion_cobros.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=gestion_cobros.giraduria ";
            cSql = cSql + " AND gestion_cobros.vencimiento between ? AND ?";
            cSql = cSql + " AND gestion_cobros.giraduria= ? ";
            cSql = cSql + " ORDER by gestion_cobros.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ngiraduria);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    gestioncobro cc = new gestioncobro();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getDouble("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setFecha_cobro(rs.getDate("fecha_cobro"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setEnviado(rs.getBigDecimal("enviado"));
                    cc.setCobrado(rs.getBigDecimal("cobrado"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
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

    public ArrayList<gestioncobro> ExtractoxGiraduria(Date fechaini, Date fechafin, int ngiraduria1, int ngiraduria2, int socio1, int socio2) throws SQLException {
        ArrayList<gestioncobro> lista = new ArrayList<gestioncobro>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT gestion_cobros.iddetalle,gestion_cobros.iddocumento,gestion_cobros.creferencia,";
            cSql = cSql + "gestion_cobros.documento,gestion_cobros.fecha,gestion_cobros.vencimiento,gestion_cobros.fecha_cobro,";
            cSql = cSql + "gestion_cobros.autorizacion,gestion_cobros.cliente,gestion_cobros.sucursal,";
            cSql = cSql + "gestion_cobros.comprobante,gestion_cobros.enviado,gestion_cobros.cobrado,";
            cSql = cSql + "gestion_cobros.giraduria,gestion_cobros.numerocuota,gestion_cobros.cuota,";
            cSql = cSql + "gestion_cobros.amortiza,gestion_cobros.minteres,gestion_cobros.nrocuenta,";
            cSql = cSql + "gestion_cobros.comercial,clientes.categoria,clientes.ruc,clientes.nombre as nombrecliente,";
            cSql = cSql + "clientes.telefono, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,";
            cSql = cSql + "giradurias.nombre as nombregiraduria,casas.nombre as nombrecomercial ";
            cSql = cSql + "FROM gestion_cobros ";
            cSql = cSql + "LEFT JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=gestion_cobros.cliente ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=gestion_cobros.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=gestion_cobros.giraduria ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=gestion_cobros.comercial ";
            cSql = cSql + " WHERE gestion_cobros.fecha_cobro between ? AND ?";
            cSql = cSql + " AND IF(?<>0,gestion_cobros.giraduria=?,TRUE) ";
            cSql = cSql + " AND IF(?<>0,gestion_cobros.cliente=?,TRUE) ";
            cSql = cSql + " ORDER by gestion_cobros.giraduria,gestion_cobros.cliente,gestion_cobros.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, ngiraduria1);
                ps.setInt(4, ngiraduria2);
                ps.setInt(5, socio1);
                ps.setInt(6, socio2);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    gestioncobro cc = new gestioncobro();
                    casa casa = new casa();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setComercial(casa);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getDouble("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setFecha_cobro(rs.getDate("fecha_cobro"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.getCliente().setDireccion(rs.getString("direccion"));
                    cc.getCliente().setTelefono(rs.getString("telefono"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setEnviado(rs.getBigDecimal("enviado"));
                    cc.setCobrado(rs.getBigDecimal("cobrado"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercial"));
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

    public ArrayList<gestioncobro> ExtractoxGiraduriaCierre(Date fechaini, Date fechafin, int ngiraduria1, int ngiraduria2, int socio1, int socio2) throws SQLException {
        ArrayList<gestioncobro> lista = new ArrayList<gestioncobro>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String cSql = "SELECT cierre_cobranzas.iddetalle,cierre_cobranzas.iddocumento,cierre_cobranzas.creferencia,";
            cSql = cSql + "cierre_cobranzas.documento,cierre_cobranzas.fecha,cierre_cobranzas.vencimiento,cierre_cobranzas.fecha_cobro,";
            cSql = cSql + "cierre_cobranzas.autorizacion,cierre_cobranzas.cliente,cierre_cobranzas.sucursal,";
            cSql = cSql + "cierre_cobranzas.comprobante,cierre_cobranzas.enviado,cierre_cobranzas.cobrado,";
            cSql = cSql + "cierre_cobranzas.giraduria,cierre_cobranzas.numerocuota,cierre_cobranzas.cuota,";
            cSql = cSql + "cierre_cobranzas.amortiza,cierre_cobranzas.minteres,cierre_cobranzas.nrocuenta,";
            cSql = cSql + "cierre_cobranzas.comercial,clientes.categoria,clientes.ruc,clientes.nombre as nombrecliente,";
            cSql = cSql + "clientes.telefono, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,";
            cSql = cSql + "giradurias.nombre as nombregiraduria,casas.nombre as nombrecomercial ";
            cSql = cSql + "FROM cierre_cobranzas ";
            cSql = cSql + "LEFT JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cierre_cobranzas.cliente ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cierre_cobranzas.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cierre_cobranzas.giraduria ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cierre_cobranzas.comercial ";
            cSql = cSql + " WHERE cierre_cobranzas.fecha_cobro between ? AND ?";
            cSql = cSql + " AND IF(?<>0,cierre_cobranzas.giraduria=?,TRUE) ";
            cSql = cSql + " AND IF(?<>0,cierre_cobranzas.cliente=?,TRUE) ";
            cSql = cSql + " ORDER by cierre_cobranzas.giraduria,cierre_cobranzas.cliente,cierre_cobranzas.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, ngiraduria1);
                ps.setInt(4, ngiraduria2);
                ps.setInt(5, socio1);
                ps.setInt(6, socio2);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    gestioncobro cc = new gestioncobro();
                    casa casa = new casa();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setComercial(casa);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getDouble("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setFecha_cobro(rs.getDate("fecha_cobro"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.getCliente().setDireccion(rs.getString("direccion"));
                    cc.getCliente().setTelefono(rs.getString("telefono"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setEnviado(rs.getBigDecimal("enviado"));
                    cc.setCobrado(rs.getBigDecimal("cobrado"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercial"));
                    lista.add(cc);
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

    public ArrayList<gestioncobro> MostrarxSocioxPlanilla(int cliente, int nplanilla) throws SQLException {
        ArrayList<gestioncobro> lista = new ArrayList<gestioncobro>();
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT gestion_cobros.iddetalle,gestion_cobros.iddocumento,gestion_cobros.creferencia,";
            cSql = cSql + "gestion_cobros.documento,gestion_cobros.fecha,gestion_cobros.vencimiento,gestion_cobros.fecha_cobro,";
            cSql = cSql + "gestion_cobros.autorizacion,gestion_cobros.cliente,gestion_cobros.sucursal,";
            cSql = cSql + "gestion_cobros.comprobante,gestion_cobros.enviado,gestion_cobros.cobrado,";
            cSql = cSql + "gestion_cobros.giraduria,gestion_cobros.numerocuota,gestion_cobros.cuota,";
            cSql = cSql + "gestion_cobros.amortiza,gestion_cobros.minteres,gestion_cobros.nrocuenta,";
            cSql = cSql + "gestion_cobros.comercial,clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,gestion_cobros.fecha_cobro,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,";
            cSql = cSql + "giradurias.nombre as nombregiraduria ";
            cSql = cSql + "FROM gestion_cobros ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=gestion_cobros.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=gestion_cobros.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=gestion_cobros.giraduria ";
            cSql = cSql + " WHERE gestion_cobros.cliente= ? ";
            cSql = cSql + " AND gestion_cobros.iddetalle= ? ";
            cSql = cSql + " ORDER by comprobantes.prioridad ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ps.setInt(2, nplanilla);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    gestioncobro cc = new gestioncobro();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getDouble("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setFecha_cobro(rs.getDate("fecha_cobro"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setEnviado(rs.getBigDecimal("enviado"));
                    cc.setCobrado(rs.getBigDecimal("cobrado"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
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

    public ArrayList<gestioncobro> MostrarxPlanilla(int nplanilla) throws SQLException {
        ArrayList<gestioncobro> lista = new ArrayList<gestioncobro>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT gestion_cobros.iddetalle,gestion_cobros.iddocumento,gestion_cobros.creferencia,";
            cSql = cSql + "gestion_cobros.documento,gestion_cobros.fecha,gestion_cobros.vencimiento,gestion_cobros.fecha_cobro,";
            cSql = cSql + "gestion_cobros.autorizacion,gestion_cobros.cliente,gestion_cobros.sucursal,";
            cSql = cSql + "gestion_cobros.comprobante,gestion_cobros.enviado,gestion_cobros.cobrado,";
            cSql = cSql + "gestion_cobros.giraduria,gestion_cobros.numerocuota,gestion_cobros.cuota,";
            cSql = cSql + "gestion_cobros.amortiza,gestion_cobros.minteres,gestion_cobros.nrocuenta,";
            cSql = cSql + "gestion_cobros.comercial,clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,gestion_cobros.fecha_cobro,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,";
            cSql = cSql + "giradurias.nombre as nombregiraduria ";
            cSql = cSql + "FROM gestion_cobros ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=gestion_cobros.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=gestion_cobros.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=gestion_cobros.giraduria ";
            cSql = cSql + " WHERE gestion_cobros.iddetalle= ? ";
            cSql = cSql + " ORDER by comprobantes.prioridad ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nplanilla);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    casa casa = new casa();
                    gestioncobro cc = new gestioncobro();

                    cc.setGiraduria(giraduria);
                    cc.setComercial(casa);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setIddetalle(rs.getString("iddetalle"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.setDocumento(rs.getDouble("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setFecha_cobro(rs.getDate("fecha_cobro"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setEnviado(rs.getBigDecimal("enviado"));
                    cc.setCobrado(rs.getBigDecimal("cobrado"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
                    lista.add(cc);
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

    public gestioncobro ActualizarDebitoCuenta(gestioncobro cta) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE gestion_cobros SET cobrado=?  WHERE iddocumento= '" + cta.getIddocumento() + "'");
        ps.setBigDecimal(1, cta.getCobrado());
        ps.executeUpdate();
        ps.close();
        st.close();
        conn.close();
        return cta;
    }

    public ArrayList<gestioncobro> ReciboGestion(Date fechaini, Date fechafin, int ngiraduria1) throws SQLException {
        ArrayList<gestioncobro> lista = new ArrayList<gestioncobro>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT gestion_cobros.cliente,";
            cSql = cSql + "clientes.nombre as nombrecliente,";
            cSql = cSql + "SUM(gestion_cobros.cobrado as totalcobro ";
            cSql = cSql + "FROM gestion_cobros ";
            cSql = cSql + "LEFT JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=gestion_cobros.cliente ";
            cSql = cSql + " WHERE gestion_cobros.fecha_cobro between ? AND ?";
            cSql = cSql + " AND gestion_cobros.giraduria=? ";
            cSql = cSql + " GROUP BY gestion_cobros.cliente=? ";
            cSql = cSql + " ORDER by gestion_cobros.giraduria,gestion_cobros.cliente";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, ngiraduria1);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    gestioncobro cc = new gestioncobro();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.setCobrado(rs.getBigDecimal("totalcobro"));
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

    public ArrayList<gestioncobro> ReciboCierre(Date fechaini, Date fechafin, int ngiraduria1) throws SQLException {
        ArrayList<gestioncobro> lista = new ArrayList<gestioncobro>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String cSql = "SELECT cierre_cobranzas.cliente,";
            cSql = cSql + "clientes.nombre AS nombrecliente,";
            cSql = cSql + "SUM(cierre_cobranzas.cobrado) AS total,clientes.mail ";
            cSql = cSql + "FROM cierre_cobranzas ";
            cSql = cSql + "LEFT JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cierre_cobranzas.cliente ";
            cSql = cSql + " WHERE cierre_cobranzas.fecha_cobro between ? AND ?";
            cSql = cSql + " AND cierre_cobranzas.giraduria=? ";
            cSql = cSql + " GROUP BY cierre_cobranzas.cliente ";
            cSql = cSql + " ORDER by cierre_cobranzas.giraduria,cierre_cobranzas.cliente ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, ngiraduria1);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    cliente c = new cliente();
                    gestioncobro cc = new gestioncobro();

                    cc.setCliente(c);
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setMail(rs.getString("mail"));
                    cc.setCobrado(rs.getBigDecimal("total"));
                    lista.add(cc);
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

    public ArrayList<gestioncobro> PlanillaGestionxNumero(int nnumero) throws SQLException {
        ArrayList<gestioncobro> lista = new ArrayList<gestioncobro>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT gestion_cobros.cliente,clientes.cedula,clientes.ruc,";
            cSql = cSql + "clientes.nombre as nombrecliente,";
            cSql = cSql + "SUM(gestion_cobros.cobrado) AS totalcobro ";
            cSql = cSql + "FROM gestion_cobros ";
            cSql = cSql + "LEFT JOIN clientes ";
            cSql = cSql + " ON clientes.codigo=gestion_cobros.cliente ";
            cSql = cSql + " WHERE gestion_cobros.iddetalle= ? ";
            cSql = cSql + " GROUP BY gestion_cobros.cliente ";
            cSql = cSql + " ORDER BY gestion_cobros.cliente";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nnumero);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    gestioncobro cc = new gestioncobro();
                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.getCliente().setCedula(rs.getString("cedula"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.setCobrado(rs.getBigDecimal("totalcobro"));
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

    public gestioncobro TotalCobroxNumero(int nnumero) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        gestioncobro cc = new gestioncobro();
        try {

            String cSql = "SELECT gestion_cobros.iddetalle,SUM(gestion_cobros.cobrado) AS totalcobro ";
            cSql = cSql + "FROM gestion_cobros ";
            cSql = cSql + " WHERE gestion_cobros.iddetalle= ? ";
            cSql = cSql + " GROUP BY gestion_cobros.iddetalle ";
            cSql = cSql + " ORDER BY gestion_cobros.iddetalle";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nnumero);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cc.setIddetalle(rs.getString("iddetalle"));
                    cc.setCobrado(rs.getBigDecimal("totalcobro"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return cc;
    }

}

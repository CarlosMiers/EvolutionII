/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.comprobante;
import Modelo.cuenta_clientes;
import Modelo.cuenta_proveedores;
import Modelo.giraduria;
import Modelo.moneda;
import Modelo.proveedor;
import Modelo.sucursal;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.math.BigDecimal;
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
public class cuenta_proveedoresDAO {

    Conexion con = null;
    Statement st = null;
    double ncredito = 0;
    double ndebito = 0;
    double saldoanterior = 0;

    public ArrayList<cuenta_proveedores> MostrarxProveedores(int proveedor) throws SQLException {
        ArrayList<cuenta_proveedores> lista = new ArrayList<cuenta_proveedores>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_proveedores.idreferencia,cuenta_proveedores.nrofactura,cuenta_proveedores.fecha,";
            cSql = cSql + "cuenta_proveedores.vencimiento,cuenta_proveedores.comprobante,cuenta_proveedores.proveedor,";
            cSql = cSql + "cuenta_proveedores.moneda,cuenta_proveedores.sucursal,cuenta_proveedores.importe,";
            cSql = cSql + "cuenta_proveedores.fecha_pago,cuenta_proveedores.numerocuota,cuenta_proveedores.cuota,";
            cSql = cSql + "cuenta_proveedores.idmovimiento,cuenta_proveedores.exentas,cuenta_proveedores.gravadas10,cuenta_proveedores.gravadas5,";
            cSql = cSql + "comprobantes.nombre AS nombrecomprobante,";
            cSql = cSql + "proveedores.nombre AS nombreproveedor, ";
            cSql = cSql + "sucursales.nombre AS nombresucursal, ";
            cSql = cSql + "monedas.nombre  AS nombremoneda, ";
            cSql = cSql + "monedas.etiqueta  AS etiqueta ";
            cSql = cSql + "FROM cuenta_proveedores ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_proveedores.comprobante ";
            cSql = cSql + "LEFT JOIN proveedores ";
            cSql = cSql + "ON proveedores.codigo=cuenta_proveedores.proveedor ";
            cSql = cSql + "LEFT JOIN sucursales ";
            cSql = cSql + "ON sucursales.codigo=cuenta_proveedores.sucursal ";
            cSql = cSql + "LEFT JOIN monedas  ";
            cSql = cSql + "ON monedas.codigo=cuenta_proveedores.moneda ";
            cSql = cSql + " WHERE cuenta_proveedores.fecha BETWEEN ? AND ? ";
            cSql = cSql + " ORDER BY cuenta_proveedores.nrofactura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, proveedor);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    proveedor pro = new proveedor();
                    comprobante m = new comprobante();
                    sucursal su = new sucursal();
                    moneda mo = new moneda();
                    cuenta_proveedores cp = new cuenta_proveedores();
                    cp.setComprobante(m);
                    cp.setMoneda(mo);
                    cp.setProveedor(pro);

                    cp.setIdreferencia(rs.getString("idreferencia"));
                    cp.setNrofactura(rs.getBigDecimal("nrofactura"));
                    cp.setFecha(rs.getDate("fecha"));
                    cp.setVencimiento(rs.getDate("vencimiento"));
                    cp.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cp.getProveedor().setCodigo(rs.getInt("proveedor"));
                    cp.getMoneda().setCodigo(rs.getInt("moneda"));
                    cp.setSucursal(rs.getInt("sucursal"));
                    cp.setImporte(rs.getBigDecimal("importe"));
                    cp.setFecha_pago(rs.getDate("fecha_pago"));
                    cp.setNumerocuota(rs.getInt("numerocuota"));
                    cp.setCuota(rs.getInt("cuota"));
                    cp.setIdmovimiento(rs.getString("idmovimiento"));
                    cp.setExentas(rs.getString("exentas"));
                    cp.setGravadas10(rs.getBigDecimal("gravadas10"));
                    cp.setGravadas5(rs.getBigDecimal("gravada5"));
                    lista.add(cp);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_proveedores> MostrarxProveedores(int nproveedor, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cuenta_proveedores> lista = new ArrayList<cuenta_proveedores>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_proveedores.idreferencia,cuenta_proveedores.nrofactura,cuenta_proveedores.fecha,";
            cSql = cSql + "cuenta_proveedores.vencimiento,cuenta_proveedores.comprobante,cuenta_proveedores.proveedor,";
            cSql = cSql + "cuenta_proveedores.moneda,cuenta_proveedores.sucursal,cuenta_proveedores.importe,";
            cSql = cSql + "cuenta_proveedores.fecha_pago,cuenta_proveedores.numerocuota,cuenta_proveedores.cuota,";
            cSql = cSql + "cuenta_proveedores.idmovimiento,cuenta_proveedores.exentas,cuenta_proveedores.gravadas10,cuenta_proveedores.gravadas5,";
            cSql = cSql + "comprobantes.nombre AS nombrecomprobante,";
            cSql = cSql + "proveedores.nombre AS nombreproveedor, ";
            cSql = cSql + "sucursales.nombre AS nombresucursal, ";
            cSql = cSql + "monedas.nombre  AS nombremoneda, ";
            cSql = cSql + "monedas.etiqueta  AS etiqueta ";
            cSql = cSql + "FROM cuenta_proveedores ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_proveedores.comprobante ";
            cSql = cSql + "LEFT JOIN proveedores ";
            cSql = cSql + "ON proveedores.codigo=cuenta_proveedores.proveedor ";
            cSql = cSql + "LEFT JOIN sucursales ";
            cSql = cSql + "ON sucursales.codigo=cuenta_proveedores.sucursal ";
            cSql = cSql + "LEFT JOIN monedas  ";
            cSql = cSql + "ON monedas.codigo=cuenta_proveedores.moneda ";
            cSql = cSql + " WHERE cuenta_proveedores.fecha BETWEEN ? AND ? ";
            cSql = cSql + " ORDER BY cuenta_proveedores.nrofactura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nproveedor);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    proveedor pro = new proveedor();
                    comprobante m = new comprobante();
                    sucursal su = new sucursal();
                    moneda mo = new moneda();
                    cuenta_proveedores cp = new cuenta_proveedores();
                    cp.setComprobante(m);
                    cp.setMoneda(mo);
                    cp.setProveedor(pro);

                    cp.setIdreferencia(rs.getString("idreferencia"));
                    cp.setNrofactura(rs.getBigDecimal("nrofactura"));
                    cp.setFecha(rs.getDate("fecha"));
                    cp.setVencimiento(rs.getDate("vencimiento"));
                    cp.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cp.getProveedor().setCodigo(rs.getInt("proveedor"));
                    cp.getMoneda().setCodigo(rs.getInt("moneda"));
                    cp.setSucursal(rs.getInt("sucursal"));
                    cp.setImporte(rs.getBigDecimal("importe"));
                    cp.setFecha_pago(rs.getDate("fecha_pago"));
                    cp.setNumerocuota(rs.getInt("numerocuota"));
                    cp.setCuota(rs.getInt("cuota"));
                    cp.setIdmovimiento(rs.getString("idmovimiento"));
                    cp.setExentas(rs.getString("exentas"));
                    cp.setGravadas10(rs.getBigDecimal("gravadas10"));
                    cp.setGravadas5(rs.getBigDecimal("gravada5"));

                    lista.add(cp);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_proveedores> MostrarxProveedoresxRango(int nproveedor, Date fechai, Date fechaf) throws SQLException {
        ArrayList<cuenta_proveedores> lista = new ArrayList<cuenta_proveedores>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_proveedores.idreferencia,cuenta_proveedores.nrofactura,cuenta_proveedores.fecha,";
            cSql = cSql + "cuenta_proveedores.vencimiento,cuenta_proveedores.comprobante,cuenta_proveedores.proveedor,";
            cSql = cSql + "cuenta_proveedores.moneda,cuenta_proveedores.sucursal,cuenta_proveedores.importe,";
            cSql = cSql + "cuenta_proveedores.fecha_pago,cuenta_proveedores.numerocuota,cuenta_proveedores.cuota,";
            cSql = cSql + "cuenta_proveedores.idmovimiento,cuenta_proveedores.exentas,cuenta_proveedores.gravadas10,cuenta_proveedores.gravadas5,";
            cSql = cSql + "comprobantes.nombre AS nombrecomprobante,";
            cSql = cSql + "proveedores.nombre AS nombreproveedor, ";
            cSql = cSql + "sucursales.nombre AS nombresucursal, ";
            cSql = cSql + "monedas.nombre  AS nombremoneda, ";
            cSql = cSql + "monedas.etiqueta  AS etiqueta ";
            cSql = cSql + "FROM cuenta_proveedores ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_proveedores.comprobante ";
            cSql = cSql + "LEFT JOIN proveedores ";
            cSql = cSql + "ON proveedores.codigo=cuenta_proveedores.proveedor ";
            cSql = cSql + "LEFT JOIN sucursales ";
            cSql = cSql + "ON sucursales.codigo=cuenta_proveedores.sucursal ";
            cSql = cSql + "LEFT JOIN monedas  ";
            cSql = cSql + "ON monedas.codigo=cuenta_proveedores.moneda ";
            cSql = cSql + " WHERE cuenta_proveedores.fecha BETWEEN ? AND ? ";
            cSql = cSql + " ORDER BY cuenta_proveedores.nrofactura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nproveedor);
                ps.setDate(2, fechai);
                ps.setDate(3, fechaf);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    proveedor pro = new proveedor();
                    comprobante m = new comprobante();
                    sucursal su = new sucursal();
                    moneda mo = new moneda();
                    cuenta_proveedores cp = new cuenta_proveedores();
                    cp.setComprobante(m);
                    cp.setMoneda(mo);
                    cp.setProveedor(pro);

                    cp.setIdreferencia(rs.getString("idreferencia"));
                    cp.setNrofactura(rs.getBigDecimal("nrofactura"));
                    cp.setFecha(rs.getDate("fecha"));
                    cp.setVencimiento(rs.getDate("vencimiento"));
                    cp.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cp.getProveedor().setCodigo(rs.getInt("proveedor"));
                    cp.getMoneda().setCodigo(rs.getInt("moneda"));
                    cp.setSucursal(rs.getInt("sucursal"));
                    cp.setImporte(rs.getBigDecimal("importe"));
                    cp.setFecha_pago(rs.getDate("fecha_pago"));
                    cp.setNumerocuota(rs.getInt("numerocuota"));
                    cp.setCuota(rs.getInt("cuota"));
                    cp.setIdmovimiento(rs.getString("idmovimiento"));
                    cp.setExentas(rs.getString("exentas"));
                    cp.setGravadas10(rs.getBigDecimal("gravadas10"));
                    cp.setGravadas5(rs.getBigDecimal("gravada5"));

                    lista.add(cp);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_proveedores> MostrarxProveedorxVence(int nproveedor, Date fechaini) throws SQLException {
        ArrayList<cuenta_proveedores> lista = new ArrayList<cuenta_proveedores>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_proveedores.idreferencia,cuenta_proveedores.nrofactura,cuenta_proveedores.fecha,";
            cSql = cSql + "cuenta_proveedores.vencimiento,cuenta_proveedores.comprobante,cuenta_proveedores.proveedor,";
            cSql = cSql + "cuenta_proveedores.moneda,cuenta_proveedores.sucursal,cuenta_proveedores.importe,";
            cSql = cSql + "cuenta_proveedores.fecha_pago,cuenta_proveedores.numerocuota,cuenta_proveedores.cuota,";
            cSql = cSql + "cuenta_proveedores.idmovimiento,cuenta_proveedores.exentas,cuenta_proveedores.gravadas10,cuenta_proveedores.gravadas5,";
            cSql = cSql + "comprobantes.nombre AS nombrecomprobante,";
            cSql = cSql + "proveedores.nombre AS nombreproveedor, ";
            cSql = cSql + "sucursales.nombre AS nombresucursal, ";
            cSql = cSql + "monedas.nombre  AS nombremoneda, ";
            cSql = cSql + "monedas.etiqueta  AS etiqueta ";
            cSql = cSql + "FROM cuenta_proveedores ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_proveedores.comprobante ";
            cSql = cSql + "LEFT JOIN proveedores ";
            cSql = cSql + "ON proveedores.codigo=cuenta_proveedores.proveedor ";
            cSql = cSql + "LEFT JOIN sucursales ";
            cSql = cSql + "ON sucursales.codigo=cuenta_proveedores.sucursal ";
            cSql = cSql + "LEFT JOIN monedas  ";
            cSql = cSql + "ON monedas.codigo=cuenta_proveedores.moneda ";
            cSql = cSql + " WHERE cuenta_proveedores.fecha BETWEEN ? AND ? ";
            cSql = cSql + " ORDER BY cuenta_proveedores.nrofactura";
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nproveedor);
                ps.setDate(2, fechaini);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    proveedor pro = new proveedor();
                    comprobante m = new comprobante();
                    sucursal su = new sucursal();
                    moneda mo = new moneda();
                    cuenta_proveedores cp = new cuenta_proveedores();
                    cp.setComprobante(m);
                    cp.setMoneda(mo);
                    cp.setProveedor(pro);

                    cp.setIdreferencia(rs.getString("idreferencia"));
                    cp.setNrofactura(rs.getBigDecimal("nrofactura"));
                    cp.setFecha(rs.getDate("fecha"));
                    cp.setVencimiento(rs.getDate("vencimiento"));
                    cp.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cp.getProveedor().setCodigo(rs.getInt("proveedor"));
                    cp.getMoneda().setCodigo(rs.getInt("moneda"));
                    cp.setSucursal(rs.getInt("sucursal"));
                    cp.setImporte(rs.getBigDecimal("importe"));
                    cp.setFecha_pago(rs.getDate("fecha_pago"));
                    cp.setNumerocuota(rs.getInt("numerocuota"));
                    cp.setCuota(rs.getInt("cuota"));
                    cp.setIdmovimiento(rs.getString("idmovimiento"));
                    cp.setExentas(rs.getString("exentas"));
                    cp.setGravadas10(rs.getBigDecimal("gravadas10"));
                    cp.setGravadas5(rs.getBigDecimal("gravada5"));

                    lista.add(cp);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_proveedores> MostrarxMes(int nproveedor, int nmes, int nanual) throws SQLException {
        ArrayList<cuenta_proveedores> lista = new ArrayList<cuenta_proveedores>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_proveedores.idreferencia,cuenta_proveedores.nrofactura,cuenta_proveedores.fecha,";
            cSql = cSql + "cuenta_proveedores.vencimiento,cuenta_proveedores.comprobante,cuenta_proveedores.proveedor,";
            cSql = cSql + "cuenta_proveedores.moneda,cuenta_proveedores.sucursal,cuenta_proveedores.importe,";
            cSql = cSql + "cuenta_proveedores.fecha_pago,cuenta_proveedores.numerocuota,cuenta_proveedores.cuota,";
            cSql = cSql + "cuenta_proveedores.idmovimiento,cuenta_proveedores.exentas,cuenta_proveedores.gravadas10,cuenta_proveedores.gravadas5,";
            cSql = cSql + "comprobantes.nombre AS nombrecomprobante,";
            cSql = cSql + "proveedores.nombre AS nombreproveedor, ";
            cSql = cSql + "sucursales.nombre AS nombresucursal, ";
            cSql = cSql + "monedas.nombre  AS nombremoneda, ";
            cSql = cSql + "monedas.etiqueta  AS etiqueta ";
            cSql = cSql + "FROM cuenta_proveedores ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_proveedores.comprobante ";
            cSql = cSql + "LEFT JOIN proveedores ";
            cSql = cSql + "ON proveedores.codigo=cuenta_proveedores.proveedor ";
            cSql = cSql + "LEFT JOIN sucursales ";
            cSql = cSql + "ON sucursales.codigo=cuenta_proveedores.sucursal ";
            cSql = cSql + "LEFT JOIN monedas  ";
            cSql = cSql + "ON monedas.codigo=cuenta_proveedores.moneda ";
            cSql = cSql + " WHERE cuenta_proveedores.fecha BETWEEN ? AND ? ";
            cSql = cSql + " ORDER BY cuenta_proveedores.nrofactura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nproveedor);
                ps.setInt(2, nmes);
                ps.setInt(3, nanual);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    proveedor pro = new proveedor();
                    comprobante m = new comprobante();
                    sucursal su = new sucursal();
                    moneda mo = new moneda();
                    cuenta_proveedores cp = new cuenta_proveedores();
                    cp.setComprobante(m);
                    cp.setMoneda(mo);
                    cp.setProveedor(pro);

                    cp.setIdreferencia(rs.getString("idreferencia"));
                    cp.setNrofactura(rs.getBigDecimal("nrofactura"));
                    cp.setFecha(rs.getDate("fecha"));
                    cp.setVencimiento(rs.getDate("vencimiento"));
                    cp.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cp.getProveedor().setCodigo(rs.getInt("proveedor"));
                    cp.getMoneda().setCodigo(rs.getInt("moneda"));
                    cp.setSucursal(rs.getInt("sucursal"));
                    cp.setImporte(rs.getBigDecimal("importe"));
                    cp.setFecha_pago(rs.getDate("fecha_pago"));
                    cp.setNumerocuota(rs.getInt("numerocuota"));
                    cp.setCuota(rs.getInt("cuota"));
                    cp.setIdmovimiento(rs.getString("idmovimiento"));
                    cp.setExentas(rs.getString("exentas"));
                    cp.setGravadas10(rs.getBigDecimal("gravadas10"));
                    cp.setGravadas5(rs.getBigDecimal("gravada5"));

                    lista.add(cp);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    /*   public ArrayList<cuenta_proveedores> MostrarAunaFecha(int moneda1, Date fecha1, Date fecha2, int moneda2) throws SQLException {
        ArrayList<cuenta_proveedores> lista = new ArrayList<cuenta_proveedores>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cliente,clientes.nombre,clientes.ruc,clientes.telefono,clientes.direccion,SUM(importe) as totalsaldo,";
            cSql = cSql + "(SELECT SUM(pago-(mora+gastos_cobranzas+punitorio)) ";
            cSql = cSql + "FROM cobranzas ";
            cSql = cSql + "INNER JOIN detalle_cobranzas ";
            cSql = cSql + "ON detalle_cobranzas.iddetalle=cobranzas.idpagos ";
            cSql = cSql + "WHERE cuenta_clientes.cliente=cobranzas.cliente ";
            cSql = cSql + "AND cobranzas.moneda=? ";
            cSql = cSql + "AND cobranzas.fecha<=? ) totalpagos ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "WHERE cuenta_clientes.fecha<=? ";
            cSql = cSql + "AND cuenta_clientes.moneda=? ";
            cSql = cSql + "AND cuenta_clientes.importe>0 ";
            cSql = cSql + "GROUP BY cliente ";
            cSql = cSql + "ORDER BY clientes.codigo";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, moneda1);
                ps.setDate(2, fecha1);
                ps.setDate(3, fecha2);
                ps.setInt(4, moneda2);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    cliente c = new cliente();
                    cuenta_clientes cc = new cuenta_clientes();
                    cp.setCliente(c);
                    cp.getCliente().setCodigo(rs.getInt("cliente"));
                    cp.getCliente().setNombre(rs.getString("nombre"));
                    cp.getCliente().setRuc(rs.getString("ruc"));
                    cp.getCliente().setTelefono(rs.getString("telefono"));
                    cp.getCliente().setDireccion(rs.getString("direccion"));
                    cp.setImporte(rs.getBigDecimal("totalsaldo"));
                    cp.setPagos(rs.getBigDecimal("totalpagos"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }*/
    public cuenta_proveedores InsertarCuenta(cuenta_proveedores c) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("INSERT INTO cuenta_proveedores(idreferencia,nrofactura,fecha,vencimiento,comprobante,proveedor,moneda,sucursal,importe,fecha_pago,numerocuota,cuota) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, c.getIdreferencia());
        ps.setBigDecimal(2, c.getNrofactura());
        ps.setDate(3, c.getFecha());
        ps.setDate(4, c.getVencimiento());
        ps.setInt(5, c.getComprobante().getCodigo());
        ps.setInt(6, c.getProveedor().getCodigo());
        ps.setInt(7, c.getMoneda().getCodigo());
        ps.setInt(8, c.getSucursal());
        ps.setBigDecimal(9, c.getImporte());
        ps.setDate(10, c.getFecha_pago());
        ps.setInt(11, c.getNumerocuota());
        ps.setInt(12, c.getCuota());
        ps.executeUpdate();
        st.close();
        ps.close();
        return c;
    }

    /*  public cuenta_proveedores ActualizarDebitoAutomaticoCuenta(cuenta_proveedores cta) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;

        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cuenta_clientes SET giraduria=?,nrocuenta=?,autorizacion=? WHERE iddocumento= '" + cta.getIddocumento() + "'");
        ps.setInt(1, cta.getGiraduria().getCodigo());
        ps.setString(2, cta.getNrocuenta());
        ps.setString(3, cta.getAutorizacion());
        ps.executeUpdate();
        st.close();
        ps.close();
        return cta;
    }
     */
 /*  public ArrayList<cuenta_proveedores> MostrarSaldoxSocio(int proveedor, Date dInicio) throws SQLException {
        ArrayList<cuenta_proveedores> lista = new ArrayList<cuenta_proveedores>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
            cSql = cSql + "giradurias.nombre as nombregiraduria,clientes.salario,clientes.ruc,clientes.telefono,";
            cSql = cSql + "casas.nombre as nombrecomercio ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + " WHERE cuenta_clientes.saldo>0 ";
            cSql = cSql + " AND cuenta_clientes.cliente= ? ";
            cSql = cSql + " AND cuenta_clientes.vencimiento<= ? ";
            cSql = cSql + " ORDER by cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, proveedor);
                ps.setDate(2, dInicio);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();

                    cp.setGiraduria(giraduria);
                    cp.setCliente(c);
                    cp.setComprobante(m);

                    cp.setCreferencia(rs.getString("creferencia"));
                    cp.setIddocumento(rs.getString("iddocumento"));
                    cp.setFecha(rs.getDate("fecha"));
                    cp.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cp.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cp.setDocumento(rs.getString("documento"));
                    cp.setVencimiento(rs.getDate("vencimiento"));
                    cp.setImporte(rs.getBigDecimal("importe"));
                    cp.getCliente().setCodigo(rs.getInt("cliente"));
                    cp.getCliente().setNombre(rs.getString("nombrecliente"));
                    cp.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cp.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cp.setSaldo(rs.getBigDecimal("saldo"));
                    cp.setAutorizacion(rs.getString("autorizacion"));
                    cp.setNrocuenta(rs.getString("nrocuenta"));
                    cp.setCuota(rs.getInt("cuota"));
                    cp.setNumerocuota(rs.getInt("numerocuota"));
                    cp.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }*/
    public boolean guardarCuenta(String detallecuota) throws SQLException {
        boolean guardacuota = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectacuota = st.getConnection();
        conectacuota.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detallecuota);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {

                    String sql = "INSERT INTO cuenta_proveedores("
                            + "idmovimiento,"
                            + "idreferencia,"
                            + "nrofactura,"
                            + "fecha,"
                            + "vencimiento,"
                            + "comprobante,"
                            + "proveedor,"
                            + "moneda,"
                            + "sucursal,"
                            + "importe,"
                            + "numerocuota,"
                            + "cuota"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("idmovimiento").getAsString());
                        ps.setString(2, obj.get("idreferencia").getAsString());
                        ps.setString(3, obj.get("nrofactura").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("vencimiento").getAsString());
                        ps.setInt(6, obj.get("comprobante").getAsInt());
                        ps.setInt(7, obj.get("proveedor").getAsInt());
                        ps.setInt(8, obj.get("moneda").getAsInt());
                        ps.setInt(9, obj.get("sucursal").getAsInt());
                        ps.setString(10, obj.get("importe").getAsString());
                        ps.setInt(11, obj.get("numerocuota").getAsInt());
                        ps.setInt(12, obj.get("cuota").getAsInt());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardacuota = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardacuota = false;
                    break;
                }
            }

            if (guardacuota) {
                try {
                    conectacuota.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
                    conectacuota.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardacuota = false;
        }
        st.close();
        conectacuota.close();
        return guardacuota;
    }

    /*    public ArrayList<cuenta_clientes> MostrarxFecha(Date fechai, Date fechaf,int moneda1,int moneda2) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,cuenta_clientes.amortiza,cuenta_clientes.minteres,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio, ";
            cSql = cSql + "cuenta_clientes.moneda,monedas.nombre as nombremoneda ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cuenta_clientes.moneda ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " AND cuenta_clientes.vencimiento between ? AND ? ";
            cSql = cSql + " AND IF(?<>0,cuenta_clientes.moneda=?,TRUE) ";
            cSql = cSql + " ORDER by cuenta_clientes.comprobante,cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechai);
                ps.setDate(2, fechaf);
                ps.setInt(3, moneda1);
                ps.setInt(4, moneda2);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    moneda moneda = new moneda();
                    cp.setMoneda(moneda);
                    cp.setCliente(c);
                    cp.setComprobante(m);
                    cp.setCreferencia(rs.getString("creferencia"));
                    cp.setIddocumento(rs.getString("iddocumento"));
                    cp.setFecha(rs.getDate("fecha"));
                    cp.setDocumento(rs.getString("documento"));
                    cp.setVencimiento(rs.getDate("vencimiento"));
                    cp.setImporte(rs.getBigDecimal("importe"));
                    cp.getCliente().setCodigo(rs.getInt("cliente"));
                    cp.getCliente().setNombre(rs.getString("nombrecliente"));
                    cp.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cp.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cp.getMoneda().setCodigo(rs.getInt("moneda"));
                    cp.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cp.setAmortiza(rs.getBigDecimal("amortiza"));
                    cp.setMinteres(rs.getBigDecimal("minteres"));
                    cp.setSaldo(rs.getBigDecimal("saldo"));
                    cp.setAutorizacion(rs.getString("autorizacion"));
                    cp.setNrocuenta(rs.getString("nrocuenta"));
                    cp.setCuota(rs.getInt("cuota"));
                    cp.setNumerocuota(rs.getInt("numerocuota"));
                    cp.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }*/
    public ArrayList<cuenta_proveedores> ExtractoxProveedor(int proveedor, int moneda, Date fechai, Date fechaf) throws SQLException {
        ArrayList<cuenta_proveedores> lista = new ArrayList<cuenta_proveedores>();
        con = new Conexion();
        st = con.conectar();

        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT 'SALDO ANTERIOR' AS descripcion,proveedor,fecha,moneda,nrofactura AS documento,"
                    + "000000000 AS recibo,SUM(importe) AS debe ,0000000000.00 AS haber "
                    + " FROM cuenta_proveedores "
                    + " WHERE fecha<'" + fechai + "'"
                    + " AND moneda= " + moneda
                    + " AND proveedor=" + proveedor
                    + " GROUP BY proveedor,moneda "
                    + " UNION ALL "
                    + "SELECT  'SALDO ANTERIOR' AS descripcion,proveedor,fecha,moneda,numero AS documento,"
                    + "000000000 AS recibo, "
                    + "0000000000.00 AS debe, "
                    + "SUM(pago) AS haber "
                    + " FROM vista_pagos "
                    + " WHERE fecha<'" + fechai + "'"
                    + " AND moneda= " + moneda
                    + " AND proveedor=" + proveedor
                    + " GROUP BY proveedor,moneda "
                    + " UNION ALL "
                    + " SELECT comprobantes.nombre AS descripcion,proveedor, "
                    + " fecha,moneda,nrofactura AS documento,0000000 AS recibo,importe AS debe,  0000000000.00 AS haber "
                    + " FROM cuenta_proveedores "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=cuenta_proveedores.comprobante "
                    + " WHERE fecha BETWEEN '" + fechai + "' AND '" + fechaf + "'"
                    + " AND moneda=" + moneda
                    + " AND proveedor='" + proveedor + "'"
                    + "UNION ALL "
                    + "SELECT 'PAGO REALIZADO' AS descripcion,proveedor,"
                    + "fecha,moneda,nrofactura AS documento, recibo,0000000000.00 AS debe,"
                    + "pago AS haber "
                    + " FROM vista_pagos "
                    + " WHERE fecha BETWEEN '" + fechai + "' AND '" + fechaf + "'"
                    + " AND moneda=" + moneda
                    + " AND proveedor=" + proveedor
                    + " ORDER BY fecha";

            System.out.println(cSql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    proveedor c = new proveedor();
                    cuenta_proveedores cc = new cuenta_proveedores();
                    cc.setProveedor(c);
                    cc.setDocumento(rs.getString("documento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setObservacion(rs.getString("descripcion"));
                    cc.setNrorecibo(rs.getString("recibo"));
                    cc.setCreditos(rs.getDouble("debe"));
                    cc.setDebitos(rs.getDouble("haber"));

                    if (rs.getString("descripcion").equals("SALDO ANTERIOR")) {
                        cc.setCreditos(0);
                        cc.setDebitos(0);
                        ncredito = ncredito + rs.getDouble("debe");
                        ndebito = ndebito + rs.getDouble("haber");
                        saldoanterior = (ncredito - ndebito);
                    } else if (saldoanterior != 0) {
                        cc.setFecha(fechai);
                        cc.setCreditos(saldoanterior);
                        cc.setDebitos(0);
                        cc.setNrorecibo("0");
                        cc.setDocumento("0");
                        cc.setObservacion("SALDO ANTERIOR");
                        lista.add(cc);
                        saldoanterior = 0;
                        cuenta_proveedores cta = new cuenta_proveedores();
                        cta.setProveedor(c);
                        cta.setDocumento(rs.getString("documento"));
                        cta.setFecha(rs.getDate("fecha"));
                        cta.setObservacion(rs.getString("descripcion"));
                        cta.setNrorecibo(rs.getString("recibo"));
                        cta.setCreditos(rs.getDouble("debe"));
                        cta.setDebitos(rs.getDouble("haber"));
                        lista.add(cta);
                    } else {
                        lista.add(cc);
                    }
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

}

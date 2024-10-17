/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.Config;
import Modelo.venta;
import Conexion.Conexion;
import Modelo.caja;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.detalle_forma_cobro;
import Modelo.giraduria;
import Modelo.localidad;
import Modelo.moneda;
import Modelo.producto;
import Modelo.rubro;
import Modelo.sucursal;
import Modelo.vendedor;
import Modelo.ventares90;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 *
 * @author SERVIDOR
 */
public class ventaDAO {

    DecimalFormat formatosinpunto = new DecimalFormat("############");
    DecimalFormat formatea = new DecimalFormat("###,###.##");

    Conexion con = null;
    Statement st = null;

    public ArrayList<venta> MostrarxFecha(Date fechaini, Date fechafin, int tipo) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT creferencia,cabecera_ventas.factura,cabecera_ventas.fecha,cabecera_ventas.factura,cabecera_ventas.vencimiento,";
            cSql = cSql + "cabecera_ventas.cliente,cabecera_ventas.sucursal,cabecera_ventas.moneda,cabecera_ventas.giraduria,comprobante,cotizacion,";
            cSql = cSql + "cabecera_ventas.vendedor,caja,supago,sucambio,totalbruto,totaldescuento,exentas,gravadas10,gravadas5,totalneto,cuotas,";
            cSql = cSql + "anuladopor,fechaanulado,registro,preventa,cierre,financiado,observacion,";
            cSql = cSql + "enviactacte,comision_vendedor,remisionnro,cabecera_ventas.marcavehiculo,cabecera_ventas.nombreconductor,direccionconductor,";
            cSql = cSql + "cabecera_ventas.chapa,cabecera_ventas.cedula,fechainitraslado,fechafintraslado,llegada,nombregarante,cabecera_ventas.direcciongarante,";
            cSql = cSql + "cabecera_ventas.cedulagarante,cabecera_ventas.turno,idusuario,ordencompra,contrato,iddireccion,cabecera_ventas.nrotimbrado,cabecera_ventas.vencimientotimbrado,";
            cSql = cSql + "centro,comanda,parallevar,cabecera_ventas.horagrabado,cabecera_ventas.formatofactura,";
            cSql = cSql + "clientes.nombre AS nombrecliente,";
            cSql = cSql + "sucursales.nombre AS nombresucursal,";
            cSql = cSql + "monedas.nombre  AS nombremoneda,";
            cSql = cSql + "monedas.etiqueta  AS etiqueta,";
            cSql = cSql + "comprobantes.nombre AS nombrecomprobante,";
            cSql = cSql + "vendedores.nombre AS nombrevendedor,";
            cSql = cSql + "cajas.nombre AS nombrecaja,";
            cSql = cSql + "clientes.localidad,clientes.direccion,clientes.ruc,";
            cSql = cSql + "localidades.nombre AS nombrelocalidad,";
            cSql = cSql + "giradurias.nombre AS nombregiraduria,cabecera_ventas.ticketold ";
            cSql = cSql + " FROM cabecera_ventas ";
            cSql = cSql + " LEFT JOIN clientes ";
            cSql = cSql + " ON clientes.codigo=cabecera_ventas.cliente ";
            cSql = cSql + " LEFT JOIN localidades ";
            cSql = cSql + " ON localidades.codigo=clientes.localidad ";
            cSql = cSql + " LEFT JOIN sucursales ";
            cSql = cSql + " ON sucursales.codigo=cabecera_ventas.sucursal ";
            cSql = cSql + " LEFT JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cabecera_ventas.moneda ";
            cSql = cSql + " LEFT JOIN giradurias ";
            cSql = cSql + " ON giradurias.codigo=cabecera_ventas.giraduria ";
            cSql = cSql + " LEFT JOIN cajas ";
            cSql = cSql + " ON cajas.codigo=cabecera_ventas.caja ";
            cSql = cSql + " LEFT JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=cabecera_ventas.comprobante ";
            cSql = cSql + " LEFT JOIN vendedores ";
            cSql = cSql + " ON vendedores.codigo=cabecera_ventas.vendedor ";
            cSql = cSql + "WHERE cabecera_ventas.fecha between ? AND ? ";
            cSql = cSql + " AND IF(?>0,cabecera_ventas.totalneto>0,cabecera_ventas.totalneto<0) ";
            cSql = cSql + " ORDER BY cabecera_ventas.factura ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, tipo);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();
                    caja caja = new caja();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();

                    localidad localidad = new localidad();

                    venta vta = new venta();

                    vta.setGiraduria(giraduria);
                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);
                    vta.setCaja(caja);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);

                    vta.setCreferencia(rs.getString("creferencia"));
                    vta.setFactura(rs.getDouble("factura"));
                    vta.setFecha(rs.getDate("fecha"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));

                    vta.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    vta.getGiraduria().setNombre(rs.getString("nombregiraduria"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));

                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));
                    vta.getMoneda().setEtiqueta(rs.getString("etiqueta"));

                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));

                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setTicketold(rs.getString("ticketold"));
                    vta.setCuotas(rs.getInt("cuotas"));
                    vta.setNrotimbrado(rs.getInt("nrotimbrado"));
                    vta.setVencimientotimbrado(rs.getDate("vencimientotimbrado"));
                    vta.setCentro(rs.getInt("centro"));
                    vta.setCotizacion(rs.getBigDecimal("cotizacion"));
                    vta.setExentas(rs.getBigDecimal("exentas"));
                    vta.setGravadas5(rs.getBigDecimal("gravadas5"));
                    vta.setGravadas10(rs.getBigDecimal("gravadas10"));
                    vta.setTotalneto(rs.getBigDecimal("totalneto"));
                    vta.setComanda(rs.getInt("comanda"));
                    vta.setTurno(rs.getInt("turno"));
                    vta.setContrato(rs.getInt("contrato"));
                    vta.setOrdencompra(rs.getInt("ordencompra"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setIdusuario(rs.getInt("idusuario"));
                    vta.setComision_vendedor(rs.getBigDecimal("comision_vendedor"));
                    vta.setRegistro(rs.getInt("registro"));
                    lista.add(vta);
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

    public ArrayList<venta> MostrarxFechaTodas(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT creferencia,cabecera_ventas.factura,cabecera_ventas.fecha,cabecera_ventas.factura,cabecera_ventas.vencimiento,";
            cSql = cSql + "cabecera_ventas.cliente,cabecera_ventas.sucursal,cabecera_ventas.moneda,cabecera_ventas.giraduria,comprobante,cotizacion,";
            cSql = cSql + "cabecera_ventas.vendedor,caja,supago,sucambio,totalbruto,totaldescuento,exentas,gravadas10,gravadas5,totalneto,cuotas,";
            cSql = cSql + "anuladopor,fechaanulado,registro,preventa,cierre,financiado,observacion,";
            cSql = cSql + "enviactacte,comision_vendedor,remisionnro,cabecera_ventas.marcavehiculo,cabecera_ventas.nombreconductor,direccionconductor,";
            cSql = cSql + "cabecera_ventas.chapa,cabecera_ventas.cedula,fechainitraslado,fechafintraslado,llegada,nombregarante,cabecera_ventas.direcciongarante,";
            cSql = cSql + "cabecera_ventas.cedulagarante,cabecera_ventas.turno,idusuario,ordencompra,contrato,iddireccion,cabecera_ventas.nrotimbrado,cabecera_ventas.vencimientotimbrado,";
            cSql = cSql + "centro,comanda,parallevar,cabecera_ventas.horagrabado,cabecera_ventas.formatofactura,";
            cSql = cSql + "clientes.nombre AS nombrecliente,";
            cSql = cSql + "sucursales.nombre AS nombresucursal,";
            cSql = cSql + "monedas.nombre  AS nombremoneda,";
            cSql = cSql + "monedas.etiqueta  AS etiqueta,";
            cSql = cSql + "comprobantes.nombre AS nombrecomprobante,";
            cSql = cSql + "vendedores.nombre AS nombrevendedor,";
            cSql = cSql + "cajas.nombre AS nombrecaja,";
            cSql = cSql + "clientes.localidad,clientes.direccion,clientes.ruc,";
            cSql = cSql + "localidades.nombre AS nombrelocalidad,";
            cSql = cSql + "giradurias.nombre AS nombregiraduria ";
            cSql = cSql + " FROM cabecera_ventas ";
            cSql = cSql + " LEFT JOIN clientes ";
            cSql = cSql + " ON clientes.codigo=cabecera_ventas.cliente ";
            cSql = cSql + " LEFT JOIN localidades ";
            cSql = cSql + " ON localidades.codigo=clientes.localidad ";
            cSql = cSql + " LEFT JOIN sucursales ";
            cSql = cSql + " ON sucursales.codigo=cabecera_ventas.sucursal ";
            cSql = cSql + " LEFT JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cabecera_ventas.moneda ";
            cSql = cSql + " LEFT JOIN giradurias ";
            cSql = cSql + " ON giradurias.codigo=cabecera_ventas.giraduria ";
            cSql = cSql + " LEFT JOIN cajas ";
            cSql = cSql + " ON cajas.codigo=cabecera_ventas.caja ";
            cSql = cSql + " LEFT JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=cabecera_ventas.comprobante ";
            cSql = cSql + " LEFT JOIN vendedores ";
            cSql = cSql + " ON vendedores.codigo=cabecera_ventas.vendedor ";
            cSql = cSql + " WHERE cabecera_ventas.fecha between ? AND ? ";
            cSql = cSql + " ORDER BY cabecera_ventas.factura ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();
                    caja caja = new caja();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();

                    localidad localidad = new localidad();

                    venta vta = new venta();

                    vta.setGiraduria(giraduria);
                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);
                    vta.setCaja(caja);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);

                    vta.setCreferencia(rs.getString("creferencia"));
                    vta.setFactura(rs.getDouble("factura"));
                    vta.setFecha(rs.getDate("fecha"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));

                    vta.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    vta.getGiraduria().setNombre(rs.getString("nombregiraduria"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));

                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));
                    vta.getMoneda().setEtiqueta(rs.getString("etiqueta"));

                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));

                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setCuotas(rs.getInt("cuotas"));
                    vta.setNrotimbrado(rs.getInt("nrotimbrado"));
                    vta.setVencimientotimbrado(rs.getDate("vencimientotimbrado"));
                    vta.setCentro(rs.getInt("centro"));
                    vta.setCotizacion(rs.getBigDecimal("cotizacion"));
                    vta.setExentas(rs.getBigDecimal("exentas"));
                    vta.setGravadas5(rs.getBigDecimal("gravadas5"));
                    vta.setGravadas10(rs.getBigDecimal("gravadas10"));
                    vta.setTotalneto(rs.getBigDecimal("totalneto"));
                    vta.setComanda(rs.getInt("comanda"));
                    vta.setTurno(rs.getInt("turno"));
                    vta.setContrato(rs.getInt("contrato"));
                    vta.setOrdencompra(rs.getInt("ordencompra"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setIdusuario(rs.getInt("idusuario"));
                    vta.setComision_vendedor(rs.getBigDecimal("comision_vendedor"));
                    lista.add(vta);
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

    public venta buscarId(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        venta vta = new venta();

        try {

            String sql = "SELECT creferencia,cabecera_ventas.factura,cabecera_ventas.fecha,cabecera_ventas.factura,cabecera_ventas.vencimiento,"
                    + "cabecera_ventas.cliente,cabecera_ventas.sucursal,cabecera_ventas.moneda,cabecera_ventas.giraduria,comprobante,cotizacion,"
                    + "cabecera_ventas.vendedor,caja,supago,sucambio,totalbruto,totaldescuento,exentas,gravadas10,gravadas5,totalneto,cuotas,"
                    + "anuladopor,fechaanulado,registro,preventa,cierre,financiado,observacion,"
                    + "enviactacte,comision_vendedor,remisionnro,cabecera_ventas.marcavehiculo,cabecera_ventas.nombreconductor,direccionconductor,"
                    + "cabecera_ventas.chapa,cabecera_ventas.cedula,fechainitraslado,fechafintraslado,llegada,nombregarante,cabecera_ventas.direcciongarante,"
                    + "cabecera_ventas.cedulagarante,cabecera_ventas.turno,idusuario,ordencompra,contrato,iddireccion,cabecera_ventas.nrotimbrado,cabecera_ventas.vencimientotimbrado,"
                    + "centro,comanda,parallevar,cabecera_ventas.horagrabado,cabecera_ventas.formatofactura,cabecera_ventas.idprestamo,"
                    + "clientes.nombre AS nombrecliente,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "monedas.nombre  AS nombremoneda,"
                    + "monedas.etiqueta  AS etiqueta,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "vendedores.nombre AS nombrevendedor,"
                    + "cajas.nombre AS nombrecaja,"
                    + "clientes.localidad,clientes.direccion,clientes.ruc,"
                    + "localidades.nombre AS nombrelocalidad,"
                    + "giradurias.nombre AS nombregiraduria "
                    + " FROM cabecera_ventas "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=cabecera_ventas.cliente "
                    + " LEFT JOIN localidades "
                    + " ON localidades.codigo=clientes.localidad "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=cabecera_ventas.sucursal "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=cabecera_ventas.moneda "
                    + " LEFT JOIN giradurias "
                    + " ON giradurias.codigo=cabecera_ventas.giraduria "
                    + " LEFT JOIN cajas "
                    + " ON cajas.codigo=cabecera_ventas.caja "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=cabecera_ventas.comprobante "
                    + " LEFT JOIN vendedores "
                    + " ON vendedores.codigo=cabecera_ventas.vendedor "
                    + "WHERE cabecera_ventas.creferencia= ?"
                    + " ORDER BY cabecera_ventas.factura ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();
                    caja caja = new caja();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();

                    localidad localidad = new localidad();

                    vta.setGiraduria(giraduria);
                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);
                    vta.setCaja(caja);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);

                    vta.setCreferencia(rs.getString("creferencia"));
                    vta.setFactura(rs.getDouble("factura"));
                    vta.setFecha(rs.getDate("fecha"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));

                    vta.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    vta.getGiraduria().setNombre(rs.getString("nombregiraduria"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));

                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));
                    vta.getMoneda().setEtiqueta(rs.getString("etiqueta"));

                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));

                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setCuotas(rs.getInt("cuotas"));
                    vta.setNrotimbrado(rs.getInt("nrotimbrado"));
                    vta.setVencimientotimbrado(rs.getDate("vencimientotimbrado"));
                    vta.setCentro(rs.getInt("centro"));
                    vta.setCotizacion(rs.getBigDecimal("cotizacion"));
                    vta.setExentas(rs.getBigDecimal("exentas"));
                    vta.setGravadas5(rs.getBigDecimal("gravadas5"));
                    vta.setGravadas10(rs.getBigDecimal("gravadas10"));
                    vta.setFinanciado(rs.getBigDecimal("financiado"));
                    vta.setTotalneto(rs.getBigDecimal("totalneto"));
                    vta.setSupago(rs.getBigDecimal("supago"));
                    vta.setComanda(rs.getInt("comanda"));
                    vta.setTurno(rs.getInt("turno"));
                    vta.setContrato(rs.getInt("contrato"));
                    vta.setOrdencompra(rs.getInt("ordencompra"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setIdusuario(rs.getInt("idusuario"));
                    vta.setComision_vendedor(rs.getBigDecimal("comision_vendedor"));
                    vta.setIdprestamo(rs.getString("idprestamo"));
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return vta;
    }

    public venta AgregarFacturaVenta(String token, venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_ventas (creferencia,fecha,factura,"
                + "vencimiento,cliente,sucursal,moneda,giraduria,"
                + "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,"
                + "gravadas5,totalneto,cuotas,financiado,observacion,"
                + "supago,idusuario,preventa,vencimientotimbrado,sucambio,"
                + "nrotimbrado,turno,formatofactura) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getFactura());
        ps.setDate(4, v.getVencimiento());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setInt(8, v.getGiraduria().getCodigo());
        ps.setInt(9, v.getComprobante().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getVendedor().getCodigo());
        ps.setInt(12, v.getCaja().getCodigo());
        ps.setBigDecimal(13, v.getExentas());
        ps.setBigDecimal(14, v.getGravadas10());
        ps.setBigDecimal(15, v.getGravadas5());
        ps.setBigDecimal(16, v.getTotalneto());
        ps.setInt(17, v.getCuotas());
        ps.setBigDecimal(18, v.getFinanciado());
        ps.setString(19, v.getObservacion());
        ps.setBigDecimal(20, v.getSupago());
        ps.setInt(21, v.getIdusuario());
        ps.setInt(22, v.getPreventa());
        ps.setDate(23, v.getVencimientotimbrado());
        ps.setBigDecimal(24, v.getSucambio());
        ps.setInt(25, v.getNrotimbrado());
        ps.setInt(26, v.getTurno());
        ps.setString(27, v.getFormatofactura());

        if (Config.cToken == token) {
            ps.executeUpdate();
            guardarItemFactura(v.getCreferencia(), detalle, con);
        } else {
            System.out.println("USUARIO NO AUTORIZADO");
        }

        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public boolean ActualizarFactura(int sucursal, Double factura) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE sucursales SET factura=? WHERE codigo=?");
        ps.setDouble(1, factura);
        ps.setInt(2, sucursal);
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

    public boolean ActualizarNroNotaCredito(int sucursal, Double factura) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE sucursales SET notacredito=? WHERE codigo=?");
        ps.setDouble(1, factura);
        ps.setInt(2, sucursal);
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

    public venta AgregarNotaCredito(venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_ventas (creferencia,fecha,"
                + "factura,vencimiento,cliente,sucursal,moneda,"
                + "giraduria,comprobante,cotizacion,"
                + "vendedor,caja,exentas,gravadas10,"
                + "gravadas5,totalneto,cuotas,financiado,"
                + "observacion,supago,idusuario,preventa,"
                + "formatofactura,vencimientotimbrado,nrotimbrado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getFactura());
        ps.setDate(4, v.getVencimiento());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setInt(8, v.getGiraduria().getCodigo());
        ps.setInt(9, v.getComprobante().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getVendedor().getCodigo());
        ps.setInt(12, v.getCaja().getCodigo());
        ps.setBigDecimal(13, v.getExentas());
        ps.setBigDecimal(14, v.getGravadas10());
        ps.setBigDecimal(15, v.getGravadas5());
        ps.setBigDecimal(16, v.getTotalneto());
        ps.setInt(17, v.getCuotas());
        ps.setBigDecimal(18, v.getFinanciado());
        ps.setString(19, v.getObservacion());
        ps.setBigDecimal(20, v.getSupago());
        ps.setInt(21, v.getIdusuario());
        ps.setInt(22, v.getPreventa());
        ps.setString(23, v.getFormatofactura());
        ps.setDate(24, v.getVencimientotimbrado());
        ps.setInt(25, v.getNrotimbrado());
        ps.executeUpdate();
        guardarItemFactura(v.getCreferencia(), detalle, con);
        ActualizarNumeroNotaCredito(v.getSucursal().getCodigo(), v.getFactura() + 1);
        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public boolean ActualizarNumeroNotaCredito(int sucursal, Double factura) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE sucursales SET notacredito=? WHERE codigo=?");
        ps.setDouble(1, factura);
        ps.setInt(2, sucursal);
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

    public venta ActualizarVenta(venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  cabecera_ventas SET fecha=?,factura=?,"
                + "vencimiento=?,cliente=?,sucursal=?,"
                + "moneda=?,giraduria=?,comprobante=?,"
                + "cotizacion=?,vendedor=?,caja=?,"
                + "exentas=?,gravadas10=?,gravadas5=?,"
                + "totalneto=?,cuotas=?,financiado=?,"
                + "observacion=?,supago=?,idusuario=?,"
                + "preventa=?,vencimientotimbrado=?,nrotimbrado=?,"
                + "turno=?,formatofactura=?,sucambio=? WHERE creferencia= '" + v.getCreferencia() + "'");
        ps.setDate(1, v.getFecha());
        ps.setDouble(2, v.getFactura());
        ps.setDate(3, v.getVencimiento());
        ps.setInt(4, v.getCliente().getCodigo());
        ps.setInt(5, v.getSucursal().getCodigo());
        ps.setInt(6, v.getMoneda().getCodigo());
        ps.setInt(7, v.getGiraduria().getCodigo());
        ps.setInt(8, v.getComprobante().getCodigo());
        ps.setBigDecimal(9, v.getCotizacion());
        ps.setInt(10, v.getVendedor().getCodigo());
        ps.setInt(11, v.getCaja().getCodigo());
        ps.setBigDecimal(12, v.getExentas());
        ps.setBigDecimal(13, v.getGravadas10());
        ps.setBigDecimal(14, v.getGravadas5());
        ps.setBigDecimal(15, v.getTotalneto());
        ps.setInt(16, v.getCuotas());
        ps.setBigDecimal(17, v.getFinanciado());
        ps.setString(18, v.getObservacion());
        ps.setBigDecimal(19, v.getSupago());
        ps.setInt(20, v.getIdusuario());
        ps.setInt(21, v.getPreventa());
        ps.setDate(22, v.getVencimientotimbrado());
        ps.setInt(23, v.getNrotimbrado());
        ps.setInt(24, v.getTurno());
        ps.setString(25, v.getFormatofactura());
        ps.setBigDecimal(26, v.getSucambio());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemFactura(v.getCreferencia(), detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return v;
    }

    public boolean guardarItemFactura(String id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement psdetalle = null;

        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_ventas WHERE dreferencia=?");
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
                    String sql = "INSERT INTO detalle_ventas("
                            + "dreferencia,"
                            + "codprod,"
                            + "prcosto,"
                            + "cantidad,"
                            + "precio,"
                            + "monto,"
                            + "impiva,"
                            + "porcentaje,"
                            + "suc"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("codprod").getAsString());
                        ps.setBigDecimal(3, obj.get("prcosto").getAsBigDecimal());
                        ps.setBigDecimal(4, obj.get("cantidad").getAsBigDecimal());
                        ps.setBigDecimal(5, obj.get("precio").getAsBigDecimal());
                        ps.setBigDecimal(6, obj.get("monto").getAsBigDecimal());
                        ps.setBigDecimal(7, obj.get("impiva").getAsBigDecimal());
                        ps.setBigDecimal(8, obj.get("porcentaje").getAsBigDecimal());
                        ps.setString(9, obj.get("suc").getAsString());
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
        conn.close();
        return guardado;
    }

    public boolean borrarDetalleCuenta(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuenta_clientes WHERE creferencia=?");
        ps.setString(1, referencia);
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

    public boolean borrarVenta(String token, String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        int rowsUpdated = 0;
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM cabecera_ventas WHERE creferencia=?");
        ps.setString(1, id);
        if (Config.cToken == token) {
            rowsUpdated = ps.executeUpdate();
        } else {
            System.out.println("USUARIO NO AUTORIZADO");
        }
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<venta> ResumenVtaConsolidado(Date fechaini, Date fechafin, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT codprod,nombreproducto,SUM(cantidad) as tcantidad,AVG(precio) as promedio,"
                    + "precio,SUM(monto) as totalventa,fecha,nombrerubro,codigo,moneda,nombremoneda"
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between ? AND ? "
                    + " AND vista_detalle_ventas.moneda=? "
                    + " GROUP BY codprod "
                    + " ORDER BY codigo,codprod ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nMoneda);
                ResultSet rs = ps.executeQuery();
                System.out.println("--> " + cSql);

                while (rs.next()) {

                    moneda moneda = new moneda();
                    venta vta = new venta();
                    vta.setMoneda(moneda);

                    vta.setCodprodventa(rs.getString("codprod"));
                    vta.setNombreproductoventa(rs.getString("nombreproducto"));
                    vta.setTotalneto(rs.getBigDecimal("totalventa"));
                    vta.setCodrubroventa(rs.getInt("codigo"));
                    vta.setNombrerubroventa(rs.getString("nombrerubro"));
                    vta.setCantidadventa(rs.getBigDecimal("tcantidad"));
                    vta.setPrecioventa(rs.getBigDecimal("precio"));
                    vta.setPromedio(rs.getBigDecimal("promedio"));
                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));

                    vta.setFecha(rs.getDate("fecha"));
                    lista.add(vta);
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

    public ArrayList<venta> ResumenVtaxSucursal(Date fechaini, Date fechafin, int nSucursal, int nSuc, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = " SELECT codprod,nombreproducto,cliente,nombrecliente,SUM(cantidad) as tcantidad,AVG(precio) as promedio,"
                    + " precio,SUM(monto) AS totalventa,fecha,codigo,nombrerubro,moneda,nombremoneda,sucursal,nombresucursal"
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_ventas.sucursal=?,TRUE) "
                    + " AND vista_detalle_ventas.moneda=? "
                    + " GROUP BY codprod "
                    + " ORDER BY codigo,codprod ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();

                    venta vta = new venta();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);

                    vta.setCodprodventa(rs.getString("codprod"));
                    vta.setNombreproductoventa(rs.getString("nombreproducto"));
                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.setCantidadventa(rs.getBigDecimal("tcantidad"));
                    vta.setPromedio(rs.getBigDecimal("promedio"));
                    vta.setPrecioventa(rs.getBigDecimal("precio"));
                    vta.setTotalneto(rs.getBigDecimal("totalventa"));
                    vta.setCodrubroventa(rs.getInt("codigo"));
                    vta.setNombrerubroventa(rs.getString("nombrerubro"));
                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));
                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.setFecha(rs.getDate("fecha"));

                    lista.add(vta);
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

    public ArrayList<venta> ResumenVtaxcaja(Date fechaini, Date fechafin, int nCaja, int nCaj, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT codprod,caja,nombrecaja,nombreproducto,SUM(cantidad) as tcantidad,AVG(precio) as promedio,"
                    + "precio,SUM(monto) as totalventa,fecha,codigo,nombrerubro,moneda,nombremoneda"
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_ventas.caja=?,TRUE) "
                    + " AND vista_detalle_ventas.moneda=? "
                    + " GROUP BY codprod "
                    + " ORDER BY codigo,codprod ";
            System.out.println(cSql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nCaja);
                ps.setInt(4, nCaj);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    caja caja = new caja();
                    moneda moneda = new moneda();

                    venta vta = new venta();

                    vta.setSucursal(sucursal);
                    vta.setCaja(caja);
                    vta.setMoneda(moneda);

                    vta.setCodprodventa(rs.getString("codprod"));
                    vta.setNombreproductoventa(rs.getString("nombreproducto"));
                    vta.setTotalneto(rs.getBigDecimal("totalventa"));
                    vta.setCodrubroventa(rs.getInt("codigo"));
                    vta.setNombrerubroventa(rs.getString("nombrerubro"));
                    vta.setCantidadventa(rs.getBigDecimal("tcantidad"));
                    vta.setPrecioventa(rs.getBigDecimal("precio"));
                    vta.setPromedio(rs.getBigDecimal("promedio"));

                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));

                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));
                    vta.setFecha(rs.getDate("fecha"));

                    lista.add(vta);
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

    public ArrayList<venta> ResumenVtaxCliente(Date fechaini, Date fechafin, int nCliente, int nCli, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT sucursal,codprod,cliente,nombrecliente,nombreproducto,SUM(cantidad) as tcantidad,AVG(precio) as promedio,"
                    + "precio,SUM(monto) as totalventa,fecha,nombrerubro,codigo,moneda,nombremoneda,nombresucursal"
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_ventas.cliente=?,TRUE) "
                    + " AND vista_detalle_ventas.moneda=? "
                    + " GROUP BY codprod "
                    + " ORDER BY cliente,codigo,codprod ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nCliente);
                ps.setInt(4, nCli);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();

                    venta vta = new venta();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);

                    vta.setCodprodventa(rs.getString("codprod"));
                    vta.setNombreproductoventa(rs.getString("nombreproducto"));
                    vta.setCodrubroventa(rs.getInt("codigo"));
                    vta.setNombrerubroventa(rs.getString("nombrerubro"));
                    vta.setTotalneto(rs.getBigDecimal("totalventa"));
                    vta.setCodrubroventa(rs.getInt("codigo"));
                    vta.setNombrerubroventa(rs.getString("nombrerubro"));
                    vta.setCantidadventa(rs.getBigDecimal("tcantidad"));
                    vta.setPrecioventa(rs.getBigDecimal("precio"));
                    vta.setPromedio(rs.getBigDecimal("promedio"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));

                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));

                    vta.setFecha(rs.getDate("fecha"));

                    lista.add(vta);
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

    public ArrayList<venta> ResumenVtaxVendedor(Date fechaini, Date fechafin, int nSucursal, int nVendedor, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT sucursal,codprod,vendedor,nombrevendedor,nombreproducto,SUM(cantidad) as tcantidad,AVG(precio) as promedio,"
                    + "precio,SUM(monto) as totalventa,fecha,nombrerubro,codigo,moneda,nombremoneda,nombresucursal"
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between'" + fechaini + "' AND '" + fechafin + "'"
                    + " AND IF(" + nVendedor + "<>0,vista_detalle_ventas.vendedor=" + nVendedor + ",TRUE) "
                    + " AND vista_detalle_ventas.moneda=" + nMoneda
                    + " AND vista_detalle_ventas.sucursal=" + nSucursal
                    + " GROUP BY vendedor,codprod "
                    + " ORDER BY vendedor,codprod ";
            System.out.println(cSql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    vendedor vendedor = new vendedor();
                    moneda moneda = new moneda();

                    venta vta = new venta();

                    vta.setSucursal(sucursal);
                    vta.setVendedor(vendedor);
                    vta.setMoneda(moneda);

                    vta.setCodprodventa(rs.getString("codprod"));
                    vta.setNombreproductoventa(rs.getString("nombreproducto"));
                    vta.setTotalneto(rs.getBigDecimal("totalventa"));
                    vta.setCodrubroventa(rs.getInt("codigo"));
                    vta.setNombrerubroventa(rs.getString("nombrerubro"));
                    vta.setCantidadventa(rs.getBigDecimal("tcantidad"));
                    vta.setPrecioventa(rs.getBigDecimal("precio"));
                    vta.setPromedio(rs.getBigDecimal("promedio"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));

                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));

                    vta.setFecha(rs.getDate("fecha"));
                    lista.add(vta);
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

    public ArrayList<venta> ResumenVtaxRubro(Date fechaini, Date fechafin, int nRubro, int nRub, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT sucursal,codprod,nombreproducto,SUM(cantidad) as tcantidad,AVG(precio) as preciopromedio,"
                    + "AVG(prcosto) as costopromedio,SUM(monto) as totalventa,fecha,codigo,nombrerubro,moneda,nombremoneda,nombresucursal"
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_ventas.codigo=?,TRUE) "
                    + " AND vista_detalle_ventas.moneda=? "
                    + " GROUP BY codprod "
                    + " ORDER BY codigo,codprod ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nRubro);
                ps.setInt(4, nRub);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();
                    rubro rubro = new rubro();

                    venta vta = new venta();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);
                    vta.setRubro(rubro);

                    vta.setCodprodventa(rs.getString("codprod"));
                    vta.setNombreproductoventa(rs.getString("nombreproducto"));
                    vta.setTotalneto(rs.getBigDecimal("totalventa"));
                    vta.setCodrubroventa(rs.getInt("codigo"));
                    vta.setNombrerubroventa(rs.getString("nombrerubro"));
                    vta.setCantidadventa(rs.getBigDecimal("tcantidad"));
                    vta.setPrecioventa(rs.getBigDecimal("preciopromedio"));
                    vta.setPromedio(rs.getBigDecimal("costopromedio"));

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));

                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));

                    vta.setFecha(rs.getDate("fecha"));

                    lista.add(vta);
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

    public ArrayList<venta> libroventaconsolidado(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_ventas.factura AS factura,"
                    + "cabecera_ventas.formatofactura,"
                    + "1 AS ntipo,"
                    + "cabecera_ventas.vendedor  AS vendedor,"
                    + "cabecera_ventas.caja AS caja,"
                    + "cabecera_ventas.sucursal AS sucursal,"
                    + "cabecera_ventas.nrotimbrado AS nrotimbrado,"
                    + "cabecera_ventas.fecha AS fecha,"
                    + "cabecera_ventas.cliente AS cliente,"
                    + "cabecera_ventas.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_ventas.exentas * cabecera_ventas.cotizacion),0),0) AS exenta,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion),0),0) AS gravada10,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion) / 11),0),0) AS iva10,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion),0),0) AS gravada5,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion) / 21),0),0) AS iva5,"
                    + "IFNULL(ROUND((cabecera_ventas.totalneto * cabecera_ventas.cotizacion),0),0) AS total,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.ruc AS ruc,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "vendedores.nombre AS nombrevendedor,"
                    + "cajas.nombre AS nombrecaja "
                    + " FROM cabecera_ventas "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=cabecera_ventas.cliente "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=cabecera_ventas.comprobante "
                    + " LEFT JOIN vendedores "
                    + " ON vendedores.codigo=cabecera_ventas.vendedor "
                    + " LEFT JOIN cajas "
                    + " ON CAJAS.codigo=cabecera_ventas.caja "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=cabecera_ventas.sucursal "
                    + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                    + " AND comprobantes.libros=1 AND cabecera_ventas.totalneto>0 "
                    + " UNION "
                    + " SELECT ventas_anuladas.factura AS factura,"
                    + "ventas_anuladas.formatofactura,"
                    + "1 AS ntipo,"
                    + " ventas_anuladas.vendedor  AS vendedor,"
                    + " ventas_anuladas.caja AS caja,"
                    + " ventas_anuladas.sucursal AS sucursal,"
                    + "0 AS nrotimbrado,"
                    + " ventas_anuladas.fecha AS fecha,"
                    + " '' AS cliente,"
                    + " ventas_anuladas.comprobante AS comprobante,"
                    + " 0   AS totalneto,"
                    + " 0 AS exenta, "
                    + " 0 AS gravada10,"
                    + " 0 AS iva10,"
                    + " 0 AS gravada5,"
                    + " 0 AS iva5,"
                    + " 'FACTURA ANULADA' AS nombrecliente,"
                    + " '' AS ruc,"
                    + " sucursales.nombre AS nombresucursal,"
                    + " comprobantes.nombre AS nombrecomprobante,"
                    + " '' AS nombrevendedor,"
                    + " '' AS nombrecaja "
                    + " FROM ventas_anuladas "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=ventas_anuladas.comprobante "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=ventas_anuladas.sucursal "
                    + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                    + " AND comprobantes.libros=1  AND ventas_anuladas.totalneto>0  "
                    + " UNION ALL "
                    + "SELECT cabecera_compras.nrofactura AS factura,"
                    + "cabecera_compras.formatofactura,"
                    + "2 AS ntipo,"
                    + "1  AS vendedor,"
                    + "1  AS caja,"
                    + "cabecera_compras.sucursal AS sucursal,"
                    + "cabecera_compras.timbrado AS nrotimbrado,"
                    + "cabecera_compras.fecha AS fecha,"
                    + "cabecera_compras.proveedor AS cliente,"
                    + "cabecera_compras.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_compras.exentas * cabecera_compras.cotizacion),0),0) AS exenta,"
                    + "IFNULL(ROUND((cabecera_compras.gravadas10 * cabecera_compras.cotizacion),0),0) AS gravada10,"
                    + "IFNULL(ROUND(((cabecera_compras.gravadas10 * cabecera_compras.cotizacion) / 11),0),0) AS iva10,"
                    + "IFNULL(ROUND((cabecera_compras.gravadas5 * cabecera_compras.cotizacion),0),0) AS gravada5,"
                    + "IFNULL(ROUND(((cabecera_compras.gravadas5 * cabecera_compras.cotizacion) / 21),0),0) AS iva5,"
                    + "IFNULL(ROUND((cabecera_compras.totalneto * cabecera_compras.cotizacion),0),0) AS total,"
                    + "proveedores.nombre AS nombrecliente,"
                    + "proveedores.ruc AS ruc,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "'MOSTRADOR' AS nombrevendedor,"
                    + "'MOSTRADOR' AS nombrecaja "
                    + "FROM cabecera_compras "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_compras.sucursal "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=cabecera_compras.proveedor "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_compras.comprobante "
                    + "WHERE cabecera_compras.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND cabecera_compras.totalneto<0 "
                    + " AND comprobantes.libros=1 "
                    + " ORDER BY ntipo,comprobante,fecha ";
            System.out.println(cSql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    cliente cliente = new cliente();
                    venta vta = new venta();
                    vendedor v = new vendedor();
                    caja c = new caja();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(v);
                    vta.setCaja(c);

                    vta.setFactura(rs.getDouble("factura"));
                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.setNrotimbrado(rs.getInt("NroTimbrado"));
                    vta.setFecha(rs.getDate("Fecha"));
                    vta.getCliente().setCodigo(rs.getInt("Cliente"));
                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.setTotalneto(rs.getBigDecimal("total"));
                    vta.setExentas(rs.getBigDecimal("exenta"));
                    vta.setGravadas10(rs.getBigDecimal("gravada10"));
                    vta.setGravadas5(rs.getBigDecimal("gravada5"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));
                    lista.add(vta);
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

    public ArrayList<venta> libroventaxsucursal(int suc, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_ventas.factura AS factura,cabecera_ventas.formatofactura,"
                    + "cabecera_ventas.vendedor  AS vendedor,"
                    + "cabecera_ventas.caja AS caja,"
                    + "cabecera_ventas.sucursal AS sucursal,"
                    + "cabecera_ventas.nrotimbrado AS nrotimbrado,"
                    + "cabecera_ventas.fecha AS fecha,"
                    + "cabecera_ventas.cliente AS cliente,"
                    + "cabecera_ventas.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_ventas.totalneto * cabecera_ventas.cotizacion),0),0) AS total,"
                    + "IFNULL(ROUND((cabecera_ventas.exentas * cabecera_ventas.cotizacion),0),0) AS exenta,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion),0),0) AS gravada10,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion) / 11),0),0) AS iva10,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion),0),0) AS gravada5,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion) / 21),0),0) AS iva5,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.ruc AS ruc,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "vendedores.nombre AS nombrevendedor,"
                    + "cajas.nombre AS nombrecaja "
                    + "FROM cabecera_ventas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cabecera_ventas.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=cabecera_ventas.vendedor "
                    + "LEFT JOIN cajas "
                    + "ON CAJAS.codigo=cabecera_ventas.caja "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_ventas.sucursal "
                    + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                    + " AND sucursal= " + suc
                    + " AND comprobantes.libros=1 "
                    + " UNION "
                    + " SELECT ventas_anuladas.factura AS factura,ventas_anuladas.formatofactura,"
                    + " ventas_anuladas.vendedor  AS vendedor,"
                    + " ventas_anuladas.caja AS caja,"
                    + " ventas_anuladas.sucursal AS sucursal,"
                    + "0 AS nrotimbrado,"
                    + " ventas_anuladas.fecha AS fecha,"
                    + " '' AS cliente,"
                    + " ventas_anuladas.comprobante AS comprobante,"
                    + " 0   AS totalneto,"
                    + " 0 AS exenta, "
                    + " 0 AS gravada10,"
                    + " 0 AS iva10,"
                    + " 0 AS gravada5,"
                    + " 0 AS iva5,"
                    + " 'FACTURA ANULADA' AS nombrecliente,"
                    + " '' AS ruc,"
                    + " sucursales.nombre AS nombresucursal,"
                    + " comprobantes.nombre AS nombrecomprobante,"
                    + " '' AS nombrevendedor,"
                    + " '' AS nombrecaja "
                    + " FROM ventas_anuladas "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=ventas_anuladas.comprobante "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=ventas_anuladas.sucursal "
                    + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                    + " AND sucursal= " + suc
                    + " AND comprobantes.libros=1 "
                    + " ORDER BY comprobante, factura ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    cliente cliente = new cliente();
                    venta vta = new venta();
                    vendedor v = new vendedor();
                    caja c = new caja();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(v);
                    vta.setCaja(c);

                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setFactura(rs.getDouble("factura"));
                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.setNrotimbrado(rs.getInt("NroTimbrado"));
                    vta.setFecha(rs.getDate("Fecha"));
                    vta.getCliente().setCodigo(rs.getInt("Cliente"));
                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.setTotalneto(rs.getBigDecimal("total"));
                    vta.setExentas(rs.getBigDecimal("exenta"));
                    vta.setGravadas10(rs.getBigDecimal("gravada10"));
                    vta.setGravadas5(rs.getBigDecimal("gravada5"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));
                    lista.add(vta);
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

    public ArrayList<venta> libroventaxcliente(int cli, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_ventas.factura AS factura,cabecera_ventas.formatofactura,"
                    + "cabecera_ventas.vendedor  AS vendedor,"
                    + "cabecera_ventas.caja AS caja,"
                    + "cabecera_ventas.sucursal AS sucursal,"
                    + "cabecera_ventas.nrotimbrado AS nrotimbrado,"
                    + "cabecera_ventas.fecha AS fecha,"
                    + "cabecera_ventas.cliente AS cliente,"
                    + "cabecera_ventas.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_ventas.totalneto * cabecera_ventas.cotizacion),0),0) AS total,"
                    + "IFNULL(ROUND((cabecera_ventas.exentas * cabecera_ventas.cotizacion),0),0) AS exenta,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion),0),0) AS gravada10,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion) / 11),0),0) AS iva10,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion),0),0) AS gravada5,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion) / 21),0),0) AS iva5,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.ruc AS ruc,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "vendedores.nombre AS nombrevendedor,"
                    + "cajas.nombre AS nombrecaja "
                    + "FROM cabecera_ventas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cabecera_ventas.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=cabecera_ventas.vendedor "
                    + "LEFT JOIN cajas "
                    + "ON CAJAS.codigo=cabecera_ventas.caja "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_ventas.sucursal "
                    + "WHERE cabecera_ventas.cliente=? AND fecha BETWEEN ? AND ? "
                    + " AND comprobantes.libros=1 "
                    + " ORDER BY cabecera_ventas.comprobante, cabecera_ventas.factura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cli);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    cliente cliente = new cliente();
                    venta vta = new venta();
                    vendedor v = new vendedor();
                    caja c = new caja();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(v);
                    vta.setCaja(c);
                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setFactura(rs.getDouble("factura"));
                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.setNrotimbrado(rs.getInt("NroTimbrado"));
                    vta.setFecha(rs.getDate("Fecha"));
                    vta.getCliente().setCodigo(rs.getInt("Cliente"));
                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.setTotalneto(rs.getBigDecimal("total"));
                    vta.setExentas(rs.getBigDecimal("exenta"));
                    vta.setGravadas10(rs.getBigDecimal("gravada10"));
                    vta.setGravadas5(rs.getBigDecimal("gravada5"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));

                    lista.add(vta);
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

    public ArrayList<venta> libroventaxvendedor(int ven, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_ventas.factura AS factura,cabecera_ventas.formatofactura,"
                    + "cabecera_ventas.vendedor  AS vendedor,"
                    + "cabecera_ventas.caja AS caja,"
                    + "cabecera_ventas.sucursal AS sucursal,"
                    + "cabecera_ventas.nrotimbrado AS nrotimbrado,"
                    + "cabecera_ventas.fecha AS fecha,"
                    + "cabecera_ventas.cliente AS cliente,"
                    + "cabecera_ventas.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_ventas.totalneto * cabecera_ventas.cotizacion),0),0) AS total,"
                    + "IFNULL(ROUND((cabecera_ventas.exentas * cabecera_ventas.cotizacion),0),0) AS exenta,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion),0),0) AS gravada10,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion) / 11),0),0) AS iva10,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion),0),0) AS gravada5,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion) / 21),0),0) AS iva5,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.ruc AS ruc,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "vendedores.nombre AS nombrevendedor,"
                    + "cajas.nombre AS nombrecaja "
                    + "FROM cabecera_ventas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cabecera_ventas.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=cabecera_ventas.vendedor "
                    + "LEFT JOIN cajas "
                    + "ON CAJAS.codigo=cabecera_ventas.caja "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_ventas.sucursal "
                    + "WHERE IF(" + ven + "<>0,cabecera_ventas.vendedor=" + ven + ",TRUE) "
                    + " AND cabecera_ventas.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND comprobantes.libros=1 "
                    + " ORDER BY cabecera_ventas.vendedor,cabecera_ventas.comprobante,cabecera_ventas.factura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    cliente cliente = new cliente();
                    venta vta = new venta();
                    vendedor v = new vendedor();
                    caja c = new caja();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(v);
                    vta.setCaja(c);

                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setFactura(rs.getDouble("factura"));
                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.setNrotimbrado(rs.getInt("NroTimbrado"));
                    vta.setFecha(rs.getDate("Fecha"));
                    vta.getCliente().setCodigo(rs.getInt("Cliente"));
                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.setTotalneto(rs.getBigDecimal("total"));
                    vta.setExentas(rs.getBigDecimal("exenta"));
                    vta.setGravadas10(rs.getBigDecimal("gravada10"));
                    vta.setGravadas5(rs.getBigDecimal("gravada5"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));

                    lista.add(vta);
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

    public ArrayList<venta> libroventaxcaja(int caja, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_ventas.factura AS factura,cabecera_ventas.formatofactura,"
                    + "cabecera_ventas.vendedor  AS vendedor,"
                    + "cabecera_ventas.caja AS caja,"
                    + "cabecera_ventas.sucursal AS sucursal,"
                    + "cabecera_ventas.nrotimbrado AS nrotimbrado,"
                    + "cabecera_ventas.fecha AS fecha,"
                    + "cabecera_ventas.cliente AS cliente,"
                    + "cabecera_ventas.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_ventas.totalneto * cabecera_ventas.cotizacion),0),0) AS total,"
                    + "IFNULL(ROUND((cabecera_ventas.exentas * cabecera_ventas.cotizacion),0),0) AS exenta,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion),0),0) AS gravada10,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion) / 11),0),0) AS iva10,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion),0),0) AS gravada5,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion) / 21),0),0) AS iva5,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.ruc AS ruc,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "vendedores.nombre AS nombrevendedor,"
                    + "cajas.nombre AS nombrecaja "
                    + "FROM cabecera_ventas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cabecera_ventas.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=cabecera_ventas.vendedor "
                    + "LEFT JOIN cajas "
                    + "ON CAJAS.codigo=cabecera_ventas.caja "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_ventas.sucursal "
                    + "WHERE cabecera_ventas.caja=? AND fecha BETWEEN ? AND ? "
                    + " AND comprobantes.libros=1 "
                    + " ORDER BY cabecera_ventas.comprobante, cabecera_ventas.factura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, caja);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    cliente cliente = new cliente();
                    venta vta = new venta();
                    vendedor v = new vendedor();
                    caja c = new caja();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(v);
                    vta.setCaja(c);

                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setFactura(rs.getDouble("factura"));
                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.setNrotimbrado(rs.getInt("NroTimbrado"));
                    vta.setFecha(rs.getDate("Fecha"));
                    vta.getCliente().setCodigo(rs.getInt("Cliente"));
                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.setTotalneto(rs.getBigDecimal("total"));
                    vta.setExentas(rs.getBigDecimal("exenta"));
                    vta.setGravadas10(rs.getBigDecimal("gravada10"));
                    vta.setGravadas5(rs.getBigDecimal("gravada5"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));

                    lista.add(vta);
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

    public ArrayList<venta> libroventaxusuario(int caja, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_ventas.factura AS factura,cabecera_ventas.formatofactura,"
                    + "cabecera_ventas.vendedor  AS vendedor,"
                    + "cabecera_ventas.caja AS caja,"
                    + "cabecera_ventas.sucursal AS sucursal,"
                    + "cabecera_ventas.nrotimbrado AS nrotimbrado,"
                    + "cabecera_ventas.fecha AS fecha,"
                    + "cabecera_ventas.cliente AS cliente,"
                    + "cabecera_ventas.comprobante AS comprobante,"
                    + "IFNULL(ROUND((cabecera_ventas.totalneto * cabecera_ventas.cotizacion),0),0) AS total,"
                    + "IFNULL(ROUND((cabecera_ventas.exentas * cabecera_ventas.cotizacion),0),0) AS exenta,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion),0),0) AS gravada10,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas10 * cabecera_ventas.cotizacion) / 11),0),0) AS iva10,"
                    + "IFNULL(ROUND((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion),0),0) AS gravada5,"
                    + "IFNULL(ROUND(((cabecera_ventas.gravadas5 * cabecera_ventas.cotizacion) / 21),0),0) AS iva5,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.ruc AS ruc,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "vendedores.nombre AS nombrevendedor,"
                    + "cajas.nombre AS nombrecaja "
                    + "FROM cabecera_ventas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cabecera_ventas.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=cabecera_ventas.vendedor "
                    + "LEFT JOIN cajas "
                    + "ON CAJAS.codigo=cabecera_ventas.caja "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_ventas.sucursal "
                    + "WHERE cabecera_ventas.idusuario=? AND fecha BETWEEN ? AND ? "
                    + " AND comprobantes.libros=1 "
                    + " ORDER BY cabecera_ventas.comprobante, cabecera_ventas.factura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, caja);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    cliente cliente = new cliente();
                    venta vta = new venta();
                    vendedor v = new vendedor();
                    caja c = new caja();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(v);
                    vta.setCaja(c);

                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setFactura(rs.getDouble("factura"));
                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.setNrotimbrado(rs.getInt("NroTimbrado"));
                    vta.setFecha(rs.getDate("Fecha"));
                    vta.getCliente().setCodigo(rs.getInt("Cliente"));
                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.setTotalneto(rs.getBigDecimal("total"));
                    vta.setExentas(rs.getBigDecimal("exenta"));
                    vta.setGravadas10(rs.getBigDecimal("gravada10"));
                    vta.setGravadas5(rs.getBigDecimal("gravada5"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));

                    lista.add(vta);
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

    public venta ActualizarNotaCredito(venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE  cabecera_ventas SET fecha=?,factura=?,vencimiento=?,cliente=?,sucursal=?,moneda=?,giraduria=?,comprobante=?,cotizacion=?,vendedor=?,caja=?,exentas=?,gravadas10=?,gravadas5=?,totalneto=?,cuotas=?,financiado=?,observacion=?,supago=?,idusuario=?,preventa=?,vencimientotimbrado=?,nrotimbrado=?,turno=?,formatofactura=? WHERE creferencia= '" + v.getCreferencia() + "'");
        ps.setDate(1, v.getFecha());
        ps.setDouble(2, v.getFactura());
        ps.setDate(3, v.getVencimiento());
        ps.setInt(4, v.getCliente().getCodigo());
        ps.setInt(5, v.getSucursal().getCodigo());
        ps.setInt(6, v.getMoneda().getCodigo());
        ps.setInt(7, v.getGiraduria().getCodigo());
        ps.setInt(8, v.getComprobante().getCodigo());
        ps.setBigDecimal(9, v.getCotizacion());
        ps.setInt(10, v.getVendedor().getCodigo());
        ps.setInt(11, v.getCaja().getCodigo());
        ps.setBigDecimal(12, v.getExentas());
        ps.setBigDecimal(13, v.getGravadas10());
        ps.setBigDecimal(14, v.getGravadas5());
        ps.setBigDecimal(15, v.getTotalneto());
        ps.setInt(16, v.getCuotas());
        ps.setBigDecimal(17, v.getFinanciado());
        ps.setString(18, v.getObservacion());
        ps.setBigDecimal(19, v.getSupago());
        ps.setInt(20, v.getIdusuario());
        ps.setInt(21, v.getPreventa());
        ps.setDate(22, v.getVencimientotimbrado());
        ps.setInt(23, v.getNrotimbrado());
        ps.setInt(24, v.getTurno());
        ps.setString(25, v.getFormatofactura());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemFactura(v.getCreferencia(), detalle, con);
        }
        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public ArrayList<venta> DetalleVtaxSucursal(Date fechaini, Date fechafin, int nSucursal, int nSuc, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT factura,codprod,nombreproducto,cantidad,precio,monto, "
                    + " codigo,nombrerubro,fecha,sucursal,nombresucursal,moneda,nombremoneda,porcentaje,nombrecliente "
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_ventas.sucursal=?,TRUE) "
                    + " AND vista_detalle_ventas.moneda=? "
                    + " ORDER BY vista_detalle_ventas.codigo,vista_detalle_ventas.fecha,vista_detalle_ventas.factura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    moneda moneda = new moneda();
                    cliente cliente = new cliente();

                    venta ven = new venta();

                    ven.setSucursal(sucursal);
                    ven.setMoneda(moneda);
                    ven.setCliente(cliente);

                    ven.setFactura(rs.getDouble("factura"));
                    ven.setCodprodventa(rs.getString("codprod"));
                    ven.setNombreproductoventa(rs.getString("nombreproducto"));
                    ven.setCantidadventa(rs.getBigDecimal("cantidad"));
                    ven.setPrecioventa(rs.getBigDecimal("precio"));
                    ven.setTotalneto(rs.getBigDecimal("monto"));
                    ven.setCodrubroventa(rs.getInt("codigo"));
                    ven.setNombrerubroventa(rs.getString("nombrerubro"));
                    ven.setFecha(rs.getDate("fecha"));
                    ven.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ven.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ven.getMoneda().setCodigo(rs.getInt("moneda"));
                    ven.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ven.setPorcentaje(rs.getInt("porcentaje"));
                    ven.getCliente().setNombre(rs.getString("nombrecliente"));
                    lista.add(ven);
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

    public ArrayList<venta> DetalleVtaxCaja(Date fechaini, Date fechafin, int nCaja, int nCaj, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT factura,codprod,nombreproducto,cantidad,precio,monto, "
                    + " codigo,nombrerubro,fecha,caja,nombrecaja,moneda,nombremoneda,porcentaje,nombrecliente "
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_ventas.caja=?,TRUE) "
                    + " AND vista_detalle_ventas.moneda=? "
                    + " ORDER BY vista_detalle_ventas.codigo,vista_detalle_ventas.fecha,vista_detalle_ventas.factura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nCaja);
                ps.setInt(4, nCaj);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    caja caja = new caja();
                    moneda moneda = new moneda();
                    cliente cliente = new cliente();

                    venta ven = new venta();

                    ven.setCaja(caja);
                    ven.setMoneda(moneda);
                    ven.setCliente(cliente);

                    ven.setFactura(rs.getDouble("factura"));
                    ven.setCodprodventa(rs.getString("codprod"));
                    ven.setNombreproductoventa(rs.getString("nombreproducto"));
                    ven.setCantidadventa(rs.getBigDecimal("cantidad"));
                    ven.setPrecioventa(rs.getBigDecimal("precio"));
                    ven.setTotalneto(rs.getBigDecimal("monto"));
                    ven.setCodrubroventa(rs.getInt("codigo"));
                    ven.setNombrerubroventa(rs.getString("nombrerubro"));
                    ven.setFecha(rs.getDate("fecha"));
                    ven.getCaja().setCodigo(rs.getInt("caja"));
                    ven.getCaja().setNombre(rs.getString("nombrecaja"));
                    ven.getMoneda().setCodigo(rs.getInt("moneda"));
                    ven.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ven.setPorcentaje(rs.getInt("porcentaje"));
                    ven.getCliente().setNombre(rs.getString("nombrecliente"));

                    lista.add(ven);
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

    public ArrayList<venta> DetalleVtaxCliente(Date fechaini, Date fechafin, int nCliente, int nCli, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT  cabecera_ventas.factura,codprod,productos.nombre as nombreproducto,"
                    + "detalle_ventas.cantidad,detalle_ventas.precio,monto,"
                    + "cabecera_ventas.fecha,moneda,monedas.nombre as nombremoneda,"
                    + "detalle_ventas.porcentaje,cliente,clientes.nombre as nombrecliente,"
                    + "sucursales.nombre as nombresucursal "
                    + "FROM cabecera_ventas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cabecera_ventas.cliente "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_ventas.sucursal "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=cabecera_ventas.moneda "
                    + "LEFT JOIN detalle_ventas "
                    + "ON detalle_ventas.dreferencia=cabecera_ventas.creferencia "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=detalle_ventas.codprod "
                    + " WHERE cabecera_ventas.fecha between ? AND ?  "
                    + " AND IF(?<>0,cabecera_ventas.cliente=?,TRUE)  "
                    + " AND cabecera_ventas.moneda=? "
                    + "ORDER BY cabecera_ventas.cliente,cabecera_ventas.factura ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nCliente);
                ps.setInt(4, nCli);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    moneda moneda = new moneda();
                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();

                    venta ven = new venta();

                    ven.setMoneda(moneda);
                    ven.setCliente(cliente);
                    ven.setSucursal(sucursal);

                    ven.setFactura(rs.getDouble("factura"));
                    ven.setCodprodventa(rs.getString("codprod"));
                    ven.setNombreproductoventa(rs.getString("nombreproducto"));
                    ven.setCantidadventa(rs.getBigDecimal("cantidad"));
                    ven.setPrecioventa(rs.getBigDecimal("precio"));
                    ven.setTotalneto(rs.getBigDecimal("monto"));
                    ven.setFecha(rs.getDate("fecha"));
                    ven.getMoneda().setCodigo(rs.getInt("moneda"));
                    ven.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ven.setPorcentaje(rs.getInt("porcentaje"));
                    ven.getCliente().setCodigo(rs.getInt("cliente"));
                    ven.getCliente().setNombre(rs.getString("nombrecliente"));
                    ven.getSucursal().setNombre(rs.getString("nombresucursal"));

                    lista.add(ven);
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

    
    public ArrayList<venta> DetalleVtaxClientePendiente(Date fechaini, Date fechafin, int nCliente, int nCli, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT  cabecera_ventas.factura,codprod,productos.nombre as nombreproducto,"
                    + "detalle_ventas.cantidad,detalle_ventas.precio,monto,"
                    + "cabecera_ventas.fecha,cabecera_ventas.moneda,monedas.nombre as nombremoneda,"
                    + "detalle_ventas.porcentaje,cliente,clientes.nombre as nombrecliente,"
                    + "sucursales.nombre as nombresucursal "
                    + "FROM cabecera_ventas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cabecera_ventas.cliente "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_ventas.sucursal "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=cabecera_ventas.moneda "
                    + "LEFT JOIN detalle_ventas "
                    + "ON detalle_ventas.dreferencia=cabecera_ventas.creferencia "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=detalle_ventas.codprod "
                    + " WHERE cabecera_ventas.fecha between ? AND ?  "
                    + " AND IF(?<>0,cabecera_ventas.cliente=?,TRUE)  "
                    + " AND cabecera_ventas.moneda=? "
                    + " AND cabecera_ventas.comprobante IN (2,3) "
                    + "ORDER BY cabecera_ventas.comprobante,cabecera_ventas.fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nCliente);
                ps.setInt(4, nCli);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    moneda moneda = new moneda();
                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();

                    venta ven = new venta();

                    ven.setMoneda(moneda);
                    ven.setCliente(cliente);
                    ven.setSucursal(sucursal);

                    ven.setFactura(rs.getDouble("factura"));
                    ven.setCodprodventa(rs.getString("codprod"));
                    ven.setNombreproductoventa(rs.getString("nombreproducto"));
                    ven.setCantidadventa(rs.getBigDecimal("cantidad"));
                    ven.setPrecioventa(rs.getBigDecimal("precio"));
                    ven.setTotalneto(rs.getBigDecimal("monto"));
                    ven.setFecha(rs.getDate("fecha"));
                    ven.getMoneda().setCodigo(rs.getInt("moneda"));
                    ven.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ven.setPorcentaje(rs.getInt("porcentaje"));
                    ven.getCliente().setCodigo(rs.getInt("cliente"));
                    ven.getCliente().setNombre(rs.getString("nombrecliente"));
                    ven.getSucursal().setNombre(rs.getString("nombresucursal"));

                    lista.add(ven);
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
    
    
    
    public ArrayList<venta> DetalleVtaProductoxCliente(Date fechaini, Date fechafin, int nCliente, String cProducto, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT factura,codprod,nombreproducto,cantidad,precio,monto, "
                    + " codigo,nombrerubro,fecha,moneda,nombremoneda,porcentaje,cliente,nombrecliente,nombresucursal "
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between ? AND ? "
                    + " AND vista_detalle_ventas.cliente=? "
                    + " AND vista_detalle_ventas.codprod=? "
                    + " AND vista_detalle_ventas.moneda=? "
                    + " ORDER BY vista_detalle_ventas.codigo,vista_detalle_ventas.fecha,vista_detalle_ventas.factura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nCliente);
                ps.setString(4, cProducto);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    moneda moneda = new moneda();
                    cliente cliente = new cliente();
                    sucursal sucursal = new sucursal();

                    venta ven = new venta();

                    ven.setMoneda(moneda);
                    ven.setCliente(cliente);
                    ven.setSucursal(sucursal);

                    ven.setFactura(rs.getDouble("factura"));
                    ven.setCodprodventa(rs.getString("codprod"));
                    ven.setNombreproductoventa(rs.getString("nombreproducto"));
                    ven.setCantidadventa(rs.getBigDecimal("cantidad"));
                    ven.setPrecioventa(rs.getBigDecimal("precio"));
                    ven.setTotalneto(rs.getBigDecimal("monto"));
                    ven.setCodrubroventa(rs.getInt("codigo"));
                    ven.setNombrerubroventa(rs.getString("nombrerubro"));
                    ven.setFecha(rs.getDate("fecha"));
                    ven.getMoneda().setCodigo(rs.getInt("moneda"));
                    ven.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ven.setPorcentaje(rs.getInt("porcentaje"));
                    ven.getCliente().setCodigo(rs.getInt("cliente"));
                    ven.getCliente().setNombre(rs.getString("nombrecliente"));
                    ven.getSucursal().setNombre(rs.getString("nombresucursal"));

                    lista.add(ven);
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

    public ArrayList<venta> DetalleVtaxVendedor(Date fechaini, Date fechafin, int nSucursal, int nSuc, int nVendedor, int nVen, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT factura,codprod,nombreproducto,cantidad,precio,monto,codigo,nombrerubro,fecha, "
                    + " moneda,nombremoneda,porcentaje,cliente,nombrecliente,sucursal,nombresucursal,vendedor,nombrevendedor "
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_ventas.sucursal=?,TRUE) "
                    + " AND IF(?<>0,vista_detalle_ventas.vendedor=?,TRUE) "
                    + " AND vista_detalle_ventas.moneda=? "
                    + " ORDER BY vista_detalle_ventas.vendedor,vista_detalle_ventas.factura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ps.setInt(5, nVendedor);
                ps.setInt(6, nVen);
                ps.setInt(7, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    vendedor vendedor = new vendedor();
                    moneda moneda = new moneda();
                    cliente cliente = new cliente();

                    venta ven = new venta();

                    ven.setSucursal(sucursal);
                    ven.setVendedor(vendedor);
                    ven.setMoneda(moneda);
                    ven.setCliente(cliente);

                    ven.setFactura(rs.getDouble("factura"));
                    ven.setCodprodventa(rs.getString("codprod"));
                    ven.setNombreproductoventa(rs.getString("nombreproducto"));
                    ven.setCantidadventa(rs.getBigDecimal("cantidad"));
                    ven.setPrecioventa(rs.getBigDecimal("precio"));
                    ven.setTotalneto(rs.getBigDecimal("monto"));
                    ven.setCodrubroventa(rs.getInt("codigo"));
                    ven.setNombrerubroventa(rs.getString("nombrerubro"));
                    ven.setFecha(rs.getDate("fecha"));
                    ven.getMoneda().setCodigo(rs.getInt("moneda"));
                    ven.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ven.setPorcentaje(rs.getInt("porcentaje"));
                    ven.getCliente().setCodigo(rs.getInt("cliente"));
                    ven.getCliente().setNombre(rs.getString("nombrecliente"));
                    ven.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ven.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ven.getVendedor().setCodigo(rs.getInt("vendedor"));
                    ven.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    lista.add(ven);
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

    public ArrayList<venta> DetalleVtaxRubro(Date fechaini, Date fechafin, int nRubro, int nRu, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT factura,codprod,nombreproducto,cantidad,precio,monto, "
                    + " codigo,nombrerubro,fecha,codigo,nombrerubro,moneda,nombremoneda,porcentaje,nombrecliente "
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between ? AND ? "
                    + " AND IF(?<>0,vista_detalle_ventas.codigo=?,TRUE) "
                    + " AND vista_detalle_ventas.moneda=? "
                    + " ORDER BY vista_detalle_ventas.codigo,vista_detalle_ventas.fecha,vista_detalle_ventas.factura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nRubro);
                ps.setInt(4, nRu);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    rubro rubro = new rubro();
                    moneda moneda = new moneda();
                    cliente cliente = new cliente();

                    venta ven = new venta();

                    ven.setRubro(rubro);
                    ven.setMoneda(moneda);
                    ven.setCliente(cliente);

                    ven.setFactura(rs.getDouble("factura"));
                    ven.setCodprodventa(rs.getString("codprod"));
                    ven.setNombreproductoventa(rs.getString("nombreproducto"));
                    ven.setCantidadventa(rs.getBigDecimal("cantidad"));
                    ven.setPrecioventa(rs.getBigDecimal("precio"));
                    ven.setTotalneto(rs.getBigDecimal("monto"));
                    ven.setCodrubroventa(rs.getInt("codigo"));
                    ven.setNombrerubroventa(rs.getString("nombrerubro"));
                    ven.setFecha(rs.getDate("fecha"));
                    ven.getRubro().setCodigo(rs.getInt("codigo"));
                    ven.getRubro().setNombre(rs.getString("nombrerubro"));
                    ven.getMoneda().setCodigo(rs.getInt("moneda"));
                    ven.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ven.setPorcentaje(rs.getInt("porcentaje"));
                    ven.getCliente().setNombre(rs.getString("nombrecliente"));

                    lista.add(ven);
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

    public ArrayList<venta> DetalleVtaxProducto(Date fechaini, Date fechafin, String nProducto, int nSucursal) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT factura,codprod,nombreproducto,cantidad,precio,monto, "
                    + " fecha,sucursal,nombresucursal,moneda,nombremoneda,nombrecliente, vendedor,nombrevendedor "
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND vista_detalle_ventas.codprod='" + nProducto + "'";
            if (nSucursal != 0) {
                cSql = cSql + " AND vista_detalle_ventas.sucursal=" + nSucursal;
            }
            cSql = cSql + " ORDER BY vista_detalle_ventas.codigo,vista_detalle_ventas.fecha,vista_detalle_ventas.factura";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    vendedor vendedor = new vendedor();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();

                    venta ven = new venta();

                    ven.setVendedor(vendedor);
                    ven.setSucursal(sucursal);
                    ven.setCliente(cliente);

                    ven.setFactura(rs.getDouble("factura"));
                    ven.setCodprodventa(rs.getString("codprod"));
                    ven.setNombreproductoventa(rs.getString("nombreproducto"));
                    ven.setCantidadventa(rs.getBigDecimal("cantidad"));
                    ven.setPrecioventa(rs.getBigDecimal("precio"));
                    ven.setTotalneto(rs.getBigDecimal("monto"));
                    ven.setFecha(rs.getDate("fecha"));
                    ven.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ven.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ven.getVendedor().setCodigo(rs.getInt("vendedor"));
                    ven.getVendedor().setNombre(rs.getString("nombrevendedor"));
                    ven.getCliente().setNombre(rs.getString("nombrecliente"));

                    lista.add(ven);
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

    public venta AgregarFacturaRemota(venta v) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_ventas (creferencia,fecha,factura,vencimiento,cliente,sucursal,moneda,giraduria,comprobante,cotizacion,vendedor,caja,exentas,gravadas10,gravadas5,totalneto,cuotas,financiado,observacion,supago,idusuario,preventa,vencimientotimbrado,nrotimbrado,turno,formatofactura) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getFactura());
        ps.setDate(4, v.getVencimiento());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setInt(8, v.getGiraduria().getCodigo());
        ps.setInt(9, v.getComprobante().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getVendedor().getCodigo());
        ps.setInt(12, v.getCaja().getCodigo());
        ps.setBigDecimal(13, v.getExentas());
        ps.setBigDecimal(14, v.getGravadas10());
        ps.setBigDecimal(15, v.getGravadas5());
        ps.setBigDecimal(16, v.getTotalneto());
        ps.setInt(17, v.getCuotas());
        ps.setBigDecimal(18, v.getFinanciado());
        ps.setString(19, v.getObservacion());
        ps.setBigDecimal(20, v.getSupago());
        ps.setInt(21, v.getIdusuario());
        ps.setInt(22, v.getPreventa());
        ps.setDate(23, v.getVencimientotimbrado());
        ps.setInt(24, v.getNrotimbrado());
        ps.setInt(25, v.getTurno());
        ps.setString(26, v.getFormatofactura());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> CARGA DE VENTAS " + ex.getLocalizedMessage());
        }

        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public boolean guardarItemFacturaRemota(String detalleventa) throws SQLException {
        boolean guardadetalle = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectadetalle = st.getConnection();
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalleventa);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO detalle_ventas("
                            + "dreferencia,"
                            + "codprod,"
                            + "prcosto,"
                            + "cantidad,"
                            + "precio,"
                            + "monto,"
                            + "impiva,"
                            + "porcentaje,"
                            + "suc"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("dreferencia").getAsString());
                        ps.setString(2, obj.get("codprod").getAsString());
                        ps.setBigDecimal(3, obj.get("prcosto").getAsBigDecimal());
                        ps.setBigDecimal(4, obj.get("cantidad").getAsBigDecimal());
                        ps.setBigDecimal(5, obj.get("precio").getAsBigDecimal());
                        ps.setBigDecimal(6, obj.get("monto").getAsBigDecimal());
                        ps.setBigDecimal(7, obj.get("impiva").getAsBigDecimal());
                        ps.setBigDecimal(8, obj.get("porcentaje").getAsBigDecimal());
                        ps.setString(9, obj.get("suc").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardadetalle = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("DETALLE VENTAS REMOTAS--->" + ex.getLocalizedMessage());
                    guardadetalle = false;
                    break;
                }
            }

        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardadetalle = false;
        }
        conectadetalle.close();
        return guardadetalle;
    }

    public venta buscarVtaServer(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        venta vta = new venta();

        try {

            String sql = "SELECT creferencia "
                    + " FROM cabecera_ventas "
                    + " WHERE cabecera_ventas.creferencia= ?"
                    + " ORDER BY cabecera_ventas.creferencia ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    vta.setCreferencia(rs.getString("creferencia"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return vta;
    }

    public ArrayList<venta> ResumenIngresoxSucursal(Date fechaini, Date fechafin, int nSucursal, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_ventas.factura,cabecera_ventas.fecha,sucursales.nombre AS nombresucursal,"
                    + " clientes.codigo AS cuenta, clientes.nombre AS nombrecliente,"
                    + "monedas.nombre AS nombremoneda,detalle_forma_cobro.netocobrado,1 AS tipo "
                    + " FROM cabecera_ventas "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=cabecera_ventas.sucursal "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=cabecera_ventas.cliente "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=cabecera_ventas.moneda "
                    + " LEFT JOIN detalle_forma_cobro "
                    + " ON detalle_forma_cobro.idmovimiento=cabecera_ventas.creferencia "
                    + " WHERE cabecera_ventas.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND IF(" + nSucursal + "<>0,cabecera_ventas.sucursal=" + nSucursal + ",TRUE) "
                    + " AND cabecera_ventas.moneda=" + nMoneda
                    + " AND detalle_forma_cobro.netocobrado<>0  "
                    + " UNION "
                    + " SELECT detalle_cobranzas.nrofactura,cobranzas.fecha,sucursales.nombre AS nombresucursal,"
                    + "clientes.codigo AS cuenta, clientes.nombre AS nombrecliente,monedas.nombre AS nombremoneda,"
                    + "detalle_forma_cobro.netocobrado,2 AS tipo "
                    + " FROM cobranzas "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=cobranzas.sucursal "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=cobranzas.cliente "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=cobranzas.moneda "
                    + "LEFT JOIN detalle_cobranzas "
                    + "ON detalle_cobranzas.iddetalle=cobranzas.idpagos  "
                    + " LEFT JOIN detalle_forma_cobro "
                    + " ON detalle_forma_cobro.idmovimiento=cobranzas.idpagos "
                    + " WHERE cobranzas.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND IF(" + nSucursal + "<>0,cobranzas.sucursal=" + nSucursal + ",TRUE) "
                    + " AND cobranzas.moneda=" + nMoneda
                    + " AND detalle_forma_cobro.netocobrado<>0  "
                    + " GROUP BY detalle_cobranzas.idfactura "
                    + " ORDER BY tipo,factura";
            System.out.println(cSql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();
                    detalle_forma_cobro dtc = new detalle_forma_cobro();

                    venta vta = new venta();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);
                    vta.setDetalle_forma_cobro(dtc);

                    vta.setFactura(rs.getDouble("factura"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.getCliente().setCodigo(rs.getInt("cuenta"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));
                    vta.getDetalle_forma_cobro().setNetocobrado(rs.getDouble("netocobrado"));

                    lista.add(vta);
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

    public venta AgregarFacturaInmo(venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_ventas (creferencia,fecha,factura,vencimiento,cliente,sucursal,moneda,giraduria,comprobante,cotizacion,vendedor,caja,exentas,gravadas10,gravadas5,totalneto,cuotas,financiado,observacion,supago,idusuario,preventa,vencimientotimbrado,nrotimbrado,turno,formatofactura) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getFactura());
        ps.setDate(4, v.getVencimiento());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setInt(8, v.getGiraduria().getCodigo());
        ps.setInt(9, v.getComprobante().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getVendedor().getCodigo());
        ps.setInt(12, v.getCaja().getCodigo());
        ps.setBigDecimal(13, v.getExentas());
        ps.setBigDecimal(14, v.getGravadas10());
        ps.setBigDecimal(15, v.getGravadas5());
        ps.setBigDecimal(16, v.getTotalneto());
        ps.setInt(17, v.getCuotas());
        ps.setBigDecimal(18, v.getFinanciado());
        ps.setString(19, v.getObservacion());
        ps.setBigDecimal(20, v.getSupago());
        ps.setInt(21, v.getIdusuario());
        ps.setInt(22, v.getPreventa());
        ps.setDate(23, v.getVencimientotimbrado());
        ps.setInt(24, v.getNrotimbrado());
        ps.setInt(25, v.getTurno());
        ps.setString(26, v.getFormatofactura());
        ps.executeUpdate();
        guardarItemFacturaInmo(v.getCreferencia(), detalle, con);
        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public boolean guardarItemFacturaInmo(String id, String detalle, Conexion conexion) throws SQLException {
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
                    String sql = "INSERT INTO detalle_ventas("
                            + "dreferencia,"
                            + "codprod,"
                            + "prcosto,"
                            + "cantidad,"
                            + "precio,"
                            + "monto,"
                            + "impiva,"
                            + "porcentaje,"
                            + "edificio,"
                            + "vence,"
                            + "suc"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("codprod").getAsString());
                        ps.setBigDecimal(3, obj.get("prcosto").getAsBigDecimal());
                        ps.setBigDecimal(4, obj.get("cantidad").getAsBigDecimal());
                        ps.setBigDecimal(5, obj.get("precio").getAsBigDecimal());
                        ps.setBigDecimal(6, obj.get("monto").getAsBigDecimal());
                        ps.setBigDecimal(7, obj.get("impiva").getAsBigDecimal());
                        ps.setBigDecimal(8, obj.get("porcentaje").getAsBigDecimal());
                        ps.setString(9, obj.get("edificio").getAsString());
                        ps.setString(10, obj.get("vence").getAsString());
                        ps.setString(11, obj.get("suc").getAsString());
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
        conn.close();
        return guardado;
    }

    public venta VerificarFactura(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        venta vta = new venta();

        try {

            String sql = "SELECT creferencia,formatofactura "
                    + " FROM cabecera_ventas "
                    + " WHERE cabecera_ventas.formatofactura= ?"
                    + " ORDER BY cabecera_ventas.formatofactura ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    vta.setFormatofactura(rs.getString("formatofactura"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return vta;
    }

    public ArrayList<venta> libroventares90(int suc, Date fechaini, Date fechafin) throws SQLException, IOException {
        ArrayList<venta> lista = new ArrayList<venta>();
        FileWriter flwriter = null;
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        String sqlregistro = "CREATE TEMPORARY TABLE ventaresolucion ("
                + "codigoregistro INT(1) DEFAULT 1,"
                + "codigoidentidicadorcomprador INT(2) DEFAULT 11,"
                + "numeroidentifacion CHAR(20),"
                + "razonsocial CHAR(250),"
                + "codigocomprobante int(3),"
                + "fechaemision CHAR(10),"
                + "timbrado DECIMAL(8,0) DEFAULT 0,"
                + "numerocomprobante CHAR(20),"
                + "montogravado10 DECIMAL (20,0) DEFAULT 0,"
                + "montogravado5 DECIMAL (20,0) DEFAULT 0,"
                + "montoexento DECIMAL (20,0) DEFAULT 0,"
                + "montototal DECIMAL (20,0) DEFAULT 0,"
                + "condicionventa INT(1) DEFAULT 1,"
                + "monedaextranjera CHAR(1) DEFAULT 'N',"
                + "imputaaliva CHAR(1) DEFAULT 'S',"
                + "imputaalire CHAR(1) DEFAULT 'N',"
                + "imputaalirprsp CHAR(1) DEFAULT 'S',"
                + "comprobanteasociado CHAR(20)DEFAULT '',"
                + "timbradoasociado  DECIMAL(8,0) DEFAULT 0)";

        PreparedStatement psregistro = conn.prepareStatement(sqlregistro);
        psregistro.executeUpdate(sqlregistro);

        String sqlGrabar = "INSERT INTO ventaresolucion"
                + "(numeroidentifacion,razonsocial,codigocomprobante,fechaemision,timbrado,numerocomprobante,"
                + "montogravado10,montogravado5,montoexento,montototal,"
                + "condicionventa) "
                + " SELECT clientes.ruc,clientes.nombre,"
                + " CASE "
                + "WHEN cabecera_ventas.totalneto>0 THEN 109 "
                + "WHEN cabecera_ventas.totalneto<0 THEN 110 "
                + "END AS codigocomprobante, "
                + "DATE_FORMAT(cabecera_ventas.fecha, '%d/%m/%Y') AS fecha,"
                + "cabecera_ventas.nrotimbrado,"
                + "cabecera_ventas.formatofactura,"
                + "cabecera_ventas.gravadas10,"
                + "cabecera_ventas.gravadas5,"
                + "cabecera_ventas.exentas,"
                + "cabecera_ventas.totalneto, "
                + " CASE "
                + "WHEN cabecera_ventas.comprobante=1 AND cabecera_ventas.totalneto>0 THEN 1 "
                + "WHEN cabecera_ventas.comprobante<>1 AND cabecera_ventas.totalneto>0 THEN 2 "
                + "WHEN cabecera_ventas.comprobante<>1 AND cabecera_ventas.totalneto<1 THEN 1 "
                + "END AS condicionventa "
                + "FROM cabecera_ventas "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=cabecera_ventas.cliente "
                + "LEFT JOIN comprobantes "
                + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cabecera_ventas.sucursal=" + suc
                + " AND comprobantes.libros=1 ORDER BY comprobante,factura ";

        PreparedStatement psplandatos = conn.prepareStatement(sqlGrabar);
        psplandatos.executeUpdate(sqlGrabar);

        String sqlventares = "SELECT *"
                + " FROM ventaresolucion ";

        PreparedStatement psventares = conn.prepareStatement(sqlventares);
        ResultSet resolucion = psventares.executeQuery(sqlventares);

        flwriter = new FileWriter("C:\\Resolucion\\estudiantes.txt");
        BufferedWriter bfwriter = new BufferedWriter(flwriter);
        try {
            while (resolucion.next()) {
                //bfwriter.write(resolucion.getInt(sqlventares)estudiante.getCedula() + "," + estudiante.getNombres() + "," + estudiante.getApellidos()						+ "," + estudiante.getTelefono() + "," + estudiante.getDireccion() + "\n");

                System.out.println(resolucion.getString("razonsocial"));

                bfwriter.write(resolucion.getInt("codigoregistro") + "\t"
                        + resolucion.getInt("codigoidentidicadorcomprador") + "\t"
                        + resolucion.getString("numeroidentifacion") + "\t"
                        + resolucion.getString("razonsocial") + "\t"
                        + resolucion.getInt("codigocomprobante") + "\t"
                        + resolucion.getString("fechaemision") + "\t"
                        + formatosinpunto.format(resolucion.getDouble("timbrado")) + "\t"
                        + resolucion.getString("numerocomprobante") + "\t"
                        + formatea.format(resolucion.getDouble("montogravado10")) + "\t"
                        + formatea.format(resolucion.getDouble("montogravado5")) + "\t"
                        + formatea.format(resolucion.getDouble("montoexento")) + "\t"
                        + formatea.format(resolucion.getDouble("montototal")) + "\t"
                        + formatosinpunto.format(resolucion.getInt("condicionventa")) + "\t"
                        + resolucion.getString("monedaextranjera") + "\t"
                        + resolucion.getString("imputaaliva") + "\t"
                        + resolucion.getString("imputaalire") + "\t"
                        + resolucion.getString("imputaalirprsp") + "\t"
                        + resolucion.getString("comprobanteasociado") + "\t"
                        + formatosinpunto.format(resolucion.getDouble("timbradoasociado")) + "\n");

            }
            bfwriter.close();
            System.out.println("Archivo creado satisfactoriamente..");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (flwriter != null) {
                try {//cierra el flujo principal
                    flwriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        st.close();
        conn.close();
        return lista;
    }

    public venta ActualizarTicket(venta v) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  cabecera_ventas SET factura=?,"
                + "vencimientotimbrado=?,nrotimbrado=?,"
                + "formatofactura=?,ticketold=?,sucursal=? WHERE creferencia= '" + v.getCreferencia() + "'");
        ps.setDouble(1, v.getFactura());
        ps.setDate(2, v.getVencimientotimbrado());
        ps.setInt(3, v.getNrotimbrado());
        ps.setString(4, v.getFormatofactura());
        ps.setString(5, v.getTicketold());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        cnn.close();
        return v;
    }

    public venta buscarIdReferencia(String id, int cliente) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        venta vta = new venta();

        try {

            String sql = "SELECT creferencia,cabecera_ventas.factura,"
                    + "cabecera_ventas.formatofactura,cabecera_ventas.nrotimbrado "
                    + " FROM cabecera_ventas "
                    + " WHERE cabecera_ventas.formatofactura='" + id + "'"
                    + " AND cabecera_ventas.cliente=" + cliente
                    + " ORDER BY cabecera_ventas.formatofactura ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    vta.setCreferencia(rs.getString("creferencia"));
                    vta.setFactura(rs.getDouble("factura"));
                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setNrotimbrado(rs.getInt("nrotimbrado"));
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return vta;
    }

    public venta CalcularTotalFerremax(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        venta vta = new venta();

        try {

            String sql = "SELECT creferencia,sum(monto) as ndescuento "
                    + " FROM cabecera_ventas "
                    + " LEFT JOIN detalle_ventas "
                    + " ON detalle_ventas.dreferencia=cabecera_ventas.creferencia "
                    + " WHERE cabecera_ventas.creferencia='" + id + "'"
                    + " AND detalle_ventas.monto>0 "
                    + " GROUP BY cabecera_ventas.creferencia ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    vta.setCreferencia(rs.getString("creferencia"));
                    vta.setTotaldescuento(rs.getBigDecimal("ndescuento"));
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return vta;
    }

    public venta AgregarFacturaVentaFerremax(String token, venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_ventas"
                + "(creferencia,fecha,factura,"
                + "vencimiento,cliente,sucursal,"
                + "moneda,giraduria,comprobante,"
                + "cotizacion,vendedor,caja,"
                + "exentas,gravadas10,gravadas5,"
                + "totalneto,cuotas,financiado,"
                + "observacion,supago,idusuario,"
                + "preventa,vencimientotimbrado,sucambio,"
                + "nrotimbrado,turno,formatofactura,"
                + "centro) "
                + "VALUES (?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getFactura());
        ps.setDate(4, v.getVencimiento());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setInt(8, v.getGiraduria().getCodigo());
        ps.setInt(9, v.getComprobante().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getVendedor().getCodigo());
        ps.setInt(12, v.getCaja().getCodigo());
        ps.setBigDecimal(13, v.getExentas());
        ps.setBigDecimal(14, v.getGravadas10());
        ps.setBigDecimal(15, v.getGravadas5());
        ps.setBigDecimal(16, v.getTotalneto());
        ps.setInt(17, v.getCuotas());
        ps.setBigDecimal(18, v.getFinanciado());
        ps.setString(19, v.getObservacion());
        ps.setBigDecimal(20, v.getSupago());
        ps.setInt(21, v.getIdusuario());
        ps.setInt(22, v.getPreventa());
        ps.setDate(23, v.getVencimientotimbrado());
        ps.setBigDecimal(24, v.getSucambio());
        ps.setInt(25, v.getNrotimbrado());
        ps.setInt(26, v.getTurno());
        ps.setString(27, v.getFormatofactura());
        ps.setInt(28, v.getCentro());
        if (Config.cToken == token) {
            ps.executeUpdate();
            guardarItemFactura(v.getCreferencia(), detalle, con);
        } else {
            System.out.println("USUARIO NO AUTORIZADO");
        }
        System.out.println("CABECERA VENTAS");
        st.close();
        ps.close();
        conn.close();
        return v;

    }

    public venta buscarIdFerremax(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        venta vta = new venta();

        try {

            String sql = "SELECT creferencia,cabecera_ventas.factura,cabecera_ventas.fecha,cabecera_ventas.factura,cabecera_ventas.vencimiento,"
                    + "cabecera_ventas.cliente,cabecera_ventas.sucursal,cabecera_ventas.moneda,cabecera_ventas.giraduria,comprobante,cotizacion,"
                    + "cabecera_ventas.vendedor,caja,supago,sucambio,totalbruto,totaldescuento,exentas,gravadas10,gravadas5,totalneto,cuotas,"
                    + "anuladopor,fechaanulado,registro,preventa,cierre,financiado,observacion,"
                    + "enviactacte,comision_vendedor,remisionnro,cabecera_ventas.marcavehiculo,cabecera_ventas.nombreconductor,direccionconductor,"
                    + "cabecera_ventas.chapa,cabecera_ventas.cedula,fechainitraslado,fechafintraslado,llegada,nombregarante,cabecera_ventas.direcciongarante,"
                    + "cabecera_ventas.cedulagarante,cabecera_ventas.turno,idusuario,ordencompra,contrato,iddireccion,cabecera_ventas.nrotimbrado,cabecera_ventas.vencimientotimbrado,"
                    + "centro,comanda,parallevar,cabecera_ventas.horagrabado,cabecera_ventas.formatofactura,"
                    + "clientes.nombre AS nombrecliente,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "monedas.nombre  AS nombremoneda,"
                    + "monedas.etiqueta  AS etiqueta,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "vendedores.nombre AS nombrevendedor,"
                    + "cajas.nombre AS nombrecaja,"
                    + "clientes.localidad,clientes.direccion,clientes.ruc,"
                    + "localidades.nombre AS nombrelocalidad,"
                    + "giradurias.nombre AS nombregiraduria, "
                    + "obras.nombre as nombreobra "
                    + " FROM cabecera_ventas "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=cabecera_ventas.cliente "
                    + " LEFT JOIN localidades "
                    + " ON localidades.codigo=clientes.localidad "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=cabecera_ventas.sucursal "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=cabecera_ventas.moneda "
                    + " LEFT JOIN giradurias "
                    + " ON giradurias.codigo=cabecera_ventas.giraduria "
                    + " LEFT JOIN cajas "
                    + " ON cajas.codigo=cabecera_ventas.caja "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=cabecera_ventas.comprobante "
                    + " LEFT JOIN vendedores "
                    + " ON vendedores.codigo=cabecera_ventas.vendedor "
                    + " LEFT JOIN obras "
                    + " ON obras.codigo=cabecera_ventas.centro "
                    + "WHERE cabecera_ventas.creferencia= ?"
                    + " ORDER BY cabecera_ventas.factura ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();
                    caja caja = new caja();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();

                    localidad localidad = new localidad();

                    vta.setGiraduria(giraduria);
                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);
                    vta.setCaja(caja);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);

                    vta.setCreferencia(rs.getString("creferencia"));
                    vta.setFactura(rs.getDouble("factura"));
                    vta.setFecha(rs.getDate("fecha"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));

                    vta.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    vta.getGiraduria().setNombre(rs.getString("nombregiraduria"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));

                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));
                    vta.getMoneda().setEtiqueta(rs.getString("etiqueta"));

                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));

                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setCuotas(rs.getInt("cuotas"));
                    vta.setNrotimbrado(rs.getInt("nrotimbrado"));
                    vta.setVencimientotimbrado(rs.getDate("vencimientotimbrado"));
                    vta.setCentro(rs.getInt("centro"));
                    if (rs.getString("nombreobra") == null) {
                        vta.setNombreobra("SD");
                    } else {
                        vta.setNombreobra(rs.getString("nombreobra"));
                    }
                    vta.setCotizacion(rs.getBigDecimal("cotizacion"));
                    vta.setExentas(rs.getBigDecimal("exentas"));
                    vta.setGravadas5(rs.getBigDecimal("gravadas5"));
                    vta.setGravadas10(rs.getBigDecimal("gravadas10"));
                    vta.setFinanciado(rs.getBigDecimal("financiado"));
                    vta.setTotalneto(rs.getBigDecimal("totalneto"));
                    vta.setSupago(rs.getBigDecimal("supago"));
                    vta.setComanda(rs.getInt("comanda"));
                    vta.setTurno(rs.getInt("turno"));
                    vta.setContrato(rs.getInt("contrato"));
                    vta.setOrdencompra(rs.getInt("ordencompra"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setIdusuario(rs.getInt("idusuario"));
                    vta.setComision_vendedor(rs.getBigDecimal("comision_vendedor"));
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return vta;
    }

    public venta AgregarFacturaFerremax(String token, venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_ventas (creferencia,fecha,factura,"
                + "vencimiento,cliente,sucursal,moneda,giraduria,"
                + "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,"
                + "gravadas5,totalneto,cuotas,financiado,observacion,"
                + "supago,idusuario,preventa,vencimientotimbrado,sucambio,"
                + "nrotimbrado,turno,formatofactura,centro) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getFactura());
        ps.setDate(4, v.getVencimiento());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setInt(8, v.getGiraduria().getCodigo());
        ps.setInt(9, v.getComprobante().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getVendedor().getCodigo());
        ps.setInt(12, v.getCaja().getCodigo());
        ps.setBigDecimal(13, v.getExentas());
        ps.setBigDecimal(14, v.getGravadas10());
        ps.setBigDecimal(15, v.getGravadas5());
        ps.setBigDecimal(16, v.getTotalneto());
        ps.setInt(17, v.getCuotas());
        ps.setBigDecimal(18, v.getFinanciado());
        ps.setString(19, v.getObservacion());
        ps.setBigDecimal(20, v.getSupago());
        ps.setInt(21, v.getIdusuario());
        ps.setInt(22, v.getPreventa());
        ps.setDate(23, v.getVencimientotimbrado());
        ps.setBigDecimal(24, v.getSucambio());
        ps.setInt(25, v.getNrotimbrado());
        ps.setInt(26, v.getTurno());
        ps.setString(27, v.getFormatofactura());
        ps.setInt(28, v.getCentro());
        if (Config.cToken == token) {
            ps.executeUpdate();
            guardarItemFactura(v.getCreferencia(), detalle, con);
        } else {
            System.out.println("USUARIO NO AUTORIZADO");
        }
        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public venta ActualizarVentaFerremax(venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  cabecera_ventas SET fecha=?,factura=?,"
                + "vencimiento=?,cliente=?,sucursal=?,"
                + "moneda=?,giraduria=?,comprobante=?,"
                + "cotizacion=?,vendedor=?,caja=?,"
                + "exentas=?,gravadas10=?,gravadas5=?,"
                + "totalneto=?,cuotas=?,financiado=?,"
                + "observacion=?,supago=?,idusuario=?,"
                + "preventa=?,vencimientotimbrado=?,nrotimbrado=?,"
                + "turno=?,formatofactura=?,sucambio=?,centro=? WHERE creferencia= '" + v.getCreferencia() + "'");
        ps.setDate(1, v.getFecha());
        ps.setDouble(2, v.getFactura());
        ps.setDate(3, v.getVencimiento());
        ps.setInt(4, v.getCliente().getCodigo());
        ps.setInt(5, v.getSucursal().getCodigo());
        ps.setInt(6, v.getMoneda().getCodigo());
        ps.setInt(7, v.getGiraduria().getCodigo());
        ps.setInt(8, v.getComprobante().getCodigo());
        ps.setBigDecimal(9, v.getCotizacion());
        ps.setInt(10, v.getVendedor().getCodigo());
        ps.setInt(11, v.getCaja().getCodigo());
        ps.setBigDecimal(12, v.getExentas());
        ps.setBigDecimal(13, v.getGravadas10());
        ps.setBigDecimal(14, v.getGravadas5());
        ps.setBigDecimal(15, v.getTotalneto());
        ps.setInt(16, v.getCuotas());
        ps.setBigDecimal(17, v.getFinanciado());
        ps.setString(18, v.getObservacion());
        ps.setBigDecimal(19, v.getSupago());
        ps.setInt(20, v.getIdusuario());
        ps.setInt(21, v.getPreventa());
        ps.setDate(22, v.getVencimientotimbrado());
        ps.setInt(23, v.getNrotimbrado());
        ps.setInt(24, v.getTurno());
        ps.setString(25, v.getFormatofactura());
        ps.setBigDecimal(26, v.getSucambio());
        ps.setInt(27, v.getCentro());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemFactura(v.getCreferencia(), detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return v;
    }

    public ArrayList<venta> ResumenVtaxClienteRuc(Date fechaini, Date fechafin, String cRuc, int nMoneda) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT sucursal,codprod,cliente,nombrecliente,nombreproducto,"
                    + "SUM(cantidad) as tcantidad,AVG(precio) as promedio,"
                    + "precio,SUM(monto) as totalventa,fecha,nombrerubro,codigo,moneda,nombremoneda,nombresucursal,ruc "
                    + " FROM vista_detalle_ventas "
                    + " WHERE vista_detalle_ventas.fecha between ? AND ? "
                    + " AND vista_detalle_ventas.ruc=? "
                    + " AND vista_detalle_ventas.moneda=? "
                    + " GROUP BY ruc,codprod "
                    + " ORDER BY ruc,codprod ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setString(3, cRuc);
                ps.setInt(4, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();
                    producto producto = new producto();

                    venta vta = new venta();

                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);

                    vta.setCodprodventa(rs.getString("codprod"));
                    vta.setNombreproductoventa(rs.getString("nombreproducto"));
                    vta.setTotalneto(rs.getBigDecimal("totalventa"));
                    vta.setCodrubroventa(rs.getInt("codigo"));
                    vta.setNombrerubroventa(rs.getString("nombrerubro"));
                    vta.setCantidadventa(rs.getBigDecimal("tcantidad"));
                    vta.setPrecioventa(rs.getBigDecimal("precio"));
                    vta.setPromedio(rs.getBigDecimal("promedio"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.getCliente().setRuc(rs.getString("ruc"));

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));

                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));

                    vta.setFecha(rs.getDate("fecha"));

                    lista.add(vta);
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

    public ArrayList<venta> libroventares90Excel(int suc, Date fechaini, Date fechafin) throws SQLException, IOException {
        String nombrearchivo = "";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            nombrearchivo = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(nombrearchivo);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sqlregistro = "CREATE TEMPORARY TABLE ventaresolucion ("
                + "codigoregistro INT(1) DEFAULT 1,"
                + "codigoidentidicadorcomprador INT(2) DEFAULT 11,"
                + "numeroidentifacion CHAR(20),"
                + "razonsocial CHAR(250),"
                + "codigocomprobante int(3),"
                + "fechaemision CHAR(10),"
                + "timbrado DECIMAL(8,0) DEFAULT 0,"
                + "numerocomprobante CHAR(20),"
                + "montogravado10 DECIMAL (20,0) DEFAULT 0,"
                + "montogravado5 DECIMAL (20,0) DEFAULT 0,"
                + "montoexento DECIMAL (20,0) DEFAULT 0,"
                + "montototal DECIMAL (20,0) DEFAULT 0,"
                + "condicionventa INT(1) DEFAULT 1,"
                + "monedaextranjera CHAR(1) DEFAULT 'N',"
                + "imputaaliva CHAR(1) DEFAULT 'S',"
                + "imputaalire CHAR(1) DEFAULT 'N',"
                + "imputaalirprsp CHAR(1) DEFAULT 'S',"
                + "comprobanteasociado CHAR(20)DEFAULT '',"
                + "timbradoasociado  DECIMAL(8,0) DEFAULT 0)";

        PreparedStatement psregistro = conn.prepareStatement(sqlregistro);
        psregistro.executeUpdate(sqlregistro);

        String sqlGrabar = "INSERT INTO ventaresolucion"
                + "(codigoidentidicadorcomprador,numeroidentifacion,"
                + "razonsocial,codigocomprobante,"
                + "fechaemision,timbrado,numerocomprobante,"
                + "montogravado10,montogravado5,montoexento,montototal,"
                + "condicionventa) "
                + "SELECT clientes.res90 AS codigoidentidicadorcomprador,"
                + "CASE "
                + "WHEN clientes.res90=11 THEN SUBSTRING(clientes.ruc FROM 1 FOR CHAR_LENGTH(clientes.ruc) - 2) "
                + "WHEN clientes.res90=12 THEN clientes.cedula "
                + "WHEN clientes.res90=13 THEN clientes.cedula "
                + "WHEN clientes.res90=14 THEN clientes.cedula "
                + "WHEN clientes.res90=15 THEN THEN clientes.cedula "
                + "WHEN clientes.res90=16 THEN clientes.cedula "
                + "WHEN clientes.res90=17 THEN clientes.cedula "
                + "WHEN clientes.res90=18 THEN clientes.cedula "
                + "END AS numeroidentificador, "
                + "clientes.nombre,"
                + " CASE "
                + "WHEN cabecera_ventas.totalneto>0 THEN 109 "
                + "WHEN cabecera_ventas.totalneto<0 THEN 110 "
                + "END AS codigocomprobante, "
                + "DATE_FORMAT(cabecera_ventas.fecha, '%d/%m/%Y') AS fecha,"
                + "cabecera_ventas.nrotimbrado,"
                + "cabecera_ventas.formatofactura,"
                + "cabecera_ventas.gravadas10,"
                + "cabecera_ventas.gravadas5,"
                + "cabecera_ventas.exentas,"
                + "cabecera_ventas.totalneto, "
                + " CASE "
                + " WHEN cabecera_ventas.cuotas=0 THEN 1 "
                + " WHEN cabecera_ventas.cuotas=1 THEN 2 "
                + "END AS condicionventa "
                + "FROM cabecera_ventas "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=cabecera_ventas.cliente "
                + "LEFT JOIN comprobantes "
                + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cabecera_ventas.sucursal=" + suc
                + " AND cabecer_ventas.totalneto>0 AND comprobantes.libros=1 ORDER BY comprobante,factura ";

        PreparedStatement psplandatos = conn.prepareStatement(sqlGrabar);
        psplandatos.executeUpdate(sqlGrabar);

        //SE INSERTAN LAS NOTAS DE CREDITO DESDE COMPRAS
        String sqlGrabarNotas = "INSERT INTO ventaresolucion"
                + "(numeroidentifacion,razonsocial,codigocomprobante,fechaemision,timbrado,numerocomprobante,"
                + "montogravado10,montogravado5,montoexento,montototal,condicionventa,comprobanteasociado,timbradoasociado) "
                + " SELECT SUBSTRING(proveedores.ruc FROM 1 FOR CHAR_LENGTH(proveedores.ruc) - 2) AS r, "
                + "proveedores.nombre, "
                + "CASE "
                + " WHEN cabecera_compras.totalneto>0 THEN 109 "
                + " WHEN cabecera_compras.totalneto<0 THEN 110 "
                + " END AS codigocomprobante, "
                + " DATE_FORMAT(cabecera_compras.fecha, '%d/%m/%Y') AS fecha,"
                + " cabecera_compras.timbrado,"
                + " cabecera_compras.formatofactura,"
                + " ABS(cabecera_compras.gravadas10),"
                + " ABS(cabecera_compras.gravadas5),"
                + " ABS(cabecera_compras.exentas),"
                + " ABS(cabecera_compras.totalneto), "
                + " 1  AS condicionventa, "
                + " nota_credito.nrofactura,"
                + " nota_credito.timbradoasociado "
                + " FROM cabecera_compras "
                + " LEFT JOIN proveedores "
                + " ON proveedores.codigo=cabecera_compras.proveedor "
                + " LEFT JOIN comprobantes "
                + " ON comprobantes.codigo=cabecera_compras.comprobante "
                + " LEFT JOIN nota_credito "
                + " ON nota_credito.idnotacredito=cabecera_compras.creferencia "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cabecera_compras.totalneto<0 AND comprobantes.libros=1  and comprobantes.codold=1 "
                + " AND cabecera_compras.sucursal=" + suc
                + " ORDER BY codigocomprobante, fecha";

        PreparedStatement pspnotas = conn.prepareStatement(sqlGrabarNotas);
        pspnotas.executeUpdate(sqlGrabarNotas);

        String sqlventares = "SELECT *"
                + " FROM ventaresolucion ";

        PreparedStatement psventares = conn.prepareStatement(sqlventares);
        ResultSet res = psventares.executeQuery(sqlventares);

        try {

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Código Registro"); //INT
            rowhead.createCell((short) 1).setCellValue("Código Identificador Comprador"); //INT
            rowhead.createCell((short) 2).setCellValue("Número Identificación");//String
            rowhead.createCell((short) 3).setCellValue("Razón Social");//String
            rowhead.createCell((short) 4).setCellValue("Código Comprobante "); //int
            rowhead.createCell((short) 5).setCellValue("Fecha Emisión "); //String
            rowhead.createCell((short) 6).setCellValue("N° Timbrado "); //double
            rowhead.createCell((short) 7).setCellValue("N° Comprobante "); //String
            rowhead.createCell((short) 8).setCellValue("Monto Gravado 10 "); //double
            rowhead.createCell((short) 9).setCellValue("Monto Gravado 5 "); //double
            rowhead.createCell((short) 10).setCellValue("Monto Exento "); //double
            rowhead.createCell((short) 11).setCellValue("Monto Total "); //double
            rowhead.createCell((short) 12).setCellValue("Condición Venta"); //int
            rowhead.createCell((short) 13).setCellValue("Moneda Extranjera"); //String
            rowhead.createCell((short) 14).setCellValue("Imputa al IVA"); //String
            rowhead.createCell((short) 15).setCellValue("Imputa al IRE"); //String
            rowhead.createCell((short) 16).setCellValue("Imputa al IRP-RSP"); //String
            rowhead.createCell((short) 17).setCellValue("Comprobante Asociado"); //String
            rowhead.createCell((short) 18).setCellValue("timbradoasociado"); //double

            int index = 1;
            while (res.next()) {

                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(res.getInt(1));
                row.createCell((short) 1).setCellValue(res.getInt(2));
                row.createCell((short) 2).setCellValue(res.getString(3));
                row.createCell((short) 3).setCellValue(res.getString(4));
                row.createCell((short) 4).setCellValue(res.getInt(5));
                row.createCell((short) 5).setCellValue(res.getString(6));
                row.createCell((short) 6).setCellValue(res.getDouble(7));
                row.createCell((short) 7).setCellValue(res.getString(8));
                row.createCell((short) 8).setCellValue(res.getDouble(9));
                row.createCell((short) 9).setCellValue(res.getDouble(10));
                row.createCell((short) 10).setCellValue(res.getDouble(11));
                row.createCell((short) 11).setCellValue(res.getDouble(12));
                row.createCell((short) 12).setCellValue(res.getInt(13));
                row.createCell((short) 13).setCellValue(res.getString(14));
                row.createCell((short) 14).setCellValue(res.getString(15));
                row.createCell((short) 15).setCellValue(res.getString(16));
                row.createCell((short) 16).setCellValue(res.getString(17));
                row.createCell((short) 17).setCellValue(res.getString(18));
                row.createCell((short) 18).setCellValue(res.getDouble(19));
                index++;
            }
            //FileOutputStream fileOut = new FileOutputStream("c:\\Resolucion\\excelFile.xls");
            FileOutputStream fileOut = new FileOutputStream(nombrearchivo);
            wb.write(fileOut);
            fileOut.close();
            res.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }

    public ArrayList<venta> libroventares90ExcelConsolidado(Date fechaini, Date fechafin) throws SQLException, IOException {
        String nombrearchivo = "";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            nombrearchivo = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(nombrearchivo);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        // SE CREA LA TABLA TEMPORAL CON LA ESTRUCTURA DE LA SET
        String sqlregistro = "CREATE TEMPORARY TABLE ventaresolucion ("
                + "codigoregistro INT(1) DEFAULT 1,"
                + "codigoidentidicadorcomprador INT(2) DEFAULT 11,"
                + "numeroidentifacion CHAR(20),"
                + "razonsocial CHAR(250),"
                + "codigocomprobante int(3),"
                + "fechaemision CHAR(10),"
                + "timbrado DECIMAL(8,0) DEFAULT 0,"
                + "numerocomprobante CHAR(20),"
                + "montogravado10 DECIMAL (20,0) DEFAULT 0,"
                + "montogravado5 DECIMAL (20,0) DEFAULT 0,"
                + "montoexento DECIMAL (20,0) DEFAULT 0,"
                + "montototal DECIMAL (20,0) DEFAULT 0,"
                + "condicionventa INT(1) DEFAULT 1,"
                + "monedaextranjera CHAR(1) DEFAULT 'N',"
                + "imputaaliva CHAR(1) DEFAULT 'S',"
                + "imputaalire CHAR(1) DEFAULT 'N',"
                + "imputaalirprsp CHAR(1) DEFAULT 'N',"
                + "comprobanteasociado CHAR(20)DEFAULT '',"
                + "timbradoasociado  DECIMAL(8,0) DEFAULT 0)";

        //SE INSERTAN LAS VENTAS A LA TABLA TEMPORAL
        PreparedStatement psregistro = conn.prepareStatement(sqlregistro);
        psregistro.executeUpdate(sqlregistro);

        String sqlGrabar = "INSERT INTO ventaresolucion"
                + "(codigoidentidicadorcomprador,numeroidentifacion,"
                + "razonsocial,codigocomprobante,"
                + "fechaemision,timbrado,numerocomprobante,"
                + "montogravado10,montogravado5,montoexento,montototal,"
                + "condicionventa) "
                + "SELECT clientes.res90 AS codigoidentidicadorcomprador,"
                + "CASE "
                + "WHEN clientes.res90=11 THEN SUBSTRING(clientes.ruc FROM 1 FOR CHAR_LENGTH(clientes.ruc) - 2) "
                + "WHEN clientes.res90=12 THEN clientes.cedula "
                + "WHEN clientes.res90=13 THEN clientes.cedula "
                + "WHEN clientes.res90=14 THEN clientes.cedula "
                + "WHEN clientes.res90=15 THEN clientes.cedula "
                + "WHEN clientes.res90=16 THEN clientes.cedula "
                + "WHEN clientes.res90=17 THEN clientes.cedula "
                + "WHEN clientes.res90=18 THEN clientes.cedula "
                + "END AS numeroidentificador, "
                + "clientes.nombre,"
                + " CASE "
                + "WHEN cabecera_ventas.totalneto>0 THEN 109 "
                + "WHEN cabecera_ventas.totalneto<0 THEN 110 "
                + "END AS codigocomprobante, "
                + "DATE_FORMAT(cabecera_ventas.fecha, '%d/%m/%Y') AS fecha,"
                + "cabecera_ventas.nrotimbrado,"
                + "cabecera_ventas.formatofactura,"
                + "cabecera_ventas.gravadas10,"
                + "cabecera_ventas.gravadas5,"
                + "cabecera_ventas.exentas,"
                + "cabecera_ventas.totalneto, "
                + " CASE "
                + " WHEN cabecera_ventas.cuotas=0 THEN 1 "
                + " WHEN cabecera_ventas.cuotas=1 THEN 2 "
                + "END AS condicionventa "
                + "FROM cabecera_ventas "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=cabecera_ventas.cliente "
                + "LEFT JOIN comprobantes "
                + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cabecera_ventas.totalneto>0 "
                + " AND comprobantes.libros=1  and comprobantes.codold=1 ORDER BY comprobante,factura ";

        PreparedStatement psplandatos = conn.prepareStatement(sqlGrabar);
        psplandatos.executeUpdate(sqlGrabar);

        //SE INSERTAN LAS NOTAS DE CREDITO DESDE COMPRAS
        String sqlGrabarNotas = "INSERT INTO ventaresolucion"
                + "(numeroidentifacion,razonsocial,codigocomprobante,fechaemision,timbrado,numerocomprobante,"
                + "montogravado10,montogravado5,montoexento,montototal,condicionventa,comprobanteasociado,timbradoasociado) "
                + " SELECT SUBSTRING(proveedores.ruc FROM 1 FOR CHAR_LENGTH(proveedores.ruc) - 2) AS r, "
                + "proveedores.nombre, "
                + "CASE "
                + " WHEN cabecera_compras.totalneto>0 THEN 109 "
                + " WHEN cabecera_compras.totalneto<0 THEN 110 "
                + " END AS codigocomprobante, "
                + " DATE_FORMAT(cabecera_compras.fecha, '%d/%m/%Y') AS fecha,"
                + " cabecera_compras.timbrado,"
                + " cabecera_compras.formatofactura,"
                + " ABS(cabecera_compras.gravadas10),"
                + " ABS(cabecera_compras.gravadas5),"
                + " ABS(cabecera_compras.exentas),"
                + " ABS(cabecera_compras.totalneto), "
                + "1 AS condicionventa, "
                + " nota_credito.nrofactura,"
                + " nota_credito.timbradoasociado "
                + " FROM cabecera_compras "
                + " LEFT JOIN proveedores "
                + " ON proveedores.codigo=cabecera_compras.proveedor "
                + " LEFT JOIN comprobantes "
                + " ON comprobantes.codigo=cabecera_compras.comprobante "
                + " LEFT JOIN nota_credito "
                + " ON nota_credito.idnotacredito=cabecera_compras.creferencia "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cabecera_compras.totalneto<0 AND comprobantes.libros=1  and comprobantes.codold=1 "
                + " ORDER BY codigocomprobante, fecha";

        PreparedStatement pspnotas = conn.prepareStatement(sqlGrabarNotas);
        pspnotas.executeUpdate(sqlGrabarNotas);

        String sqlventares = "SELECT *"
                + " FROM ventaresolucion ";
        PreparedStatement psventares = conn.prepareStatement(sqlventares);
        ResultSet res = psventares.executeQuery(sqlventares);

        try {

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Código Registro"); //INT
            rowhead.createCell((short) 1).setCellValue("Código Identificador Comprador"); //INT
            rowhead.createCell((short) 2).setCellValue("Número Identificación");//String
            rowhead.createCell((short) 3).setCellValue("Razón Social");//String
            rowhead.createCell((short) 4).setCellValue("Código Comprobante "); //int
            rowhead.createCell((short) 5).setCellValue("Fecha Emisión "); //String
            rowhead.createCell((short) 6).setCellValue("N° Timbrado "); //double
            rowhead.createCell((short) 7).setCellValue("N° Comprobante "); //String
            rowhead.createCell((short) 8).setCellValue("Monto Gravado 10 "); //double
            rowhead.createCell((short) 9).setCellValue("Monto Gravado 5 "); //double
            rowhead.createCell((short) 10).setCellValue("Monto Exento "); //double
            rowhead.createCell((short) 11).setCellValue("Monto Total "); //double
            rowhead.createCell((short) 12).setCellValue("Condición Venta"); //int
            rowhead.createCell((short) 13).setCellValue("Moneda Extranjera"); //String
            rowhead.createCell((short) 14).setCellValue("Imputa al IVA"); //String
            rowhead.createCell((short) 15).setCellValue("Imputa al IRE"); //String
            rowhead.createCell((short) 16).setCellValue("Imputa al IRP-RSP"); //String
            rowhead.createCell((short) 17).setCellValue("Comprobante Asociado"); //String
            rowhead.createCell((short) 18).setCellValue("timbradoasociado"); //double

            int index = 1;
            while (res.next()) {

                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(res.getInt(1));
                row.createCell((short) 1).setCellValue(res.getInt(2));
                row.createCell((short) 2).setCellValue(res.getString(3));
                row.createCell((short) 3).setCellValue(res.getString(4));
                row.createCell((short) 4).setCellValue(res.getInt(5));
                row.createCell((short) 5).setCellValue(res.getString(6));
                row.createCell((short) 6).setCellValue(res.getDouble(7));
                row.createCell((short) 7).setCellValue(res.getString(8));
                row.createCell((short) 8).setCellValue(res.getDouble(9));
                row.createCell((short) 9).setCellValue(res.getDouble(10));
                row.createCell((short) 10).setCellValue(res.getDouble(11));
                row.createCell((short) 11).setCellValue(res.getDouble(12));
                row.createCell((short) 12).setCellValue(res.getInt(13));
                row.createCell((short) 13).setCellValue(res.getString(14));
                row.createCell((short) 14).setCellValue(res.getString(15));
                row.createCell((short) 15).setCellValue(res.getString(16));
                row.createCell((short) 16).setCellValue(res.getString(17));
                row.createCell((short) 17).setCellValue(res.getString(18));
                row.createCell((short) 18).setCellValue(res.getDouble(19));
                index++;
            }
            //FileOutputStream fileOut = new FileOutputStream("c:\\Resolucion\\excelFile.xls");
            FileOutputStream fileOut = new FileOutputStream(nombrearchivo);
            wb.write(fileOut);
            fileOut.close();
            res.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }

    public ArrayList<venta> ResumenVentaSucursalExcel(int suc, int moneda, Date fechaini, Date fechafin) throws SQLException, IOException {
        String nombrearchivo = "";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            nombrearchivo = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(nombrearchivo);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        String sqlventares = "SELECT  cabecera_ventas.factura,"
                + "cabecera_ventas.fecha,"
                + "clientes.codigo AS cuenta, clientes.nombre AS nombrecliente,"
                + "SUM(IF(forma=1,netocobrado,0000000000)) AS forma1,"
                + "SUM(IF(forma=2,netocobrado,0000000000)) AS forma2,"
                + "SUM(IF(forma=3,netocobrado,0000000000)) AS forma3,"
                + "SUM(IF(forma=4,netocobrado,0000000000)) AS forma4,"
                + "SUM(IF(forma=5,netocobrado,0000000000)) AS forma5,"
                + "SUM(IF(forma=6,netocobrado,0000000000)) AS forma6,"
                + "SUM(IF(forma=7,netocobrado,0000000000)) AS forma7,"
                + "SUM(netocobrado) AS totalcobro "
                + " FROM cabecera_ventas "
                + " INNER JOIN sucursales "
                + " ON sucursales.codigo=cabecera_ventas.sucursal "
                + " INNER JOIN clientes "
                + " ON clientes.codigo=cabecera_ventas.cliente "
                + " INNER JOIN monedas "
                + " ON monedas.codigo=cabecera_ventas.moneda "
                + " INNER JOIN detalle_forma_cobro "
                + " ON detalle_forma_cobro.idmovimiento=cabecera_ventas.creferencia "
                + " INNER JOIN  formaspago "
                + " ON formaspago.codigo=detalle_forma_cobro.forma "
                + " WHERE cabecera_ventas.fecha BETWEEN'" + fechaini + "'"
                + " AND '" + fechafin + "'"
                + " AND cabecera_ventas.sucursal=" + suc
                + " AND cabecera_ventas.moneda= " + moneda
                + " AND detalle_forma_cobro.netocobrado<>0 "
                + " GROUP BY cabecera_ventas.creferencia "
                + " UNION "
                + "SELECT cobranzas.numero,cobranzas.fecha,"
                + "clientes.codigo AS cuenta, clientes.nombre AS nombrecliente,"
                + "SUM(IF(forma=1,netocobrado,0000000000)) AS forma1,"
                + "SUM(IF(forma=2,netocobrado,0000000000)) AS forma2,"
                + "SUM(IF(forma=3,netocobrado,0000000000)) AS forma3,"
                + "SUM(IF(forma=4,netocobrado,0000000000)) AS forma4,"
                + "SUM(IF(forma=5,netocobrado,0000000000)) AS forma5,"
                + "SUM(IF(forma=6,netocobrado,0000000000)) AS forma6,"
                + "SUM(IF(forma=7,netocobrado,0000000000)) AS forma7,"
                + "SUM(netocobrado) AS totalcobro "
                + " FROM cobranzas "
                + " INNER JOIN sucursales "
                + " ON sucursales.codigo=cobranzas.sucursal "
                + " INNER JOIN clientes "
                + " ON clientes.codigo=cobranzas.cliente "
                + " INNER JOIN monedas "
                + " ON monedas.codigo=cobranzas.moneda "
                + " INNER JOIN detalle_forma_cobro "
                + " ON detalle_forma_cobro.idmovimiento=cobranzas.idpagos "
                + " INNER JOIN  formaspago "
                + " ON formaspago.codigo=detalle_forma_cobro.forma "
                + " WHERE cobranzas.fecha BETWEEN'" + fechaini + "'"
                + " AND '" + fechafin + "'"
                + " AND cobranzas.sucursal=" + suc
                + " AND cobranzas.moneda= " + moneda
                + " AND detalle_forma_cobro.netocobrado<>0"
                + " GROUP BY cobranzas.idpagos"
                + " ORDER BY fecha";

        PreparedStatement psventares = conn.prepareStatement(sqlventares);
        ResultSet res = psventares.executeQuery(sqlventares);

        try {

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Documento"); //INT
            rowhead.createCell((short) 1).setCellValue("Fecha"); //INT
            rowhead.createCell((short) 2).setCellValue("Cuenta");//String
            rowhead.createCell((short) 3).setCellValue("Nombre Socio");//String
            rowhead.createCell((short) 4).setCellValue("Efectivo "); //int
            rowhead.createCell((short) 5).setCellValue("Cheque "); //String
            rowhead.createCell((short) 6).setCellValue("Tarjeta Crédito"); //double
            rowhead.createCell((short) 7).setCellValue("Retenciones "); //String
            rowhead.createCell((short) 8).setCellValue("Tarjeta Débito "); //double
            rowhead.createCell((short) 9).setCellValue("Transferencia "); //double
            rowhead.createCell((short) 10).setCellValue("Giros "); //double
            rowhead.createCell((short) 11).setCellValue("Total "); //double

            int index = 1;
            while (res.next()) {

                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(res.getDouble(1));
                row.createCell((short) 1).setCellValue(res.getString(2));
                row.createCell((short) 2).setCellValue(res.getInt(3));
                row.createCell((short) 3).setCellValue(res.getString(4));
                row.createCell((short) 4).setCellValue(res.getDouble(5));
                row.createCell((short) 5).setCellValue(res.getDouble(6));
                row.createCell((short) 6).setCellValue(res.getDouble(7));
                row.createCell((short) 7).setCellValue(res.getDouble(8));
                row.createCell((short) 8).setCellValue(res.getDouble(9));
                row.createCell((short) 9).setCellValue(res.getDouble(10));
                row.createCell((short) 10).setCellValue(res.getDouble(11));
                row.createCell((short) 11).setCellValue(res.getDouble(12));
                index++;
            }
            //FileOutputStream fileOut = new FileOutputStream("c:\\Resolucion\\excelFile.xls");
            FileOutputStream fileOut = new FileOutputStream(nombrearchivo);
            wb.write(fileOut);
            fileOut.close();
            res.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }

    public ArrayList<venta> MostrarxFechaFerremax(Date fechaini, Date fechafin, int tipo) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT creferencia,cabecera_ventas.factura,cabecera_ventas.fecha,cabecera_ventas.factura,cabecera_ventas.vencimiento,";
            cSql = cSql + "cabecera_ventas.cliente,cabecera_ventas.sucursal,cabecera_ventas.moneda,cabecera_ventas.giraduria,comprobante,cotizacion,";
            cSql = cSql + "cabecera_ventas.vendedor,caja,supago,sucambio,totalbruto,totaldescuento,exentas,gravadas10,gravadas5,totalneto,cuotas,";
            cSql = cSql + "anuladopor,fechaanulado,registro,preventa,cierre,financiado,observacion,";
            cSql = cSql + "enviactacte,comision_vendedor,remisionnro,cabecera_ventas.marcavehiculo,cabecera_ventas.nombreconductor,direccionconductor,";
            cSql = cSql + "cabecera_ventas.chapa,cabecera_ventas.cedula,fechainitraslado,fechafintraslado,llegada,nombregarante,cabecera_ventas.direcciongarante,";
            cSql = cSql + "cabecera_ventas.cedulagarante,cabecera_ventas.turno,idusuario,ordencompra,contrato,iddireccion,cabecera_ventas.nrotimbrado,cabecera_ventas.vencimientotimbrado,";
            cSql = cSql + "centro,comanda,parallevar,cabecera_ventas.horagrabado,cabecera_ventas.formatofactura,";
            cSql = cSql + "clientes.nombre AS nombrecliente,";
            cSql = cSql + "sucursales.nombre AS nombresucursal,";
            cSql = cSql + "monedas.nombre  AS nombremoneda,";
            cSql = cSql + "monedas.etiqueta  AS etiqueta,";
            cSql = cSql + "comprobantes.nombre AS nombrecomprobante,";
            cSql = cSql + "vendedores.nombre AS nombrevendedor,";
            cSql = cSql + "cajas.nombre AS nombrecaja,";
            cSql = cSql + "clientes.localidad,clientes.direccion,clientes.ruc,";
            cSql = cSql + "localidades.nombre AS nombrelocalidad,";
            cSql = cSql + "giradurias.nombre AS nombregiraduria,cabecera_ventas.ticketold ";
            cSql = cSql + " FROM cabecera_ventas ";
            cSql = cSql + " LEFT JOIN clientes ";
            cSql = cSql + " ON clientes.codigo=cabecera_ventas.cliente ";
            cSql = cSql + " LEFT JOIN localidades ";
            cSql = cSql + " ON localidades.codigo=clientes.localidad ";
            cSql = cSql + " LEFT JOIN sucursales ";
            cSql = cSql + " ON sucursales.codigo=cabecera_ventas.sucursal ";
            cSql = cSql + " LEFT JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cabecera_ventas.moneda ";
            cSql = cSql + " LEFT JOIN giradurias ";
            cSql = cSql + " ON giradurias.codigo=cabecera_ventas.giraduria ";
            cSql = cSql + " LEFT JOIN cajas ";
            cSql = cSql + " ON cajas.codigo=cabecera_ventas.caja ";
            cSql = cSql + " LEFT JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=cabecera_ventas.comprobante ";
            cSql = cSql + " LEFT JOIN vendedores ";
            cSql = cSql + " ON vendedores.codigo=cabecera_ventas.vendedor ";
            cSql = cSql + "WHERE cabecera_ventas.fecha between ? AND ? ";
            cSql = cSql + " AND IF(?>0,cabecera_ventas.totalneto>0,cabecera_ventas.totalneto<0) ";
            cSql = cSql + " ORDER BY cabecera_ventas.horagrabado ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, tipo);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    moneda moneda = new moneda();
                    caja caja = new caja();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();

                    localidad localidad = new localidad();

                    venta vta = new venta();

                    vta.setGiraduria(giraduria);
                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setMoneda(moneda);
                    vta.setCaja(caja);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);

                    vta.setCreferencia(rs.getString("creferencia"));
                    vta.setFactura(rs.getDouble("factura"));
                    vta.setFecha(rs.getDate("fecha"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));

                    vta.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    vta.getGiraduria().setNombre(rs.getString("nombregiraduria"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));

                    vta.getMoneda().setCodigo(rs.getInt("moneda"));
                    vta.getMoneda().setNombre(rs.getString("nombremoneda"));
                    vta.getMoneda().setEtiqueta(rs.getString("etiqueta"));

                    vta.getCaja().setCodigo(rs.getInt("caja"));
                    vta.getCaja().setNombre(rs.getString("nombrecaja"));

                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setFormatofactura(rs.getString("formatofactura"));
                    vta.setTicketold(rs.getString("ticketold"));
                    vta.setCuotas(rs.getInt("cuotas"));
                    vta.setNrotimbrado(rs.getInt("nrotimbrado"));
                    vta.setVencimientotimbrado(rs.getDate("vencimientotimbrado"));
                    vta.setCentro(rs.getInt("centro"));
                    vta.setCotizacion(rs.getBigDecimal("cotizacion"));
                    vta.setExentas(rs.getBigDecimal("exentas"));
                    vta.setGravadas5(rs.getBigDecimal("gravadas5"));
                    vta.setGravadas10(rs.getBigDecimal("gravadas10"));
                    vta.setTotalneto(rs.getBigDecimal("totalneto"));
                    vta.setComanda(rs.getInt("comanda"));
                    vta.setTurno(rs.getInt("turno"));
                    vta.setContrato(rs.getInt("contrato"));
                    vta.setOrdencompra(rs.getInt("ordencompra"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setIdusuario(rs.getInt("idusuario"));
                    vta.setComision_vendedor(rs.getBigDecimal("comision_vendedor"));
                    vta.setHoragrabado(rs.getString("horagrabado"));
                    lista.add(vta);
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

    public ArrayList<venta> libroventares55ExcelConsolidado(Date fechaini, Date fechafin) throws SQLException, IOException {
        String nombrearchivo = "";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            nombrearchivo = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(nombrearchivo);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        // SE CREA LA TABLA TEMPORAL CON LA ESTRUCTURA DE LA SET
        String sqlregistro = "CREATE TEMPORARY TABLE ventaresolucion ("
                + "tipoidentificacion CHAR(15) DEFAULT 'RUC',"
                + "numeroidentifacion CHAR(20),"
                + "razonsocial CHAR(250),"
                + "organismo  CHAR(250) DEFAULT '',"
                + "tipocomprobante CHAR(15) DEFAULT 'FACTURA',"
                + "timbrado DECIMAL(8,0) DEFAULT 0,"
                + "numerocomprobante CHAR(20),"
                + "condicion CHAR(7),"
                + "fechaemision CHAR(10),"
                + "montototal DECIMAL (20,0) DEFAULT 0,"
                + "montoexento DECIMAL (20,0) DEFAULT 0,"
                + "montogravado5 DECIMAL (20,0) DEFAULT 0,"
                + "montoiva5 DECIMAL (20,0) DEFAULT 0,"
                + "montogravado10 DECIMAL (20,0) DEFAULT 0,"
                + "montoiva10 DECIMAL (20,0) DEFAULT 0,"
                + "irpgravado CHAR(1) DEFAULT 'N',"
                + "irpnogravado CHAR(1) DEFAULT 'N',"
                + "irpsimple CHAR(1) DEFAULT 'N')";

        //SE INSERTAN LAS VENTAS A LA TABLA TEMPORAL
        PreparedStatement psregistro = conn.prepareStatement(sqlregistro);
        psregistro.executeUpdate(sqlregistro);

        String sqlGrabar = "INSERT INTO ventaresolucion"
                + "(numeroidentifacion,razonsocial,timbrado,numerocomprobante,condicion,fechaemision,"
                + "montototal,montoexento,montogravado5,montoiva5,montogravado10,montoiva10) "
                + "SELECT clientes.ruc, "
                + "clientes.nombre,"
                + "cabecera_ventas.nrotimbrado,"
                + "cabecera_ventas.formatofactura,"
                + " CASE "
                + " WHEN cabecera_ventas.cuotas=0 THEN 'CONTADO' "
                + " WHEN cabecera_ventas.cuotas>1 THEN 'CRÉDITO' "
                + "END AS condicion, "
                + "DATE_FORMAT(cabecera_ventas.fecha, '%d/%m/%Y') AS fecha,"
                + "cabecera_ventas.totalneto, "
                + "cabecera_ventas.exentas, "
                + "cabecera_ventas.gravadas5, "
                + "ROUND(cabecera_ventas.gravadas5/21) AS montoiva5,"
                + "cabecera_ventas.gravadas10, "
                + "ROUND(cabecera_ventas.gravadas10/11) AS montoiva10 "
                + "FROM cabecera_ventas "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=cabecera_ventas.cliente "
                + "LEFT JOIN comprobantes "
                + "ON comprobantes.codigo=cabecera_ventas.comprobante "
                + " WHERE fecha BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cabecera_ventas.totalneto>0 "
                + " AND comprobantes.libros=1 ORDER BY comprobante,factura ";

        PreparedStatement psplandatos = conn.prepareStatement(sqlGrabar);
        psplandatos.executeUpdate(sqlGrabar);

        String sqlventares = "SELECT *"
                + " FROM ventaresolucion ";
        PreparedStatement psventares = conn.prepareStatement(sqlventares);
        ResultSet res = psventares.executeQuery(sqlventares);

        try {

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Tipo Identificación"); //String
            rowhead.createCell((short) 1).setCellValue("RUC"); //String
            rowhead.createCell((short) 2).setCellValue("Razón Social"); //String
            rowhead.createCell((short) 3).setCellValue("Organismo"); //String
            rowhead.createCell((short) 4).setCellValue("Tipo Comprobante");//String
            rowhead.createCell((short) 5).setCellValue("Timbrado");//int
            rowhead.createCell((short) 6).setCellValue("N° Comprobante "); //String
            rowhead.createCell((short) 7).setCellValue("Condición "); //String
            rowhead.createCell((short) 8).setCellValue("Fecha Emisión"); //String
            rowhead.createCell((short) 9).setCellValue("Importe Total "); //double
            rowhead.createCell((short) 10).setCellValue("Importe Exento "); //double
            rowhead.createCell((short) 11).setCellValue("Gravadas 5% "); //double
            rowhead.createCell((short) 12).setCellValue("IVA 5% "); //double
            rowhead.createCell((short) 13).setCellValue("Gravadas 10%"); //double
            rowhead.createCell((short) 14).setCellValue("IVA 10%"); //double
            rowhead.createCell((short) 15).setCellValue("IRP Gravado"); //String
            rowhead.createCell((short) 16).setCellValue("IRP No Gravado"); //String
            rowhead.createCell((short) 17).setCellValue("IRP Simple"); //String

            int index = 1;
            while (res.next()) {

                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(res.getString(1));
                row.createCell((short) 1).setCellValue(res.getString(2));
                row.createCell((short) 2).setCellValue(res.getString(3));
                row.createCell((short) 3).setCellValue(res.getString(4));
                row.createCell((short) 4).setCellValue(res.getString(5));
                row.createCell((short) 5).setCellValue(res.getInt(6));
                row.createCell((short) 6).setCellValue(res.getString(7));
                row.createCell((short) 7).setCellValue(res.getString(8));
                row.createCell((short) 8).setCellValue(res.getString(9));
                row.createCell((short) 9).setCellValue(res.getDouble(10));
                row.createCell((short) 10).setCellValue(res.getDouble(11));
                row.createCell((short) 11).setCellValue(res.getDouble(12));
                row.createCell((short) 12).setCellValue(res.getDouble(13));
                row.createCell((short) 13).setCellValue(res.getDouble(14));
                row.createCell((short) 14).setCellValue(res.getDouble(15));
                row.createCell((short) 15).setCellValue(res.getString(16));
                row.createCell((short) 16).setCellValue(res.getString(17));
                row.createCell((short) 17).setCellValue(res.getString(18));
                index++;
            }
            //FileOutputStream fileOut = new FileOutputStream("c:\\Resolucion\\excelFile.xls");
            FileOutputStream fileOut = new FileOutputStream(nombrearchivo);
            wb.write(fileOut);
            fileOut.close();
            res.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }

    public venta AgregarFacturaVentaCasaBolsa(venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_ventas (creferencia,fecha,factura,"
                + "vencimiento,cliente,sucursal,moneda,giraduria,"
                + "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,"
                + "gravadas5,totalneto,cuotas,financiado,observacion,"
                + "supago,idusuario,preventa,vencimientotimbrado,sucambio,"
                + "nrotimbrado,turno,formatofactura,prestamo,idprestamo) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getFactura());
        ps.setDate(4, v.getVencimiento());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setInt(8, v.getGiraduria().getCodigo());
        ps.setInt(9, v.getComprobante().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getVendedor().getCodigo());
        ps.setInt(12, v.getCaja().getCodigo());
        ps.setBigDecimal(13, v.getExentas());
        ps.setBigDecimal(14, v.getGravadas10());
        ps.setBigDecimal(15, v.getGravadas5());
        ps.setBigDecimal(16, v.getTotalneto());
        ps.setInt(17, v.getCuotas());
        ps.setBigDecimal(18, v.getFinanciado());
        ps.setString(19, v.getObservacion());
        ps.setBigDecimal(20, v.getSupago());
        ps.setInt(21, v.getIdusuario());
        ps.setInt(22, v.getPreventa());
        ps.setDate(23, v.getVencimientotimbrado());
        ps.setBigDecimal(24, v.getSucambio());
        ps.setInt(25, v.getNrotimbrado());
        ps.setInt(26, v.getTurno());
        ps.setString(27, v.getFormatofactura());
        ps.setInt(28, v.getPrestamo());
        ps.setString(29, v.getIdprestamo());
        ps.executeUpdate();
        guardarItemFactura(v.getCreferencia(), detalle, con);
        System.out.println("CABECERA VENTAS");
        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public venta ActualizarVentaCasaBolsa(venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  cabecera_ventas SET fecha=?,factura=?,"
                + "vencimiento=?,cliente=?,sucursal=?,"
                + "moneda=?,giraduria=?,comprobante=?,"
                + "cotizacion=?,vendedor=?,caja=?,"
                + "exentas=?,gravadas10=?,gravadas5=?,"
                + "totalneto=?,cuotas=?,financiado=?,"
                + "observacion=?,supago=?,idusuario=?,"
                + "preventa=?,vencimientotimbrado=?,nrotimbrado=?,"
                + "turno=?,formatofactura=?,sucambio=?,prestamo=?,idprestamo=? WHERE creferencia= '" + v.getCreferencia() + "'");
        ps.setDate(1, v.getFecha());
        ps.setDouble(2, v.getFactura());
        ps.setDate(3, v.getVencimiento());
        ps.setInt(4, v.getCliente().getCodigo());
        ps.setInt(5, v.getSucursal().getCodigo());
        ps.setInt(6, v.getMoneda().getCodigo());
        ps.setInt(7, v.getGiraduria().getCodigo());
        ps.setInt(8, v.getComprobante().getCodigo());
        ps.setBigDecimal(9, v.getCotizacion());
        ps.setInt(10, v.getVendedor().getCodigo());
        ps.setInt(11, v.getCaja().getCodigo());
        ps.setBigDecimal(12, v.getExentas());
        ps.setBigDecimal(13, v.getGravadas10());
        ps.setBigDecimal(14, v.getGravadas5());
        ps.setBigDecimal(15, v.getTotalneto());
        ps.setInt(16, v.getCuotas());
        ps.setBigDecimal(17, v.getFinanciado());
        ps.setString(18, v.getObservacion());
        ps.setBigDecimal(19, v.getSupago());
        ps.setInt(20, v.getIdusuario());
        ps.setInt(21, v.getPreventa());
        ps.setDate(22, v.getVencimientotimbrado());
        ps.setInt(23, v.getNrotimbrado());
        ps.setInt(24, v.getTurno());
        ps.setString(25, v.getFormatofactura());
        ps.setBigDecimal(26, v.getSucambio());
        ps.setInt(27, v.getPrestamo());
        ps.setString(28, v.getIdprestamo());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemFactura(v.getCreferencia(), detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return v;
    }

    public venta AgregarFacturaVentaFacultad(venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_ventas (creferencia,fecha,factura,"
                + "vencimiento,cliente,sucursal,moneda,giraduria,"
                + "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,"
                + "gravadas5,totalneto,cuotas,financiado,observacion,"
                + "supago,idusuario,preventa,vencimientotimbrado,sucambio,"
                + "nrotimbrado,turno,formatofactura) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getFactura());
        ps.setDate(4, v.getVencimiento());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setInt(8, v.getGiraduria().getCodigo());
        ps.setInt(9, v.getComprobante().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getVendedor().getCodigo());
        ps.setInt(12, v.getCaja().getCodigo());
        ps.setBigDecimal(13, v.getExentas());
        ps.setBigDecimal(14, v.getGravadas10());
        ps.setBigDecimal(15, v.getGravadas5());
        ps.setBigDecimal(16, v.getTotalneto());
        ps.setInt(17, v.getCuotas());
        ps.setBigDecimal(18, v.getFinanciado());
        ps.setString(19, v.getObservacion());
        ps.setBigDecimal(20, v.getSupago());
        ps.setInt(21, v.getIdusuario());
        ps.setInt(22, v.getPreventa());
        ps.setDate(23, v.getVencimientotimbrado());
        ps.setBigDecimal(24, v.getSucambio());
        ps.setInt(25, v.getNrotimbrado());
        ps.setInt(26, v.getTurno());
        ps.setString(27, v.getFormatofactura());
        ps.executeUpdate();
        guardarItemFacturaFacultad(v.getCreferencia(), detalle, con);
        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public boolean guardarItemFacturaFacultad(String id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement psdetalle = null;

        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_ventas WHERE dreferencia=?");
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
                    String sql = "INSERT INTO detalle_ventas("
                            + "dreferencia,"
                            + "codprod,"
                            + "prcosto,"
                            + "cantidad,"
                            + "precio,"
                            + "monto,"
                            + "impiva,"
                            + "porcentaje,"
                            + "suc,"
                            + "cuota"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("codprod").getAsString());
                        ps.setBigDecimal(3, obj.get("prcosto").getAsBigDecimal());
                        ps.setBigDecimal(4, obj.get("cantidad").getAsBigDecimal());
                        ps.setBigDecimal(5, obj.get("precio").getAsBigDecimal());
                        ps.setBigDecimal(6, obj.get("monto").getAsBigDecimal());
                        ps.setBigDecimal(7, obj.get("impiva").getAsBigDecimal());
                        ps.setBigDecimal(8, obj.get("porcentaje").getAsBigDecimal());
                        ps.setString(9, obj.get("suc").getAsString());
                        ps.setString(10, obj.get("cuota").getAsString());
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
        conn.close();
        return guardado;
    }

    public venta AgregarFacturaVentaVemay(String token, venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_ventas"
                + "(creferencia,fecha,factura,"
                + "vencimiento,cliente,sucursal,"
                + "moneda,giraduria,comprobante,"
                + "cotizacion,vendedor,caja,"
                + "exentas,gravadas10,gravadas5,"
                + "totalneto,cuotas,financiado,"
                + "observacion,supago,idusuario,"
                + "preventa,vencimientotimbrado,sucambio,"
                + "nrotimbrado,turno,formatofactura,"
                + "centro,remisionnro) "
                + "VALUES (?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getFactura());
        ps.setDate(4, v.getVencimiento());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setInt(8, v.getGiraduria().getCodigo());
        ps.setInt(9, v.getComprobante().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getVendedor().getCodigo());
        ps.setInt(12, v.getCaja().getCodigo());
        ps.setBigDecimal(13, v.getExentas());
        ps.setBigDecimal(14, v.getGravadas10());
        ps.setBigDecimal(15, v.getGravadas5());
        ps.setBigDecimal(16, v.getTotalneto());
        ps.setInt(17, v.getCuotas());
        ps.setBigDecimal(18, v.getFinanciado());
        ps.setString(19, v.getObservacion());
        ps.setBigDecimal(20, v.getSupago());
        ps.setInt(21, v.getIdusuario());
        ps.setInt(22, v.getPreventa());
        ps.setDate(23, v.getVencimientotimbrado());
        ps.setBigDecimal(24, v.getSucambio());
        ps.setInt(25, v.getNrotimbrado());
        ps.setInt(26, v.getTurno());
        ps.setString(27, v.getFormatofactura());
        ps.setInt(28, v.getCentro());
        ps.setInt(29, v.getRemisionnro());
        if (Config.cToken == token) {
            ps.executeUpdate();
            guardarItemFactura(v.getCreferencia(), detalle, con);
        } else {
            System.out.println("USUARIO NO AUTORIZADO");
        }
        System.out.println("CABECERA VENTAS");
        st.close();
        ps.close();
        conn.close();
        return v;

    }

    public ArrayList<venta> UtilidadesxVentas(Date fechaini, Date fechafin, int nRubro, int nSucursal) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_ventas.sucursal,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "detalle_ventas.codprod,"
                    + "productos.nombre AS nombreproducto,"
                    + "productos.rubro,"
                    + "rubros.nombre AS nombrerubro,"
                    + "SUM(detalle_ventas.cantidad) AS unidades,"
                    + "detalle_ventas.prcosto,"
                    + "detalle_ventas.precio,"
                    + "SUM(detalle_ventas.cantidad*detalle_ventas.prcosto) AS costomercaderia,"
                    + "SUM(detalle_ventas.cantidad*detalle_ventas.precio) AS ventasnetas,"
                    + "SUM((detalle_ventas.cantidad*detalle_ventas.precio)-"
                    + "(detalle_ventas.cantidad*detalle_ventas.prcosto)) AS utilidades "
                    + "FROM cabecera_ventas "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_ventas.sucursal "
                    + "LEFT JOIN detalle_ventas "
                    + "ON detalle_ventas.dreferencia=cabecera_ventas.creferencia "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=detalle_ventas.codprod "
                    + "LEFT JOIN rubros "
                    + "ON rubros.codigo=productos.rubro "
                    + "WHERE cabecera_ventas.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "AND cabecera_ventas.sucursal=" + nSucursal
                    + " AND IF(" + nRubro + "<>0,productos.rubro=" + nRubro + ",TRUE) "
                    + " AND !ISNULL(codprod) "
                    + " GROUP BY productos.codigo "
                    + " ORDER BY productos.rubro,productos.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    rubro rubro = new rubro();
                    sucursal sucursal = new sucursal();
                    producto producto = new producto();
                    venta ven = new venta();

                    ven.setSucursal(sucursal);
                    ven.setCodprodventa(rs.getString("codprod"));
                    ven.setNombreproductoventa(rs.getString("nombreproducto"));
                    ven.setCantidadventa(rs.getBigDecimal("unidades"));
                    ven.setPrecioventa(rs.getBigDecimal("precio"));
                    ven.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ven.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ven.setCosto(rs.getDouble("prcosto"));
                    ven.setCostomercaderia(rs.getDouble("costomercaderia"));
                    ven.setVentasnetas(rs.getDouble("ventasnetas"));
                    ven.setUtilidades(rs.getDouble("utilidades"));
                    lista.add(ven);
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

    public boolean guardarItemFacturaComentario(String id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement psdetalle = null;

        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_ventas WHERE dreferencia=?");
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
                    String sql = "INSERT INTO detalle_ventas("
                            + "dreferencia,"
                            + "codprod,"
                            + "prcosto,"
                            + "cantidad,"
                            + "precio,"
                            + "monto,"
                            + "impiva,"
                            + "porcentaje,"
                            + "comentario,"
                            + "suc"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("codprod").getAsString());
                        ps.setBigDecimal(3, obj.get("prcosto").getAsBigDecimal());
                        ps.setBigDecimal(4, obj.get("cantidad").getAsBigDecimal());
                        ps.setBigDecimal(5, obj.get("precio").getAsBigDecimal());
                        ps.setBigDecimal(6, obj.get("monto").getAsBigDecimal());
                        ps.setBigDecimal(7, obj.get("impiva").getAsBigDecimal());
                        ps.setBigDecimal(8, obj.get("porcentaje").getAsBigDecimal());
                        ps.setString(9, obj.get("comentario").getAsString());
                        ps.setString(10, obj.get("suc").getAsString());
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
        conn.close();
        return guardado;
    }

    public venta AgregarFacturaVentaComentario(String token, venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_ventas (creferencia,fecha,factura,"
                + "vencimiento,cliente,sucursal,moneda,giraduria,"
                + "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,"
                + "gravadas5,totalneto,cuotas,financiado,observacion,"
                + "supago,idusuario,preventa,vencimientotimbrado,sucambio,"
                + "nrotimbrado,turno,formatofactura,centro) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getFactura());
        ps.setDate(4, v.getVencimiento());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setInt(6, v.getSucursal().getCodigo());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setInt(8, v.getGiraduria().getCodigo());
        ps.setInt(9, v.getComprobante().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getVendedor().getCodigo());
        ps.setInt(12, v.getCaja().getCodigo());
        ps.setBigDecimal(13, v.getExentas());
        ps.setBigDecimal(14, v.getGravadas10());
        ps.setBigDecimal(15, v.getGravadas5());
        ps.setBigDecimal(16, v.getTotalneto());
        ps.setInt(17, v.getCuotas());
        ps.setBigDecimal(18, v.getFinanciado());
        ps.setString(19, v.getObservacion());
        ps.setBigDecimal(20, v.getSupago());
        ps.setInt(21, v.getIdusuario());
        ps.setInt(22, v.getPreventa());
        ps.setDate(23, v.getVencimientotimbrado());
        ps.setBigDecimal(24, v.getSucambio());
        ps.setInt(25, v.getNrotimbrado());
        ps.setInt(26, v.getTurno());
        ps.setString(27, v.getFormatofactura());
        ps.setInt(28, v.getCentro());

        if (Config.cToken == token) {
            ps.executeUpdate();
            guardarItemFacturaComentario(v.getCreferencia(), detalle, con);
        } else {
            System.out.println("USUARIO NO AUTORIZADO");
        }

        st.close();
        ps.close();
        conn.close();
        return v;
    }


    public venta ActualizarVentaComentario(venta v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  cabecera_ventas SET fecha=?,factura=?,"
                + "vencimiento=?,cliente=?,sucursal=?,"
                + "moneda=?,giraduria=?,comprobante=?,"
                + "cotizacion=?,vendedor=?,caja=?,"
                + "exentas=?,gravadas10=?,gravadas5=?,"
                + "totalneto=?,cuotas=?,financiado=?,"
                + "observacion=?,supago=?,idusuario=?,"
                + "preventa=?,vencimientotimbrado=?,nrotimbrado=?,"
                + "turno=?,formatofactura=?,sucambio=? WHERE creferencia= '" + v.getCreferencia() + "'");
        ps.setDate(1, v.getFecha());
        ps.setDouble(2, v.getFactura());
        ps.setDate(3, v.getVencimiento());
        ps.setInt(4, v.getCliente().getCodigo());
        ps.setInt(5, v.getSucursal().getCodigo());
        ps.setInt(6, v.getMoneda().getCodigo());
        ps.setInt(7, v.getGiraduria().getCodigo());
        ps.setInt(8, v.getComprobante().getCodigo());
        ps.setBigDecimal(9, v.getCotizacion());
        ps.setInt(10, v.getVendedor().getCodigo());
        ps.setInt(11, v.getCaja().getCodigo());
        ps.setBigDecimal(12, v.getExentas());
        ps.setBigDecimal(13, v.getGravadas10());
        ps.setBigDecimal(14, v.getGravadas5());
        ps.setBigDecimal(15, v.getTotalneto());
        ps.setInt(16, v.getCuotas());
        ps.setBigDecimal(17, v.getFinanciado());
        ps.setString(18, v.getObservacion());
        ps.setBigDecimal(19, v.getSupago());
        ps.setInt(20, v.getIdusuario());
        ps.setInt(21, v.getPreventa());
        ps.setDate(22, v.getVencimientotimbrado());
        ps.setInt(23, v.getNrotimbrado());
        ps.setInt(24, v.getTurno());
        ps.setString(25, v.getFormatofactura());
        ps.setBigDecimal(26, v.getSucambio());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemFacturaComentario(v.getCreferencia(), detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return v;
    }


}

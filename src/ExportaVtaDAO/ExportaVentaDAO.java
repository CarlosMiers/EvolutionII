/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import ExportaVtaDAO.*;
import Conexion.ConexionEspejo;
import Modelo.venta;
import Modelo.caja;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.giraduria;
import Modelo.localidad;
import Modelo.moneda;
import Modelo.producto;
import Modelo.rubro;
import Modelo.sucursal;
import Modelo.vendedor;
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
 * @author SERVIDOR
 */
public class ExportaVentaDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public ArrayList<venta> MostrarxFecha(Date fechaini, Date fechafin, Integer nsuc) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }

        Connection conne = stEspejo.getConnection();
        try {

            String cSql = "SELECT creferencia,cabecera_ventas.factura,cabecera_ventas.fecha,cabecera_ventas.factura,cabecera_ventas.vencimiento,";
            cSql = cSql + "cabecera_ventas.cliente,cabecera_ventas.sucursal,cabecera_ventas.moneda,cabecera_ventas.giraduria,comprobante,cotizacion,";
            cSql = cSql + "cabecera_ventas.vendedor,caja,supago,sucambio,totalbruto,totaldescuento,exentas,gravadas10,gravadas5,totalneto,cuotas,";
            cSql = cSql + "anuladopor,fechaanulado,registro,preventa,cierre,financiado,observacion,";
            cSql = cSql + "enviactacte,comision_vendedor,remisionnro,cabecera_ventas.marcavehiculo,cabecera_ventas.nombreconductor,direccionconductor,";
            cSql = cSql + "cabecera_ventas.chapa,cabecera_ventas.cedula,fechainitraslado,fechafintraslado,llegada,nombregarante,cabecera_ventas.direcciongarante,";
            cSql = cSql + "cabecera_ventas.cedulagarante,cabecera_ventas.turno,idusuario,ordencompra,contrato,iddireccion,cabecera_ventas.nrotimbrado,cabecera_ventas.vencimientotimbrado,";
            cSql = cSql + "centro,comanda,parallevar,cabecera_ventas.horagrabado,";
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
            cSql = cSql + "WHERE cabecera_ventas.fecha between ? AND ? ";
            cSql = cSql + " ORDER BY cabecera_ventas.factura ";

            try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(cSql)) {
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
                    vta.setCuotas(rs.getInt("cuotas"));
                    vta.setNrotimbrado(rs.getInt("nrotimbrado"));
                    vta.setVencimientotimbrado(rs.getDate("vencimientotimbrado"));
                    vta.setCentro(rs.getInt("centro"));
                    vta.setCotizacion(rs.getBigDecimal("cotizacion"));
                    vta.setFinanciado(rs.getBigDecimal("financiado"));
                    vta.setSupago(rs.getBigDecimal("supago"));
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
            System.out.println("--> cabecera de venta origen " + ex.getLocalizedMessage());
        }
        stEspejo.close();
        conne.close();
        return lista;
    }

    public venta AgregarFacturaRemota(venta v, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("INSERT INTO cabecera_ventas (creferencia,fecha,factura,vencimiento,cliente,sucursal,moneda,giraduria,comprobante,cotizacion,vendedor,caja,exentas,gravadas10,gravadas5,totalneto,cuotas,financiado,observacion,supago,idusuario,preventa,vencimientotimbrado,nrotimbrado,turno,formatofactura) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
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

        stEspejo.close();
        ps.close();
        conne.close();
        return v;
    }

    public venta buscarVtaServer(String id, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        venta vta = new venta();

        try {

            String sql = "SELECT creferencia "
                    + " FROM cabecera_ventas "
                    + " WHERE cabecera_ventas.creferencia= ?"
                    + " ORDER BY cabecera_ventas.creferencia ";

            try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
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
        stEspejo.close();
        conne.close();
        return vta;
    }

    public boolean borrarVenta(Date fechaini, Date fechafin, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }

        Connection conne = stEspejo.getConnection();

        PreparedStatement ps = null;
        ps = stEspejo.getConnection().prepareStatement("DELETE FROM cabecera_ventas WHERE fecha>=? AND fecha<=?");
        ps.setDate(1, fechaini);
        ps.setDate(2, fechafin);
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        conne.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

}

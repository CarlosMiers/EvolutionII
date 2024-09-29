/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOBACKUP;

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
public class ImportaVentaDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public ArrayList<venta> MostrarxFecha(Date fechaini, Date fechafin, Integer nsuc) throws SQLException {
        ArrayList<venta> lista = new ArrayList<venta>();
        conEsp = new ConexionEspejo();
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
                    vta.setExentas(rs.getBigDecimal("exentas"));
                    vta.setGravadas5(rs.getBigDecimal("gravadas5"));
                    vta.setGravadas10(rs.getBigDecimal("gravadas10"));
                    vta.setTotalneto(rs.getBigDecimal("totalneto"));
                    vta.setComanda(rs.getInt("comanda"));
                    vta.setTurno(rs.getInt("turno"));
                    vta.setContrato(rs.getInt("contrato"));
                    vta.setOrdencompra(rs.getInt("ordencompra"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setSupago(rs.getBigDecimal("supago"));
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

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOBACKUP;

import Conexion.ConexionEspejo;
import Modelo.cabecera_compra;
import Modelo.comprobante;
import Modelo.moneda;
import Modelo.proveedor;
import Modelo.sucursal;
import java.sql.Connection;
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
public class ImportaCompraDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public ArrayList<cabecera_compra> MostrarxFecha(Date fechaini, Date fechafin, Integer nsuc) throws SQLException {
        ArrayList<cabecera_compra> lista = new ArrayList<cabecera_compra>();
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        try {

            String cSql = "SELECT cabecera_compras.creferencia,cabecera_compras.nrofactura,cabecera_compras.sucursal,cabecera_compras.fecha,cabecera_compras.proveedor,";
            cSql = cSql + "cabecera_compras.exentas,cabecera_compras.gravadas10,cabecera_compras.gravadas5,cabecera_compras.totalneto,cabecera_compras.moneda,";
            cSql = cSql + "cabecera_compras.timbrado,cabecera_compras.vencetimbrado,cabecera_compras.cotizacion,cabecera_compras.observacion,cabecera_compras.primer_vence,";
            cSql = cSql + "cabecera_compras.cierre,cabecera_compras.comprobante,cabecera_compras.pagos,cabecera_compras.financiado,cabecera_compras.enviarcta,cabecera_compras.generarasiento,";
            cSql = cSql + "cabecera_compras.cuotas,cabecera_compras.usuarioalta,cabecera_compras.fechaalta,cabecera_compras.usuarioupdate,cabecera_compras.fechaupdate,";
            cSql = cSql + "cabecera_compras.tipo_gasto,cabecera_compras.retencion,cabecera_compras.importado,cabecera_compras.ordencompra,cabecera_compras.asiento,";
            cSql = cSql + "proveedores.nombre AS nombreproveedor,";
            cSql = cSql + "comprobantes.nombre AS nombrecomprobante,";
            cSql = cSql + "sucursales.nombre AS nombresucursal,";
            cSql = cSql + "monedas.nombre  AS nombremoneda,";
            cSql = cSql + "monedas.etiqueta  AS etiqueta ";
            cSql = cSql + " FROM cabecera_compras ";
            cSql = cSql + " LEFT JOIN proveedores ";
            cSql = cSql + " ON proveedores.codigo=cabecera_compras.proveedor ";
            cSql = cSql + " LEFT JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=cabecera_compras.comprobante ";
            cSql = cSql + " LEFT JOIN sucursales  ";
            cSql = cSql + " ON sucursales.codigo=cabecera_compras.sucursal ";
            cSql = cSql + " LEFT JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cabecera_compras.moneda";
            cSql = cSql + " WHERE cabecera_compras.fecha BETWEEN ?  AND ? ";
            cSql = cSql + " ORDER BY cabecera_compras.fecha ";

            try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();
                    comprobante comprobante = new comprobante();

                    cabecera_compra com = new cabecera_compra();

                    com.setSucursal(sucursal);
                    com.setProveedor(proveedor);
                    com.setMoneda(moneda);
                    com.setComprobante(comprobante);

                    com.setCreferencia(rs.getString("creferencia"));
                    com.setNrofactura(rs.getDouble("nrofactura"));
                    com.getSucursal().setCodigo(rs.getInt("sucursal"));
                    com.setFecha(rs.getDate("fecha"));
                    com.getProveedor().setCodigo(rs.getInt("proveedor"));
                    //   com.getProveedor().setRuc(rs.getString("ruc"));
                    com.setExentas(rs.getBigDecimal("exentas"));
                    com.setGravadas10(rs.getBigDecimal("gravadas10"));
                    com.setGravadas5(rs.getBigDecimal("gravadas5"));
                    com.setTotalneto(rs.getBigDecimal("totalneto"));
                    com.getMoneda().setCodigo(rs.getInt("moneda"));
                    com.getMoneda().setEtiqueta(rs.getString("etiqueta"));
                    com.setTimbrado(rs.getInt("timbrado"));
                    com.setVencetimbrado(rs.getDate("vencetimbrado"));
                    com.setCotizacion(rs.getBigDecimal("cotizacion"));
                    com.setObservacion(rs.getString("observacion"));
                    com.setPrimer_vence(rs.getDate("primer_vence"));
                    com.setCierre(rs.getInt("cierre"));
                    com.getComprobante().setCodigo(rs.getInt("comprobante"));
                    com.setPagos(rs.getBigDecimal("pagos"));
                    com.setFinanciado(rs.getBigDecimal("financiado"));
                    com.setEnviarcta(rs.getInt("enviarcta"));
                    com.setGenerarasiento(rs.getInt("generarasiento"));
                    com.setAsiento(rs.getInt("asiento"));
                    com.setCuotas(rs.getInt("cuotas"));
                    com.setUsuarioalta(rs.getInt("usuarioalta"));
                    com.setFechaalta(rs.getDate("fechaalta"));
                    com.setUsuarioupdate(rs.getInt("usuarioupdate"));
                    com.setFechaupdate(rs.getDate("fechaupdate"));
                    com.setTipo_gasto(rs.getInt("tipo_gasto"));
                    com.setRetencion(rs.getBigDecimal("retencion"));
                    com.setImportado(rs.getInt("importado"));
                    com.setOrdencompra(rs.getInt("ordencompra"));
                    lista.add(com);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->cabecera compras " + ex.getLocalizedMessage());
        }
        stEspejo.close();
        conne.close();
        return lista;
    }

}

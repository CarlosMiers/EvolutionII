/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.venta;
import Conexion.Conexion;
import Modelo.caja;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.detalle_forma_cobro;
import Modelo.giraduria;
import Modelo.lista_precios_lotes;
import Modelo.localidad;
import Modelo.lote;
import Modelo.moneda;
import Modelo.producto;
import Modelo.rubro;
import Modelo.sucursal;
import Modelo.vendedor;
import Modelo.ventas_lotes;
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
 * @author Pedro Meza
 */
public class venta_lotesDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<ventas_lotes> MostrarxFecha(Date fechaini, Date fechafin/*, int tipo*/) throws SQLException {
        ArrayList<ventas_lotes> lista = new ArrayList<>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql =   "SELECT\n" +
                            "   venta_lotes.creferencia,\n" +
                            "   venta_lotes.factura,\n" +
                            "   venta_lotes.idventa, \n" +
                            "   clientes.nombre as cliente,\n" +
                            "   clientes.ruc as ruc,\n" +
                            "   clientes.direccion as direccion,\n" +
                            "   venta_lotes.fecha,\n" +
                            "   comprobantes.nombre as comprobante,\n" +
                            "   lotes.nombre as lote,\n" +
                            "   lotes.manzana as manzana,\n" +
                            "   lotes.superficie as superficie,\n" +
                            "   lotes.nrolote as nrolote,\n" +
                            "   lista_precio_lotes.descripcion as listaprecio,\n" +
                            "   lista_precio_lotes.cuotas as cuotasprecio,\n" +
                            "   lista_precio_lotes.plazo as plazo,\n" +
                            "   lista_precio_lotes.precio as precio,\n" +
                            "   venta_lotes.cuotas,\n" +
                            "   venta_lotes.plazo,\n" +
                            "   venta_lotes.primeravence,\n" +
                            "   venta_lotes.cliente as idcliente,\n" +
                            "   venta_lotes.comprobante as idcomprobante,\n" +
                            "   venta_lotes.lote as idlote,\n" +
                            "   venta_lotes.listaprecio as idprecio,\n" +
                            "   sucursales.nombre as sucursal,\n" +
                            "   venta_lotes.sucursal as idsucursal,\n" +
                            "   monedas.nombre as moneda,\n" +
                            "   venta_lotes.moneda as idmoneda,\n" +
                            "   venta_lotes.cotizacion as cotizacion\n" +
                            "FROM venta_lotes\n" +
                            "INNER JOIN clientes ON clientes.codigo=venta_lotes.cliente\n" +
                            "INNER JOIN comprobantes ON comprobantes.codigo=venta_lotes.comprobante\n" +
                            "INNER JOIN lotes ON lotes.codigo=venta_lotes.lote\n" +
                            "INNER JOIN lista_precio_lotes ON lista_precio_lotes.idlista=venta_lotes.listaprecio\n" +
                            "INNER JOIN sucursales ON sucursales.codigo=venta_lotes.sucursal\n" +
                            "INNER JOIN monedas ON monedas.codigo=venta_lotes.moneda\n" +
                            "WHERE venta_lotes.fecha between ? and ?\n" +
                            "ORDER BY venta_lotes.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                //ps.setInt(3, tipo);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    lote lote = new lote();
                    lista_precios_lotes listaprecio = new lista_precios_lotes();
                    
                    ventas_lotes vta = new ventas_lotes();
                    
                    vta.setCreferencia(rs.getString("creferencia"));
                    vta.setFactura(rs.getString("factura"));
                    vta.setIdventa(rs.getInt("idventa"));
                    vta.setFecha(rs.getDate("fecha"));

                    cliente.setCodigo(rs.getInt("idcliente"));
                    cliente.setNombre(rs.getString("cliente"));
                    vta.setCliente(cliente);
                    
                    comprobante.setCodigo(rs.getInt("idcomprobante"));
                    comprobante.setNombre(rs.getString("comprobante"));
                    vta.setComprobante(comprobante);
                    
                    lote.setCodigo(rs.getInt("idlote"));
                    lote.setNombre(rs.getString("lote"));
                    lote.setManzanas(rs.getInt("manzana"));
                    lote.setSuperficie(rs.getString("superficie"));
                    lote.setNrolote(rs.getInt("nrolote"));
                    vta.setLote(lote);
                    
                    listaprecio.setIdlista(rs.getInt("idprecio"));
                    listaprecio.setDescripcion(rs.getString("listaprecio"));
                    listaprecio.setCuotas(rs.getBigDecimal("cuotasprecio"));
                    listaprecio.setPlazo(rs.getInt("plazo"));
                    listaprecio.setPrecio(rs.getBigDecimal("precio"));
                    vta.setListaprecio(listaprecio);
                    
                    vta.setCuotas(rs.getBigDecimal("cuotas"));
                    vta.setPlazo(rs.getInt("plazo"));
                    vta.setPrimeravence(rs.getDate("primeravence"));
                    
                    sucursal sucursal = new sucursal();
                    sucursal.setCodigo(rs.getInt("idsucursal"));
                    sucursal.setNombre(rs.getString("sucursal"));
                    vta.setSucursal(sucursal);
                    
                    moneda moneda = new moneda();
                    moneda.setCodigo(rs.getInt("idmoneda"));
                    moneda.setNombre(rs.getString("moneda"));
                    vta.setMoneda(moneda);
                    
                    vta.setCotizacion(rs.getBigDecimal("cotizacion"));
                    
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

    public ventas_lotes buscarId(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        ventas_lotes vta = new ventas_lotes();

        try {

            String sql ="SELECT\n" +
                        "   venta_lotes.creferencia,\n" +
                        "   venta_lotes.factura,\n" +
                        "   venta_lotes.idventa, \n" +
                        "   clientes.nombre as cliente,\n" +
                        "   clientes.ruc as ruc,\n" +
                        "   clientes.direccion as direccion,\n" +
                        "   venta_lotes.fecha,\n" +
                        "   comprobantes.nombre as comprobante,\n" +
                        "   lotes.nombre as lote,\n" +
                        "   lotes.manzana as manzana,\n" +
                        "   lotes.superficie as superficie,\n" +
                        "   lotes.nrolote as nrolote,\n" +
                        "   lista_precio_lotes.descripcion as listaprecio,\n" +
                        "   lista_precio_lotes.cuotas as cuotasprecio,\n" +
                        "   lista_precio_lotes.plazo as plazo,\n" +
                        "   lista_precio_lotes.precio as precio,\n" +
                        "   venta_lotes.cuotas,\n" +
                        "   venta_lotes.plazo,\n" +
                        "   venta_lotes.primeravence,\n" +
                        "   venta_lotes.cliente as idcliente,\n" +
                        "   venta_lotes.comprobante as idcomprobante,\n" +
                        "   venta_lotes.lote as idlote,\n" +
                        "   venta_lotes.listaprecio as idprecio,\n" +
                        "   sucursales.nombre as sucursal,\n" +
                        "   venta_lotes.sucursal as idsucursal,\n" +
                        "   monedas.nombre as moneda,\n" +
                        "   venta_lotes.moneda as idmoneda,\n" +
                        "   venta_lotes.vendedor as idvendedor,\n" +
                        "   venta_lotes.cotizacion as cotizacion\n" +
                        "FROM venta_lotes\n" +
                        "INNER JOIN clientes ON clientes.codigo=venta_lotes.cliente\n" +
                        "INNER JOIN comprobantes ON comprobantes.codigo=venta_lotes.comprobante\n" +
                        "INNER JOIN lotes ON lotes.codigo=venta_lotes.lote\n" +
                        "INNER JOIN lista_precio_lotes ON lista_precio_lotes.idlista=venta_lotes.listaprecio\n" +
                        "INNER JOIN sucursales ON sucursales.codigo=venta_lotes.sucursal\n" +
                        "INNER JOIN monedas ON monedas.codigo=venta_lotes.moneda\n" +
                        "WHERE venta_lotes.idventa =? \n" +
                        "ORDER BY venta_lotes.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    lote lote = new lote();
                    lista_precios_lotes listaprecio = new lista_precios_lotes();
                    
                    vta.setCreferencia(rs.getString("creferencia"));
                    vta.setFactura(rs.getString("factura"));
                    vta.setIdventa(rs.getInt("idventa"));
                    vta.setFecha(rs.getDate("fecha"));

                    cliente.setCodigo(rs.getInt("idcliente"));
                    cliente.setNombre(rs.getString("cliente"));
                    cliente.setDireccion(rs.getString("direccion"));
                    vta.setCliente(cliente);
                    
                    comprobante.setCodigo(rs.getInt("idcomprobante"));
                    comprobante.setNombre(rs.getString("comprobante"));
                    vta.setComprobante(comprobante);
                    
                    lote.setCodigo(rs.getInt("idlote"));
                    lote.setNombre(rs.getString("lote"));
                    lote.setManzanas(rs.getInt("manzana"));
                    lote.setSuperficie(rs.getString("superficie"));
                    lote.setNrolote(rs.getInt("nrolote"));
                    vta.setLote(lote);
                    
                    listaprecio.setIdlista(rs.getInt("idprecio"));
                    listaprecio.setDescripcion(rs.getString("listaprecio"));
                    listaprecio.setCuotas(rs.getBigDecimal("cuotasprecio"));
                    listaprecio.setPlazo(rs.getInt("plazo"));
                    listaprecio.setPrecio(rs.getBigDecimal("precio"));
                    vta.setListaprecio(listaprecio);
                    
                    vta.setCuotas(rs.getBigDecimal("cuotas"));
                    vta.setPlazo(rs.getInt("plazo"));
                    vta.setPrimeravence(rs.getDate("primeravence"));
                    
                    sucursal sucursal = new sucursal();
                    sucursal.setCodigo(rs.getInt("idsucursal"));
                    sucursal.setNombre(rs.getString("sucursal"));
                    vta.setSucursal(sucursal);
                    
                    vendedor vendedor = new vendedor();
                    vendedor.setCodigo(rs.getInt("idvendedor"));
                    vta.setVendedor(vendedor);
                    
                    moneda moneda = new moneda();
                    moneda.setCodigo(rs.getInt("idmoneda"));
                    moneda.setNombre(rs.getString("moneda"));
                    vta.setMoneda(moneda);
                    
                    vta.setCotizacion(rs.getBigDecimal("cotizacion"));
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

    public ventas_lotes AgregarVenta(ventas_lotes v) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement(
                "INSERT INTO venta_lotes (\n"+
                "   creferencia,"+
                "   factura,"+
                "   fecha,"+
                "   cliente,"+
                "   comprobante,"+
                "   lote,"+
                "   listaprecio,"+
                "   cuotas,"+
                "   primeravence,\n"+
                "   sucursal,\n"+
                "   moneda,\n"+
                "   cotizacion,\n"+
                "   plazo,\n"+
                "   vendedor\n"+
                ")VALUES (\n"+
                "   ?,?,?,?,?,?,?,?,?,?,?,?,?,?"+
                "\n)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setString(2, v.getFactura());
        ps.setDate(3, v.getFecha());
        ps.setInt(4, v.getCliente().getCodigo());
        ps.setInt(5, v.getComprobante().getCodigo());
        ps.setInt(6, v.getLote().getCodigo());
        ps.setInt(7, v.getListaprecio().getIdlista());
        ps.setBigDecimal(8, v.getCuotas());
        ps.setDate(9, v.getPrimeravence());
        ps.setInt(10, v.getSucursal().getCodigo());
        ps.setInt(11, v.getMoneda().getCodigo());
        ps.setBigDecimal(12, v.getCotizacion());
        ps.setInt(13, v.getPlazo());
        ps.setInt(14, v.getVendedor().getCodigo());
        //System.out.println("\n"+ps.toString()+"\n\n");
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public ventas_lotes ActualizarVenta(ventas_lotes v) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement(
            "UPDATE  venta_lotes \n"+
            "SET "
                    + "fecha=?," //1
                    + "cliente=?," //2
                    + "comprobante=?," //3
                    + "lote=?," //4
                    + "listaprecio=?," //5
                    + "cuotas=?," //6
                    + "primeravence=?," //7
                    + "sucursal=?,"//8
                    + "moneda=?,"//9
                    + "cotizacion=?,"//10
                    + "plazo=?,"//11
                    + "vendedor=?, "
                    + "factura=? "+
            "WHERE creferencia= '" + v.getCreferencia() + "'");
        
        ps.setDate(1, v.getFecha());
        ps.setInt(2, v.getCliente().getCodigo());
        ps.setInt(3, v.getComprobante().getCodigo());
        ps.setInt(4, v.getLote().getCodigo());
        ps.setInt(5, v.getListaprecio().getIdlista());
        //System.out.println(v.getCuotas()+"");
        ps.setBigDecimal(6, v.getCuotas());
        ps.setDate(7, v.getPrimeravence());
        ps.setInt(8, v.getSucursal().getCodigo());
        ps.setInt(9, v.getMoneda().getCodigo());
        ps.setBigDecimal(10, v.getCotizacion());
        ps.setInt(11, v.getPlazo());
        ps.setInt(12, v.getVendedor().getCodigo());
        ps.setString(13, v.getFactura());
        //System.out.println(ps.toString());
        ps.executeUpdate();
        
        st.close();
        ps.close();
        cnn.close();
        return v;
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

    public boolean borrarVenta(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM venta_lotes WHERE idventa=?");
        ps.setString(1, id);
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
      
}

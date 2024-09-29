/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.banco;
import Modelo.comprobante;

import Modelo.edificio;
import Modelo.gasto_unidad;
import Modelo.inmueble;
import Modelo.moneda;
import Modelo.plan;
import Modelo.proveedor;
import Modelo.rubro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class gasto_unidadDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<gasto_unidad> MostrarDetalleGastos(Double id) throws SQLException {
        ArrayList<gasto_unidad> lista = new ArrayList<gasto_unidad>();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql= "SELECT \n" +
                        "    gastos_unidad.dnumero, -- \n" +
                        "    gastos_unidad.nrofactura, -- 0\n" +
                        "    gastos_unidad.fechafactura, -- 1\n" +
                        "    gastos_unidad.vencimiento, -- 2\n" +
                        "    gastos_unidad.tipo, -- 3\n" +
                        "    gastos_unidad.comprobante, -- 4\n" +
                        "    comprobantes.nombre as nombrecomprobante, -- 5\n" +
                        "    gastos_unidad.rubro, -- 6\n" +
                        "    rubros.nombre as nombrerubro, -- 7\n" +
                        "    gastos_unidad.moneda, -- 8\n" +
                        "    monedas.nombre as nombremoneda, -- 9\n" +
                        "    gastos_unidad.moneda as cotizacion, -- 10\n" +
                        "    monedas.venta as venta, -- 11\n" +
                        "    gastos_unidad.unidad, -- 12\n" +
                        "    edificios.ctactral AS ctactral, -- 13\n" +
                        "    gastos_unidad.timbrado,  -- 14\n" +
                        "    gastos_unidad.vencimientotimbrado,  -- 15\n" +
                        "    gastos_unidad.proveedor, -- 16\n" +
                        "    proveedores.nombre as nombreproveedor, -- 17\n" +
                        "    gastos_unidad.exentas, -- 18\n" +
                        "    gastos_unidad.gravadas10, -- 19\n" +
                        "    gastos_unidad.iva10, -- 20\n" +
                        "    gastos_unidad.gravadas5, -- 21 \n" +
                        "    gastos_unidad.iva5, -- 22\n" +
                        "    gastos_unidad.monto as totalneto, -- 23\n" +
                        "    gastos_unidad.concepto as observacion, -- 24\n" +
                        "    gastos_unidad.idcta, -- ??\n" +
                        "    plan.nombre AS nombreplan,\n" +
                        "    inmuebles.nomedif,\n" +
                        "    inmuebles.idinmueble\n" +
                        "FROM gastos_unidad \n" +
                        "    LEFT JOIN comprobantes ON gastos_unidad.comprobante=comprobantes.codigo\n" +
                        "    LEFT JOIN rubros on gastos_unidad.rubro=rubros.codigo\n" +
                        "    LEFT JOIN monedas on gastos_unidad.moneda=monedas.codigo\n" +
                        "    LEFT JOIN plan  ON plan.codigo=gastos_unidad.idcta \n" +
                        "    LEFT JOIN edificios ON edificios.idunidad=gastos_unidad.unidad  \n" +
                        "    LEFT JOIN inmuebles ON inmuebles.idinmueble=edificios.inmueble  \n" +
                        "    LEFT JOIN proveedores on gastos_unidad.proveedor = proveedores.codigo\n" +
                        "WHERE gastos_unidad.dnumero=? ORDER BY gastos_unidad.dnumero";
            //System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    
                    gasto_unidad gu = new gasto_unidad();
                    comprobante comprobante = new comprobante();
                    rubro rubro = new rubro();
                    moneda moneda = new moneda();
                    edificio unidad = new edificio();
                    proveedor proveedor = new proveedor();
                    plan pl = new plan();
                    inmueble inmueble = new inmueble();
                    
                    gu.setDnumero(rs.getInt("dnumero"));
                    gu.setNrofactura(rs.getString("nrofactura"));
                    gu.setFechafactura(rs.getDate("fechafactura"));
                    gu.setVencimiento(rs.getDate("vencimiento"));
                    gu.setTipo(rs.getInt("tipo"));
                    
                    comprobante.setCodigo(rs.getInt("comprobante"));
                    comprobante.setNombre(rs.getString("nombrecomprobante"));
                    gu.setComprobante(comprobante);
                    
                    rubro.setCodigo(rs.getInt("rubro"));
                    rubro.setNombre(rs.getString("nombrerubro"));
                    gu.setRubro(rubro);
                    
                    moneda.setCodigo(rs.getInt("moneda"));
                    moneda.setNombre(rs.getString("nombremoneda"));
                    moneda.setVenta(rs.getBigDecimal("venta"));
                    gu.setMoneda(moneda);
                    
                    unidad.setIdunidad(rs.getInt("unidad"));
                    unidad.setCtactral(rs.getString("ctactral"));
                    gu.setUnidad(unidad);
                    
                    gu.setTimbrado(rs.getString("timbrado"));
                    gu.setVencimientotimbrado(rs.getDate("vencimientotimbrado"));
                    
                    proveedor.setCodigo(rs.getInt("proveedor"));
                    proveedor.setNombre(rs.getString("nombreproveedor"));
                    gu.setProveedor(proveedor);
                    
                    gu.setExentas(rs.getDouble("exentas"));
                    gu.setGravadas10(rs.getDouble("gravadas10"));
                    gu.setIva10(rs.getDouble("iva10"));
                    gu.setGravadas5(rs.getDouble("gravadas5"));
                    gu.setIva5(rs.getDouble("iva5"));
                    gu.setTotalneto(rs.getDouble("totalneto"));
                    gu.setObservacion(rs.getString("observacion"));
                    
                    pl.setCodigo(rs.getString("idcta"));
                    pl.setNombre(rs.getString("nombreplan"));
                    gu.setIdcta(pl);
                    
                    inmueble.setIdinmueble(rs.getInt("idinmueble"));
                    inmueble.setNomedif(rs.getString("nomedif"));
                    gu.setInmueble(inmueble);
                    
                    lista.add(gu);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public boolean borrarDetalleGasto(Double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM gastos_unidad WHERE dnumero=?");
        ps.setDouble(1, id);
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

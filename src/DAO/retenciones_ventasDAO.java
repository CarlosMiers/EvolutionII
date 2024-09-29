/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.caja;
import Modelo.cliente;
import Modelo.moneda;
import Modelo.retenciones_ventas;
import Modelo.sucursal;
import Modelo.venta;
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
public class retenciones_ventasDAO {

    Conexion con = null;
    Statement st = null;

    public retenciones_ventas AgregarRetencionesVenta(retenciones_ventas v) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_retenciones_ventas (creferencia,nrofactura,"
                + "sucursal,fecha,cliente,"
                + "totalneto,moneda,"
                + "importe_sin_iva,importe_iva,importe_gravado_total,porcentaje_retencion,"
                + "valor_retencion,"
                + "cotizacion,observacion,"
                + "enviarcta,generarasiento,nroretencion)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, v.getCreferencia());
        ps.setString(2, v.getNrofactura());
        ps.setInt(3, v.getSucursal().getCodigo());
        ps.setDate(4, v.getFecha());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setDouble(6, v.getTotalneto());
        ps.setInt(7, v.getMoneda().getCodigo());
        ps.setDouble(8, v.getImporte_sin_iva());
        ps.setDouble(9, v.getImporte_iva());
        ps.setDouble(10, v.getImporte_gravado_total());
        ps.setDouble(11, v.getPorcentaje_retencion());
        ps.setDouble(12, v.getValor_retencion());
        ps.setDouble(13, v.getCotizacion());
        ps.setString(14, v.getObservacion());
        ps.setInt(15, v.getEnviarcta());
        ps.setInt(16, v.getGenerarasiento());
        ps.setString(17, v.getNroretencion());
        ps.executeUpdate();
        st.close();
        ps.close();
        return v;
    }

    public retenciones_ventas ActualizarRetencionVenta(retenciones_ventas v) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  nrofactura=?,"
                + "sucursal=?,fecha=?,cliente=?,"
                + "totalneto=?,moneda=?,"
                + "importe_sin_iva=?,importe_iva=?,importe_gravado_total=?,porcentaje_retencion=?,"
                + "valor_retencion=?,"
                + "cotizacion=?,observacion=?,"
                + "enviarcta=?,generarasiento=? WHERE creferencia= '" + v.getCreferencia() + "'");
        ps.setString(1, v.getNrofactura());
        ps.setInt(2, v.getSucursal().getCodigo());
        ps.setDate(3, v.getFecha());
        ps.setInt(4, v.getCliente().getCodigo());
        ps.setDouble(5, v.getTotalneto());
        ps.setInt(6, v.getMoneda().getCodigo());
        ps.setDouble(7, v.getImporte_sin_iva());
        ps.setDouble(8, v.getImporte_iva());
        ps.setDouble(9, v.getImporte_gravado_total());
        ps.setDouble(10, v.getPorcentaje_retencion());
        ps.setDouble(11, v.getValor_retencion());
        ps.setDouble(12, v.getCotizacion());
        ps.setString(13, v.getObservacion());
        ps.setInt(14, v.getEnviarcta());
        ps.setInt(15, v.getGenerarasiento());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        cnn.close();
        return v;
    }

    public boolean borrarCuenta(String referencia, int comprobante) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuenta_clientes WHERE creferencia=? and comprobante= ?");
        ps.setString(1, referencia);
        ps.setInt(2, comprobante);
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

    public boolean borrarRetencion(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM cabecera_retenciones_ventas WHERE creferencia=?");
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

    public ArrayList<retenciones_ventas> LibroRetencionVenta(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<retenciones_ventas> lista = new ArrayList<retenciones_ventas>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_retenciones_ventas.nroretencion,nrofactura,fecha,sucursal,cliente,valor_retencion as totalneto,clientes.ruc,"
                    + "clientes.nombre AS nombrecliente,sucursales.nombre AS nombresucursal "
                    + " FROM cabecera_retenciones_ventas "
                    + " INNER JOIN sucursales "
                    + " ON sucursales.codigo=cabecera_retenciones_ventas.sucursal "
                    + " INNER JOIN clientes "
                    + " ON clientes.codigo=cabecera_retenciones_ventas.cliente "
                    + "WHERE  fecha BETWEEN ? AND ? "
                    + " ORDER BY fecha";


            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    cliente cliente = new cliente();
                    retenciones_ventas vta = new retenciones_ventas();
                    vta.setSucursal(sucursal);
                    vta.setCliente(cliente);
                    vta.setNroretencion(rs.getString("nroretencion"));
                    vta.setNrofactura(rs.getString("nrofactura"));
                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.setFecha(rs.getDate("Fecha"));
                    vta.getCliente().setCodigo(rs.getInt("Cliente"));
                    vta.setTotalneto(rs.getDouble("totalneto"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
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

}

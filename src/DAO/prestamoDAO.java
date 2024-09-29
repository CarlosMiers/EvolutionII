/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.giraduria;
import Modelo.moneda;
import Modelo.prestamo;
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
import javax.swing.JOptionPane;

/**
 *
 * @author SERVIDOR
 */
public class prestamoDAO {

    Conexion con = null;
    Statement st = null;

    public prestamo buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        prestamo pre = new prestamo();

        try {

            String sql = "SELECT prestamos.idprestamos,prestamos.numero,prestamos.fecha,prestamos.moneda,prestamos.sucursal,acreedor,porcentaje_acreedor,comision_acreedor,"
                    + "solicitud,socio,monto_entregar,porcentaje_deudor,comision_deudor,prestamos.plazo,deducciones,importe,"
                    + "monto_cuota,primer_vence,prestamos.tipo,prestamos.tasa,prestamos.interes,prestamos.ivainteres,cierre,asiento,totalamortizacion,"
                    + "totalprestamo,tipopago,prestamos.garante,gastos_escritura,garante2,comision_asesor,codusuario,"
                    + "generariva,cotizacionmoneda,interesnocobrado,oficial,prestamos.asesor,destino,"
                    + "garantia,prestamos.estado,entregarneto,observaciones,aprobadopor,idfactura,"
                    + "prestamos.giraduria,prestamos.nrocuenta,prestamos.segurovida,prestamos.montoaprobado,giradurias.nombre as nombregiraduria,"
                    + "clientes.nombre AS nombrecliente,comprobantes.nombre as nombrecomprobante,"
                    + "sucursales.nombre as nombresucursal,vendedores.nombre as nombrevendedor "
                    + " FROM prestamos "
                    + " LEFT JOIN giradurias "
                    + " ON giradurias.codigo=prestamos.giraduria "
                    + " INNER JOIN vendedores "
                    + " ON vendedores.codigo=prestamos.asesor "
                    + " INNER JOIN clientes "
                    + " ON clientes.codigo=prestamos.socio "
                    + " INNER JOIN comprobantes "
                    + " ON comprobantes.codigo=prestamos.tipo "
                    + " INNER JOIN sucursales "
                    + " ON sucursales.codigo=prestamos.sucursal "
                    + " WHERE prestamos.numero= ? "
                    + " ORDER BY prestamos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    giraduria gi = new giraduria();
                    cliente cl = new cliente();
                    vendedor ve = new vendedor();
                    comprobante co = new comprobante();
                    sucursal su = new sucursal();
                    moneda mo = new moneda();

                    pre.setGiraduria(gi);
                    pre.setSocio(cl);
                    pre.setOficial(ve);
                    pre.setTipo(co);
                    pre.setSucursal(su);
                    pre.setMoneda(mo);

                    pre.setIdprestamos(rs.getString("idprestamos"));
                    pre.getSucursal().setCodigo(rs.getInt("sucursal"));
                    pre.getSucursal().setNombre(rs.getString("nombresucursal"));
                    pre.getMoneda().setCodigo(rs.getInt("moneda"));
                    pre.setNumero(rs.getInt("numero"));
                    pre.setFecha(rs.getDate("fecha"));
                    pre.setPrimer_vence(rs.getDate("primer_vence"));
                    pre.setImporte(rs.getBigDecimal("importe"));
                    pre.setPlazo(rs.getInt("plazo"));
                    pre.setDestino(rs.getInt("destino"));
                    pre.setGarantia(rs.getInt("garantia"));
                    pre.setTasa(rs.getBigDecimal("tasa"));
                    pre.setInteres(rs.getBigDecimal("interes"));
                    pre.setIvainteres(rs.getBigDecimal("ivainteres"));
                    pre.setComision_deudor(rs.getBigDecimal("comision_deudor"));
                    pre.setTotalamortizacion(rs.getBigDecimal("totalamortizacion"));
                    pre.setMonto_entregar(rs.getBigDecimal("monto_entregar"));
                    pre.setDeducciones(rs.getBigDecimal("deducciones"));
                    pre.setGastos_escritura(rs.getBigDecimal("gastos_escritura"));
                    pre.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    pre.setMontoaprobado(rs.getBigDecimal("montoaprobado"));
                    pre.setSegurovida(rs.getBigDecimal("segurovida"));
                    pre.setTotalprestamo(rs.getBigDecimal("totalprestamo"));
                    pre.getSocio().setCodigo(rs.getInt("socio"));
                    pre.getSocio().setNombre(rs.getString("nombrecliente"));
                    pre.getTipo().setCodigo(rs.getInt("tipo"));
                    pre.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    pre.getOficial().setCodigo(rs.getInt("asesor"));
                    pre.getOficial().setNombre(rs.getString("nombrevendedor"));
                    pre.setAsiento(rs.getInt("asiento"));
                    pre.setNrocuenta(rs.getString("nrocuenta"));
                    pre.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    pre.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    pre.setObservaciones(rs.getString("observaciones"));
                    pre.setEstado(rs.getString("estado"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();

        if (conn != null) {
            System.out.println("ESTAS CONECTADO ");
        } else {
            System.out.println("CONEXIONES CERRADAS ");
        }

        return pre;
    }

    public prestamo ActualizarPrestamoDebitoAutomatico(prestamo pre) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        boolean guardado = false;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  prestamos  SET giraduria=?,nrocuenta=? WHERE numero= " + pre.getNumero());
        ps.setInt(1, pre.getGiraduria().getCodigo());
        ps.setString(2, pre.getNrocuenta());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return pre;
    }

    public prestamo ActualizarPrestamoDesembolso(prestamo pre) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        boolean guardado = false;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  prestamos  SET estado=? WHERE numero= " + pre.getNumero());
        ps.setString(1, pre.getEstado());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return pre;
    }

    public ArrayList<prestamo> ConsultaPrestamoxAsesor(int nvendedor, Date FechaI, Date FechaF) throws SQLException {
        ArrayList<prestamo> lista = new ArrayList<prestamo>();

        con = new Conexion();
        st = con.conectar();

        try {

            String sql = "SELECT prestamos.idprestamos,prestamos.numero,prestamos.fecha,prestamos.moneda,prestamos.sucursal,acreedor,porcentaje_acreedor,comision_acreedor,"
                    + "solicitud,prestamos.socio,monto_entregar,porcentaje_deudor,comision_deudor,prestamos.plazo,deducciones,importe,"
                    + "monto_cuota,primer_vence,prestamos.tipo,prestamos.tasa,prestamos.interes,prestamos.ivainteres,cierre,asiento,totalamortizacion,"
                    + "totalprestamo,tipopago,prestamos.garante,gastos_escritura,garante2,comision_asesor,codusuario,"
                    + "generariva,cotizacionmoneda,interesnocobrado,oficial,destino,"
                    + "garantia,prestamos.estado,entregarneto,observaciones,aprobadopor,idfactura,"
                    + "prestamos.giraduria,prestamos.nrocuenta,segurovida,montoaprobado,giradurias.nombre as nombregiraduria,"
                    + "clientes.nombre AS nombrecliente,comprobantes.nombre as nombrecomprobante,"
                    + "sucursales.nombre as nombresucursal,vendedores.nombre as nombrevendedor "
                    + " FROM prestamos "
                    + " LEFT JOIN giradurias "
                    + " ON giradurias.codigo=prestamos.giraduria "
                    + " INNER JOIN vendedores "
                    + " ON vendedores.codigo=prestamos.oficial "
                    + " INNER JOIN clientes "
                    + " ON clientes.codigo=prestamos.socio "
                    + " INNER JOIN comprobantes "
                    + " ON comprobantes.codigo=prestamos.tipo "
                    + " INNER JOIN sucursales "
                    + " ON sucursales.codigo=prestamos.sucursal "
                    + " WHERE prestamos.oficial= ? "
                    + " AND prestamos.fecha between ? AND ?"
                    + " AND prestamos.estado='DESEMBOLSADO' "
                    + " ORDER BY prestamos.numero ";
            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, nvendedor);
                ps.setDate(2, FechaI);
                ps.setDate(3, FechaF);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    giraduria gi = new giraduria();
                    cliente cl = new cliente();
                    vendedor ve = new vendedor();
                    comprobante co = new comprobante();
                    sucursal su = new sucursal();
                    prestamo pre = new prestamo();

                    pre.setGiraduria(gi);
                    pre.setSocio(cl);
                    pre.setOficial(ve);
                    pre.setTipo(co);
                    pre.setSucursal(su);

                    pre.setIdprestamos(rs.getString("idprestamos"));
                    pre.getSucursal().setCodigo(rs.getInt("sucursal"));
                    pre.getSucursal().setNombre(rs.getString("nombresucursal"));
                    pre.setNumero(rs.getInt("numero"));
                    pre.setFecha(rs.getDate("fecha"));
                    pre.setImporte(rs.getBigDecimal("importe"));
                    pre.setPlazo(rs.getInt("plazo"));
                    pre.setTasa(rs.getBigDecimal("tasa"));
                    pre.setInteres(rs.getBigDecimal("interes"));
                    pre.setIvainteres(rs.getBigDecimal("ivainteres"));
                    pre.setComision_deudor(rs.getBigDecimal("comision_deudor"));
                    pre.setTotalamortizacion(rs.getBigDecimal("totalamortizacion"));
                    pre.setDeducciones(rs.getBigDecimal("deducciones"));
                    pre.setGastos_escritura(rs.getBigDecimal("gastos_escritura"));
                    pre.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    pre.setSegurovida(rs.getBigDecimal("segurovida"));
                    pre.getSocio().setCodigo(rs.getInt("socio"));
                    pre.getSocio().setNombre(rs.getString("nombrecliente"));
                    pre.getTipo().setCodigo(rs.getInt("tipo"));
                    pre.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    pre.getOficial().setCodigo(rs.getInt("oficial"));
                    pre.getOficial().setNombre(rs.getString("nombrevendedor"));
                    pre.setAsiento(rs.getInt("asiento"));
                    pre.setNrocuenta(rs.getString("nrocuenta"));
                    pre.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    pre.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    pre.setObservaciones(rs.getString("observaciones"));
                    lista.add(pre);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<prestamo> ConsultaPrestamoxMoneda(int nmoneda, Date FechaI, Date FechaF) throws SQLException {
        ArrayList<prestamo> lista = new ArrayList<prestamo>();

        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT prestamos.idprestamos,prestamos.numero,prestamos.fecha,prestamos.moneda,prestamos.sucursal,acreedor,porcentaje_acreedor,comision_acreedor,"
                    + "prestamos.solicitud,socio,monto_entregar,porcentaje_deudor,comision_deudor,prestamos.plazo,deducciones,importe,"
                    + "monto_cuota,primer_vence,prestamos.tipo,prestamos.tasa,prestamos.interes,prestamos.ivainteres,cierre,asiento,totalamortizacion,"
                    + "totalprestamo,tipopago,prestamos.garante,gastos_escritura,garante2,comision_asesor,codusuario,"
                    + "generariva,cotizacionmoneda,interesnocobrado,oficial,destino,"
                    + "garantia,prestamos.estado,entregarneto,observaciones,aprobadopor,idfactura,"
                    + "prestamos.giraduria,prestamos.nrocuenta,segurovida,montoaprobado,giradurias.nombre as nombregiraduria,"
                    + "clientes.nombre AS nombrecliente,comprobantes.nombre as nombrecomprobante,"
                    + "sucursales.nombre as nombresucursal,vendedores.nombre as nombrevendedor "
                    + " FROM prestamos "
                    + " LEFT JOIN giradurias "
                    + " ON giradurias.codigo=prestamos.giraduria "
                    + " INNER JOIN vendedores "
                    + " ON vendedores.codigo=prestamos.oficial "
                    + " INNER JOIN clientes "
                    + " ON clientes.codigo=prestamos.socio "
                    + " INNER JOIN comprobantes "
                    + " ON comprobantes.codigo=prestamos.tipo "
                    + " INNER JOIN sucursales "
                    + " ON sucursales.codigo=prestamos.sucursal "
                    + " WHERE prestamos.moneda= ? "
                    + " AND prestamos.fecha between ? AND ?"
                    + " AND prestamos.estado='DESEMBOLSADO' "
                    + " ORDER BY prestamos.numero ";
            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, nmoneda);
                ps.setDate(2, FechaI);
                ps.setDate(3, FechaF);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    giraduria gi = new giraduria();
                    cliente cl = new cliente();
                    vendedor ve = new vendedor();
                    comprobante co = new comprobante();
                    sucursal su = new sucursal();
                    prestamo pre = new prestamo();

                    pre.setGiraduria(gi);
                    pre.setSocio(cl);
                    pre.setOficial(ve);
                    pre.setTipo(co);
                    pre.setSucursal(su);

                    pre.setIdprestamos(rs.getString("idprestamos"));
                    pre.getSucursal().setCodigo(rs.getInt("sucursal"));
                    pre.getSucursal().setNombre(rs.getString("nombresucursal"));
                    pre.setNumero(rs.getInt("numero"));
                    pre.setFecha(rs.getDate("fecha"));
                    pre.setImporte(rs.getBigDecimal("importe"));
                    pre.setPlazo(rs.getInt("plazo"));
                    pre.setTasa(rs.getBigDecimal("tasa"));
                    pre.setInteres(rs.getBigDecimal("interes"));
                    pre.setIvainteres(rs.getBigDecimal("ivainteres"));
                    pre.setComision_deudor(rs.getBigDecimal("comision_deudor"));
                    pre.setTotalamortizacion(rs.getBigDecimal("totalamortizacion"));
                    pre.setDeducciones(rs.getBigDecimal("deducciones"));
                    pre.setGastos_escritura(rs.getBigDecimal("gastos_escritura"));
                    pre.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    pre.setSegurovida(rs.getBigDecimal("segurovida"));
                    pre.getSocio().setCodigo(rs.getInt("socio"));
                    pre.getSocio().setNombre(rs.getString("nombrecliente"));
                    pre.getTipo().setCodigo(rs.getInt("tipo"));
                    pre.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    pre.getOficial().setCodigo(rs.getInt("oficial"));
                    pre.getOficial().setNombre(rs.getString("nombrevendedor"));
                    pre.setAsiento(rs.getInt("asiento"));
                    pre.setNrocuenta(rs.getString("nrocuenta"));
                    pre.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    pre.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    pre.setObservaciones(rs.getString("observaciones"));
                    lista.add(pre);
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

    public ArrayList<prestamo> ConsultaPrestamoxFecha(Date FechaI, Date FechaF) throws SQLException {
        ArrayList<prestamo> lista = new ArrayList<prestamo>();

        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT prestamos.idprestamos,prestamos.numero,prestamos.fecha,prestamos.moneda,prestamos.sucursal,acreedor,porcentaje_acreedor,comision_acreedor,"
                    + "solicitud,socio,monto_entregar,porcentaje_deudor,comision_deudor,prestamos.plazo,deducciones,importe,"
                    + "monto_cuota,primer_vence,prestamos.tipo,tasa,interes,ivainteres,cierre,asiento,totalamortizacion,"
                    + "totalprestamo,tipopago,prestamos.garante,gastos_escritura,garante2,comision_asesor,codusuario,"
                    + "generariva,cotizacionmoneda,interesnocobrado,oficial,destino,"
                    + "garantia,prestamos.estado,entregarneto,observaciones,aprobadopor,idfactura,"
                    + "prestamos.giraduria,prestamos.nrocuenta,segurovida,montoaprobado,giradurias.nombre as nombregiraduria,"
                    + "clientes.nombre AS nombrecliente,comprobantes.nombre as nombrecomprobante,"
                    + "sucursales.nombre as nombresucursal,vendedores.nombre as nombrevendedor "
                    + " FROM prestamos "
                    + " LEFT JOIN giradurias "
                    + " ON giradurias.codigo=prestamos.giraduria "
                    + " INNER JOIN vendedores "
                    + " ON vendedores.codigo=prestamos.oficial "
                    + " INNER JOIN clientes "
                    + " ON clientes.codigo=prestamos.socio "
                    + " INNER JOIN comprobantes "
                    + " ON comprobantes.codigo=prestamos.tipo "
                    + " INNER JOIN sucursales "
                    + " ON sucursales.codigo=prestamos.sucursal "
                    + " WHERE prestamos.fecha between ? AND ?"
                    + " AND prestamos.estado='DESEMBOLSADO' "
                    + " ORDER BY prestamos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, FechaI);
                ps.setDate(2, FechaF);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria gi = new giraduria();
                    cliente cl = new cliente();
                    vendedor ve = new vendedor();
                    comprobante co = new comprobante();
                    sucursal su = new sucursal();
                    prestamo pre = new prestamo();

                    pre.setGiraduria(gi);
                    pre.setSocio(cl);
                    pre.setOficial(ve);
                    pre.setTipo(co);
                    pre.setSucursal(su);

                    pre.setIdprestamos(rs.getString("idprestamos"));
                    pre.getSucursal().setCodigo(rs.getInt("sucursal"));
                    pre.getSucursal().setNombre(rs.getString("nombresucursal"));
                    pre.setNumero(rs.getInt("numero"));
                    pre.setFecha(rs.getDate("fecha"));
                    pre.setImporte(rs.getBigDecimal("importe"));
                    pre.setPlazo(rs.getInt("plazo"));
                    pre.setTasa(rs.getBigDecimal("tasa"));
                    pre.setInteres(rs.getBigDecimal("interes"));
                    pre.setIvainteres(rs.getBigDecimal("ivainteres"));
                    pre.setComision_deudor(rs.getBigDecimal("comision_deudor"));
                    pre.setTotalamortizacion(rs.getBigDecimal("totalamortizacion"));
                    pre.setDeducciones(rs.getBigDecimal("deducciones"));
                    pre.setGastos_escritura(rs.getBigDecimal("gastos_escritura"));
                    pre.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    pre.setSegurovida(rs.getBigDecimal("segurovida"));
                    pre.getSocio().setCodigo(rs.getInt("socio"));
                    pre.getSocio().setNombre(rs.getString("nombrecliente"));
                    pre.getTipo().setCodigo(rs.getInt("tipo"));
                    pre.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    pre.getOficial().setCodigo(rs.getInt("oficial"));
                    pre.getOficial().setNombre(rs.getString("nombrevendedor"));
                    pre.setAsiento(rs.getInt("asiento"));
                    pre.setNrocuenta(rs.getString("nrocuenta"));
                    pre.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    pre.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    pre.setObservaciones(rs.getString("observaciones"));
                    lista.add(pre);
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

    public ArrayList<prestamo> InteresADevengarDevengados(Date dfechaini, Date dfechafin, int nmoneda) throws SQLException {
        ArrayList<prestamo> lista = new ArrayList<prestamo>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cta.idprestamos,cta.numero,"
                    + "cta.fecha,cta.socio,cta.fecha,cta.tipo,clientes.nombre,"
                    + "monedas.nombre AS nombremoneda,cta.moneda,"
                    + "clientes.cedula,comprobantes.nombre AS nombrecomprobante,"
                    + "comprobantes.nomalias,"
                    + "cta.totalprestamo AS totalcredito,"
                    + "cta.importe AS capital,"
                    + "COALESCE(p.interesdevengado,0) AS interesdevengado,"
                    + "COALESCE(pre.interesadevengar,0) AS interesadevengar "
                    + "FROM prestamos cta "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=cta.socio "
                    + "INNER JOIN comprobantes "
                    + "ON comprobantes.codigo=cta.tipo "
                    + "INNER JOIN monedas "
                    + "ON monedas.codigo=cta.moneda "
                    + "LEFT JOIN "
                    + "(SELECT detalle_prestamo.nprestamo,COALESCE(SUM(detalle_prestamo.minteres),0) "
                    + "AS interesdevengado "
                    + "FROM detalle_prestamo "
                    + "LEFT JOIN prestamos "
                    + "ON prestamos.numero=detalle_prestamo.nprestamo "
                    + "WHERE detalle_prestamo.vence<='" + dfechafin + "'"
                    + " GROUP BY detalle_prestamo.nprestamo) "
                    + " p ON p.nprestamo=cta.numero "
                    + "LEFT JOIN "
                    + "(SELECT detalle_prestamo.nprestamo,COALESCE(SUM(detalle_prestamo.minteres),0) "
                    + " AS interesadevengar "
                    + " FROM detalle_prestamo "
                    + " LEFT JOIN prestamos "
                    + " ON prestamos.numero=detalle_prestamo.nprestamo "
                    + " WHERE detalle_prestamo.vence>'" + dfechafin + "'"
                    + " GROUP BY detalle_prestamo.nprestamo) "
                    + " pre ON pre.nprestamo=cta.numero "
                    + " WHERE cta.fecha BETWEEN '" + dfechaini + "' AND '" + dfechafin + "'"
                    + " AND cta.estado='DESEMBOLSADO' "
                    + " AND cta.moneda= " + nmoneda
                    + " GROUP BY cta.numero "
                    + " ORDER BY cta.moneda,cta.tipo,cta.fecha ";

            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    comprobante com = new comprobante();
                    prestamo pre = new prestamo();
                    moneda mo = new moneda();
                    pre.setSocio(c);
                    pre.setTipo(com);
                    pre.setNumero(rs.getInt("numero"));
                    pre.setFecha(rs.getDate("fecha"));
                    pre.getSocio().setCodigo(rs.getInt("socio"));
                    pre.getSocio().setNombre(rs.getString("nombre"));
                    pre.getTipo().setCodigo(rs.getInt("tipo"));
                    pre.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    pre.setImporte(rs.getBigDecimal("capital"));
                    pre.setTotalprestamo(rs.getBigDecimal("totalcredito"));
                    pre.setInteresnocobrado(rs.getBigDecimal("interesadevengar"));
                    pre.setInteres(rs.getBigDecimal("interesdevengado"));
                    lista.add(pre);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public boolean actualizarBICSA() throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        System.out.println("ENTRE A PRESTAMODAO");

        String sql2 = "UPDATE prestamos SET saldoprestamo=(SELECT SUM(saldo) FROM cuenta_clientes WHERE idprestamos=creferencia GROUP BY creferencia)";
        PreparedStatement ps2 = conn.prepareStatement(sql2);
        System.out.println(sql2);
        ps2.executeUpdate(sql2);

        String sql3 = "UPDATE prestamos SET saldoprestamo=0 WHERE ISNULL(saldoprestamo)";
        PreparedStatement ps3 = conn.prepareStatement(sql3);
        System.out.println(sql3);
        ps3.executeUpdate(sql3);

        String sql4 = "UPDATE prestamos SET cuotaspagadas=(SELECT COUNT(creferencia) FROM cuenta_clientes WHERE idprestamos=creferencia AND saldo=0 GROUP BY creferencia)";
        PreparedStatement ps4 = conn.prepareStatement(sql4);
        System.out.println(sql4);
        ps4.executeUpdate(sql4);

        String sql5 = "UPDATE prestamos SET cuotaspagadas=0 WHERE ISNULL(cuotaspagadas)";
        PreparedStatement ps5 = conn.prepareStatement(sql5);
        System.out.println(sql5);
        ps5.executeUpdate(sql5);

        String sql6 = "UPDATE cuenta_clientes SET diasmora=DATEDIFF(CURDATE(),vencimiento) WHERE saldo>0 AND CURDATE()>vencimiento";
        PreparedStatement ps6 = conn.prepareStatement(sql6);
        System.out.println(sql6);

        ps6.executeUpdate(sql6);

        String sql7 = "UPDATE cuenta_clientes SET diasmora=0 WHERE ISNULL(diasmora)";
        PreparedStatement ps7 = conn.prepareStatement(sql7);
        System.out.println(sql7);
        ps7.executeUpdate(sql7);

        String sql8 = "UPDATE cabecera_ventas SET saldoprestamo=(SELECT SUM(saldo) FROM cuenta_clientes WHERE cabecera_ventas.creferencia=cuenta_clientes.creferencia GROUP BY creferencia)";
        PreparedStatement ps8 = conn.prepareStatement(sql8);
        System.out.println(sql8);
        ps8.executeUpdate(sql8);

        String sql9 = "UPDATE cabecera_ventas SET saldoprestamo=0 WHERE ISNULL(saldoprestamo)";
        PreparedStatement ps9 = conn.prepareStatement(sql9);
        System.out.println(sql9);
        ps8.executeUpdate(sql9);

        String sql10 = "UPDATE cabecera_ventas SET cuotaspagadas=(SELECT COUNT(creferencia) FROM cuenta_clientes WHERE cabecera_ventas.creferencia=cuenta_clientes.creferencia AND saldo=0 GROUP BY creferencia)";
        PreparedStatement ps10 = conn.prepareStatement(sql10);
        System.out.println(sql10);
        ps10.executeUpdate(sql10);

        String sql11 = "UPDATE cabecera_ventas SET cuotaspagadas=0 WHERE ISNULL(cuotaspagadas)";
        PreparedStatement ps11 = conn.prepareStatement(sql11);
        System.out.println(sql11);
        ps11.executeUpdate(sql11);

        String sql12 = "UPDATE cabecera_ventas SET fechacancelacion=(SELECT MAX(fecha_pago) FROM cuenta_clientes WHERE cabecera_ventas.creferencia=cuenta_clientes.creferencia GROUP BY creferencia)";
        PreparedStatement ps12 = conn.prepareStatement(sql12);
        System.out.println(sql12);
        ps12.executeUpdate(sql12);

        String sql13 = "UPDATE prestamos SET fechacancelacion=(SELECT MAX(fecha_pago) FROM cuenta_clientes WHERE idprestamos=creferencia GROUP BY creferencia)";
        PreparedStatement ps13 = conn.prepareStatement(sql13);
        System.out.println(sql13);
        ps13.executeUpdate(sql13);

        st.close();
        ps2.close();
        ps3.close();
        ps4.close();
        ps5.close();
        ps5.close();
        ps7.close();
        ps8.close();
        ps9.close();
        ps10.close();
        conn.close();
        return true;
    }

    public prestamo InsertarMicroCredito(prestamo v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO prestamos "
                + "(idprestamos,fecha,primer_vence,"
                + "moneda,sucursal,socio,"
                + "plazo,importe,monto_cuota,"
                + "tipo,tasa,interes,"
                + "totalprestamo,gastos_escritura,asesor,"
                + "codusuario,destino,entregarneto,"
                + "seguro,capitalizacion,aporte,"
                + "solidaridad,serviciocobrador,"
                + "fondoproteccion) "
                + "VALUES (?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getIdprestamos());
        ps.setDate(2, v.getFecha());
        ps.setDate(3, v.getPrimer_vence());
        ps.setInt(4, v.getMoneda().getCodigo());
        ps.setInt(5, v.getSucursal().getCodigo());
        ps.setInt(6, v.getSocio().getCodigo());
        ps.setInt(7, v.getPlazo());
        ps.setBigDecimal(8, v.getImporte());
        ps.setBigDecimal(9, v.getMonto_cuota());
        ps.setInt(10, v.getTipo().getCodigo());
        ps.setBigDecimal(11, v.getTasa());
        ps.setBigDecimal(12, v.getInteres());
        ps.setBigDecimal(13, v.getTotalprestamo());
        ps.setBigDecimal(14, v.getGastos_escritura());
        ps.setInt(15, v.getAsesor().getCodigo());
        ps.setInt(16, v.getCodusuario());
        ps.setInt(17, v.getDestino());
        ps.setBigDecimal(18, v.getEntregarneto());
        ps.setBigDecimal(19, v.getSeguro());
        ps.setBigDecimal(20, v.getCapitalizacion());
        ps.setBigDecimal(21, v.getAporte());
        ps.setBigDecimal(22, v.getSolidaridad());
        ps.setBigDecimal(23, v.getServiciocobrador());
        ps.setBigDecimal(24, v.getFondoproteccion());

        try {
            ps.executeUpdate();
            ResultSet keyset = ps.getGeneratedKeys();
            if (keyset.next()) {
                id = keyset.getInt(1);
                guardado = guardarDetalle(id, detalle, con);
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }

        st.close();
        ps.close();
        conn.close();
        JOptionPane.showMessageDialog(null, "N° de Préstamo Generado " + id);
        return v;
    }

    public boolean ActualizarMicroCredito(prestamo v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        boolean guardado = false;
        int id = 0;

        ps = st.getConnection().prepareStatement("UPDATE prestamos "
                + " SET idprestamos=?,fecha=?,primer_vence=?,"
                + "moneda=?,sucursal=?,socio=?,"
                + "plazo=?,importe=?,monto_cuota=?,"
                + "tipo=?,tasa=?,interes=?,"
                + "totalprestamo=?,gastos_escritura=?,asesor=?,"
                + "codusuario=?,destino=?,entregarneto=?,"
                + "seguro=?,capitalizacion=?,aporte=?,"
                + "solidaridad=?,serviciocobrador=?,"
                + "fondoproteccion=? WHERE numero=" + v.getNumero());

        ps.setString(1, v.getIdprestamos());
        ps.setDate(2, v.getFecha());
        ps.setDate(3, v.getPrimer_vence());
        ps.setInt(4, v.getMoneda().getCodigo());
        ps.setInt(5, v.getSucursal().getCodigo());
        ps.setInt(6, v.getSocio().getCodigo());
        ps.setInt(7, v.getPlazo());
        ps.setBigDecimal(8, v.getImporte());
        ps.setBigDecimal(9, v.getMonto_cuota());
        ps.setInt(10, v.getTipo().getCodigo());
        ps.setBigDecimal(11, v.getTasa());
        ps.setBigDecimal(12, v.getInteres());
        ps.setBigDecimal(13, v.getTotalprestamo());
        ps.setBigDecimal(14, v.getGastos_escritura());
        ps.setInt(15, v.getAsesor().getCodigo());
        ps.setInt(16, v.getCodusuario());
        ps.setInt(17, v.getDestino());
        ps.setBigDecimal(18, v.getEntregarneto());
        ps.setBigDecimal(19, v.getSeguro());
        ps.setBigDecimal(20, v.getCapitalizacion());
        ps.setBigDecimal(21, v.getAporte());
        ps.setBigDecimal(22, v.getSolidaridad());
        ps.setBigDecimal(23, v.getServiciocobrador());
        ps.setBigDecimal(24, v.getFondoproteccion());
        id = (int) v.getNumero();
        int rowsUpdated = ps.executeUpdate();
        guardado = guardarDetalle(id, detalle, con);
        st.close();
        ps.close();
        conn.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
    }

    public boolean guardarDetalle(int id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement psdetalle = null;
        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_prestamo WHERE nprestamo=?");
        psdetalle.setInt(1, id);
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

                    String sql = "insert into  detalle_prestamo("
                            + "nprestamo,"
                            + "nrocuota,"
                            + "capital,"
                            + "emision,"
                            + "vence,"
                            + "dias,"
                            + "monto) "
                            + "values(?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("nrocuota").getAsString());
                        ps.setString(3, obj.get("capital").getAsString());
                        ps.setString(4, obj.get("emision").getAsString());
                        ps.setString(5, obj.get("vence").getAsString());
                        ps.setString(6, obj.get("dias").getAsString());
                        ps.setString(7, obj.get("monto").getAsString());
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
        st.close();
        conn.close();
        return guardado;
    }

    public prestamo buscarMicroCredito(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        prestamo pre = new prestamo();
        try {
            String sql = "SELECT prestamos.idprestamos,prestamos.numero,"
                    + "prestamos.fecha,prestamos.moneda,"
                    + "prestamos.sucursal,prestamos.importe,"
                    + "solicitud,socio,prestamos.plazo,"
                    + "monto_cuota,primer_vence,prestamos.tipo,"
                    + "prestamos.tasa,prestamos.interes,"
                    + "totalprestamo,gastos_escritura,codusuario,"
                    + "prestamos.asesor,destino,prestamos.estado,entregarneto,"
                    + "observaciones,aprobadopor,idfactura,"
                    + "prestamos.montoaprobado,"
                    + "clientes.nombre AS nombrecliente,comprobantes.nombre as nombrecomprobante,"
                    + "sucursales.nombre as nombresucursal,vendedores.nombre as nombrevendedor,"
                    + "prestamos.capitalizacion,prestamos.seguro,"
                    + "prestamos.aporte,prestamos.solidaridad,"
                    + "prestamos.serviciocobrador,prestamos.fondoproteccion "
                    + " FROM prestamos "
                    + " INNER JOIN vendedores "
                    + " ON vendedores.codigo=prestamos.asesor "
                    + " INNER JOIN clientes "
                    + " ON clientes.codigo=prestamos.socio "
                    + " INNER JOIN comprobantes "
                    + " ON comprobantes.codigo=prestamos.tipo "
                    + " INNER JOIN sucursales "
                    + " ON sucursales.codigo=prestamos.sucursal "
                    + " WHERE prestamos.numero= ? "
                    + " ORDER BY prestamos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cl = new cliente();
                    vendedor ve = new vendedor();
                    comprobante co = new comprobante();
                    sucursal su = new sucursal();

                    pre.setSocio(cl);
                    pre.setAsesor(ve);
                    pre.setTipo(co);
                    pre.setSucursal(su);

                    pre.setIdprestamos(rs.getString("idprestamos"));
                    pre.getSucursal().setCodigo(rs.getInt("sucursal"));
                    pre.getSucursal().setNombre(rs.getString("nombresucursal"));
                    pre.setNumero(rs.getInt("numero"));
                    pre.setFecha(rs.getDate("fecha"));
                    pre.setPrimer_vence(rs.getDate("primer_vence"));
                    pre.setImporte(rs.getBigDecimal("importe"));
                    pre.setPlazo(rs.getInt("plazo"));
                    pre.setDestino(rs.getInt("destino"));
                    pre.setTasa(rs.getBigDecimal("tasa"));
                    pre.setInteres(rs.getBigDecimal("interes"));
                    pre.setEntregarneto(rs.getBigDecimal("entregarneto"));
                    pre.setGastos_escritura(rs.getBigDecimal("gastos_escritura"));
                    pre.setMonto_cuota(rs.getBigDecimal("monto_cuota"));
                    pre.setTotalprestamo(rs.getBigDecimal("totalprestamo"));
                    pre.getSocio().setCodigo(rs.getInt("socio"));
                    pre.getSocio().setNombre(rs.getString("nombrecliente"));
                    pre.getTipo().setCodigo(rs.getInt("tipo"));
                    pre.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    pre.getAsesor().setCodigo(rs.getInt("asesor"));
                    pre.getAsesor().setNombre(rs.getString("nombrevendedor"));
                    pre.setSeguro(rs.getBigDecimal("seguro"));
                    pre.setCapitalizacion(rs.getBigDecimal("capitalizacion"));
                    pre.setAporte(rs.getBigDecimal("aporte"));
                    pre.setSolidaridad(rs.getBigDecimal("solidaridad"));
                    pre.setServiciocobrador(rs.getBigDecimal("serviciocobrador"));
                    pre.setFondoproteccion(rs.getBigDecimal("fondoproteccion"));
                    pre.setEstado(rs.getString("estado"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();

        if (conn != null) {
            System.out.println("ESTAS CONECTADO ");
        } else {
            System.out.println("CONEXIONES CERRADAS ");
        }

        return pre;
    }

    public prestamo InsertarPrestamoGCalvo(prestamo v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO prestamos "
                + "(idprestamos,fecha,primer_vence,"
                + "moneda,sucursal,socio,"
                + "plazo,importe,monto_cuota,"
                + "tipo,tasa,interes,"
                + "totalprestamo,asesor,"
                + "codusuario,destino) "
                + "VALUES (?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getIdprestamos());
        ps.setDate(2, v.getFecha());
        ps.setDate(3, v.getPrimer_vence());
        ps.setInt(4, v.getMoneda().getCodigo());
        ps.setInt(5, v.getSucursal().getCodigo());
        ps.setInt(6, v.getSocio().getCodigo());
        ps.setInt(7, v.getPlazo());
        ps.setBigDecimal(8, v.getImporte());
        ps.setBigDecimal(9, v.getMonto_cuota());
        ps.setInt(10, v.getTipo().getCodigo());
        ps.setBigDecimal(11, v.getTasa());
        ps.setBigDecimal(12, v.getInteres());
        ps.setBigDecimal(13, v.getTotalprestamo());
        ps.setInt(14, v.getAsesor().getCodigo());
        ps.setInt(15, v.getCodusuario());
        ps.setInt(16, v.getDestino());
        try {
            ps.executeUpdate();
            ResultSet keyset = ps.getGeneratedKeys();
            if (keyset.next()) {
                id = keyset.getInt(1);
                guardado = guardarDetalleGimenezCalvo(id, detalle, con);
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }

        st.close();
        ps.close();
        conn.close();
        JOptionPane.showMessageDialog(null, "N° de Préstamo Generado " + id);
        return v;
    }

    public boolean ActualizarPrestamoGCalvo(prestamo v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        boolean guardado = false;
        int id = 0;

        ps = st.getConnection().prepareStatement("UPDATE prestamos "
                + " SET idprestamos=?,fecha=?,primer_vence=?,"
                + "moneda=?,sucursal=?,socio=?,"
                + "plazo=?,importe=?,monto_cuota=?,"
                + "tipo=?,tasa=?,interes=?,"
                + "totalprestamo=?,asesor=?,"
                + "codusuario=?,destino=? WHERE numero=" + v.getNumero());

        ps.setString(1, v.getIdprestamos());
        ps.setDate(2, v.getFecha());
        ps.setDate(3, v.getPrimer_vence());
        ps.setInt(4, v.getMoneda().getCodigo());
        ps.setInt(5, v.getSucursal().getCodigo());
        ps.setInt(6, v.getSocio().getCodigo());
        ps.setInt(7, v.getPlazo());
        ps.setBigDecimal(8, v.getImporte());
        ps.setBigDecimal(9, v.getMonto_cuota());
        ps.setInt(10, v.getTipo().getCodigo());
        ps.setBigDecimal(11, v.getTasa());
        ps.setBigDecimal(12, v.getInteres());
        ps.setBigDecimal(13, v.getTotalprestamo());
        ps.setInt(14, v.getAsesor().getCodigo());
        ps.setInt(15, v.getCodusuario());
        ps.setInt(16, v.getDestino());
        id = (int) v.getNumero();
        int rowsUpdated = ps.executeUpdate();
        guardado = guardarDetalleGimenezCalvo(id, detalle, con);
        st.close();
        ps.close();
        conn.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
    }

    public boolean guardarDetalleGimenezCalvo(int id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement psdetalle = null;
        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_prestamo WHERE nprestamo=?");
        psdetalle.setInt(1, id);
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

                    String sql = "insert into  detalle_prestamo("
                            + "nprestamo,"
                            + "nrocuota,"
                            + "capital,"
                            + "emision,"
                            + "vence,"
                            + "dias,"
                            + "amortiza,"
                            + "minteres,"
                            + "monto) "
                            + "values(?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("nrocuota").getAsString());
                        ps.setString(3, obj.get("capital").getAsString());
                        ps.setString(4, obj.get("emision").getAsString());
                        ps.setString(5, obj.get("vence").getAsString());
                        ps.setString(6, obj.get("dias").getAsString());
                        ps.setString(7, obj.get("amortiza").getAsString());
                        ps.setString(8, obj.get("minteres").getAsString());
                        ps.setString(9, obj.get("monto").getAsString());
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
        st.close();
        conn.close();
        return guardado;
    }

    public prestamo buscarIdBancard(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        prestamo pre = new prestamo();

        try {

            String sql = "SELECT numero,DAY(p.primer_vence) AS dia_debito,"
                    + "p.totalprestamo,d.monto AS cuota,"
                    + "DATE_FORMAT(p.primer_vence, '%d/%m/%Y') AS fecha_inicio,"
                    + "MAX(DATE_FORMAT(d.vence, '%d/%m/%Y')) AS fecha_final "
                    + "FROM prestamos P "
                    + "LEFT JOIN detalle_prestamo d "
                    + "ON d.nprestamo=p.numero "
                    + "WHERE  p.numero=" + id
                    + " GROUP BY p.numero "
                    + "ORDER BY p.numero";

            System.out.println("--> " + sql);
            
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    pre.setNumero(rs.getInt("numero"));
                    pre.setTotalprestamo(rs.getBigDecimal("totalprestamo"));
                    pre.setDia_debito(rs.getInt("dia_debito"));
                    pre.setMonto_cuota(rs.getBigDecimal("cuota"));
                    pre.setFecha_inicio(rs.getString("fecha_inicio"));
                    pre.setFecha_final(rs.getString("fecha_final"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return pre;
    }

}

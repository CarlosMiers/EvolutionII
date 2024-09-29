package DAO;

import Conexion.Conexion;
import Modelo.comprobante;
import Modelo.moneda;
import Modelo.proveedor;
import Modelo.saldo_proveedores;
import Modelo.sucursal;
import Vista.saldo_proveedor;
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
 * @author Usuario
 */
public class saldo_proveedoresDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<saldo_proveedores> buscarId(int id, int idmoneda) throws SQLException {
        ArrayList<saldo_proveedores> lista = new ArrayList<saldo_proveedores>();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT idmovimiento,idreferencia,nrofactura,emision,saldo_proveedores.vencimiento,proveedor,proveedores.nombre AS nombreproveedor,"
                    + "cuota,numerocuota,importe,saldo,comprobantes.nombre AS nombrecomprobante,comprobantes.codigo AS comprobante "
                    + " FROM saldo_proveedores "
                    + " INNER JOIN proveedores "
                    + " ON proveedores.codigo=saldo_proveedores.proveedor "
                    + " INNER JOIN comprobantes "
                    + " ON comprobantes.codigo=saldo_proveedores.comprobante "
                    + " where saldo_proveedores.proveedor = ? "
                    + " AND saldo_proveedores.moneda = ? "
                    + " AND saldo_proveedores.saldo<>0 "
                    + " ORDER BY saldo_proveedores.vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setInt(2, idmoneda);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    saldo_proveedores saldos = new saldo_proveedores();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();
                    proveedor pr = new proveedor();
                    comprobante comprobante = new comprobante();
                    saldos.setComprobante(comprobante);
                    saldos.setProveedor(pr);
                    saldos.setIdmovimiento(rs.getString("idmovimiento"));
                    saldos.setIdreferencia(rs.getString("idreferencia"));
                    saldos.setNrofactura(rs.getString("nrofactura"));
                    saldos.setEmision(rs.getDate("emision"));
                    saldos.setVencimiento(rs.getDate("vencimiento"));
                    saldos.setNombreproveedor(rs.getString("nombreproveedor"));
                    saldos.setCuota(rs.getInt("cuota"));
                    saldos.setNumerocuota(rs.getInt("numerocuota"));
                    saldos.setImporte(rs.getDouble("importe"));
                    saldos.setSaldo(rs.getDouble("saldo"));
                    saldos.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    saldos.getComprobante().setCodigo(rs.getInt("comprobante"));
                    lista.add(saldos);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<saldo_proveedores> ResumenxProveedor(Date fechaini, int nProveedor, int nMoneda) throws SQLException {
        ArrayList<saldo_proveedores> lista = new ArrayList<saldo_proveedores>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT prov.codigo,prov.nombre,prov.ruc,"
                    + "prov.telefono,prov.direccion,"
                    + "COALESCE(c.creditos,0) AS creditos,"
                    + "COALESCE(p.pagos,0) AS pagos "
                    + "FROM proveedores prov "
                    + "LEFT JOIN (SELECT cuenta_proveedores.proveedor, SUM(cuenta_proveedores.importe) AS creditos "
                    + "FROM cuenta_proveedores "
                    + "WHERE cuenta_proveedores.fecha<='" + fechaini + "'"
                    + " AND cuenta_proveedores.moneda=" + nMoneda
                    + " GROUP BY cuenta_proveedores.proveedor) c ON c.proveedor=prov.codigo "
                    + " LEFT JOIN (SELECT pagos.proveedor, SUM(pagos.totalpago) AS pagos "
                    + " FROM pagos "
                    + " WHERE pagos.fecha<='" + fechaini + "'"
                    + " AND pagos.moneda= " + nMoneda
                    + " GROUP BY pagos.proveedor) p ON p.proveedor=prov.codigo "
                    + " WHERE IF(" + nProveedor + "<>0,prov.codigo=" + nProveedor + ",TRUE) "
                    + " ORDER BY prov.codigo ";

            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    proveedor proveedor = new proveedor();
                    moneda moneda = new moneda();

                    saldo_proveedores sp = new saldo_proveedores();

                    sp.setProveedor(proveedor);
                    sp.setMoneda(moneda);

                    sp.getProveedor().setCodigo(rs.getInt("codigo"));
                    sp.getProveedor().setNombre(rs.getString("nombre"));
                    sp.getProveedor().setDireccion(rs.getString("direccion"));
                    sp.getProveedor().setTelefono(rs.getString("telefono"));
                    sp.getProveedor().setRuc(rs.getString("ruc"));
                    sp.setCredito(rs.getDouble("creditos"));
                    sp.setPagos(rs.getDouble("pagos"));
                    sp.setSaldo(sp.getCredito() - sp.getPagos());
                    if (sp.getSaldo() != 0) {
                        lista.add(sp);
                    }
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

    public ArrayList<saldo_proveedores> DetalleSaldoxProveedor(Date fechaini, int nSucursal, int nSuc, int nProveedor, int nPro, int nMoneda) throws SQLException {
        ArrayList<saldo_proveedores> lista = new ArrayList<saldo_proveedores>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT saldo_proveedores.nrofactura,saldo_proveedores.emision,saldo_proveedores.vencimiento,"
                    + " proveedores.ruc AS rucpro,proveedores.nombre AS nombrepro,saldo_proveedores.nombre AS nombrecomprobante,"
                    + " saldo_proveedores.importe,saldo_proveedores.saldo AS saldofactura, sucursales.codigo AS sucursalcod, sucursales.nombre AS sucursalnombre"
                    + " FROM saldo_proveedores "
                    + " LEFT JOIN proveedores "
                    + " ON proveedores.codigo=saldo_proveedores.proveedor "
                    + " LEFT JOIN sucursales  "
                    + " ON sucursales.codigo=saldo_proveedores.sucursal "
                    + " WHERE saldo_proveedores.emision<=?  "
                    + " AND IF(?<>0,saldo_proveedores.sucursal=?,TRUE) "
                    + " AND IF(?<>0,saldo_proveedores.proveedor=?,TRUE) "
                    + " AND saldo_proveedores.moneda=?  "
                    + " AND saldo_proveedores.saldo<>0  "
                    + " ORDER BY saldo_proveedores.proveedor,saldo_proveedores.vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setInt(2, nSucursal);
                ps.setInt(3, nSuc);
                ps.setInt(4, nProveedor);
                ps.setInt(5, nPro);
                ps.setInt(6, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    proveedor proveedores = new proveedor();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();
                    saldo_proveedores sp = new saldo_proveedores();

                    sp.setProveedor(proveedores);
                    sp.setMoneda(moneda);
                    sp.setSucursal(sucursal);

                    sp.setNrofactura(rs.getString("nrofactura"));
                    sp.setEmision(rs.getDate("emision"));
                    sp.setVencimiento(rs.getDate("vencimiento"));
                    sp.getProveedor().setRuc(rs.getString("rucpro"));
                    sp.getProveedor().setNombre(rs.getString("nombrepro"));
                    sp.setNombre(rs.getString("nombrecomprobante"));
                    sp.setImporte(rs.getDouble("importe"));
                    sp.setSaldo(rs.getDouble("saldofactura"));
                    sp.getSucursal().setCodigo(rs.getInt("sucursalcod"));
                    sp.getSucursal().setNombre(rs.getString("sucursalnombre"));
                    lista.add(sp);
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

    public ArrayList<saldo_proveedores> VencimientoxProveedor(Date fechaini, Date fechafin, int nSucursal, int nSuc, int nProveedor, int nPro, int nMoneda) throws SQLException {
        ArrayList<saldo_proveedores> lista = new ArrayList<saldo_proveedores>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT saldo_proveedores.nrofactura,saldo_proveedores.emision,saldo_proveedores.vencimiento,"
                    + " proveedores.ruc AS rucpro,proveedores.nombre AS nombrepro,saldo_proveedores.nombre AS nombrecomprobante,"
                    + " saldo_proveedores.importe,saldo_proveedores.saldo AS saldofactura, sucursales.codigo AS sucursalcod, sucursales.nombre AS sucursalnombre"
                    + " FROM saldo_proveedores "
                    + " LEFT JOIN proveedores "
                    + " ON proveedores.codigo=saldo_proveedores.proveedor "
                    + " LEFT JOIN sucursales  "
                    + " ON sucursales.codigo=saldo_proveedores.sucursal "
                    + " WHERE saldo_proveedores.emision BETWEEN ? AND ?  "
                    + " AND IF(?<>0,saldo_proveedores.sucursal=?,TRUE) "
                    + " AND IF(?<>0,saldo_proveedores.proveedor=?,TRUE) "
                    + " AND saldo_proveedores.moneda=?  "
                    + " AND saldo_proveedores.saldo<>0  "
                    + " ORDER BY saldo_proveedores.proveedor,saldo_proveedores.vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ps.setInt(5, nProveedor);
                ps.setInt(6, nPro);
                ps.setInt(7, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    proveedor proveedores = new proveedor();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();
                    saldo_proveedores sp = new saldo_proveedores();

                    sp.setProveedor(proveedores);
                    sp.setMoneda(moneda);
                    sp.setSucursal(sucursal);

                    sp.setNrofactura(rs.getString("nrofactura"));
                    sp.setEmision(rs.getDate("emision"));
                    sp.setVencimiento(rs.getDate("vencimiento"));
                    sp.getProveedor().setRuc(rs.getString("rucpro"));
                    sp.getProveedor().setNombre(rs.getString("nombrepro"));
                    sp.setNombre(rs.getString("nombrecomprobante"));
                    sp.setImporte(rs.getDouble("importe"));
                    sp.setSaldo(rs.getDouble("saldofactura"));
                    sp.getSucursal().setCodigo(rs.getInt("sucursalcod"));
                    sp.getSucursal().setNombre(rs.getString("sucursalnombre"));
                    lista.add(sp);
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

    public saldo_proveedores SaldoMovimiento(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        saldo_proveedores sp = new saldo_proveedores();
        try {

            String cSql = "SELECT idmovimiento,saldo,nrofactura "
                    + " FROM saldo_proveedores "
                    + " WHERE idmovimiento='" + id + "'"
                    + " AND saldo<>importe "
                    + " ORDER BY saldo_proveedores.idmovimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    sp.setNrofactura(rs.getString("nrofactura"));
                    sp.setSaldo(rs.getDouble("saldo"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return sp;

    }
}

///saldo de proveedores a una fecha
/*    SELECT pro.codigo,pro.nombre,pro.ruc,pro.direccion,
COALESCE(cta.saldoinicial,0) AS saldoinicial,
COALESCE(pag.pagos,0) AS pagos
FROM proveedores pro
LEFT JOIN (SELECT cuenta_proveedores.proveedor,sum(cuenta_proveedores.importe) as saldoinicial 
FROM cuenta_proveedores
WHERE cuenta_proveedores.fecha <= '2020-01-31'
GROUP BY cuenta_proveedores.proveedor) cta ON cta.proveedor=pro.codigo
LEFT JOIN (SELECT pagos.proveedor,sum(pagos.totalpago) as pagos
FROM pagos
WHERE pagos.fecha <= '2020-01-31'
GROUP BY pagos.proveedor) pag ON pag.proveedor=pro.codigo
ORDER BY pro.codigo*/

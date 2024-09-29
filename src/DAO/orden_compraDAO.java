/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Pc_Server
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Conexion.Conexion;
import Modelo.moneda;
import Modelo.orden_compra;
import Modelo.proveedor;
import Modelo.sucursal;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author Pc_Server
 */
public class orden_compraDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<orden_compra> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<orden_compra> lista = new ArrayList<orden_compra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT orden_compra.numero,orden_compra.fecha,"
                    + "orden_compra.fechaentrega,orden_compra.totalneto,"
                    + "orden_compra.observaciones,orden_compra.condicion,"
                    + "orden_compra.usuario,orden_compra.estado,"
                    + "orden_compra.moneda,monedas.nombre AS nombremoneda,"
                    + "orden_compra.sucursal,sucursales.nombre AS nombresucursal,"
                    + "orden_compra.proveedor,proveedores.nombre AS nombreproveedor "
                    + "FROM orden_compra "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=orden_compra.moneda "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=orden_compra.sucursal "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=orden_compra.proveedor "
                    + "WHERE orden_compra.fecha between ? AND ? "
                    + " ORDER BY orden_compra.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal suc = new sucursal();
                    moneda mon = new moneda();
                    proveedor pro = new proveedor();
                    orden_compra orden = new orden_compra();
                    orden.setNumero(rs.getDouble("numero"));
                    orden.setSucursal(suc);
                    orden.setMoneda(mon);
                    orden.setProveedor(pro);
                    orden.getSucursal().setCodigo(rs.getInt("sucursal"));
                    orden.getSucursal().setNombre(rs.getString("nombresucursal"));
                    orden.getMoneda().setCodigo(rs.getInt("moneda"));
                    orden.getMoneda().setNombre(rs.getString("nombremoneda"));
                    orden.getProveedor().setCodigo(rs.getInt("proveedor"));
                    orden.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    orden.setFecha(rs.getDate("fecha"));
                    orden.setFechaentrega(rs.getDate("fechaentrega"));
                    orden.setCondicion(rs.getInt("condicion"));
                    orden.setTotalneto(rs.getDouble("totalneto"));
                    orden.setObservaciones(rs.getString("observaciones"));
                    orden.setEstado(rs.getString("estado"));
                    lista.add(orden);
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

    public orden_compra buscarId(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        orden_compra orden = new orden_compra();

        try {

            String cSql = "SELECT orden_compra.numero,orden_compra.fecha,"
                    + "orden_compra.fechaentrega,orden_compra.totalneto,"
                    + "orden_compra.observaciones,orden_compra.condicion,"
                    + "orden_compra.usuario,orden_compra.estado,"
                    + "orden_compra.moneda,monedas.nombre AS nombremoneda,"
                    + "orden_compra.sucursal,sucursales.nombre AS nombresucursal,"
                    + "orden_compra.proveedor,proveedores.nombre AS nombreproveedor,proveedores.ruc "
                    + "FROM orden_compra "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=orden_compra.moneda "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=orden_compra.sucursal "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=orden_compra.proveedor "
                    + "WHERE orden_compra.numero= ? "
                    + " ORDER BY orden_compra.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal suc = new sucursal();
                    moneda mon = new moneda();
                    proveedor pro = new proveedor();
                    orden.setNumero(rs.getDouble("numero"));
                    orden.setSucursal(suc);
                    orden.setMoneda(mon);
                    orden.setProveedor(pro);
                    orden.getProveedor().setCodigo(rs.getInt("proveedor"));
                    orden.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    orden.getProveedor().setRuc(rs.getString("ruc"));
                    orden.getSucursal().setCodigo(rs.getInt("sucursal"));
                    orden.getSucursal().setNombre(rs.getString("nombresucursal"));
                    orden.getMoneda().setCodigo(rs.getInt("moneda"));
                    orden.getMoneda().setNombre(rs.getString("nombremoneda"));
                    orden.setFecha(rs.getDate("fecha"));
                    orden.setFechaentrega(rs.getDate("fechaentrega"));
                    orden.setCondicion(rs.getInt("condicion"));
                    orden.setTotalneto(rs.getDouble("totalneto"));
                    orden.setObservaciones(rs.getString("observaciones"));
                    orden.setEstado(rs.getString("estado"));
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return orden;
    }

    public orden_compra AgregarOrden(orden_compra v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        double id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO orden_compra"
                + " (sucursal,moneda,fecha,"
                + "fechaentrega,proveedor,totalneto,"
                + "observaciones,condicion,usuario) VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, v.getSucursal().getCodigo());
        ps.setInt(2, v.getMoneda().getCodigo());
        ps.setDate(3, v.getFecha());
        ps.setDate(4, v.getFechaentrega());
        ps.setInt(5, v.getProveedor().getCodigo());
        ps.setDouble(6, v.getTotalneto());
        ps.setString(7, v.getObservaciones());
        ps.setInt(8, v.getCondicion());
        ps.setInt(9, v.getUsuario());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getDouble(1);
            guardado = guardarItemOrden(id, detalle, con);
        }
        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public orden_compra ActualizarOrden(orden_compra v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        double id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE orden_compra"
                + " SET sucursal=?,moneda=?,fecha=?,"
                + "fechaentrega=?,proveedor=?,totalneto=?,"
                + "observaciones=?,condicion=?,usuario=? WHERE numero= " + v.getNumero());
        ps.setInt(1, v.getSucursal().getCodigo());
        ps.setInt(2, v.getMoneda().getCodigo());
        ps.setDate(3, v.getFecha());
        ps.setDate(4, v.getFechaentrega());
        ps.setInt(5, v.getProveedor().getCodigo());
        ps.setDouble(6, v.getTotalneto());
        ps.setString(7, v.getObservaciones());
        ps.setInt(8, v.getCondicion());
        ps.setInt(9, v.getUsuario());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemOrden(v.getNumero(), detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return v;
    }

    public boolean guardarItemOrden(double id, String detalle, Conexion conexion) throws SQLException {
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

                    /*iddetalle
codprod
cantidad
precio
impuesto
importeiva
totalitem*/
                    String sql = "INSERT INTO detalle_orden_compra("
                            + "iddetalle,"
                            + "codprod,"
                            + "cantidad,"
                            + "precio,"
                            + "impuesto,"
                            + "importeiva,"
                            + "totalitem"
                            + ") "
                            + "values(?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setDouble(1, id);
                        ps.setString(2, obj.get("codprod").getAsString());
                        ps.setString(3, obj.get("cantidad").getAsString());
                        ps.setString(4, obj.get("precio").getAsString());
                        ps.setString(5, obj.get("impuesto").getAsString());
                        ps.setString(6, obj.get("importeiva").getAsString());
                        ps.setString(7, obj.get("totalitem").getAsString());
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

    public boolean eliminarOrden(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM orden_compra WHERE numero=?");
        ps.setDouble(1, cod);
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


    
      public boolean CerrarOrdenCompra(int numeroorden) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE orden_compra SET cierre=1 WHERE numero= ?");
        ps.setInt(1, numeroorden);
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        st.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    

    public ArrayList<orden_compra> MostrarActivos() throws SQLException {
        ArrayList<orden_compra> lista = new ArrayList<orden_compra>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT orden_compra.numero,orden_compra.fecha,"
                    + "orden_compra.fechaentrega,orden_compra.totalneto,"
                    + "orden_compra.observaciones,orden_compra.condicion,"
                    + "orden_compra.usuario,orden_compra.estado,"
                    + "orden_compra.moneda,monedas.nombre AS nombremoneda,"
                    + "orden_compra.sucursal,sucursales.nombre AS nombresucursal,"
                    + "orden_compra.proveedor,proveedores.nombre AS nombreproveedor "
                    + "FROM orden_compra "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=orden_compra.moneda "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=orden_compra.sucursal "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=orden_compra.proveedor "
                    + "WHERE orden_compra.cierre=0  "
                    + " ORDER BY orden_compra.numero";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal suc = new sucursal();
                    moneda mon = new moneda();
                    proveedor pro = new proveedor();
                    orden_compra orden = new orden_compra();
                    orden.setNumero(rs.getDouble("numero"));
                    orden.setSucursal(suc);
                    orden.setMoneda(mon);
                    orden.setProveedor(pro);
                    orden.getSucursal().setCodigo(rs.getInt("sucursal"));
                    orden.getSucursal().setNombre(rs.getString("nombresucursal"));
                    orden.getProveedor().setCodigo(rs.getInt("proveedor"));
                    orden.getProveedor().setNombre(rs.getString("nombreproveedor"));
                    orden.setFecha(rs.getDate("fecha"));
                    orden.setTotalneto(rs.getDouble("totalneto"));
                    lista.add(orden);
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

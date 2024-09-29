/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.Config;
import Conexion.Conexion;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.sucursal;
import Modelo.preventa;
import Modelo.vendedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Pc_Server
 */
public class preventaDAO {

    Conexion con = null;
    Statement st = null;

    public preventa InsertarPreVenta(String token, preventa v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);

        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO preventa (fecha,vencimiento,"
                + "sucursal,vendedor,comprobante,cliente,"
                + "totalneto,moneda,observacion,tipo,cierre) VALUES (?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, v.getFecha());
        ps.setDate(2, v.getVencimiento());
        ps.setInt(3, v.getSucursal().getCodigo());
        ps.setInt(4, v.getVendedor().getCodigo());
        ps.setInt(5, v.getComprobante().getCodigo());
        ps.setInt(6, v.getCliente().getCodigo());
        ps.setDouble(7, v.getTotalneto());
        ps.setInt(8, v.getMoneda());
        ps.setString(9, v.getObservacion());
        ps.setInt(10, v.getTipo());
        ps.setInt(11, v.getCierre());
        if (Config.cToken == token) {
            ps.executeUpdate();
            ResultSet keyset = ps.getGeneratedKeys();
            if (keyset.next()) {
                id = keyset.getInt(1);
                guardado = guardarDetalle(id, detalle, con);
                if (guardado) {
                    try {
                        conn.commit();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "N° de Preventa Generado " + id);
                    }
                } else {
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Falla al Intengar Guardar Preventa" + id);
                    }
                }
            }
        } else {
            System.out.println("USUARIO NO AUTORIZADO");
        }

        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public boolean ActualizarPreventa(preventa v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        boolean guardado = false;
        int id = 0;

        ps = st.getConnection().prepareStatement("UPDATE preventa SET fecha=?,vencimiento=?,"
                + "sucursal=?,vendedor=?,comprobante=?,cliente=?,"
                + "totalneto=?,moneda=?,observacion=?,tipo=? WHERE numero=" + v.getNumero());
        ps.setDate(1, v.getFecha());
        ps.setDate(2, v.getVencimiento());
        ps.setInt(3, v.getSucursal().getCodigo());
        ps.setInt(4, v.getVendedor().getCodigo());
        ps.setInt(5, v.getComprobante().getCodigo());
        ps.setInt(6, v.getCliente().getCodigo());
        ps.setDouble(7, v.getTotalneto());
        ps.setInt(8, v.getMoneda());
        ps.setString(9, v.getObservacion());
        ps.setInt(10, v.getTipo());
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

    public boolean ActualizarPreventaFerremax(preventa v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        boolean guardado = false;
        int id = 0;

        ps = st.getConnection().prepareStatement("UPDATE preventa SET fecha=?,vencimiento=?,"
                + "sucursal=?,vendedor=?,comprobante=?,cliente=?,"
                + "totalneto=?,moneda=?,observacion=?,tipo=?,cuotas=?,caja=? WHERE numero=" + v.getNumero());
        ps.setDate(1, v.getFecha());
        ps.setDate(2, v.getVencimiento());
        ps.setInt(3, v.getSucursal().getCodigo());
        ps.setInt(4, v.getVendedor().getCodigo());
        ps.setInt(5, v.getComprobante().getCodigo());
        ps.setInt(6, v.getCliente().getCodigo());
        ps.setDouble(7, v.getTotalneto());
        ps.setInt(8, v.getMoneda());
        ps.setString(9, v.getObservacion());
        ps.setInt(10, v.getTipo());
        ps.setInt(11, v.getCuotas());
        ps.setInt(12, v.getCaja());
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

    public boolean AutorizarDescuento(preventa v) throws SQLException {

        System.out.println("--> FIRMA " + v.getFirma());
        System.out.println("--> NUMERO " + v.getNumero());

        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        int rowsUpdated = 0;
        ps = st.getConnection().prepareStatement("UPDATE preventa SET firma=? WHERE numero=" + v.getNumero());
        ps.setString(1, v.getFirma());

        try {
            rowsUpdated = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> ERROR DESCUENTO " + ex.getLocalizedMessage());
        }

        st.close();
        ps.close();
        if (rowsUpdated > 0) {
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
        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_preventas WHERE iddetalle=?");
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

                    String sql = "insert into  detalle_preventas("
                            + "iddetalle,"
                            + "codprod,"
                            + "cantidad,"
                            + "precio,"
                            + "monto,"
                            + "impiva,"
                            + "porcentaje,"
                            + "comentario"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("codprod").getAsString());
                        ps.setString(3, obj.get("cantidad").getAsString());
                        ps.setString(4, obj.get("precio").getAsString());
                        ps.setString(5, obj.get("monto").getAsString());
                        ps.setString(6, obj.get("impiva").getAsString());
                        ps.setString(7, obj.get("porcentaje").getAsString());
                        ps.setString(8, obj.get("comentario").getAsString());
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

    public preventa buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        preventa vta = new preventa();

        try {

            String sql = "SELECT numero,fecha,vencimiento,cliente,sucursal,moneda,comprobante,cotizacion,"
                    + "preventa.vendedor,caja,totalneto,cierre,preventa.tipo,"
                    + "vendedores.nombre AS nombrevendedor,clientes.nombre AS nombrecliente,"
                    + "comprobantes.nombre AS nombrecomprobante,preventa.vencimiento, "
                    + "sucursales.nombre as nombresucursal,preventa.observacion "
                    + "FROM preventa "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=preventa.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=preventa.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=preventa.vendedor "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=preventa.sucursal "
                    + "WHERE preventa.numero=? "
                    + "ORDER BY preventa.numero  ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();
                    sucursal suc = new sucursal();

                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);
                    vta.setSucursal(suc);

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.setTipo(rs.getInt("tipo"));

                    vta.setNumero(rs.getInt("numero"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.setVencimiento(rs.getDate("vencimiento"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setTotalneto(rs.getDouble("totalneto"));
                    vta.setCierre(rs.getInt("cierre"));
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

    public ArrayList<preventa> Todos() throws SQLException {
        ArrayList<preventa> lista = new ArrayList<preventa>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT numero,fecha,vencimiento,cliente,sucursal,moneda,comprobante,cotizacion,"
                    + "preventa.vendedor,caja,totalneto,preventa.observacion,cierre,clientes.ruc,clientes.direccion,"
                    + "vendedores.nombre AS nombrevendedor,clientes.nombre AS nombrecliente,"
                    + "comprobantes.nombre AS nombrecomprobante "
                    + "FROM preventa "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=preventa.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=preventa.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=preventa.vendedor "
                    + "WHERE preventa.cierre<>1 AND preventa.tipo=1 "
                    + "ORDER BY preventa.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    preventa vta = new preventa();
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();

                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);

                    vta.setNumero(rs.getInt("numero"));
                    vta.setFecha(rs.getDate("fecha"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getCliente().setDireccion(rs.getString("direccion"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setTotalneto(rs.getDouble("totalneto"));
                    vta.setCierre(rs.getInt("cierre"));
                    lista.add(vta);
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public ArrayList<preventa> TodosHoy() throws SQLException {
        ArrayList<preventa> lista = new ArrayList<preventa>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT numero,fecha,vencimiento,cliente,sucursal,moneda,comprobante,cotizacion,"
                    + "preventa.vendedor,caja,totalneto,preventa.observacion,cierre,clientes.ruc,clientes.direccion,"
                    + "vendedores.nombre AS nombrevendedor,clientes.nombre AS nombrecliente,"
                    + "comprobantes.nombre AS nombrecomprobante "
                    + "FROM preventa "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=preventa.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=preventa.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=preventa.vendedor "
                    + "WHERE preventa.cierre<>1 AND preventa.tipo=1 "
                    + " AND preventa.fecha=CURDATE() "
                    + "ORDER BY preventa.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    preventa vta = new preventa();
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();

                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);

                    vta.setNumero(rs.getInt("numero"));
                    vta.setFecha(rs.getDate("fecha"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getCliente().setDireccion(rs.getString("direccion"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setTotalneto(rs.getDouble("totalneto"));
                    vta.setCierre(rs.getInt("cierre"));
                    lista.add(vta);
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean CerrarPreventa(int numeropreventa) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE preventa SET cierre=1 WHERE numero= ?");
        ps.setInt(1, numeropreventa);
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

    public ArrayList<preventa> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<preventa> lista = new ArrayList<preventa>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT numero,fecha,vencimiento,cliente,sucursal,moneda,comprobante,cotizacion,"
                    + "preventa.vendedor,caja,totalneto,preventa.observacion,cierre,clientes.ruc,clientes.direccion,"
                    + "vendedores.nombre AS nombrevendedor,clientes.nombre AS nombrecliente,"
                    + "comprobantes.nombre AS nombrecomprobante,preventa.observacion,"
                    + "preventa.tipo "
                    + "FROM preventa "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=preventa.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=preventa.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=preventa.vendedor "
                    + " WHERE preventa.fecha between ? AND ? "
                    + " ORDER BY preventa.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    preventa vta = new preventa();
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();

                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);

                    vta.setNumero(rs.getInt("numero"));
                    vta.setTipo(rs.getInt("tipo"));
                    vta.setFecha(rs.getDate("fecha"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getCliente().setDireccion(rs.getString("direccion"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));

                    vta.setObservacion(rs.getString("observacion"));

                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setTotalneto(rs.getDouble("totalneto"));
                    vta.setCierre(rs.getInt("cierre"));
                    lista.add(vta);
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean EliminarPreventa(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM preventa WHERE numero=?");
        ps.setInt(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void Imprimir(int id, Conexion conexion) throws SQLException {
        con = new Conexion();
        st = con.conectar();

        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            parameters.put("cReferencia", id);

            JasperReport jr = null;

            URL url = getClass().getClassLoader().getResource("Reports/preventa_vemay.jasper");
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos
            masterPrint = JasperFillManager.fillReport(jr, parameters, st.getConnection());

            //    JasperViewer ventana = new JasperViewer(masterPrint, false);
//            ventana.setTitle("Vista Previa");
//            ventana.setVisible(true);
            JasperPrintManager.printReport(masterPrint, false);

        } catch (Exception e) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            JOptionPane.showMessageDialog(null, "No puede emitirse el Reporte");
            System.out.println(e);
        }        // TODO add your handling code here:
    }

    public ArrayList<preventa> MostrarxFechaFerremax(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<preventa> lista = new ArrayList<preventa>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT numero,fecha,vencimiento,cliente,sucursal,moneda,comprobante,cotizacion,"
                    + "preventa.vendedor,caja,totalneto,preventa.observacion,cierre,clientes.ruc,clientes.direccion,"
                    + "vendedores.nombre AS nombrevendedor,clientes.nombre AS nombrecliente,firma,totaldescuento,"
                    + "comprobantes.nombre AS nombrecomprobante,preventa.observacion,"
                    + "preventa.tipo "
                    + "FROM preventa "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=preventa.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=preventa.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=preventa.vendedor "
                    + " WHERE preventa.fecha between ? AND ? "
                    + " AND preventa.cierre=0 and preventa.tipo>1 "
                    + " ORDER BY preventa.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    preventa vta = new preventa();
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();

                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);

                    vta.setNumero(rs.getInt("numero"));
                    vta.setTipo(rs.getInt("tipo"));
                    vta.setFecha(rs.getDate("fecha"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getCliente().setDireccion(rs.getString("direccion"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));

                    vta.setObservacion(rs.getString("observacion"));

                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setTotalneto(rs.getDouble("totalneto"));
                    vta.setTotaldescuento(rs.getDouble("totaldescuento"));
                    vta.setFirma(rs.getString("firma"));
                    vta.setCierre(rs.getInt("cierre"));
                    lista.add(vta);
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public ArrayList<preventa> TodosFerremax() throws SQLException {
        ArrayList<preventa> lista = new ArrayList<preventa>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT numero,fecha,vencimiento,"
                    + "cliente,sucursal,moneda,comprobante,cotizacion,"
                    + "preventa.vendedor,caja,totalneto,"
                    + "preventa.observacion,cierre,clientes.ruc,clientes.direccion,"
                    + "vendedores.nombre AS nombrevendedor,"
                    + "clientes.nombre AS nombrecliente,preventa.cuotas,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "obras.nombre as nombreobra,totaldescuento,preventa.firma,"
                    + "preventa.caja "
                    + "FROM preventa "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=preventa.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=preventa.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=preventa.vendedor "
                    + "LEFT JOIN obras "
                    + "ON obras.codigo=preventa.cuotas "
                    + "WHERE preventa.cierre<>1 AND preventa.tipo=1 AND preventa.totalneto<>0 "
                    + "ORDER BY preventa.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    preventa vta = new preventa();
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();

                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);

                    vta.setNumero(rs.getInt("numero"));
                    vta.setFecha(rs.getDate("fecha"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));
                    vta.getCliente().setRuc(rs.getString("ruc"));
                    vta.getCliente().setDireccion(rs.getString("direccion"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setTotalneto(rs.getDouble("totalneto"));
                    vta.setCuotas(rs.getInt("cuotas"));
                    vta.setCaja(rs.getInt("caja"));
                    vta.setTotaldescuento(rs.getDouble("totaldescuento"));
                    vta.setFirma(rs.getString("firma"));
                    if (rs.getString("nombreobra") == null) {
                        vta.setNombreobra("SD");
                    } else {
                        vta.setNombreobra(rs.getString("nombreobra"));
                    }

                    vta.setCierre(rs.getInt("cierre"));
                    lista.add(vta);
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public preventa InsertarPreVentaFerremax(String token, preventa v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);

        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO preventa (fecha,vencimiento,"
                + "sucursal,vendedor,comprobante,cliente,"
                + "totalneto,moneda,observacion,tipo,cierre,cuotas,totaldescuento,caja) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, v.getFecha());
        ps.setDate(2, v.getVencimiento());
        ps.setInt(3, v.getSucursal().getCodigo());
        ps.setInt(4, v.getVendedor().getCodigo());
        ps.setInt(5, v.getComprobante().getCodigo());
        ps.setInt(6, v.getCliente().getCodigo());
        ps.setDouble(7, v.getTotalneto());
        ps.setInt(8, v.getMoneda());
        ps.setString(9, v.getObservacion());
        ps.setInt(10, v.getTipo());
        ps.setInt(11, v.getCierre());
        ps.setInt(12, v.getCuotas());
        ps.setDouble(13, v.getTotaldescuento());
        ps.setInt(14, v.getCaja());
        if (Config.cToken == token) {
            ps.executeUpdate();
            ResultSet keyset = ps.getGeneratedKeys();
            if (keyset.next()) {
                id = keyset.getInt(1);
                guardado = guardarDetalle(id, detalle, con);
                if (guardado) {
                    try {
                        conn.commit();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "N° de Preventa Generado " + id);
                    }
                } else {
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Falla al Intengar Guardar Preventa" + id);
                    }
                }
            }
        } else {
            System.out.println("USUARIO NO AUTORIZADO");
        }
        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public preventa buscarIdFerremax(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        preventa vta = new preventa();

        try {

            String sql = "SELECT numero,fecha,vencimiento,cliente,sucursal,moneda,comprobante,cotizacion,"
                    + "preventa.vendedor,caja,totalneto,cierre,preventa.tipo,cuotas,"
                    + "vendedores.nombre AS nombrevendedor,clientes.nombre AS nombrecliente,"
                    + "comprobantes.nombre AS nombrecomprobante,preventa.vencimiento, "
                    + "sucursales.nombre as nombresucursal,preventa.observacion,obras.nombre as nombreobra, "
                    + "preventa.caja, cajas.nombre as nombrecaja "
                    + "FROM preventa "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=preventa.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=preventa.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=preventa.vendedor "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=preventa.sucursal "
                    + "LEFT JOIN obras "
                    + "ON obras.codigo=preventa.cuotas "
                    + "LEFT JOIN cajas "
                    + "ON cajas.codigo=preventa.caja "
                    + "WHERE preventa.numero=? "
                    + "ORDER BY preventa.numero  ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();
                    sucursal suc = new sucursal();

                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);
                    vta.setSucursal(suc);

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.setTipo(rs.getInt("tipo"));

                    vta.setNumero(rs.getInt("numero"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.setVencimiento(rs.getDate("vencimiento"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.setCaja(rs.getInt("caja"));
                    vta.setNombrecaja(rs.getString("nombrecaja"));
                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setTotalneto(rs.getDouble("totalneto"));
                    vta.setCierre(rs.getInt("cierre"));
                    vta.setCuotas(rs.getInt("cuotas"));
                    vta.setNombreobra(rs.getString("nombreobra"));
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

    public preventa buscarPedidoVemay(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        preventa vta = new preventa();

        try {

            String sql = "SELECT numero,fecha,vencimiento,cliente,sucursal,"
                    + "moneda,comprobante,cotizacion,"
                    + "preventa.vendedor,caja,totalneto,cierre,preventa.tipo,"
                    + "vendedores.nombre AS nombrevendedor,clientes.nombre AS nombrecliente,"
                    + "comprobantes.nombre AS nombrecomprobante,preventa.vencimiento, "
                    + "sucursales.nombre as nombresucursal,preventa.observacion, "
                    + "preventa.destinatario,"
                    + "preventa.direccion_entrega,"
                    + "preventa.telefono,preventa.referencia_lugar,"
                    + "preventa.mensaje,preventa.firma,preventa.horaentrega "
                    + "FROM preventa "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=preventa.cliente "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=preventa.comprobante "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=preventa.vendedor "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=preventa.sucursal "
                    + "WHERE preventa.numero=? "
                    + "ORDER BY preventa.numero  ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    vendedor vendedor = new vendedor();
                    sucursal suc = new sucursal();

                    vta.setCliente(cliente);
                    vta.setComprobante(comprobante);
                    vta.setVendedor(vendedor);
                    vta.setSucursal(suc);

                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.setTipo(rs.getInt("tipo"));

                    vta.setNumero(rs.getInt("numero"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.setVencimiento(rs.getDate("vencimiento"));

                    vta.getCliente().setCodigo(rs.getInt("cliente"));
                    vta.getCliente().setNombre(rs.getString("nombrecliente"));

                    vta.getVendedor().setCodigo(rs.getInt("vendedor"));
                    vta.getVendedor().setNombre(rs.getString("nombrevendedor"));

                    vta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    vta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    vta.setObservacion(rs.getString("observacion"));
                    vta.setVencimiento(rs.getDate("vencimiento"));
                    vta.setTotalneto(rs.getDouble("totalneto"));
                    vta.setDestinatario(rs.getString("destinatario"));
                    vta.setDireccion_entrega(rs.getString("direccion_entrega"));
                    vta.setTelefono(rs.getString("telefono"));
                    vta.setReferencia_lugar(rs.getString("referencia_lugar"));
                    vta.setMensaje(rs.getString("mensaje"));
                    vta.setFirma(rs.getString("firma"));
                    vta.setHoraentrega(rs.getString("horaentrega"));
                    vta.setCierre(rs.getInt("cierre"));
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

    public preventa InsertarPreVentaPedido(String token, preventa v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO preventa (fecha,sucursal,"
                + "vendedor,comprobante,"
                + "cliente,totalneto,moneda,"
                + "observacion,destinatario,"
                + "direccion_entrega,"
                + "telefono,referencia_lugar,"
                + "mensaje,vencimiento,tipo,"
                + "firma,horaentrega,caja) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, v.getFecha());
        ps.setInt(2, v.getSucursal().getCodigo());
        ps.setInt(3, v.getVendedor().getCodigo());
        ps.setInt(4, v.getComprobante().getCodigo());
        ps.setInt(5, v.getCliente().getCodigo());
        ps.setDouble(6, v.getTotalneto());
        ps.setInt(7, v.getMoneda());
        ps.setString(8, v.getObservacion());
        ps.setString(9, v.getDestinatario());
        ps.setString(10, v.getDireccion_entrega());
        ps.setString(11, v.getTelefono());
        ps.setString(12, v.getReferencia_lugar());
        ps.setString(13, v.getMensaje());
        ps.setDate(14, v.getVencimiento());
        ps.setInt(15, v.getTipo());
        ps.setString(16, v.getFirma());
        ps.setString(17, v.getHoraentrega());
        ps.setInt(18, v.getCaja());
        if (Config.cToken == token) {
            ps.executeUpdate();
            ResultSet keyset = ps.getGeneratedKeys();
            if (keyset.next()) {
                id = keyset.getInt(1);
                guardado = guardarDetalle(id, detalle, con);

                Object[] opciones = {"  Si   ", "   No   "};
                int ret = JOptionPane.showOptionDialog(null, "Desea Imprimir el Documento ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
                if (ret == 0) {
                    Imprimir(id, con);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "USUARIO NO AUTORIZADO ");
        }
        st.close();
        ps.close();
        conn.close();
        JOptionPane.showMessageDialog(null, "N° de Preventa Generado " + id);
        return v;
    }

    public boolean ActualizarPedidoVemay(preventa v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        boolean guardado = false;
        int id = 0;

        ps = st.getConnection().prepareStatement("UPDATE preventa "
                + "SET fecha=?,vencimiento=?,"
                + "sucursal=?,vendedor=?,"
                + "comprobante=?,cliente=?,"
                + "totalneto=?,moneda=?,"
                + "observacion=?,tipo=?"
                + "destinatario=?,direccion_entrega=?,"
                + "telefono=?,referencia_lugar=?,"
                + "mensaje=?,firma=?,"
                + "horaentrega=? "
                + " WHERE numero=" + v.getNumero());
        ps.setDate(1, v.getFecha());
        ps.setDate(2, v.getVencimiento());
        ps.setInt(3, v.getSucursal().getCodigo());
        ps.setInt(4, v.getVendedor().getCodigo());
        ps.setInt(5, v.getComprobante().getCodigo());
        ps.setInt(6, v.getCliente().getCodigo());
        ps.setDouble(7, v.getTotalneto());
        ps.setInt(8, v.getMoneda());
        ps.setString(9, v.getObservacion());
        ps.setInt(10, v.getTipo());
        ps.setString(11, v.getDestinatario());
        ps.setString(12, v.getDireccion_entrega());
        ps.setString(13, v.getTelefono());
        ps.setString(14, v.getReferencia_lugar());
        ps.setString(15, v.getMensaje());
        ps.setString(16, v.getFirma());
        ps.setString(17, v.getHoraentrega());
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

    public preventa InsertarPreVentaFerremaxOnline(preventa v) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO preventa (fecha,vencimiento,"
                + "sucursal,vendedor,comprobante,cliente,"
                + "totalneto,moneda,observacion,tipo,cierre,cuotas,totaldescuento,caja) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, v.getFecha());
        ps.setDate(2, v.getVencimiento());
        ps.setInt(3, v.getSucursal().getCodigo());
        ps.setInt(4, v.getVendedor().getCodigo());
        ps.setInt(5, v.getComprobante().getCodigo());
        ps.setInt(6, v.getCliente().getCodigo());
        ps.setDouble(7, v.getTotalneto());
        ps.setInt(8, v.getMoneda());
        ps.setString(9, v.getObservacion());
        ps.setInt(10, v.getTipo());
        ps.setInt(11, v.getCierre());
        ps.setInt(12, v.getCuotas());
        ps.setDouble(13, v.getTotaldescuento());
        ps.setInt(14, v.getCaja());
        ps.executeUpdate();
        try {
            ResultSet keyset = ps.getGeneratedKeys();
            if (keyset.next()) {
                id = keyset.getInt(1);
            }
            v.setNumero(Double.valueOf(id));
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public boolean ActualizarPreventaFerremaxOnline(preventa v) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        boolean guardado = false;
        int id = 0;

        ps = st.getConnection().prepareStatement("UPDATE preventa SET fecha=?,vencimiento=?,"
                + "sucursal=?,vendedor=?,comprobante=?,cliente=?,"
                + "totalneto=?,moneda=?,observacion=?,tipo=?,cuotas=?,caja=? WHERE numero=" + v.getNumero());
        ps.setDate(1, v.getFecha());
        ps.setDate(2, v.getVencimiento());
        ps.setInt(3, v.getSucursal().getCodigo());
        ps.setInt(4, v.getVendedor().getCodigo());
        ps.setInt(5, v.getComprobante().getCodigo());
        ps.setInt(6, v.getCliente().getCodigo());
        ps.setDouble(7, v.getTotalneto());
        ps.setInt(8, v.getMoneda());
        ps.setString(9, v.getObservacion());
        ps.setInt(10, v.getTipo());
        ps.setInt(11, v.getCuotas());
        ps.setInt(12, v.getCaja());
        id = (int) v.getNumero();
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
    }


}

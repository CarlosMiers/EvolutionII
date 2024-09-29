/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.sucursal;
import Modelo.producto;
import Modelo.stock;
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

/**
 *
 * @author Pc_Server
 */
public class stockDAO {

    Conexion con, conex = null;
    Statement st, stk = null;

    public stock BuscarStockAgrupado(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        stock stk = new stock();
        String sql = "SELECT productos.nombre as nombreproducto,sucursales.nombre as nombresucursal,"
                + "SUM(stock.stock) as disponible "
                + " FROM stock "
                + " LEFT JOIN productos "
                + " ON productos.codigo=stock.producto "
                + " LEFT JOIN sucursales "
                + " ON sucursales.codigo=stock.sucursal "
                + " WHERE stock.producto='" + id + "'"
                + " GROUP BY stock.producto "
                + " ORDER BY stock.producto ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                producto prod = new producto();
                stk.setProducto(prod);
                stk.setStock(rs.getDouble("disponible"));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + stk.getProducto().getCodigo() + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return stk;
    }

    public ArrayList<stock> ActualizarStock(int nSuc) throws SQLException {
        ArrayList<stock> lista = new ArrayList<stock>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        int nsucursal = nSuc;
        String sql = "CREATE TEMPORARY TABLE InventarioProductos"
                + "(SELECT a.rubro AS codrubro,a.medida AS codunidad,unidades.nombre AS nombreunid,COALESCE(a.costo,0) AS costo,"
                + " COALESCE(a.precio_minimo,0) AS precio_minimo,a.stock,a.codigo,a.nombre,"
                + " COALESCE(c.compras,0) AS compras, COALESCE(v.ventas,0) AS ventas,"
                + " COALESCE(e.entradas,0) AS entradas,COALESCE(s.salidas,0) AS salidas,"
                + " COALESCE(te.transEntrada,0) AS transEntrada,"
                + " COALESCE(ts.transSalida,0) AS  transSalida"
                + " FROM productos a"
                + " LEFT JOIN unidades"
                + " ON unidades.codigo=a.medida"
                + " LEFT JOIN (SELECT cabecera_compras.fecha,detalle_compras.codprod AS codigo, SUM(detalle_compras.cantidad) AS compras"
                + " FROM detalle_compras"
                + " INNER JOIN cabecera_compras"
                + " ON cabecera_compras.creferencia=detalle_compras.dreferencia"
                + " WHERE cabecera_compras.sucursal=" + nSuc
                + " GROUP BY detalle_compras.codprod) c ON c.codigo=a.codigo"
                + " LEFT JOIN (SELECT cabecera_ventas.fecha,detalle_ventas.codprod AS codigo, SUM(detalle_ventas.cantidad*-1) AS ventas"
                + " FROM detalle_ventas"
                + " INNER JOIN cabecera_ventas"
                + " ON cabecera_ventas.creferencia=detalle_ventas.dreferencia"
                + " WHERE cabecera_ventas.sucursal=" + nSuc
                + " GROUP BY detalle_ventas.codprod) v ON v.codigo=a.codigo"
                + " LEFT JOIN (SELECT ajuste_mercaderias.fecha,detalle_ajuste_mercaderias.producto AS codigo, SUM(detalle_ajuste_mercaderias.cantidad) AS entradas"
                + " FROM detalle_ajuste_mercaderias"
                + " INNER JOIN ajuste_mercaderias"
                + " ON ajuste_mercaderias.idreferencia=detalle_ajuste_mercaderias.dreferencia"
                + " WHERE ajuste_mercaderias.sucursal=" + nSuc
                + " GROUP BY detalle_ajuste_mercaderias.producto) e ON e.codigo=a.codigo"
                + " LEFT JOIN (SELECT cabecera_salida_mercaderias.fecha,detalle_salida_mercaderias.producto AS codigo, SUM(detalle_salida_mercaderias.cantidad*-1) AS salidas"
                + " FROM detalle_salida_mercaderias"
                + " INNER JOIN cabecera_salida_mercaderias"
                + " ON cabecera_salida_mercaderias.idreferencia=detalle_salida_mercaderias.dreferencia"
                + " WHERE cabecera_salida_mercaderias.sucursal=+" + nSuc
                + " GROUP BY detalle_salida_mercaderias.producto)s ON s.codigo=a.codigo"
                + " LEFT JOIN (SELECT transferencias.fecha,detalle_transferencias.producto AS codigo, SUM(detalle_transferencias.cantidad) AS TransEntrada"
                + " FROM detalle_transferencias"
                + " INNER JOIN transferencias"
                + " ON transferencias.idtransferencia=detalle_transferencias.dreferencia"
                + " WHERE transferencias.destino=" + nSuc
                + " GROUP BY detalle_transferencias.producto)te ON te.codigo=a.codigo"
                + " LEFT JOIN (SELECT transferencias.fecha,detalle_transferencias.producto AS codigo, SUM(detalle_transferencias.cantidad*-1) AS TransSalida"
                + " FROM detalle_transferencias"
                + " INNER JOIN transferencias"
                + " ON transferencias.idtransferencia=detalle_transferencias.dreferencia"
                + " WHERE transferencias.origen=" + nSuc
                + " GROUP BY detalle_transferencias.producto)ts ON ts.codigo=a.codigo"
                + " WHERE a.estado=1 "
                + " GROUP BY a.codigo"
                + " ORDER BY a.nombre)";

        PreparedStatement psInventario = conn.prepareStatement(sql);
        psInventario.executeUpdate(sql);

        String sqlGrabar = "INSERT INTO stock(producto,sucursal,stock) "
                + "SELECT codigo," + nSuc + ",(compras + entradas + transEntrada) - (ABS(ventas)+ ABS(salidas)+ABS(transSalida)) "
                + " FROM InventarioProductos";

        PreparedStatement psStockdatos = conn.prepareStatement(sqlGrabar);
        try {
            psStockdatos.executeUpdate(sqlGrabar);
            System.out.println("-->Grabando "  );

        } catch (SQLException ex) {
            System.out.println("-->ERRORES STOCK " + ex.getLocalizedMessage());
        }

        st.close();
        conn.close();
        return lista;
    }

    public boolean ActualizarStockFicha() throws SQLException {
        conex = new Conexion();
        stk = conex.conectar();
        Connection conne = stk.getConnection();
        PreparedStatement ps = null;
        int rowsUpdated = 0;
        ps = stk.getConnection().prepareStatement("UPDATE productos SET stock= "
                + "(SELECT SUM(stock) AS inventario "
                + " FROM stock "
                + "WHERE productos.codigo=stock.producto "
                + " AND stock.stock<>0 "
                + " GROUP BY stock.producto)");
        try {
            rowsUpdated = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> ERRORES PRODUCTOS " + ex.getLocalizedMessage());
        }
        stk.close();
        ps.close();
        conne.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean CerarFicha() throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE productos SET stock=0");
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

    public boolean eliminarStock(int nSuc) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM stock WHERE sucursal=" + nSuc);
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

    public boolean guardarStock(String detalle) throws SQLException {
        boolean guardacuota = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectacuota = st.getConnection();
        conectacuota.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalle);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO stock ("
                            + "sucursal,"
                            + "producto,"
                            + "stock)"
                            + "VALUES(?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("sucursal").getAsString());
                        ps.setString(2, obj.get("producto").getAsString());
                        ps.setString(3, obj.get("stock").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardacuota = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardacuota = false;
                    break;
                }
            }

            if (guardacuota) {
                try {
                    conectacuota.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
                    conectacuota.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardacuota = false;
        }
        st.close();
        conectacuota.close();
        return guardacuota;
    }

    public ArrayList<stock> BuscarStockProducto(String id) throws SQLException {
        ArrayList<stock> lista = new ArrayList<stock>();

        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        String sql = "SELECT stock.producto,sucursales.nombre as nombresucursal,"
                + "stock.stock "
                + " FROM stock "
                + " LEFT JOIN sucursales "
                + " ON sucursales.codigo=stock.sucursal "
                + " WHERE stock.producto='" + id + "'"
                + " ORDER BY stock.sucursal ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sucursal suc = new sucursal();
                stock stk = new stock();
                stk.setSucursal(suc);
                stk.setStock(rs.getDouble("stock"));
                stk.getSucursal().setNombre(rs.getString("nombresucursal"));
                lista.add(stk);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public stock StockxProducto(String id) throws SQLException {

        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        stock stk = new stock();

        String sql = "SELECT stock.producto,"
                + "SUM(stock.stock) AS totalstock "
                + " FROM stock "
                + " WHERE stock.producto='" + id + "'"
                + " GROUP BY producto "
                + " ORDER BY producto ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                producto prod = new producto();
                stk.setProducto(prod);
                stk.getProducto().setCodigo(rs.getString("producto"));
                stk.setStock(rs.getDouble("totalstock"));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return stk;
    }

    public stock insertarStockCero(stock stk) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO stock (producto,sucursal,stock) VALUES (?,?,?)");
        ps.setString(1, stk.getProducto().getCodigo());
        ps.setInt(2, stk.getSucursal().getCodigo());
        ps.setDouble(3, stk.getStock());
        ps.executeUpdate();
        st.close();
        ps.close();
        return stk;
    }

    public boolean CerarNulos() throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE productos SET stock=0 WHERE ISNULL(stock)");
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

    public ArrayList<stock> BuscarStockProductoSucursal(int sucursal,String id) throws SQLException {
        ArrayList<stock> lista = new ArrayList<stock>();

        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        String sql = "SELECT stock.producto,sucursales.nombre as nombresucursal,"
                + "stock.stock "
                + " FROM stock "
                + " LEFT JOIN sucursales "
                + " ON sucursales.codigo=stock.sucursal "
                + " WHERE stock.sucursal= "+sucursal
                +" AND stock.producto='" + id + "'"
                + " ORDER BY stock.sucursal ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sucursal suc = new sucursal();
                stock stk = new stock();
                stk.setSucursal(suc);
                stk.setStock(rs.getDouble("stock"));
                stk.getSucursal().setNombre(rs.getString("nombresucursal"));
                lista.add(stk);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }
    
    
}

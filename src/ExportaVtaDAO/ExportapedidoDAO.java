/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import DAO.*;
import Conexion.Conexion;
import Conexion.ConexionEspejo;
import Modelo.sucursal;
import Modelo.pedidos;
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
 * @author Usuario
 */
public class ExportapedidoDAO {

    ConexionEspejo conEsp = null;
    Statement stb = null;
    Connection connEs = null;

    String ip2 = "45.180.183.194";

    public ArrayList<pedidos> MostrarEspejoxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<pedidos> listEspejoa = new ArrayList<pedidos>();
        conEsp = new ConexionEspejo();
        stb = conEsp.conectarEspejo(ip2);
        try {

            String sql = "SELECT idpedido,fecha,sucursal,totales,"
                    + "sucursales.nombre AS nombresucursal,cierre "
                    + "FROM pedidos  "
                    + "LEFT JOIN sucursales "
                    + " ON sucursales.codigo=pedidos.sucursal "
                    + " WHERE pedidos.fecha between ? AND ? "
                    + " ORDER BY pedidos.idpedido ";

            try (PreparedStatement ps = stb.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    pedidos tr = new pedidos();
                    sucursal sucursal = new sucursal();
                    tr.setIdpedido(rs.getInt("idpedido"));
                    tr.setFecha(rs.getDate("fecha"));
                    tr.setSucursal(sucursal);
                    tr.setCierre(rs.getInt("cierre"));
                    tr.getSucursal().setCodigo(rs.getInt("sucursal"));
                    tr.getSucursal().setNombre(rs.getString("nombresucursal"));
                    tr.setTotales(rs.getDouble("totales"));
                    listEspejoa.add(tr);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        stb.close();
        return listEspejoa;
    }

    public pedidos BuscarId(Double id) throws SQLException {
        conEsp = new ConexionEspejo();
        stb = conEsp.conectarEspejo(ip2);
        pedidos tr = new pedidos();
        sucursal sucursal = new sucursal();
        try {

            String sql = "SELECT idpedido,fecha,sucursal,totales,"
                    + "sucursales.nombre AS nombresucursal,cierre "
                    + "FROM pedidos  "
                    + "LEFT JOIN sucursales "
                    + " ON sucursales.codigo=pedidos.sucursal "
                    + " WHERE pedidos.idpedido= ? "
                    + " ORDER BY pedidos.idpedido ";

            try (PreparedStatement ps = stb.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    tr.setIdpedido(rs.getInt("idpedido"));
                    tr.setFecha(rs.getDate("fecha"));
                    tr.setSucursal(sucursal);
                    tr.setCierre(rs.getInt("cierre"));
                    tr.getSucursal().setCodigo(rs.getInt("sucursal"));
                    tr.getSucursal().setNombre(rs.getString("nombresucursal"));
                    tr.setTotales(rs.getDouble("totales"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        stb.close();
        return tr;
    }

    public pedidos insertarmercaderia(pedidos ocr, String detalle) throws SQLException {
        conEsp = new ConexionEspejo();
        stb = conEsp.conectarEspejo(ip2);
        Connection connEs = stb.getConnection();
        PreparedStatement ps = null;
        ResultSet keyset = null;
        boolean guardado = false;
        int id = 0;

        try {
            conEsp = new ConexionEspejo();
            connEs = conEsp.conectarEspejo(ip2).getConnection();
            String sql = "INSERT INTO pedidos (fecha, sucursal, totales) VALUES (?, ?, ?)";
            ps = connEs.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setDate(1, ocr.getFecha());
            ps.setInt(2, ocr.getSucursal().getCodigo());
            ps.setDouble(3, ocr.getTotales());

            ps.executeUpdate();

            keyset = ps.getGeneratedKeys();
            if (keyset.next()) {
                id = keyset.getInt(1);
                guardado = guardarDetalle(id, detalle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según sea necesario
        } finally {
            // Cerrar todos los recursos en el bloque finally
            try {
                if (keyset != null) {
                    keyset.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connEs != null) {
                    connEs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ocr;
    }

    public boolean actualizarAjusteMercaderia(pedidos aj, String detalle) throws SQLException {
        conEsp = new ConexionEspejo();
        stb = conEsp.conectarEspejo(ip2);
        Connection connEs = stb.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = stb.getConnection().prepareStatement("UPDATE pedidos SET fecha=?,"
                + "sucursal=?,totales=? WHERE idpedido=" + aj.getIdpedido());
        ps.setDate(1, aj.getFecha());
        ps.setInt(2, aj.getSucursal().getCodigo());
        ps.setDouble(3, aj.getTotales());

        int rowsUpdated = ps.executeUpdate();
        guardado = guardarDetalle(id, detalle);
        stb.close();
        ps.close();
        connEs.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
    }

    public boolean guardarDetalle(int id, String detalle) throws SQLException {
        boolean guardado = true;
        Connection conn = stb.getConnection();
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
                    String sql = "insert into pedidos_detalle("
                            + "idpedido,"
                            + "producto,"
                            + "cantidad,"
                            + "costo,"
                            + "total"
                            + ") "
                            + "values(?,?,?,?,?)";
                    try (PreparedStatement ps = stb.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("producto").getAsString());
                        ps.setString(3, obj.get("cantidad").getAsString());
                        ps.setString(4, obj.get("costo").getAsString());
                        ps.setString(5, obj.get("total").getAsString());
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
        stb.close();
        conn.close();
        return guardado;
    }
    
    
        public boolean borrarPedidos(Double id) throws SQLException {
        conEsp = new ConexionEspejo();
        stb = conEsp.conectarEspejo(ip2);
        Connection connEs = stb.getConnection();
        PreparedStatement ps = null;

        ps = stb.getConnection().prepareStatement("DELETE FROM pedidos_detalle WHERE idpedido=?");
        ps.setDouble(1, id);
        int rowsUpdated = ps.executeUpdate();
        stb.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }


}

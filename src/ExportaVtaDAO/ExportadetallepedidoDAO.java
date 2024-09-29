/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import Conexion.ConexionEspejo;
import Modelo.pedidos_detalle;
import Modelo.producto;
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
public class ExportadetallepedidoDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.194";

    public ArrayList<pedidos_detalle> MostrarDetalle(Double id) throws SQLException {
        ArrayList<pedidos_detalle> lista = new ArrayList<pedidos_detalle>();
        conEsp = new ConexionEspejo();
        stEspejo = conEsp.conectarEspejo(ip2);
        Connection conne = stEspejo.getConnection();
        try {
            String sql = "SELECT idpedido,item,producto,cantidad,costo,total "
                    + "FROM pedidos_detalle "
                    + "INNER JOIN pedidos "
                    + "ON pedidos.idpedido=pedidos_detalle.idpedido "
                    + "WHERE pedidos.idpedido= ? "
                    + " ORDER BY pedidos.idpedido";

            try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    pedidos_detalle dt = new pedidos_detalle();
                    producto prod = new producto();
                    dt.setProducto(prod);
                    dt.getProducto().setCodigo(rs.getString("producto"));
                    dt.setIdpedido(rs.getDouble("idpedido"));
                    dt.setCantidad(rs.getDouble("cantidad"));
                    dt.setCosto(rs.getDouble("costo"));
                    dt.setTotal(rs.getDouble("total"));
                    lista.add(dt);
                }
                ps.close();
                stEspejo.close();
                conne.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> detalle de solicitud " + ex.getLocalizedMessage());
        }
        return lista;
    }

    
    public boolean borrarDetallePedido(Double id) throws SQLException {
        conEsp = new ConexionEspejo();
        stEspejo = conEsp.conectarEspejo(ip2);
        Connection conne = stEspejo.getConnection();
        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("DELETE FROM pedidos_detalle WHERE idpedido=?");
        ps.setDouble(1, id);
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }    
    

}

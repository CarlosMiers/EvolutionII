/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import DAO.*;
import Conexion.Conexion;
import Modelo.detalle_transferencia;
import Modelo.producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Exportadetalle_transferenciaDAO {
    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_transferencia> MostrarDetalle(String id) throws SQLException {
        ArrayList<detalle_transferencia> lista = new ArrayList<detalle_transferencia>();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT detalle_transferencias.dreferencia,detalle_transferencias.producto,"
                    + "detalle_transferencias.cantidad,detalle_transferencias.costo,productos.nombre AS nombreproducto  "
                    + "FROM detalle_transferencias  "
                    + "INNER JOIN productos "
                    + "ON productos.codigo=detalle_transferencias.producto "
                    + "WHERE detalle_transferencias.dreferencia= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_transferencia dt = new detalle_transferencia();
                    producto prod = new producto();
                    dt.setProducto(prod);
                    dt.getProducto().setCodigo(rs.getString("producto"));
                    dt.getProducto().setNombre(rs.getString("nombreproducto"));
                    dt.setDreferencia(rs.getString("dreferencia"));
                    dt.setCantidad(rs.getBigDecimal("cantidad"));
                    dt.setCosto(rs.getBigDecimal("costo"));
                    lista.add(dt);
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarDetalleSalida(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_transferencias WHERE dreferencia=?");
        ps.setString(1, referencia);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
}

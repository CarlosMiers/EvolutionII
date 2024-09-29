/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_compras_gastos;
import Modelo.plan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class detalle_compras_gastosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_compras_gastos> MostrarDetalle(String id) throws SQLException {
        ArrayList<detalle_compras_gastos> lista = new ArrayList<detalle_compras_gastos>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT dreferencia,descripcion,cantidad,prcosto,"
                    + "monto,impiva,porcentaje,idcta,plan.nombre AS nombrecuenta "
                    + "FROM detalle_compras_gastos "
                    + "LEFT JOIN plan "
                    + "ON plan.codigo=detalle_compras_gastos.idcta "
                    + "WHERE detalle_compras_gastoss.dreferencia= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_compras_gastos dt = new detalle_compras_gastos();
                    plan p = new plan();
                    dt.setIdcuenta(p);
                    dt.getIdcuenta().setCodigo(rs.getString("idcta"));
                    dt.getIdcuenta().setNombre(rs.getString("nombrecuenta"));
                    dt.setDreferencia(rs.getString("dreferencia"));
                    dt.setCantidad(rs.getDouble("cantidad"));
                    dt.setPrcosto(rs.getDouble("prcosto"));
                    dt.setPorcentaje(rs.getDouble("porcentaje"));
                    dt.setMonto(rs.getDouble("monto"));
                    dt.setImpiva(rs.getDouble("impiva"));
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

    public boolean borrarDetalleGastos(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_compras_gastos WHERE dreferencia=?");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_compra_gastos;
import Modelo.plan;
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
public class detalle_compra_gastosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_compra_gastos> MostrarxReferencia(String cReferencia) throws SQLException {
        ArrayList<detalle_compra_gastos> lista = new ArrayList<detalle_compra_gastos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT dreferencia,descripcion,cantidad,prcosto,"
                    + "monto,impiva,porcentaje,idcta,itemid,plan.nombre AS nombrecuenta "
                    + "FROM detalle_compras_gastos "
                    + "LEFT JOIN plan "
                    + "ON plan.codigo=detalle_compras_gastos.idcta "
                    + "WHERE dreferencia='" + cReferencia + "'";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    plan pl = new plan();
                    detalle_compra_gastos ot = new detalle_compra_gastos();

                    ot.setIdcta(pl);
                    ot.getIdcta().setCodigo(rs.getString("idcta"));
                    ot.getIdcta().setNombre(rs.getString("nombrecuenta"));

                    ot.setItemid(rs.getDouble("itemid"));
                    ot.setDreferencia(rs.getString("dreferencia"));
                    ot.setDescripcion(rs.getString("descripcion"));
                    ot.setCantidad(rs.getDouble("cantidad"));
                    ot.setPrcosto(rs.getDouble("prcosto"));
                    ot.setMonto(rs.getDouble("monto"));
                    ot.setImpiva(rs.getDouble("impiva"));
                    ot.setPorcentaje(rs.getDouble("porcentaje"));

                    lista.add(ot);
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

    public detalle_compra_gastos buscarId(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        detalle_compra_gastos ot = new detalle_compra_gastos();
        try {
            String sql = "SELECT dreferencia,descripcion,cantidad,prcosto,"
                    + "monto,impiva,porcentaje,idcta,itemid,plan.nombre AS nombrecuenta "
                    + "FROM detalle_compras_gastos "
                    + "LEFT JOIN plan "
                    + "ON plan.codigo=detalle_compras_gastos.idcta "
                    + "WHERE itemid=" + id;

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    plan pl = new plan();

                    ot.setIdcta(pl);
                    ot.getIdcta().setCodigo(rs.getString("idcta"));
                    ot.getIdcta().setNombre(rs.getString("nombrecuenta"));

                    ot.setItemid(rs.getDouble("itemid"));
                    ot.setDreferencia(rs.getString("dreferencia"));
                    ot.setDescripcion(rs.getString("descripcion"));
                    ot.setCantidad(rs.getDouble("cantidad"));
                    ot.setPrcosto(rs.getDouble("prcosto"));
                    ot.setMonto(rs.getDouble("monto"));
                    ot.setImpiva(rs.getDouble("impiva"));
                    ot.setPorcentaje(rs.getDouble("porcentaje"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return ot;
    }

    public detalle_compra_gastos insertarItem(detalle_compra_gastos ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO detalle_compras_gastos"
                + " (dreferencia,descripcion,cantidad,"
                + "prcosto,monto,impiva,porcentaje,idcta)"
                + " VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getDreferencia());
        ps.setString(2, ocr.getDescripcion());
        ps.setDouble(3, ocr.getCantidad());
        ps.setDouble(4, ocr.getPrcosto());
        ps.setDouble(5, ocr.getMonto());
        ps.setDouble(6, ocr.getImpiva());
        ps.setDouble(7, ocr.getPorcentaje());
        ps.setString(8, ocr.getIdcta().getCodigo());
        try {
            ps.executeUpdate();
            ResultSet keyset = ps.getGeneratedKeys();
        } catch (SQLException ex) {
            System.out.println("ERROR NUEVO GASTO--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public detalle_compra_gastos actualizarItem(detalle_compra_gastos ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE detalle_compras_gastos "
                + " SET dreferencia=?,descripcion=?,cantidad=?,"
                + "prcosto=?,monto=?,impiva=?,porcentaje=?,idcta=?"
                + " WHERE itemid=" + ocr.getItemid());
        ps.setString(1, ocr.getDreferencia());
        ps.setString(2, ocr.getDescripcion());
        ps.setDouble(3, ocr.getCantidad());
        ps.setDouble(4, ocr.getPrcosto());
        ps.setDouble(5, ocr.getMonto());
        ps.setDouble(6, ocr.getImpiva());
        ps.setDouble(7, ocr.getPorcentaje());
        ps.setString(8, ocr.getIdcta().getCodigo());
        try {
            ps.executeUpdate();
            int rowsUpdated = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ACTUALIZAR GASTOS--> " + ex.getLocalizedMessage());
        }

        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    
       public boolean EliminarItem(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_compras_gastos WHERE itemid=?");
        ps.setDouble(1, cod);
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

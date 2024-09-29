/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.centro_costo;
import Modelo.detalle_centro_costo;
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
public class detalle_centro_costoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_centro_costo> MostrarxReferencia(String cReferencia) throws SQLException {
        ArrayList<detalle_centro_costo> lista = new ArrayList<detalle_centro_costo>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT dreferencia,descripcion,cantidad,prcosto,"
                    + "monto,impiva,porcentaje,idcentro,itemid,centro_costo.nombre AS nombrecentro "
                    + "FROM detalle_centro_costo "
                    + "LEFT JOIN centro_costo "
                    + "ON centro_costo.codigo=detalle_centro_costo.idcentro "
                    + "WHERE dreferencia='" + cReferencia + "'";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    centro_costo pl = new centro_costo();
                    detalle_centro_costo ot = new detalle_centro_costo();

                    ot.setIdcentro(pl);
                    ot.getIdcentro().setCodigo(rs.getInt("idcentro"));
                    ot.getIdcentro().setNombre(rs.getString("nombrecentro"));

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

    public detalle_centro_costo buscarId(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        detalle_centro_costo ot = new detalle_centro_costo();
        try {
            String sql = "SELECT dreferencia,descripcion,cantidad,prcosto,"
                    + "monto,impiva,porcentaje,idcentro,itemid,centro_costo.nombre AS nombrecentro "
                    + "FROM detalle_centro_costo "
                    + "LEFT JOIN centro_costo "
                    + "ON centro_costo.codigo=detalle_centro_costo.idcentro "
                    + "WHERE itemid=" + id;

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    centro_costo pl = new centro_costo();

                    ot.setIdcentro(pl);
                    ot.getIdcentro().setCodigo(rs.getInt("idcentro"));
                    ot.getIdcentro().setNombre(rs.getString("nombrecentro"));

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

    public detalle_centro_costo insertarItem(detalle_centro_costo ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO detalle_centro_costo"
                + " (dreferencia,descripcion,cantidad,"
                + "prcosto,monto,impiva,porcentaje,idcentro,tipo)"
                + " VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getDreferencia());
        ps.setString(2, ocr.getDescripcion());
        ps.setDouble(3, ocr.getCantidad());
        ps.setDouble(4, ocr.getPrcosto());
        ps.setDouble(5, ocr.getMonto());
        ps.setDouble(6, ocr.getImpiva());
        ps.setDouble(7, ocr.getPorcentaje());
        ps.setInt(8, ocr.getIdcentro().getCodigo());
        ps.setInt(9, ocr.getTipo());
        
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

    public detalle_centro_costo actualizarItem(detalle_centro_costo ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE detalle_centro_costo "
                + " SET dreferencia=?,descripcion=?,cantidad=?,"
                + "prcosto=?,monto=?,impiva=?,porcentaje=?,idcentro=?"
                + " WHERE itemid=" + ocr.getItemid());
        ps.setString(1, ocr.getDreferencia());
        ps.setString(2, ocr.getDescripcion());
        ps.setDouble(3, ocr.getCantidad());
        ps.setDouble(4, ocr.getPrcosto());
        ps.setDouble(5, ocr.getMonto());
        ps.setDouble(6, ocr.getImpiva());
        ps.setDouble(7, ocr.getPorcentaje());
        ps.setInt(8, ocr.getIdcentro().getCodigo());
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

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_centro_costo WHERE itemid=?");
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

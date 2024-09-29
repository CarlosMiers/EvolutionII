/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_preventa;
import Modelo.producto;
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
public class detalle_preventaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_preventa> MostrarDetalle(int id) throws SQLException {
        ArrayList<detalle_preventa> lista = new ArrayList<detalle_preventa>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT iditem,iddetalle,codprod,cantidad,comentario,prcosto,precio,"
                    + "monto,impiva,porcentaje,"
                    + "productos.nombre AS nombreproducto "
                    + "FROM detalle_preventas "
                    + "INNER JOIN productos "
                    + "ON productos.codigo=detalle_preventas.codprod "
                    + "WHERE detalle_preventas.iddetalle= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_preventa dt = new detalle_preventa();
                    producto prod = new producto();
                    dt.setCodprod(prod);
                    dt.getCodprod().setCodigo(rs.getString("codprod"));
                    dt.getCodprod().setNombre(rs.getString("nombreproducto"));
                    dt.setIddetalle(rs.getInt("iddetalle"));
                    dt.setCantidad(rs.getDouble("cantidad"));
                    dt.setComentario(rs.getString("comentario"));
                    dt.setPrcosto(rs.getDouble("prcosto"));
                    dt.setPrecio(rs.getDouble("precio"));
                    dt.setMonto(rs.getDouble("monto"));
                    dt.setImpiva(rs.getDouble("impiva"));
                    dt.setIditem(rs.getDouble("iditem"));
                    dt.setPorcentaje(rs.getDouble("porcentaje"));
                    lista.add(dt);
                }
                rs.close();
                ps.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean EliminarDetallePreventa(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_preventas WHERE iddetalle=?");
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

    public detalle_preventa insertarItem(detalle_preventa ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO detalle_preventas "
                + " (iddetalle,codprod,cantidad,comentario,"
                + "prcosto,precio,monto,impiva,porcentaje)"
                + " VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, ocr.getIddetalle());
        ps.setString(2, ocr.getCodprod().getCodigo());
        ps.setDouble(3, ocr.getCantidad());
        ps.setString(4, ocr.getComentario());
        ps.setDouble(5, ocr.getPrcosto());
        ps.setDouble(6, ocr.getPrecio());
        ps.setDouble(7, ocr.getMonto());
        ps.setDouble(8, ocr.getImpiva());
        ps.setDouble(9, ocr.getPorcentaje());
        try {
            ps.executeUpdate();
            ResultSet keyset = ps.getGeneratedKeys();
        } catch (SQLException ex) {
            System.out.println("ERROR NUEVO ITEM--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public detalle_preventa actualizarItem(detalle_preventa ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE detalle_preventas "
                + " SET iddetalle=?,codprod=?,cantidad=?,"
                + "comentario=?,prcosto=?,precio=?,"
                + "monto=?,impiva=?,porcentaje=?,"
                + " WHERE itemid=" + ocr.getIditem());
        ps.setInt(1, ocr.getIddetalle());
        ps.setString(2, ocr.getCodprod().getCodigo());
        ps.setDouble(3, ocr.getCantidad());
        ps.setString(4, ocr.getComentario());
        ps.setDouble(5, ocr.getPrcosto());
        ps.setDouble(6, ocr.getPrecio());
        ps.setDouble(7, ocr.getMonto());
        ps.setDouble(8, ocr.getImpiva());
        ps.setDouble(9, ocr.getPorcentaje());
        try {
            ps.executeUpdate();
            int rowsUpdated = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ACTUALIZAR ITEM--> " + ex.getLocalizedMessage());
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

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_preventas WHERE iditem=?");
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

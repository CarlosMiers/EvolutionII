/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.caracteristicas_cargo;
import Modelo.cargo;
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
public class caracteristicas_cargoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<caracteristicas_cargo> MostrarxCargo(int id) throws SQLException {
        ArrayList<caracteristicas_cargo> lista = new ArrayList<caracteristicas_cargo>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT iditem,idcargo,descripcion,"
                    + "cargos.nombre AS nombrecargo "
                    + "FROM caracteristicas_cargo "
                    + "LEFT JOIN cargos "
                    + "ON cargos.codigo=caracteristicas_cargo.idcargo "
                    + "WHERE idcargo= " + id
                    + " ORDER BY iditem ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    caracteristicas_cargo cg = new caracteristicas_cargo();
                    cargo c = new cargo();
                    cg.setIdcargo(c);
                    cg.setIdtem(rs.getInt("iditem"));
                    cg.setDescripcion(rs.getString("descripcion"));
                    lista.add(cg);
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

    public caracteristicas_cargo buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        caracteristicas_cargo cg = new caracteristicas_cargo();
        cargo c = new cargo();

        try {
            String sql = "SELECT iditem,idcargo,descripcion,"
                    + "cargos.nombre AS nombrecargo "
                    + "FROM caracteristicas_cargo "
                    + "LEFT JOIN cargos "
                    + "ON cargos.codigo=caracteristicas_cargo.idcargo "
                    + "WHERE iditem= " + id
                    + " ORDER BY iditem ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cg.setIdcargo(c);
                    cg.setIdtem(rs.getInt("iditem"));
                    cg.setDescripcion(rs.getString("descripcion"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return cg;
    }

    public caracteristicas_cargo insertarItem(caracteristicas_cargo ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO caracteristicas_cargo "
                + " (idcargo,descripcion) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, ocr.getIdcargo().getCodigo());
        ps.setString(2, ocr.getDescripcion());
        try {
            ps.executeUpdate();
            ResultSet keyset = ps.getGeneratedKeys();
        } catch (SQLException ex) {
            System.out.println("ERROR NUEVO CARGO--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public caracteristicas_cargo actualizarItem(caracteristicas_cargo ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE caracteristicas_cargo "
                + " SET descripcion=?  WHERE iditem=" + ocr.getIdtem());
        ps.setString(1, ocr.getDescripcion());
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

    public boolean EliminarItem(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM caracteristicas_cargo WHERE iditem=?");
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

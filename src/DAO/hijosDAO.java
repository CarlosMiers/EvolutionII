/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.hijos;
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
public class hijosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<hijos> MostrarxReferencia(Integer empleado) throws SQLException {
        ArrayList<hijos> lista = new ArrayList<hijos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT iditem,id_empleado,nombre,fecha_nacimiento,"
                    + "edad,sexo,bonificacion "
                    + " FROM hijos "
                    + " WHERE id_empleado=" + empleado;

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    hijos h = new hijos();

                    h.setIditem(rs.getDouble("iditem"));
                    h.setSexo(rs.getInt("sexo"));
                    h.setEdad(rs.getInt("edad"));
                    h.setBonificacion(rs.getInt("bonificacion"));
                    h.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
                    h.setNombrehijo(rs.getString("nombre"));

                    lista.add(h);
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

    public hijos buscarId(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        hijos h = new hijos();
        try {
            String sql = "SELECT iditem,id_empleado,nombre,fecha_nacimiento,"
                    + "edad,sexo,bonificacion "
                    + " FROM hijos "
                    + " WHERE iditem=" + id;

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    h.setIditem(rs.getDouble("iditem"));
                    h.setSexo(rs.getInt("sexo"));
                    h.setEdad(rs.getInt("edad"));
                    h.setBonificacion(rs.getInt("bonificacion"));
                    h.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
                    h.setNombrehijo(rs.getString("nombre"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return h;
    }

    public hijos insertarItem(hijos ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO hijos "
                + " (id_empleado,nombre,fecha_nacimiento,sexo,bonificacion)"
                + " VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, ocr.getId_empleado().getCodigo());
        ps.setString(2, ocr.getNombrehijo());
        ps.setDate(3, ocr.getFecha_nacimiento());
        ps.setInt(4, ocr.getSexo());
        ps.setInt(5, ocr.getBonificacion());
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

    public hijos actualizarItem(hijos ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE hijos "
                + " SET id_empleado=?,nombre=?,fecha_nacimiento=?,sexo=?,bonificacion=? "
                + " WHERE iditem=" + ocr.getIditem());
        ps.setInt(1, ocr.getId_empleado().getCodigo());
        ps.setString(2, ocr.getNombrehijo());
        ps.setDate(3, ocr.getFecha_nacimiento());
        ps.setInt(4, ocr.getSexo());
        ps.setInt(5, ocr.getBonificacion());
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

        ps = st.getConnection().prepareStatement("DELETE FROM hijos WHERE iditem=?");
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

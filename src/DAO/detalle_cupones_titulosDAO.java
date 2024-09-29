/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_cupones_titulos;
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
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Usuario
 */
public class detalle_cupones_titulosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_cupones_titulos> MostrarxTitulo(int nReferencia) throws SQLException {
        ArrayList<detalle_cupones_titulos> lista = new ArrayList<detalle_cupones_titulos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT titulo,numerocupon,fechavencimiento,plazo,fechainicio,estadocupon "
                    + "FROM detalle_cupones_titulos "
                    + "WHERE titulo=" + nReferencia
                    + " ORDER BY numerocupon ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    detalle_cupones_titulos ot = new detalle_cupones_titulos();
                    ot.setNumerocupon(rs.getInt("numerocupon"));
                    ot.setTitulo(rs.getInt("titulo"));
                    ot.setFechainicio(rs.getDate("fechainicio"));
                    ot.setFechavencimiento(rs.getDate("fechavencimiento"));
                    ot.setPlazo(rs.getInt("plazo"));
                    ot.setEstadocupon(rs.getInt("estadocupon"));
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

    public ArrayList<detalle_cupones_titulos> MostrarxTituloActivos(int nReferencia, Date vence) throws SQLException {
        ArrayList<detalle_cupones_titulos> lista = new ArrayList<detalle_cupones_titulos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT titulo,numerocupon,fechavencimiento,plazo,fechainicio,estadocupon "
                    + "FROM detalle_cupones_titulos "
                    + "WHERE titulo=" + nReferencia
                    +" AND fechavencimiento> '"+vence+"' "
                    + " ORDER BY numerocupon ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    detalle_cupones_titulos ot = new detalle_cupones_titulos();
                    ot.setNumerocupon(rs.getInt("numerocupon"));
                    ot.setTitulo(rs.getInt("titulo"));
                    ot.setFechainicio(rs.getDate("fechainicio"));
                    ot.setFechavencimiento(rs.getDate("fechavencimiento"));
                    ot.setPlazo(rs.getInt("plazo"));
                    ot.setEstadocupon(rs.getInt("estadocupon"));
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


    
        public ArrayList<detalle_cupones_titulos> MostrarxTituloTodos(int nReferencia) throws SQLException {
        ArrayList<detalle_cupones_titulos> lista = new ArrayList<detalle_cupones_titulos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT titulo,numerocupon,"
                    +"TIMESTAMPDIFF(DAY,CURDATE(),fechavencimiento) AS ndias,"
                    + "fechavencimiento,plazo,fechainicio,estadocupon "
                    + "FROM detalle_cupones_titulos "
                    + "WHERE titulo=" + nReferencia
                    + " ORDER BY numerocupon ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    detalle_cupones_titulos ot = new detalle_cupones_titulos();
                    ot.setNumerocupon(rs.getInt("numerocupon"));
                    ot.setTitulo(rs.getInt("titulo"));
                    ot.setFechainicio(rs.getDate("fechainicio"));
                    ot.setFechavencimiento(rs.getDate("fechavencimiento"));
                    ot.setPlazo(rs.getInt("plazo"));
                    ot.setEstadocupon(rs.getInt("ndias"));
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

    
    
}

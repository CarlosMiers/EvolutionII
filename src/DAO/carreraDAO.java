/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.carrera;
import Modelo.facultad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class carreraDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<carrera> todos() throws SQLException {
        ArrayList<carrera> lista = new ArrayList<carrera>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT carreras.codigo,"
                + "carreras.nombre,"
                + "carreras.titulo,"
                + "carreras.duracion,"
                + "carreras.periodo_duracion,"
                + "carreras.semestres,"
                + "carreras.facultad,"
                + "carreras.estado,"
                + "facultad.nombre AS nombrefacultad "
                + "FROM carreras "
                + "LEFT JOIN facultad "
                + "ON facultad.codigo=carreras.facultad "
                + "ORDER BY carreras.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                carrera carrera = new carrera();
                facultad facu = new facultad();
                carrera.setFacultad(facu);
                carrera.getFacultad().setCodigo(rs.getInt("facultad"));
                carrera.getFacultad().setNombre(rs.getString("nombrefacultad"));
                carrera.setCodigo(rs.getInt("codigo"));
                carrera.setNombre(rs.getString("nombre"));
                carrera.setTitulo(rs.getString("titulo"));
                carrera.setSemestres(rs.getInt("semestres"));
                carrera.setEstado(rs.getInt("estado"));
                lista.add(carrera);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<carrera> lista() throws SQLException {
        ArrayList<carrera> lista = new ArrayList<carrera>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT carreras.codigo,"
                + "carreras.nombre "
                + "FROM carreras "
                + "ORDER BY carreras.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                carrera carrera = new carrera();
                carrera.setCodigo(rs.getInt("codigo"));
                carrera.setNombre(rs.getString("nombre"));
                lista.add(carrera);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    
    public carrera buscarIdSimple(int id) throws SQLException {
        carrera carrera = new carrera();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT carreras.codigo,"
                    + "carreras.nombre "
                    + "FROM carreras "
                    + "WHERE carreras.codigo=? "
                    + " ORDER BY carreras.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    carrera.setCodigo(rs.getInt("codigo"));
                    carrera.setNombre(rs.getString("nombre"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return carrera;
    }
    
    
    
    
    public carrera buscarId(int id) throws SQLException {
        carrera carrera = new carrera();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT carreras.codigo,"
                    + "carreras.nombre,"
                    + "carreras.titulo,"
                    + "carreras.duracion,"
                    + "carreras.periodo_duracion,"
                    + "carreras.semestres,"
                    + "carreras.facultad,"
                    + "carreras.estado,"
                    + "facultad.nombre AS nombrefacultad "
                    + "FROM carreras "
                    + "LEFT JOIN facultad "
                    + "ON facultad.codigo=carreras.facultad "
                    + "WHERE carreras.codigo=? "
                    + " ORDER BY carreras.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    facultad facu = new facultad();
                    carrera.setFacultad(facu);
                    carrera.getFacultad().setCodigo(rs.getInt("facultad"));
                    carrera.getFacultad().setNombre(rs.getString("nombrefacultad"));
                    carrera.setCodigo(rs.getInt("codigo"));
                    carrera.setNombre(rs.getString("nombre"));
                    carrera.setTitulo(rs.getString("titulo"));
                    carrera.setSemestres(rs.getInt("semestres"));
                    carrera.setEstado(rs.getInt("estado"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return carrera;
    }

    public carrera insertarCarrera(carrera ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO carreras "
                + "(nombre,titulo,semestres,facultad,estado)"
                + "VALUES (?,?,?,?,?)");
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getTitulo());
        ps.setInt(3, ca.getSemestres());
        ps.setInt(4, ca.getFacultad().getCodigo());
        ps.setInt(5, ca.getEstado());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarCarrera(carrera ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE carreras "
                + "SET nombre=?,titulo=?,"
                + "semestres=?,facultad=?,estado=? "
                + " WHERE codigo=" + ca.getCodigo());
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getTitulo());
        ps.setInt(3, ca.getSemestres());
        ps.setInt(4, ca.getFacultad().getCodigo());
        ps.setInt(5, ca.getEstado());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarCarrera(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM carreras WHERE codigo=?");
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
}

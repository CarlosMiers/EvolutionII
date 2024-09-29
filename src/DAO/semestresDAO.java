/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.semestres;
import Modelo.carrera;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class semestresDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<semestres> todos() throws SQLException {
        ArrayList<semestres> lista = new ArrayList<semestres>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT semestres.iditem,semestres.nombre,"
                + "semestres.idcarrera,semestres.semestre,carreras.nombre as nombrecarrera "
                + " FROM semestres "
                + " LEFT JOIN carreras "
                + " ON carreras.codigo=semestres.idcarrera"
                + " ORDER BY semestres.iditem ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                semestres semestres = new semestres();
                carrera carrera = new carrera();
                semestres.setIdcarrera(carrera);
                semestres.getIdcarrera().setCodigo(rs.getInt("idcarrera"));
                semestres.getIdcarrera().setNombre(rs.getString("nombrecarrera"));
                semestres.setIditem(rs.getInt("iditem"));
                semestres.setNombre(rs.getString("nombre"));
                semestres.setSemestre(rs.getInt("semestre"));
                lista.add(semestres);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<semestres> todosxCarrera(int ncarrera) throws SQLException {
        ArrayList<semestres> lista = new ArrayList<semestres>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT semestres.iditem,semestres.nombre,"
                + "semestres.idcarrera,semestres.semestre,carreras.nombre as nombrecarrera "
                + " FROM semestres "
                + " LEFT JOIN carreras "
                + " ON carreras.codigo=semestres.idcarrera"
                + " WHERE semestres.idcarrera = ?"
                + " ORDER BY semestres.semestre ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ncarrera);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                semestres semestres = new semestres();
                carrera carrera = new carrera();
                semestres.setIdcarrera(carrera);
                semestres.getIdcarrera().setCodigo(rs.getInt("idcarrera"));
                semestres.getIdcarrera().setNombre(rs.getString("nombrecarrera"));
                semestres.setIditem(rs.getInt("iditem"));
                semestres.setNombre(rs.getString("nombre"));
                semestres.setSemestre(rs.getInt("semestre"));
                lista.add(semestres);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public semestres buscarId(int id) throws SQLException {
        semestres semestres = new semestres();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT semestres.iditem,semestres.nombre,"
                    + "semestres.idcarrera,semestres.semestre,carreras.nombre as nombrecarrera "
                    + " FROM semestres "
                    + " LEFT JOIN carreras "
                    + " ON carreras.codigo=semestres.idcarrera "
                    + " WHERE semestres.iditem=? "
                    + " ORDER BY semestres.iditem ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    carrera carrera = new carrera();
                    semestres.setIdcarrera(carrera);
                    semestres.getIdcarrera().setCodigo(rs.getInt("idcarrera"));
                    semestres.getIdcarrera().setNombre(rs.getString("nombrecarrera"));
                    semestres.setIditem(rs.getInt("iditem"));
                    semestres.setNombre(rs.getString("nombre"));
                    semestres.setSemestre(rs.getInt("semestre"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return semestres;
    }

    public semestres insertarSemestre(semestres ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO semestres "
                + "(nombre,idcarrera,semestre)"
                + "VALUES (?,?,?)");
        ps.setString(1, ca.getNombre());
        ps.setInt(2, ca.getIdcarrera().getCodigo());
        ps.setInt(3, ca.getSemestre());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            JOptionPane.showMessageDialog(null, "Es Probable que el Semestre ya Exista");
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarSemestre(semestres ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE semestres "
                + "SET nombre=?,idcarrera=?,semestre=? "
                + " WHERE iditem=" + ca.getIditem());
        ps.setString(1, ca.getNombre());
        ps.setInt(2, ca.getIdcarrera().getCodigo());
        ps.setInt(3, ca.getSemestre());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarSemestre(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM semestres WHERE iditem=?");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.materias;
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
public class materiasDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<materias> todos() throws SQLException {
        ArrayList<materias> lista = new ArrayList<materias>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT codigo,idmateria,nombre,idcarrera,cargahoraria,semestre "
                + "FROM materias "
                + "ORDER BY idcarrera,semestre,idmateria ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                materias mat = new materias();
                mat.setIdmateria(rs.getInt("idmateria"));
                mat.setNombre(rs.getString("nombre"));
                mat.setSemestre(rs.getInt("semestre"));
                mat.setIdcarrera(rs.getInt("idcarrera"));
                mat.setCodigo(rs.getInt("codigo"));
                mat.setCargahoraria(rs.getInt("cargahoraria"));
                lista.add(mat);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<materias> todosxCarreraxSemestre(int ncarrera, int nsemestre) throws SQLException {
        ArrayList<materias> lista = new ArrayList<materias>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT codigo,idmateria,nombre,idcarrera,semestre "
                + " FROM materias "
                + " WHERE idcarrera=? AND semestre=? "
                + " ORDER BY idcarrera,semestre,idmateria ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ncarrera);
            ps.setInt(2, nsemestre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                materias mat = new materias();
                mat.setIdmateria(rs.getInt("idmateria"));
                mat.setNombre(rs.getString("nombre"));
                mat.setSemestre(rs.getInt("semestre"));
                mat.setIdcarrera(rs.getInt("idcarrera"));
                mat.setCodigo(rs.getInt("codigo"));
                lista.add(mat);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public materias BuscarxCarreraxSemestre(int nmateria, int ncarrera, int nsemestre) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        materias mat = new materias();
        String sql = "SELECT codigo,idmateria,nombre,idcarrera,semestre "
                + " FROM materias "
                + " WHERE idmateria=? AND idcarrera=? AND semestre=? "
                + " ORDER BY idmateria,idcarrera,semestre ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, nmateria);
            ps.setInt(2, ncarrera);
            ps.setInt(3, nsemestre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mat.setIdmateria(rs.getInt("idmateria"));
                mat.setNombre(rs.getString("nombre"));
                mat.setSemestre(rs.getInt("semestre"));
                mat.setIdcarrera(rs.getInt("idcarrera"));
                mat.setCodigo(rs.getInt("codigo"));
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        st.close();
        return mat;
    }

    public materias insertarMateria(materias ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO materias "
                + "(idmateria,nombre,idcarrera,cargahoraria,semestre)"
                + "VALUES (?,?,?,?,?)");
        ps.setInt(1, ca.getIdmateria());
        ps.setString(2, ca.getNombre());
        ps.setInt(3, ca.getIdcarrera());
        ps.setInt(4, ca.getCargahoraria());
        ps.setInt(5, ca.getSemestre());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            JOptionPane.showMessageDialog(null, "Es Probable que la Materia ya Exista");
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarMateria(materias ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE materias "
                + "SET idmateria=?,nombre=?,idcarrera=?,"
                + "cargahoraria=?,semestre=? "
                + " WHERE codigo=" + ca.getCodigo());
        ps.setInt(1, ca.getIdmateria());
        ps.setString(2, ca.getNombre());
        ps.setInt(3, ca.getIdcarrera());
        ps.setInt(4, ca.getCargahoraria());
        ps.setInt(5, ca.getSemestre());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarMateria(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM materias WHERE codigo=?");
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

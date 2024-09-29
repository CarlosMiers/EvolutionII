/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.aula;
import Modelo.edificio_sede;
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
public class aulaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<aula> todos() throws SQLException {
        ArrayList<aula> lista = new ArrayList<aula>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT aulas.codigo,aulas.nombre,"
                + "aulas.edificio,aulas.capacidad,"
                + "aulas.estado,aulas.observacion,"
                + "edificio_sedes.nombre AS nombreedificio "
                + " FROM aulas "
                + " LEFT JOIN edificio_sedes "
                + " ON edificio_sedes.codigo=aulas.edificio"
                + " ORDER BY aulas.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                aula aula = new aula();
                edificio_sede edificio = new edificio_sede();
                aula.setEdificio(edificio);
                aula.getEdificio().setCodigo(rs.getInt("edificio"));
                aula.getEdificio().setNombre(rs.getString("nombreedificio"));
                aula.setCodigo(rs.getInt("codigo"));
                aula.setNombre(rs.getString("nombre"));
                aula.setCapacidad(rs.getInt("capacidad"));
                aula.setObservacion(rs.getString("observacion"));
                aula.setEstado(rs.getInt("estado"));
                lista.add(aula);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public aula buscarId(int id) throws SQLException {
        aula aula = new aula();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT aulas.codigo,aulas.nombre,"
                    + "aulas.edificio,aulas.capacidad,"
                    + "aulas.estado,aulas.observacion,"
                    + "edificio_sedes.nombre AS nombreedificio "
                    + " FROM aulas "
                    + " LEFT JOIN edificio_sedes "
                    + " ON edificio_sedes.codigo=aulas.edificio "
                    + " WHERE aulas.codigo=? "
                    + " ORDER BY aulas.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    edificio_sede edificio = new edificio_sede();
                    aula.setEdificio(edificio);
                    aula.getEdificio().setCodigo(rs.getInt("edificio"));
                    aula.getEdificio().setNombre(rs.getString("nombreedificio"));
                    aula.setCodigo(rs.getInt("codigo"));
                    aula.setNombre(rs.getString("nombre"));
                    aula.setCapacidad(rs.getInt("capacidad"));
                    aula.setObservacion(rs.getString("observacion"));
                    aula.setEstado(rs.getInt("estado"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return aula;
    }

    public aula insertarAula(aula ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO aulas "
                + "(nombre,edificio,capacidad,observacion,estado)"
                + "VALUES (?,?,?,?,?)");
        ps.setString(1, ca.getNombre());
        ps.setInt(2, ca.getEdificio().getCodigo());
        ps.setInt(3, ca.getCapacidad());
        ps.setString(4, ca.getObservacion());
        ps.setInt(5, ca.getEstado());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarAula(aula ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE aulas "
                + "SET nombre=?,edificio=?,capacidad=?,observacion=?,estado=? "
                + " WHERE codigo=" + ca.getCodigo());
        ps.setString(1, ca.getNombre());
        ps.setInt(2, ca.getEdificio().getCodigo());
        ps.setInt(3, ca.getCapacidad());
        ps.setString(4, ca.getObservacion());
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

    public boolean eliminarAula(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM aulas WHERE codigo=?");
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

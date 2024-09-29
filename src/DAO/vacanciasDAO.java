/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cargo;
import Modelo.vacancias;
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
public class vacanciasDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<vacancias> todos(Date dFechaIni, Date dFechaFin) throws SQLException {
        ArrayList<vacancias> lista = new ArrayList<vacancias>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT numero,nombrepuesto,fecha,perfil,descripcion,"
                + "edades,sexo,disponible,cargos.nombre AS nombreperfil,cupos "
                + " FROM vacancias "
                + " LEFT JOIN cargos "
                + " ON cargos.codigo=vacancias.perfil "
                + " WHERE  vacancias.fecha between ? AND ? "
                + " ORDER BY vacancias.numero ";

        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechaIni);
            ps.setDate(2, dFechaFin);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vacancias va = new vacancias();
                cargo ca = new cargo();
                va.setPerfil(ca);
                va.setNumero(rs.getDouble("numero"));
                va.setNombrepuesto(rs.getString("nombrepuesto"));
                va.setFecha(rs.getDate("fecha"));
                va.setDescripcion(rs.getString("descripcion"));
                va.setEdades(rs.getString("edades"));
                va.setSexo(rs.getInt("sexo"));
                va.setDisponible(rs.getInt("disponible"));
                va.setCupos(rs.getInt("cupos"));
                va.getPerfil().setCodigo(rs.getInt("perfil"));
                va.getPerfil().setNombre(rs.getString("nombreperfil"));
                lista.add(va);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<vacancias> BuscarTodos() throws SQLException {
        ArrayList<vacancias> lista = new ArrayList<vacancias>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT numero,nombrepuesto,fecha,perfil,descripcion,"
                + "edades,sexo,disponible,cargos.nombre AS nombreperfil,cupos "
                + " FROM vacancias "
                + " LEFT JOIN cargos "
                + " ON cargos.codigo=vacancias.perfil "
                + " ORDER BY vacancias.numero ";

        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vacancias va = new vacancias();
                cargo ca = new cargo();
                va.setPerfil(ca);
                va.setNumero(rs.getDouble("numero"));
                va.setNombrepuesto(rs.getString("nombrepuesto"));
                va.setFecha(rs.getDate("fecha"));
                va.setDescripcion(rs.getString("descripcion"));
                va.setEdades(rs.getString("edades"));
                va.setSexo(rs.getInt("sexo"));
                va.setDisponible(rs.getInt("disponible"));
                va.setCupos(rs.getInt("cupos"));
                va.getPerfil().setCodigo(rs.getInt("perfil"));
                va.getPerfil().setNombre(rs.getString("nombreperfil"));
                lista.add(va);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }
    
    
    public ArrayList<vacancias> Activos() throws SQLException {
        ArrayList<vacancias> lista = new ArrayList<vacancias>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT numero,nombrepuesto,fecha,perfil,descripcion,"
                + "edades,sexo,disponible,cargos.nombre AS perfilnombre,cupos "
                + " FROM vacancias "
                + " LEFT JOIN cargos "
                + " ON cargos.codigo=vacancias.perfil "
                + " WHERE  vacancias.cupos<vacancias.disponible "
                + " ORDER BY vacancias.numero ";

        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vacancias va = new vacancias();
                cargo ca = new cargo();
                va.setPerfil(ca);
                va.setNumero(rs.getDouble("numero"));
                va.setNombrepuesto(rs.getString("nombrepuesto"));
                va.setFecha(rs.getDate("fecha"));
                va.setDescripcion(rs.getString("descripcion"));
                va.setEdades(rs.getString("edades"));
                va.setSexo(rs.getInt("sexo"));
                va.setDisponible(rs.getInt("disponible"));
                va.setCupos(rs.getInt("cupos"));
                va.getPerfil().setCodigo(rs.getInt("perfil"));
                va.getPerfil().setNombre(rs.getString("nombreperfil"));
                lista.add(va);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public vacancias buscarId(double id) throws SQLException {
        vacancias va = new vacancias();
        cargo ca = new cargo();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT numero,nombrepuesto,fecha,perfil,descripcion,"
                    + "edades,sexo,disponible,cargos.nombre AS nombreperfil,cupos "
                    + " FROM vacancias "
                    + " LEFT JOIN cargos "
                    + " ON cargos.codigo=vacancias.perfil "
                    + " WHERE  vacancias.numero=? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    va.setPerfil(ca);
                    va.setNumero(rs.getDouble("numero"));
                    va.setNombrepuesto(rs.getString("nombrepuesto"));
                    va.setFecha(rs.getDate("fecha"));
                    va.setDescripcion(rs.getString("descripcion"));
                    va.setEdades(rs.getString("edades"));
                    va.setSexo(rs.getInt("sexo"));
                    va.setDisponible(rs.getInt("disponible"));
                    va.setCupos(rs.getInt("cupos"));
                    va.getPerfil().setCodigo(rs.getInt("perfil"));
                    va.getPerfil().setNombre(rs.getString("nombreperfil"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return va;
    }

    public vacancias insertarVacancia(vacancias ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO vacancias "
                + "(nombrepuesto,fecha,perfil,descripcion,"
                + "edades,sexo,disponible)"
                + "VALUES (?,?,?,?,?,?,?)");

        ps.setString(1, ca.getNombrepuesto());
        ps.setDate(2, ca.getFecha());
        ps.setInt(3, ca.getPerfil().getCodigo());
        ps.setString(4, ca.getDescripcion());
        ps.setString(5, ca.getEdades());
        ps.setInt(6, ca.getSexo());
        ps.setInt(7, ca.getDisponible());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarVacancia(vacancias ca) throws SQLException {
        int rowsUpdated = 0;
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE vacancias "
                + " SET nombrepuesto=?,fecha=?,perfil=?,descripcion=?,"
                + "edades=?,sexo=?,disponible=? WHERE numero=" + ca.getNumero());
        ps.setString(1, ca.getNombrepuesto());
        ps.setDate(2, ca.getFecha());
        ps.setInt(3, ca.getPerfil().getCodigo());
        ps.setString(4, ca.getDescripcion());
        ps.setString(5, ca.getEdades());
        ps.setInt(6, ca.getSexo());
        ps.setInt(7, ca.getDisponible());
        try {
            rowsUpdated = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarVacancia(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM vacancias WHERE numero=?");
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

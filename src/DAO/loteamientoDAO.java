/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.localidad;
import Modelo.loteamiento;
import Modelo.propietario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class loteamientoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<loteamiento> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT loteamientos.codigo,loteamientos.nombre,loteamientos.hectarea,loteamientos.manzanas,"
                + "loteamientos.lotesdisponibles,loteamientos.metroxlotes,loteamientos.localidad,loteamientos.propietario,"
                + "loteamientos.observaciones,propietarios.nombre AS nombrepropietario,"
                + "localidades.nombre AS nombrelocalidad "
                + "FROM loteamientos "
                + "INNER JOIN propietarios "
                + "ON propietarios.codpro=loteamientos.propietario "
                + "INNER JOIN localidades "
                + "ON localidades.codigo=loteamientos.localidad "
                + "ORDER BY loteamientos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                loteamiento lot = new loteamiento();
                propietario pro = new propietario();
                localidad loc = new localidad();
                lot.setCodigo(rs.getInt("codigo"));
                lot.setNombre(rs.getString("nombre"));
                lot.setHectarea(rs.getString("hectarea"));
                lot.setManzanas(rs.getInt("manzanas"));
                lot.setLotesdisponibles(rs.getInt("lotesdisponibles"));
                lot.setMetroxlotes(rs.getString("metroxlotes"));
                lot.setObservaciones(rs.getString("observaciones"));
                lot.setPropietario(pro);
                lot.setLocalidad(loc);
                lot.getPropietario().setCodpro(rs.getInt("propietario"));
                lot.getPropietario().setNombre(rs.getString("nombrepropietario"));
                lot.getLocalidad().setCodigo(rs.getInt("localidad"));
                lot.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                lista.add(lot);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }
    
   
    public loteamiento buscarId(int id) throws SQLException {

        loteamiento lot = new loteamiento();
        propietario pro = new propietario();
        localidad loc = new localidad();

        con = new Conexion();
        st = con.conectar();
        try {
        String sql = "SELECT loteamientos.codigo,loteamientos.nombre,loteamientos.hectarea,loteamientos.manzanas,"
                + "loteamientos.lotesdisponibles,loteamientos.metroxlotes,loteamientos.localidad,loteamientos.propietario,"
                + "loteamientos.observaciones,propietarios.nombre AS nombrepropietario,"
                + "localidades.nombre AS nombrelocalidad "
                + "FROM loteamientos "
                + "INNER JOIN propietarios "
                + "ON propietarios.codpro=loteamientos.propietario "
                + "INNER JOIN localidades "
                + "ON localidades.codigo=loteamientos.localidad "
                + "WHERE loteamientos.codigo=? "
                + "ORDER BY loteamientos.codigo ";
        System.out.println(sql);
          try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                lot.setCodigo(rs.getInt("codigo"));
                lot.setNombre(rs.getString("nombre"));
                lot.setHectarea(rs.getString("hectarea"));
                lot.setManzanas(rs.getInt("manzanas"));
                lot.setLotesdisponibles(rs.getInt("lotesdisponibles"));
                lot.setMetroxlotes(rs.getString("metroxlotes"));
                lot.setObservaciones(rs.getString("observaciones"));
                lot.setPropietario(pro);
                lot.setLocalidad(loc);
                lot.getPropietario().setCodpro(rs.getInt("propietario"));
                lot.getPropietario().setNombre(rs.getString("nombrepropietario"));
                lot.getLocalidad().setCodigo(rs.getInt("localidad"));
                lot.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lot;
    }

    public loteamiento insertarloteamiento(loteamiento lot) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO loteamientos(nombre,hectarea,manzanas,lotesdisponibles,metroxlotes,localidad,propietario,observaciones) VALUES (?,?,?,?,?,?,?,?)");
        ps.setString(1, lot.getNombre());
        ps.setString(2, lot.getHectarea());
        ps.setInt(3, lot.getManzanas());
        ps.setInt(4, lot.getLotesdisponibles());
        ps.setString(5, lot.getMetroxlotes());
        ps.setInt(6, lot.getLocalidad().getCodigo());
        ps.setInt(7, lot.getPropietario().getCodpro());
        ps.setString(8, lot.getObservaciones());
        ps.executeUpdate();
        st.close();
        ps.close();
        return lot;
    }

    public boolean actualizarloteamiento(loteamiento lot) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE loteamientos SET nombre=?,hectarea=?,manzanas=?,lotesdisponibles=?,metroxlotes=?,localidad=?,propietario=?,observaciones=? WHERE codigo=" + lot.getCodigo());
        ps.setString(1, lot.getNombre());
        ps.setString(2, lot.getHectarea());
        ps.setInt(3, lot.getManzanas());
        ps.setInt(4, lot.getLotesdisponibles());
        ps.setString(5, lot.getMetroxlotes());
        ps.setInt(6, lot.getLocalidad().getCodigo());
        ps.setInt(7, lot.getPropietario().getCodpro());
        ps.setString(8, lot.getObservaciones());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarloteamiento(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM loteamientos WHERE codigo=?");
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

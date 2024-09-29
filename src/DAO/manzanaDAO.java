/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.localidad;
import Modelo.loteamiento;
import Modelo.manzana;
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
public class manzanaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<manzana> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT manzanas.codigo,manzanas.nombre,manzanas.superficie,manzanas.lotes,"
                + "manzanas.loteamiento,loteamientos.nombre as nombreloteamiento "
                + " FROM manzanas "
                + " LEFT JOIN loteamientos "
                + " ON loteamientos.codigo=manzanas.loteamiento "
                + " ORDER BY manzanas.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                manzana ma = new manzana();
                loteamiento lot = new loteamiento();

                ma.setCodigo(rs.getInt("codigo"));
                ma.setNombre(rs.getString("nombre"));
                ma.setSuperficie(rs.getDouble("superficie"));
                ma.setLotes(rs.getInt("lotes"));
                ma.setLoteamiento(lot);
                ma.getLoteamiento().setCodigo(rs.getInt("loteamiento"));
                ma.getLoteamiento().setNombre(rs.getString("nombreloteamiento"));
                lista.add(ma);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<manzana> PorLoteamiento(int ltm) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT "
                +       "manzanas.codigo,manzanas.nombre,"
                +       "manzanas.superficie,manzanas.lotes,"
                +       "manzanas.loteamiento,"
                +       "loteamientos.nombre as nombreloteamiento "
                + " FROM manzanas "
                + " LEFT JOIN loteamientos "
                + " ON loteamientos.codigo=manzanas.loteamiento "
                + " WHERE manzanas.loteamiento=?"
                + " ORDER BY manzanas.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ltm);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                manzana ma = new manzana();
                loteamiento lot = new loteamiento();

                ma.setCodigo(rs.getInt("codigo"));
                ma.setNombre(rs.getString("nombre"));
                ma.setSuperficie(rs.getDouble("superficie"));
                ma.setLotes(rs.getInt("lotes"));
                lot.setCodigo(rs.getInt("loteamiento"));
                lot.setNombre(rs.getString("nombreloteamiento"));
                ma.setLoteamiento(lot);
                
                lista.add(ma);
            }
            //System.out.println("total manzanas: "+lista.size());
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public manzana buscarId(int id) throws SQLException {

        loteamiento lot = new loteamiento();
        manzana ma = new manzana();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT manzanas.codigo,manzanas.nombre,manzanas.superficie,manzanas.lotes,"
                    + "manzanas.loteamiento,loteamientos.nombre as nombreloteamiento "
                    + " FROM manzanas "
                    + " LEFT JOIN loteamientos "
                    + " ON loteamientos.codigo=manzanas.loteamiento "
                    + " WHERE manzanas.codigo=? "
                    + " ORDER BY manzanas.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ma.setCodigo(rs.getInt("codigo"));
                    ma.setNombre(rs.getString("nombre"));
                    ma.setSuperficie(rs.getDouble("superficie"));
                    ma.setLotes(rs.getInt("lotes"));
                    ma.setLoteamiento(lot);
                    ma.getLoteamiento().setCodigo(rs.getInt("loteamiento"));
                    ma.getLoteamiento().setNombre(rs.getString("nombreloteamiento"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return ma;
    }

    public manzana buscarIdLoteamiento(int id, int ltm) throws SQLException {

        loteamiento lot = new loteamiento();
        manzana ma = new manzana();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT manzanas.codigo,manzanas.nombre,manzanas.superficie,manzanas.lotes,"
                    + "manzanas.loteamiento,loteamientos.nombre as nombreloteamiento "
                    + " FROM manzanas "
                    + " LEFT JOIN loteamientos "
                    + " ON loteamientos.codigo=manzanas.loteamiento "
                    + " WHERE manzanas.codigo=? and manzanas.loteamiento=?"
                    + " ORDER BY manzanas.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setInt(2, ltm);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ma.setCodigo(rs.getInt("codigo"));
                    ma.setNombre(rs.getString("nombre"));
                    ma.setSuperficie(rs.getDouble("superficie"));
                    ma.setLotes(rs.getInt("lotes"));
                    lot.setCodigo(rs.getInt("loteamiento"));
                    lot.setNombre(rs.getString("nombreloteamiento"));
                    ma.setLoteamiento(lot);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return ma;
    }

    public manzana insertarmanzana(manzana ma) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("INSERT INTO manzanas (nombre,superficie,lotes,loteamiento) VALUES (?,?,?,?)");
        ps.setString(1, ma.getNombre());
        ps.setDouble(2, ma.getSuperficie());
        ps.setInt(3, ma.getLotes());
        ps.setInt(4, ma.getLoteamiento().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ma;
    }

    public boolean actualizarmanzana(manzana ma) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE manzanas SET nombre=?,superficie=?,lotes=?,loteamiento=? WHERE codigo=" + ma.getCodigo());
        ps.setString(1, ma.getNombre());
        ps.setDouble(2, ma.getSuperficie());
        ps.setInt(3, ma.getLotes());
        ps.setInt(4, ma.getLoteamiento().getCodigo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarmanzana(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM manzanas WHERE codigo=?");
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

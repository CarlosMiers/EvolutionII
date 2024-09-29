/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.lote;
import Modelo.loteamiento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class lotesDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<lote> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT lotes.codigo,lotes.nombre,loteamientos.codigo AS codloteamiento,loteamientos.nombre AS nombreloteamiento,"
                + "lotes.manzana,lotes.nrolote,lotes.superficie,lotes.estado "
                + "FROM lotes "
                + "INNER JOIN loteamientos "
                + "ON loteamientos.codigo=lotes.loteamiento "
                + "ORDER BY lotes.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lote lot = new lote();
                loteamiento lote = new loteamiento();
                lot.setLoteamiento(lote);
  
                lot.setCodigo(rs.getInt("codigo"));
                lot.setNombre(rs.getString("nombre"));
                lot.getLoteamiento().setCodigo(rs.getInt("codloteamiento"));
                lot.getLoteamiento().setNombre(rs.getString("nombreloteamiento"));
                
                lot.setManzanas(rs.getInt("manzana"));
                lot.setNrolote(rs.getInt("nrolote"));
                lot.setSuperficie(rs.getString("superficie"));
                lot.setEstado(rs.getString("estado"));
      
                lista.add(lot);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public lote buscarId(int id) throws SQLException {

        lote lot = new lote();
        loteamiento lote = new loteamiento();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT lotes.codigo,lotes.nombre,loteamientos.codigo AS codloteamiento,loteamientos.nombre AS nombreloteamiento,"
                    + "lotes.manzana, manzanas.nombre as nombremanzana,lotes.nrolote,lotes.superficie,lotes.estado "
                    + "FROM lotes "
                    + "INNER JOIN loteamientos ON loteamientos.codigo=lotes.loteamiento "
                    + "INNER JOIN manzanas ON manzanas.codigo=lotes.manzana "
                    + "WHERE lotes.codigo=? "
                    + "ORDER BY lotes.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                lot.setLoteamiento(lote);
                lot.setCodigo(rs.getInt("codigo"));
                lot.setNombre(rs.getString("nombre"));
                lot.getLoteamiento().setCodigo(rs.getInt("codloteamiento"));
                lot.getLoteamiento().setNombre(rs.getString("nombreloteamiento"));
                lot.setManzanas(rs.getInt("manzana"));
                lot.setNombremanzanas(rs.getString("nombremanzana"));
                lot.setNrolote(rs.getInt("nrolote"));
                lot.setSuperficie(rs.getString("superficie"));
                lot.setEstado(rs.getString("estado"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lot;
    }

    public lote insertarlote(lote lot) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO lotes("
                + "nombre,loteamiento,manzana,nrolote,superficie,estado) VALUES (?,?,?,?,?,?)");
        ps.setString(1, lot.getNombre());
        ps.setInt(2, lot.getLoteamiento().getCodigo());
        ps.setInt(3, lot.getManzanas());
        ps.setInt(4, lot.getNrolote());
        ps.setString(5, lot.getSuperficie());
        ps.setString(6, lot.getEstado());
        ps.executeUpdate();
        st.close();
        ps.close();
        return lot;
    }

    public boolean actualizarlote(lote lot) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE lotes SET nombre=?,loteamiento=?,manzana=?,nrolote=?,superficie=?,estado=? WHERE codigo=" + lot.getCodigo());
        ps.setString(1, lot.getNombre());
        ps.setInt(2, lot.getLoteamiento().getCodigo());
        ps.setInt(3, lot.getManzanas());
        ps.setInt(4, lot.getNrolote());
        ps.setString(5, lot.getSuperficie());
        ps.setString(6, lot.getEstado());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarlote(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM lotes WHERE codigo=?");
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

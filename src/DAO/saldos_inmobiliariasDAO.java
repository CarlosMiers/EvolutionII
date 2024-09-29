/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.saldos_inmobiliaria;
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
 * @author Pc_Server
 */
public class saldos_inmobiliariasDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<saldos_inmobiliaria> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<saldos_inmobiliaria> lista = new ArrayList<saldos_inmobiliaria>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = " SELECT idreferencia,numero,fecha,total,inquilino, "
                    + " clientes.nombre AS nombreinquilino "
                    + " FROM saldos_inmobiliaria "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=saldos_inmobiliaria.inquilino "
                    + " WHERE saldos_inmobiliaria.fecha between ? AND ? "
                    + " ORDER BY saldos_inmobiliaria.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    saldos_inmobiliaria ajuste = new saldos_inmobiliaria();
                    ajuste.setInquilino(cliente);

                    ajuste.setIdreferencia(rs.getString("idreferencia"));
                    ajuste.setNumero(rs.getInt("numero"));
                    ajuste.setFecha(rs.getDate("fecha"));
                    ajuste.getInquilino().setCodigo(rs.getInt("inquilino"));
                    ajuste.getInquilino().setNombre(rs.getString("nombreinquilino"));
                    ajuste.setTotal(rs.getDouble("total"));
                    lista.add(ajuste);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public saldos_inmobiliaria buscarId(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        saldos_inmobiliaria ajuste = new saldos_inmobiliaria();

        try {

            String sql = " SELECT idreferencia,numero,fecha,total,inquilino, "
                    + " clientes.nombre AS nombreinquilino "
                    + " FROM saldos_inmobiliaria "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=saldos_inmobiliaria.inquilino "
                    + " WHERE saldos_inmobiliaria.numero=? "
                    + " ORDER BY saldos_inmobiliaria.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    ajuste.setInquilino(cliente);

                    ajuste.setIdreferencia(rs.getString("idreferencia"));
                    ajuste.setNumero(rs.getInt("numero"));
                    ajuste.setFecha(rs.getDate("fecha"));
                    ajuste.getInquilino().setCodigo(rs.getInt("inquilino"));
                    ajuste.getInquilino().setNombre(rs.getString("nombreinquilino"));
                    ajuste.setTotal(rs.getDouble("total"));
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return ajuste;
    }

    public saldos_inmobiliaria insertarsaldos(saldos_inmobiliaria ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("INSERT INTO saldos_inmobiliaria(idreferencia,fecha,total,inquilino) "
                + "VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdreferencia());
        ps.setDate(2, ocr.getFecha());
        ps.setDouble(3, ocr.getTotal());
        ps.setInt(4, ocr.getInquilino().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ocr;
    }

    public saldos_inmobiliaria actualizarAjuste(saldos_inmobiliaria aj) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE saldos_inmobiliaria SET fecha=?,inquilino=?,total=? WHERE numero=" + aj.getNumero());
        ps.setDate(1, aj.getFecha());
        ps.setInt(2, aj.getInquilino().getCodigo());
        ps.setDouble(3, aj.getTotal());
        ps.executeUpdate();
        st.close();
        ps.close();
        return aj;
    }


    public boolean borrarAjustes(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM saldos_inmobiliaria WHERE idreferencia=?");
        ps.setString(1, referencia);
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

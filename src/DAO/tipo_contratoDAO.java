/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.tipo_contrato;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class tipo_contratoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<tipo_contrato> todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String sql = "select tipo_contratos.codigo,tipo_contratos.nombre,tipo_contratos.documento "
                + "from tipo_contratos "
                + " ORDER BY codigo";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tipo_contrato tipo = new tipo_contrato();
                tipo.setCodigo(rs.getInt("codigo"));
                tipo.setNombre(rs.getString("nombre"));
                tipo.setDocumento(rs.getString("documento"));
                lista.add(tipo);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public tipo_contrato buscarId(int id) throws SQLException {

        tipo_contrato tipo_contrato = new tipo_contrato();
        tipo_contrato.setCodigo(0);
        tipo_contrato.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select tipo_contratos.codigo,tipo_contratos.nombre, tipo_contratos.documento "
                    + " from tipo_contratos "
                    + " where tipo_contratos.codigo = ? "
                    + "order by tipo_contratos.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    tipo_contrato.setCodigo(rs.getInt("codigo"));
                    tipo_contrato.setNombre(rs.getString("nombre"));
                    tipo_contrato.setDocumento(rs.getString("documento"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return tipo_contrato;
    }

    public tipo_contrato insertartipo_contrato(tipo_contrato loca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO tipo_contratos (nombre,documento) VALUES (?,?)");
        ps.setString(1, loca.getNombre());
        ps.setString(2, loca.getDocumento());
        ps.executeUpdate();
        st.close();
        ps.close();
        return loca;
    }

    public boolean actualizartipo_contrato(tipo_contrato loca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE tipo_contratos SET nombre=?,documento=? WHERE codigo=" + loca.getCodigo());
        ps.setString(1, loca.getNombre());
        ps.setString(2, loca.getDocumento());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminartipo_contrato(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM tipo_contratos WHERE codigo=?");
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

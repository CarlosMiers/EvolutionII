/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.nota_credito;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class nota_creditoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<nota_credito> MostrarDetalle(String id) throws SQLException {
        ArrayList<nota_credito> lista = new ArrayList<nota_credito>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT iditem,idnotacredito,notacredito,timbrado,timbradoasociado,tipo,"
                    + "idfactura,nrofactura "
                    + "FROM nota_credito"
                    + " WHERE nota_credito.idnotacredito='"+id+"'"
                    + " ORDER BY iditem";
            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    nota_credito dc = new nota_credito();
                    dc.setIditem(rs.getDouble("iditem"));
                    dc.setNrofactura(rs.getString("nrofactura"));
                    dc.setTimbradoasociado(rs.getInt("timbradoasociado"));
                    dc.setTipo(rs.getInt("tipo"));
                    lista.add(dc);
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarDetalle(Double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM nota_credito WHERE dnumero=?");
        ps.setDouble(1, id);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public nota_credito insertarReferencia(nota_credito pe) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO nota_credito"
                + " (idnotacredito,notacredito,timbrado,"
                + "idfactura,nrofactura,timbradoasociado,tipo)"
                + " VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        
        ps.setString(1, pe.getIdnotacredito());
        ps.setString(2, pe.getNotacredito());
        ps.setInt(3, pe.getTimbrado());
        ps.setString(4, pe.getIdfactura());
        ps.setString(5, pe.getNrofactura());
        ps.setInt(6,pe.getTimbradoasociado());
        ps.setInt(7,pe.getTipo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return pe;
    }

    public nota_credito borrarReferencia(nota_credito pe) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM nota_credito WHERE iditem=?");
        ps.setDouble(1,pe.getIditem());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        return pe;
    }

}

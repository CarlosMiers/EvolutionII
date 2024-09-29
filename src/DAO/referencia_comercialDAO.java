/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.referencia_comercial;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class referencia_comercialDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<referencia_comercial> muestrarefxcliente(int codcliente) throws SQLException {
        ArrayList<referencia_comercial> lista = new ArrayList<referencia_comercial>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT idcliente,descripcion,telefono,item"
                    + " FROM referencia_comercial "
                    + "WHERE referencia_comercial.idcliente= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, codcliente);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    referencia_comercial rc = new referencia_comercial();
                    cliente cliente = new cliente();
                    rc.setIdcliente(cliente);
                    rc.setItem(rs.getInt("item"));
                    rc.setDescripcion(rs.getString("descripcion"));
                    rc.getIdcliente().setCodigo(rs.getInt("idcliente"));
                    rc.setTelefono(rs.getString("telefono"));
                    lista.add(rc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public referencia_comercial insertarRefComercial(referencia_comercial rc) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO referencia_comercial (idcliente,descripcion,telefono) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, rc.getIdcliente().getCodigo());
        ps.setString(2, rc.getDescripcion());
        ps.setString(3, rc.getTelefono());
        ps.executeUpdate();
        st.close();
        ps.close();
        return rc;
    }

    public boolean borrarRefComercial(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM referencia_comercial WHERE item=?");
        ps.setInt(1, id);
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

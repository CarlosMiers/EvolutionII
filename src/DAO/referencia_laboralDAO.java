/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.referencia_laboral;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class referencia_laboralDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<referencia_laboral> muestrarefxcliente(int codcliente) throws SQLException {
        ArrayList<referencia_laboral> lista = new ArrayList<referencia_laboral>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT idcliente,descripcion,telefono,item"
                    + " FROM referencia_laboral "
                    + "WHERE referencia_laboral.idcliente= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, codcliente);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    referencia_laboral rf = new referencia_laboral();
                    cliente cliente = new cliente();
                    rf.setIdcliente(cliente);
                    rf.setItem(rs.getInt("item"));
                    rf.setDescripcion(rs.getString("descripcion"));
                    rf.getIdcliente().setCodigo(rs.getInt("idcliente"));
                    rf.setTelefono(rs.getString("telefono"));
                    lista.add(rf);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public referencia_laboral insertarRefLaboral(referencia_laboral ref) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO referencia_laboral (idcliente,descripcion,telefono) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, ref.getIdcliente().getCodigo());
        ps.setString(2, ref.getDescripcion());
        ps.setString(3, ref.getTelefono());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ref;
    }

    public boolean borrarRefLaboral(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM referencia_laboral WHERE item=?");
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

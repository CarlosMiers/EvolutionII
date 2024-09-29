/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.inmueble_propietario;
import Modelo.propietario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class inmueble_propietarioDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<inmueble_propietario> muestraxinmueble(int inmueble) throws SQLException {
        ArrayList<inmueble_propietario> lista = new ArrayList<inmueble_propietario>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT codinmueble,idpropietario,propietarios.nombre,propietarios.apellido,"
                    +"propietarios.teleparticular "
                    + " FROM inmueble_propietarios "
                    +"LEFT JOIN propietarios "
                    +"ON propietarios.codpro=inmueble_propietarios.idpropietario "
                    + " WHERE inmueble_propietarios.codinmueble= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, inmueble);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    inmueble_propietario rc = new inmueble_propietario();
                    propietario pro = new propietario();
                    rc.setIdpropietario(pro);
                    rc.setCodinmueble(rs.getInt("codinmueble"));
                    rc.getIdpropietario().setCodpro(rs.getInt("idpropietario"));
                    rc.getIdpropietario().setNombre(rs.getString("nombre"));
                    rc.getIdpropietario().setApellido(rs.getString("apellido"));
                    rc.getIdpropietario().setTeleparticular(rs.getString("teleparticular"));
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

    public inmueble_propietario insertarPro(inmueble_propietario rc) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO inmueble_propietarios (codinmueble,idpropietario) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, rc.getCodinmueble());
        ps.setInt(2, rc.getIdpropietario().getCodpro());
        ps.executeUpdate();
        st.close();
        ps.close();
        return rc;
    }

     public boolean borrarpropietario(int codinmueble) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM inmueble_propietarios WHERE codinmueble=?");
        ps.setInt(1, codinmueble);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
     
      public boolean borrarpropietarioIN(String idcontrato) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM inmueble_propietarios WHERE codinmueble=?");
        ps.setString(1, idcontrato);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    
    
    
  /*   public boolean borrarPropietario(int codinmueble) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM inmueble_propietario WHERE codinmueble=? ");
        ps.setInt(1, codinmueble);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }*/
}

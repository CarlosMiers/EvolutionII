/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cobrador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author SERVIDOR
 */
public class cobradorDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cobrador> todos() throws SQLException {
        ArrayList<cobrador> lista = new ArrayList<cobrador>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT codigo,nombre,nacimiento,cedula,estadocivil,conyugue,"
                + "direccion,telefono,celular,mail,comision,"
                + "estado "
                + "FROM cobradores "
                + " ORDER BY cobradores.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cobrador caj = new cobrador();
                caj.setCodigo(rs.getInt("codigo"));
                caj.setNombre(rs.getString("nombre"));
                caj.setNacimiento(rs.getDate("nacimiento"));
                caj.setCedula(rs.getInt("cedula"));
                caj.setEstadocivil(rs.getString("estadocivil"));
                caj.setDireccion(rs.getString("direccion"));
                caj.setTelefono(rs.getString("telefono"));
                caj.setMail(rs.getString("mail"));
                caj.setEstado(rs.getInt("estado"));
                caj.setComision(rs.getBigDecimal("comision"));
                lista.add(caj);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public cobrador buscarId(int id) throws SQLException {
        cobrador caj = new cobrador();
        caj.setCodigo(0);
        caj.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
     String sql = "SELECT codigo,nombre,nacimiento,cedula,estadocivil,conyugue,"
                + "direccion,telefono,celular,mail,comision,"
                + "estado "
                + "FROM cobradores where cobradores.codigo=? "
                + " ORDER BY cobradores.codigo ";

     try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    caj.setCodigo(rs.getInt("codigo"));
                    caj.setNombre(rs.getString("nombre"));
                    caj.setNacimiento(rs.getDate("nacimiento"));
                    caj.setCedula(rs.getInt("cedula"));
                    caj.setEstadocivil(rs.getString("estadocivil"));
                    caj.setDireccion(rs.getString("direccion"));
                    caj.setTelefono(rs.getString("telefono"));
                    caj.setMail(rs.getString("mail"));
                    caj.setEstado(rs.getInt("estado"));
                    caj.setComision(rs.getBigDecimal("comision"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return caj;
    }

  public cobrador insertarCobrador(cobrador ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cobradores (nombre,nacimiento,cedula,estadocivil,conyugue,direccion,telefono,celular,mail,estado) VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, ca.getNombre());
        ps.setDate(2, (java.sql.Date) ca.getNacimiento());
        ps.setInt(3, ca.getCedula());
        ps.setString(4, ca.getEstadocivil());
        ps.setString(5, ca.getConyugue());
        ps.setString(6, ca.getDireccion());
        ps.setString(7, ca.getTelefono());
        ps.setString(8, ca.getCelular());
        ps.setString(9, ca.getMail());
        ps.setInt(10, ca.getEstado());
        
        ps.executeUpdate();
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarCobrador(cobrador ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cobradores SET nombre=?,nacimiento=?,cedula=?,estadocivil=?,conyugue=?,direccion=?,telefono=?,celular=?,mail=?,estado=? WHERE codigo=" + ca.getCodigo());
        ps.setString(1, ca.getNombre());
        ps.setDate(2, (java.sql.Date) ca.getNacimiento());
        ps.setInt(3, ca.getCedula());
        ps.setString(4, ca.getEstadocivil());
        ps.setString(5, ca.getConyugue());
        ps.setString(6, ca.getDireccion());
        ps.setString(7, ca.getTelefono());
        ps.setString(8, ca.getCelular());
        ps.setString(9, ca.getMail());
        ps.setInt(10, ca.getEstado());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarCobrador(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cobradores WHERE codigo=?");
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

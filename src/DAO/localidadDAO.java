/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Usuario
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Conexion.Conexion;
import Modelo.localidad;
import Modelo.tabdep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Webmaster
 */
public class localidadDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<localidad> todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String cSql = "select localidades.codigo,localidades.nombre,localidades.departamento,"
                + "tabdep.nombre AS nombredepartamento "
                + " from localidades "
                + " LEFT JOIN tabdep "
                + " ON tabdep.codigo=localidades.departamento "
                + " order by localidades.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                localidad localidad = new localidad();
                tabdep tabdep = new tabdep();
                localidad.setDepartamento(tabdep);
                localidad.setCodigo(rs.getInt("codigo"));
                localidad.setNombre(rs.getString("nombre"));
                localidad.getDepartamento().setCodigo(rs.getInt("departamento"));
                localidad.getDepartamento().setNombre(rs.getString("nombredepartamento"));
                lista.add(localidad);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public localidad buscarLocalidad(int codlocalidad) throws SQLException {
        localidad localidad = new localidad();
        localidad.setCodigo(0);
        localidad.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "select localidades.codigo,localidades.nombre,localidades.departamento,"
                    + "tabdep.nombre AS nombredepartamento "
                    + "from localidades "
                    + " LEFT JOIN tabdep"
                    + " ON tabdep.codigo=localidades.departamento "
                    + " where localidades.codigo=?"
                    + " order by localidades.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, codlocalidad);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    tabdep tabdep = new tabdep();
                    localidad.setDepartamento(tabdep);
                    localidad.setCodigo(rs.getInt("codigo"));
                    localidad.setNombre(rs.getString("nombre"));
                    localidad.getDepartamento().setCodigo(rs.getInt("departamento"));
                    localidad.getDepartamento().setNombre(rs.getString("nombredepartamento"));
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return localidad;
    }

    public localidad insertarlocalidad(localidad loca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps= st.getConnection().prepareStatement("INSERT INTO localidades (nombre,departamento) VALUES (?,?)");
        ps.setString(1, loca.getNombre());
        ps.setInt(2, loca.getDepartamento().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return loca;
    }

    public boolean actualizarlocalidad(localidad loca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE localidades SET nombre=?,departamento=? WHERE codigo=" + loca.getCodigo());
        ps.setString(1, loca.getNombre());
        ps.setInt(2, loca.getDepartamento().getCodigo());
        
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean eliminarLocalidad(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM localidades WHERE codigo=?");
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

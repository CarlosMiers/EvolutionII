/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.barrio;
import Modelo.localidad;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class barrioDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<barrio> todosxlocalidad(int codloca) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT barrios.codigo,barrios.nombre,barrios.codlocalidad,localidades.nombre as nombrelocalidad "
                + " FROM barrios "
                + " LEFT JOIN localidades "
                + " ON barrios.codlocalidad=localidades.codigo "
                + " WHERE barrios.codlocalidad=? "
                + " ORDER BY barrios.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, codloca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                barrio barrio = new barrio();
                localidad localidad = new localidad();
                barrio.setCodlocalidad(localidad);
                barrio.setCodigo(rs.getInt("codigo"));
                barrio.setNombre(rs.getString("nombre"));
                barrio.getCodlocalidad().setCodigo(rs.getInt("codlocalidad"));
                barrio.getCodlocalidad().setNombre(rs.getString("nombrelocalidad"));
                lista.add(barrio);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

        public ArrayList<barrio> todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String sql = "select barrios.codigo,barrios.nombre,barrios.codlocalidad,localidades.nombre as nombrelocalidad "
                + "from barrios "
                + " LEFT JOIN localidades "
                + " ON barrios.codlocalidad=localidades.codigo "
                + " ORDER BY barrios.codigo";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                barrio barrio = new barrio();
                localidad localidad = new localidad();
                barrio.setCodlocalidad(localidad);
                barrio.setCodigo(rs.getInt("codigo"));
                barrio.setNombre(rs.getString("nombre"));
                barrio.getCodlocalidad().setCodigo(rs.getInt("codlocalidad"));
                barrio.getCodlocalidad().setNombre(rs.getString("nombrelocalidad"));
                lista.add(barrio);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    
    public barrio buscarId(int id) throws SQLException {

        barrio barrio = new barrio();
        localidad localidad = new localidad();
        barrio.setCodigo(0);
        barrio.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select barrios.codigo,barrios.nombre,barrios.codlocalidad,localidades.nombre as nombrelocalidad "
                    + " from barrios "
                    + " LEFT JOIN localidades "
                    + " ON localidades.codigo=barrios.codlocalidad "
                    + " where barrios.codigo = ? "
                    + "order by barrios.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    barrio.setCodlocalidad(localidad);
                    barrio.setCodigo(rs.getInt("codigo"));
                    barrio.setNombre(rs.getString("nombre"));
                    barrio.getCodlocalidad().setCodigo(rs.getInt("codlocalidad"));
                    barrio.getCodlocalidad().setNombre(rs.getString("nombrelocalidad"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return barrio;
    }


    public barrio buscarIdxLocalidad(int id,int idlocalidad) throws SQLException {

        barrio barrio = new barrio();
        localidad localidad = new localidad();
        barrio.setCodigo(0);
        barrio.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT barrios.codigo,barrios.nombre,barrios.codlocalidad,localidades.nombre as nombrelocalidad "
                    + " from barrios "
                    + " LEFT JOIN localidades "
                    + " ON localidades.codigo=barrios.codlocalidad "
                    + " where barrios.codigo = ? "
                    + " AND barrios.codlocalidad = ? "
                    + " ORDER by barrios.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setInt(2, idlocalidad);
                
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    barrio.setCodlocalidad(localidad);
                    barrio.setCodigo(rs.getInt("codigo"));
                    barrio.setNombre(rs.getString("nombre"));
                    barrio.getCodlocalidad().setCodigo(rs.getInt("codlocalidad"));
                    barrio.getCodlocalidad().setNombre(rs.getString("nombrelocalidad"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return barrio;
    }


    public barrio insertarbarrio(barrio ba) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO barrios (nombre,codlocalidad) VALUES (?,?)");
        ps.setString(1, ba.getNombre());
        ps.setInt(2, ba.getCodlocalidad().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ba;
    }

    public boolean actualizarbarrio(barrio ba) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE barrios SET nombre=?,codlocalidad=? WHERE codigo=" + ba.getCodigo());
        ps.setString(1, ba.getNombre());
        ps.setInt(2, ba.getCodlocalidad().getCodigo());

        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarbarrio(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM barrios WHERE codigo=?");
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

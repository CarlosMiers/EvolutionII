/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.concepto_salario;
import Modelo.plan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class concepto_salarioDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<concepto_salario> todos() throws SQLException {
        ArrayList<concepto_salario> lista = new ArrayList<concepto_salario>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT concepto_salarios.codigo,concepto_salarios.nombre,concepto_salarios.tipo,concepto_salarios.estado "
                + " FROM concepto_salarios "
                + " ORDER BY concepto_salarios.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                concepto_salario cs = new concepto_salario();

                cs.setCodigo(rs.getInt("codigo"));
                cs.setNombre(rs.getString("nombre"));
                cs.setTipo(rs.getString("tipo"));
                cs.setEstado(rs.getInt("estado"));
                lista.add(cs);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("-->Todas  " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<concepto_salario> TodoAsentables() throws SQLException {
        ArrayList<concepto_salario> lista = new ArrayList<concepto_salario>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT concepto_salarios.codigo,concepto_salarios.nombre,concepto_salarios.tipo,concepto_salarios.estado "
                + " FROM concepto_salarios "
                + " WHERE concepto_salarios.estado=1 "
                + " ORDER BY concepto_salarios.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                concepto_salario cs = new concepto_salario();

                cs.setCodigo(rs.getInt("codigo"));
                cs.setNombre(rs.getString("nombre"));
                cs.setTipo(rs.getString("tipo"));
                cs.setEstado(rs.getInt("estado"));
                lista.add(cs);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("-->Asentables " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }
    

    public ArrayList<concepto_salario> TodoxTipo(String cTipo) throws SQLException {
        ArrayList<concepto_salario> lista = new ArrayList<concepto_salario>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT concepto_salarios.codigo,concepto_salarios.nombre,concepto_salarios.tipo,concepto_salarios.estado "
                + " FROM concepto_salarios "
                + " WHERE concepto_salarios.estado=1 "
                +" AND concepto_salarios.tipo='"+cTipo+"'"
                + " ORDER BY concepto_salarios.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                concepto_salario cs = new concepto_salario();

                cs.setCodigo(rs.getInt("codigo"));
                cs.setNombre(rs.getString("nombre"));
                cs.setTipo(rs.getString("tipo"));
                cs.setEstado(rs.getInt("estado"));
                lista.add(cs);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("-->Asentables " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }


     public concepto_salario buscarIdxTipo(int id, String cTipo ) throws SQLException {
        concepto_salario concepto_salario = new concepto_salario();
        
        concepto_salario.setCodigo(0);
        concepto_salario.setNombre("");
        concepto_salario.setTipo("");

        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT concepto_salarios.codigo,concepto_salarios.nombre, concepto_salarios.tipo,concepto_salarios.estado,"
                    + "concepto_salarios.idcta,plan.nombre AS nombrecuenta "
                    + "FROM concepto_salarios "
                    + "LEFT JOIN plan "
                    + "ON plan.codigo=concepto_salarios.idcta "
                    + " WHERE concepto_salarios.codigo=? "
                    +" AND concepto_salarios.tipo=?"
                    + "ORDER BY concepto_salarios.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setString(2, cTipo);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    plan p = new plan();
                  
                    concepto_salario.setIdcta(p);
                    
               
                    concepto_salario.setCodigo(rs.getInt("codigo"));
                    concepto_salario.setNombre(rs.getString("nombre"));
                    concepto_salario.setTipo(rs.getString("tipo"));
                    concepto_salario.setNombre(rs.getString("estado"));
                    concepto_salario.getIdcta().setCodigo(rs.getString("idcta"));
                    concepto_salario.getIdcta().setNombre(rs.getString("nombrecuenta"));
                    
                    
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return concepto_salario;
    }

    
    
    
     public concepto_salario buscarId(int id) throws SQLException {
        concepto_salario concepto_salario = new concepto_salario();
        
        concepto_salario.setCodigo(0);
        concepto_salario.setNombre("");
        concepto_salario.setTipo("");

        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT concepto_salarios.codigo,concepto_salarios.nombre, concepto_salarios.tipo,concepto_salarios.estado,"
                    + "concepto_salarios.idcta,plan.nombre AS nombrecuenta "
                    + "FROM concepto_salarios "
                    + "LEFT JOIN plan "
                    + "ON plan.codigo=concepto_salarios.idcta "
                    + " WHERE concepto_salarios.codigo=? "
                    + "ORDER BY concepto_salarios.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    plan p = new plan();
                  
                    concepto_salario.setIdcta(p);
                    
               
                    concepto_salario.setCodigo(rs.getInt("codigo"));
                    concepto_salario.setNombre(rs.getString("nombre"));
                    concepto_salario.setTipo(rs.getString("tipo"));
                    concepto_salario.setNombre(rs.getString("estado"));
                    concepto_salario.getIdcta().setCodigo(rs.getString("idcta"));
                    concepto_salario.getIdcta().setNombre(rs.getString("nombrecuenta"));
                    
                    
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return concepto_salario;
    }

   /* public concepto_salario buscarId(int id) throws SQLException {
        concepto_salario cs = new concepto_salario();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT concepto_salarios.codigo,concepto_salarios.nombre,concepto_salarios.tipo,concepto_salarios.estado "
                    + " FROM concepto_salarios "
                    + " where concepto_salarios.codigo = ? and concepto_salarios.estado=1 "
                    + "order by concepto_salarios.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    cs.setCodigo(rs.getInt("codigo"));
                    cs.setNombre(rs.getString("nombre"));
                    cs.setTipo(rs.getString("tipo"));
                    cs.setEstado(rs.getInt("estado"));

                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->Busqueda " + ex.getLocalizedMessage());
        }
        st.close();
        return cs;
    }*/

    public concepto_salario buscarIdTodos(String id) throws SQLException {
        concepto_salario cs = new concepto_salario();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT concepto_salarios.codigo,concepto_salarios.nombre,concepto_salarios.tipo,concepto_salarios.estado "
                    + " FROM concepto_salarios "
                    + " where concepto_salarios.codigo = ? "
                    + "order by concepto_salarios.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    cs.setCodigo(rs.getInt("codigo"));
                    cs.setNombre(rs.getString("nombre"));
                    cs.setTipo(rs.getString("tipo"));
                    cs.setEstado(rs.getInt("estado"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->Busqueda " + ex.getLocalizedMessage());
        }
        st.close();
        return cs;
    }

    public concepto_salario insertarsalario(concepto_salario csl) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement cs = null;

        cs = st.getConnection().prepareStatement("INSERT INTO concepto_salarios (codigo,nombre,tipo,estado) VALUES (?,?,?,?)");
        cs.setInt(1, csl.getCodigo());
        cs.setString(2, csl.getNombre());
        cs.setString(3, csl.getTipo());
        cs.setInt(4, csl.getEstado());

        cs.executeUpdate();
        st.close();
        cs.close();
        return csl;
    }

    public boolean actualizarSalario(concepto_salario csl) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement cs = null;

        cs = st.getConnection().prepareStatement("UPDATE concepto_salarios SET nombre=?,tipo=?,estado=? WHERE codigo= " + csl.getCodigo());
        // ps.setString(1, pl.getCodigo());
        cs.setString(1, csl.getNombre());
        cs.setString(2, csl.getTipo());
        cs.setInt(3, csl.getEstado());
        int rowsUpdated = cs.executeUpdate();
        st.close();
        cs.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarSalario(String cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement cs = null;

        cs = st.getConnection().prepareStatement("DELETE FROM concepto_salarios WHERE codigo=?");
        cs.setString(1, cod);
        int rowsUpdated = cs.executeUpdate();
        st.close();
        cs.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    
     public boolean EnlaceContableSalario(concepto_salario csl) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE concepto_salarios SET idcta=? WHERE codigo=" + csl.getCodigo());
        ps.setString(1, csl.getIdcta().getCodigo());
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

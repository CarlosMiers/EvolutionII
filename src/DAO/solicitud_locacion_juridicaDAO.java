/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.solicitud_locacion_juridica;
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
public class solicitud_locacion_juridicaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<solicitud_locacion_juridica> muestraxjuridica(int juridica) throws SQLException {
        ArrayList<solicitud_locacion_juridica> lista = new ArrayList<solicitud_locacion_juridica>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT solicitud_loc_juridica.idcontrato, solicitud_loc_juridica.idcliente"
                    + " FROM solicitud_loc_juridica  "
                    + " LEFT JOIN clientes "
                    + " ON  clientes.codigo=solicitud_loc_juridica.idcliente "
                    + " WHERE solicitud_loc_juridica.idcontrato= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, juridica);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    solicitud_locacion_juridica slj = new solicitud_locacion_juridica();
                    cliente cl = new cliente();

                    slj.setCliente(cl);

                    slj.setIdcontrato(rs.getInt("idcontrato"));
                    slj.getCliente().setCodigo(rs.getInt("idcliente"));
                    slj.getCliente().setNombre(rs.getString("nombre"));
                    lista.add(slj);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<solicitud_locacion_juridica> todos(String id) throws SQLException {
        ArrayList<solicitud_locacion_juridica> lista = new ArrayList<solicitud_locacion_juridica>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT solicitud_loc_juridica.idcontrato, solicitud_loc_juridica.idcliente"
                + " FROM solicitud_loc_juridica  "
                + " LEFT JOIN clientes "
                + " ON  clientes.codigo=solicitud_loc_juridica.idcliente "
                + "  WHERE solicitud_loc_juridica.idcontrato= ? ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                solicitud_locacion_juridica slj = new solicitud_locacion_juridica();
                cliente cl = new cliente();

                slj.setCliente(cl);

                slj.setIdcontrato(rs.getInt("idcontrato"));
                slj.getCliente().setCodigo(rs.getInt("idcliente"));
                slj.getCliente().setNombre(rs.getString("nombre"));
                lista.add(slj);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public solicitud_locacion_juridica insertarjuridica(solicitud_locacion_juridica slj) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO solicitud_loc_juridica (idcontrato,idcliente) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, slj.getIdcontrato());
        ps.setInt(2, slj.getCliente().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return slj;
    }
    
    public ArrayList<solicitud_locacion_juridica> MostrarxJuridica(int id) throws SQLException {
        ArrayList<solicitud_locacion_juridica> lista = new ArrayList<solicitud_locacion_juridica>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT solicitud_loc_juridica.idcliente,clientes.nombre"
                    + " FROM solicitud_loc_juridica "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=solicitud_loc_juridica.idcliente "
                    + " WHERE solicitud_loc_juridica.idcontrato= ? "
                    + " ORDER BY solicitud_loc_juridica.idcontrato";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    solicitud_locacion_juridica slj = new solicitud_locacion_juridica();
                    
                    cliente cli = new cliente();
                    slj.setCliente(cli);

                  
                    slj.setIdcliente(rs.getInt("idcliente"));
                    slj.getCliente().setNombre(rs.getString("nombre"));
                    System.out.println(rs.getInt("idcliente"));
                    System.out.println(rs.getString("nombre"));
                    
                    lista.add(slj);
                }
                ps.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarjuridica(int idcontrato, int idcliente) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM solicitud_loc_juridica WHERE idcontrato=? and idcliente=? ");
        ps.setInt(1, idcontrato);
        ps.setInt(2, idcliente);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

  
   
    public boolean borrarJuridica(int idcontrato) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM solicitud_loc_juridica WHERE idcontrato=? ");
        ps.setInt(1, idcontrato);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
}



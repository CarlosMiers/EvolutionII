/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.solicitud_locacion_propietario;
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
public class solicitud_locacion_propietarioDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<solicitud_locacion_propietario> muestraxpropietario(int propietario) throws SQLException {
        ArrayList<solicitud_locacion_propietario> lista = new ArrayList<solicitud_locacion_propietario>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT solicitud_loc_propietario.idcontrato, solicitud_loc_propietario.idcliente"
                    + " FROM solicitud_loc_propietario  "
                    + " LEFT JOIN clientes "
                    + " ON  clientes.codigo=solicitud_loc_propietario.idcliente "
                    + " WHERE solicitud_loc_propietario.idcontrato= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, propietario);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    solicitud_locacion_propietario slp = new solicitud_locacion_propietario();
                    cliente cl = new cliente();

                    slp.setCliente(cl);

                    slp.setIdcontrato(rs.getInt("idcontrato"));
                    slp.getCliente().setCodigo(rs.getInt("idcliente"));
                    slp.getCliente().setNombre(rs.getString("nombre"));
                    lista.add(slp);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<solicitud_locacion_propietario> todos(String id) throws SQLException {
        ArrayList<solicitud_locacion_propietario> lista = new ArrayList<solicitud_locacion_propietario>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT solicitud_loc_propietario.idcontrato, solicitud_loc_propietario.idcliente"
                + " FROM solicitud_loc_propietario  "
                + " LEFT JOIN clientes "
                + " ON  clientes.codigo=solicitud_loc_propietario.idcliente "
                + "  WHERE solicitud_loc_propietario.idcontrato= ? ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                solicitud_locacion_propietario slp = new solicitud_locacion_propietario();
                cliente cl = new cliente();

                slp.setCliente(cl);

                slp.setIdcontrato(rs.getInt("idcontrato"));
                slp.getCliente().setCodigo(rs.getInt("idcliente"));
                slp.getCliente().setNombre(rs.getString("nombre"));
                lista.add(slp);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public solicitud_locacion_propietario insertarpropietario(solicitud_locacion_propietario slp) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO solicitud_loc_propietario (idcontrato,idcliente) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, slp.getIdcontrato());
        ps.setInt(2, slp.getCliente().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return slp;
    }
    
    public ArrayList<solicitud_locacion_propietario> MostrarxPropietario(int id) throws SQLException {
        ArrayList<solicitud_locacion_propietario> lista = new ArrayList<solicitud_locacion_propietario>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT solicitud_loc_propietario.idcliente,clientes.nombre"
                    + " FROM solicitud_loc_propietario "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=solicitud_loc_propietario.idcliente "
                    + " WHERE solicitud_loc_propietario.idcontrato= ? "
                    + " ORDER BY solicitud_loc_propietario.idcontrato";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    solicitud_locacion_propietario slp = new solicitud_locacion_propietario();
                    
                    cliente cli = new cliente();
                    slp.setCliente(cli);

                  
                    slp.setIdcliente(rs.getInt("idcliente"));
                    slp.getCliente().setNombre(rs.getString("nombre"));
                    System.out.println(rs.getInt("idcliente"));
                    System.out.println(rs.getString("nombre"));
                    
                    lista.add(slp);
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

    public boolean borrarpropietario(int idcontrato, int idcliente) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM personas_juridica WHERE idcontrato=? and idcliente=? ");
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

  
   
    public boolean borrarPropietario(int idcontrato) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM solicitud_loc_propietario WHERE idcontrato=? ");
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



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.solicitud_locacion_codeudor;
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
public class solicitud_locacion_codeudorDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<solicitud_locacion_codeudor> muestraxcodeudor(int codeudor) throws SQLException {
        ArrayList<solicitud_locacion_codeudor> lista = new ArrayList<solicitud_locacion_codeudor>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT garante_alquiler.idcontrato, garante_alquiler.idcliente, clientes.codigo, clientes.ruc, clientes.nombre"
                    + " FROM garante_alquiler  "
                    + " LEFT JOIN clientes "
                    + " ON  clientes.codigo=garante_alquiler.idcliente "
                    + " WHERE garante_alquiler.idcontrato= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, codeudor);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    solicitud_locacion_codeudor slc = new solicitud_locacion_codeudor();
                    cliente cl = new cliente();

                    slc.setCliente(cl);

                    slc.setIdcontrato(rs.getInt("idcontrato"));
                    slc.getCliente().setCodigo(rs.getInt("idcliente"));
                    slc.getCliente().setNombre(rs.getString("nombre"));
                    lista.add(slc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<solicitud_locacion_codeudor> todos(String id) throws SQLException {
        ArrayList<solicitud_locacion_codeudor> lista = new ArrayList<solicitud_locacion_codeudor>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT garante_alquiler.idcontrato, garante_alquiler.idcliente, clientes.codigo, clientes.nombre"
                + " FROM garante_alquiler  "
                + " LEFT JOIN clientes "
                + " ON  clientes.codigo=garante_alquiler.idcliente "
                + "  WHERE garante_alquiler.idcontrato= ? ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                solicitud_locacion_codeudor slc = new solicitud_locacion_codeudor();
                cliente cl = new cliente();

                slc.setCliente(cl);

                slc.setIdcontrato(rs.getInt("idcontrato"));
                slc.getCliente().setCodigo(rs.getInt("idcliente"));
                slc.getCliente().setNombre(rs.getString("nombre"));
                lista.add(slc);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public solicitud_locacion_codeudor insertarcodeudor(solicitud_locacion_codeudor slg) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO garante_alquiler (idcontrato,idcliente) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, slg.getIdcontrato());
        ps.setInt(2, slg.getCliente().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return slg;
    }

    public boolean borrarcodeudor(int idcontrato, int idcliente) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM garante_alquiler WHERE idcontrato=? and idcliente=? ");
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

    public boolean borrarcodeudorSL(String idcontrato) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM garante_alquiler WHERE idcontrato=?");
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
    
     public ArrayList<solicitud_locacion_codeudor> MostrarDetalle(int id) throws SQLException {
        ArrayList<solicitud_locacion_codeudor> lista = new ArrayList<solicitud_locacion_codeudor>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT garante_alquiler.idcliente,clientes.nombre"
                    + " FROM garante_alquiler "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=garante_alquiler.idcliente "
                    + " WHERE garante_alquiler.idcontrato= ? "
                    + " ORDER BY garante_alquiler.idcontrato";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    solicitud_locacion_codeudor dt = new solicitud_locacion_codeudor();
                    
                    cliente cli = new cliente();
                    dt.setCliente(cli);

                  
                    dt.setIdcliente(rs.getInt("idcliente"));
                    dt.getCliente().setNombre(rs.getString("nombre"));
                    System.out.println(rs.getInt("idcliente"));
                    System.out.println(rs.getString("nombre"));
                    
                    lista.add(dt);
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

    public boolean borrarDetalle(int idcontrato) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM garante_alquiler WHERE idcontrato=? ");
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



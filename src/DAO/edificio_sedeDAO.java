/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.edificio_sede;
import Modelo.sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class edificio_sedeDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<edificio_sede> todos() throws SQLException {
        ArrayList<edificio_sede> lista = new ArrayList<edificio_sede>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT edificio_sedes.codigo,edificio_sedes.nombre,"
                + "edificio_sedes.idsede,edificio_sedes.direccion,"
                + "edificio_sedes.telefono,edificio_sedes.nropisos,"
                + "edificio_sedes.nroaulas,edificio_sedes.estado,"
                + "sucursales.nombre AS nombresucursal "
                + " FROM edificio_sedes "
                + " LEFT JOIN sucursales "
                + " ON sucursales.codigo=edificio_sedes.idsede "
                + " ORDER BY edificio_sedes.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                edificio_sede edif = new edificio_sede();
                sucursal suc = new sucursal();
                edif.setIdsede(suc);
                edif.getIdsede().setCodigo(rs.getInt("idsede"));
                edif.getIdsede().setNombre(rs.getString("nombresucursal"));
                edif.setCodigo(rs.getInt("codigo"));
                edif.setNombre(rs.getString("nombre"));
                edif.setDireccion(rs.getString("direccion"));
                edif.setTelefono(rs.getString("telefono"));
                edif.setNropisos(rs.getInt("nropisos"));
                edif.setNroaulas(rs.getInt("nroaulas"));
                edif.setEstado(rs.getInt("estado"));
                lista.add(edif);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public edificio_sede buscarId(int id) throws SQLException {
        edificio_sede edif = new edificio_sede();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT edificio_sedes.codigo,edificio_sedes.nombre,"
                    + "edificio_sedes.idsede,edificio_sedes.direccion,"
                    + "edificio_sedes.telefono,edificio_sedes.nropisos,"
                    + "edificio_sedes.nroaulas,edificio_sedes.estado,"
                    + "sucursales.nombre AS nombresucursal "
                    + " FROM edificio_sedes "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=edificio_sedes.idsede "
                    + " WHERE edificio_sedes.codigo =? "
                    + " ORDER BY edificio_sedes.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    sucursal suc = new sucursal();
                    edif.setIdsede(suc);
                    edif.getIdsede().setCodigo(rs.getInt("idsede"));
                    edif.getIdsede().setNombre(rs.getString("nombresucursal"));
                    edif.setCodigo(rs.getInt("codigo"));
                    edif.setNombre(rs.getString("nombre"));
                    edif.setDireccion(rs.getString("direccion"));
                    edif.setTelefono(rs.getString("telefono"));
                    edif.setNropisos(rs.getInt("nropisos"));
                    edif.setNroaulas(rs.getInt("nroaulas"));
                    edif.setEstado(rs.getInt("estado"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return edif;
    }

    public edificio_sede insertarEdificio(edificio_sede ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO edificio_sedes "
                + "(nombre,idsede,direccion,telefono,nropisos,nroaulas,estado)"
                + "VALUES (?,?,?,?,?,?,?)");
        ps.setString(1, ca.getNombre());
        ps.setInt(2, ca.getIdsede().getCodigo());
        ps.setString(3, ca.getDireccion());
        ps.setString(4, ca.getTelefono());
        ps.setInt(5, ca.getNropisos());
        ps.setInt(6, ca.getNroaulas());
        ps.setInt(7, ca.getEstado());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarEdificio(edificio_sede ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE edificio_sedes "
                + "SET nombre=?,idsede=?,direccion=?,telefono=?,"
                + "nropisos=?,nroaulas=?,estado=?"
                + " WHERE codigo=" + ca.getCodigo());
        ps.setString(1, ca.getNombre());
        ps.setInt(2, ca.getIdsede().getCodigo());
        ps.setString(3, ca.getDireccion());
        ps.setString(4, ca.getTelefono());
        ps.setInt(5, ca.getNropisos());
        ps.setInt(6, ca.getNroaulas());
        ps.setInt(7, ca.getEstado());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarEdificio(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM edificio_sedes WHERE codigo=?");
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

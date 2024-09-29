/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ficha_empleado;
import Modelo.sancion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author Pc_Server
 */
public class sancionDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<sancion> Todos(Date dFechaIni, Date dFechaFin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT sanciones.numero,sanciones.fecha,empleados.codigo AS codempleado,"
                + "CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,"
                + "sanciones.dias,sanciones.inicio,sanciones.fin,"
                + "sanciones.tipo_sancion,sanciones.idsucursal, "
                + "sanciones.observacion,sanciones.tipo_sancion "
                + " FROM sanciones "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=sanciones.funcionario "
                + " WHERE  sanciones.fecha between ? AND ? "
                + " ORDER BY sanciones.numero ";

        System.out.println(sql);

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechaIni);
            ps.setDate(2, dFechaFin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sancion sancion = new sancion();
                ficha_empleado ficha = new ficha_empleado();
                sancion.setFicha_empleado(ficha);
                sancion.setNumero(rs.getDouble("numero"));
                sancion.setFecha(rs.getDate("fecha"));
                sancion.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                sancion.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                sancion.setDias(rs.getInt("dias"));
                sancion.setInicio(rs.getDate("inicio"));
                sancion.setFin(rs.getDate("fin"));
                sancion.setTipo_sancion(rs.getInt("tipo_sancion"));
                sancion.setObservacion(rs.getString("observacion"));
                lista.add(sancion);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public sancion buscarId(int id) throws SQLException {

        sancion sancion = new sancion();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT sanciones.numero,sanciones.fecha,empleados.codigo AS codempleado,"
                    + "CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,"
                    + "sanciones.dias,sanciones.inicio,sanciones.fin,"
                    + "sanciones.tipo_sancion,sanciones.idsucursal, "
                    + "sanciones.observacion,sanciones.tipo_sancion "
                    + " FROM sanciones "
                    + "INNER JOIN empleados "
                    + "ON empleados.codigo=sanciones.funcionario "
                    + " WHERE  sanciones.numero=? "
                    + " ORDER BY sanciones.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ficha_empleado ficha = new ficha_empleado();
                    sancion.setFicha_empleado(ficha);
                    sancion.setFicha_empleado(ficha);
                    sancion.setNumero(rs.getDouble("numero"));
                    sancion.setFecha(rs.getDate("fecha"));
                    sancion.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                    sancion.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                    sancion.setDias(rs.getInt("dias"));
                    sancion.setInicio(rs.getDate("inicio"));
                    sancion.setFin(rs.getDate("fin"));
                    sancion.setTipo_sancion(rs.getInt("tipo_sancion"));
                    sancion.setObservacion(rs.getString("observacion"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return sancion;
    }

    public sancion insertarsancion(sancion sancion) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO sanciones(fecha,funcionario,dias,"
                + "observacion,inicio,fin,idsucursal,tipo_sancion) VALUES (?,?,?,?,?,?,?,?)");
        ps.setDate(1, sancion.getFecha());
        ps.setInt(2, sancion.getFicha_empleado().getCodigo());
        ps.setInt(3, sancion.getDias());
        ps.setString(4, sancion.getObservacion());
        ps.setDate(5, sancion.getInicio());
        ps.setDate(6, sancion.getFin());
        ps.setInt(7, sancion.getGiraduria());
        ps.setInt(8, sancion.getTipo_sancion());
        ps.executeUpdate();
        st.close();
        ps.close();
        return sancion;
    }

    public boolean actualizarsancion(sancion sancion) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE sanciones SET fecha=?,"
                + "funcionario=?,dias=?,observacion=?,inicio=?,fin=?,idsucursal=?, tipo_sancion=? WHERE numero=" + sancion.getNumero());
        ps.setDate(1, sancion.getFecha());
        ps.setInt(2, sancion.getFicha_empleado().getCodigo());
        ps.setInt(3, sancion.getDias());
        ps.setString(4, sancion.getObservacion());
        ps.setDate(5, sancion.getInicio());
        ps.setDate(6, sancion.getFin());
        ps.setInt(7, sancion.getGiraduria());
        ps.setInt(8, sancion.getTipo_sancion());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarsancion(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM sanciones WHERE numero=?");
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

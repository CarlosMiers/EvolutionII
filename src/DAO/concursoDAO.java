/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cargo;
import Modelo.concurso;
import Modelo.vacancias;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class concursoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<concurso> todos(Date dFechaIni, Date dFechaFin) throws SQLException {
        ArrayList<concurso> lista = new ArrayList<concurso>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT concursos.idconcurso, concursos.fecha,"
                + "concursos.limitedesde,concursos.limitehasta,"
                + "concursos.idvacancia,concursos.tipo_concurso,"
                + "vacancias.perfil,vacancias.descripcion,"
                + "vacancias.nombrepuesto "
                + "FROM concursos "
                + "LEFT JOIN vacancias "
                + "ON vacancias.numero=concursos.idvacancia "
                + " WHERE  concursos.fecha between ? AND ? "
                + " ORDER BY idconcurso";

        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechaIni);
            ps.setDate(2, dFechaFin);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                concurso co = new concurso();
                vacancias vac = new vacancias();
                co.setIdvacancia(vac);
                co.setIdconcurso(rs.getDouble("idconcurso"));
                co.setFecha(rs.getDate("fecha"));
                co.getIdvacancia().setNumero(rs.getDouble("idvacancia"));
                co.getIdvacancia().setDescripcion(rs.getString("descripcion"));
                co.getIdvacancia().setNombrepuesto(rs.getString("nombrepuesto"));
                co.setLimitedesde(rs.getDate("limitedesde"));
                co.setLimitehasta(rs.getDate("limitehasta"));
                co.setTipo_concurso(rs.getInt("tipo_concurso"));
                lista.add(co);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<concurso> todosActivo(Date inicio) throws SQLException {
        ArrayList<concurso> lista = new ArrayList<concurso>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT concursos.idconcurso, concursos.fecha,"
                + "concursos.limitedesde,concursos.limitehasta,"
                + "concursos.idvacancia,concursos.tipo_concurso,"
                + "vacancias.perfil,vacancias.descripcion,"
                + "vacancias.nombrepuesto "
                + "FROM concursos "
                + "LEFT JOIN vacancias "
                + "ON vacancias.numero=concursos.idvacancia "
                + " WHERE  concursos.limitehasta>? "
                + " ORDER BY idconcurso";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, inicio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                concurso co = new concurso();
                vacancias vac = new vacancias();
                co.setIdvacancia(vac);
                co.setIdconcurso(rs.getDouble("idconcurso"));
                co.setFecha(rs.getDate("fecha"));
                co.getIdvacancia().setNumero(rs.getDouble("idvacancia"));
                co.getIdvacancia().setDescripcion(rs.getString("descripcion"));
                co.getIdvacancia().setNombrepuesto(rs.getString("nombrepuesto"));
                co.setLimitedesde(rs.getDate("limitedesde"));
                co.setLimitehasta(rs.getDate("limitehasta"));
                co.setTipo_concurso(rs.getInt("tipo_concurso"));
                lista.add(co);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public concurso buscarId(double id, Date inicio) throws SQLException {
        concurso co = new concurso();
        cargo ca = new cargo();
        vacancias vac = new vacancias();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT concursos.idconcurso, concursos.fecha,"
                    + "concursos.limitedesde,concursos.limitehasta,"
                    + "concursos.idvacancia,concursos.tipo_concurso,"
                    + "vacancias.perfil,vacancias.descripcion,vacancias.nombrepuesto "
                    + "FROM concursos "
                    + "LEFT JOIN vacancias "
                    + "ON vacancias.numero=concursos.idvacancia "
                    + " WHERE concursos.idconcurso = ? "
                    + " AND  concursos.limitehasta>? "
                    + " ORDER BY idconcurso";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ps.setDate(2, inicio);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    co.setIdvacancia(vac);
                    co.setIdconcurso(rs.getDouble("idconcurso"));
                    co.setFecha(rs.getDate("fecha"));
                    co.getIdvacancia().setNumero(rs.getDouble("idvacancia"));
                    co.getIdvacancia().setDescripcion(rs.getString("descripcion"));
                    co.getIdvacancia().setNombrepuesto(rs.getString("nombrepuesto"));
                    co.setLimitedesde(rs.getDate("limitedesde"));
                    co.setLimitehasta(rs.getDate("limitehasta"));
                    co.setTipo_concurso(rs.getInt("tipo_concurso"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return co;
    }

    public concurso buscarIdEdicion(double id) throws SQLException {
        concurso co = new concurso();
        cargo ca = new cargo();
        vacancias vac = new vacancias();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT concursos.idconcurso, concursos.fecha,"
                    + "concursos.limitedesde,concursos.limitehasta,"
                    + "concursos.idvacancia,concursos.tipo_concurso,"
                    + "vacancias.perfil,vacancias.descripcion,vacancias.nombrepuesto "
                    + "FROM concursos "
                    + "LEFT JOIN vacancias "
                    + "ON vacancias.numero=concursos.idvacancia "
                    + " WHERE concursos.idconcurso = ? "
                    + " ORDER BY idconcurso";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    co.setIdvacancia(vac);
                    co.setIdconcurso(rs.getDouble("idconcurso"));
                    co.setFecha(rs.getDate("fecha"));
                    co.getIdvacancia().setNumero(rs.getDouble("idvacancia"));
                    co.getIdvacancia().setDescripcion(rs.getString("descripcion"));
                    co.getIdvacancia().setNombrepuesto(rs.getString("nombrepuesto"));
                    co.setLimitedesde(rs.getDate("limitedesde"));
                    co.setLimitehasta(rs.getDate("limitehasta"));
                    co.setTipo_concurso(rs.getInt("tipo_concurso"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return co;
    }

    public concurso insertarConcurso(concurso ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO concursos "
                + "(fecha,limitedesde,limitehasta,idvacancia,tipo_concurso)"
                + "VALUES (?,?,?,?,?)");
        ps.setDate(1, ca.getFecha());
        ps.setDate(2, ca.getLimitedesde());
        ps.setDate(3, ca.getLimitehasta());
        ps.setDouble(4, ca.getIdvacancia().getNumero());
        ps.setInt(5, ca.getTipo_concurso());
        try {
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Registro no fue Guardado");
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarConcurso(concurso ca) throws SQLException {
        int rowsUpdated = 0;
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE concursos "
                + " SET fecha=?,limitedesde=?,limitehasta=?,idvacancia=?,"
                + "tipo_concurso=? WHERE idconcurso=" + ca.getIdconcurso());
        ps.setDate(1, ca.getFecha());
        ps.setDate(2, ca.getLimitedesde());
        ps.setDate(3, ca.getLimitehasta());
        ps.setDouble(4, ca.getIdvacancia().getNumero());
        ps.setInt(5, ca.getTipo_concurso());
        try {
            rowsUpdated = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            JOptionPane.showMessageDialog(null, "Es posible que la Vacancia ya haya sido utilizada");
        }
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarConcurso(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM concursos WHERE idconcurso=?");
        ps.setDouble(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public concurso ValidarCantidad(double id, double idpostulante) throws SQLException {
        concurso co = new concurso();
        cargo ca = new cargo();
        vacancias vac = new vacancias();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT concursos.idconcurso,concurso,vacancias.disponible,postulantes.estado,"
                    + "concursos.idvacancia,vacancias.nombrepuesto,"
                    + "IF( postulantes.estado=4,COUNT(postulantes.estado),0) AS postula "
                    + " FROM postulantes "
                    + " INNER JOIN concursos "
                    + " ON concursos.idconcurso=postulantes.concurso "
                    + " INNER JOIN vacancias "
                    + " ON vacancias.numero=concursos.idvacancia "
                    + " WHERE  concursos.idconcurso=? AND postulantes.codigo<>? "
                    + " GROUP BY concurso ";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ps.setDouble(2, idpostulante);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    co.setIdvacancia(vac);
                    co.setIdconcurso(rs.getDouble("idconcurso"));
                    co.getIdvacancia().setNumero(rs.getDouble("idvacancia"));
                    co.getIdvacancia().setDisponible(rs.getInt("disponible"));
                    co.getIdvacancia().setCupos(rs.getInt("postula"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return co;
    }

}

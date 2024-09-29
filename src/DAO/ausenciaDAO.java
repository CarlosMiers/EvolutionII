/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ausencia;
import Modelo.ficha_empleado;
import Modelo.motivo_ausencia;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class ausenciaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<ausencia> Todos(Date ini, Date fin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT ausencias.numero,ausencias.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,CONCAT(ausencias.dias,' ',ausencias.unidmed) AS tiempo, "
                + "ausencias.dias,motivo_ausencias.Codigo AS codausencia, motivo_ausencias.nombre AS nombreausencia,ausencias.importe,ausencias.unidmed, ausencias.justificado "
                + "FROM ausencias  "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=ausencias.funcionario "
                + "INNER JOIN motivo_ausencias "
                + "ON motivo_ausencias.Codigo=ausencias.ausencia "
                + " WHERE  ausencias.fecha between ? AND ?"
                + "ORDER BY ausencias.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, ini);
            ps.setDate(2, fin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                ausencia aus = new ausencia();
                ficha_empleado ficha = new ficha_empleado();
                motivo_ausencia mot = new motivo_ausencia();

                aus.setFicha_empleado(ficha);
                aus.setMotivo_ausencia(mot);

                aus.setNumero(rs.getString("numero"));
                aus.setFecha(rs.getDate("fecha"));
                aus.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                aus.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                aus.setDias(rs.getInt("dias"));
                aus.getMotivo_ausencia().setCodigo(rs.getInt("codausencia"));
                aus.getMotivo_ausencia().setNombre(rs.getString("nombreausencia"));
                aus.setImporte(rs.getBigDecimal("importe"));
                aus.setUnidmed(rs.getString("unidmed"));
                aus.setJustificado(rs.getString("justificado"));
                aus.setTiempo(rs.getString("tiempo"));

                lista.add(aus);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }


    public ArrayList<ausencia> TodosxFecha(Date ini, Date fin, Integer suc) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT ausencias.numero,ausencias.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,CONCAT(ausencias.dias,' ',ausencias.unidmed) AS tiempo, "
                + "ausencias.dias,motivo_ausencias.Codigo AS codausencia, motivo_ausencias.nombre AS nombreausencia,ausencias.importe,ausencias.unidmed, ausencias.justificado "
                + "FROM ausencias  "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=ausencias.funcionario "
                + "INNER JOIN motivo_ausencias "
                + "ON motivo_ausencias.Codigo=ausencias.ausencia "
                + " WHERE  ausencias.fecha between ? AND ? "
                + " AND ausencias.idsucursal=? "
                + " ORDER BY ausencias.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, ini);
            ps.setDate(2, fin);
            ps.setInt(3, suc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                ausencia aus = new ausencia();
                ficha_empleado ficha = new ficha_empleado();
                motivo_ausencia mot = new motivo_ausencia();

                aus.setFicha_empleado(ficha);
                aus.setMotivo_ausencia(mot);

                aus.setNumero(rs.getString("numero"));
                aus.setFecha(rs.getDate("fecha"));
                aus.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                aus.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                aus.setDias(rs.getInt("dias"));
                aus.getMotivo_ausencia().setCodigo(rs.getInt("codausencia"));
                aus.getMotivo_ausencia().setNombre(rs.getString("nombreausencia"));
                aus.setImporte(rs.getBigDecimal("importe"));
                aus.setUnidmed(rs.getString("unidmed"));
                aus.setJustificado(rs.getString("justificado"));
                aus.setTiempo(rs.getString("tiempo"));

                lista.add(aus);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ausencia buscarId(int id) throws SQLException {

        ausencia aus = new ausencia();
        ficha_empleado ficha = new ficha_empleado();
        motivo_ausencia mot = new motivo_ausencia();

        aus.setFicha_empleado(ficha);
        aus.setMotivo_ausencia(mot);

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT ausencias.numero,ausencias.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,CONCAT(ausencias.dias,' ',ausencias.unidmed) AS tiempo, "
                    + "ausencias.dias,motivo_ausencias.Codigo AS codausencia, motivo_ausencias.nombre AS nombreausencia,ausencias.importe, ausencias.unidmed, ausencias.justificado, "
                    + "empleados.salario,empleados.tipo_salario "
                    + "FROM ausencias "
                    + "INNER JOIN empleados "
                    + "ON empleados.codigo=ausencias.funcionario "
                    + "INNER JOIN motivo_ausencias "
                    + "ON motivo_ausencias.Codigo=ausencias.ausencia "
                    + "WHERE ausencias.numero=? "
                    + "ORDER BY ausencias.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    aus.setNumero(rs.getString("numero"));
                    aus.setFecha(rs.getDate("fecha"));
                    aus.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                    aus.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                    aus.getFicha_empleado().setSalario(rs.getBigDecimal("salario"));
                    aus.getFicha_empleado().setTipo_salario(rs.getInt("tipo_salario"));
                    aus.getMotivo_ausencia().setCodigo(rs.getInt("codausencia"));
                    aus.getMotivo_ausencia().setNombre(rs.getString("nombreausencia"));
                    aus.setDias(rs.getInt("dias"));
                    aus.setImporte(rs.getBigDecimal("importe"));
                    aus.setUnidmed(rs.getString("unidmed"));
                    aus.setJustificado(rs.getString("justificado"));
                    aus.setTiempo(rs.getString("tiempo"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return aus;
    }

    public ausencia insertarausencia(ausencia aus) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO ausencias(fecha,funcionario,ausencia,"
                + "dias,importe, unidmed, justificado,idsucursal) VALUES (?,?,?,?,?,?,?,?)");
        ps.setDate(1, aus.getFecha());
        ps.setInt(2, aus.getFicha_empleado().getCodigo());
        ps.setInt(3, aus.getMotivo_ausencia().getCodigo());
        ps.setInt(4, aus.getDias());
        ps.setBigDecimal(5, aus.getImporte());
        ps.setString(6, aus.getUnidmed());
        ps.setString(7, aus.getJustificado());
        ps.setInt(8,aus.getGiraduria());
        ps.executeUpdate();
        st.close();
        ps.close();
        return aus;
    }

    public boolean actualizarausencia(ausencia aus) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE ausencias SET fecha=?,funcionario=?,"
                + "ausencia=?,dias=?,importe=?,unidmed=?,justificado=?,idsucursal=? "
                + " WHERE numero=" + aus.getNumero());
        ps.setDate(1, aus.getFecha());
        ps.setInt(2, aus.getFicha_empleado().getCodigo());
        ps.setInt(3, aus.getMotivo_ausencia().getCodigo());
        ps.setInt(4, aus.getDias());
        ps.setBigDecimal(5, aus.getImporte());
        ps.setString(6, aus.getUnidmed());
        ps.setString(7, aus.getJustificado());
        ps.setInt(8, aus.getGiraduria());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarausencia(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM ausencias WHERE numero=?");
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

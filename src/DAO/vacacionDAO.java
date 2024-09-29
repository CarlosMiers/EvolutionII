/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ficha_empleado;
import Modelo.vacacion;

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
public class vacacionDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<vacacion> Todos(Date dFechaIni, Date dFechaFin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT vacaciones.numero,vacaciones.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,"
                + "CONCAT(vacaciones.horas,' ', vacaciones.unidmed) AS tiempo, vacaciones.horas,vacaciones.unidmed,vacaciones.importe,"
                + "vacaciones.inicio,vacaciones.fin "
                + "FROM vacaciones "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=vacaciones.funcionario "
                + " WHERE  vacaciones.fecha between ? AND ? "
                + " ORDER BY vacaciones.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechaIni);
            ps.setDate(2, dFechaFin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                vacacion vacacion = new vacacion();
                ficha_empleado ficha = new ficha_empleado();
                vacacion.setFicha_empleado(ficha);

                vacacion.setNumero(rs.getDouble("numero"));
                vacacion.setFecha(rs.getDate("fecha"));
                vacacion.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                vacacion.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                vacacion.setHoras(rs.getInt("horas"));
                vacacion.setUnidmed(rs.getString("unidmed"));
                vacacion.setImporte(rs.getBigDecimal("importe"));
                vacacion.setInicio(rs.getDate("inicio"));
                vacacion.setFin(rs.getDate("fin"));
                lista.add(vacacion);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<vacacion> TodosxSuc(Date dFechaIni, Date dFechaFin, Integer suc) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT vacaciones.numero,vacaciones.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,"
                + "CONCAT(vacaciones.horas,' ', vacaciones.unidmed) AS tiempo, vacaciones.horas,vacaciones.unidmed,vacaciones.importe,"
                + "vacaciones.inicio,vacaciones.fin "
                + "FROM vacaciones "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=vacaciones.funcionario "
                + " WHERE  vacaciones.fecha between ? AND ? "
                + " AND vacaciones.idsucursal= ?"
                + " ORDER BY vacaciones.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechaIni);
            ps.setDate(2, dFechaFin);
            ps.setInt(3, suc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                vacacion vacacion = new vacacion();
                ficha_empleado ficha = new ficha_empleado();
                vacacion.setFicha_empleado(ficha);

                vacacion.setNumero(rs.getDouble("numero"));
                vacacion.setFecha(rs.getDate("fecha"));
                vacacion.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                vacacion.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                vacacion.setHoras(rs.getInt("horas"));
                vacacion.setUnidmed(rs.getString("unidmed"));
                vacacion.setImporte(rs.getBigDecimal("importe"));
                vacacion.setInicio(rs.getDate("inicio"));
                vacacion.setFin(rs.getDate("fin"));
                lista.add(vacacion);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public vacacion buscarId(int id) throws SQLException {

        vacacion vacacion = new vacacion();
        ficha_empleado ficha = new ficha_empleado();
        vacacion.setFicha_empleado(ficha);

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT vacaciones.numero,vacaciones.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado, "
                    + "CONCAT(vacaciones.horas,' ', vacaciones.unidmed) AS tiempo, vacaciones.horas,vacaciones.unidmed,vacaciones.importe,"
                    + "empleados.salario,empleados.tipo_salario, "
                    + "vacaciones.inicio,vacaciones.fin,vacaciones.periodo "
                    + " FROM vacaciones "
                    + "INNER JOIN empleados "
                    + "ON empleados.codigo=vacaciones.funcionario "
                    + "WHERE vacaciones.numero=? "
                    + "ORDER BY vacaciones.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    vacacion.setNumero(rs.getDouble("numero"));
                    vacacion.setFecha(rs.getDate("fecha"));
                    vacacion.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                    vacacion.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                    vacacion.getFicha_empleado().setSalario(rs.getBigDecimal("salario"));
                    vacacion.getFicha_empleado().setTipo_salario(rs.getInt("tipo_salario"));
                    vacacion.setHoras(rs.getInt("horas"));
                    vacacion.setPeriodo(rs.getInt("periodo"));
                    vacacion.setUnidmed(rs.getString("unidmed"));
                    vacacion.setImporte(rs.getBigDecimal("importe"));
                    vacacion.setInicio(rs.getDate("inicio"));
                    vacacion.setFin(rs.getDate("fin"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return vacacion;
    }

    public vacacion insertarvacacion(vacacion vacacion) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO vacaciones(fecha,funcionario,horas,"
                + "unidmed,importe,inicio,fin,idsucursal,periodo) VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setDate(1, vacacion.getFecha());
        ps.setInt(2, vacacion.getFicha_empleado().getCodigo());
        ps.setInt(3, vacacion.getHoras());
        ps.setString(4, vacacion.getUnidmed());
        ps.setBigDecimal(5, vacacion.getImporte());
        ps.setDate(6, vacacion.getInicio());
        ps.setDate(7, vacacion.getFin());
        ps.setInt(8, vacacion.getGiraduria());
        ps.setInt(9, vacacion.getPeriodo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return vacacion;
    }

    public boolean actualizarvacacion(vacacion vacacion) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE vacaciones SET fecha=?,"
                + "funcionario=?,horas=?,unidmed=?,importe=?,inicio=?,"
                + "fin=?,idsucursal=?,periodo=? WHERE numero=" + vacacion.getNumero());
        ps.setDate(1, vacacion.getFecha());
        ps.setInt(2, vacacion.getFicha_empleado().getCodigo());
        ps.setInt(3, vacacion.getHoras());
        ps.setString(4, vacacion.getUnidmed());
        ps.setBigDecimal(5, vacacion.getImporte());
        ps.setDate(6, vacacion.getInicio());
        ps.setDate(7, vacacion.getFin());
        ps.setInt(8, vacacion.getGiraduria());
        ps.setInt(9, vacacion.getPeriodo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarvacacion(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM vacaciones WHERE numero=?");
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

    public vacacion Tomadas(int empleado, int periodo) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        vacacion vacacion = new vacacion();

        String sql = "SELECT  SUM(horas) as tomadas,unidmed,periodo "
                + "FROM vacaciones "
                + "WHERE funcionario=? "
                + "AND periodo=? "
                + "GROUP BY funcionario,periodo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, empleado);
            ps.setInt(2, periodo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vacacion.setHoras(rs.getInt("tomadas"));
                vacacion.setPeriodo(rs.getInt("periodo"));
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return vacacion;
    }

}

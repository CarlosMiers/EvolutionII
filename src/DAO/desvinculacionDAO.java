/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ficha_empleado;
import Modelo.desvinculacion;

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
public class desvinculacionDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<desvinculacion> Todos(Date dFechaIni, Date dFechaFin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT desvinculaciones.numero,desvinculaciones.fecha,empleados.codigo AS codempleado,"
                + "CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,"
                + " desvinculaciones.antiguedad,desvinculaciones.tipo_desvinculacion,"
                + "desvinculaciones.dias_preaviso,"
                + "desvinculaciones.inicio,desvinculaciones.fin,"
                + "salario_ordinario,vacaciones,"
                + "aguinaldo,preaviso,indemnizacion,"
                + "desvinculaciones.ips,desvinculaciones.idsucursal "
                + "FROM desvinculaciones "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=desvinculaciones.funcionario "
                + " WHERE  desvinculaciones.fecha between ? AND ?"
                + "ORDER BY desvinculaciones.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechaIni);
            ps.setDate(2, dFechaFin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                desvinculacion desvinculacion = new desvinculacion();
                ficha_empleado ficha = new ficha_empleado();
                desvinculacion.setFuncionario(ficha);

                desvinculacion.setNumero(rs.getDouble("numero"));
                desvinculacion.setFecha(rs.getDate("fecha"));
                desvinculacion.getFuncionario().setCodigo(rs.getInt("codempleado"));
                desvinculacion.getFuncionario().setNombreempleado(rs.getString("nombreempleado"));
                desvinculacion.setAntiguedad(rs.getDouble("antiguedad"));
                desvinculacion.setDias_preaviso(rs.getInt("dias_preaviso"));

                desvinculacion.setSalario_ordinario(rs.getDouble("salario_ordinario"));
                desvinculacion.setVacaciones(rs.getDouble("vacaciones"));
                desvinculacion.setAguinaldo(rs.getDouble("aguinaldo"));
                desvinculacion.setPreaviso(rs.getDouble("preaviso"));
                desvinculacion.setIndemnizacion(rs.getDouble("indemnizacion"));
                desvinculacion.setIps(rs.getDouble("ips"));

                desvinculacion.setInicio(rs.getDate("inicio"));
                desvinculacion.setFin(rs.getDate("fin"));
                lista.add(desvinculacion);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public desvinculacion buscarId(int id) throws SQLException {

        desvinculacion desvinculacion = new desvinculacion();
        ficha_empleado ficha = new ficha_empleado();
        desvinculacion.setFuncionario(ficha);

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT desvinculaciones.numero,desvinculaciones.fecha,empleados.codigo AS codempleado,"
                    + "CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,"
                    + " desvinculaciones.antiguedad,desvinculaciones.tipo_desvinculacion,"
                    + "desvinculaciones.dias_preaviso,"
                    + "desvinculaciones.inicio,desvinculaciones.fin,"
                    + "salario_ordinario,vacaciones,"
                    + "aguinaldo,preaviso,indemnizacion,"
                    + "desvinculaciones.ips,desvinculaciones.idsucursal "
                    + "FROM desvinculaciones "
                    + "INNER JOIN empleados "
                    + "ON empleados.codigo=desvinculaciones.funcionario "
                    + " WHERE  desvinculaciones.numero=? "
                    + " ORDER BY desvinculaciones.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    desvinculacion.setNumero(rs.getDouble("numero"));
                    desvinculacion.setFecha(rs.getDate("fecha"));
                    desvinculacion.getFuncionario().setCodigo(rs.getInt("codempleado"));
                    desvinculacion.getFuncionario().setNombreempleado(rs.getString("nombreempleado"));
                    desvinculacion.setAntiguedad(rs.getDouble("antiguedad"));
                    desvinculacion.setDias_preaviso(rs.getInt("dias_preaviso"));
                    desvinculacion.setTipo_desvinculacion(rs.getInt("tipo_desvinculacion"));

                    desvinculacion.setSalario_ordinario(rs.getDouble("salario_ordinario"));
                    desvinculacion.setVacaciones(rs.getDouble("vacaciones"));
                    desvinculacion.setAguinaldo(rs.getDouble("aguinaldo"));
                    desvinculacion.setPreaviso(rs.getDouble("preaviso"));
                    desvinculacion.setIndemnizacion(rs.getDouble("indemnizacion"));
                    desvinculacion.setIps(rs.getDouble("ips"));

                    desvinculacion.setInicio(rs.getDate("inicio"));
                    desvinculacion.setFin(rs.getDate("fin"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return desvinculacion;
    }

    public desvinculacion insertardesvinculacion(desvinculacion desvinculacion) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO desvinculaciones(fecha,inicio,fin,"
                + "tipo_desvinculacion,funcionario,dias_preaviso,"
                + "salario_ordinario,vacaciones,"
                + "aguinaldo,preaviso,indemnizacion,"
                + "ips,idsucursal,antiguedad) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setDate(1, desvinculacion.getFecha());
        ps.setDate(2, desvinculacion.getInicio());
        ps.setDate(3, desvinculacion.getFin());
        ps.setInt(4, desvinculacion.getTipo_desvinculacion());
        ps.setInt(5, desvinculacion.getFuncionario().getCodigo());
        ps.setInt(6, desvinculacion.getDias_preaviso());
        ps.setDouble(7, desvinculacion.getSalario_ordinario());
        ps.setDouble(8, desvinculacion.getVacaciones());
        ps.setDouble(9, desvinculacion.getAguinaldo());
        ps.setDouble(10, desvinculacion.getPreaviso());
        ps.setDouble(11, desvinculacion.getIndemnizacion());
        ps.setDouble(12, desvinculacion.getIps());
        ps.setInt(13, desvinculacion.getIdsucursal());
        ps.setDouble(14, desvinculacion.getAntiguedad());
        ps.executeUpdate();
        st.close();
        ps.close();
        return desvinculacion;
    }

    public boolean actualizardesvinculacion(desvinculacion desvinculacion) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE desvinculaciones SET fecha=?,inicio=?,fin=?,"
                + "tipo_desvinculacion=?,funcionario=?,dias_preaviso=?,"
                + "salario_ordinario=?,vacaciones=?,"
                + "aguinaldo=?,preaviso=?,indemnizacion=?,"
                + "ips=?,idsucursal=?,antiguedad=? WHERE numero=" + desvinculacion.getNumero());
        ps.setDate(1, desvinculacion.getFecha());
        ps.setDate(2, desvinculacion.getInicio());
        ps.setDate(3, desvinculacion.getFin());
        ps.setInt(4, desvinculacion.getTipo_desvinculacion());
        ps.setInt(5, desvinculacion.getFuncionario().getCodigo());
        ps.setInt(6, desvinculacion.getDias_preaviso());
        ps.setDouble(7, desvinculacion.getSalario_ordinario());
        ps.setDouble(8, desvinculacion.getVacaciones());
        ps.setDouble(9, desvinculacion.getAguinaldo());
        ps.setDouble(10, desvinculacion.getPreaviso());
        ps.setDouble(11, desvinculacion.getIndemnizacion());
        ps.setDouble(12, desvinculacion.getIps());
        ps.setInt(13, desvinculacion.getIdsucursal());
        ps.setDouble(14, desvinculacion.getAntiguedad());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminardesvinculacion(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM desvinculaciones WHERE numero=?");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ficha_empleado;
import Modelo.motivo_ausencia;
import Modelo.permiso;
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
public class permisoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<permiso> Todos(Date dFechaIni, Date dFechaFin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT permisos.numero,permisos.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,"
                + "CONCAT(permisos.horas,' ', permisos.unidmed) AS tiempo, permisos.horas,permisos.unidmed,"
                + "permisos.inicio,permisos.fin,permisos.motivo,motivo_ausencias.nombre AS nombremotivo,permisos.idsucursal "
                + "FROM permisos "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=permisos.funcionario "
                + " LEFT JOIN motivo_ausencias "
                + " ON motivo_ausencias.codigo=permisos.motivo "
                + " WHERE  permisos.fecha between ? AND ? "
                + " ORDER BY permisos.numero ";

        System.out.println(sql);

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechaIni);
            ps.setDate(2, dFechaFin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                permiso permiso = new permiso();
                ficha_empleado ficha = new ficha_empleado();
                motivo_ausencia mo = new motivo_ausencia();

                permiso.setFicha_empleado(ficha);
                permiso.setMotivo(mo);
                permiso.setNumero(rs.getDouble("numero"));
                permiso.setFecha(rs.getDate("fecha"));
                permiso.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                permiso.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                permiso.getMotivo().setCodigo(rs.getInt("motivo"));
                permiso.getMotivo().setNombre(rs.getString("nombremotivo"));
                permiso.setHoras(rs.getInt("horas"));
                permiso.setUnidmed(rs.getString("unidmed"));
                permiso.setInicio(rs.getDate("inicio"));
                permiso.setFin(rs.getDate("fin"));
                lista.add(permiso);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public permiso buscarId(int id) throws SQLException {

        permiso permiso = new permiso();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT permisos.numero,permisos.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado, "
                    + "CONCAT(permisos.horas,' ', permisos.unidmed) AS tiempo, permisos.horas,permisos.unidmed,"
                    + "empleados.salario,empleados.tipo_salario,permisos.motivo,motivo_ausencias.nombre AS nombremotivo,"
                    + "permisos.inicio,permisos.fin,permisos.idsucursal "
                    + " FROM permisos "
                    + "INNER JOIN empleados "
                    + "ON empleados.codigo=permisos.funcionario "
                    + " LEFT JOIN motivo_ausencias "
                    + " ON motivo_ausencias.codigo=permisos.motivo "
                    + " WHERE permisos.numero=? "
                    + " ORDER BY permisos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ficha_empleado ficha = new ficha_empleado();
                    permiso.setFicha_empleado(ficha);

                    motivo_ausencia mo = new motivo_ausencia();
                    permiso.setMotivo(mo);

                    permiso.setNumero(rs.getDouble("numero"));
                    permiso.setFecha(rs.getDate("fecha"));
                    permiso.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                    permiso.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                    permiso.getFicha_empleado().setSalario(rs.getBigDecimal("salario"));
                    permiso.getFicha_empleado().setTipo_salario(rs.getInt("tipo_salario"));
                    permiso.getMotivo().setCodigo(rs.getInt("motivo"));
                    permiso.getMotivo().setNombre(rs.getString("nombremotivo"));
                    permiso.setHoras(rs.getInt("horas"));
                    permiso.setUnidmed(rs.getString("unidmed"));
                    permiso.setInicio(rs.getDate("inicio"));
                    permiso.setFin(rs.getDate("fin"));
                    permiso.setGiraduria(rs.getInt("idsucursal"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return permiso;
    }

    public permiso insertarpermiso(permiso permiso) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO permisos(fecha,funcionario,horas,"
                + "unidmed,inicio,fin,idsucursal,permiso) VALUES (?,?,?,?,?,?,?,?)");
        ps.setDate(1, permiso.getFecha());
        ps.setInt(2, permiso.getFicha_empleado().getCodigo());
        ps.setInt(3, permiso.getHoras());
        ps.setString(4, permiso.getUnidmed());
        ps.setDate(5, permiso.getInicio());
        ps.setDate(6, permiso.getFin());
        ps.setInt(7, permiso.getGiraduria());
        ps.setInt(8, permiso.getMotivo().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return permiso;
    }

    public boolean actualizarpermiso(permiso permiso) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE permisos SET fecha=?,"
                + "funcionario=?,horas=?,unidmed=?,inicio=?,fin=?,idsucursal=?, motivo=? WHERE numero=" + permiso.getNumero());
        ps.setDate(1, permiso.getFecha());
        ps.setInt(2, permiso.getFicha_empleado().getCodigo());
        ps.setInt(3, permiso.getHoras());
        ps.setString(4, permiso.getUnidmed());
        ps.setDate(5, permiso.getInicio());
        ps.setDate(6, permiso.getFin());
        ps.setInt(7, permiso.getGiraduria());
        ps.setInt(8, permiso.getMotivo().getCodigo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarpermiso(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM permisos WHERE numero=?");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ficha_empleado;

import Modelo.hora_extra;
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
public class hora_extraDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<hora_extra> Todos(Date dFechaIni, Date dFechaFin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT horas_extras.numero,horas_extras.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,"
                + "CONCAT(horas_extras.horas,' ', horas_extras.unidmed) AS tiempo, horas_extras.horas,horas_extras.unidmed,horas_extras.importe,horas_extras.tipo_horario "
                + "FROM horas_extras "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=horas_extras.funcionario "
                + " WHERE  horas_extras.fecha between ? AND ?"
                + "ORDER BY horas_extras.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechaIni);
            ps.setDate(2, dFechaFin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                hora_extra  horae = new hora_extra();
                ficha_empleado ficha = new ficha_empleado();
                horae.setFicha_empleado(ficha);

                horae.setNumero(rs.getString("numero"));
                horae.setFecha(rs.getDate("fecha"));
                horae.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                horae.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                horae.setHoras(rs.getInt("horas"));
                horae.setUnidmed(rs.getString("unidmed"));
                horae.setImporte(rs.getBigDecimal("importe"));
                horae.setTiempo(rs.getString("tiempo"));
                horae.setTipo_horario(rs.getInt("tipo_horario"));
                lista.add(horae);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<hora_extra> TodosxSuc(Date dFechaIni, Date dFechaFin, Integer suc) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT horas_extras.numero,horas_extras.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,"
                + "CONCAT(horas_extras.horas,' ', horas_extras.unidmed) AS tiempo, horas_extras.horas,horas_extras.unidmed,horas_extras.importe,horas_extras.tipo_horario "
                + "FROM horas_extras "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=horas_extras.funcionario "
                + " WHERE  horas_extras.fecha between ? AND ? "
                +" AND horas_extras.idsucursal=? "
                + "ORDER BY horas_extras.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechaIni);
            ps.setDate(2, dFechaFin);
            ps.setInt(3, suc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                hora_extra  horae = new hora_extra();
                ficha_empleado ficha = new ficha_empleado();
                horae.setFicha_empleado(ficha);

                horae.setNumero(rs.getString("numero"));
                horae.setFecha(rs.getDate("fecha"));
                horae.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                horae.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                horae.setHoras(rs.getInt("horas"));
                horae.setUnidmed(rs.getString("unidmed"));
                horae.setImporte(rs.getBigDecimal("importe"));
                horae.setTiempo(rs.getString("tiempo"));
                horae.setTipo_horario(rs.getInt("tipo_horario"));
                lista.add(horae);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public hora_extra buscarId(int id) throws SQLException {

        hora_extra  horae = new hora_extra();
        ficha_empleado ficha = new ficha_empleado();
        horae.setFicha_empleado(ficha);

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT horas_extras.numero,horas_extras.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado, "
                    + "CONCAT(horas_extras.horas,' ', horas_extras.unidmed) AS tiempo, horas_extras.horas,horas_extras.unidmed,horas_extras.importe,horas_extras.tipo_horario, " 
                    +"empleados.salario,empleados.tipo_salario "
                    + "FROM horas_extras "
                    + "INNER JOIN empleados "
                    + "ON empleados.codigo=horas_extras.funcionario "
                    + "WHERE horas_extras.numero=? "
                    + "ORDER BY horas_extras.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    horae.setNumero(rs.getString("numero"));
                    horae.setFecha(rs.getDate("fecha"));
                    horae.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                    horae.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                    horae.setHoras(rs.getInt("horas"));
                    horae.setUnidmed(rs.getString("unidmed"));
                    horae.setImporte(rs.getBigDecimal("importe"));
                    horae.setTiempo(rs.getString("tiempo"));
                    horae.getFicha_empleado().setSalario(rs.getBigDecimal("salario"));
                    horae.getFicha_empleado().setTipo_salario(rs.getInt("tipo_salario"));
                    horae.setTipo_horario(rs.getInt("tipo_horario"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return horae;
    }

    public hora_extra insertarllegada(hora_extra horae) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO horas_extras(numero,fecha,"
                + "funcionario,horas,unidmed,importe,tipo_horario,idsucursal) VALUES (?,?,?,?,?,?,?,?)");
        ps.setString(1, horae.getNumero());
        ps.setDate(2, horae.getFecha());
        ps.setInt(3, horae.getFicha_empleado().getCodigo());
        ps.setInt(4, horae.getHoras());
        ps.setString(5, horae.getUnidmed());
        ps.setBigDecimal(6, horae.getImporte());
        ps.setInt(7, horae.getTipo_horario());
        ps.setInt(8, horae.getGiraduria());

        ps.executeUpdate();
        st.close();
        ps.close();
        return horae;
    }

    public boolean actualizarllegada(hora_extra horae) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE horas_extras SET numero=?,fecha=?,funcionario=?,"
                + "horas=?,unidmed=?,importe=?,tipo_horario=?,idsucursal=? WHERE numero=" + horae.getNumero());
        ps.setString(1, horae.getNumero());
        ps.setDate(2, horae.getFecha());
        ps.setInt(3, horae.getFicha_empleado().getCodigo());
        ps.setInt(4, horae.getHoras());
        ps.setString(5, horae.getUnidmed());
        ps.setBigDecimal(6, horae.getImporte());
        ps.setInt(7, horae.getTipo_horario());
        ps.setInt(8, horae.getGiraduria());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarllegada(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM horas_extras WHERE numero=?");
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

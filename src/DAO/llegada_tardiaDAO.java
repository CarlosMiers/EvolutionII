/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ficha_empleado;

import Modelo.llegada_tardia;
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
public class llegada_tardiaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<llegada_tardia> Todos(Date dFechaIni, Date dFechaFin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT llegadas_tardias.numero,llegadas_tardias.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,"
                + "CONCAT(llegadas_tardias.horas,' ', llegadas_tardias.unidmed) AS tiempo, llegadas_tardias.horas,llegadas_tardias.unidmed,llegadas_tardias.importe "
                + "FROM llegadas_tardias "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=llegadas_tardias.funcionario "
                + " WHERE  llegadas_tardias.fecha between ? AND ?"
                + "ORDER BY llegadas_tardias.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechaIni);
            ps.setDate(2, dFechaFin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                llegada_tardia llegada = new llegada_tardia();
                ficha_empleado ficha = new ficha_empleado();
                llegada.setFicha_empleado(ficha);

                llegada.setNumero(rs.getString("numero"));
                llegada.setFecha(rs.getDate("fecha"));
                llegada.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                llegada.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                llegada.setHoras(rs.getInt("horas"));
                llegada.setUnidmed(rs.getString("unidmed"));
                llegada.setImporte(rs.getBigDecimal("importe"));
                llegada.setTiempo(rs.getString("tiempo"));

                lista.add(llegada);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }


    public ArrayList<llegada_tardia> TodosxSuc(Date dFechaIni, Date dFechaFin,Integer suc) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT llegadas_tardias.numero,llegadas_tardias.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,"
                + "CONCAT(llegadas_tardias.horas,' ', llegadas_tardias.unidmed) AS tiempo, llegadas_tardias.horas,llegadas_tardias.unidmed,llegadas_tardias.importe "
                + "FROM llegadas_tardias "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=llegadas_tardias.funcionario "
                + " WHERE  llegadas_tardias.fecha between ? AND ? "
                +" AND llegadas_tardias.idsucursal=? "
                + "ORDER BY llegadas_tardias.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechaIni);
            ps.setDate(2, dFechaFin);
            ps.setInt(3, suc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                llegada_tardia llegada = new llegada_tardia();
                ficha_empleado ficha = new ficha_empleado();
                llegada.setFicha_empleado(ficha);

                llegada.setNumero(rs.getString("numero"));
                llegada.setFecha(rs.getDate("fecha"));
                llegada.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                llegada.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                llegada.setHoras(rs.getInt("horas"));
                llegada.setUnidmed(rs.getString("unidmed"));
                llegada.setImporte(rs.getBigDecimal("importe"));
                llegada.setTiempo(rs.getString("tiempo"));

                lista.add(llegada);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }


    public llegada_tardia buscarId(int id) throws SQLException {

        llegada_tardia llegada = new llegada_tardia();
        ficha_empleado ficha = new ficha_empleado();
        llegada.setFicha_empleado(ficha);

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT llegadas_tardias.numero,llegadas_tardias.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado, "
                    + "CONCAT(llegadas_tardias.horas,' ', llegadas_tardias.unidmed) AS tiempo, llegadas_tardias.horas,llegadas_tardias.unidmed,llegadas_tardias.importe,"
                    +"empleados.salario,empleados.tipo_salario "
                    + " FROM llegadas_tardias "
                    + "INNER JOIN empleados "
                    + "ON empleados.codigo=llegadas_tardias.funcionario "
                    + "WHERE llegadas_tardias.numero=? "
                    + "ORDER BY llegadas_tardias.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    llegada.setNumero(rs.getString("numero"));
                    llegada.setFecha(rs.getDate("fecha"));
                    llegada.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                    llegada.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                    llegada.getFicha_empleado().setSalario(rs.getBigDecimal("salario"));
                    llegada.getFicha_empleado().setTipo_salario(rs.getInt("tipo_salario"));
                    llegada.setHoras(rs.getInt("horas"));
                    llegada.setUnidmed(rs.getString("unidmed"));
                    llegada.setImporte(rs.getBigDecimal("importe"));
                    llegada.setTiempo(rs.getString("tiempo"));
                    
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return llegada;
    }

    public llegada_tardia insertarllegada(llegada_tardia llegada) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO llegadas_tardias(fecha,funcionario,horas,unidmed,importe,idsucursal)"
                + " VALUES (?,?,?,?,?,?)");
        ps.setDate(1, llegada.getFecha());
        ps.setInt(2, llegada.getFicha_empleado().getCodigo());
        ps.setInt(3, llegada.getHoras());
        ps.setString(4, llegada.getUnidmed());
        ps.setBigDecimal(5, llegada.getImporte());
        ps.setInt(6,llegada.getGiraduria());
        ps.executeUpdate();
        st.close();
        ps.close();
        return llegada;
    }

    public boolean actualizarllegada(llegada_tardia llegada) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE llegadas_tardias SET fecha=?,"
                + "funcionario=?,horas=?,unidmed=?,importe=?,idsucursal=? WHERE numero=" + llegada.getNumero());
        ps.setDate(1, llegada.getFecha());
        ps.setInt(2, llegada.getFicha_empleado().getCodigo());
        ps.setInt(3, llegada.getHoras());
        ps.setString(4, llegada.getUnidmed());
        ps.setBigDecimal(5, llegada.getImporte());
        ps.setInt(6,llegada.getGiraduria());
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

        ps = st.getConnection().prepareStatement("DELETE FROM llegadas_tardias WHERE numero=?");
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

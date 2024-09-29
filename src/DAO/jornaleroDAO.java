/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ficha_empleado;
import Modelo.jornalero;
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
public class jornaleroDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<jornalero> Todos(Date ini, Date fin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT jornaleros.numero,jornaleros.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,CONCAT(jornaleros.dias,' ',jornaleros.unidmed) AS tiempo, "
                + "jornaleros.dias,jornaleros.importe,jornaleros.unidmed "
                + "FROM jornaleros  "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=jornaleros.funcionario "
                + " WHERE  jornaleros.fecha between ? AND ?"
                + "ORDER BY jornaleros.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, ini);
            ps.setDate(2, fin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                jornalero aus = new jornalero();
                ficha_empleado ficha = new ficha_empleado();
        
                aus.setFicha_empleado(ficha);
                aus.setNumero(rs.getString("numero"));
                aus.setFecha(rs.getDate("fecha"));
                aus.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                aus.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                
                aus.setDias(rs.getInt("dias"));
                aus.setImporte(rs.getBigDecimal("importe"));
                aus.setUnidmed(rs.getString("unidmed"));
                aus.setTiempo(rs.getString("tiempo"));

                lista.add(aus);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("BROWSE" + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

        public ArrayList<jornalero> TodosxSucursal(Date ini, Date fin,int suc) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT jornaleros.numero,jornaleros.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,CONCAT(jornaleros.dias,' ',jornaleros.unidmed) AS tiempo, "
                + "jornaleros.dias,jornaleros.importe,jornaleros.unidmed "
                + "FROM jornaleros  "
                + "INNER JOIN empleados "
                + "ON empleados.codigo=jornaleros.funcionario "
                + " WHERE  jornaleros.fecha between ? AND ? "
                +" AND jornaleros.idsucursal=? "
                + " ORDER BY jornaleros.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, ini);
            ps.setDate(2, fin);
            ps.setInt(3, suc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                jornalero aus = new jornalero();
                ficha_empleado ficha = new ficha_empleado();
        
                aus.setFicha_empleado(ficha);
                aus.setNumero(rs.getString("numero"));
                aus.setFecha(rs.getDate("fecha"));
                aus.getFicha_empleado().setCodigo(rs.getInt("codempleado"));
                aus.getFicha_empleado().setNombreempleado(rs.getString("nombreempleado"));
                
                aus.setDias(rs.getInt("dias"));
                aus.setImporte(rs.getBigDecimal("importe"));
                aus.setUnidmed(rs.getString("unidmed"));
                aus.setTiempo(rs.getString("tiempo"));

                lista.add(aus);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("BROWSE" + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    
    public jornalero buscarId(int id) throws SQLException {

        jornalero aus = new jornalero();
        ficha_empleado ficha = new ficha_empleado();
        aus.setFicha_empleado(ficha);

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT jornaleros.numero,jornaleros.fecha,empleados.codigo AS codempleado,CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado,CONCAT(jornaleros.dias,' ',jornaleros.unidmed) AS tiempo, "
                    + "jornaleros.dias,jornaleros.importe, jornaleros.unidmed, "
                    + "empleados.salario,empleados.tipo_salario,jornaleros.observacion "
                    + "FROM jornaleros "
                    + "INNER JOIN empleados "
                    + " ON empleados.codigo=jornaleros.funcionario "
                    + " WHERE jornaleros.numero=? "
                    + " ORDER BY jornaleros.numero ";

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
                    aus.setDias(rs.getInt("dias"));
                    aus.setImporte(rs.getBigDecimal("importe"));
                    aus.setUnidmed(rs.getString("unidmed"));
                    aus.setTiempo(rs.getString("tiempo"));
                    aus.setObservacion(rs.getString("observacion"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return aus;
    }

    public jornalero insertarjornada(jornalero aus) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO jornaleros(fecha,funcionario,dias,"
                + "importe, unidmed,idsucursal,observacion)"
                + " VALUES (?,?,?,?,?,?,?)");
        ps.setDate(1, aus.getFecha());
        ps.setInt(2, aus.getFicha_empleado().getCodigo());
        ps.setDouble(3, aus.getDias());
        ps.setBigDecimal(4, aus.getImporte());
        ps.setString(5, aus.getUnidmed());
        ps.setInt(6, aus.getSucursal());
        ps.setString(7, aus.getObservacion());
        ps.executeUpdate();
        st.close();
        ps.close();
        return aus;
    }

    public boolean actualizarjornada(jornalero aus) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE jornaleros SET "
                + " fecha=?,funcionario=?,dias=?,"
                + "importe=?,unidmed=?,idsucursal=?,"
                + "observacion=?  WHERE numero=" + aus.getNumero());
        ps.setDate(1, aus.getFecha());
        ps.setInt(2, aus.getFicha_empleado().getCodigo());
        ps.setDouble(3, aus.getDias());
        ps.setBigDecimal(4, aus.getImporte());
        ps.setString(5, aus.getUnidmed());
        ps.setInt(6, aus.getSucursal());
        ps.setString(7, aus.getObservacion());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarjornada(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM jornaleros WHERE numero=?");
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

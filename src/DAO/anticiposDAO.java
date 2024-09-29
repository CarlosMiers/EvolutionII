/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.anticipo;
import Modelo.ficha_empleado;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class anticiposDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<anticipo> Todos() throws SQLException {
        ArrayList<anticipo> lista = new ArrayList<anticipo>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT anticipos.idcontrol,anticipos.fecha,"
                    + "anticipos.funcionario,"
                    + "anticipos.importe,anticipos.fechagrabado,"
                    + "anticipos.idusuario,"
                    + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario "
                    + " FROM anticipos"
                    + " LEFT JOIN empleados"
                    + " ON empleados.codigo=anticipos.funcionario"
                    + " ORDER BY anticipos.idcontrol ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    anticipo anti = new anticipo();
                    ficha_empleado ficha = new ficha_empleado();
                    anti.setFuncionario(ficha);
                    anti.setIdcontrol(rs.getDouble("idcontrol"));
                    anti.setFecha(rs.getDate("fecha"));
                    anti.setFechagrabado(rs.getDate("fechagrabado"));
                    anti.setImporte(rs.getBigDecimal("importe"));
                    anti.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    anti.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                    anti.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));
                    // System.out.println("Unidad Get " + ed. getIdunidad());
                    lista.add(anti);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<anticipo> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT anticipos.idcontrol,anticipos.fecha,"
                + "anticipos.funcionario,"
                + "anticipos.importe,anticipos.fechagrabado,"
                + "anticipos.idusuario,"
                + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario "
                + "FROM anticipos "
                + "LEFT JOIN empleados "
                + "ON empleados.codigo=anticipos.funcionario "
                + "WHERE anticipos.fecha between ? AND ? "
                + " ORDER BY anticipos.idcontrol ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                anticipo anti = new anticipo();
                ficha_empleado ficha = new ficha_empleado();
                anti.setFuncionario(ficha);
                anti.setIdcontrol(rs.getDouble("idcontrol"));
                anti.setFecha(rs.getDate("fecha"));
                anti.setFechagrabado(rs.getDate("fechagrabado"));
                anti.setImporte(rs.getBigDecimal("importe"));
                anti.getFuncionario().setCodigo(rs.getInt("funcionario"));
                anti.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                anti.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));
                lista.add(anti);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    
    
    public ArrayList<anticipo> MostrarxFechaxSuc(Date fechaini, Date fechafin,Integer nsuc) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT anticipos.idcontrol,anticipos.fecha,"
                + "anticipos.funcionario,"
                + "anticipos.importe,anticipos.fechagrabado,"
                + "anticipos.idusuario,"
                + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario "
                + "FROM anticipos "
                + "LEFT JOIN empleados "
                + "ON empleados.codigo=anticipos.funcionario "
                + "WHERE anticipos.fecha between ? AND ? "
                + " AND anticipos.idsucursal=? "
                + " ORDER BY anticipos.idcontrol ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ps.setInt(3, nsuc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                anticipo anti = new anticipo();
                ficha_empleado ficha = new ficha_empleado();
                anti.setFuncionario(ficha);
                anti.setIdcontrol(rs.getDouble("idcontrol"));
                anti.setFecha(rs.getDate("fecha"));
                anti.setFechagrabado(rs.getDate("fechagrabado"));
                anti.setImporte(rs.getBigDecimal("importe"));
                anti.getFuncionario().setCodigo(rs.getInt("funcionario"));
                anti.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                anti.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));
                lista.add(anti);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }
    
    public anticipo buscarId(double id) throws SQLException {

        anticipo anti = new anticipo();
        ficha_empleado ficha = new ficha_empleado();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT anticipos.idcontrol,anticipos.fecha,"
                    + "anticipos.funcionario,"
                    + "anticipos.importe,anticipos.fechagrabado,"
                    + "anticipos.idusuario,"
                    + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario "
                    + "FROM anticipos "
                    + "LEFT JOIN empleados "
                    + "ON empleados.codigo=anticipos.funcionario "
                    + "WHERE anticipos.idcontrol=? "
                    + " ORDER BY anticipos.idcontrol ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    anti.setFuncionario(ficha);
                    anti.setIdcontrol(rs.getDouble("idcontrol"));
                    anti.setFecha(rs.getDate("fecha"));
                    anti.setFechagrabado(rs.getDate("fechagrabado"));
                    anti.setImporte(rs.getBigDecimal("importe"));
                    anti.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    anti.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                    anti.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));

                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return anti;
    }

    public anticipo InsertarAnticipo(anticipo anti) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO anticipos (fecha,funcionario,importe,idusuario,idsucursal)"
                + "  VALUES (?,?,?,?,?)");
        ps.setDate(1, anti.getFecha());
        ps.setInt(2, anti.getFuncionario().getCodigo());
        ps.setBigDecimal(3, anti.getImporte());
        ps.setInt(4, anti.getIdusuario());
        ps.setInt(5, anti.getGiraduria());
        ps.executeUpdate();
        st.close();
        ps.close();
        return anti;
    }

    public boolean ActualizarAnticipo(anticipo anti) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE anticipos SET fecha=?,funcionario=?,"
                + "importe=?,idusuario=?,idsucursal=? WHERE idcontrol=" + anti.getIdcontrol());
        ps.setDate(1, anti.getFecha());
        ps.setInt(2, anti.getFuncionario().getCodigo());
        ps.setBigDecimal(3, anti.getImporte());
        ps.setInt(4, anti.getIdusuario());
        ps.setInt(5, anti.getGiraduria());

        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarAnticipo(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM anticipos WHERE idcontrol=?");
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
}

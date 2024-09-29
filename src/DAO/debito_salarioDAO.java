/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.concepto_salario;
import Modelo.debito_salario;
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
public class debito_salarioDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<debito_salario> Todos() throws SQLException {
        ArrayList<debito_salario> lista = new ArrayList<debito_salario>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT debito_salarios.idcontrol,debito_salarios.fecha,"
                    + "debito_salarios.funcionario,debito_salarios.concepto,debito_salarios.observacion,"
                    + "debito_salarios.importe,debito_salarios.fechagrabado,"
                    + "debito_salarios.idusuario,"
                    + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario,"
                    + "concepto_salarios.nombre AS nombreconcepto, concepto_salarios.tipo AS tipoconcepto "
                    + " FROM debito_salarios"
                    + " LEFT JOIN empleados"
                    + " ON empleados.codigo=debito_salarios.funcionario"
                    + " LEFT JOIN concepto_salarios"
                    + " ON concepto_salarios.codigo=debito_salarios.concepto"
                    + " ORDER BY debito_salarios.idcontrol ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    debito_salario debito = new debito_salario();
                    ficha_empleado ficha = new ficha_empleado();
                    concepto_salario concepto = new concepto_salario();

                    debito.setFuncionario(ficha);
                    debito.setConcepto(concepto);
                    debito.setObservacion(rs.getString("observacion"));
                    debito.setIdcontrol(rs.getDouble("idcontrol"));
                    debito.setFecha(rs.getDate("fecha"));
                    debito.setFechagrabado(rs.getDate("fechagrabado"));
                    debito.setImporte(rs.getBigDecimal("importe"));
                    debito.getConcepto().setCodigo(rs.getInt("concepto"));
                    debito.getConcepto().setNombre(rs.getString("nombreconcepto"));
                    debito.getConcepto().setTipo(rs.getString("tipoconcepto"));
                    debito.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    debito.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                    debito.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));
                    // System.out.println("Unidad Get " + ed. getIdunidad());
                    lista.add(debito);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<debito_salario> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT debito_salarios.idcontrol,debito_salarios.fecha,"
                + "debito_salarios.funcionario,debito_salarios.concepto,"
                + "debito_salarios.importe,debito_salarios.fechagrabado,"
                + "debito_salarios.idusuario,debito_salarios.observacion,"
                + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario,"
                + "concepto_salarios.nombre AS nombreconcepto, concepto_salarios.tipo AS tipoconcepto "
                + "FROM debito_salarios "
                + "LEFT JOIN empleados "
                + "ON empleados.codigo=debito_salarios.funcionario "
                + "LEFT JOIN concepto_salarios "
                + "ON concepto_salarios.codigo=debito_salarios.concepto "
                + "WHERE debito_salarios.fecha between ? AND ? "
                + " ORDER BY debito_salarios.idcontrol ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                debito_salario debito = new debito_salario();
                concepto_salario concepto = new concepto_salario();
                ficha_empleado ficha = new ficha_empleado();
                debito.setFuncionario(ficha);
                debito.setObservacion(rs.getString("observacion"));
                debito.setConcepto(concepto);
                debito.setIdcontrol(rs.getDouble("idcontrol"));
                debito.setFecha(rs.getDate("fecha"));
                debito.setFechagrabado(rs.getDate("fechagrabado"));
                debito.setImporte(rs.getBigDecimal("importe"));
                debito.getConcepto().setCodigo(rs.getInt("concepto"));
                debito.getConcepto().setNombre(rs.getString("nombreconcepto"));
                debito.getConcepto().setTipo(rs.getString("tipoconcepto"));
                debito.getFuncionario().setCodigo(rs.getInt("funcionario"));
                debito.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                debito.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));

                lista.add(debito);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }


    public ArrayList<debito_salario> MostrarxFechaxSuc(Date fechaini, Date fechafin, Integer suc) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT debito_salarios.idcontrol,debito_salarios.fecha,"
                + "debito_salarios.funcionario,debito_salarios.concepto,"
                + "debito_salarios.importe,debito_salarios.fechagrabado,"
                + "debito_salarios.idusuario,debito_salarios.observacion,"
                + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario,"
                + "concepto_salarios.nombre AS nombreconcepto, concepto_salarios.tipo AS tipoconcepto "
                + "FROM debito_salarios "
                + "LEFT JOIN empleados "
                + "ON empleados.codigo=debito_salarios.funcionario "
                + "LEFT JOIN concepto_salarios "
                + "ON concepto_salarios.codigo=debito_salarios.concepto "
                + "WHERE debito_salarios.fecha between ? AND ? "
                +" AND  debito_salarios.idsucursal=? "
                + " ORDER BY debito_salarios.idcontrol ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ps.setInt(3, suc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                debito_salario debito = new debito_salario();
                concepto_salario concepto = new concepto_salario();
                ficha_empleado ficha = new ficha_empleado();
                debito.setFuncionario(ficha);
                debito.setConcepto(concepto);
                debito.setObservacion(rs.getString("observacion"));
                debito.setIdcontrol(rs.getDouble("idcontrol"));
                debito.setFecha(rs.getDate("fecha"));
                debito.setFechagrabado(rs.getDate("fechagrabado"));
                debito.setImporte(rs.getBigDecimal("importe"));
                debito.getConcepto().setCodigo(rs.getInt("concepto"));
                debito.getConcepto().setNombre(rs.getString("nombreconcepto"));
                debito.getConcepto().setTipo(rs.getString("tipoconcepto"));
                debito.getFuncionario().setCodigo(rs.getInt("funcionario"));
                debito.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                debito.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));

                lista.add(debito);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public debito_salario buscarId(double id) throws SQLException {

        debito_salario debito = new debito_salario();
        concepto_salario concepto = new concepto_salario();
        ficha_empleado ficha = new ficha_empleado();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT debito_salarios.idcontrol,debito_salarios.fecha,"
                    + "debito_salarios.funcionario,debito_salarios.concepto,"
                    + "debito_salarios.importe,debito_salarios.fechagrabado,"
                    + "debito_salarios.idusuario,debito_salarios.observacion,"
                    + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario,"
                    + "concepto_salarios.nombre AS nombreconcepto, concepto_salarios.tipo AS tipoconcepto "
                    + "FROM debito_salarios "
                    + "LEFT JOIN empleados "
                    + "ON empleados.codigo=debito_salarios.funcionario "
                    + "LEFT JOIN concepto_salarios "
                    + "ON concepto_salarios.codigo=debito_salarios.concepto "
                    + "WHERE debito_salarios.idcontrol=? "
                    + " ORDER BY debito_salarios.idcontrol ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    debito.setFuncionario(ficha);
                    debito.setConcepto(concepto);
                    debito.setIdcontrol(rs.getDouble("idcontrol"));
                    debito.setFecha(rs.getDate("fecha"));
                    debito.setFechagrabado(rs.getDate("fechagrabado"));
                    debito.setObservacion(rs.getString("observacion"));
                    debito.setImporte(rs.getBigDecimal("importe"));
                    debito.getConcepto().setCodigo(rs.getInt("concepto"));
                    debito.getConcepto().setNombre(rs.getString("nombreconcepto"));
                    debito.getConcepto().setTipo(rs.getString("tipoconcepto"));
                    debito.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    debito.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                    debito.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));

                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return debito;
    }

    public debito_salario InsertarDebito(debito_salario debito) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO debito_salarios (fecha,funcionario,concepto,importe,idusuario,idsucursal,observacion)"
                + "  VALUES (?,?,?,?,?,?,?)");
        ps.setDate(1, debito.getFecha());
        ps.setInt(2, debito.getFuncionario().getCodigo());
        ps.setInt(3, debito.getConcepto().getCodigo());
        ps.setBigDecimal(4, debito.getImporte());
        ps.setInt(5, debito.getIdusuario());
        ps.setInt(6, debito.getGiraduria());
        ps.setString(7,debito.getObservacion());
        ps.executeUpdate();
        st.close();
        ps.close();
        return debito;
    }

    public boolean ActualizarDebito(debito_salario debito) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE debito_salarios SET fecha=?,funcionario=?,"
                + "concepto=?,importe=?,idusuario=?,idsucursal=?,observacion=? WHERE idcontrol=" + debito.getIdcontrol());
        ps.setDate(1, debito.getFecha());
        ps.setInt(2, debito.getFuncionario().getCodigo());
        ps.setInt(3, debito.getConcepto().getCodigo());
        ps.setBigDecimal(4, debito.getImporte());
        ps.setInt(5, debito.getIdusuario());
        ps.setInt(6, debito.getGiraduria());
        ps.setString(7,debito.getObservacion());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarDebito(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM debito_salarios WHERE idcontrol=?");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.concepto_salario;
import Modelo.credito_salario;
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
public class credito_salarioDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<credito_salario> Todos() throws SQLException {
        ArrayList<credito_salario> lista = new ArrayList<credito_salario>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT credito_salarios.idcontrol,credito_salarios.fecha,"
                    + "credito_salarios.funcionario,credito_salarios.concepto,"
                    + "credito_salarios.importe,credito_salarios.fechagrabado,"
                    + "credito_salarios.idusuario,credito_salarios.observacion,"
                    + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario,"
                    + "concepto_salarios.nombre AS nombreconcepto, concepto_salarios.tipo AS tipoconcepto "
                    + " FROM credito_salarios"
                    + " LEFT JOIN empleados"
                    + " ON empleados.codigo=credito_salarios.funcionario"
                    + " LEFT JOIN concepto_salarios"
                    + " ON concepto_salarios.codigo=credito_salarios.concepto"
                    + " ORDER BY credito_salarios.idcontrol ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    credito_salario credito = new credito_salario();
                    ficha_empleado ficha = new ficha_empleado();
                    concepto_salario concepto = new concepto_salario();

                    credito.setFuncionario(ficha);
                    credito.setConcepto(concepto);
                    credito.setIdcontrol(rs.getDouble("idcontrol"));
                    credito.setObservacion(rs.getString("observacion"));
                    credito.setFecha(rs.getDate("fecha"));
                    credito.setFechagrabado(rs.getDate("fechagrabado"));
                    credito.setImporte(rs.getBigDecimal("importe"));
                    credito.getConcepto().setCodigo(rs.getInt("concepto"));
                    credito.getConcepto().setNombre(rs.getString("nombreconcepto"));
                    credito.getConcepto().setTipo(rs.getString("tipoconcepto"));
                    credito.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    credito.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                    credito.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));
                    // System.out.println("Unidad Get " + ed. getIdunidad());
                    lista.add(credito);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<credito_salario> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT credito_salarios.idcontrol,credito_salarios.fecha,"
                + "credito_salarios.funcionario,credito_salarios.concepto,"
                + "credito_salarios.importe,credito_salarios.fechagrabado,credito_salarios.observacion,"
                + "credito_salarios.idusuario,"
                + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario,"
                + "concepto_salarios.nombre AS nombreconcepto, concepto_salarios.tipo AS tipoconcepto "
                + "FROM credito_salarios "
                + "LEFT JOIN empleados "
                + "ON empleados.codigo=credito_salarios.funcionario "
                + "LEFT JOIN concepto_salarios "
                + "ON concepto_salarios.codigo=credito_salarios.concepto "
                + "WHERE credito_salarios.fecha between ? AND ? "
                + " ORDER BY credito_salarios.idcontrol ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                credito_salario credito = new credito_salario();
                concepto_salario concepto = new concepto_salario();
                ficha_empleado ficha = new ficha_empleado();
                credito.setFuncionario(ficha);
                credito.setConcepto(concepto);
                credito.setObservacion(rs.getString("observacion"));
                credito.setIdcontrol(rs.getDouble("idcontrol"));
                credito.setFecha(rs.getDate("fecha"));
                credito.setFechagrabado(rs.getDate("fechagrabado"));
                credito.setImporte(rs.getBigDecimal("importe"));
                credito.getConcepto().setCodigo(rs.getInt("concepto"));
                credito.getConcepto().setNombre(rs.getString("nombreconcepto"));
                credito.getConcepto().setTipo(rs.getString("tipoconcepto"));
                credito.getFuncionario().setCodigo(rs.getInt("funcionario"));
                credito.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                credito.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));
                System.out.println(credito.getFuncionario().getCodigo());
                lista.add(credito);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<credito_salario> MostrarxFechaxSuc(Date fechaini, Date fechafin, Integer suc) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT credito_salarios.idcontrol,credito_salarios.fecha,"
                + "credito_salarios.funcionario,credito_salarios.concepto,"
                + "credito_salarios.importe,credito_salarios.fechagrabado,credito_salarios.observacion,"
                + "credito_salarios.idusuario,"
                + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario,"
                + "concepto_salarios.nombre AS nombreconcepto, concepto_salarios.tipo AS tipoconcepto "
                + "FROM credito_salarios "
                + "LEFT JOIN empleados "
                + "ON empleados.codigo=credito_salarios.funcionario "
                + "LEFT JOIN concepto_salarios "
                + "ON concepto_salarios.codigo=credito_salarios.concepto "
                + "WHERE credito_salarios.fecha between ? AND ? "
                + " AND credito_salarios.idsucursal=? "
                + " ORDER BY credito_salarios.idcontrol ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ps.setInt(3, suc);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                credito_salario credito = new credito_salario();
                concepto_salario concepto = new concepto_salario();
                ficha_empleado ficha = new ficha_empleado();
                credito.setFuncionario(ficha);
                credito.setObservacion(rs.getString("observacion"));
                credito.setConcepto(concepto);
                credito.setIdcontrol(rs.getDouble("idcontrol"));
                credito.setFecha(rs.getDate("fecha"));
                credito.setFechagrabado(rs.getDate("fechagrabado"));
                credito.setImporte(rs.getBigDecimal("importe"));
                credito.getConcepto().setCodigo(rs.getInt("concepto"));
                credito.getConcepto().setNombre(rs.getString("nombreconcepto"));
                credito.getConcepto().setTipo(rs.getString("tipoconcepto"));
                credito.getFuncionario().setCodigo(rs.getInt("funcionario"));
                credito.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                credito.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));
                System.out.println(credito.getFuncionario().getCodigo());
                lista.add(credito);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public credito_salario buscarId(double id) throws SQLException {

        credito_salario credito = new credito_salario();
        concepto_salario concepto = new concepto_salario();
        ficha_empleado ficha = new ficha_empleado();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT credito_salarios.idcontrol,credito_salarios.fecha,"
                    + "credito_salarios.funcionario,credito_salarios.concepto,"
                    + "credito_salarios.importe,credito_salarios.fechagrabado,credito_salarios.observacion,"
                    + "credito_salarios.idusuario,"
                    + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario,"
                    + "concepto_salarios.nombre AS nombreconcepto, concepto_salarios.tipo AS tipoconcepto "
                    + "FROM credito_salarios "
                    + "LEFT JOIN empleados "
                    + "ON empleados.codigo=credito_salarios.funcionario "
                    + "LEFT JOIN concepto_salarios "
                    + "ON concepto_salarios.codigo=credito_salarios.concepto "
                    + "WHERE credito_salarios.idcontrol=? "
                    + " ORDER BY credito_salarios.idcontrol ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    credito.setFuncionario(ficha);
                    credito.setConcepto(concepto);
                    credito.setIdcontrol(rs.getDouble("idcontrol"));
                    credito.setObservacion(rs.getString("observacion"));
                    credito.setFecha(rs.getDate("fecha"));
                    credito.setFechagrabado(rs.getDate("fechagrabado"));
                    credito.setImporte(rs.getBigDecimal("importe"));
                    credito.getConcepto().setCodigo(rs.getInt("concepto"));
                    credito.getConcepto().setNombre(rs.getString("nombreconcepto"));
                    credito.getConcepto().setTipo(rs.getString("tipoconcepto"));
                    credito.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    credito.getFuncionario().setNombreempleado(rs.getString("nombrefuncionario"));
                    credito.getFuncionario().setDireccion(rs.getString("direccionfuncionario"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return credito;
    }

    public credito_salario InsertarCredito(credito_salario credito) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO credito_salarios (fecha,funcionario,"
                + "concepto,importe,idusuario,idsucursal,observacion)  VALUES (?,?,?,?,?,?,?)");
        ps.setDate(1, credito.getFecha());
        ps.setInt(2, credito.getFuncionario().getCodigo());
        ps.setInt(3, credito.getConcepto().getCodigo());
        ps.setBigDecimal(4, credito.getImporte());
        ps.setInt(5, credito.getIdusuario());
        ps.setInt(6, credito.getGiraduria());
        ps.setString(7, credito.getObservacion());
        ps.executeUpdate();
        st.close();
        ps.close();
        return credito;
    }

    public boolean ActualizarCredito(credito_salario credito) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE credito_salarios SET fecha=?,funcionario=?,"
                + "concepto=?,importe=?,idusuario=?,idsucursal=?,observacion=? "
                + " WHERE idcontrol=" + credito.getIdcontrol());
        ps.setDate(1, credito.getFecha());
        ps.setInt(2, credito.getFuncionario().getCodigo());
        ps.setInt(3, credito.getConcepto().getCodigo());
        ps.setBigDecimal(4, credito.getImporte());
        ps.setInt(5, credito.getIdusuario());
        ps.setInt(6, credito.getGiraduria());
        ps.setString(7, credito.getObservacion());

        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarCredito(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM credito_salarios WHERE idcontrol=?");
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

    public ArrayList<credito_salario> SaldoAnteriorExtracto(Integer nfuncionario, Date fechaini) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT empleados.codigo,CONCAT(empleados.nombres,' ',empleados.apellidos) "
                + "AS nombreempleado, "
                + "COALESCE(cred.credito,0) AS credito,"
                + "COALESCE(deb.debito,0) AS debito "
                + "FROM empleados "
                + "LEFT JOIN (SELECT credito_salarios.funcionario,"
                + " SUM(credito_salarios.importe) AS credito "
                + "FROM credito_salarios "
                + "WHERE credito_salarios.fecha < '" + fechaini + "' "
                + "GROUP BY credito_salarios.funcionario) "
                + "cred ON cred.funcionario=empleados.codigo "
                + "LEFT JOIN (SELECT debito_salarios.funcionario, "
                + "SUM(debito_salarios.importe) AS debito "
                + "FROM debito_salarios "
                + "WHERE debito_salarios.fecha <'" + fechaini + "' "
                + "GROUP BY debito_salarios.funcionario)"
                + " deb ON deb.funcionario=empleados.codigo "
                +" WHERE empleados.codigo="+nfuncionario
                + " ORDER BY empleados.codigo";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                credito_salario credito = new credito_salario();
                System.out.println(rs.getDouble("credito"));
                System.out.println(rs.getDouble("debito"));
                credito.setCreditos(rs.getDouble("credito"));
                credito.setDebitos(rs.getDouble("debito"));
                lista.add(credito);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<credito_salario> MostrarExtracto(Integer nfuncionario, Date fechaini, Date fechafin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT credito_salarios.idcontrol,credito_salarios.fecha,credito_salarios.observacion,"
                + "credito_salarios.funcionario,credito_salarios.concepto,"
                + "credito_salarios.importe AS creditos,0000000000 AS debitos,"
                + "CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado "
                + "FROM credito_salarios "
                + "LEFT JOIN empleados "
                + "ON empleados.codigo=credito_salarios.funcionario "
                + "WHERE credito_salarios.funcionario=" + nfuncionario
                + " AND fecha between '" + fechaini + "' AND '" + fechafin + "' "
                + "UNION "
                + "SELECT debito_salarios.idcontrol,debito_salarios.fecha,debito_salarios.observacion,"
                + "debito_salarios.funcionario,debito_salarios.concepto,"
                + "0000000000 AS creditos,debito_salarios.importe AS debitos,"
                + "CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado "
                + "FROM debito_salarios "
                + "LEFT JOIN empleados "
                + "ON empleados.codigo=debito_salarios.funcionario "
                + "WHERE debito_salarios.funcionario=" + nfuncionario
                + " AND fecha between '" + fechaini + "' AND '" + fechafin + "' "
                + "ORDER BY fecha";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                credito_salario credito = new credito_salario();
                concepto_salario concepto = new concepto_salario();
                ficha_empleado ficha = new ficha_empleado();
                credito.setFuncionario(ficha);
                credito.setObservacion(rs.getString("observacion"));
                credito.setConcepto(concepto);
                credito.setIdcontrol(rs.getDouble("idcontrol"));
                credito.setFecha(rs.getDate("fecha"));
                credito.setObservacion(rs.getString("observacion"));
                credito.getFuncionario().setCodigo(rs.getInt("funcionario"));
                credito.getFuncionario().setNombreempleado(rs.getString("nombreempleado"));
                credito.setCreditos(rs.getDouble("creditos"));
                credito.setDebitos(rs.getDouble("debitos"));
                System.out.println(credito.getFuncionario().getCodigo());
                lista.add(credito);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<credito_salario> MostrarResumenExtracto(Integer ngiraduria, Date fechaini, Date fechafin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT empleados.codigo,"
                + "CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado, "
                + "COALESCE(crea.creanterior,0) AS creanterior,"
                + "COALESCE(deba.debanterior,0) AS debanterior,"
                + "COALESCE(cred.credito,0) AS credito,"
                + "COALESCE(deb.debito,0) AS debito "
                + "FROM empleados "
                + "LEFT JOIN (SELECT credito_salarios.funcionario, "
                + "SUM(credito_salarios.importe) AS creanterior "
                + "FROM credito_salarios "
                + "WHERE credito_salarios.fecha < '" + fechaini + "' "
                + "GROUP BY credito_salarios.funcionario) crea ON crea.funcionario=empleados.codigo "
                + "LEFT JOIN (SELECT debito_salarios.funcionario,"
                + " SUM(debito_salarios.importe) AS debanterior "
                + "FROM debito_salarios "
                + "WHERE debito_salarios.fecha < '" + fechaini + "' "
                + "GROUP BY debito_salarios.funcionario) deba ON deba.funcionario=empleados.codigo "
                + "LEFT JOIN (SELECT credito_salarios.funcionario,"
                + " SUM(credito_salarios.importe) AS credito "
                + "FROM credito_salarios "
                + "WHERE credito_salarios.fecha >= '" + fechaini + "' AND credito_salarios.fecha <= '" + fechafin + "' "
                + "GROUP BY credito_salarios.funcionario) cred ON cred.funcionario=empleados.codigo "
                + "LEFT JOIN (SELECT debito_salarios.funcionario, SUM(debito_salarios.importe) AS debito "
                + "FROM debito_salarios "
                + "WHERE debito_salarios.fecha >= '" + fechaini + "' AND debito_salarios.fecha <= '" + fechafin + "' "
                + "GROUP BY debito_salarios.funcionario) deb ON deb.funcionario=empleados.codigo "
                +" WHERE empleados.giraduria="+ngiraduria
                + " ORDER BY empleados.codigo";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                credito_salario credito = new credito_salario();
                concepto_salario concepto = new concepto_salario();
                ficha_empleado ficha = new ficha_empleado();
                credito.setFuncionario(ficha);
                credito.getFuncionario().setCodigo(rs.getInt("codigo"));
                credito.getFuncionario().setNombreempleado(rs.getString("nombreempleado"));
                credito.setSaldoanterior(rs.getDouble("creanterior")-rs.getDouble("debanterior"));
                credito.setCreditos(rs.getDouble("credito"));
                credito.setDebitos(rs.getDouble("debito"));
                credito.setSaldoactual(credito.getSaldoanterior()+((credito.getCreditos()-credito.getDebitos())));
                lista.add(credito);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

}

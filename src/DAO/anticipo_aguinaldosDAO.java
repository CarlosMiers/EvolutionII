/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.anticipo_aguinaldos;
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
public class anticipo_aguinaldosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<anticipo_aguinaldos> Todos() throws SQLException {
        ArrayList<anticipo_aguinaldos> lista = new ArrayList<anticipo_aguinaldos>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT anticipo_aguinaldos.idcontrol,anticipo_aguinaldos.fecha,"
                    + "anticipo_aguinaldos.funcionario,"
                    + "anticipo_aguinaldos.importe,anticipo_aguinaldos.fechagrabado,"
                    + "anticipo_aguinaldos.idusuario,"
                    + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario "
                    + " FROM anticipo_aguinaldos "
                    + " LEFT JOIN empleados"
                    + " ON empleados.codigo=anticipo_aguinaldos.funcionario"
                    + " ORDER BY anticipo_aguinaldos.idcontrol ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    anticipo_aguinaldos anti = new anticipo_aguinaldos();
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

    public ArrayList<anticipo_aguinaldos> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT anticipo_aguinaldos.idcontrol,anticipo_aguinaldos.fecha,"
                + "anticipo_aguinaldos.funcionario,"
                + "anticipo_aguinaldos.importe,anticipo_aguinaldos.fechagrabado,"
                + "anticipo_aguinaldos.idusuario,"
                + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario "
                + "FROM anticipo_aguinaldos "
                + "LEFT JOIN empleados "
                + "ON empleados.codigo=anticipo_aguinaldos.funcionario "
                + "WHERE anticipo_aguinaldos.fecha between ? AND ? "
                + " ORDER BY anticipo_aguinaldos.idcontrol ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                anticipo_aguinaldos anti = new anticipo_aguinaldos();
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

    public anticipo_aguinaldos buscarId(double id) throws SQLException {

        anticipo_aguinaldos anti = new anticipo_aguinaldos();
        ficha_empleado ficha = new ficha_empleado();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT anticipo_aguinaldos.idcontrol,anticipo_aguinaldos.fecha,"
                    + "anticipo_aguinaldos.funcionario,"
                    + "anticipo_aguinaldos.importe,anticipo_aguinaldos.fechagrabado,"
                    + "anticipo_aguinaldos.idusuario,"
                    + "CONCAT(empleados.nombres,', ', empleados.apellidos) AS nombrefuncionario,empleados.direccion AS direccionfuncionario "
                    + "FROM anticipo_aguinaldos "
                    + "LEFT JOIN empleados "
                    + "ON empleados.codigo=anticipo_aguinaldos.funcionario "
                    + "WHERE anticipo_aguinaldos.idcontrol=? "
                    + " ORDER BY anticipo_aguinaldos.idcontrol ";

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

    public anticipo_aguinaldos InsertarAnticipo(anticipo_aguinaldos anti) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO anticipo_aguinaldos (fecha,funcionario,importe,idusuario,idsucursal)"
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

    public boolean ActualizarAnticipo(anticipo_aguinaldos anti) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE anticipo_aguinaldos SET fecha=?,"
                + "funcionario=?,importe=?,idusuario=?,idsucursal=? WHERE idcontrol=" + anti.getIdcontrol());
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

        ps = st.getConnection().prepareStatement("DELETE FROM anticipo_aguinaldos WHERE idcontrol=?");
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

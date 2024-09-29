package DAO;

import Conexion.Conexion;
import Modelo.sucursal;
import Modelo.aguinaldo;
import Modelo.configuracion;
import Modelo.ficha_empleado;
import Modelo.giraduria;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class aguinaldoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<aguinaldo> consultarPlanilla(int nperiodo, int giraduria) throws SQLException {
        ArrayList<aguinaldo> lista = new ArrayList<aguinaldo>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT planilla_aguinaldos.fecha,planilla_aguinaldos.idnumero,planilla_aguinaldos.mes,"
                    + "planilla_aguinaldos.periodo,planilla_aguinaldos.sucursal,"
                    + "planilla_aguinaldos.giraduria,planilla_aguinaldos.funcionario,+"
                    + "planilla_aguinaldos.salario_bruto,planilla_aguinaldos.aguinaldo,"
                    + "sucursales.nombre as nombresucursal,"
                    + "CONCAT(empleados.nombres,', ',empleados.apellidos) AS nombreempleado,"
                    + "giradurias.nombre as nombregiraduria "
                    + "FROM planilla_aguinaldos "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=planilla_aguinaldos.sucursal "
                    + "INNER JOIN empleados "
                    + "ON empleados.codigo=planilla_aguinaldos.funcionario "
                    + "INNER JOIN giradurias "
                    + "ON giradurias.codigo=planilla_aguinaldos.giraduria "
                    + "WHERE planilla_aguinaldos.periodo=? "
                    + " AND planilla_aguinaldos.giraduria=? "
                    + " ORDER BY giradurias.codigo,planilla_aguinaldos.funcionario ";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, nperiodo);
                ps.setInt(2, giraduria);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    aguinaldo liquidacion = new aguinaldo();
                    ficha_empleado ficha = new ficha_empleado();
                    giraduria gira = new giraduria();

                    liquidacion.setIdnumero(rs.getDouble("idnumero"));
                    liquidacion.setFecha(rs.getDate("fecha"));
                    liquidacion.setMes(rs.getInt("mes"));
                    liquidacion.setPeriodo(rs.getInt("periodo"));
                    liquidacion.setSucursal(sucursal);
                    liquidacion.getSucursal().setCodigo(rs.getInt("sucursal"));
                    liquidacion.getSucursal().setNombre(rs.getString("nombresucursal"));
                    liquidacion.setGiraduria(gira);
                    liquidacion.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    liquidacion.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    liquidacion.setFuncionario(ficha);
                    liquidacion.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    liquidacion.getFuncionario().setNombres(rs.getString("nombreempleado"));
                    liquidacion.setSalario_bruto(rs.getDouble("salario_bruto"));
                    liquidacion.setAguinaldo(rs.getDouble("aguinaldo"));
                    lista.add(liquidacion);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }

        st.close();
        return lista;
    }

    public ArrayList<aguinaldo> Todos(Date dFechaIni, Date dFechaFin) throws SQLException {
        ArrayList<aguinaldo> lista = new ArrayList<aguinaldo>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT planilla_aguinaldos.fecha,planilla_aguinaldos.idnumero,planilla_aguinaldos.mes,"
                    + "planilla_aguinaldos.periodo,planilla_aguinaldos.sucursal,"
                    + "planilla_aguinaldos.giraduria,planilla_aguinaldos.funcionario,+"
                    + "planilla_aguinaldos.salario_bruto,planilla_aguinaldos.aguinaldo,"
                    + "sucursales.nombre as nombresucursal,"
                    + "CONCAT(empleados.nombres,', ',empleados.apellidos) AS nombreempleado,"
                    + "giradurias.nombre as nombregiraduria "
                    + "FROM planilla_aguinaldos "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=planilla_aguinaldos.sucursal "
                    + "INNER JOIN empleados "
                    + "ON empleados.codigo=planilla_aguinaldos.funcionario "
                    + "INNER JOIN giradurias "
                    + "ON giradurias.codigo=planilla_aguinaldos.giraduria "
                    + " WHERE  planilla_aguinaldos.fecha between ? AND ? "
                    + " ORDER BY idnumero ";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, dFechaIni);
                ps.setDate(2, dFechaFin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    aguinaldo liquidacion = new aguinaldo();
                    ficha_empleado ficha = new ficha_empleado();
                    giraduria gira = new giraduria();

                    liquidacion.setIdnumero(rs.getDouble("idnumero"));
                    liquidacion.setFecha(rs.getDate("fecha"));
                    liquidacion.setMes(rs.getInt("mes"));
                    liquidacion.setPeriodo(rs.getInt("periodo"));
                    liquidacion.setSucursal(sucursal);
                    liquidacion.getSucursal().setCodigo(rs.getInt("sucursal"));
                    liquidacion.getSucursal().setNombre(rs.getString("nombresucursal"));
                    liquidacion.setGiraduria(gira);
                    liquidacion.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    liquidacion.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    liquidacion.setFuncionario(ficha);
                    liquidacion.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    liquidacion.getFuncionario().setNombres(rs.getString("nombreempleado"));
                    liquidacion.setSalario_bruto(rs.getDouble("salario_bruto"));
                    liquidacion.setAguinaldo(rs.getDouble("aguinaldo"));
                    lista.add(liquidacion);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }

        st.close();
        return lista;
    }

    public aguinaldo buscarId(int id) throws SQLException {
        aguinaldo liquidacion = new aguinaldo();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT planilla_aguinaldos.fecha,planilla_aguinaldos.idnumero,planilla_aguinaldos.mes,"
                    + "planilla_aguinaldos.periodo,planilla_aguinaldos.sucursal,"
                    + "planilla_aguinaldos.giraduria,planilla_aguinaldos.funcionario,+"
                    + "planilla_aguinaldos.salario_bruto,planilla_aguinaldos.aguinaldo,"
                    + "sucursales.nombre as nombresucursal,"
                    + "CONCAT(empleados.nombres,', ',empleados.apellidos) AS nombreempleado,"
                    + "giradurias.nombre as nombregiraduria "
                    + "FROM planilla_aguinaldos "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=planilla_aguinaldos.sucursal "
                    + "INNER JOIN empleados "
                    + "ON empleados.codigo=planilla_aguinaldos.funcionario "
                    + "INNER JOIN giradurias "
                    + "ON giradurias.codigo=planilla_aguinaldos.giraduria "
                    + " WHERE  planilla_aguinaldos.idnumero= ? "
                    + " ORDER BY idnumero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    sucursal sucursal = new sucursal();
                    ficha_empleado ficha = new ficha_empleado();
                    giraduria gira = new giraduria();

                    liquidacion.setIdnumero(rs.getDouble("idnumero"));
                    liquidacion.setFecha(rs.getDate("fecha"));
                    liquidacion.setMes(rs.getInt("mes"));
                    liquidacion.setPeriodo(rs.getInt("periodo"));
                    liquidacion.setSucursal(sucursal);
                    liquidacion.getSucursal().setCodigo(rs.getInt("sucursal"));
                    liquidacion.getSucursal().setNombre(rs.getString("nombresucursal"));
                    liquidacion.setGiraduria(gira);
                    liquidacion.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    liquidacion.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    liquidacion.setFuncionario(ficha);
                    liquidacion.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    liquidacion.getFuncionario().setNombres(rs.getString("nombreempleado"));
                    liquidacion.setSalario_bruto(rs.getDouble("salario_bruto"));
                    liquidacion.setAguinaldo(rs.getDouble("aguinaldo"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return liquidacion;
    }

    public aguinaldo insertar(aguinaldo vacacion) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO planilla_aguinaldos (fecha,mes,periodo,sucursal,"
                + "giraduria,funcionario,salario_bruto,aguinaldo)"
                + " VALUES (?,?,?,?,?,?,?,?)");
        ps.setDate(1, vacacion.getFecha());
        ps.setInt(2, vacacion.getMes());
        ps.setInt(3, vacacion.getPeriodo());
        ps.setInt(4, vacacion.getSucursal().getCodigo());
        ps.setInt(5, vacacion.getGiraduria().getCodigo());
        ps.setInt(6, vacacion.getFuncionario().getCodigo());
        ps.setDouble(7, vacacion.getSalario_bruto());
        ps.setDouble(8, vacacion.getAguinaldo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return vacacion;
    }

    public boolean actualizar(aguinaldo vacacion) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE planilla_aguinaldos SET fecha=?,"
                + "mes=?,periodo=?,sucursal=?,"
                + "giraduria=?,funcionario=?,salario_bruto=?,aguinaldo=? WHERE idnumero=" + vacacion.getIdnumero());
        ps.setDate(1, vacacion.getFecha());
        ps.setInt(2, vacacion.getMes());
        ps.setInt(3, vacacion.getPeriodo());
        ps.setInt(4, vacacion.getSucursal().getCodigo());
        ps.setInt(5, vacacion.getGiraduria().getCodigo());
        ps.setInt(6, vacacion.getFuncionario().getCodigo());
        ps.setDouble(7, vacacion.getSalario_bruto());
        ps.setDouble(8, vacacion.getAguinaldo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean eliminar(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM planilla_aguinaldos WHERE idnumero=?");
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

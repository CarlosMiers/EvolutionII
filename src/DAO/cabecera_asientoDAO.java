/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cabecera_asientos;
import Modelo.plan;
import Modelo.sucursal;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
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
public class cabecera_asientoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cabecera_asientos> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cabecera_asientos> lista = new ArrayList<cabecera_asientos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "select cabecera_asientos.numero,cabecera_asientos.fecha,cabecera_asientos.registro,cabecera_asientos.sucursal,"
                    + "cabecera_asientos.periodo,cabecera_asientos.debe,cabecera_asientos.haber,cabecera_asientos.grabado,"
                    + "sucursales.nombre as nombresucursal "
                    + "from cabecera_asientos "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_asientos.sucursal "
                    + "WHERE cabecera_asientos.fecha between ? AND ? "
                    + " ORDER BY cabecera_asientos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal suc = new sucursal();
                    cabecera_asientos asiento = new cabecera_asientos();
                    asiento.setNumero(rs.getDouble("numero"));
                    asiento.setSucursal(suc);
                    asiento.getSucursal().setCodigo(rs.getInt("sucursal"));
                    asiento.getSucursal().setNombre(rs.getString("nombresucursal"));
                    asiento.setFecha(rs.getDate("fecha"));
                    asiento.setPeriodo(rs.getInt("periodo"));
                    asiento.setGrabado(rs.getString("grabado"));
                    asiento.setHaber(rs.getDouble("haber"));
                    asiento.setDebe(rs.getDouble("debe"));
                    lista.add(asiento);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<cabecera_asientos> MostrarxFechaSinBalance(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cabecera_asientos> lista = new ArrayList<cabecera_asientos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_asientos.numero,cabecera_asientos.fecha,"
                    + "cabecera_asientos.registro,cabecera_asientos.sucursal,"
                    + "cabecera_asientos.periodo,cabecera_asientos.debe,cabecera_asientos.haber,cabecera_asientos.grabado,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "SUM(detalle_asientos.impdebe) AS totaldebe,"
                    + "SUM(detalle_asientos.imphaber) AS totalhaber "
                    + " FROM cabecera_asientos "
                    + " INNER JOIN sucursales "
                    + " ON sucursales.codigo=cabecera_asientos.sucursal "
                    + " INNER JOIN detalle_asientos"
                    + " ON detalle_asientos.asi_asient=cabecera_asientos.numero "
                    + " WHERE cabecera_asientos.fecha between ? AND ? "
                    + " GROUP BY cabecera_asientos.numero"
                    + " HAVING (totaldebe<>totalhaber)"
                    + " ORDER BY cabecera_asientos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal suc = new sucursal();
                    cabecera_asientos asiento = new cabecera_asientos();
                    asiento.setNumero(rs.getDouble("numero"));
                    asiento.setSucursal(suc);
                    asiento.getSucursal().setCodigo(rs.getInt("sucursal"));
                    asiento.getSucursal().setNombre(rs.getString("nombresucursal"));
                    asiento.setFecha(rs.getDate("fecha"));
                    asiento.setPeriodo(rs.getInt("periodo"));
                    asiento.setGrabado(rs.getString("grabado"));
                    asiento.setHaber(rs.getDouble("haber"));
                    asiento.setDebe(rs.getDouble("debe"));
                    lista.add(asiento);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public cabecera_asientos buscarId(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        cabecera_asientos asiento = new cabecera_asientos();

        try {

            String cSql = "select cabecera_asientos.numero,cabecera_asientos.fecha,cabecera_asientos.registro,cabecera_asientos.sucursal,"
                    + "cabecera_asientos.periodo,cabecera_asientos.debe,cabecera_asientos.haber,cabecera_asientos.grabado,"
                    + "sucursales.nombre as nombresucursal "
                    + "from cabecera_asientos "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_asientos.sucursal "
                    + "WHERE cabecera_asientos.numero=? "
                    + " ORDER BY cabecera_asientos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal suc = new sucursal();
                    asiento.setNumero(rs.getDouble("numero"));
                    asiento.setSucursal(suc);
                    asiento.getSucursal().setCodigo(rs.getInt("sucursal"));
                    asiento.getSucursal().setNombre(rs.getString("nombresucursal"));
                    asiento.setFecha(rs.getDate("fecha"));
                    asiento.setPeriodo(rs.getInt("periodo"));
                    asiento.setGrabado(rs.getString("grabado"));
                    asiento.setHaber(rs.getDouble("haber"));
                    asiento.setDebe(rs.getDouble("debe"));
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return asiento;
    }

    public cabecera_asientos consultar() throws SQLException {
        cabecera_asientos configuracion = new cabecera_asientos();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        String cSqlReg = "";
        try {

            String sql = "SELECT @rownum:=@rownum+1 AS rownum, cabecera_asientos.numero,cabecera_asientos.fecha "
                    + "FROM (SELECT @rownum:=0) r, "
                    + "cabecera_asientos "
                    + "ORDER BY fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getInt("rownum") == 1) {
                        configuracion.setNuminicio(rs.getDouble("rownum"));
                        configuracion.setFechainicio(rs.getDate("fecha"));
                    }
                    configuracion.setNumfinal(rs.getDouble("rownum"));
                    configuracion.setFechafinal(rs.getDate("fecha"));
                    cSqlReg = "UPDATE cabecera_asientos SET registro = " + rs.getDouble("rownum") + " WHERE numero=" + rs.getDouble("numero");
                    st.executeUpdate(cSqlReg);
                }
                ps.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return configuracion;
    }

    public ArrayList<cabecera_asientos> consultatabla(Date fechaini, Date fechafin, int nSucursal, int nSuc, String nInicio, String nFinal) throws SQLException {
        ArrayList<cabecera_asientos> lista = new ArrayList<cabecera_asientos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_asientos.numero, cabecera_asientos.registro, cabecera_asientos.fecha, cabecera_asientos.periodo,"
                    + "cabecera_asientos.debe, cabecera_asientos.grabado, cabecera_asientos.sucursal, detalle_asientos.asi_asient, detalle_asientos.asi_numero,"
                    + "detalle_asientos.asi_codigo, detalle_asientos.asi_descri,detalle_asientos.impdebe, detalle_asientos.imphaber,plan.codigo AS codigocuenta,plan.nombre AS nombrecuenta"
                    + " FROM cabecera_asientos "
                    + " LEFT JOIN detalle_asientos "
                    + " ON detalle_asientos.asi_asient=cabecera_asientos.numero "
                    + " LEFT JOIN plan "
                    + " ON plan.codigo=detalle_asientos.asi_codigo "
                    + " WHERE cabecera_asientos.fecha BETWEEN ? AND ? "
                    + " AND IF(?>0,cabecera_asientos.sucursal=?,TRUE) "
                    + " AND cabecera_asientos.numero BETWEEN ? AND ? "
                    + " ORDER BY cabecera_asientos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ps.setString(5, nInicio);
                ps.setString(6, nFinal);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cabecera_asientos CA = new cabecera_asientos();
                    sucursal sucursal = new sucursal();
                    plan plan = new plan();

                    CA.setSucursal(sucursal);
                    CA.setPlan(plan);

                    CA.setFecha(rs.getDate("fecha"));
                    CA.setNumero(rs.getDouble("numero"));
                    CA.setAsi_numero(rs.getString("asi_numero"));
                    CA.getPlan().setCodigo(rs.getString("codigocuenta"));
                    CA.getPlan().setNombre(rs.getString("nombrecuenta"));
                    CA.setAsi_descri(rs.getString("asi_descri"));
                    CA.setImpdebe(rs.getDouble("impdebe"));
                    CA.setImphaber(rs.getDouble("imphaber"));
                    CA.getSucursal().setCodigo(rs.getInt("sucursal"));
                    lista.add(CA);
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<cabecera_asientos> consultalibrodiario(Date fechaini, Date fechafin, int nSucursal, int nSuc, String nInicio, String nFinal) throws SQLException {
        ArrayList<cabecera_asientos> lista = new ArrayList<cabecera_asientos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_asientos.numero,cabecera_asientos.fecha, cabecera_asientos.registro, cabecera_asientos.periodo,"
                    + "cabecera_asientos.debe, cabecera_asientos.grabado, cabecera_asientos.sucursal, sucursales.nombre AS nombresucursal, detalle_asientos.asi_asient,"
                    + "detalle_asientos.asi_numero,detalle_asientos.asi_codigo, detalle_asientos.asi_descri,detalle_asientos.impdebe, detalle_asientos.imphaber,plan.codigo AS codigocuenta,"
                    + "plan.nombre AS nombrecuenta"
                    + " FROM cabecera_asientos  "
                    + " LEFT JOIN detalle_asientos "
                    + " ON detalle_asientos.asi_asient=cabecera_asientos.numero "
                    + " LEFT JOIN plan "
                    + " ON plan.codigo=detalle_asientos.asi_codigo "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=cabecera_asientos.sucursal "
                    + " WHERE cabecera_asientos.fecha BETWEEN ? AND ? "
                    + " AND IF(?>0,cabecera_asientos.sucursal=?,TRUE) "
                    + " AND cabecera_asientos.registro BETWEEN ? AND ? "
                    + " ORDER BY cabecera_asientos.registro ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ps.setString(5, nInicio);
                ps.setString(6, nFinal);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cabecera_asientos CA = new cabecera_asientos();
                    sucursal sucursal = new sucursal();
                    plan plan = new plan();

                    CA.setSucursal(sucursal);
                    CA.setPlan(plan);

                    CA.setFecha(rs.getDate("fecha"));
                    CA.setNumero(rs.getDouble("numero"));
                    CA.setRownum(rs.getDouble("registro"));
                    CA.setAsi_numero(rs.getString("asi_numero"));
                    CA.getPlan().setCodigo(rs.getString("codigocuenta"));
                    CA.getPlan().setNombre(rs.getString("nombrecuenta"));
                    CA.setAsi_descri(rs.getString("asi_descri"));
                    CA.setImpdebe(rs.getDouble("impdebe"));
                    CA.setImphaber(rs.getDouble("imphaber"));
                    CA.getSucursal().setCodigo(rs.getInt("sucursal"));
                    CA.getSucursal().setNombre(rs.getString("nombresucursal"));
                    lista.add(CA);
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<cabecera_asientos> buscadorlibrodiario(Date fechaini, Date fechafin, int nSucursal, int nSuc) throws SQLException {
        ArrayList<cabecera_asientos> lista = new ArrayList<cabecera_asientos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT @numero:=@numero+1 AS asiento, cabecera_asientos.numero,fecha"
                    + " FROM (SELECT @numero:=0) r, cabecera_asientos "
                    + " WHERE cabecera_asientos.fecha BETWEEN ? AND ? "
                    + " AND IF(?>0,cabecera_asientos.sucursal=?,TRUE) "
                    + " ORDER BY cabecera_asientos.fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nSucursal);
                ps.setInt(4, nSuc);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cabecera_asientos CA = new cabecera_asientos();
                    sucursal sucursal = new sucursal();
                    plan plan = new plan();

                    CA.setSucursal(sucursal);
                    CA.setPlan(plan);

                    CA.setFecha(rs.getDate("fecha"));
                    CA.setNumero(rs.getDouble("asiento"));
                    CA.setAsi_numero(rs.getString("numero"));
                    lista.add(CA);
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public cabecera_asientos AgregarAsiento(cabecera_asientos v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        double id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_asientos (sucursal,fecha,debe,haber,periodo) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, v.getSucursal().getCodigo());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getDebe());
        ps.setDouble(4, v.getHaber());
        ps.setInt(5, v.getPeriodo());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getDouble(1);
            guardado = guardarItemAsiento(id, detalle, con);
        }
        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public cabecera_asientos ActualizarAsiento(cabecera_asientos v, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        double id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  cabecera_asientos SET sucursal=?,fecha=?,debe=?,haber=? WHERE numero= " + v.getNumero());
        ps.setInt(1, v.getSucursal().getCodigo());
        ps.setDate(2, v.getFecha());
        ps.setDouble(3, v.getDebe());
        ps.setDouble(4, v.getDebe());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemAsiento(v.getNumero(), detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return v;
    }

    public boolean guardarItemAsiento(double id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalle);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {

                    //asi_asient,asi_numero,moneda,importe,cotizacion,asi_codigo,asi_descri,impdebe,imphaber,item
                    String sql = "INSERT INTO detalle_asientos("
                            + "asi_asient,"
                            + "asi_numero,"
                            + "moneda,"
                            + "importe,"
                            + "cotizacion,"
                            + "asi_codigo,"
                            + "asi_descri,"
                            + "impdebe,"
                            + "imphaber,"
                            + "item"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setDouble(1, id);
                        ps.setString(2, obj.get("asi_numero").getAsString());
                        ps.setString(3, obj.get("moneda").getAsString());
                        ps.setString(4, obj.get("importe").getAsString());
                        ps.setString(5, obj.get("cotizacion").getAsString());
                        ps.setString(6, obj.get("asi_codigo").getAsString());
                        ps.setString(7, obj.get("asi_descri").getAsString());
                        ps.setString(8, obj.get("impdebe").getAsString());
                        ps.setString(9, obj.get("imphaber").getAsString());
                        ps.setString(10, obj.get("item").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardado = false;
                    break;
                }
            }

            if (guardado) {
                try {
                    conn.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardado = false;
        }
        conn.close();
        return guardado;
    }

    public boolean eliminarAsiento(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cabecera_asientos WHERE numero=?");
        ps.setDouble(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<cabecera_asientos> libromayor(int nPeriodo, Date fechaini, Date fechafin, int nSucursal, String CtaInicio, String CtaFinal) throws SQLException {
        ArrayList<cabecera_asientos> lista = new ArrayList<cabecera_asientos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_asientos.numero,cabecera_asientos.sucursal,cabecera_asientos.fecha,"
                    + "detalle_asientos.asi_numero,"
                    + "detalle_asientos.moneda,importe,cotizacion,detalle_asientos.asi_codigo,plan.naturaleza,"
                    + "'SALDO ANTERIOR' AS asi_descri,00.00 AS impdebe,00.00 AS imphaber,"
                    + "plan.nombre AS nombrecuenta,sucursales.nombre AS nombresucursal,"
                    + "SUM(detalle_asientos.impdebe-detalle_asientos.imphaber) AS saldoanterior "
                    + "FROM cabecera_asientos "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_asientos.sucursal "
                    + "INNER JOIN detalle_asientos "
                    + "ON  detalle_asientos.asi_asient=cabecera_asientos.numero "
                    + "INNER JOIN plan "
                    + " ON detalle_asientos.asi_codigo=plan.codigo "
                    + " WHERE fecha<'" + fechaini + "'"
                    + " AND YEAR(fecha)=" + nPeriodo;
            if (nSucursal != 0) {
                cSql = cSql + " AND cabecera_asientos.sucursal= " + nSucursal;
            }
            cSql = cSql + " AND detalle_asientos.asi_codigo>='" + CtaInicio + "'"
                    + " AND detalle_asientos.asi_codigo<='" + CtaFinal + "'"
                    + " GROUP BY detalle_asientos.asi_codigo "
                    + " UNION "
                    + "SELECT cabecera_asientos.numero,cabecera_asientos.sucursal,cabecera_asientos.fecha,"
                    + "detalle_asientos.asi_numero,"
                    + "detalle_asientos.moneda,importe,cotizacion,detalle_asientos.asi_codigo,plan.naturaleza,"
                    + "detalle_asientos.asi_descri,detalle_asientos.impdebe,detalle_asientos.imphaber,"
                    + "plan.nombre AS nombrecuenta,sucursales.nombre AS nombresucursal,00.00 AS saldoanterior "
                    + "FROM cabecera_asientos "
                    + "INNER JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_asientos.sucursal "
                    + "INNER JOIN detalle_asientos "
                    + "ON  detalle_asientos.asi_asient=cabecera_asientos.numero "
                    + "INNER JOIN plan "
                    + " ON detalle_asientos.asi_codigo=plan.codigo "
                    + " WHERE fecha>='" + fechaini + "'"
                    + " AND fecha<='" + fechafin + "'"
                    + " AND YEAR(fecha)=" + nPeriodo;
            if (nSucursal != 0) {
                cSql = cSql + " AND cabecera_asientos.sucursal= " + nSucursal;
            }
            cSql = cSql + " AND detalle_asientos.asi_codigo>='" + CtaInicio + "'"
                    + " AND detalle_asientos.asi_codigo<='" + CtaFinal + "'"
                    + " ORDER BY asi_codigo,fecha";

            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cabecera_asientos CA = new cabecera_asientos();
                    sucursal sucursal = new sucursal();
                    plan plan = new plan();

                    CA.setSucursal(sucursal);
                    CA.setPlan(plan);

                    CA.setFecha(rs.getDate("fecha"));
                    CA.setNumero(rs.getDouble("numero"));
                    CA.setAsi_numero(rs.getString("asi_numero"));
                    CA.getPlan().setCodigo(rs.getString("asi_codigo"));
                    CA.getPlan().setNombre(rs.getString("nombrecuenta"));
                    CA.getPlan().setNaturaleza(rs.getString("naturaleza"));
                    CA.setAsi_descri(rs.getString("asi_descri"));
                    CA.setSaldoanterior(rs.getDouble("saldoanterior"));
                    CA.setImphaber(rs.getDouble("imphaber"));
                    CA.setImpdebe(rs.getDouble("impdebe"));
                    CA.getSucursal().setCodigo(rs.getInt("sucursal"));
                    CA.getSucursal().setNombre(rs.getString("nombresucursal"));
                    if (CA.getAsi_descri().equals("SALDO ANTERIOR") && CA.getSaldoanterior() != 0) {
                        CA.setNumero(0);
                        CA.setImpdebe(0);
                        CA.setImphaber(0);
                        lista.add(CA);
                    }
                    if (CA.getImpdebe() != 0 || CA.getImphaber() != 0 && !CA.getAsi_descri().equals("SALDO ANTERIOR")) {
                        lista.add(CA);
                    }
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

}

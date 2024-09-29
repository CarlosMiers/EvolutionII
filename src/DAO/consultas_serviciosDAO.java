/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.consultas_servicios;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
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
public class consultas_serviciosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<consultas_servicios> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<consultas_servicios> lista = new ArrayList<consultas_servicios>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT consultas_servicios.numero,consultas_servicios.fecha,consultas_servicios.cliente,"
                    + "consultas_servicios.periodopago,consultas_servicios.vencimiento,consultas_servicios.totales,"
                    + "consultas_servicios.usuarioalta,consultas_servicios.fechaalta,"
                    + "clientes.nombre as nombrecliente "
                    + " FROM consultas_servicios "
                    + " left join clientes "
                    + " ON clientes.codigo=consultas_servicios.cliente "
                    + " WHERE consultas_servicios.fecha between ? AND ? "
                    + " ORDER BY consultas_servicios.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    consultas_servicios c = new consultas_servicios();
                    cliente cliente = new cliente();
                    c.setCliente(cliente);

                    c.setNumero(rs.getInt("numero"));
                    c.setFecha(rs.getDate("fecha"));
                    c.setVencimiento(rs.getDate("vencimiento"));
                    c.setPeriodopago(rs.getInt("periodopago"));
                    c.getCliente().setCodigo(rs.getInt("cliente"));
                    c.getCliente().setNombre(rs.getString("nombrecliente"));
                    c.setTotales(rs.getDouble("totales"));
                    c.setUsuarioalta(rs.getInt("usuarioalta"));
                    lista.add(c);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public consultas_servicios buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        consultas_servicios c = new consultas_servicios();

        try {

            String sql = "SELECT consultas_servicios.numero,consultas_servicios.fecha,consultas_servicios.cliente,"
                    + "consultas_servicios.periodopago,consultas_servicios.vencimiento,consultas_servicios.totales,"
                    + "consultas_servicios.usuarioalta,consultas_servicios.fechaalta,"
                    + "clientes.nombre as nombrecliente "
                    + " FROM consultas_servicios "
                    + " left join clientes "
                    + " ON clientes.codigo=consultas_servicios.cliente "
                    + " WHERE consultas_servicios.numero= ? "
                    + " ORDER BY consultas_servicios.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    c.setCliente(cliente);
                    c.setNumero(rs.getInt("numero"));
                    c.setFecha(rs.getDate("fecha"));
                    c.setVencimiento(rs.getDate("vencimiento"));
                    c.setPeriodopago(rs.getInt("periodopago"));
                    c.getCliente().setCodigo(rs.getInt("cliente"));
                    c.getCliente().setNombre(rs.getString("nombrecliente"));
                    c.setTotales(rs.getDouble("totales"));
                    c.setUsuarioalta(rs.getInt("usuarioalta"));
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return c;
    }

    public consultas_servicios Insertar(consultas_servicios ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        PreparedStatement ps = null;
        double id = 0;
        ps = st.getConnection().prepareStatement("INSERT INTO consultas_servicios(fecha,cliente,periodopago,vencimiento,totales,usuarioalta) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, ocr.getFecha());
        ps.setInt(2, ocr.getCliente().getCodigo());
        ps.setInt(3, ocr.getPeriodopago());
        ps.setDate(4, ocr.getVencimiento());
        ps.setDouble(5, ocr.getTotales());
        ps.setInt(6, ocr.getUsuarioalta());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getDouble(1);
            guardado = guardarDetalle(id, detalle, con);
        }
        st.close();
        ps.close();
        return ocr;
    }

    public boolean Actualizar(consultas_servicios ocr, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        boolean guardado = false;

        ps = st.getConnection().prepareStatement("UPDATE consultas_servicios SET fecha=?,cliente=?,periodopago=?,vencimiento=?,totales=?,usuarioalta=? WHERE numero=" + ocr.getNumero());
        ps.setDate(1, ocr.getFecha());
        ps.setInt(2, ocr.getCliente().getCodigo());
        ps.setInt(3, ocr.getPeriodopago());
        ps.setDate(4, ocr.getVencimiento());
        ps.setDouble(5, ocr.getTotales());
        ps.setInt(6, ocr.getUsuarioalta());

        int rowsUpdated = ps.executeUpdate();
        guardado = guardarDetalle(ocr.getNumero(), detalle, con);
        st.close();
        ps.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
    }

    public boolean guardarDetalle(double id, String detalle, Conexion conexion) throws SQLException {
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
                    String sql = "insert into detalle_consulta_servicios("
                            + "dnumero,"
                            + "servicio,"
                            + "importe"
                            + ")"
                            + "values(?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setDouble(1, id);
                        ps.setString(2, obj.get("servicio").getAsString());
                        ps.setString(3, obj.get("importe").getAsString());
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
        st.close();
        conn.close();
        return guardado;
    }

    public boolean Borrar(Double numero) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM consultas_servicios WHERE numero=?");
        ps.setDouble(1, numero);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<consultas_servicios> MostrarVencimientos(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<consultas_servicios> lista = new ArrayList<consultas_servicios>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT  numero,fecha,cliente,periodopago,max(vencimiento) as vence,"
                    + "clientes.nombre as nombrecliente,"
                    + "CASE periodopago "
                    + "WHEN 1 THEN 'Diario' "
                    + "WHEN 2 THEN 'Semanal' "
                    + "WHEN 3 THEN 'Quincenal' "
                    + "WHEN 4 THEN 'Mensual' "
                    + "END AS calendario "
                    + " FROM consultas_servicios "
                    + " INNER JOIN clientes "
                    + " ON clientes.codigo=consultas_servicios.cliente "
                    + " WHERE clientes.estado=1 "
                    + " GROUP BY cliente "
                    + " HAVING(vence BETWEEN ? AND ? )";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    consultas_servicios c = new consultas_servicios();
                    cliente cliente = new cliente();
                    c.setCliente(cliente);

                    c.setNumero(rs.getInt("numero"));
                    c.setFecha(rs.getDate("fecha"));
                    c.setVencimiento(rs.getDate("vence"));
                    c.setPeriodopago(rs.getInt("periodopago"));
                    c.getCliente().setCodigo(rs.getInt("cliente"));
                    c.getCliente().setNombre(rs.getString("nombrecliente"));
                    c.setTipovencimiento(rs.getString("calendario"));
                    lista.add(c);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }
    
        public ArrayList<consultas_servicios> MostrarVencidos(Date fechaini) throws SQLException {
        ArrayList<consultas_servicios> lista = new ArrayList<consultas_servicios>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT  numero,fecha,cliente,periodopago,max(vencimiento) as vence,"
                    + "clientes.nombre as nombrecliente,"
                    + "CASE periodopago "
                    + "WHEN 1 THEN 'Diario' "
                    + "WHEN 2 THEN 'Semanal' "
                    + "WHEN 3 THEN 'Quincenal' "
                    + "WHEN 4 THEN 'Mensual' "
                    + "END AS calendario "
                    + " FROM consultas_servicios "
                    + " INNER JOIN clientes "
                    + " ON clientes.codigo=consultas_servicios.cliente "
                    + " WHERE clientes.estado=1 "
                    + " GROUP BY cliente "
                    + " HAVING(vence < ? )";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    consultas_servicios c = new consultas_servicios();
                    cliente cliente = new cliente();
                    c.setCliente(cliente);

                    c.setNumero(rs.getInt("numero"));
                    c.setFecha(rs.getDate("fecha"));
                    c.setVencimiento(rs.getDate("vence"));
                    c.setPeriodopago(rs.getInt("periodopago"));
                    c.getCliente().setCodigo(rs.getInt("cliente"));
                    c.getCliente().setNombre(rs.getString("nombrecliente"));
                    c.setTipovencimiento(rs.getString("calendario"));
                    lista.add(c);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

}

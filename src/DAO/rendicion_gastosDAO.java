/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.banco;
import Modelo.rendicion_gastos;
import Modelo.sucursal;
import Modelo.moneda;
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
public class rendicion_gastosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<rendicion_gastos> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<rendicion_gastos> lista = new ArrayList<rendicion_gastos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT reposicion_fondos.numero,reposicion_fondos.fecha,reposicion_fondos.sucursal,"
                    + "reposicion_fondos.banco,reposicion_fondos.importe,reposicion_fondos.nrocheque,"
                    + "reposicion_fondos.moneda,reposicion_fondos.cotizacion,reposicion_fondos.creferencia,"
                    + "reposicion_fondos.asiento,reposicion_fondos.observacion,reposicion_fondos.cierre,"
                    + "bancos.nombre as nombrebanco,sucursales.nombre as nombresucursal,monedas.nombre as nombremoneda "
                    + "from reposicion_fondos "
                    + "left join bancos "
                    + "on bancos.codigo=reposicion_fondos.banco "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=reposicion_fondos.sucursal "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=reposicion_fondos.moneda "
                    + " WHERE reposicion_fondos.fecha between ? AND ?  "
                    + " ORDER BY reposicion_fondos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal suc = new sucursal();
                    banco ba = new banco();
                    moneda mo = new moneda();
                    rendicion_gastos cab = new rendicion_gastos();
                    cab.setNumero(rs.getDouble("numero"));
                    cab.setSucursal(suc);
                    cab.setBanco(ba);
                    cab.setMoneda(mo);
                    cab.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cab.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cab.getBanco().setCodigo(rs.getInt("banco"));
                    cab.getBanco().setNombre(rs.getString("nombrebanco"));
                    cab.getMoneda().setCodigo(rs.getInt("moneda"));
                    cab.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cab.setFecha(rs.getDate("fecha"));
                    cab.setImporte(rs.getDouble("importe"));
                    cab.setNrocheque(rs.getString("nrocheque"));
                    cab.setAsiento(rs.getDouble("asiento"));
                    cab.setCreferencia(rs.getString("creferencia"));
                    cab.setCotizacion(rs.getDouble("cotizacion"));
                    cab.setAsiento(rs.getDouble("asiento"));
                    lista.add(cab);
                }
                rs.close();
                ps.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public rendicion_gastos buscarId(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        rendicion_gastos cab = new rendicion_gastos();
        sucursal suc = new sucursal();
        banco ba = new banco();
        moneda mo = new moneda();

        try {

            String cSql = "SELECT reposicion_fondos.numero,reposicion_fondos.fecha,reposicion_fondos.sucursal,"
                    + "reposicion_fondos.banco,reposicion_fondos.importe,reposicion_fondos.nrocheque,"
                    + "reposicion_fondos.moneda,reposicion_fondos.cotizacion,reposicion_fondos.creferencia,"
                    + "reposicion_fondos.asiento,reposicion_fondos.observacion,reposicion_fondos.cierre,"
                    + "bancos.nombre as nombrebanco,sucursales.nombre as nombresucursal,monedas.nombre as nombremoneda "
                    + "from reposicion_fondos "
                    + "left join bancos "
                    + "on bancos.codigo=reposicion_fondos.banco "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=reposicion_fondos.sucursal "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=reposicion_fondos.moneda "
                    + " WHERE reposicion_fondos.numero= ?  "
                    + " ORDER BY reposicion_fondos.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cab.setNumero(rs.getDouble("numero"));
                    cab.setSucursal(suc);
                    cab.setBanco(ba);
                    cab.setMoneda(mo);
                    cab.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cab.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cab.getBanco().setCodigo(rs.getInt("banco"));
                    cab.getBanco().setNombre(rs.getString("nombrebanco"));
                    cab.getMoneda().setCodigo(rs.getInt("moneda"));
                    cab.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cab.setFecha(rs.getDate("fecha"));
                    cab.setImporte(rs.getDouble("importe"));
                    cab.setNrocheque(rs.getString("nrocheque"));
                    cab.setAsiento(rs.getDouble("asiento"));
                    cab.setCreferencia(rs.getString("creferencia"));
                    cab.setCotizacion(rs.getDouble("cotizacion"));
                    cab.setAsiento(rs.getDouble("asiento"));
                    cab.setObservacion(rs.getString("observacion"));
                    System.out.println("sucursal " + cab.getSucursal().getNombre());
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return cab;
    }

    public rendicion_gastos AgregarGastos(rendicion_gastos c, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        double id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO reposicion_fondos (creferencia,moneda,cotizacion,fecha,sucursal,banco,importe,nrocheque,usuarioalta,observacion) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getCreferencia());
        ps.setInt(2, c.getMoneda().getCodigo());
        ps.setDouble(3, c.getCotizacion());
        ps.setDate(4, c.getFecha());
        ps.setInt(5, c.getSucursal().getCodigo());
        ps.setInt(6, c.getBanco().getCodigo());
        ps.setDouble(7, c.getImporte());
        ps.setString(8, c.getNrocheque());
        ps.setInt(9, c.getUsuarioalta());
        ps.setString(10, c.getObservacion());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarItemGasto(id, detalle, con);
        }
        st.close();
        ps.close();
        conn.close();
        return c;
    }

    public rendicion_gastos ActualizarGastos(rendicion_gastos c, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        double id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  reposicion_fondos SET fecha=?,sucursal=?,banco=?,importe=?,nrocheque=?,usuarioalta=?,observacion=? WHERE numero= " + c.getNumero());
        ps.setDate(1, c.getFecha());
        ps.setInt(2, c.getSucursal().getCodigo());
        ps.setInt(3, c.getBanco().getCodigo());
        ps.setDouble(4, c.getImporte());
        ps.setString(5, c.getNrocheque());
        ps.setInt(6, c.getUsuarioalta());
        ps.setString(7, c.getObservacion());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemGasto(c.getNumero(), detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return c;
    }

    public boolean guardarItemGasto(double id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement psdetalle = null;
        psdetalle = st.getConnection().prepareStatement("DELETE FROM gastos_compras WHERE fondofijo=?");
        psdetalle.setDouble(1, id);
        int rowsUpdated = psdetalle.executeUpdate();

        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalle);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {

                    //  dnumero,nrofactura,unidad,fechafactura,concepto,monto,tipo,idcta
                    String sql = "INSERT INTO  gastos_compras(\n"
                            + "fondofijo,\n" //1
                            + "formatofactura,\n" //2
                            + "nrofactura,\n" //2
                            + "fecha,\n" //3
                            + "primer_vence,\n" //4
                            + "comprobante,\n" //6
                            + "concepto,\n" //
                            + "moneda,\n" //8
                            + "timbrado,\n" //10
                            + "vencetimbrado,\n" //11
                            + "proveedor,\n" //
                            + "exentas,\n" //13
                            + "gravadas10,\n" //14
                            + "iva10,\n" //15
                            + "gravadas5,\n" //16
                            + "iva5,\n" //17
                            + "totalneto,\n" //18
                            + "observacion,\n" //19
                            + "cotizacion,\n" //20
                            + "sucursal,\n" //20
                            + "creferencia\n" //21
                            + ") \n"
                            + "values(?, ?, ?, ?, ?, "
                            + "?, ?, ?, ?, ?, "
                            + "?, ?, ?, ?, ?, "
                            + "?, ?, ?, ?,?,?)";

                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setDouble(1, id);
                        ps.setString(2, obj.get("formatofactura").getAsString());
                        ps.setString(3, obj.get("nrofactura").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("primer_vence").getAsString());
                        ps.setInt(6, Integer.valueOf(obj.get("comprobante").getAsString()));
                        ps.setInt(7, Integer.valueOf(obj.get("concepto").getAsString()));
                        ps.setInt(8, Integer.valueOf(obj.get("moneda").getAsString()));
                        ps.setString(9, obj.get("timbrado").getAsString());
                        ps.setString(10, obj.get("vencetimbrado").getAsString());
                        ps.setInt(11, Integer.valueOf(obj.get("proveedor").getAsString()));
                        ps.setDouble(12, Double.valueOf(obj.get("exentas").getAsString()));
                        ps.setDouble(13, Double.valueOf(obj.get("gravadas10").getAsString()));
                        ps.setDouble(14, Double.valueOf(obj.get("iva10").getAsString()));
                        ps.setDouble(15, Double.valueOf(obj.get("gravadas5").getAsString()));
                        ps.setDouble(16, Double.valueOf(obj.get("iva5").getAsString()));
                        ps.setDouble(17, Double.valueOf(obj.get("totalneto").getAsString()));
                        ps.setString(18, obj.get("observacion").getAsString());
                        ps.setDouble(19, Double.valueOf(obj.get("cotizacion").getAsString()));
                        ps.setInt(20, Integer.valueOf(obj.get("sucursal").getAsString()));
                        ps.setString(21, obj.get("creferencia").getAsString());
                        //System.out.println(ps.toString());
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

    public boolean eliminarItemGastos(Double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM reposicion_fondos WHERE numero=?");
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

    public boolean borrardetalle(Double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM gastos_compras WHERE fondofijo=?");
        ps.setDouble(1, id);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public rendicion_gastos AgregarGastosContable(rendicion_gastos c, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conne = st.getConnection();
        conne.setAutoCommit(false);

        boolean guardado = false;
        boolean guardacuota = false;
        double id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO reposicion_fondos (creferencia,moneda,cotizacion,fecha,sucursal,banco,importe,nrocheque,usuarioalta,observacion) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getCreferencia());
        ps.setInt(2, c.getMoneda().getCodigo());
        ps.setDouble(3, c.getCotizacion());
        ps.setDate(4, c.getFecha());
        ps.setInt(5, c.getSucursal().getCodigo());
        ps.setInt(6, c.getBanco().getCodigo());
        ps.setDouble(7, c.getImporte());
        ps.setString(8, c.getNrocheque());
        ps.setInt(9, c.getUsuarioalta());
        ps.setString(10, c.getObservacion());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarItemGastoContable(id, detalle, con);
        }

        if (guardado) {
            try {
                conne.commit();
            } catch (SQLException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        } else {
            try {
                conne.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        st.close();
        ps.close();
        conne.close();
        return c;
    }

    public boolean guardarItemGastoContable(double id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement psdetalle = null;
        psdetalle = st.getConnection().prepareStatement("DELETE FROM gastos_compras WHERE fondofijo=?");
        psdetalle.setDouble(1, id);
        int rowsUpdated = psdetalle.executeUpdate();

        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalle);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {

                    //  dnumero,nrofactura,unidad,fechafactura,concepto,monto,tipo,idcta
                    String sql = "INSERT INTO  gastos_compras(\n"
                            + "fondofijo,\n" //1
                            + "formatofactura,\n" //2
                            + "nrofactura,\n" //2
                            + "fecha,\n" //3
                            + "primer_vence,\n" //4
                            + "comprobante,\n" //6
                            + "idcta,\n" //
                            + "moneda,\n" //8
                            + "timbrado,\n" //10
                            + "vencetimbrado,\n" //11
                            + "proveedor,\n" //
                            + "exentas,\n" //13
                            + "gravadas10,\n" //14
                            + "iva10,\n" //15
                            + "gravadas5,\n" //16
                            + "iva5,\n" //17
                            + "totalneto,\n" //18
                            + "observacion,\n" //19
                            + "cotizacion,\n" //20
                            + "sucursal,\n" //20
                            + "creferencia\n" //21
                            + ") \n"
                            + "values(?, ?, ?, ?, ?, "
                            + "?, ?, ?, ?, ?, "
                            + "?, ?, ?, ?, ?, "
                            + "?, ?, ?, ?,?,?)";

                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setDouble(1, id);
                        ps.setString(2, obj.get("formatofactura").getAsString());
                        ps.setString(3, obj.get("nrofactura").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("primer_vence").getAsString());
                        ps.setInt(6, Integer.valueOf(obj.get("comprobante").getAsString()));
                        ps.setInt(7, Integer.valueOf(obj.get("idcta").getAsString()));
                        ps.setInt(8, Integer.valueOf(obj.get("moneda").getAsString()));
                        ps.setString(9, obj.get("timbrado").getAsString());
                        ps.setString(10, obj.get("vencetimbrado").getAsString());
                        ps.setInt(11, Integer.valueOf(obj.get("proveedor").getAsString()));
                        ps.setDouble(12, Double.valueOf(obj.get("exentas").getAsString()));
                        ps.setDouble(13, Double.valueOf(obj.get("gravadas10").getAsString()));
                        ps.setDouble(14, Double.valueOf(obj.get("iva10").getAsString()));
                        ps.setDouble(15, Double.valueOf(obj.get("gravadas5").getAsString()));
                        ps.setDouble(16, Double.valueOf(obj.get("iva5").getAsString()));
                        ps.setDouble(17, Double.valueOf(obj.get("totalneto").getAsString()));
                        ps.setString(18, obj.get("observacion").getAsString());
                        ps.setDouble(19, Double.valueOf(obj.get("cotizacion").getAsString()));
                        ps.setInt(20, Integer.valueOf(obj.get("sucursal").getAsString()));
                        ps.setString(21, obj.get("creferencia").getAsString());
                        //System.out.println(ps.toString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado = false;
                            return guardado;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardado = false;
                    //break;
                    return guardado;
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

    public rendicion_gastos ActualizarGastosContable(rendicion_gastos c, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        double id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  reposicion_fondos SET fecha=?,sucursal=?,banco=?,importe=?,nrocheque=?,usuarioalta=?,observacion=? WHERE numero= " + c.getNumero());
        ps.setDate(1, c.getFecha());
        ps.setInt(2, c.getSucursal().getCodigo());
        ps.setInt(3, c.getBanco().getCodigo());
        ps.setDouble(4, c.getImporte());
        ps.setString(5, c.getNrocheque());
        ps.setInt(6, c.getUsuarioalta());
        ps.setString(7, c.getObservacion());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemGastoContable(c.getNumero(), detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return c;
    }

}

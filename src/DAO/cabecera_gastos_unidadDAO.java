/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.banco;
import Modelo.cabecera_gastos_unidad;
import Modelo.sucursal;
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
public class cabecera_gastos_unidadDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cabecera_gastos_unidad> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cabecera_gastos_unidad> lista = new ArrayList<cabecera_gastos_unidad>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cabecera_gastos_unidad.numero,cabecera_gastos_unidad.fecha,cabecera_gastos_unidad.sucursal,"
                    + "cabecera_gastos_unidad.banco,cabecera_gastos_unidad.importe,cabecera_gastos_unidad.nrocheque,"
                    + "cabecera_gastos_unidad.asiento,cabecera_gastos_unidad.observacion,cabecera_gastos_unidad.cierre,"
                    + "bancos.nombre as nombrebanco,sucursales.nombre as nombresucursal "
                    + "from cabecera_gastos_unidad "
                    + "left join bancos "
                    + "on bancos.codigo=cabecera_gastos_unidad.banco "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=cabecera_gastos_unidad.banco "
                    + "WHERE cabecera_gastos_unidad.fecha between ? AND ?  ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal suc = new sucursal();
                    banco ba = new banco();
                    cabecera_gastos_unidad cab = new cabecera_gastos_unidad();
                    cab.setNumero(rs.getDouble("numero"));
                    cab.setSucursal(suc);
                    cab.setBanco(ba);
                    cab.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cab.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cab.getBanco().setCodigo(rs.getInt("banco"));
                    cab.getBanco().setNombre(rs.getString("nombrebanco"));
                    cab.setFecha(rs.getDate("fecha"));
                    cab.setImporte(rs.getDouble("importe"));
                    cab.setNrocheque(rs.getString("nrocheque"));
                    cab.setAsiento(rs.getDouble("asiento"));
                    cab.setCierre(rs.getInt("cierre"));
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

    public cabecera_gastos_unidad buscarId(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        cabecera_gastos_unidad cab = new cabecera_gastos_unidad();

        try {

            String cSql = "SELECT cabecera_gastos_unidad.numero,cabecera_gastos_unidad.fecha,cabecera_gastos_unidad.sucursal,"
                    + "cabecera_gastos_unidad.banco,cabecera_gastos_unidad.importe,cabecera_gastos_unidad.nrocheque,"
                    + "cabecera_gastos_unidad.asiento,cabecera_gastos_unidad.observacion,cabecera_gastos_unidad.cierre,"
                    + "bancos.nombre as nombrebanco,sucursales.nombre as nombresucursal "
                    + "from cabecera_gastos_unidad "
                    + "LEFT JOIN bancos "
                    + " ON bancos.codigo=cabecera_gastos_unidad.banco "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=cabecera_gastos_unidad.sucursal "
                    + " WHERE cabecera_gastos_unidad.numero = ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sucursal suc = new sucursal();
                    banco ba = new banco();
                    cab.setNumero(rs.getDouble("numero"));
                    cab.setSucursal(suc);
                    cab.setBanco(ba);
                    cab.getSucursal().setCodigo(rs.getInt("sucursal"));
                    cab.getSucursal().setNombre(rs.getString("nombresucursal"));
                    cab.getBanco().setCodigo(rs.getInt("banco"));
                    cab.getBanco().setNombre(rs.getString("nombrebanco"));
                    cab.setFecha(rs.getDate("fecha"));
                    cab.setImporte(rs.getDouble("importe"));
                    cab.setNrocheque(rs.getString("nrocheque"));
                    cab.setAsiento(rs.getDouble("asiento"));
                    cab.setObservacion(rs.getString("observacion"));
                    cab.setCierre(rs.getInt("cierre"));
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


    public cabecera_gastos_unidad AgregarGastos(cabecera_gastos_unidad c, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        double id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_gastos_unidad (fecha,sucursal,banco,importe,nrocheque,usuarioalta,asiento,observacion) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, c.getFecha());
        ps.setInt(2, c.getSucursal().getCodigo());
        ps.setInt(3, c.getBanco().getCodigo());
        ps.setDouble(4, c.getImporte());
        ps.setString(5, c.getNrocheque());
        ps.setInt(6, c.getUsuarioalta());
        ps.setDouble(7, c.getAsiento());
        ps.setString(8, c.getObservacion());
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

    public cabecera_gastos_unidad ActualizarGastos(cabecera_gastos_unidad c, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        double id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  cabecera_gastos_unidad SET fecha=?,sucursal=?,banco=?,importe=?,nrocheque=?,usuarioalta=?,asiento=?,observacion=? WHERE numero= " + c.getNumero());
        ps.setDate(1, c.getFecha());
        ps.setInt(2, c.getSucursal().getCodigo());
        ps.setInt(3, c.getBanco().getCodigo());
        ps.setDouble(4, c.getImporte());
        ps.setString(5, c.getNrocheque());
        ps.setInt(6, c.getUsuarioalta());
        ps.setDouble(7, c.getAsiento());
        ps.setString(8, c.getObservacion());
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
                    String sql = "INSERT INTO  gastos_unidad(\n"
                                    + "dnumero,\n"              //1
                                    + "nrofactura,\n"           //2
                                    + "fechafactura,\n"         //3
                                    + "vencimiento,\n"          //4
                                    + "tipo,\n"                 //5
                                    + "comprobante,\n"          //6
                                    + "rubro,\n"                //
                                    + "moneda,\n"               //8
                                    + "unidad,\n"               //9
                                    + "timbrado,\n"             //10
                                    + "vencimientotimbrado,\n"  //11
                                    + "proveedor,\n"            //
                                    + "exentas,\n"              //13
                                    + "gravadas10,\n"           //14
                                    + "iva10,\n"                //15
                                    + "gravadas5,\n"            //16
                                    + "iva5,\n"                 //17
                                    + "monto,\n"                //18
                                    + "concepto,\n"             //19
                                    + "idcta\n"                 //20
                                + ") \n"
                                + "values(?, ?, ?, ?, ?, "
                                        + "?, ?, ?, ?, ?, "
                                        + "?, ?, ?, ?, ?, "
                                        + "?, ?, ?, ?, ?)";
                    
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setDouble(1, id);
                        ps.setString(2,  obj.get("nrofactura").getAsString());
                        ps.setString(3,  obj.get("fechafactura").getAsString());
                        ps.setString(4,  obj.get("vencimiento").getAsString());
                        ps.setInt(5,  Integer.valueOf(obj.get("tipo").getAsString()));
                        ps.setInt(6,  Integer.valueOf(obj.get("comprobante").getAsString()));
                        ps.setInt(7,  Integer.valueOf(obj.get("rubro").getAsString()));
                        ps.setInt(8,  Integer.valueOf(obj.get("moneda").getAsString()));
                        ps.setInt(9,  Integer.valueOf(obj.get("unidad").getAsString()));
                        ps.setString(10, obj.get("timbrado").getAsString());
                        ps.setString(11, obj.get("vencimientotimbrado").getAsString());
                        ps.setInt(12, Integer.valueOf(obj.get("proveedor").getAsString()));
                        ps.setDouble(13, Double.valueOf(obj.get("exentas").getAsString()));
                        ps.setDouble(14, Double.valueOf(obj.get("gravadas10").getAsString()));
                        ps.setDouble(15, Double.valueOf(obj.get("iva10").getAsString()));
                        ps.setDouble(16, Double.valueOf(obj.get("gravadas5").getAsString()));
                        ps.setDouble(17, Double.valueOf(obj.get("iva5").getAsString()));
                        ps.setDouble(18, Double.valueOf(obj.get("monto").getAsString()));
                        ps.setDouble(19, Double.valueOf(obj.get("concepto").getAsString()));
                        ps.setString(20, obj.get("idcta").getAsString());
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

    public boolean eliminarItemGastos(String cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cabecera_gastos_unidad WHERE numero=?");
        ps.setString(1, cod);
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
    
    public boolean borrardetalle(String dcontrato) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM gastos_unidad WHERE dnumero=?");
        ps.setString(1, dcontrato);
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

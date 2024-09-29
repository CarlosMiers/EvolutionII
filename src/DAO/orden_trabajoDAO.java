/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ficha_empleado;
import Modelo.orden_trabajo;
import Modelo.seccion;
import Modelo.sucursal;
import Modelo.usuario;
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
 * @author Usuario
 */
public class orden_trabajoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<orden_trabajo> MostrarxFechaEmision(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<orden_trabajo> lista = new ArrayList<orden_trabajo>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "select orden_trabajo.numero,orden_trabajo.fechaemision,orden_trabajo.sucursal,orden_trabajo.fechainicio,"
                    + "orden_trabajo.fechaentregaprevista,orden_trabajo.fechaentrega,"
                    + "orden_trabajo.solicitadopor,orden_trabajo.seccion,orden_trabajo.galpon,"
                    + "orden_trabajo.tipo,orden_trabajo.aprobado,orden_trabajo.alerta,"
                    + "orden_trabajo.trabajoarealizar,orden_trabajo.codusuario,"
                    + "orden_trabajo.alertarme,orden_trabajo.estado,orden_trabajo.totalpresupuesto,"
                    + "concat(empleados.nombres,' ',empleados.apellidos) as nombresolicitante,"
                    + "secciones.nombre as nombreseccion,usuarios.last_name as nombreusuario,"
                    + "sucursales.nombre as nombresucursal "
                    + " from orden_trabajo "
                    + "left join empleados "
                    + "ON empleados.codigo=orden_trabajo.solicitadopor "
                    + "LEFT JOIN secciones "
                    + "ON secciones.codigo=orden_trabajo.seccion "
                    + "LEFT JOIN usuarios "
                    + "ON usuarios.employee_id=orden_trabajo.codusuario "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=orden_trabajo.sucursal "
                    + " WHERE orden_trabajo.fechaemision between'" + fechaini + "' AND '" + fechafin + "'"
                    + " ORDER BY orden_trabajo.numero ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    ficha_empleado emp = new ficha_empleado();
                    seccion sec = new seccion();
                    usuario usu = new usuario();
                    orden_trabajo ot = new orden_trabajo();

                    ot.setSucursal(sucursal);
                    ot.setSolicitadopor(emp);
                    ot.setSeccion(sec);
                    ot.setCodusuario(usu);

                    ot.setNumero(rs.getDouble("numero"));
                    ot.setFechaemision(rs.getDate("fechaemision"));
                    ot.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ot.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ot.getSeccion().setCodigo(rs.getInt("seccion"));
                    ot.getSeccion().setNombre(rs.getString("nombreseccion"));
                    ot.getCodusuario().setEmployee_id(rs.getInt("codusuario"));
                    ot.getCodusuario().setLast_name(rs.getString("nombreusuario"));
                    ot.getSolicitadopor().setCodigo(rs.getInt("solicitadopor"));
                    ot.getSolicitadopor().setNombreempleado(rs.getString("nombresolicitante"));
                    ot.getSeccion().setCodigo(rs.getInt("seccion"));
                    ot.getSeccion().setNombre(rs.getString("nombreseccion"));
                    ot.setFechainicio(rs.getDate("fechainicio"));
                    ot.setFechaentregaprevista(rs.getDate("fechaentregaprevista"));
                    ot.setFechaentrega(rs.getDate("fechaentrega"));
                    ot.setGalpon(rs.getString("galpon"));
                    ot.setTipo(rs.getString("tipo"));
                    ot.setAprobado(rs.getString("aprobado"));
                    ot.setAlerta(rs.getInt("alerta"));
                    ot.setTrabajoarealizar(rs.getString("trabajoarealizar"));
                    ot.setAlertarme(rs.getString("alertarme"));
                    ot.setEstado(rs.getString("estado"));
                    ot.setTotalpresupuesto(rs.getDouble("totalpresupuesto"));

                    lista.add(ot);
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

    public ArrayList<orden_trabajo> MostrarxFechaEntrega(Date fecha) throws SQLException {
        ArrayList<orden_trabajo> lista = new ArrayList<orden_trabajo>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "select orden_trabajo.numero,orden_trabajo.fechaemision,orden_trabajo.sucursal,orden_trabajo.fechainicio,"
                    + "orden_trabajo.fechaentregaprevista,orden_trabajo.fechaentrega,"
                    + "orden_trabajo.solicitadopor,orden_trabajo.seccion,orden_trabajo.galpon,"
                    + "orden_trabajo.tipo,orden_trabajo.aprobado,orden_trabajo.alerta,"
                    + "orden_trabajo.trabajoarealizar,orden_trabajo.codusuario,"
                    + "orden_trabajo.alertarme,orden_trabajo.estado,orden_trabajo.totalpresupuesto,"
                    + "concat(empleados.nombres,' ',empleados.apellidos) as nombresolicitante,"
                    + "secciones.nombre as nombreseccion,usuarios.last_name as nombreusuario,"
                    + "sucursales.nombre as nombresucursal "
                    + " from orden_trabajo "
                    + "left join empleados "
                    + "ON empleados.codigo=orden_trabajo.solicitadopor "
                    + "LEFT JOIN secciones "
                    + "ON secciones.codigo=orden_trabajo.seccion "
                    + "LEFT JOIN usuarios "
                    + "ON usuarios.employee_id=orden_trabajo.codusuario "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=orden_trabajo.sucursal "
                    + " WHERE orden_trabajo.fechaentregaprevista='" + fecha + "'"
                    + " ORDER BY orden_trabajo.numero ";

            //System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    ficha_empleado emp = new ficha_empleado();
                    seccion sec = new seccion();
                    usuario usu = new usuario();
                    orden_trabajo ot = new orden_trabajo();

                    ot.setSucursal(sucursal);
                    ot.setSolicitadopor(emp);
                    ot.setSeccion(sec);
                    ot.setCodusuario(usu);

                    ot.setNumero(rs.getDouble("numero"));
                    ot.setFechaemision(rs.getDate("fechaemision"));
                    ot.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ot.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ot.getSeccion().setCodigo(rs.getInt("seccion"));
                    ot.getSeccion().setNombre(rs.getString("nombreseccion"));
                    ot.getCodusuario().setEmployee_id(rs.getInt("codusuario"));
                    ot.getCodusuario().setLast_name(rs.getString("nombreusuario"));
                    ot.getSolicitadopor().setCodigo(rs.getInt("solicitadopor"));
                    ot.getSolicitadopor().setNombreempleado(rs.getString("nombresolicitante"));
                    ot.getSeccion().setCodigo(rs.getInt("seccion"));
                    ot.getSeccion().setNombre(rs.getString("nombreseccion"));
                    ot.setFechainicio(rs.getDate("fechainicio"));
                    ot.setFechaentregaprevista(rs.getDate("fechaentregaprevista"));
                    ot.setFechaentrega(rs.getDate("fechaentrega"));
                    ot.setGalpon(rs.getString("galpon"));
                    ot.setTipo(rs.getString("tipo"));
                    ot.setAprobado(rs.getString("aprobado"));
                    ot.setAlerta(rs.getInt("alerta"));
                    ot.setTrabajoarealizar(rs.getString("trabajoarealizar"));
                    ot.setAlertarme(rs.getString("alertarme"));
                    ot.setEstado(rs.getString("estado"));
                    ot.setTotalpresupuesto(rs.getDouble("totalpresupuesto"));

                    lista.add(ot);
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

    public orden_trabajo buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        orden_trabajo ot = new orden_trabajo();
        try {
            String sql = "select orden_trabajo.numero,orden_trabajo.fechaemision,orden_trabajo.sucursal,"
                    + "orden_trabajo.fechainicio as fechainicio,"
                    + "orden_trabajo.fechaentregaprevista as fechaentregaprevista,"
                    + "orden_trabajo.fechaentrega AS fechaentrega,"
                    + "orden_trabajo.solicitadopor,orden_trabajo.seccion,orden_trabajo.galpon,"
                    + "orden_trabajo.tipo,orden_trabajo.aprobado,orden_trabajo.alerta,"
                    + "orden_trabajo.trabajoarealizar,orden_trabajo.codusuario,"
                    + "orden_trabajo.alertarme,orden_trabajo.estado,orden_trabajo.totalpresupuesto,"
                    + "concat(empleados.nombres,' ',empleados.apellidos) as nombresolicitante,"
                    + "secciones.nombre as nombreseccion,usuarios.last_name as nombreusuario,"
                    + "sucursales.nombre as nombresucursal "
                    + " from orden_trabajo "
                    + "left join empleados "
                    + "ON empleados.codigo=orden_trabajo.solicitadopor "
                    + "LEFT JOIN secciones "
                    + "ON secciones.codigo=orden_trabajo.seccion "
                    + "LEFT JOIN usuarios "
                    + "ON usuarios.employee_id=orden_trabajo.codusuario "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=orden_trabajo.sucursal "
                    + " WHERE orden_trabajo.numero= ? "
                    + " ORDER BY orden_trabajo.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    ficha_empleado emp = new ficha_empleado();
                    seccion sec = new seccion();
                    usuario usu = new usuario();

                    ot.setSucursal(sucursal);
                    ot.setSolicitadopor(emp);
                    ot.setSeccion(sec);
                    ot.setCodusuario(usu);

                    ot.setNumero(rs.getDouble("numero"));
                    ot.setFechaemision(rs.getDate("fechaemision"));
                    ot.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ot.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ot.getSeccion().setCodigo(rs.getInt("seccion"));
                    ot.getSeccion().setNombre(rs.getString("nombreseccion"));
                    ot.getCodusuario().setEmployee_id(rs.getInt("codusuario"));
                    ot.getCodusuario().setLast_name(rs.getString("nombreusuario"));
                    ot.getSolicitadopor().setCodigo(rs.getInt("solicitadopor"));
                    ot.getSolicitadopor().setNombreempleado(rs.getString("nombresolicitante"));
                    ot.getSeccion().setCodigo(rs.getInt("seccion"));
                    ot.getSeccion().setNombre(rs.getString("nombreseccion"));
                    ot.setFechainicio(rs.getDate("fechainicio"));
                    ot.setFechaentregaprevista(rs.getDate("fechaentregaprevista"));
                    ot.setFechaentrega(rs.getDate("fechaentrega"));
                    ot.setGalpon(rs.getString("galpon"));
                    ot.setTipo(rs.getString("tipo"));
                    ot.setAprobado(rs.getString("aprobado"));
                    ot.setAlerta(rs.getInt("alerta"));
                    ot.setTrabajoarealizar(rs.getString("trabajoarealizar"));
                    ot.setAlertarme(rs.getString("alertarme"));
                    ot.setEstado(rs.getString("estado"));
                    ot.setTotalpresupuesto(rs.getDouble("totalpresupuesto"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return ot;
    }

    public orden_trabajo insertarOT(orden_trabajo ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO orden_trabajo (sucursal,fechaemision,solicitadopor,"
                + "seccion,tipo,galpon,trabajoarealizar,codusuario)"
                + " VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, ocr.getSucursal().getCodigo());
        ps.setDate(2, ocr.getFechaemision());
        ps.setInt(3, ocr.getSolicitadopor().getCodigo());
        ps.setInt(4, ocr.getSeccion().getCodigo());
        ps.setString(5, ocr.getTipo());
        ps.setString(6, ocr.getGalpon());
        ps.setString(7, ocr.getTrabajoarealizar());
        ps.setInt(8, ocr.getCodusuario().getEmployee_id());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public orden_trabajo actualizarOT(orden_trabajo ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  orden_trabajo SET sucursal=?,fechaemision=?,solicitadopor=?,"
                + "seccion=?,tipo=?,galpon=?,trabajoarealizar=?,codusuario=? WHERE numero= " + ocr.getNumero());
        ps.setInt(1, ocr.getSucursal().getCodigo());
        ps.setDate(2, ocr.getFechaemision());
        ps.setInt(3, ocr.getSolicitadopor().getCodigo());
        ps.setInt(4, ocr.getSeccion().getCodigo());
        ps.setString(5, ocr.getTipo());
        ps.setString(6, ocr.getGalpon());
        ps.setString(7, ocr.getTrabajoarealizar());
        ps.setInt(8, ocr.getCodusuario().getEmployee_id());
        ps.executeUpdate();

        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

    public orden_trabajo ActualizarOTMantenimiento(orden_trabajo ot, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  orden_trabajo SET totalpresupuesto=?  WHERE numero= " + ot.getNumero());
        ps.setDouble(1, ot.getTotalpresupuesto());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemMercaderias(ot.getNumero(), detalle, con);
        }
        st.close();
        ps.close();
        return ot;
    }

    public boolean guardarItemMercaderias(Double id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement psdetalle = null;

        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_orden_trabajo WHERE dnumero=?");
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
                    String sql = "INSERT INTO detalle_orden_trabajo("
                            + "dnumero,"
                            + "item,"
                            + "codprod,"
                            + "cantidad,"
                            + "costo,"
                            + "disponible,"
                            + "total,"
                            + "potrero"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setDouble(1, id);
                        ps.setString(2, obj.get("item").getAsString());
                        ps.setString(3, obj.get("codprod").getAsString());
                        ps.setString(4, obj.get("cantidad").getAsString());
                        ps.setString(5, obj.get("costo").getAsString());
                        ps.setString(6, obj.get("disponible").getAsString());
                        ps.setString(7, obj.get("total").getAsString());
                        ps.setString(8, obj.get("potrero").getAsString());
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

    public orden_trabajo ActualizarOTTerceros(orden_trabajo ot, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  orden_trabajo SET totalpresupuesto=?  WHERE numero= " + ot.getNumero());
        ps.setDouble(1, ot.getTotalpresupuesto());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarItemPropuestaTerceros(ot.getNumero(), detalle, con);
        }
        st.close();
        ps.close();
        return ot;
    }

    public orden_trabajo ActualizarOTAprobado(orden_trabajo ot) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  orden_trabajo SET aprobado=?,estado='PRESUPUESTO APROBADO'  WHERE numero= " + ot.getNumero());
        ps.setString(1, ot.getAprobado());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ot;
    }

    public orden_trabajo ActualizarOTInicio(orden_trabajo ot) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  orden_trabajo SET fechainicio=?,estado=?  WHERE numero= " + ot.getNumero());
        ps.setDate(1, ot.getFechainicio());
        ps.setString(2, ot.getEstado());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ot;
    }

    public orden_trabajo ActualizarOTFinal(orden_trabajo ot) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  orden_trabajo SET fechaentrega=?,estado=?  WHERE numero= " + ot.getNumero());
        ps.setDate(1, ot.getFechaentrega());
        ps.setString(2, ot.getEstado());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ot;
    }

    public orden_trabajo ActualizarFechaPrevista(orden_trabajo ot) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  orden_trabajo SET fechaentregaprevista=?  WHERE numero= " + ot.getNumero());
        ps.setDate(1, ot.getFechaentregaprevista());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ot;
    }

    public boolean guardarItemPropuestaTerceros(Double id, String detalle, Conexion conexion) throws SQLException {
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
                    String sql = "INSERT INTO detalle_propuesta_terceros("
                            + "dnumero,"
                            + "item,"
                            + "proveedor,"
                            + "presupuestado,"
                            + "aprobado"
                            + ") "
                            + "values(?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setDouble(1, id);
                        ps.setString(2, obj.get("item").getAsString());
                        ps.setString(3, obj.get("proveedor").getAsString());
                        ps.setString(4, obj.get("presupuestado").getAsString());
                        ps.setString(5, obj.get("aprobado").getAsString());
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

    public orden_trabajo ActualizarOTTareas(orden_trabajo ot, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        guardado = guardarItemTarea(ot.getNumero(), detalle, con);
        st.close();
        return ot;
    }

    public boolean guardarItemTarea(Double id, String detalle, Conexion conexion) throws SQLException {
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
                    String sql = "INSERT INTO detalle_tarea_orden_trabajo("
                            + "dnumero,"
                            + "item,"
                            + "descripcion,"
                            + "estado"
                            + ") "
                            + "values(?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setDouble(1, id);
                        ps.setString(2, obj.get("item").getAsString());
                        ps.setString(3, obj.get("descripcion").getAsString());
                        ps.setString(4, obj.get("estado").getAsString());
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

    public ArrayList<orden_trabajo> VenceFechaEntrega(Date fecha) throws SQLException {
        ArrayList<orden_trabajo> lista = new ArrayList<orden_trabajo>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "select orden_trabajo.numero,orden_trabajo.fechaemision,orden_trabajo.sucursal,orden_trabajo.fechainicio,"
                    + "orden_trabajo.fechaentregaprevista,orden_trabajo.fechaentrega,"
                    + "orden_trabajo.solicitadopor,orden_trabajo.seccion,orden_trabajo.galpon,"
                    + "orden_trabajo.tipo,orden_trabajo.aprobado,orden_trabajo.alerta,"
                    + "orden_trabajo.trabajoarealizar,orden_trabajo.codusuario,"
                    + "orden_trabajo.alertarme,orden_trabajo.estado,orden_trabajo.totalpresupuesto,"
                    + "concat(empleados.nombres,' ',empleados.apellidos) as nombresolicitante,"
                    + "secciones.nombre as nombreseccion,usuarios.last_name as nombreusuario,"
                    + "sucursales.nombre as nombresucursal,"
                    + "DATEDIFF(" + "'" + fecha + "'" + ",orden_trabajo.fechaentregaprevista) AS vence "
                    + " from orden_trabajo "
                    + " left join empleados "
                    + " ON empleados.codigo=orden_trabajo.solicitadopor "
                    + " LEFT JOIN secciones "
                    + " ON secciones.codigo=orden_trabajo.seccion "
                    + " LEFT JOIN usuarios "
                    + " ON usuarios.employee_id=orden_trabajo.codusuario "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=orden_trabajo.sucursal "
                    + "WHERE orden_trabajo.estado<>'TAREA FINALIZADA'"
                    + " HAVING(vence<=alerta AND vence>0) "
                    + " ORDER BY orden_trabajo.fechaentregaprevista ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    ficha_empleado emp = new ficha_empleado();
                    seccion sec = new seccion();
                    usuario usu = new usuario();
                    orden_trabajo ot = new orden_trabajo();

                    ot.setSucursal(sucursal);
                    ot.setSolicitadopor(emp);
                    ot.setSeccion(sec);
                    ot.setCodusuario(usu);

                    ot.setNumero(rs.getDouble("numero"));
                    ot.setFechaemision(rs.getDate("fechaemision"));
                    ot.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ot.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ot.getSeccion().setCodigo(rs.getInt("seccion"));
                    ot.getSeccion().setNombre(rs.getString("nombreseccion"));
                    ot.getCodusuario().setEmployee_id(rs.getInt("codusuario"));
                    ot.getCodusuario().setLast_name(rs.getString("nombreusuario"));
                    ot.getSolicitadopor().setCodigo(rs.getInt("solicitadopor"));
                    ot.getSolicitadopor().setNombreempleado(rs.getString("nombresolicitante"));
                    ot.getSeccion().setCodigo(rs.getInt("seccion"));
                    ot.getSeccion().setNombre(rs.getString("nombreseccion"));
                    ot.setFechainicio(rs.getDate("fechainicio"));
                    ot.setFechaentregaprevista(rs.getDate("fechaentregaprevista"));
                    ot.setFechaentrega(rs.getDate("fechaentrega"));
                    ot.setGalpon(rs.getString("galpon"));
                    ot.setTipo(rs.getString("tipo"));
                    ot.setAprobado(rs.getString("aprobado"));
                    ot.setAlerta(rs.getInt("alerta"));
                    ot.setTrabajoarealizar(rs.getString("trabajoarealizar"));
                    ot.setAlertarme(rs.getString("alertarme"));
                    ot.setEstado(rs.getString("estado"));
                    ot.setTotalpresupuesto(rs.getDouble("totalpresupuesto"));

                    lista.add(ot);
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

    public ArrayList<orden_trabajo> VerificarFuncionario(int id, Date fecha) throws SQLException {
        ArrayList<orden_trabajo> lista = new ArrayList<orden_trabajo>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "select numero,fechaentregaprevista,empleado "
                    + "from orden_trabajo "
                    + "left join detalle_funcionarios_ot "
                    + "ON detalle_funcionarios_ot.dnumero=orden_trabajo.numero "
                    + "WHERE empleado="+id+" and fechaentregaprevista>='"+fecha+"'";


            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ficha_empleado emp = new ficha_empleado();
                    orden_trabajo ot = new orden_trabajo();
                    ot.setNumero(rs.getDouble("numero"));
                    ot.setFechaentregaprevista(rs.getDate("fechaentregaprevista"));
                    lista.add(ot);
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

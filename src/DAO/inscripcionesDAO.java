/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Clases.Config;
import Modelo.pensum;
import Modelo.cliente;
import Modelo.carrera;
import Modelo.inscripcion;
import Modelo.periodo_lectivo;
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
 * @author Usuario
 */
public class inscripcionesDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<inscripcion> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<inscripcion> lista = new ArrayList<inscripcion>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idreferencia,contrato,inscripciones.periodo,fecha,sede,codalumno,inscripciones.carrera,curso,"
                    + "turno,codmatricula,codcuota,importecuota,importematricula,inscripciones.semestre,"
                    + "primeracuota,periodo_lectivo.nombre AS nombreperiodo,"
                    + "clientes.nombre AS nombrealumno,sucursales.nombre AS nombresede,"
                    + "(SELECT nombre FROM productos WHERE codmatricula=productos.codigo) nombrematricula, "
                    + "(SELECT nombre FROM productos WHERE codcuota=productos.codigo)  nombrecuota,"
                    + "carreras.nombre as nombrecarrera,inscripciones.pensum,pensum.nombre as nombrepensum "
                    + "FROM inscripciones "
                    + "LEFT JOIN periodo_lectivo "
                    + "ON periodo_lectivo.codigo=inscripciones.periodo "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=inscripciones.codalumno "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=inscripciones.sede "
                    + "LEFT JOIN carreras "
                    + "ON carreras.codigo=inscripciones.carrera "
                    + " LEFT JOIN pensum "
                    + " ON pensum.iditem=inscripciones.pensum "
                    + "WHERE inscripciones.fecha between ? AND ? "
                    + "ORDER BY inscripciones.contrato";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    periodo_lectivo pe = new periodo_lectivo();
                    sucursal suc = new sucursal();
                    cliente cl = new cliente();
                    carrera ca = new carrera();
                    pensum pen = new pensum();
                    inscripcion inc = new inscripcion();

                    inc.setPeriodo(pe);
                    inc.setPensum(pen);
                    inc.setSede(suc);
                    inc.setCodalumno(cl);
                    inc.setCarrera(ca);

                    inc.setIdreferencia(rs.getString("idreferencia"));
                    inc.setContrato(rs.getDouble("contrato"));
                    inc.setFecha(rs.getDate("fecha"));
                    inc.getSede().setCodigo(rs.getInt("sede"));
                    inc.getSede().setNombre(rs.getString("nombresede"));
                    inc.getPeriodo().setCodigo(rs.getInt("periodo"));
                    inc.getPeriodo().setNombre(rs.getString("nombreperiodo"));
                    inc.getCodalumno().setCodigo(rs.getInt("codalumno"));
                    inc.getCodalumno().setNombre(rs.getString("nombrealumno"));
                    inc.getCarrera().setCodigo(rs.getInt("carrera"));
                    inc.getCarrera().setNombre(rs.getString("nombrecarrera"));
                    inc.getPensum().setIditem(rs.getInt("pensum"));
                    inc.getPensum().setNombre(rs.getString("nombrepensum"));
                    inc.setNombrecuota(rs.getString("nombrecuota"));
                    inc.setNombrematricula(rs.getString("nombrematricula"));
                    inc.setImportematricula(rs.getDouble("importematricula"));
                    inc.setImportecuota(rs.getDouble("importecuota"));
                    inc.setPrimeracuota(rs.getDate("primeracuota"));
                    inc.setCodcuota(rs.getString("codcuota"));
                    inc.setCodmatricula(rs.getString("codmatricula"));
                    inc.setCurso(rs.getInt("curso"));
                    inc.setTurno(rs.getString("turno"));
                    inc.setSemestre(rs.getInt("semestre"));
                    lista.add(inc);
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->CONSULTA DESDE INSCRIPCIONES " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public inscripcion buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        periodo_lectivo pe = new periodo_lectivo();
        sucursal suc = new sucursal();
        cliente cl = new cliente();
        carrera ca = new carrera();
        pensum pen = new pensum();
        inscripcion inc = new inscripcion();

        try {

            String sql = "SELECT idreferencia,contrato,inscripciones.periodo,fecha,sede,codalumno,inscripciones.carrera,curso,"
                    + "turno,codmatricula,codcuota,importecuota,importematricula,inscripciones.semestre,"
                    + "primeracuota,periodo_lectivo.nombre AS nombreperiodo,"
                    + "clientes.nombre AS nombrealumno,sucursales.nombre AS nombresede,"
                    + "(SELECT nombre FROM productos WHERE codmatricula=productos.codigo) nombrematricula, "
                    + "(SELECT nombre FROM productos WHERE codcuota=productos.codigo)  nombrecuota,"
                    + "carreras.nombre as nombrecarrera,inscripciones.pensum,pensum.nombre as nombrepensum, inscripciones.calendario "
                    + "FROM inscripciones "
                    + "LEFT JOIN periodo_lectivo "
                    + "ON periodo_lectivo.codigo=inscripciones.periodo "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=inscripciones.codalumno "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=inscripciones.sede "
                    + "LEFT JOIN carreras "
                    + "ON carreras.codigo=inscripciones.carrera "
                    + " LEFT JOIN pensum "
                    + " ON pensum.iditem=inscripciones.pensum "
                    + "WHERE inscripciones.contrato= ? "
                    + "ORDER BY inscripciones.contrato";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    inc.setPeriodo(pe);
                    inc.setPensum(pen);
                    inc.setSede(suc);
                    inc.setCodalumno(cl);
                    inc.setCarrera(ca);

                    inc.setIdreferencia(rs.getString("idreferencia"));
                    inc.setContrato(rs.getDouble("contrato"));
                    inc.setFecha(rs.getDate("fecha"));
                    inc.getSede().setCodigo(rs.getInt("sede"));
                    inc.getSede().setNombre(rs.getString("nombresede"));
                    inc.getPeriodo().setCodigo(rs.getInt("periodo"));
                    inc.getPeriodo().setNombre(rs.getString("nombreperiodo"));
                    inc.getCodalumno().setCodigo(rs.getInt("codalumno"));
                    inc.getCodalumno().setNombre(rs.getString("nombrealumno"));
                    inc.getCarrera().setCodigo(rs.getInt("carrera"));
                    inc.getCarrera().setNombre(rs.getString("nombrecarrera"));
                    inc.getPensum().setIditem(rs.getInt("pensum"));
                    inc.getPensum().setNombre(rs.getString("nombrepensum"));
                    inc.setNombrecuota(rs.getString("nombrecuota"));
                    inc.setNombrematricula(rs.getString("nombrematricula"));
                    inc.setImportematricula(rs.getDouble("importematricula"));
                    inc.setImportecuota(rs.getDouble("importecuota"));
                    inc.setPrimeracuota(rs.getDate("primeracuota"));
                    inc.setCodcuota(rs.getString("codcuota"));
                    inc.setCodmatricula(rs.getString("codmatricula"));
                    inc.setCurso(rs.getInt("curso"));
                    inc.setTurno(rs.getString("turno"));
                    inc.setSemestre(rs.getInt("semestre"));
                    inc.setCalendario(rs.getInt("calendario"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return inc;
    }

    public inscripcion insertarInscripcion(String token, inscripcion ocr, String cCuenta) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO inscripciones (idreferencia,periodo,fecha,"
                + "sede,codalumno,carrera,curso,turno,"
                + "codmatricula,codcuota,importecuota,"
                + "importematricula,semestre,primeracuota,"
                + "pensum,calendario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdreferencia());
        ps.setInt(2, ocr.getPeriodo().getCodigo());
        ps.setDate(3, ocr.getFecha());
        ps.setInt(4, ocr.getSede().getCodigo());
        ps.setInt(5, ocr.getCodalumno().getCodigo());
        ps.setInt(6, ocr.getCarrera().getCodigo());
        ps.setInt(7, ocr.getCurso());
        ps.setString(8, ocr.getTurno());
        ps.setString(9, ocr.getCodmatricula());
        ps.setString(10, ocr.getCodcuota());
        ps.setDouble(11, ocr.getImportecuota());
        ps.setDouble(12, ocr.getImportematricula());
        ps.setInt(13, ocr.getSemestre());
        ps.setDate(14, ocr.getPrimeracuota());
        ps.setInt(15, ocr.getPensum().getIditem());
        ps.setInt(16, ocr.getCalendario());

        if (Config.cToken == token) {
            ps.executeUpdate();
            ResultSet keyset = ps.getGeneratedKeys();
            if (keyset.next()) {
                id = keyset.getInt(1);
                guardado = guardarDetalle(id, cCuenta, con);
            }
        } else {
            System.out.println("USUARIO NO AUTORIZADO");
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;

    }

    public boolean borrarInscripcion(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM inscripciones WHERE contrato=?");
        ps.setDouble(1, id);
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

    public boolean borrarDetalleInscripcion(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuenta_clientes WHERE creferencia=?");
        ps.setString(1, referencia);
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

    public inscripcion ActualizarInscripcion(String token, inscripcion ocr, String cCuenta) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        double id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  inscripciones"
                + " SET idreferencia=?,periodo=?,fecha=?,"
                + "sede=?,codalumno=?,carrera=?,curso=?,turno=?,"
                + "codmatricula=?,codcuota=?,importecuota=?,"
                + "importematricula=?,semestre=?,primeracuota=?,"
                + "pensum=?,calendario=? WHERE contrato= " + ocr.getContrato());
        ps.setString(1, ocr.getIdreferencia());
        ps.setInt(2, ocr.getPeriodo().getCodigo());
        ps.setDate(3, ocr.getFecha());
        ps.setInt(4, ocr.getSede().getCodigo());
        ps.setInt(5, ocr.getCodalumno().getCodigo());
        ps.setInt(6, ocr.getCarrera().getCodigo());
        ps.setInt(7, ocr.getCurso());
        ps.setString(8, ocr.getTurno());
        ps.setString(9, ocr.getCodmatricula());
        ps.setString(10, ocr.getCodcuota());
        ps.setDouble(11, ocr.getImportecuota());
        ps.setDouble(12, ocr.getImportematricula());
        ps.setInt(13, ocr.getSemestre());
        ps.setDate(14, ocr.getPrimeracuota());
        ps.setInt(15, ocr.getPensum().getIditem());
        ps.setInt(16, ocr.getCalendario());
        id = ocr.getContrato();
        if (Config.cToken == token) {
            ps.executeUpdate();
            int rowsUpdated = ps.executeUpdate();
            if (ocr.getImportecuota() > 0 || ocr.getImportematricula() > 0) {
                guardado = guardarDetalle(id, cCuenta, con);
            }

        } else {
            System.out.println("USUARIO AUTORIZADO");
        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
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
                    String sql = "INSERT INTO cuenta_clientes("
                            + "iddocumento,"
                            + "creferencia,"
                            + "documento,"
                            + "fecha,"
                            + "vencimiento,"
                            + "cliente,"
                            + "sucursal,"
                            + "moneda,"
                            + "importe,"
                            + "comprobante,"
                            + "idcarrera,"
                            + "numerocuota,"
                            + "cuota,"
                            + "saldo,"
                            + "periodo,"
                            + "producto,"
                            + "semestre"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddocumento").getAsString());
                        ps.setString(2, obj.get("creferencia").getAsString());
                        ps.setDouble(3, id);
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("vencimiento").getAsString());
                        ps.setInt(6, obj.get("cliente").getAsInt());
                        ps.setInt(7, obj.get("sucursal").getAsInt());
                        ps.setInt(8, obj.get("moneda").getAsInt());
                        ps.setString(9, obj.get("importe").getAsString());
                        ps.setInt(10, obj.get("comprobante").getAsInt());
                        ps.setInt(11, obj.get("idcarrera").getAsInt());
                        ps.setInt(12, obj.get("numerocuota").getAsInt());
                        ps.setInt(13, obj.get("cuota").getAsInt());
                        ps.setString(14, obj.get("saldo").getAsString());
                        ps.setInt(15, obj.get("periodo").getAsInt());
                        ps.setString(16, obj.get("producto").getAsString());
                        ps.setInt(17, obj.get("semestre").getAsInt());
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

}

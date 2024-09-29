/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.barrio;
import Modelo.inmueble;
import Modelo.localidad;
import Modelo.propietario;
import Modelo.tipo_inmueble;
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

/**
 *
 * @author SERVIDOR
 */
/**
 *
 * @author Usuario
 */
public class inmuebleDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<inmueble> todos() throws SQLException {
        ArrayList<inmueble> lista = new ArrayList<inmueble>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT inmuebles.idinmueble,inmuebles.tipinmueble,inmuebles.coddis,"
                    + "inmuebles.nomedif,inmuebles.diredif,inmuebles.finca,inmuebles.codbar,"
                    + "inmuebles.ctactral,inmuebles.usuario,inmuebles.fecalta,inmuebles.observacion,inmuebles.reglainter,"
                    + "tipo_inmueble.nombre AS nombretipo,barrios.nombre AS nombrebarrio,inmuebles.fecmodi, "
                    + "inmuebles.codpro,barrios.nombre AS nombrebarrio,inmuebles.fecmodi,inmuebles.codloca, localidades.nombre AS nombrelocalidad,"
                    + " CONCAT(RTRIM(propietarios.nombre),',',RTRIM(propietarios.apellido)) as nombrepropietario,"
                    + "inmuebles.ivaexpensa "
                    + "FROM inmuebles "
                    + "LEFT JOIN propietarios "
                    + "ON propietarios.codpro=inmuebles.codpro "
                    + "LEFT JOIN tipo_inmueble "
                    + "ON tipo_inmueble.codigo=inmuebles.tipinmueble "
                    + "LEFT JOIN barrios "
                    + "ON barrios.codigo=inmuebles.coddis "
                    + "LEFT JOIN localidades "
                    + "ON localidades.codigo=inmuebles.codloca "
                    + " ORDER BY inmuebles.idinmueble ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    barrio barrio = new barrio();
                    tipo_inmueble tipo = new tipo_inmueble();
                    inmueble inm = new inmueble();
                    localidad loc = new localidad();
                    propietario p = new propietario();
                    inm.setCodpro(p);
                    inm.setTipinmueble(tipo);
                    inm.setCoddis(barrio);
                    inm.setCodloca(loc);
                    inm.setNomedif(rs.getString("nomedif"));
                    inm.setDiredif(rs.getString("diredif"));
                    inm.setFinca(rs.getString("finca"));
                    inm.setIdinmueble(rs.getInt("idinmueble"));
                    inm.getTipinmueble().setCodigo(rs.getInt("tipinmueble"));
                    inm.getTipinmueble().setNombre(rs.getString("nombretipo"));
                    inm.getCodloca().setCodigo(rs.getInt("codloca"));
                    inm.getCodloca().setNombre(rs.getString("nombretipo"));
                    inm.getCoddis().setCodigo(rs.getInt("coddis"));
                    inm.getCoddis().setNombre(rs.getString("nombrebarrio"));
                    inm.getCodloca().setCodigo(rs.getInt("codloca"));
                    inm.getCodloca().setNombre(rs.getString("nombrelocalidad"));
                    inm.getCodpro().setCodpro(rs.getInt("codpro"));
                    inm.getCodpro().setNombre(rs.getString("nombrepropietario"));
                    inm.setCtactral(rs.getString("ctactral"));
                    inm.setFecalta(rs.getDate("fecalta"));
                    inm.setFecmodi(rs.getDate("fecmodi"));
                    inm.setObservacion(rs.getString("observacion"));
                    inm.setReglainter(rs.getString("reglainter"));
                    inm.setIvaexpensa(rs.getDouble("ivaexpensa"));
                    lista.add(inm);
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

    public inmueble buscarinmueblesId(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        inmueble inm = new inmueble();

        try {
            String sql = "SELECT inmuebles.idinmueble,inmuebles.tipinmueble,inmuebles.coddis,"
                    + "inmuebles.codpro,inmuebles.nomedif,inmuebles.diredif,inmuebles.finca,inmuebles.codbar,"
                    + "inmuebles.ctactral,inmuebles.usuario,inmuebles.fecalta,inmuebles.observacion,inmuebles.reglainter,"
                    + "tipo_inmueble.nombre AS nombretipo,barrios.nombre AS nombrebarrio,inmuebles.fecmodi,inmuebles.codloca, localidades.nombre AS nombrelocalidad,"
                    + " CONCAT(RTRIM(propietarios.nombre),',',RTRIM(propietarios.apellido)) as nombrepropietario,"
                    + "inmuebles.ivaexpensa "
                    + "FROM inmuebles "
                    + "LEFT JOIN propietarios "
                    + "ON propietarios.codpro=inmuebles.codpro "
                    + "LEFT JOIN tipo_inmueble "
                    + "ON tipo_inmueble.codigo=inmuebles.tipinmueble "
                    + "LEFT JOIN barrios "
                    + "ON barrios.codigo=inmuebles.coddis "
                    + "LEFT JOIN localidades "
                    + "ON localidades.codigo=inmuebles.codloca "
                    + " WHERE inmuebles.idinmueble = ? ORDER BY inmuebles.idinmueble";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    barrio barrio = new barrio();
                    tipo_inmueble tipo = new tipo_inmueble();
                    localidad loc = new localidad();
                    propietario p = new propietario();
                    inm.setCodpro(p);
                    inm.setTipinmueble(tipo);
                    inm.setCoddis(barrio);
                    inm.setCodloca(loc);
                    inm.setNomedif(rs.getString("nomedif"));
                    inm.setDiredif(rs.getString("diredif"));
                    inm.setFinca(rs.getString("finca"));
                    inm.setIdinmueble(rs.getInt("idinmueble"));
                    inm.getTipinmueble().setCodigo(rs.getInt("tipinmueble"));
                    inm.getTipinmueble().setNombre(rs.getString("nombretipo"));
                    inm.getCodloca().setCodigo(rs.getInt("codloca"));
                    inm.getCodloca().setNombre(rs.getString("nombretipo"));
                    inm.getCoddis().setCodigo(rs.getInt("coddis"));
                    inm.getCoddis().setNombre(rs.getString("nombrebarrio"));
                    inm.getCodloca().setCodigo(rs.getInt("codloca"));
                    inm.getCodloca().setNombre(rs.getString("nombrelocalidad"));
                    inm.getCodpro().setCodpro(rs.getInt("codpro"));
                    inm.getCodpro().setNombre(rs.getString("nombrepropietario"));
                    inm.setCtactral(rs.getString("ctactral"));
                    inm.setFecalta(rs.getDate("fecalta"));
                    inm.setFecmodi(rs.getDate("fecmodi"));
                    inm.setIvaexpensa(rs.getDouble("ivaexpensa"));
                    inm.setObservacion(rs.getString("observacion"));
                    inm.setReglainter(rs.getString("reglainter"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return inm;
    }

    public inmueble insertarInmuebles(inmueble ocr, String propietario) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement inm = null;

        inm = st.getConnection().prepareStatement("INSERT INTO inmuebles (tipinmueble,coddis,nomedif,diredif,finca,ctactral,codpro,codloca,observacion,reglainter,ivaexpensa) VALUES (?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

        inm.setInt(1, ocr.getTipinmueble().getCodigo());
        inm.setInt(2, ocr.getCoddis().getCodigo());
        inm.setString(3, ocr.getNomedif());
        inm.setString(4, ocr.getDiredif());
        inm.setString(5, ocr.getFinca());
        inm.setString(6, ocr.getCtactral());
        inm.setInt(7, ocr.getCodpro().getCodpro());
        inm.setInt(8, ocr.getCodloca().getCodigo());
        inm.setString(9, ocr.getObservacion());
        inm.setString(10, ocr.getReglainter());
        inm.setDouble(11, ocr.getIvaexpensa());
        int rowsUpdated = inm.executeUpdate();
        ResultSet keyset = inm.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
             guardado = guardarPropietario(id, propietario, con);
        }
        ocr.setIdinmueble(id);
        st.close();
        inm.close();
        return ocr;
    }

    public inmueble ActualizarInmueble(inmueble ocr, String propietario) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        int id = 0;
        boolean guardado = false;
        PreparedStatement inm = null;

        inm = st.getConnection().prepareStatement("UPDATE inmuebles SET tipinmueble=?,coddis=?,nomedif=?,diredif=?,finca=?,ctactral,codpro=?,codloca=?,observacion=?,reglainter=?,ivaexpensa=? WHERE idinmueble=" + ocr.getIdinmueble());
        inm.setInt(1, ocr.getTipinmueble().getCodigo());
        inm.setInt(2, ocr.getCoddis().getCodigo());
        inm.setString(3, ocr.getNomedif());
        inm.setString(4, ocr.getDiredif());
        inm.setString(5, ocr.getFinca());
        inm.setString(6, ocr.getCtactral());
        inm.setInt(7, ocr.getCodpro().getCodpro());
        inm.setInt(8, ocr.getCodloca().getCodigo());
        inm.setString(9, ocr.getObservacion());
        inm.setString(10, ocr.getReglainter());
        inm.setDouble(11, ocr.getIvaexpensa());
        inm.executeUpdate();
        int rowsUpdated = inm.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarPropietario(ocr.getIdinmueble(), propietario, con);
        }
        st.close();
        inm.close();
        cnn.close();
        return ocr;
    }

    public boolean eliminarinmueble(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM inmuebles WHERE idinmueble=?");
        ps.setInt(1, cod);
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

    public boolean guardarPropietario(int id, String propietario, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(propietario);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO inmueble_propietarios("
                            + "codinmueble,"
                            + "idpropietario"
                            + ") "
                            + "values(?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("idpropietario").getAsString());
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

    public boolean borrarAjustes(String idinmueble) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM inmuebles WHERE idinmueble=?");
        ps.setString(1, idinmueble);
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

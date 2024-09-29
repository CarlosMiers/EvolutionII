/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.edificio;
import Modelo.solicitud_locacion;
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
public class solicitud_locacionDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<solicitud_locacion> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<solicitud_locacion> lista = new ArrayList<solicitud_locacion>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT solicitud_locacion.idsolicitud,solicitud_locacion.fechasolicitud,solicitud_locacion.idcliente,";
            cSql = cSql + "solicitud_locacion.idunidad,solicitud_locacion.idconyugue,solicitud_locacion.nrohabitantes,";
            cSql = cSql + "solicitud_locacion.estado,solicitud_locacion.usuarioalta,";
            cSql = cSql + "solicitud_locacion.usuarioproceso,solicitud_locacion.fechaproceso,solicitud_locacion.observaciones,";
            cSql = cSql + "clientes.fechanacimiento AS fechanacimiento, clientes.nombre AS nombrelocatario, clientes.direccion AS direccionlocatario,";
            cSql = cSql + "clientes.ruc AS ruclocatario,clientes.celular AS telefonofijo, clientes.telefono AS telefonomovil,";
            cSql = cSql + "edificios.ctactral AS nombrectactral, edificios.nir AS nir, edificios.nis AS nis, edificios.telunid AS telefonoedificio,";
            cSql = cSql + " edificios.medcorpo AS corposana, edificios.medande AS ande,edificios.depgtia AS montogarantia, edificios.alquiler AS alquiler,";
            cSql = cSql + "(SELECT clientes.codigo ";
            cSql = cSql + "FROM clientes ";
            cSql = cSql + "WHERE clientes.codigo=solicitud_locacion.idconyugue) idconyugue, ";
            cSql = cSql + "(SELECT clientes.fechanacimiento ";
            cSql = cSql + "FROM clientes ";
            cSql = cSql + "WHERE clientes.codigo=solicitud_locacion.idconyugue) nacimientoconyugue, ";
            cSql = cSql + "(SELECT clientes.nombre ";
            cSql = cSql + "FROM clientes ";
            cSql = cSql + "WHERE clientes.codigo=solicitud_locacion.idconyugue) nombreconyugue, ";
            cSql = cSql + "(SELECT clientes.direccion ";
            cSql = cSql + "FROM clientes ";
            cSql = cSql + "WHERE clientes.codigo=solicitud_locacion.idconyugue) direccionparticularconyugue, ";
            cSql = cSql + "(SELECT clientes.ruc ";
            cSql = cSql + "FROM clientes ";
            cSql = cSql + "WHERE clientes.codigo=solicitud_locacion.idconyugue) nrodocumentoconyugue, ";
            cSql = cSql + "(SELECT clientes.celular ";
            cSql = cSql + "FROM clientes ";
            cSql = cSql + "WHERE clientes.codigo=solicitud_locacion.idconyugue) telefonomovilconyugue, ";
            cSql = cSql + "(SELECT clientes.telefono ";
            cSql = cSql + "FROM clientes ";
            cSql = cSql + "WHERE clientes.codigo=solicitud_locacion.idconyugue) telefonoconyugue, ";
            cSql = cSql + "(SELECT inmuebles.nomedif ";
            cSql = cSql + "FROM inmuebles  ";
            cSql = cSql + "WHERE inmuebles.idinmueble=solicitud_locacion.idunidad) nomedif, ";
            cSql = cSql + " CASE clientes.estadocivil ";
            cSql = cSql + " WHEN \"C\" THEN \"CASADO/A\" ";
            cSql = cSql + " WHEN \"S\" THEN \"SOLTERO/A\" ";
            cSql = cSql + " WHEN \"SE\" THEN \"SEPARADO/A\" ";
            cSql = cSql + " WHEN \"V\" THEN \"VIUDO/A\" ";
            cSql = cSql + " END AS estadocivillocatario ";
            cSql = cSql + " FROM solicitud_locacion ";
            cSql = cSql + " LEFT JOIN clientes ";
            cSql = cSql + " ON clientes.codigo=solicitud_locacion.idcliente ";
            cSql = cSql + " LEFT JOIN edificios ";
            cSql = cSql + " ON edificios.idunidad=solicitud_locacion.idunidad ";
            cSql = cSql + " LEFT JOIN inmuebles ";
            cSql = cSql + " ON inmuebles.idinmueble=solicitud_locacion.idunidad ";
            cSql = cSql + " WHERE solicitud_locacion.fechasolicitud BETWEEN ? AND ? ";
            cSql = cSql + " ORDER BY solicitud_locacion.idsolicitud ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    solicitud_locacion sl = new solicitud_locacion();
                    cliente cl = new cliente();
                    edificio ed = new edificio();

                    sl.setIdcliente(cl);
                    sl.setIdunidad(ed);

                    sl.setIdsolicitud(rs.getInt("idsolicitud"));
                    sl.setFechasolicitud(rs.getDate("fechasolicitud"));
                    sl.getIdcliente().setCodigo(rs.getInt("idcliente"));
                    sl.getIdcliente().setFechanacimiento(rs.getDate("fechanacimiento"));
                    sl.getIdcliente().setNombre(rs.getString("nombrelocatario"));
                    sl.getIdcliente().setDireccion(rs.getString("direccionlocatario"));
                    sl.getIdcliente().setRuc(rs.getString("ruclocatario"));
                    sl.getIdcliente().setEstadocivil(rs.getString("estadocivillocatario"));
                    sl.getIdcliente().setCelular(rs.getString("telefonofijo"));
                    sl.getIdcliente().setTelefono(rs.getString("telefonomovil"));

                    sl.setNombreinmueble(rs.getString("nomedif"));

                    sl.setIdconyugue(rs.getInt("idconyugue"));
                    sl.setNacimientoconyugue(rs.getDate("nacimientoconyugue"));
                    sl.setNombreconyugue(rs.getString("nombreconyugue"));
                    sl.setDireccionparticularconyugue(rs.getString("direccionparticularconyugue"));
                    sl.setNrodocumentoconyugue(rs.getString("nrodocumentoconyugue"));
                    sl.setTelefonomovilconyugue(rs.getString("telefonomovilconyugue"));
                    sl.setTelefonoconyugue(rs.getString("Telefonoconyugue"));

                    sl.getIdunidad().setIdunidad(rs.getInt("idunidad"));
                    sl.getIdunidad().setCtactral(rs.getString("nombrectactral"));
                    sl.getIdunidad().setNir(rs.getString("nir"));
                    sl.getIdunidad().setNis(rs.getString("nis"));
                    sl.getIdunidad().setTelunid(rs.getString("telefonoedificio"));
                    sl.getIdunidad().setMedcorpo(rs.getString("corposana"));
                    sl.getIdunidad().setMedande(rs.getString("ande"));
                    sl.getIdunidad().setDepgtia(rs.getBigDecimal("montogarantia"));
                    sl.getIdunidad().setAlquiler(rs.getBigDecimal("alquiler"));

                    sl.setNrohabitantes(rs.getInt("nrohabitantes"));
                    sl.setEstado(rs.getString("estado"));
                    sl.setUsuarioalta(rs.getInt("usuarioalta"));
                    sl.setUsuarioproceso(rs.getInt("usuarioproceso"));
                    sl.setFechaproceso(rs.getDate("fechaproceso"));
                    sl.setObservaciones(rs.getString("observaciones"));
                    lista.add(sl);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public solicitud_locacion buscarSolicitud_locacion(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        solicitud_locacion sl = new solicitud_locacion();
        try {
            String sql = "SELECT solicitud_locacion.idsolicitud,solicitud_locacion.fechasolicitud,solicitud_locacion.idcliente,"
                    + "solicitud_locacion.idunidad,solicitud_locacion.idconyugue,solicitud_locacion.nrohabitantes,"
                    + "solicitud_locacion.estado,solicitud_locacion.usuarioalta,solicitud_locacion.depositogarantia,solicitud_locacion.importealquiler,"
                    + "solicitud_locacion.usuarioproceso,solicitud_locacion.fechaproceso,solicitud_locacion.observaciones,"
                    + "clientes.fechanacimiento AS fechanacimiento, clientes.nombre AS nombrelocatario, clientes.direccion AS direccionlocatario,"
                    + "clientes.ruc AS ruclocatario,clientes.celular AS telefonofijo, clientes.telefono AS telefonomovil,"
                    + "edificios.ctactral AS nombrectactral, edificios.nir AS nir, edificios.nis AS nis, edificios.telunid AS telefonoedificio,"
                    + " edificios.medcorpo AS corposana, edificios.medande AS ande,edificios.depgtia AS montogarantia, edificios.alquiler AS alquiler,"
                    + "(SELECT clientes.codigo "
                    + "FROM clientes "
                    + "WHERE clientes.codigo=solicitud_locacion.idconyugue) idconyugue, "
                    + "(SELECT clientes.fechanacimiento "
                    + "FROM clientes "
                    + "WHERE clientes.codigo=solicitud_locacion.idconyugue) nacimientoconyugue, "
                    + "(SELECT clientes.nombre "
                    + "FROM clientes "
                    + "WHERE clientes.codigo=solicitud_locacion.idconyugue) nombreconyugue, "
                    + "(SELECT clientes.direccion "
                    + "FROM clientes "
                    + "WHERE clientes.codigo=solicitud_locacion.idconyugue) direccionparticularconyugue, "
                    + "(SELECT clientes.ruc "
                    + "FROM clientes "
                    + "WHERE clientes.codigo=solicitud_locacion.idconyugue) nrodocumentoconyugue, "
                    + "(SELECT clientes.celular "
                    + "FROM clientes "
                    + "WHERE clientes.codigo=solicitud_locacion.idconyugue) telefonomovilconyugue, "
                    + "(SELECT clientes.telefono "
                    + "FROM clientes "
                    + "WHERE clientes.codigo=solicitud_locacion.idconyugue) telefonoconyugue, "
                    + "(SELECT inmuebles.nomedif "
                    + "FROM inmuebles  "
                    + "WHERE inmuebles.idinmueble=solicitud_locacion.idunidad) nomedif, "
                    + " CASE clientes.estadocivil "
                    + " WHEN \"C\" THEN \"CASADO/A\" "
                    + " WHEN \"S\" THEN \"SOLTERO/A\" "
                    + " WHEN \"SE\" THEN \"SEPARADO/A\" "
                    + " WHEN \"V\" THEN \"VIUDO/A\" "
                    + " END AS estadocivillocatario "
                    + " FROM solicitud_locacion "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=solicitud_locacion.idcliente "
                    + " LEFT JOIN edificios "
                    + " ON edificios.idunidad=solicitud_locacion.idunidad "
                    + " LEFT JOIN inmuebles "
                    + " ON inmuebles.idinmueble=solicitud_locacion.idunidad "
                    + " WHERE solicitud_locacion.idsolicitud = ? "
                    + " ORDER BY solicitud_locacion.idsolicitud ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cl = new cliente();
                    edificio ed = new edificio();

                    sl.setIdcliente(cl);
                    sl.setIdunidad(ed);

                    sl.setIdsolicitud(rs.getInt("idsolicitud"));
                    sl.setFechasolicitud(rs.getDate("fechasolicitud"));
                    sl.getIdcliente().setCodigo(rs.getInt("idcliente"));
                    sl.getIdcliente().setFechanacimiento(rs.getDate("fechanacimiento"));
                    sl.getIdcliente().setNombre(rs.getString("nombrelocatario"));
                    sl.getIdcliente().setDireccion(rs.getString("direccionlocatario"));
                    sl.getIdcliente().setRuc(rs.getString("ruclocatario"));
                    sl.getIdcliente().setEstadocivil(rs.getString("estadocivillocatario"));
                    sl.getIdcliente().setCelular(rs.getString("telefonofijo"));
                    sl.getIdcliente().setTelefono(rs.getString("telefonomovil"));

                    sl.setNombreinmueble(rs.getString("nomedif"));

                    sl.setIdconyugue(rs.getInt("idconyugue"));
                    sl.setNacimientoconyugue(rs.getDate("nacimientoconyugue"));
                    sl.setNombreconyugue(rs.getString("nombreconyugue"));
                    sl.setDireccionparticularconyugue(rs.getString("direccionparticularconyugue"));
                    sl.setNrodocumentoconyugue(rs.getString("nrodocumentoconyugue"));
                    sl.setTelefonomovilconyugue(rs.getString("telefonomovilconyugue"));
                    sl.setTelefonoconyugue(rs.getString("Telefonoconyugue"));

                    sl.getIdunidad().setIdunidad(rs.getInt("idunidad"));
                    sl.getIdunidad().setCtactral(rs.getString("nombrectactral"));
                    sl.getIdunidad().setNir(rs.getString("nir"));
                    sl.getIdunidad().setNis(rs.getString("nis"));
                    sl.getIdunidad().setTelunid(rs.getString("telefonoedificio"));
                    sl.getIdunidad().setMedcorpo(rs.getString("corposana"));
                    sl.getIdunidad().setMedande(rs.getString("ande"));
                    sl.getIdunidad().setDepgtia(rs.getBigDecimal("montogarantia"));
                    sl.getIdunidad().setAlquiler(rs.getBigDecimal("alquiler"));

                    sl.setNrohabitantes(rs.getInt("nrohabitantes"));
                    sl.setEstado(rs.getString("estado"));
                    sl.setUsuarioalta(rs.getInt("usuarioalta"));
                    sl.setUsuarioproceso(rs.getInt("usuarioproceso"));
                    sl.setFechaproceso(rs.getDate("fechaproceso"));
                    sl.setObservaciones(rs.getString("observaciones"));
                    
                    sl.setDepositogarantia(rs.getDouble("depositogarantia"));
                    sl.setImportealquiler(rs.getDouble("importealquiler"));

                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return sl;
    }

    public solicitud_locacion insertarSolicitud(solicitud_locacion ocr, String detalle, String juridica, String propietario) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        int id = 0;
        boolean guardado, guardado2, guardado3 = false;
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("INSERT INTO solicitud_locacion (fechasolicitud,"
                + "idcliente,idunidad,nacimiento,nombres,direccionparticular,"
                + "ruc,estadocivil,telefonofijo,telefonomovil,usuarioalta,"
                + "observaciones,idconyugue,nacimientoconyugue,"
                + "nombreconyugue,direccionparticularconyugue,"
                + "nrodocumentoconyugue,telefonoconyugue,"
                + "telefonomovilconyugue,nrohabitantes,depositogarantia,importealquiler) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, ocr.getFechasolicitud());
        ps.setInt(2, ocr.getIdcliente().getCodigo());
        ps.setInt(3, ocr.getIdunidad().getIdunidad());
        ps.setDate(4, ocr.getNacimiento());
        ps.setString(5, ocr.getNombres());
        ps.setString(6, ocr.getDireccionparticular());
        ps.setString(7, ocr.getRuc());
        ps.setString(8, ocr.getEstadocivil());
        ps.setString(9, ocr.getTelefonofijo());
        ps.setString(10, ocr.getTelefonomovil());
        ps.setInt(11, ocr.getUsuarioalta());
        ps.setString(12, ocr.getObservaciones());
        ps.setInt(13, ocr.getIdconyugue());
        ps.setDate(14, ocr.getNacimientoconyugue());
        ps.setString(15, ocr.getNombreconyugue());
        ps.setString(16, ocr.getDireccionparticularconyugue());
        ps.setString(17, ocr.getNrodocumentoconyugue());
        ps.setString(18, ocr.getTelefonoconyugue());
        ps.setString(19, ocr.getTelefonomovilconyugue());
        ps.setInt(20, ocr.getNrohabitantes());
        ps.setDouble(21, ocr.getDepositogarantia());
        ps.setDouble(22, ocr.getImportealquiler());
        
        int rowsUpdated = ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            guardado = guardarGarante(id, detalle, con);
            guardado2 = guardarPersonaJuridica(id, juridica, con);
            guardado3 = guardarPropietario(id, propietario, con);
            //if (ocr.getNcontroldetalle() == 1) {
              //  guardado2 = guardarPersonaJuridica(id, juridica, con);
            //}
        }
        ocr.setIdsolicitud(id);

        st.close();

        ps.close();
        return ocr;
    }

    public solicitud_locacion ActualizarAsiento(solicitud_locacion ocr, String detalle, String juridica, String propietario ) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        int id = 0;
        boolean guardado,guardado2,guardado3 = false;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE solicitud_locacion SET fechasolicitud=?,idcliente=?,idunidad=?,nacimiento=?,nombres=?,direccionparticular=?,ruc=?,estadocivil=?,telefonofijo=?,telefonomovil=?,usuarioalta=?,observaciones=?,idconyugue=?,nacimientoconyugue=?,nombreconyugue=?,direccionparticularconyugue=?,nrodocumentoconyugue=?,telefonoconyugue=?,telefonomovilconyugue=?,nrohabitantes=? WHERE idsolicitud=" + ocr.getIdsolicitud());
        ps.setDate(1, ocr.getFechasolicitud());
        ps.setInt(2, ocr.getIdcliente().getCodigo());
        ps.setInt(3, ocr.getIdunidad().getIdunidad());
        ps.setDate(4, ocr.getNacimiento());
        ps.setString(5, ocr.getNombres());
        ps.setString(6, ocr.getDireccionparticular());
        ps.setString(7, ocr.getRuc());
        ps.setString(8, ocr.getEstadocivil());
        ps.setString(9, ocr.getTelefonofijo());
        ps.setString(10, ocr.getTelefonomovil());
        ps.setInt(11, ocr.getUsuarioalta());
        ps.setString(12, ocr.getObservaciones());
        ps.setInt(13, ocr.getIdconyugue());
        ps.setDate(14, ocr.getNacimientoconyugue());
        ps.setString(15, ocr.getNombreconyugue());
        ps.setString(16, ocr.getDireccionparticularconyugue());
        ps.setString(17, ocr.getNrodocumentoconyugue());
        ps.setString(18, ocr.getTelefonoconyugue());
        ps.setString(19, ocr.getTelefonomovilconyugue());
        ps.setInt(20, ocr.getNrohabitantes());
        ps.executeUpdate();
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            guardado = guardarGarante(ocr.getIdsolicitud(), detalle, con);
            guardado2 = guardarPersonaJuridica(ocr.getIdsolicitud(), juridica, con);
            guardado3 = guardarPropietario(ocr.getIdsolicitud(), propietario, con);

        }
        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

  
    public boolean guardarGarante(int id, String detalle, Conexion conexion) throws SQLException {
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
                    String sql = "INSERT INTO garante_alquiler("
                            + "idcontrato,"
                            + "idcliente"
                            + ") "
                            + "values(?,?)";                    
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("idcliente").getAsString());
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
        return guardado;
    }

    public boolean guardarPersonaJuridica(int id, String juridica, Conexion conexion) throws SQLException {
        boolean guardado2 = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(juridica);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO solicitud_loc_juridica("
                            + "idcontrato,"
                            + "idcliente"
                            + ") "
                            + "values(?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("idcliente").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado2 = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardado2 = false;
                    break;
                }
            }

            if (guardado2) {
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
            guardado2 = false;
        }
        
        return guardado2;
    }

 public boolean guardarPropietario(int id, String propietario, Conexion conexion) throws SQLException {
        boolean guardado3 = true;
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
                    String sql = "INSERT INTO solicitud_loc_propietario("
                            + "idcontrato,"
                            + "idcliente"
                            + ") "
                            + "values(?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("idcliente").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado3 = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardado3 = false;
                    break;
                }
            }

            if (guardado3) {
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
            guardado3 = false;
        }
        conn.close();
        return guardado3;
    }    
    
    
    
    public boolean ActualizarGarante(int id, String detalle, Conexion conexion) throws SQLException {
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
                    String sql = "UPDSATE garante_alquiler SET("
                            + "idcontrato,"
                            + "idcliente"
                            + ") "
                            + "values(?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("idcliente").getAsString());
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

    public boolean actualizarPersonaJuridica(int id, String juridica, Conexion conexion) throws SQLException {
        boolean guardado2 = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(juridica);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "UPDSATE solicitud_loc_juridica SET("
                            + "idcontrato,"
                            + "idcliente"
                            + ") "
                            + "values(?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("idcliente").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado2 = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardado2 = false;
                    break;
                }
            }

            if (guardado2) {
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
            guardado2 = false;
        }
        conn.close();
        return guardado2;
    }
public boolean actualizarPropietario(int id, String propietario, Conexion conexion) throws SQLException {
        boolean guardado3 = true;
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
                    String sql = "UPDSATE solicitud_loc_propietario SET("
                            + "idcontrato,"
                            + "idcliente"
                            + ") "
                            + "values(?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, obj.get("idcliente").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado3 = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardado3 = false;
                    break;
                }
            }

            if (guardado3) {
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
            guardado3 = false;
        }
        conn.close();
        return guardado3;
    }
    public boolean borrarAjustes(String idsolicitud) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM solicitud_locacion WHERE idsolicitud=?");
        ps.setString(1, idsolicitud);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.Config;
import Conexion.Conexion;
import Modelo.abogado;
import Modelo.barrio;
import Modelo.cliente;
import Modelo.localidad;
import Modelo.giraduria;
import Modelo.carrera;
import Modelo.plan;
import Modelo.situacion;
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
public class clienteDAO {

    Conexion con = null;
    Statement st = null;
    int nAsesor = Integer.valueOf(Config.cCodigoAsesor);

    public ArrayList<cliente> todos() throws SQLException {
        ArrayList<cliente> lista = new ArrayList<cliente>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT clientes.codigo,clientes.comitente,clientes.nombre,clientes.ruc,clientes.fechanacimiento,clientes.estadocivil,clientes.conyugue,clientes.direccion,"
                + "clientes.telefono,clientes.fax,clientes.celular,clientes.mail,clientes.profesion,clientes.lugartrabajo,clientes.cargolaboral,clientes.salario,clientes.legajo,"
                + "clientes.turnolegajo,clientes.descuentos_aportes,clientes.direccionlaboral,clientes.fechaingreso,clientes.fechaingresofuncionario,clientes.telefonolaboral,clientes.faxlaboral,"
                + "clientes.maillaboral,clientes.vendedor,clientes.categoria,clientes.salarioconyugue,clientes.otrosingresos,clientes.localidad,clientes.limitecredito,clientes.credito,"
                + "clientes.notas,clientes.estado,clientes.saldo,clientes.cedulaconyugue,clientes.garante,clientes.conyuguegarante,clientes.cedulagarante,clientes.cedulaconyuguegarante,"
                + "clientes.direcciongarante,clientes.telefonogarante,clientes.lugartrabajogarante,clientes.profesiongarante,clientes.teloficinagarante,clientes.idcta,clientes.asesor,"
                + "clientes.banco,clientes.cuenta,clientes.casapropia,clientes.autopropio,clientes.informconf,clientes.sexo,clientes.abogado,clientes.barrio,"
                + "clientes.plazocredito,localidades.nombre as nombrelocalidad,giradurias.nombre as nombregiraduria,clientes.giraduria,clientes.cedula "
                + " FROM clientes "
                + " LEFT JOIN localidades "
                + " ON localidades.codigo=clientes.localidad "
                + " LEFT JOIN giradurias "
                + " ON giradurias.codigo=clientes.giraduria "
                + " WHERE clientes.estado=1 "
                + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                + " ORDER BY clientes.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente cl = new cliente();
                localidad lo = new localidad();
                giraduria gi = new giraduria();
                barrio ba = new barrio();
                abogado ab = new abogado();
                cl.setAbogado(ab);
                cl.setLocalidad(lo);
                cl.setGiraduria(gi);
                cl.setBarrio(ba);
                cl.setCodigo(rs.getInt("codigo"));
                cl.setComitente(rs.getInt("comitente"));
                cl.setNombre(rs.getString("nombre"));
                cl.setPlazocredito(rs.getInt("plazocredito"));
                cl.setRuc(rs.getString("ruc"));
                cl.setCedula(rs.getString("cedula"));
                cl.setFechanacimiento(rs.getDate("fechanacimiento"));
                cl.setEstadocivil(rs.getString("estadocivil"));
                cl.setConyugue(rs.getString("conyugue"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setFax(rs.getString("fax"));
                cl.setCelular(rs.getString("celular"));
                cl.setMail(rs.getString("mail"));
                cl.setProfesion(rs.getString("profesion"));
                cl.setLugartrabajo(rs.getString("lugartrabajo"));
                cl.setCargolaboral(rs.getString("cargolaboral"));
                cl.setSalario(rs.getDouble("salario"));
                cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                cl.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                cl.setDireccionlaboral(rs.getString("direccionlaboral"));
                cl.setFechaingreso(rs.getDate("fechaingreso"));
                cl.setTelefonolaboral(rs.getString("telefonolaboral"));
                cl.setFaxlaboral(rs.getString("faxlaboral"));
                cl.setMaillaboral(rs.getString("maillaboral"));
                cl.setVendedor(rs.getInt("vendedor"));
                cl.setCategoria(rs.getInt("categoria"));
                cl.setSalarioconyugue(rs.getDouble("salarioconyugue"));
                cl.setOtrosingresos(rs.getDouble("otrosingresos"));
                cl.getLocalidad().setCodigo(rs.getInt("localidad"));
                cl.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                cl.setLimitecredito(rs.getDouble("limitecredito"));
                cl.setCredito(rs.getDouble("credito"));
                cl.setNotas(rs.getString("notas"));
                cl.setEstado(rs.getInt("estado"));
                cl.setSaldo(rs.getDouble("saldo"));
                cl.setCedulaconyugue(rs.getString("cedulaconyugue"));
                cl.setGarante(rs.getString("garante"));
                cl.setConyuguegarante(rs.getString("conyuguegarante"));
                cl.setCedulaconyugue(rs.getString("cedulagarante"));
                cl.setCedulaconyuguegarante(rs.getString("cedulaconyuguegarante"));
                cl.setDirecciongarante(rs.getString("direcciongarante"));
                cl.setTelefonogarante(rs.getString("telefonogarante"));
                cl.setLugartrabajogarante(rs.getString("lugartrabajogarante"));
                cl.setProfesiongarante(rs.getString("profesiongarante"));
                cl.setTeloficinagarante(rs.getString("teloficinagarante"));
                cl.setAsesor(rs.getInt("asesor"));
                cl.setBanco(rs.getInt("banco"));
                cl.setCuenta(rs.getString("cuenta"));
                cl.setCasapropia(rs.getInt("casapropia"));
                cl.setAutopropio(rs.getInt("autopropio"));
                cl.setInformconf(rs.getInt("informconf"));
                cl.setSexo(rs.getInt("sexo"));
                cl.getAbogado().setCodigo(rs.getInt("abogado"));
                 cl.getBarrio().setCodigo(rs.getInt("barrio"));
                lista.add(cl);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<cliente> todosimple() throws SQLException {
        ArrayList<cliente> lista = new ArrayList<cliente>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT clientes.codigo,clientes.nombre,clientes.direccion,clientes.telefono,clientes.giraduria,"
                + "giradurias.nombre as nombregiraduria,clientes.ruc,clientes.cedula,clientes.plazocredito,clientes.limitecredito "
                + " FROM clientes "
                + " LEFT JOIN giradurias "
                + " ON giradurias.codigo=clientes.giraduria "
                + " WHERE clientes.estado=1 "
                + " ORDER BY clientes.codigo ";

        System.out.println(sql);

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente cl = new cliente();
                giraduria gi = new giraduria();
                cl.setGiraduria(gi);
                cl.setCodigo(rs.getInt("codigo"));
                cl.setNombre(rs.getString("nombre"));
                cl.setRuc(rs.getString("ruc"));
                cl.setCedula(rs.getString("cedula"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setPlazocredito(rs.getInt("plazocredito"));
                cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                cl.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                cl.setLimitecredito(rs.getDouble("limitecredito"));
                lista.add(cl);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<cliente> todosxgiraduria(int giraduria) throws SQLException {
        ArrayList<cliente> lista = new ArrayList<cliente>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT clientes.codigo,clientes.nombre,clientes.direccion,clientes.telefono,clientes.giraduria,"
                + "giradurias.nombre as nombregiraduria,clientes.ruc,clientes.cedula "
                + " FROM clientes "
                + " LEFT JOIN giradurias "
                + " ON giradurias.codigo=clientes.giraduria "
                + " WHERE clientes.giraduria=? "
                + " AND clientes.estado=1 "
                + " ORDER BY clientes.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, giraduria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente cl = new cliente();
                giraduria gi = new giraduria();
                cl.setGiraduria(gi);
                cl.setCodigo(rs.getInt("codigo"));
                cl.setNombre(rs.getString("nombre"));
                cl.setRuc(rs.getString("ruc"));
                cl.setCedula(rs.getString("cedula"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                cl.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                lista.add(cl);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public cliente buscarIdSimple(int id) throws SQLException {
        cliente cl = new cliente();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT clientes.codigo,clientes.nombre,clientes.direccion,clientes.telefono,clientes.giraduria,"
                    + "giradurias.nombre as nombregiraduria,clientes.ruc,clientes.cedula,clientes.plazocredito,clientes.limitecredito "
                    + " FROM clientes "
                    + " LEFT JOIN giradurias "
                    + " ON giradurias.codigo=clientes.giraduria "
                    + " WHERE clientes.estado=1 and clientes.codigo=? "
                    + " ORDER BY clientes.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    giraduria gi = new giraduria();
                    cl.setGiraduria(gi);
                    cl.setCodigo(rs.getInt("codigo"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setRuc(rs.getString("ruc"));
                    cl.setCedula(rs.getString("cedula"));
                    cl.setDireccion(rs.getString("direccion"));
                    cl.setTelefono(rs.getString("telefono"));
                    cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cl.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cl.setPlazocredito(rs.getInt("plazocredito"));
                    cl.setLimitecredito(rs.getDouble("limitecredito"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();

        return cl;
    }

    public cliente buscarId(int id) throws SQLException {
        cliente cl = new cliente();

        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT clientes.codigo,clientes.comitente,"
                    + "clientes.nombre,clientes.ruc,clientes.fechanacimiento,"
                    + "clientes.estadocivil,clientes.conyugue,clientes.direccion,"
                    + "clientes.telefono,clientes.fax,clientes.celular,"
                    + "clientes.mail,clientes.profesion,clientes.lugartrabajo,"
                    + "clientes.cargolaboral,clientes.salario,clientes.legajo,"
                    + "clientes.turnolegajo,clientes.descuentos_aportes,"
                    + "clientes.direccionlaboral,clientes.fechaingreso,"
                    + "clientes.fechaingresofuncionario,clientes.telefonolaboral,"
                    + "clientes.faxlaboral,"
                    + "clientes.maillaboral,clientes.vendedor,clientes.categoria,"
                    + "clientes.salarioconyugue,clientes.otrosingresos,"
                    + "clientes.localidad,clientes.limitecredito,clientes.credito,"
                    + "clientes.notas,clientes.estado,clientes.saldo,"
                    + "clientes.cedulaconyugue,clientes.garante,"
                    + "clientes.conyuguegarante,clientes.cedulagarante,"
                    + "clientes.cedulaconyuguegarante,"
                    + "clientes.direcciongarante,clientes.telefonogarante,"
                    + "clientes.lugartrabajogarante,clientes.profesiongarante,"
                    + "clientes.teloficinagarante,clientes.idcta,clientes.asesor,"
                    + "clientes.plazocredito,clientes.banco,"
                    + "clientes.cuenta,clientes.casapropia,clientes.autopropio,"
                    + "clientes.informconf,clientes.sexo,clientes.abogado,"
                    + "clientes.barrio,clientes.giraduria,clientes.programa,"
                    + "localidades.nombre as nombrelocalidad,giradurias.nombre as nombregiraduria,clientes.cedula "
                    + " FROM clientes "
                    + " LEFT JOIN localidades "
                    + " ON localidades.codigo=clientes.localidad "
                    + " LEFT JOIN giradurias "
                    + " ON giradurias.codigo=clientes.giraduria "
                    + " WHERE clientes.codigo= ? "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " ORDER BY clientes.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    localidad lo = new localidad();
                    giraduria gi = new giraduria();
                    barrio ba = new barrio();
                    cl.setGiraduria(gi);
                    cl.setLocalidad(lo);
                    cl.setBarrio(ba);
                    abogado ab = new abogado();
                    cl.setAbogado(ab);

                    cl.setCodigo(rs.getInt("codigo"));
                    cl.setComitente(rs.getInt("comitente"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setPlazocredito(rs.getInt("plazocredito"));
                    cl.setRuc(rs.getString("ruc"));
                    cl.setCedula(rs.getString("cedula"));
                    cl.setFechanacimiento(rs.getDate("fechanacimiento"));
                    cl.setEstadocivil(rs.getString("estadocivil"));
                    cl.setConyugue(rs.getString("conyugue"));
                    cl.setDireccion(rs.getString("direccion"));
                    cl.setTelefono(rs.getString("telefono"));
                    cl.setFax(rs.getString("fax"));
                    cl.setCelular(rs.getString("celular"));
                    cl.setMail(rs.getString("mail"));
                    cl.setProfesion(rs.getString("profesion"));
                    cl.setLugartrabajo(rs.getString("lugartrabajo"));
                    cl.setCargolaboral(rs.getString("cargolaboral"));
                    cl.setSalario(rs.getDouble("salario"));
                    cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cl.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cl.setDireccionlaboral(rs.getString("direccionlaboral"));
                    cl.setFechaingreso(rs.getDate("fechaingreso"));
                    cl.setFechaingresofuncionario(rs.getDate("fechaingresofuncionario"));
                    cl.setLegajo(rs.getInt("legajo"));
                    cl.setDescuentos_aportes(rs.getInt("descuentos_aportes"));
                    cl.setTelefonolaboral(rs.getString("telefonolaboral"));
                    cl.setFaxlaboral(rs.getString("faxlaboral"));
                    cl.setMaillaboral(rs.getString("maillaboral"));
                    cl.setVendedor(rs.getInt("vendedor"));
                    cl.setCategoria(rs.getInt("categoria"));
                    cl.setSalarioconyugue(rs.getDouble("salarioconyugue"));
                    cl.setOtrosingresos(rs.getDouble("otrosingresos"));
                    cl.getLocalidad().setCodigo(rs.getInt("localidad"));
                    cl.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                    cl.setLimitecredito(rs.getDouble("limitecredito"));
                    cl.setCredito(rs.getDouble("credito"));
                    cl.setNotas(rs.getString("notas"));
                    cl.setEstado(rs.getInt("estado"));
                    cl.setSaldo(rs.getDouble("saldo"));
                    cl.setCedulaconyugue(rs.getString("cedulaconyugue"));
                    cl.setGarante(rs.getString("garante"));
                    cl.setConyuguegarante(rs.getString("conyuguegarante"));
                    cl.setCedulaconyugue(rs.getString("cedulagarante"));
                    cl.setCedulaconyuguegarante(rs.getString("cedulaconyuguegarante"));
                    cl.setDirecciongarante(rs.getString("direcciongarante"));
                    cl.setTelefonogarante(rs.getString("telefonogarante"));
                    cl.setLugartrabajogarante(rs.getString("lugartrabajogarante"));
                    cl.setProfesiongarante(rs.getString("profesiongarante"));
                    cl.setTeloficinagarante(rs.getString("teloficinagarante"));
                    cl.setAsesor(rs.getInt("asesor"));
                    cl.setBanco(rs.getInt("banco"));
                    cl.setCuenta(rs.getString("cuenta"));
                    cl.setCasapropia(rs.getInt("casapropia"));
                    cl.setAutopropio(rs.getInt("autopropio"));
                    cl.setInformconf(rs.getInt("informconf"));
                    cl.setTurno(rs.getString("turnolegajo"));
                    cl.setSexo(rs.getInt("sexo"));
                    cl.getAbogado().setCodigo(rs.getInt("abogado"));
                    cl.getBarrio().setCodigo(rs.getInt("barrio"));
                    cl.setPrograma(rs.getInt("programa"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return cl;
    }

    public cliente buscarIdxGiraduria(int id) throws SQLException {

        cliente cl = new cliente();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT clientes.codigo,clientes.nombre,clientes.ruc,clientes.telefono,clientes.direccion,"
                    + " localidades.nombre as nombrelocalidad,giradurias.nombre as nombregiraduria,clientes.cedula "
                    + " FROM clientes "
                    + " LEFT JOIN localidades "
                    + " ON localidades.codigo=clientes.localidad "
                    + " LEFT JOIN giradurias "
                    + " ON giradurias.codigo=clientes.giraduria "
                    + " WHERE clientes.codigo= ? "
                    + " ORDER BY clientes.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    localidad lo = new localidad();
                    giraduria gi = new giraduria();
                    cl.setLocalidad(lo);
                    cl.setCodigo(rs.getInt("codigo"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setRuc(rs.getString("ruc"));
                    cl.setCedula(rs.getString("cedula"));
                    cl.setDireccion(rs.getString("direccion"));
                    cl.setTelefono(rs.getString("telefono"));
                    cl.getLocalidad().setCodigo(rs.getInt("localidad"));
                    cl.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                    cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cl.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();

        return cl;
    }

    public cliente buscarIdxAsesor(int id) throws SQLException {

        cliente cl = new cliente();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT clientes.codigo,clientes.nombre,clientes.ruc,"
                    + "clientes.telefono,clientes.direccion,"
                    + " localidades.nombre as nombrelocalidad,clientes.cedula "
                    + " FROM clientes "
                    + " LEFT JOIN localidades "
                    + " ON localidades.codigo=clientes.localidad "
                    + " WHERE clientes.codigo= ? "
                    + " AND clientes.asesor= " + nAsesor
                    + " ORDER BY clientes.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    localidad lo = new localidad();
                    cl.setLocalidad(lo);
                    cl.setCodigo(rs.getInt("codigo"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setRuc(rs.getString("ruc"));
                    cl.setCedula(rs.getString("cedula"));
                    cl.setDireccion(rs.getString("direccion"));
                    cl.setTelefono(rs.getString("telefono"));
                    cl.getLocalidad().setCodigo(rs.getInt("localidad"));
                    cl.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();

        return cl;
    }

    public cliente insertarCliente(cliente c) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO clientes (codigo,nombre,"
                + "direccion,localidad,"
                + "ruc,telefono,fechaingreso,"
                + "fechanacimiento,estado,cedula) VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, c.getCodigo());
        ps.setString(2, c.getNombre());
        ps.setString(3, c.getDireccion());
        ps.setInt(4, c.getLocalidad().getCodigo());
        ps.setString(5, c.getRuc());
        ps.setString(6, c.getTelefono());
        ps.setDate(7, c.getFechaingreso());
        ps.setDate(8, c.getFechanacimiento());
        ps.setInt(9, c.getEstado());
        ps.setString(10, c.getCedula());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> CARGA DE clientes " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        conn.close();
        return c;
    }

    public boolean actualizarCliente(cliente c) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE clientes SET nombre=?, direccion=?, localidad=?, ruc=?, telefono=?,estado=?,fechaingreso=?,fechanacimiento=? WHERE codigo=" + c.getCodigo());
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getDireccion());
        ps.setInt(3, c.getLocalidad().getCodigo());
        ps.setString(4, c.getRuc());
        ps.setString(5, c.getTelefono());
        ps.setInt(6, c.getEstado());
        ps.setDate(7, c.getFechaingreso());
        ps.setDate(8, c.getFechanacimiento());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ActualizarSociosAso(cliente c) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE clientes SET giraduria=?,"
                + "fechaingresofuncionario=?,legajo=?,"
                + "fechaingreso=?,descuentos_aportes=?,"
                + "turnolegajo=?,lugartrabajo=?,credito=? "
                + " WHERE codigo=" + c.getCodigo());
        ps.setInt(1, c.getGiraduria().getCodigo());
        ps.setDate(2, c.getFechaingresofuncionario());
        ps.setInt(3, c.getLegajo());
        ps.setDate(4, c.getFechaingreso());
        ps.setInt(5, c.getDescuentos_aportes());
        ps.setString(6, c.getTurno());
        ps.setString(7, c.getLugartrabajo());
        ps.setDouble(8, c.getCredito());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarCliente(int codcliente) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM clientes WHERE codigo=?");
        ps.setInt(1, codcliente);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean DesactivarCliente(cliente c) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE clientes SET estado=?  WHERE codigo=" + c.getCodigo());
        ps.setInt(1, c.getEstado());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public cliente buscarIdServer(int id) throws SQLException {
        cliente cl = new cliente();

        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT clientes.codigo "
                    + " FROM clientes "
                    + " WHERE clientes.codigo= ? "
                    + " ORDER BY clientes.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    cl.setCodigo(rs.getInt("codigo"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return cl;
    }

    public ArrayList<cliente> descontaraportes(int giraduria) throws SQLException {
        ArrayList<cliente> lista = new ArrayList<cliente>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT clientes.codigo,clientes.nombre,"
                + "clientes.direccion,clientes.telefono,clientes.giraduria,"
                + "giradurias.nombre as nombregiraduria,descuentos_aportes,"
                + "clientes.ruc,clientes.cedula,clientes.credito "
                + " FROM clientes "
                + " LEFT JOIN giradurias "
                + " ON giradurias.codigo=clientes.giraduria "
                + " WHERE clientes.giraduria=? "
                + " AND clientes.estado=1 "
                + " AND clientes.descuentos_aportes=1"
                + " ORDER BY clientes.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, giraduria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente cl = new cliente();
                giraduria gi = new giraduria();
                cl.setGiraduria(gi);
                cl.setCodigo(rs.getInt("codigo"));
                cl.setNombre(rs.getString("nombre"));
                cl.setRuc(rs.getString("ruc"));
                cl.setCedula(rs.getString("cedula"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setDescuentos_aportes(rs.getInt("descuentos_aportes"));
                cl.setCredito(rs.getDouble("credito"));
                cl.setTelefono(rs.getString("telefono"));
                cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                cl.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                lista.add(cl);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public cliente buscarRuc(String id) throws SQLException {
        cliente cl = new cliente();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT clientes.codigo,clientes.nombre,clientes.direccion,clientes.telefono,clientes.notas,"
                    + "clientes.ruc,clientes.cedula "
                    + " FROM clientes "
                    + " WHERE clientes.ruc=? "
                    + " ORDER BY clientes.ruc ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cl.setCodigo(rs.getInt("codigo"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setRuc(rs.getString("ruc"));
                    cl.setCedula(rs.getString("cedula"));
                    cl.setDireccion(rs.getString("direccion"));
                    cl.setTelefono(rs.getString("telefono"));
                    cl.setNotas(rs.getString("notas"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();

        return cl;
    }

    public boolean ActualizarCuentaContable(cliente p) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE clientes SET "
                + " idcta=?  WHERE codigo='" + p.getCodigo() + "'");
        ps.setString(1, p.getIdcta().getCodigo());
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

    public cliente BuscarCuentaCliente(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        cliente pr = new cliente();
        String sql = "SELECT clientes.codigo,clientes.nombre,clientes.idcta,"
                + "plan.nombre AS nombrecuenta "
                + " FROM clientes "
                + " LEFT JOIN plan"
                + " ON plan.codigo=clientes.idcta "
                + " WHERE clientes.codigo=" + id
                + " ORDER BY clientes.codigo ";
        System.out.println(sql);

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                plan pl = new plan();
                pr.setCodigo(rs.getInt("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.setIdcta(pl);
                pr.getIdcta().setCodigo(rs.getString("idcta"));
                if (rs.getString("idcta").isEmpty()) {
                    pr.getIdcta().setNombre("");
                } else {
                    pr.getIdcta().setNombre(rs.getString("nombrecuenta"));
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return pr;
    }

    public boolean ActualizarCarrera(cliente c) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE clientes SET carrera=?,semestre=?,"
                + "fechaculminacion=?,fechaingreso=?,nroregistro=?  WHERE codigo=" + c.getCodigo());
        ps.setInt(1, c.getCarrera().getCodigo());
        ps.setInt(2, c.getSemestre());
        ps.setDate(3, c.getFechaculminacion());
        ps.setDate(4, c.getFechaingreso());
        ps.setString(5, c.getNroregistro());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public cliente buscarIdCarrera(int id) throws SQLException {
        cliente cl = new cliente();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT clientes.codigo,clientes.nombre,clientes.direccion,clientes.telefono,clientes.carrera,"
                    + "carreras.nombre as nombrecarrera,clientes.ruc,clientes.cedula,clientes.semestre,"
                    + "clientes.fechaculminacion,clientes.fechaingreso,clientes.nroregistro "
                    + " FROM clientes "
                    + " LEFT JOIN carreras "
                    + " ON carreras.codigo=clientes.carrera "
                    + " WHERE clientes.codigo=? "
                    + " ORDER BY clientes.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    carrera ca = new carrera();
                    cl.setCarrera(ca);
                    cl.setCodigo(rs.getInt("codigo"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setRuc(rs.getString("ruc"));
                    cl.setCedula(rs.getString("cedula"));
                    cl.setDireccion(rs.getString("direccion"));
                    cl.setTelefono(rs.getString("telefono"));
                    cl.getCarrera().setCodigo(rs.getInt("carrera"));
                    cl.getCarrera().setNombre(rs.getString("nombrecarrera"));
                    cl.setSemestre(rs.getInt("semestre"));
                    cl.setNroregistro(rs.getString("nroregistro"));
                    cl.setFechaculminacion(rs.getDate("fechaculminacion"));
                    cl.setFechaingreso(rs.getDate("fechaingreso"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();

        return cl;
    }

    public ArrayList<cliente> todosxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cliente> lista = new ArrayList<cliente>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT clientes.codigo,clientes.comitente,clientes.nombre,clientes.ruc,clientes.fechanacimiento,clientes.estadocivil,clientes.conyugue,clientes.direccion,"
                + "clientes.telefono,clientes.fax,clientes.celular,clientes.mail,clientes.profesion,clientes.lugartrabajo,clientes.cargolaboral,clientes.salario,clientes.legajo,"
                + "clientes.turnolegajo,clientes.descuentos_aportes,clientes.direccionlaboral,clientes.fechaingreso,clientes.fechaingresofuncionario,clientes.telefonolaboral,clientes.faxlaboral,"
                + "clientes.maillaboral,clientes.vendedor,clientes.categoria,clientes.salarioconyugue,clientes.otrosingresos,clientes.localidad,clientes.limitecredito,clientes.credito,"
                + "clientes.notas,clientes.estado,clientes.saldo,clientes.cedulaconyugue,clientes.garante,clientes.conyuguegarante,clientes.cedulagarante,clientes.cedulaconyuguegarante,"
                + "clientes.direcciongarante,clientes.telefonogarante,clientes.lugartrabajogarante,clientes.profesiongarante,clientes.teloficinagarante,clientes.idcta,clientes.asesor,"
                + "clientes.banco,clientes.cuenta,clientes.casapropia,clientes.autopropio,clientes.informconf,clientes.sexo,clientes.abogado,clientes.barrio,"
                + "clientes.plazocredito,localidades.nombre as nombrelocalidad,giradurias.nombre as nombregiraduria,clientes.giraduria,clientes.cedula "
                + " FROM clientes "
                + " LEFT JOIN localidades "
                + " ON localidades.codigo=clientes.localidad "
                + " LEFT JOIN giradurias "
                + " ON giradurias.codigo=clientes.giraduria "
                + " WHERE clientes.fechaingreso between ? AND ? AND clientes.estado=1 "
                + " ORDER BY clientes.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ResultSet rs = ps.executeQuery();
            System.out.println("--> " + sql);
            while (rs.next()) {
                cliente cl = new cliente();
                localidad lo = new localidad();
                giraduria gi = new giraduria();
                barrio ba = new barrio();
                cl.setLocalidad(lo);
                cl.setGiraduria(gi);
                cl.setBarrio(ba);
                abogado ab = new abogado();
                cl.setAbogado(ab);

                cl.setCodigo(rs.getInt("codigo"));
                cl.setComitente(rs.getInt("comitente"));
                cl.setNombre(rs.getString("nombre"));
                cl.setPlazocredito(rs.getInt("plazocredito"));
                cl.setRuc(rs.getString("ruc"));
                cl.setCedula(rs.getString("cedula"));
                cl.setFechanacimiento(rs.getDate("fechanacimiento"));
                cl.setEstadocivil(rs.getString("estadocivil"));
                cl.setConyugue(rs.getString("conyugue"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setFax(rs.getString("fax"));
                cl.setCelular(rs.getString("celular"));
                cl.setMail(rs.getString("mail"));
                cl.setProfesion(rs.getString("profesion"));
                cl.setLugartrabajo(rs.getString("lugartrabajo"));
                cl.setCargolaboral(rs.getString("cargolaboral"));
                cl.setSalario(rs.getDouble("salario"));
                cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                cl.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                cl.setDireccionlaboral(rs.getString("direccionlaboral"));
                cl.setFechaingreso(rs.getDate("fechaingreso"));
                cl.setTelefonolaboral(rs.getString("telefonolaboral"));
                cl.setFaxlaboral(rs.getString("faxlaboral"));
                cl.setMaillaboral(rs.getString("maillaboral"));
                cl.setVendedor(rs.getInt("vendedor"));
                cl.setCategoria(rs.getInt("categoria"));
                cl.setSalarioconyugue(rs.getDouble("salarioconyugue"));
                cl.setOtrosingresos(rs.getDouble("otrosingresos"));
                cl.getLocalidad().setCodigo(rs.getInt("localidad"));
                cl.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                cl.setLimitecredito(rs.getDouble("limitecredito"));
                cl.setCredito(rs.getDouble("credito"));
                cl.setNotas(rs.getString("notas"));
                cl.setEstado(rs.getInt("estado"));
                cl.setSaldo(rs.getDouble("saldo"));
                cl.setCedulaconyugue(rs.getString("cedulaconyugue"));
                cl.setGarante(rs.getString("garante"));
                cl.setConyuguegarante(rs.getString("conyuguegarante"));
                cl.setCedulaconyugue(rs.getString("cedulagarante"));
                cl.setCedulaconyuguegarante(rs.getString("cedulaconyuguegarante"));
                cl.setDirecciongarante(rs.getString("direcciongarante"));
                cl.setTelefonogarante(rs.getString("telefonogarante"));
                cl.setLugartrabajogarante(rs.getString("lugartrabajogarante"));
                cl.setProfesiongarante(rs.getString("profesiongarante"));
                cl.setTeloficinagarante(rs.getString("teloficinagarante"));
                cl.setAsesor(rs.getInt("asesor"));
                cl.setBanco(rs.getInt("banco"));
                cl.setCuenta(rs.getString("cuenta"));
                cl.setCasapropia(rs.getInt("casapropia"));
                cl.setAutopropio(rs.getInt("autopropio"));
                cl.setInformconf(rs.getInt("informconf"));
                cl.setSexo(rs.getInt("sexo"));
                cl.getAbogado().setCodigo(rs.getInt("abogado"));
                cl.getBarrio().setCodigo(rs.getInt("barrio"));
                lista.add(cl);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<cliente> todoSencillo() throws SQLException {
        ArrayList<cliente> lista = new ArrayList<cliente>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT clientes.codigo,clientes.nombre,"
                + "clientes.direccion,clientes.ruc,clientes.notas"
                + " FROM clientes "
                + " ORDER BY clientes.codigo ";

        System.out.println(sql);

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente cl = new cliente();
                cl.setCodigo(rs.getInt("codigo"));
                cl.setNombre(rs.getString("nombre"));
                cl.setNotas(rs.getString("notas"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setRuc(rs.getString("ruc"));
                lista.add(cl);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public boolean ActualizarRes90(cliente c) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE clientes SET res90=?"
                + "  WHERE codigo=" + c.getCodigo());
        ps.setInt(1, c.getRes90());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public cliente BuscarRes90Cliente(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        cliente pr = new cliente();
        String sql = "SELECT clientes.codigo,clientes.nombre,clientes.res90 "
                + " FROM clientes "
                + " WHERE clientes.codigo=" + id
                + " ORDER BY clientes.codigo ";
        System.out.println(sql);

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                plan pl = new plan();
                pr.setCodigo(rs.getInt("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.setRes90(rs.getInt("res90"));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return pr;
    }

    public ArrayList<cliente> todosCarrerasimple() throws SQLException {
        ArrayList<cliente> lista = new ArrayList<cliente>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT clientes.codigo,clientes.nombre,clientes.direccion,clientes.telefono,clientes.carrera,"
                + "carreras.nombre as nombrecarrera,clientes.ruc,clientes.cedula,clientes.semestre,"
                + "clientes.fechaculminacion,clientes.fechaingreso,clientes.nroregistro "
                + " FROM clientes "
                + " LEFT JOIN carreras "
                + " ON carreras.codigo=clientes.carrera "
                + " ORDER BY clientes.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                carrera ca = new carrera();
                cliente cl = new cliente();
                cl.setCarrera(ca);
                cl.setCodigo(rs.getInt("codigo"));
                cl.setNombre(rs.getString("nombre"));
                cl.setRuc(rs.getString("ruc"));
                cl.setCedula(rs.getString("cedula"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.getCarrera().setCodigo(rs.getInt("carrera"));
                cl.getCarrera().setNombre(rs.getString("nombrecarrera"));
                cl.setSemestre(rs.getInt("semestre"));
                cl.setNroregistro(rs.getString("nroregistro"));
                cl.setFechaculminacion(rs.getDate("fechaculminacion"));
                cl.setFechaingreso(rs.getDate("fechaingreso"));
                lista.add(cl);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<cliente> todosxAsesor() throws SQLException {
        ArrayList<cliente> lista = new ArrayList<cliente>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT clientes.codigo,clientes.comitente,clientes.nombre,clientes.ruc,clientes.fechanacimiento,clientes.estadocivil,clientes.conyugue,clientes.direccion,"
                + "clientes.telefono,clientes.fax,clientes.celular,clientes.mail,clientes.profesion,clientes.lugartrabajo,clientes.cargolaboral,clientes.salario,clientes.legajo,"
                + "clientes.turnolegajo,clientes.descuentos_aportes,clientes.direccionlaboral,clientes.fechaingreso,clientes.fechaingresofuncionario,clientes.telefonolaboral,clientes.faxlaboral,"
                + "clientes.maillaboral,clientes.vendedor,clientes.categoria,clientes.salarioconyugue,clientes.otrosingresos,clientes.localidad,clientes.limitecredito,clientes.credito,"
                + "clientes.notas,clientes.estado,clientes.saldo,clientes.cedulaconyugue,clientes.garante,clientes.conyuguegarante,clientes.cedulagarante,clientes.cedulaconyuguegarante,"
                + "clientes.direcciongarante,clientes.telefonogarante,clientes.lugartrabajogarante,clientes.profesiongarante,clientes.teloficinagarante,clientes.idcta,clientes.asesor,"
                + "clientes.banco,clientes.cuenta,clientes.casapropia,clientes.autopropio,clientes.informconf,clientes.sexo,clientes.abogado,clientes.barrio,"
                + "clientes.plazocredito,localidades.nombre as nombrelocalidad,giradurias.nombre as nombregiraduria,clientes.giraduria,clientes.cedula "
                + " FROM clientes "
                + " LEFT JOIN localidades "
                + " ON localidades.codigo=clientes.localidad "
                + " LEFT JOIN giradurias "
                + " ON giradurias.codigo=clientes.giraduria "
                + " WHERE clientes.estado=1 "
                + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                + " ORDER BY clientes.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente cl = new cliente();
                localidad lo = new localidad();
                giraduria gi = new giraduria();
                barrio ba = new barrio();
                cl.setBarrio(ba);
                cl.setLocalidad(lo);
                cl.setGiraduria(gi);
                
                abogado ab = new abogado();
                cl.setAbogado(ab);

                cl.setCodigo(rs.getInt("codigo"));
                cl.setComitente(rs.getInt("comitente"));
                cl.setNombre(rs.getString("nombre"));
                cl.setPlazocredito(rs.getInt("plazocredito"));
                cl.setRuc(rs.getString("ruc"));
                cl.setCedula(rs.getString("cedula"));
                cl.setFechanacimiento(rs.getDate("fechanacimiento"));
                cl.setEstadocivil(rs.getString("estadocivil"));
                cl.setConyugue(rs.getString("conyugue"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setFax(rs.getString("fax"));
                cl.setCelular(rs.getString("celular"));
                cl.setMail(rs.getString("mail"));
                cl.setProfesion(rs.getString("profesion"));
                cl.setLugartrabajo(rs.getString("lugartrabajo"));
                cl.setCargolaboral(rs.getString("cargolaboral"));
                cl.setSalario(rs.getDouble("salario"));
                cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                cl.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                cl.setDireccionlaboral(rs.getString("direccionlaboral"));
                cl.setFechaingreso(rs.getDate("fechaingreso"));
                cl.setTelefonolaboral(rs.getString("telefonolaboral"));
                cl.setFaxlaboral(rs.getString("faxlaboral"));
                cl.setMaillaboral(rs.getString("maillaboral"));
                cl.setVendedor(rs.getInt("vendedor"));
                cl.setCategoria(rs.getInt("categoria"));
                cl.setSalarioconyugue(rs.getDouble("salarioconyugue"));
                cl.setOtrosingresos(rs.getDouble("otrosingresos"));
                cl.getLocalidad().setCodigo(rs.getInt("localidad"));
                cl.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                cl.setLimitecredito(rs.getDouble("limitecredito"));
                cl.setCredito(rs.getDouble("credito"));
                cl.setNotas(rs.getString("notas"));
                cl.setEstado(rs.getInt("estado"));
                cl.setSaldo(rs.getDouble("saldo"));
                cl.setCedulaconyugue(rs.getString("cedulaconyugue"));
                cl.setGarante(rs.getString("garante"));
                cl.setConyuguegarante(rs.getString("conyuguegarante"));
                cl.setCedulaconyugue(rs.getString("cedulagarante"));
                cl.setCedulaconyuguegarante(rs.getString("cedulaconyuguegarante"));
                cl.setDirecciongarante(rs.getString("direcciongarante"));
                cl.setTelefonogarante(rs.getString("telefonogarante"));
                cl.setLugartrabajogarante(rs.getString("lugartrabajogarante"));
                cl.setProfesiongarante(rs.getString("profesiongarante"));
                cl.setTeloficinagarante(rs.getString("teloficinagarante"));
                cl.setAsesor(rs.getInt("asesor"));
                cl.setBanco(rs.getInt("banco"));
                cl.setCuenta(rs.getString("cuenta"));
                cl.setCasapropia(rs.getInt("casapropia"));
                cl.setAutopropio(rs.getInt("autopropio"));
                cl.setInformconf(rs.getInt("informconf"));
                cl.setSexo(rs.getInt("sexo"));
                cl.getAbogado().setCodigo(rs.getInt("abogado"));
                cl.getBarrio().setCodigo(rs.getInt("barrio"));
                lista.add(cl);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public cliente buscarIdCasaBolsa(int id) throws SQLException {
        cliente cl = new cliente();

        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT clientes.codigo,clientes.comitente,clientes.nombre,clientes.ruc,"
                    + "clientes.fechanacimiento,clientes.estadocivil,clientes.conyugue,clientes.direccion,"
                    + "clientes.telefono,clientes.fax,clientes.celular,clientes.mail,clientes.profesion,"
                    + "clientes.lugartrabajo,clientes.cargolaboral,clientes.salario,clientes.legajo,"
                    + "clientes.turnolegajo,clientes.descuentos_aportes,clientes.direccionlaboral,"
                    + "clientes.fechaingreso,clientes.fechaingresofuncionario,"
                    + "clientes.telefonolaboral,clientes.faxlaboral,clientes.observacion_cliente,"
                    + "clientes.maillaboral,clientes.vendedor,clientes.categoria,clientes.salarioconyugue,"
                    + "clientes.otrosingresos,clientes.localidad,clientes.limitecredito,clientes.credito,"
                    + "clientes.notas,clientes.estado,clientes.saldo,clientes.cedulaconyugue,"
                    + "clientes.garante,clientes.conyuguegarante,clientes.cedulagarante,"
                    + "clientes.cedulaconyuguegarante,"
                    + "clientes.direcciongarante,clientes.telefonogarante,clientes.lugartrabajogarante,"
                    + "clientes.profesiongarante,clientes.teloficinagarante,clientes.idcta,clientes.asesor,"
                    + "clientes.plazocredito,clientes.banco,clientes.cuenta,clientes.casapropia,"
                    + "clientes.autopropio,clientes.informconf,clientes.sexo,clientes.abogado,"
                    + "clientes.barrio,clientes.giraduria,barrios.nombre as nombrebarrio,"
                    + "localidades.nombre as nombrelocalidad,giradurias.nombre as nombregiraduria,"
                    + "clientes.cedula,clientes.codfais,clientes.pep,clientes.apoderado,"
                    + "clientes.objeto,clientes.directorio,"
                    + "clientes.facturar_a_nombre,clientes.direccionfactura,clientes.telefonofactura,"
                    + "clientes.rucfactura,clientes.imprimirtitular,clientes.perfilinterno,clientes.inversorcalificado "
                    + " FROM clientes "
                    + " LEFT JOIN localidades "
                    + " ON localidades.codigo=clientes.localidad "
                    + " LEFT JOIN barrios "
                    + " ON barrios.codigo=clientes.barrio "
                    + " LEFT JOIN giradurias "
                    + " ON giradurias.codigo=clientes.giraduria "
                    + " WHERE clientes.codigo= ? "
                    + " ORDER BY clientes.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    localidad lo = new localidad();
                    giraduria gi = new giraduria();
                    barrio ba = new barrio();
                    cl.setBarrio(ba);
                    cl.setGiraduria(gi);
                    cl.setLocalidad(lo);
                    abogado ab = new abogado();
                    cl.setAbogado(ab);

                    cl.setCodigo(rs.getInt("codigo"));
                    cl.setComitente(rs.getInt("comitente"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setPlazocredito(rs.getInt("plazocredito"));
                    cl.setRuc(rs.getString("ruc"));
                    cl.setCedula(rs.getString("cedula"));
                    cl.setFechanacimiento(rs.getDate("fechanacimiento"));
                    cl.setEstadocivil(rs.getString("estadocivil"));
                    cl.setConyugue(rs.getString("conyugue"));
                    cl.setDireccion(rs.getString("direccion"));
                    cl.setTelefono(rs.getString("telefono"));
                    cl.setFax(rs.getString("fax"));
                    cl.setCelular(rs.getString("celular"));
                    cl.setMail(rs.getString("mail"));
                    cl.setProfesion(rs.getString("profesion"));
                    cl.setLugartrabajo(rs.getString("lugartrabajo"));
                    cl.setCargolaboral(rs.getString("cargolaboral"));
                    cl.setSalario(rs.getDouble("salario"));
                    cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cl.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cl.setDireccionlaboral(rs.getString("direccionlaboral"));
                    cl.setFechaingreso(rs.getDate("fechaingreso"));
                    cl.setFechaingresofuncionario(rs.getDate("fechaingresofuncionario"));
                    cl.setLegajo(rs.getInt("legajo"));
                    cl.setDescuentos_aportes(rs.getInt("descuentos_aportes"));
                    cl.setTelefonolaboral(rs.getString("telefonolaboral"));
                    cl.setFaxlaboral(rs.getString("faxlaboral"));
                    cl.setMaillaboral(rs.getString("maillaboral"));
                    cl.setVendedor(rs.getInt("vendedor"));
                    cl.setCategoria(rs.getInt("categoria"));
                    cl.setSalarioconyugue(rs.getDouble("salarioconyugue"));
                    cl.setOtrosingresos(rs.getDouble("otrosingresos"));
                    cl.getLocalidad().setCodigo(rs.getInt("localidad"));
                    cl.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                    cl.setLimitecredito(rs.getDouble("limitecredito"));
                    cl.setCredito(rs.getDouble("credito"));
                    cl.setNotas(rs.getString("notas"));
                    cl.setEstado(rs.getInt("estado"));
                    cl.setSaldo(rs.getDouble("saldo"));
                    cl.setCedulaconyugue(rs.getString("cedulaconyugue"));
                    cl.setGarante(rs.getString("garante"));
                    cl.setConyuguegarante(rs.getString("conyuguegarante"));
                    cl.setCedulaconyugue(rs.getString("cedulagarante"));
                    cl.setCedulaconyuguegarante(rs.getString("cedulaconyuguegarante"));
                    cl.setDirecciongarante(rs.getString("direcciongarante"));
                    cl.setTelefonogarante(rs.getString("telefonogarante"));
                    cl.setLugartrabajogarante(rs.getString("lugartrabajogarante"));
                    cl.setProfesiongarante(rs.getString("profesiongarante"));
                    cl.setTeloficinagarante(rs.getString("teloficinagarante"));
                    cl.setAsesor(rs.getInt("asesor"));
                    cl.setBanco(rs.getInt("banco"));
                    cl.setCuenta(rs.getString("cuenta"));
                    cl.setCasapropia(rs.getInt("casapropia"));
                    cl.setAutopropio(rs.getInt("autopropio"));
                    cl.setInformconf(rs.getInt("informconf"));
                    cl.setTurno(rs.getString("turnolegajo"));
                    cl.setSexo(rs.getInt("sexo"));
                    cl.setCodfais(rs.getInt("codfais"));
                    cl.setPep(rs.getString("pep"));
                    cl.setObjeto(rs.getString("objeto"));
                    cl.setApoderado(rs.getString("apoderado"));
                    cl.setDirectorio(rs.getString("directorio"));
                    cl.getAbogado().setCodigo(rs.getInt("abogado"));
                    cl.getBarrio().setCodigo(rs.getInt("barrio"));
                    cl.getBarrio().setNombre(rs.getString("nombrebarrio"));
                    cl.setFacturar_a_nombre(rs.getString("facturar_a_nombre"));
                    cl.setDireccionfactura(rs.getString("direccionfactura"));
                    cl.setTelefonofactura(rs.getString("telefonofactura"));
                    cl.setRucfactura(rs.getString("rucfactura"));
                    cl.setImprimirtitular(rs.getInt("imprimirtitular"));
                    cl.setObservacion_cliente(rs.getString("observacion_cliente"));
                    cl.setPerfilinterno(rs.getString("perfilinterno"));
                    cl.setInversorcalificado(rs.getString("inversorcalificado"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return cl;
    }

    
        public boolean actualizarSituacionCliente(cliente c) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE clientes SET "
                + "abogado=?, situacion=?, motivosituacion=?"
                + " WHERE codigo=" + c.getCodigo());
        ps.setInt(1, c.getAbogado().getCodigo());
        ps.setInt(2, c.getSituacion().getCodigo());
        ps.setString(3, c.getMotivosituacion());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

        
    public cliente buscarIdSituacion(int id) throws SQLException {
        cliente cl = new cliente();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT clientes.codigo,clientes.nombre,"
                    + "clientes.cedula,clientes.abogado,clientes.situacion,"
                    + "clientes.motivosituacion,abogados.nombre as nombreabogado,"
                    + "situacion.nombre as nombresituacion "
                    + " FROM clientes "
                    + " LEFT JOIN abogados "
                    + " ON abogados.codigo=clientes.abogado "
                    + " LEFT JOIN situacion "
                    + " ON situacion.codigo=clientes.situacion "
                    + " WHERE clientes.codigo=? "
                    + " ORDER BY clientes.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    abogado ab = new abogado();
                    cl.setAbogado(ab);
                    situacion sit = new situacion();
                    cl.setSituacion(sit);
                    cl.setCodigo(rs.getInt("codigo"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setCedula(rs.getString("cedula"));
                    cl.getAbogado().setCodigo(rs.getInt("abogado"));
                    cl.getAbogado().setNombre(rs.getString("nombreabogado"));
                    cl.getSituacion().setCodigo(rs.getInt("situacion"));
                    cl.getSituacion().setNombre(rs.getString("nombresituacion"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();

        return cl;
    }
        
        
        
}

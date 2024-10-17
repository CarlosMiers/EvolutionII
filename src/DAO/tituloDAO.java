/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.emisor;
import Modelo.instrumento;
import Modelo.moneda;
import Modelo.pais;
import Modelo.rubro_emisor;
import Modelo.titulo;
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
 * @author Usuario
 */
public class tituloDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<titulo> todos() throws SQLException {
        ArrayList<titulo> lista = new ArrayList<titulo>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT titulos.codigo,titulos.nombre,titulos.empresa,"
                + "titulos.tipo,titulos.pais,titulos.resbvpasa,"
                + "titulos.fechabvpasa,titulos.rescnv,titulos.fechacnv,"
                + "titulos.nomalias,titulos.moneda,titulos.base,"
                + "titulos.pagointeres,titulos.referencia,titulos.fecha,"
                + "titulos.tasa,titulos.monto_emision,titulos.corte_minimo,"
                + "titulos.fechaemision,titulos.vencimiento,"
                + "titulos.instrumento,titulos.fechacotizacion,"
                + "titulos.preciocotizacion,titulos.nominal,"
                + "titulos.nombreprograma,titulos.negociable,"
                + "emisores.nombre AS nombreemisor,"
                + "paises.nombre AS nombrepais,"
                + "monedas.nombre AS nombremoneda,"
                + "instrumentos.nombre AS nombreinstrumento "
                + "FROM titulos "
                + "LEFT JOIN emisores "
                + "ON emisores.codigo=titulos.empresa "
                + "LEFT JOIN paises "
                + "ON paises.codigo=titulos.pais "
                + "LEFT JOIN monedas "
                + "ON monedas.codigo=titulos.moneda "
                + "LEFT JOIN instrumentos "
                + "ON instrumentos.codigo=titulos.instrumento "
                + "ORDER BY titulos.codigo";

        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                titulo t = new titulo();
                emisor em = new emisor();
                pais pais = new pais();
                moneda mn = new moneda();
                instrumento inst = new instrumento();

                t.setPais(pais);
                t.setEmpresa(em);;
                t.setMoneda(mn);
                t.setInstrumento(inst);

                t.setCodigo(rs.getInt("codigo"));
                t.setNombre(rs.getString("nombre"));
                t.setNomalias(rs.getString("nomalias"));
                t.setTipo(rs.getInt("tipo"));
                t.setResbvpasa(rs.getString("resbvpasa"));
                t.setFechabvpasa(rs.getDate("fechabvpasa"));
                t.setRescnv(rs.getString("rescnv"));
                t.setFechacnv(rs.getDate("fechacnv"));
                t.setNombreprograma(rs.getString("nombreprograma"));
                t.setBase(rs.getInt("base"));
                t.setPagointeres(rs.getString("pagointeres"));
                t.setReferencia(rs.getString("referencia"));
                t.setFecha(rs.getDate("fecha"));
                t.setTasa(rs.getBigDecimal("tasa"));
                t.setMonto_emision(rs.getBigDecimal("monto_emision"));
                t.setFechaemision(rs.getDate("fechaemision"));
                t.setVencimiento(rs.getDate("vencimiento"));
                t.setFechacotizacion(rs.getDate("fechacotizacion"));
                t.setPreciocotizacion(rs.getDouble("preciocotizacion"));
                t.setNominal(rs.getBigDecimal("nominal"));
                t.setNegociable(rs.getInt("negociable"));
                t.getMoneda().setCodigo(rs.getInt("moneda"));
                t.getMoneda().setNombre(rs.getString("nombremoneda"));
                t.getPais().setCodigo(rs.getInt("pais"));
                t.getPais().setNombre(rs.getString("nombrepais"));
                t.getEmpresa().setCodigo(rs.getInt("empresa"));
                t.getEmpresa().setNombre(rs.getString("nombreemisor"));
                t.getInstrumento().setCodigo(rs.getInt("instrumento"));
                t.getInstrumento().setNombre(rs.getString("nombreinstrumento"));

                lista.add(t);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public titulo buscarId(int id) throws SQLException {
        titulo t = new titulo();
        emisor em = new emisor();
        pais pais = new pais();
        moneda mn = new moneda();
        instrumento inst = new instrumento();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT titulos.codigo,titulos.nombre,titulos.empresa,"
                    + "titulos.tipo,titulos.pais,titulos.resbvpasa,"
                    + "titulos.fechabvpasa,titulos.rescnv,titulos.fechacnv,"
                    + "titulos.nomalias,titulos.moneda,titulos.base,"
                    + "titulos.pagointeres,titulos.referencia,titulos.fecha,"
                    + "titulos.tasa,titulos.monto_emision,titulos.corte_minimo,"
                    + "titulos.fechaemision,titulos.vencimiento,"
                    + "titulos.instrumento,titulos.fechacotizacion,"
                    + "titulos.preciocotizacion,titulos.nominal,titulos.cupones,"
                    + "titulos.nombreprograma,titulos.negociable,titulos.estado,"
                    + "emisores.nombre AS nombreemisor,"
                    + "paises.nombre AS nombrepais,"
                    + "monedas.nombre AS nombremoneda,"
                    + "instrumentos.nombre AS nombreinstrumento "
                    + "FROM titulos "
                    + "LEFT JOIN emisores "
                    + "ON emisores.codigo=titulos.empresa "
                    + "LEFT JOIN paises "
                    + "ON paises.codigo=titulos.pais "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=titulos.moneda "
                    + "LEFT JOIN instrumentos "
                    + "ON instrumentos.codigo=titulos.instrumento "
                    + "WHERE titulos.codigo=? "
                    + "ORDER BY titulos.codigo";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    t.setPais(pais);
                    t.setEmpresa(em);;
                    t.setMoneda(mn);
                    t.setInstrumento(inst);

                    t.setCodigo(rs.getInt("codigo"));
                    t.setNombre(rs.getString("nombre"));
                    t.setNomalias(rs.getString("nomalias"));
                    t.setTipo(rs.getInt("tipo"));
                    t.setResbvpasa(rs.getString("resbvpasa"));
                    t.setFechabvpasa(rs.getDate("fechabvpasa"));
                    t.setRescnv(rs.getString("rescnv"));
                    t.setFechacnv(rs.getDate("fechacnv"));
                    t.setNombreprograma(rs.getString("nombreprograma"));
                    t.setBase(rs.getInt("base"));
                    t.setPagointeres(rs.getString("pagointeres"));
                    t.setReferencia(rs.getString("referencia"));
                    t.setFecha(rs.getDate("fecha"));
                    t.setEstado(rs.getInt("estado"));
                    t.setTasa(rs.getBigDecimal("tasa"));
                    t.setMonto_emision(rs.getBigDecimal("monto_emision"));
                    t.setFechaemision(rs.getDate("fechaemision"));
                    t.setVencimiento(rs.getDate("vencimiento"));
                    t.setFechacotizacion(rs.getDate("fechacotizacion"));
                    t.setPreciocotizacion(rs.getDouble("preciocotizacion"));
                    t.setNominal(rs.getBigDecimal("nominal"));
                    t.setCorte_minimo(rs.getBigDecimal("corte_minimo"));
                    t.setNegociable(rs.getInt("negociable"));
                    t.setCupones(rs.getInt("cupones"));

                    t.getMoneda().setCodigo(rs.getInt("moneda"));
                    t.getMoneda().setNombre(rs.getString("nombremoneda"));

                    t.getPais().setCodigo(rs.getInt("pais"));
                    t.getPais().setNombre(rs.getString("nombrepais"));

                    t.getEmpresa().setCodigo(rs.getInt("empresa"));
                    t.getEmpresa().setNombre(rs.getString("nombreemisor"));

                    t.getInstrumento().setCodigo(rs.getInt("instrumento"));
                    t.getInstrumento().setNombre(rs.getString("nombreinstrumento"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return t;
    }

    public titulo insertarTitulos(titulo ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO titulos "
                + "(nombre,nomalias,nombreprograma,"
                + "rescnv,fechacnv,resbvpasa,fechabvpasa,"
                + "empresa,pais,moneda,instrumento,"
                + "tipo,negociable,estado)"
                + " VALUES (?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?)");

        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getNomalias());
        ps.setString(3, ca.getNombreprograma());
        ps.setString(4, ca.getRescnv());
        ps.setDate(5, ca.getFechacnv());
        ps.setString(6, ca.getResbvpasa());
        ps.setDate(7, ca.getFechabvpasa());
        ps.setInt(8, ca.getEmpresa().getCodigo());
        ps.setInt(9, ca.getPais().getCodigo());
        ps.setInt(10, ca.getMoneda().getCodigo());
        ps.setInt(11, ca.getInstrumento().getCodigo());
        ps.setInt(12, ca.getTipo());
        ps.setInt(13, ca.getNegociable());
        ps.setInt(14, ca.getEstado());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarTitulos(titulo ca) throws SQLException {
        int rowsUpdated = 0;
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE titulos "
                + "SET nombre=?,nomalias=?,"
                + "nombreprograma=?,rescnv=?,"
                + "fechacnv=?,resbvpasa=?,"
                + "fechabvpasa=?,empresa=?,"
                + "pais=?,moneda=?,"
                + "instrumento=?,tipo=?,"
                + "negociable=?,estado=?"
                + " WHERE codigo=" + ca.getCodigo());
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getNomalias());
        ps.setString(3, ca.getNombreprograma());
        ps.setString(4, ca.getRescnv());
        ps.setDate(5, ca.getFechacnv());
        ps.setString(6, ca.getResbvpasa());
        ps.setDate(7, ca.getFechabvpasa());
        ps.setInt(8, ca.getEmpresa().getCodigo());
        ps.setInt(9, ca.getPais().getCodigo());
        ps.setInt(10, ca.getMoneda().getCodigo());
        ps.setInt(11, ca.getInstrumento().getCodigo());
        ps.setInt(12, ca.getTipo());
        ps.setInt(13, ca.getNegociable());
        ps.setInt(14, ca.getEstado());

        try {
            rowsUpdated = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarTitulo(int cod) throws SQLException {
        int rowsUpdated = 0;
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM titulos WHERE codigo=?");
        ps.setInt(1, cod);
        try {
            rowsUpdated = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public titulo buscarIdOperacion(int id, int ntipo) throws SQLException {
        titulo t = new titulo();
        emisor em = new emisor();
        pais pais = new pais();
        moneda mn = new moneda();
        instrumento inst = new instrumento();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT titulos.codigo,titulos.nombre,titulos.empresa,"
                    + "titulos.tipo,titulos.pais,titulos.resbvpasa,"
                    + "titulos.fechabvpasa,titulos.rescnv,titulos.fechacnv,"
                    + "titulos.nomalias,titulos.moneda,titulos.base,"
                    + "titulos.pagointeres,titulos.referencia,titulos.fecha,"
                    + "titulos.tasa,titulos.monto_emision,titulos.corte_minimo,"
                    + "titulos.fechaemision,titulos.vencimiento,"
                    + "titulos.instrumento,titulos.fechacotizacion,"
                    + "titulos.preciocotizacion,titulos.nominal,titulos.cupones,"
                    + "titulos.nombreprograma,titulos.negociable,titulos.estado,"
                    + "emisores.nombre AS nombreemisor,"
                    + "paises.nombre AS nombrepais,"
                    + "monedas.nombre AS nombremoneda,"
                    + "instrumentos.nombre AS nombreinstrumento "
                    + "FROM titulos "
                    + "LEFT JOIN emisores "
                    + "ON emisores.codigo=titulos.empresa "
                    + "LEFT JOIN paises "
                    + "ON paises.codigo=titulos.pais "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=titulos.moneda "
                    + "LEFT JOIN instrumentos "
                    + "ON instrumentos.codigo=titulos.instrumento "
                    + "WHERE titulos.codigo=? "
                    + " AND titulos.tipo=? "
                    + " ORDER BY titulos.codigo";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setInt(2, ntipo);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    t.setPais(pais);
                    t.setEmpresa(em);;
                    t.setMoneda(mn);
                    t.setInstrumento(inst);

                    t.setCodigo(rs.getInt("codigo"));
                    t.setNombre(rs.getString("nombre"));
                    t.setNomalias(rs.getString("nomalias"));
                    t.setTipo(rs.getInt("tipo"));
                    t.setResbvpasa(rs.getString("resbvpasa"));
                    t.setFechabvpasa(rs.getDate("fechabvpasa"));
                    t.setRescnv(rs.getString("rescnv"));
                    t.setFechacnv(rs.getDate("fechacnv"));
                    t.setNombreprograma(rs.getString("nombreprograma"));
                    t.setBase(rs.getInt("base"));
                    t.setPagointeres(rs.getString("pagointeres"));
                    t.setReferencia(rs.getString("referencia"));
                    t.setFecha(rs.getDate("fecha"));
                    t.setEstado(rs.getInt("estado"));
                    t.setTasa(rs.getBigDecimal("tasa"));
                    t.setMonto_emision(rs.getBigDecimal("monto_emision"));
                    t.setFechaemision(rs.getDate("fechaemision"));
                    t.setVencimiento(rs.getDate("vencimiento"));
                    t.setFechacotizacion(rs.getDate("fechacotizacion"));
                    t.setPreciocotizacion(rs.getDouble("preciocotizacion"));
                    t.setNominal(rs.getBigDecimal("nominal"));
                    t.setCorte_minimo(rs.getBigDecimal("corte_minimo"));
                    t.setNegociable(rs.getInt("negociable"));
                    t.setCupones(rs.getInt("cupones"));

                    t.getMoneda().setCodigo(rs.getInt("moneda"));
                    t.getMoneda().setNombre(rs.getString("nombremoneda"));

                    t.getPais().setCodigo(rs.getInt("pais"));
                    t.getPais().setNombre(rs.getString("nombrepais"));

                    t.getEmpresa().setCodigo(rs.getInt("empresa"));
                    t.getEmpresa().setNombre(rs.getString("nombreemisor"));

                    t.getInstrumento().setCodigo(rs.getInt("instrumento"));
                    t.getInstrumento().setNombre(rs.getString("nombreinstrumento"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return t;
    }

    public ArrayList<titulo> todoOperaciones(int ntipo) throws SQLException {
        ArrayList<titulo> lista = new ArrayList<titulo>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT titulos.codigo,titulos.nombre,titulos.empresa,"
                + "titulos.tipo,titulos.pais,titulos.resbvpasa,"
                + "titulos.fechabvpasa,titulos.rescnv,titulos.fechacnv,"
                + "titulos.nomalias,titulos.moneda,titulos.base,"
                + "titulos.pagointeres,titulos.referencia,titulos.fecha,"
                + "titulos.tasa,titulos.monto_emision,titulos.corte_minimo,"
                + "titulos.fechaemision,titulos.vencimiento,"
                + "titulos.instrumento,titulos.fechacotizacion,"
                + "titulos.preciocotizacion,titulos.nominal,titulos.cupones,"
                + "titulos.nombreprograma,titulos.negociable,"
                + "emisores.nombre AS nombreemisor,"
                + "paises.nombre AS nombrepais,"
                + "monedas.nombre AS nombremoneda,"
                + "instrumentos.nombre AS nombreinstrumento "
                + "FROM titulos "
                + "LEFT JOIN emisores "
                + "ON emisores.codigo=titulos.empresa "
                + "LEFT JOIN paises "
                + "ON paises.codigo=titulos.pais "
                + "LEFT JOIN monedas "
                + "ON monedas.codigo=titulos.moneda "
                + "LEFT JOIN instrumentos "
                + "ON instrumentos.codigo=titulos.instrumento "
                + " WHERE titulos.tipo=? "
                + " AND titulos.estado=1 "
                + "ORDER BY titulos.codigo";

        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ntipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                titulo t = new titulo();
                emisor em = new emisor();
                pais pais = new pais();
                moneda mn = new moneda();
                instrumento inst = new instrumento();

                t.setPais(pais);
                t.setEmpresa(em);;
                t.setMoneda(mn);
                t.setInstrumento(inst);

                t.setCodigo(rs.getInt("codigo"));
                t.setNombre(rs.getString("nombre"));
                t.setNomalias(rs.getString("nomalias"));
                t.setTipo(rs.getInt("tipo"));
                t.setResbvpasa(rs.getString("resbvpasa"));
                t.setFechabvpasa(rs.getDate("fechabvpasa"));
                t.setRescnv(rs.getString("rescnv"));
                t.setFechacnv(rs.getDate("fechacnv"));
                t.setNombreprograma(rs.getString("nombreprograma"));
                t.setBase(rs.getInt("base"));
                t.setCupones(rs.getInt("cupones"));
                t.setPagointeres(rs.getString("pagointeres"));
                t.setReferencia(rs.getString("referencia"));
                t.setFecha(rs.getDate("fecha"));
                t.setTasa(rs.getBigDecimal("tasa"));
                t.setMonto_emision(rs.getBigDecimal("monto_emision"));
                t.setFechaemision(rs.getDate("fechaemision"));
                t.setVencimiento(rs.getDate("vencimiento"));
                t.setFechacotizacion(rs.getDate("fechacotizacion"));
                t.setPreciocotizacion(rs.getDouble("preciocotizacion"));
                t.setNominal(rs.getBigDecimal("nominal"));
                t.setNegociable(rs.getInt("negociable"));
                t.getMoneda().setCodigo(rs.getInt("moneda"));
                t.getMoneda().setNombre(rs.getString("nombremoneda"));
                t.getPais().setCodigo(rs.getInt("pais"));
                t.getPais().setNombre(rs.getString("nombrepais"));
                t.getEmpresa().setCodigo(rs.getInt("empresa"));
                t.getEmpresa().setNombre(rs.getString("nombreemisor"));
                t.getInstrumento().setCodigo(rs.getInt("instrumento"));
                t.getInstrumento().setNombre(rs.getString("nombreinstrumento"));

                lista.add(t);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public titulo actualizarTitulosRentaFija(titulo ca, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();

        int id = 0;
        boolean guardado = false;
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE titulos "
                + "SET monto_emision=?,corte_minimo=?,"
                + "fechaemision=?,vencimiento=?,tasa=?,base=?,"
                + "pagointeres=?,cupones=?,nominal=? "
                + " WHERE codigo=" + ca.getCodigo());

        ps.setBigDecimal(1, ca.getMonto_emision());
        ps.setBigDecimal(2, ca.getCorte_minimo());
        ps.setDate(3, ca.getFechaemision());
        ps.setDate(4, ca.getVencimiento());
        ps.setBigDecimal(5, ca.getTasa());
        ps.setInt(6, ca.getBase());
        ps.setString(7, ca.getPagointeres());
        ps.setInt(8, ca.getCupones());
        ps.setBigDecimal(9, ca.getNominal());
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            id = ca.getCodigo();
            guardado = guardarDetalleCupones(id, detalle, con);
        }
        st.close();
        ps.close();
        cnn.close();
        return ca;
    }

    public boolean guardarDetalleCupones(int id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        System.out.println("codigo Título "+id);
        PreparedStatement psdetalle = null;

        psdetalle = st.getConnection().prepareStatement("DELETE FROM detalle_cupones_titulos WHERE titulo=?");
        psdetalle.setInt(1, id);
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

                    String sql = "INSERT INTO detalle_cupones_titulos("
                            + "titulo,"
                            + "numerocupon,"
                            + "fechavencimiento,"
                            + "plazo,"
                            + "fechainicio,"
                            + "estadocupon"
                            + ") "
                            + "values(?,?,?,?,?,?)";

                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("titulo").getAsString());
                        ps.setString(2, obj.get("numerocupon").getAsString());
                        ps.setString(3, obj.get("fechavencimiento").getAsString());
                        ps.setString(4, obj.get("plazo").getAsString());
                        ps.setString(5, obj.get("fechainicio").getAsString());
                        ps.setString(6, obj.get("estadocupon").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("DETALLE CUPONES--->" + ex.getLocalizedMessage());
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




    public titulo buscarString(String id) throws SQLException {
        titulo t = new titulo();
        emisor em = new emisor();
        pais pais = new pais();
        moneda mn = new moneda();
        instrumento inst = new instrumento();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT titulos.codigo,titulos.nombre,titulos.empresa,"
                    + "titulos.tipo,titulos.pais,titulos.resbvpasa,"
                    + "titulos.fechabvpasa,titulos.rescnv,titulos.fechacnv,"
                    + "titulos.nomalias,titulos.moneda,titulos.base,"
                    + "titulos.pagointeres,titulos.referencia,titulos.fecha,"
                    + "titulos.tasa,titulos.monto_emision,titulos.corte_minimo,"
                    + "titulos.fechaemision,titulos.vencimiento,"
                    + "titulos.instrumento,titulos.fechacotizacion,"
                    + "titulos.preciocotizacion,titulos.nominal,titulos.cupones,"
                    + "titulos.nombreprograma,titulos.negociable,titulos.estado,"
                    + "emisores.nombre AS nombreemisor,"
                    +"emisores.nomalias AS isin,"
                    + "paises.nombre AS nombrepais,"
                    + "monedas.nombre AS nombremoneda,"
                    + "instrumentos.nombre AS nombreinstrumento "
                    + "FROM titulos "
                    + "LEFT JOIN emisores "
                    + "ON emisores.codigo=titulos.empresa "
                    + "LEFT JOIN paises "
                    + "ON paises.codigo=titulos.pais "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=titulos.moneda "
                    + "LEFT JOIN instrumentos "
                    + "ON instrumentos.codigo=titulos.instrumento "
                    + "WHERE titulos.nomalias=? "
                    + "ORDER BY titulos.codigo";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    t.setPais(pais);
                    t.setEmpresa(em);;
                    t.setMoneda(mn);
                    t.setInstrumento(inst);

                    t.setCodigo(rs.getInt("codigo"));
                    t.setNombre(rs.getString("nombre"));
                    t.setNomalias(rs.getString("nomalias"));
                    t.setTipo(rs.getInt("tipo"));
                    t.setResbvpasa(rs.getString("resbvpasa"));
                    t.setFechabvpasa(rs.getDate("fechabvpasa"));
                    t.setRescnv(rs.getString("rescnv"));
                    t.setFechacnv(rs.getDate("fechacnv"));
                    t.setBase(rs.getInt("base"));
                    t.setPagointeres(rs.getString("pagointeres"));
                    t.setReferencia(rs.getString("referencia"));
                    t.setFecha(rs.getDate("fecha"));
                    t.setEstado(rs.getInt("estado"));
                    t.setTasa(rs.getBigDecimal("tasa"));
                    t.setMonto_emision(rs.getBigDecimal("monto_emision"));
                    t.setFechaemision(rs.getDate("fechaemision"));
                    t.setVencimiento(rs.getDate("vencimiento"));
                    t.setFechacotizacion(rs.getDate("fechacotizacion"));
                    t.setPreciocotizacion(rs.getDouble("preciocotizacion"));
                    t.setNominal(rs.getBigDecimal("nominal"));
                    t.setCorte_minimo(rs.getBigDecimal("corte_minimo"));
                    t.setNombreprograma(rs.getString("nombreprograma"));
                    t.setNegociable(rs.getInt("negociable"));
                    t.setCupones(rs.getInt("cupones"));

                    t.getMoneda().setCodigo(rs.getInt("moneda"));
                    t.getMoneda().setNombre(rs.getString("nombremoneda"));

                    t.getPais().setCodigo(rs.getInt("pais"));
                    t.getPais().setNombre(rs.getString("nombrepais"));

                    t.getEmpresa().setCodigo(rs.getInt("empresa"));
                    t.getEmpresa().setNombre(rs.getString("nombreemisor"));
                    t.getEmpresa().setNomalias(rs.getString("isin"));
                    t.getInstrumento().setCodigo(rs.getInt("instrumento"));
                    t.getInstrumento().setNombre(rs.getString("nombreinstrumento"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return t;
    }



}

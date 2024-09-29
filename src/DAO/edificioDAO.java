/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.cobrador;
import Modelo.edificio;
import Modelo.inmueble;
import Modelo.moneda;
import Modelo.propietario;
import Modelo.tipo_inmueble;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class edificioDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<edificio> Todos() throws SQLException {
        ArrayList<edificio> lista = new ArrayList<edificio>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT  edificios.idunidad,edificios.ctactral,edificios.ubicacion,edificios.medande,"
                    + "edificios.nir,edificios.nis,edificios.medcorpo,edificios.subcuentacorpo,edificios.telunid,edificios.tipunid,edificios.alquiler,edificios.depgtia,edificios.garaje, "
                    + "edificios.nrogaraj,edificios.expensa,edificios.fondoreserva,edificios.llave,edificios.diapago,edificios.indemniz,edificios.comision,edificios.multa,"
                    + "edificios.ocasionales,edificios.prorexp,edificios.ivauni,edificios.agua,edificios.enerelectrica,edificios.recolebasura,edificios.inicon,edificios.vencon,edificios.fecocup, "
                    + "edificios.ambientes,edificios.observaciones,edificios.unidad,edificios.nrodoc,edificios.tipdoc,edificios.proptel,edificios.scta,edificios.aire, "
                    + "edificios.destino1,edificios.destino2,edificios.dist1,edificios.dist2,edificios.dist3,edificios.dist4,edificios.mmalqui,edificios.vgarantia,"
                    + "edificios.finiexp,edificios.ffinexp,edificios.coddepgara,edificios.controlc,edificios.codcomi,edificios.nuevo,edificios.usuario,edificios.fecalta,edificios.fecmodi, "
                    + "edificios.ndocgara,edificios.tipdocga,edificios.contrato,edificios.contvenc,edificios.contrat,edificios.estado,edificios.inmueble, "
                    + "inmuebles.nomedif AS nombreinmueble,propietarios.codpro AS codpropietario,propietarios.cedula AS cedulaprop, CONCAT(propietarios.nombre,', ', propietarios.apellido) AS nombrepropietario,edificios.tipinmueble,tipo_inmueble.nombre AS nombretipoinmueble, "
                    + "edificios.cobrador,cobradores.nombre AS nombrecobrador,edificios.comproventa,edificios.superftotal,edificios.superfedif,edificios.moneda,monedas.nombre AS nombremoneda, clientes.codigo AS clientecod, clientes.ruc AS clienteruc,clientes.nombre AS clientenombre "
                    + " FROM edificios"
                    + " LEFT JOIN inmuebles"
                    + " ON inmuebles.idinmueble=edificios.inmueble"
                    + " LEFT JOIN tipo_inmueble"
                    + " ON tipo_inmueble.codigo=edificios.tipinmueble"
                    + " LEFT JOIN monedas"
                    + " ON monedas.codigo=edificios.moneda"
                    + " LEFT JOIN cobradores"
                    + " ON cobradores.codigo=edificios.cobrador"
                    + " LEFT JOIN propietarios"
                    + " ON propietarios.codpro=inmuebles.codpro "
                    + " LEFT JOIN clientes"
                    + " ON clientes.cedula=edificios.nrodoc "
                    + " ORDER BY inmuebles.idinmueble,edificios.idunidad ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cobrador cobrador = new cobrador();
                    inmueble inmueble = new inmueble();
                    tipo_inmueble tipinmueble = new tipo_inmueble();
                    moneda mon = new moneda();
                    cliente cli = new cliente();
                    edificio ed = new edificio();
                    propietario pro = new propietario();

                    ed.setCobrador(cobrador);
                    ed.setInmueble(inmueble);
                    ed.setTipinmueble(tipinmueble);
                    ed.setMoneda(mon);
                    ed.setCliente(cli);
                    ed.setPropietario(pro);

                    ed.setIdunidad(rs.getInt("idunidad"));
                    ed.getInmueble().setIdinmueble(rs.getInt("inmueble"));
                    ed.getInmueble().setNomedif(rs.getString("nombreinmueble"));
                    ed.getTipinmueble().setCodigo(rs.getInt("tipinmueble"));
                    ed.getTipinmueble().setNombre(rs.getString("nombretipoinmueble"));
                    ed.getMoneda().setCodigo(rs.getInt("moneda"));
                    ed.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ed.setCtactral(rs.getString("ctactral"));
                    ed.setUbicacion(rs.getString("ubicacion"));
                    ed.setMedande(rs.getString("medande"));
                    ed.setNir(rs.getString("nir"));
                    ed.setNis(rs.getString("nis"));
                    ed.setMedcorpo(rs.getString("medcorpo"));
                    ed.setSubcuentacorpo(rs.getString("subcuentacorpo"));
                    ed.setTelunid(rs.getString("telunid"));
                    ed.setAlquiler(rs.getBigDecimal("alquiler"));
                    ed.setDepgtia(rs.getBigDecimal("depgtia"));
                    ed.setGaraje(rs.getBigDecimal("garaje"));
                    ed.setNrogaraj(rs.getString("nrogaraj"));
                    ed.setExpensa(rs.getBigDecimal("expensa"));
                    ed.setFondoreserva(rs.getBigDecimal("fondoreserva"));
                    ed.setLlave(rs.getBigDecimal("llave"));
                    ed.setDiapago(rs.getInt("diapago"));
                    ed.setIndemniz(rs.getBigDecimal("indemniz"));
                    ed.setComision(rs.getBigDecimal("comision"));
                    ed.setMulta(rs.getBigDecimal("multa"));
                    ed.setOcasionales(rs.getBigDecimal("ocasionales"));
                    ed.setProrexp(rs.getString("prorexp"));
                    ed.setIvauni(rs.getString("ivauni"));
                    ed.setAgua(rs.getInt("agua"));
                    ed.setEnerelectrica(rs.getInt("enerelectrica"));
                    ed.setRecolebasura(rs.getInt("recolebasura"));
                    ed.setInicon(rs.getDate("inicon"));
                    ed.setVencon(rs.getDate("vencon"));
                    ed.setFecocup(rs.getDate("fecocup"));
                    ed.setAmbientes(rs.getString("ambientes"));
                    ed.setObservaciones(rs.getString("observaciones"));
                    ed.setAire(rs.getBigDecimal("aire"));
                    ed.setUnidad(rs.getInt("unidad"));
                    ed.setNrodoc(rs.getString("nrodoc"));
                    ed.setTipdoc(rs.getInt("tipdoc"));
                    ed.setUbicacion(rs.getString("ubicacion"));
                    ed.setProptel(rs.getInt("proptel"));
                    ed.setScta(rs.getString("scta"));
                    ed.setComision(rs.getBigDecimal("comision"));
                    ed.setTipunid(rs.getInt("tipunid")); //1 = LOCAL COMERCIAL 2=VIVIENDA
                    ed.setDestino1(rs.getInt("destino1"));
                    ed.setDestino2(rs.getInt("destino2"));
                    ed.setDist1(rs.getString("dist1"));
                    ed.setDist2(rs.getString("dist2"));
                    ed.setDist3(rs.getString("dist3"));
                    ed.setDist4(rs.getString("dist4"));
                    ed.getCobrador().setCodigo(rs.getInt("cobrador"));
                    ed.getCobrador().setNombre(rs.getString("nombrecobrador"));
                    ed.setMmalqui(rs.getBigDecimal("mmalqui"));
                    ed.setVgarantia(rs.getBigDecimal("vgarantia"));
                    ed.setFiniexp(rs.getDate("finiexp"));
                    ed.setFfinexp(rs.getDate("ffinexp"));
                    ed.setCoddepgara(rs.getInt("coddepgara"));
                    ed.setControlc(rs.getInt("controlc"));
                    ed.setCodcomi(rs.getInt("codcomi"));
                    ed.setNuevo(rs.getString("nuevo"));
                    ed.setUsuario(rs.getString("usuario"));
                    ed.setFecalta(rs.getDate("fecalta"));
                    ed.setFecmodi(rs.getDate("fecmodi"));
                    ed.setTipdocga(rs.getInt("tipdocga"));
                    ed.setContrato(rs.getString("contrato"));
                    ed.setContvenc(rs.getString("contvenc"));
                    ed.setContrat(rs.getBigDecimal("contrat"));
                    ed.setEstado(rs.getInt("estado"));
                    ed.setComproventa(rs.getInt("comproventa"));
                    ed.setSuperftotal(rs.getString("superftotal"));
                    ed.setSuperfedif(rs.getString("superfedif"));
                    ed.getPropietario().setCedula(rs.getString("cedulaprop"));
                    ed.setNombrepropie(rs.getString("nombrepropietario"));
                    ed.getCliente().setCodigo(rs.getInt("clientecod"));
                    ed.getCliente().setRuc(rs.getString("clienteruc"));
                    ed.getCliente().setNombre(rs.getString("clientenombre"));

                    // System.out.println("Unidad Get " + ed. getIdunidad());
                    lista.add(ed);
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

    public edificio MostrarxEstado(int nestado) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        edificio ed = new edificio();
       
        try {

            String sql = "SELECT  edificios.idunidad,edificios.ctactral,edificios.ubicacion,edificios.medande,"
                    + "edificios.nir,edificios.nis,edificios.medcorpo,edificios.subcuentacorpo,edificios.telunid,edificios.tipunid,edificios.alquiler,edificios.depgtia,edificios.garaje, "
                    + "edificios.nrogaraj,edificios.expensa,edificios.fondoreserva,edificios.llave,edificios.diapago,edificios.indemniz,edificios.comision,edificios.multa,"
                    + "edificios.ocasionales,edificios.prorexp,edificios.ivauni,edificios.agua,edificios.enerelectrica,edificios.recolebasura,edificios.inicon,edificios.vencon,edificios.fecocup,"
                    + "edificios.ambientes,edificios.observaciones,edificios.unidad,edificios.nrodoc,edificios.tipdoc,edificios.proptel,edificios.scta,edificios.aire,"
                    + "edificios.destino1,edificios.destino2,edificios.dist1,edificios.dist2,edificios.dist3,edificios.dist4,edificios.mmalqui,edificios.vgarantia,"
                    + "edificios.finiexp,edificios.ffinexp,edificios.coddepgara,edificios.controlc,edificios.codcomi,edificios.nuevo,edificios.usuario,edificios.fecalta,edificios.fecmodi,"
                    + "edificios.ndocgara,edificios.tipdocga,edificios.contrato,edificios.contvenc,edificios.contrat,edificios.estado,edificios.inmueble,"
                    + "inmuebles.nomedif AS nombreinmueble,propietarios.codpro AS codpropietario,propietarios.cedula AS cedulaprop, CONCAT(propietarios.nombre,', ', propietarios.apellido) AS nombrepropietario,edificios.tipinmueble,tipo_inmueble.nombre AS nombretipoinmueble,"
                    + "edificios.cobrador,cobradores.nombre AS nombrecobrador,edificios.comproventa,edificios.superftotal,edificios.superfedif,edificios.moneda,monedas.nombre AS nombremoneda, clientes.codigo AS clientecod, clientes.ruc AS clienteruc,clientes.nombre AS clientenombre "
                    + " FROM edificios"
                    + " LEFT JOIN inmuebles"
                    + " ON inmuebles.idinmueble=edificios.inmueble"
                    + " LEFT JOIN tipo_inmueble"
                    + " ON tipo_inmueble.codigo=edificios.tipinmueble"
                    + " LEFT JOIN monedas"
                    + " ON monedas.codigo=edificios.moneda"
                    + " LEFT JOIN cobradores"
                    + " ON cobradores.codigo=edificios.cobrador"
                    + " LEFT JOIN propietarios"
                    + " ON propietarios.codpro=inmuebles.codpro "
                    + " LEFT JOIN clientes"
                    + " ON clientes.ruc=edificios.nrodoc "
                    + " WHERE edificios.estado=?"
                    + " ORDER BY edificios.idunidad ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, nestado);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cobrador cobrador = new cobrador();
                    inmueble inmueble = new inmueble();
                    tipo_inmueble tipinmueble = new tipo_inmueble();
                    moneda mon = new moneda();
                    cliente cli = new cliente();
                    propietario pro = new propietario();

                    ed.setCobrador(cobrador);
                    ed.setInmueble(inmueble);
                    ed.setTipinmueble(tipinmueble);
                    ed.setMoneda(mon);
                    ed.setCliente(cli);
                    ed.setPropietario(pro);

                    ed.setIdunidad(rs.getInt("idunidad"));
                    ed.getInmueble().setIdinmueble(rs.getInt("inmueble"));
                    ed.getInmueble().setNomedif(rs.getString("nombreinmueble"));
                    ed.getTipinmueble().setCodigo(rs.getInt("tipinmueble"));
                    ed.getTipinmueble().setNombre(rs.getString("nombretipoinmueble"));
                    ed.getMoneda().setCodigo(rs.getInt("moneda"));
                    ed.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ed.setCtactral(rs.getString("ctactral"));
                    ed.setUbicacion(rs.getString("ubicacion"));
                    ed.setMedande(rs.getString("medande"));
                    ed.setNir(rs.getString("nir"));
                    ed.setNis(rs.getString("nis"));
                    ed.setMedcorpo(rs.getString("medcorpo"));
                    ed.setSubcuentacorpo(rs.getString("subcuentacorpo"));
                    ed.setTelunid(rs.getString("telunid"));
                    ed.setAlquiler(rs.getBigDecimal("alquiler"));
                    ed.setDepgtia(rs.getBigDecimal("depgtia"));
                    ed.setGaraje(rs.getBigDecimal("garaje"));
                    ed.setNrogaraj(rs.getString("nrogaraj"));
                    ed.setExpensa(rs.getBigDecimal("expensa"));
                    ed.setFondoreserva(rs.getBigDecimal("fondoreserva"));
                    ed.setLlave(rs.getBigDecimal("llave"));
                    ed.setDiapago(rs.getInt("diapago"));
                    ed.setIndemniz(rs.getBigDecimal("indemniz"));
                    ed.setComision(rs.getBigDecimal("comision"));
                    ed.setMulta(rs.getBigDecimal("multa"));
                    ed.setOcasionales(rs.getBigDecimal("ocasionales"));
                    ed.setProrexp(rs.getString("prorexp"));
                    ed.setIvauni(rs.getString("ivauni"));
                    ed.setAgua(rs.getInt("agua"));
                    ed.setEnerelectrica(rs.getInt("enerelectrica"));
                    ed.setRecolebasura(rs.getInt("recolebasura"));
                    ed.setInicon(rs.getDate("inicon"));
                    ed.setVencon(rs.getDate("vencon"));
                    ed.setFecocup(rs.getDate("fecocup"));
                    ed.setAmbientes(rs.getString("ambientes"));
                    ed.setObservaciones(rs.getString("observaciones"));
                    ed.setAire(rs.getBigDecimal("aire"));
                    ed.setUnidad(rs.getInt("unidad"));
                    ed.setNrodoc(rs.getString("nrodoc"));
                    ed.setTipdoc(rs.getInt("tipdoc"));
                    ed.setUbicacion(rs.getString("ubicacion"));
                    ed.setProptel(rs.getInt("proptel"));
                    ed.setScta(rs.getString("scta"));
                    ed.setComision(rs.getBigDecimal("comision"));
                    ed.setTipunid(rs.getInt("tipunid")); //1 = LOCAL COMERCIAL 2=VIVIENDA
                    ed.setDestino1(rs.getInt("destino1"));
                    ed.setDestino2(rs.getInt("destino2"));
                    ed.setDist1(rs.getString("dist1"));
                    ed.setDist2(rs.getString("dist2"));
                    ed.setDist3(rs.getString("dist3"));
                    ed.setDist4(rs.getString("dist4"));
                    ed.getCobrador().setCodigo(rs.getInt("cobrador"));
                    ed.getCobrador().setNombre(rs.getString("nombrecobrador"));
                    ed.setMmalqui(rs.getBigDecimal("mmalqui"));
                    ed.setVgarantia(rs.getBigDecimal("vgarantia"));
                    ed.setFiniexp(rs.getDate("finiexp"));
                    ed.setFfinexp(rs.getDate("ffinexp"));
                    ed.setCoddepgara(rs.getInt("coddepgara"));
                    ed.setControlc(rs.getInt("controlc"));
                    ed.setCodcomi(rs.getInt("codcomi"));
                    ed.setNuevo(rs.getString("nuevo"));
                    ed.setUsuario(rs.getString("usuario"));
                    ed.setFecalta(rs.getDate("fecalta"));
                    ed.setFecmodi(rs.getDate("fecmodi"));
                    ed.setTipdocga(rs.getInt("tipdocga"));
                    ed.setContrato(rs.getString("contrato"));
                    ed.setContvenc(rs.getString("contvenc"));
                    ed.setContrat(rs.getBigDecimal("contrat"));
                    ed.setEstado(rs.getInt("estado"));
                    ed.setComproventa(rs.getInt("comproventa"));
                    ed.setSuperftotal(rs.getString("superftotal"));
                    ed.setSuperfedif(rs.getString("superfedif"));
                    ed.getPropietario().setCedula(rs.getString("cedulaprop"));
                    ed.setNombrepropie(rs.getString("nombrepropietario"));
                    ed.getCliente().setCodigo(rs.getInt("clientecod"));
                    ed.getCliente().setRuc(rs.getString("clienteruc"));
                    ed.getCliente().setNombre(rs.getString("clientenombre"));

                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return ed;
    }

    public edificio insertaredificio(edificio ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ed = null;

        ed = st.getConnection().prepareStatement("INSERT INTO edificios (inmueble,tipinmueble,moneda,ctactral,ubicacion,medande,nir,nis,medcorpo,subcuentacorpo,"
                + "telunid,tipunid,alquiler,depgtia,garaje,nrogaraj,expensa,fondoreserva,llave,diapago,indemniz,comision,multa,ocasionales,"
                + "prorexp,ivauni,inicon,vencon,fecocup,agua,enerelectrica,recolebasura,ambientes,observaciones,comproventa,superftotal,superfedif,nombrepropie) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ed.setInt(1, ocr.getInmueble().getIdinmueble());
        ed.setInt(2, ocr.getTipinmueble().getCodigo());
        ed.setInt(3, ocr.getMoneda().getCodigo());
        ed.setString(4, ocr.getCtactral());
        ed.setString(5, ocr.getUbicacion());
        ed.setString(6, ocr.getMedande());
        ed.setString(7, ocr.getNir());
        ed.setString(8, ocr.getNis());
        ed.setString(9, ocr.getMedcorpo());
        ed.setString(10, ocr.getSubcuentacorpo());
        ed.setString(11, ocr.getTelunid());
        ed.setInt(12, ocr.getTipunid());
        ed.setBigDecimal(13, ocr.getAlquiler());
        ed.setBigDecimal(14, ocr.getDepgtia());
        ed.setBigDecimal(15, ocr.getGaraje());
        ed.setString(16, ocr.getNrogaraj());
        ed.setBigDecimal(17, ocr.getExpensa());
        ed.setBigDecimal(18, ocr.getFondoreserva());
        ed.setBigDecimal(19, ocr.getLlave());
        ed.setInt(20, ocr.getDiapago());
        ed.setBigDecimal(21, ocr.getIndemniz());
        ed.setBigDecimal(22, ocr.getComision());
        ed.setBigDecimal(23, ocr.getMulta());
        ed.setBigDecimal(24, ocr.getOcasionales());
        ed.setString(25, ocr.getProrexp());
        ed.setString(26, ocr.getIvauni());
        ed.setDate(27, ocr.getInicon());
        ed.setDate(28, ocr.getVencon());
        ed.setDate(29, ocr.getFecocup());
        ed.setInt(30, ocr.getAgua());
        ed.setInt(31, ocr.getEnerelectrica());
        ed.setInt(32, ocr.getRecolebasura());
        ed.setString(33, ocr.getAmbientes());
        ed.setString(34, ocr.getObservaciones());
        ed.setInt(35, ocr.getComproventa());
        ed.setString(36, ocr.getSuperftotal());
        ed.setString(37, ocr.getSuperfedif());
        ed.setString(38, ocr.getNombrepropie());

        int rowsUpdated = ed.executeUpdate();
        ResultSet keyset = ed.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
        }
        ocr.setIdunidad(id);
        st.close();
        ed.close();
        conn.close();
        return ocr;
    }

    public boolean actualizarEdificio(edificio ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ed = null;

        ed = st.getConnection().prepareStatement("UPDATE edificios SET inmueble=?,tipinmueble=?,moneda=?,ctactral=?,ubicacion=?,medande=?,nir=?,nis=?,medcorpo=?,subcuentacorpo=?,telunid=?,tipunid=?,alquiler=?,depgtia=?,garaje=?,nrogaraj=?,expensa=?,fondoreserva=?,llave=?,diapago=?,indemniz=?,comision=?,multa=?,ocasionales=?,prorexp=?,ivauni=?,inicon=?,vencon=?,fecocup=?,agua=?,enerelectrica=?,recolebasura=?,ambientes=?,observaciones=?,comproventa=?,superftotal=?,superfedif=?, nombrepropie=? WHERE idunidad=" + ocr.getIdunidad());
        ed.setInt(1, ocr.getInmueble().getIdinmueble());
        ed.setInt(2, ocr.getTipinmueble().getCodigo());
        ed.setInt(3, ocr.getMoneda().getCodigo());
        ed.setString(4, ocr.getCtactral());
        ed.setString(5, ocr.getUbicacion());
        ed.setString(6, ocr.getMedande());
        ed.setString(7, ocr.getNir());
        ed.setString(8, ocr.getNis());
        ed.setString(9, ocr.getMedcorpo());
        ed.setString(10, ocr.getSubcuentacorpo());
        ed.setString(11, ocr.getTelunid());
        ed.setInt(12, ocr.getTipunid());
        ed.setBigDecimal(13, ocr.getAlquiler());
        ed.setBigDecimal(14, ocr.getDepgtia());
        ed.setBigDecimal(15, ocr.getGaraje());
        ed.setString(16, ocr.getNrogaraj());
        ed.setBigDecimal(17, ocr.getExpensa());
        ed.setBigDecimal(18, ocr.getFondoreserva());
        ed.setBigDecimal(19, ocr.getLlave());
        ed.setInt(20, ocr.getDiapago());
        ed.setBigDecimal(21, ocr.getIndemniz());
        ed.setBigDecimal(22, ocr.getComision());
        ed.setBigDecimal(23, ocr.getMulta());
        ed.setBigDecimal(24, ocr.getOcasionales());
        ed.setString(25, ocr.getProrexp());
        ed.setString(26, ocr.getIvauni());
        ed.setDate(27, ocr.getInicon());
        ed.setDate(28, ocr.getVencon());
        ed.setDate(29, ocr.getFecocup());
        ed.setInt(30, ocr.getAgua());
        ed.setInt(31, ocr.getEnerelectrica());
        ed.setInt(32, ocr.getRecolebasura());
        ed.setString(33, ocr.getAmbientes());
        ed.setString(34, ocr.getObservaciones());
        ed.setInt(35, ocr.getComproventa());
        ed.setString(36, ocr.getSuperftotal());
        ed.setString(37, ocr.getSuperfedif());
        ed.setString(38, ocr.getNombrepropie());
        int rowsUpdated = ed.executeUpdate();
        st.close();
        ed.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean borrarEdificios(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM edificios WHERE idunidad=?");
        ps.setInt(1, id);
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

    public edificio buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        edificio ed = new edificio();
        try {

            String sql = "SELECT  edificios.idunidad,edificios.ctactral,edificios.ubicacion,edificios.medande,"
                    + "edificios.nir,edificios.nis,edificios.medcorpo,edificios.subcuentacorpo,edificios.telunid,edificios.tipunid,edificios.alquiler,edificios.depgtia,edificios.garaje, "
                    + "edificios.nrogaraj,edificios.expensa,edificios.fondoreserva,edificios.llave,edificios.diapago,edificios.indemniz,edificios.comision,edificios.multa,"
                    + "edificios.ocasionales,edificios.prorexp,edificios.ivauni,edificios.agua,edificios.enerelectrica,edificios.recolebasura,edificios.inicon,edificios.vencon,edificios.fecocup,"
                    + "edificios.ambientes,edificios.observaciones,edificios.unidad,edificios.nrodoc,edificios.tipdoc,edificios.proptel,edificios.scta,edificios.aire,"
                    + "edificios.destino1,edificios.destino2,edificios.dist1,edificios.dist2,edificios.dist3,edificios.dist4,edificios.mmalqui,edificios.vgarantia,"
                    + "edificios.finiexp,edificios.ffinexp,edificios.coddepgara,edificios.controlc,edificios.codcomi,edificios.nuevo,edificios.usuario,edificios.fecalta,edificios.fecmodi,"
                    + "edificios.ndocgara,edificios.tipdocga,edificios.contrato,edificios.contvenc,edificios.contrat,edificios.estado,edificios.inmueble,"
                    + "inmuebles.nomedif AS nombreinmueble,propietarios.codpro AS codpropietario,propietarios.cedula AS cedulaprop, CONCAT(propietarios.nombre,', ', propietarios.apellido) AS nombrepropietario,edificios.tipinmueble,tipo_inmueble.nombre AS nombretipoinmueble,"
                    + "edificios.cobrador,cobradores.nombre AS nombrecobrador,edificios.comproventa,edificios.superftotal,edificios.superfedif,edificios.moneda,monedas.nombre AS nombremoneda, clientes.codigo AS clientecod, clientes.ruc AS clienteruc,clientes.nombre AS clientenombre "
                    + " FROM edificios "
                    + " LEFT JOIN inmuebles "
                    + " ON inmuebles.idinmueble=edificios.inmueble "
                    + " LEFT JOIN tipo_inmueble"
                    + " ON tipo_inmueble.codigo=edificios.tipinmueble"
                    + " LEFT JOIN monedas"
                    + " ON monedas.codigo=edificios.moneda"
                    + " LEFT JOIN cobradores"
                    + " ON cobradores.codigo=edificios.cobrador "
                    + " LEFT JOIN propietarios"
                    + " ON propietarios.codpro=inmuebles.codpro "
                    + " LEFT JOIN clientes"
                    + " ON clientes.ruc=edificios.nrodoc "
                    + " WHERE edificios.idunidad = ? "
                    + " ORDER BY edificios.idunidad ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cobrador cobrador = new cobrador();
                    inmueble inmueble = new inmueble();
                    tipo_inmueble tipinmueble = new tipo_inmueble();
                    moneda mon = new moneda();
                    cliente cli = new cliente();
                    propietario pro = new propietario();

                    ed.setCobrador(cobrador);
                    ed.setInmueble(inmueble);
                    ed.setTipinmueble(tipinmueble);
                    ed.setMoneda(mon);
                    ed.setCliente(cli);
                    ed.setPropietario(pro);

                    ed.setIdunidad(rs.getInt("idunidad"));
                    ed.getInmueble().setIdinmueble(rs.getInt("inmueble"));
                    ed.getInmueble().setNomedif(rs.getString("nombreinmueble"));
                    ed.getTipinmueble().setCodigo(rs.getInt("tipinmueble"));
                    ed.getTipinmueble().setNombre(rs.getString("nombretipoinmueble"));
                    ed.getMoneda().setCodigo(rs.getInt("moneda"));
                    ed.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ed.setCtactral(rs.getString("ctactral"));
                    ed.setUbicacion(rs.getString("ubicacion"));
                    ed.setMedande(rs.getString("medande"));
                    ed.setNir(rs.getString("nir"));
                    ed.setNis(rs.getString("nis"));
                    ed.setMedcorpo(rs.getString("medcorpo"));
                    ed.setSubcuentacorpo(rs.getString("subcuentacorpo"));
                    ed.setTelunid(rs.getString("telunid"));
                    ed.setAlquiler(rs.getBigDecimal("alquiler"));
                    ed.setDepgtia(rs.getBigDecimal("depgtia"));
                    ed.setGaraje(rs.getBigDecimal("garaje"));
                    ed.setNrogaraj(rs.getString("nrogaraj"));
                    ed.setExpensa(rs.getBigDecimal("expensa"));
                    ed.setFondoreserva(rs.getBigDecimal("fondoreserva"));
                    ed.setLlave(rs.getBigDecimal("llave"));
                    ed.setDiapago(rs.getInt("diapago"));
                    ed.setIndemniz(rs.getBigDecimal("indemniz"));
                    ed.setComision(rs.getBigDecimal("comision"));
                    ed.setMulta(rs.getBigDecimal("multa"));
                    ed.setOcasionales(rs.getBigDecimal("ocasionales"));
                    ed.setProrexp(rs.getString("prorexp"));
                    ed.setIvauni(rs.getString("ivauni"));
                    ed.setAgua(rs.getInt("agua"));
                    ed.setEnerelectrica(rs.getInt("enerelectrica"));
                    ed.setRecolebasura(rs.getInt("recolebasura"));
                    ed.setInicon(rs.getDate("inicon"));
                    ed.setVencon(rs.getDate("vencon"));
                    ed.setFecocup(rs.getDate("fecocup"));
                    ed.setAmbientes(rs.getString("ambientes"));
                    ed.setObservaciones(rs.getString("observaciones"));
                    ed.setAire(rs.getBigDecimal("aire"));
                    ed.setUnidad(rs.getInt("unidad"));
                    ed.setNrodoc(rs.getString("nrodoc"));
                    ed.setTipdoc(rs.getInt("tipdoc"));
                    ed.setUbicacion(rs.getString("ubicacion"));
                    ed.setProptel(rs.getInt("proptel"));
                    ed.setScta(rs.getString("scta"));
                    ed.setComision(rs.getBigDecimal("comision"));
                    ed.setTipunid(rs.getInt("tipunid")); //1 = LOCAL COMERCIAL 2=VIVIENDA
                    ed.setDestino1(rs.getInt("destino1"));
                    ed.setDestino2(rs.getInt("destino2"));
                    ed.setDist1(rs.getString("dist1"));
                    ed.setDist2(rs.getString("dist2"));
                    ed.setDist3(rs.getString("dist3"));
                    ed.setDist4(rs.getString("dist4"));
                    ed.getCobrador().setCodigo(rs.getInt("cobrador"));
                    ed.getCobrador().setNombre(rs.getString("nombrecobrador"));
                    ed.setMmalqui(rs.getBigDecimal("mmalqui"));
                    ed.setVgarantia(rs.getBigDecimal("vgarantia"));
                    ed.setFiniexp(rs.getDate("finiexp"));
                    ed.setFfinexp(rs.getDate("ffinexp"));
                    ed.setCoddepgara(rs.getInt("coddepgara"));
                    ed.setControlc(rs.getInt("controlc"));
                    ed.setCodcomi(rs.getInt("codcomi"));
                    ed.setNuevo(rs.getString("nuevo"));
                    ed.setUsuario(rs.getString("usuario"));
                    ed.setFecalta(rs.getDate("fecalta"));
                    ed.setFecmodi(rs.getDate("fecmodi"));
                    ed.setTipdocga(rs.getInt("tipdocga"));
                    ed.setContrato(rs.getString("contrato"));
                    ed.setContvenc(rs.getString("contvenc"));
                    ed.setContrat(rs.getBigDecimal("contrat"));
                    ed.setEstado(rs.getInt("estado"));
                    ed.setComproventa(rs.getInt("comproventa"));
                    ed.setSuperftotal(rs.getString("superftotal"));
                    ed.setSuperfedif(rs.getString("superfedif"));
                    ed.getPropietario().setCedula(rs.getString("cedulaprop"));
                    ed.setNombrepropie(rs.getString("nombrepropietario"));
                    ed.getCliente().setCodigo(rs.getInt("clientecod"));
                    ed.getCliente().setRuc(rs.getString("clienteruc"));
                    ed.getCliente().setNombre(rs.getString("clientenombre"));

                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return ed;
    }

    public ArrayList<edificio> buscarxInmueble(int idinmueble) throws SQLException {
        ArrayList<edificio> lista = new ArrayList<edificio>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT  edificios.idunidad,edificios.ctactral,edificios.ubicacion,edificios.medande,"
                    + "edificios.nir,edificios.nis,edificios.medcorpo,edificios.subcuentacorpo,edificios.telunid,edificios.tipunid,edificios.alquiler,edificios.depgtia,edificios.garaje, "
                    + "edificios.nrogaraj,edificios.expensa,edificios.fondoreserva,edificios.llave,edificios.diapago,edificios.indemniz,edificios.comision,edificios.multa,"
                    + "edificios.ocasionales,edificios.prorexp,edificios.ivauni,edificios.agua,edificios.enerelectrica,edificios.recolebasura,edificios.inicon,edificios.vencon,edificios.fecocup,"
                    + "edificios.ambientes,edificios.observaciones,edificios.unidad,edificios.nrodoc,edificios.tipdoc,edificios.proptel,edificios.scta,edificios.aire,"
                    + "edificios.destino1,edificios.destino2,edificios.dist1,edificios.dist2,edificios.dist3,edificios.dist4,edificios.mmalqui,edificios.vgarantia,"
                    + "edificios.finiexp,edificios.ffinexp,edificios.coddepgara,edificios.controlc,edificios.codcomi,edificios.nuevo,edificios.usuario,edificios.fecalta,edificios.fecmodi,"
                    + "edificios.ndocgara,edificios.tipdocga,edificios.contrato,edificios.contvenc,edificios.contrat,edificios.estado,edificios.inmueble,"
                    + "inmuebles.nomedif AS nombreinmueble,propietarios.codpro AS codpropietario,propietarios.cedula AS cedulaprop, CONCAT(propietarios.nombre,', ', propietarios.apellido) AS nombrepropietario,edificios.tipinmueble,tipo_inmueble.nombre AS nombretipoinmueble,"
                    + "edificios.cobrador,cobradores.nombre AS nombrecobrador,edificios.comproventa,edificios.superftotal,edificios.superfedif,edificios.moneda,monedas.nombre AS nombremoneda, clientes.codigo AS clientecod, clientes.ruc AS clienteruc,clientes.nombre AS clientenombre "
                    + " FROM edificios "
                    + " LEFT JOIN inmuebles "
                    + " ON inmuebles.idinmueble=edificios.inmueble "
                    + " LEFT JOIN tipo_inmueble"
                    + " ON tipo_inmueble.codigo=edificios.tipinmueble"
                    + " LEFT JOIN monedas"
                    + " ON monedas.codigo=edificios.moneda"
                    + " LEFT JOIN cobradores"
                    + " ON cobradores.codigo=edificios.cobrador "
                    + " LEFT JOIN propietarios"
                    + " ON propietarios.codpro=inmuebles.codpro "
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=inmuebles.codloca "
                    + " WHERE edificios.inmueble = ? "
                    + " ORDER BY edificios.idunidad ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, idinmueble);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    edificio ed = new edificio();
                    cobrador cobrador = new cobrador();
                    inmueble inmueble = new inmueble();
                    tipo_inmueble tipinmueble = new tipo_inmueble();
                    moneda mon = new moneda();
                    cliente cli = new cliente();
                    propietario pro = new propietario();

                    ed.setCobrador(cobrador);
                    ed.setInmueble(inmueble);
                    ed.setTipinmueble(tipinmueble);
                    ed.setMoneda(mon);
                    ed.setCliente(cli);
                    ed.setPropietario(pro);

                    ed.setIdunidad(rs.getInt("idunidad"));
                    ed.getInmueble().setIdinmueble(rs.getInt("inmueble"));
                    ed.getInmueble().setNomedif(rs.getString("nombreinmueble"));
                    ed.getTipinmueble().setCodigo(rs.getInt("tipinmueble"));
                    ed.getTipinmueble().setNombre(rs.getString("nombretipoinmueble"));
                    ed.getMoneda().setCodigo(rs.getInt("moneda"));
                    ed.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ed.setCtactral(rs.getString("ctactral"));
                    ed.setUbicacion(rs.getString("ubicacion"));
                    ed.setMedande(rs.getString("medande"));
                    ed.setNir(rs.getString("nir"));
                    ed.setNis(rs.getString("nis"));
                    ed.setMedcorpo(rs.getString("medcorpo"));
                    ed.setSubcuentacorpo(rs.getString("subcuentacorpo"));
                    ed.setTelunid(rs.getString("telunid"));
                    ed.setAlquiler(rs.getBigDecimal("alquiler"));
                    ed.setDepgtia(rs.getBigDecimal("depgtia"));
                    ed.setGaraje(rs.getBigDecimal("garaje"));
                    ed.setNrogaraj(rs.getString("nrogaraj"));
                    ed.setExpensa(rs.getBigDecimal("expensa"));
                    ed.setFondoreserva(rs.getBigDecimal("fondoreserva"));
                    ed.setLlave(rs.getBigDecimal("llave"));
                    ed.setDiapago(rs.getInt("diapago"));
                    ed.setIndemniz(rs.getBigDecimal("indemniz"));
                    ed.setComision(rs.getBigDecimal("comision"));
                    ed.setMulta(rs.getBigDecimal("multa"));
                    ed.setOcasionales(rs.getBigDecimal("ocasionales"));
                    ed.setProrexp(rs.getString("prorexp"));
                    ed.setIvauni(rs.getString("ivauni"));
                    ed.setAgua(rs.getInt("agua"));
                    ed.setEnerelectrica(rs.getInt("enerelectrica"));
                    ed.setRecolebasura(rs.getInt("recolebasura"));
                    ed.setInicon(rs.getDate("inicon"));
                    ed.setVencon(rs.getDate("vencon"));
                    ed.setFecocup(rs.getDate("fecocup"));
                    ed.setAmbientes(rs.getString("ambientes"));
                    ed.setObservaciones(rs.getString("observaciones"));
                    ed.setAire(rs.getBigDecimal("aire"));
                    ed.setUnidad(rs.getInt("unidad"));
                    ed.setNrodoc(rs.getString("nrodoc"));
                    ed.setTipdoc(rs.getInt("tipdoc"));
                    ed.setUbicacion(rs.getString("ubicacion"));
                    ed.setProptel(rs.getInt("proptel"));
                    ed.setScta(rs.getString("scta"));
                    ed.setComision(rs.getBigDecimal("comision"));
                    ed.setTipunid(rs.getInt("tipunid")); //1 = LOCAL COMERCIAL 2=VIVIENDA
                    ed.setDestino1(rs.getInt("destino1"));
                    ed.setDestino2(rs.getInt("destino2"));
                    ed.setDist1(rs.getString("dist1"));
                    ed.setDist2(rs.getString("dist2"));
                    ed.setDist3(rs.getString("dist3"));
                    ed.setDist4(rs.getString("dist4"));
                    ed.getCobrador().setCodigo(rs.getInt("cobrador"));
                    ed.getCobrador().setNombre(rs.getString("nombrecobrador"));
                    ed.setMmalqui(rs.getBigDecimal("mmalqui"));
                    ed.setVgarantia(rs.getBigDecimal("vgarantia"));
                    ed.setFiniexp(rs.getDate("finiexp"));
                    ed.setFfinexp(rs.getDate("ffinexp"));
                    ed.setCoddepgara(rs.getInt("coddepgara"));
                    ed.setControlc(rs.getInt("controlc"));
                    ed.setCodcomi(rs.getInt("codcomi"));
                    ed.setNuevo(rs.getString("nuevo"));
                    ed.setUsuario(rs.getString("usuario"));
                    ed.setFecalta(rs.getDate("fecalta"));
                    ed.setFecmodi(rs.getDate("fecmodi"));
                    ed.setTipdocga(rs.getInt("tipdocga"));
                    ed.setContrato(rs.getString("contrato"));
                    ed.setContvenc(rs.getString("contvenc"));
                    ed.setContrat(rs.getBigDecimal("contrat"));
                    ed.setEstado(rs.getInt("estado"));
                    ed.setComproventa(rs.getInt("comproventa"));
                    ed.setSuperftotal(rs.getString("superftotal"));
                    ed.setSuperfedif(rs.getString("superfedif"));
                    ed.getPropietario().setCedula(rs.getString("cedulaprop"));
                    ed.setNombrepropie(rs.getString("nombrepropietario"));
                    ed.getCliente().setCodigo(rs.getInt("clientecod"));
                    ed.getCliente().setRuc(rs.getString("clienteruc"));
                    ed.getCliente().setNombre(rs.getString("clientenombre"));
                    lista.add(ed);
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

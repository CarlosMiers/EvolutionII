/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.acciones;
import Modelo.emisor;
import Modelo.moneda;
import Modelo.precierre_operacion;
import Modelo.titulo;
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
public class precierre_operacionDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<precierre_operacion> MostrarxFechaRentaFija(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<precierre_operacion> lista = new ArrayList<precierre_operacion>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT creferencia,precierre_operacion.numero,negociado,"
                    + "precierre_operacion.fecha,validohasta,fechacierre,"
                    + "precierre_operacion.fechaemision,"
                    + "precierre_operacion.vencimiento,comprador,"
                    + "vendedor,"
                    + "(SELECT nombre FROM clientes WHERE precierre_operacion.comprador=clientes.codigo) nombreclientecomprador, "
                    + "(SELECT nombre FROM clientes WHERE precierre_operacion.vendedor=clientes.codigo)  nombreclientevendedor ,"
                    + "asesorcompra,asesorventa,"
                    + "(SELECT nombre FROM vendedores WHERE precierre_operacion.asesorcompra=vendedores.codigo) nombreasesorcomprador, "
                    + "(SELECT nombre FROM vendedores WHERE precierre_operacion.asesorventa=vendedores.codigo)  nombreasesorvendedor,"
                    + "precierre_operacion.operacion,tipooperacion,"
                    + "precierre_operacion.mercado,precierre_operacion.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,titulos.nominal AS nnominal,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "precierre_operacion.tasa,plazo,tipoplazo,numerobolsa,"
                    + "precierre_operacion.base,precierre_operacion.cortes,"
                    + "precierre_operacion.preciocorte,precierre_operacion.periodopago"
                    + " FROM precierre_operacion "
                    + " LEFT JOIN monedas "
                    + " ON precierre_operacion.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON precierre_operacion.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON precierre_operacion.titulo=titulos.codigo "
                    + " WHERE precierre_operacion.fecha between ? AND ? "
                    + " AND operacion=1 ORDER BY numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    precierre_operacion p = new precierre_operacion();
                    moneda m = new moneda();
                    titulo t = new titulo();
                    emisor e = new emisor();

                    p.setCreferencia(rs.getString("creferencia"));
                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(rs.getInt("comprador"));
                    p.setNombrecomprador(rs.getString("nombreclientecomprador"));
                    p.setVendedor(rs.getInt("vendedor"));
                    p.setNombrevendedor(rs.getString("nombreclientevendedor"));
                    p.setAsesorcompra(rs.getInt("asesorcompra"));
                    p.setAsesorventa(rs.getInt("asesorventa"));
                    p.setNombre_asesor_compra(rs.getString("nombreasesorcomprador"));
                    p.setNombre_asesor_venta(rs.getString("nombreasesorvendedor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(m);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(e);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(t);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNominal(rs.getBigDecimal("nnominal"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setBase(rs.getInt("base"));
                    p.setCortes(rs.getInt("cortes"));
                    p.setPreciocorte(rs.getBigDecimal("preciocorte"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    System.out.println("--> CLIENTE VENDEDOR " + rs.getString("nombreclientevendedor"));

                    lista.add(p);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    ////MERCADO RENTA FIJA
    public precierre_operacion BuscarxOperacion(Double id) throws SQLException {
        precierre_operacion p = new precierre_operacion();
        moneda m = new moneda();
        titulo t = new titulo();
        emisor e = new emisor();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT creferencia,precierre_operacion.numero,negociado,"
                    + "precierre_operacion.fecha,validohasta,fechacierre,"
                    + "precierre_operacion.fechaemision,"
                    + "precierre_operacion.vencimiento,comprador,precierre_operacion.cupones,"
                    + "vendedor,precierre_operacion.referenciaventa,precierre_operacion.idcarteravendida,"
                    + "(SELECT nombre FROM clientes WHERE precierre_operacion.comprador=clientes.codigo) nombreclientecomprador, "
                    + "(SELECT nombre FROM clientes WHERE precierre_operacion.vendedor=clientes.codigo) nombreclientevendedor ,"
                    + "asesorcompra,asesorventa,"
                    + "(SELECT nombre FROM vendedores WHERE precierre_operacion.asesorcompra=vendedores.codigo) nombreasesorcomprador, "
                    + "(SELECT nombre FROM vendedores WHERE precierre_operacion.asesorventa=vendedores.codigo)  nombreasesorvendedor,"
                    + "precierre_operacion.operacion,tipooperacion,"
                    + "precierre_operacion.mercado,precierre_operacion.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,titulos.nomalias,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "precierre_operacion.tasa,plazo,tipoplazo,numerobolsa,"
                    + "precierre_operacion.base,precierre_operacion.cortes,"
                    + "precierre_operacion.preciocorte,precierre_operacion.periodopago,"
                    + "(SELECT nombre FROM bancos_plaza WHERE precierre_operacion.bancopago=bancos_plaza.codigo) nombrebanco,"
                    + "precierre_operacion.bancopago,precierre_operacion.cuentapago,"
                    + "precierre_operacion.arancelcompra,precierre_operacion.arancelventa,"
                    + "precierre_operacion.comcomprador,precierre_operacion.comvendedor "
                    + " FROM precierre_operacion "
                    + " LEFT JOIN monedas "
                    + " ON precierre_operacion.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON precierre_operacion.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON precierre_operacion.titulo=titulos.codigo "
                    + " WHERE precierre_operacion.numero= ? "
                    + " AND operacion=1 ORDER BY numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {

                    p.setCreferencia(rs.getString("creferencia"));
                    p.setReferenciaventa(rs.getString("referenciaventa"));
                    p.setIdcarteravendida(rs.getString("idcarteravendida"));
                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(rs.getInt("comprador"));
                    p.setNombrecomprador(rs.getString("nombreclientecomprador"));
                    p.setVendedor(rs.getInt("vendedor"));
                    p.setNombrevendedor(rs.getString("nombreclientevendedor"));
                    p.setAsesorcompra(rs.getInt("asesorcompra"));
                    p.setAsesorventa(rs.getInt("asesorventa"));
                    p.setNombre_asesor_compra(rs.getString("nombreasesorcomprador"));
                    p.setNombre_asesor_venta(rs.getString("nombreasesorvendedor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(m);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(e);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(t);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("nomalias"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setBase(rs.getInt("base"));
                    p.setCortes(rs.getInt("cortes"));
                    p.setCupones(rs.getInt("cupones"));
                    p.setPreciocorte(rs.getBigDecimal("preciocorte"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setBancopago(rs.getInt("bancopago"));
                    p.setNombrebanco(rs.getString("nombrebanco"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setArancelventa(rs.getInt("arancelventa"));
                    p.setArancelcompra(rs.getInt("arancelcompra"));
                    p.setComcomprador(rs.getBigDecimal("comcomprador"));
                    p.setComvendedor(rs.getBigDecimal("comvendedor"));

                    if (rs.getString("nombrebanco") == null) {
                        p.setBancopago(0);
                        p.setNombrebanco("");
                        p.setCuentapago("");
                    }

                    System.out.println("CLIENTE VENDEDOR---> " + rs.getString("nombreclientevendedor"));

                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return p;
    }

    ///MERCADO ACCIONARIO
    public precierre_operacion BuscarxOperacionAcciones(Double id) throws SQLException {
        precierre_operacion p = new precierre_operacion();
        moneda m = new moneda();
        titulo t = new titulo();
        emisor e = new emisor();
        acciones acc = new acciones();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT creferencia,precierre_operacion.numero,negociado,"
                    + "precierre_operacion.fecha,validohasta,fechacierre,"
                    + "comprador,vendedor,"
                    + "(SELECT nombre FROM clientes WHERE precierre_operacion.comprador=clientes.codigo) nombreclientecomprador, "
                    + "(SELECT nombre FROM clientes WHERE precierre_operacion.vendedor=clientes.codigo) nombreclientevendedor ,"
                    + "asesorcompra,asesorventa,"
                    + "(SELECT nombre FROM vendedores WHERE precierre_operacion.asesorcompra=vendedores.codigo) nombreasesorcomprador, "
                    + "(SELECT nombre FROM vendedores WHERE precierre_operacion.asesorventa=vendedores.codigo)  nombreasesorvendedor,"
                    + "precierre_operacion.operacion,tipooperacion,"
                    + "precierre_operacion.mercado,precierre_operacion.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,titulos.nomalias,"
                    + "cantidad,precio,valor_inversion,"
                    + "numerobolsa,"
                    + "(SELECT nombre FROM bancos_plaza WHERE precierre_operacion.bancopago=bancos_plaza.codigo) nombrebanco,"
                    + "precierre_operacion.bancopago,precierre_operacion.cuentapago,"
                    + "precierre_operacion.arancelcompra,precierre_operacion.arancelventa,"
                    + "precierre_operacion.comcomprador,precierre_operacion.comvendedor,"
                    + "precierre_operacion.tipoaccion, acciones.nombre AS nombreaccion "
                    + " FROM precierre_operacion "
                    + " LEFT JOIN monedas "
                    + " ON precierre_operacion.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON precierre_operacion.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON precierre_operacion.titulo=titulos.codigo "
                    + " LEFT JOIN acciones "
                    + " ON precierre_operacion.tipoaccion=acciones.codigo "
                    + " WHERE precierre_operacion.numero= ? "
                    + " AND operacion=2 ORDER BY numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {

                    p.setCreferencia(rs.getString("creferencia"));
                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setComprador(rs.getInt("comprador"));
                    p.setNombrecomprador(rs.getString("nombreclientecomprador"));
                    p.setVendedor(rs.getInt("vendedor"));
                    p.setNombrevendedor(rs.getString("nombreclientevendedor"));
                    p.setAsesorcompra(rs.getInt("asesorcompra"));
                    p.setAsesorventa(rs.getInt("asesorventa"));
                    p.setNombre_asesor_compra(rs.getString("nombreasesorcomprador"));
                    p.setNombre_asesor_venta(rs.getString("nombreasesorvendedor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(m);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(e);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(t);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("nomalias"));

                    p.setTipoaccion(acc);
                    p.getTipoaccion().setCodigo(rs.getInt("tipoaccion"));
                    p.getTipoaccion().setNombre(rs.getString("nombreaccion"));

                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setBancopago(rs.getInt("bancopago"));
                    p.setNombrebanco(rs.getString("nombrebanco"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setArancelventa(rs.getInt("arancelventa"));
                    p.setArancelcompra(rs.getInt("arancelcompra"));
                    p.setComcomprador(rs.getBigDecimal("comcomprador"));
                    p.setComvendedor(rs.getBigDecimal("comvendedor"));

                    if (rs.getString("nombrebanco") == null) {
                        p.setBancopago(0);
                        p.setNombrebanco("");
                        p.setCuentapago("");
                    }

                    System.out.println("CLIENTE VENDEDOR---> " + rs.getString("nombreclientevendedor"));

                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return p;
    }

    public precierre_operacion insertarOperacion(precierre_operacion cont, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO precierre_operacion"
                + " (creferencia,fecha,comprador,vendedor,asesorcompra,asesorventa,"
                + "operacion,tipooperacion,mercado,moneda,emisor,titulo,valor_nominal,cantidad,"
                + "valor_inversion,tasa,plazo,tipoplazo,"
                + "precio,fechacierre,negociado,base,"
                + "periodopago,numerobolsa,fechaemision,vencimiento,"
                + "usuarioalta,cupones,bancopago,cuentapago,arancelventa,"
                + "arancelcompra,comcomprador,comvendedor,idcarteravendida,referenciaventa)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, cont.getCreferencia());
        ps.setDate(2, cont.getFecha());
        ps.setInt(3, cont.getComprador());
        ps.setInt(4, cont.getVendedor());
        ps.setInt(5, cont.getAsesorcompra());
        ps.setInt(6, cont.getAsesorventa());
        ps.setInt(7, cont.getOperacion());
        ps.setInt(8, cont.getTipooperacion());
        ps.setInt(9, cont.getMercado());
        ps.setInt(10, cont.getMoneda().getCodigo());
        ps.setInt(11, cont.getEmisor().getCodigo());
        ps.setInt(12, cont.getTitulo().getCodigo());
        ps.setBigDecimal(13, cont.getValor_nominal());
        ps.setBigDecimal(14, cont.getCantidad());
        ps.setBigDecimal(15, cont.getValor_inversion());
        ps.setBigDecimal(16, cont.getTasa());
        ps.setInt(17, cont.getPlazo());
        ps.setInt(18, cont.getTipoplazo());
        ps.setBigDecimal(19, cont.getPrecio());
        ps.setDate(20, cont.getFechacierre());
        ps.setInt(21, cont.getNegociado());
        ps.setInt(22, cont.getBase());
        ps.setInt(23, cont.getPeriodopago());
        ps.setString(24, cont.getNumerobolsa());
        ps.setDate(25, cont.getFechaemision());
        ps.setDate(26, cont.getVencimiento());
        ps.setInt(27, cont.getUsuarioalta());
        ps.setInt(28, cont.getCupones());
        ps.setInt(29, cont.getBancopago());
        ps.setString(30, cont.getCuentapago());
        ps.setInt(31, cont.getArancelventa());
        ps.setInt(32, cont.getArancelcompra());
        ps.setBigDecimal(33, cont.getComcomprador());
        ps.setBigDecimal(34, cont.getComvendedor());
        ps.setString(35, cont.getIdcarteravendida());
        ps.setString(36, cont.getReferenciaventa());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            cont.setNumero(id);
            liquidacionDAO liqDAO = new liquidacionDAO();
            liqDAO.BuscarxOperacion(cont.getNumero());
            guardado = guardarCupones(cont.getNumero(), cont.getCreferencia(), detalle, con);
        }
        st.close();
        ps.close();
        conn.close();
        return cont;
    }

    public precierre_operacion ActualizarPrecierre(precierre_operacion cont, String detalle) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  precierre_operacion "
                + "SET creferencia=?,fecha=?,comprador=?,vendedor=?,asesorcompra=?,asesorventa=?,"
                + "operacion=?,tipooperacion=?,mercado=?,moneda=?,emisor=?,titulo=?,"
                + "valor_nominal=?,cantidad=?,"
                + "valor_inversion=?,tasa=?,plazo=?,tipoplazo=?,"
                + "precio=?,fechacierre=?,negociado=?,base=?,"
                + "periodopago=?,numerobolsa=?,fechaemision=?,vencimiento=?,"
                + "usuarioupdate=?,cupones=?,bancopago=?,cuentapago=?,"
                + "arancelventa=?,arancelcompra=?,"
                + "comcomprador=?,comvendedor=? "
                + " WHERE numero= " + cont.getNumero());

        ps.setString(1, cont.getCreferencia());
        ps.setDate(2, cont.getFecha());
        ps.setInt(3, cont.getComprador());
        ps.setInt(4, cont.getVendedor());
        ps.setInt(5, cont.getAsesorcompra());
        ps.setInt(6, cont.getAsesorventa());
        ps.setInt(7, cont.getOperacion());
        ps.setInt(8, cont.getTipooperacion());
        ps.setInt(9, cont.getMercado());
        ps.setInt(10, cont.getMoneda().getCodigo());
        ps.setInt(11, cont.getEmisor().getCodigo());
        ps.setInt(12, cont.getTitulo().getCodigo());
        ps.setBigDecimal(13, cont.getValor_nominal());
        ps.setBigDecimal(14, cont.getCantidad());
        ps.setBigDecimal(15, cont.getValor_inversion());
        ps.setBigDecimal(16, cont.getTasa());
        ps.setInt(17, cont.getPlazo());
        ps.setInt(18, cont.getTipoplazo());
        ps.setBigDecimal(19, cont.getPrecio());
        ps.setDate(20, cont.getFechacierre());
        ps.setInt(21, cont.getNegociado());
        ps.setInt(22, cont.getBase());
        ps.setInt(23, cont.getPeriodopago());
        ps.setString(24, cont.getNumerobolsa());
        ps.setDate(25, cont.getFechaemision());
        ps.setDate(26, cont.getVencimiento());
        ps.setInt(27, cont.getUsuarioupdate());
        ps.setInt(28, cont.getCupones());
        ps.setInt(29, cont.getBancopago());
        ps.setString(30, cont.getCuentapago());
        ps.setInt(31, cont.getArancelventa());
        ps.setInt(32, cont.getArancelcompra());
        ps.setBigDecimal(33, cont.getComcomprador());
        ps.setBigDecimal(34, cont.getComvendedor());
        int rowsUpdated = ps.executeUpdate();

        if (rowsUpdated > 0) {
            guardado = guardarCupones(cont.getNumero(), cont.getCreferencia(), detalle, con);
            liquidacionDAO liqDAO = new liquidacionDAO();
            liqDAO.BuscarxOperacion(cont.getNumero());
        }
        st.close();
        ps.close();
        conn.close();
        return cont;
    }

    public boolean borrarPrecierre(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM precierre_operacion WHERE numero=?");
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

    public boolean guardarCupones(double nnumeroprecierre, String id, String detalle, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement psdetalle = null;

        psdetalle = st.getConnection().prepareStatement("DELETE FROM cupones WHERE idprecierre=?");
        psdetalle.setString(1, id);
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

                    String sql = "INSERT INTO cupones ("
                            + "idprecierre,"
                            + "idcupon,"
                            + "numeroprecierre,"
                            + "iddesglose,"
                            + "ncantidad,"
                            + "ncupon,"
                            + "diaemision,"
                            + "fechainicial,"
                            + "plazocupon,"
                            + "base,"
                            + "interes,"
                            + "fechavencimiento,"
                            + "valorfuturo,"
                            + "montocapital,"
                            + "estado"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("idprecierre").getAsString());
                        ps.setString(2, obj.get("idcupon").getAsString());
                        ps.setDouble(3, nnumeroprecierre);
                        ps.setString(4, obj.get("iddesglose").getAsString());
                        ps.setString(5, obj.get("ncantidad").getAsString());
                        ps.setString(6, obj.get("ncupon").getAsString());
                        ps.setString(7, obj.get("diaemision").getAsString());
                        ps.setString(8, obj.get("fechainicial").getAsString());
                        ps.setString(9, obj.get("plazocupon").getAsString());
                        ps.setString(10, obj.get("base").getAsString());
                        ps.setString(11, obj.get("interes").getAsString());
                        ps.setString(12, obj.get("fechavencimiento").getAsString());
                        ps.setString(13, obj.get("valorfuturo").getAsString());
                        ps.setString(14, obj.get("montocapital").getAsString());
                        ps.setString(15, obj.get("estado").getAsString());

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

    public ArrayList<precierre_operacion> MostrarxFechaRentaVariable(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<precierre_operacion> lista = new ArrayList<precierre_operacion>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT creferencia,precierre_operacion.numero,negociado,"
                    + "precierre_operacion.fecha,validohasta,fechacierre,"
                    + "comprador,"
                    + "vendedor,"
                    + "(SELECT nombre FROM clientes WHERE precierre_operacion.comprador=clientes.codigo) nombreclientecomprador, "
                    + "(SELECT nombre FROM clientes WHERE precierre_operacion.vendedor=clientes.codigo)  nombreclientevendedor ,"
                    + "asesorcompra,asesorventa,"
                    + "(SELECT nombre FROM vendedores WHERE precierre_operacion.asesorcompra=vendedores.codigo) nombreasesorcomprador, "
                    + "(SELECT nombre FROM vendedores WHERE precierre_operacion.asesorventa=vendedores.codigo)  nombreasesorvendedor,"
                    + "precierre_operacion.operacion,tipooperacion,"
                    + "precierre_operacion.mercado,precierre_operacion.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "cantidad,precio,valor_inversion,"
                    + "numerobolsa "
                    + " FROM precierre_operacion "
                    + " LEFT JOIN monedas "
                    + " ON precierre_operacion.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON precierre_operacion.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON precierre_operacion.titulo=titulos.codigo "
                    + " WHERE precierre_operacion.fecha between ? AND ? "
                    + " AND operacion=2 ORDER BY numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    precierre_operacion p = new precierre_operacion();
                    moneda m = new moneda();
                    titulo t = new titulo();
                    emisor e = new emisor();

                    p.setCreferencia(rs.getString("creferencia"));
                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setComprador(rs.getInt("comprador"));
                    p.setNombrecomprador(rs.getString("nombreclientecomprador"));
                    p.setVendedor(rs.getInt("vendedor"));
                    p.setNombrevendedor(rs.getString("nombreclientevendedor"));
                    p.setAsesorcompra(rs.getInt("asesorcompra"));
                    p.setAsesorventa(rs.getInt("asesorventa"));
                    p.setNombre_asesor_compra(rs.getString("nombreasesorcomprador"));
                    p.setNombre_asesor_venta(rs.getString("nombreasesorvendedor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(m);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(e);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(t);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    System.out.println("--> CLIENTE VENDEDOR " + rs.getString("nombreclientevendedor"));

                    lista.add(p);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public precierre_operacion insertarOperacionRentaVariable(precierre_operacion cont) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO precierre_operacion"
                + " (creferencia,fecha,comprador,vendedor,asesorcompra,asesorventa,"
                + "operacion,tipooperacion,mercado,moneda,emisor,titulo,cantidad,"
                + "valor_inversion,"
                + "precio,fechacierre,negociado,"
                + "numerobolsa,"
                + "usuarioalta,bancopago,cuentapago,arancelventa,arancelcompra,comcomprador,comvendedor,tipoaccion)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, cont.getCreferencia());
        ps.setDate(2, cont.getFecha());
        ps.setInt(3, cont.getComprador());
        ps.setInt(4, cont.getVendedor());
        ps.setInt(5, cont.getAsesorcompra());
        ps.setInt(6, cont.getAsesorventa());
        ps.setInt(7, cont.getOperacion());
        ps.setInt(8, cont.getTipooperacion());
        ps.setInt(9, cont.getMercado());
        ps.setInt(10, cont.getMoneda().getCodigo());
        ps.setInt(11, cont.getEmisor().getCodigo());
        ps.setInt(12, cont.getTitulo().getCodigo());
        ps.setBigDecimal(13, cont.getCantidad());
        ps.setBigDecimal(14, cont.getValor_inversion());
        ps.setBigDecimal(15, cont.getPrecio());
        ps.setDate(16, cont.getFechacierre());
        ps.setInt(17, cont.getNegociado());
        ps.setString(18, cont.getNumerobolsa());
        ps.setInt(19, cont.getUsuarioalta());
        ps.setInt(20, cont.getBancopago());
        ps.setString(21, cont.getCuentapago());
        ps.setInt(22, cont.getArancelventa());
        ps.setInt(23, cont.getArancelcompra());
        ps.setBigDecimal(24, cont.getComcomprador());
        ps.setBigDecimal(25, cont.getComvendedor());
        ps.setInt(26, cont.getTipoaccion().getCodigo());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> ERROR DE INSERT " + ex.getLocalizedMessage());
        }

        ResultSet keyset = ps.getGeneratedKeys();

        if (keyset.next()) {
            id = keyset.getInt(1);
            cont.setNumero(id);
            liquidacionDAO liqDAO = new liquidacionDAO();
            liqDAO.BuscarxOperacionAcciones(cont.getNumero());
        }

        st.close();
        ps.close();
        conn.close();
        return cont;
    }

    public precierre_operacion ActualizarPrecierreRentaVariable(precierre_operacion cont) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE  precierre_operacion SET "
                + "creferencia=?,fecha=?,comprador=?,"
                + "vendedor=?,asesorcompra=?,asesorventa=?,"
                + "operacion=?,tipooperacion=?,mercado=?,"
                + "moneda=?,emisor=?,titulo=?,"
                + "cantidad=?,valor_inversion=?,precio=?,"
                + "fechacierre=?,negociado=?,numerobolsa=?,"
                + "usuarioupdate=?,bancopago=?,cuentapago=?,"
                + "arancelventa=?,arancelcompra=?,"
                + "comcomprador=?,comvendedor=?,"
                + "tipoaccion=? "
                + " WHERE numero= " + cont.getNumero());

        ps.setString(1, cont.getCreferencia());
        ps.setDate(2, cont.getFecha());
        ps.setInt(3, cont.getComprador());
        ps.setInt(4, cont.getVendedor());
        ps.setInt(5, cont.getAsesorcompra());
        ps.setInt(6, cont.getAsesorventa());
        ps.setInt(7, cont.getOperacion());
        ps.setInt(8, cont.getTipooperacion());
        ps.setInt(9, cont.getMercado());
        ps.setInt(10, cont.getMoneda().getCodigo());
        ps.setInt(11, cont.getEmisor().getCodigo());
        ps.setInt(12, cont.getTitulo().getCodigo());
        ps.setBigDecimal(13, cont.getCantidad());
        ps.setBigDecimal(14, cont.getValor_inversion());
        ps.setBigDecimal(15, cont.getPrecio());
        ps.setDate(16, cont.getFechacierre());
        ps.setInt(17, cont.getNegociado());
        ps.setString(18, cont.getNumerobolsa());
        ps.setInt(19, cont.getUsuarioupdate());
        ps.setInt(20, cont.getBancopago());
        ps.setString(21, cont.getCuentapago());
        ps.setInt(22, cont.getArancelventa());
        ps.setInt(23, cont.getArancelcompra());
        ps.setBigDecimal(24, cont.getComcomprador());
        ps.setBigDecimal(25, cont.getComvendedor());
        ps.setInt(26, cont.getTipoaccion().getCodigo());
        try {
            ps.executeUpdate();
            liquidacionDAO liqDAO = new liquidacionDAO();
            System.out.println("--> NUMERO DE ACTUALIZACION " + cont.getNumero());

            liqDAO.BuscarxOperacionAcciones(cont.getNumero());
        } catch (SQLException ex) {
            System.out.println("--> ERROR DE ACTUALIZACION " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        conn.close();
        return cont;
    }

    public ArrayList<precierre_operacion> MostrarxOperacionesxFecha(int ntipo, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<precierre_operacion> lista = new ArrayList<precierre_operacion>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT p.fechacierre,p.numerobolsa,p.comprador as cuentacliente,c.nombre,'CM' AS tipo,p.emisor,e.nomalias,"
                    + "t.nomalias AS serie,p.vencimiento,p.cantidad,p.titulo,t.nomalias,p.mercado,"
                    + "IF(p.mercado=1,'PRIMARIO','SECUNDARIO') AS nmercado,"
                    + "p.moneda,m.etiqueta,"
                    + "p.valor_nominal,p.precio,p.valor_inversion "
                    + "FROM precierre_operacion p "
                    + "INNER JOIN emisores e "
                    + "ON e.codigo=p.emisor "
                    + "INNER JOIN titulos t "
                    + "ON t.codigo=p.titulo "
                    + "INNER JOIN monedas m "
                    + "ON m.codigo=p.moneda "
                    + "INNER JOIN clientes c "
                    + "ON c.codigo=p.comprador "
                    + "WHERE p.fechacierre BETWEEN '"+fechaini+"' "
                    + "AND '"+fechafin+"' "
                    + "AND p.comprador<>0 "
                    + "AND p.operacion= "+ntipo
                    + " UNION "
                    + "SELECT p.fechacierre,p.numerobolsa,p.comprador as cuentacliente,c.nombre,'VN' AS tipo,p.emisor,e.nomalias,"
                    + "t.nomalias AS serie,p.vencimiento,p.cantidad,p.titulo,t.nomalias,p.mercado,"
                    + "IF(p.mercado=1,'PRIMARIO','SECUNDARIO') AS nmercado,"
                    + "p.moneda,m.etiqueta,"
                    + "p.valor_nominal,p.precio,p.valor_inversion "
                    + "FROM precierre_operacion p "
                    + "INNER JOIN emisores e "
                    + "ON e.codigo=p.emisor "
                    + "INNER JOIN titulos t "
                    + "ON t.codigo=p.titulo "
                    + "INNER JOIN monedas m "
                    + "ON m.codigo=p.moneda "
                    + "INNER JOIN clientes c "
                    + "ON c.codigo=p.vendedor "
                    + "WHERE p.fechacierre BETWEEN '"+fechaini+"' "
                    + "AND '"+fechafin+"' "
                    + "AND p.comprador<>0 "
                    + "AND p.operacion= "+ntipo
                    +"ORDER BY moneda,numerobolsa" ;

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, ntipo);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    precierre_operacion p = new precierre_operacion();
                    moneda m = new moneda();
                    titulo t = new titulo();
                    emisor e = new emisor();

                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setCuentacliente(rs.getInt("cuentacliente"));
                    p.setNombrecliente(rs.getString("nombrecliente"));
                    p.setNombremercado(rs.getString("nombremercado"));
                    p.setMoneda(m);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.getMoneda().setEtiqueta(rs.getString("nombremoneda"));
                    p.setEmisor(e);
                    p.setTitulo(t);
                    p.getTitulo().setNomalias(rs.getString("nomalias"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    lista.add(p);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

}

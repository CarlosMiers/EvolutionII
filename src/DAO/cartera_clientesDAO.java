/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.Config;
import Conexion.Conexion;
import Modelo.bancoplaza;
import Modelo.vendedor;
import Modelo.cartera_clientes;
import Modelo.cliente;
import Modelo.emisor;
import Modelo.moneda;
import Modelo.titulo;
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
public class cartera_clientesDAO {

    Conexion con = null;
    Statement st = null;
    int nAsesor = Integer.valueOf(Config.cCodigoAsesor);

    public cartera_clientes insertarRentaFija(cartera_clientes ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cartera_clientes"
                + " (creferencia,idcartera,numero,fecha,comprador,"
                + "asesorcompra,operacion,tipooperacion,"
                + "mercado,moneda,emisor,titulo,"
                + "valor_nominal,cantidad,valor_inversion,"
                + "tasa,plazo,tipoplazo,custodia,precio,"
                + "fechacierre,negociado,base,periodopago,numerobolsa,"
                + "fechaemision,vencimiento,serie,nro_titulo,"
                + "desde_acci,hasta_acci,estado,itemdesglose,bancopago,cuentapago)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?)");
        ps.setString(1, ca.getCreferencia());
        ps.setString(2, ca.getIdcartera());
        ps.setDouble(3, ca.getNumero());
        ps.setDate(4, ca.getFecha());
        ps.setInt(5, ca.getComprador().getCodigo());
        ps.setInt(6, ca.getAsesorcompra().getCodigo());
        ps.setInt(7, ca.getOperacion());
        ps.setInt(8, ca.getTipooperacion());
        ps.setInt(9, ca.getMercado());
        ps.setInt(10, ca.getMoneda().getCodigo());
        ps.setInt(11, ca.getEmisor().getCodigo());
        ps.setInt(12, ca.getTitulo().getCodigo());
        ps.setBigDecimal(13, ca.getValor_nominal());
        ps.setBigDecimal(14, ca.getCantidad());
        ps.setBigDecimal(15, ca.getValor_inversion());
        ps.setBigDecimal(16, ca.getTasa());
        ps.setInt(17, ca.getPlazo());
        ps.setInt(18, ca.getTipoplazo());
        ps.setInt(19, ca.getCustodia());
        ps.setBigDecimal(20, ca.getPrecio());
        ps.setDate(21, ca.getFechacierre());
        ps.setInt(22, ca.getNegociado());
        ps.setInt(23, ca.getBase());
        ps.setInt(24, ca.getPeriodopago());
        ps.setString(25, ca.getNumerobolsa());
        ps.setDate(26, ca.getFechaemision());
        ps.setDate(27, ca.getVencimiento());
        ps.setString(28, ca.getSerie());
        ps.setString(29, ca.getNro_titulo());
        ps.setInt(30, ca.getDesde_acci());
        ps.setInt(31, ca.getHasta_acci());
        ps.setInt(32, ca.getEstado());
        ps.setDouble(33, ca.getItemdesglose());
        ps.setInt(34, ca.getBancopago().getCodigo());
        ps.setString(35, ca.getCuentapago());

        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> CARTERA DE CLIENTES " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        conn.close();
        return ca;
    }

    public boolean borrarCartera(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM cartera_clientes WHERE itemdesglose=?");
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

    public ArrayList<cartera_clientes> MostrarxVencimientoEmisorPrincipal(int emisor1, int emisor2, int moneda1, int moneda2, Date fechaini, Date fechafin, int nestado) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cartera_clientes.vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.mail,"
                    + "clientes.cedula,"
                    + "clientes.ruc,"
                    + "clientes.categoria,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "cartera_clientes.tasa,plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,bancos_plaza.reporte,"
                    + "cartera_clientes.periodopago,1 AS tipodocumento,"
                    + "'PRINCIPALES' AS nombredocumento, 1 AS ncupon,"
                    + "1 AS ncantidad "
                    + " FROM cartera_clientes "
                    + " LEFT JOIN monedas "
                    + " ON cartera_clientes.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON cartera_clientes.titulo=titulos.codigo "
                    + " LEFT JOIN clientes "
                    + " ON cartera_clientes.comprador=clientes.codigo "
                    + " LEFT JOIN vendedores "
                    + " ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + " LEFT JOIN bancos_plaza "
                    + " ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + " WHERE IF(?<>0,cartera_clientes.emisor=?,TRUE)"
                    + " AND IF(?<>0,cartera_clientes.moneda=?,TRUE)"
                    + " AND cartera_clientes.vencimiento BETWEEN ? AND ? "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " AND IF(" + nestado + "<>0,cartera_clientes.estado=1,TRUE)"
                    + " AND operacion=1  "
                    + "ORDER BY emisor,moneda,vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, emisor1);
                ps.setInt(2, emisor2);
                ps.setInt(3, moneda1);
                ps.setInt(4, moneda2);
                ps.setDate(5, fechaini);
                ps.setDate(6, fechafin);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.getComprador().setMail(rs.getString("mail"));
                    if (rs.getInt("categoria") == 1) {
                        p.getComprador().setRuc(rs.getString("cedula"));
                    } else {
                        p.getComprador().setRuc(rs.getString("ruc"));
                    }
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setBancopago(bco);
                    p.getBancopago().setNombre(rs.getString("nombrebanco"));
                    p.getBancopago().setReporte(rs.getString("reporte"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setTipodocumento(rs.getInt("tipodocumento"));
                    p.setNombredocumento(rs.getString("nombredocumento"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setNcupon(rs.getInt("ncupon"));
                    p.setNcantidad(rs.getInt("ncantidad"));
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

    public ArrayList<cartera_clientes> MostrarxVencimientoEmisorCupones(int emisor1, int emisor2, int moneda1, int moneda2, Date fechaini, Date fechafin, int nestado) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cupones.fechavencimiento AS vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.mail,"
                    + "clientes.cedula,"
                    + "clientes.ruc,"
                    + "clientes.categoria,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valorfuturo AS valor_inversion,"
                    + "cartera_clientes.tasa,cupones.plazocupon AS plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,bancos_plaza.reporte,"
                    + "cartera_clientes.periodopago,2 AS tipodocumento,"
                    + "'CUPONES' AS nombredocumento, cupones.ncupon,"
                    + "cupones.ncantidad "
                    + "FROM cartera_clientes "
                    + "LEFT JOIN monedas "
                    + "ON cartera_clientes.moneda=monedas.codigo "
                    + "LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + "LEFT JOIN titulos "
                    + "ON cartera_clientes.titulo=titulos.codigo "
                    + "LEFT JOIN clientes "
                    + "ON cartera_clientes.comprador=clientes.codigo "
                    + "LEFT JOIN vendedores "
                    + "ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + "LEFT JOIN bancos_plaza "
                    + "ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + "LEFT JOIN cupones "
                    + "ON cartera_clientes.creferencia=cupones.idprecierre "
                    + " WHERE IF(?<>0,cartera_clientes.emisor=?,TRUE) "
                    + " AND IF(?<>0,cartera_clientes.moneda=?,TRUE) "
                    + " AND cupones.fechavencimiento BETWEEN ? AND ? "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + "AND cartera_clientes.operacion=1 "
                    + " AND IF(" + nestado + "<>0,cupones.estado=1,TRUE)"
                    + " ORDER BY cartera_clientes.emisor,cartera_clientes.moneda,cupones.fechavencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, emisor1);
                ps.setInt(2, emisor2);
                ps.setInt(3, moneda1);
                ps.setInt(4, moneda2);
                ps.setDate(5, fechaini);
                ps.setDate(6, fechafin);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.getComprador().setMail(rs.getString("mail"));
                    if (rs.getInt("categoria") == 1) {
                        p.getComprador().setRuc(rs.getString("cedula"));
                    } else {
                        p.getComprador().setRuc(rs.getString("ruc"));
                    }
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setBancopago(bco);
                    p.getBancopago().setNombre(rs.getString("nombrebanco"));
                    p.getBancopago().setReporte(rs.getString("reporte"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setTipodocumento(rs.getInt("tipodocumento"));
                    p.setNombredocumento(rs.getString("nombredocumento"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setNcupon(rs.getInt("ncupon"));
                    p.setNcantidad(rs.getInt("ncantidad"));
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

    public ArrayList<cartera_clientes> MostrarxVencimientoEmisorPrincipalCupones(int emisor1, int moneda1, Date fechaini, Date fechafin, int nestado) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        int nValorEstado = nestado;
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cartera_clientes.vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.mail,"
                    + "clientes.cedula,"
                    + "clientes.ruc,"
                    + "clientes.categoria,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "cartera_clientes.tasa,plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,bancos_plaza.reporte,"
                    + "cartera_clientes.periodopago,1 AS tipodocumento,"
                    + "'PRINCIPALES' AS nombredocumento, 1 AS ncupon,"
                    + "1 AS ncantidad "
                    + " FROM cartera_clientes "
                    + " LEFT JOIN monedas "
                    + " ON cartera_clientes.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON cartera_clientes.titulo=titulos.codigo "
                    + " LEFT JOIN clientes "
                    + " ON cartera_clientes.comprador=clientes.codigo "
                    + " LEFT JOIN vendedores "
                    + " ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + " LEFT JOIN bancos_plaza "
                    + " ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + " WHERE IF(" + emisor1 + "<>0,cartera_clientes.emisor=" + emisor1 + ",TRUE)"
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " AND IF(" + nValorEstado + "<>0,cartera_clientes.estado=1,cartera_clientes.estado=0)"
                    + " AND cartera_clientes.vencimiento BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " UNION "
                    + "SELECT cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cupones.fechavencimiento AS vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.mail,"
                    + "clientes.cedula,"
                    + "clientes.ruc,"
                    + "clientes.categoria,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valorfuturo AS valor_inversion,"
                    + "cartera_clientes.tasa,cupones.plazocupon AS plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,bancos_plaza.reporte,"
                    + "cartera_clientes.periodopago,2 AS tipodocumento,"
                    + "'CUPONES' AS nombredocumento, cupones.ncupon,"
                    + "cupones.ncantidad "
                    + "FROM cartera_clientes "
                    + "LEFT JOIN monedas "
                    + "ON cartera_clientes.moneda=monedas.codigo "
                    + "LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + "LEFT JOIN titulos "
                    + "ON cartera_clientes.titulo=titulos.codigo "
                    + "LEFT JOIN clientes "
                    + "ON cartera_clientes.comprador=clientes.codigo "
                    + "LEFT JOIN vendedores "
                    + "ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + "LEFT JOIN bancos_plaza "
                    + "ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + "LEFT JOIN cupones "
                    + "ON cartera_clientes.creferencia=cupones.idprecierre "
                    + " WHERE IF(" + emisor1 + "<>0,cartera_clientes.emisor=" + emisor1 + ",TRUE)"
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND cupones.fechavencimiento BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " AND IF(" + nValorEstado + "<>0,cupones.estado=1,TRUE)"
                    + " ORDER BY tipodocumento,emisor,moneda,vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();
                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.getComprador().setMail(rs.getString("mail"));
                    p.getComprador().setCategoria(rs.getInt("categoria"));
                    if (rs.getInt("categoria") == 1) {
                        p.getComprador().setRuc(rs.getString("cedula"));
                    } else {
                        p.getComprador().setRuc(rs.getString("ruc"));
                    }
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setBancopago(bco);
                    p.getBancopago().setNombre(rs.getString("nombrebanco"));
                    p.getBancopago().setReporte(rs.getString("reporte"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setTipodocumento(rs.getInt("tipodocumento"));
                    p.setNombredocumento(rs.getString("nombredocumento"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setNcupon(rs.getInt("ncupon"));
                    p.setNcantidad(rs.getInt("ncantidad"));
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

    public ArrayList<cartera_clientes> MostrarxVencimientoClientePrincipal(int cliente1, int cliente2, int moneda1, int moneda2, Date fechaini, Date fechafin, int emisor1, int emisor2) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cartera_clientes.vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "cartera_clientes.tasa,plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,1 AS tipodocumento,"
                    + "'PRINCIPALES' AS nombredocumento, 1 AS ncupon,"
                    + "1 AS ncantidad "
                    + " FROM cartera_clientes "
                    + " LEFT JOIN monedas "
                    + " ON cartera_clientes.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON cartera_clientes.titulo=titulos.codigo "
                    + " LEFT JOIN clientes "
                    + " ON cartera_clientes.comprador=clientes.codigo "
                    + " LEFT JOIN vendedores "
                    + " ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + " LEFT JOIN bancos_plaza "
                    + " ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + " WHERE IF(?<>0,cartera_clientes.comprador=?,TRUE)"
                    + " AND IF(?<>0,cartera_clientes.moneda=?,TRUE)"
                    + " AND cartera_clientes.vencimiento BETWEEN ? AND ? "
                    + " AND IF(?<>0,cartera_clientes.emisor=?,TRUE) "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " AND operacion=1  "
                    + " ORDER BY comprador,moneda,vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, cliente1);
                ps.setInt(2, cliente2);
                ps.setInt(3, moneda1);
                ps.setInt(4, moneda2);
                ps.setDate(5, fechaini);
                ps.setDate(6, fechafin);
                ps.setInt(7, emisor1);
                ps.setInt(8, emisor2);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setBancopago(bco);
                    p.getBancopago().setNombre(rs.getString("nombrebanco"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setTipodocumento(rs.getInt("tipodocumento"));
                    p.setNombredocumento(rs.getString("nombredocumento"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setNcupon(rs.getInt("ncupon"));
                    p.setNcantidad(rs.getInt("ncantidad"));
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

    public ArrayList<cartera_clientes> MostrarxVencimientoClienteCupones(int cliente1, int cliente2, int moneda1, int moneda2, Date fechaini, Date fechafin, int emisor1, int emisor2) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cupones.fechavencimiento AS vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valorfuturo AS valor_inversion,"
                    + "cartera_clientes.tasa,cupones.plazocupon AS plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,2 AS tipodocumento,"
                    + "'CUPONES' AS nombredocumento, cupones.ncupon,"
                    + "cupones.ncantidad "
                    + "FROM cartera_clientes "
                    + "LEFT JOIN monedas "
                    + "ON cartera_clientes.moneda=monedas.codigo "
                    + "LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + "LEFT JOIN titulos "
                    + "ON cartera_clientes.titulo=titulos.codigo "
                    + "LEFT JOIN clientes "
                    + "ON cartera_clientes.comprador=clientes.codigo "
                    + "LEFT JOIN vendedores "
                    + "ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + "LEFT JOIN bancos_plaza "
                    + "ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + "LEFT JOIN cupones "
                    + "ON cartera_clientes.creferencia=cupones.idprecierre "
                    + " WHERE IF(?<>0,cartera_clientes.comprador=?,TRUE) "
                    + " AND IF(?<>0,cartera_clientes.moneda=?,TRUE) "
                    + " AND cupones.fechavencimiento BETWEEN ? AND ? "
                    + " AND IF(?<>0,cartera_clientes.emisor=?,TRUE) "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " ORDER BY cartera_clientes.comprador,cartera_clientes.moneda,cupones.fechavencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, cliente1);
                ps.setInt(2, cliente2);
                ps.setInt(3, moneda1);
                ps.setInt(4, moneda2);
                ps.setDate(5, fechaini);
                ps.setDate(6, fechafin);
                ps.setInt(7, emisor1);
                ps.setInt(8, emisor2);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setBancopago(bco);
                    p.getBancopago().setNombre(rs.getString("nombrebanco"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setTipodocumento(rs.getInt("tipodocumento"));
                    p.setNombredocumento(rs.getString("nombredocumento"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setNcupon(rs.getInt("ncupon"));
                    p.setNcantidad(rs.getInt("ncantidad"));
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

    public ArrayList<cartera_clientes> MostrarxVencimientoClientePrincipalCupones(int cliente1, int moneda1, Date fechaini, Date fechafin, int emisor) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.creferencia,cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cartera_clientes.vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "cartera_clientes.tasa,plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,1 AS tipodocumento,"
                    + "'PR' AS nombredocumento, 1 AS ncupon,"
                    + "1 AS ncantidad "
                    + " FROM cartera_clientes "
                    + " LEFT JOIN monedas "
                    + " ON cartera_clientes.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON cartera_clientes.titulo=titulos.codigo "
                    + " LEFT JOIN clientes "
                    + " ON cartera_clientes.comprador=clientes.codigo "
                    + " LEFT JOIN vendedores "
                    + " ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + " LEFT JOIN bancos_plaza "
                    + " ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + " WHERE IF(" + cliente1 + "<>0,cartera_clientes.comprador=" + cliente1 + ",TRUE)"
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND IF(" + emisor + "<>0,cartera_clientes.emisor=" + emisor + ",TRUE)"
                    + " AND cartera_clientes.vencimiento BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " UNION "
                    + "SELECT cupones.idcupon AS creferencia,cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cupones.fechavencimiento AS vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valorfuturo AS valor_inversion,"
                    + "cartera_clientes.tasa,cupones.plazocupon AS plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,2 AS tipodocumento,"
                    + "'CP' AS nombredocumento, cupones.ncupon,"
                    + "cupones.ncantidad "
                    + "FROM cartera_clientes "
                    + "LEFT JOIN monedas "
                    + "ON cartera_clientes.moneda=monedas.codigo "
                    + "LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + "LEFT JOIN titulos "
                    + "ON cartera_clientes.titulo=titulos.codigo "
                    + "LEFT JOIN clientes "
                    + "ON cartera_clientes.comprador=clientes.codigo "
                    + "LEFT JOIN vendedores "
                    + "ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + "LEFT JOIN bancos_plaza "
                    + "ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + "LEFT JOIN cupones "
                    + "ON cartera_clientes.creferencia=cupones.idprecierre "
                    + " WHERE IF(" + cliente1 + "<>0,cartera_clientes.comprador=" + cliente1 + ",TRUE)"
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND IF(" + emisor + "<>0,cartera_clientes.emisor=" + emisor + ",TRUE)"
                    + " AND cupones.fechavencimiento BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND cartera_clientes.operacion=1 "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " ORDER BY tipodocumento,comprador,moneda,vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setBancopago(bco);
                    p.getBancopago().setNombre(rs.getString("nombrebanco"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setTipodocumento(rs.getInt("tipodocumento"));
                    p.setNombredocumento(rs.getString("nombredocumento"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setNcupon(rs.getInt("ncupon"));
                    p.setNcantidad(rs.getInt("ncantidad"));
                    p.setCreferencia(rs.getString("creferencia"));
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

    public ArrayList<cartera_clientes> MostrarCarteraRentaFijaCliente(int cliente1, int moneda1, Date fechaini) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cartera_clientes.vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "cartera_clientes.tasa,plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,1 AS tipodocumento,"
                    + "'PRINCIPALES' AS nombredocumento, 1 AS ncupon,"
                    + "1 AS ncantidad "
                    + " FROM cartera_clientes "
                    + " LEFT JOIN monedas "
                    + " ON cartera_clientes.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON cartera_clientes.titulo=titulos.codigo "
                    + " LEFT JOIN clientes "
                    + " ON cartera_clientes.comprador=clientes.codigo "
                    + " LEFT JOIN vendedores "
                    + " ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + " LEFT JOIN bancos_plaza "
                    + " ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + " WHERE IF(" + cliente1 + "<>0,cartera_clientes.comprador=" + cliente1 + ",TRUE)"
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND cartera_clientes.fechacierre<='" + fechaini + "'"
                    + " AND cartera_clientes.operacion=1 and cartera_clientes.estado=1 "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " UNION "
                    + "SELECT cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cupones.fechavencimiento AS vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valorfuturo AS valor_inversion,"
                    + "cartera_clientes.tasa,cupones.plazocupon AS plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,2 AS tipodocumento,"
                    + "'CUPONES' AS nombredocumento, cupones.ncupon,"
                    + "cupones.ncantidad "
                    + "FROM cartera_clientes "
                    + "LEFT JOIN monedas "
                    + "ON cartera_clientes.moneda=monedas.codigo "
                    + "LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + "LEFT JOIN titulos "
                    + "ON cartera_clientes.titulo=titulos.codigo "
                    + "LEFT JOIN clientes "
                    + "ON cartera_clientes.comprador=clientes.codigo "
                    + "LEFT JOIN vendedores "
                    + "ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + "LEFT JOIN bancos_plaza "
                    + "ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + "LEFT JOIN cupones "
                    + "ON cartera_clientes.creferencia=cupones.idprecierre "
                    + " WHERE IF(" + cliente1 + "<>0,cartera_clientes.comprador=" + cliente1 + ",TRUE)"
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND cartera_clientes.fechacierre BETWEEN '" + fechaini + "'"
                    + " and cupones.estado=1 "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " ORDER BY tipodocumento,comprador,moneda,vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setBancopago(bco);
                    p.getBancopago().setNombre(rs.getString("nombrebanco"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setTipodocumento(rs.getInt("tipodocumento"));
                    p.setNombredocumento(rs.getString("nombredocumento"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setNcupon(rs.getInt("ncupon"));
                    p.setNcantidad(rs.getInt("ncantidad"));
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

    public cartera_clientes VerificarCertificado(double id) throws SQLException {
        cartera_clientes p = new cartera_clientes();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.numero,nrocertificado "
                    + "FROM cartera_clientes "
                    + " WHERE cartera_clientes.numero= " + id
                    + " ORDER BY numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    p.setNumero(rs.getDouble("numero"));
                    p.setNrocertificado(rs.getInt("nrocertificado"));
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

    public cartera_clientes CrearNumeroCertificado(double id) throws SQLException {
        cartera_clientes p = new cartera_clientes();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            //GENERO PROXIMO NUMERO DE CERTIFICADO
            System.out.println("//GENERO PROXIMO NUMERO DE CERTIFICADO");

            String sqlActualiza = "UPDATE cartera_clientes SET nrocertificado=(SELECT certificado+1 "
                    + " FROM configuracion) "
                    + " WHERE cartera_clientes.numero=" + id;

            PreparedStatement psgrabar = conn.prepareStatement(sqlActualiza);
            psgrabar.executeUpdate(sqlActualiza);

            String sqlCertificado = "UPDATE configuracion SET certificado=certificado+1";
            PreparedStatement pscertificado = conn.prepareStatement(sqlCertificado);
            pscertificado.executeUpdate(sqlCertificado);

            psgrabar.close();
            pscertificado.close();

        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return p;
    }

    public boolean borrarCarteraTotal(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM cartera_clientes WHERE creferencia=?");
        ps.setString(1, id);
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

    public boolean ActualizarEstadoCartera(int nestado, String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE cartera_clientes SET estado=? WHERE idcartera=? ");
        ps.setInt(1, nestado);
        ps.setString(2, id);
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

    public boolean ActualizarCuentaCompensatoria(cartera_clientes ca) {
        Connection conn = null;
        PreparedStatement ps = null;
        int rowsUpdated = 0;

        try {
            con = new Conexion();
            st = con.conectar();
            conn = st.getConnection();
            String sql = "UPDATE cartera_clientes SET bancopago=?, cuentapago=? "
                    + " WHERE vencimiento >= CURDATE() AND creferencia ='" + ca.getCreferencia().trim() + "'";
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ca.getBancopago().getCodigo());
            ps.setString(2, ca.getCuentapago());
            rowsUpdated = ps.executeUpdate();
//            rowsUpdated = ps.executeUpdate();

        } catch (SQLException e) {
            // Manejar la excepción de SQL
            e.printStackTrace(); // Imprime la traza de la excepción (opcional)
            return false;
        } finally {
            // Cerrar los recursos en el bloque finally
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Manejar el posible error al cerrar los recursos
            }
        }

        return rowsUpdated > 0;
    }

    public ArrayList<cartera_clientes> CarteraRentaFija(int cliente1, int moneda1, Date fechaini) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.creferencia,cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cartera_clientes.vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "emisores.nomalias,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "cartera_clientes.tasa,plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.periodopago,"
                    + "COALESCE(intcob.interescobrado,0) AS interescobrado,"
                    + "COALESCE(intacob.interesacobrar,0) AS interesacobrar "
                    + " FROM cartera_clientes "
                    + " LEFT JOIN monedas "
                    + " ON cartera_clientes.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON cartera_clientes.titulo=titulos.codigo "
                    + " LEFT JOIN clientes "
                    + " ON cartera_clientes.comprador=clientes.codigo "
                    + " LEFT JOIN vendedores "
                    + " ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + " LEFT JOIN (SELECT cupones.idprecierre, SUM(cupones.valorfuturo) AS interescobrado "
                    + " FROM cupones "
                    + " WHERE cupones.fechavencimiento<'" + fechaini + "'"
                    + " GROUP BY cupones.idprecierre) intcob ON intcob.idprecierre=cartera_clientes.creferencia"
                    + " LEFT JOIN (SELECT cupones.idprecierre, SUM(cupones.valorfuturo) AS interesacobrar "
                    + " FROM cupones "
                    + " WHERE cupones.fechavencimiento>'" + fechaini + "'"
                    + " GROUP BY cupones.idprecierre) intacob ON intacob.idprecierre=cartera_clientes.creferencia "
                    + " WHERE IF(" + cliente1 + "<>0,cartera_clientes.comprador=" + cliente1 + ",TRUE)"
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND cartera_clientes.fechacierre<='" + fechaini + "'"
                    + " AND operacion=1 "
                    + " AND cartera_clientes.estado=1 "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " ORDER BY moneda,vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.getEmisor().setNomalias(rs.getString("nomalias"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setInteresdevengado(rs.getBigDecimal("interescobrado"));
                    p.setInteresadevengar(rs.getBigDecimal("interesacobrar"));
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

    public ArrayList<cartera_clientes> CarteraGeneral(int cliente1, int moneda1, Date fechaini, int nestado) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.creferencia,cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cartera_clientes.vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "emisores.nomalias,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "cartera_clientes.tasa,plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.periodopago "
                    + " FROM cartera_clientes "
                    + " LEFT JOIN monedas "
                    + " ON cartera_clientes.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON cartera_clientes.titulo=titulos.codigo "
                    + " LEFT JOIN clientes "
                    + " ON cartera_clientes.comprador=clientes.codigo "
                    + " LEFT JOIN vendedores "
                    + " ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + " WHERE cartera_clientes.comprador=" + cliente1
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND cartera_clientes.fechacierre<='" + fechaini + "'"
                    + " AND cartera_clientes.estado= " + nestado
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE) "
                    + " ORDER BY moneda,operacion,fechacierre ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.getEmisor().setNomalias(rs.getString("nomalias"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setPeriodopago(rs.getInt("periodopago"));
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

    public cartera_clientes insertarRentaVariable(cartera_clientes ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cartera_clientes"
                + " (creferencia,idcartera,numero,fecha,comprador,"
                + "asesorcompra,operacion,tipooperacion,"
                + "mercado,moneda,emisor,titulo,"
                + "valor_nominal,cantidad,valor_inversion,"
                + "custodia,precio,"
                + "fechacierre,negociado,numerobolsa,"
                + "serie,nro_titulo,"
                + "desde_acci,hasta_acci,estado,itemdesglose,bancopago,cuentapago)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,"
                + "?,?,?,?,?)");
        ps.setString(1, ca.getCreferencia());
        ps.setString(2, ca.getIdcartera());
        ps.setDouble(3, ca.getNumero());
        ps.setDate(4, ca.getFecha());
        ps.setInt(5, ca.getComprador().getCodigo());
        ps.setInt(6, ca.getAsesorcompra().getCodigo());
        ps.setInt(7, ca.getOperacion());
        ps.setInt(8, ca.getTipooperacion());
        ps.setInt(9, ca.getMercado());
        ps.setInt(10, ca.getMoneda().getCodigo());
        ps.setInt(11, ca.getEmisor().getCodigo());
        ps.setInt(12, ca.getTitulo().getCodigo());
        ps.setBigDecimal(13, ca.getPrecio());
        ps.setBigDecimal(14, ca.getCantidad());
        ps.setBigDecimal(15, ca.getValor_inversion());
        ps.setInt(16, ca.getCustodia());
        ps.setBigDecimal(17, ca.getPrecio());
        ps.setDate(18, ca.getFechacierre());
        ps.setInt(19, ca.getNegociado());
        ps.setString(20, ca.getNumerobolsa());
        ps.setString(21, ca.getSerie());
        ps.setString(22, ca.getNro_titulo());
        ps.setInt(23, ca.getDesde_acci());
        ps.setInt(24, ca.getHasta_acci());
        ps.setInt(25, ca.getEstado());
        ps.setDouble(26, ca.getItemdesglose());
        ps.setInt(27, ca.getBancopago().getCodigo());
        ps.setString(28, ca.getCuentapago());

        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> CARTERA DE CLIENTES " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        conn.close();
        return ca;
    }

    public boolean ActualizarCarteraVencida() throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql1 = "UPDATE cupones  SET estado=0 WHERE fechavencimiento<CURDATE()";
        PreparedStatement ps = conn.prepareStatement(sql1);
        ps.executeUpdate(sql1);

        String sql6 = "UPDATE  cartera_clientes SET estado = 0 WHERE vencimiento<CURDATE() AND operacion= 1";
        PreparedStatement ps6 = conn.prepareStatement(sql6);
        ps6.executeUpdate(sql6);

        st.close();
        ps.close();
        ps6.close();
        conn.close();
        return true;
    }

    public ArrayList<cartera_clientes> MostrarxVencimientoClientePrincipalCuponesTodos(int cliente1, int moneda1, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.idcartera as id,cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cartera_clientes.vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "cartera_clientes.tasa,plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,1 AS tipodocumento,"
                    + "'PR' AS nombredocumento, 1 AS ncupon,"
                    + "1 AS ncantidad "
                    + " FROM cartera_clientes "
                    + " LEFT JOIN monedas "
                    + " ON cartera_clientes.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON cartera_clientes.titulo=titulos.codigo "
                    + " LEFT JOIN clientes "
                    + " ON cartera_clientes.comprador=clientes.codigo "
                    + " LEFT JOIN vendedores "
                    + " ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + " LEFT JOIN bancos_plaza "
                    + " ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + " WHERE IF(" + cliente1 + "<>0,cartera_clientes.comprador=" + cliente1 + ",TRUE)"
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND cartera_clientes.vencimiento BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " UNION "
                    + "SELECT cupones.idcupon AS id,cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cupones.fechavencimiento AS vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valorfuturo AS valor_inversion,"
                    + "cartera_clientes.tasa,cupones.plazocupon AS plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,2 AS tipodocumento,"
                    + "'CP' AS nombredocumento, cupones.ncupon,"
                    + "cupones.ncantidad "
                    + "FROM cartera_clientes "
                    + "LEFT JOIN monedas "
                    + "ON cartera_clientes.moneda=monedas.codigo "
                    + "LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + "LEFT JOIN titulos "
                    + "ON cartera_clientes.titulo=titulos.codigo "
                    + "LEFT JOIN clientes "
                    + "ON cartera_clientes.comprador=clientes.codigo "
                    + "LEFT JOIN vendedores "
                    + "ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + "LEFT JOIN bancos_plaza "
                    + "ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + "LEFT JOIN cupones "
                    + "ON cartera_clientes.creferencia=cupones.idprecierre "
                    + " WHERE IF(" + cliente1 + "<>0,cartera_clientes.comprador=" + cliente1 + ",TRUE)"
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND cupones.fechavencimiento BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND cartera_clientes.operacion=1 "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " ORDER BY tipodocumento,comprador,moneda,vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setBancopago(bco);
                    p.getBancopago().setNombre(rs.getString("nombrebanco"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setTipodocumento(rs.getInt("tipodocumento"));
                    p.setNombredocumento(rs.getString("nombredocumento"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setNcupon(rs.getInt("ncupon"));
                    p.setNcantidad(rs.getInt("ncantidad"));
                    p.setCreferencia(rs.getString("id"));
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

    public ArrayList<cartera_clientes> CarteraParaVenta(int cliente, int moneda, int noperacion) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.creferencia,cartera_clientes.idcartera,"
                    + "cartera_clientes.numero,negociado,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cartera_clientes.vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "emisores.nomalias,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "cartera_clientes.tasa,plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.periodopago "
                    + " FROM cartera_clientes "
                    + " LEFT JOIN monedas "
                    + " ON cartera_clientes.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON cartera_clientes.titulo=titulos.codigo "
                    + " LEFT JOIN clientes "
                    + " ON cartera_clientes.comprador=clientes.codigo "
                    + " LEFT JOIN vendedores "
                    + " ON cartera_clientes.asesorcompra=vendedores.codigo "
                    + " WHERE cartera_clientes.comprador=" + cliente
                    + " AND cartera_clientes.moneda=" + moneda
                    + " AND cartera_clientes.operacion=" + noperacion
                    + " AND cartera_clientes.estado= 1 "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE) "
                    + " ORDER BY moneda,operacion,fechacierre ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setCreferencia(rs.getString("creferencia"));
                    p.setIdcartera(rs.getString("idcartera"));
                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.getEmisor().setNomalias(rs.getString("nomalias"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setPeriodopago(rs.getInt("periodopago"));
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

    public boolean GrabarCarteraRentaFijaVendida(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "INSERT INTO cartera_clientes_ventas(creferencia,idcartera,"
                + "numero,negociado,"
                + "fecha,fechacierre,"
                + "fechaemision,"
                + "vencimiento,"
                + "comprador,asesorcompra,"
                + "operacion,tipooperacion,"
                + "mercado,moneda,"
                + "emisor,"
                + "titulo,"
                + "valor_nominal,"
                + "cantidad,"
                + "precio,"
                + "valor_inversion,"
                + "tasa,plazo,"
                + "tipoplazo,numerobolsa,"
                + "periodopago)"
                + " SELECT cartera_clientes.creferencia,cartera_clientes.idcartera,"
                + "cartera_clientes.numero,negociado,"
                + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                + "cartera_clientes.fechaemision,"
                + "cartera_clientes.vencimiento,"
                + "comprador,asesorcompra,"
                + "cartera_clientes.operacion,cartera_clientes.tipooperacion,"
                + "cartera_clientes.mercado,cartera_clientes.moneda,"
                + "cartera_clientes.emisor,"
                + "cartera_clientes.titulo,"
                + "cartera_clientes.valor_nominal,"
                + "cartera_clientes.cantidad,"
                + "cartera_clientes.precio,"
                + "cartera_clientes.valor_inversion,"
                + "cartera_clientes.tasa,cartera_clientes.plazo,"
                + "cartera_clientes.tipoplazo,cartera_clientes.numerobolsa,"
                + "cartera_clientes.periodopago "
                + " FROM cartera_clientes "
                + " WHERE cartera_clientes.idcartera='" + id + "'";

        PreparedStatement ps = conn.prepareStatement(sql);
        try {
            ps.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("-->ERROR EN VENTA DE CARTERA " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        conn.close();
        return true;
    }

    public boolean borrarCarteraVendida(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM cartera_clientes_ventas WHERE idcartera=?");
        ps.setString(1, id);
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

    public boolean ActualizarEstadoCarteraVendidaParcial(String id, double nimporte, int nmoneda) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        if (nmoneda == 1) {
            ps = st.getConnection().prepareStatement("UPDATE cartera_clientes SET valor_nominal=" + nimporte + ",valor_inversion=ROUND(precio*" + nimporte + "/100) WHERE idcartera=? ");
        } else {
            ps = st.getConnection().prepareStatement("UPDATE cartera_clientes SET valor_nominal=" + nimporte + ",valor_inversion=precio*" + nimporte + "/100 WHERE idcartera=? ");
        }
        ps.setString(1, id);
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

    public cartera_clientes BuscarxCarteraVendida(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        cartera_clientes p = new cartera_clientes();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.numero,cartera_clientes.idcartera,"
                    + "cartera_clientes.creferencia,cartera_clientes.estado "
                    + "FROM cartera_clientes "
                    + "WHERE cartera_clientes.idcartera=? "
                    + " ORDER BY cartera_clientes.idcartera ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    p.setNumero(rs.getDouble("numero"));
                    p.setCreferencia(rs.getString("creferencia"));
                    p.setIdcartera(rs.getString("idcartera"));
                    p.setEstado(rs.getInt("estado"));
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

    public boolean RetornarCarteraVendidaParcial(String id, double nimporte, int nmoneda) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        if (nmoneda == 1) {
            ps = st.getConnection().prepareStatement("UPDATE cartera_clientes SET valor_nominal=valor_nominal+" + nimporte + ",valor_inversion=ROUND(precio*valor_nominal/100) WHERE idcartera=? ");
        } else {
            ps = st.getConnection().prepareStatement("UPDATE cartera_clientes SET valor_nominal=valor_nominal+" + nimporte + ",valor_inversion=precio*valor_nominal/100 WHERE idcartera=? ");
        }
        ps.setString(1, id);
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

    public ArrayList<cartera_clientes> MostrarxVencimientoEmisorPrincipalCuponesAsesor(int emisor1, int moneda1, Date fechaini, Date fechafin, int nestado, int codasesor) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        int nValorEstado = nestado;
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT cartera_clientes.numero,negociado,clientes.asesor,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cartera_clientes.vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "cartera_clientes.tasa,plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,1 AS tipodocumento,"
                    + "'PRINCIPALES' AS nombredocumento, 1 AS ncupon,"
                    + "1 AS ncantidad "
                    + " FROM cartera_clientes "
                    + " LEFT JOIN monedas "
                    + " ON cartera_clientes.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON cartera_clientes.titulo=titulos.codigo "
                    + " LEFT JOIN clientes "
                    + " ON cartera_clientes.comprador=clientes.codigo "
                    + " LEFT JOIN vendedores "
                    + " ON clientes.asesor=vendedores.codigo "
                    + " LEFT JOIN bancos_plaza "
                    + " ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + " WHERE IF(" + emisor1 + "<>0,cartera_clientes.emisor=" + emisor1 + ",TRUE)"
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND IF(" + codasesor + "<>0,clientes.asesor=" + codasesor + ",TRUE)"
                    + " AND IF(" + nValorEstado + "<>0,cartera_clientes.estado=1,cartera_clientes.estado=0)"
                    + " AND cartera_clientes.vencimiento BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " UNION "
                    + "SELECT cartera_clientes.numero,negociado,clientes.asesor,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cupones.fechavencimiento AS vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valorfuturo AS valor_inversion,"
                    + "cartera_clientes.tasa,cupones.plazocupon AS plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,2 AS tipodocumento,"
                    + "'CUPONES' AS nombredocumento, cupones.ncupon,"
                    + "cupones.ncantidad "
                    + "FROM cartera_clientes "
                    + "LEFT JOIN monedas "
                    + "ON cartera_clientes.moneda=monedas.codigo "
                    + "LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + "LEFT JOIN titulos "
                    + "ON cartera_clientes.titulo=titulos.codigo "
                    + "LEFT JOIN clientes "
                    + "ON cartera_clientes.comprador=clientes.codigo "
                    + "LEFT JOIN vendedores "
                    + "ON clientes.asesor=vendedores.codigo "
                    + "LEFT JOIN bancos_plaza "
                    + "ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + "LEFT JOIN cupones "
                    + "ON cartera_clientes.creferencia=cupones.idprecierre "
                    + " WHERE IF(" + emisor1 + "<>0,cartera_clientes.emisor=" + emisor1 + ",TRUE)"
                    + " AND IF(" + moneda1 + "<>0,cartera_clientes.moneda=" + moneda1 + ",TRUE)"
                    + " AND cupones.fechavencimiento BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " AND IF(" + codasesor + "<>0,clientes.asesor=" + codasesor + ",TRUE)"
                    + " AND IF(" + nValorEstado + "<>0,cupones.estado=1,TRUE) "
                    + " ORDER BY asesor,moneda,vencimiento ";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setBancopago(bco);
                    p.getBancopago().setNombre(rs.getString("nombrebanco"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setTipodocumento(rs.getInt("tipodocumento"));
                    p.setNombredocumento(rs.getString("nombredocumento"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setNcupon(rs.getInt("ncupon"));
                    p.setNcantidad(rs.getInt("ncantidad"));
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

    public ArrayList<cartera_clientes> MostrarxVencimientoEmisorPrincipalAsesor(int emisor1, int emisor2, int moneda1, int moneda2, Date fechaini, Date fechafin, int nestado, int codasesor) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT cartera_clientes.numero,negociado,clientes.asesor,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cartera_clientes.vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valor_inversion,"
                    + "cartera_clientes.tasa,plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,1 AS tipodocumento,"
                    + "'PRINCIPALES' AS nombredocumento, 1 AS ncupon,"
                    + "1 AS ncantidad "
                    + " FROM cartera_clientes "
                    + " LEFT JOIN monedas "
                    + " ON cartera_clientes.moneda=monedas.codigo "
                    + " LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + " LEFT JOIN titulos "
                    + " ON cartera_clientes.titulo=titulos.codigo "
                    + " LEFT JOIN clientes "
                    + " ON cartera_clientes.comprador=clientes.codigo "
                    + " LEFT JOIN vendedores "
                    + " ON clientes.asesor=vendedores.codigo "
                    + " LEFT JOIN bancos_plaza "
                    + " ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + " WHERE IF(?<>0,cartera_clientes.emisor=?,TRUE)"
                    + " AND IF(?<>0,cartera_clientes.moneda=?,TRUE)"
                    + " AND cartera_clientes.vencimiento BETWEEN ? AND ? "
                    + " AND IF(" + codasesor + "<>0,clientes.asesor=" + codasesor + ",TRUE)"
                    + " AND IF(" + nestado + "<>0,cartera_clientes.estado=1,TRUE)"
                    + " AND operacion=1  "
                    + "ORDER BY asesor,moneda,vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, emisor1);
                ps.setInt(2, emisor2);
                ps.setInt(3, moneda1);
                ps.setInt(4, moneda2);
                ps.setDate(5, fechaini);
                ps.setDate(6, fechafin);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setBancopago(bco);
                    p.getBancopago().setNombre(rs.getString("nombrebanco"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setTipodocumento(rs.getInt("tipodocumento"));
                    p.setNombredocumento(rs.getString("nombredocumento"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setNcupon(rs.getInt("ncupon"));
                    p.setNcantidad(rs.getInt("ncantidad"));
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

    public ArrayList<cartera_clientes> MostrarxVencimientoEmisorCuponesAsesor(int emisor1, int emisor2, int moneda1, int moneda2, Date fechaini, Date fechafin, int nestado, int codasesor) throws SQLException {
        ArrayList<cartera_clientes> lista = new ArrayList<cartera_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT cartera_clientes.numero,negociado,clientes.asesor,"
                    + "cartera_clientes.fecha,cartera_clientes.fechacierre,"
                    + "cartera_clientes.fechaemision,"
                    + "cupones.fechavencimiento AS vencimiento,"
                    + "comprador,asesorcompra,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.comitente,vendedores.nombre AS nombreasesor,"
                    + "cartera_clientes.operacion,tipooperacion,"
                    + "cartera_clientes.mercado,cartera_clientes.moneda,"
                    + "monedas.nombre AS nombremoneda,"
                    + "emisor,emisores.nombre AS nombreemisor,"
                    + "titulo,titulos.nombre AS nombretitulo,"
                    + "titulos.nomalias AS aliastitulo,"
                    + "valor_nominal,cantidad,precio,valorfuturo AS valor_inversion,"
                    + "cartera_clientes.tasa,cupones.plazocupon AS plazo,tipoplazo,numerobolsa,"
                    + "cartera_clientes.bancopago,cartera_clientes.cuentapago,"
                    + "bancos_plaza.nombre AS nombrebanco,"
                    + "cartera_clientes.periodopago,2 AS tipodocumento,"
                    + "'CUPONES' AS nombredocumento, cupones.ncupon,"
                    + "cupones.ncantidad "
                    + "FROM cartera_clientes "
                    + "LEFT JOIN monedas "
                    + "ON cartera_clientes.moneda=monedas.codigo "
                    + "LEFT JOIN emisores "
                    + " ON cartera_clientes.emisor=emisores.codigo "
                    + "LEFT JOIN titulos "
                    + "ON cartera_clientes.titulo=titulos.codigo "
                    + "LEFT JOIN clientes "
                    + "ON cartera_clientes.comprador=clientes.codigo "
                    + "LEFT JOIN vendedores "
                    + "ON clientes.asesor=vendedores.codigo "
                    + "LEFT JOIN bancos_plaza "
                    + "ON cartera_clientes.bancopago=bancos_plaza.codigo "
                    + "LEFT JOIN cupones "
                    + "ON cartera_clientes.creferencia=cupones.idprecierre "
                    + " WHERE IF(?<>0,cartera_clientes.emisor=?,TRUE) "
                    + " AND IF(?<>0,cartera_clientes.moneda=?,TRUE) "
                    + " AND cupones.fechavencimiento BETWEEN ? AND ? "
                    + " AND IF(" + codasesor + "<>0,clientes.asesor=" + codasesor + ",TRUE)"
                    + "AND cartera_clientes.operacion=1 "
                    + " AND IF(" + nestado + "<>0,cupones.estado=1,TRUE)"
                    + " ORDER BY asesor,cartera_clientes.moneda,cupones.fechavencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, emisor1);
                ps.setInt(2, emisor2);
                ps.setInt(3, moneda1);
                ps.setInt(4, moneda2);
                ps.setDate(5, fechaini);
                ps.setDate(6, fechafin);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    cartera_clientes p = new cartera_clientes();
                    moneda mo = new moneda();
                    titulo ti = new titulo();
                    emisor em = new emisor();
                    cliente cl = new cliente();
                    bancoplaza bco = new bancoplaza();
                    vendedor ve = new vendedor();

                    p.setNumero(rs.getDouble("numero"));
                    p.setNegociado(rs.getInt("negociado"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setFechacierre(rs.getDate("fechacierre"));
                    p.setFechaemision(rs.getDate("fechaemision"));
                    p.setVencimiento(rs.getDate("vencimiento"));
                    p.setComprador(cl);
                    p.getComprador().setCodigo(rs.getInt("comprador"));
                    p.getComprador().setNombre(rs.getString("nombrecliente"));
                    p.getComprador().setComitente(rs.getInt("comitente"));
                    p.setAsesorcompra(ve);
                    p.getAsesorcompra().setCodigo(rs.getInt("asesorcompra"));
                    p.getAsesorcompra().setNombre(rs.getString("nombreasesor"));
                    p.setOperacion(rs.getInt("operacion"));
                    p.setTipooperacion(rs.getInt("tipooperacion"));
                    p.setMercado(rs.getInt("mercado"));
                    p.setMoneda(mo);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(em);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setTitulo(ti);
                    p.getTitulo().setCodigo(rs.getInt("titulo"));
                    p.getTitulo().setNombre(rs.getString("nombretitulo"));
                    p.getTitulo().setNomalias(rs.getString("aliastitulo"));
                    p.setBancopago(bco);
                    p.getBancopago().setNombre(rs.getString("nombrebanco"));
                    p.setCuentapago(rs.getString("cuentapago"));
                    p.setValor_nominal(rs.getBigDecimal("valor_nominal"));
                    p.setCantidad(rs.getBigDecimal("cantidad"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setValor_inversion(rs.getBigDecimal("valor_inversion"));
                    p.setTasa(rs.getBigDecimal("tasa"));
                    p.setPlazo(rs.getInt("plazo"));
                    p.setTipoplazo(rs.getInt("tipoplazo"));
                    p.setNumerobolsa(rs.getString("numerobolsa"));
                    p.setTipodocumento(rs.getInt("tipodocumento"));
                    p.setNombredocumento(rs.getString("nombredocumento"));
                    p.setPeriodopago(rs.getInt("periodopago"));
                    p.setNcupon(rs.getInt("ncupon"));
                    p.setNcantidad(rs.getInt("ncantidad"));
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

    public boolean EliminarCartera(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM cartera_clientes WHERE idcartera=?");
        ps.setString(1, id);
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

}

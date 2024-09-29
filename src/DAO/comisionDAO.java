/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.UUID;
import Conexion.Conexion;
import Modelo.emisor;
import Modelo.liquidacion;
import Modelo.cliente;
import Modelo.comision;
import Modelo.configuracion;
import java.sql.Date;
import Modelo.moneda;
import Modelo.vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;

/**
 *
 * @author Fais
 */
public class comisionDAO {

    String concepto1;
    String concepto2;
    Conexion con = null;
    Statement st = null;
    Connection conn = null;

    public comision InsertarComision(comision co) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO comisiones("
                + "idtransaccion,idliquidacion,"
                + "numerobolsa,fecha,"
                + "emisor,renta,"
                + "cliente,moneda,"
                + "punta,asesor,"
                + "valor_inversion,porcentajeiva,"
                + "comision,monto,"
                + "montoiva,totales,saldo) VALUES(?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?)", Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, co.getIdtransaccion());
        ps.setString(2, co.getIdliquidacion());
        ps.setDouble(3, co.getNumerobolsa());
        ps.setDate(4, co.getFecha());
        ps.setInt(5, co.getEmisor().getCodigo());
        ps.setInt(6, co.getRenta());
        ps.setInt(7, co.getCliente().getCodigo());
        ps.setInt(8, co.getMoneda().getCodigo());
        ps.setInt(9, co.getPunta());
        ps.setInt(10, co.getAsesor().getCodigo());
        ps.setDouble(11, co.getValor_inversion());
        ps.setDouble(12, co.getPorcentajeiva());
        ps.setDouble(13, co.getComision());
        ps.setDouble(14, co.getMonto());
        ps.setDouble(15, co.getMontoiva());
        ps.setDouble(16, co.getTotales());
        ps.setDouble(17, co.getTotales());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> LIQUIDACION DE COMISIONES " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        conn.close();
        return co;

    }

    public comision CalcularComision(String id, int npunta, int nmoneda, int nasesor) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        comision p = new comision();
        UUID UUI = new UUID();

        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = new configuracion();
        config = configDAO.configBursatil();

        if (nmoneda == 1) {
            concepto1 = config.getConceptobolsa();
            concepto2 = config.getConceptofondogarantia();
        } else {
            concepto1 = config.getConceptobolsausd();
            concepto2 = config.getConceptofondogarantiausd();
        }
        double ivaporcentaje = config.getPorcentajeiva();

        Connection conn = st.getConnection();
        try {

            String sql = "SELECT liquidaciones.idtransaccion,liquidaciones.idliquidacion,"
                    + "liquidaciones.numerobolsa,"
                    + "liquidaciones.fecha,emisor,renta,liquidaciones.cliente,liquidaciones.moneda,"
                    + "liquidaciones.tipo  AS punta,concepto,liquidaciones.cantidad,liquidaciones.precio,valor_inversion,"
                    + "porcentajeiva,arancel,sum(monto) AS totalmonto,montoiva,totales,"
                    + "emisores.nombre AS nombreemisor,"
                    + "clientes.nombre AS nombrecliente,"
                    + "monedas.nombre AS nombremoneda,"
                    + "productos.nombre AS nombreproducto "
                    + "FROM liquidaciones "
                    + "LEFT JOIN emisores "
                    + "ON emisores.codigo=liquidaciones.emisor "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=liquidaciones.cliente "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=liquidaciones.moneda "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=liquidaciones.concepto "
                    + " WHERE liquidaciones.idtransaccion='" + id + "'"
                    + " AND liquidaciones.moneda=" + nmoneda
                    + " AND liquidaciones.tipo=" + npunta
                    + " AND liquidaciones.concepto NOT IN ('" + concepto1.trim() + "','" + concepto2.trim() + "')"
                    + "  GROUP BY liquidaciones.idtransaccion "
                    + " ORDER BY liquidaciones.idtransaccion";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    String idliquidacion = UUID.crearUUID();
                    idliquidacion = idliquidacion.substring(1, 25);

                    moneda m = new moneda();
                    vendedor a = new vendedor();
                    emisor e = new emisor();
                    cliente cl = new cliente();
                    vendedor ve = new vendedor();
                    vendedorDAO veDAO = new vendedorDAO();
                    ve = veDAO.buscarId(nasesor);
                    p.setEmisor(e);
                    p.setCliente(cl);
                    p.setMoneda(m);
                    p.setAsesor(ve);
                    p.getAsesor().setCodigo(ve.getCodigo());
                    if (npunta == 1) {
                        p.setComision(ve.getComisioncontado());
                    } else {
                        p.setComision(ve.getComisioncredito());
                    }

                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getCliente().setCodigo(rs.getInt("cliente"));

                    p.setIdtransaccion(rs.getString("idtransaccion"));
                    p.setIdliquidacion(idliquidacion);
                    p.setFecha(rs.getDate("fecha"));
                    p.setNumerobolsa(rs.getDouble("numerobolsa"));
                    p.setRenta(rs.getInt("renta"));
                    p.setPunta(rs.getInt("punta"));

                    p.setCantidad(rs.getDouble("cantidad"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setValor_inversion(rs.getDouble("valor_inversion"));
                    p.setPorcentajeiva(ivaporcentaje);
                    double nMontoTotal = rs.getDouble("totalmonto");
                    double nMonto = 0;
                    double nImporteIva = 0;
                    if (nmoneda == 1) {
                        nMonto = Math.round(nMontoTotal * p.getComision() / 100);
                        nImporteIva = Math.round(nMonto * ivaporcentaje / 100);
                    } else {
                        nMonto = (nMontoTotal * p.getComision() / 100);
                        nImporteIva = (nMonto * ivaporcentaje / 100);
                    }
                    p.setMonto(nMonto);
                    p.setMontoiva(nImporteIva);
                    p.setTotales(nMonto + nImporteIva);
                    p.setSaldo(nMonto + nImporteIva);
                    if (nMonto != 0) {
                        this.InsertarComision(p);
                    }
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

    public void eliminarComision(String cReferencia) throws SQLException {

        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM comisiones WHERE idtransaccion=?");
        ps.setString(1, cReferencia);
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> BORRADO COMISION DE ASESORES BURSATILES " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
    }

    public ArrayList<comision> MostrarComisiones(String id) throws SQLException {
        ArrayList<comision> lista = new ArrayList<comision>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT comisiones.punta,comisiones.comision,"
                    + "comisiones.monto,comisiones.montoiva,"
                    + "comisiones.totales,"
                    + "comisiones.asesor,vendedores.nombre as nombreasesor,"
                    + "CASE comisiones.punta"
                    + "  WHEN 1 THEN 'Compra'"
                    + "  WHEN 2 THEN 'Venta'"
                    + "END AS observacion "
                    + "FROM comisiones "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=comisiones.asesor "
                    + "WHERE comisiones.idtransaccion='" + id + "'";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    comision c = new comision();
                    vendedor ve = new vendedor();

                    c.setAsesor(ve);
                    c.getAsesor().setCodigo(rs.getInt("asesor"));
                    c.getAsesor().setNombre(rs.getString("nombreasesor"));

                    c.setPunta(rs.getInt("punta"));
                    c.setComision(rs.getDouble("comision"));
                    c.setMonto(rs.getDouble("monto"));
                    c.setMontoiva(rs.getDouble("montoiva"));
                    c.setTotales(rs.getDouble("totales"));
                    c.setObservacion(rs.getString("observacion"));
                    lista.add(c);
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

    public ArrayList<comision> MostrarComisionesxAsesor(int nasesor, int nmoneda, Date fechainicio, Date fechafin) throws SQLException {
        ArrayList<comision> lista = new ArrayList<comision>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT comisiones.numerobolsa,precierre_operacion.numerobolsa AS numerooperacion,"
                    + "vendedores.nombre AS nombreasesor,"
                    + "comisiones.fecha,precierre_operacion.comprador AS cliente,"
                    + "clientes.nombre AS nombrecliente, 'COMPRA' AS movimiento,"
                    + "emisores.nombre AS nombreemisor,titulos.nomalias,"
                    + "titulos.nombre AS nombretitulo,"
                    + "IF(precierre_operacion.mercado=1,'PRIMARIO','SECUNDARIO') AS tipomercado,"
                    + "monedas.nombre AS nombremoneda,precierre_operacion.valor_nominal,"
                    + "precierre_operacion.comcomprador AS porcentajecomisioncobrada,"
                    + "comisiones.totales AS comisionasesor "
                    + "FROM comisiones "
                    + "LEFT JOIN precierre_operacion "
                    + "ON precierre_operacion.numero=comisiones.numerobolsa "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=precierre_operacion.comprador "
                    + "LEFT JOIN emisores "
                    + "ON emisores.codigo=precierre_operacion.emisor "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=precierre_operacion.moneda "
                    + "LEFT JOIN titulos "
                    + "ON titulos.codigo=precierre_operacion.titulo "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=precierre_operacion.asesorcompra "
                    + "WHERE precierre_operacion.asesorcompra= "+nasesor
                    + " AND comisiones.fecha BETWEEN '"+fechainicio+"' AND '"+fechafin +"' "
                    + " AND precierre_operacion.moneda="+nmoneda
                    + " UNION "
                    + "SELECT comisiones.numerobolsa,precierre_operacion.numerobolsa AS numerooperacion,comisiones.fecha,"
                    + "vendedores.nombre AS nombreasesor,"
                    + "precierre_operacion.vendedor AS cliente,"
                    + "clientes.nombre AS nombrecliente, 'VENTA' AS movimiento,"
                    + "emisores.nombre AS nombreemisor,titulos.nomalias,"
                    + "titulos.nombre AS nombretitulo,"
                    + "IF(precierre_operacion.mercado=1,'PRIMARIO','SECUNDARIO') AS tipomercado,"
                    + "monedas.nombre AS nombremoneda,precierre_operacion.valor_nominal,"
                    + "precierre_operacion.comvendedor AS porcentajecomisioncobrada,"
                    + "comisiones.totales AS comisionasesor "
                    + "FROM comisiones "
                    + "LEFT JOIN precierre_operacion "
                    + "ON precierre_operacion.numero=comisiones.numerobolsa "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=precierre_operacion.vendedor "
                    + "LEFT JOIN emisores "
                    + "ON emisores.codigo=precierre_operacion.emisor "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=precierre_operacion.moneda "
                    + "LEFT JOIN titulos "
                    + "ON titulos.codigo=precierre_operacion.titulo "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=precierre_operacion.asesorventa "
                    + "WHERE precierre_operacion.asesorventa="+nasesor
                    + " AND comisiones.fecha BETWEEN '"+fechainicio+"' AND '"+fechafin +"' "
                    + " AND precierre_operacion.moneda="+nmoneda
                    + " ORDER BY fecha ";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    comision c = new comision();
                    vendedor ve = new vendedor();
                    emisor em = new emisor();
                    moneda mo = new moneda();

                    c.setMoneda(mo);
                    c.setNumerobolsa(rs.getDouble("numerobolsa"));
                    c.setFecha(rs.getDate("fecha"));
                    c.getMoneda().setNombre(rs.getString("nombremoneda"));
                    c.setEmisor(em);
                    c.getEmisor().setNombre(rs.getString("nombreemisor"));
                    c.setAsesor(ve);
                    c.getAsesor().setNombre(rs.getString("nombreasesor"));
                    c.setValor_nominal(rs.getDouble("valor_nominal"));
                    c.setTotales(rs.getDouble("comisionasesor"));
                    lista.add(c);
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

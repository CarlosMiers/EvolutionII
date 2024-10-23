/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.casa;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.cuenta_clientes;
import Modelo.detalle_descuento_documento;
import Modelo.carrera;
import Modelo.edificio;
import Modelo.giraduria;
import Modelo.inmueble;
import Modelo.moneda;
import Modelo.obra;
import Modelo.producto;
import Modelo.propietario;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author SERVIDOR
 */
public class cuenta_clienteDAO {

    double nMora, nAlquiler = 0;
    double nGarage, nComision, nExpensa = 0;
    double nGastos, nPunitorio, nSaldo = 0;
    Conexion con = null;
    Statement st = null;
    double ncredito = 0;
    double ndebito = 0;
    double saldoanterior = 0;

    public ArrayList<cuenta_clientes> MostrarxCliente(int cliente) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
            cSql = cSql + "giradurias.nombre as nombregiraduria ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + "AND cuenta_clientes.cliente= ? ";
            cSql = cSql + " ORDER by cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        conn.close();
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxClienteMoneda(int cliente, int moneda) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "giradurias.nombre as nombregiraduria ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + "AND cuenta_clientes.cliente= ? ";
            cSql = cSql + "AND cuenta_clientes.moneda= ? ";
            cSql = cSql + " ORDER by cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ps.setInt(2, moneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));

                    lista.add(cc);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        conn.close();
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxGiraduria(int ngiraduria, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,cuenta_clientes.fecha_pago,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,";
            cSql = cSql + "giradurias.nombre as nombregiraduria,cuenta_clientes.comercial,casas.nombre as nombrecomercial ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + " WHERE cuenta_clientes.saldo>0 ";
            cSql = cSql + " AND cuenta_clientes.giraduria= ? ";
            cSql = cSql + " AND cuenta_clientes.vencimiento between ? AND ?";
            cSql = cSql + " ORDER by cuenta_clientes.cliente,cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ngiraduria);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    casa casa = new casa();

                    cc.setComercial(casa);
                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercial"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxGiraduriaxRango(int ngiraduria, Date fechai, Date fechaf) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
            cSql = cSql + "giradurias.nombre as nombregiraduria,cuenta_clientes.comercial,casas.nombre as nombrecomercial, ";
            cSql = cSql + "cuenta_clientes.amortiza,cuenta_clientes.minteres ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + " WHERE cuenta_clientes.saldo>0 ";
            cSql = cSql + " AND cuenta_clientes.giraduria=" + ngiraduria;
            cSql = cSql + " AND cuenta_clientes.vencimiento between '" + fechai + "'  AND '" + fechaf + "'";
            cSql = cSql + " AND clientes.estado=1 and cuenta_clientes.refinanciado=0 ";
            cSql = cSql + " UNION ALL ";
            cSql = cSql + "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
            cSql = cSql + "giradurias.nombre as nombregiraduria,cuenta_clientes.comercial,casas.nombre as nombrecomercial, ";
            cSql = cSql + "cuenta_clientes.amortiza,cuenta_clientes.minteres ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + " WHERE cuenta_clientes.saldo>0 ";
            cSql = cSql + " AND cuenta_clientes.giraduria=" + ngiraduria;
            cSql = cSql + " AND cuenta_clientes.vencegracia between '" + fechai + "'  AND '" + fechaf + "'";;
            cSql = cSql + " AND clientes.estado=1 and cuenta_clientes.refinanciado=1 ";
            cSql = cSql + " ORDER by cliente,vencimiento";

            System.out.println(cSql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    casa casa = new casa();
                    cc.setComercial(casa);
                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercial"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> CONSULTA DESDE CUENTA CLIENTES " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxGiraduriaxVence(int ngiraduria, Date fechaini) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
            cSql = cSql + "giradurias.nombre as nombregiraduria,cuenta_clientes.comercial,casas.nombre as nombrecomercial ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + " WHERE cuenta_clientes.saldo>0 ";
            cSql = cSql + " AND cuenta_clientes.giraduria= ? ";
            cSql = cSql + " AND cuenta_clientes.vencimiento<= ?";
            cSql = cSql + " ORDER by cuenta_clientes.cliente,cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ngiraduria);
                ps.setDate(2, fechaini);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    casa casa = new casa();
                    cc.setComercial(casa);
                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercial"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxMes(int ngiraduria, int nmes, int nanual) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "giradurias.nombre as nombregiraduria,cuenta_clientes.comercial,casas.nombre as nombrecomercial ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + " WHERE cuenta_clientes.saldo>0 ";
            cSql = cSql + " AND cuenta_clientes.giraduria= ? ";
            cSql = cSql + " AND MONTH(cuenta_clientes.vencimiento)= ? ";
            cSql = cSql + " AND YEAR(cuenta_clientes.vencimiento)= ? ";
            cSql = cSql + " AND clientes.estado=1 ";
            cSql = cSql + " ORDER by cuenta_clientes.cliente,cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ngiraduria);
                ps.setInt(2, nmes);
                ps.setInt(3, nanual);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    casa casa = new casa();

                    cc.setComercial(casa);
                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercial"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarAunaFecha(Date dfecha, int nmoneda, int ncomprobante) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT cta.creferencia,cta.iddocumento,cta.documento,"
                    + "cta.fecha,cta.cliente,cta.fecha,cta.comprobante,clientes.nombre,"
                    + "monedas.nombre as nombremoneda,cta.moneda,"
                    + "clientes.cedula,comprobantes.nombre as nombrecomprobante,"
                    + "comprobantes.nomalias,"
                    + "SUM(cta.importe) AS totalcredito,"
                    + "COALESCE(p.cobrosxcaja,0) AS cobrosxcaja,"
                    + "COALESCE(pre.factura,0) AS factura "
                    + "FROM cuenta_clientes cta "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=cta.cliente "
                    + " INNER JOIN comprobantes "
                    + "ON comprobantes.codigo=cta.comprobante "
                    + "INNER JOIN monedas "
                    + "ON monedas.codigo=cta.moneda "
                    + "LEFT JOIN "
                    + "(SELECT cuenta_clientes.creferencia, "
                    + "COALESCE(SUM(detalle_cobranzas.pago-detalle_cobranzas.punitorio-detalle_cobranzas.mora-detalle_cobranzas.gastos_cobranzas),0) "
                    + " AS cobrosxcaja "
                    + "from detalle_cobranzas "
                    + " LEFT JOIN cobranzas "
                    + "on cobranzas.idpagos=detalle_cobranzas.iddetalle "
                    + " LEFT JOIN cuenta_clientes "
                    + " ON cuenta_clientes.iddocumento=detalle_cobranzas.idfactura "
                    + " WHERE cobranzas.fecha<='" + dfecha + "'"
                    + " GROUP BY cuenta_clientes.creferencia) "
                    + " p ON p.creferencia=cta.creferencia "
                    + " LEFT JOIN (SELECT cuenta_clientes.creferencia,cabecera_ventas.factura AS factura "
                    + " FROM cabecera_ventas "
                    + " INNER JOIN prestamos "
                    + " ON prestamos.idprestamos=cabecera_ventas.creferencia "
                    + " INNER JOIN cuenta_clientes "
                    + " ON cuenta_clientes.creferencia=cabecera_ventas.creferencia "
                    + " WHERE prestamos.fecha<='" + dfecha + "'"
                    + " GROUP BY cuenta_clientes.creferencia) pre "
                    + " ON pre.creferencia=cta.creferencia "
                    + " WHERE cta.fecha<='" + dfecha + "'"
                    + " and cta.moneda=" + nmoneda
                    + " and cta.comprobante=" + ncomprobante
                    + " GROUP BY cta.creferencia "
                    + " HAVING(totalcredito>0) "
                    + " ORDER by cta.comprobante,cta.cliente ";

            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    cliente c = new cliente();
                    cuenta_clientes cc = new cuenta_clientes();
                    comprobante com = new comprobante();
                    cc.setCliente(c);
                    cc.setComprobante(com);
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombre"));
                    cc.getCliente().setCedula(rs.getString("cedula"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setFactura(rs.getString("factura"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setImporte(rs.getBigDecimal("totalcredito"));
                    cc.setPagos(rs.getBigDecimal("cobrosxcaja"));
                    cc.setFactura(rs.getString("factura"));

                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxPlazo(int nmoneda, int ncomprobante, Date dVencido) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT  clientes.ruc,cuenta_clientes.cliente,clientes.nombre AS nombrecliente,cuenta_clientes.moneda,";
            cSql += "SUM(cuenta_clientes.saldo) AS totalmora,cuenta_clientes.comprobante,";
            cSql += "SUM(  IF( DATEDIFF(CURDATE(),vencimiento) BETWEEN 1 AND 30 ,  cuenta_clientes.saldo, 0 ) ) AS 30dias,";
            cSql += "SUM(  IF( DATEDIFF(CURDATE(),vencimiento) BETWEEN 31 AND 60 ,  cuenta_clientes.saldo, 0 ) ) AS 60dias,";
            cSql += "SUM(  IF( DATEDIFF(CURDATE(),vencimiento) BETWEEN 61 AND 90 ,  cuenta_clientes.saldo, 0 ) ) AS 90dias,";
            cSql += "SUM(  IF( DATEDIFF(CURDATE(),vencimiento) BETWEEN 91 AND 180 ,  cuenta_clientes.saldo, 0 ) ) AS 180dias,";
            cSql += "SUM(  IF( DATEDIFF(CURDATE(),vencimiento)>180, cuenta_clientes.saldo, 0 ) ) AS Masde180,";
            cSql += "comprobantes.nombre AS nombrecomprobante,monedas.nombre AS nombremoneda ";
            cSql += "FROM clientes ";
            cSql += "INNER JOIN cuenta_clientes ";
            cSql += "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql += "INNER JOIN comprobantes ";
            cSql += "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql += "INNER JOIN monedas ";
            cSql += "ON monedas.codigo= cuenta_clientes.moneda ";
            cSql += " WHERE cuenta_clientes.moneda = ? ";
            cSql += " AND cuenta_clientes.comprobante = ? ";
            cSql += " AND vencimiento< ?";
            cSql += " GROUP BY clientes.codigo ";
            cSql += " HAVING(totalmora)>0 ";
            cSql += " ORDER BY clientes.codigo";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nmoneda);
                ps.setInt(2, ncomprobante);
                ps.setDate(3, dVencido);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    moneda mo = new moneda();
                    cc.setMoneda(mo);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.getMoneda().setCodigo(rs.getInt("moneda"));
                    cc.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setN30dias(rs.getBigDecimal("30dias"));
                    cc.setN60dias(rs.getBigDecimal("60dias"));
                    cc.setN90dias(rs.getBigDecimal("90dias"));
                    cc.setN180dias(rs.getBigDecimal("180dias"));
                    cc.setMasde180dias(rs.getBigDecimal("Masde180"));
                    cc.setSaldo(rs.getBigDecimal("totalmora"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public cuenta_clientes InsertarCuenta(cuenta_clientes c) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("INSERT INTO cuenta_clientes(iddocumento,creferencia,documento,fecha,vencimiento,cliente,sucursal,moneda,comprobante,vendedor,importe,numerocuota,cuota,saldo) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, c.getIddocumento());
        ps.setString(2, c.getCreferencia());
        ps.setString(3, c.getDocumento());
        ps.setDate(4, c.getFecha());
        ps.setDate(5, c.getFecha());
        ps.setInt(6, c.getCliente().getCodigo());
        ps.setInt(7, c.getSucursal());
        ps.setInt(8, c.getMoneda().getCodigo());
        ps.setInt(9, c.getComprobante().getCodigo());
        ps.setInt(10, c.getVendedor());
        ps.setBigDecimal(11, c.getImporte());
        ps.setInt(12, c.getNumerocuota());
        ps.setInt(13, c.getCuota());
        ps.setBigDecimal(14, c.getSaldo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return c;
    }

    public cuenta_clientes ActualizarDebitoAutomaticoCuenta(cuenta_clientes cta) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;

        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cuenta_clientes SET giraduria=?,nrocuenta=?,autorizacion=? WHERE iddocumento= '" + cta.getIddocumento() + "'");
        ps.setInt(1, cta.getGiraduria().getCodigo());
        ps.setString(2, cta.getNrocuenta());
        ps.setString(3, cta.getAutorizacion());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return cta;
    }

    public ArrayList<cuenta_clientes> MostrarSaldoxSocioVencimiento(int cliente, Date dInicio) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String cSql = "CREATE TEMPORARY TABLE Saldos (";
        cSql = cSql + "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
        cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
        cSql = cSql + "cuenta_clientes.importe AS importecuota,cuenta_clientes.cliente,cuenta_clientes.saldo AS saldocuota,cuenta_clientes.tasaoperativa,";
        cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota AS nrocuota,cuenta_clientes.numerocuota,";
        cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
        cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
        cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
        cSql = cSql + "giradurias.nombre as nombregiraduria,clientes.salario,clientes.ruc,clientes.telefono,";
        cSql = cSql + "casas.nombre as nombrecomercio,000000000.00 as totaldeuda ";
        cSql = cSql + "FROM cuenta_clientes ";
        cSql = cSql + "INNER JOIN CLIENTES ";
        cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
        cSql = cSql + "INNER JOIN comprobantes ";
        cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
        cSql = cSql + "LEFT JOIN giradurias ";
        cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
        cSql = cSql + "LEFT JOIN casas ";
        cSql = cSql + "ON casas.codigo=cuenta_clientes.comercial ";
        cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
        cSql = cSql + " AND cuenta_clientes.cliente= " + cliente;
        cSql = cSql + " AND cuenta_clientes.vencimiento<='" + dInicio + "' ";
        cSql = cSql + " ORDER by cuenta_clientes.creferencia,cuenta_clientes.vencimiento)";
        System.out.println(cSql);
        PreparedStatement ps = conn.prepareStatement(cSql);
        ps.executeUpdate(cSql);

        String cSqlSaldos = "CREATE TEMPORARY TABLE SaldoActual ("
                + " SELECT creferencia,sum(saldo) AS total "
                + "FROM cuenta_clientes "
                + " WHERE cuenta_clientes.cliente= " + cliente
                + " GROUP BY cuenta_clientes.creferencia"
                + " ORDER BY cuenta_clientes.creferencia)";

        PreparedStatement pssaldos = conn.prepareStatement(cSqlSaldos);
        pssaldos.executeUpdate(cSqlSaldos);

        String sqlSaldoActual = "UPDATE saldos SET totaldeuda=(SELECT total FROM SaldoActual "
                + " WHERE SaldoActual.creferencia=saldos.creferencia "
                + " ORDER BY SaldoActual.creferencia)";

        System.out.println(sqlSaldoActual);

        PreparedStatement psActual = conn.prepareStatement(sqlSaldoActual);
        psActual.executeUpdate(sqlSaldoActual);

        String sqlDatos = "SELECT *"
                + " FROM Saldos ";

        PreparedStatement pscierre = conn.prepareStatement(sqlDatos);
        ResultSet rs = pscierre.executeQuery(sqlDatos);
        String cReferencia = "";
        while (rs.next()) {
            giraduria giraduria = new giraduria();
            cliente c = new cliente();
            casa ca = new casa();
            comprobante m = new comprobante();
            cuenta_clientes cc = new cuenta_clientes();

            cc.setGiraduria(giraduria);
            cc.setCliente(c);
            cc.setComprobante(m);
            cc.setComercial(ca);

            cc.setCreferencia(rs.getString("creferencia"));
            cc.setIddocumento(rs.getString("iddocumento"));
            cc.setFecha(rs.getDate("fecha"));
            cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
            cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
            cc.setDocumento(rs.getString("documento"));
            cc.setVencimiento(rs.getDate("vencimiento"));
            cc.setImporte(rs.getBigDecimal("importecuota"));
            cc.getCliente().setCodigo(rs.getInt("cliente"));
            cc.getCliente().setNombre(rs.getString("nombrecliente"));
            cc.getComprobante().setCodigo(rs.getInt("comprobante"));
            cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
            cc.getComercial().setNombre(rs.getString("nombrecomercio"));

            cc.setSaldo(rs.getBigDecimal("saldocuota"));
            if (!cReferencia.equals(rs.getString("creferencia"))) {
                cc.setPagos(rs.getBigDecimal("totaldeuda"));
                cReferencia=rs.getString("creferencia");
            } else {
                cc.setPagos(new BigDecimal("0.00"));
            }
            cc.setAutorizacion(rs.getString("autorizacion"));
            cc.setNrocuenta(rs.getString("nrocuenta"));
            cc.setCuota(rs.getInt("nrocuota"));
            cc.setNumerocuota(rs.getInt("numerocuota"));
            cc.setPlazo(rs.getInt("numerocuota"));
            lista.add(cc);
        }
        ps.close();

        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarSaldoxSocio(int cliente, Date dInicio) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "MIN(cuenta_clientes.importe) AS importecuota,cuenta_clientes.cliente,MIN(cuenta_clientes.saldo) AS saldocuota,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,MIN(cuenta_clientes.cuota) AS nrocuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
            cSql = cSql + "giradurias.nombre as nombregiraduria,clientes.salario,clientes.ruc,clientes.telefono,";
            cSql = cSql + "casas.nombre as nombrecomercio,SUM(cuenta_clientes.saldo) as totaldeuda ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " AND cuenta_clientes.cliente= ? ";
            cSql = cSql + " AND cuenta_clientes.fecha<= ? ";
            cSql = cSql + " GROUP BY cuenta_clientes.creferencia ";
            cSql = cSql + " ORDER by cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ps.setDate(2, dInicio);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importecuota"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("totaldeuda"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("nrocuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public boolean guardarCuenta(String detallecuota) throws SQLException {
        boolean guardacuota = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectacuota = st.getConnection();
        conectacuota.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detallecuota);
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
                            + "comprobante,"
                            + "vendedor,"
                            + "importe,"
                            + "numerocuota,"
                            + "cuota,"
                            + "saldo"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddocumento").getAsString());
                        ps.setString(2, obj.get("creferencia").getAsString());
                        ps.setString(3, obj.get("documento").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("vencimiento").getAsString());
                        ps.setInt(6, obj.get("cliente").getAsInt());
                        ps.setInt(7, obj.get("sucursal").getAsInt());
                        ps.setInt(8, obj.get("moneda").getAsInt());
                        ps.setInt(9, obj.get("comprobante").getAsInt());
                        ps.setInt(10, obj.get("vendedor").getAsInt());
                        ps.setString(11, obj.get("importe").getAsString());
                        ps.setInt(12, obj.get("numerocuota").getAsInt());
                        ps.setInt(13, obj.get("cuota").getAsInt());
                        ps.setString(14, obj.get("saldo").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardacuota = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardacuota = false;
                    break;
                }
            }

            if (guardacuota) {
                try {
                    conectacuota.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
                    conectacuota.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardacuota = false;
        }
        st.close();
        conectacuota.close();
        return guardacuota;
    }

    public ArrayList<cuenta_clientes> MostrarxFecha(Date fechai, Date fechaf, int moneda1, int moneda2) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,cuenta_clientes.amortiza,cuenta_clientes.minteres,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio, ";
            cSql = cSql + "cuenta_clientes.moneda,monedas.nombre as nombremoneda ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cuenta_clientes.moneda ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " AND cuenta_clientes.vencimiento between ? AND ? ";
            cSql = cSql + " AND IF(?<>0,cuenta_clientes.moneda=?,TRUE) ";
            cSql = cSql + " ORDER by cuenta_clientes.comprobante,cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechai);
                ps.setDate(2, fechaf);
                ps.setInt(3, moneda1);
                ps.setInt(4, moneda2);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    moneda moneda = new moneda();
                    cc.setMoneda(moneda);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.getMoneda().setCodigo(rs.getInt("moneda"));
                    cc.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public boolean borrarDetalleCuenta(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuenta_clientes WHERE creferencia=?");
        ps.setString(1, referencia);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean guardarCuentaxGiraduria(String detallecuota) throws SQLException {

        boolean guardacuota = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectacuota = st.getConnection();
        conectacuota.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detallecuota);
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
                            + "giraduria,"
                            + "cliente,"
                            + "sucursal,"
                            + "moneda,"
                            + "comprobante,"
                            + "vendedor,"
                            + "importe,"
                            + "numerocuota,"
                            + "cuota,"
                            + "saldo"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddocumento").getAsString());
                        ps.setString(2, obj.get("creferencia").getAsString());
                        ps.setString(3, obj.get("documento").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("vencimiento").getAsString());
                        ps.setInt(6, obj.get("giraduria").getAsInt());
                        ps.setInt(7, obj.get("cliente").getAsInt());
                        ps.setInt(8, obj.get("sucursal").getAsInt());
                        ps.setInt(9, obj.get("moneda").getAsInt());
                        ps.setInt(10, obj.get("comprobante").getAsInt());
                        ps.setInt(11, obj.get("vendedor").getAsInt());
                        ps.setString(12, obj.get("importe").getAsString());
                        ps.setInt(13, obj.get("numerocuota").getAsInt());
                        ps.setInt(14, obj.get("cuota").getAsInt());
                        ps.setString(15, obj.get("saldo").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardacuota = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardacuota = false;
                    break;
                }
            }

            if (guardacuota) {
                try {
                    conectacuota.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
                    conectacuota.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardacuota = false;
        }
        st.close();
        conectacuota.close();
        return guardacuota;
    }

    public boolean borrarCuentaxGiraduria(int giraduria, int mes, int anual, int comprobante) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuenta_clientes WHERE giraduria=? and month(vencimiento)=? and year(vencimiento)=? and comprobante=?");
        ps.setInt(1, giraduria);
        ps.setInt(2, mes);
        ps.setInt(3, anual);
        ps.setInt(4, comprobante);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<cuenta_clientes> MostrarxGiraduriaIncluido(int ngiraduria, Date fechaf) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,cuenta_clientes.comercial,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
            cSql = cSql + "giradurias.nombre as nombregiraduria, ";
            cSql = cSql + "cuenta_clientes.amortiza,cuenta_clientes.minteres ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " AND cuenta_clientes.giraduria= ? ";
            cSql = cSql + " AND cuenta_clientes.vencimiento<= ? ";
            cSql = cSql + " AND clientes.estado=1 ";
            cSql = cSql + " ORDER by cuenta_clientes.cliente,cuenta_clientes.vencimiento";
            System.out.println("ESTOY AQUI " + cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ngiraduria);
                ps.setDate(2, fechaf);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    casa casa = new casa();
                    cc.setComercial(casa);

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxGiraduriaIncluidoCorto(int ngiraduria, Date fechaf) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT cuenta_clientes.creferencia,"
                    + "cuenta_clientes.iddocumento,"
                    + "cuenta_clientes.documento,cuenta_clientes.fecha,"
                    + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,"
                    + "cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,"
                    + "cuenta_clientes.comercial,"
                    + "cuenta_clientes.importe,cuenta_clientes.cliente,"
                    + "cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,"
                    + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,"
                    + "cuenta_clientes.cuota,cuenta_clientes.numerocuota,"
                    + "DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,"
                    + "DATEDIFF(CURDATE(),fecha_pago) AS di,"
                    + "cuenta_clientes.amortiza,cuenta_clientes.minteres "
                    + "FROM cuenta_clientes "
                    + " WHERE cuenta_clientes.saldo<>0 "
                    + " AND cuenta_clientes.giraduria= ? "
                    + " AND cuenta_clientes.vencimiento<= ? "
                    + " ORDER BY cuenta_clientes.vencimiento";
            System.out.println("ESTOY AQUI " + cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ngiraduria);
                ps.setDate(2, fechaf);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    casa casa = new casa();
                    cc.setComercial(casa);

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxDiasMora(int ndiaI, int ndiaF) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT MAX(clientes_mora.atraso) AS atraso,clientes_mora.creferencia,clientes_mora.iddocumento,"
                    + "clientes_mora.documento,clientes_mora.fecha,clientes_mora.telefono,"
                    + "MIN(clientes_mora.vencimiento) AS vencimiento,clientes_mora.giraduria,clientes_mora.comprobante,clientes_mora.fecha_pago,"
                    + "clientes_mora.importe,clientes_mora.cliente,clientes_mora.saldo,clientes_mora.tasaoperativa,"
                    + "clientes_mora.nombrecliente,clientes_mora.nombrecomprobante,clientes_mora.comprobante,"
                    + "clientes_mora.autorizacion,clientes_mora.nrocuenta,MIN(clientes_mora.cuota) AS cuota,clientes_mora.numerocuota "
                    + "FROM clientes_mora "
                    + " LEFT JOIN comprobantes"
                    + " ON comprobantes.codigo=clientes_mora.comprobante "
                    + " WHERE clientes_mora.saldo>0 "
                    + " GROUP BY clientes_mora.cliente "
                    + " HAVING(atraso>=? AND atraso<=?) "
                    + " ORDER BY clientes_mora.cliente ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ndiaI);
                ps.setInt(2, ndiaF);
                System.out.println(cSql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setTelefono(rs.getString("telefono"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("atraso"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> CuentaPuertoSeguro(int cliente1, int cliente2) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,cuenta_clientes.amortiza,cuenta_clientes.minteres,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,monedas.nombre as nombremoneda,cuenta_clientes.moneda,";
            cSql = cSql + "clientes.ruc,clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio, ";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nomalias,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS diamora,";
            cSql = cSql + "comprobantes.interespunitorio,comprobantes.diasgracia_gastos,comprobantes.diasgracia,cuenta_clientes.tasaoperativa as interesmora,comprobantes.gastoscobros,cuenta_clientes.importe_iva,cuenta_clientes.punitorio, ";
            cSql = cSql + "(SELECT SUM(mora)";
            cSql = cSql + " FROM detalle_cobranzas ";
            cSql = cSql + " WHERE cuenta_clientes.iddocumento = detalle_cobranzas.idfactura ";
            cSql = cSql + " GROUP BY idfactura) moracobrada, ";
            cSql = cSql + "(SELECT SUM(punitorio)";
            cSql = cSql + " FROM detalle_cobranzas ";
            cSql = cSql + " WHERE cuenta_clientes.iddocumento = detalle_cobranzas.idfactura ";
            cSql = cSql + " GROUP BY idfactura) punitoriocobrado, ";
            cSql = cSql + "(SELECT SUM(gastos_cobranzas)";
            cSql = cSql + " FROM detalle_cobranzas ";
            cSql = cSql + " WHERE cuenta_clientes.iddocumento = detalle_cobranzas.idfactura ";
            cSql = cSql + " GROUP BY idfactura) gastoscobrado, ";
            cSql = cSql + " (SELECT SUM(mora)";
            cSql = cSql + "  FROM pagoexpress";
            cSql = cSql + "  WHERE cuenta_clientes.iddocumento = pagoexpress.iddocumento";
            cSql = cSql + "  GROUP BY pagoexpress.iddocumento) morape,";
            cSql = cSql + " (SELECT SUM(gastos)";
            cSql = cSql + "  FROM pagoexpress";
            cSql = cSql + "  WHERE cuenta_clientes.iddocumento = pagoexpress.iddocumento";
            cSql = cSql + "  GROUP BY pagoexpress.iddocumento) gastope,";
            cSql = cSql + " (SELECT SUM(punitorio)";
            cSql = cSql + "  FROM pagoexpress";
            cSql = cSql + "  WHERE cuenta_clientes.iddocumento = pagoexpress.iddocumento";
            cSql = cSql + "  GROUP BY pagoexpress.iddocumento) punitoriope ";
            cSql = cSql + " FROM cuenta_clientes ";
            cSql = cSql + " INNER JOIN CLIENTES ";
            cSql = cSql + " ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + " INNER JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + " INNER JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cuenta_clientes.moneda ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 AND cuenta_clientes.comprobante IN(8,9,10) ";
            cSql = cSql + " AND IF(?<>0,cuenta_clientes.cliente=?,TRUE) ";
            cSql = cSql + " and cuenta_clientes.moneda= 1";
            cSql = cSql + " ORDER by cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente1);
                ps.setInt(2, cliente2);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    moneda moneda = new moneda();
                    cc.setMoneda(moneda);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.getMoneda().setCodigo(rs.getInt("moneda"));
                    cc.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    System.out.println("SALDO " + rs.getBigDecimal("saldo"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    cc.setDiasmora(rs.getInt("diamora"));
                    cc.setDiasgraciagastos(rs.getInt("diasgracia"));
                    cc.setTasaoperativa(rs.getBigDecimal("tasaoperativa"));
                    cc.setInterespunitorio(rs.getBigDecimal("interespunitorio"));
                    //PAGOS POR CAJA
                    cc.setMorapagadocaja(rs.getBigDecimal("moracobrada"));
                    cc.setPunipagadocaja(rs.getBigDecimal("punitoriocobrado"));
                    cc.setGastospagadocaja(rs.getBigDecimal("gastoscobrado"));
                    //PAGOS POR PAGO EXPRESS
                    cc.setGastospagadope(rs.getBigDecimal("gastope"));
                    cc.setMorapagadope(rs.getBigDecimal("morape"));
                    cc.setPunipagadope(rs.getBigDecimal("punitoriope"));
                    ///TODOS LOS PAGOS POR MORA EN CAJA
                    //SE INICIALIZAN EN CERO PARA EVITAR ERROR
                    cc.setPunitorio(new BigDecimal("0.00"));
                    cc.setMora(new BigDecimal("0.00"));
                    cc.setGastos(new BigDecimal("0.00"));

                    if (cc.getPunipagadocaja() == null) {
                        cc.setPunipagadocaja(new BigDecimal("0.00"));
                    }
                    if (cc.getMorapagadocaja() == null) {
                        cc.setMorapagadocaja(new BigDecimal("0.00"));
                    }
                    if (cc.getGastospagadocaja() == null) {
                        cc.setGastospagadocaja(new BigDecimal("0.00"));
                    }

                    ///TODOS LOS PAGOS POR MORA EN PAGO EXPRESS
                    if (cc.getPunipagadope() == null) {
                        cc.setPunipagadope(new BigDecimal("0.00"));
                    }
                    if (cc.getMorapagadope() == null) {
                        cc.setMorapagadope(new BigDecimal("0.00"));
                    }
                    if (cc.getGastospagadope() == null) {
                        cc.setGastospagadope(new BigDecimal("0.00"));
                    }
                    if (cc.getDiasmora() > cc.getDiasgraciamora()) {
                        nMora = Math.round(cc.getImporte().doubleValue() * ((cc.getTasaoperativa().doubleValue() / 100) / 360 * cc.getDiasmora()));
                        cc.setMora(new BigDecimal(nMora));
                        nPunitorio = Math.round(nMora * cc.getInterespunitorio().doubleValue() / 100);
                    }
                    if (cc.getDiasmora() > cc.getDiasgraciagastos()) {
                        if (cc.getComprobante().getCodigo() == 8) {
                            if (cc.getDiasmora() <= 30) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(5)).divide(new BigDecimal(100)));
                            } else if (cc.getDiasmora() >= 31 && cc.getDiasmora() <= 60) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(10)).divide(new BigDecimal(100)));
                            } else if (cc.getDiasmora() >= 60) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(20)).divide(new BigDecimal(100)));
                            }
                        }
                        if (cc.getComprobante().getCodigo() == 9) {
                            if (cc.getDiasmora() <= 30) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(5)).divide(new BigDecimal(100)));
                            } else if (cc.getDiasmora() >= 31 && cc.getDiasmora() <= 60) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(10)).divide(new BigDecimal(100)));
                            } else if (cc.getDiasmora() >= 61 && cc.getDiasmora() <= 360) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(20)).divide(new BigDecimal(100)));
                            } else if (cc.getDiasmora() >= 361 && cc.getDiasmora() <= 540) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(25)).divide(new BigDecimal(100)));
                            } else if (cc.getDiasmora() >= 541 && cc.getDiasmora() <= 720) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(30)).divide(new BigDecimal(100)));
                            } else if (cc.getDiasmora() >= 721 && cc.getDiasmora() <= 900) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(40)).divide(new BigDecimal(100)));
                            } else if (cc.getDiasmora() >= 901) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(50)).divide(new BigDecimal(100)));
                            }
                        }
                        if (cc.getComprobante().getCodigo() == 10) {
                            if (cc.getDiasmora() <= 30) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(3)).divide(new BigDecimal(100)));
                            } else if (cc.getDiasmora() > 31 && cc.getDiasmora() <= 60) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(6)).divide(new BigDecimal(100)));
                            } else if (cc.getDiasmora() >= 61 && cc.getDiasmora() <= 90) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(10)).divide(new BigDecimal(100)));
                            } else if (cc.getDiasmora() >= 91) {
                                cc.setGastos(cc.getImporte().multiply(new BigDecimal(15)).divide(new BigDecimal(100)));
                            }
                        }
                        nGastos = cc.getGastos().doubleValue();
                        nPunitorio = nPunitorio - (cc.getPunipagadocaja().doubleValue() + cc.getPunipagadope().doubleValue());
                        cc.setPunitorio(new BigDecimal(nPunitorio));
                        nMora = nMora - (cc.getMorapagadocaja().doubleValue() + cc.getMorapagadope().doubleValue());
                        cc.setMora(new BigDecimal(nMora));
                        nGastos = nGastos - Math.abs(cc.getGastospagadocaja().doubleValue() + cc.getGastospagadope().doubleValue());
                        cc.setGastos(new BigDecimal(Math.abs(nGastos)));
                        nSaldo = cc.getSaldo().doubleValue() + nGastos + nPunitorio + nMora;
                        cc.setSaldo(new BigDecimal(nSaldo));
                    }
                    lista.add(cc);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> Vencimientoxcarteracheque(Date fechaini, Date fechafin, int nCliente, int nCli, int nMoneda) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT IFNULL(detalle_descuento_documentos.nrodocumento,0) AS nrodocumento ,detalle_descuento_documentos.compra AS fecha,"
                    + " detalle_descuento_documentos.vencimiento AS vencimiento,cuenta_clientes.importe,cuenta_clientes.saldo,"
                    + " clientes.codigo AS cliente,clientes.nombre AS nombrecliente,monedas.codigo AS moneda,comprobantes.codigo AS codcomprobante"
                    + " FROM cuenta_clientes"
                    + " LEFT JOIN detalle_descuento_documentos "
                    + " ON detalle_descuento_documentos.nrodocumento=cuenta_clientes.documento "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=cuenta_clientes.cliente  "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=cuenta_clientes.moneda  "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=cuenta_clientes.comprobante  "
                    + " WHERE detalle_descuento_documentos.vencimiento BETWEEN ? AND ?  "
                    + " AND IF(?<>0,cuenta_clientes.cliente=?,TRUE) "
                    + " AND cuenta_clientes.moneda=?  "
                    + " AND cuenta_clientes.saldo<>0  "
                    + " AND detalle_descuento_documentos.comprobante=10  "
                    + " ORDER BY detalle_descuento_documentos.nrodocumento,detalle_descuento_documentos.vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ps.setInt(3, nCliente);
                ps.setInt(4, nCli);
                ps.setInt(5, nMoneda);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    detalle_descuento_documento detalle_descuento_documento = new detalle_descuento_documento();
                    moneda moneda = new moneda();
                    cliente cliente = new cliente();
                    comprobante comprobante = new comprobante();
                    cuenta_clientes vc = new cuenta_clientes();

                    vc.setDetalle_descuento_documento(detalle_descuento_documento);
                    vc.setMoneda(moneda);
                    vc.setCliente(cliente);
                    vc.setComprobante(comprobante);

                    vc.getDetalle_descuento_documento().setNrodocumento(rs.getBigDecimal("nrodocumento"));
                    vc.getDetalle_descuento_documento().setCompra(rs.getDate("fecha"));
                    vc.getDetalle_descuento_documento().setVencimiento(rs.getDate("vencimiento"));
                    vc.setImporte(rs.getBigDecimal("importe"));
                    vc.setSaldo(rs.getBigDecimal("saldo"));
                    vc.getCliente().setCodigo(rs.getInt("cliente"));
                    vc.getCliente().setNombre(rs.getString("nombrecliente"));
                    vc.getMoneda().setCodigo(rs.getInt("moneda"));
                    vc.getComprobante().setCodigo(rs.getInt("codcomprobante"));
                    lista.add(vc);
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

    public ArrayList<cuenta_clientes> CuentasInmobiliarias(int cliente1, int cliente2, Date FechaIni) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,"
                    + "cuenta_clientes.vencimiento,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,"
                    + "cuenta_clientes.importe,cuenta_clientes.cliente,edificios.multa,"
                    + "COALESCE(cuenta_clientes.alquiler+cuenta_clientes.iva,'0') AS alquiler,"
                    + "COALESCE(cuenta_clientes.iva,'0') AS iva,"
                    + "COALESCE(cuenta_clientes.garage+cuenta_clientes.ivagar,'0') AS garage,"
                    + "COALESCE(cuenta_clientes.ivagar,'0') AS ivagar,"
                    + "COALESCE(cuenta_clientes.expensa+cuenta_clientes.ivaexp,'0') AS expensa,"
                    + "COALESCE(cuenta_clientes.ivaexp,'0') AS ivaexp,"
                    + "COALESCE(cuenta_clientes.comision+cuenta_clientes.ivacomi,'0') AS comision,"
                    + "COALESCE(cuenta_clientes.ivacomi,'0') AS ivacomi,"
                    + "COALESCE(cuenta_clientes.garantia,'0') AS garantia,"
                    + "COALESCE(cuenta_clientes.fondo,'0') AS fondo,"
                    + "COALESCE(cuenta_clientes.llave,'0') AS llave,"
                    + "COALESCE(cuenta_clientes.otros,'0') AS otros,"
                    + "COALESCE(cuenta_clientes.saldo,'0') AS saldo,"
                    + "cuenta_clientes.cuota,cuenta_clientes.numerocuota,edificios.diapago,edificios.ctactral,edificios.tipunid,"
                    + "clientes.nombre AS nombrecliente,monedas.nombre AS nombremoneda,cuenta_clientes.moneda,"
                    + "clientes.ruc,clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre AS nombrecomprobante,comprobantes.nomalias,"
                    + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nomalias,DATEDIFF('" + FechaIni + "',cuenta_clientes.vencimiento) AS diamora,"
                    + "cuenta_clientes.idedificio,inmuebles.diredif,CONCAT(propietarios.nombre,' ',propietarios.apellido) AS nombrepropietario,"
                    + "edificios.ubicacion,inmuebles.nomedif,"
                    + "(SELECT SUM(mora) "
                    + "FROM detalle_cobranzas "
                    + "WHERE cuenta_clientes.iddocumento = detalle_cobranzas.idfactura "
                    + "GROUP BY idfactura) moracobrada, "
                    + "(SELECT SUM(mora) "
                    + "FROM pagoexpress "
                    + "WHERE cuenta_clientes.iddocumento = pagoexpress.iddocumento "
                    + "GROUP BY pagoexpress.iddocumento) morape "
                    + "FROM cuenta_clientes "
                    + "INNER JOIN CLIENTES "
                    + "ON clientes.codigo=cuenta_clientes.cliente "
                    + "INNER JOIN comprobantes "
                    + "ON comprobantes.codigo=cuenta_clientes.comprobante "
                    + "INNER JOIN monedas "
                    + "ON monedas.codigo=cuenta_clientes.moneda "
                    + "INNER JOIN edificios "
                    + "ON edificios.idunidad=cuenta_clientes.idedificio "
                    + "INNER JOIN inmuebles "
                    + "ON inmuebles.idinmueble=edificios.inmueble "
                    + "INNER JOIN propietarios "
                    + "ON propietarios.codpro=inmuebles.codpro "
                    + "WHERE cuenta_clientes.saldo<>0 "
                    + "AND IF(?<>0,cuenta_clientes.cliente=?,TRUE) "
                    + "AND cuenta_clientes.moneda= 1 "
                    + "ORDER BY cuenta_clientes.vencimiento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente1);
                ps.setInt(2, cliente2);
                ResultSet rs = ps.executeQuery();
                System.out.println(cSql);
                while (rs.next()) {
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    moneda moneda = new moneda();
                    edificio edif = new edificio();
                    inmueble inm = new inmueble();
                    cc.setEdificio(edif);
                    cc.getEdificio().setIdunidad(rs.getInt("idedificio"));
                    cc.getEdificio().setCtactral(rs.getString("ctactral"));
                    cc.getEdificio().setTipunid(rs.getInt("tipunid"));
                    cc.getEdificio().setUbicacion(rs.getString("ubicacion"));
                    cc.getEdificio().setInmueble(inm);

                    cc.getEdificio().getInmueble().setDiredif(rs.getString("diredif"));
                    cc.getEdificio().getInmueble().setNombrepropietario(rs.getString("nombrepropietario"));
                    cc.getEdificio().getInmueble().setNomedif(rs.getString("nomedif"));

                    cc.setMoneda(moneda);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));

                    cc.setAlquiler(rs.getDouble("alquiler"));
                    cc.setIvaalquiler(rs.getDouble("iva"));
                    cc.setGarage(rs.getDouble("garage"));
                    cc.setIvagarage(rs.getDouble("ivagar"));
                    cc.setExpensa(rs.getDouble("expensa"));
                    cc.setIvaexpensa(rs.getDouble("ivaexp"));
                    cc.setComision(rs.getDouble("comision"));
                    cc.setIvacomision(rs.getDouble("ivacomi"));
                    cc.setMulta(rs.getDouble("multa"));
                    cc.setGarantia(rs.getDouble("garantia"));
                    cc.setOtros(rs.getDouble("otros"));
                    cc.setLlave(rs.getDouble("llave"));
                    cc.setFondo(rs.getDouble("fondo"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.getMoneda().setCodigo(rs.getInt("moneda"));
                    cc.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    cc.setDiasmora(rs.getInt("diamora"));
                    //PAGOS POR CAJA
                    cc.setMorapagadocaja(rs.getBigDecimal("moracobrada"));
                    //PAGOS POR PAGO EXPRESS
                    cc.setMorapagadope(rs.getBigDecimal("morape"));
                    ///TODOS LOS PAGOS POR MORA EN CAJA
                    //SE INICIALIZAN EN CERO PARA EVITAR ERROR

                    ///TODOS LOS PAGOS POR MORA EN PAGO EXPRESS
                    if (cc.getMorapagadope() == null) {
                        cc.setMorapagadope(new BigDecimal("0.00"));
                    }
                    nMora = 0.00;
                    nExpensa = 0.00;
                    nAlquiler = 0.00;
                    nGarage = 0.00;
                    nComision = 0.00;
                    if (rs.getInt("diamora") > 0) {
                        nMora = rs.getDouble("multa") * rs.getInt("diamora");
                        cc.setIvamulta(Math.round(nMora * 10 / 100));
                        nMora = nMora + Math.round(nMora * 10 / 100);
                        cc.setMora(new BigDecimal(nMora));
                    } else {
                        cc.setMora(new BigDecimal("0.00"));
                        cc.setIvamulta(0);
                    }
                    System.out.println("IMPORTE MULTA + IVA" + cc.getMulta());

                    // SE COBRA IVA 5% SI LA UNIDAD ES PARA RESIDENCIA
                    // SE COBRA IVA 10% SI LA UNIDAD ES PARA COMERCIO
                    //nSaldo = nMora+cc.getIvamulta()+nAlquiler+cc.getIvaalquiler()+nGarage+cc.getIvagarage()+nComision+cc.getIvacomision()+nExpensa+cc.getIvaexpensa();
                    nSaldo = cc.getSaldo().doubleValue() + nMora;
                    cc.setSaldo(new BigDecimal(nSaldo));
                    lista.add(cc);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public cuenta_clientes CuentaCuota(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        cuenta_clientes cc = new cuenta_clientes();
        try {

            String cSql = "SELECT cuenta_clientes.saldo FROM cuenta_clientes ";
            cSql = cSql + " WHERE cuenta_clientes.iddocumento=? and saldo>0 ";
            cSql = cSql + " ORDER by cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return cc;
    }

    public ArrayList<cuenta_clientes> Cobranzasvencxpropietarios(Date fechaini, Date fechafin, int nPropietario) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = " SELECT cuenta_clientes.creferencia,"
                    + "cuenta_clientes.cliente,"
                    + "COALESCE(cuenta_clientes.documento,'0') AS documento,"
                    + "cuenta_clientes.vencimiento AS vencimiento,"
                    + "clientes.ruc AS ruccliente,"
                    + "COALESCE(clientes.nombre,'SD') AS locatario,"
                    + "COALESCE(inmuebles.nomedif,'SD') AS nomedif,"
                    + "COALESCE(propietarios.codpro,'0') AS codpro,"
                    + "COALESCE(propietarios.cedula,'0') AS cedulapro," + ""
                    + "CONCAT(propietarios.nombre,' ', propietarios.apellido) AS nombreprop,"
                    + "edificios.inmueble AS inmueble,"
                    + "COALESCE(cuenta_clientes.alquiler+cuenta_clientes.iva,'0') AS alquiler,"
                    + "COALESCE(cuenta_clientes.garage+cuenta_clientes.ivagar,'0') AS garage,"
                    + "COALESCE(cuenta_clientes.expensa+cuenta_clientes.ivaexp,'0') AS expensa,"
                    + "COALESCE(cuenta_clientes.comision+cuenta_clientes.ivacomi,'0') AS comision,"
                    + "COALESCE(cuenta_clientes.multa+cuenta_clientes.ivamul,'0') AS multa,"
                    + "COALESCE(cuenta_clientes.garantia,'0') AS garantia,"
                    + "COALESCE(cuenta_clientes.fondo,'0') AS fondo,"
                    + "COALESCE(cuenta_clientes.llave,'0') AS llave,"
                    + "COALESCE(cuenta_clientes.otros,'0') AS otros,"
                    + "COALESCE(cuenta_clientes.saldo,'0') AS saldo "
                    + " FROM cuenta_clientes "
                    + " LEFT JOIN clientes"
                    + " ON clientes.codigo=cuenta_clientes.cliente"
                    + " LEFT JOIN edificios"
                    + " ON edificios.idunidad=cuenta_clientes.idedificio"
                    + " LEFT JOIN inmuebles"
                    + " ON edificios.inmueble=inmuebles.idinmueble"
                    + " LEFT JOIN propietarios"
                    + " ON propietarios.codpro=inmuebles.codpro"
                    + " WHERE cuenta_clientes.saldo<>0 "
                    + " AND cuenta_clientes.vencimiento between '" + fechaini + "' AND '" + fechafin + "'";
            if (nPropietario != 0) {
                cSql += " AND propietarios.codpro= " + nPropietario;
            }
            cSql += " ORDER BY propietarios.codpro,inmuebles.idinmueble,cuenta_clientes.cliente,cuenta_clientes.vencimiento";
            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cuenta_clientes cuenta = new cuenta_clientes();
                    cliente cli = new cliente();
                    edificio ed = new edificio();
                    inmueble inm = new inmueble();
                    propietario pro = new propietario();

                    cuenta.setCliente(cli);
                    cuenta.setEdificio(ed);
                    cuenta.setInmueble(inm);
                    cuenta.setPropietario(pro);

                    cuenta.setCreferencia(rs.getString("creferencia"));
                    cuenta.setDocumento(rs.getString("documento"));
                    cuenta.setVencimiento(rs.getDate("vencimiento"));
                    cuenta.getCliente().setCodigo(rs.getInt("cliente"));
                    cuenta.getCliente().setRuc(rs.getString("ruccliente"));
                    cuenta.getCliente().setNombre(rs.getString("locatario"));
                    cuenta.getInmueble().setNomedif(rs.getString("nomedif"));
                    cuenta.getPropietario().setCodpro(rs.getInt("codpro"));
                    cuenta.getPropietario().setCedula(rs.getString("cedulapro"));
                    cuenta.getPropietario().setNombreprop(rs.getString("nombreprop"));
                    cuenta.getInmueble().setIdinmueble(rs.getInt("inmueble"));
                    cuenta.setAlquiler(rs.getDouble("alquiler"));
                    cuenta.setGarage(rs.getDouble("garage"));
                    cuenta.setExpensa(rs.getDouble("expensa"));
                    cuenta.setComision(rs.getDouble("comision"));
                    cuenta.setMulta(rs.getDouble("multa"));
                    cuenta.setGarantia(rs.getDouble("garantia"));
                    cuenta.setOtros(rs.getDouble("otros"));
                    cuenta.setLlave(rs.getDouble("llave"));
                    cuenta.setFondo(rs.getDouble("fondo"));
                    cuenta.setSaldo(rs.getBigDecimal("saldo"));
                    lista.add(cuenta);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> alquilerxlocatario(int nCliente, int nCli, int cVencidos) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = " SELECT cuenta_clientes.creferencia,cuenta_clientes.saldo,COALESCE(cuenta_clientes.documento,'0') AS documento,cuenta_clientes.vencimiento AS vencimiento,"
                    + " COALESCE(propietarios.codpro,'0') AS codpro,COALESCE(propietarios.cedula,'0') AS cedulaprop,COALESCE(CONCAT(propietarios.nombre,' ',propietarios.apellido),'SD') AS nombreprop,"
                    + " COALESCE(propietarios.dirparticular,'SD') AS direprop,COALESCE(propietarios.teleparticular,'0') AS telefprop,clientes.codigo,COALESCE(clientes.ruc,'') AS ruclocatario,"
                    + " clientes.nombre AS nombrelocatario,COALESCE(clientes.direccion,'SD') AS locatarioDirec,COALESCE(clientes.telefono,'0') AS locatarioTelef,COALESCE(inmuebles.idinmueble,'0') AS idinmueble,"
                    + " COALESCE(edificios.ctactral,'0') AS edifctactral,COALESCE(inmuebles.nomedif,'0') AS nomedif,COALESCE(inmuebles.diredif,'SD') AS diredif,"
                    + " COALESCE(inmuebles.ctactral,'0') AS inmctactral,COALESCE(edificios.nrodoc,'0') AS nrodoc,COALESCE(cuenta_clientes.alquiler,'0') AS alquiler,"
                    + " COALESCE(cuenta_clientes.garage,'0') AS garage,COALESCE(cuenta_clientes.expensa,'0') AS expensa,COALESCE(cuenta_clientes.comision,'0') AS comision,COALESCE(cuenta_clientes.multa,'0') AS multa,"
                    + " COALESCE(cuenta_clientes.numerocuota,'0') AS numerocuota,COALESCE(cuenta_clientes.cuota,'0') AS cuota"
                    + " FROM cuenta_clientes "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cuenta_clientes.cliente "
                    + "LEFT JOIN edificios "
                    + "ON edificios.idunidad=cuenta_clientes.idedificio "
                    + "LEFT JOIN inmuebles "
                    + "ON edificios.inmueble=inmuebles.idinmueble "
                    + "LEFT JOIN propietarios "
                    + "ON propietarios.codpro=inmuebles.codpro "
                    + " WHERE IF(?<>0,clientes.codigo=?,TRUE) "
                    + " AND cuenta_clientes.saldo<>0 ";

            if (cVencidos == 1) {
                cSql = cSql + " AND cuenta_clientes.vencimiento<=curdate()";
            }
            cSql = cSql + " ORDER BY clientes.codigo ";

            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {

                ps.setInt(1, nCliente);
                ps.setInt(2, nCli);
//              ps.setInt(3, cVencidos);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cuenta_clientes cuenta = new cuenta_clientes();
                    cliente cli = new cliente();
                    edificio ed = new edificio();
                    inmueble inm = new inmueble();
                    propietario pro = new propietario();

                    cuenta.setCliente(cli);
                    cuenta.setEdificio(ed);
                    cuenta.setInmueble(inm);
                    cuenta.setPropietario(pro);

                    cuenta.setCreferencia(rs.getString("creferencia"));
                    cuenta.setSaldo(rs.getBigDecimal("saldo"));
                    cuenta.setDocumento(rs.getString("documento"));
                    cuenta.setVencimiento(rs.getDate("vencimiento"));
                    cuenta.getPropietario().setCodpro(rs.getInt("codpro"));
                    cuenta.getPropietario().setCedula(rs.getString("cedulaprop"));
                    cuenta.getPropietario().setNombreprop(rs.getString("nombreprop"));
                    cuenta.getPropietario().setDirparticular(rs.getString("direprop"));
                    cuenta.getPropietario().setTeleparticular(rs.getString("telefprop"));
                    cuenta.getCliente().setCodigo(rs.getInt("codigo"));
                    cuenta.getCliente().setRuc(rs.getString("ruclocatario"));
                    cuenta.getCliente().setNombre(rs.getString("nombrelocatario"));
                    cuenta.getCliente().setDireccion(rs.getString("locatarioDirec"));
                    cuenta.getCliente().setTelefono(rs.getString("locatarioTelef"));
                    cuenta.getInmueble().setIdinmueble(rs.getInt("idinmueble"));
                    cuenta.getEdificio().setCtactral(rs.getString("edifctactral"));
                    cuenta.getInmueble().setNomedif(rs.getString("nomedif"));
                    cuenta.getInmueble().setDiredif(rs.getString("diredif"));
                    cuenta.getInmueble().setCtactral(rs.getString("inmctactral"));
                    cuenta.getEdificio().setNrodoc(rs.getString("nrodoc"));
                    cuenta.setAlquiler(rs.getDouble("alquiler"));
                    cuenta.setGarage(rs.getDouble("garage"));
                    cuenta.setExpensa(rs.getDouble("expensa"));
                    cuenta.setComision(rs.getDouble("comision"));
                    cuenta.setMulta(rs.getDouble("multa"));
                    cuenta.setNumerocuota(rs.getInt("numerocuota"));
                    cuenta.setCuota(rs.getInt("cuota"));

                    lista.add(cuenta);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarSaldoxServicio(int cliente, int nServicio, double numini, double numfin) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe AS importe,cuenta_clientes.cliente,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "giradurias.nombre as nombregiraduria,clientes.salario,clientes.ruc,clientes.telefono,";
            cSql = cSql + "casas.nombre as nombrecomercio,cuenta_clientes.saldo,cuenta_clientes.vencegracia,cuenta_clientes.refinanciado ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " AND cuenta_clientes.cliente= " + cliente + " ";
            cSql = cSql + " AND cuenta_clientes.comprobante=" + nServicio + " ";
            if (numini > 0) {
                cSql = cSql + " AND cuenta_clientes.documento=" + numini + " ";
            }
            cSql = cSql + " ORDER by cuenta_clientes.documento,cuenta_clientes.vencimiento";

            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setRefinanciado(rs.getInt("refinanciado"));
                    if (rs.getDate("vencegracia") == null) {
                        cc.setVencegracia(rs.getDate("vencimiento"));
                    } else {
                        cc.setVencegracia(rs.getDate("vencegracia"));
                    }
                    lista.add(cc);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public cuenta_clientes ActualizarDiasGracia(cuenta_clientes cta) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;

        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cuenta_clientes SET refinanciado=?,vencegracia=?  WHERE iddocumento= '" + cta.getIddocumento() + "'");
        ps.setInt(1, cta.getRefinanciado());
        ps.setDate(2, cta.getVencegracia());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return cta;
    }

    public ArrayList<cuenta_clientes> saldosxinmobiliaria(String creferencia) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = " SELECT cuenta_clientes.creferencia,cuenta_clientes.idedificio,"
                    + "cuenta_clientes.cliente,cuenta_clientes.comprobante,"
                    + "comprobantes.nombre as nombrecomprobante,"
                    + "COALESCE(cuenta_clientes.documento,'0') AS documento,"
                    + "cuenta_clientes.vencimiento AS vencimiento,"
                    + "COALESCE(inmuebles.nomedif,'SD') AS nomedif,"
                    + "COALESCE(propietarios.codpro,'0') AS codpro,"
                    + "COALESCE(propietarios.cedula,'0') AS cedulapro,"
                    + "CONCAT(propietarios.nombre,' ', propietarios.apellido) AS nombreprop,"
                    + "edificios.inmueble AS inmueble,"
                    + "COALESCE(cuenta_clientes.importe,'0') AS importe "
                    + " FROM cuenta_clientes "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=cuenta_clientes.cliente "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=cuenta_clientes.comprobante "
                    + " LEFT JOIN edificios"
                    + " ON edificios.idunidad=cuenta_clientes.idedificio"
                    + " LEFT JOIN inmuebles"
                    + " ON edificios.inmueble=inmuebles.idinmueble"
                    + " LEFT JOIN propietarios"
                    + " ON propietarios.codpro=inmuebles.codpro"
                    + " WHERE  cuenta_clientes.creferencia='" + creferencia + "'"
                    + " ORDER BY cuenta_clientes.creferencia";
            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cuenta_clientes cuenta = new cuenta_clientes();
                    cliente cli = new cliente();
                    edificio ed = new edificio();
                    inmueble inm = new inmueble();
                    propietario pro = new propietario();
                    comprobante m = new comprobante();

                    cuenta.setComprobante(m);
                    cuenta.setCliente(cli);
                    cuenta.setEdificio(ed);
                    cuenta.setInmueble(inm);
                    cuenta.setPropietario(pro);
                    cuenta.setDocumento(rs.getString("documento"));
                    cuenta.setVencimiento(rs.getDate("vencimiento"));
                    cuenta.getEdificio().setIdunidad(rs.getInt("idedificio"));
                    cuenta.getInmueble().setNomedif(rs.getString("nomedif"));
                    cuenta.getInmueble().setIdinmueble(rs.getInt("inmueble"));
                    cuenta.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cuenta.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cuenta.setImporte(rs.getBigDecimal("importe"));
                    lista.add(cuenta);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public boolean guardarCuentaInmo(String detallecuota) throws SQLException {
        boolean guardacuota = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectacuota = st.getConnection();
        conectacuota.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detallecuota);
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
                            + "comprobante,"
                            + "vendedor,"
                            + "importe,"
                            + "numerocuota,"
                            + "cuota,"
                            + "saldo,"
                            + "idedificio"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddocumento").getAsString());
                        ps.setString(2, obj.get("creferencia").getAsString());
                        ps.setString(3, obj.get("documento").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("vencimiento").getAsString());
                        ps.setString(6, obj.get("cliente").getAsString());
                        ps.setString(7, obj.get("sucursal").getAsString());
                        ps.setString(8, obj.get("moneda").getAsString());
                        ps.setString(9, obj.get("comprobante").getAsString());
                        ps.setString(10, obj.get("vendedor").getAsString());
                        ps.setString(11, obj.get("importe").getAsString());
                        ps.setString(12, obj.get("numerocuota").getAsString());
                        ps.setString(13, obj.get("cuota").getAsString());
                        ps.setString(14, obj.get("saldo").getAsString());
                        ps.setString(15, obj.get("idedificio").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardacuota = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardacuota = false;
                    break;
                }
            }

            if (guardacuota) {
                try {
                    conectacuota.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
                    conectacuota.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardacuota = false;
        }
        st.close();
        conectacuota.close();
        return guardacuota;
    }

    public ArrayList<cuenta_clientes> MostrarSaldoxSocioDetallado(int cliente, Date dInicio) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT clientes.codigo,clientes.categoria,clientes.asesor,clientes.nombre AS nombrecliente,"
                    + "clientes.celular, clientes.direccion, clientes.mail,giradurias.nombre AS nombregiraduria,"
                    + "clientes.salario,clientes.ruc,clientes.telefono,"
                    + "cuenta_clientes.documento,cuenta_clientes.fecha,cuenta_clientes.vencimiento,"
                    + "cuenta_clientes.comprobante,cuenta_clientes.importe,cuenta_clientes.saldo,"
                    + "cuenta_clientes.numerocuota,cuenta_clientes.cuota,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "casas.nombre as nombrecomercio "
                    + "FROM clientes "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=clientes.giraduria "
                    + "LEFT JOIN "
                    + "cuenta_clientes "
                    + "ON cuenta_clientes.cliente=clientes.codigo "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cuenta_clientes.comprobante "
                    + "LEFT JOIN casas "
                    + "ON casas.codigo=cuenta_clientes.comercial "
                    + "WHERE clientes.codigo= ? "
                    + "AND cuenta_clientes.fecha<= ?"
                    + "AND cuenta_clientes.saldo>0 "
                    + "ORDER BY cuenta_clientes.cliente,cuenta_clientes.fecha,cuenta_clientes.documento,cuenta_clientes.cuota";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ps.setDate(2, dInicio);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("codigo"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxClienteMonedaRuc(int cliente, int moneda, String cRuc) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "giradurias.nombre as nombregiraduria ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + "AND cuenta_clientes.moneda= ? ";
            cSql = cSql + "AND clientes.ruc= ? ";
            cSql = cSql + " ORDER by cuenta_clientes.fecha";
            //cSql = cSql + " ORDER by cuenta_clientes.vencimiento";            

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ps.setInt(1, moneda);
                ps.setString(2, cRuc);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));

                    lista.add(cc);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        conn.close();
        st.close();
        return lista;
    }

    public cuenta_clientes MostrarxDocumento(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        cliente c = new cliente();
        cuenta_clientes cc = new cuenta_clientes();

        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,";
            cSql = cSql + "clientes.nombre as nombrecliente ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + " WHERE cuenta_clientes.iddocumento=? ";
            cSql = cSql + " ORDER by cuenta_clientes.iddocumento ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cc.setCliente(c);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        conn.close();
        st.close();
        return cc;
    }

    public ArrayList<cuenta_clientes> MostrarxFechaxCliente(Date fechai, Date fechaf, int moneda1, int cliente1, int cliente2) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,cuenta_clientes.amortiza,cuenta_clientes.minteres,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio, ";
            cSql = cSql + "cuenta_clientes.moneda,monedas.nombre as nombremoneda ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cuenta_clientes.moneda ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " AND cuenta_clientes.vencimiento between ? AND ? ";
            cSql = cSql + " AND cuenta_clientes.moneda=? ";
            cSql = cSql + " AND IF(?<>0,cuenta_clientes.cliente=?,TRUE) ";
            cSql = cSql + " ORDER by cuenta_clientes.cliente,cuenta_clientes.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechai);
                ps.setDate(2, fechaf);
                ps.setInt(3, moneda1);
                ps.setInt(4, cliente1);
                ps.setInt(5, cliente2);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    moneda moneda = new moneda();
                    cc.setMoneda(moneda);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.getMoneda().setCodigo(rs.getInt("moneda"));
                    cc.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxFechaxClienteRuc(Date fechai, Date fechaf, int moneda1, String cliente1, String cliente2) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT clientes.ruc,cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,cuenta_clientes.amortiza,cuenta_clientes.minteres,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio, ";
            cSql = cSql + "cuenta_clientes.moneda,monedas.nombre as nombremoneda ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cuenta_clientes.moneda ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " AND cuenta_clientes.vencimiento between ? AND ? ";
            cSql = cSql + " AND cuenta_clientes.moneda=? ";
            cSql = cSql + " AND IF(?<>0,clientes.ruc=?,TRUE) ";
            cSql = cSql + " ORDER by clientes.ruc,cuenta_clientes.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechai);
                ps.setDate(2, fechaf);
                ps.setInt(3, moneda1);
                ps.setString(4, cliente1);
                ps.setString(5, cliente2);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    moneda moneda = new moneda();
                    cc.setMoneda(moneda);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.getMoneda().setCodigo(rs.getInt("moneda"));
                    cc.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> FacturasalaFecha(Date fechai, int moneda, int cliente) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cli.codigo,"
                    + "cli.nombre as nombrecliente,"
                    + "cuenta_clientes.documento,"
                    + "cli.ruc,"
                    + "cuenta_clientes.comprobante,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "cuenta_clientes.fecha,"
                    + "cuenta_clientes.vencimiento,"
                    + "cuenta_clientes.iddocumento,"
                    + "cuenta_clientes.importe,"
                    + "COALESCE(p.supago,0) AS supago,cuenta_clientes.autorizacion "
                    + "FROM clientes cli "
                    + "INNER JOIN cuenta_clientes "
                    + "ON cuenta_clientes.cliente=cli.codigo "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cuenta_clientes.comprobante "
                    + "LEFT JOIN (SELECT cobranzas.cliente,detalle_cobranzas.idfactura, SUM(pago-(mora+gastos_cobranzas+punitorio)) AS supago "
                    + "FROM cobranzas "
                    + " LEFT JOIN detalle_cobranzas "
                    + "ON cobranzas.idpagos=detalle_cobranzas.iddetalle "
                    + "WHERE cobranzas.fecha<='" + fechai + "'"
                    + " AND cobranzas.moneda=" + moneda
                    + " GROUP BY detalle_cobranzas.idfactura) p ON p.idfactura=cuenta_clientes.iddocumento"
                    + " WHERE IF(" + cliente + "<>0,cli.codigo=" + cliente + ",TRUE) "
                    + " AND cuenta_clientes.fecha<='" + fechai + "'"
                    + " AND cuenta_clientes.moneda=" + moneda
                    + "  ORDER BY cli.ruc,cuenta_clientes.fecha ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("codigo"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setCreditos(rs.getDouble("importe"));
                    cc.setDebitos(rs.getDouble("supago"));
                    cc.setSaldofactura(cc.getCreditos() - cc.getDebitos());
                    if (cc.getSaldofactura() != 0) {
                        lista.add(cc);
                    }
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> ResumeSaldoalaFecha(Date fechai, int moneda, int cliente) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cli.codigo,"
                    + "cli.nombre as nombrecliente,"
                    + "cli.ruc,cli.telefono,cli.direccion,"
                    + "cuenta_clientes.documento,"
                    + "cuenta_clientes.comprobante,"
                    + "SUM(cuenta_clientes.importe) as credito,"
                    + "COALESCE(p.supago,0) AS supago, "
                    + "COALESCE(d.descuento,0) AS descuento "
                    + "FROM clientes cli "
                    + "INNER JOIN cuenta_clientes "
                    + " ON cuenta_clientes.cliente=cli.codigo "
                    + " LEFT JOIN (SELECT cobranzas.cliente,detalle_cobranzas.idfactura,"
                    + " SUM(pago-(mora+gastos_cobranzas+punitorio)) AS supago "
                    + "FROM cobranzas "
                    + " LEFT JOIN detalle_cobranzas "
                    + "ON cobranzas.idpagos=detalle_cobranzas.iddetalle "
                    + "WHERE cobranzas.fecha<='" + fechai + "'"
                    + " AND cobranzas.moneda=" + moneda
                    + " GROUP BY cobranzas.cliente) p ON p.cliente=cli.codigo "
                    + " LEFT JOIN (SELECT cierre_cobranzas.cliente, "
                    + " SUM(cierre_cobranzas.cobrado) AS descuento "
                    + " FROM cierre_cobranzas "
                    + " WHERE cierre_cobranzas.fecha_cobro<='" + fechai + "'"
                    + " GROUP BY cierre_cobranzas.cliente) d ON d.cliente=cli.codigo "
                    + " WHERE IF(" + cliente + "<>0,cli.codigo=" + cliente + ",TRUE) "
                    + " AND cuenta_clientes.fecha<='" + fechai + "'"
                    + " AND cuenta_clientes.moneda=" + moneda
                    + " GROUP BY cuenta_clientes.cliente "
                    + "  ORDER BY cli.codigo,cuenta_clientes.fecha ";

            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    cuenta_clientes cc = new cuenta_clientes();
                    cc.setCliente(c);
                    cc.getCliente().setCodigo(rs.getInt("codigo"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.getCliente().setTelefono(rs.getString("telefono"));
                    cc.setCreditos(rs.getDouble("credito"));
                    cc.setDebitos(rs.getDouble("supago") + rs.getDouble("descuento"));
                    cc.setSaldofactura(cc.getCreditos() - cc.getDebitos());
                    if (cc.getSaldofactura() != 0) {
                        lista.add(cc);
                    }
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> ResumenSaldoOrdenes(int giraduria, Date fecha) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT creferencia,documento,fecha,giraduria,sucursal,moneda,"
                    + "comprobante,cliente,numerocuota,cuota,importe,"
                    + "SUM(saldo) as importeseguro "
                    + " FROM cuenta_clientes"
                    + " WHERE giraduria=? "
                    + " AND vencimiento>=? "
                    + " AND comprobante IN (4,5) "
                    + " GROUP BY cliente"
                    + " ORDER BY cliente";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, giraduria);
                ps.setDate(2, fecha);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    cuenta_clientes cc = new cuenta_clientes();
                    cc.setCliente(c);
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.setSaldo(rs.getBigDecimal("importeseguro"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxGiraduriaxRangoFecha(int ngiraduria, Date fechai, Date fechaf) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio,";
            cSql = cSql + "giradurias.nombre as nombregiraduria,cuenta_clientes.comercial,casas.nombre as nombrecomercial ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN casas ";
            cSql = cSql + "ON casas.codigo=cuenta_clientes.comercial ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + " WHERE cuenta_clientes.saldo>0 ";
            cSql = cSql + " AND cuenta_clientes.giraduria= ? ";
            cSql = cSql + " AND cuenta_clientes.vencimiento between ? AND ? ";
            cSql = cSql + " AND clientes.estado=1 ";
            cSql = cSql + " ORDER by cliente,fecha";

            System.out.println(cSql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ngiraduria);
                ps.setDate(2, fechai);
                ps.setDate(3, fechaf);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    casa casa = new casa();
                    cc.setComercial(casa);
                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.getComercial().setCodigo(rs.getInt("comercial"));
                    cc.getComercial().setNombre(rs.getString("nombrecomercial"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public cuenta_clientes SaldoMovimiento(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        cuenta_clientes cl = new cuenta_clientes();
        try {

            String cSql = "SELECT creferencia,saldo,documento "
                    + " FROM cuenta_clientes "
                    + " WHERE creferencia='" + id + "'"
                    + " AND saldo<>importe "
                    + " ORDER BY cuenta_clientes.creferencia ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cl.setDocumento(rs.getString("documento"));
                    cl.setSaldo(rs.getBigDecimal("saldo"));
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

    public ArrayList<cuenta_clientes> ExtractoxCliente(String cliente, int moneda, Date fechai, Date fechaf) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();

        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT 'SALDO ANTERIOR' AS descripcion,cliente,fecha,moneda,documento,"
                    + "000000000000 AS recibo,"
                    + "SUM(importe) AS debe,0000000000000.00 AS haber,"
                    + "autorizacion "
                    + " FROM cuenta_clientes "
                    + " INNER JOIN clientes"
                    + " ON clientes.codigo=cuenta_clientes.cliente"
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=cuenta_clientes.comprobante "
                    + " WHERE fecha<'" + fechai + "'"
                    + " AND moneda=" + moneda
                    + " AND clientes.ruc='" + cliente + "'"
                    + " GROUP BY cliente "
                    + " UNION ALL"
                    + " SELECT 'SALDO ANTERIOR' AS descripcion,cliente,fecha,moneda,"
                    + "nrofactura AS documento,numero AS recibo,"
                    + " 0000000000000.00 AS debe,SUM(pago) AS haber,"
                    + "autorizacion "
                    + " FROM vista_cobranzas"
                    + " INNER JOIN clientes"
                    + " ON clientes.codigo=vista_cobranzas.cliente"
                    + " WHERE fecha<'" + fechai + "'"
                    + " AND moneda=" + moneda
                    + " AND clientes.ruc='" + cliente + "'"
                    + " GROUP BY cliente"
                    + " UNION ALL"
                    + " SELECT comprobantes.nombre AS descripcion,cliente,fecha,moneda,documento,"
                    + " 000000000000 AS recibo,"
                    + " importe AS debe,0000000000000.00 AS haber,"
                    + "autorizacion "
                    + " FROM cuenta_clientes "
                    + " INNER JOIN clientes"
                    + " ON clientes.codigo=cuenta_clientes.cliente"
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=cuenta_clientes.comprobante "
                    + " WHERE fecha BETWEEN '" + fechai + "' AND '" + fechaf + "'"
                    + " AND moneda=" + moneda
                    + " AND clientes.ruc='" + cliente + "'"
                    + " UNION ALL "
                    + " SELECT observacion AS descripcion,cliente,fecha,moneda,"
                    + "nrofactura AS documento,numero AS recibo,"
                    + " 0000000000000.00 AS debe,pago AS haber,"
                    + "autorizacion "
                    + " FROM vista_cobranzas"
                    + " INNER JOIN clientes"
                    + " ON clientes.codigo=vista_cobranzas.cliente"
                    + " WHERE fecha BETWEEN '" + fechai + "' AND '" + fechaf + "'"
                    + " AND moneda=" + moneda
                    + " AND clientes.ruc='" + cliente + "'"
                    + " ORDER BY fecha";

            System.out.println(cSql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    cliente c = new cliente();
                    cuenta_clientes cc = new cuenta_clientes();
                    cc.setCliente(c);
                    cc.setDocumento(rs.getString("documento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDescripcion(rs.getString("descripcion"));
                    cc.setNrorecibo(rs.getString("recibo"));
                    cc.setCreditos(rs.getDouble("debe"));
                    cc.setDebitos(rs.getDouble("haber"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    if (rs.getString("descripcion").equals("SALDO ANTERIOR")) {
                        cc.setCreditos(0);
                        cc.setDebitos(0);
                        ncredito = ncredito + rs.getDouble("debe");
                        ndebito = ndebito + rs.getDouble("haber");
                        saldoanterior = (ncredito - ndebito);
                    } else if (saldoanterior != 0) {
                        cc.setFecha(fechai);
                        cc.setCreditos(saldoanterior);
                        cc.setDebitos(0);
                        cc.setNrorecibo("0");
                        cc.setDocumento("0");
                        cc.setDescripcion("SALDO ANTERIOR");
                        lista.add(cc);
                        saldoanterior = 0;
                        cuenta_clientes cta = new cuenta_clientes();
                        cta.setCliente(c);
                        cta.setDocumento(rs.getString("documento"));
                        cta.setFecha(rs.getDate("fecha"));
                        cta.setDescripcion(rs.getString("descripcion"));
                        cta.setNrorecibo(rs.getString("recibo"));
                        cta.setCreditos(rs.getDouble("debe"));
                        cta.setDebitos(rs.getDouble("haber"));
                        cta.setAutorizacion(rs.getString("autorizacion"));
                        lista.add(cta);
                    } else {
                        lista.add(cc);
                    }
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public cuenta_clientes ActualizarTicket(cuenta_clientes cta) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;

        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cuenta_clientes"
                + " SET documento=?,autorizacion=?,sucursal=? WHERE creferencia= '" + cta.getCreferencia() + "'");
        ps.setString(1, cta.getDocumento());
        ps.setString(2, cta.getAutorizacion());
        ps.setInt(3, cta.getSucursal());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return cta;
    }

    public boolean guardarCuentaFerremax(String detallecuota) throws SQLException {
        boolean guardacuota = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectacuota = st.getConnection();
        conectacuota.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detallecuota);
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
                            + "comprobante,"
                            + "vendedor,"
                            + "idedificio,"
                            + "importe,"
                            + "numerocuota,"
                            + "cuota,"
                            + "saldo"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddocumento").getAsString());
                        ps.setString(2, obj.get("creferencia").getAsString());
                        ps.setString(3, obj.get("documento").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("vencimiento").getAsString());
                        ps.setInt(6, obj.get("cliente").getAsInt());
                        ps.setInt(7, obj.get("sucursal").getAsInt());
                        ps.setInt(8, obj.get("moneda").getAsInt());
                        ps.setInt(9, obj.get("comprobante").getAsInt());
                        ps.setInt(10, obj.get("vendedor").getAsInt());
                        ps.setString(11, obj.get("idedificio").getAsString());
                        ps.setString(12, obj.get("importe").getAsString());
                        ps.setInt(13, obj.get("numerocuota").getAsInt());
                        ps.setInt(14, obj.get("cuota").getAsInt());
                        ps.setString(15, obj.get("saldo").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardacuota = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardacuota = false;
                    break;
                }
            }

            if (guardacuota) {
                try {
                    conectacuota.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
                    conectacuota.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardacuota = false;
        }
        st.close();
        conectacuota.close();
        return guardacuota;
    }

    public ArrayList<cuenta_clientes> FacturasalaFechaObra(Date fechai, int moneda, int cliente) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cli.codigo,"
                    + "cli.nombre as nombrecliente,"
                    + "cuenta_clientes.documento,"
                    + "cli.ruc,"
                    + "cuenta_clientes.comprobante,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "cuenta_clientes.fecha,"
                    + "cuenta_clientes.vencimiento,"
                    + "cuenta_clientes.iddocumento,"
                    + "cuenta_clientes.importe,"
                    + "cuenta_clientes.idedificio,"
                    + "obras.nombre as nombreobra,"
                    + "COALESCE(p.supago,0) AS supago,cuenta_clientes.autorizacion "
                    + "FROM clientes cli "
                    + "INNER JOIN cuenta_clientes "
                    + "ON cuenta_clientes.cliente=cli.codigo "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=cuenta_clientes.comprobante "
                    + " LEFT JOIN obras "
                    + " ON obras.codigo=cuenta_clientes.idedificio "
                    + " LEFT JOIN (SELECT cobranzas.cliente,detalle_cobranzas.idfactura, SUM(pago-(mora+gastos_cobranzas+punitorio)) AS supago "
                    + " FROM cobranzas "
                    + " LEFT JOIN detalle_cobranzas "
                    + "ON cobranzas.idpagos=detalle_cobranzas.iddetalle "
                    + " WHERE cobranzas.fecha<='" + fechai + "'"
                    + " AND cobranzas.moneda=" + moneda
                    + " GROUP BY detalle_cobranzas.idfactura) p ON p.idfactura=cuenta_clientes.iddocumento"
                    + " WHERE IF(" + cliente + "<>0,cli.codigo=" + cliente + ",TRUE) "
                    + " AND cuenta_clientes.fecha<='" + fechai + "'"
                    + " AND cuenta_clientes.moneda=" + moneda
                    + "  ORDER BY cli.ruc,cuenta_clientes.idedificio,cuenta_clientes.fecha ";

            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("codigo"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setCreditos(rs.getDouble("importe"));
                    cc.setDebitos(rs.getDouble("supago"));
                    cc.setAsesor(rs.getInt("idedificio"));
                    cc.setNombreobra(rs.getString("nombreobra"));
                    cc.setSaldofactura(cc.getCreditos() - cc.getDebitos());
                    if (cc.getSaldofactura() != 0) {
                        lista.add(cc);
                    }
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarSaldoxSocioDetalladoConSaldo(int cliente, Date dInicio) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT clientes.codigo,clientes.categoria,clientes.asesor,clientes.nombre AS nombrecliente,"
                    + "clientes.celular, clientes.direccion, clientes.mail,giradurias.nombre AS nombregiraduria,"
                    + "clientes.salario,clientes.ruc,clientes.telefono,"
                    + "cuenta_clientes.documento,cuenta_clientes.fecha,cuenta_clientes.vencimiento,"
                    + "cuenta_clientes.comprobante,cuenta_clientes.importe,cuenta_clientes.saldo,"
                    + "cuenta_clientes.numerocuota,cuenta_clientes.cuota,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "casas.nombre as nombrecomercio "
                    + "FROM clientes "
                    + "LEFT JOIN giradurias "
                    + "ON giradurias.codigo=clientes.giraduria "
                    + "LEFT JOIN "
                    + "cuenta_clientes "
                    + "ON cuenta_clientes.cliente=clientes.codigo "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cuenta_clientes.comprobante "
                    + "LEFT JOIN casas "
                    + "ON casas.codigo=cuenta_clientes.comercial "
                    + "WHERE clientes.codigo= ? "
                    + "AND cuenta_clientes.fecha<= ? "
                    + " ORDER BY cuenta_clientes.cliente,cuenta_clientes.fecha,cuenta_clientes.documento,cuenta_clientes.cuota";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ps.setDate(2, dInicio);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("codigo"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> VencimientoxGiraduriaExcel(int giraduria, Date fechaini, Date fechafin) throws SQLException, IOException {
        String nombrearchivo = "";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            nombrearchivo = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(nombrearchivo);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sqlregistro = "CREATE TEMPORARY TABLE tablavence("
                + "anual INT(4) DEFAULT 2022,"
                + "mes CHAR(2),"
                + "entidad CHAR(4),"
                + "cedula CHAR(15),"
                + "beneficiario int(6) DEFAULT 0,"
                + "nombre char(40), "
                + "numerocuota int(3) default 0,"
                + "cuota int(3) DEFAULT 0,"
                + "importe decimal(10),"
                + "total DECIMAL(10),"
                + "boleta int(8), "
                + "estado CHAR(1) DEFAULT 'N')";

        PreparedStatement psregistro = conn.prepareStatement(sqlregistro);
        psregistro.executeUpdate(sqlregistro);

        String sqlGrabar = "INSERT INTO tablavence (anual,mes,entidad,"
                + "cedula,beneficiario,nombre,numerocuota,cuota,importe,total,boleta,estado)"
                + "SELECT YEAR(cuenta_clientes.vencimiento),"
                + "SUBSTR(DATE_FORMAT(cuenta_clientes.fecha,'%d/%m/%Y'),4,2),'0055' AS entidad,"
                + "clientes.cedula,123456 as beneficiario,SUBSTR(clientes.nombre,1,40),"
                + "cuenta_clientes.numerocuota,cuenta_clientes.cuota,cuenta_clientes.saldo,"
                + "SUM(cuenta_clientes.importe*cuenta_clientes.numerocuota) AS total,"
                + "substr(cuenta_clientes.documento,1,8), 'N' AS estado "
                + "FROM cuenta_clientes "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=cuenta_clientes.cliente "
                + " WHERE vencimiento BETWEEN '" + fechaini + "' AND  '" + fechafin + "'"
                + " AND cuenta_Clientes.giraduria=" + giraduria
                + "GROUP BY cuenta_clientes.iddocumento";

        PreparedStatement psplandatos = conn.prepareStatement(sqlGrabar);
        psplandatos.executeUpdate(sqlGrabar);

        String sqlventares = "SELECT *"
                + " FROM tablavence ";

        PreparedStatement psventares = conn.prepareStatement(sqlventares);
        ResultSet res = psventares.executeQuery(sqlventares);

        try {

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Código Registro"); //INT
            rowhead.createCell((short) 1).setCellValue("Código Identificador Comprador"); //INT
            rowhead.createCell((short) 2).setCellValue("Número Identificación");//String
            rowhead.createCell((short) 3).setCellValue("Razón Social");//String
            rowhead.createCell((short) 4).setCellValue("Código Comprobante "); //int
            rowhead.createCell((short) 5).setCellValue("Fecha Emisión "); //String
            rowhead.createCell((short) 6).setCellValue("N° Timbrado "); //double
            rowhead.createCell((short) 7).setCellValue("N° Comprobante "); //String
            rowhead.createCell((short) 8).setCellValue("Monto Gravado 10 "); //double
            rowhead.createCell((short) 9).setCellValue("Monto Gravado 5 "); //double
            rowhead.createCell((short) 10).setCellValue("Monto Exento "); //double
            rowhead.createCell((short) 11).setCellValue("Monto Total "); //double
            rowhead.createCell((short) 12).setCellValue("Condición Venta"); //int
            rowhead.createCell((short) 13).setCellValue("Moneda Extranjera"); //String
            rowhead.createCell((short) 14).setCellValue("Imputa al IVA"); //String
            rowhead.createCell((short) 15).setCellValue("Imputa al IRE"); //String
            rowhead.createCell((short) 16).setCellValue("Imputa al IRP-RSP"); //String
            rowhead.createCell((short) 17).setCellValue("Comprobante Asociado"); //String
            rowhead.createCell((short) 18).setCellValue("timbradoasociado"); //double

            int index = 1;
            while (res.next()) {

                HSSFRow row = sheet.createRow((short) index);
                row.createCell((short) 0).setCellValue(res.getInt(1));
                row.createCell((short) 1).setCellValue(res.getInt(2));
                row.createCell((short) 2).setCellValue(res.getString(3));
                row.createCell((short) 3).setCellValue(res.getString(4));
                row.createCell((short) 4).setCellValue(res.getInt(5));
                row.createCell((short) 5).setCellValue(res.getString(6));
                row.createCell((short) 6).setCellValue(res.getDouble(7));
                row.createCell((short) 7).setCellValue(res.getString(8));
                row.createCell((short) 8).setCellValue(res.getDouble(9));
                row.createCell((short) 9).setCellValue(res.getDouble(10));
                row.createCell((short) 10).setCellValue(res.getDouble(11));
                row.createCell((short) 11).setCellValue(res.getDouble(12));
                row.createCell((short) 12).setCellValue(res.getInt(13));
                row.createCell((short) 13).setCellValue(res.getString(14));
                row.createCell((short) 14).setCellValue(res.getString(15));
                row.createCell((short) 15).setCellValue(res.getString(16));
                row.createCell((short) 16).setCellValue(res.getString(17));
                row.createCell((short) 17).setCellValue(res.getString(18));
                row.createCell((short) 18).setCellValue(res.getDouble(19));
                index++;
            }
            //FileOutputStream fileOut = new FileOutputStream("c:\\Resolucion\\excelFile.xls");
            FileOutputStream fileOut = new FileOutputStream(nombrearchivo);
            wb.write(fileOut);
            fileOut.close();
            res.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }

    public ArrayList<cuenta_clientes> MostrarxFechaComprobante(Date fechai, Date fechaf, int moneda1, int moneda2, int comprobante) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,cuenta_clientes.amortiza,cuenta_clientes.minteres,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio, ";
            cSql = cSql + "cuenta_clientes.moneda,monedas.nombre as nombremoneda ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "LEFT JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cuenta_clientes.moneda ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " AND cuenta_clientes.vencimiento between ? AND ? ";
            cSql = cSql + " AND IF(?<>0,cuenta_clientes.moneda=?,TRUE) ";
            cSql = cSql + " AND cuenta_clientes.comprobante=? ";
            cSql = cSql + " ORDER BY cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setDate(1, fechai);
                ps.setDate(2, fechaf);
                ps.setInt(3, moneda1);
                ps.setInt(4, moneda2);
                ps.setInt(5, comprobante);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();
                    moneda moneda = new moneda();
                    cc.setMoneda(moneda);
                    cc.setCliente(c);
                    cc.setComprobante(m);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.getMoneda().setCodigo(rs.getInt("moneda"));
                    cc.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cc.setAmortiza(rs.getBigDecimal("amortiza"));
                    cc.setMinteres(rs.getBigDecimal("minteres"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public cuenta_clientes MostrarAgrupadoxCliente(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        cliente c = new cliente();
        cuenta_clientes cc = new cuenta_clientes();

        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT SUM(saldo) as deuda ";
            cSql = cSql + " FROM cuenta_clientes ";
            cSql = cSql + " WHERE cuenta_clientes.cliente=" + id;
            cSql = cSql + " and  saldo<>0 ";

            System.out.println(cSql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    if (rs.getBigDecimal("deuda") == null) {
                        cc.setSaldo(BigDecimal.ZERO);
                    } else {
                        cc.setSaldo(rs.getBigDecimal("deuda"));
                    }
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        conn.close();
        st.close();
        return cc;
    }

    public ArrayList<cuenta_clientes> MostrarxAlumno(int nperiodo, int ncliente) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.producto,cuenta_clientes.fecha_pago,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,";
            cSql = cSql + "cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.nombre as nombrecliente,";
            cSql = cSql + "clientes.celular, clientes.direccion,cuenta_clientes.producto,"
                    + " clientes.mail,productos.nombre as nombreproducto,";
            cSql = cSql + "cuenta_clientes.moneda,monedas.nombre as nombremoneda ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "LEFT JOIN productos ";
            cSql = cSql + "ON productos.codigo=cuenta_clientes.producto ";
            cSql = cSql + "LEFT JOIN monedas ";
            cSql = cSql + " ON monedas.codigo=cuenta_clientes.moneda ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " AND cuenta_clientes.periodo=? ";
            cSql = cSql + " AND cuenta_clientes.cliente=? ";
            cSql = cSql + " ORDER BY cuenta_clientes.comprobante,cuenta_clientes.vencimiento";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nperiodo);
                ps.setInt(2, ncliente);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    cuenta_clientes cc = new cuenta_clientes();
                    moneda moneda = new moneda();
                    producto pr = new producto();
                    cc.setMoneda(moneda);
                    cc.setCliente(c);
                    cc.setProducto(pr);
                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getProducto().setCodigo(rs.getString("producto"));
                    cc.getProducto().setNombre(rs.getString("nombreproducto"));
                    cc.getMoneda().setCodigo(rs.getInt("moneda"));
                    cc.getMoneda().setNombre(rs.getString("nombremoneda"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> ResumeSaldoalaFechaxRUC(Date fechai, int moneda, int cliente) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SELECT clientes.codigo,clientes.ruc,clientes.nombre,clientes.direccion,clientes.telefono,"
                    + "COALESCE(sa.totalimporte,0) AS totalimporte,"
                    + "COALESCE(pa.pagos,0) AS pagos "
                    + " FROM clientes "
                    + " LEFT JOIN (SELECT cuenta_clientes.cliente,clientes.ruc, "
                    + " SUM(importe) AS totalimporte"
                    + " FROM cuenta_clientes "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=cuenta_clientes.cliente"
                    + " WHERE cuenta_clientes.fecha<='" + fechai + "'"
                    + " AND cuenta_clientes.moneda=" + moneda
                    + " GROUP BY clientes.ruc) "
                    + " sa ON sa.ruc=clientes.ruc "
                    + " LEFT JOIN (SELECT cobranzas.cliente,clientes.ruc,"
                    + " SUM(cobranzas.totalpago) AS pagos "
                    + " FROM cobranzas "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=cobranzas.cliente"
                    + " WHERE cobranzas.fecha <='" + fechai + "'"
                    + " AND cobranzas.moneda=" + moneda
                    + " GROUP BY clientes.ruc) "
                    + " pa ON pa.ruc=clientes.ruc "
                    + " WHERE IF(" + cliente + "<>0,clientes.codigo=" + cliente + ",TRUE) "
                    + " GROUP BY clientes.ruc "
                    + " HAVING(totalimporte>0) "
                    + " ORDER BY clientes.codigo ";

            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente c = new cliente();
                    cuenta_clientes cc = new cuenta_clientes();
                    cc.setCliente(c);
                    cc.getCliente().setCodigo(rs.getInt("codigo"));
                    cc.getCliente().setNombre(rs.getString("nombre"));
                    cc.getCliente().setRuc(rs.getString("ruc"));
                    cc.getCliente().setTelefono(rs.getString("telefono"));
                    cc.setCreditos(rs.getDouble("totalimporte"));
                    cc.setDebitos(rs.getDouble("pagos"));
                    cc.setSaldofactura(cc.getCreditos() - cc.getDebitos());
                    if (cc.getSaldofactura() != 0) {
                        lista.add(cc);
                    }
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public boolean guardarCuentaPrestamo(String detallecuota) throws SQLException {
        boolean guardacuota = true;
        con = new Conexion();
        st = con.conectar();
        Connection conectacuota = st.getConnection();
        conectacuota.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detallecuota);
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
                            + "comprobante,"
                            + "vendedor,"
                            + "idedificio,"
                            + "importe,"
                            + "amortiza,"
                            + "minteres,"
                            + "numerocuota,"
                            + "cuota,"
                            + "saldo"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, obj.get("iddocumento").getAsString());
                        ps.setString(2, obj.get("creferencia").getAsString());
                        ps.setString(3, obj.get("documento").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("vencimiento").getAsString());
                        ps.setInt(6, obj.get("cliente").getAsInt());
                        ps.setInt(7, obj.get("sucursal").getAsInt());
                        ps.setInt(8, obj.get("moneda").getAsInt());
                        ps.setInt(9, obj.get("comprobante").getAsInt());
                        ps.setInt(10, obj.get("vendedor").getAsInt());
                        ps.setString(11, obj.get("idedificio").getAsString());
                        ps.setString(12, obj.get("importe").getAsString());
                        ps.setString(13, obj.get("amortiza").getAsString());
                        ps.setString(14, obj.get("minteres").getAsString());
                        ps.setInt(15, obj.get("numerocuota").getAsInt());
                        ps.setInt(16, obj.get("cuota").getAsInt());
                        ps.setString(17, obj.get("saldo").getAsString());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardacuota = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardacuota = false;
                    break;
                }
            }

            if (guardacuota) {
                try {
                    conectacuota.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
                    conectacuota.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            guardacuota = false;
        }
        st.close();
        conectacuota.close();
        return guardacuota;
    }

    public boolean ActualizarNumero(String cReferencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        String sqldatos = "UPDATE cuenta_clientes SET documento=(SELECT numero "
                + "FROM prestamos WHERE prestamos.idprestamos=cuenta_clientes.creferencia) "
                + "WHERE cuenta_clientes.creferencia=?";
        ps = st.getConnection().prepareStatement(sqldatos);
        ps.setString(1, cReferencia);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<cuenta_clientes> MostrarxClienteMonedaRucVence(int cliente, int moneda, String cRuc) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nombre as nombrecomprobante,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "giradurias.nombre as nombregiraduria ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN clientes ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "LEFT JOIN giradurias ";
            cSql = cSql + "ON giradurias.codigo=cuenta_clientes.giraduria ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + "AND cuenta_clientes.moneda= ? ";
            cSql = cSql + "AND clientes.ruc= ? ";
            cSql = cSql + " ORDER by cuenta_clientes.documento,cuenta_clientes.cuota";
            //cSql = cSql + " ORDER by cuenta_clientes.fecha";
            //cSql = cSql + " ORDER by cuenta_clientes.vencimiento";            

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ps.setInt(1, moneda);
                ps.setString(2, cRuc);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cc.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setAutorizacion(rs.getString("autorizacion"));
                    cc.setNrocuenta(rs.getString("nrocuenta"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));

                    lista.add(cc);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        conn.close();
        st.close();
        return lista;
    }

    public ArrayList<cuenta_clientes> Factura_pendiente_xemision(int cliente, Date dInicio, Date dFinal) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,";
            cSql = cSql + "cuenta_clientes.importe AS importecuota,cuenta_clientes.cliente,cuenta_clientes.saldo,";
            cSql = cSql + "cuenta_clientes.cuota AS nrocuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.nombre as nombrecliente,";
            cSql = cSql + "comprobantes.nombre as nombrecomprobante,comprobantes.nomalias ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " AND cuenta_clientes.cliente= ? ";
            cSql = cSql + " AND cuenta_clientes.fecha BETWEEN  ?  AND ? ";
            cSql = cSql + " ORDER by cuenta_clientes.fecha";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ps.setDate(2, dInicio);
                ps.setDate(3, dFinal);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();

                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setCreferencia(rs.getString("creferencia"));
                    cc.setIddocumento(rs.getString("iddocumento"));
                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importecuota"));
                    cc.getCliente().setCodigo(rs.getInt("cliente"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setCuota(rs.getInt("nrocuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }



    public boolean borrarItem(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuenta_clientes WHERE iddocumento=?");
        ps.setString(1, referencia);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }


        public ArrayList<cuenta_clientes> MostrarSaldoxClienteDetallado(int cliente, Date dInicio) throws SQLException {
        ArrayList<cuenta_clientes> lista = new ArrayList<cuenta_clientes>();
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT clientes.codigo,clientes.categoria,clientes.asesor,clientes.nombre AS nombrecliente,"
                    + "clientes.celular, clientes.direccion, clientes.mail,"
                    + "clientes.salario,clientes.ruc,clientes.telefono,"
                    + "cuenta_clientes.documento,cuenta_clientes.fecha,cuenta_clientes.vencimiento,"
                    + "cuenta_clientes.comprobante,cuenta_clientes.importe,cuenta_clientes.saldo,"
                    + "cuenta_clientes.numerocuota,cuenta_clientes.cuota,"
                    + "comprobantes.nombre AS nombrecomprobante "
                    + "FROM clientes "
                    + "LEFT JOIN "
                    + "cuenta_clientes "
                    + "ON cuenta_clientes.cliente=clientes.codigo "
                    + "LEFT JOIN comprobantes "
                    + "ON comprobantes.codigo=cuenta_clientes.comprobante "
                    + "WHERE clientes.codigo= ? "
                    + "AND cuenta_clientes.fecha<= ?"
                    + "AND cuenta_clientes.saldo<>0 "
                    + "ORDER BY cuenta_clientes.cliente,cuenta_clientes.fecha,cuenta_clientes.documento,cuenta_clientes.cuota";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, cliente);
                ps.setDate(2, dInicio);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    giraduria giraduria = new giraduria();
                    cliente c = new cliente();
                    comprobante m = new comprobante();
                    cuenta_clientes cc = new cuenta_clientes();

                    cc.setGiraduria(giraduria);
                    cc.setCliente(c);
                    cc.setComprobante(m);

                    cc.setFecha(rs.getDate("fecha"));
                    cc.setDocumento(rs.getString("documento"));
                    cc.setVencimiento(rs.getDate("vencimiento"));
                    cc.setImporte(rs.getBigDecimal("importe"));
                    cc.getCliente().setCodigo(rs.getInt("codigo"));
                    cc.getCliente().setNombre(rs.getString("nombrecliente"));
                    cc.getComprobante().setCodigo(rs.getInt("comprobante"));
                    cc.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    cc.setSaldo(rs.getBigDecimal("saldo"));
                    cc.setCuota(rs.getInt("cuota"));
                    cc.setNumerocuota(rs.getInt("numerocuota"));
                    cc.setPlazo(rs.getInt("numerocuota"));
                    lista.add(cc);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    
    
    
    
}

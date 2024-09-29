/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.BuscadorImpresora;
import Clases.numero_a_letras;
import Conexion.Conexion;
import Modelo.banco;
import Modelo.caja;
import Modelo.configuracion;
import Modelo.extraccion;
import Modelo.moneda;
import Modelo.sucursal;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.openide.util.Exceptions;

/**
 *
 * @author Usuario
 */
public class extraccionDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<extraccion> MostrarxFecha(int nbanco, Date fechaini, Date fechafin, String cTipo) throws SQLException {
        ArrayList<extraccion> lista = new ArrayList<extraccion>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT idcontrol,idmovimiento,documento,extracciones.fecha,sucursal,banco,"
                    + "moneda,cotizacion,importe,observaciones,vencimiento,extracciones.tipo,"
                    + "retirado,cobrado,asiento,extracciones.idcta,chequenro,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "monedas.nombre AS nombremoneda,"
                    + "bancos.nombre AS nombrebanco "
                    + "FROM extracciones "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=extracciones.sucursal "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=extracciones.moneda "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=extracciones.moneda "
                    + "WHERE extracciones.banco=? "
                    + "AND extracciones.fecha between ? AND ? "
                    + "AND extracciones.tipo=? "
                    + "ORDER BY extracciones.fecha ";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, nbanco);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);
                ps.setString(4, cTipo);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    banco banco = new banco();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();
                    extraccion ext = new extraccion();
                    ext.setSucursal(sucursal);
                    ext.setBanco(banco);
                    ext.setMoneda(moneda);
                    ext.setIdcontrol(rs.getInt("idcontrol"));
                    ext.setIdmovimiento(rs.getString("idmovimiento"));
                    ext.setDocumento(rs.getString("documento"));
                    ext.setFecha(rs.getDate("fecha"));
                    ext.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ext.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ext.getBanco().setCodigo(rs.getInt("banco"));
                    ext.getBanco().setNombre(rs.getString("nombrebanco"));
                    ext.getMoneda().setCodigo(rs.getInt("moneda"));
                    ext.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ext.setCotizacion(rs.getBigDecimal("cotizacion"));
                    ext.setImporte(rs.getBigDecimal("importe"));
                    ext.setObservaciones(rs.getString("observaciones"));
                    ext.setChequenro(rs.getString("chequenro"));
                    ext.setIdcta(rs.getString("idcta"));
                    ext.setAsiento(rs.getInt("asiento"));
                    ext.setCobrado(rs.getDate("cobrado"));
                    ext.setVencimiento(rs.getDate("vencimiento"));
                    ext.setTipo(rs.getString("tipo"));
                    lista.add(ext);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public extraccion insertarMovBanco(extraccion mov) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO extracciones (idmovimiento,documento,fecha,sucursal,"
                + "banco,tipo,cotizacion,importe,observaciones,chequenro,vencimiento,moneda) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, mov.getIdmovimiento());
        ps.setString(2, mov.getDocumento());
        ps.setDate(3, mov.getFecha());
        ps.setInt(4, mov.getSucursal().getCodigo());
        ps.setInt(5, mov.getBanco().getCodigo());
        ps.setString(6, mov.getTipo());
        ps.setBigDecimal(7, mov.getCotizacion());
        ps.setBigDecimal(8, mov.getImporte());
        ps.setString(9, mov.getObservaciones());
        ps.setString(10, mov.getChequenro());
        ps.setDate(11, mov.getVencimiento());
        ps.setInt(12, mov.getMoneda().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return mov;
    }

    public extraccion insertarMovBancoCbsa(extraccion mov) throws SQLException {
        Conexion con = null;
        PreparedStatement ps = null;
        try {
            con = new Conexion();
            st = con.conectar();

            ps = st.getConnection().prepareStatement("INSERT INTO extracciones (idmovimiento,documento,fecha,sucursal,"
                    + "banco,tipo,cotizacion,importe,observaciones,chequenro,vencimiento,moneda,idcta) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, mov.getIdmovimiento());
            ps.setString(2, mov.getDocumento());
            ps.setDate(3, mov.getFecha());
            ps.setInt(4, mov.getSucursal().getCodigo());
            ps.setInt(5, mov.getBanco().getCodigo());
            ps.setString(6, mov.getTipo());
            ps.setBigDecimal(7, mov.getCotizacion());
            ps.setBigDecimal(8, mov.getImporte());
            ps.setString(9, mov.getObservaciones());
            ps.setString(10, mov.getChequenro());
            ps.setDate(11, mov.getVencimiento());
            ps.setInt(12, mov.getMoneda().getCodigo());
            ps.setString(13, mov.getIdcta());

            ps.executeUpdate();
            return mov;

        } finally {
            if (ps != null) {
                ps.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.conectar().close();
            }
        }
    }

    public boolean borrarExtraccion(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM extracciones WHERE idmovimiento=?");
        ps.setString(1, id);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean grabar(String id, String detallebanco) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM extracciones WHERE idmovimiento=?");
        ps.setString(1, id);
        guardado = guardarDebitoCredito(id, detallebanco, con);
        st.close();
        ps.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
    }

    public boolean guardarDebitoCredito(String id, String detallebanco, Conexion conexion) throws SQLException {
        boolean guardado = true;
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        try {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detallebanco);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "INSERT INTO extracciones("
                            + "idmovimiento,"
                            + "documento,"
                            + "proveedor,"
                            + "fecha,"
                            + "sucursal,"
                            + "banco,"
                            + "moneda,"
                            + "cotizacion,"
                            + "importe,"
                            + "chequenro,"
                            + "observaciones,"
                            + "vencimiento"
                            + ") "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                        ps.setString(1, id);
                        ps.setString(2, obj.get("documento").getAsString());
                        ps.setString(3, obj.get("proveedor").getAsString());
                        ps.setString(4, obj.get("fecha").getAsString());
                        ps.setString(5, obj.get("sucursal").getAsString());
                        ps.setString(6, obj.get("banco").getAsString());
                        ps.setString(7, obj.get("moneda").getAsString());
                        ps.setString(8, obj.get("cotizacion").getAsString());
                        ps.setString(9, obj.get("importe").getAsString());
                        ps.setString(10, obj.get("chequenro").getAsString());
                        ps.setString(11, obj.get("observaciones").getAsString());
                        ps.setString(12, obj.get("vencimiento").getAsString());

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

    public ArrayList<extraccion> SaldAnterior(int nBanco, Date d1) throws SQLException {
        ArrayList<extraccion> lista = new ArrayList<extraccion>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        Double nSaldo = 0.00;
        String cSql = "SELECT  documento,fecha,"
                + "SUM(IF(tipo ='C', importe, 0)) credito,"
                + "SUM(IF(tipo ='D', importe, 0)) debito "
                + "FROM extracciones "
                + "WHERE vencimiento<'" + d1 + "'"
                + " AND banco=" + nBanco
                + " GROUP BY banco"
                + " ORDER BY fecha";

        try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                extraccion a = new extraccion();
                a.setFecha(rs.getDate("fecha"));
                a.setDocumento(rs.getString("documento"));
                a.setCredito(rs.getDouble("credito"));
                a.setDebito(rs.getDouble("debito"));

                if (a.getCredito() == 0) {
                    a.setCredito(nSaldo);
                }
                if (a.getDebito() == 0) {
                    a.setDebito(nSaldo);
                }
                a.setCredito((a.getCredito() - a.getDebito()));

                lista.add(a);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        conn.close();
        st.close();
        return lista;
    }

    public ArrayList<extraccion> ExtractoxFecha(int nbanco, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<extraccion> lista = new ArrayList<extraccion>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT idcontrol,idmovimiento,documento,extracciones.fecha,sucursal,banco,"
                    + "moneda,cotizacion,importe,observaciones,vencimiento,extracciones.tipo,"
                    + "retirado,cobrado,asiento,extracciones.idcta,chequenro,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "monedas.nombre AS nombremoneda,"
                    + "bancos.nombre AS nombrebanco, "
                    + "SUM(IF(extracciones.tipo ='C', importe, 0)) credito,"
                    + "SUM(IF(extracciones.tipo ='D', importe, 0)) debito "
                    + " FROM extracciones "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=extracciones.sucursal "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=extracciones.moneda "
                    + " LEFT JOIN bancos "
                    + " ON bancos.codigo=extracciones.moneda "
                    + " WHERE extracciones.banco=? "
                    + " AND extracciones.vencimiento between ? AND ? "
                    + " GROUP BY idcontrol "
                    + " ORDER BY fecha ";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, nbanco);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    banco banco = new banco();
                    moneda moneda = new moneda();
                    sucursal sucursal = new sucursal();
                    extraccion ext = new extraccion();
                    ext.setSucursal(sucursal);
                    ext.setBanco(banco);
                    ext.setMoneda(moneda);
                    ext.setIdcontrol(rs.getInt("idcontrol"));
                    ext.setIdmovimiento(rs.getString("idmovimiento"));
                    ext.setDocumento(rs.getString("documento"));
                    ext.setFecha(rs.getDate("vencimiento"));
                    ext.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ext.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ext.getBanco().setCodigo(rs.getInt("banco"));
                    ext.getBanco().setNombre(rs.getString("nombrebanco"));
                    ext.getMoneda().setCodigo(rs.getInt("moneda"));
                    ext.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ext.setCotizacion(rs.getBigDecimal("cotizacion"));
                    ext.setImporte(rs.getBigDecimal("importe"));
                    ext.setCredito(rs.getDouble("credito"));
                    ext.setDebito(rs.getDouble("debito"));
                    ext.setObservaciones(rs.getString("observaciones"));
                    ext.setChequenro(rs.getString("chequenro"));
                    ext.setIdcta(rs.getString("idcta"));
                    ext.setAsiento(rs.getInt("asiento"));
                    ext.setCobrado(rs.getDate("cobrado"));
                    ext.setVencimiento(rs.getDate("vencimiento"));
                    ext.setTipo(rs.getString("tipo"));
                    lista.add(ext);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public extraccion buscarId(int id) throws SQLException {
        banco banco = new banco();
        moneda moneda = new moneda();
        sucursal sucursal = new sucursal();
        extraccion ext = new extraccion();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT idcontrol,idmovimiento,documento,extracciones.fecha,sucursal,banco,"
                    + "moneda,cotizacion,importe,observaciones,vencimiento,extracciones.tipo,"
                    + "retirado,cobrado,asiento,extracciones.idcta,chequenro,extracciones.cierre,"
                    + "sucursales.nombre AS nombresucursal,"
                    + "monedas.nombre AS nombremoneda,"
                    + "bancos.nombre AS nombrebanco "
                    + "FROM extracciones "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=extracciones.sucursal "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=extracciones.moneda "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=extracciones.moneda "
                    + "WHERE extracciones.idcontrol=? "
                    + "ORDER BY extracciones.idcontrol ";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ext.setSucursal(sucursal);
                    ext.setBanco(banco);
                    ext.setMoneda(moneda);
                    ext.setIdcontrol(rs.getInt("idcontrol"));
                    ext.setIdmovimiento(rs.getString("idmovimiento"));
                    ext.setDocumento(rs.getString("documento"));
                    ext.setFecha(rs.getDate("vencimiento"));
                    ext.getSucursal().setCodigo(rs.getInt("sucursal"));
                    ext.getSucursal().setNombre(rs.getString("nombresucursal"));
                    ext.getBanco().setCodigo(rs.getInt("banco"));
                    ext.getBanco().setNombre(rs.getString("nombrebanco"));
                    ext.getMoneda().setCodigo(rs.getInt("moneda"));
                    ext.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ext.setCotizacion(rs.getBigDecimal("cotizacion"));
                    ext.setImporte(rs.getBigDecimal("importe"));
                    ext.setObservaciones(rs.getString("observaciones"));
                    ext.setChequenro(rs.getString("chequenro"));
                    ext.setVencimiento(rs.getDate("vencimiento"));
                    ext.setCierre(rs.getInt("cierre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return ext;

    }

    public extraccion insertarDiferidos(extraccion mov) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO extracciones (idmovimiento,documento,fecha,sucursal,"
                + "banco,tipo,cotizacion,importe,observaciones,"
                + "chequenro,vencimiento,moneda,idcta) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, mov.getIdmovimiento());
        ps.setString(2, mov.getDocumento());
        ps.setDate(3, mov.getFecha());
        ps.setInt(4, mov.getSucursal().getCodigo());
        ps.setInt(5, mov.getBanco().getCodigo());
        ps.setString(6, mov.getTipo());
        ps.setBigDecimal(7, mov.getCotizacion());
        ps.setBigDecimal(8, mov.getImporte());
        ps.setString(9, mov.getObservaciones());
        ps.setString(10, mov.getChequenro());
        ps.setDate(11, mov.getVencimiento());
        ps.setInt(12, mov.getMoneda().getCodigo());
        ps.setString(13, mov.getIdcta());
        ps.executeUpdate();
        st.close();
        ps.close();
        return mov;
    }

    public boolean ActualizarChequeDiferido(int numero) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE extracciones SET "
                + " cierre=1  WHERE idcontrol=" + numero);
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

    public extraccion insertarMovBancoFerremax(extraccion mov) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO extracciones (idmovimiento,documento,fecha,sucursal,"
                + "banco,tipo,cotizacion,importe,observaciones,chequenro,vencimiento,moneda) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, mov.getIdmovimiento());
        ps.setString(2, mov.getDocumento());
        ps.setDate(3, mov.getFecha());
        ps.setInt(4, mov.getSucursal().getCodigo());
        ps.setInt(5, mov.getBanco().getCodigo());
        ps.setString(6, mov.getTipo());
        ps.setBigDecimal(7, mov.getCotizacion());
        ps.setBigDecimal(8, mov.getImporte());
        ps.setString(9, mov.getObservaciones());
        ps.setString(10, mov.getChequenro());
        ps.setDate(11, mov.getVencimiento());
        ps.setInt(12, mov.getMoneda().getCodigo());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            // Imprimir(id);
        }
        st.close();
        ps.close();
        return mov;
    }

    public void Imprimir(int id) throws SQLException {

        //    con = new Conexion();
//      st = con.conectar();
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal suc = new sucursal();
        try {
            suc = sucDAO.buscarId(1);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        BuscadorImpresora printer = new BuscadorImpresora();

        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);

        if (printService.length > 0)//si existen impresoras
        {
            //se elige la impresora
            PrintService impresora = printer.buscar(suc.getImpresorarecibosuc());
            if (impresora != null) //Si se selecciono una impresora
            {
                try {
                    Map parameters = new HashMap();
                    //esto para el JasperReport

                    parameters.put("id", id);

                    JasperReport jasperReport;
                    JasperPrint jasperPrint;
                    //se carga el reporte
                    //URL in = this.getClass().getResource("reporte.jasper");
                    URL url = getClass().getClassLoader().getResource("Reports/ticket_egreso.jasper");

                    jasperReport = (JasperReport) JRLoader.loadObject(url);
                    //se procesa el archivo jasper
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, st.getConnection());
                    //se manda a la impresora
                    JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                    jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, impresora);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                    jrprintServiceExporter.exportReport();
                } catch (JRException ex) {
                    System.err.println("Error JRException: " + ex.getMessage());
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    public extraccion insertarDivisas(extraccion mov) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO extracciones (idmovimiento,documento,fecha,sucursal,"
                + "banco,tipo,cotizacion,importe,observaciones,"
                + "chequenro,vencimiento,moneda) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, mov.getIdmovimiento());
        ps.setString(2, mov.getDocumento());
        ps.setDate(3, mov.getFecha());
        ps.setInt(4, mov.getSucursal().getCodigo());
        ps.setInt(5, mov.getBanco().getCodigo());
        ps.setString(6, mov.getTipo());
        ps.setBigDecimal(7, mov.getCotizacion());
        ps.setBigDecimal(8, mov.getImporte());
        ps.setString(9, mov.getObservaciones());
        ps.setString(10, mov.getChequenro());
        ps.setDate(11, mov.getVencimiento());
        ps.setInt(12, mov.getMoneda().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return mov;
    }

}

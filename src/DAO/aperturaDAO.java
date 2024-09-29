/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.BuscadorImpresora;
import Clases.Config;
import Clases.numero_a_letras;
import Conexion.Conexion;
import Modelo.apertura;
import Modelo.caja;
import Modelo.configuracion;
import Modelo.sucursal;
import Modelo.usuario;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 * @author SERVIDOR
 */
public class aperturaDAO {

    Conexion con = null;
    Statement st = null;

    public apertura insertarApertura(apertura g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        int id = 0;

        ps = st.getConnection().prepareStatement("INSERT INTO apertura (caja,usuario,fecha,turno,monto,nombre,importe) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, g.getCaja().getCodigo());
        ps.setInt(2, g.getUsuario().getEmployee_id());
        ps.setDate(3, g.getFecha());
        ps.setInt(4, g.getTurno());
        ps.setDouble(5, g.getMonto());
        ps.setString(6, g.getNombre());
        ps.setDouble(7, g.getImporte());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);

            Object[] opciones = {"  Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Imprimir el Documento ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                Imprimir(id, con);
            }

        }

        st.close();
        ps.close();
        conn.close();
        return g;
    }

    public ArrayList<apertura> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT apertura.idcontrol,apertura.caja,apertura.fecha, "
                + "apertura.nombre,apertura.turno,apertura.monto, "
                + "apertura.usuario,usuarios.last_name AS nombreusuario, "
                + "cajas.nombre AS nombrecaja "
                + "FROM apertura "
                + "LEFT JOIN cajas "
                + "ON cajas.codigo=apertura.caja "
                + "LEFT JOIN usuarios "
                + "ON usuarios.employee_id=apertura.usuario "
                + "WHERE apertura.fecha BETWEEN ? AND ? "
                + " ORDER BY apertura.idcontrol";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                apertura ap = new apertura();
                caja caja = new caja();
                usuario usu = new usuario();
                ap.setCaja(caja);
                ap.setUsuario(usu);

                ap.setIdcontrol(rs.getDouble("idcontrol"));
                ap.setFecha(rs.getDate("fecha"));
                ap.setNombre(rs.getString("nombre"));
                ap.setTurno(rs.getInt("turno"));
                ap.setMonto(rs.getDouble("monto"));
                ap.getCaja().setCodigo(rs.getInt("caja"));
                ap.getCaja().setNombre(rs.getString("nombrecaja"));
                ap.getUsuario().setEmployee_id(rs.getInt("usuario"));
                ap.getUsuario().setLast_name(rs.getString("nombreusuario"));
                lista.add(ap);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<apertura> MostrarxUsuario(int id) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT apertura.idcontrol,apertura.caja,apertura.fecha, "
                + "apertura.nombre,apertura.turno,apertura.monto, "
                + "apertura.usuario,usuarios.last_name AS nombreusuario, "
                + "cajas.nombre AS nombrecaja "
                + "FROM apertura "
                + "LEFT JOIN cajas "
                + "ON cajas.codigo=apertura.caja "
                + "LEFT JOIN usuarios "
                + "ON usuarios.employee_id=apertura.usuario "
                + "WHERE apertura.usuario=? "
                + " ORDER BY apertura.idcontrol";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                apertura ap = new apertura();
                caja caja = new caja();
                usuario usu = new usuario();
                ap.setCaja(caja);
                ap.setUsuario(usu);

                ap.setIdcontrol(rs.getDouble("idcontrol"));
                ap.setFecha(rs.getDate("fecha"));
                ap.setNombre(rs.getString("nombre"));
                ap.setTurno(rs.getInt("turno"));
                ap.setMonto(rs.getDouble("monto"));
                ap.getCaja().setCodigo(rs.getInt("caja"));
                ap.getCaja().setNombre(rs.getString("nombrecaja"));
                ap.getUsuario().setEmployee_id(rs.getInt("usuario"));
                ap.getUsuario().setLast_name(rs.getString("nombreusuario"));
                lista.add(ap);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<apertura> MostrarxCaja(int id) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT apertura.idcontrol,apertura.caja,apertura.fecha, "
                + "apertura.nombre,apertura.turno,apertura.monto, "
                + "apertura.usuario,usuarios.last_name AS nombreusuario, "
                + "cajas.nombre AS nombrecaja "
                + "FROM apertura "
                + "LEFT JOIN cajas "
                + "ON cajas.codigo=apertura.caja "
                + "LEFT JOIN usuarios "
                + "ON usuarios.employee_id=apertura.usuario "
                + "WHERE apertura.caja=? "
                + " ORDER BY apertura.idcontrol";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                apertura ap = new apertura();
                caja caja = new caja();
                usuario usu = new usuario();
                ap.setCaja(caja);
                ap.setUsuario(usu);

                ap.setIdcontrol(rs.getDouble("idcontrol"));
                ap.setFecha(rs.getDate("fecha"));
                ap.setNombre(rs.getString("nombre"));
                ap.setTurno(rs.getInt("turno"));
                ap.setMonto(rs.getDouble("monto"));
                ap.getCaja().setCodigo(rs.getInt("caja"));
                ap.getCaja().setNombre(rs.getString("nombrecaja"));
                ap.getUsuario().setEmployee_id(rs.getInt("usuario"));
                ap.getUsuario().setLast_name(rs.getString("nombreusuario"));
                lista.add(ap);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public apertura insertarAperturaFerremax(apertura g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        int id = 0;

        ps = st.getConnection().prepareStatement("INSERT INTO apertura (caja,usuario,fecha,"
                + "turno,monto,nombre,moneda"
                + ",importe,cotizacion,banco) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, g.getCaja().getCodigo());
        ps.setInt(2, g.getUsuario().getEmployee_id());
        ps.setDate(3, g.getFecha());
        ps.setInt(4, g.getTurno());
        ps.setDouble(5, g.getMonto());
        ps.setString(6, g.getNombre());
        ps.setInt(7, g.getMoneda().getCodigo());
        ps.setDouble(8, g.getImporte());
        ps.setDouble(9, g.getCotizacion());
        ps.setInt(10, g.getBanco().getCodigo());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
            Object[] opciones = {"  Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Imprimir el Documento ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                Imprimir(id, con);
            }
        }
        st.close();
        ps.close();
        conn.close();
        return g;
    }

    public void Imprimir(int id, Conexion conexion) throws SQLException {
        con = new Conexion();
        st = con.conectar();

        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cNombreRecibo = config.getNombrerecibo();

        sucursalDAO sucDAO = new sucursalDAO();
        sucursal suc = new sucursal();
        try {
            suc = sucDAO.buscarId(1);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        BuscadorImpresora printer = new BuscadorImpresora();

        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);

        if (printService.length > 0) {

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
        } else {
            System.out.println("NO HAY IMPRESORA");
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.servicio;
import Modelo.producto;
import Modelo.caja;
import Modelo.configuracion;
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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 * @author Usuario
 */
public class serviciosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<servicio> Todos() throws SQLException {
        ArrayList<servicio> lista = new ArrayList<servicio>();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT numero,fecha,cajero,servicio,concepto,destino,"
                    + "importe,comision,redondeo,total,tipo,"
                    + "productos.nombre as nombreproducto,cajas.nombre as nombrecaja "
                    + "FROM servicios "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=servicios.servicio "
                    + "LEFT JOIN cajas "
                    + "ON cajas.codigo=servicios.cajero "
                    + "ORDER BY servicios.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    servicio anti = new servicio();
                    producto ficha = new producto();
                    caja ca = new caja();
                    anti.setNumero(rs.getDouble("numero"));
                    anti.setFecha(rs.getDate("fecha"));
                    anti.setConcepto(rs.getString("concepto"));
                    anti.setDestino(rs.getString("destino"));
                    anti.setImporte(rs.getDouble("importe"));
                    anti.setRedondeo(rs.getDouble("redondeo"));
                    anti.setComision(rs.getDouble("comision"));
                    anti.setTotal(rs.getDouble("total"));
                    anti.setTipo(rs.getInt("tipo"));
                    anti.setServicio(ficha);
                    anti.getServicio().setCodigo(rs.getString("servicio"));
                    anti.getServicio().setNombre(rs.getString("nombreproducto"));
                    anti.setCajero(ca);
                    anti.getCajero().setCodigo(rs.getInt("cajero"));
                    anti.getCajero().setNombre(rs.getString("nombrecaja"));
                    lista.add(anti);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<servicio> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT numero,fecha,cajero,servicio,concepto,destino,"
                + "importe,comision,redondeo,total,tipo,"
                + "productos.nombre as nombreproducto,cajas.nombre as nombrecaja "
                + "FROM servicios "
                + "LEFT JOIN productos "
                + "ON productos.codigo=servicios.servicio "
                + "LEFT JOIN cajas "
                + "ON cajas.codigo=servicios.cajero "
                + "WHERE servicios.fecha between ? AND ?  "
                + " ORDER BY servicios.numero ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                servicio anti = new servicio();
                producto ficha = new producto();
                caja ca = new caja();
                anti.setNumero(rs.getDouble("numero"));
                anti.setFecha(rs.getDate("fecha"));
                anti.setConcepto(rs.getString("concepto"));
                anti.setDestino(rs.getString("destino"));
                anti.setImporte(rs.getDouble("importe"));
                anti.setRedondeo(rs.getDouble("redondeo"));
                anti.setComision(rs.getDouble("comision"));
                anti.setTotal(rs.getDouble("total"));
                anti.setTipo(rs.getInt("tipo"));
                anti.setServicio(ficha);
                anti.getServicio().setCodigo(rs.getString("servicio"));
                anti.getServicio().setNombre(rs.getString("nombreproducto"));
                anti.setCajero(ca);
                anti.getCajero().setCodigo(rs.getInt("cajero"));
                anti.getCajero().setNombre(rs.getString("nombrecaja"));
                lista.add(anti);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public servicio buscarId(double id) throws SQLException {

        servicio anti = new servicio();
        producto ficha = new producto();
        caja ca = new caja();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT numero,fecha,cajero,servicio,concepto,destino,"
                    + "importe,comision,redondeo,total,tipo,"
                    + "productos.nombre as nombreproducto,cajas.nombre as nombrecaja "
                    + "FROM servicios "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=servicios.servicio "
                    + "LEFT JOIN cajas "
                    + "ON cajas.codigo=servicios.cajero "
                    + "WHERE servicios.numero = ?  "
                    + " ORDER BY servicios.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    anti.setNumero(rs.getDouble("numero"));
                    anti.setFecha(rs.getDate("fecha"));
                    anti.setConcepto(rs.getString("concepto"));
                    anti.setDestino(rs.getString("destino"));
                    anti.setImporte(rs.getDouble("importe"));
                    anti.setRedondeo(rs.getDouble("redondeo"));
                    anti.setComision(rs.getDouble("comision"));
                    anti.setTotal(rs.getDouble("total"));
                    anti.setTipo(rs.getInt("tipo"));
                    anti.setServicio(ficha);
                    anti.getServicio().setCodigo(rs.getString("servicio"));
                    anti.getServicio().setNombre(rs.getString("nombreproducto"));
                    anti.setCajero(ca);
                    anti.getCajero().setCodigo(rs.getInt("cajero"));
                    anti.getCajero().setNombre(rs.getString("nombrecaja"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return anti;
    }

    public servicio InsertarServicio(servicio anti) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO servicios (fecha,cajero,servicio,"
                + "concepto,destino,"
                + "importe,comision,redondeo,total,tipo)"
                + "  VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, anti.getFecha());
        ps.setInt(2, anti.getCajero().getCodigo());
        ps.setString(3, anti.getServicio().getCodigo());
        ps.setString(4, anti.getConcepto());
        ps.setString(5, anti.getDestino());
        ps.setDouble(6, anti.getImporte());
        ps.setDouble(7, anti.getComision());
        ps.setDouble(8, anti.getRedondeo());
        ps.setDouble(9, anti.getTotal());
        ps.setInt(10, anti.getTipo());
        ps.executeUpdate();
        ResultSet keyset = ps.getGeneratedKeys();
        if (keyset.next()) {
            id = keyset.getInt(1);
        }
        anti.setNumero(Double.valueOf(id));
        st.close();
        ps.close();
        return anti;
    }

    public boolean ActualizarServicio(servicio anti) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE servicios SET fecha=?,cajero=?,"
                + "servicio=?,concepto=?,destino=?,"
                + "importe=?,comision=?,redondeo=?,"
                + "total=?,tipo=? WHERE numero=" + anti.getNumero());
        ps.setDate(1, anti.getFecha());
        ps.setInt(2, anti.getCajero().getCodigo());
        ps.setString(3, anti.getServicio().getCodigo());
        ps.setString(4, anti.getConcepto());
        ps.setString(5, anti.getDestino());
        ps.setDouble(6, anti.getImporte());
        ps.setDouble(7, anti.getComision());
        ps.setDouble(8, anti.getRedondeo());
        ps.setDouble(9, anti.getTotal());
        ps.setInt(10, anti.getTipo());

        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarServicio(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM servicios WHERE numero=?");
        ps.setDouble(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void Imprimir(int id, int nTipo, Conexion conexion) throws SQLException {
        Connection conn = st.getConnection();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cReporte="";
        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte

            parameters.put("cRamo", config.getRamo());
            parameters.put("cResponsable", config.getResponsable());
            parameters.put("cRuc", config.getRuc());
            parameters.put("cTelefono", config.getTelefono());
            parameters.put("cDireccion", config.getDireccion());
            parameters.put("cNombreEmpresa", config.getEmpresa());
            parameters.put("id", id);
            
            if(nTipo==1){
                cReporte="carga_dinero.jasper";
            }
            
            JasperReport jr = null;
            URL url = getClass().getClassLoader().getResource("Reports/" + cReporte.trim());
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos
            masterPrint = JasperFillManager.fillReport(jr, parameters, st.getConnection());
            //Enviar a Vista Previa
            JasperViewer ventana = new JasperViewer(masterPrint, false);
            ventana.setTitle("Vista Previa");
            ventana.setVisible(true);
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
    }
}

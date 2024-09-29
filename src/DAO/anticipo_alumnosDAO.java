/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.anticipo_alumno;
import Modelo.cliente;
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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Usuario
 */
public class anticipo_alumnosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<anticipo_alumno> Todos() throws SQLException {
        ArrayList<anticipo_alumno> lista = new ArrayList<anticipo_alumno>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT anticipo_alumnos.idcontrol,anticipo_alumnos.fecha,"
                    + "anticipo_alumnos.funcionario,"
                    + "anticipo_alumnos.importe,anticipo_alumnos.fechagrabado,"
                    + "anticipo_alumnos.idusuario,observacion,"
                    + "clientes.nombre AS nombrefuncionario,clientes.ruc "
                    + " FROM anticipo_alumnos "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=anticipo_alumnos.funcionario"
                    + " ORDER BY anticipo_alumnos.idcontrol ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    anticipo_alumno anti = new anticipo_alumno();
                    cliente ficha = new cliente();
                    anti.setFuncionario(ficha);
                    anti.setIdcontrol(rs.getDouble("idcontrol"));
                    anti.setFecha(rs.getDate("fecha"));
                    anti.setFechagrabado(rs.getDate("fechagrabado"));
                    anti.setImporte(rs.getBigDecimal("importe"));
                    anti.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    anti.getFuncionario().setNombre(rs.getString("nombrefuncionario"));
                    anti.getFuncionario().setRuc(rs.getString("ruc"));
                    anti.setObservacion(rs.getString("observacion"));
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

    public ArrayList<anticipo_alumno> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT anticipo_alumnos.idcontrol,anticipo_alumnos.fecha,"
                + "anticipo_alumnos.funcionario,"
                + "anticipo_alumnos.importe,anticipo_alumnos.fechagrabado,"
                + "anticipo_alumnos.idusuario,observacion,"
                + "clientes.nombre AS nombrefuncionario,clientes.ruc AS ruc "
                + "FROM anticipo_alumnos "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=anticipo_alumnos.funcionario "
                + "WHERE anticipo_alumnos.fecha between ? AND ? "
                + " ORDER BY anticipo_alumnos.idcontrol ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                anticipo_alumno anti = new anticipo_alumno();
                cliente ficha = new cliente();
                anti.setFuncionario(ficha);
                anti.setIdcontrol(rs.getDouble("idcontrol"));
                anti.setFecha(rs.getDate("fecha"));
                anti.setFechagrabado(rs.getDate("fechagrabado"));
                anti.setImporte(rs.getBigDecimal("importe"));
                anti.getFuncionario().setCodigo(rs.getInt("funcionario"));
                anti.getFuncionario().setNombre(rs.getString("nombrefuncionario"));
                anti.getFuncionario().setRuc(rs.getString("ruc"));
                anti.setObservacion(rs.getString("observacion"));
                lista.add(anti);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<anticipo_alumno> MostrarxFechaxSuc(Date fechaini, Date fechafin, Integer nsuc) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT anticipo_alumnos.idcontrol,anticipo_alumnos.fecha,"
                + "anticipo_alumnos.funcionario,"
                + "anticipo_alumnos.importe,anticipo_alumnos.fechagrabado,"
                + "anticipo_alumnos.idusuario,observacion,"
                + "clientes.nombre. AS nombrefuncionario,clientes.ruc AS ruc "
                + "FROM anticipo_alumnos "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=anticipo_alumnos.funcionario "
                + "WHERE anticipo_alumnos.fecha between ? AND ? "
                + " AND anticipo_alumnos.idsucursal=? "
                + " ORDER BY anticipo_alumnos.idcontrol ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, fechaini);
            ps.setDate(2, fechafin);
            ps.setInt(3, nsuc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                anticipo_alumno anti = new anticipo_alumno();
                cliente ficha = new cliente();
                anti.setFuncionario(ficha);
                anti.setIdcontrol(rs.getDouble("idcontrol"));
                anti.setFecha(rs.getDate("fecha"));
                anti.setFechagrabado(rs.getDate("fechagrabado"));
                anti.setImporte(rs.getBigDecimal("importe"));
                anti.getFuncionario().setCodigo(rs.getInt("funcionario"));
                anti.getFuncionario().setNombre(rs.getString("nombrefuncionario"));
                anti.getFuncionario().setRuc(rs.getString("ruc"));
                anti.setObservacion(rs.getString("observacion"));
                lista.add(anti);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public anticipo_alumno buscarId(double id) throws SQLException {

        anticipo_alumno anti = new anticipo_alumno();
        cliente ficha = new cliente();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT anticipo_alumnos.idcontrol,anticipo_alumnos.fecha,"
                    + "anticipo_alumnos.funcionario,"
                    + "anticipo_alumnos.importe,anticipo_alumnos.fechagrabado,"
                    + "anticipo_alumnos.idusuario,anticipo_alumnos.observacion,"
                    + "clientes.nombre AS nombrefuncionario,clientes.ruc AS ruc "
                    + "FROM anticipo_alumnos "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=anticipo_alumnos.funcionario "
                    + "WHERE anticipo_alumnos.idcontrol=? "
                    + " ORDER BY anticipo_alumnos.idcontrol ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    anti.setFuncionario(ficha);
                    anti.setIdcontrol(rs.getDouble("idcontrol"));
                    anti.setFecha(rs.getDate("fecha"));
                    anti.setFechagrabado(rs.getDate("fechagrabado"));
                    anti.setImporte(rs.getBigDecimal("importe"));
                    anti.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    anti.getFuncionario().setNombre(rs.getString("nombrefuncionario"));
                    anti.getFuncionario().setRuc(rs.getString("ruc"));
                    anti.setObservacion(rs.getString("observacion"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return anti;
    }

    public anticipo_alumno InsertarAnticipo(anticipo_alumno anti) throws SQLException {

        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO anticipo_alumnos "
                + "(fecha,funcionario,importe,idusuario,idsucursal,observacion)"
                + "  VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, anti.getFecha());
        ps.setInt(2, anti.getFuncionario().getCodigo());
        ps.setBigDecimal(3, anti.getImporte());
        ps.setInt(4, anti.getIdusuario());
        ps.setInt(5, anti.getGiraduria());
        ps.setString(6,anti.getObservacion());
        ps.executeUpdate();
        st.close();
        ps.close();
        return anti;
    }

    public boolean ActualizarAnticipo(anticipo_alumno anti) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE anticipo_alumnos SET fecha=?,funcionario=?,"
                + "importe=?,idusuario=?,idsucursal=?,observacion=? WHERE idcontrol=" + anti.getIdcontrol());
        ps.setDate(1, anti.getFecha());
        ps.setInt(2, anti.getFuncionario().getCodigo());
        ps.setBigDecimal(3, anti.getImporte());
        ps.setInt(4, anti.getIdusuario());
        ps.setInt(5, anti.getGiraduria());
        ps.setString(6, anti.getObservacion());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarAnticipo(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM anticipo_alumnos WHERE idcontrol=?");
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

    public anticipo_alumno VerSaldo(double id) throws SQLException {

        anticipo_alumno anti = new anticipo_alumno();
        cliente ficha = new cliente();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT funcionario,sum(anticipo_alumnos.importe) as saldo "
                    + "FROM anticipo_alumnos "
                    + "WHERE anticipo_alumnos.funcionario=? "
                    + " GROUP BY funcionario "
                    + " ORDER BY anticipo_alumnos.funcionario ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    anti.setFuncionario(ficha);
                    anti.getFuncionario().setCodigo(rs.getInt("funcionario"));
                    System.out.println(rs.getInt("funcionario"));
                    if (rs.getInt("funcionario") != 0) {
                        anti.setImporte(rs.getBigDecimal("saldo"));
                    } else {
                        anti.setImporte(new BigDecimal(0));
                    }
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return anti;
    }

    public void Imprimir(int id, Conexion conexion) throws SQLException {
        con = new Conexion();
        st = con.conectar();

        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            parameters.put("cReferencia", id);

            JasperReport jr = null;

            URL url = getClass().getClassLoader().getResource("Reports/preventa_vemay.jasper");
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos
            masterPrint = JasperFillManager.fillReport(jr, parameters, st.getConnection());

            //    JasperViewer ventana = new JasperViewer(masterPrint, false);
//            ventana.setTitle("Vista Previa");
//            ventana.setVisible(true);
            JasperPrintManager.printReport(masterPrint, false);

        } catch (Exception e) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            JOptionPane.showMessageDialog(null, "No puede emitirse el Reporte");
            System.out.println(e);
        }        // TODO add your handling code here:
    }

    public ArrayList<anticipo_alumno> MostrarCuentaxFechaxAlumno(Integer nAlum1, Integer nAlum2, Integer nTipo) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String cCadenaFiltro = "";
        if (nTipo == 0) {
            cCadenaFiltro = " ";
        } else if (nTipo == 1) {
            cCadenaFiltro = " HAVING(cuenta<0) ";
        } else {
            cCadenaFiltro = " HAVING(cuenta>0)";
        }

        String sql = "SELECT anticipo_alumnos.idcontrol,anticipo_alumnos.fecha,"
                + "anticipo_alumnos.funcionario,SUM(importe) as cuenta,"
                + "anticipo_alumnos.importe,anticipo_alumnos.fechagrabado,"
                + "anticipo_alumnos.idusuario,"
                + "clientes.nombre AS nombrefuncionario,clientes.ruc AS ruc "
                + "FROM anticipo_alumnos "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=anticipo_alumnos.funcionario "
                + "WHERE  IF(?>0,anticipo_alumnos.funcionario=?,TRUE) "
                +" GROUP BY funcionario "
                + cCadenaFiltro
                + " ORDER BY clientes.nombre ";
        System.out.println(sql);
        
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, nAlum1);
            ps.setInt(2, nAlum2);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                anticipo_alumno anti = new anticipo_alumno();
                cliente ficha = new cliente();
                anti.setFuncionario(ficha);
                anti.setIdcontrol(rs.getDouble("idcontrol"));
                anti.setFecha(rs.getDate("fecha"));
                anti.setFechagrabado(rs.getDate("fechagrabado"));
                anti.setImporte(rs.getBigDecimal("cuenta"));
                anti.getFuncionario().setCodigo(rs.getInt("funcionario"));
                anti.getFuncionario().setNombre(rs.getString("nombrefuncionario"));
                anti.getFuncionario().setRuc(rs.getString("ruc"));
                lista.add(anti);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

}

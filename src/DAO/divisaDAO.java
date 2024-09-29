/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.Config;
import Conexion.Conexion;
import Modelo.banco;
import Modelo.cliente;
import Modelo.divisa;
import Modelo.formapago;
import Modelo.usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
 * @author Pc_Server
 */
public class divisaDAO {

    Conexion con = null;
    Statement st = null;

    public divisa InsertarDivisa(divisa v) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO divisas (creferencia,fecha,"
                + "operacion,cantidad,cambio,"
                + "recibe,entregado,vuelto,"
                + "cliente,formapago,caja,"
                + "chequenro,usuario,"
                + "monedaorigen,monedadestino) "
                + "VALUES (?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, v.getCreferencia());
        ps.setDate(2, v.getFecha());
        ps.setInt(3, v.getOperacion());
        ps.setDouble(4, v.getCantidad());
        ps.setDouble(5, v.getCambio());
        ps.setDouble(6, v.getRecibe());
        ps.setDouble(7, v.getEntregado());//IMPORTE QUE EL CLIENTE ENTREGA
        ps.setDouble(8, v.getVuelto());
        ps.setInt(9, v.getCliente().getCodigo());
        ps.setInt(10, v.getFormapago().getCodigo());
        ps.setInt(11, v.getCaja().getCodigo());
        ps.setString(12, v.getChequenro());
        ps.setInt(13, v.getUsuario().getEmployee_id());
        ps.setInt(14, v.getMonedaorigen());
        ps.setInt(15, v.getMonedadestino());
        try {
            ps.executeUpdate();
            ResultSet keyset = ps.getGeneratedKeys();
            if (keyset.next()) {
                id = keyset.getInt(1);
            }
            v.setNumero(Double.valueOf(id));
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }

        st.close();
        ps.close();
        conn.close();
        return v;
    }

    public boolean ActualizarDivisa(divisa v) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        boolean guardado = false;
        int id = 0;

        ps = st.getConnection().prepareStatement("UPDATE divisas SET fecha=?,"
                + "operacion=?,cantidad=?,cambio=?,"
                + "recibe=?,entregado=?,vuelto=?,"
                + "cliente=?,formapago=?,caja=?,"
                + "chequenro=?,usuario=?,"
                + "monedaorigen=?,monedadestino=? WHERE numero=" + v.getNumero());
        ps.setDate(1, v.getFecha());
        ps.setInt(2, v.getOperacion());
        ps.setDouble(3, v.getCantidad());
        ps.setDouble(4, v.getCambio());
        ps.setDouble(5, v.getRecibe());
        ps.setDouble(6, v.getEntregado());//IMPORTE QUE EL CLIENTE ENTREGA
        ps.setDouble(7, v.getVuelto());
        ps.setInt(8, v.getCliente().getCodigo());
        ps.setInt(9, v.getFormapago().getCodigo());
        ps.setInt(10, v.getCaja().getCodigo());
        ps.setString(11, v.getChequenro());
        ps.setInt(12, v.getUsuario().getEmployee_id());
        ps.setInt(13, v.getMonedaorigen());
        ps.setInt(14, v.getMonedadestino());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (guardado) {
            return true;
        } else {
            return false;
        }
    }

    public divisa buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        divisa div = new divisa();

        try {

            String sql = "SELECT numero,creferencia,fecha,operacion,"
                    + "cantidad,cambio,recibe,entregado,"
                    + "vuelto,cliente,formapago,caja,usuario,"
                    + "chequenro,monedaorigen,monedadestino,"
                    + "clientes.nombre AS nombrecliente,"
                    + "bancos.nombre AS nombrebanco,"
                    + "formaspago.nombre AS nombreformapago,"
                    + "usuarios.last_name AS nombreusuario,"
                    + "(SELECT nombre FROM monedas"
                    + " WHERE divisas.monedaorigen=monedas.codigo) nombremonedaorigen,"
                    + "(SELECT nombre FROM monedas"
                    + " WHERE divisas.monedadestino=monedas.codigo) nombremonedadestino "
                    + "FROM divisas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=divisas.cliente "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=divisas.caja "
                    + "LEFT JOIN formaspago "
                    + "ON formaspago.codigo=divisas.formapago "
                    + "LEFT JOIN usuarios "
                    + "ON usuarios.employee_id=divisas.usuario "
                    + "WHERE divisas.numero=? "
                    + "ORDER BY divisas.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    cliente cliente = new cliente();
                    formapago forma = new formapago();
                    banco banco = new banco();
                    usuario usu = new usuario();

                    div.setCliente(cliente);
                    div.setFormapago(forma);
                    div.setCaja(banco);
                    div.setUsuario(usu);

                    div.getCliente().setCodigo(rs.getInt("cliente"));
                    div.getCliente().setNombre(rs.getString("nombrecliente"));
                    div.getCaja().setCodigo(rs.getInt("caja"));
                    div.getCaja().setNombre(rs.getString("nombrebanco"));
                    div.getFormapago().setCodigo(rs.getInt("formapago"));
                    div.getFormapago().setNombre(rs.getString("nombreformapago"));
                    div.getUsuario().setEmployee_id(rs.getInt("usuario"));
                    div.getUsuario().setLast_name(rs.getString("nombreusuario"));

                    div.setNumero(rs.getDouble("numero"));
                    div.setFecha(rs.getDate("fecha"));
                    div.setOperacion(rs.getInt("operacion"));
                    div.setCantidad(rs.getDouble("cantidad"));
                    div.setCambio(rs.getDouble("cambio"));
                    div.setRecibe(rs.getDouble("recibe"));
                    div.setEntregado(rs.getDouble("entregado"));
                    div.setVuelto(rs.getDouble("vuelto"));
                    div.setChequenro(rs.getString("chequenro"));
                    div.setMonedaorigen(rs.getInt("monedaorigen"));
                    div.setMonedadestino(rs.getInt("monedadestino"));
                    div.setNombremonedaorigen(rs.getString("nombremonedaorigen"));
                    div.setNombremonedadestino(rs.getString("nombremonedadestino"));
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return div;
    }

    public ArrayList<divisa> TodosxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<divisa> lista = new ArrayList<divisa>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT numero,creferencia,fecha,operacion,"
                    + "cantidad,cambio,recibe,entregado,"
                    + "vuelto,cliente,formapago,caja,usuario,"
                    + "chequenro,monedaorigen,monedadestino,"
                    + "clientes.nombre AS nombrecliente,"
                    + "bancos.nombre AS nombrebanco,"
                    + "formaspago.nombre AS nombreformapago,"
                    + "usuarios.last_name AS nombreusuario,"
                    + "(SELECT nombre FROM monedas"
                    + " WHERE divisas.monedaorigen=monedas.codigo) nombremonedaorigen,"
                    + "(SELECT nombre FROM monedas"
                    + " WHERE divisas.monedadestino=monedas.codigo) nombremonedadestino "
                    + "FROM divisas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=divisas.cliente "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=divisas.caja "
                    + "LEFT JOIN formaspago "
                    + "ON formaspago.codigo=divisas.formapago "
                    + "LEFT JOIN usuarios "
                    + "ON usuarios.employee_id=divisas.usuario "
                    + "WHERE divisas.numero=? "
                    + "ORDER BY divisas.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    formapago forma = new formapago();
                    banco banco = new banco();
                    usuario usu = new usuario();
                    divisa div = new divisa();

                    div.setCliente(cliente);
                    div.setFormapago(forma);
                    div.setCaja(banco);
                    div.setUsuario(usu);

                    div.getCliente().setCodigo(rs.getInt("cliente"));
                    div.getCliente().setNombre(rs.getString("nombrecliente"));
                    div.getCaja().setCodigo(rs.getInt("caja"));
                    div.getCaja().setNombre(rs.getString("nombrebanco"));
                    div.getFormapago().setCodigo(rs.getInt("formapago"));
                    div.getFormapago().setNombre(rs.getString("nombreformapago"));
                    div.getUsuario().setEmployee_id(rs.getInt("usuario"));
                    div.getUsuario().setLast_name(rs.getString("nombreusuario"));

                    div.setNumero(rs.getDouble("numero"));
                    div.setFecha(rs.getDate("fecha"));
                    div.setOperacion(rs.getInt("operacion"));
                    div.setCantidad(rs.getDouble("cantidad"));
                    div.setCambio(rs.getDouble("cambio"));
                    div.setRecibe(rs.getDouble("recibe"));
                    div.setEntregado(rs.getDouble("entregado"));
                    div.setVuelto(rs.getDouble("vuelto"));
                    lista.add(div);
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean EliminarDivisa(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM divisas WHERE numero=?");
        ps.setInt(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
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

}

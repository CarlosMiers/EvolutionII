/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import Conexion.ConexionEspejo;
import Modelo.cliente;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pc_Server
 */
public class clienteVpnDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.194";
    String ip3 = "45.180.183.194";

    public cliente insertarClienteVpn(cliente c, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("INSERT INTO clientes (codigo,nombre,"
                + "direccion,localidad,"
                + "ruc,telefono,fechaingreso,"
                + "fechanacimiento,estado,cedula) VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, c.getCodigo());
        ps.setString(2, c.getNombre());
        ps.setString(3, c.getDireccion());
        ps.setInt(4, c.getLocalidad().getCodigo());
        ps.setString(5, c.getRuc());
        ps.setString(6, c.getTelefono());
        ps.setDate(7, c.getFechaingreso());
        ps.setDate(8, c.getFechanacimiento());
        ps.setInt(9, c.getEstado());
        ps.setString(10, c.getCedula());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> CARGA DE clientes " + ex.getLocalizedMessage());
        }
        stEspejo.close();
        ps.close();
        conne.close();
        return c;
    }

    public boolean actualizarClienteVpn(cliente c, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("UPDATE clientes SET nombre=?, direccion=?, localidad=?, ruc=?, telefono=?,estado=?,fechaingreso=?,fechanacimiento=? WHERE codigo=" + c.getCodigo());
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getDireccion());
        ps.setInt(3, c.getLocalidad().getCodigo());
        ps.setString(4, c.getRuc());
        ps.setString(5, c.getTelefono());
        ps.setInt(6, c.getEstado());
        ps.setDate(7, c.getFechaingreso());
        ps.setDate(8, c.getFechanacimiento());
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public cliente buscarIdSimple(int id, Integer nsuc) throws SQLException {
        cliente cl = new cliente();
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        try {
            String sql = "SELECT clientes.codigo"
                    + " FROM clientes "
                    + " WHERE clientes.codigo=? "
                    + " ORDER BY clientes.codigo ";
            try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cl.setCodigo(rs.getInt("codigo"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        stEspejo.close();

        return cl;
    }

}

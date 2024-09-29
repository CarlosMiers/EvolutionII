/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import ExportaVtaDAO.*;
import Conexion.ConexionEspejo;
import Modelo.cliente;
import Modelo.giraduria;
import Modelo.localidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class ExportaClienteDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public cliente insertarCliente(cliente c, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }

        Connection conne = stEspejo.getConnection();

        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("INSERT INTO clientes (codigo,nombre,direccion,localidad,ruc,telefono,fechaingreso,fechanacimiento,estado) VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, c.getCodigo());
        ps.setString(2, c.getNombre());
        ps.setString(3, c.getDireccion());
        ps.setInt(4, c.getLocalidad().getCodigo());
        ps.setString(5, c.getRuc());
        ps.setString(6, c.getTelefono());
        ps.setDate(7, c.getFechaingreso());
        ps.setDate(8, c.getFechanacimiento());
        ps.setInt(9, c.getEstado());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> CARGA DE CLIENTES " + ex.getLocalizedMessage());
        }
        stEspejo.close();
        ps.close();
        conne.close();
        return c;
    }

    public cliente buscarId(int id,Integer nsuc) throws SQLException {
        cliente cl = new cliente();
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }

        Connection conne = stEspejo.getConnection();
        try {
            String sql = "SELECT clientes.codigo,clientes.nombre,clientes.direccion,clientes.telefono,clientes.giraduria,"
                    + "giradurias.nombre as nombregiraduria,clientes.ruc,clientes.cedula "
                    + " FROM clientes "
                    + " LEFT JOIN giradurias "
                    + " ON giradurias.codigo=clientes.giraduria "
                    + " WHERE clientes.estado=1 and clientes.codigo=? "
                    + " ORDER BY clientes.codigo ";
            try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    giraduria gi = new giraduria();
                    cl.setGiraduria(gi);
                    cl.setCodigo(rs.getInt("codigo"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setRuc(rs.getString("ruc"));
                    cl.setCedula(rs.getString("cedula"));
                    cl.setDireccion(rs.getString("direccion"));
                    cl.setTelefono(rs.getString("telefono"));
                    cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    cl.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        System.out.println("--> MENSAJE DESDE EL SERVIDOR DESTINO");
        stEspejo.close();
        return cl;
    }
}

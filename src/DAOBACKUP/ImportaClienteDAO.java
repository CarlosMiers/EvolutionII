/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOBACKUP;

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
public class ImportaClienteDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public ArrayList<cliente> todosOrigen(Integer nsuc) throws SQLException {
        ArrayList<cliente> lista = new ArrayList<cliente>();
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        String sql = "SELECT CLIENTES.codigo,CLIENTES.nombre,CLIENTES.ruc,CLIENTES.fechanacimiento,CLIENTES.estadocivil,CLIENTES.direccion,"
                + "CLIENTES.telefono,CLIENTES.fax,CLIENTES.celular,"
                + "CLIENTES.fechaingreso,"
                + "CLIENTES.categoria,CLIENTES.localidad,"
                + "CLIENTES.estado,"
                + "clientes.giraduria,clientes.cedula "
                + " FROM CLIENTES "
                + " WHERE clientes.estado=1 "
                + " ORDER BY clientes.codigo ";
        try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente cl = new cliente();
                localidad lo = new localidad();
                giraduria gi = new giraduria();
                cl.setLocalidad(lo);
                cl.setGiraduria(gi);

                cl.setCodigo(rs.getInt("codigo"));
                cl.setNombre(rs.getString("nombre"));
                cl.setRuc(rs.getString("ruc"));
                cl.setCedula(rs.getString("ruc"));
                cl.setFechanacimiento(rs.getDate("fechanacimiento"));
                cl.setEstadocivil(rs.getString("estadocivil"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setFax(rs.getString("fax"));
                cl.setCelular(rs.getString("celular"));
                cl.getGiraduria().setCodigo(rs.getInt("giraduria"));
                cl.getLocalidad().setCodigo(rs.getInt("localidad"));
                cl.setCategoria(rs.getInt("categoria"));
                cl.setEstado(rs.getInt("estado"));
                lista.add(cl);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        stEspejo.close();
        conne.close();
        return lista;
    }
}

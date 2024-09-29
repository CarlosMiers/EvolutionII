/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOBACKUP;

import Conexion.ConexionEspejo;
import Modelo.localidad;
import Modelo.proveedor;
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
public class ImportaProveedorDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public ArrayList<proveedor> todos(Integer nsuc) throws SQLException {
        ArrayList proveedores = new ArrayList();
        conEsp = new ConexionEspejo();
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        String sql = "SELECT codigo,nombre,localidad,direccion,ruc,telefono,timbrado,vencimiento,estado,localidad "
                + "FROM proveedores "
                + " ORDER BY codigo ";
        try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                localidad loca = new localidad();
                proveedor proveedor = new proveedor();
                proveedor.setLocalidad(loca);
                proveedor.setCodigo(rs.getInt("codigo"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setRuc(rs.getString("ruc"));
                proveedor.setVencimiento(rs.getDate("vencimiento"));
                proveedor.setTimbrado(rs.getString("timbrado"));
                proveedor.getLocalidad().setCodigo(rs.getInt("localidad"));
                proveedor.setEstado(rs.getInt("estado"));
                proveedores.add(proveedor);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        stEspejo.close();
        conne.close();
        return proveedores;
    }
}

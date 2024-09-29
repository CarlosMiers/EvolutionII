/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import Conexion.ConexionEspejo;
import DAO.configuracionDAO;
import Modelo.configuracion;
import Modelo.stock;
import Modelo.sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author Pc_Server
 */
public class stockVpnDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    configuracion configEmpresa = new configuracion();
    String ip2 = "45.180.183.194";
    String ip3 = "45.180.183.194";

    public ArrayList<stock> BuscarStockProductoVpn(String id, Integer nsuc) throws SQLException {
        ArrayList<stock> lista = new ArrayList<stock>();
        configuracionDAO configDAO = new configuracionDAO();
        configEmpresa = configDAO.consultar();
        int nsucursal = configEmpresa.getSucursaldefecto().getCodigo();
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();

        String sql = "SELECT stock.producto,sucursales.nombre as nombresucursal,"
                + "stock.stock "
                + " FROM stock "
                + " LEFT JOIN sucursales "
                + " ON sucursales.codigo=stock.sucursal "
                + " WHERE stock.producto='" + id + "'"
                + " AND stock.sucursal<>" + nsucursal
                + " ORDER BY stock.sucursal ";
        try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sucursal suc = new sucursal();
                stock stk = new stock();
                stk.setSucursal(suc);
                stk.setStock(rs.getDouble("stock"));
                stk.getSucursal().setNombre(rs.getString("nombresucursal"));
                lista.add(stk);
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

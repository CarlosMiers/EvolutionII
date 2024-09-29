/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import Conexion.Conexion;
import Conexion.ConexionEspejo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author notebook
 */
public class AnularVentasDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;

    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public boolean borrarAnulado(String id, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }

        Connection conne = stEspejo.getConnection();
        PreparedStatement ps = null;
        ps = stEspejo.getConnection().prepareStatement("DELETE FROM ventas_anuladas WHERE creferencia=?");
        ps.setString(1, id);
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        conne.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

}

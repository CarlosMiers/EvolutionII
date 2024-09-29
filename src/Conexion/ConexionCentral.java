/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

/**
 *
 * @author Pc_Server
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionCentral {

    public static Connection conectarEspejo(String direccionIp) {
        Connection conne = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conne = DriverManager.getConnection("jdbc:mysql://" + direccionIp + "/evolution2021", "root", "1541947");
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Hubo un problema al conectar con la base ");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return conne;
    }
}

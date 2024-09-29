package Conexion;

import Clases.Config;
import com.mysql.jdbc.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConexionEspejo {

    public static Statement conectarEspejo(String direccionIp) {
        Connection conne = null;
        Statement stb = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //   Class.forName("com.mysql.cj.jdbc.Driver"); 8.0
            // 45.180.183.178

            //conne = DriverManager.getConnection("jdbc:mysql://192.168.0.15/ferremax", "root", "1541947");
            //  conne = DriverManager.getConnection("jdbc:mysql://45.180.183.178/evolution2021", "root", "1541947"); // FERREMAX
            //conne = DriverManager.getConnection("jdbc:mysql://181.126.96.21/evolution2021", "root", "1541947"); // ROSMAR
//        conne = DriverManager.getConnection("jdbc:mysql://45.180.183.194/evolution2021", "root", "1541947");
            conne = DriverManager.getConnection("jdbc:mysql://" + direccionIp.toString() + "/evolution2021", "root", "1541947");
            if (conne != null) {
                //JOptionPane.showMessageDialog(null, "Hemos conectado " + url + " ... Ok");
                stb = conne.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Hubo un problema al conectar con la base ");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return stb;
    }
}

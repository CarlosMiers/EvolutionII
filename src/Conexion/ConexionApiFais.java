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

public class ConexionApiFais{

    public static Statement conectarEspejo() {
        Connection conne = null;
        Statement stb = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //   Class.forName("com.mysql.cj.jdbc.Driver"); 8.0
            // 

         conne = DriverManager.getConnection("jdbc:mysql://75.102.22.194/pilrunxq_app", "pilrunxq_faisroot", "FpSXt$&Fy7$");
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



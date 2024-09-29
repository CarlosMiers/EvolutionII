package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @web http://www.jc-mouse.net/
 * @author Mouse
 */
public class database {

    /**
     * Constructor de clase
     */
    /**
     * Metodo que obtiene un registro aleatoriamente
     *
     * @return String Nombre de universitario
     */
    public static void main(String args[]) {
        Connection conn = null;
        String url = "jdbc:mysql://181.120.121.204/evolution";
        String bd = "evolution";
        String login = "root";
        String password = "1541947";
        try {
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
            //obtenemos la conexion
            conn = DriverManager.getConnection(url, "root", "1541947");
            if (conn != null) {

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        String q = "SHOW PROCESSLIST";
        try {
            PreparedStatement pstm = conn.prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                System.out.println(res.getString("id"));
                System.out.println(res.getString("command"));
                System.out.println(res.getString("time"));
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

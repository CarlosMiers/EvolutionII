package Conexion;

import Clases.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexion {


    public static Statement conectar() {
        Connection conn = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //   Class.forName("com.mysql.cj.jdbc.Driver"); 8.0
//conn = DriverManager.getConnection("jdbc:mysql://181.122.62.203/evolution2021", "root", "1541947"); //RUFINO SAN LORENZO
//conn = DriverManager.getConnection("jdbc:mysql://190.104.134.134/evolution2021", "root", "1541947"); //UNIVERSIDAD AUTONOMA
//conn = DriverManager.getConnection("jdbc:mysql://181.126.96.21/evolution2021", "root", "1541947"); //SUCURSAL ROSMAR     
//conn = DriverManager.getConnection("jdbc:mysql://181.126.96.126/evolution2021", "root", "1541947"); //MILLARD           
//conn = DriverManager.getConnection("jdbc:mysql://181.120.28.59/evolution2021", "root", "1541947"); //RUFINO MARIANO           
//conn = DriverManager.getConnection("jdbc:mysql://181.126.81.250/evolution2021", "root", "1541947"); //LACASITA
//conn = DriverManager.getConnection("jdbc:mysql://181.122.145.124/evolution2021", "root", "1541947"); //ENVASES Y MAS
//conn = DriverManager.getConnection("jdbc:mysql://181.122.120.64/evolution2021", "root", "1541947"); //SALLEMMA
//conn = DriverManager.getConnection("jdbc:mysql://200.108.134.46/evolution", "root", "1541947"); //AFUMA
//conn = DriverManager.getConnection("jdbc:mysql://181.120.74.131/evolution2020", "root", "1541947"); //DIORO
//conn = DriverManager.getConnection("jdbc:mysql://181.94.222.150/evolution2021", "root", "1541947");//KM15181.94.222.150
//conn = DriverManager.getConnection("jdbc:mysql://45.180.183.194/evolution2021", "root", "1541947"); //FERREMAX CENTRAL
//conn = DriverManager.getConnection("jdbc:mysql://45.180.183.178/evolution2021", "root", "1541947"); //SUCURSAL 1 FERREMAX   
//conn = DriverManager.getConnection("jdbc:mysql://45.180.183.152/evolution2021", "root", "1541947"); //SUCURSAL 2 FERREMAX   
//conn = DriverManager.getConnection("jdbc:mysql://" + Config.cIpServer.trim() + "/Evolution", "root", "1541947");
//conn = DriverManager.getConnection("jdbc:mysql://" + Config.cIpServer.trim()+":3307" + "/Evolution2021", "root", "1541947");
//conn = DriverManager.getConnection("jdbc:mysql://" + Config.cIpServer.trim()+ "/Evolution2021", "root", "1541947");
conn = DriverManager.getConnection( "jdbc:mysql://Localhost/rmachado", "root", "1541947");
//      conn = DriverManager.getConnection("jdbc:mysql://Localhost/colegio","root", "1541947");
//    conn = DriverManager.getConnection( "jdbc:mysql://Localhost/evolution2021", "root", "1541947");
//             conn = DriverManager.getConnection( "jdbc:mysql://Localhost/evolution2021", "root", "1541947");

//               conn = DriverManager.getConnection( "jdbc:mysql://Localhost/financiera", "root", "1541947");
//            conn = DriverManager.getConnection( "jdbc:mysql://Localhost/rmachado", "root", "1541947");
//     conn = DriverManager.getConnection("jdbc:mysql://181.78.27.142:3307/evolution2021", "root", "1541947"); 


            if (conn != null) {
                //JOptionPane.showMessageDialog(null, "Hemos conectado " + url + " ... Ok");
                st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Hubo un problema al conectar con la base ");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return st;
    }

}

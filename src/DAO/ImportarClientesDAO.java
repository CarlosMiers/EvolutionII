package DAO;

import Clases.Config;
import Conexion.Conexion;
import Conexion.ConexionEspejo;
import Modelo.config_contable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ImportarClientesDAO {

    Conexion con = null;
    Statement st = null;

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;

    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public boolean generarEntrada(java.util.Date desde, java.util.Date hasta, Integer nsuc) throws SQLException {
        System.out.println("Generando Consulta Clientes");
        Date dFecha;
        boolean valor = true;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);

        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();

            conEsp = new ConexionEspejo();
            if (nsuc == 2) {
                stEspejo = conEsp.conectarEspejo(ip2);
            } else if (nsuc == 3) {
                stEspejo = conEsp.conectarEspejo(ip3);
            }

            Connection conne = stEspejo.getConnection();

            // Verificar que existen ventas
            String sqlClientes = "SELECT codigo,nombre,direccion,localidad,ruc,telefono,fechaingreso,fechanacimiento,estado "
                    + " FROM clientes "
                    + " WHERE  fechaingreso BETWEEN'" + fDesde + "' AND '" + fHasta + "'"
                    + "ORDER BY clientes.codigo ";

            //ORIGEN SERVIDOR REMOTO
            PreparedStatement psorigen = conne.prepareStatement(sqlClientes);
            ResultSet clientes = psorigen.executeQuery(sqlClientes);

            //DESTINO SERVIDOR LOCAL
            PreparedStatement psdestino = conn.prepareStatement("");
            // configuracion
            // Generar asientos de compras
            while (clientes.next()) {
                int codigo = clientes.getInt("codigo");
                String nombre = clientes.getString("nombre");
                int localidad = clientes.getInt("localidad");
                String ruc = clientes.getString("ruc");
                String direccion = clientes.getString("direccion");
                String telefono = clientes.getString("telefono");
                int estado = clientes.getInt("estado");

                System.out.println("Fecha ingreso " + clientes.getDate("fechaingreso"));
                // BORRAMOS CABECERA DESTINO

                // Agregar cabecera
                System.out.println("Fechas " + clientes.getDate("fechaingreso"));
                String sqlAgregarCliente = "INSERT INTO clientes (codigo,nombre,direccion,"
                        + "localidad,ruc,telefono,fechaingreso,fechanacimiento,estado) "
                        + "VALUES (" + codigo + ",'" + nombre + "','" + direccion + "'," + localidad + ",'"
                        + ruc + "','" + telefono + "','" + clientes.getDate("fechaingreso")
                        + "','" + clientes.getDate("fechanacimiento") + "'," + estado + ")";

                try {
                    st.executeUpdate(sqlAgregarCliente);
                } catch (Exception e) {
                    System.out.println("SEGUIMOS AL SIGUIENTE " + e.getMessage());
                }

            }
            st.close();
            stEspejo.close();
            System.out.println("Generando Clientes - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }
}

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

public class ImportarCuentasDAO {

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
            String sqlClientes = "SELECT iddocumento,creferencia,documento,fecha,vencimiento,cliente,"
                    + "sucursal,autorizacion,moneda,comprobante,vendedor,caja,importe,giraduria,"
                    + "numerocuota,cuota,saldo,idedificio "
                    + "FROM cuenta_clientes "
                    + " WHERE  fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "'"
                    + "ORDER BY fecha ";

            //ORIGEN SERVIDOR REMOTO
            PreparedStatement psorigen = conne.prepareStatement(sqlClientes);
            ResultSet cuentas = psorigen.executeQuery(sqlClientes);

            //DESTINO SERVIDOR LOCAL
            PreparedStatement psdestino = conn.prepareStatement("");
            // configuracion
            // Generar asientos de compras
            while (cuentas.next()) {

                // BORRAMOS CABECERA DESTINO
                String sqlBorrarDetalle = "DELETE FROM cuenta_clientes WHERE iddocumento='" + cuentas.getString("iddocumento") + "'";
                psdestino = conn.prepareStatement(sqlBorrarDetalle);
                psdestino.executeUpdate();

                String sqlAgregarCliente = "INSERT INTO cuenta_clientes (iddocumento,"
                        + "creferencia,documento,"
                        + "fecha,vencimiento,cliente,"
                        + "sucursal,"
                        + "moneda,comprobante,vendedor,"
                        + "caja,importe,giraduria,"
                        + "numerocuota,cuota,"
                        + "saldo,idedificio) "
                        + "VALUES ('" + cuentas.getString("iddocumento") + "','"
                        + cuentas.getString("creferencia") + "',"
                        + cuentas.getBigDecimal("documento") + ",'"
                        + cuentas.getDate("fecha") + "','"
                        + cuentas.getDate("vencimiento") + "',"
                        + cuentas.getInt("cliente") + ","
                        + cuentas.getInt("sucursal") + ","
                        + cuentas.getInt("moneda") + ","
                        + cuentas.getInt("comprobante") + ","
                        + cuentas.getInt("vendedor") + ","
                        + cuentas.getInt("caja") + ","
                        + cuentas.getDouble("importe") + ","
                        + cuentas.getInt("giraduria") + ","
                        + cuentas.getInt("numerocuota") + ","
                        + cuentas.getInt("cuota") + ","
                        + cuentas.getDouble("saldo") + ","
                        + cuentas.getInt("idedificio") + ")";
                System.out.println(sqlAgregarCliente);
                st.executeUpdate(sqlAgregarCliente);
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

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

public class ExportarEntradaMercaderiasDAO {

    Conexion con = null;
    Statement st = null;
    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;

    public boolean generarEntrada(java.util.Date desde, java.util.Date hasta, int nsuc) throws SQLException {
        System.out.println("Generando Consulta Transferencias");
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
            String sqlCabecera = "SELECT idtransferencia,numero,fecha,origen,destino,tipo "
                    + " FROM transferencias "
                    + " WHERE  fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "'"
                    + " AND destino = "+nsuc
                    + " ORDER BY numero ";

            PreparedStatement ps = conn.prepareStatement(sqlCabecera);
            ResultSet cabecera_transferencias = ps.executeQuery(sqlCabecera);

            PreparedStatement psdestino = conne.prepareStatement("");

            // configuracion
            // Generar asientos de compras
            while (cabecera_transferencias.next()) {
                String idtransferencia = cabecera_transferencias.getString("idtransferencia");
                int sucursal = cabecera_transferencias.getInt("destino");
                int tipo = cabecera_transferencias.getInt("tipo");
                int numero = cabecera_transferencias.getInt("numero");

                System.out.println("Fecha Entrada " + cabecera_transferencias.getDate("fecha"));
                // BORRAMOS CABECERA DESTINO
                String sqlBorrarCabecera = "DELETE FROM ajuste_mercaderias WHERE idreferencia='" + idtransferencia + "'";
                psdestino = conne.prepareStatement(sqlBorrarCabecera);
                psdestino.executeUpdate();

                String sqlBorrarDetalle = "DELETE FROM detalle_ajuste_mercaderias WHERE dreferencia='" + idtransferencia + "'";
                psdestino = conne.prepareStatement(sqlBorrarDetalle);
                psdestino.executeUpdate();

                // Agregar cabecera
                System.out.println("Fechas " + cabecera_transferencias.getDate("fecha"));
                String sqlAgregarCabecera = "INSERT INTO ajuste_mercaderias(idreferencia,fecha,sucursal,tipo)"
                        + "VALUES ('" + idtransferencia + "','" + cabecera_transferencias.getDate("fecha")
                        + "'," + sucursal + "," + tipo + ")";
                stEspejo.executeUpdate(sqlAgregarCabecera);
            }

            String sqlDetalle = "SELECT dreferencia,producto,cantidad,costo,suc_entrada "
                    + " FROM transferencias "
                    + " LEFT JOIN detalle_transferencias "
                    + " ON detalle_transferencias.dreferencia=transferencias.idtransferencia "
                    + " WHERE  fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "' "
                    +" AND transferencias.destino="+nsuc
                    + " ORDER BY numero ";

            ps = conn.prepareStatement(sqlDetalle);
            ResultSet detalle_transferencias = ps.executeQuery(sqlDetalle);
            while (detalle_transferencias.next()) {
                System.out.println("Recorriendo Detalles " + detalle_transferencias.getString("producto"));

                String dreferencia = detalle_transferencias.getString("dreferencia");
                String producto = detalle_transferencias.getString("producto");
                double cantidad = detalle_transferencias.getDouble("cantidad");
                double costo = detalle_transferencias.getDouble("costo");
                int suc_entrada = detalle_transferencias.getInt("suc_entrada");

                String sqlAgregarDetalle = "INSERT INTO detalle_ajuste_mercaderias(dreferencia,producto,cantidad,costo,suc) "
                        + "VALUES(?,?,?,?,?)";
                psdestino = conne.prepareStatement(sqlAgregarDetalle);
                psdestino.setString(1, dreferencia);
                psdestino.setString(2, producto);
                psdestino.setDouble(3, cantidad);
                psdestino.setDouble(4, costo);
                psdestino.setInt(5, suc_entrada);
                psdestino.executeUpdate();
            }
            st.close();
            stEspejo.close();
            System.out.println("Generando Transferencia de Mercaderias - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }
    
    
    
    public boolean generarEntradaId(String id, Integer nsuc) throws SQLException {
        System.out.println("Generando Consulta Transferencias "+id);
        Date dFecha;
        boolean valor = true;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

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
            String sqlCabecera = "SELECT idtransferencia,numero,fecha,origen,destino,tipo "
                    + " FROM transferencias "
                    + " WHERE  idtransferencia='" +id +"'" 
                    + " ORDER BY numero ";

            PreparedStatement ps = conn.prepareStatement(sqlCabecera);
            ResultSet cabecera_transferencias = ps.executeQuery(sqlCabecera);

            PreparedStatement psdestino = conne.prepareStatement("");

            // configuracion
            // Generar asientos de compras
            while (cabecera_transferencias.next()) {
                String idtransferencia = cabecera_transferencias.getString("idtransferencia");
                int sucursal = cabecera_transferencias.getInt("destino");
                int tipo = cabecera_transferencias.getInt("tipo");
                int numero = cabecera_transferencias.getInt("numero");

                System.out.println("Fecha Entrada " + cabecera_transferencias.getDate("fecha"));
                // BORRAMOS CABECERA DESTINO
                String sqlBorrarCabecera = "DELETE FROM ajuste_mercaderias WHERE idreferencia='" + idtransferencia + "'";
                psdestino = conne.prepareStatement(sqlBorrarCabecera);
                psdestino.executeUpdate();

                String sqlBorrarDetalle = "DELETE FROM detalle_ajuste_mercaderias WHERE dreferencia='" + idtransferencia + "'";
                psdestino = conne.prepareStatement(sqlBorrarDetalle);
                psdestino.executeUpdate();

                // Agregar cabecera
                System.out.println("Fechas " + cabecera_transferencias.getDate("fecha"));
                String sqlAgregarCabecera = "INSERT INTO ajuste_mercaderias(idreferencia,fecha,sucursal,tipo)"
                        + "VALUES ('" + idtransferencia + "','" + cabecera_transferencias.getDate("fecha")
                        + "'," + sucursal + "," + tipo + ")";
                stEspejo.executeUpdate(sqlAgregarCabecera);
            }

            String sqlDetalle = "SELECT dreferencia,producto,cantidad,costo,suc_entrada "
                    + " FROM transferencias "
                    + " LEFT JOIN detalle_transferencias "
                    + " ON detalle_transferencias.dreferencia=transferencias.idtransferencia "
                    + " WHERE  dreferencia='" +id+ "'"
                    + " ORDER BY numero ";

            ps = conn.prepareStatement(sqlDetalle);
            ResultSet detalle_transferencias = ps.executeQuery(sqlDetalle);
            while (detalle_transferencias.next()) {
                System.out.println("Recorriendo Detalles " + detalle_transferencias.getString("producto"));

                String dreferencia = detalle_transferencias.getString("dreferencia");
                String producto = detalle_transferencias.getString("producto");
                double cantidad = detalle_transferencias.getDouble("cantidad");
                double costo = detalle_transferencias.getDouble("costo");
                int suc_entrada = detalle_transferencias.getInt("suc_entrada");

                String sqlAgregarDetalle = "INSERT INTO detalle_ajuste_mercaderias(dreferencia,producto,cantidad,costo,suc) "
                        + "VALUES(?,?,?,?,?)";
                psdestino = conne.prepareStatement(sqlAgregarDetalle);
                psdestino.setString(1, dreferencia);
                psdestino.setString(2, producto);
                psdestino.setDouble(3, cantidad);
                psdestino.setDouble(4, costo);
                psdestino.setInt(5, suc_entrada);
                psdestino.executeUpdate();
            }
            st.close();
            stEspejo.close();
            System.out.println("Generando Transferencia de Mercaderias - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }    
    
}

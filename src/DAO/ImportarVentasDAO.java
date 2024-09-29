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

public class ImportarVentasDAO {

    Conexion con = null;
    Statement st = null;

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;

    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";
    
    public boolean generarEntrada(java.util.Date desde, java.util.Date hasta, Integer nsuc) throws SQLException {
        System.out.println("Generando Consulta Ventas");
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
            String sqlCabecera = "SELECT creferencia,cabecera_ventas.factura,cabecera_ventas.fecha,"
                    + "cabecera_ventas.factura,cabecera_ventas.vencimiento,cabecera_ventas.formatofactura,"
                    + "cabecera_ventas.cliente,cabecera_ventas.sucursal,cabecera_ventas.financiado,"
                    + "cabecera_ventas.moneda,cabecera_ventas.giraduria,comprobante,cotizacion,"
                    + "cabecera_ventas.vendedor,caja,supago,sucambio,totalbruto,"
                    + "totaldescuento,exentas,gravadas10,gravadas5,totalneto,cuotas,"
                    + "cabecera_ventas.turno,idusuario,cabecera_ventas.nrotimbrado,"
                    + "cabecera_ventas.vencimientotimbrado,observacion "
                    + "FROM cabecera_ventas "
                    + " WHERE  fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "'"
                    + "ORDER BY cabecera_ventas.factura ";

            //ORIGEN SERVIDOR REMOTO
            PreparedStatement psorigen = conne.prepareStatement(sqlCabecera);
            ResultSet cabecera_ventas = psorigen.executeQuery(sqlCabecera);

            //DESTINO SERVIDOR LOCAL
            PreparedStatement psdestino = conn.prepareStatement("");
            // configuracion
            // Generar asientos de compras
            while (cabecera_ventas.next()) {
                String creferencia = cabecera_ventas.getString("creferencia");
                int sucursal = cabecera_ventas.getInt("sucursal");
                int comprobante = cabecera_ventas.getInt("comprobante");
                String formatofactura = cabecera_ventas.getString("formatofactura");
                double factura = cabecera_ventas.getDouble("factura");
                int cliente = cabecera_ventas.getInt("cliente");
                int moneda = cabecera_ventas.getInt("moneda");
                double cotizacion = cabecera_ventas.getDouble("cotizacion");
                int vendedor = cabecera_ventas.getInt("vendedor");
                int caja = cabecera_ventas.getInt("caja");
                double exentas = cabecera_ventas.getDouble("exentas");
                double gravadas10 = cabecera_ventas.getDouble("gravadas10");
                double gravadas5 = cabecera_ventas.getDouble("gravadas5");
                double totalneto = cabecera_ventas.getDouble("totalneto");
                int cuotas = cabecera_ventas.getInt("cuotas");
                double financiado = cabecera_ventas.getDouble("financiado");
                double supago = cabecera_ventas.getDouble("supago");
                int idusuario = cabecera_ventas.getInt("idusuario");
                double sucambio = cabecera_ventas.getDouble("sucambio");
                int nrotimbrado = cabecera_ventas.getInt("nrotimbrado");
                int turno = cabecera_ventas.getInt("turno");
                String observacion = cabecera_ventas.getString("observacion");

                System.out.println("Fecha Venta " + cabecera_ventas.getDate("fecha"));
                // BORRAMOS CABECERA DESTINO

                String sqlBorrarDetalle = "DELETE FROM detalle_ventas WHERE dreferencia='" + creferencia + "'";
                psdestino = conn.prepareStatement(sqlBorrarDetalle);
                psdestino.executeUpdate();

                String sqlBorrarCabecera = "DELETE FROM cabecera_ventas WHERE creferencia='" + creferencia + "'";
                psdestino = conn.prepareStatement(sqlBorrarCabecera);
                psdestino.executeUpdate();

                String sqlBorrarAnuladas = "DELETE FROM ventas_anuladas WHERE creferencia='" + creferencia + "'";
                psdestino = conn.prepareStatement(sqlBorrarAnuladas);
                psdestino.executeUpdate();

                // Agregar cabecera
                System.out.println("Fechas " + cabecera_ventas.getDate("fecha"));
                String sqlAgregarCabecera = "INSERT INTO cabecera_ventas (creferencia,fecha,factura,"
                        + "vencimiento,cliente,sucursal,moneda,"
                        + "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,"
                        + "gravadas5,totalneto,cuotas,financiado,"
                        + "supago,idusuario,vencimientotimbrado,sucambio,"
                        + "nrotimbrado,turno,formatofactura,observacion) "
                        + "VALUES ('" + creferencia + "','" + cabecera_ventas.getDate("fecha")
                        + "'," + factura + ",'" + cabecera_ventas.getDate("vencimiento") + "'," + cliente + ","
                        + sucursal + "," + moneda + "," + comprobante + "," + cotizacion + "," + vendedor + "," + caja
                        + "," + exentas + "," + gravadas10 + "," + gravadas5 + "," + totalneto + "," + cuotas
                        + "," + financiado + "," + supago + "," + idusuario + ",'" + cabecera_ventas.getDate("vencimientotimbrado")
                        + "'," + sucambio + "," + nrotimbrado + "," + turno + ",'" + formatofactura+"','"+ observacion + "')";

                st.executeUpdate(sqlAgregarCabecera);
            }

            String sqlDetalle = "SELECT dreferencia,codprod,cantidad,prcosto,precio,monto,impiva,porcentaje,suc "
                    + " FROM cabecera_ventas "
                    + " LEFT JOIN detalle_ventas "
                    + " ON detalle_ventas.dreferencia=cabecera_ventas.creferencia "
                    + " WHERE  fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "'"
                    + " ORDER BY factura ";

            psorigen = conne.prepareStatement(sqlDetalle);
            ResultSet detalle_ventas = psorigen.executeQuery(sqlDetalle);
            while (detalle_ventas.next()) {
                System.out.println("Recorriendo Detalles " + detalle_ventas.getString("codprod"));

                String dreferencia = detalle_ventas.getString("dreferencia");
                String codprod = detalle_ventas.getString("codprod");
                double cantidad = detalle_ventas.getDouble("cantidad");
                double prcosto = detalle_ventas.getDouble("prcosto");
                int suc = detalle_ventas.getInt("suc");
                double precio = detalle_ventas.getDouble("precio");
                double monto = detalle_ventas.getDouble("monto");
                double impiva = detalle_ventas.getDouble("impiva");
                double porcentaje = detalle_ventas.getDouble("porcentaje");

                String sqlAgregarDetalle = "INSERT INTO detalle_ventas(dreferencia,codprod,cantidad,"
                        + "prcosto,precio,monto,impiva,porcentaje,suc) "
                        + "VALUES(?,?,?,?,?,?,?,?,?)";
                psdestino = conn.prepareStatement(sqlAgregarDetalle);
                psdestino.setString(1, dreferencia);
                psdestino.setString(2, codprod);
                psdestino.setDouble(3, cantidad);
                psdestino.setDouble(4, prcosto);
                psdestino.setDouble(5, precio);
                psdestino.setDouble(6, monto);
                psdestino.setDouble(7, impiva);
                psdestino.setDouble(8, porcentaje);
                psdestino.setInt(9, suc);
                psdestino.executeUpdate();
            }
            st.close();
            stEspejo.close();
            System.out.println("Generando Ventas  de Mercaderias - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }
}

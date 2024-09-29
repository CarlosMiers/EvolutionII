package DAO;

import Clases.Config;
import Conexion.Conexion;
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

public class GenerarAsientoVentasCostosDAO {

    Conexion con = null;
    Statement st = null;

    public boolean generarVentas(java.util.Date desde, java.util.Date hasta) {
        boolean valor = true;
        int nIva5 = 0, nIva10 = 0, nProveedor = 0, nTotales = 0, nItems = 0, nContador = 0, nAsiento = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de ventas - Inicio");

        String cOrigen = "VENTAS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Borrar asientos cabecera
            String sql = "DELETE FROM cabecera_asientos WHERE fecha>=? AND fecha<=? AND grabado=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fDesde);
            ps.setString(2, fHasta);
            ps.setString(3, cOrigen);
            ps.executeUpdate();
            // Recuperar datos de varias tablas
            // configuracion
            sql = "SELECT * FROM configuracion";
            ps = conn.prepareStatement(sql);
            ResultSet config = ps.executeQuery(sql);
            String CONFIG_comprobantedefecto = "";
            while (config.next()) {
                CONFIG_comprobantedefecto = config.getString("comprobantedefecto");
                System.out.println("config -> " + CONFIG_comprobantedefecto);
            }
            // configuracion
            sql = "SELECT * FROM config_contable";
            ps = conn.prepareStatement(sql);
            ResultSet config_contable = ps.executeQuery(sql);
            String CONFIG_CONTABLE_ventacontado = "";
            String CONFIG_CONTABLE_ventacredito = "";
            String CONFIG_CONTABLE_ivaventa10 = "";
            String CONFIG_CONTABLE_ivaventa5 = "";
            String CONFIG_CONTABLE_ventaexenta = "";
            while (config_contable.next()) {
                CONFIG_CONTABLE_ventacontado = config_contable.getString("ventacontado");
                CONFIG_CONTABLE_ventacredito = config_contable.getString("ventacredito");
                CONFIG_CONTABLE_ivaventa10 = config_contable.getString("ivaventa10");
                CONFIG_CONTABLE_ivaventa5 = config_contable.getString("ivaventa5");
                CONFIG_CONTABLE_ventaexenta = config_contable.getString("ventaexenta");
                System.out.println("config_contable -> "
                        + CONFIG_CONTABLE_ventacontado + " | "
                        + CONFIG_CONTABLE_ventacredito + " | "
                        + CONFIG_CONTABLE_ivaventa10 + " | "
                        + CONFIG_CONTABLE_ivaventa5 + " | "
                        + CONFIG_CONTABLE_ventaexenta
                );
            }
            // Asientos temporales
            ArrayList<TableAsientos> tablaAsientos = new ArrayList();
            // Tmpdebe
            sql = "SELECT factura,comprobante,SUM(ROUND(monto*cotizacion,0)) as total "
                    + "FROM vista_detalle_ventas "
                    + "WHERE fecha>='" + fDesde + "' AND fecha<='" + fHasta + "' AND comprobante<>13 "
                    + "GROUP BY comprobante "
                    + "ORDER BY comprobante";
            ps = conn.prepareStatement(sql);
            System.out.println("sql -> " + sql);
            ResultSet tmpdebe = ps.executeQuery(sql);
            while (tmpdebe.next()) {
                System.out.println("tmpdebe -> " + tmpdebe.getString("factura") + " | "
                        + tmpdebe.getString("comprobante") + " | " + tmpdebe.getString("total"));
                String CONFIG_CONTABLE = "";
                if (tmpdebe.getString("comprobante").equals(CONFIG_comprobantedefecto)) {
                    CONFIG_CONTABLE = CONFIG_CONTABLE_ventacontado;
                } else {
                    CONFIG_CONTABLE = CONFIG_CONTABLE_ventacredito;
                }
                // Agregar al ArrayList
                TableAsientos tableAsiento = new TableAsientos();
                tableAsiento.asi_codigo = CONFIG_CONTABLE;
                tableAsiento.factura = tmpdebe.getLong("factura");
                tableAsiento.impdebe = tmpdebe.getDouble("total");
                tablaAsientos.add(tableAsiento);
            }

            // Tmpdebe
            /*    sql = "SELECT factura,comprobante,SUM(ROUND(monto*cotizacion,0)) as total "
                    + "FROM vista_detalle_ventas "
                    + "WHERE fecha>='" + fDesde + "' AND fecha<='" + fHasta + "' AND monto>0 AND comprobante=1 "
                    + "GROUP BY comprobante "
                    + "ORDER BY comprobante";*/
            // Tmphaber
            sql = "SELECT factura,porcentaje,productos.rubro,rubros.idcta, "
                    + "SUM(ROUND(monto*cotizacion,0)) as total "
                    + "FROM vista_detalle_ventas "
                    + "INNER JOIN productos "
                    + "ON vista_detalle_ventas.codprod=productos.codigo "
                    + "INNER JOIN rubros "
                    + "ON productos.rubro=rubros.codigo "
                    + "WHERE fecha>='" + fDesde + "' AND fecha<='" + fHasta + "' AND comprobante<>13 "
                    + " GROUP BY rubros.idcta,porcentaje "
                    + "ORDER BY porcentaje DESC";
            ps = conn.prepareStatement(sql);
            System.out.println("sql ventas -> " + sql);

            ResultSet tmphaber = ps.executeQuery(sql);
            while (tmphaber.next()) {
                System.out.println("tmphaber -> "
                        + tmphaber.getLong("factura") + " | "
                        + tmphaber.getInt("porcentaje") + " | "
                        + tmphaber.getString("rubro") + " | "
                        + tmphaber.getString("idcta") + " | "
                        + tmphaber.getDouble("total"));
                // Agregar al ArrayList
                TableAsientos tableAsiento = new TableAsientos();
                int porcentaje = tmphaber.getInt("porcentaje");
                double total = tmphaber.getDouble("total");
                String idcta = tmphaber.getString("idcta");
                long factura = tmphaber.getLong("factura");
                switch (porcentaje) {
                    case 10:
                        tableAsiento.asi_codigo = idcta;
                        tableAsiento.imphaber = total - Math.round(total / 11);
                        tableAsiento.factura = factura;
                        tablaAsientos.add(tableAsiento);
                        tableAsiento = new TableAsientos();
                        tableAsiento.asi_codigo = CONFIG_CONTABLE_ivaventa10;
                        tableAsiento.imphaber = Math.round(total / 11);
                        tableAsiento.factura = factura;
                        tablaAsientos.add(tableAsiento);
                        break;
                    case 5:
                        tableAsiento.asi_codigo = idcta;
                        tableAsiento.imphaber = total - Math.round(total / 21);
                        tableAsiento.factura = factura;
                        tablaAsientos.add(tableAsiento);
                        tableAsiento = new TableAsientos();
                        tableAsiento.asi_codigo = CONFIG_CONTABLE_ivaventa5;
                        tableAsiento.imphaber = Math.round(total / 21);
                        tableAsiento.factura = factura;
                        tablaAsientos.add(tableAsiento);
                        break;
                    case 0:
                        tableAsiento.asi_codigo = CONFIG_CONTABLE_ventaexenta;
                        tableAsiento.imphaber = total;
                        tableAsiento.factura = factura;
                        tablaAsientos.add(tableAsiento);
                        break;
                }
            }
            double totalneto = 0;
            for (TableAsientos tablaAsiento : tablaAsientos) {
                totalneto += tablaAsiento.impdebe;
                System.out.println("tablaAsientos -> "
                        + tablaAsiento.asi_codigo + " | "
                        + tablaAsiento.impdebe + " | "
                        + tablaAsiento.imphaber + " | "
                        + tablaAsiento.factura + " | "
                        + tablaAsiento.item);
            }
            // Graba cabecera de asiento
            long asi_asient = 0;
            sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                    + "VALUES (1,'" + fHasta + "'," + totalneto + "," + totalneto + ",0,'" + cOrigen + "')";
            System.out.println("sql -> " + sql);
            st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet keyset = st.getGeneratedKeys();
            if (keyset.next()) {
                asi_asient = keyset.getInt(1);
            }
            System.out.println("asi_asient=" + asi_asient);
            // Graba detalle de asiento
            int item = 0;
            String observacion = "ASIENTO DE VENTAS";
            for (TableAsientos tablaAsiento : tablaAsientos) {
                item++;
                tablaAsiento.item = item;
                System.out.println("tablaAsientos -> "
                        + tablaAsiento.asi_codigo + " | "
                        + tablaAsiento.impdebe + " | "
                        + tablaAsiento.imphaber + " | "
                        + tablaAsiento.factura + " | "
                        + tablaAsiento.item);
                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,1,1,0,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setLong(1, asi_asient);
                ps.setLong(2, tablaAsiento.factura);
                ps.setString(3, tablaAsiento.asi_codigo);
                ps.setString(4, observacion);
                ps.setDouble(5, tablaAsiento.impdebe);
                ps.setDouble(6, tablaAsiento.imphaber);
                ps.setLong(7, item);
                ps.executeUpdate();
            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de ventas - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return valor;
    }

    public boolean generarDevolucionClientes(java.util.Date desde, java.util.Date hasta) {
        boolean valor = true;
        int nIva5 = 0, nIva10 = 0, nProveedor = 0, nTotales = 0, nItems = 0, nContador = 0, nAsiento = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de ventas - Inicio");

        String cOrigen = "VENTAS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Borrar asientos cabecera
            String sql = "";
            PreparedStatement ps = conn.prepareStatement(sql);
            // Recuperar datos de varias tablas
            // configuracion
            sql = "SELECT * FROM configuracion";
            ps = conn.prepareStatement(sql);
            ResultSet config = ps.executeQuery(sql);
            String CONFIG_comprobantedefecto = "";
            while (config.next()) {
                CONFIG_comprobantedefecto = config.getString("comprobantedefecto");
                System.out.println("config -> " + CONFIG_comprobantedefecto);
            }
            // configuracion
            sql = "SELECT * FROM config_contable";
            ps = conn.prepareStatement(sql);
            ResultSet config_contable = ps.executeQuery(sql);
            String CONFIG_CONTABLE_ventacontado = "";
            String CONFIG_CONTABLE_ventacredito = "";
            String CONFIG_CONTABLE_ivaventa10 = "";
            String CONFIG_CONTABLE_ivaventa5 = "";
            String CONFIG_CONTABLE_ventaexenta = "";
            String CONFIG_CONTABLE_devolucion = "";
            while (config_contable.next()) {
                CONFIG_CONTABLE_ventacontado = config_contable.getString("ventacontado");
                CONFIG_CONTABLE_ventacredito = config_contable.getString("ventacredito");
                CONFIG_CONTABLE_ivaventa10 = config_contable.getString("ivaventa10");
                CONFIG_CONTABLE_ivaventa5 = config_contable.getString("ivaventa5");
                CONFIG_CONTABLE_ventaexenta = config_contable.getString("ventaexenta");
                CONFIG_CONTABLE_devolucion = config_contable.getString("notacredito");
                System.out.println("config_contable -> "
                        + CONFIG_CONTABLE_ventacontado + " | "
                        + CONFIG_CONTABLE_ventacredito + " | "
                        + CONFIG_CONTABLE_ivaventa10 + " | "
                        + CONFIG_CONTABLE_ivaventa5 + " | "
                        + CONFIG_CONTABLE_ventaexenta
                );
            }
            // Asientos temporales
            ArrayList<TableAsientos> tablaAsientos = new ArrayList();

            // TmpDEBE
            sql = "SELECT factura,porcentaje,productos.rubro,rubros.idcta, "
                    + "SUM(ROUND(monto*cotizacion,0))*-1 as total "
                    + "FROM vista_detalle_ventas "
                    + "INNER JOIN productos "
                    + "ON vista_detalle_ventas.codprod=productos.codigo "
                    + "INNER JOIN rubros "
                    + "ON productos.rubro=rubros.codigo "
                    + "WHERE fecha>='" + fDesde + "' AND fecha<='" + fHasta + "'  AND comprobante = 13 "
                    + "GROUP BY rubros.idcta,porcentaje "
                    + "ORDER BY porcentaje DESC";
            ps = conn.prepareStatement(sql);
            System.out.println("sql -> " + sql);
            ResultSet tmpdebe = ps.executeQuery(sql);
            while (tmpdebe.next()) {
                System.out.println("tmphaber -> "
                        + tmpdebe.getLong("factura") + " | "
                        + tmpdebe.getInt("porcentaje") + " | "
                        + tmpdebe.getString("rubro") + " | "
                        + tmpdebe.getString("idcta") + " | "
                        + tmpdebe.getDouble("total"));
                // Agregar al ArrayList
                TableAsientos tableAsiento = new TableAsientos();
                int porcentaje = tmpdebe.getInt("porcentaje");
                double total = tmpdebe.getDouble("total");
                String idcta = CONFIG_CONTABLE_devolucion;
                long factura = tmpdebe.getLong("factura");
                switch (porcentaje) {
                    case 10:
                        tableAsiento.asi_codigo = idcta;
                        tableAsiento.impdebe = total - Math.round(total / 11);
                        tableAsiento.factura = factura;
                        tablaAsientos.add(tableAsiento);
                        tableAsiento = new TableAsientos();
                        tableAsiento.asi_codigo = CONFIG_CONTABLE_ivaventa10;
                        tableAsiento.impdebe = Math.round(total / 11);
                        tableAsiento.factura = factura;
                        tablaAsientos.add(tableAsiento);
                        break;
                    case 5:
                        tableAsiento.asi_codigo = idcta;
                        tableAsiento.impdebe = total - Math.round(total / 21);
                        tableAsiento.factura = factura;
                        tablaAsientos.add(tableAsiento);
                        tableAsiento = new TableAsientos();
                        tableAsiento.asi_codigo = CONFIG_CONTABLE_ivaventa5;
                        tableAsiento.impdebe = Math.round(total / 21);
                        tableAsiento.factura = factura;
                        tablaAsientos.add(tableAsiento);
                        break;
                    case 0:
                        tableAsiento.asi_codigo = CONFIG_CONTABLE_ventaexenta;
                        tableAsiento.impdebe = total;
                        tableAsiento.factura = factura;
                        tablaAsientos.add(tableAsiento);
                        break;
                }
            }

            // TmpHABER
            sql = "SELECT factura,cuotas,SUM(ROUND(monto*cotizacion,0))*-1 as total "
                    + "FROM vista_detalle_ventas "
                    + "WHERE fecha>='" + fDesde + "' AND fecha<='" + fHasta + "' AND comprobante = 13  "
                    + "GROUP BY cuotas "
                    + "ORDER BY cuotas ";
            ps = conn.prepareStatement(sql);
            System.out.println("sql -> " + sql);
            ResultSet tmphaber = ps.executeQuery(sql);
            while (tmphaber.next()) {
                System.out.println("tmphaber-> " + tmphaber.getString("factura") + " | "
                        + tmphaber.getString("cuotas") + " | " + tmphaber.getString("total"));
                String CONFIG_CONTABLE = "";
                if (tmphaber.getString("cuotas").equals("0")) {
                    CONFIG_CONTABLE = CONFIG_CONTABLE_ventacontado;
                } else {
                    CONFIG_CONTABLE = CONFIG_CONTABLE_ventacredito;
                }
                // Agregar al ArrayList
                TableAsientos tableAsiento = new TableAsientos();
                tableAsiento.asi_codigo = CONFIG_CONTABLE;
                tableAsiento.factura = tmphaber.getLong("factura");
                tableAsiento.imphaber = tmphaber.getDouble("total");
                tablaAsientos.add(tableAsiento);
            }

            long totalneto = 0;
            for (TableAsientos tablaAsiento : tablaAsientos) {
                totalneto += tablaAsiento.impdebe;
                System.out.println("tablaAsientos -> "
                        + tablaAsiento.asi_codigo + " | "
                        + tablaAsiento.impdebe + " | "
                        + tablaAsiento.imphaber + " | "
                        + tablaAsiento.factura + " | "
                        + tablaAsiento.item);
            }
            // Graba cabecera de asiento
            long asi_asient = 0;
            sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                    + "VALUES (1,'" + fHasta + "'," + totalneto + "," + totalneto + ",0,'" + cOrigen + "')";
            System.out.println("sql -> " + sql);
            st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet keyset = st.getGeneratedKeys();
            if (keyset.next()) {
                asi_asient = keyset.getInt(1);
            }
            System.out.println("asi_asient=" + asi_asient);
            // Graba detalle de asiento
            int item = 0;
            String observacion = "ASIENTO DE VENTAS";
            for (TableAsientos tablaAsiento : tablaAsientos) {
                item++;
                tablaAsiento.item = item;
                System.out.println("tablaAsientos -> "
                        + tablaAsiento.asi_codigo + " | "
                        + tablaAsiento.impdebe + " | "
                        + tablaAsiento.imphaber + " | "
                        + tablaAsiento.factura + " | "
                        + tablaAsiento.item);
                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,1,1,0,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setLong(1, asi_asient);
                ps.setLong(2, tablaAsiento.factura);
                ps.setString(3, tablaAsiento.asi_codigo);
                ps.setString(4, observacion);
                ps.setDouble(5, tablaAsiento.impdebe);
                ps.setDouble(6, tablaAsiento.imphaber);
                ps.setLong(7, item);
                ps.executeUpdate();
            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de ventas - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return valor;
    }

    public boolean generarCostos(java.util.Date desde, java.util.Date hasta) throws SQLException {
        System.out.println("Generando asientos de Costos");
        boolean valor = true;

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        String cOrigen = "COSTOS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Borrar asientos cabecera
            String sql = "DELETE FROM cabecera_asientos WHERE fecha>=? AND fecha<=? AND grabado=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fDesde);
            ps.setString(2, fHasta);
            ps.setString(3, cOrigen);
            ps.executeUpdate();

            sql = "SELECT factura,cabecera_ventas.fecha,rubros.idctacosto,rubros.idctamercaderia,productos.rubro,"
                    + "porcentaje,"
                    + "ROUND(SUM(IF(porcentaje=0,cantidad*prcosto,0000000000))) AS exentas,"
                    + "ROUND(SUM(IF(porcentaje=5,cantidad*prcosto,0000000000))) AS iva5,"
                    + "ROUND(SUM(IF(porcentaje=10,cantidad*prcosto,0000000000))) AS iva10"
                    + " FROM cabecera_ventas "
                    + " INNER JOIN detalle_ventas "
                    + " ON creferencia=dreferencia "
                    + " INNER JOIN productos "
                    + " ON detalle_ventas.codprod=productos.codigo "
                    + " INNER JOIN rubros "
                    + " ON productos.rubro=rubros.codigo "
                    + " WHERE fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "'"
                    + " GROUP BY rubros.idctamercaderia "
                    + " ORDER BY productos.rubro ";

            PreparedStatement ps2 = conn.prepareStatement(sql);
            ResultSet costos = ps2.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            while (costos.next()) {
                nContador++;
                double cotizacion = 1;
                String nrofactura = costos.getString("factura");
                String observacion = "ASIENTO DE COSTO";
                int asiento = 0;
                int sucursal = 1;
                String moneda = "1";

                double gravadas10 = Math.round(costos.getDouble("iva10") - (costos.getDouble("iva10") / 11));
                double gravadas5 = Math.round(costos.getDouble("iva5") - (costos.getDouble("iva5") / 21));
                double exentas = Math.round(costos.getDouble("exentas"));

                double totalneto = gravadas10 + gravadas5 + exentas;
                String ctadebe = costos.getString("idctacosto");
                String ctahaber = costos.getString("idctamercaderia");
                System.out.println("Fecha Costos " + costos.getDate("fecha"));

                // Agregar cabecera
                sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                        + "VALUES (" + sucursal + ",'" + fHasta + "'," + totalneto + "," + totalneto + ",0,'" + cOrigen + "')";
                st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet keyset = st.getGeneratedKeys();
                if (keyset.next()) {
                    asiento = keyset.getInt(1);
                }
                System.out.println("Asiento Nuevo asiento=" + asiento);
                // Table pagos
                // Table datos
                int item = 1;
                String asi_codigo = "";
                //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                asi_codigo = ctadebe;
                //GRABA EL DEBE
                double importe = totalneto;
                double impdebe = totalneto;
                double imphaber = 0;
                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setString(2, nrofactura);
                ps.setString(3, moneda);
                ps.setDouble(4, cotizacion);
                ps.setDouble(5, importe);
                ps.setString(6, asi_codigo);
                ps.setString(7, observacion);
                ps.setDouble(8, impdebe);
                ps.setDouble(9, imphaber);
                ps.setInt(10, item);
                ps.executeUpdate();

                // GRABA EL HABER
                importe = 0;
                impdebe = 0;
                imphaber = totalneto;
                importe = totalneto;
                item++;
                asi_codigo = ctahaber;
                // busqueda en proveedores

                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setString(2, nrofactura);
                ps.setString(3, moneda);
                ps.setDouble(4, cotizacion);
                ps.setDouble(5, importe);
                ps.setString(6, asi_codigo);
                ps.setString(7, observacion);
                ps.setDouble(8, impdebe);
                ps.setDouble(9, imphaber);
                ps.setInt(10, item);
                ps.executeUpdate();

            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de costos- Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;

    }

}

class TableAsientosVentas {

    public String asi_codigo;
    public long impdebe;
    public long imphaber;
    public long factura;
    public int item;
}

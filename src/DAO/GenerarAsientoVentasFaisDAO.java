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

public class GenerarAsientoVentasFaisDAO {

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
        String referencia = null;

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
            sql = "SELECT creferencia,fecha,factura,sucursal,moneda,cliente,asiento,"
                    + "cotizacion,comprobante,SUM(ROUND(monto*cotizacion,0)) as total,ctadebe "
                    + "FROM vista_detalle_ventas "
                    + "WHERE fecha>='" + fDesde + "' AND fecha<='" + fHasta + "' AND monto>0 "
                    + " GROUP BY creferencia "
                    + " ORDER BY factura ";
            ps = conn.prepareStatement(sql);
            System.out.println("sql -> " + sql);
            ResultSet tmpdebe = ps.executeQuery(sql);
            while (tmpdebe.next()) {

                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                double totalneto = 0;
                int cliente = 0;
                int asiento = tmpdebe.getInt("asiento");
                int sucursal = tmpdebe.getInt("sucursal");
                String creferencia = tmpdebe.getString("creferencia");
                String moneda = tmpdebe.getString("moneda");
                cotizacion = tmpdebe.getDouble("cotizacion");
                nrofactura = tmpdebe.getString("factura");
                cliente = tmpdebe.getInt("cliente");
                totalneto = tmpdebe.getDouble("total");
                observacion = "VENTAS SEGÚN FACTURA N°" + nrofactura;
                int ncomprobante = tmpdebe.getInt("comprobante");
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + tmpdebe.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='" + tmpdebe.getDate("fecha") + "',debe=" + totalneto + ",haber=" + totalneto + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + tmpdebe.getDate("fecha") + "'," + totalneto + "," + totalneto + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }

                sql = "SELECT creferencia,factura,porcentaje,ctadebe,fecha, "
                        + "SUM(ROUND(monto*cotizacion,0)) as total "
                        + "FROM vista_detalle_ventas "
                        + " WHERE creferencia='" + creferencia + "' "
                        + " GROUP BY creferencia,ctadebe,porcentaje "
                        + " ORDER BY porcentaje DESC";

                System.out.println(sql);

                ps = conn.prepareStatement(sql);
                ResultSet tmp_ventadebe = ps.executeQuery(sql);
                int item = 1;
                String asi_codigo = "";
                nAsiento = 0;
                while (tmp_ventadebe.next()) {
                    item++;
                    //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                    asi_codigo = "";
                    // busqueda en rubros
                    if (ncomprobante == 1) {
                        asi_codigo = CONFIG_CONTABLE_ventacontado;
                    } else {
                        asi_codigo = tmp_ventadebe.getString("ctadebe");
                    }
                    double sumarubro = tmp_ventadebe.getDouble("total");
                    double importe = tmp_ventadebe.getDouble("total");
                    double impdebe = tmp_ventadebe.getDouble("total");
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

                }
                ///HABER

                sql = "SELECT factura,porcentaje,productos.rubro,rubros.idcta,"
                        + "SUM(ROUND(impiva*cotizacion,0)) as montoiva,"
                        + "SUM(ROUND(monto*cotizacion,0)) as total "
                        + "FROM vista_detalle_ventas "
                        + "INNER JOIN productos "
                        + " ON vista_detalle_ventas.codprod=productos.codigo "
                        + " INNER JOIN rubros "
                        + " ON productos.rubro=rubros.codigo "
                        + " WHERE creferencia='" + creferencia + "'"
                        + " GROUP BY rubros.idcta,porcentaje "
                        + " ORDER BY porcentaje DESC";

                System.out.println(sql);

                ps = conn.prepareStatement(sql);
                ResultSet tmphaber = ps.executeQuery(sql);
                item = item + 1;
                asi_codigo = "";

                nIva5 = 0;
                nIva10 = 0;
                nTotales = 0;
                while (tmphaber.next()) {
                    item++;
                    //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                    asi_codigo = "";
                    asi_codigo = tmphaber.getString("idcta");
                    double sumarubro = Math.abs(tmphaber.getDouble("total") - tmphaber.getDouble("montoiva"));
                    double importe = sumarubro;
                    double impdebe = 0;
                    double imphaber = Math.round(sumarubro);
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
                    if (tmphaber.getInt("porcentaje") == 10) {
                        nIva10 += tmphaber.getDouble("montoiva");
                    } else if (tmphaber.getInt("porcentaje") == 5) {
                        nIva5 += tmphaber.getDouble("montoiva");
                    }
                }
                System.out.println("IVA 10 " + nIva10);
                if (nIva10 > 0) {
                    double impdebe = 0;
                    double imphaber = 0;
                    double importe = 0;
                    imphaber = Math.abs(nIva10);
                    impdebe = 0;
                    importe = nIva10;
                    item++;
                    asi_codigo = CONFIG_CONTABLE_ivaventa10;
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
                // Iva 5
                if (nIva5 > 0) {
                    double impdebe = 0;
                    double imphaber = 0;
                    double importe = 0;
                    imphaber = Math.abs(nIva5);
                    imphaber = 0;
                    importe = nIva5;
                    item++;
                    asi_codigo = CONFIG_CONTABLE_ivaventa5;
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
                sql = "UPDATE cabecera_ventas SET registro=? WHERE creferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
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
        String creferencia = null;
        int item=0;
        int asiento=0;
        String asi_codigo="";

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
            // Tmphaber
            sql = "SELECT creferencia,fecha,factura,sucursal,moneda,cliente,asiento,"
                    + "cotizacion,comprobante,ctadebe,"
                    + "SUM(ROUND(monto*cotizacion,0))*-1 as total "
                    + "FROM vista_detalle_ventas "
                    + "WHERE fecha>='" + fDesde + "' AND fecha<='" + fHasta + "' AND monto<0 "
                    + " GROUP BY creferencia "
                    + " ORDER BY factura ";
            ps = conn.prepareStatement(sql);
            System.out.println("sql -> " + sql);
            ResultSet tmphaber = ps.executeQuery(sql);
            while (tmphaber.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                double totalneto = 0;
                int cliente = 0;
                asiento = tmphaber.getInt("asiento");
                int sucursal = tmphaber.getInt("sucursal");
                creferencia = tmphaber.getString("creferencia");
                String moneda = tmphaber.getString("moneda");
                cotizacion = tmphaber.getDouble("cotizacion");
                nrofactura = tmphaber.getString("factura");
                cliente = tmphaber.getInt("cliente");
                totalneto = tmphaber.getDouble("total");
                observacion = "DEVOLUCIONES SEGÚN FACTURA N°" + nrofactura;
                int ncomprobante = tmphaber.getInt("comprobante");
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + tmphaber.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='" + tmphaber.getDate("fecha") + "',debe=" + totalneto + ",haber=" + totalneto + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + tmphaber.getDate("fecha") + "'," + totalneto + "," + totalneto + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }

                ///DEBE
                sql = "SELECT factura,porcentaje,productos.rubro,rubros.idcta,"
                        + "SUM(ROUND(impiva*cotizacion,0))*-1 as montoiva,"
                        + "SUM(ROUND(monto*cotizacion,0))*-1 as total "
                        + "FROM vista_detalle_ventas "
                        + "INNER JOIN productos "
                        + " ON vista_detalle_ventas.codprod=productos.codigo "
                        + " INNER JOIN rubros "
                        + " ON productos.rubro=rubros.codigo "
                        + " WHERE creferencia='" + creferencia + "'"
                        + " GROUP BY rubros.idcta,porcentaje "
                        + " ORDER BY porcentaje DESC";

                System.out.println(sql);

                ps = conn.prepareStatement(sql);
                ResultSet tmpdebe = ps.executeQuery(sql);
                item = item + 1;
                asi_codigo = "";

                nIva5 = 0;
                nIva10 = 0;
                nTotales = 0;
                while (tmpdebe.next()) {
                    item++;
                    //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                    asi_codigo = "";
                    asi_codigo = tmpdebe.getString("idcta");
                    double sumarubro = Math.abs(tmpdebe.getDouble("total") - tmpdebe.getDouble("montoiva"));
                    double importe = sumarubro;
                    double imphaber = 0;
                    double impdebe = Math.round(sumarubro);
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
                    if (tmpdebe.getInt("porcentaje") == 10) {
                        nIva10 += tmpdebe.getDouble("montoiva");
                    } else if (tmpdebe.getInt("porcentaje") == 5) {
                        nIva5 += tmpdebe.getDouble("montoiva");
                    }
                }
                System.out.println("IVA 10 " + nIva10);
                if (nIva10 > 0) {
                    double impdebe = 0;
                    double imphaber = 0;
                    double importe = 0;
                    impdebe = Math.abs(nIva10);
                    imphaber = 0;
                    importe = nIva10;
                    item++;
                    asi_codigo = CONFIG_CONTABLE_ivaventa10;
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
                // Iva 5
                if (nIva5 > 0) {
                    double impdebe = 0;
                    double imphaber = 0;
                    double importe = 0;
                    impdebe = Math.abs(nIva5);
                    imphaber = 0;
                    importe = nIva5;
                    item++;
                    asi_codigo = CONFIG_CONTABLE_ivaventa5;
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
                //HABER

                item++;
                asi_codigo = "";
                // busqueda en rubros
                if (ncomprobante == 1) {
                    asi_codigo = CONFIG_CONTABLE_ventacontado;
                } else {
                    asi_codigo = CONFIG_CONTABLE_ventacredito;
                }
                double sumarubro = tmphaber.getDouble("total");
                double importe = tmphaber.getDouble("total");
                double imphaber = tmphaber.getDouble("total");
                double impdebe = 0;
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
                sql = "UPDATE cabecera_ventas SET registro=? WHERE creferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();

            st.close();
            ps.close();
            System.out.println("Generando asientos de ventas - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return valor;
    }


}

class TableAsientosVentasFais {

    public String asi_codigo;
    public double impdebe;
    public double imphaber;
    public long factura;
    public int item;
}

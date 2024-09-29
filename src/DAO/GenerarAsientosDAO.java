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

public class GenerarAsientosDAO {

    Conexion con = null;
    Statement st = null;

    public boolean generarCobranzas(java.util.Date desde, java.util.Date hasta) {
        boolean valor = true;
        int nIva5 = 0, nIva10 = 0, nProveedor = 0, nTotales = 0, nItems = 0, nContador = 0, nAsiento = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de cobros - Inicio");

        String cOrigen = "COBROS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Borrar asientos cabecera
            String sql = "";
            PreparedStatement ps = conn.prepareStatement(sql);
            // Recuperar datos de varias tablas
            // configuracion

            // TmpDEBE
            sql = "SELECT idpagos,numero,sucursal,fecha,moneda,"
                    + "cotizacionmoneda,totalpago,observacion,asiento, "
                    + "clientes.idcta AS ctahaber,cliente "
                    + "FROM cobranzas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cobranzas.cliente "
                    + " WHERE fecha>='" + fDesde + "' AND fecha<='" + fHasta + "' "
                    + " ORDER BY fecha";

            System.out.println("sql -> " + sql);
            ResultSet tmpcobranza = ps.executeQuery(sql);
            while (tmpcobranza.next()) {
                ArrayList<TableAsientos> tablaAsientos = new ArrayList();
                nContador++;
                double cotizacion = 0;
                long nrofactura;;
                String observacion = "";
                int asiento = tmpcobranza.getInt("asiento");
                int sucursal = tmpcobranza.getInt("sucursal");
                String idpagos = tmpcobranza.getString("idpagos");
                String moneda = tmpcobranza.getString("moneda");
                cotizacion = tmpcobranza.getDouble("cotizacionmoneda");
                nrofactura = tmpcobranza.getLong("numero");
                int cliente = tmpcobranza.getInt("cliente");
                double totalneto = tmpcobranza.getDouble("totalpago");
                observacion = tmpcobranza.getString("observacion");
                String ctahaber = tmpcobranza.getString("ctahaber");
                System.out.println("Cta haber" + ctahaber);
                System.out.println("Fecha Cobro " + tmpcobranza.getDate("fecha"));

                sql = "SELECT idmovimiento,nrocheque,confirmacion,netocobrado,idcta as ctadebe"
                        + " FROM detalle_forma_cobro "
                        + " LEFT JOIN formaspago"
                        + " ON formaspago.codigo=detalle_forma_cobro.forma "
                        + "WHERE idmovimiento='" + idpagos + "'";

                ps = conn.prepareStatement(sql);
                System.out.println("sql -> " + sql);
                ResultSet tmpdebe = ps.executeQuery(sql);
                //SE GENERA EL DEBE
                while (tmpdebe.next()) {
                    // Agregar al ArrayList
                    double importedebe = tmpdebe.getDouble("netocobrado");
                    TableAsientos tableAsiento = new TableAsientos();
                    tableAsiento.asi_codigo = tmpdebe.getString("ctadebe");
                    tableAsiento.factura = nrofactura;
                    tableAsiento.impdebe = Math.round(importedebe * cotizacion);
                    tablaAsientos.add(tableAsiento);
                }
                //SE GENERA EL HABER
                TableAsientos tableAsiento = new TableAsientos();
                tableAsiento.asi_codigo = ctahaber;
                tableAsiento.imphaber = Math.round(totalneto * cotizacion);
                tableAsiento.factura = nrofactura;
                tablaAsientos.add(tableAsiento);

                if (asiento != 0) {
                    double nt = Math.round(totalneto * cotizacion);
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, tmpcobranza.getInt("asiento"));
                    ps.executeUpdate();
                    // Agregar cabecera
                    sql = "UPDATE cabecera_asientos SET sucursal = " + tmpcobranza.getInt("sucursal") + ",fecha='"
                            + tmpcobranza.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + tmpcobranza.getDate("fecha") + "',"
                            + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }

                // Graba detalle de asiento
                int item = 0;

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
                    ps.setLong(1, asiento);
                    ps.setLong(2, tablaAsiento.factura);
                    ps.setString(3, tablaAsiento.asi_codigo);
                    ps.setString(4, observacion);
                    ps.setDouble(5, tablaAsiento.impdebe);
                    ps.setDouble(6, tablaAsiento.imphaber);
                    ps.setLong(7, item);
                    ps.executeUpdate();
                }

                // Actualiza cabecera de compras
                sql = "UPDATE cobranzas SET asiento=? WHERE idpagos=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, idpagos);
                ps.executeUpdate();

            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de cobranzas - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return valor;
    }

    public boolean generarPagos(java.util.Date desde, java.util.Date hasta) {
        boolean valor = true;
        int nIva5 = 0, nIva10 = 0, nProveedor = 0, nTotales = 0, nItems = 0, nContador = 0, nAsiento = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de pagos - Inicio");

        String cOrigen = "PAGOS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Borrar asientos cabecera
            String sql = "";
            PreparedStatement ps = conn.prepareStatement(sql);
            // Recuperar datos de varias tablas
            // configuracion

            // TmpDEBE
            sql = "SELECT idpagos,numero,sucursal,fecha,proveedor,recibo,totalpago,observacion,moneda,"
                    + "proveedores.idcta AS ctadebe,asiento,cotizacionmoneda "
                    + " FROM pagos "
                    + "INNER JOIN proveedores "
                    + " ON pagos.proveedor=proveedores.codigo "
                    + " WHERE fecha>='" + fDesde + "' AND fecha<='" + fHasta + "' "
                    + " ORDER BY fecha ";

            System.out.println("sql -> " + sql);
            ResultSet tmppagos = ps.executeQuery(sql);
            while (tmppagos.next()) {
                ArrayList<TableAsientos> tablaAsientos = new ArrayList();
                nContador++;
                double cotizacion = 0;
                long nrofactura;;
                String observacion = "";
                int asiento = tmppagos.getInt("asiento");
                int sucursal = tmppagos.getInt("sucursal");
                String idpagos = tmppagos.getString("idpagos");
                String moneda = tmppagos.getString("moneda");
                cotizacion = tmppagos.getDouble("cotizacionmoneda");
                nrofactura = tmppagos.getLong("recibo");
                int proveedor = tmppagos.getInt("proveedor");
                double totalneto = tmppagos.getDouble("totalpago");
                observacion = tmppagos.getString("observacion");
                String ctadebe = tmppagos.getString("ctadebe");
                System.out.println("Fecha Cobro " + tmppagos.getDate("fecha"));

                //SE GENERA EL DEBE
                if (asiento != 0) {
                    double nt = Math.round(totalneto * cotizacion);
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, tmppagos.getInt("asiento"));
                    ps.executeUpdate();
                    // Agregar cabecera
                    sql = "UPDATE cabecera_asientos SET sucursal = " + tmppagos.getInt("sucursal") + ",fecha='"
                            + tmppagos.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + tmppagos.getDate("fecha") + "',"
                            + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }

                int item = 1;
                String asi_codigo = "";
                //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                asi_codigo = ctadebe;

                double importe = Math.round(totalneto * cotizacion);
                double impdebe = Math.round(totalneto * cotizacion);;
                double imphaber = 0;
                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setLong(2, nrofactura);
                ps.setString(3, moneda);
                ps.setDouble(4, cotizacion);
                ps.setDouble(5, importe);
                ps.setString(6, asi_codigo);
                ps.setString(7, observacion);
                ps.setDouble(8, impdebe);
                ps.setDouble(9, imphaber);
                ps.setInt(10, item);
                ps.executeUpdate();

                //SE GENERA EL HABER
                sql = "SELECT idmovimiento,banco,nrocheque,"
                        + "confirmacion,netocobrado,bancos.idcuenta as ctahaber "
                        + "FROM detalle_forma_pago "
                        + "LEFT JOIN bancos "
                        + "ON bancos.codigo=detalle_forma_pago.banco "
                        + "WHERE idmovimiento='" + idpagos + "'";

                ps = conn.prepareStatement(sql);
                System.out.println("sql HABER -> " + sql);
                ResultSet tmphaber = ps.executeQuery(sql);

                while (tmphaber.next()) {
                    // Agregar al ArrayList
                    double importehaber = tmphaber.getDouble("netocobrado");
                    TableAsientos tableAsiento = new TableAsientos();
                    tableAsiento.asi_codigo = tmphaber.getString("ctahaber");
                    tableAsiento.factura = tmphaber.getLong("nrocheque");
                    tableAsiento.imphaber = Math.round(importehaber * cotizacion);
                    tablaAsientos.add(tableAsiento);
                }

                // Graba detalle de asiento
                item = item++;

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
                    ps.setLong(1, asiento);
                    ps.setLong(2, tablaAsiento.factura);
                    ps.setString(3, tablaAsiento.asi_codigo);
                    ps.setString(4, observacion);
                    ps.setDouble(5, tablaAsiento.impdebe);
                    ps.setDouble(6, tablaAsiento.imphaber);
                    ps.setLong(7, item);
                    ps.executeUpdate();
                }

                // Actualiza cabecera de compras
                sql = "UPDATE pagos SET asiento=? WHERE idpagos=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, idpagos);
                ps.executeUpdate();

            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de pagos - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return valor;
    }

    public boolean generarCobranzasItem(String id) {
        boolean valor = true;
        int nIva5 = 0, nIva10 = 0, nProveedor = 0, nTotales = 0, nItems = 0, nContador = 0, nAsiento = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Generando asientos de cobros - Inicio");

        String cOrigen = "COBROS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Borrar asientos cabecera
            String sql = "";
            PreparedStatement ps = conn.prepareStatement(sql);
            // Recuperar datos de varias tablas
            // configuracion

            // TmpDEBE
            sql = "SELECT idpagos,numero,sucursal,fecha,moneda,"
                    + "cotizacionmoneda,totalpago,observacion,asiento, "
                    + "clientes.idcta AS ctahaber,cliente "
                    + "FROM cobranzas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cobranzas.cliente "
                    + " WHERE idpagos='" + id + "' ORDER BY fecha";

            System.out.println("sql -> " + sql);
            ResultSet tmpcobranza = ps.executeQuery(sql);
            while (tmpcobranza.next()) {
                ArrayList<TableAsientos> tablaAsientos = new ArrayList();
                nContador++;
                double cotizacion = 0;
                long nrofactura;;
                String observacion = "";
                int asiento = tmpcobranza.getInt("asiento");
                int sucursal = tmpcobranza.getInt("sucursal");
                String idpagos = tmpcobranza.getString("idpagos");
                String moneda = tmpcobranza.getString("moneda");
                cotizacion = tmpcobranza.getDouble("cotizacionmoneda");
                nrofactura = tmpcobranza.getLong("numero");
                int cliente = tmpcobranza.getInt("cliente");
                double totalneto = tmpcobranza.getDouble("totalpago");
                observacion = tmpcobranza.getString("observacion");
                String ctahaber = tmpcobranza.getString("ctahaber");
                System.out.println("Cta. haber " + ctahaber);
                System.out.println("Fecha Cobro " + tmpcobranza.getDate("fecha"));

                sql = "SELECT idmovimiento,nrocheque,confirmacion,netocobrado,idcta as ctadebe"
                        + " FROM detalle_forma_cobro "
                        + " LEFT JOIN formaspago"
                        + " ON formaspago.codigo=detalle_forma_cobro.forma "
                        + "WHERE idmovimiento='" + idpagos + "'";

                ps = conn.prepareStatement(sql);
                System.out.println("sql -> " + sql);
                ResultSet tmpdebe = ps.executeQuery(sql);
                //SE GENERA EL DEBE
                while (tmpdebe.next()) {
                    // Agregar al ArrayList
                    double importedebe = tmpdebe.getDouble("netocobrado");
                    TableAsientos tableAsiento = new TableAsientos();
                    tableAsiento.asi_codigo = tmpdebe.getString("ctadebe");
                    tableAsiento.factura = nrofactura;
                    tableAsiento.impdebe = Math.round(importedebe * cotizacion);
                    tablaAsientos.add(tableAsiento);
                }
                //SE GENERA EL HABER
                TableAsientos tableAsiento = new TableAsientos();
                tableAsiento.asi_codigo = ctahaber;
                tableAsiento.imphaber = Math.round(totalneto * cotizacion);
                tableAsiento.factura = nrofactura;
                tablaAsientos.add(tableAsiento);

                if (asiento != 0) {
                    double nt = Math.round(totalneto * cotizacion);
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, tmpcobranza.getInt("asiento"));
                    ps.executeUpdate();
                    // Agregar cabecera
                    sql = "UPDATE cabecera_asientos SET sucursal = " + tmpcobranza.getInt("sucursal") + ",fecha='"
                            + tmpcobranza.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + tmpcobranza.getDate("fecha") + "',"
                            + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }

                // Graba detalle de asiento
                int item = 0;

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
                    ps.setLong(1, asiento);
                    ps.setLong(2, tablaAsiento.factura);
                    ps.setString(3, tablaAsiento.asi_codigo);
                    ps.setString(4, observacion);
                    ps.setDouble(5, tablaAsiento.impdebe);
                    ps.setDouble(6, tablaAsiento.imphaber);
                    ps.setLong(7, item);
                    ps.executeUpdate();
                }

                // Actualiza cabecera de compras
                sql = "UPDATE cobranzas SET asiento=? WHERE idpagos=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, idpagos);
                ps.executeUpdate();

            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de cobranzas - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return valor;
    }

    public boolean generarPagosItem(String id) {
        boolean valor = true;
        int nIva5 = 0, nIva10 = 0, nProveedor = 0, nTotales = 0, nItems = 0, nContador = 0, nAsiento = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Generando asientos de pagos - Inicio");

        String cOrigen = "PAGOS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Borrar asientos cabecera
            String sql = "";
            PreparedStatement ps = conn.prepareStatement(sql);
            // Recuperar datos de varias tablas
            // configuracion

            // TmpDEBE
            sql = "SELECT idpagos,numero,sucursal,fecha,proveedor,recibo,totalpago,observacion,moneda,"
                    + "proveedores.idcta AS ctadebe,asiento,cotizacionmoneda "
                    + " FROM pagos "
                    + "INNER JOIN proveedores "
                    + " ON pagos.proveedor=proveedores.codigo "
                    + " WHERE idpagos='" + id + "' ORDER BY fecha ";

            System.out.println("sql -> " + sql);
            ResultSet tmppagos = ps.executeQuery(sql);
            while (tmppagos.next()) {
                ArrayList<TableAsientos> tablaAsientos = new ArrayList();
                nContador++;
                double cotizacion = 0;
                long nrofactura;;
                String observacion = "";
                int asiento = tmppagos.getInt("asiento");
                int sucursal = tmppagos.getInt("sucursal");
                String idpagos = tmppagos.getString("idpagos");
                String moneda = tmppagos.getString("moneda");
                cotizacion = tmppagos.getDouble("cotizacionmoneda");
                nrofactura = tmppagos.getLong("recibo");
                int proveedor = tmppagos.getInt("proveedor");
                double totalneto = tmppagos.getDouble("totalpago");
                observacion = tmppagos.getString("observacion");
                String ctadebe = tmppagos.getString("ctadebe");
                System.out.println("Fecha Cobro " + tmppagos.getDate("fecha"));

                //SE GENERA EL DEBE
                if (asiento != 0) {
                    double nt = Math.round(totalneto * cotizacion);
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, tmppagos.getInt("asiento"));
                    ps.executeUpdate();
                    // Agregar cabecera
                    sql = "UPDATE cabecera_asientos SET sucursal = " + tmppagos.getInt("sucursal") + ",fecha='"
                            + tmppagos.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + tmppagos.getDate("fecha") + "',"
                            + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }

                int item = 1;
                String asi_codigo = "";
                //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                asi_codigo = ctadebe;

                double importe = Math.round(totalneto * cotizacion);
                double impdebe = Math.round(totalneto * cotizacion);;
                double imphaber = 0;
                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setLong(2, nrofactura);
                ps.setString(3, moneda);
                ps.setDouble(4, cotizacion);
                ps.setDouble(5, importe);
                ps.setString(6, asi_codigo);
                ps.setString(7, observacion);
                ps.setDouble(8, impdebe);
                ps.setDouble(9, imphaber);
                ps.setInt(10, item);
                ps.executeUpdate();

                //SE GENERA EL HABER
                sql = "SELECT idmovimiento,banco,nrocheque,"
                        + "confirmacion,netocobrado,bancos.idcuenta as ctahaber "
                        + "FROM detalle_forma_pago "
                        + "LEFT JOIN bancos "
                        + "ON bancos.codigo=detalle_forma_pago.banco "
                        + "WHERE idmovimiento='" + idpagos + "'";

                ps = conn.prepareStatement(sql);
                System.out.println("sql HABER -> " + sql);
                ResultSet tmphaber = ps.executeQuery(sql);

                while (tmphaber.next()) {
                    // Agregar al ArrayList
                    double importehaber = tmphaber.getDouble("netocobrado");
                    TableAsientos tableAsiento = new TableAsientos();
                    tableAsiento.asi_codigo = tmphaber.getString("ctahaber");
                    tableAsiento.factura = tmphaber.getLong("nrocheque");
                    tableAsiento.imphaber = Math.round(importehaber * cotizacion);
                    tablaAsientos.add(tableAsiento);
                }

                // Graba detalle de asiento
                item = item++;

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
                    ps.setLong(1, asiento);
                    ps.setLong(2, tablaAsiento.factura);
                    ps.setString(3, tablaAsiento.asi_codigo);
                    ps.setString(4, observacion);
                    ps.setDouble(5, tablaAsiento.impdebe);
                    ps.setDouble(6, tablaAsiento.imphaber);
                    ps.setLong(7, item);
                    ps.executeUpdate();
                }

                // Actualiza cabecera de compras
                sql = "UPDATE pagos SET asiento=? WHERE idpagos=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, idpagos);
                ps.executeUpdate();

            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de pagos - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return valor;
    }

    public boolean generarCobranzasItemFais(String id) {
        boolean valor = true;
        int nIva5 = 0, nIva10 = 0, nProveedor = 0, nTotales = 0, nItems = 0, nContador = 0, nAsiento = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Generando asientos de cobros - Inicio");

        String cOrigen = "COBROS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Borrar asientos cabecera
            String sql = "";
            PreparedStatement ps = conn.prepareStatement(sql);
            // Recuperar datos de varias tablas
            // configuracion

            // TmpDEBE

            sql = "SELECT idpagos,numero,sucursal,fecha,moneda,"
                    + "cotizacionmoneda,totalpago,observacion,asiento, "
                    + "clientes.idcta AS ctahaber,cliente "
                    + "FROM cobranzas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cobranzas.cliente "
                    + " WHERE cobranzas.idpagos='" + id + "' ORDER BY fecha";

            System.out.println("sql -> " + sql);
            ResultSet tmpcobranza = ps.executeQuery(sql);
            while (tmpcobranza.next()) {
                ArrayList<TableAsientos> tablaAsientos = new ArrayList();
                nContador++;
                double cotizacion = 0;
                long nrofactura;;
                String observacion = "";
                int asiento = tmpcobranza.getInt("asiento");
                int sucursal = tmpcobranza.getInt("sucursal");
                String idpagos = tmpcobranza.getString("idpagos");
                String moneda = tmpcobranza.getString("moneda");
                cotizacion = tmpcobranza.getDouble("cotizacionmoneda");
                nrofactura = tmpcobranza.getLong("numero");
                int cliente = tmpcobranza.getInt("cliente");
                double totalneto = tmpcobranza.getDouble("totalpago");
                observacion = tmpcobranza.getString("observacion");
                String ctahaber = tmpcobranza.getString("ctahaber");
                System.out.println("Fecha Cobro " + tmpcobranza.getDate("fecha"));

                sql = "SELECT idmovimiento,nrocheque,confirmacion,netocobrado,idcta as ctadebe"
                        + " FROM detalle_forma_cobro "
                        + " LEFT JOIN formaspago"
                        + " ON formaspago.codigo=detalle_forma_cobro.forma "
                        + "WHERE idmovimiento='" + idpagos + "'";

                ps = conn.prepareStatement(sql);
                System.out.println("sql -> " + sql);
                ResultSet tmpdebe = ps.executeQuery(sql);
                //SE GENERA EL DEBE
                while (tmpdebe.next()) {
                    // Agregar al ArrayList
                    double importedebe = tmpdebe.getDouble("netocobrado");
                    TableAsientos tableAsiento = new TableAsientos();
                    tableAsiento.asi_codigo = tmpdebe.getString("ctadebe");
                    tableAsiento.factura = nrofactura;
                    tableAsiento.impdebe = Math.round(importedebe * cotizacion);
                    tablaAsientos.add(tableAsiento);
                }

                //SE GENERA EL HABER
            sql = "SELECT cobranzas.idpagos,numero,sucursal,fecha,moneda,"
                    + "cotizacionmoneda,cobranzas.observacion,asiento,cobranzas.cliente,"
                    + "productos.ctadebe AS ctahaber,"
                    + "cobranzas.totalpago,"
                    + "sum(cobrositem.pago) as supago  "
                    + "FROM cobranzas "
                    + "LEFT JOIN cobrositem "
                    + "ON cobrositem.idpagos=cobranzas.idpagos "
                    + "LEFT JOIN detalle_ventas "
                    + "ON detalle_ventas.item=cobrositem.iditem "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=detalle_ventas.codprod "
                    + " WHERE cobranzas.idpagos='" + id + "'"
                    + " GROUP BY productos.ctadebe "
                    + " ORDER BY fecha";

                ps = conn.prepareStatement(sql);
                System.out.println("sql -> " + sql);
                ResultSet tmphaber = ps.executeQuery(sql);

                while (tmphaber.next()) {
                    // Agregar al ArrayList
                    double importehaber = tmphaber.getDouble("supago");
                    TableAsientos tableAsiento = new TableAsientos();
                    tableAsiento.asi_codigo = tmphaber.getString("ctahaber");
                    tableAsiento.factura = nrofactura;
                    tableAsiento.imphaber = Math.round(importehaber * cotizacion);
                    tablaAsientos.add(tableAsiento);
                }

                if (asiento != 0) {
                    double nt = Math.round(totalneto * cotizacion);
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, tmpcobranza.getInt("asiento"));
                    ps.executeUpdate();
                    // Agregar cabecera
                    sql = "UPDATE cabecera_asientos SET sucursal = " + tmpcobranza.getInt("sucursal") + ",fecha='"
                            + tmpcobranza.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + tmpcobranza.getDate("fecha") + "',"
                            + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }

                // Graba detalle de asiento
                int item = 0;

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
                    ps.setLong(1, asiento);
                    ps.setLong(2, tablaAsiento.factura);
                    ps.setString(3, tablaAsiento.asi_codigo);
                    ps.setString(4, observacion);
                    ps.setDouble(5, tablaAsiento.impdebe);
                    ps.setDouble(6, tablaAsiento.imphaber);
                    ps.setLong(7, item);
                    ps.executeUpdate();
                }

                // Actualiza cabecera de compras
                sql = "UPDATE cobranzas SET asiento=? WHERE idpagos=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, idpagos);
                ps.executeUpdate();

            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de cobranzas - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return valor;
    }

}

class TableAsientos {

    public String asi_codigo;
    public double impdebe;
    public double imphaber;
    public long factura;
    public int item;
}

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

public class GenerarAsientoComprasDAO {

    Conexion con = null;
    Statement st = null;

    public boolean generarCompras(java.util.Date desde, java.util.Date hasta) {
        System.out.println("Generando asientos de compras");
        boolean valor = true;
        config_contableDAO configDAO = new config_contableDAO();
        config_contable config = new config_contable();
        config = configDAO.consultar();
        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de compras - Inicio");

        String cOrigen = "COMPRAS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas
            String sql = "SELECT * FROM cabecera_compras WHERE  fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "' AND cabecera_compras.totalneto>0  ORDER BY fecha";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet cabecera_compras = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            double totalneto = 0;
            double totalpagos = 0;
            while (cabecera_compras.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                int proveedor = 0;
                double nivadescuento = 0;
                int asiento = cabecera_compras.getInt("asiento");
                int sucursal = cabecera_compras.getInt("sucursal");
                String creferencia = cabecera_compras.getString("creferencia");
                String moneda = cabecera_compras.getString("moneda");
                cotizacion = cabecera_compras.getDouble("cotizacion");
                nrofactura = cabecera_compras.getString("nrofactura");
                proveedor = cabecera_compras.getInt("proveedor");
                totalneto = cabecera_compras.getDouble("totalneto");
                observacion = cabecera_compras.getString("observacion");

                System.out.println("Fecha Compra " + cabecera_compras.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + cabecera_compras.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='" + cabecera_compras.getDate("fecha") + "',debe=" + totalneto + ",haber=" + totalneto + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + cabecera_compras.getDate("fecha") + "'," + totalneto + "," + totalneto + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }
                // Table pagos
                sql = "SELECT idmovimiento,forma,banco,codmoneda,"
                        + " confirmacion,netocobrado,nrocheque,"
                        + " bancos.idcuenta "
                        + " FROM detalle_forma_pago "
                        + " LEFT JOIN bancos "
                        + " ON bancos.codigo=detalle_forma_pago.banco "
                        + " WHERE idmovimiento='" + creferencia + "'";
                ps = conn.prepareStatement(sql);
                ResultSet table_pagos = ps.executeQuery(sql);
                // Table datos

                sql = "SELECT productos.rubro,rubros.idctamercaderia,"
                        + "SUM(monto-impiva) AS sumarubro,"
                        + "SUM(impiva) AS montoiva,"
                        + "productos.observacion,porcentaje "
                        + " FROM rubros,detalle_compras,productos "
                        + "WHERE rubros.codigo=productos.rubro "
                        + "AND productos.codigo=detalle_compras.codprod "
                        + " AND detalle_compras.dreferencia='" + creferencia + "' "
                        + "GROUP BY rubros.idctamercaderia";
                ps = conn.prepareStatement(sql);
                System.out.println(sql);

                ResultSet tmp_rubros = ps.executeQuery(sql);
                int item = 1;
                String asi_codigo = "";
                nAsiento = 0;
                nIva5 = 0;
                nIva10 = 0;
                nTotales = 0;

                while (tmp_rubros.next()) {
                    item++;
                    //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                    asi_codigo = "";
                    // busqueda en rubros
                    asi_codigo = tmp_rubros.getString("idctamercaderia");
                    double sumarubro = tmp_rubros.getDouble("sumarubro");
                    double importe = sumarubro;
                    double impdebe = Math.round(importe * cotizacion);
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
                    if (tmp_rubros.getInt("porcentaje") == 10) {
                        nIva10 += tmp_rubros.getDouble("montoiva");
                    } else if (tmp_rubros.getInt("porcentaje") == 5) {
                        nIva5 += tmp_rubros.getDouble("montoiva");
                    }
                }
                System.out.println("IVA 10 "+nIva10 );
                if (nIva10 > 0) {
                    double impdebe = 0;
                    double imphaber = 0;
                    double importe = 0;
                    impdebe = nIva10;
                    impdebe = Math.round(impdebe * cotizacion);
                    imphaber = 0;
                    importe = nIva10;
                    item++;
                    asi_codigo = config.getIvacompra10();
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
                    impdebe = nIva5;
                    impdebe = Math.round(impdebe * cotizacion);
                    imphaber = 0;
                    importe = nIva5;
                    item++;
                    asi_codigo = config.getIvacompra5();
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
                /// SE GENERA EL ASIENTO DE DESCUENTOS OBTENIDOS
                // ANTES DE GENERAR EL PAGO

                // GRABA EL HABER
                // EN CASO QUE LA FACTURA SEA CREDITO
                // GRABA EL HABER
                // EN CASO QUE TENGA UNA FINANCIACION
                totalpagos = 0;
                while (table_pagos.next()) {
                    double netocobrado = table_pagos.getDouble("netocobrado");
                    totalpagos = totalpagos + netocobrado;
                    double impdebe = 0;
                    double imphaber = 0;

                    double importe = 0;
                    impdebe = 0;
                    imphaber = Math.round(netocobrado * cotizacion);
                    importe = netocobrado;
                    item++;
                    asi_codigo = table_pagos.getString("idcuenta");
                    String banco = table_pagos.getString("banco");
                    String nrocheque = table_pagos.getString("nrocheque");
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

                if (totalneto - totalpagos > 0) {
                    double impdebe = 0;
                    double imphaber = 0;
                    double importe = 0;
                    impdebe = 0;
                    imphaber = Math.round((totalneto - totalpagos) * cotizacion);
                    importe = totalneto - totalpagos;
                    item++;
                    asi_codigo = "";
                    // busqueda en proveedores
                    sql = "SELECT idcta FROM proveedores WHERE codigo=" + proveedor;
                    ps = conn.prepareStatement(sql);
                    ResultSet proveedores = ps.executeQuery(sql);
                    proveedores.last();
                    asi_codigo = proveedores.getString("idcta");

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
                // Graba el haber
                // En caso que tenga pagos realizados
                // Actualiza cabecera de compras
                sql = "UPDATE cabecera_compras SET asiento=? WHERE creferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();
            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de compras - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        return valor;
    }

    public boolean generarGastosCredito(java.util.Date desde, java.util.Date hasta) throws SQLException {
        System.out.println("Generando asientos de gastos a pagar");
        boolean valor = true;
        config_contableDAO configDAO = new config_contableDAO();
        config_contable config = new config_contable();
        config = configDAO.consultar();

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de compras - Inicio");

        String cOrigen = "GASTOS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas
            String sql = "";
            if (Integer.valueOf(Config.nCentroCosto) == 0) {
                sql = "SELECT creferencia,nrofactura,sucursal,fecha,proveedor,"
                        + "concepto,exentas,gravadas10,gravadas5,iva10,iva5,totalneto,"
                        + "moneda,cotizacion,observacion,asiento,rubro_compras.idcta AS ctadebe,"
                        + "proveedores.idcta AS ctahaber "
                        + "FROM gastos_compras "
                        + "LEFT JOIN rubro_compras "
                        + "ON rubro_compras.codigo=gastos_compras.concepto "
                        + "LEFT JOIN proveedores "
                        + "ON proveedores.codigo=gastos_compras.proveedor "
                        + "WHERE totalneto>0 "
                        + "AND gastos_compras.fondofijo=0 "
                        + "AND fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "' ORDER BY fecha";
            } else {
                sql = "SELECT creferencia,nrofactura,sucursal,fecha,proveedor,"
                        + "concepto,exentas,gravadas10,gravadas5,iva10,iva5,totalneto,"
                        + "moneda,cotizacion,observacion,asiento,gastos_compras.idcta AS ctadebe,"
                        + "proveedores.idcta AS ctahaber "
                        + "FROM gastos_compras "
                        + "LEFT JOIN proveedores "
                        + "ON proveedores.codigo=gastos_compras.proveedor "
                        + "WHERE totalneto>0 "
                        + "AND gastos_compras.fondofijo=0 "
                        + "AND fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "' ORDER BY fecha";
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet gastos_credito = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            while (gastos_credito.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                int proveedor = 0;
                int asiento = gastos_credito.getInt("asiento");
                int sucursal = gastos_credito.getInt("sucursal");
                String creferencia = gastos_credito.getString("creferencia");
                String moneda = gastos_credito.getString("moneda");
                cotizacion = gastos_credito.getDouble("cotizacion");
                nrofactura = gastos_credito.getString("nrofactura");
                proveedor = gastos_credito.getInt("proveedor");
                double totalneto = gastos_credito.getDouble("totalneto");
                double exentas = gastos_credito.getDouble("exentas");
                double gravadas10 = gastos_credito.getDouble("gravadas10");
                double gravadas5 = gastos_credito.getDouble("gravadas5");
                double iva10 = gastos_credito.getDouble("iva10");
                double iva5 = gastos_credito.getDouble("iva5");
                observacion = gastos_credito.getString("observacion");
                String ctadebe = gastos_credito.getString("ctadebe");
                String ctahaber = gastos_credito.getString("ctahaber");
                System.out.println("Fecha Compra " + gastos_credito.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + gastos_credito.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='"
                            + gastos_credito.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + gastos_credito.getDate("fecha") + "'," + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }
                // Table pagos
                // Table datos
                int item = 1;
                String asi_codigo = "";
                //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                asi_codigo = ctadebe;

                double importe = Math.round(totalneto * cotizacion);
                totalneto = importe;

                if (iva10 > 0) {
                    iva10 = Math.round(totalneto / 11);
                }
                if (iva5 > 0) {
                    iva5 = Math.round(totalneto / 21);
                }
                double impdebe = Math.round(totalneto - (iva10 + iva5));
                double imphaber = 0;
                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setString(2, nrofactura);
                ps.setString(3, moneda);
                ps.setDouble(4, cotizacion);
                ps.setDouble(5, importe / cotizacion);
                ps.setString(6, asi_codigo);
                ps.setString(7, observacion);
                ps.setDouble(8, impdebe);
                ps.setDouble(9, imphaber);
                ps.setInt(10, item);
                ps.executeUpdate();
                // Iva 10
                if (iva10 != 0) {
                    impdebe = 0;
                    imphaber = 0;
                    importe = 0;
                    impdebe = iva10;
                    imphaber = 0;
                    importe = iva10 / cotizacion;
                    item++;
                    asi_codigo = config.getIvacompra10();
                    sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                            + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, asiento);
                    ps.setString(2, nrofactura);
                    ps.setString(3, moneda);
                    ps.setDouble(4, cotizacion);
                    ps.setDouble(5, iva10 / cotizacion);
                    ps.setString(6, asi_codigo);
                    ps.setString(7, observacion);
                    ps.setDouble(8, impdebe);
                    ps.setDouble(9, imphaber);
                    ps.setInt(10, item);
                    ps.executeUpdate();
                }
                // Iva 5
                if (iva5 != 0) {
                    impdebe = 0;
                    imphaber = 0;
                    importe = 0;
                    impdebe = iva5;
                    imphaber = 0;
                    importe = iva5 / cotizacion;
                    item++;
                    asi_codigo = config.getIvacompra5();
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
                // GRABA EL HABER
                // QUE AQUI SIEMPRE VA SER A CREDITO
                // GRABA EL HABER
                importe = 0;
                impdebe = 0;
                imphaber = totalneto;
                importe = totalneto / cotizacion;
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

                // Actualiza cabecera de compras
                sql = "UPDATE gastos_compras SET asiento=? WHERE creferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();
            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de compras - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }

    public boolean generarNotaCreditoCompras(java.util.Date desde, java.util.Date hasta) {
        System.out.println("Generando devoluciones de compras");
        boolean valor = true;
        config_contableDAO configDAO = new config_contableDAO();
        config_contable config = new config_contable();
        config = configDAO.consultar();

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de compras - Inicio");

        String cOrigen = "COMPRAS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas
            String sql = "SELECT * FROM cabecera_compras WHERE  fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "' AND cabecera_compras.totalneto<0  ORDER BY fecha";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet cabecera_compras = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            double totalneto = 0;
            double totalpagos = 0;
            while (cabecera_compras.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                int proveedor = 0;
                int asiento = cabecera_compras.getInt("asiento");
                int sucursal = cabecera_compras.getInt("sucursal");
                String creferencia = cabecera_compras.getString("creferencia");
                String moneda = cabecera_compras.getString("moneda");
                cotizacion = cabecera_compras.getDouble("cotizacion");
                nrofactura = cabecera_compras.getString("nrofactura");
                proveedor = cabecera_compras.getInt("proveedor");
                totalneto = cabecera_compras.getDouble("totalneto");
                observacion = cabecera_compras.getString("observacion");
                System.out.println("Fecha Compra " + cabecera_compras.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + cabecera_compras.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='" + cabecera_compras.getDate("fecha") + "',debe=" + Math.abs(totalneto) + ",haber=" + Math.abs(totalneto) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + cabecera_compras.getDate("fecha") + "'," + Math.abs(totalneto) + "," + Math.abs(totalneto) + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }

                //SE GENERA EL DEBE, EN ESTE CASO PRIMERO EL PROVEEDOR
                int item = 0;
                double impdebe = 0;
                double imphaber = 0;
                double importe = 0;
                impdebe = Math.abs(Math.round(totalneto * cotizacion));
                imphaber = 0;
                importe = totalneto;
                item++;
                // busqueda en proveedores
                sql = "SELECT idcta FROM proveedores WHERE codigo=" + proveedor;
                ps = conn.prepareStatement(sql);
                ResultSet proveedores = ps.executeQuery(sql);
                proveedores.last();
                String asi_codigo = proveedores.getString("idcta");

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

                // Table pagos
                sql = "SELECT idmovimiento,forma,banco,codmoneda,"
                        + " confirmacion,netocobrado,nrocheque,"
                        + " bancos.idcuenta "
                        + " FROM detalle_forma_pago "
                        + " LEFT JOIN bancos "
                        + " ON bancos.codigo=detalle_forma_pago.banco "
                        + " WHERE idmovimiento='" + creferencia + "'";
                ps = conn.prepareStatement(sql);
                ResultSet table_pagos = ps.executeQuery(sql);
                // Table datos
                sql = "SELECT * FROM detalle_compras WHERE dreferencia='" + creferencia + "'";
                ps = conn.prepareStatement(sql);
                ResultSet table_datos = ps.executeQuery(sql);
                nIva10 = 0;
                nIva5 = 0;
                while (table_datos.next()) {
                    if (table_datos.getInt("porcentaje") == 10) {
                        nIva10 += table_datos.getDouble("impiva");
                    } else if (table_datos.getInt("porcentaje") == 5) {
                        nIva5 += table_datos.getDouble("impiva");
                    }
                }
                // tmp_rubros ATENCION table_datos reemplazado por detalle_compras
                sql = "SELECT productos.rubro,rubros.idctamercaderia,SUM(monto-impiva) as sumarubro,productos.observacion "
                        + "FROM rubros,detalle_compras,productos "
                        + "WHERE rubros.codigo=productos.rubro "
                        + "AND productos.codigo=detalle_compras.codprod "
                        + "AND detalle_compras.dreferencia='" + creferencia + "' "
                        + "GROUP BY rubros.idctamercaderia";
                ps = conn.prepareStatement(sql);
                ResultSet tmp_rubros = ps.executeQuery(sql);
                item++;

                asi_codigo = "";
                while (tmp_rubros.next()) {
                    item++;
                    //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                    asi_codigo = "";
                    int rubro = tmp_rubros.getInt("rubro");
                    // busqueda en rubros
                    sql = "SELECT idctamercaderia FROM rubros WHERE codigo=" + rubro;
                    ps = conn.prepareStatement(sql);
                    ResultSet rubros = ps.executeQuery(sql);
                    if (rubros.next()) {
                        asi_codigo = rubros.getString("idctamercaderia");
                    }

                    double sumarubro = tmp_rubros.getDouble("sumarubro");
                    importe = sumarubro;
                    impdebe = 0;
                    imphaber = Math.abs(Math.round(sumarubro * cotizacion));
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
                // Iva 10
                if (nIva10 != 0) {
                    impdebe = 0;
                    imphaber = nIva10;
                    importe = 0;
                    impdebe = 0;
                    imphaber = Math.abs(Math.round(imphaber * cotizacion));
                    importe = nIva10;
                    item++;
                    asi_codigo = config.getIvacompra10();
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
                if (nIva5 != 0) {
                    impdebe = 0;
                    imphaber = nIva5;
                    importe = 0;
                    impdebe = 0;
                    imphaber = Math.abs(Math.round(impdebe * cotizacion));
                    importe = nIva5;
                    item++;
                    asi_codigo = config.getIvacompra5();
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

                // Graba el haber
                // En caso que tenga pagos realizados
                // Actualiza cabecera de compras
                sql = "UPDATE cabecera_compras SET asiento=? WHERE creferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();
            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de compras - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        return valor;
    }

    public boolean generarComprasItem(String id) {
        System.out.println("Generando asientos de compras");
        boolean valor = true;
        config_contableDAO configDAO = new config_contableDAO();
        config_contable config = new config_contable();
        config = configDAO.consultar();
        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Generando asientos de compras - Inicio");

        String cOrigen = "COMPRAS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas
            String sql = "SELECT * FROM cabecera_compras WHERE  creferencia='" + id + "' ORDER BY creferencia";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet cabecera_compras = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            double totalneto = 0;
            double totalpagos = 0;
            while (cabecera_compras.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                int proveedor = 0;
                int asiento = cabecera_compras.getInt("asiento");
                int sucursal = cabecera_compras.getInt("sucursal");
                String creferencia = cabecera_compras.getString("creferencia");
                String moneda = cabecera_compras.getString("moneda");
                cotizacion = cabecera_compras.getDouble("cotizacion");
                nrofactura = cabecera_compras.getString("nrofactura");
                proveedor = cabecera_compras.getInt("proveedor");
                totalneto = cabecera_compras.getDouble("totalneto");
                observacion = cabecera_compras.getString("observacion");

                System.out.println("Fecha Compra " + cabecera_compras.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + cabecera_compras.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='" + cabecera_compras.getDate("fecha") + "',debe=" + totalneto + ",haber=" + totalneto + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + cabecera_compras.getDate("fecha") + "'," + totalneto + "," + totalneto + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }
                // Table pagos
                sql = "SELECT idmovimiento,forma,banco,codmoneda,"
                        + " confirmacion,netocobrado,nrocheque,"
                        + " bancos.idcuenta "
                        + " FROM detalle_forma_pago "
                        + " LEFT JOIN bancos "
                        + " ON bancos.codigo=detalle_forma_pago.banco "
                        + " WHERE idmovimiento='" + creferencia + "'";
                ps = conn.prepareStatement(sql);
                ResultSet table_pagos = ps.executeQuery(sql);
                // Table datos
                sql = "SELECT productos.rubro,rubros.idctamercaderia,SUM(monto-impiva) as sumarubro,"
                        + "SUM(impiva) AS montoiva,productos.observacion,porcentaje "
                        + " FROM rubros,detalle_compras,productos "
                        + "WHERE rubros.codigo=productos.rubro "
                        + "AND productos.codigo=detalle_compras.codprod "
                        + " AND detalle_compras.dreferencia='" + creferencia + "' "
                        + "GROUP BY rubros.idctamercaderia";
                ps = conn.prepareStatement(sql);
                System.out.println(sql);
                ResultSet tmp_rubros = ps.executeQuery(sql);
                int item = 1;
                String asi_codigo = "";
                nAsiento = 0;
                nIva5 = 0;
                nIva10 = 0;
                nTotales = 0;

                while (tmp_rubros.next()) {
                    item++;
                    //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                    asi_codigo = "";
                    // busqueda en rubros
                    asi_codigo = tmp_rubros.getString("idctamercaderia");
                    double sumarubro = tmp_rubros.getDouble("sumarubro");
                    double importe = sumarubro;
                    double impdebe = Math.round(importe * cotizacion);
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

                    if (tmp_rubros.getInt("porcentaje") == 10) {
                        nIva10 += tmp_rubros.getDouble("montoiva");
                    } else if (tmp_rubros.getInt("porcentaje") == 5) {
                        nIva5 += tmp_rubros.getDouble("montoiva");
                    }

                }

                if (nIva10 > 0) {
                    double impdebe = 0;
                    double imphaber = 0;
                    double importe = 0;
                    impdebe = nIva10;
                    impdebe = Math.round(impdebe * cotizacion);
                    imphaber = 0;
                    importe = nIva10;
                    item++;
                    asi_codigo = config.getIvacompra10();
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
                    impdebe = nIva5;
                    impdebe = Math.round(impdebe * cotizacion);
                    imphaber = 0;
                    importe = nIva5;
                    item++;
                    asi_codigo = config.getIvacompra5();
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
                // GRABA EL HABER
                // EN CASO QUE LA FACTURA SEA CREDITO
                // GRABA EL HABER
                // EN CASO QUE TENGA UNA FINANCIACION
                totalpagos = 0;
                while (table_pagos.next()) {
                    double netocobrado = table_pagos.getDouble("netocobrado");
                    totalpagos = totalpagos + netocobrado;
                    double impdebe = 0;
                    double imphaber = 0;

                    double importe = 0;
                    impdebe = 0;
                    imphaber = Math.round(netocobrado * cotizacion);
                    importe = netocobrado;
                    item++;
                    asi_codigo = table_pagos.getString("idcuenta");
                    String banco = table_pagos.getString("banco");
                    String nrocheque = table_pagos.getString("nrocheque");
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

                if (totalneto - totalpagos > 0) {
                    double impdebe = 0;
                    double imphaber = 0;
                    double importe = 0;
                    impdebe = 0;
                    imphaber = Math.round((totalneto - totalpagos) * cotizacion);
                    importe = totalneto - totalpagos;
                    item++;
                    asi_codigo = "";
                    // busqueda en proveedores
                    sql = "SELECT idcta FROM proveedores WHERE codigo=" + proveedor;
                    ps = conn.prepareStatement(sql);
                    ResultSet proveedores = ps.executeQuery(sql);
                    proveedores.last();
                    asi_codigo = proveedores.getString("idcta");

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
                // Graba el haber
                // En caso que tenga pagos realizados
                // Actualiza cabecera de compras
                sql = "UPDATE cabecera_compras SET asiento=? WHERE creferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();
            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de compras - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        return valor;
    }

    public boolean generarNotaCreditoItem(String id) {
        System.out.println("Generando devoluciones de compras");
        boolean valor = true;
        config_contableDAO configDAO = new config_contableDAO();
        config_contable config = new config_contable();
        config = configDAO.consultar();

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Generando asientos de compras - Inicio");

        String cOrigen = "COMPRAS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas
            String sql = "SELECT * FROM cabecera_compras WHERE  creferencia='" + id + "'  ORDER BY creferencia";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet cabecera_compras = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            double totalneto = 0;
            double totalpagos = 0;
            while (cabecera_compras.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                int proveedor = 0;
                int asiento = cabecera_compras.getInt("asiento");
                int sucursal = cabecera_compras.getInt("sucursal");
                String creferencia = cabecera_compras.getString("creferencia");
                String moneda = cabecera_compras.getString("moneda");
                cotizacion = cabecera_compras.getDouble("cotizacion");
                nrofactura = cabecera_compras.getString("nrofactura");
                proveedor = cabecera_compras.getInt("proveedor");
                totalneto = cabecera_compras.getDouble("totalneto");
                observacion = cabecera_compras.getString("observacion");
                System.out.println("Fecha Compra " + cabecera_compras.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + cabecera_compras.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='" + cabecera_compras.getDate("fecha") + "',debe=" + Math.abs(totalneto) + ",haber=" + Math.abs(totalneto) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + cabecera_compras.getDate("fecha") + "'," + Math.abs(totalneto) + "," + Math.abs(totalneto) + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }

                //SE GENERA EL DEBE, EN ESTE CASO PRIMERO EL PROVEEDOR
                int item = 0;
                double impdebe = 0;
                double imphaber = 0;
                double importe = 0;
                impdebe = Math.abs(Math.round(totalneto * cotizacion));
                imphaber = 0;
                importe = totalneto;
                item++;
                // busqueda en proveedores
                sql = "SELECT idcta FROM proveedores WHERE codigo=" + proveedor;
                ps = conn.prepareStatement(sql);
                ResultSet proveedores = ps.executeQuery(sql);
                proveedores.last();
                String asi_codigo = proveedores.getString("idcta");

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

                // Table pagos
                sql = "SELECT idmovimiento,forma,banco,codmoneda,"
                        + " confirmacion,netocobrado,nrocheque,"
                        + " bancos.idcuenta "
                        + " FROM detalle_forma_pago "
                        + " LEFT JOIN bancos "
                        + " ON bancos.codigo=detalle_forma_pago.banco "
                        + " WHERE idmovimiento='" + creferencia + "'";
                ps = conn.prepareStatement(sql);
                ResultSet table_pagos = ps.executeQuery(sql);
                // Table datos
                sql = "SELECT * FROM detalle_compras WHERE dreferencia='" + creferencia + "'";
                ps = conn.prepareStatement(sql);
                ResultSet table_datos = ps.executeQuery(sql);
                nIva10 = 0;
                nIva5 = 0;
                while (table_datos.next()) {
                    if (table_datos.getInt("porcentaje") == 10) {
                        nIva10 += table_datos.getDouble("impiva");
                    } else if (table_datos.getInt("porcentaje") == 5) {
                        nIva5 += table_datos.getDouble("impiva");
                    }
                }
                // tmp_rubros ATENCION table_datos reemplazado por detalle_compras
                sql = "SELECT productos.rubro,rubros.idctamercaderia,SUM(monto-impiva) as sumarubro,productos.observacion "
                        + "FROM rubros,detalle_compras,productos "
                        + "WHERE rubros.codigo=productos.rubro "
                        + "AND productos.codigo=detalle_compras.codprod "
                        + "AND detalle_compras.dreferencia='" + creferencia + "' "
                        + "GROUP BY rubros.idctamercaderia";
                ps = conn.prepareStatement(sql);
                ResultSet tmp_rubros = ps.executeQuery(sql);
                item++;

                asi_codigo = "";
                while (tmp_rubros.next()) {
                    item++;
                    //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                    asi_codigo = "";
                    int rubro = tmp_rubros.getInt("rubro");
                    // busqueda en rubros
                    sql = "SELECT idctamercaderia FROM rubros WHERE codigo=" + rubro;
                    ps = conn.prepareStatement(sql);
                    ResultSet rubros = ps.executeQuery(sql);
                    if (rubros.next()) {
                        asi_codigo = rubros.getString("idctamercaderia");
                    }

                    double sumarubro = tmp_rubros.getDouble("sumarubro");
                    importe = sumarubro;
                    impdebe = 0;
                    imphaber = Math.abs(Math.round(sumarubro * cotizacion));
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
                // Iva 10
                if (nIva10 != 0) {
                    impdebe = 0;
                    imphaber = nIva10;
                    importe = 0;
                    impdebe = 0;
                    imphaber = Math.abs(Math.round(imphaber * cotizacion));
                    importe = nIva10;
                    item++;
                    asi_codigo = config.getIvacompra10();
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
                if (nIva5 != 0) {
                    impdebe = 0;
                    imphaber = nIva5;
                    importe = 0;
                    impdebe = 0;
                    imphaber = Math.abs(Math.round(impdebe * cotizacion));
                    importe = nIva5;
                    item++;
                    asi_codigo = config.getIvacompra5();
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

                // Graba el haber
                // En caso que tenga pagos realizados
                // Actualiza cabecera de compras
                sql = "UPDATE cabecera_compras SET asiento=? WHERE creferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();
            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de compras - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        return valor;
    }

    public boolean generarGastosCreditoItem(String id) throws SQLException {
        System.out.println("Generando asientos de gastos a pagar");
        boolean valor = true;
        config_contableDAO configDAO = new config_contableDAO();
        config_contable config = new config_contable();
        config = configDAO.consultar();

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Generando asientos de compras - Inicio");

        String cOrigen = "GASTOS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas
            String sql = "";
            if (Integer.valueOf(Config.nCentroCosto) == 0) {
                sql = "SELECT creferencia,nrofactura,sucursal,fecha,proveedor,"
                        + "concepto,exentas,gravadas10,gravadas5,iva10,iva5,totalneto,"
                        + "moneda,cotizacion,observacion,asiento,rubro_compras.idcta AS ctadebe,"
                        + "proveedores.idcta AS ctahaber "
                        + "FROM gastos_compras "
                        + "LEFT JOIN rubro_compras "
                        + "ON rubro_compras.codigo=gastos_compras.concepto "
                        + "LEFT JOIN proveedores "
                        + "ON proveedores.codigo=gastos_compras.proveedor "
                        + "WHERE totalneto>0 "
                        + "AND gastos_compras.fondofijo=0 "
                        + " AND creferencia='" + id + "' ORDER BY fecha";
            } else {
                sql = "SELECT creferencia,nrofactura,sucursal,fecha,proveedor,"
                        + "concepto,exentas,gravadas10,gravadas5,iva10,iva5,totalneto,"
                        + "moneda,cotizacion,observacion,asiento,gastos_compras.idcta AS ctadebe,"
                        + "proveedores.idcta AS ctahaber "
                        + "FROM gastos_compras "
                        + "LEFT JOIN proveedores "
                        + "ON proveedores.codigo=gastos_compras.proveedor "
                        + "WHERE totalneto>0 "
                        + "AND gastos_compras.fondofijo=0 "
                        + " AND creferencia='" + id + "' ORDER BY fecha";
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet gastos_credito = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            while (gastos_credito.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                int proveedor = 0;
                int asiento = gastos_credito.getInt("asiento");
                int sucursal = gastos_credito.getInt("sucursal");
                String creferencia = gastos_credito.getString("creferencia");
                String moneda = gastos_credito.getString("moneda");
                cotizacion = gastos_credito.getDouble("cotizacion");
                nrofactura = gastos_credito.getString("nrofactura");
                proveedor = gastos_credito.getInt("proveedor");
                double totalneto = gastos_credito.getDouble("totalneto");
                double exentas = gastos_credito.getDouble("exentas");
                double gravadas10 = gastos_credito.getDouble("gravadas10");
                double gravadas5 = gastos_credito.getDouble("gravadas5");
                double iva10 = gastos_credito.getDouble("iva10");
                double iva5 = gastos_credito.getDouble("iva5");
                observacion = gastos_credito.getString("observacion");
                String ctadebe = gastos_credito.getString("ctadebe");
                String ctahaber = gastos_credito.getString("ctahaber");
                System.out.println("Fecha Compra " + gastos_credito.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + gastos_credito.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='"
                            + gastos_credito.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + gastos_credito.getDate("fecha") + "'," + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }
                // Table pagos
                // Table datos
                int item = 1;
                String asi_codigo = "";
                //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                asi_codigo = ctadebe;

                double importe = Math.round(totalneto * cotizacion);
                totalneto = importe;

                if (iva10 > 0) {
                    iva10 = Math.round(totalneto / 11);
                }
                if (iva5 > 0) {
                    iva5 = Math.round(totalneto / 21);
                }
                double impdebe = Math.round(totalneto - (iva10 + iva5));
                double imphaber = 0;
                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setString(2, nrofactura);
                ps.setString(3, moneda);
                ps.setDouble(4, cotizacion);
                ps.setDouble(5, importe / cotizacion);
                ps.setString(6, asi_codigo);
                ps.setString(7, observacion);
                ps.setDouble(8, impdebe);
                ps.setDouble(9, imphaber);
                ps.setInt(10, item);
                ps.executeUpdate();
                // Iva 10
                if (iva10 != 0) {
                    impdebe = 0;
                    imphaber = 0;
                    importe = 0;
                    impdebe = iva10;
                    imphaber = 0;
                    importe = iva10 / cotizacion;
                    item++;
                    asi_codigo = config.getIvacompra10();
                    sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                            + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, asiento);
                    ps.setString(2, nrofactura);
                    ps.setString(3, moneda);
                    ps.setDouble(4, cotizacion);
                    ps.setDouble(5, iva10 / cotizacion);
                    ps.setString(6, asi_codigo);
                    ps.setString(7, observacion);
                    ps.setDouble(8, impdebe);
                    ps.setDouble(9, imphaber);
                    ps.setInt(10, item);
                    ps.executeUpdate();
                }
                // Iva 5
                if (iva5 != 0) {
                    impdebe = 0;
                    imphaber = 0;
                    importe = 0;
                    impdebe = iva5;
                    imphaber = 0;
                    importe = iva5 / cotizacion;
                    item++;
                    asi_codigo = config.getIvacompra5();
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
                // GRABA EL HABER
                // QUE AQUI SIEMPRE VA SER A CREDITO
                // GRABA EL HABER
                importe = 0;
                impdebe = 0;
                imphaber = totalneto;
                importe = totalneto / cotizacion;
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

                // Actualiza cabecera de compras
                sql = "UPDATE gastos_compras SET asiento=? WHERE creferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();
            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de compras - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }

}

class TableAsientosCompras {

    public String asi_codigo;
    public long impdebe;
    public long imphaber;
    public long factura;
    public int item;
}

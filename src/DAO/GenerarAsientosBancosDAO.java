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

public class GenerarAsientosBancosDAO {

    Conexion con = null;
    Statement st = null;



    public boolean generarDepositos(java.util.Date desde, java.util.Date hasta) throws SQLException {
        System.out.println("Generando asientos de Depósitos");
        boolean valor = true;

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de Depositos- Inicio");

        String cOrigen = "DEPOSITOS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas

            String sql = "SELECT extracciones.idmovimiento,extracciones.idcontrol,extracciones.fecha,extracciones.documento,"
                    + "extracciones.sucursal,extracciones.banco,extracciones.cotizacion,"
                    + "extracciones.importe,extracciones.observaciones,extracciones.idcta AS ctahaber,"
                    + "extracciones.asiento,bancos.idcuenta AS ctadebe,extracciones.moneda "
                    + "FROM extracciones "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=extracciones.banco "
                    + "WHERE extracciones.tipo='C' "
                    + " AND idcta<>'' "
                    + " AND extracciones.fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "' ORDER BY fecha";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet depositos = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            while (depositos.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                int asiento = depositos.getInt("asiento");
                int sucursal = depositos.getInt("sucursal");
                String creferencia = depositos.getString("idmovimiento");
                String moneda = depositos.getString("moneda");
                cotizacion = depositos.getDouble("cotizacion");
                nrofactura = depositos.getString("documento");
                double totalneto = depositos.getDouble("importe");
                observacion = depositos.getString("observaciones");
                String ctadebe = depositos.getString("ctadebe");
                String ctahaber = depositos.getString("ctahaber");
                System.out.println("Fecha Depósito " + depositos.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + depositos.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='"
                            + depositos.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + depositos.getDate("fecha") + "'," + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
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
                //GRABA EL DEBE
                double importe = Math.round(totalneto * cotizacion);
                double impdebe = Math.round(totalneto * cotizacion);
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
                imphaber = Math.round(totalneto * cotizacion);
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

                // Actualiza cabecera de compras
                sql = "UPDATE extracciones SET asiento=? WHERE idmovimiento=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();
            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de depositos- Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }

    public boolean generarExtracciones(java.util.Date desde, java.util.Date hasta) throws SQLException {
        System.out.println("Generando asientos de Depósitos");
        boolean valor = true;

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de Extracciones- Inicio");

        String cOrigen = "EXTRACCIONES";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas

            String sql = "SELECT extracciones.idmovimiento,extracciones.idcontrol,extracciones.fecha,extracciones.documento,"
                    + "extracciones.sucursal,extracciones.banco,extracciones.cotizacion,"
                    + "extracciones.importe,extracciones.observaciones,extracciones.idcta AS ctadebe,"
                    + "extracciones.asiento,bancos.idcuenta AS ctahaber,extracciones.moneda "
                    + "FROM extracciones "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=extracciones.banco "
                    + "WHERE extracciones.tipo='D' "
                    + " AND idcta<>'' "
                    + " AND extracciones.fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "' ORDER BY fecha";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet extracciones = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            while (extracciones.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                int asiento = extracciones.getInt("asiento");
                int sucursal = extracciones.getInt("sucursal");
                String creferencia = extracciones.getString("idmovimiento");
                String moneda = extracciones.getString("moneda");
                cotizacion = extracciones.getDouble("cotizacion");
                nrofactura = extracciones.getString("documento");
                double totalneto = extracciones.getDouble("importe");
                observacion = extracciones.getString("observaciones");
                String ctadebe = extracciones.getString("ctadebe");
                String ctahaber = extracciones.getString("ctahaber");
                System.out.println("Fecha Depósito " + extracciones.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + extracciones.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='"
                            + extracciones.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + extracciones.getDate("fecha") + "'," + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
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
                //GRABA EL DEBE
                double importe = Math.round(totalneto * cotizacion);
                double impdebe = Math.round(totalneto * cotizacion);
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
                imphaber = Math.round(totalneto * cotizacion);
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

                // Actualiza cabecera de compras
                sql = "UPDATE extracciones SET asiento=? WHERE idmovimiento=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();
            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de extracciones- Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }

    public boolean generarReposicion(java.util.Date desde, java.util.Date hasta) throws SQLException {
        System.out.println("Generando asientos de Reposición");
        boolean valor = true;

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de Reposición- Inicio");

        String cOrigen = "REPOSICION";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas

            String sql = "SELECT numero,creferencia,fecha,sucursal,origenfondo,importe,moneda,"
                    + "nrocheque,cotizacion,asiento,"
                    + "destinofondo,asiento,observacion,"
                    + "bancos.idcuenta AS ctahaber,"
                    + "ba.ctadebe AS ctadebe "
                    + " FROM reposicion_fondo_fijo "
                    + " LEFT JOIN bancos "
                    + " ON bancos.codigo=reposicion_fondo_fijo.origenfondo "
                    + " LEFT JOIN (SELECT bancos.codigo, bancos.idcuenta AS ctadebe "
                    + " FROM bancos) ba ON ba.codigo=reposicion_fondo_fijo.destinofondo "
                    + " WHERE reposicion_fondo_fijo.fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "' ORDER BY fecha";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet extracciones = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            while (extracciones.next()) {
                nContador++;
                double cotizacion = 0;
                double nrofactura = 0;
                String observacion = "";
                int asiento = extracciones.getInt("asiento");
                int sucursal = extracciones.getInt("sucursal");
                String creferencia = extracciones.getString("creferencia");
                String moneda = extracciones.getString("moneda");
                cotizacion = extracciones.getDouble("cotizacion");
                nrofactura = extracciones.getDouble("numero");
                double totalneto = extracciones.getDouble("importe");
                observacion = extracciones.getString("observacion");
                String ctadebe = extracciones.getString("ctadebe");
                String ctahaber = extracciones.getString("ctahaber");
                System.out.println("Fecha Reposicion " + extracciones.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + extracciones.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='"
                            + extracciones.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + extracciones.getDate("fecha") + "'," + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
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
                asi_codigo = ctadebe;
                //GRABA EL DEBE
                double importe = Math.round(totalneto * cotizacion);
                double impdebe = Math.round(totalneto * cotizacion);
                double imphaber = 0;
                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setDouble(2, nrofactura);
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
                imphaber = Math.round(totalneto * cotizacion);
                importe = totalneto;
                item++;
                asi_codigo = ctahaber;
                // busqueda en proveedores

                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setDouble(2, nrofactura);
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
                sql = "UPDATE reposicion_fondo_fijo SET asiento=? WHERE creferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();
            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de Reposición- Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }

    public boolean generarRendicionFondoFijo(java.util.Date desde, java.util.Date hasta) {
        boolean valor = true;
        int nIva5 = 0, nIva10 = 0, nProveedor = 0, nTotales = 0, nItems = 0, nContador = 0, nAsiento = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de Rendición de Fondo Fijo");
        String cOrigen = "FONDO FIJO";

        config_contableDAO configDAO = new config_contableDAO();
        config_contable config = new config_contable();
        config = configDAO.consultar();

        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Borrar asientos cabecera
            String sql = "";

            // TmpDEBE

            sql = "SELECT numero,creferencia,fecha,sucursal,banco,importe,nrocheque,"
                    + "observacion,asiento,bancos.idcuenta as ctahaber,cotizacion,moneda "
                    + " FROM reposicion_fondos "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=reposicion_fondos.banco "
                    + " WHERE fecha>='" + fDesde + "' AND fecha<='" + fHasta + "'"
                    + " ORDER BY fecha";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet CabFondoFijo = ps.executeQuery(sql);

            while (CabFondoFijo.next()) {
                ArrayList<TableAsientos> tablaAsientos = new ArrayList();
                nContador++;
                int item = 1;
                double cotizacion = 0;
                double numero;
                double totalhaber = 0;
                String observacion = "";
                int asiento = CabFondoFijo.getInt("asiento");
                int sucursal = CabFondoFijo.getInt("sucursal");
                String idpagos = CabFondoFijo.getString("creferencia");
                String moneda = CabFondoFijo.getString("moneda");
                cotizacion = CabFondoFijo.getDouble("cotizacion");
                numero = CabFondoFijo.getDouble("numero");
                String cCheque=CabFondoFijo.getString("nrocheque");
                long importe = CabFondoFijo.getLong("importe");
                totalhaber = Math.round(importe * cotizacion);
                observacion = CabFondoFijo.getString("observacion");
                String ctahaber = CabFondoFijo.getString("ctahaber");
                System.out.println("Fecha Rendición " + CabFondoFijo.getDate("fecha"));

                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='"
                            + CabFondoFijo.getDate("fecha") + "',debe=" + Math.round(importe * cotizacion) + ",haber="
                            + Math.round(importe * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + CabFondoFijo.getDate("fecha") + "'," + Math.round(importe * cotizacion) + "," + Math.round(importe * cotizacion) + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }

                if (Integer.valueOf(Config.nCentroCosto) == 0) {
                    sql = "SELECT creferencia,nrofactura,sucursal,fecha,proveedor,"
                            + "concepto,exentas,gravadas10,gravadas5,iva10,iva5,totalneto,"
                            + "moneda,cotizacion,observacion,asiento,rubro_compras.idcta AS ctadebe "
                            + "FROM gastos_compras "
                            + "LEFT JOIN rubro_compras "
                            + "ON rubro_compras.codigo=gastos_compras.concepto "
                            + "WHERE totalneto>0 "
                            + "AND gastos_compras.fondofijo=" + numero;
                } else {
                    sql = "SELECT creferencia,nrofactura,sucursal,fecha,proveedor,"
                            + "concepto,exentas,gravadas10,gravadas5,iva10,iva5,totalneto,"
                            + "moneda,cotizacion,observacion,asiento,gastos_compras.idcta AS ctadebe "
                            + " FROM gastos_compras "
                            + " WHERE totalneto>0 "
                            + " AND gastos_compras.fondofijo=" + numero;
                }

                System.out.println(sql);

                PreparedStatement psd = conn.prepareStatement(sql);
                ResultSet gastos_credito = psd.executeQuery(sql);
                // configuracion
                // Generar asientos de compras

                while (gastos_credito.next()) {
                    nContador++;
                    String nrofactura = "";
                    int proveedor = 0;
                    String creferencia = gastos_credito.getString("creferencia");
                    moneda = gastos_credito.getString("moneda");
                    cotizacion = gastos_credito.getDouble("cotizacion");
                    nrofactura = gastos_credito.getString("nrofactura");
                    proveedor = gastos_credito.getInt("proveedor");
                    double totalneto = gastos_credito.getDouble("totalneto");
                    double exentas = gastos_credito.getDouble("exentas");
                    double gravadas10 = gastos_credito.getDouble("gravadas10");
                    double gravadas5 = gastos_credito.getDouble("gravadas5");
                    double iva10 = gastos_credito.getDouble("iva10");
                    double iva5 = gastos_credito.getDouble("iva5");
                    String observaciondetalle = gastos_credito.getString("observacion");
                    if(observaciondetalle.isEmpty()){
                        observaciondetalle="Rendición de Gastos N° "+cCheque;
                    }
                    String ctadebe = gastos_credito.getString("ctadebe");
                    System.out.println("Fecha Compra " + gastos_credito.getDate("fecha"));
                    // Table pagos
                    // Table datos
                    String asi_codigo = "";
                    asi_codigo = ctadebe;

                    double importedetalle = totalneto - (iva10 + iva5);
                    double impdebe = Math.round(totalneto - (iva10 + iva5) * cotizacion);
                    double imphaber = 0;
                    sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                            + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, asiento);
                    ps.setString(2, nrofactura);
                    ps.setString(3, moneda);
                    ps.setDouble(4, cotizacion);
                    ps.setDouble(5, importedetalle);
                    ps.setString(6, asi_codigo);
                    ps.setString(7, observaciondetalle);
                    ps.setDouble(8, impdebe);
                    ps.setDouble(9, imphaber);
                    ps.setInt(10, item);
                    ps.executeUpdate();
                    // Iva 10
                    if (iva10 != 0) {
                        impdebe = 0;
                        imphaber = 0;
                        importe = 0;
                        impdebe = nIva10;
                        impdebe = Math.round(iva10 * cotizacion);
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
                        ps.setString(7, observaciondetalle);
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
                        impdebe = Math.round(iva5 * cotizacion);
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
                        ps.setString(7, observaciondetalle);
                        ps.setDouble(8, impdebe);
                        ps.setDouble(9, imphaber);
                        ps.setInt(10, item);
                        ps.executeUpdate();
                    }

                }
                // GRABA EL HABER
                // QUE AQUI SIEMPRE VA SER A CREDITO
                // GRABA EL HABER
                double importedetalle = 0;
                double impdebe = 0;
                double imphaber = Math.round(importe * cotizacion);
                importedetalle = importe;
                item++;
                String asi_codigo = ctahaber;
                // busqueda en proveedores

                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setString(2, cCheque);
                ps.setString(3, moneda);
                ps.setDouble(4, cotizacion);
                ps.setDouble(5, importe);
                ps.setString(6, asi_codigo);
                ps.setString(7, observacion);
                ps.setDouble(8, impdebe);
                ps.setDouble(9, totalhaber);
                ps.setInt(10, item);
                ps.executeUpdate();

                // Actualiza cabecera de compras
                sql = "UPDATE reposicion_fondos SET asiento=? WHERE numero=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setDouble(2, numero);
                ps.executeUpdate();

            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de Rendición de Fondo Fijo - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return valor;
    }

    
    
        public boolean generarDepositosItem(String id) throws SQLException {
        System.out.println("Generando asientos de Depósitos");
        boolean valor = true;

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Generando asientos de Depositos- Inicio");

        String cOrigen = "DEPOSITOS";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas

            String sql = "SELECT extracciones.idmovimiento,extracciones.idcontrol,extracciones.fecha,extracciones.documento,"
                    + "extracciones.sucursal,extracciones.banco,extracciones.cotizacion,"
                    + "extracciones.importe,extracciones.observaciones,extracciones.idcta AS ctahaber,"
                    + "extracciones.asiento,bancos.idcuenta AS ctadebe,extracciones.moneda "
                    + "FROM extracciones "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=extracciones.banco "
                    + "WHERE extracciones.tipo='C' "
                    + " AND idcta<>'' "
                    + " AND extracciones.idmovimiento='" + id + "' ORDER BY fecha";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet depositos = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            while (depositos.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                int asiento = depositos.getInt("asiento");
                int sucursal = depositos.getInt("sucursal");
                String creferencia = depositos.getString("idmovimiento");
                String moneda = depositos.getString("moneda");
                cotizacion = depositos.getDouble("cotizacion");
                nrofactura = depositos.getString("documento");
                double totalneto = depositos.getDouble("importe");
                observacion = depositos.getString("observaciones");
                String ctadebe = depositos.getString("ctadebe");
                String ctahaber = depositos.getString("ctahaber");
                System.out.println("Fecha Depósito " + depositos.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + depositos.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='"
                            + depositos.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + depositos.getDate("fecha") + "'," + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
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
                //GRABA EL DEBE
                double importe = totalneto;
                double impdebe = Math.round(totalneto * cotizacion);
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
                imphaber = Math.round(totalneto * cotizacion);
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

                // Actualiza cabecera de compras
                sql = "UPDATE extracciones SET asiento=? WHERE idmovimiento=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, id);
                ps.executeUpdate();
            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de depositos- Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }

        
    public boolean generarExtraccionesItem(String id) throws SQLException {
        System.out.println("Generando asientos de Extracciones");
        boolean valor = true;

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Generando asientos de Extracciones- Inicio");

        String cOrigen = "EXTRACCIONES";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas

            String sql = "SELECT extracciones.idmovimiento,extracciones.idcontrol,extracciones.fecha,extracciones.documento,"
                    + "extracciones.sucursal,extracciones.banco,extracciones.cotizacion,"
                    + "extracciones.importe,extracciones.observaciones,extracciones.idcta AS ctadebe,"
                    + "extracciones.asiento,bancos.idcuenta AS ctahaber,extracciones.moneda "
                    + "FROM extracciones "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=extracciones.banco "
                    + "WHERE extracciones.tipo='D' "
                    + " AND idcta<>'' "
                    + " AND extracciones.idmovimiento='" + id + "'  ORDER BY fecha";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet extracciones = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            while (extracciones.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                int asiento = extracciones.getInt("asiento");
                int sucursal = extracciones.getInt("sucursal");
                String creferencia = extracciones.getString("idmovimiento");
                String moneda = extracciones.getString("moneda");
                cotizacion = extracciones.getDouble("cotizacion");
                nrofactura = extracciones.getString("documento");
                double totalneto = extracciones.getDouble("importe");
                observacion = extracciones.getString("observaciones");
                String ctadebe = extracciones.getString("ctadebe");
                String ctahaber = extracciones.getString("ctahaber");
                System.out.println("Fecha Depósito " + extracciones.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + extracciones.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='"
                            + extracciones.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + extracciones.getDate("fecha") + "'," + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
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
                //GRABA EL DEBE
                double importe = totalneto;
                double impdebe = Math.round(totalneto * cotizacion);
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
                imphaber = Math.round(totalneto * cotizacion);
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

                // Actualiza cabecera de compras
                sql = "UPDATE extracciones SET asiento=? WHERE idmovimiento=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, id);
                ps.executeUpdate();
            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de extracciones- Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }
        


    public boolean generarReposicionItem(String id) throws SQLException {
        System.out.println("Generando asientos de Reposición");
        boolean valor = true;

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Generando asientos de Reposición- Inicio");

        String cOrigen = "REPOSICION";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas

            String sql = "SELECT numero,creferencia,fecha,sucursal,origenfondo,importe,moneda,"
                    + "nrocheque,cotizacion,asiento,"
                    + "destinofondo,asiento,observacion,"
                    + "bancos.idcuenta AS ctahaber,"
                    + "ba.ctadebe AS ctadebe "
                    + " FROM reposicion_fondo_fijo "
                    + " LEFT JOIN bancos "
                    + " ON bancos.codigo=reposicion_fondo_fijo.origenfondo "
                    + " LEFT JOIN (SELECT bancos.codigo, bancos.idcuenta AS ctadebe "
                    + " FROM bancos) ba ON ba.codigo=reposicion_fondo_fijo.destinofondo "
                    + " WHERE reposicion_fondo_fijo.creferencia='" + id + "' ORDER BY fecha";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet extracciones = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            while (extracciones.next()) {
                nContador++;
                double cotizacion = 0;
                double nrofactura = 0;
                String observacion = "";
                int asiento = extracciones.getInt("asiento");
                int sucursal = extracciones.getInt("sucursal");
                String creferencia = extracciones.getString("creferencia");
                String moneda = extracciones.getString("moneda");
                cotizacion = extracciones.getDouble("cotizacion");
                nrofactura = extracciones.getDouble("numero");
                double totalneto = extracciones.getDouble("importe");
                observacion = extracciones.getString("observacion");
                String ctadebe = extracciones.getString("ctadebe");
                String ctahaber = extracciones.getString("ctahaber");
                System.out.println("Fecha Reposicion " + extracciones.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + extracciones.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='"
                            + extracciones.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + extracciones.getDate("fecha") + "'," + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
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
                asi_codigo = ctadebe;
                //GRABA EL DEBE
                double importe = Math.round(totalneto * cotizacion);
                double impdebe = Math.round(totalneto * cotizacion);
                double imphaber = 0;
                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setDouble(2, nrofactura);
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
                imphaber = Math.round(totalneto * cotizacion);
                importe = totalneto;
                item++;
                asi_codigo = ctahaber;
                // busqueda en proveedores

                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setDouble(2, nrofactura);
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
                sql = "UPDATE reposicion_fondo_fijo SET asiento=? WHERE creferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();
            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de Reposición- Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }



    public boolean generarRendicionFondoFijoItem(String id) {
        boolean valor = true;
        int nIva5 = 0, nIva10 = 0, nProveedor = 0, nTotales = 0, nItems = 0, nContador = 0, nAsiento = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Generando asientos de Rendición de Fondo Fijo");
        String cOrigen = "FONDO FIJO";

        config_contableDAO configDAO = new config_contableDAO();
        config_contable config = new config_contable();
        config = configDAO.consultar();

        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Borrar asientos cabecera
            String sql = "";

            // TmpDEBE
            sql = "SELECT numero,creferencia,fecha,sucursal,banco,importe,nrocheque,"
                    + "observacion,asiento,bancos.idcuenta as ctahaber,cotizacion,moneda "
                    + " FROM reposicion_fondos "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=reposicion_fondos.banco "
                    + " WHERE creferencia='" + id + "' ORDER BY fecha";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet CabFondoFijo = ps.executeQuery(sql);

            while (CabFondoFijo.next()) {
                ArrayList<TableAsientos> tablaAsientos = new ArrayList();
                nContador++;
                int item = 1;
                double cotizacion = 0;
                double numero;
                double totalhaber = 0;
                String observacion = "";
                int asiento = CabFondoFijo.getInt("asiento");
                int sucursal = CabFondoFijo.getInt("sucursal");
                String idpagos = CabFondoFijo.getString("creferencia");
                String moneda = CabFondoFijo.getString("moneda");
                cotizacion = CabFondoFijo.getDouble("cotizacion");
                numero = CabFondoFijo.getDouble("numero");
                String cCheque=CabFondoFijo.getString("nrocheque");
                long importe = CabFondoFijo.getLong("importe");
                totalhaber = Math.round(importe * cotizacion);
                observacion = CabFondoFijo.getString("observacion");
                String ctahaber = CabFondoFijo.getString("ctahaber");
                System.out.println("Fecha Rendición " + CabFondoFijo.getDate("fecha"));

                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='"
                            + CabFondoFijo.getDate("fecha") + "',debe=" + Math.round(importe * cotizacion) + ",haber="
                            + Math.round(importe * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + CabFondoFijo.getDate("fecha") + "'," + Math.round(importe * cotizacion) + "," + Math.round(importe * cotizacion) + ",0,'" + cOrigen + "')";
                    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = st.getGeneratedKeys();
                    if (keyset.next()) {
                        asiento = keyset.getInt(1);
                    }
                    System.out.println("Asiento Nuevo asiento=" + asiento);
                }

                if (Integer.valueOf(Config.nCentroCosto) == 0) {
                    sql = "SELECT creferencia,nrofactura,sucursal,fecha,proveedor,"
                            + "concepto,exentas,gravadas10,gravadas5,iva10,iva5,totalneto,"
                            + "moneda,cotizacion,observacion,asiento,rubro_compras.idcta AS ctadebe "
                            + "FROM gastos_compras "
                            + "LEFT JOIN rubro_compras "
                            + "ON rubro_compras.codigo=gastos_compras.concepto "
                            + "WHERE totalneto>0 "
                            + "AND gastos_compras.fondofijo=" + numero;
                } else {
                    sql = "SELECT creferencia,nrofactura,sucursal,fecha,proveedor,"
                            + "concepto,exentas,gravadas10,gravadas5,iva10,iva5,totalneto,"
                            + "moneda,cotizacion,observacion,asiento,gastos_compras.idcta AS ctadebe "
                            + " FROM gastos_compras "
                            + " WHERE totalneto>0 "
                            + " AND gastos_compras.fondofijo=" + numero;
                }

                System.out.println(sql);

                PreparedStatement psd = conn.prepareStatement(sql);
                ResultSet gastos_credito = psd.executeQuery(sql);
                // configuracion
                // Generar asientos de compras

                while (gastos_credito.next()) {
                    nContador++;
                    String nrofactura = "";
                    int proveedor = 0;
                    String creferencia = gastos_credito.getString("creferencia");
                    moneda = gastos_credito.getString("moneda");
                    cotizacion = gastos_credito.getDouble("cotizacion");
                    nrofactura = gastos_credito.getString("nrofactura");
                    proveedor = gastos_credito.getInt("proveedor");
                    double totalneto = gastos_credito.getDouble("totalneto");
                    double exentas = gastos_credito.getDouble("exentas");
                    double gravadas10 = gastos_credito.getDouble("gravadas10");
                    double gravadas5 = gastos_credito.getDouble("gravadas5");
                    double iva10 = gastos_credito.getDouble("iva10");
                    double iva5 = gastos_credito.getDouble("iva5");
                    String observaciondetalle = gastos_credito.getString("observacion");
                    String ctadebe = gastos_credito.getString("ctadebe");
                    System.out.println("Fecha Compra " + gastos_credito.getDate("fecha"));
                    // Table pagos
                    // Table datos
                    String asi_codigo = "";
                    asi_codigo = ctadebe;

                    double importedetalle = totalneto - (iva10 + iva5);
                    double impdebe = Math.round(totalneto - (iva10 + iva5) * cotizacion);
                    double imphaber = 0;
                    sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                            + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, asiento);
                    ps.setString(2, nrofactura);
                    ps.setString(3, moneda);
                    ps.setDouble(4, cotizacion);
                    ps.setDouble(5, importedetalle);
                    ps.setString(6, asi_codigo);
                    ps.setString(7, observaciondetalle);
                    ps.setDouble(8, impdebe);
                    ps.setDouble(9, imphaber);
                    ps.setInt(10, item);
                    ps.executeUpdate();
                    // Iva 10
                    if (iva10 != 0) {
                        impdebe = 0;
                        imphaber = 0;
                        importe = 0;
                        impdebe = nIva10;
                        impdebe = Math.round(iva10 * cotizacion);
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
                        ps.setString(7, observaciondetalle);
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
                        impdebe = Math.round(iva5 * cotizacion);
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
                        ps.setString(7, observaciondetalle);
                        ps.setDouble(8, impdebe);
                        ps.setDouble(9, imphaber);
                        ps.setInt(10, item);
                        ps.executeUpdate();
                    }

                }
                // GRABA EL HABER
                // QUE AQUI SIEMPRE VA SER A CREDITO
                // GRABA EL HABER
                double importedetalle = 0;
                double impdebe = 0;
                double imphaber = Math.round(importe * cotizacion);
                importedetalle = importe;
                item++;
                String asi_codigo = ctahaber;
                // busqueda en proveedores

                sql = "INSERT INTO detalle_asientos(asi_asient,asi_numero,moneda,cotizacion,importe,asi_codigo,asi_descri,impdebe,imphaber,item) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, asiento);
                ps.setString(2, cCheque);
                ps.setString(3, moneda);
                ps.setDouble(4, cotizacion);
                ps.setDouble(5, importe);
                ps.setString(6, asi_codigo);
                ps.setString(7, observacion);
                ps.setDouble(8, impdebe);
                ps.setDouble(9, totalhaber);
                ps.setInt(10, item);
                ps.executeUpdate();

                // Actualiza cabecera de compras
                sql = "UPDATE reposicion_fondos SET asiento=? WHERE numero=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setDouble(2, numero);
                ps.executeUpdate();

            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de Rendición de Fondo Fijo - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return valor;
    }


    
    
}

class TableAsientosBancos {

    public String asi_codigo;
    public long impdebe;
    public long imphaber;
    public long factura;
    public int item;
}

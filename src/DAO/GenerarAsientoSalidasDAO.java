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

public class GenerarAsientoSalidasDAO {

    Conexion con = null;
    Statement st = null;

    public boolean generarSalida(java.util.Date desde, java.util.Date hasta) throws SQLException {
        System.out.println("Generando asientos de Salidas");
        boolean valor = true;
        config_contableDAO configDAO = new config_contableDAO();
        config_contable config = new config_contable();
        config = configDAO.consultar();
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de SALIDAS - Inicio");

        String cOrigen = "SALIDA";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas

            String sql = "SELECT idreferencia,numero,fecha,sucursal,asiento,1 AS moneda,"
                    + "1 AS cotizacion,"
                    + "SUM(detalle_salida_mercaderias.cantidad*detalle_salida_mercaderias.costo) AS totalneto "
                    + " FROM cabecera_salida_mercaderias "
                    + " LEFT JOIN detalle_salida_mercaderias "
                    + " ON detalle_salida_mercaderias.dreferencia=cabecera_salida_mercaderias.idreferencia "
                    + " WHERE  fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "'"
                    + " GROUP BY numero "
                    + " ORDER BY numero ";

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
                String observacion = "SALIDA DE MERCADERIAS";
                int asiento = cabecera_compras.getInt("asiento");
                int sucursal = cabecera_compras.getInt("sucursal");
                String creferencia = cabecera_compras.getString("idreferencia");
                String moneda = cabecera_compras.getString("moneda");
                cotizacion = cabecera_compras.getDouble("cotizacion");
                nrofactura = cabecera_compras.getString("numero");
                totalneto = cabecera_compras.getDouble("totalneto");

                System.out.println("Fecha Salida " + cabecera_compras.getDate("fecha"));
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

                String sqldebe = "SELECT cabecera_salida_mercaderias.numero,cabecera_salida_mercaderias.tipo,cabecera_salida_mercaderias.fecha,"
                        + "cabecera_salida_mercaderias.sucursal,cabecera_salida_mercaderias.asiento,"
                        + "comprobantes.idcta AS ctadebe,detalle_salida_mercaderias.cantidad,detalle_salida_mercaderias.costo,"
                        + "SUM(detalle_salida_mercaderias.cantidad*detalle_salida_mercaderias.costo) AS totalitem "
                        + " FROM  cabecera_salida_mercaderias "
                        + " LEFT JOIN detalle_salida_mercaderias "
                        + " ON detalle_salida_mercaderias.dreferencia=cabecera_salida_mercaderias.idreferencia "
                        + " LEFT JOIN comprobantes "
                        + " ON comprobantes.codigo=cabecera_salida_mercaderias.tipo "
                        + " WHERE idreferencia='" + creferencia + "'"
                        + " GROUP BY comprobantes.idcta";

                int item = 1;
                ps = conn.prepareStatement(sqldebe);
                ResultSet tmpdebe = ps.executeQuery(sqldebe);
                String asi_codigo = "";
                while (tmpdebe.next()) {
                    item++;
                    //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                    asi_codigo = "";
                    // busqueda en rubros
                    asi_codigo = tmpdebe.getString("ctadebe");

                    double sumarubro = tmpdebe.getDouble("totalitem");
                    double importe = sumarubro;
                    double impdebe = Math.round(importe * cotizacion);;
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

                String sqlhaber = "SELECT cabecera_salida_mercaderias.numero,cabecera_salida_mercaderias.tipo,cabecera_salida_mercaderias.fecha,"
                        + "cabecera_salida_mercaderias.sucursal,cabecera_salida_mercaderias.asiento,"
                        + "rubros.idctamercaderia AS ctahaber,detalle_salida_mercaderias.cantidad,detalle_salida_mercaderias.costo,"
                        + "SUM(detalle_salida_mercaderias.cantidad*detalle_salida_mercaderias.costo) AS totalitem "
                        + " FROM  cabecera_salida_mercaderias "
                        + " LEFT JOIN detalle_salida_mercaderias "
                        + " ON detalle_salida_mercaderias.dreferencia=cabecera_salida_mercaderias.idreferencia "
                        + " LEFT JOIN productos "
                        + " ON productos.codigo=detalle_salida_mercaderias.producto "
                        + " LEFT JOIN rubros "
                        + " ON rubros.codigo=productos.rubro "
                        + " WHERE idreferencia='" + creferencia + "'"
                        + " GROUP BY rubros.idctamercaderia";

                ps = conn.prepareStatement(sqlhaber);
                ResultSet tmphaber = ps.executeQuery(sqlhaber);
                asi_codigo = "";
                while (tmphaber.next()) {
                    item++;
                    //asi_codigo= IIF(SEEK(TmpRubros.rubro,'RUBROS'),RUBROS.idctamercaderia,'')
                    asi_codigo = "";
                    // busqueda en rubros
                    asi_codigo = tmphaber.getString("ctahaber");

                    double sumarubro = tmphaber.getDouble("totalitem");
                    double importe = sumarubro;
                    double impdebe = 0;
                    double imphaber = Math.round(importe * cotizacion);
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

                // 
                sql = "UPDATE cabecera_salida_mercaderias SET asiento=? WHERE idreferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();

            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de SALIDAS DE AJUSTE - Fin");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }
}

class TableAsientosSalidas {

    public String asi_codigo;
    public long impdebe;
    public long imphaber;
    public long factura;
    public int item;
}

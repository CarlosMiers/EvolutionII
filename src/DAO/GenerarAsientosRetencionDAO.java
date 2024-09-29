/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.config_contable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 *
 * @author Pc_Server
 */
public class GenerarAsientosRetencionDAO {

    Conexion con = null;
    Statement st = null;

    public boolean generarRetencionVenta(java.util.Date desde, java.util.Date hasta) throws SQLException {
        System.out.println("Generando asientos de Retención de Ventas");
        boolean valor = true;
        config_contableDAO configDAO = new config_contableDAO();
        config_contable config = new config_contable();
        config = configDAO.consultar();

        String CONFIG_CONTABLE_ventacontado = config.getVentacontado();
        String CONFIG_CONTABLE_ventacredito = config.getVentacredito();
        String CONFIG_CONTABLE_retencion = config.getRetencionventa();

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);
        System.out.println("Generando asientos de retenciones - Inicio");

        String cOrigen = "RETVENTA";
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas
            String sql = "";
            sql = "SELECT creferencia,nrofactura,sucursal,cliente,totalneto,moneda,"
                    + "cotizacion,valor_retencion,fecha,"
                    + "observacion,asiento,enviarcta "
                    + " FROM cabecera_retenciones_ventas "
                    + " WHERE fecha BETWEEN'" + fDesde + "' AND '" + fHasta + "' ORDER BY fecha";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet retenciones = ps.executeQuery(sql);
            // configuracion
            // Generar asientos de compras
            while (retenciones.next()) {
                nContador++;
                double cotizacion = 0;
                String nrofactura = "";
                String observacion = "";
                int asiento = retenciones.getInt("asiento");
                int sucursal = retenciones.getInt("sucursal");
                String creferencia = retenciones.getString("creferencia");
                String moneda = retenciones.getString("moneda");
                cotizacion = retenciones.getDouble("cotizacion");
                nrofactura = retenciones.getString("nrofactura");
                double totalneto = retenciones.getDouble("valor_retencion");
                observacion = retenciones.getString("observacion");
                int enviarcta = retenciones.getInt("enviarcta");
                System.out.println("Fecha Compra " + retenciones.getDate("fecha"));
                if (asiento != 0) {
                    // Borrar cabecera
                    sql = "DELETE FROM detalle_asientos WHERE asi_asient=?";
                    ps = conn.prepareStatement(sql);
                    ps.setDouble(1, asiento);
                    ps.executeUpdate();
                    // Agregar cabecera
                    System.out.println("Fechas " + retenciones.getDate("fecha"));
                    sql = "UPDATE cabecera_asientos SET sucursal = " + sucursal + ",fecha='"
                            + retenciones.getDate("fecha") + "',debe=" + Math.round(totalneto * cotizacion) + ",haber="
                            + Math.round(totalneto * cotizacion) + ",saldo=0" + ",grabado='" + cOrigen + "'" + " WHERE numero=" + asiento;
                    st.executeUpdate(sql);
                    System.out.println("Asiento existente asi_asient=" + asiento);
                } else {
                    // Agregar cabecera
                    sql = "INSERT INTO cabecera_asientos(sucursal,fecha,debe,haber,saldo,grabado) "
                            + "VALUES (" + sucursal + ",'" + retenciones.getDate("fecha") + "'," + Math.round(totalneto * cotizacion) + "," + Math.round(totalneto * cotizacion) + ",0,'" + cOrigen + "')";
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
                asi_codigo = CONFIG_CONTABLE_retencion;

                double importe = Math.round(totalneto * cotizacion);
                totalneto = importe;
                double impdebe = Math.round(totalneto);
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
                // GRABA EL HABER
                // QUE AQUI SIEMPRE VA SER A CREDITO
                // GRABA EL HABER
                importe = 0;
                impdebe = 0;
                imphaber = totalneto;
                importe = totalneto / cotizacion;
                item++;
                if (enviarcta == 1) {
                    asi_codigo = CONFIG_CONTABLE_ventacredito;
                } else {
                    asi_codigo = CONFIG_CONTABLE_ventacontado;
                }
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
                sql = "UPDATE cabecera_retenciones_ventas SET asiento=? WHERE creferencia=?";
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, asiento);
                ps.setString(2, creferencia);
                ps.executeUpdate();
            }
            st.close();
            ps.close();
            System.out.println("Generando asientos de Retenciones de Ventas - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }
}

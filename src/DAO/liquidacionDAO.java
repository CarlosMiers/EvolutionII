/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.UUID;
import Conexion.Conexion;
import Modelo.emisor;
import Modelo.liquidacion;
import Modelo.cliente;
import Modelo.configuracion;
import java.sql.Date;
import Modelo.moneda;
import Modelo.precierre_operacion;
import Modelo.producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Fais
 */
public class liquidacionDAO {

    String idtransaccion;
    String idliquidacion;
    double numerobolsa;
    String fecha;
    int emisor;
    int renta;
    int mercado;
    int comprador;
    int vendedor;
    int moneda;
    int tipo; // INDICA 1 SI ES COMPRADOR 2 SI ES VENDEDOR
    double iva;
    double limitecomision;
    String concepto;
    double cantidad;
    double precio;
    double valor_nominal;
    double valor_inversion;
    double porcentajeiva;
    double comcomprador;
    double comvendedor;
    double monto;
    double montoiva;
    double totales;
    double totalinversion;
    int arancelcompra;
    int arancelventa;
    double arancel;
    int cliente;
    double nComisionResto;

    Conexion con = null;
    Statement st = null;
    Connection conn = null;

    public liquidacion BuscarxOperacion(Double id) throws SQLException {
        liquidacion p = new liquidacion();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = new configuracion();
        config = configDAO.configBursatil();
        UUID UUI = new UUID();

        iva = config.getPorcentajeiva();
        limitecomision = config.getLimitecomision();

        con = new Conexion();
        st = con.conectar();
        conn = st.getConnection();

        try {

            String sql = "SELECT creferencia,precierre_operacion.numero,negociado,"
                    + "precierre_operacion.fecha,validohasta,fechacierre,"
                    + "precierre_operacion.fechaemision,"
                    + "precierre_operacion.vencimiento,comprador,precierre_operacion.cupones,"
                    + "vendedor,precierre_operacion.valor_nominal,precierre_operacion.valor_inversion,"
                    + "precierre_operacion.operacion,tipooperacion,"
                    + "precierre_operacion.mercado,precierre_operacion.moneda,"
                    + "emisor,precierre_operacion.cantidad,precierre_operacion.precio,"
                    + "precierre_operacion.tasa,plazo,tipoplazo,numerobolsa,"
                    + "precierre_operacion.base,precierre_operacion.cortes,"
                    + "precierre_operacion.preciocorte,precierre_operacion.periodopago,"
                    + "precierre_operacion.arancelcompra,precierre_operacion.arancelventa,"
                    + "precierre_operacion.comcomprador,precierre_operacion.comvendedor "
                    + " FROM precierre_operacion "
                    + " WHERE precierre_operacion.numero= ? "
                    + " AND operacion=1 ORDER BY numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    idtransaccion = rs.getString("creferencia");
                    numerobolsa = rs.getDouble("numero");
                    fecha = rs.getString("fechacierre");
                    mercado = rs.getInt("mercado");
                    moneda = rs.getInt("moneda");
                    cantidad = rs.getDouble("cantidad");
                    precio = rs.getDouble("precio");
                    valor_nominal = rs.getDouble("valor_nominal");
                    valor_inversion = rs.getDouble("valor_inversion");
                    comprador = rs.getInt("comprador");
                    vendedor = rs.getInt("vendedor");
                    emisor = rs.getInt("emisor");
                    renta = 1;
                    arancelventa = rs.getInt("arancelventa");
                    arancelcompra = rs.getInt("arancelcompra");
                    comcomprador = rs.getDouble("comcomprador");
                    comvendedor = rs.getDouble("comvendedor");
                    this.eliminarLiquidacion(idtransaccion);

                    //PUNTA COMPRADORA COMISIONES
                    if (mercado == 1) {
                        totalinversion = valor_nominal;
                    } else {
                        totalinversion = valor_inversion;
                    }

                    if (comprador != 0) {
                        cliente = comprador;
                        tipo = 1;
                        //SE CALCULA ARANCEL DE BOLSA    
                        arancel = config.getArancelrentafija();
                        if (arancelcompra == 1) {
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            if (moneda == 1) {
                                concepto = config.getConceptobolsa();
                                monto = Math.round(totalinversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                concepto = config.getConceptobolsausd();
                                monto = totalinversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaFija();
                        }

                        if (comcomprador > 0) {
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            if (comcomprador > config.getLimitecomision()) {
                                nComisionResto = comcomprador - config.getLimitecomision();
                                arancel = config.getLimitecomision();
                            } else {
                                arancel = comcomprador;
                            }
                            if (moneda == 1) {
                                concepto = config.getConceptolimite();
                                monto = Math.round(totalinversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                concepto = config.getConceptolimiteusd();
                                monto = totalinversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaFija();

                            if (nComisionResto > 0) {
                                idliquidacion = UUID.crearUUID();
                                idliquidacion = idliquidacion.substring(1, 25);
                                arancel = nComisionResto;
                                if (moneda == 1) {
                                    concepto = config.getConceptocomision();
                                    monto = Math.round(totalinversion * arancel / 100);
                                    montoiva = Math.round(monto * iva / 100);
                                } else {
                                    concepto = config.getConceptocomisionusd();
                                    monto = totalinversion * arancel / 100;
                                    montoiva = monto * iva / 100;
                                }
                                //SE ENVIA A CREAR REGISTRO
                                this.LiquidarRentaFija();
                            }
                        }

                        if (config.getFondocomision() > 0) {
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            arancel = config.getFondocomision();
                            if (moneda == 1) {
                                concepto = config.getConceptofondogarantia();
                                monto = Math.round(totalinversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                concepto = config.getConceptofondogarantiausd();
                                monto = totalinversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaFija();
                        }
                        //FIN CALCULOS PUNTA COMPRADORA
                    }
                    ////PUNTA VENDEDORA COMISIONES
                    if (vendedor != 0) {
                        cliente = vendedor;
                        tipo = 2;
                        //SE CALCULA ARANCEL DE BOLSA    
                        arancel = config.getArancelrentafija();
                        if (arancelventa == 1) {
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            if (moneda == 1) {
                                concepto = config.getConceptobolsa();
                                monto = Math.round(totalinversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                concepto = config.getConceptobolsausd();
                                monto = totalinversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaFija();
                        }

                        if (comvendedor > 0) {
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            if (comvendedor > config.getLimitecomision()) {
                                nComisionResto = comvendedor - config.getLimitecomision();
                                arancel = config.getLimitecomision();
                            } else {
                                arancel = comvendedor;
                            }
                            if (moneda == 1) {
                                concepto = config.getConceptolimite();
                                monto = Math.round(totalinversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                concepto = config.getConceptolimiteusd();
                                monto = totalinversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaFija();

                            if (nComisionResto > 0) {
                                idliquidacion = UUID.crearUUID();
                                idliquidacion = idliquidacion.substring(1, 25);
                                arancel = nComisionResto;
                                if (moneda == 1) {
                                    concepto = config.getConceptocomision();
                                    monto = Math.round(totalinversion * arancel / 100);
                                    montoiva = Math.round(monto * iva / 100);
                                } else {
                                    concepto = config.getConceptocomisionusd();
                                    monto = totalinversion * arancel / 100;
                                    montoiva = monto * iva / 100;
                                }
                                //SE ENVIA A CREAR REGISTRO
                                this.LiquidarRentaFija();
                            }
                        }
                        if (config.getFondocomision() > 0) {
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            arancel = config.getFondocomision();
                            if (moneda == 1) {
                                concepto = config.getConceptofondogarantia();
                                monto = Math.round(totalinversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                concepto = config.getConceptofondogarantiausd();
                                monto = totalinversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaFija();
                        }
                    }
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }

        st.close();

        conn.close();
        return p;

    }

    public void LiquidarRentaFija() throws SQLException {
        String sql = "INSERT INTO liquidaciones("
                + "idtransaccion,idliquidacion,"
                + "numerobolsa,fecha,"
                + "emisor,renta,"
                + "cliente,moneda,"
                + "tipo,concepto,"
                + "cantidad,precio,"
                + "valor_inversion,porcentajeiva,"
                + "arancel,monto,"
                + "montoiva,totales) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idtransaccion);
        ps.setString(2, idliquidacion);
        ps.setDouble(3, numerobolsa);
        ps.setString(4, fecha);
        ps.setInt(5, emisor);
        ps.setInt(6, renta);
        ps.setInt(7, cliente);
        ps.setInt(8, moneda);
        ps.setInt(9, tipo);
        ps.setString(10, concepto);
        ps.setDouble(11, cantidad);
        ps.setDouble(12, precio);
        ps.setDouble(13, totalinversion);
        ps.setDouble(14, iva);
        ps.setDouble(15, arancel);
        ps.setDouble(16, monto);
        ps.setDouble(17, montoiva);
        ps.setDouble(18, monto + montoiva);
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> LIQUIDACION DE OPERACIONES BURSATILES " + ex.getLocalizedMessage());
        }
    }

    public void eliminarLiquidacion(String cReferencia) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM liquidaciones WHERE idtransaccion=?");
        ps.setString(1, cReferencia);
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> BORRADO LIQUIDACION DE OPERACIONES BURSATILES " + ex.getLocalizedMessage());
        }
    }

    public ArrayList<liquidacion> MostrarxFecha(int cliente, int moneda, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<liquidacion> lista = new ArrayList<liquidacion>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idtransaccion,idliquidacion,numerobolsa,"
                    + "liquidaciones.fecha,emisor,renta,cliente,moneda,"
                    + "liquidaciones.tipo,concepto,cantidad,precio,valor_inversion,"
                    + "porcentajeiva,arancel,monto,montoiva,totales,"
                    + "emisores.nombre AS nombreemisor,"
                    + "clientes.nombre AS nombrecliente,"
                    + "monedas.nombre AS nombremoneda,"
                    + "productos.nombre AS nombreproducto "
                    + "FROM liquidaciones "
                    + "LEFT JOIN emisores "
                    + "ON emisores.codigo=liquidaciones.emisor "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=liquidaciones.cliente "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=liquidaciones.moneda "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=liquidaciones.concepto "
                    + " WHERE IF(" + cliente + "<>0,liquidaciones.cliente=" + cliente + ",TRUE)"
                    + " AND IF(" + moneda + "<>0,liquidaciones.moneda=" + moneda + ",TRUE)"
                    + " AND liquidaciones.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                    + " ORDER BY liquidaciones.cliente,liquidaciones.moneda,liquidaciones.numerobolsa";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    liquidacion p = new liquidacion();
                    moneda m = new moneda();
                    producto t = new producto();
                    emisor e = new emisor();
                    cliente cl = new cliente();

                    p.setIdtransaccion(rs.getString("idtransaccion"));
                    p.setNumerobolsa(rs.getDouble("numerobolsa"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setRenta(rs.getInt("renta"));
                    p.setMoneda(m);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setEmisor(e);
                    p.getEmisor().setCodigo(rs.getInt("emisor"));
                    p.getEmisor().setNombre(rs.getString("nombreemisor"));
                    p.setCliente(cl);
                    p.getCliente().setCodigo(rs.getInt("cliente"));
                    p.getCliente().setNombre(rs.getString("nombrecliente"));
                    p.setConcepto(t);
                    p.getConcepto().setCodigo(rs.getString("concepto"));
                    p.getConcepto().setNombre(rs.getString("nombreproducto"));
                    p.setCantidad(rs.getDouble("cantidad"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setValor_inversion(rs.getDouble("valor_inversion"));
                    p.setArancel(rs.getDouble("arancel"));
                    p.setPorcentajeiva(rs.getDouble("porcentajeiva"));
                    p.setMonto(rs.getDouble("monto"));
                    p.setMontoiva(rs.getDouble("montoiva"));
                    p.setTotales(rs.getDouble("totales"));

                    lista.add(p);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<liquidacion> MostrarPendientes(int cliente, int moneda) throws SQLException {
        ArrayList<liquidacion> lista = new ArrayList<liquidacion>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idtransaccion,liquidaciones.numerobolsa,"
                    + "liquidaciones.fecha,liquidaciones.cliente,"
                    + "clientes.nombre AS nombrecliente,"
                    + "clientes.direccion,clientes.ruc,"
                    + "liquidaciones.moneda,"
                    + "monedas.etiqueta AS nombremoneda,"
                    + "'Operación de ' AS descripcion,"
                    + "CASE liquidaciones.tipo"
                    + "  WHEN 1 THEN 'Compra de'"
                    + "  WHEN 2 THEN 'Venta de'"
                    + "END AS tipooperacion,"
                    + "titulos.nombre AS nombretitulo,"
                    + "emisores.nombre AS nombreemisor,"
                    + "precierre_operacion.valor_nominal,"
                    + "precierre_operacion.valor_inversion,"
                    + "SUM(liquidaciones.totales) AS totalfacturar "
                    + "FROM liquidaciones "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=liquidaciones.cliente "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=liquidaciones.moneda "
                    + "LEFT JOIN precierre_operacion "
                    + "ON precierre_operacion.creferencia=liquidaciones.idtransaccion "
                    + "LEFT JOIN titulos "
                    + "ON titulos.codigo=precierre_operacion.titulo "
                    + "LEFT JOIN emisores "
                    + "ON  emisores.codigo=precierre_operacion.emisor "
                    + "WHERE liquidaciones.cliente=" + cliente
                    + " AND liquidaciones.moneda=" + moneda
                    + " AND liquidaciones.idfactura='PENDIENTE' "
                    + "GROUP BY idtransaccion,liquidaciones.cliente";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    liquidacion p = new liquidacion();
                    moneda m = new moneda();
                    cliente cl = new cliente();

                    p.setIdtransaccion(rs.getString("idtransaccion"));
                    p.setNumerobolsa(rs.getDouble("numerobolsa"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setMoneda(m);
                    p.getMoneda().setCodigo(rs.getInt("moneda"));
                    p.getMoneda().setNombre(rs.getString("nombremoneda"));
                    p.setCliente(cl);
                    p.getCliente().setCodigo(rs.getInt("cliente"));
                    p.getCliente().setNombre(rs.getString("nombrecliente"));
                    p.getCliente().setDireccion(rs.getString("direccion"));
                    p.getCliente().setRuc(rs.getString("ruc"));

                    p.setValor_nominal(rs.getDouble("valor_nominal"));
                    p.setValor_inversion(rs.getDouble("valor_inversion"));
                    p.setTotales(rs.getDouble("totalfacturar"));
                    p.setObservacion(rs.getString("descripcion") + " " + rs.getString("tipooperacion") + " " + rs.getString("nombretitulo") + " " + rs.getString("nombreemisor"));
                    lista.add(p);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public ArrayList<liquidacion> MostrarxId(String id, int cliente, int moneda) throws SQLException {
        ArrayList<liquidacion> lista = new ArrayList<liquidacion>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idtransaccion,idliquidacion,numerobolsa,"
                    + "liquidaciones.fecha,emisor,renta,cliente,moneda,"
                    + "liquidaciones.tipo,concepto,cantidad,precio,valor_inversion,"
                    + "porcentajeiva,arancel,monto,montoiva,totales,"
                    + "emisores.nombre AS nombreemisor,"
                    + "clientes.nombre AS nombrecliente,"
                    + "monedas.nombre AS nombremoneda,"
                    + "productos.nombre AS nombreproducto "
                    + "FROM liquidaciones "
                    + "LEFT JOIN emisores "
                    + "ON emisores.codigo=liquidaciones.emisor "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=liquidaciones.cliente "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=liquidaciones.moneda "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=liquidaciones.concepto "
                    + " WHERE liquidaciones.idtransaccion='" + id + "'"
                    + " AND liquidaciones.cliente=" + cliente
                    + " AND liquidaciones.moneda=" + moneda
                    + " ORDER BY liquidaciones.cliente,liquidaciones.moneda,liquidaciones.numerobolsa";

            System.out.println(sql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    liquidacion p = new liquidacion();
                    producto t = new producto();

                    p.setNumerobolsa(rs.getDouble("numerobolsa"));
                    p.setRenta(rs.getInt("renta"));
                    p.setConcepto(t);
                    p.getConcepto().setCodigo(rs.getString("concepto"));
                    p.getConcepto().setNombre(rs.getString("nombreproducto"));
                    p.setCantidad(rs.getDouble("cantidad"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setValor_inversion(rs.getDouble("valor_inversion"));
                    p.setArancel(rs.getDouble("arancel"));
                    p.setPorcentajeiva(rs.getDouble("porcentajeiva"));
                    p.setMonto(rs.getDouble("monto"));
                    p.setMontoiva(rs.getDouble("montoiva"));
                    p.setTotales(rs.getDouble("totales"));
                    lista.add(p);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public boolean MarcarFacturado(String idFactura, int cliente, String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE liquidaciones SET idfactura=? WHERE cliente=? AND idtransaccion=?");
        ps.setString(1, idFactura);
        ps.setInt(2, cliente);
        ps.setString(3, id);

        int rowsUpdated = ps.executeUpdate();

        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean DesMarcarFacturado(String idFactura) throws SQLException {
        String cEstado = "PENDIENTE";
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE liquidaciones SET idfactura='PENDIENTE' WHERE idfactura='" + idFactura + "'");
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public liquidacion BuscarxOperacionAcciones(Double id) throws SQLException {
        liquidacion p = new liquidacion();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = new configuracion();
        config = configDAO.configBursatil();
        UUID UUI = new UUID();

        iva = config.getPorcentajeiva();
        limitecomision = config.getLimitecomision();
        con = new Conexion();
        st = con.conectar();
        conn = st.getConnection();

        try {

            String sql = "SELECT creferencia,precierre_operacion.numero,negociado,"
                    + "precierre_operacion.fecha,validohasta,fechacierre,"
                    + "comprador,precierre_operacion.cupones,"
                    + "vendedor,precierre_operacion.valor_inversion,"
                    + "precierre_operacion.operacion,tipooperacion,"
                    + "precierre_operacion.mercado,precierre_operacion.moneda,"
                    + "emisor,precierre_operacion.cantidad,precierre_operacion.precio,"
                    + "numerobolsa,precierre_operacion.arancelcompra,precierre_operacion.arancelventa,"
                    + "precierre_operacion.comcomprador,precierre_operacion.comvendedor "
                    + " FROM precierre_operacion "
                    + " WHERE precierre_operacion.numero= ? "
                    + " AND operacion=2 ORDER BY numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                System.out.println(sql);
                while (rs.next()) {
                    idtransaccion = rs.getString("creferencia");
                    numerobolsa = rs.getDouble("numero");
                    fecha = rs.getString("fechacierre");
                    mercado = rs.getInt("mercado");
                    moneda = rs.getInt("moneda");
                    cantidad = rs.getDouble("cantidad");
                    precio = rs.getDouble("precio");
                    valor_inversion = rs.getDouble("valor_inversion");
                    comprador = rs.getInt("comprador");
                    vendedor = rs.getInt("vendedor");
                    emisor = rs.getInt("emisor");
                    renta = 2;
                    arancelventa = rs.getInt("arancelventa");
                    arancelcompra = rs.getInt("arancelcompra");
                    comcomprador = rs.getDouble("comcomprador");
                    comvendedor = rs.getDouble("comvendedor");
                    arancel = config.getArancelrentafija();
                    this.eliminarLiquidacion(idtransaccion);

                    System.out.println("Valor Inversión " + valor_inversion);
                    System.out.println("Valor Inversión 2 " + rs.getDouble("valor_inversion"));
                    if (comprador != 0) {
                        cliente = comprador;
                        tipo = 1;
                        //SE CALCULA ARANCEL DE BOLSA    
                        if (arancelcompra == 1) {
                            arancel = config.getArancelrentafija();
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            if (moneda == 1) {
                                concepto = config.getConceptobolsa();
                                monto = Math.round(valor_inversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                concepto = config.getConceptobolsausd();
                                monto = valor_inversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaVariable();
                        }

                        if (comcomprador > 0) {
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            if (comcomprador > config.getLimitecomision()) {
                                nComisionResto = comcomprador - config.getLimitecomision();
                                arancel = config.getLimitecomision();
                            } else {
                                arancel = comcomprador;
                            }
                            if (moneda == 1) {
                                concepto = config.getConceptolimite();
                                monto = Math.round(valor_inversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                concepto = config.getConceptolimiteusd();
                                monto = valor_inversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaVariable();

                            if (nComisionResto > 0) {
                                idliquidacion = UUID.crearUUID();
                                idliquidacion = idliquidacion.substring(1, 25);
                                arancel = nComisionResto;
                                if (moneda == 1) {
                                    concepto = config.getConceptocomision();
                                    monto = Math.round(valor_inversion * arancel / 100);
                                    montoiva = Math.round(monto * iva / 100);
                                } else {
                                    concepto = config.getConceptocomisionusd();
                                    monto = valor_inversion * arancel / 100;
                                    montoiva = monto * iva / 100;
                                }
                                //SE ENVIA A CREAR REGISTRO
                                this.LiquidarRentaVariable();
                            }
                        }

                        if (config.getFondocomision() > 0) {
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            arancel = config.getFondocomision();
                            if (moneda == 1) {
                                concepto = config.getConceptofondogarantia();
                                monto = Math.round(valor_inversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                concepto = config.getConceptofondogarantiausd();
                                monto = valor_inversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaVariable();
                        }
                        //FIN CALCULOS PUNTA COMPRADORA
                    }
                    ////PUNTA VENDEDORA COMISIONES
                    if (vendedor != 0) {
                        cliente = vendedor;
                        concepto = config.getConceptobolsa();
                        tipo = 2;
                        //SE CALCULA ARANCEL DE BOLSA    
                        if (arancelventa == 1) {
                            arancel = config.getArancelrentafija();
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            if (moneda == 1) {
                                monto = Math.round(valor_inversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                monto = valor_inversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaFija();
                        }

                        if (comvendedor > 0) {
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            if (comvendedor > config.getLimitecomision()) {
                                nComisionResto = comvendedor - config.getLimitecomision();
                                arancel = config.getLimitecomision();
                            } else {
                                arancel = comvendedor;
                            }
                            if (moneda == 1) {
                                concepto = config.getConceptolimite();
                                monto = Math.round(valor_inversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                concepto = config.getConceptolimiteusd();
                                monto = valor_inversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaVariable();

                            if (nComisionResto > 0) {
                                idliquidacion = UUID.crearUUID();
                                idliquidacion = idliquidacion.substring(1, 25);
                                arancel = nComisionResto;
                                if (moneda == 1) {
                                    concepto = config.getConceptocomision();
                                    monto = Math.round(valor_inversion * arancel / 100);
                                    montoiva = Math.round(monto * iva / 100);
                                } else {
                                    concepto = config.getConceptocomisionusd();
                                    monto = valor_inversion * arancel / 100;
                                    montoiva = monto * iva / 100;
                                }
                                //SE ENVIA A CREAR REGISTRO
                                this.LiquidarRentaVariable();
                            }
                        }
                        if (config.getFondocomision() > 0) {
                            idliquidacion = UUID.crearUUID();
                            idliquidacion = idliquidacion.substring(1, 25);
                            arancel = config.getFondocomision();
                            if (moneda == 1) {
                                concepto = config.getConceptofondogarantia();
                                monto = Math.round(valor_inversion * arancel / 100);
                                montoiva = Math.round(monto * iva / 100);
                            } else {
                                concepto = config.getConceptofondogarantiausd();
                                monto = valor_inversion * arancel / 100;
                                montoiva = monto * iva / 100;
                            }
                            //SE ENVIA A CREAR REGISTRO
                            this.LiquidarRentaVariable();
                        }
                    }
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return p;
    }

    public void LiquidarRentaVariable() throws SQLException {
        String sql = "INSERT INTO liquidaciones("
                + "idtransaccion,idliquidacion,"
                + "numerobolsa,fecha,"
                + "emisor,renta,"
                + "cliente,moneda,"
                + "tipo,concepto,"
                + "cantidad,precio,"
                + "valor_inversion,porcentajeiva,"
                + "arancel,monto,"
                + "montoiva,totales) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idtransaccion);
        ps.setString(2, idliquidacion);
        ps.setDouble(3, numerobolsa);
        ps.setString(4, fecha);
        ps.setInt(5, emisor);
        ps.setInt(6, renta);
        ps.setInt(7, cliente);
        ps.setInt(8, moneda);
        ps.setInt(9, tipo);
        ps.setString(10, concepto);
        ps.setDouble(11, cantidad);
        ps.setDouble(12, precio);
        ps.setDouble(13, valor_inversion);
        ps.setDouble(14, iva);
        ps.setDouble(15, arancel);
        ps.setDouble(16, monto);
        ps.setDouble(17, montoiva);
        ps.setDouble(18, monto + montoiva);
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> LIQUIDACION DE OPERACIONES BURSATILES " + ex.getLocalizedMessage());
        }
    }

}

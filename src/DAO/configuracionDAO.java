/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.caja;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.configuracion;
import Modelo.moneda;
import Modelo.sucursal;
import Modelo.vendedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuario
 */
public class configuracionDAO {

    Conexion con = null;
    Statement st = null;

    public configuracion consultar() {
        configuracion configuracion = new configuracion();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT empresa,configuracion.ruc,configuracion.direccion,\n"
                    + "configuracion.telefono,configuracion.fax,configuracion.mail,configuracion.web,\n"
                    + "monedadefecto,sucursaldefecto,periodoactivo,facturaratrasado,\n"
                    + "sucursales.factura,\n"
                    + "cajadefecto,vendedordefecto,clientedefecto,comprobantedefecto,\n"
                    + "itemfacturas,tipo_factura_impresion,pagoefectivo,\n"
                    + "configuracion.giraduria,cantidadfactura,modificarprecio,comentaritem,\n"
                    + "interfazvtalote,interfazpuntovta,prodingreso,prodsalida,\n"
                    + "antesimprimir,menuprincipal,impresorafactura,impresorarecibo,\n"
                    + "lectorcodigobarra,facturar_s_stock,emite_retencion,\n"
                    + "cod_retencion,centro_costo,updatecuotas,verificar_timbrado,\n"
                    + "lista_precio,buscarproducto,verificarcuenta,actualizarprecio,\n"
                    + "configuracion.responsable,configuracion.ramo,generarinteres,\n"
                    + "fechainteres,configuracion.tasainteres,configuracion.precierre,capital_colocacion,cartera_propia,\n"
                    + "construcciones,codmora,codgastos,porcentajeiva,\n"
                    + "cobropagoexpress,cobrocomanda,limitedescuento,ivaincluido,\n"
                    + "configuracion.nombrefactura,\n"
                    + "configuracion.nombrepagare,nombrepagarecuota,nombrerecibo,\n"
                    + "nombresolicitud,nombreliquidacion,codcapital,codinteres,\n"
                    + "codcolocacion,gastospp,gastosph,gastosdd,codpunitorio,\n"
                    + "cobrador,aranceliva,arancelbanco,\n"
                    + "arancelseguro,arancelgastos,comisiones,segurovida,\n"
                    + "codasesor,sucursales.nombre AS nombresucursal,sucursales.vencetimbrado,\n"
                    + "sucursales.nrotimbrado,\n"
                    + "clientes.nombre AS nombrecliente,monedas.nombre AS nombremoneda,\n"
                    + "monedas.venta,monedas.compra,comprobantes.nombre AS nombrecomprobante,\n"
                    + "vendedores.nombre as nombrevendedor, "
                    + "configuracion.codigoordencredito,configuracion.codigoordencompra,\n"
                    + "configuracion.codigocuotasocial,configuracion.codigoaporte,\n"
                    + "configuracion.codigosolidaridad,\n"
                    + "configuracion.importecuotasocial,configuracion.importeaporte,\n"
                    + "configuracion.importesolidaridad,cajas.nombre as nombrecaja,cajas.recibo,\n"
                    + "cajas.timbrado as timbradocaja,cajas.factura as facturacaja,\n"
                    + "cajas.expedicion,cajas.vencetimbrado as vencetimbradocaja,\n"
                    + "sucursales.expedicion as sucexpedicion,\n"
                    + "configuracion.nombrecontrato1,configuracion.nombrecontrato2,\n"
                    + "configuracion.nombrecontrato3,configuracion.nombrecontrato4,\n"
                    + "configuracion.nombreliquidacioncheques,\n"
                    + "configuracion.nombreliquidacionph,\n"
                    + "configuracion.salario_minimo,\n"
                    + "configuracion.porcentaje_ips,\n"
                    + "configuracion.porcentaje_bonificacion,\n"
                    + "configuracion.horas_extras_diurnas_habiles, "
                    + "configuracion.horas_extras_nocturnas, "
                    + "configuracion.codalquiler,configuracion.codgarage,\n"
                    + "configuracion.codgarantia,configuracion.codexpensa,\n"
                    + "configuracion.codcomision,configuracion.codmulta,\n"
                    + "configuracion.codfondo,configuracion.codllave,\n"
                    + "configuracion.codotros,configuracion.comprobanteinteres\n "
                    + "FROM configuracion\n"
                    + "LEFT JOIN sucursales\n"
                    + "ON sucursales.codigo=configuracion.sucursaldefecto\n"
                    + "LEFT JOIN monedas\n"
                    + "ON monedas.codigo=configuracion.monedadefecto\n"
                    + "LEFT JOIN clientes\n"
                    + "ON clientes.codigo=configuracion.clientedefecto\n"
                    + "LEFT JOIN comprobantes\n"
                    + "ON comprobantes.codigo=configuracion.comprobantedefecto\n"
                    + "LEFT JOIN cajas\n"
                    + "ON cajas.codigo=configuracion.cajadefecto\n"
                    + "LEFT JOIN vendedores\n"
                    + "ON vendedores.codigo=configuracion.vendedordefecto\n";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    vendedor vendedor = new vendedor();
                    sucursal sucursal = new sucursal();
                    moneda moneda = new moneda();
                    comprobante comp = new comprobante();
                    cliente cl = new cliente();
                    caja ca = new caja();

                    configuracion.setVendedordefecto(vendedor);
                    configuracion.setSucursaldefecto(sucursal);
                    configuracion.setMonedadefecto(moneda);
                    configuracion.setComprobantedefecto(comp);
                    configuracion.setClientedefecto(cl);
                    configuracion.setCajadefecto(ca);

                    //primera página DATOS DE LA EMPRESA
                    configuracion.setEmpresa(rs.getString("empresa"));
                    configuracion.setDireccion(rs.getString("direccion"));
                    configuracion.setRuc(rs.getString("ruc"));
                    configuracion.setTelefono(rs.getString("telefono"));
                    configuracion.setFax(rs.getString("fax"));
                    configuracion.setMail(rs.getString("mail"));
                    configuracion.setWeb(rs.getString("web"));
                    configuracion.setRamo(rs.getString("ramo"));
                    configuracion.setResponsable(rs.getString("responsable"));
                    configuracion.setPeriodoactivo(rs.getInt("periodoactivo"));

                    // segunda página VALORES POR DEFECTO
                    configuracion.getVendedordefecto().setCodigo(rs.getInt("vendedordefecto"));
                    configuracion.getVendedordefecto().setNombre(rs.getString("nombrevendedor"));

                    configuracion.getSucursaldefecto().setCodigo(rs.getInt("sucursaldefecto"));
                    configuracion.getSucursaldefecto().setNombre(rs.getString("nombresucursal"));
                    configuracion.getSucursaldefecto().setVencetimbrado(rs.getDate("vencetimbrado"));
                    configuracion.getSucursaldefecto().setNrotimbrado(rs.getString("nrotimbrado"));
                    configuracion.getSucursaldefecto().setFactura(rs.getDouble("factura"));
                    configuracion.getSucursaldefecto().setExpedicion(rs.getString("sucexpedicion"));

                    configuracion.getMonedadefecto().setCodigo(rs.getInt("monedadefecto"));
                    configuracion.getMonedadefecto().setNombre(rs.getString("nombremoneda"));
                    configuracion.getMonedadefecto().setVenta(rs.getBigDecimal("venta"));
                    configuracion.getMonedadefecto().setCompra(rs.getBigDecimal("compra"));

                    configuracion.getComprobantedefecto().setCodigo(rs.getInt("comprobantedefecto"));
                    configuracion.getComprobantedefecto().setNombre(rs.getString("nombrecomprobante"));
                    configuracion.setCod_retencion(rs.getInt("cod_retencion"));

                    configuracion.getCajadefecto().setCodigo(rs.getInt("cajadefecto"));
                    configuracion.getCajadefecto().setNombre(rs.getString("nombrecaja"));
                    configuracion.getCajadefecto().setRecibo(rs.getDouble("recibo"));
                    configuracion.getCajadefecto().setFactura(rs.getInt("facturacaja"));
                    configuracion.getCajadefecto().setTimbrado(rs.getInt("timbradocaja"));
                    configuracion.getCajadefecto().setExpedicion(rs.getString("expedicion"));
                    configuracion.getCajadefecto().setVencetimbrado(rs.getDate("vencetimbradocaja"));

                    configuracion.getClientedefecto().setCodigo(rs.getInt("clientedefecto"));
                    configuracion.getClientedefecto().setNombre(rs.getString("nombrecliente"));
                    configuracion.getClientedefecto().setDireccion(rs.getString("direccion"));

                    // DATOS PARA ASOCIACIONES DE EMPLEADOS
                    configuracion.setCodigoordencompra(rs.getInt("codigoordencompra"));
                    configuracion.setCodigoordencredito(rs.getInt("codigoordencredito"));
                    configuracion.setComprobanteinteres(rs.getInt("comprobanteinteres"));
                    configuracion.setCodigoaporte(rs.getInt("codigoaporte"));
                    configuracion.setImporteaporte(rs.getBigDecimal("importeaporte"));

                    configuracion.setCodigocuotasocial(rs.getInt("codigocuotasocial"));
                    configuracion.setImportecuotasocial(rs.getBigDecimal("importecuotasocial"));

                    configuracion.setCodigosolidaridad(rs.getInt("codigosolidaridad"));
                    configuracion.setImportesolidaridad(rs.getBigDecimal("importesolidaridad"));

                    // DATOS PARA VENTAS
                    configuracion.setLista_precio(rs.getInt("lista_precio"));
                    configuracion.setItemfacturas(rs.getInt("itemfacturas"));//CONTROLA LA CANTIDAD DE ITEMS DE FACTURAS
                    configuracion.setImpresorafactura(rs.getInt("impresorafactura"));//TIPO DE IMPRESION
                    configuracion.setTipo_factura_impresion(rs.getInt("tipo_factura_impresion"));//TIPO DE IMPRESORA
                    configuracion.setModificarprecio(rs.getInt("modificarprecio"));
                    configuracion.setPeriodoactivo(rs.getInt("periodoactivo"));
                    configuracion.setAntesimprimir(rs.getInt("antesimprimir"));
                    configuracion.setLectorcodigobarra(rs.getInt("lectorcodigobarra"));
                    configuracion.setFacturar_s_stock(rs.getInt("facturar_s_stock"));
                    //
                    configuracion.setProdingreso(rs.getInt("prodingreso"));

                    // DATOS PARA IMPRESION DE DOCUMENTOS
                    configuracion.setNombrefactura(rs.getString("nombrefactura"));
                    configuracion.setNombrepagare(rs.getString("nombrepagare"));
                    configuracion.setNombrepagarecuota(rs.getString("nombrepagarecuota"));
                    configuracion.setNombrerecibo(rs.getString("nombrerecibo"));
                    configuracion.setNombresolicitud(rs.getString("nombresolicitud"));
                    configuracion.setNombreliquidacion(rs.getString("nombreliquidacion"));
                    configuracion.setNombreliquidacionph(rs.getString("nombreliquidacionph"));
                    configuracion.setNombrecontrato1(rs.getString("nombrecontrato1"));
                    configuracion.setNombrecontrato2(rs.getString("nombrecontrato2"));
                    configuracion.setNombrecontrato3(rs.getString("nombrecontrato3"));
                    configuracion.setNombrecontrato4(rs.getString("nombrecontrato4"));
                    configuracion.setNombreliquidacioncheques(rs.getString("nombreliquidacioncheques"));

                    // DATOS PARA SALARIOS
                    configuracion.setSalario_minimo(rs.getDouble("salario_minimo"));
                    configuracion.setPorcentaje_ips(rs.getDouble("porcentaje_ips"));
                    configuracion.setPorcentaje_bonificacion(rs.getDouble("porcentaje_bonificacion"));
                    configuracion.setHoras_extras_diurnas_habiles(rs.getDouble("horas_extras_diurnas_habiles"));
                    configuracion.setHoras_extras_nocturnas(rs.getDouble("horas_extras_nocturnas"));

                    // DATOS PARA INMMOBILIARIAS
                    configuracion.setCodalquiler(rs.getString("codalquiler"));
                    configuracion.setCodgarage(rs.getString("codgarage"));
                    configuracion.setCodgarantia(rs.getString("codgarantia"));
                    configuracion.setCodexpensa(rs.getString("codexpensa"));
                    configuracion.setCodcomision(rs.getString("codcomision"));
                    configuracion.setCodmulta(rs.getString("codmulta"));
                    configuracion.setCodfondo(rs.getString("codfondo"));
                    configuracion.setCodllave(rs.getString("codllave"));
                    configuracion.setCodotros(rs.getString("codotros"));

                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->CONFIG " + ex.getLocalizedMessage());
        }
        return configuracion;
    }

    public boolean ConfigInicial(configuracion config) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE configuracion SET empresa=?,ruc=?,direccion=?,telefono=?,fax=?,mail=?,web=?,ramo=?,responsable=?,periodoactivo=?");
        ps.setString(1, config.getEmpresa());
        ps.setString(2, config.getRuc());
        ps.setString(3, config.getDireccion());
        ps.setString(4, config.getTelefono());
        ps.setString(5, config.getFax());
        ps.setString(6, config.getMail());
        ps.setString(7, config.getWeb());
        ps.setString(8, config.getRamo());
        ps.setString(9, config.getResponsable());
        ps.setInt(10, config.getPeriodoactivo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public configuracion configBursatil() {
        configuracion configuracion = new configuracion();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT arancelrentafija,"
                    + "arancelrentavariable,"
                    + "porcentajeiva,"
                    + "limitecomision,"
                    + "fondogarantia,"
                    + "conceptobolsa,"
                    + "conceptocomision,"
                    + "conceptolimite, "
                    + "conceptofondogarantia,"
                    + "conceptobolsausd,"
                    + "conceptocomisionusd,"
                    + "conceptolimiteusd, "
                    + "conceptofondogarantiausd "
                    + "FROM configuracion ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    configuracion.setPorcentajeiva(rs.getDouble("porcentajeiva"));
                    configuracion.setArancelrentafija(rs.getDouble("arancelrentafija"));
                    configuracion.setArancelrentavariable(rs.getDouble("arancelrentavariable"));
                    configuracion.setLimitecomision(rs.getDouble("limitecomision"));
                    configuracion.setFondocomision(rs.getDouble("fondogarantia"));

                    configuracion.setConceptobolsa(rs.getString("conceptobolsa"));
                    configuracion.setConceptocomision(rs.getString("conceptocomision"));
                    configuracion.setConceptolimite(rs.getString("conceptolimite"));
                    configuracion.setConceptofondogarantia(rs.getString("conceptofondogarantia"));

                    configuracion.setConceptobolsausd(rs.getString("conceptobolsausd"));
                    configuracion.setConceptocomisionusd(rs.getString("conceptocomisionusd"));
                    configuracion.setConceptolimiteusd(rs.getString("conceptolimiteusd"));
                    configuracion.setConceptofondogarantiausd(rs.getString("conceptofondogarantiausd"));

                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->CONFIG " + ex.getLocalizedMessage());
        }
        return configuracion;
    }

    public configuracion configMicroCredito() {
        configuracion conf = new configuracion();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT empresa,ruc,direccion,telefono,"
                    + "nombrepagare,nombresolicitud,nombreliquidacion,arancelaporte,"
                    + "nombrecontrato1,nombrefactura,nombrerecibo,capitalizacionaporte,"
                    + "arancelseguro,arancelgastos,arancelsolidaridad,arancelcobrador,"
                    + "arancelfondoproteccion "
                    + "FROM configuracion";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    conf.setEmpresa(rs.getString("empresa"));
                    conf.setRuc(rs.getString("ruc"));
                    conf.setTelefono(rs.getString("telefono"));
                    conf.setDireccion(rs.getString("direccion"));
                    conf.setNombrepagare(rs.getString("nombrepagare"));
                    conf.setNombresolicitud(rs.getString("nombresolicitud"));
                    conf.setNombreliquidacion(rs.getString("nombreliquidacion"));
                    conf.setNombrecontrato1(rs.getString("nombrecontrato1"));
                    conf.setNombrefactura(rs.getString("nombrefactura"));
                    conf.setNombrerecibo(rs.getString("nombrerecibo"));

                    conf.setCapitalizacionaporte(rs.getDouble("capitalizacionaporte"));
                    conf.setArancelseguro(rs.getBigDecimal("arancelseguro"));
                    conf.setArancelgastos(rs.getBigDecimal("arancelgastos"));
                    conf.setArancelaporte(rs.getBigDecimal("arancelaporte"));
                    conf.setArancelsolidaridad(rs.getBigDecimal("arancelsolidaridad"));
                    conf.setArancelcobrador(rs.getBigDecimal("arancelcobrador"));
                    conf.setArancelfondoproteccion(rs.getBigDecimal("arancelfondoproteccion"));
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->CONFIG " + ex.getLocalizedMessage());
        }
        return conf;
    }

    public boolean UpdateConfigMicrocredito(configuracion config) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE configuracion"
                + " SET empresa=?,ruc=?,direccion=?,telefono=?,"
                + "nombrepagare=?,nombresolicitud=?,nombreliquidacion=?,"
                + "nombrecontrato1=?,nombrefactura=?,nombrerecibo=?,capitalizacionaporte=?,"
                + "arancelseguro=?,arancelgastos=?,arancelaporte=?,"
                + "arancelsolidaridad=?,arancelcobrador=?,"
                + "arancelfondoproteccion=?");
        ps.setString(1, config.getEmpresa());
        ps.setString(2, config.getRuc());
        ps.setString(3, config.getDireccion());
        ps.setString(4, config.getTelefono());
        ps.setString(5, config.getNombrepagare());
        ps.setString(6, config.getNombresolicitud());
        ps.setString(7, config.getNombreliquidacion());
        ps.setString(8, config.getNombrecontrato1());
        ps.setString(9, config.getNombrefactura());
        ps.setString(10, config.getNombrerecibo());
        ps.setDouble(11, config.getCapitalizacionaporte());
        ps.setBigDecimal(12, config.getArancelseguro());
        ps.setBigDecimal(13, config.getArancelgastos());
        ps.setBigDecimal(14, config.getArancelaporte());
        ps.setBigDecimal(15, config.getArancelsolidaridad());
        ps.setBigDecimal(16, config.getArancelcobrador());
        ps.setBigDecimal(17, config.getArancelfondoproteccion());

        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public configuracion configCierre() {
        configuracion conf = new configuracion();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT codigointeres,interesmora,porcentajeivainteres "
                    + " FROM configuracion";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    conf.setCodigointeres(rs.getInt("codigointeres"));
                    conf.setInteresmora(rs.getDouble("interesmora"));
                    conf.setPorcentajeivainteres(rs.getDouble("porcentajeivainteres"));
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->CONFIG " + ex.getLocalizedMessage());
        }
        return conf;
    }

    public configuracion configBancard() {
        configuracion conf = new configuracion();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT bancard_tpago_recepcion,bancard_tpago_linkpago_crear,"
                    + "bancard_tpago_linkpago_reversar_hook_alias,"
                    + "bancard_tpago_link_suscription_crear,"
                    + "bancard_tpago_link_suscription_recuperar,"
                    + "bancard_tpago_link_suscription_actualizar,"
                    + "bancard_tpago_link_suscription_cancelar,"
                    + "bancard_tpago_link_suscription_reintentarpago,"
                    + "bancard_tpago_link_suscription_adelantarpago,"
                    + "bancard_tpago_link_suscription_pausarpago,"
                    + "auth_bancard_user,"
                    + "auth_bancard_password "
                    + "FROM configuracion ";

            
            
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    conf.setBancard_tpago_linkpago_crear(rs.getString("bancard_tpago_recepcion"));
                    conf.setBancard_tpago_linkpago_crear(rs.getString("bancard_tpago_linkpago_crear"));
                    conf.setBancard_tpago_linkpago_reversar_hook_alias(rs.getString("bancard_tpago_linkpago_reversar_hook_alias"));
                    conf.setBancard_tpago_link_suscription_cancelar(rs.getString("bancard_tpago_link_suscription_cancelar"));
                    conf.setBancard_tpago_link_suscription_crear(rs.getString("bancard_tpago_link_suscription_crear"));
                    conf.setBancard_tpago_link_suscription_recuperar(rs.getString("bancard_tpago_link_suscription_recuperar"));
                    conf.setBancard_tpago_link_suscription_actualizar(rs.getString("bancard_tpago_link_suscription_actualizar"));
                    conf.setBancard_tpago_link_suscription_reintentarpago(rs.getString("bancard_tpago_link_suscription_reintentarpago"));
                    conf.setBancard_tpago_link_suscription_pausarpago(rs.getString("bancard_tpago_link_suscription_pausarpago"));
                    conf.setBancard_tpago_link_suscription_adelantarpago(rs.getString("bancard_tpago_link_suscription_adelantarpago"));
                    conf.setAuth_bancard_user(rs.getString("auth_bancard_user"));
                    conf.setAuth_bancard_password(rs.getString("auth_bancard_password"));
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->CONFIG " + ex.getLocalizedMessage());
        }
        return conf;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class configuracion {

    String empresa;
    String ruc;
    String direccion;
    String telefono;
    String fax;
    String mail;
    String web;
    moneda monedadefecto;
    sucursal sucursaldefecto;
    int periodoactivo;
    int facturaratrasado;
    caja cajadefecto;
    vendedor vendedordefecto;
    cliente clientedefecto;
    comprobante comprobantedefecto;
    int itemfacturas;
    int tipo_factura_impresion;
    formapago pagoefectivo;
    int giraduria;
    int cantidadfactura;
    int modificarprecio;
    int comentaritem;
    int interfazvtalote;
    int interfazpuntovta;
    int prodingreso;
    int prodsalida;
    int antesimprimir;
    String menuprincipal;
    int impresorafactura;
    int impresorarecibo;
    int lectorcodigobarra;
    int facturar_s_stock;
    int emite_retencion;
    int cod_retencion;
    int centro_costo;
    int updatecuotas;
    int verificar_timbrado;
    int lista_precio;
    int buscarproducto;
    int verificarcuenta;
    int actualizarprecio;
    String responsable;
    String ramo;
    String generarinteres;
    Date fechainteres;
    BigDecimal tasainteres;
    int comprobanteinteres;
    int precierre;
    int capital_colocacion;
    int cartera_propia;
    int construcciones;
    String codmora;
    String codgastos;
    double porcentajeiva;
    double porcentajeivainteres;
    int cobropagoexpress;
    int cobrocomanda;
    BigDecimal limitedescuento;
    int ivaincluido;
    String nombrefactura;
    String nombrepagare;
    String nombrepagarecuota;
    String nombrerecibo;
    String nombresolicitud;
    String nombreliquidacionph;
    String nombreliquidacion;
    String nombreliquidacioncheques;
    String nombrecontrato1;
    String nombrecontrato2;
    String nombrecontrato3;
    String nombrecontrato4;
    String codcapital;
    String codinteres;
    String codcolocacion;
    String gastospp;
    String gastosph;
    String gastosdd;
    String codpunitorio;
    double cobrador;
    BigDecimal aranceliva;
    BigDecimal arancelbanco;

    double capitalizacionaporte;
    BigDecimal arancelseguro;
    BigDecimal arancelgastos;
    BigDecimal arancelaporte;
    BigDecimal arancelsolidaridad;
    BigDecimal arancelcobrador;
    BigDecimal arancelfondoproteccion;

    String comisiones;
    String segurovida;
    int codasesor;
    int codigoordencompra;
    int codigoordencredito;
    int codigoaporte;
    int codigosolidaridad;
    int codigointeres;
    int codigocuotasocial;
    double salario_minimo;
    double porcentaje_ips;
    double porcentaje_bonificacion;
    BigDecimal importeaporte;
    BigDecimal importesolidaridad;
    BigDecimal importecuotasocial;
    double horas_extras_diurnas_habiles;
    double horas_extras_nocturnas;
    String codalquiler;
    String codgarage;
    String codgarantia;
    String codexpensa;
    String codcomision;
    String codmulta;
    String codfondo;
    String codllave;
    String codotros;

    Double arancelrentafija;
    Double arancelrentavariable;
    Double limitecomision;
    Double fondocomision;

    String conceptobolsa;
    String conceptocomision;
    String conceptolimite;
    String conceptofondogarantia;

    String conceptofondogarantiausd;
    String conceptobolsausd;
    String conceptocomisionusd;
    String conceptolimiteusd;
    Double interesmora;
    
    String bancard_tpago_recepcion;
    String bancard_tpago_linkpago_crear;
    String bancard_tpago_linkpago_reversar_hook_alias;
    String bancard_tpago_link_suscription_crear;
    String bancard_tpago_link_suscription_recuperar;
    String bancard_tpago_link_suscription_actualizar;
    String bancard_tpago_link_suscription_cancelar;
    String bancard_tpago_link_suscription_reintentarpago;
    String bancard_tpago_link_suscription_adelantarpago;
    String bancard_tpago_link_suscription_pausarpago;
    String auth_bancard_user;
    String auth_bancard_password;

    public configuracion() {

    }

    public configuracion(String empresa, String ruc, String direccion, String telefono, String fax, String mail, String web, moneda monedadefecto, sucursal sucursaldefecto, int periodoactivo, int facturaratrasado, caja cajadefecto, vendedor vendedordefecto, cliente clientedefecto, comprobante comprobantedefecto, int itemfacturas, int tipo_factura_impresion, formapago pagoefectivo, int giraduria, int cantidadfactura, int modificarprecio, int comentaritem, int interfazvtalote, int interfazpuntovta, int prodingreso, int prodsalida, int antesimprimir, String menuprincipal, int impresorafactura, int impresorarecibo, int lectorcodigobarra, int facturar_s_stock, int emite_retencion, int cod_retencion, int centro_costo, int updatecuotas, int verificar_timbrado, int lista_precio, int buscarproducto, int verificarcuenta, int actualizarprecio, String responsable, String ramo, String generarinteres, Date fechainteres, BigDecimal tasainteres, int comprobanteinteres, int precierre, int capital_colocacion, int cartera_propia, int construcciones, String codmora, String codgastos, double porcentajeiva, double porcentajeivainteres, int cobropagoexpress, int cobrocomanda, BigDecimal limitedescuento, int ivaincluido, String nombrefactura, String nombrepagare, String nombrepagarecuota, String nombrerecibo, String nombresolicitud, String nombreliquidacionph, String nombreliquidacion, String nombreliquidacioncheques, String nombrecontrato1, String nombrecontrato2, String nombrecontrato3, String nombrecontrato4, String codcapital, String codinteres, String codcolocacion, String gastospp, String gastosph, String gastosdd, String codpunitorio, double cobrador, BigDecimal aranceliva, BigDecimal arancelbanco, double capitalizacionaporte, BigDecimal arancelseguro, BigDecimal arancelgastos, BigDecimal arancelaporte, BigDecimal arancelsolidaridad, BigDecimal arancelcobrador, BigDecimal arancelfondoproteccion, String comisiones, String segurovida, int codasesor, int codigoordencompra, int codigoordencredito, int codigoaporte, int codigosolidaridad, int codigointeres, int codigocuotasocial, double salario_minimo, double porcentaje_ips, double porcentaje_bonificacion, BigDecimal importeaporte, BigDecimal importesolidaridad, BigDecimal importecuotasocial, double horas_extras_diurnas_habiles, double horas_extras_nocturnas, String codalquiler, String codgarage, String codgarantia, String codexpensa, String codcomision, String codmulta, String codfondo, String codllave, String codotros, Double arancelrentafija, Double arancelrentavariable, Double limitecomision, Double fondocomision, String conceptobolsa, String conceptocomision, String conceptolimite, String conceptofondogarantia, String conceptofondogarantiausd, String conceptobolsausd, String conceptocomisionusd, String conceptolimiteusd, Double interesmora, String bancard_tpago_recepcion, String bancard_tpago_linkpago_crear, String bancard_tpago_linkpago_reversar_hook_alias, String bancard_tpago_link_suscription_crear, String bancard_tpago_link_suscription_recuperar, String bancard_tpago_link_suscription_actualizar, String bancard_tpago_link_suscription_cancelar, String bancard_tpago_link_suscription_reintentarpago, String bancard_tpago_link_suscription_adelantarpago, String bancard_tpago_link_suscription_pausarpago, String auth_bancard_user, String auth_bancard_password) {
        this.empresa = empresa;
        this.ruc = ruc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fax = fax;
        this.mail = mail;
        this.web = web;
        this.monedadefecto = monedadefecto;
        this.sucursaldefecto = sucursaldefecto;
        this.periodoactivo = periodoactivo;
        this.facturaratrasado = facturaratrasado;
        this.cajadefecto = cajadefecto;
        this.vendedordefecto = vendedordefecto;
        this.clientedefecto = clientedefecto;
        this.comprobantedefecto = comprobantedefecto;
        this.itemfacturas = itemfacturas;
        this.tipo_factura_impresion = tipo_factura_impresion;
        this.pagoefectivo = pagoefectivo;
        this.giraduria = giraduria;
        this.cantidadfactura = cantidadfactura;
        this.modificarprecio = modificarprecio;
        this.comentaritem = comentaritem;
        this.interfazvtalote = interfazvtalote;
        this.interfazpuntovta = interfazpuntovta;
        this.prodingreso = prodingreso;
        this.prodsalida = prodsalida;
        this.antesimprimir = antesimprimir;
        this.menuprincipal = menuprincipal;
        this.impresorafactura = impresorafactura;
        this.impresorarecibo = impresorarecibo;
        this.lectorcodigobarra = lectorcodigobarra;
        this.facturar_s_stock = facturar_s_stock;
        this.emite_retencion = emite_retencion;
        this.cod_retencion = cod_retencion;
        this.centro_costo = centro_costo;
        this.updatecuotas = updatecuotas;
        this.verificar_timbrado = verificar_timbrado;
        this.lista_precio = lista_precio;
        this.buscarproducto = buscarproducto;
        this.verificarcuenta = verificarcuenta;
        this.actualizarprecio = actualizarprecio;
        this.responsable = responsable;
        this.ramo = ramo;
        this.generarinteres = generarinteres;
        this.fechainteres = fechainteres;
        this.tasainteres = tasainteres;
        this.comprobanteinteres = comprobanteinteres;
        this.precierre = precierre;
        this.capital_colocacion = capital_colocacion;
        this.cartera_propia = cartera_propia;
        this.construcciones = construcciones;
        this.codmora = codmora;
        this.codgastos = codgastos;
        this.porcentajeiva = porcentajeiva;
        this.porcentajeivainteres = porcentajeivainteres;
        this.cobropagoexpress = cobropagoexpress;
        this.cobrocomanda = cobrocomanda;
        this.limitedescuento = limitedescuento;
        this.ivaincluido = ivaincluido;
        this.nombrefactura = nombrefactura;
        this.nombrepagare = nombrepagare;
        this.nombrepagarecuota = nombrepagarecuota;
        this.nombrerecibo = nombrerecibo;
        this.nombresolicitud = nombresolicitud;
        this.nombreliquidacionph = nombreliquidacionph;
        this.nombreliquidacion = nombreliquidacion;
        this.nombreliquidacioncheques = nombreliquidacioncheques;
        this.nombrecontrato1 = nombrecontrato1;
        this.nombrecontrato2 = nombrecontrato2;
        this.nombrecontrato3 = nombrecontrato3;
        this.nombrecontrato4 = nombrecontrato4;
        this.codcapital = codcapital;
        this.codinteres = codinteres;
        this.codcolocacion = codcolocacion;
        this.gastospp = gastospp;
        this.gastosph = gastosph;
        this.gastosdd = gastosdd;
        this.codpunitorio = codpunitorio;
        this.cobrador = cobrador;
        this.aranceliva = aranceliva;
        this.arancelbanco = arancelbanco;
        this.capitalizacionaporte = capitalizacionaporte;
        this.arancelseguro = arancelseguro;
        this.arancelgastos = arancelgastos;
        this.arancelaporte = arancelaporte;
        this.arancelsolidaridad = arancelsolidaridad;
        this.arancelcobrador = arancelcobrador;
        this.arancelfondoproteccion = arancelfondoproteccion;
        this.comisiones = comisiones;
        this.segurovida = segurovida;
        this.codasesor = codasesor;
        this.codigoordencompra = codigoordencompra;
        this.codigoordencredito = codigoordencredito;
        this.codigoaporte = codigoaporte;
        this.codigosolidaridad = codigosolidaridad;
        this.codigointeres = codigointeres;
        this.codigocuotasocial = codigocuotasocial;
        this.salario_minimo = salario_minimo;
        this.porcentaje_ips = porcentaje_ips;
        this.porcentaje_bonificacion = porcentaje_bonificacion;
        this.importeaporte = importeaporte;
        this.importesolidaridad = importesolidaridad;
        this.importecuotasocial = importecuotasocial;
        this.horas_extras_diurnas_habiles = horas_extras_diurnas_habiles;
        this.horas_extras_nocturnas = horas_extras_nocturnas;
        this.codalquiler = codalquiler;
        this.codgarage = codgarage;
        this.codgarantia = codgarantia;
        this.codexpensa = codexpensa;
        this.codcomision = codcomision;
        this.codmulta = codmulta;
        this.codfondo = codfondo;
        this.codllave = codllave;
        this.codotros = codotros;
        this.arancelrentafija = arancelrentafija;
        this.arancelrentavariable = arancelrentavariable;
        this.limitecomision = limitecomision;
        this.fondocomision = fondocomision;
        this.conceptobolsa = conceptobolsa;
        this.conceptocomision = conceptocomision;
        this.conceptolimite = conceptolimite;
        this.conceptofondogarantia = conceptofondogarantia;
        this.conceptofondogarantiausd = conceptofondogarantiausd;
        this.conceptobolsausd = conceptobolsausd;
        this.conceptocomisionusd = conceptocomisionusd;
        this.conceptolimiteusd = conceptolimiteusd;
        this.interesmora = interesmora;
        this.bancard_tpago_recepcion = bancard_tpago_recepcion;
        this.bancard_tpago_linkpago_crear = bancard_tpago_linkpago_crear;
        this.bancard_tpago_linkpago_reversar_hook_alias = bancard_tpago_linkpago_reversar_hook_alias;
        this.bancard_tpago_link_suscription_crear = bancard_tpago_link_suscription_crear;
        this.bancard_tpago_link_suscription_recuperar = bancard_tpago_link_suscription_recuperar;
        this.bancard_tpago_link_suscription_actualizar = bancard_tpago_link_suscription_actualizar;
        this.bancard_tpago_link_suscription_cancelar = bancard_tpago_link_suscription_cancelar;
        this.bancard_tpago_link_suscription_reintentarpago = bancard_tpago_link_suscription_reintentarpago;
        this.bancard_tpago_link_suscription_adelantarpago = bancard_tpago_link_suscription_adelantarpago;
        this.bancard_tpago_link_suscription_pausarpago = bancard_tpago_link_suscription_pausarpago;
        this.auth_bancard_user = auth_bancard_user;
        this.auth_bancard_password = auth_bancard_password;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public moneda getMonedadefecto() {
        return monedadefecto;
    }

    public void setMonedadefecto(moneda monedadefecto) {
        this.monedadefecto = monedadefecto;
    }

    public sucursal getSucursaldefecto() {
        return sucursaldefecto;
    }

    public void setSucursaldefecto(sucursal sucursaldefecto) {
        this.sucursaldefecto = sucursaldefecto;
    }

    public int getPeriodoactivo() {
        return periodoactivo;
    }

    public void setPeriodoactivo(int periodoactivo) {
        this.periodoactivo = periodoactivo;
    }

    public int getFacturaratrasado() {
        return facturaratrasado;
    }

    public void setFacturaratrasado(int facturaratrasado) {
        this.facturaratrasado = facturaratrasado;
    }

    public caja getCajadefecto() {
        return cajadefecto;
    }

    public void setCajadefecto(caja cajadefecto) {
        this.cajadefecto = cajadefecto;
    }

    public vendedor getVendedordefecto() {
        return vendedordefecto;
    }

    public void setVendedordefecto(vendedor vendedordefecto) {
        this.vendedordefecto = vendedordefecto;
    }

    public cliente getClientedefecto() {
        return clientedefecto;
    }

    public void setClientedefecto(cliente clientedefecto) {
        this.clientedefecto = clientedefecto;
    }

    public comprobante getComprobantedefecto() {
        return comprobantedefecto;
    }

    public void setComprobantedefecto(comprobante comprobantedefecto) {
        this.comprobantedefecto = comprobantedefecto;
    }

    public int getItemfacturas() {
        return itemfacturas;
    }

    public void setItemfacturas(int itemfacturas) {
        this.itemfacturas = itemfacturas;
    }

    public int getTipo_factura_impresion() {
        return tipo_factura_impresion;
    }

    public void setTipo_factura_impresion(int tipo_factura_impresion) {
        this.tipo_factura_impresion = tipo_factura_impresion;
    }

    public formapago getPagoefectivo() {
        return pagoefectivo;
    }

    public void setPagoefectivo(formapago pagoefectivo) {
        this.pagoefectivo = pagoefectivo;
    }

    public int getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(int giraduria) {
        this.giraduria = giraduria;
    }

    public int getCantidadfactura() {
        return cantidadfactura;
    }

    public void setCantidadfactura(int cantidadfactura) {
        this.cantidadfactura = cantidadfactura;
    }

    public int getModificarprecio() {
        return modificarprecio;
    }

    public void setModificarprecio(int modificarprecio) {
        this.modificarprecio = modificarprecio;
    }

    public int getComentaritem() {
        return comentaritem;
    }

    public void setComentaritem(int comentaritem) {
        this.comentaritem = comentaritem;
    }

    public int getInterfazvtalote() {
        return interfazvtalote;
    }

    public void setInterfazvtalote(int interfazvtalote) {
        this.interfazvtalote = interfazvtalote;
    }

    public int getInterfazpuntovta() {
        return interfazpuntovta;
    }

    public void setInterfazpuntovta(int interfazpuntovta) {
        this.interfazpuntovta = interfazpuntovta;
    }

    public int getProdingreso() {
        return prodingreso;
    }

    public void setProdingreso(int prodingreso) {
        this.prodingreso = prodingreso;
    }

    public int getProdsalida() {
        return prodsalida;
    }

    public void setProdsalida(int prodsalida) {
        this.prodsalida = prodsalida;
    }

    public int getAntesimprimir() {
        return antesimprimir;
    }

    public void setAntesimprimir(int antesimprimir) {
        this.antesimprimir = antesimprimir;
    }

    public String getMenuprincipal() {
        return menuprincipal;
    }

    public void setMenuprincipal(String menuprincipal) {
        this.menuprincipal = menuprincipal;
    }

    public int getImpresorafactura() {
        return impresorafactura;
    }

    public void setImpresorafactura(int impresorafactura) {
        this.impresorafactura = impresorafactura;
    }

    public int getImpresorarecibo() {
        return impresorarecibo;
    }

    public void setImpresorarecibo(int impresorarecibo) {
        this.impresorarecibo = impresorarecibo;
    }

    public int getLectorcodigobarra() {
        return lectorcodigobarra;
    }

    public void setLectorcodigobarra(int lectorcodigobarra) {
        this.lectorcodigobarra = lectorcodigobarra;
    }

    public int getFacturar_s_stock() {
        return facturar_s_stock;
    }

    public void setFacturar_s_stock(int facturar_s_stock) {
        this.facturar_s_stock = facturar_s_stock;
    }

    public int getEmite_retencion() {
        return emite_retencion;
    }

    public void setEmite_retencion(int emite_retencion) {
        this.emite_retencion = emite_retencion;
    }

    public int getCod_retencion() {
        return cod_retencion;
    }

    public void setCod_retencion(int cod_retencion) {
        this.cod_retencion = cod_retencion;
    }

    public int getCentro_costo() {
        return centro_costo;
    }

    public void setCentro_costo(int centro_costo) {
        this.centro_costo = centro_costo;
    }

    public int getUpdatecuotas() {
        return updatecuotas;
    }

    public void setUpdatecuotas(int updatecuotas) {
        this.updatecuotas = updatecuotas;
    }

    public int getVerificar_timbrado() {
        return verificar_timbrado;
    }

    public void setVerificar_timbrado(int verificar_timbrado) {
        this.verificar_timbrado = verificar_timbrado;
    }

    public int getLista_precio() {
        return lista_precio;
    }

    public void setLista_precio(int lista_precio) {
        this.lista_precio = lista_precio;
    }

    public int getBuscarproducto() {
        return buscarproducto;
    }

    public void setBuscarproducto(int buscarproducto) {
        this.buscarproducto = buscarproducto;
    }

    public int getVerificarcuenta() {
        return verificarcuenta;
    }

    public void setVerificarcuenta(int verificarcuenta) {
        this.verificarcuenta = verificarcuenta;
    }

    public int getActualizarprecio() {
        return actualizarprecio;
    }

    public void setActualizarprecio(int actualizarprecio) {
        this.actualizarprecio = actualizarprecio;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getGenerarinteres() {
        return generarinteres;
    }

    public void setGenerarinteres(String generarinteres) {
        this.generarinteres = generarinteres;
    }

    public Date getFechainteres() {
        return fechainteres;
    }

    public void setFechainteres(Date fechainteres) {
        this.fechainteres = fechainteres;
    }

    public BigDecimal getTasainteres() {
        return tasainteres;
    }

    public void setTasainteres(BigDecimal tasainteres) {
        this.tasainteres = tasainteres;
    }

    public int getComprobanteinteres() {
        return comprobanteinteres;
    }

    public void setComprobanteinteres(int comprobanteinteres) {
        this.comprobanteinteres = comprobanteinteres;
    }

    public int getPrecierre() {
        return precierre;
    }

    public void setPrecierre(int precierre) {
        this.precierre = precierre;
    }

    public int getCapital_colocacion() {
        return capital_colocacion;
    }

    public void setCapital_colocacion(int capital_colocacion) {
        this.capital_colocacion = capital_colocacion;
    }

    public int getCartera_propia() {
        return cartera_propia;
    }

    public void setCartera_propia(int cartera_propia) {
        this.cartera_propia = cartera_propia;
    }

    public int getConstrucciones() {
        return construcciones;
    }

    public void setConstrucciones(int construcciones) {
        this.construcciones = construcciones;
    }

    public String getCodmora() {
        return codmora;
    }

    public void setCodmora(String codmora) {
        this.codmora = codmora;
    }

    public String getCodgastos() {
        return codgastos;
    }

    public void setCodgastos(String codgastos) {
        this.codgastos = codgastos;
    }

    public double getPorcentajeiva() {
        return porcentajeiva;
    }

    public void setPorcentajeiva(double porcentajeiva) {
        this.porcentajeiva = porcentajeiva;
    }

    public double getPorcentajeivainteres() {
        return porcentajeivainteres;
    }

    public void setPorcentajeivainteres(double porcentajeivainteres) {
        this.porcentajeivainteres = porcentajeivainteres;
    }

    public int getCobropagoexpress() {
        return cobropagoexpress;
    }

    public void setCobropagoexpress(int cobropagoexpress) {
        this.cobropagoexpress = cobropagoexpress;
    }

    public int getCobrocomanda() {
        return cobrocomanda;
    }

    public void setCobrocomanda(int cobrocomanda) {
        this.cobrocomanda = cobrocomanda;
    }

    public BigDecimal getLimitedescuento() {
        return limitedescuento;
    }

    public void setLimitedescuento(BigDecimal limitedescuento) {
        this.limitedescuento = limitedescuento;
    }

    public int getIvaincluido() {
        return ivaincluido;
    }

    public void setIvaincluido(int ivaincluido) {
        this.ivaincluido = ivaincluido;
    }

    public String getNombrefactura() {
        return nombrefactura;
    }

    public void setNombrefactura(String nombrefactura) {
        this.nombrefactura = nombrefactura;
    }

    public String getNombrepagare() {
        return nombrepagare;
    }

    public void setNombrepagare(String nombrepagare) {
        this.nombrepagare = nombrepagare;
    }

    public String getNombrepagarecuota() {
        return nombrepagarecuota;
    }

    public void setNombrepagarecuota(String nombrepagarecuota) {
        this.nombrepagarecuota = nombrepagarecuota;
    }

    public String getNombrerecibo() {
        return nombrerecibo;
    }

    public void setNombrerecibo(String nombrerecibo) {
        this.nombrerecibo = nombrerecibo;
    }

    public String getNombresolicitud() {
        return nombresolicitud;
    }

    public void setNombresolicitud(String nombresolicitud) {
        this.nombresolicitud = nombresolicitud;
    }

    public String getNombreliquidacionph() {
        return nombreliquidacionph;
    }

    public void setNombreliquidacionph(String nombreliquidacionph) {
        this.nombreliquidacionph = nombreliquidacionph;
    }

    public String getNombreliquidacion() {
        return nombreliquidacion;
    }

    public void setNombreliquidacion(String nombreliquidacion) {
        this.nombreliquidacion = nombreliquidacion;
    }

    public String getNombreliquidacioncheques() {
        return nombreliquidacioncheques;
    }

    public void setNombreliquidacioncheques(String nombreliquidacioncheques) {
        this.nombreliquidacioncheques = nombreliquidacioncheques;
    }

    public String getNombrecontrato1() {
        return nombrecontrato1;
    }

    public void setNombrecontrato1(String nombrecontrato1) {
        this.nombrecontrato1 = nombrecontrato1;
    }

    public String getNombrecontrato2() {
        return nombrecontrato2;
    }

    public void setNombrecontrato2(String nombrecontrato2) {
        this.nombrecontrato2 = nombrecontrato2;
    }

    public String getNombrecontrato3() {
        return nombrecontrato3;
    }

    public void setNombrecontrato3(String nombrecontrato3) {
        this.nombrecontrato3 = nombrecontrato3;
    }

    public String getNombrecontrato4() {
        return nombrecontrato4;
    }

    public void setNombrecontrato4(String nombrecontrato4) {
        this.nombrecontrato4 = nombrecontrato4;
    }

    public String getCodcapital() {
        return codcapital;
    }

    public void setCodcapital(String codcapital) {
        this.codcapital = codcapital;
    }

    public String getCodinteres() {
        return codinteres;
    }

    public void setCodinteres(String codinteres) {
        this.codinteres = codinteres;
    }

    public String getCodcolocacion() {
        return codcolocacion;
    }

    public void setCodcolocacion(String codcolocacion) {
        this.codcolocacion = codcolocacion;
    }

    public String getGastospp() {
        return gastospp;
    }

    public void setGastospp(String gastospp) {
        this.gastospp = gastospp;
    }

    public String getGastosph() {
        return gastosph;
    }

    public void setGastosph(String gastosph) {
        this.gastosph = gastosph;
    }

    public String getGastosdd() {
        return gastosdd;
    }

    public void setGastosdd(String gastosdd) {
        this.gastosdd = gastosdd;
    }

    public String getCodpunitorio() {
        return codpunitorio;
    }

    public void setCodpunitorio(String codpunitorio) {
        this.codpunitorio = codpunitorio;
    }

    public double getCobrador() {
        return cobrador;
    }

    public void setCobrador(double cobrador) {
        this.cobrador = cobrador;
    }

    public BigDecimal getAranceliva() {
        return aranceliva;
    }

    public void setAranceliva(BigDecimal aranceliva) {
        this.aranceliva = aranceliva;
    }

    public BigDecimal getArancelbanco() {
        return arancelbanco;
    }

    public void setArancelbanco(BigDecimal arancelbanco) {
        this.arancelbanco = arancelbanco;
    }

    public double getCapitalizacionaporte() {
        return capitalizacionaporte;
    }

    public void setCapitalizacionaporte(double capitalizacionaporte) {
        this.capitalizacionaporte = capitalizacionaporte;
    }

    public BigDecimal getArancelseguro() {
        return arancelseguro;
    }

    public void setArancelseguro(BigDecimal arancelseguro) {
        this.arancelseguro = arancelseguro;
    }

    public BigDecimal getArancelgastos() {
        return arancelgastos;
    }

    public void setArancelgastos(BigDecimal arancelgastos) {
        this.arancelgastos = arancelgastos;
    }

    public BigDecimal getArancelaporte() {
        return arancelaporte;
    }

    public void setArancelaporte(BigDecimal arancelaporte) {
        this.arancelaporte = arancelaporte;
    }

    public BigDecimal getArancelsolidaridad() {
        return arancelsolidaridad;
    }

    public void setArancelsolidaridad(BigDecimal arancelsolidaridad) {
        this.arancelsolidaridad = arancelsolidaridad;
    }

    public BigDecimal getArancelcobrador() {
        return arancelcobrador;
    }

    public void setArancelcobrador(BigDecimal arancelcobrador) {
        this.arancelcobrador = arancelcobrador;
    }

    public BigDecimal getArancelfondoproteccion() {
        return arancelfondoproteccion;
    }

    public void setArancelfondoproteccion(BigDecimal arancelfondoproteccion) {
        this.arancelfondoproteccion = arancelfondoproteccion;
    }

    public String getComisiones() {
        return comisiones;
    }

    public void setComisiones(String comisiones) {
        this.comisiones = comisiones;
    }

    public String getSegurovida() {
        return segurovida;
    }

    public void setSegurovida(String segurovida) {
        this.segurovida = segurovida;
    }

    public int getCodasesor() {
        return codasesor;
    }

    public void setCodasesor(int codasesor) {
        this.codasesor = codasesor;
    }

    public int getCodigoordencompra() {
        return codigoordencompra;
    }

    public void setCodigoordencompra(int codigoordencompra) {
        this.codigoordencompra = codigoordencompra;
    }

    public int getCodigoordencredito() {
        return codigoordencredito;
    }

    public void setCodigoordencredito(int codigoordencredito) {
        this.codigoordencredito = codigoordencredito;
    }

    public int getCodigoaporte() {
        return codigoaporte;
    }

    public void setCodigoaporte(int codigoaporte) {
        this.codigoaporte = codigoaporte;
    }

    public int getCodigosolidaridad() {
        return codigosolidaridad;
    }

    public void setCodigosolidaridad(int codigosolidaridad) {
        this.codigosolidaridad = codigosolidaridad;
    }

    public int getCodigointeres() {
        return codigointeres;
    }

    public void setCodigointeres(int codigointeres) {
        this.codigointeres = codigointeres;
    }

    public int getCodigocuotasocial() {
        return codigocuotasocial;
    }

    public void setCodigocuotasocial(int codigocuotasocial) {
        this.codigocuotasocial = codigocuotasocial;
    }

    public double getSalario_minimo() {
        return salario_minimo;
    }

    public void setSalario_minimo(double salario_minimo) {
        this.salario_minimo = salario_minimo;
    }

    public double getPorcentaje_ips() {
        return porcentaje_ips;
    }

    public void setPorcentaje_ips(double porcentaje_ips) {
        this.porcentaje_ips = porcentaje_ips;
    }

    public double getPorcentaje_bonificacion() {
        return porcentaje_bonificacion;
    }

    public void setPorcentaje_bonificacion(double porcentaje_bonificacion) {
        this.porcentaje_bonificacion = porcentaje_bonificacion;
    }

    public BigDecimal getImporteaporte() {
        return importeaporte;
    }

    public void setImporteaporte(BigDecimal importeaporte) {
        this.importeaporte = importeaporte;
    }

    public BigDecimal getImportesolidaridad() {
        return importesolidaridad;
    }

    public void setImportesolidaridad(BigDecimal importesolidaridad) {
        this.importesolidaridad = importesolidaridad;
    }

    public BigDecimal getImportecuotasocial() {
        return importecuotasocial;
    }

    public void setImportecuotasocial(BigDecimal importecuotasocial) {
        this.importecuotasocial = importecuotasocial;
    }

    public double getHoras_extras_diurnas_habiles() {
        return horas_extras_diurnas_habiles;
    }

    public void setHoras_extras_diurnas_habiles(double horas_extras_diurnas_habiles) {
        this.horas_extras_diurnas_habiles = horas_extras_diurnas_habiles;
    }

    public double getHoras_extras_nocturnas() {
        return horas_extras_nocturnas;
    }

    public void setHoras_extras_nocturnas(double horas_extras_nocturnas) {
        this.horas_extras_nocturnas = horas_extras_nocturnas;
    }

    public String getCodalquiler() {
        return codalquiler;
    }

    public void setCodalquiler(String codalquiler) {
        this.codalquiler = codalquiler;
    }

    public String getCodgarage() {
        return codgarage;
    }

    public void setCodgarage(String codgarage) {
        this.codgarage = codgarage;
    }

    public String getCodgarantia() {
        return codgarantia;
    }

    public void setCodgarantia(String codgarantia) {
        this.codgarantia = codgarantia;
    }

    public String getCodexpensa() {
        return codexpensa;
    }

    public void setCodexpensa(String codexpensa) {
        this.codexpensa = codexpensa;
    }

    public String getCodcomision() {
        return codcomision;
    }

    public void setCodcomision(String codcomision) {
        this.codcomision = codcomision;
    }

    public String getCodmulta() {
        return codmulta;
    }

    public void setCodmulta(String codmulta) {
        this.codmulta = codmulta;
    }

    public String getCodfondo() {
        return codfondo;
    }

    public void setCodfondo(String codfondo) {
        this.codfondo = codfondo;
    }

    public String getCodllave() {
        return codllave;
    }

    public void setCodllave(String codllave) {
        this.codllave = codllave;
    }

    public String getCodotros() {
        return codotros;
    }

    public void setCodotros(String codotros) {
        this.codotros = codotros;
    }

    public Double getArancelrentafija() {
        return arancelrentafija;
    }

    public void setArancelrentafija(Double arancelrentafija) {
        this.arancelrentafija = arancelrentafija;
    }

    public Double getArancelrentavariable() {
        return arancelrentavariable;
    }

    public void setArancelrentavariable(Double arancelrentavariable) {
        this.arancelrentavariable = arancelrentavariable;
    }

    public Double getLimitecomision() {
        return limitecomision;
    }

    public void setLimitecomision(Double limitecomision) {
        this.limitecomision = limitecomision;
    }

    public Double getFondocomision() {
        return fondocomision;
    }

    public void setFondocomision(Double fondocomision) {
        this.fondocomision = fondocomision;
    }

    public String getConceptobolsa() {
        return conceptobolsa;
    }

    public void setConceptobolsa(String conceptobolsa) {
        this.conceptobolsa = conceptobolsa;
    }

    public String getConceptocomision() {
        return conceptocomision;
    }

    public void setConceptocomision(String conceptocomision) {
        this.conceptocomision = conceptocomision;
    }

    public String getConceptolimite() {
        return conceptolimite;
    }

    public void setConceptolimite(String conceptolimite) {
        this.conceptolimite = conceptolimite;
    }

    public String getConceptofondogarantia() {
        return conceptofondogarantia;
    }

    public void setConceptofondogarantia(String conceptofondogarantia) {
        this.conceptofondogarantia = conceptofondogarantia;
    }

    public String getConceptofondogarantiausd() {
        return conceptofondogarantiausd;
    }

    public void setConceptofondogarantiausd(String conceptofondogarantiausd) {
        this.conceptofondogarantiausd = conceptofondogarantiausd;
    }

    public String getConceptobolsausd() {
        return conceptobolsausd;
    }

    public void setConceptobolsausd(String conceptobolsausd) {
        this.conceptobolsausd = conceptobolsausd;
    }

    public String getConceptocomisionusd() {
        return conceptocomisionusd;
    }

    public void setConceptocomisionusd(String conceptocomisionusd) {
        this.conceptocomisionusd = conceptocomisionusd;
    }

    public String getConceptolimiteusd() {
        return conceptolimiteusd;
    }

    public void setConceptolimiteusd(String conceptolimiteusd) {
        this.conceptolimiteusd = conceptolimiteusd;
    }

    public Double getInteresmora() {
        return interesmora;
    }

    public void setInteresmora(Double interesmora) {
        this.interesmora = interesmora;
    }

    public String getBancard_tpago_recepcion() {
        return bancard_tpago_recepcion;
    }

    public void setBancard_tpago_recepcion(String bancard_tpago_recepcion) {
        this.bancard_tpago_recepcion = bancard_tpago_recepcion;
    }

    public String getBancard_tpago_linkpago_crear() {
        return bancard_tpago_linkpago_crear;
    }

    public void setBancard_tpago_linkpago_crear(String bancard_tpago_linkpago_crear) {
        this.bancard_tpago_linkpago_crear = bancard_tpago_linkpago_crear;
    }

    public String getBancard_tpago_linkpago_reversar_hook_alias() {
        return bancard_tpago_linkpago_reversar_hook_alias;
    }

    public void setBancard_tpago_linkpago_reversar_hook_alias(String bancard_tpago_linkpago_reversar_hook_alias) {
        this.bancard_tpago_linkpago_reversar_hook_alias = bancard_tpago_linkpago_reversar_hook_alias;
    }

    public String getBancard_tpago_link_suscription_crear() {
        return bancard_tpago_link_suscription_crear;
    }

    public void setBancard_tpago_link_suscription_crear(String bancard_tpago_link_suscription_crear) {
        this.bancard_tpago_link_suscription_crear = bancard_tpago_link_suscription_crear;
    }

    public String getBancard_tpago_link_suscription_recuperar() {
        return bancard_tpago_link_suscription_recuperar;
    }

    public void setBancard_tpago_link_suscription_recuperar(String bancard_tpago_link_suscription_recuperar) {
        this.bancard_tpago_link_suscription_recuperar = bancard_tpago_link_suscription_recuperar;
    }

    public String getBancard_tpago_link_suscription_actualizar() {
        return bancard_tpago_link_suscription_actualizar;
    }

    public void setBancard_tpago_link_suscription_actualizar(String bancard_tpago_link_suscription_actualizar) {
        this.bancard_tpago_link_suscription_actualizar = bancard_tpago_link_suscription_actualizar;
    }

    public String getBancard_tpago_link_suscription_cancelar() {
        return bancard_tpago_link_suscription_cancelar;
    }

    public void setBancard_tpago_link_suscription_cancelar(String bancard_tpago_link_suscription_cancelar) {
        this.bancard_tpago_link_suscription_cancelar = bancard_tpago_link_suscription_cancelar;
    }

    public String getBancard_tpago_link_suscription_reintentarpago() {
        return bancard_tpago_link_suscription_reintentarpago;
    }

    public void setBancard_tpago_link_suscription_reintentarpago(String bancard_tpago_link_suscription_reintentarpago) {
        this.bancard_tpago_link_suscription_reintentarpago = bancard_tpago_link_suscription_reintentarpago;
    }

    public String getBancard_tpago_link_suscription_adelantarpago() {
        return bancard_tpago_link_suscription_adelantarpago;
    }

    public void setBancard_tpago_link_suscription_adelantarpago(String bancard_tpago_link_suscription_adelantarpago) {
        this.bancard_tpago_link_suscription_adelantarpago = bancard_tpago_link_suscription_adelantarpago;
    }

    public String getBancard_tpago_link_suscription_pausarpago() {
        return bancard_tpago_link_suscription_pausarpago;
    }

    public void setBancard_tpago_link_suscription_pausarpago(String bancard_tpago_link_suscription_pausarpago) {
        this.bancard_tpago_link_suscription_pausarpago = bancard_tpago_link_suscription_pausarpago;
    }

    public String getAuth_bancard_user() {
        return auth_bancard_user;
    }

    public void setAuth_bancard_user(String auth_bancard_user) {
        this.auth_bancard_user = auth_bancard_user;
    }

    public String getAuth_bancard_password() {
        return auth_bancard_password;
    }

    public void setAuth_bancard_password(String auth_bancard_password) {
        this.auth_bancard_password = auth_bancard_password;
    }

    
}

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
 * @author SERVIDOR
 */
public class venta {

    String creferencia;
    Date fecha;
    Double factura;
    Date vencimiento;
    cliente cliente;
    sucursal sucursal;
    moneda moneda;
    giraduria giraduria;
    comprobante comprobante;
    BigDecimal cotizacion;
    vendedor vendedor;
    rubro rubro;
    caja caja;
    BigDecimal supago;
    BigDecimal sucambio;
    BigDecimal totalbruto;
    BigDecimal totaldescuento;
    BigDecimal exentas;
    BigDecimal gravadas10;
    BigDecimal gravadas5;
    BigDecimal totalneto;
    int cuotas;
    int anuladopor;
    Date fechaanulado;
    BigDecimal pagos;
    BigDecimal autos_recibidos;
    BigDecimal comision_vendedor;
    int registro;
    int preventa;
    int cierre;
    BigDecimal financiado;
    String observacion;
    int enviactacte;
    int remisionnro;
    String marcavehiculo;
    String nombreconductor;
    String direccionconductor;
    String chapa;
    String cedula;
    Date fechainitraslado;
    Date fechafintraslado;
    String llegadachar;
    String nombregarante;
    String direcciongarante;
    String cedulagarante;
    int turno;
    int idusuario;
    int ordencompra;
    int contrato;
    int iddireccion;
    Date vencimientotimbrado;
    int nrotimbrado;
    int centro;
    int comanda;
    int parallevar;
    String codprodventa;
    String nombreproductoventa;
    int codrubroventa;
    String nombrerubroventa;
    BigDecimal cantidadventa;
    BigDecimal precioventa;
    BigDecimal promedio;
    int porcentaje;
    String formatofactura;
    int listaprecio;
    detalle_forma_cobro detalle_forma_cobro;
    String ticketold;
    String nombreobra;
    String horagrabado;
    int prestamo;
    String idprestamo;
    double costo;
    double costomercaderia;
    double ventasnetas;
    double utilidades;

    public venta() {

    }

    public venta(String creferencia, Date fecha, Double factura, Date vencimiento, cliente cliente, sucursal sucursal, moneda moneda, giraduria giraduria, comprobante comprobante, BigDecimal cotizacion, vendedor vendedor, rubro rubro, caja caja, BigDecimal supago, BigDecimal sucambio, BigDecimal totalbruto, BigDecimal totaldescuento, BigDecimal exentas, BigDecimal gravadas10, BigDecimal gravadas5, BigDecimal totalneto, int cuotas, int anuladopor, Date fechaanulado, BigDecimal pagos, BigDecimal autos_recibidos, BigDecimal comision_vendedor, int registro, int preventa, int cierre, BigDecimal financiado, String observacion, int enviactacte, int remisionnro, String marcavehiculo, String nombreconductor, String direccionconductor, String chapa, String cedula, Date fechainitraslado, Date fechafintraslado, String llegadachar, String nombregarante, String direcciongarante, String cedulagarante, int turno, int idusuario, int ordencompra, int contrato, int iddireccion, Date vencimientotimbrado, int nrotimbrado, int centro, int comanda, int parallevar, String codprodventa, String nombreproductoventa, int codrubroventa, String nombrerubroventa, BigDecimal cantidadventa, BigDecimal precioventa, BigDecimal promedio, int porcentaje, String formatofactura, int listaprecio, detalle_forma_cobro detalle_forma_cobro, String ticketold, String nombreobra, String horagrabado, int prestamo, String idprestamo, double costo, double costomercaderia, double ventasnetas, double utilidades) {
        this.creferencia = creferencia;
        this.fecha = fecha;
        this.factura = factura;
        this.vencimiento = vencimiento;
        this.cliente = cliente;
        this.sucursal = sucursal;
        this.moneda = moneda;
        this.giraduria = giraduria;
        this.comprobante = comprobante;
        this.cotizacion = cotizacion;
        this.vendedor = vendedor;
        this.rubro = rubro;
        this.caja = caja;
        this.supago = supago;
        this.sucambio = sucambio;
        this.totalbruto = totalbruto;
        this.totaldescuento = totaldescuento;
        this.exentas = exentas;
        this.gravadas10 = gravadas10;
        this.gravadas5 = gravadas5;
        this.totalneto = totalneto;
        this.cuotas = cuotas;
        this.anuladopor = anuladopor;
        this.fechaanulado = fechaanulado;
        this.pagos = pagos;
        this.autos_recibidos = autos_recibidos;
        this.comision_vendedor = comision_vendedor;
        this.registro = registro;
        this.preventa = preventa;
        this.cierre = cierre;
        this.financiado = financiado;
        this.observacion = observacion;
        this.enviactacte = enviactacte;
        this.remisionnro = remisionnro;
        this.marcavehiculo = marcavehiculo;
        this.nombreconductor = nombreconductor;
        this.direccionconductor = direccionconductor;
        this.chapa = chapa;
        this.cedula = cedula;
        this.fechainitraslado = fechainitraslado;
        this.fechafintraslado = fechafintraslado;
        this.llegadachar = llegadachar;
        this.nombregarante = nombregarante;
        this.direcciongarante = direcciongarante;
        this.cedulagarante = cedulagarante;
        this.turno = turno;
        this.idusuario = idusuario;
        this.ordencompra = ordencompra;
        this.contrato = contrato;
        this.iddireccion = iddireccion;
        this.vencimientotimbrado = vencimientotimbrado;
        this.nrotimbrado = nrotimbrado;
        this.centro = centro;
        this.comanda = comanda;
        this.parallevar = parallevar;
        this.codprodventa = codprodventa;
        this.nombreproductoventa = nombreproductoventa;
        this.codrubroventa = codrubroventa;
        this.nombrerubroventa = nombrerubroventa;
        this.cantidadventa = cantidadventa;
        this.precioventa = precioventa;
        this.promedio = promedio;
        this.porcentaje = porcentaje;
        this.formatofactura = formatofactura;
        this.listaprecio = listaprecio;
        this.detalle_forma_cobro = detalle_forma_cobro;
        this.ticketold = ticketold;
        this.nombreobra = nombreobra;
        this.horagrabado = horagrabado;
        this.prestamo = prestamo;
        this.idprestamo = idprestamo;
        this.costo = costo;
        this.costomercaderia = costomercaderia;
        this.ventasnetas = ventasnetas;
        this.utilidades = utilidades;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getFactura() {
        return factura;
    }

    public void setFactura(Double factura) {
        this.factura = factura;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public giraduria getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(giraduria giraduria) {
        this.giraduria = giraduria;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public BigDecimal getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(BigDecimal cotizacion) {
        this.cotizacion = cotizacion;
    }

    public vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public rubro getRubro() {
        return rubro;
    }

    public void setRubro(rubro rubro) {
        this.rubro = rubro;
    }

    public caja getCaja() {
        return caja;
    }

    public void setCaja(caja caja) {
        this.caja = caja;
    }

    public BigDecimal getSupago() {
        return supago;
    }

    public void setSupago(BigDecimal supago) {
        this.supago = supago;
    }

    public BigDecimal getSucambio() {
        return sucambio;
    }

    public void setSucambio(BigDecimal sucambio) {
        this.sucambio = sucambio;
    }

    public BigDecimal getTotalbruto() {
        return totalbruto;
    }

    public void setTotalbruto(BigDecimal totalbruto) {
        this.totalbruto = totalbruto;
    }

    public BigDecimal getTotaldescuento() {
        return totaldescuento;
    }

    public void setTotaldescuento(BigDecimal totaldescuento) {
        this.totaldescuento = totaldescuento;
    }

    public BigDecimal getExentas() {
        return exentas;
    }

    public void setExentas(BigDecimal exentas) {
        this.exentas = exentas;
    }

    public BigDecimal getGravadas10() {
        return gravadas10;
    }

    public void setGravadas10(BigDecimal gravadas10) {
        this.gravadas10 = gravadas10;
    }

    public BigDecimal getGravadas5() {
        return gravadas5;
    }

    public void setGravadas5(BigDecimal gravadas5) {
        this.gravadas5 = gravadas5;
    }

    public BigDecimal getTotalneto() {
        return totalneto;
    }

    public void setTotalneto(BigDecimal totalneto) {
        this.totalneto = totalneto;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public int getAnuladopor() {
        return anuladopor;
    }

    public void setAnuladopor(int anuladopor) {
        this.anuladopor = anuladopor;
    }

    public Date getFechaanulado() {
        return fechaanulado;
    }

    public void setFechaanulado(Date fechaanulado) {
        this.fechaanulado = fechaanulado;
    }

    public BigDecimal getPagos() {
        return pagos;
    }

    public void setPagos(BigDecimal pagos) {
        this.pagos = pagos;
    }

    public BigDecimal getAutos_recibidos() {
        return autos_recibidos;
    }

    public void setAutos_recibidos(BigDecimal autos_recibidos) {
        this.autos_recibidos = autos_recibidos;
    }

    public BigDecimal getComision_vendedor() {
        return comision_vendedor;
    }

    public void setComision_vendedor(BigDecimal comision_vendedor) {
        this.comision_vendedor = comision_vendedor;
    }

    public int getRegistro() {
        return registro;
    }

    public void setRegistro(int registro) {
        this.registro = registro;
    }

    public int getPreventa() {
        return preventa;
    }

    public void setPreventa(int preventa) {
        this.preventa = preventa;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public BigDecimal getFinanciado() {
        return financiado;
    }

    public void setFinanciado(BigDecimal financiado) {
        this.financiado = financiado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getEnviactacte() {
        return enviactacte;
    }

    public void setEnviactacte(int enviactacte) {
        this.enviactacte = enviactacte;
    }

    public int getRemisionnro() {
        return remisionnro;
    }

    public void setRemisionnro(int remisionnro) {
        this.remisionnro = remisionnro;
    }

    public String getMarcavehiculo() {
        return marcavehiculo;
    }

    public void setMarcavehiculo(String marcavehiculo) {
        this.marcavehiculo = marcavehiculo;
    }

    public String getNombreconductor() {
        return nombreconductor;
    }

    public void setNombreconductor(String nombreconductor) {
        this.nombreconductor = nombreconductor;
    }

    public String getDireccionconductor() {
        return direccionconductor;
    }

    public void setDireccionconductor(String direccionconductor) {
        this.direccionconductor = direccionconductor;
    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getFechainitraslado() {
        return fechainitraslado;
    }

    public void setFechainitraslado(Date fechainitraslado) {
        this.fechainitraslado = fechainitraslado;
    }

    public Date getFechafintraslado() {
        return fechafintraslado;
    }

    public void setFechafintraslado(Date fechafintraslado) {
        this.fechafintraslado = fechafintraslado;
    }

    public String getLlegadachar() {
        return llegadachar;
    }

    public void setLlegadachar(String llegadachar) {
        this.llegadachar = llegadachar;
    }

    public String getNombregarante() {
        return nombregarante;
    }

    public void setNombregarante(String nombregarante) {
        this.nombregarante = nombregarante;
    }

    public String getDirecciongarante() {
        return direcciongarante;
    }

    public void setDirecciongarante(String direcciongarante) {
        this.direcciongarante = direcciongarante;
    }

    public String getCedulagarante() {
        return cedulagarante;
    }

    public void setCedulagarante(String cedulagarante) {
        this.cedulagarante = cedulagarante;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getOrdencompra() {
        return ordencompra;
    }

    public void setOrdencompra(int ordencompra) {
        this.ordencompra = ordencompra;
    }

    public int getContrato() {
        return contrato;
    }

    public void setContrato(int contrato) {
        this.contrato = contrato;
    }

    public int getIddireccion() {
        return iddireccion;
    }

    public void setIddireccion(int iddireccion) {
        this.iddireccion = iddireccion;
    }

    public Date getVencimientotimbrado() {
        return vencimientotimbrado;
    }

    public void setVencimientotimbrado(Date vencimientotimbrado) {
        this.vencimientotimbrado = vencimientotimbrado;
    }

    public int getNrotimbrado() {
        return nrotimbrado;
    }

    public void setNrotimbrado(int nrotimbrado) {
        this.nrotimbrado = nrotimbrado;
    }

    public int getCentro() {
        return centro;
    }

    public void setCentro(int centro) {
        this.centro = centro;
    }

    public int getComanda() {
        return comanda;
    }

    public void setComanda(int comanda) {
        this.comanda = comanda;
    }

    public int getParallevar() {
        return parallevar;
    }

    public void setParallevar(int parallevar) {
        this.parallevar = parallevar;
    }

    public String getCodprodventa() {
        return codprodventa;
    }

    public void setCodprodventa(String codprodventa) {
        this.codprodventa = codprodventa;
    }

    public String getNombreproductoventa() {
        return nombreproductoventa;
    }

    public void setNombreproductoventa(String nombreproductoventa) {
        this.nombreproductoventa = nombreproductoventa;
    }

    public int getCodrubroventa() {
        return codrubroventa;
    }

    public void setCodrubroventa(int codrubroventa) {
        this.codrubroventa = codrubroventa;
    }

    public String getNombrerubroventa() {
        return nombrerubroventa;
    }

    public void setNombrerubroventa(String nombrerubroventa) {
        this.nombrerubroventa = nombrerubroventa;
    }

    public BigDecimal getCantidadventa() {
        return cantidadventa;
    }

    public void setCantidadventa(BigDecimal cantidadventa) {
        this.cantidadventa = cantidadventa;
    }

    public BigDecimal getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(BigDecimal precioventa) {
        this.precioventa = precioventa;
    }

    public BigDecimal getPromedio() {
        return promedio;
    }

    public void setPromedio(BigDecimal promedio) {
        this.promedio = promedio;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getFormatofactura() {
        return formatofactura;
    }

    public void setFormatofactura(String formatofactura) {
        this.formatofactura = formatofactura;
    }

    public int getListaprecio() {
        return listaprecio;
    }

    public void setListaprecio(int listaprecio) {
        this.listaprecio = listaprecio;
    }

    public detalle_forma_cobro getDetalle_forma_cobro() {
        return detalle_forma_cobro;
    }

    public void setDetalle_forma_cobro(detalle_forma_cobro detalle_forma_cobro) {
        this.detalle_forma_cobro = detalle_forma_cobro;
    }

    public String getTicketold() {
        return ticketold;
    }

    public void setTicketold(String ticketold) {
        this.ticketold = ticketold;
    }

    public String getNombreobra() {
        return nombreobra;
    }

    public void setNombreobra(String nombreobra) {
        this.nombreobra = nombreobra;
    }

    public String getHoragrabado() {
        return horagrabado;
    }

    public void setHoragrabado(String horagrabado) {
        this.horagrabado = horagrabado;
    }

    public int getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(int prestamo) {
        this.prestamo = prestamo;
    }

    public String getIdprestamo() {
        return idprestamo;
    }

    public void setIdprestamo(String idprestamo) {
        this.idprestamo = idprestamo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getCostomercaderia() {
        return costomercaderia;
    }

    public void setCostomercaderia(double costomercaderia) {
        this.costomercaderia = costomercaderia;
    }

    public double getVentasnetas() {
        return ventasnetas;
    }

    public void setVentasnetas(double ventasnetas) {
        this.ventasnetas = ventasnetas;
    }

    public double getUtilidades() {
        return utilidades;
    }

    public void setUtilidades(double utilidades) {
        this.utilidades = utilidades;
    }


}

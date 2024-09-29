/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Pc_Server
 */
public class retenciones_ventas {

    String creferencia;
    String nroretencion;
    String nrofactura;
    sucursal sucursal;
    Date fecha;
    cliente cliente;
    Double totalneto;
    Double importe_sin_iva;
    Double importe_iva;
    Double importe_gravado_total;
    Double porcentaje_retencion;
    Double valor_retencion;
    moneda moneda;
    Double cotizacion;
    String observacion;
    int enviarcta;
    int generarasiento;
    Double asiento;
    int usuarioalta;
    Date fechaalta;
    int usuarioupdate;
    Date fechaupdate;

    public retenciones_ventas() {

    }

    public retenciones_ventas(String creferencia, String nroretencion, String nrofactura, sucursal sucursal, Date fecha, cliente cliente, Double totalneto, Double importe_sin_iva, Double importe_iva, Double importe_gravado_total, Double porcentaje_retencion, Double valor_retencion, moneda moneda, Double cotizacion, String observacion, int enviarcta, int generarasiento, Double asiento, int usuarioalta, Date fechaalta, int usuarioupdate, Date fechaupdate) {
        this.creferencia = creferencia;
        this.nroretencion = nroretencion;
        this.nrofactura = nrofactura;
        this.sucursal = sucursal;
        this.fecha = fecha;
        this.cliente = cliente;
        this.totalneto = totalneto;
        this.importe_sin_iva = importe_sin_iva;
        this.importe_iva = importe_iva;
        this.importe_gravado_total = importe_gravado_total;
        this.porcentaje_retencion = porcentaje_retencion;
        this.valor_retencion = valor_retencion;
        this.moneda = moneda;
        this.cotizacion = cotizacion;
        this.observacion = observacion;
        this.enviarcta = enviarcta;
        this.generarasiento = generarasiento;
        this.asiento = asiento;
        this.usuarioalta = usuarioalta;
        this.fechaalta = fechaalta;
        this.usuarioupdate = usuarioupdate;
        this.fechaupdate = fechaupdate;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public String getNroretencion() {
        return nroretencion;
    }

    public void setNroretencion(String nroretencion) {
        this.nroretencion = nroretencion;
    }

    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(String nrofactura) {
        this.nrofactura = nrofactura;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public Double getTotalneto() {
        return totalneto;
    }

    public void setTotalneto(Double totalneto) {
        this.totalneto = totalneto;
    }

    public Double getImporte_sin_iva() {
        return importe_sin_iva;
    }

    public void setImporte_sin_iva(Double importe_sin_iva) {
        this.importe_sin_iva = importe_sin_iva;
    }

    public Double getImporte_iva() {
        return importe_iva;
    }

    public void setImporte_iva(Double importe_iva) {
        this.importe_iva = importe_iva;
    }

    public Double getImporte_gravado_total() {
        return importe_gravado_total;
    }

    public void setImporte_gravado_total(Double importe_gravado_total) {
        this.importe_gravado_total = importe_gravado_total;
    }

    public Double getPorcentaje_retencion() {
        return porcentaje_retencion;
    }

    public void setPorcentaje_retencion(Double porcentaje_retencion) {
        this.porcentaje_retencion = porcentaje_retencion;
    }

    public Double getValor_retencion() {
        return valor_retencion;
    }

    public void setValor_retencion(Double valor_retencion) {
        this.valor_retencion = valor_retencion;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public Double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getEnviarcta() {
        return enviarcta;
    }

    public void setEnviarcta(int enviarcta) {
        this.enviarcta = enviarcta;
    }

    public int getGenerarasiento() {
        return generarasiento;
    }

    public void setGenerarasiento(int generarasiento) {
        this.generarasiento = generarasiento;
    }

    public Double getAsiento() {
        return asiento;
    }

    public void setAsiento(Double asiento) {
        this.asiento = asiento;
    }

    public int getUsuarioalta() {
        return usuarioalta;
    }

    public void setUsuarioalta(int usuarioalta) {
        this.usuarioalta = usuarioalta;
    }

    public Date getFechaalta() {
        return fechaalta;
    }

    public void setFechaalta(Date fechaalta) {
        this.fechaalta = fechaalta;
    }

    public int getUsuarioupdate() {
        return usuarioupdate;
    }

    public void setUsuarioupdate(int usuarioupdate) {
        this.usuarioupdate = usuarioupdate;
    }

    public Date getFechaupdate() {
        return fechaupdate;
    }

    public void setFechaupdate(Date fechaupdate) {
        this.fechaupdate = fechaupdate;
    }

    
    
}

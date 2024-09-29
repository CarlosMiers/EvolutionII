/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class gastos_compras {
    
    double fondofijo;
    String nrofactura;
    String formatofactura;
    Date fechafactura;
    Date vencimiento;
    int tipo;
    comprobante Comprobante;
    rubro_compra concepto;
    moneda moneda;
    String timbrado;
    Date vencimientotimbrado;
    proveedor proveedor;
    double exentas;
    double gravadas10;
    double iva10;
    double gravadas5;
    double iva5;
    double totalneto;
    String observacion;
    String creferencia;
    double cotizacion;
    plan idca;
    int pagado;
    public gastos_compras() {

    }

    public gastos_compras(double fondofijo, String nrofactura, String formatofactura, Date fechafactura, Date vencimiento, int tipo, comprobante Comprobante, rubro_compra concepto, moneda moneda, String timbrado, Date vencimientotimbrado, proveedor proveedor, double exentas, double gravadas10, double iva10, double gravadas5, double iva5, double totalneto, String observacion, String creferencia, double cotizacion, plan idca, int pagado) {
        this.fondofijo = fondofijo;
        this.nrofactura = nrofactura;
        this.formatofactura = formatofactura;
        this.fechafactura = fechafactura;
        this.vencimiento = vencimiento;
        this.tipo = tipo;
        this.Comprobante = Comprobante;
        this.concepto = concepto;
        this.moneda = moneda;
        this.timbrado = timbrado;
        this.vencimientotimbrado = vencimientotimbrado;
        this.proveedor = proveedor;
        this.exentas = exentas;
        this.gravadas10 = gravadas10;
        this.iva10 = iva10;
        this.gravadas5 = gravadas5;
        this.iva5 = iva5;
        this.totalneto = totalneto;
        this.observacion = observacion;
        this.creferencia = creferencia;
        this.cotizacion = cotizacion;
        this.idca = idca;
        this.pagado = pagado;
    }

    public double getFondofijo() {
        return fondofijo;
    }

    public void setFondofijo(double fondofijo) {
        this.fondofijo = fondofijo;
    }

    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(String nrofactura) {
        this.nrofactura = nrofactura;
    }

    public String getFormatofactura() {
        return formatofactura;
    }

    public void setFormatofactura(String formatofactura) {
        this.formatofactura = formatofactura;
    }

    public Date getFechafactura() {
        return fechafactura;
    }

    public void setFechafactura(Date fechafactura) {
        this.fechafactura = fechafactura;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public comprobante getComprobante() {
        return Comprobante;
    }

    public void setComprobante(comprobante Comprobante) {
        this.Comprobante = Comprobante;
    }

    public rubro_compra getConcepto() {
        return concepto;
    }

    public void setConcepto(rubro_compra concepto) {
        this.concepto = concepto;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public String getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(String timbrado) {
        this.timbrado = timbrado;
    }

    public Date getVencimientotimbrado() {
        return vencimientotimbrado;
    }

    public void setVencimientotimbrado(Date vencimientotimbrado) {
        this.vencimientotimbrado = vencimientotimbrado;
    }

    public proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public double getExentas() {
        return exentas;
    }

    public void setExentas(double exentas) {
        this.exentas = exentas;
    }

    public double getGravadas10() {
        return gravadas10;
    }

    public void setGravadas10(double gravadas10) {
        this.gravadas10 = gravadas10;
    }

    public double getIva10() {
        return iva10;
    }

    public void setIva10(double iva10) {
        this.iva10 = iva10;
    }

    public double getGravadas5() {
        return gravadas5;
    }

    public void setGravadas5(double gravadas5) {
        this.gravadas5 = gravadas5;
    }

    public double getIva5() {
        return iva5;
    }

    public void setIva5(double iva5) {
        this.iva5 = iva5;
    }

    public double getTotalneto() {
        return totalneto;
    }

    public void setTotalneto(double totalneto) {
        this.totalneto = totalneto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public plan getIdca() {
        return idca;
    }

    public void setIdca(plan idca) {
        this.idca = idca;
    }

    public int getPagado() {
        return pagado;
    }

    public void setPagado(int pagado) {
        this.pagado = pagado;
    }



}

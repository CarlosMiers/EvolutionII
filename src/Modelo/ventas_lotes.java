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
 * @author Pedro Meza
 */
public class ventas_lotes {

    String creferencia;
    String factura;
    int idventa;
    Date fecha;
    cliente cliente;
    comprobante comprobante;
    lote lote;
    lista_precios_lotes listaprecio;
    BigDecimal cuotas;
    Date primeravence;
    sucursal sucursal;
    moneda moneda;
    BigDecimal cotizacion;
    int plazo;
    vendedor vendedor;
    
    public ventas_lotes() {
    }

    public ventas_lotes(String creferencia, int idventa, Date fecha, cliente cliente, comprobante comprobante, lote lote, lista_precios_lotes listaprecio, BigDecimal cuotas, Date primeravence) {
        this.creferencia = creferencia;
        this.idventa=idventa;
        this.fecha = fecha;
        this.cliente=cliente;
        this.comprobante=comprobante;
        this.lote=lote;
        this.listaprecio=listaprecio;
        this.cuotas=cuotas;
        this.primeravence=primeravence;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
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

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public lote getLote() {
        return lote;
    }

    public void setLote(lote lote) {
        this.lote = lote;
    }

    public lista_precios_lotes getListaprecio() {
        return listaprecio;
    }

    public void setListaprecio(lista_precios_lotes listaprecio) {
        this.listaprecio = listaprecio;
    }

    public BigDecimal getCuotas() {
        return cuotas;
    }

    public void setCuotas(BigDecimal cuotas) {
        this.cuotas = cuotas;
    }

    public Date getPrimeravence() {
        return primeravence;
    }

    public void setPrimeravence(Date primeravence) {
        this.primeravence = primeravence;
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

    public BigDecimal getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(BigDecimal cotizacion) {
        this.cotizacion = cotizacion;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author Fais
 */
public class liquidacion {



String idtransaccion;
String idliquidacion;
double numerobolsa;
Date fecha;
emisor emisor;
int renta;
cliente cliente;
moneda moneda;
int  tipo;
producto concepto;
Double cantidad;
Double precio;
Double valor_inversion;
Double valor_nominal;
Double porcentajeiva;
Double arancel;
Double monto;
Double montoiva;
Double totales;
String idfactura;
String observacion;

public liquidacion(){
    
}

    public liquidacion(String idtransaccion, String idliquidacion, double numerobolsa, Date fecha, emisor emisor, int renta, cliente cliente, moneda moneda, int tipo, producto concepto, Double cantidad, Double precio, Double valor_inversion, Double valor_nominal, Double porcentajeiva, Double arancel, Double monto, Double montoiva, Double totales, String idfactura, String observacion) {
        this.idtransaccion = idtransaccion;
        this.idliquidacion = idliquidacion;
        this.numerobolsa = numerobolsa;
        this.fecha = fecha;
        this.emisor = emisor;
        this.renta = renta;
        this.cliente = cliente;
        this.moneda = moneda;
        this.tipo = tipo;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.valor_inversion = valor_inversion;
        this.valor_nominal = valor_nominal;
        this.porcentajeiva = porcentajeiva;
        this.arancel = arancel;
        this.monto = monto;
        this.montoiva = montoiva;
        this.totales = totales;
        this.idfactura = idfactura;
        this.observacion = observacion;
    }

    public String getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(String idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public String getIdliquidacion() {
        return idliquidacion;
    }

    public void setIdliquidacion(String idliquidacion) {
        this.idliquidacion = idliquidacion;
    }

    public double getNumerobolsa() {
        return numerobolsa;
    }

    public void setNumerobolsa(double numerobolsa) {
        this.numerobolsa = numerobolsa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(emisor emisor) {
        this.emisor = emisor;
    }

    public int getRenta() {
        return renta;
    }

    public void setRenta(int renta) {
        this.renta = renta;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public producto getConcepto() {
        return concepto;
    }

    public void setConcepto(producto concepto) {
        this.concepto = concepto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getValor_inversion() {
        return valor_inversion;
    }

    public void setValor_inversion(Double valor_inversion) {
        this.valor_inversion = valor_inversion;
    }

    public Double getValor_nominal() {
        return valor_nominal;
    }

    public void setValor_nominal(Double valor_nominal) {
        this.valor_nominal = valor_nominal;
    }

    public Double getPorcentajeiva() {
        return porcentajeiva;
    }

    public void setPorcentajeiva(Double porcentajeiva) {
        this.porcentajeiva = porcentajeiva;
    }

    public Double getArancel() {
        return arancel;
    }

    public void setArancel(Double arancel) {
        this.arancel = arancel;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getMontoiva() {
        return montoiva;
    }

    public void setMontoiva(Double montoiva) {
        this.montoiva = montoiva;
    }

    public Double getTotales() {
        return totales;
    }

    public void setTotales(Double totales) {
        this.totales = totales;
    }

    public String getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(String idfactura) {
        this.idfactura = idfactura;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }



}

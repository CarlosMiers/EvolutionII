/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;



/**
 *
 * @author Fais
 */
public class comision {

    Double itemid;
    String idtransaccion;
    String idliquidacion;
    Double numerobolsa;
    Date fecha;
    emisor emisor;
    vendedor asesor;
    int renta;
    cliente cliente;
    moneda moneda;
    int punta;
    Double cantidad;
    Double precio;
    Double valor_inversion;
    Double valor_nominal;
    Double porcentajeiva;
    Double comision;
    Double monto;
    Double montoiva;
    Double totales;
    Double saldo;
    String observacion;

    public comision() {

    }

    public comision(Double itemid, String idtransaccion, String idliquidacion, Double numerobolsa, Date fecha, emisor emisor, vendedor asesor, int renta, cliente cliente, moneda moneda, int punta, Double cantidad, Double precio, Double valor_inversion, Double valor_nominal, Double porcentajeiva, Double comision, Double monto, Double montoiva, Double totales, Double saldo, String observacion) {
        this.itemid = itemid;
        this.idtransaccion = idtransaccion;
        this.idliquidacion = idliquidacion;
        this.numerobolsa = numerobolsa;
        this.fecha = fecha;
        this.emisor = emisor;
        this.asesor = asesor;
        this.renta = renta;
        this.cliente = cliente;
        this.moneda = moneda;
        this.punta = punta;
        this.cantidad = cantidad;
        this.precio = precio;
        this.valor_inversion = valor_inversion;
        this.valor_nominal = valor_nominal;
        this.porcentajeiva = porcentajeiva;
        this.comision = comision;
        this.monto = monto;
        this.montoiva = montoiva;
        this.totales = totales;
        this.saldo = saldo;
        this.observacion = observacion;
    }

    public Double getItemid() {
        return itemid;
    }

    public void setItemid(Double itemid) {
        this.itemid = itemid;
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

    public Double getNumerobolsa() {
        return numerobolsa;
    }

    public void setNumerobolsa(Double numerobolsa) {
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

    public vendedor getAsesor() {
        return asesor;
    }

    public void setAsesor(vendedor asesor) {
        this.asesor = asesor;
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

    public int getPunta() {
        return punta;
    }

    public void setPunta(int punta) {
        this.punta = punta;
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

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    
}

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
public class disponibilidad {

    double numero;
    Date fecha;
    banco destino;
    cliente cliente;
    moneda moneda;
    double totales;
    double cotizacionmoneda;
    String tipo;
    String observaciones;
    double asiento;
    int cierre;
    String referencia;
    int usuario;

    public disponibilidad() {

    }

    public disponibilidad(double numero, Date fecha, banco destino, cliente cliente, moneda moneda, double totales, double cotizacionmoneda, String tipo, String observaciones, double asiento, int cierre, String referencia, int usuario) {
        this.numero = numero;
        this.fecha = fecha;
        this.destino = destino;
        this.cliente = cliente;
        this.moneda = moneda;
        this.totales = totales;
        this.cotizacionmoneda = cotizacionmoneda;
        this.tipo = tipo;
        this.observaciones = observaciones;
        this.asiento = asiento;
        this.cierre = cierre;
        this.referencia = referencia;
        this.usuario = usuario;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public banco getDestino() {
        return destino;
    }

    public void setDestino(banco destino) {
        this.destino = destino;
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

    public double getTotales() {
        return totales;
    }

    public void setTotales(double totales) {
        this.totales = totales;
    }

    public double getCotizacionmoneda() {
        return cotizacionmoneda;
    }

    public void setCotizacionmoneda(double cotizacionmoneda) {
        this.cotizacionmoneda = cotizacionmoneda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getAsiento() {
        return asiento;
    }

    public void setAsiento(double asiento) {
        this.asiento = asiento;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

}

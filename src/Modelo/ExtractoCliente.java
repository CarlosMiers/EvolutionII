/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Pc_Server
 */
public class ExtractoCliente {

    String fecha;
    String factura;
    String recibo;
    String cuenta;
    String denominacion;
    String detalle;
    double debe;
    double haber;
    String autorizacion;

    public void ExtractoCliente() {

    }

    public ExtractoCliente(String fecha, String factura, String recibo, String cuenta, String denominacion, String detalle, double debe, double haber, String autorizacion) {
        this.fecha = fecha;
        this.factura = factura;
        this.recibo = recibo;
        this.cuenta = cuenta;
        this.denominacion = denominacion;
        this.detalle = detalle;
        this.debe = debe;
        this.haber = haber;
        this.autorizacion = autorizacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getDebe() {
        return debe;
    }

    public void setDebe(double debe) {
        this.debe = debe;
    }

    public double getHaber() {
        return haber;
    }

    public void setHaber(double haber) {
        this.haber = haber;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

}

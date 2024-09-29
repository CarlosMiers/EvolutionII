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
public class pago {

    String idpagos;
    int numero;
    sucursal sucursal;
    detallepago detallepago;
    String recibo;
    Date fecha;
    proveedor proveedor;
    moneda moneda;
    Double cotizacionmoneda;
    Double valores;
    Double totalpago;
    String observacion;
    int asiento;
    int cierre;
    int codusuario;

    public pago() {

    }

    public pago(String idpagos, int numero, sucursal sucursal, detallepago detallepago, String recibo, Date fecha, proveedor proveedor, moneda moneda, Double cotizacionmoneda, Double valores, Double totalpago, String observacion, int asiento, int cierre, int codusuario) {
        this.idpagos = idpagos;
        this.numero = numero;
        this.sucursal = sucursal;
        this.detallepago = detallepago;
        this.recibo = recibo;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.moneda = moneda;
        this.cotizacionmoneda = cotizacionmoneda;
        this.valores = valores;
        this.totalpago = totalpago;
        this.observacion = observacion;
        this.asiento = asiento;
        this.cierre = cierre;
        this.codusuario = codusuario;
    }

    public String getIdpagos() {
        return idpagos;
    }

    public void setIdpagos(String idpagos) {
        this.idpagos = idpagos;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public detallepago getDetallepago() {
        return detallepago;
    }

    public void setDetallepago(detallepago detallepago) {
        this.detallepago = detallepago;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public Double getCotizacionmoneda() {
        return cotizacionmoneda;
    }

    public void setCotizacionmoneda(Double cotizacionmoneda) {
        this.cotizacionmoneda = cotizacionmoneda;
    }

    public Double getValores() {
        return valores;
    }

    public void setValores(Double valores) {
        this.valores = valores;
    }

    public Double getTotalpago() {
        return totalpago;
    }

    public void setTotalpago(Double totalpago) {
        this.totalpago = totalpago;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public int getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(int codusuario) {
        this.codusuario = codusuario;
    }

 
}

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
public class rendicion_gastos {

    double numero;
    String creferencia;
    Date fecha;
    sucursal sucursal;
    banco banco;
    double importe;
    moneda moneda;
    String nrocheque;
    double cotizacion;
    int usuarioalta;
    double asiento;
    String observacion;

    public rendicion_gastos() {

    }

    public rendicion_gastos(double numero, String creferencia, Date fecha, sucursal sucursal, banco banco, double importe, moneda moneda, String nrocheque, double cotizacion, int usuarioalta, double asiento, String observacion) {
        this.numero = numero;
        this.creferencia = creferencia;
        this.fecha = fecha;
        this.sucursal = sucursal;
        this.banco = banco;
        this.importe = importe;
        this.moneda = moneda;
        this.nrocheque = nrocheque;
        this.cotizacion = cotizacion;
        this.usuarioalta = usuarioalta;
        this.asiento = asiento;
        this.observacion = observacion;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
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

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public banco getBanco() {
        return banco;
    }

    public void setBanco(banco banco) {
        this.banco = banco;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public String getNrocheque() {
        return nrocheque;
    }

    public void setNrocheque(String nrocheque) {
        this.nrocheque = nrocheque;
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public int getUsuarioalta() {
        return usuarioalta;
    }

    public void setUsuarioalta(int usuarioalta) {
        this.usuarioalta = usuarioalta;
    }

    public double getAsiento() {
        return asiento;
    }

    public void setAsiento(double asiento) {
        this.asiento = asiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}

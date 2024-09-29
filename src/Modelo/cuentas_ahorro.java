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
public class cuentas_ahorro {

    double cuenta;
    cliente socio;
    Date fechaapertura;
    tipo_ahorro tipoahorro;
    int tipocuenta;
    moneda moneda;
    int enviocorreo;
    int firmas;
    double caucion;
    double sobregiro;
    int estado;

    public cuentas_ahorro() {

    }

    public cuentas_ahorro(double cuenta, cliente socio, Date fechaapertura, tipo_ahorro tipoahorro, int tipocuenta, moneda moneda, int enviocorreo, int firmas, double caucion, double sobregiro, int estado) {
        this.cuenta = cuenta;
        this.socio = socio;
        this.fechaapertura = fechaapertura;
        this.tipoahorro = tipoahorro;
        this.tipocuenta = tipocuenta;
        this.moneda = moneda;
        this.enviocorreo = enviocorreo;
        this.firmas = firmas;
        this.caucion = caucion;
        this.sobregiro = sobregiro;
        this.estado = estado;
    }

    public double getCuenta() {
        return cuenta;
    }

    public void setCuenta(double cuenta) {
        this.cuenta = cuenta;
    }

    public cliente getSocio() {
        return socio;
    }

    public void setSocio(cliente socio) {
        this.socio = socio;
    }

    public Date getFechaapertura() {
        return fechaapertura;
    }

    public void setFechaapertura(Date fechaapertura) {
        this.fechaapertura = fechaapertura;
    }

    public tipo_ahorro getTipoahorro() {
        return tipoahorro;
    }

    public void setTipoahorro(tipo_ahorro tipoahorro) {
        this.tipoahorro = tipoahorro;
    }

    public int getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(int tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public int getEnviocorreo() {
        return enviocorreo;
    }

    public void setEnviocorreo(int enviocorreo) {
        this.enviocorreo = enviocorreo;
    }

    public int getFirmas() {
        return firmas;
    }

    public void setFirmas(int firmas) {
        this.firmas = firmas;
    }

    public double getCaucion() {
        return caucion;
    }

    public void setCaucion(double caucion) {
        this.caucion = caucion;
    }

    public double getSobregiro() {
        return sobregiro;
    }

    public void setSobregiro(double sobregiro) {
        this.sobregiro = sobregiro;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    
}

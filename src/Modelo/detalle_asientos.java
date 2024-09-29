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
public class detalle_asientos {
double asi_asient;
String asi_numero;
moneda moneda;
double importe;
double cotizacion;
plan asi_codigo;
String asi_descri;
double impdebe;
double imphaber;
int item;
double saldoanterior;
int centro;
int ntipo;
    
    public detalle_asientos(){
        
    }

    public detalle_asientos(double asi_asient, String asi_numero, moneda moneda, double importe, double cotizacion, plan asi_codigo, String asi_descri, double impdebe, double imphaber, int item, double saldoanterior, int centro, int ntipo) {
        this.asi_asient = asi_asient;
        this.asi_numero = asi_numero;
        this.moneda = moneda;
        this.importe = importe;
        this.cotizacion = cotizacion;
        this.asi_codigo = asi_codigo;
        this.asi_descri = asi_descri;
        this.impdebe = impdebe;
        this.imphaber = imphaber;
        this.item = item;
        this.saldoanterior = saldoanterior;
        this.centro = centro;
        this.ntipo = ntipo;
    }

    public double getAsi_asient() {
        return asi_asient;
    }

    public void setAsi_asient(double asi_asient) {
        this.asi_asient = asi_asient;
    }

    public String getAsi_numero() {
        return asi_numero;
    }

    public void setAsi_numero(String asi_numero) {
        this.asi_numero = asi_numero;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public plan getAsi_codigo() {
        return asi_codigo;
    }

    public void setAsi_codigo(plan asi_codigo) {
        this.asi_codigo = asi_codigo;
    }

    public String getAsi_descri() {
        return asi_descri;
    }

    public void setAsi_descri(String asi_descri) {
        this.asi_descri = asi_descri;
    }

    public double getImpdebe() {
        return impdebe;
    }

    public void setImpdebe(double impdebe) {
        this.impdebe = impdebe;
    }

    public double getImphaber() {
        return imphaber;
    }

    public void setImphaber(double imphaber) {
        this.imphaber = imphaber;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public double getSaldoanterior() {
        return saldoanterior;
    }

    public void setSaldoanterior(double saldoanterior) {
        this.saldoanterior = saldoanterior;
    }

    public int getCentro() {
        return centro;
    }

    public void setCentro(int centro) {
        this.centro = centro;
    }

    public int getNtipo() {
        return ntipo;
    }

    public void setNtipo(int ntipo) {
        this.ntipo = ntipo;
    }

    
}

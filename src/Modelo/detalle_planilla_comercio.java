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
public class detalle_planilla_comercio {
  

double dnumero;
String iddocumento;
cliente socio;
double numeroorden;
double saldototal;
Date emision_orden;
Date vence_cuota;
double monto;
int numerocuota;
int cuota;


public detalle_planilla_comercio(){
    
}

    public detalle_planilla_comercio(double dnumero, String iddocumento, cliente socio, double numeroorden, double saldototal, Date emision_orden, Date vence_cuota, double monto, int numerocuota, int cuota) {
        this.dnumero = dnumero;
        this.iddocumento = iddocumento;
        this.socio = socio;
        this.numeroorden = numeroorden;
        this.saldototal = saldototal;
        this.emision_orden = emision_orden;
        this.vence_cuota = vence_cuota;
        this.monto = monto;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
    }

    public double getDnumero() {
        return dnumero;
    }

    public void setDnumero(double dnumero) {
        this.dnumero = dnumero;
    }

    public String getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(String iddocumento) {
        this.iddocumento = iddocumento;
    }

    public cliente getSocio() {
        return socio;
    }

    public void setSocio(cliente socio) {
        this.socio = socio;
    }

    public double getNumeroorden() {
        return numeroorden;
    }

    public void setNumeroorden(double numeroorden) {
        this.numeroorden = numeroorden;
    }

    public double getSaldototal() {
        return saldototal;
    }

    public void setSaldototal(double saldototal) {
        this.saldototal = saldototal;
    }

    public Date getEmision_orden() {
        return emision_orden;
    }

    public void setEmision_orden(Date emision_orden) {
        this.emision_orden = emision_orden;
    }

    public Date getVence_cuota() {
        return vence_cuota;
    }

    public void setVence_cuota(Date vence_cuota) {
        this.vence_cuota = vence_cuota;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getNumerocuota() {
        return numerocuota;
    }

    public void setNumerocuota(int numerocuota) {
        this.numerocuota = numerocuota;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }


}

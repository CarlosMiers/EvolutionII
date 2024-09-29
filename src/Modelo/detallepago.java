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
public class detallepago {

    String iddetalle;
    String idfactura;
    Double nrofactura;
    Date emision;
    comprobante comprobante;
    Double pago;
    int numerocuota;
    int cuota;
    Date vencecuota;

    public detallepago() {

    }

    public detallepago(String iddetalle, String idfactura, Double nrofactura, Date emision, comprobante comprobante, Double pago, int numerocuota, int cuota, Date vencecuota) {
        this.iddetalle = iddetalle;
        this.idfactura = idfactura;
        this.nrofactura = nrofactura;
        this.emision = emision;
        this.comprobante = comprobante;
        this.pago = pago;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
        this.vencecuota = vencecuota;
    }

    public String getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(String iddetalle) {
        this.iddetalle = iddetalle;
    }

    public String getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(String idfactura) {
        this.idfactura = idfactura;
    }

    public Double getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(Double nrofactura) {
        this.nrofactura = nrofactura;
    }

    public Date getEmision() {
        return emision;
    }

    public void setEmision(Date emision) {
        this.emision = emision;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public Double getPago() {
        return pago;
    }

    public void setPago(Double pago) {
        this.pago = pago;
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

    public Date getVencecuota() {
        return vencecuota;
    }

    public void setVencecuota(Date vencecuota) {
        this.vencecuota = vencecuota;
    }

}

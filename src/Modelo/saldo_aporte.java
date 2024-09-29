/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.sql.Date;




/**
 *
 * @author SERVIDOR
 */
public class saldo_aporte {

    String referencia;
    Double documento;
    cliente socio;
    Date fecha;
    comprobante comprobante;
    BigDecimal retencion;
    BigDecimal descuentos;
    BigDecimal importe;
    BigDecimal saldo;

    public saldo_aporte() {

    }

    public saldo_aporte(String referencia, Double documento, cliente socio, Date fecha, comprobante comprobante, BigDecimal retencion, BigDecimal descuentos, BigDecimal importe, BigDecimal saldo) {
        this.referencia = referencia;
        this.documento = documento;
        this.socio = socio;
        this.fecha = fecha;
        this.comprobante = comprobante;
        this.retencion = retencion;
        this.descuentos = descuentos;
        this.importe = importe;
        this.saldo = saldo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Double getDocumento() {
        return documento;
    }

    public void setDocumento(Double documento) {
        this.documento = documento;
    }

    public cliente getSocio() {
        return socio;
    }

    public void setSocio(cliente socio) {
        this.socio = socio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public BigDecimal getRetencion() {
        return retencion;
    }

    public void setRetencion(BigDecimal retencion) {
        this.retencion = retencion;
    }

    public BigDecimal getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(BigDecimal descuentos) {
        this.descuentos = descuentos;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

   
   
}

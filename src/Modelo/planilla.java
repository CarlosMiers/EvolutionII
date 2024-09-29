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
public class planilla {
    
    
double idnumero;
Date fecha;
Date vence;
giraduria giraduria;
BigDecimal total;
BigDecimal porcentajebonif;
BigDecimal importebonif;
BigDecimal importeiva;
BigDecimal subtotal;
BigDecimal retencion;
BigDecimal netoacobrar;
BigDecimal saldoplanilla;
BigDecimal pagos;
    
    public planilla(){
        
    }

    public planilla(double idnumero, Date fecha, Date vence, giraduria giraduria, BigDecimal total, BigDecimal porcentajebonif, BigDecimal importebonif, BigDecimal importeiva, BigDecimal subtotal, BigDecimal retencion, BigDecimal netoacobrar, BigDecimal saldoplanilla, BigDecimal pagos) {
        this.idnumero = idnumero;
        this.fecha = fecha;
        this.vence = vence;
        this.giraduria = giraduria;
        this.total = total;
        this.porcentajebonif = porcentajebonif;
        this.importebonif = importebonif;
        this.importeiva = importeiva;
        this.subtotal = subtotal;
        this.retencion = retencion;
        this.netoacobrar = netoacobrar;
        this.saldoplanilla = saldoplanilla;
        this.pagos = pagos;
    }

    public double getIdnumero() {
        return idnumero;
    }

    public void setIdnumero(double idnumero) {
        this.idnumero = idnumero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getVence() {
        return vence;
    }

    public void setVence(Date vence) {
        this.vence = vence;
    }

    public giraduria getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(giraduria giraduria) {
        this.giraduria = giraduria;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getPorcentajebonif() {
        return porcentajebonif;
    }

    public void setPorcentajebonif(BigDecimal porcentajebonif) {
        this.porcentajebonif = porcentajebonif;
    }

    public BigDecimal getImportebonif() {
        return importebonif;
    }

    public void setImportebonif(BigDecimal importebonif) {
        this.importebonif = importebonif;
    }

    public BigDecimal getImporteiva() {
        return importeiva;
    }

    public void setImporteiva(BigDecimal importeiva) {
        this.importeiva = importeiva;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getRetencion() {
        return retencion;
    }

    public void setRetencion(BigDecimal retencion) {
        this.retencion = retencion;
    }

    public BigDecimal getNetoacobrar() {
        return netoacobrar;
    }

    public void setNetoacobrar(BigDecimal netoacobrar) {
        this.netoacobrar = netoacobrar;
    }

    public BigDecimal getSaldoplanilla() {
        return saldoplanilla;
    }

    public void setSaldoplanilla(BigDecimal saldoplanilla) {
        this.saldoplanilla = saldoplanilla;
    }

    public BigDecimal getPagos() {
        return pagos;
    }

    public void setPagos(BigDecimal pagos) {
        this.pagos = pagos;
    }

    
}

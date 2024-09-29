/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;

/**
 *
 * @author Usuario
 */
public class detalle_compra {

String dreferencia; 
producto codprod;
BigDecimal cantidad;
BigDecimal prcosto;
BigDecimal monto;
BigDecimal impiva;
BigDecimal porcentaje;
String centro; 
Double utilidad1;
Double utilidad2;
Double precio1;
Double precio2;
Double precioviejo1;
Double precioviejo2;
int suc;
BigDecimal costo_moneda_local;
BigDecimal participacion_costo;
BigDecimal participacion_gastos;
BigDecimal costo_real;

public detalle_compra() {

}

    public String getDreferencia() {
        return dreferencia;
    }

    public void setDreferencia(String dreferencia) {
        this.dreferencia = dreferencia;
    }

    public producto getCodprod() {
        return codprod;
    }

    public void setCodprod(producto codprod) {
        this.codprod = codprod;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrcosto() {
        return prcosto;
    }

    public void setPrcosto(BigDecimal prcosto) {
        this.prcosto = prcosto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getImpiva() {
        return impiva;
    }

    public void setImpiva(BigDecimal impiva) {
        this.impiva = impiva;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public Double getUtilidad1() {
        return utilidad1;
    }

    public void setUtilidad1(Double utilidad1) {
        this.utilidad1 = utilidad1;
    }

    public Double getUtilidad2() {
        return utilidad2;
    }

    public void setUtilidad2(Double utilidad2) {
        this.utilidad2 = utilidad2;
    }

    public Double getPrecio1() {
        return precio1;
    }

    public void setPrecio1(Double precio1) {
        this.precio1 = precio1;
    }

    public Double getPrecio2() {
        return precio2;
    }

    public void setPrecio2(Double precio2) {
        this.precio2 = precio2;
    }

    public Double getPrecioviejo1() {
        return precioviejo1;
    }

    public void setPrecioviejo1(Double precioviejo1) {
        this.precioviejo1 = precioviejo1;
    }

    public Double getPrecioviejo2() {
        return precioviejo2;
    }

    public void setPrecioviejo2(Double precioviejo2) {
        this.precioviejo2 = precioviejo2;
    }

    public int getSuc() {
        return suc;
    }

    public void setSuc(int suc) {
        this.suc = suc;
    }

    public BigDecimal getCosto_moneda_local() {
        return costo_moneda_local;
    }

    public void setCosto_moneda_local(BigDecimal costo_moneda_local) {
        this.costo_moneda_local = costo_moneda_local;
    }

    public BigDecimal getParticipacion_costo() {
        return participacion_costo;
    }

    public void setParticipacion_costo(BigDecimal participacion_costo) {
        this.participacion_costo = participacion_costo;
    }

    public BigDecimal getParticipacion_gastos() {
        return participacion_gastos;
    }

    public void setParticipacion_gastos(BigDecimal participacion_gastos) {
        this.participacion_gastos = participacion_gastos;
    }

    public BigDecimal getCosto_real() {
        return costo_real;
    }

    public void setCosto_real(BigDecimal costo_real) {
        this.costo_real = costo_real;
    }

    public detalle_compra(String dreferencia, producto codprod, BigDecimal cantidad, BigDecimal prcosto, BigDecimal monto, BigDecimal impiva, BigDecimal porcentaje, String centro, Double utilidad1, Double utilidad2, Double precio1, Double precio2, Double precioviejo1, Double precioviejo2, int suc, BigDecimal costo_moneda_local, BigDecimal participacion_costo, BigDecimal participacion_gastos, BigDecimal costo_real) {
        this.dreferencia = dreferencia;
        this.codprod = codprod;
        this.cantidad = cantidad;
        this.prcosto = prcosto;
        this.monto = monto;
        this.impiva = impiva;
        this.porcentaje = porcentaje;
        this.centro = centro;
        this.utilidad1 = utilidad1;
        this.utilidad2 = utilidad2;
        this.precio1 = precio1;
        this.precio2 = precio2;
        this.precioviejo1 = precioviejo1;
        this.precioviejo2 = precioviejo2;
        this.suc = suc;
        this.costo_moneda_local = costo_moneda_local;
        this.participacion_costo = participacion_costo;
        this.participacion_gastos = participacion_gastos;
        this.costo_real = costo_real;
    }


}





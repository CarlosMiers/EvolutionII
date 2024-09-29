/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;

/**
 *
 * @author SERVIDOR
 */
public class detalle_venta {

    String dreferencia;
    producto codprod;
    BigDecimal cantidad;
    String comentario;
    BigDecimal prcosto;
    BigDecimal precio;
    BigDecimal monto;
    BigDecimal impiva;
    BigDecimal porcentaje;
    String detalle_item;
    BigDecimal comision_venta;
    Double supago;
    String porcentaje_descuento;
    plan ctadebe;
    int suc;
    Double item;
    Double saldo;

    public detalle_venta() {

    }

    public detalle_venta(String dreferencia, producto codprod, BigDecimal cantidad, String comentario, BigDecimal prcosto, BigDecimal precio, BigDecimal monto, BigDecimal impiva, BigDecimal porcentaje, String detalle_item, BigDecimal comision_venta, Double supago, String porcentaje_descuento, plan ctadebe, int suc, Double item, Double saldo) {
        this.dreferencia = dreferencia;
        this.codprod = codprod;
        this.cantidad = cantidad;
        this.comentario = comentario;
        this.prcosto = prcosto;
        this.precio = precio;
        this.monto = monto;
        this.impiva = impiva;
        this.porcentaje = porcentaje;
        this.detalle_item = detalle_item;
        this.comision_venta = comision_venta;
        this.supago = supago;
        this.porcentaje_descuento = porcentaje_descuento;
        this.ctadebe = ctadebe;
        this.suc = suc;
        this.item = item;
        this.saldo = saldo;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public BigDecimal getPrcosto() {
        return prcosto;
    }

    public void setPrcosto(BigDecimal prcosto) {
        this.prcosto = prcosto;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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

    public String getDetalle_item() {
        return detalle_item;
    }

    public void setDetalle_item(String detalle_item) {
        this.detalle_item = detalle_item;
    }

    public BigDecimal getComision_venta() {
        return comision_venta;
    }

    public void setComision_venta(BigDecimal comision_venta) {
        this.comision_venta = comision_venta;
    }

    public Double getSupago() {
        return supago;
    }

    public void setSupago(Double supago) {
        this.supago = supago;
    }

    public String getPorcentaje_descuento() {
        return porcentaje_descuento;
    }

    public void setPorcentaje_descuento(String porcentaje_descuento) {
        this.porcentaje_descuento = porcentaje_descuento;
    }

    public plan getCtadebe() {
        return ctadebe;
    }

    public void setCtadebe(plan ctadebe) {
        this.ctadebe = ctadebe;
    }

    public int getSuc() {
        return suc;
    }

    public void setSuc(int suc) {
        this.suc = suc;
    }

    public Double getItem() {
        return item;
    }

    public void setItem(Double item) {
        this.item = item;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    
}

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
 * @author Usuario
 */
public class detalle_ajuste_mercaderia {

    String dreferencia;
    producto producto;
    BigDecimal cantidad;
    BigDecimal costo;

    public detalle_ajuste_mercaderia() {

    }

    public detalle_ajuste_mercaderia(String dreferencia, producto producto, BigDecimal cantidad, BigDecimal costo) {
        this.dreferencia = dreferencia;
        this.producto = producto;
        this.cantidad = cantidad;
        this.costo = costo;
    }

    public String getDreferencia() {
        return dreferencia;
    }

    public void setDreferencia(String dreferencia) {
        this.dreferencia = dreferencia;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }



}
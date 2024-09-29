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
public class detalle_transferencia {
String dreferencia;
producto producto;
BigDecimal cantidad;
BigDecimal costo;
BigDecimal salida;
BigDecimal entrada;

public detalle_transferencia(){
        
}

    public detalle_transferencia(String dreferencia, producto producto, BigDecimal cantidad, BigDecimal costo, BigDecimal salida, BigDecimal entrada) {
        this.dreferencia = dreferencia;
        this.producto = producto;
        this.cantidad = cantidad;
        this.costo = costo;
        this.salida = salida;
        this.entrada = entrada;
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

    public BigDecimal getSalida() {
        return salida;
    }

    public void setSalida(BigDecimal salida) {
        this.salida = salida;
    }

    public BigDecimal getEntrada() {
        return entrada;
    }

    public void setEntrada(BigDecimal entrada) {
        this.entrada = entrada;
    }

   
}
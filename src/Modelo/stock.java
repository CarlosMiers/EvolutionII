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
public class stock {
    
    sucursal sucursal;
    producto producto;
    Double Stock;
   
    public stock(){
        
    }

    public stock(sucursal sucursal, producto producto, Double Stock) {
        this.sucursal = sucursal;
        this.producto = producto;
        this.Stock = Stock;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    public Double getStock() {
        return Stock;
    }

    public void setStock(Double Stock) {
        this.Stock = Stock;
    }

   
}

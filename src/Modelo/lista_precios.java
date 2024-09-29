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
public class lista_precios {
    producto producto;
    double limitecantidad;
    double precioventa;
    double utilidad;
    double itemid;
    public lista_precios() {

    }

    public lista_precios(producto producto, double limitecantidad, double precioventa, double utilidad, double itemid) {
        this.producto = producto;
        this.limitecantidad = limitecantidad;
        this.precioventa = precioventa;
        this.utilidad = utilidad;
        this.itemid = itemid;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    public double getLimitecantidad() {
        return limitecantidad;
    }

    public void setLimitecantidad(double limitecantidad) {
        this.limitecantidad = limitecantidad;
    }

    public double getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(double precioventa) {
        this.precioventa = precioventa;
    }

    public double getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(double utilidad) {
        this.utilidad = utilidad;
    }

    public double getItemid() {
        return itemid;
    }

    public void setItemid(double itemid) {
        this.itemid = itemid;
    }

    
    
}

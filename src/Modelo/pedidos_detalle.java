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
public class pedidos_detalle {

    double idpedido;
    double item;
    producto producto;
    double cantidad;
    double costo;
    double total;

    public pedidos_detalle() {

    }

    public pedidos_detalle(double idpedido, double item, producto producto, double cantidad, double costo, double total) {
        this.idpedido = idpedido;
        this.item = item;
        this.producto = producto;
        this.cantidad = cantidad;
        this.costo = costo;
        this.total = total;
    }

    public double getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(double idpedido) {
        this.idpedido = idpedido;
    }

    public double getItem() {
        return item;
    }

    public void setItem(double item) {
        this.item = item;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
    
}

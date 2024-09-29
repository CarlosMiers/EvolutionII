/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author Pc_Server
 */
public class Detalleventasxrubro {

    String factura;
    String fecha;
    String producto;
    String nombreproducto;
    Double cantidad;
    Double precio;
    int iva;
    Double total;
    String nombrecliente;
    int rubro;
    String nombrerubro;

    public void Detalleventasxrubro() {

    }

    public Detalleventasxrubro(String factura, String fecha, String producto, String nombreproducto, Double cantidad, Double precio, int iva, Double total, String nombrecliente, int rubro, String nombrerubro) {
        this.factura = factura;
        this.fecha = fecha;
        this.producto = producto;
        this.nombreproducto = nombreproducto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.iva = iva;
        this.total = total;
        this.nombrecliente = nombrecliente;
        this.rubro = rubro;
        this.nombrerubro = nombrerubro;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public int getRubro() {
        return rubro;
    }

    public void setRubro(int rubro) {
        this.rubro = rubro;
    }

    public String getNombrerubro() {
        return nombrerubro;
    }

    public void setNombrerubro(String nombrerubro) {
        this.nombrerubro = nombrerubro;
    }

  
}

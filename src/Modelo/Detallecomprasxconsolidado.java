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
public class Detallecomprasxconsolidado {
    String factura;
    String fecha;
    String producto;
    String nombreproducto;
    Double cantidad;
    Double precio;
    Double total;
    int sucursal;
    String nombresucursal;
    String nombreproveedor;
  
    
     public void Detallecomprasxconsolidado(){
        
    }

    public Detallecomprasxconsolidado(String factura, String fecha, String producto, String nombreproducto, Double cantidad, Double precio, Double total, int sucursal, String nombresucursal, String nombreproveedor) {
        this.factura = factura;
        this.fecha = fecha;
        this.producto = producto;
        this.nombreproducto = nombreproducto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.sucursal = sucursal;
        this.nombresucursal = nombresucursal;
        this.nombreproveedor = nombreproveedor;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public String getNombresucursal() {
        return nombresucursal;
    }

    public void setNombresucursal(String nombresucursal) {
        this.nombresucursal = nombresucursal;
    }

    public String getNombreproveedor() {
        return nombreproveedor;
    }

    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }

  
}

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
public class ResumenxVentas {
  
    String producto;
    String nombreproducto;
    Double cantidad;
    Double precio;
    Double total;
    int rubro;
    String nombrerubro;
    Double promedio;

    public void ResumenxVentas(){
        
    }
    
    public ResumenxVentas(String producto, String nombreproducto, Double cantidad, Double precio, Double total, int rubro, String nombrerubro, Double promedio) {
        this.producto = producto;
        this.nombreproducto = nombreproducto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.rubro = rubro;
        this.nombrerubro = nombrerubro;
        this.promedio = promedio;
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

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }


    
}

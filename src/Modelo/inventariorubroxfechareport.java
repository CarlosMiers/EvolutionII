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
public class inventariorubroxfechareport {

    String producto;
    String nombreproducto;
    Double precio;
    Double stock;
    Double valorizacion;
    String nombreunidad;
    int rubro;
    String nombrerubro;
   

    public void inventarioxrubroreport() {

    }

    public inventariorubroxfechareport(String producto, String nombreproducto, Double precio, Double stock, Double valorizacion, String nombreunidad, int rubro, String nombrerubro) {
        this.producto = producto;
        this.nombreproducto = nombreproducto;
        this.precio = precio;
        this.stock = stock;
        this.valorizacion = valorizacion;
        this.nombreunidad = nombreunidad;
        this.rubro = rubro;
        this.nombrerubro = nombrerubro;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getValorizacion() {
        return valorizacion;
    }

    public void setValorizacion(Double valorizacion) {
        this.valorizacion = valorizacion;
    }

    public String getNombreunidad() {
        return nombreunidad;
    }

    public void setNombreunidad(String nombreunidad) {
        this.nombreunidad = nombreunidad;
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

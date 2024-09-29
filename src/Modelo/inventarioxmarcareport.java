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
public class inventarioxmarcareport {

    String producto;
    String nombreproducto;
    Double precio;
    Double preciominorista;
    Double stock;
    String nombreunidad;
    int marca;
    String nombremarca;

    public void inventarioxmarcareport() {

    }

    public inventarioxmarcareport(String producto, String nombreproducto, Double precio, Double preciominorista, Double stock, String nombreunidad, int marca, String nombremarca) {
        this.producto = producto;
        this.nombreproducto = nombreproducto;
        this.precio = precio;
        this.preciominorista = preciominorista;
        this.stock = stock;
        this.nombreunidad = nombreunidad;
        this.marca = marca;
        this.nombremarca = nombremarca;
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

    public Double getPreciominorista() {
        return preciominorista;
    }

    public void setPreciominorista(Double preciominorista) {
        this.preciominorista = preciominorista;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public String getNombreunidad() {
        return nombreunidad;
    }

    public void setNombreunidad(String nombreunidad) {
        this.nombreunidad = nombreunidad;
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    public String getNombremarca() {
        return nombremarca;
    }

    public void setNombremarca(String nombremarca) {
        this.nombremarca = nombremarca;
    }

   
   
}

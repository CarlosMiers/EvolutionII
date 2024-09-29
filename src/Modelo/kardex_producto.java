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
public class kardex_producto {

    String numero;
    String fecha;
    String nombrecomprobante;
    Double entrada;
    Double costo;
    Double salida;
    Double precio;
    public kardex_producto() {

    }

    public kardex_producto(String numero, String fecha, String nombrecomprobante, Double entrada, Double costo, Double salida, Double precio) {
        this.numero = numero;
        this.fecha = fecha;
        this.nombrecomprobante = nombrecomprobante;
        this.entrada = entrada;
        this.costo = costo;
        this.salida = salida;
        this.precio = precio;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombrecomprobante() {
        return nombrecomprobante;
    }

    public void setNombrecomprobante(String nombrecomprobante) {
        this.nombrecomprobante = nombrecomprobante;
    }

    public Double getEntrada() {
        return entrada;
    }

    public void setEntrada(Double entrada) {
        this.entrada = entrada;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getSalida() {
        return salida;
    }

    public void setSalida(Double salida) {
        this.salida = salida;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

   
}

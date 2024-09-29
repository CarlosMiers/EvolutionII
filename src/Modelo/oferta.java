/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Fais
 */
public class oferta {
    


double id;
Date fecha;
Date validohasta;
int renta;
emisor emisor;
moneda moneda;
titulo titulo;
int plazo;
double tasa;
int estado;
double cantidad;
double precio;
double valor_inversion;
String comentario;
    
    public oferta(){
        
    }

    public oferta(double id, Date fecha, Date validohasta, int renta, emisor emisor, moneda moneda, titulo titulo, int plazo, double tasa, int estado, double cantidad, double precio, double valor_inversion, String comentario) {
        this.id = id;
        this.fecha = fecha;
        this.validohasta = validohasta;
        this.renta = renta;
        this.emisor = emisor;
        this.moneda = moneda;
        this.titulo = titulo;
        this.plazo = plazo;
        this.tasa = tasa;
        this.estado = estado;
        this.cantidad = cantidad;
        this.precio = precio;
        this.valor_inversion = valor_inversion;
        this.comentario = comentario;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getValidohasta() {
        return validohasta;
    }

    public void setValidohasta(Date validohasta) {
        this.validohasta = validohasta;
    }

    public int getRenta() {
        return renta;
    }

    public void setRenta(int renta) {
        this.renta = renta;
    }

    public emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(emisor emisor) {
        this.emisor = emisor;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(titulo titulo) {
        this.titulo = titulo;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public double getTasa() {
        return tasa;
    }

    public void setTasa(double tasa) {
        this.tasa = tasa;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getValor_inversion() {
        return valor_inversion;
    }

    public void setValor_inversion(double valor_inversion) {
        this.valor_inversion = valor_inversion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    
}

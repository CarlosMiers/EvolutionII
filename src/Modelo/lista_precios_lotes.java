/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;

/**
 *
 * @author Pc_Server
 */
public class lista_precios_lotes {

    int idlista;
    lote lote;
    int plazo;
    BigDecimal cuotas;
    BigDecimal precio;
    String descripcion;

    public lista_precios_lotes() {

    }

    public lista_precios_lotes( int idlista, lote lote, int plazo, BigDecimal cuotas, BigDecimal precio, String descripcion) {
        this.idlista=idlista;
        this.lote=lote;
        this.plazo=plazo;
        this.cuotas=cuotas;
        this.precio=precio;
        this.descripcion=descripcion;
    }

    public int getIdlista() {
        return idlista;
    }

    public void setIdlista(int idlista) {
        this.idlista = idlista;
    }

    public lote getLote() {
        return lote;
    }

    public void setLote(lote lote) {
        this.lote = lote;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public BigDecimal getCuotas() {
        return cuotas;
    }

    public void setCuotas(BigDecimal cuotas) {
        this.cuotas = cuotas;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}

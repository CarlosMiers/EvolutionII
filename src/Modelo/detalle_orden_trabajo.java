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
public class detalle_orden_trabajo {

    double dnumero;
    int item;
    producto codprod;
    Double cantidad;
    Double disponible;
    Double costo;
    Double total;
    String potrero;

    public detalle_orden_trabajo() {

    }

    public detalle_orden_trabajo(double dnumero, int item, producto codprod, Double cantidad, Double disponible, Double costo, Double total, String potrero) {
        this.dnumero = dnumero;
        this.item = item;
        this.codprod = codprod;
        this.cantidad = cantidad;
        this.disponible = disponible;
        this.costo = costo;
        this.total = total;
        this.potrero = potrero;
    }

    public double getDnumero() {
        return dnumero;
    }

    public void setDnumero(double dnumero) {
        this.dnumero = dnumero;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public producto getCodprod() {
        return codprod;
    }

    public void setCodprod(producto codprod) {
        this.codprod = codprod;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getDisponible() {
        return disponible;
    }

    public void setDisponible(Double disponible) {
        this.disponible = disponible;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }


    public String getPotrero() {
        return potrero;
    }

    public void setPotrero(String potrero) {
        this.potrero = potrero;
    }
    
}

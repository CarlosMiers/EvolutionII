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
public class detalle_orden_compra {

    Double iddetalle;
    producto codprod;
    Double cantidad;
    Double precio;
    Double impuesto;
    Double importeiva;
    Double totalitem;

    public detalle_orden_compra(){
        
    }

    public detalle_orden_compra(Double iddetalle, producto codprod, Double cantidad, Double precio, Double impuesto, Double importeiva, Double totalitem) {
        this.iddetalle = iddetalle;
        this.codprod = codprod;
        this.cantidad = cantidad;
        this.precio = precio;
        this.impuesto = impuesto;
        this.importeiva = importeiva;
        this.totalitem = totalitem;
    }

    public Double getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(Double iddetalle) {
        this.iddetalle = iddetalle;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    public Double getImporteiva() {
        return importeiva;
    }

    public void setImporteiva(Double importeiva) {
        this.importeiva = importeiva;
    }

    public Double getTotalitem() {
        return totalitem;
    }

    public void setTotalitem(Double totalitem) {
        this.totalitem = totalitem;
    }
    
    
}

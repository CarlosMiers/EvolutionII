/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;

/**
 *
 * @author SERVIDOR
 */
public class detalle_descuentos_varios {

    String id_detalle;
    cliente socio;
    BigDecimal descuento;
   
    
    
    



    public detalle_descuentos_varios() {

    }

    public detalle_descuentos_varios(String id_detalle, cliente socio, BigDecimal descuento) {
        this.id_detalle = id_detalle;
        this.socio = socio;
        this.descuento = descuento;
    }

    public String getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(String id_detalle) {
        this.id_detalle = id_detalle;
    }

    public cliente getSocio() {
        return socio;
    }

    public void setSocio(cliente socio) {
        this.socio = socio;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

   
    
    
}

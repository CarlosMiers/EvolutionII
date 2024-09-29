/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;

/**
 *
 * @author Usuario
 */
public class deducciones {
    private int nprestamo;
    private String totaldescuento;
    
    public deducciones(){
        
    }

    public deducciones(int nprestamo, String totaldescuento) {
        this.nprestamo = nprestamo;
        this.totaldescuento = totaldescuento;
    }

    public int getNprestamo() {
        return nprestamo;
    }

    public void setNprestamo(int nprestamo) {
        this.nprestamo = nprestamo;
    }

    public String getTotaldescuento() {
        return totaldescuento;
    }

    public void setTotaldescuento(String totaldescuento) {
        this.totaldescuento = totaldescuento;
    }
    
}

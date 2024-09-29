/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author faisc
 */
public class FlujoVencimientos {
    String tipo;
    String cupon;
    String vencimiento;
    Double monto;
    
    public void Flujovencimientos(){
        
    }

    public FlujoVencimientos(String tipo, String cupon, String vencimiento, Double monto) {
        this.tipo = tipo;
        this.cupon = cupon;
        this.vencimiento = vencimiento;
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCupon() {
        return cupon;
    }

    public void setCupon(String cupon) {
        this.cupon = cupon;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
    
    
    
}

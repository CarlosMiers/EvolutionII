/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author SERVIDOR
 */
public class tasa {
    private comprobante comprobante;
    double plazoi;
    double porcentaje;
    public tasa(){
        
    }

    public tasa(comprobante comprobante, double plazoi, double porcentaje) {
        this.comprobante = comprobante;
        this.plazoi = plazoi;
        this.porcentaje = porcentaje;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public double getPlazoi() {
        return plazoi;
    }

    public void setPlazoi(double plazoi) {
        this.plazoi = plazoi;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}

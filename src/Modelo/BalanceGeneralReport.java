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
public class BalanceGeneralReport {
    String cuenta;
    String descripcion;
    String activo;
    String pasivo;

    public void SumasSaldos(){     
        
    }

    public BalanceGeneralReport(String cuenta, String descripcion, String activo, String pasivo) {
        this.cuenta = cuenta;
        this.descripcion = descripcion;
        this.activo = activo;
        this.pasivo = pasivo;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getPasivo() {
        return pasivo;
    }

    public void setPasivo(String pasivo) {
        this.pasivo = pasivo;
    }

    
        
}

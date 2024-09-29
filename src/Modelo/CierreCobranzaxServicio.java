
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
public class CierreCobranzaxServicio {

    String cuenta;
    String nombre;
    String comercio;
    String emision;
    String vencimiento;
    String cuota;
    Double enviado;
    Double cobrado;
    Double rebotado;

   public void CierreCobranzaxServicio(){
       
   }

    public CierreCobranzaxServicio(String cuenta, String nombre, String comercio, String emision, String vencimiento, String cuota, Double enviado, Double cobrado, Double rebotado) {
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.comercio = comercio;
        this.emision = emision;
        this.vencimiento = vencimiento;
        this.cuota = cuota;
        this.enviado = enviado;
        this.cobrado = cobrado;
        this.rebotado = rebotado;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public String getEmision() {
        return emision;
    }

    public void setEmision(String emision) {
        this.emision = emision;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public Double getEnviado() {
        return enviado;
    }

    public void setEnviado(Double enviado) {
        this.enviado = enviado;
    }

    public Double getCobrado() {
        return cobrado;
    }

    public void setCobrado(Double cobrado) {
        this.cobrado = cobrado;
    }

    public Double getRebotado() {
        return rebotado;
    }

    public void setRebotado(Double rebotado) {
        this.rebotado = rebotado;
    }
}

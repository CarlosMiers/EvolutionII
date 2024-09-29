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
public class ResumenxProveedor {
  
    int cuenta;
    String nombrepro;
    String direccionpro;
    String telefonopro;
    String ruc;
    double credito;
    double pagos;
    double saldopro;
   
    public void ResumenxProveedor(){
        
    }

    public ResumenxProveedor(int cuenta, String nombrepro, String direccionpro, String telefonopro, String ruc, double credito, double pagos, double saldopro) {
        this.cuenta = cuenta;
        this.nombrepro = nombrepro;
        this.direccionpro = direccionpro;
        this.telefonopro = telefonopro;
        this.ruc = ruc;
        this.credito = credito;
        this.pagos = pagos;
        this.saldopro = saldopro;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombrepro() {
        return nombrepro;
    }

    public void setNombrepro(String nombrepro) {
        this.nombrepro = nombrepro;
    }

    public String getDireccionpro() {
        return direccionpro;
    }

    public void setDireccionpro(String direccionpro) {
        this.direccionpro = direccionpro;
    }

    public String getTelefonopro() {
        return telefonopro;
    }

    public void setTelefonopro(String telefonopro) {
        this.telefonopro = telefonopro;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public double getPagos() {
        return pagos;
    }

    public void setPagos(double pagos) {
        this.pagos = pagos;
    }

    public double getSaldopro() {
        return saldopro;
    }

    public void setSaldopro(double saldopro) {
        this.saldopro = saldopro;
    }

    
}

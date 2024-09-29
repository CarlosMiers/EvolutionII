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
public class LibroMayorReporte {
    String fecha;
    String asiento;
    String documento;
    String cuenta;
    String nombrecuenta;
    String detalle;
    double saldoanterior;
    double debe;
    double haber;
    String naturaleza;
    
     public void LibroMayorReporte(){     
        
    }   

    public LibroMayorReporte(String fecha, String asiento, String documento, String cuenta, String nombrecuenta, String detalle, double saldoanterior, double debe, double haber, String naturaleza) {
        this.fecha = fecha;
        this.asiento = asiento;
        this.documento = documento;
        this.cuenta = cuenta;
        this.nombrecuenta = nombrecuenta;
        this.detalle = detalle;
        this.saldoanterior = saldoanterior;
        this.debe = debe;
        this.haber = haber;
        this.naturaleza = naturaleza;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombrecuenta() {
        return nombrecuenta;
    }

    public void setNombrecuenta(String nombrecuenta) {
        this.nombrecuenta = nombrecuenta;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getSaldoanterior() {
        return saldoanterior;
    }

    public void setSaldoanterior(double saldoanterior) {
        this.saldoanterior = saldoanterior;
    }

    public double getDebe() {
        return debe;
    }

    public void setDebe(double debe) {
        this.debe = debe;
    }

    public double getHaber() {
        return haber;
    }

    public void setHaber(double haber) {
        this.haber = haber;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }
     
}

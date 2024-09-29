/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;


/**
 *
 * @author Pc_Server
 */
public class detalle_forma_cuotas {
    
String iddetalle;
int autorizacion;
double monto;
int ncuotas;
double montocuota;
Date primeracuota;
int ncantidad;

    public detalle_forma_cuotas(){
        
    }

    
    public detalle_forma_cuotas(String iddetalle, int autorizacion, double monto, int ncuotas, double montocuota, Date primeracuota, int ncantidad) {
        this.iddetalle = iddetalle;
        this.autorizacion = autorizacion;
        this.monto = monto;
        this.ncuotas = ncuotas;
        this.montocuota = montocuota;
        this.primeracuota = primeracuota;
        this.ncantidad = ncantidad;
    }
    
    public String getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(String iddetalle) {
        this.iddetalle = iddetalle;
    }

    public int getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(int autorizacion) {
        this.autorizacion = autorizacion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getNcuotas() {
        return ncuotas;
    }

    public void setNcuotas(int ncuotas) {
        this.ncuotas = ncuotas;
    }

    public double getMontocuota() {
        return montocuota;
    }

    public void setMontocuota(double montocuota) {
        this.montocuota = montocuota;
    }

    public Date getPrimeracuota() {
        return primeracuota;
    }

    public void setPrimeracuota(Date primeracuota) {
        this.primeracuota = primeracuota;
    }

    public int getNcantidad() {
        return ncantidad;
    }

    public void setNcantidad(int ncantidad) {
        this.ncantidad = ncantidad;
    }

    
}

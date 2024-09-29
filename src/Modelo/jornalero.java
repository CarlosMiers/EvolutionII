/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;


import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Pc_Server
 */
public class jornalero {

    String numero;
    Date fecha;
    ficha_empleado ficha_empleado;
    double dias;
    BigDecimal importe;
    String unidmed;
    String tiempo;
    int sucursal;
    String observacion;

    public jornalero() {

    }

    public jornalero(String numero, Date fecha, ficha_empleado ficha_empleado, double dias, BigDecimal importe, String unidmed, String tiempo, int sucursal, String observacion) {
        this.numero = numero;
        this.fecha = fecha;
        this.ficha_empleado = ficha_empleado;
        this.dias = dias;
        this.importe = importe;
        this.unidmed = unidmed;
        this.tiempo = tiempo;
        this.sucursal = sucursal;
        this.observacion = observacion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ficha_empleado getFicha_empleado() {
        return ficha_empleado;
    }

    public void setFicha_empleado(ficha_empleado ficha_empleado) {
        this.ficha_empleado = ficha_empleado;
    }

    public double getDias() {
        return dias;
    }

    public void setDias(double dias) {
        this.dias = dias;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getUnidmed() {
        return unidmed;
    }

    public void setUnidmed(String unidmed) {
        this.unidmed = unidmed;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    
   
}

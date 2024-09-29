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
public class sancion {

    double numero;
    Date fecha;
    Date inicio;
    Date fin;
    ficha_empleado ficha_empleado;
    int dias;
    int giraduria;
    int tipo_sancion;
    String observacion;

    public sancion() {

    }

    public sancion(double numero, Date fecha, Date inicio, Date fin, ficha_empleado ficha_empleado, int dias, int giraduria, int tipo_sancion, String observacion) {
        this.numero = numero;
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.ficha_empleado = ficha_empleado;
        this.dias = dias;
        this.giraduria = giraduria;
        this.tipo_sancion = tipo_sancion;
        this.observacion = observacion;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public ficha_empleado getFicha_empleado() {
        return ficha_empleado;
    }

    public void setFicha_empleado(ficha_empleado ficha_empleado) {
        this.ficha_empleado = ficha_empleado;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(int giraduria) {
        this.giraduria = giraduria;
    }

    public int getTipo_sancion() {
        return tipo_sancion;
    }

    public void setTipo_sancion(int tipo_sancion) {
        this.tipo_sancion = tipo_sancion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }


    
}

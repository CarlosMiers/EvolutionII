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
public class vacacion {

    Double numero;
    Date fecha;
    Date inicio;
    Date fin;
    ficha_empleado ficha_empleado;
    int horas;
    String unidmed;
    BigDecimal importe;
    int giraduria;
    int periodo;

    public vacacion() {

    }

    public vacacion(Double numero, Date fecha, Date inicio, Date fin, ficha_empleado ficha_empleado, int horas, String unidmed, BigDecimal importe, int giraduria, int periodo) {
        this.numero = numero;
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.ficha_empleado = ficha_empleado;
        this.horas = horas;
        this.unidmed = unidmed;
        this.importe = importe;
        this.giraduria = giraduria;
        this.periodo = periodo;
    }

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
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

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public String getUnidmed() {
        return unidmed;
    }

    public void setUnidmed(String unidmed) {
        this.unidmed = unidmed;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public int getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(int giraduria) {
        this.giraduria = giraduria;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }


    
}

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
public class hora_extra {

    String numero;
    Date fecha;
    ficha_empleado ficha_empleado;
    int horas;
    String unidmed;
    BigDecimal importe;
    String tiempo;
    int tipo_horario;
    int giraduria;

    public hora_extra() {

    }

    public hora_extra(String numero, Date fecha, ficha_empleado ficha_empleado, int horas, String unidmed, BigDecimal importe, String tiempo, int tipo_horario, int giraduria) {
        this.numero = numero;
        this.fecha = fecha;
        this.ficha_empleado = ficha_empleado;
        this.horas = horas;
        this.unidmed = unidmed;
        this.importe = importe;
        this.tiempo = tiempo;
        this.tipo_horario = tipo_horario;
        this.giraduria = giraduria;
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

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public int getTipo_horario() {
        return tipo_horario;
    }

    public void setTipo_horario(int tipo_horario) {
        this.tipo_horario = tipo_horario;
    }

    public int getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(int giraduria) {
        this.giraduria = giraduria;
    }

    
}

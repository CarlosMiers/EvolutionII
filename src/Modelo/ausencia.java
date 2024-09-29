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
public class ausencia {

    String numero;
    Date fecha;
    ficha_empleado ficha_empleado;
    motivo_ausencia motivo_ausencia;
    int dias;
    BigDecimal importe;
    String unidmed;
    String justificado;
    String tiempo;
    int giraduria;
    public ausencia() {

    }

    public ausencia(String numero, Date fecha, ficha_empleado ficha_empleado, motivo_ausencia motivo_ausencia, int dias, BigDecimal importe, String unidmed, String justificado, String tiempo, int giraduria) {
        this.numero = numero;
        this.fecha = fecha;
        this.ficha_empleado = ficha_empleado;
        this.motivo_ausencia = motivo_ausencia;
        this.dias = dias;
        this.importe = importe;
        this.unidmed = unidmed;
        this.justificado = justificado;
        this.tiempo = tiempo;
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

    public motivo_ausencia getMotivo_ausencia() {
        return motivo_ausencia;
    }

    public void setMotivo_ausencia(motivo_ausencia motivo_ausencia) {
        this.motivo_ausencia = motivo_ausencia;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
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

    public String getJustificado() {
        return justificado;
    }

    public void setJustificado(String justificado) {
        this.justificado = justificado;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public int getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(int giraduria) {
        this.giraduria = giraduria;
    }

   
}

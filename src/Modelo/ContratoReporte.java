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
public class ContratoReporte {

    String numero;
    String fecha;
    String socio;
    String nombresocio;
    Double importe;
    String plazo;
    Double montocuota;
    int comercio;
    String nombrecomercial;
    String observacion;

    public void ContratoReporte() {

    }

    public ContratoReporte(String numero, String fecha, String socio, String nombresocio, Double importe, String plazo, Double montocuota, int comercio, String nombrecomercial, String observacion) {
        this.numero = numero;
        this.fecha = fecha;
        this.socio = socio;
        this.nombresocio = nombresocio;
        this.importe = importe;
        this.plazo = plazo;
        this.montocuota = montocuota;
        this.comercio = comercio;
        this.nombrecomercial = nombrecomercial;
        this.observacion = observacion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSocio() {
        return socio;
    }

    public void setSocio(String socio) {
        this.socio = socio;
    }

    public String getNombresocio() {
        return nombresocio;
    }

    public void setNombresocio(String nombresocio) {
        this.nombresocio = nombresocio;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public Double getMontocuota() {
        return montocuota;
    }

    public void setMontocuota(Double montocuota) {
        this.montocuota = montocuota;
    }

    public int getComercio() {
        return comercio;
    }

    public void setComercio(int comercio) {
        this.comercio = comercio;
    }

    public String getNombrecomercial() {
        return nombrecomercial;
    }

    public void setNombrecomercial(String nombrecomercial) {
        this.nombrecomercial = nombrecomercial;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}
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
public class PagareOrdenCredito {

    String numero;
    String fecha;
    String vencimiento;
    String socio;
    String nombresocio;
    Double importe;
    String plazo;
    Double montocuota;
    String comercial;
    Double saldo;

    public PagareOrdenCredito(String numero, String fecha, String vencimiento, String socio, String nombresocio, Double importe, String plazo, Double montocuota, String comercial, Double saldo) {
        this.numero = numero;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.socio = socio;
        this.nombresocio = nombresocio;
        this.importe = importe;
        this.plazo = plazo;
        this.montocuota = montocuota;
        this.comercial = comercial;
        this.saldo = saldo;
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

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
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

    public String getComercial() {
        return comercial;
    }

    public void setComercial(String comercial) {
        this.comercial = comercial;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    
}

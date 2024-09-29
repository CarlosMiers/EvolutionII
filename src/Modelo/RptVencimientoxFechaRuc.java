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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class RptVencimientoxFechaRuc {

    String numero;
    String fecha;
    String vencimiento;
    String socio;
    String nombresocio;
    String cuota;
    Double amortiza;
    Double minteres;
    Double saldo;
    String comprobante;
    String nombrecomprobante;
    String nombremoneda;
    String ruc;

    public void RptVencimientoxFechaRuc() {

    }

    public RptVencimientoxFechaRuc(String numero, String fecha, String vencimiento, String socio, String nombresocio, String cuota, Double amortiza, Double minteres, Double saldo, String comprobante, String nombrecomprobante, String nombremoneda, String ruc) {
        this.numero = numero;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.socio = socio;
        this.nombresocio = nombresocio;
        this.cuota = cuota;
        this.amortiza = amortiza;
        this.minteres = minteres;
        this.saldo = saldo;
        this.comprobante = comprobante;
        this.nombrecomprobante = nombrecomprobante;
        this.nombremoneda = nombremoneda;
        this.ruc = ruc;
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

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public Double getAmortiza() {
        return amortiza;
    }

    public void setAmortiza(Double amortiza) {
        this.amortiza = amortiza;
    }

    public Double getMinteres() {
        return minteres;
    }

    public void setMinteres(Double minteres) {
        this.minteres = minteres;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getNombrecomprobante() {
        return nombrecomprobante;
    }

    public void setNombrecomprobante(String nombrecomprobante) {
        this.nombrecomprobante = nombrecomprobante;
    }

    public String getNombremoneda() {
        return nombremoneda;
    }

    public void setNombremoneda(String nombremoneda) {
        this.nombremoneda = nombremoneda;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }


}

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
public class CobranzaxCobradorReporte {

    String numero;
    String fecha;
    String socio;
    String nombresocio;
    String documento;
    Double pago;
    Double amortiza;
    Double minteres;
    Double gastos;
    Double mora;
    Double punitorio;
    String cuota;
    String cobrador;
    String nombrecobrador;
    String sucursal;
    String moneda;
    String comprobante;

    public void CobranzaxCobradorReporte() {

    }

    public CobranzaxCobradorReporte(String numero, String fecha, String socio, String nombresocio, String documento, Double pago, Double amortiza, Double minteres, Double gastos, Double mora, Double punitorio, String cuota, String cobrador, String nombrecobrador, String sucursal, String moneda, String comprobante) {
        this.numero = numero;
        this.fecha = fecha;
        this.socio = socio;
        this.nombresocio = nombresocio;
        this.documento = documento;
        this.pago = pago;
        this.amortiza = amortiza;
        this.minteres = minteres;
        this.gastos = gastos;
        this.mora = mora;
        this.punitorio = punitorio;
        this.cuota = cuota;
        this.cobrador = cobrador;
        this.nombrecobrador = nombrecobrador;
        this.sucursal = sucursal;
        this.moneda = moneda;
        this.comprobante = comprobante;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Double getPago() {
        return pago;
    }

    public void setPago(Double pago) {
        this.pago = pago;
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

    public Double getGastos() {
        return gastos;
    }

    public void setGastos(Double gastos) {
        this.gastos = gastos;
    }

    public Double getMora() {
        return mora;
    }

    public void setMora(Double mora) {
        this.mora = mora;
    }

    public Double getPunitorio() {
        return punitorio;
    }

    public void setPunitorio(Double punitorio) {
        this.punitorio = punitorio;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public String getCobrador() {
        return cobrador;
    }

    public void setCobrador(String cobrador) {
        this.cobrador = cobrador;
    }

    public String getNombrecobrador() {
        return nombrecobrador;
    }

    public void setNombrecobrador(String nombrecobrador) {
        this.nombrecobrador = nombrecobrador;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    
}

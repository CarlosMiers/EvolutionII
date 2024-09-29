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
public class SaldoxSocioReporte {

    String documento;
    String fecha;
    String vencimiento;
    Double importe;
    Double saldo;
    Double totaldeuda;
    String plazo;
    String nombrecomprobante;
    String nombrecomercio;

    public void ContratoReporte() {

    }

    public SaldoxSocioReporte(String documento, String fecha, String vencimiento, Double importe, Double saldo, Double totaldeuda, String plazo, String nombrecomprobante, String nombrecomercio) {
        this.documento = documento;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.importe = importe;
        this.saldo = saldo;
        this.totaldeuda = totaldeuda;
        this.plazo = plazo;
        this.nombrecomprobante = nombrecomprobante;
        this.nombrecomercio = nombrecomercio;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getTotaldeuda() {
        return totaldeuda;
    }

    public void setTotaldeuda(Double totaldeuda) {
        this.totaldeuda = totaldeuda;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getNombrecomprobante() {
        return nombrecomprobante;
    }

    public void setNombrecomprobante(String nombrecomprobante) {
        this.nombrecomprobante = nombrecomprobante;
    }

    public String getNombrecomercio() {
        return nombrecomercio;
    }

    public void setNombrecomercio(String nombrecomercio) {
        this.nombrecomercio = nombrecomercio;
    }

}

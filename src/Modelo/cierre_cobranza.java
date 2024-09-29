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
public class cierre_cobranza {

    String iddocumento;
    String creferencia;
    String documento;
    Date fecha;
    Date vencimiento;
    Date fecha_cobro;
    String autorizacion;
    cliente cliente;
    sucursal sucursal;
    moneda moneda;
    comprobante comprobante;
    Double enviado;
    Double cobrado;
    giraduria giraduria;
    int numerocuota;
    int cuota;
    double saldo;
    casa comercial;

    public cierre_cobranza() {

    }

    public cierre_cobranza(String iddocumento, String creferencia, String documento, Date fecha, Date vencimiento, Date fecha_cobro, String autorizacion, cliente cliente, sucursal sucursal, moneda moneda, comprobante comprobante, Double enviado, Double cobrado, giraduria giraduria, int numerocuota, int cuota, double saldo, casa comercial) {
        this.iddocumento = iddocumento;
        this.creferencia = creferencia;
        this.documento = documento;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.fecha_cobro = fecha_cobro;
        this.autorizacion = autorizacion;
        this.cliente = cliente;
        this.sucursal = sucursal;
        this.moneda = moneda;
        this.comprobante = comprobante;
        this.enviado = enviado;
        this.cobrado = cobrado;
        this.giraduria = giraduria;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
        this.saldo = saldo;
        this.comercial = comercial;
    }

    public String getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(String iddocumento) {
        this.iddocumento = iddocumento;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Date getFecha_cobro() {
        return fecha_cobro;
    }

    public void setFecha_cobro(Date fecha_cobro) {
        this.fecha_cobro = fecha_cobro;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public Double getEnviado() {
        return enviado;
    }

    public void setEnviado(Double enviado) {
        this.enviado = enviado;
    }

    public Double getCobrado() {
        return cobrado;
    }

    public void setCobrado(Double cobrado) {
        this.cobrado = cobrado;
    }

    public giraduria getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(giraduria giraduria) {
        this.giraduria = giraduria;
    }

    public int getNumerocuota() {
        return numerocuota;
    }

    public void setNumerocuota(int numerocuota) {
        this.numerocuota = numerocuota;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public casa getComercial() {
        return comercial;
    }

    public void setComercial(casa comercial) {
        this.comercial = comercial;
    }

}

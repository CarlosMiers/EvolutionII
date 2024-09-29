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
 * @author SERVIDOR
 */
public class gestioncobro {

    String iddocumento;
    String iddetalle;
    String creferencia;
    Double documento;
    Date fecha;
    Date vencimiento;
    Date fecha_cobro;
    cliente cliente;
    int sucursal;
    int moneda;
    comprobante comprobante;
    int vendedor;
    int caja;
    BigDecimal enviado;
    BigDecimal cobrado;
    giraduria giraduria;
    casa comercial;
    int numerocuota;
    int cuota;
    String autorizacion;
    String nrocuenta;
    BigDecimal minteres;
    BigDecimal amortiza;

    public gestioncobro() {

    }

    public gestioncobro(String iddocumento, String iddetalle, String creferencia, Double documento, Date fecha, Date vencimiento, Date fecha_cobro, cliente cliente, int sucursal, int moneda, comprobante comprobante, int vendedor, int caja, BigDecimal enviado, BigDecimal cobrado, giraduria giraduria, casa comercial, int numerocuota, int cuota, String autorizacion, String nrocuenta, BigDecimal minteres, BigDecimal amortiza) {
        this.iddocumento = iddocumento;
        this.iddetalle = iddetalle;
        this.creferencia = creferencia;
        this.documento = documento;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.fecha_cobro = fecha_cobro;
        this.cliente = cliente;
        this.sucursal = sucursal;
        this.moneda = moneda;
        this.comprobante = comprobante;
        this.vendedor = vendedor;
        this.caja = caja;
        this.enviado = enviado;
        this.cobrado = cobrado;
        this.giraduria = giraduria;
        this.comercial = comercial;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
        this.autorizacion = autorizacion;
        this.nrocuenta = nrocuenta;
        this.minteres = minteres;
        this.amortiza = amortiza;
    }

    public String getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(String iddocumento) {
        this.iddocumento = iddocumento;
    }

    public String getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(String iddetalle) {
        this.iddetalle = iddetalle;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public Double getDocumento() {
        return documento;
    }

    public void setDocumento(Double documento) {
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

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    public BigDecimal getEnviado() {
        return enviado;
    }

    public void setEnviado(BigDecimal enviado) {
        this.enviado = enviado;
    }

    public BigDecimal getCobrado() {
        return cobrado;
    }

    public void setCobrado(BigDecimal cobrado) {
        this.cobrado = cobrado;
    }

    public giraduria getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(giraduria giraduria) {
        this.giraduria = giraduria;
    }

    public casa getComercial() {
        return comercial;
    }

    public void setComercial(casa comercial) {
        this.comercial = comercial;
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

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getNrocuenta() {
        return nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        this.nrocuenta = nrocuenta;
    }

    public BigDecimal getMinteres() {
        return minteres;
    }

    public void setMinteres(BigDecimal minteres) {
        this.minteres = minteres;
    }

    public BigDecimal getAmortiza() {
        return amortiza;
    }

    public void setAmortiza(BigDecimal amortiza) {
        this.amortiza = amortiza;
    }



}

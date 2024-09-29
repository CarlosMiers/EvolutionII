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
public class cuenta_seguros {

    String iddocumento;
    String creferencia;
    String documento;
    Date fecha;
    Date vencimiento;
    Date fechapago;
    cliente cliente;
    int sucursal;
    moneda moneda;
    int vendedor;
    int giraduria;
    int comprobante;
    BigDecimal importe;
    int numerocuota;
    int cuota;
    BigDecimal saldo;
    BigDecimal importeseguro;
    public cuenta_seguros() {

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

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
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

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }

    public int getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(int giraduria) {
        this.giraduria = giraduria;
    }

    public int getComprobante() {
        return comprobante;
    }

    public void setComprobante(int comprobante) {
        this.comprobante = comprobante;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
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

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getImporteseguro() {
        return importeseguro;
    }

    public void setImporteseguro(BigDecimal importeseguro) {
        this.importeseguro = importeseguro;
    }

    public cuenta_seguros(String iddocumento, String creferencia, String documento, Date fecha, Date vencimiento, Date fechapago, cliente cliente, int sucursal, moneda moneda, int vendedor, int giraduria, int comprobante, BigDecimal importe, int numerocuota, int cuota, BigDecimal saldo, BigDecimal importeseguro) {
        this.iddocumento = iddocumento;
        this.creferencia = creferencia;
        this.documento = documento;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.fechapago = fechapago;
        this.cliente = cliente;
        this.sucursal = sucursal;
        this.moneda = moneda;
        this.vendedor = vendedor;
        this.giraduria = giraduria;
        this.comprobante = comprobante;
        this.importe = importe;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
        this.saldo = saldo;
        this.importeseguro = importeseguro;
    }

    
}

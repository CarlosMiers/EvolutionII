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
 * @author Usuario
 */
public class saldo_proveedores {

    String idmovimiento;
    String idreferencia;
    String nrofactura;
    Date emision;
    Date vencimiento;
    proveedor proveedor;
    String nombreproveedor;
    String nombre;
    moneda moneda;
    sucursal sucursal;
    int cuota;
    int numerocuota;
    double importe;
    double saldo;
    double credito;
    double pagos;
    comprobante comprobante;

    public saldo_proveedores() {

    }

    public saldo_proveedores(String idmovimiento, String idreferencia, String nrofactura, Date emision, Date vencimiento, proveedor proveedor, String nombreproveedor, String nombre, moneda moneda, sucursal sucursal, int cuota, int numerocuota, double importe, double saldo, double credito, double pagos, comprobante comprobante) {
        this.idmovimiento = idmovimiento;
        this.idreferencia = idreferencia;
        this.nrofactura = nrofactura;
        this.emision = emision;
        this.vencimiento = vencimiento;
        this.proveedor = proveedor;
        this.nombreproveedor = nombreproveedor;
        this.nombre = nombre;
        this.moneda = moneda;
        this.sucursal = sucursal;
        this.cuota = cuota;
        this.numerocuota = numerocuota;
        this.importe = importe;
        this.saldo = saldo;
        this.credito = credito;
        this.pagos = pagos;
        this.comprobante = comprobante;
    }

    public String getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(String idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    public String getIdreferencia() {
        return idreferencia;
    }

    public void setIdreferencia(String idreferencia) {
        this.idreferencia = idreferencia;
    }

    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(String nrofactura) {
        this.nrofactura = nrofactura;
    }

    public Date getEmision() {
        return emision;
    }

    public void setEmision(Date emision) {
        this.emision = emision;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombreproveedor() {
        return nombreproveedor;
    }

    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public int getNumerocuota() {
        return numerocuota;
    }

    public void setNumerocuota(int numerocuota) {
        this.numerocuota = numerocuota;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public double getPagos() {
        return pagos;
    }

    public void setPagos(double pagos) {
        this.pagos = pagos;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

   
}

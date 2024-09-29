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
public class cuenta_proveedores {

    String idreferencia;
    BigDecimal nrofactura;
    String documento;
    Date fecha;
    Date vencimiento;
    comprobante comprobante;
    proveedor proveedor;
    moneda moneda;
    int sucursal;
    BigDecimal importe;
    Date fecha_pago;
    int numerocuota;
    int cuota;
    String idmovimiento;
    String exentas;
    BigDecimal gravadas10;
    BigDecimal gravadas5;
    double creditos;
    double debitos;
    String observacion;
    String nrorecibo;
    
    
     public cuenta_proveedores(){
        
    }

    public cuenta_proveedores(String idreferencia, BigDecimal nrofactura, String documento, Date fecha, Date vencimiento, comprobante comprobante, proveedor proveedor, moneda moneda, int sucursal, BigDecimal importe, Date fecha_pago, int numerocuota, int cuota, String idmovimiento, String exentas, BigDecimal gravadas10, BigDecimal gravadas5, double creditos, double debitos, String observacion, String nrorecibo) {
        this.idreferencia = idreferencia;
        this.nrofactura = nrofactura;
        this.documento = documento;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.comprobante = comprobante;
        this.proveedor = proveedor;
        this.moneda = moneda;
        this.sucursal = sucursal;
        this.importe = importe;
        this.fecha_pago = fecha_pago;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
        this.idmovimiento = idmovimiento;
        this.exentas = exentas;
        this.gravadas10 = gravadas10;
        this.gravadas5 = gravadas5;
        this.creditos = creditos;
        this.debitos = debitos;
        this.observacion = observacion;
        this.nrorecibo = nrorecibo;
    }

    public String getIdreferencia() {
        return idreferencia;
    }

    public void setIdreferencia(String idreferencia) {
        this.idreferencia = idreferencia;
    }

    public BigDecimal getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(BigDecimal nrofactura) {
        this.nrofactura = nrofactura;
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

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
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

    public String getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(String idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    public String getExentas() {
        return exentas;
    }

    public void setExentas(String exentas) {
        this.exentas = exentas;
    }

    public BigDecimal getGravadas10() {
        return gravadas10;
    }

    public void setGravadas10(BigDecimal gravadas10) {
        this.gravadas10 = gravadas10;
    }

    public BigDecimal getGravadas5() {
        return gravadas5;
    }

    public void setGravadas5(BigDecimal gravadas5) {
        this.gravadas5 = gravadas5;
    }

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }

    public double getDebitos() {
        return debitos;
    }

    public void setDebitos(double debitos) {
        this.debitos = debitos;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNrorecibo() {
        return nrorecibo;
    }

    public void setNrorecibo(String nrorecibo) {
        this.nrorecibo = nrorecibo;
    }

    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author SERVIDOR
 */
public class SaldoxFecha {

    private String cliente;
    private String nombre;
    private String ruc;
    private String documento;
    private String emision;
    private double importe;
    private double pagos;
    private double saldo;
    private int comprobante;
    private String nombrecomprobante;
    private String factura;

    public void SaldoxFecha(){
        
    }

    public SaldoxFecha(String cliente, String nombre, String ruc, String documento, String emision, double importe, double pagos, double saldo, int comprobante, String nombrecomprobante, String factura) {
        this.cliente = cliente;
        this.nombre = nombre;
        this.ruc = ruc;
        this.documento = documento;
        this.emision = emision;
        this.importe = importe;
        this.pagos = pagos;
        this.saldo = saldo;
        this.comprobante = comprobante;
        this.nombrecomprobante = nombrecomprobante;
        this.factura = factura;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmision() {
        return emision;
    }

    public void setEmision(String emision) {
        this.emision = emision;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getPagos() {
        return pagos;
    }

    public void setPagos(double pagos) {
        this.pagos = pagos;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getComprobante() {
        return comprobante;
    }

    public void setComprobante(int comprobante) {
        this.comprobante = comprobante;
    }

    public String getNombrecomprobante() {
        return nombrecomprobante;
    }

    public void setNombrecomprobante(String nombrecomprobante) {
        this.nombrecomprobante = nombrecomprobante;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }
    

    
}

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
public class SaldoFacturas_obra {
  
    int cliente;
    String nombre;
    String factura;
    String fecha;
    String vencimiento;
    String nombrecomprobante;
    double importe;
    double pagos;
    double saldo;
    String ruc;
    String autorizacion;
    int obra;
    String nombreobra;
   
    public void SaldoFacturas_obra(){
        
    }

    public SaldoFacturas_obra(int cliente, String nombre, String factura, String fecha, String vencimiento, String nombrecomprobante, double importe, double pagos, double saldo, String ruc, String autorizacion, int obra, String nombreobra) {
        this.cliente = cliente;
        this.nombre = nombre;
        this.factura = factura;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.nombrecomprobante = nombrecomprobante;
        this.importe = importe;
        this.pagos = pagos;
        this.saldo = saldo;
        this.ruc = ruc;
        this.autorizacion = autorizacion;
        this.obra = obra;
        this.nombreobra = nombreobra;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
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

    public String getNombrecomprobante() {
        return nombrecomprobante;
    }

    public void setNombrecomprobante(String nombrecomprobante) {
        this.nombrecomprobante = nombrecomprobante;
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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public int getObra() {
        return obra;
    }

    public void setObra(int obra) {
        this.obra = obra;
    }

    public String getNombreobra() {
        return nombreobra;
    }

    public void setNombreobra(String nombreobra) {
        this.nombreobra = nombreobra;
    }

    
}

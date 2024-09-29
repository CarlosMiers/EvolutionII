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
public class descuentos_varios {
 String iddescuentosvarios;
 double numero;
 Date fecha;
 sucursal sucursal;
 moneda moneda;
 comprobante servicio;
 Date vencimiento;
 double importe;
 double totales;
 double asiento;
 Date fechaalta;
 Date fechamodi;
 int usuarioalta;
 int usuariomodi;
 
 public descuentos_varios(){
     
 }

    public descuentos_varios(String iddescuentosvarios, double numero, Date fecha, sucursal sucursal, moneda moneda, comprobante servicio, Date vencimiento, double importe, double totales, double asiento, Date fechaalta, Date fechamodi, int usuarioalta, int usuariomodi) {
        this.iddescuentosvarios = iddescuentosvarios;
        this.numero = numero;
        this.fecha = fecha;
        this.sucursal = sucursal;
        this.moneda = moneda;
        this.servicio = servicio;
        this.vencimiento = vencimiento;
        this.importe = importe;
        this.totales = totales;
        this.asiento = asiento;
        this.fechaalta = fechaalta;
        this.fechamodi = fechamodi;
        this.usuarioalta = usuarioalta;
        this.usuariomodi = usuariomodi;
    }

    public String getIddescuentosvarios() {
        return iddescuentosvarios;
    }

    public void setIddescuentosvarios(String iddescuentosvarios) {
        this.iddescuentosvarios = iddescuentosvarios;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public comprobante getServicio() {
        return servicio;
    }

    public void setServicio(comprobante servicio) {
        this.servicio = servicio;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getTotales() {
        return totales;
    }

    public void setTotales(double totales) {
        this.totales = totales;
    }

    public double getAsiento() {
        return asiento;
    }

    public void setAsiento(double asiento) {
        this.asiento = asiento;
    }

    public Date getFechaalta() {
        return fechaalta;
    }

    public void setFechaalta(Date fechaalta) {
        this.fechaalta = fechaalta;
    }

    public Date getFechamodi() {
        return fechamodi;
    }

    public void setFechamodi(Date fechamodi) {
        this.fechamodi = fechamodi;
    }

    public int getUsuarioalta() {
        return usuarioalta;
    }

    public void setUsuarioalta(int usuarioalta) {
        this.usuarioalta = usuarioalta;
    }

    public int getUsuariomodi() {
        return usuariomodi;
    }

    public void setUsuariomodi(int usuariomodi) {
        this.usuariomodi = usuariomodi;
    }

   
 
}

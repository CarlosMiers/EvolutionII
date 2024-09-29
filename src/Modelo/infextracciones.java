/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author SERVIDOR
 */
public class infextracciones {
 private String documento;
 String fecha;
 String vencimiento;
 String cliente;
 String nombrecliente;
 double importe;
 String observaciones;
 String sucursal;

public infextracciones(){
    
}

    public infextracciones(String documento, String fecha, String vencimiento, String cliente, String nombrecliente, double importe, String observaciones, String sucursal) {
        this.documento = documento;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.cliente = cliente;
        this.nombrecliente = nombrecliente;
        this.importe = importe;
        this.observaciones = observaciones;
        this.sucursal = sucursal;
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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
}

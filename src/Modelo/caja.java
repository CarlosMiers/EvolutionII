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
public class caja {

    int codigo;
    String nombre;
    String responsable;
    double factura;
    int timbrado;
    Date vencetimbrado;
    Date iniciotimbrado;
    String ipcaja;
    String acceso;
    double recibo;
    int estado;
    String expedicion;
    String nombrefactura;
    String impresoracaja;

    public caja() {

    }

    public caja(int codigo, String nombre, String responsable, double factura, int timbrado, Date vencetimbrado, Date iniciotimbrado, String ipcaja, String acceso, double recibo, int estado, String expedicion, String nombrefactura, String impresoracaja) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.responsable = responsable;
        this.factura = factura;
        this.timbrado = timbrado;
        this.vencetimbrado = vencetimbrado;
        this.iniciotimbrado = iniciotimbrado;
        this.ipcaja = ipcaja;
        this.acceso = acceso;
        this.recibo = recibo;
        this.estado = estado;
        this.expedicion = expedicion;
        this.nombrefactura = nombrefactura;
        this.impresoracaja = impresoracaja;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public double getFactura() {
        return factura;
    }

    public void setFactura(double factura) {
        this.factura = factura;
    }

    public int getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(int timbrado) {
        this.timbrado = timbrado;
    }

    public Date getVencetimbrado() {
        return vencetimbrado;
    }

    public void setVencetimbrado(Date vencetimbrado) {
        this.vencetimbrado = vencetimbrado;
    }

    public Date getIniciotimbrado() {
        return iniciotimbrado;
    }

    public void setIniciotimbrado(Date iniciotimbrado) {
        this.iniciotimbrado = iniciotimbrado;
    }

    public String getIpcaja() {
        return ipcaja;
    }

    public void setIpcaja(String ipcaja) {
        this.ipcaja = ipcaja;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }

    public double getRecibo() {
        return recibo;
    }

    public void setRecibo(double recibo) {
        this.recibo = recibo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getExpedicion() {
        return expedicion;
    }

    public void setExpedicion(String expedicion) {
        this.expedicion = expedicion;
    }

    public String getNombrefactura() {
        return nombrefactura;
    }

    public void setNombrefactura(String nombrefactura) {
        this.nombrefactura = nombrefactura;
    }

    public String getImpresoracaja() {
        return impresoracaja;
    }

    public void setImpresoracaja(String impresoracaja) {
        this.impresoracaja = impresoracaja;
    }

    
}

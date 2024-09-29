/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class gasto_unidad {
    
    double dnumero;
    String nrofactura;
    Date fechafactura;
    Date vencimiento;
    int tipo;
    comprobante Comprobante;
    rubro rubro;
    moneda moneda;
    edificio unidad;
    String timbrado;
    Date vencimientotimbrado;
    proveedor proveedor;
    double exentas;
    double gravadas10;
    double iva10;
    double gravadas5;
    double iva5;
    double totalneto;
    String observacion;
    plan idcta;
    inmueble inmueble;
    
    public gasto_unidad() {

    }

    public gasto_unidad( double dnumero, String nrofactura, Date fechafactura, Date vencimiento, int tipo, comprobante Comprobante, rubro rubro, 
            moneda Moneda, edificio unidad, String timbrado, Date vencimientotimbrado, proveedor proveedor, double exentas, 
            double gravadas10, double iva10, double gravadas5, double iva5, double totalneto, String observacion, plan plan, inmueble inmueble) {
	this.dnumero = dnumero;
        this.nrofactura = nrofactura;
        this.fechafactura = fechafactura;
        this.vencimiento =  vencimiento;
        this.tipo =    tipo;
        this.Comprobante =  Comprobante;
        this.rubro =   rubro;
        this.moneda =  Moneda;
        this.unidad =  unidad;
        this.timbrado =   timbrado;
        this.vencimientotimbrado = vencimientotimbrado;
        this.proveedor =  proveedor;
        this.exentas = exentas;
        this.gravadas10 = gravadas10;
        this.iva10 =   iva10;
        this.gravadas5 =  gravadas5;
        this.iva5 =    iva5;
        this.totalneto =  totalneto;
        this.observacion =  observacion;
        this.idcta= plan;
        this.inmueble= inmueble;
    }

    public double getDnumero() {
        return dnumero;
    }

    public void setDnumero(double dnumero) {
        this.dnumero = dnumero;
    }

    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(String nrofactura) {
        this.nrofactura = nrofactura;
    }

    public Date getFechafactura() {
        return fechafactura;
    }

    public void setFechafactura(Date fechafactura) {
        this.fechafactura = fechafactura;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public comprobante getComprobante() {
        return Comprobante;
    }

    public void setComprobante(comprobante Comprobante) {
        this.Comprobante = Comprobante;
    }

    public rubro getRubro() {
        return rubro;
    }

    public void setRubro(rubro rubro) {
        this.rubro = rubro;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda Moneda) {
        this.moneda = Moneda;
    }

    public edificio getUnidad() {
        return unidad;
    }

    public void setUnidad(edificio unidad) {
        this.unidad = unidad;
    }

    public String getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(String timbrado) {
        this.timbrado = timbrado;
    }

    public Date getVencimientotimbrado() {
        return vencimientotimbrado;
    }

    public void setVencimientotimbrado(Date vencimientotimbrado) {
        this.vencimientotimbrado = vencimientotimbrado;
    }

    public proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public double getExentas() {
        return exentas;
    }

    public void setExentas(double exentas) {
        this.exentas = exentas;
    }

    public double getGravadas10() {
        return gravadas10;
    }

    public void setGravadas10(double gravadas10) {
        this.gravadas10 = gravadas10;
    }

    public double getIva10() {
        return iva10;
    }

    public void setIva10(double iva10) {
        this.iva10 = iva10;
    }

    public double getGravadas5() {
        return gravadas5;
    }

    public void setGravadas5(double gravadas5) {
        this.gravadas5 = gravadas5;
    }

    public double getIva5() {
        return iva5;
    }

    public void setIva5(double iva5) {
        this.iva5 = iva5;
    }

    public double getTotalneto() {
        return totalneto;
    }

    public void setTotalneto(double totalneto) {
        this.totalneto = totalneto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public plan getIdcta() {
        return idcta;
    }

    public void setIdcta(plan idcta) {
        this.idcta = idcta;
    }

    public inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(inmueble inmueble) {
        this.inmueble = inmueble;
    }

   

}

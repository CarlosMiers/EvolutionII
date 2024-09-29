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
public class sucursal {

    int codigo;
    String nombre;
    String nombreconductor;
    String marcavehiculo;
    String chapa;
    String direccion;
    String telefono;
    String responsable;
    String fonoresponsable;
    String cedulaconductor;
    String ruc;
    double factura;
    double notacredito;
    double nroretencion;
    Date vencetimbrado;
    String nrotimbrado;
    String expedicion;
    String expedicion_nota;
    String nombrefacturasuc;
    String nombreremisionsuc;
    String nombrerecibosuc;
    String impresorafacturasuc;
    String impresorarecibosuc;
    String impresoraremisionsuc;

    public sucursal() {

    }

    public sucursal(int codigo, String nombre, String nombreconductor, String marcavehiculo, String chapa, String direccion, String telefono, String responsable, String fonoresponsable, String cedulaconductor, String ruc, double factura, double notacredito, double nroretencion, Date vencetimbrado, String nrotimbrado, String expedicion, String expedicion_nota, String nombrefacturasuc, String nombreremisionsuc, String nombrerecibosuc, String impresorafacturasuc, String impresorarecibosuc, String impresoraremisionsuc) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nombreconductor = nombreconductor;
        this.marcavehiculo = marcavehiculo;
        this.chapa = chapa;
        this.direccion = direccion;
        this.telefono = telefono;
        this.responsable = responsable;
        this.fonoresponsable = fonoresponsable;
        this.cedulaconductor = cedulaconductor;
        this.ruc = ruc;
        this.factura = factura;
        this.notacredito = notacredito;
        this.nroretencion = nroretencion;
        this.vencetimbrado = vencetimbrado;
        this.nrotimbrado = nrotimbrado;
        this.expedicion = expedicion;
        this.expedicion_nota = expedicion_nota;
        this.nombrefacturasuc = nombrefacturasuc;
        this.nombreremisionsuc = nombreremisionsuc;
        this.nombrerecibosuc = nombrerecibosuc;
        this.impresorafacturasuc = impresorafacturasuc;
        this.impresorarecibosuc = impresorarecibosuc;
        this.impresoraremisionsuc = impresoraremisionsuc;
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

    public String getNombreconductor() {
        return nombreconductor;
    }

    public void setNombreconductor(String nombreconductor) {
        this.nombreconductor = nombreconductor;
    }

    public String getMarcavehiculo() {
        return marcavehiculo;
    }

    public void setMarcavehiculo(String marcavehiculo) {
        this.marcavehiculo = marcavehiculo;
    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getFonoresponsable() {
        return fonoresponsable;
    }

    public void setFonoresponsable(String fonoresponsable) {
        this.fonoresponsable = fonoresponsable;
    }

    public String getCedulaconductor() {
        return cedulaconductor;
    }

    public void setCedulaconductor(String cedulaconductor) {
        this.cedulaconductor = cedulaconductor;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public double getFactura() {
        return factura;
    }

    public void setFactura(double factura) {
        this.factura = factura;
    }

    public double getNotacredito() {
        return notacredito;
    }

    public void setNotacredito(double notacredito) {
        this.notacredito = notacredito;
    }

    public double getNroretencion() {
        return nroretencion;
    }

    public void setNroretencion(double nroretencion) {
        this.nroretencion = nroretencion;
    }

    public Date getVencetimbrado() {
        return vencetimbrado;
    }

    public void setVencetimbrado(Date vencetimbrado) {
        this.vencetimbrado = vencetimbrado;
    }

    public String getNrotimbrado() {
        return nrotimbrado;
    }

    public void setNrotimbrado(String nrotimbrado) {
        this.nrotimbrado = nrotimbrado;
    }

    public String getExpedicion() {
        return expedicion;
    }

    public void setExpedicion(String expedicion) {
        this.expedicion = expedicion;
    }

    public String getExpedicion_nota() {
        return expedicion_nota;
    }

    public void setExpedicion_nota(String expedicion_nota) {
        this.expedicion_nota = expedicion_nota;
    }

    public String getNombrefacturasuc() {
        return nombrefacturasuc;
    }

    public void setNombrefacturasuc(String nombrefacturasuc) {
        this.nombrefacturasuc = nombrefacturasuc;
    }

    public String getNombreremisionsuc() {
        return nombreremisionsuc;
    }

    public void setNombreremisionsuc(String nombreremisionsuc) {
        this.nombreremisionsuc = nombreremisionsuc;
    }

    public String getNombrerecibosuc() {
        return nombrerecibosuc;
    }

    public void setNombrerecibosuc(String nombrerecibosuc) {
        this.nombrerecibosuc = nombrerecibosuc;
    }

    public String getImpresorafacturasuc() {
        return impresorafacturasuc;
    }

    public void setImpresorafacturasuc(String impresorafacturasuc) {
        this.impresorafacturasuc = impresorafacturasuc;
    }

    public String getImpresorarecibosuc() {
        return impresorarecibosuc;
    }

    public void setImpresorarecibosuc(String impresorarecibosuc) {
        this.impresorarecibosuc = impresorarecibosuc;
    }

    public String getImpresoraremisionsuc() {
        return impresoraremisionsuc;
    }

    public void setImpresoraremisionsuc(String impresoraremisionsuc) {
        this.impresoraremisionsuc = impresoraremisionsuc;
    }


}

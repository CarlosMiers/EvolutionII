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
public class FacturaVenta {

    String factura;
    String ruc;
    String nombre;
    String direccion;
    String telefono;
    int comprobante;
    String remisionnro;
    //
    String codprod;
    String nombreproducto;
    Double precio;
    Double iva;
    Double monto;
    Double cantidad;
    //
    Double totalexentas;
    Double totaliva5;
    Double totaliva10;
    Double totalneto;

    public void FacturaVenta() {

    }

    public FacturaVenta(String factura,String ruc, String nombre, String direccion, String telefono, int comprobante, String remisionnro, String codprod, String nombreproducto, Double precio, Double iva, Double monto, Double cantidad, Double totalexentas, Double totaliva5, Double totaliva10, Double totalneto) {
        this.factura = factura;
        this.ruc = ruc;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.comprobante = comprobante;
        this.remisionnro = remisionnro;
        this.codprod = codprod;
        this.nombreproducto = nombreproducto;
        this.precio = precio;
        this.iva = iva;
        this.monto = monto;
        this.cantidad = cantidad;
        this.totalexentas = totalexentas;
        this.totaliva5 = totaliva5;
        this.totaliva10 = totaliva10;
        this.totalneto = totalneto;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getComprobante() {
        return comprobante;
    }

    public void setComprobante(int comprobante) {
        this.comprobante = comprobante;
    }

    public String getRemisionnro() {
        return remisionnro;
    }

    public void setRemisionnro(String remisionnro) {
        this.remisionnro = remisionnro;
    }

    public String getCodprod() {
        return codprod;
    }

    public void setCodprod(String codprod) {
        this.codprod = codprod;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotalexentas() {
        return totalexentas;
    }

    public void setTotalexentas(Double totalexentas) {
        this.totalexentas = totalexentas;
    }

    public Double getTotaliva5() {
        return totaliva5;
    }

    public void setTotaliva5(Double totaliva5) {
        this.totaliva5 = totaliva5;
    }

    public Double getTotaliva10() {
        return totaliva10;
    }

    public void setTotaliva10(Double totaliva10) {
        this.totaliva10 = totaliva10;
    }

    public Double getTotalneto() {
        return totalneto;
    }

    public void setTotalneto(Double totalneto) {
        this.totalneto = totalneto;
    }

}

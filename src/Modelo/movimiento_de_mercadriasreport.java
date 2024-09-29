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
public class movimiento_de_mercadriasreport {

    String producto;
    String nombreproducto;
    Double entradas;
    Double compra;
    Double transferenciaE;
    Double salidas;
    Double venta;
    Double transferenciaS;
    int rubro;
    String nombrerubro;

    public void movimiento_de_mercadriasreport() {

    }

    public movimiento_de_mercadriasreport(String producto, String nombreproducto, Double entradas, Double compra, Double transferenciaE, Double salidas, Double venta, Double transferenciaS, int rubro, String nombrerubro) {
        this.producto = producto;
        this.nombreproducto = nombreproducto;
        this.entradas = entradas;
        this.compra = compra;
        this.transferenciaE = transferenciaE;
        this.salidas = salidas;
        this.venta = venta;
        this.transferenciaS = transferenciaS;
        this.rubro = rubro;
        this.nombrerubro = nombrerubro;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public Double getEntradas() {
        return entradas;
    }

    public void setEntradas(Double entradas) {
        this.entradas = entradas;
    }

    public Double getCompra() {
        return compra;
    }

    public void setCompra(Double compra) {
        this.compra = compra;
    }

    public Double getTransferenciaE() {
        return transferenciaE;
    }

    public void setTransferenciaE(Double transferenciaE) {
        this.transferenciaE = transferenciaE;
    }

    public Double getSalidas() {
        return salidas;
    }

    public void setSalidas(Double salidas) {
        this.salidas = salidas;
    }

    public Double getVenta() {
        return venta;
    }

    public void setVenta(Double venta) {
        this.venta = venta;
    }

    public Double getTransferenciaS() {
        return transferenciaS;
    }

    public void setTransferenciaS(Double transferenciaS) {
        this.transferenciaS = transferenciaS;
    }

    public int getRubro() {
        return rubro;
    }

    public void setRubro(int rubro) {
        this.rubro = rubro;
    }

    public String getNombrerubro() {
        return nombrerubro;
    }

    public void setNombrerubro(String nombrerubro) {
        this.nombrerubro = nombrerubro;
    }

   
}

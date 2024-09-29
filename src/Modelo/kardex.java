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
 * @author Pc_Server
 */
public class kardex {

    sucursal sucursal;
    BigDecimal numero;
    Date fecha;
    int comprobante;
    String nombrecomprobante;
    String producto;
    String nombreproducto;
    BigDecimal costo;
    rubro rubro;
    marca marca;
    int ubicacion;
    medida medida;
    
    Double entrada;
    Double salida;
    Double precio;
    
    Double compras;
    Double ventas;
    Double ingresos;
    Double egresos;
    Double tentrada;
    Double tsalida;
    
    public kardex() {

    }

    public kardex(sucursal sucursal, BigDecimal numero, Date fecha, int comprobante, String nombrecomprobante, String producto, String nombreproducto, BigDecimal costo, rubro rubro, marca marca, int ubicacion, medida medida, Double entrada, Double salida, Double precio, Double compras, Double ventas, Double ingresos, Double egresos, Double tentrada, Double tsalida) {
        this.sucursal = sucursal;
        this.numero = numero;
        this.fecha = fecha;
        this.comprobante = comprobante;
        this.nombrecomprobante = nombrecomprobante;
        this.producto = producto;
        this.nombreproducto = nombreproducto;
        this.costo = costo;
        this.rubro = rubro;
        this.marca = marca;
        this.ubicacion = ubicacion;
        this.medida = medida;
        this.entrada = entrada;
        this.salida = salida;
        this.precio = precio;
        this.compras = compras;
        this.ventas = ventas;
        this.ingresos = ingresos;
        this.egresos = egresos;
        this.tentrada = tentrada;
        this.tsalida = tsalida;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public BigDecimal getNumero() {
        return numero;
    }

    public void setNumero(BigDecimal numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public rubro getRubro() {
        return rubro;
    }

    public void setRubro(rubro rubro) {
        this.rubro = rubro;
    }

    public marca getMarca() {
        return marca;
    }

    public void setMarca(marca marca) {
        this.marca = marca;
    }

    public int getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(int ubicacion) {
        this.ubicacion = ubicacion;
    }

    public medida getMedida() {
        return medida;
    }

    public void setMedida(medida medida) {
        this.medida = medida;
    }

    public Double getEntrada() {
        return entrada;
    }

    public void setEntrada(Double entrada) {
        this.entrada = entrada;
    }

    public Double getSalida() {
        return salida;
    }

    public void setSalida(Double salida) {
        this.salida = salida;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getCompras() {
        return compras;
    }

    public void setCompras(Double compras) {
        this.compras = compras;
    }

    public Double getVentas() {
        return ventas;
    }

    public void setVentas(Double ventas) {
        this.ventas = ventas;
    }

    public Double getIngresos() {
        return ingresos;
    }

    public void setIngresos(Double ingresos) {
        this.ingresos = ingresos;
    }

    public Double getEgresos() {
        return egresos;
    }

    public void setEgresos(Double egresos) {
        this.egresos = egresos;
    }

    public Double getTentrada() {
        return tentrada;
    }

    public void setTentrada(Double tentrada) {
        this.tentrada = tentrada;
    }

    public Double getTsalida() {
        return tsalida;
    }

    public void setTsalida(Double tsalida) {
        this.tsalida = tsalida;
    }

    
}

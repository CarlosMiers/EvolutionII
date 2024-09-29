/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author Pc_Server
 */
public class atajo_producto {

    String codigo;
    String nombre;
    Date fecha_ingreso;
    double stock;
    double stockactual;

    public atajo_producto() {

    }

    public atajo_producto(String codigo, String nombre, Date fecha_ingreso, double stock, double stockactual) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fecha_ingreso = fecha_ingreso;
        this.stock = stock;
        this.stockactual = stockactual;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getStockactual() {
        return stockactual;
    }

    public void setStockactual(double stockactual) {
        this.stockactual = stockactual;
    }

}

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
public class detalle_preventa {
    double iditem;
    int iddetalle;
    producto codprod;
    double cantidad;
    String comentario;
    double prcosto;
    double precio;
    double monto;
    double impiva;
    double porcentaje;

    public detalle_preventa() {

    }

    public detalle_preventa(double iditem, int iddetalle, producto codprod, double cantidad, String comentario, double prcosto, double precio, double monto, double impiva, double porcentaje) {
        this.iditem = iditem;
        this.iddetalle = iddetalle;
        this.codprod = codprod;
        this.cantidad = cantidad;
        this.comentario = comentario;
        this.prcosto = prcosto;
        this.precio = precio;
        this.monto = monto;
        this.impiva = impiva;
        this.porcentaje = porcentaje;
    }

    public double getIditem() {
        return iditem;
    }

    public void setIditem(double iditem) {
        this.iditem = iditem;
    }

    public int getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(int iddetalle) {
        this.iddetalle = iddetalle;
    }

    public producto getCodprod() {
        return codprod;
    }

    public void setCodprod(producto codprod) {
        this.codprod = codprod;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getPrcosto() {
        return prcosto;
    }

    public void setPrcosto(double prcosto) {
        this.prcosto = prcosto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getImpiva() {
        return impiva;
    }

    public void setImpiva(double impiva) {
        this.impiva = impiva;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }


}

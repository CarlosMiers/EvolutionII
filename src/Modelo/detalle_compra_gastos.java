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
public class detalle_compra_gastos {
  
    String dreferencia;
    String descripcion;
    double cantidad;
    double prcosto;
    double monto;
    double impiva;
    double porcentaje;
    plan idcta;
    double itemid;
    
    public detalle_compra_gastos(){
        
    }

    public detalle_compra_gastos(String dreferencia, String descripcion, double cantidad, double prcosto, double monto, double impiva, double porcentaje, plan idcta, double itemid) {
        this.dreferencia = dreferencia;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.prcosto = prcosto;
        this.monto = monto;
        this.impiva = impiva;
        this.porcentaje = porcentaje;
        this.idcta = idcta;
        this.itemid = itemid;
    }

    public String getDreferencia() {
        return dreferencia;
    }

    public void setDreferencia(String dreferencia) {
        this.dreferencia = dreferencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrcosto() {
        return prcosto;
    }

    public void setPrcosto(double prcosto) {
        this.prcosto = prcosto;
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

    public plan getIdcta() {
        return idcta;
    }

    public void setIdcta(plan idcta) {
        this.idcta = idcta;
    }

    public double getItemid() {
        return itemid;
    }

    public void setItemid(double itemid) {
        this.itemid = itemid;
    }

    
}

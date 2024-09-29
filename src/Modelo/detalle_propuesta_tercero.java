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
public class detalle_propuesta_tercero {

    double dnumero;
    int item;
    String proveedor;
    Double presupuestado;
    String aprobado;

    public detalle_propuesta_tercero() {

    }

    public detalle_propuesta_tercero(double dnumero, int item, String proveedor, Double presupuestado, String aprobado) {
        this.dnumero = dnumero;
        this.item = item;
        this.proveedor = proveedor;
        this.presupuestado = presupuestado;
        this.aprobado = aprobado;
    }

    public double getDnumero() {
        return dnumero;
    }

    public void setDnumero(double dnumero) {
        this.dnumero = dnumero;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Double getPresupuestado() {
        return presupuestado;
    }

    public void setPresupuestado(Double presupuestado) {
        this.presupuestado = presupuestado;
    }

    public String getAprobado() {
        return aprobado;
    }

    public void setAprobado(String aprobado) {
        this.aprobado = aprobado;
    }

}

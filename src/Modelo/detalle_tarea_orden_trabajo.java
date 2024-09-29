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
public class detalle_tarea_orden_trabajo {

    double dnumero;
    int item;
    String descripcion;
    String estado;

    public detalle_tarea_orden_trabajo() {

    }

    public detalle_tarea_orden_trabajo(double dnumero, int item, String descripcion, String estado) {
        this.dnumero = dnumero;
        this.item = item;
        this.descripcion = descripcion;
        this.estado = estado;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}

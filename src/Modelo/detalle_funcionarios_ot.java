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
public class detalle_funcionarios_ot {

    double dnumero;
    int item;
    ficha_empleado empleado;

    public detalle_funcionarios_ot() {

    }

    public detalle_funcionarios_ot(double dnumero, int item, ficha_empleado empleado) {
        this.dnumero = dnumero;
        this.item = item;
        this.empleado = empleado;
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

    public ficha_empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(ficha_empleado empleado) {
        this.empleado = empleado;
    }
    
}

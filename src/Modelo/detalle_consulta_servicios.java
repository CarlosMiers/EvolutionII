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
public class detalle_consulta_servicios {

    double dnumero;
    producto servicio;
    double importe;

    public detalle_consulta_servicios() {

    }

    public detalle_consulta_servicios(double dnumero, producto servicio, double importe) {
        this.dnumero = dnumero;
        this.servicio = servicio;
        this.importe = importe;
    }

    public double getDnumero() {
        return dnumero;
    }

    public void setDnumero(double idnumero) {
        this.dnumero = idnumero;
    }

    public producto getServicio() {
        return servicio;
    }

    public void setServicio(producto servicio) {
        this.servicio = servicio;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

}

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
public class cuoterocredito {

    comprobante comprobante;
    int id;
    double importe;
    double cuota2;
    double cuota3;

   
    public cuoterocredito() {

    }

    public cuoterocredito(comprobante comprobante, int id, double importe, double cuota2, double cuota3) {
        this.comprobante = comprobante;
        this.id = id;
        this.importe = importe;
        this.cuota2 = cuota2;
        this.cuota3 = cuota3;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getCuota2() {
        return cuota2;
    }

    public void setCuota2(double cuota2) {
        this.cuota2 = cuota2;
    }

    public double getCuota3() {
        return cuota3;
    }

    public void setCuota3(double cuota3) {
        this.cuota3 = cuota3;
    }
    

}

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
public class detalle_derecho_examen {

    double idnota;
    double item;
    cliente codalumno;
    int matricula_pago;
    int cuota_pago;
    String iddocumento;
    double importe_examen;

    public detalle_derecho_examen() {

    }

    public detalle_derecho_examen(double idnota, double item, cliente codalumno, int matricula_pago, int cuota_pago, String iddocumento, double importe_examen) {
        this.idnota = idnota;
        this.item = item;
        this.codalumno = codalumno;
        this.matricula_pago = matricula_pago;
        this.cuota_pago = cuota_pago;
        this.iddocumento = iddocumento;
        this.importe_examen = importe_examen;
    }

    public double getIdnota() {
        return idnota;
    }

    public void setIdnota(double idnota) {
        this.idnota = idnota;
    }

    public double getItem() {
        return item;
    }

    public void setItem(double item) {
        this.item = item;
    }

    public cliente getCodalumno() {
        return codalumno;
    }

    public void setCodalumno(cliente codalumno) {
        this.codalumno = codalumno;
    }

    public int getMatricula_pago() {
        return matricula_pago;
    }

    public void setMatricula_pago(int matricula_pago) {
        this.matricula_pago = matricula_pago;
    }

    public int getCuota_pago() {
        return cuota_pago;
    }

    public void setCuota_pago(int cuota_pago) {
        this.cuota_pago = cuota_pago;
    }

    public String getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(String iddocumento) {
        this.iddocumento = iddocumento;
    }

    public double getImporte_examen() {
        return importe_examen;
    }

    public void setImporte_examen(double importe_examen) {
        this.importe_examen = importe_examen;
    }

}

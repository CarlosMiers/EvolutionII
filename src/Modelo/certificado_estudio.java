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
public class certificado_estudio {

    double idnota;
    double item;
    cliente codalumno;
    int nota;
    String estado;

    public certificado_estudio() {

    }

    public certificado_estudio(double idnota, double item, cliente codalumno, int nota, String estado) {
        this.idnota = idnota;
        this.item = item;
        this.codalumno = codalumno;
        this.nota = nota;
        this.estado = estado;
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

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

}

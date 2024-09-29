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
public class ExtractoFuncionario {

    String documento;
    String fecha;
    String observacion;
    String nombre;
    Double creditos;
    Double debitos;

    public void ExtractoFuncionario() {

    }

    public ExtractoFuncionario(String documento, String fecha, String observacion, String nombre, Double creditos, Double debitos) {
        this.documento = documento;
        this.fecha = fecha;
        this.observacion = observacion;
        this.nombre = nombre;
        this.creditos = creditos;
        this.debitos = debitos;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getCreditos() {
        return creditos;
    }

    public void setCreditos(Double creditos) {
        this.creditos = creditos;
    }

    public Double getDebitos() {
        return debitos;
    }

    public void setDebitos(Double debitos) {
        this.debitos = debitos;
    }

    
}

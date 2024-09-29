/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import java.sql.Date;


/**
 *
 * @author Pc_Server
 */
public class vacancias {
    double numero;
    Date fecha;
    String nombrepuesto;
    String descripcion;
    cargo perfil;
    String edades;
    int sexo;
    int disponible;
    int cupos;
    
    
    public vacancias(){
        
    }

    public vacancias(double numero, Date fecha, String nombrepuesto, String descripcion, cargo perfil, String edades, int sexo, int disponible, int cupos) {
        this.numero = numero;
        this.fecha = fecha;
        this.nombrepuesto = nombrepuesto;
        this.descripcion = descripcion;
        this.perfil = perfil;
        this.edades = edades;
        this.sexo = sexo;
        this.disponible = disponible;
        this.cupos = cupos;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombrepuesto() {
        return nombrepuesto;
    }

    public void setNombrepuesto(String nombrepuesto) {
        this.nombrepuesto = nombrepuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public cargo getPerfil() {
        return perfil;
    }

    public void setPerfil(cargo perfil) {
        this.perfil = perfil;
    }

    public String getEdades() {
        return edades;
    }

    public void setEdades(String edades) {
        this.edades = edades;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    
}

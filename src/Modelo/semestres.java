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
public class semestres {
    carrera idcarrera;
    int iditem;
    int semestre;
    String nombre;
    
    public semestres(){
        
    }

    public semestres(carrera idcarrera, int iditem, int semestre, String nombre) {
        this.idcarrera = idcarrera;
        this.iditem = iditem;
        this.semestre = semestre;
        this.nombre = nombre;
    }

    public carrera getIdcarrera() {
        return idcarrera;
    }

    public void setIdcarrera(carrera idcarrera) {
        this.idcarrera = idcarrera;
    }

    public int getIditem() {
        return iditem;
    }

    public void setIditem(int iditem) {
        this.iditem = iditem;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}

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
public class pensum {

    int iditem;
    String nombre;
    int periodo;
    carrera carrera;

    public pensum() {

    }

    public pensum(int iditem, String nombre, int periodo, carrera carrera) {
        this.iditem = iditem;
        this.nombre = nombre;
        this.periodo = periodo;
        this.carrera = carrera;
    }

    public int getIditem() {
        return iditem;
    }

    public void setIditem(int iditem) {
        this.iditem = iditem;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(carrera carrera) {
        this.carrera = carrera;
    }
    
   
}

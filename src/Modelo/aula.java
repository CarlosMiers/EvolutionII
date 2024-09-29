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
public class aula {
    int codigo;
    String nombre;
    edificio_sede edificio;
    int capacidad;
    String observacion;
    int estado;
    
    public aula(){
        
    }

    public aula(int codigo, String nombre, edificio_sede edificio, int capacidad, String observacion, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.edificio = edificio;
        this.capacidad = capacidad;
        this.observacion = observacion;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public edificio_sede getEdificio() {
        return edificio;
    }

    public void setEdificio(edificio_sede edificio) {
        this.edificio = edificio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
            
    
    
}

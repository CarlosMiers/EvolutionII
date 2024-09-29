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
public class carrera {

    int codigo;
    String nombre;
    String titulo;
    int duracion;
    String periodo_duracion;
    int semestres;
    facultad facultad;
    int estado;

    public carrera() {

    }

    public carrera(int codigo, String nombre, String titulo, int duracion, String periodo_duracion, int semestres, facultad facultad, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.titulo = titulo;
        this.duracion = duracion;
        this.periodo_duracion = periodo_duracion;
        this.semestres = semestres;
        this.facultad = facultad;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getPeriodo_duracion() {
        return periodo_duracion;
    }

    public void setPeriodo_duracion(String periodo_duracion) {
        this.periodo_duracion = periodo_duracion;
    }

    public int getSemestres() {
        return semestres;
    }

    public void setSemestres(int semestres) {
        this.semestres = semestres;
    }

    public facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(facultad facultad) {
        this.facultad = facultad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
}

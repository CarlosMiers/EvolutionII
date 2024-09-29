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
public class Plancta {
    String codigo;
    String nombre;
    String naturaleza;
    int nivel;
    String asentable;
    String corte;
     public void PlanCta(){     
        
    }

    public Plancta(String codigo, String nombre, String naturaleza, int nivel, String asentable, String corte) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.naturaleza = naturaleza;
        this.nivel = nivel;
        this.asentable = asentable;
        this.corte = corte;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getAsentable() {
        return asentable;
    }

    public void setAsentable(String asentable) {
        this.asentable = asentable;
    }

    public String getCorte() {
        return corte;
    }

    public void setCorte(String corte) {
        this.corte = corte;
    }


   
}

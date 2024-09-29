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
public class manzana {
    int codigo;
    String nombre;
    Double superficie;
    int lotes;
    loteamiento loteamiento;
    
    public manzana(){
        
    }

    public manzana(int codigo, String nombre, Double superficie, int lotes, loteamiento loteamiento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.superficie = superficie;
        this.lotes = lotes;
        this.loteamiento = loteamiento;
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

    public Double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Double superficie) {
        this.superficie = superficie;
    }

    public int getLotes() {
        return lotes;
    }

    public void setLotes(int lotes) {
        this.lotes = lotes;
    }

    public loteamiento getLoteamiento() {
        return loteamiento;
    }

    public void setLoteamiento(loteamiento loteamiento) {
        this.loteamiento = loteamiento;
    }
    
    
    
    
    
}

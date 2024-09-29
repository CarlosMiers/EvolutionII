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
public class lote {

    int codigo;
    String nombre;
    loteamiento loteamiento;
    int manzanas;
    String nombremanzanas;
    int nrolote;
    String superficie;
    String estado;

    public lote() {

    }

    public lote(int codigo, String nombre, loteamiento loteamiento, int manzanas, String nombremanzanas, int nrolote, String superficie, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.loteamiento = loteamiento;
        this.manzanas = manzanas;
        this.nombremanzanas = nombremanzanas;
        this.nrolote = nrolote;
        this.superficie = superficie;
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

    public loteamiento getLoteamiento() {
        return loteamiento;
    }

    public void setLoteamiento(loteamiento loteamiento) {
        this.loteamiento = loteamiento;
    }

    public int getManzanas() {
        return manzanas;
    }

    public void setManzanas(int manzanas) {
        this.manzanas = manzanas;
    }

    public int getNrolote() {
        return nrolote;
    }

    public void setNrolote(int nrolote) {
        this.nrolote = nrolote;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombremanzanas() {
        return nombremanzanas;
    }

    public void setNombremanzanas(String nombremanzanas) {
        this.nombremanzanas = nombremanzanas;
    }

   
}

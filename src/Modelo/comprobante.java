/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;

/**
 *
 * @author SERVIDOR
 */
public class comprobante {
    int codigo;
    String nombre;
    BigDecimal tasainteres;
    BigDecimal tasainteresmora;
    BigDecimal interesmora;
    BigDecimal interespunitorio;
    
    public comprobante(){
        
    }

    public comprobante(int codigo, String nombre, BigDecimal tasainteres, BigDecimal tasainteresmora, BigDecimal interesmora, BigDecimal interespunitorio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tasainteres = tasainteres;
        this.tasainteresmora = tasainteresmora;
        this.interesmora = interesmora;
        this.interespunitorio = interespunitorio;
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

    public BigDecimal getTasainteres() {
        return tasainteres;
    }

    public void setTasainteres(BigDecimal tasainteres) {
        this.tasainteres = tasainteres;
    }

    public BigDecimal getTasainteresmora() {
        return tasainteresmora;
    }

    public void setTasainteresmora(BigDecimal tasainteresmora) {
        this.tasainteresmora = tasainteresmora;
    }

    public BigDecimal getInteresmora() {
        return interesmora;
    }

    public void setInteresmora(BigDecimal interesmora) {
        this.interesmora = interesmora;
    }

    public BigDecimal getInterespunitorio() {
        return interespunitorio;
    }

    public void setInterespunitorio(BigDecimal interespunitorio) {
        this.interespunitorio = interespunitorio;
    }

    
}

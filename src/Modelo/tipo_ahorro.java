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
public class tipo_ahorro {

    int codigo;
    String nombre;
    double interesanual;
    String pagointeres;
    int tipo;

    public tipo_ahorro() {

    }

    public tipo_ahorro(int codigo, String nombre, double interesanual, String pagointeres, int tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.interesanual = interesanual;
        this.pagointeres = pagointeres;
        this.tipo = tipo;
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

    public double getInteresanual() {
        return interesanual;
    }

    public void setInteresanual(double interesanual) {
        this.interesanual = interesanual;
    }

    public String getPagointeres() {
        return pagointeres;
    }

    public void setPagointeres(String pagointeres) {
        this.pagointeres = pagointeres;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    
}

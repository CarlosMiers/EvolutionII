/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author SERVIDOR
 */
public class rubro_compra {
int codigo;
String nombre;
plan idcta;
    
    public rubro_compra(){
        
    }

    public rubro_compra(int codigo, String nombre, plan idcta) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idcta = idcta;
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

    public plan getIdcta() {
        return idcta;
    }

    public void setIdcta(plan idcta) {
        this.idcta = idcta;
    }

}

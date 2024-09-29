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
public class barrio {
   int codigo;
   String nombre;
   localidad codlocalidad;
   public barrio(){
       
   }

    public barrio(int codigo, String nombre, localidad codlocalidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.codlocalidad = codlocalidad;
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

    public localidad getCodlocalidad() {
        return codlocalidad;
    }

    public void setCodlocalidad(localidad codlocalidad) {
        this.codlocalidad = codlocalidad;
    }

}

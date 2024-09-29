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
public class departamento_laboral {
   
int  codigo;
String Nombre;
 public departamento_laboral(){
     
 }   

    public departamento_laboral(int codigo, String Nombre) {
        this.codigo = codigo;
        this.Nombre = Nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
 
 
}

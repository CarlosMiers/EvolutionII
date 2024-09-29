/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Usuario
 */
public class concepto_salario {
     int codigo;
     String nombre;
     String tipo;
     plan idcta;
     int estado;
   
     
    public concepto_salario(){
        
    }

    public concepto_salario(int codigo, String nombre, String tipo, plan idcta, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.idcta = idcta;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public plan getIdcta() {
        return idcta;
    }

    public void setIdcta(plan idcta) {
        this.idcta = idcta;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

   
}

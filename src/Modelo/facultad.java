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
public class facultad {

    int codigo;
    String nombre;
    String rector;
    String decano;
    String secretario_general;
    
    public facultad(){
        
    }

    public facultad(int codigo, String nombre, String rector, String decano, String secretario_general) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.rector = rector;
        this.decano = decano;
        this.secretario_general = secretario_general;
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

    public String getRector() {
        return rector;
    }

    public void setRector(String rector) {
        this.rector = rector;
    }

    public String getDecano() {
        return decano;
    }

    public void setDecano(String decano) {
        this.decano = decano;
    }

    public String getSecretario_general() {
        return secretario_general;
    }

    public void setSecretario_general(String secretario_general) {
        this.secretario_general = secretario_general;
    }
    
    
    

}

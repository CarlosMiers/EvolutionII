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
public class experiencia_cargo {
    int idtem;
    String descripcion;
    cargo idcargo;
    
    public experiencia_cargo(){
        
    }

    public experiencia_cargo(int idtem, String descripcion, cargo idcargo) {
        this.idtem = idtem;
        this.descripcion = descripcion;
        this.idcargo = idcargo;
    }

    public int getIdtem() {
        return idtem;
    }

    public void setIdtem(int idtem) {
        this.idtem = idtem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public cargo getIdcargo() {
        return idcargo;
    }

    public void setIdcargo(cargo idcargo) {
        this.idcargo = idcargo;
    }
    
    
}

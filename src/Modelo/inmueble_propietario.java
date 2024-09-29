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
public class inmueble_propietario {
int codinmueble;
propietario idpropietario;

public inmueble_propietario(){
    
}
    public inmueble_propietario(int codinmueble, propietario idpropietario) {
        this.codinmueble = codinmueble;
        this.idpropietario = idpropietario;
    }

    public int getCodinmueble() {
        return codinmueble;
    }

    public void setCodinmueble(int codinmueble) {
        this.codinmueble = codinmueble;
    }

    public propietario getIdpropietario() {
        return idpropietario;
    }

    public void setIdpropietario(propietario idpropietario) {
        this.idpropietario = idpropietario;
    }
    
}

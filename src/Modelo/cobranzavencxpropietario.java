/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class cobranzavencxpropietario {
    
   
    cliente cliente;
    edificio edificio;
    inmueble inmueble;
    propietario propietario;
    cuenta_clientes cuenta_clientes;
    
  

    public cobranzavencxpropietario() {

    }

    public cobranzavencxpropietario(cliente cliente, edificio edificio, inmueble inmueble, propietario propietario, cuenta_clientes cuenta_clientes) {
        this.cliente = cliente;
        this.edificio = edificio;
        this.inmueble = inmueble;
        this.propietario = propietario;
        this.cuenta_clientes = cuenta_clientes;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(edificio edificio) {
        this.edificio = edificio;
    }

    public inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(propietario propietario) {
        this.propietario = propietario;
    }

    public cuenta_clientes getCuenta_clientes() {
        return cuenta_clientes;
    }

    public void setCuenta_clientes(cuenta_clientes cuenta_clientes) {
        this.cuenta_clientes = cuenta_clientes;
    }

   
}

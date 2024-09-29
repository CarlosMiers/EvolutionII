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
public class cobranzaxpropietario {
    
    String idpagos;
    String formatofactura;
    Date fecha;
    cliente cliente;
    edificio edificio;
    inmueble inmueble;
    propietario propietario;
    detalle_cobranza detalle_cobranza;
    
  

    public cobranzaxpropietario() {

    }

    public cobranzaxpropietario(String idpagos, String formatofactura, Date fecha, cliente cliente, edificio edificio, inmueble inmueble, propietario propietario, detalle_cobranza detalle_cobranza) {
        this.idpagos = idpagos;
        this.formatofactura = formatofactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.edificio = edificio;
        this.inmueble = inmueble;
        this.propietario = propietario;
        this.detalle_cobranza = detalle_cobranza;
    }

    public String getIdpagos() {
        return idpagos;
    }

    public void setIdpagos(String idpagos) {
        this.idpagos = idpagos;
    }

    public String getFormatofactura() {
        return formatofactura;
    }

    public void setFormatofactura(String formatofactura) {
        this.formatofactura = formatofactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public detalle_cobranza getDetalle_cobranza() {
        return detalle_cobranza;
    }

    public void setDetalle_cobranza(detalle_cobranza detalle_cobranza) {
        this.detalle_cobranza = detalle_cobranza;
    }

   
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Usuario
 */
public class transferencia {

    String idtransferencia;
    int numero;
    Date fecha;
    int origen;
    String nombreorigen;
    int destino;
    String nombredestino;
    comprobante tipo;
    int cierre;
    int codusuario;
    double idpedido;
    Time horagrabado;
    int enviado;
    int recibido;

    public transferencia() {

    }

    public transferencia(String idtransferencia, int numero, Date fecha, int origen, String nombreorigen, int destino, String nombredestino, comprobante tipo, int cierre, int codusuario, double idpedido, Time horagrabado, int enviado, int recibido) {
        this.idtransferencia = idtransferencia;
        this.numero = numero;
        this.fecha = fecha;
        this.origen = origen;
        this.nombreorigen = nombreorigen;
        this.destino = destino;
        this.nombredestino = nombredestino;
        this.tipo = tipo;
        this.cierre = cierre;
        this.codusuario = codusuario;
        this.idpedido = idpedido;
        this.horagrabado = horagrabado;
        this.enviado = enviado;
        this.recibido=recibido;
    }

    public String getIdtransferencia() {
        return idtransferencia;
    }

    public void setIdtransferencia(String idtransferencia) {
        this.idtransferencia = idtransferencia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public String getNombreorigen() {
        return nombreorigen;
    }

    public void setNombreorigen(String nombreorigen) {
        this.nombreorigen = nombreorigen;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public String getNombredestino() {
        return nombredestino;
    }

    public void setNombredestino(String nombredestino) {
        this.nombredestino = nombredestino;
    }

    public comprobante getTipo() {
        return tipo;
    }

    public void setTipo(comprobante tipo) {
        this.tipo = tipo;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public int getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(int codusuario) {
        this.codusuario = codusuario;
    }

    public double getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(double idpedido) {
        this.idpedido = idpedido;
    }

    public Time getHoragrabado() {
        return horagrabado;
    }

    public void setHoragrabado(Time horagrabado) {
        this.horagrabado = horagrabado;
    }


    public int getEnviado() {
        return enviado;
    }

    public void setEnviado(int enviado) {
        this.enviado = enviado;
    }
    

    public int getRecibido() {
        return recibido;
    }

    public void setRecibido(int recibido) {
        this.recibido= recibido;
    }
    
    
}

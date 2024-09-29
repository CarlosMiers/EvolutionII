/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Pc_Server
 */
public class divisa {

    Double numero;
    Date fecha;
    String creferencia;
    int operacion;
    int monedaorigen;
    int monedadestino;
    Double cantidad;
    Double cambio;
    Double recibe;
    Double entregado;
    Double vuelto;
    cliente cliente;
    formapago formapago;
    banco caja;
    String chequenro;
    usuario usuario;
    String nombremonedaorigen;
    String nombremonedadestino;

    public divisa() {

    }

    public divisa(Double numero, Date fecha, String creferencia, int operacion, int monedaorigen, int monedadestino, Double cantidad, Double cambio, Double recibe, Double entregado, Double vuelto, cliente cliente, formapago formapago, banco caja, String chequenro, usuario usuario, String nombremonedaorigen, String nombremonedadestino) {
        this.numero = numero;
        this.fecha = fecha;
        this.creferencia = creferencia;
        this.operacion = operacion;
        this.monedaorigen = monedaorigen;
        this.monedadestino = monedadestino;
        this.cantidad = cantidad;
        this.cambio = cambio;
        this.recibe = recibe;
        this.entregado = entregado;
        this.vuelto = vuelto;
        this.cliente = cliente;
        this.formapago = formapago;
        this.caja = caja;
        this.chequenro = chequenro;
        this.usuario = usuario;
        this.nombremonedaorigen = nombremonedaorigen;
        this.nombremonedadestino = nombremonedadestino;
    }

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public int getMonedaorigen() {
        return monedaorigen;
    }

    public void setMonedaorigen(int monedaorigen) {
        this.monedaorigen = monedaorigen;
    }

    public int getMonedadestino() {
        return monedadestino;
    }

    public void setMonedadestino(int monedadestino) {
        this.monedadestino = monedadestino;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCambio() {
        return cambio;
    }

    public void setCambio(Double cambio) {
        this.cambio = cambio;
    }

    public Double getRecibe() {
        return recibe;
    }

    public void setRecibe(Double recibe) {
        this.recibe = recibe;
    }

    public Double getEntregado() {
        return entregado;
    }

    public void setEntregado(Double entregado) {
        this.entregado = entregado;
    }

    public Double getVuelto() {
        return vuelto;
    }

    public void setVuelto(Double vuelto) {
        this.vuelto = vuelto;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public formapago getFormapago() {
        return formapago;
    }

    public void setFormapago(formapago formapago) {
        this.formapago = formapago;
    }

    public banco getCaja() {
        return caja;
    }

    public void setCaja(banco caja) {
        this.caja = caja;
    }

    public String getChequenro() {
        return chequenro;
    }

    public void setChequenro(String chequenro) {
        this.chequenro = chequenro;
    }

    public usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombremonedaorigen() {
        return nombremonedaorigen;
    }

    public void setNombremonedaorigen(String nombremonedaorigen) {
        this.nombremonedaorigen = nombremonedaorigen;
    }

    public String getNombremonedadestino() {
        return nombremonedadestino;
    }

    public void setNombremonedadestino(String nombremonedadestino) {
        this.nombremonedadestino = nombremonedadestino;
    }

}

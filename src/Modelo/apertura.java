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
public class apertura {

    double idcontrol;
    usuario usuario;
    caja caja;
    Date fecha;
    int turno;
    double monto;
    String nombre;
    moneda moneda;
    double cotizacion;
    double importe;
    banco banco;
    
    
    public apertura(){
        
    }

    public apertura(double idcontrol, usuario usuario, caja caja, Date fecha, int turno, double monto, String nombre, moneda moneda, double cotizacion, double importe, banco banco) {
        this.idcontrol = idcontrol;
        this.usuario = usuario;
        this.caja = caja;
        this.fecha = fecha;
        this.turno = turno;
        this.monto = monto;
        this.nombre = nombre;
        this.moneda = moneda;
        this.cotizacion = cotizacion;
        this.importe = importe;
        this.banco = banco;
    }

    public double getIdcontrol() {
        return idcontrol;
    }

    public void setIdcontrol(double idcontrol) {
        this.idcontrol = idcontrol;
    }

    public usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(usuario usuario) {
        this.usuario = usuario;
    }

    public caja getCaja() {
        return caja;
    }

    public void setCaja(caja caja) {
        this.caja = caja;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public banco getBanco() {
        return banco;
    }

    public void setBanco(banco banco) {
        this.banco = banco;
    }



}

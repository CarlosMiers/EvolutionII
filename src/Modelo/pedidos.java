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
public class pedidos {
    double idpedido;
    Date fecha;
    sucursal sucursal;
    double totales;
    int cierre;
    int nproducto ; 
    
    public pedidos(){
        
    }

    public pedidos(double idpedido, Date fecha, sucursal sucursal, double totales, int cierre, int nproducto) {
        this.idpedido = idpedido;
        this.fecha = fecha;
        this.sucursal = sucursal;
        this.totales = totales;
        this.cierre = cierre;
        this.nproducto = nproducto;
    }

    public double getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(double idpedido) {
        this.idpedido = idpedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public double getTotales() {
        return totales;
    }

    public void setTotales(double totales) {
        this.totales = totales;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public int getNproducto() {
        return nproducto;
    }

    public void setNproducto(int nproducto) {
        this.nproducto = nproducto;
    }

    
}

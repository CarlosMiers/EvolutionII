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
public class saldos_inmobiliaria {
    
    String idreferencia;
    Date fecha;
    double numero;
    cliente inquilino;
    double total;
    
    public saldos_inmobiliaria(){
        
    }

    public saldos_inmobiliaria(String idreferencia, Date fecha, double numero, cliente inquilino, double total) {
        this.idreferencia = idreferencia;
        this.fecha = fecha;
        this.numero = numero;
        this.inquilino = inquilino;
        this.total = total;
    }

    public String getIdreferencia() {
        return idreferencia;
    }

    public void setIdreferencia(String idreferencia) {
        this.idreferencia = idreferencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public cliente getInquilino() {
        return inquilino;
    }

    public void setInquilino(cliente inquilino) {
        this.inquilino = inquilino;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}

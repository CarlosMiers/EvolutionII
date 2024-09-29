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
public class Resumen_Saldos_alafecha {
  
    int cliente;
    String nombre;
    String ruc;
    double importe;
    double pagos;
    double saldo;
   
    public void Resumen_Saldos_alafecha(){
        
    }

    public Resumen_Saldos_alafecha(int cliente, String nombre, String ruc, double importe, double pagos, double saldo) {
        this.cliente = cliente;
        this.nombre = nombre;
        this.ruc = ruc;
        this.importe = importe;
        this.pagos = pagos;
        this.saldo = saldo;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getPagos() {
        return pagos;
    }

    public void setPagos(double pagos) {
        this.pagos = pagos;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


    
}

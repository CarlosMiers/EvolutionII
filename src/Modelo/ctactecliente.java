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
public class ctactecliente {

    int iditem;
    String nrocuenta;
    cliente cliente;
    moneda moneda;
    bancoplaza bancos;
    
    public ctactecliente(){
        
    }

    public ctactecliente(int iditem, String nrocuenta, cliente cliente, moneda moneda, bancoplaza bancos) {
        this.iditem = iditem;
        this.nrocuenta = nrocuenta;
        this.cliente = cliente;
        this.moneda = moneda;
        this.bancos = bancos;
    }

    public int getIditem() {
        return iditem;
    }

    public void setIditem(int iditem) {
        this.iditem = iditem;
    }

    public String getNrocuenta() {
        return nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        this.nrocuenta = nrocuenta;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public bancoplaza getBancos() {
        return bancos;
    }

    public void setBancos(bancoplaza bancos) {
        this.bancos = bancos;
    }
    
}

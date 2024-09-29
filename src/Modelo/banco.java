/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author SERVIDOR
 */
public class banco {
    
    int codigo;
    String nombre;
    String nrocuenta;
    String contacto;
    String direccion;
    String telefono;
    String mail;
    plan idcuenta;
    int tipo;
    int estado;
    public banco(){
        
    }

    public banco(int codigo, String nombre, String nrocuenta, String contacto, String direccion, String telefono, String mail, plan idcuenta, int tipo, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nrocuenta = nrocuenta;
        this.contacto = contacto;
        this.direccion = direccion;
        this.telefono = telefono;
        this.mail = mail;
        this.idcuenta = idcuenta;
        this.tipo = tipo;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNrocuenta() {
        return nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        this.nrocuenta = nrocuenta;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public plan getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(plan idcuenta) {
        this.idcuenta = idcuenta;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
}

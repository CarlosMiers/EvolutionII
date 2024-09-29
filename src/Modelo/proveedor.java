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
public class proveedor {

    int codigo;
    String nombre;
    localidad localidad;
    String direccion;
    String ruc;
    String telefono;
    String fax;
    String email;
    String web;
    String timbrado;
    Date vencimiento;
    int estado;
    plan idcta;
    String contacto;
    int plazo;

    public proveedor() {

    }

    public proveedor(int codigo, String nombre, localidad localidad, String direccion, String ruc, String telefono, String fax, String email, String web, String timbrado, Date vencimiento, int estado, plan idcta, String contacto, int plazo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.localidad = localidad;
        this.direccion = direccion;
        this.ruc = ruc;
        this.telefono = telefono;
        this.fax = fax;
        this.email = email;
        this.web = web;
        this.timbrado = timbrado;
        this.vencimiento = vencimiento;
        this.estado = estado;
        this.idcta = idcta;
        this.contacto = contacto;
        this.plazo = plazo;
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

    public localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(localidad localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(String timbrado) {
        this.timbrado = timbrado;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public plan getIdcta() {
        return idcta;
    }

    public void setIdcta(plan idcta) {
        this.idcta = idcta;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

   
}

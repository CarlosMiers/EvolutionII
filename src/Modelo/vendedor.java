/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author SERVIDOR
 */
public class vendedor {
 
    
int codigo;
String nombre;
Date nacimiento;
String cedula;
String estadocivil;
String conyugue;
String direccion;
String telefono;
String celular;
String mail;
Double comisioncontado;
Double comisioncredito;
String estado;
    
    
    public vendedor(){
        
    }

    public vendedor(int codigo, String nombre, Date nacimiento, String cedula, String estadocivil, String conyugue, String direccion, String telefono, String celular, String mail, Double comisioncontado, Double comisioncredito, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.cedula = cedula;
        this.estadocivil = estadocivil;
        this.conyugue = conyugue;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.mail = mail;
        this.comisioncontado = comisioncontado;
        this.comisioncredito = comisioncredito;
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

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getConyugue() {
        return conyugue;
    }

    public void setConyugue(String conyugue) {
        this.conyugue = conyugue;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Double getComisioncontado() {
        return comisioncontado;
    }

    public void setComisioncontado(Double comisioncontado) {
        this.comisioncontado = comisioncontado;
    }

    public Double getComisioncredito() {
        return comisioncredito;
    }

    public void setComisioncredito(Double comisioncredito) {
        this.comisioncredito = comisioncredito;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

   
}

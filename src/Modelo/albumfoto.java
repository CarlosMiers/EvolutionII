/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Image;

/**
 *
 * @author SERVIDOR
 */
public class albumfoto {
cliente codigo;
Image foto;
String nombre;

    public albumfoto() {

    }

    public albumfoto(cliente codigo, Image foto, String nombre) {
        this.codigo = codigo;
        this.foto = foto;
        this.nombre = nombre;
    }

    public cliente getCodigo() {
        return codigo;
    }

    public void setCodigo(cliente codigo) {
        this.codigo = codigo;
    }

    public Image getFoto() {
        return foto;
    }

    public void setFoto(Image foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}

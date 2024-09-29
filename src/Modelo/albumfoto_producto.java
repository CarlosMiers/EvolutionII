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
public class albumfoto_producto {

    producto codigo;
    Image foto;
    String nombre;
    byte[] imagen;

    public albumfoto_producto() {

    }

    public albumfoto_producto(producto codigo, Image foto, String nombre, byte[] imagen) {
        this.codigo = codigo;
        this.foto = foto;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public producto getCodigo() {
        return codigo;
    }

    public void setCodigo(producto codigo) {
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

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

}

package Modelo;

import java.awt.Image;

/**
 *
 * @author SERVIDOR
 */
public class albumfoto_inmueble {

    inmueble codigo;
    Image foto;
    String nombre;

    public albumfoto_inmueble(){
        
    }

    public albumfoto_inmueble(inmueble codigo, Image foto, String nombre) {
        this.codigo = codigo;
        this.foto = foto;
        this.nombre = nombre;
    }

    public inmueble getCodigo() {
        return codigo;
    }

    public void setCodigo(inmueble codigo) {
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


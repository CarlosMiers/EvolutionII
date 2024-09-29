package Modelo;

import java.awt.Image;

/**
 *
 * @author SERVIDOR
 */
public class albumfoto_propietario {

    int codigo;
    Image foto;
    String nombre;

    public albumfoto_propietario(){
        
    }

    public albumfoto_propietario(int codigo, Image foto, String nombre) {
        this.codigo = codigo;
        this.foto = foto;
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
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


package Modelo;

import java.awt.Image;

/**
 *
 * @author SERVIDOR
 */
public class albumfoto_solicitud_locacion {

    solicitud_locacion nrosolicitud;
    Image foto;
    String nombre;

    public albumfoto_solicitud_locacion() {

    }

    public albumfoto_solicitud_locacion(solicitud_locacion nrosolicitud, Image foto, String nombre) {
        this.nrosolicitud = nrosolicitud;
        this.foto = foto;
        this.nombre = nombre;
    }

    public solicitud_locacion getNrosolicitud() {
        return nrosolicitud;
    }

    public void setNrosolicitud(solicitud_locacion nrosolicitud) {
        this.nrosolicitud = nrosolicitud;
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

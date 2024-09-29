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
public class referencia_laboral {

    private cliente idcliente;
    String descripcion;
    String telefono;
    int item;
    public referencia_laboral() {

    }
   public referencia_laboral(cliente idcliente, String descripcion, String telefono, int item) {
        this.idcliente = idcliente;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.item = item;
    }

    public cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(cliente idcliente) {
        this.idcliente = idcliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

 
}

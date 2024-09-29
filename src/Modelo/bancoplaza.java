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
public class bancoplaza {
 
    int codigo;
    String nombre;
    String reporte;
    
    public bancoplaza(){
        
    }

    public bancoplaza(int codigo, String nombre, String reporte) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.reporte= reporte;
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
    

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author notebook
 */
public class emisor {

    int codigo;
    String nombre;
    String cedula;
    pais pais;
    String direccion;
    String responsable;
    String telefono;
    String fax;
    String email;
    String nomalias;
    rubro_emisor rubro;
    int tiposociedad;
    int tipoentidad;
    int clase;
    String situacion;
    calificadora calificadora;
    String calificacion;
    int estado;

    public emisor() {

    }

    public emisor(int codigo, String nombre, String cedula, pais pais, String direccion, String responsable, String telefono, String fax, String email, String nomalias, rubro_emisor rubro, int tiposociedad, int tipoentidad, int clase, String situacion, calificadora calificadora, String calificacion, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cedula = cedula;
        this.pais = pais;
        this.direccion = direccion;
        this.responsable = responsable;
        this.telefono = telefono;
        this.fax = fax;
        this.email = email;
        this.nomalias = nomalias;
        this.rubro = rubro;
        this.tiposociedad = tiposociedad;
        this.tipoentidad = tipoentidad;
        this.clase = clase;
        this.situacion = situacion;
        this.calificadora = calificadora;
        this.calificacion = calificacion;
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public pais getPais() {
        return pais;
    }

    public void setPais(pais pais) {
        this.pais = pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
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

    public String getNomalias() {
        return nomalias;
    }

    public void setNomalias(String nomalias) {
        this.nomalias = nomalias;
    }

    public rubro_emisor getRubro() {
        return rubro;
    }

    public void setRubro(rubro_emisor rubro) {
        this.rubro = rubro;
    }

    public int getTiposociedad() {
        return tiposociedad;
    }

    public void setTiposociedad(int tiposociedad) {
        this.tiposociedad = tiposociedad;
    }

    public int getTipoentidad() {
        return tipoentidad;
    }

    public void setTipoentidad(int tipoentidad) {
        this.tipoentidad = tipoentidad;
    }

    public int getClase() {
        return clase;
    }

    public void setClase(int clase) {
        this.clase = clase;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public calificadora getCalificadora() {
        return calificadora;
    }

    public void setCalificadora(calificadora calificadora) {
        this.calificadora = calificadora;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }



}

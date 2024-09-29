package Modelo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class solicitud_locacion_codeudor {

    int idcontrato;
    int idcliente;
    cliente cliente;
    localidad localidad;
    barrio barrio;
    solicitud_locacion solicitud_locacion;
    String nombre;
    String ruc;
    Date fechanacimiento;
    String estadocivil;
    String direccionparticular;
    String telefonofijo;
    String telefonomovil;
    String email;
    String direccion;
    String cargo;
    String lugartrabajo;
    String ingreso;

    public solicitud_locacion_codeudor() {

    }

    public solicitud_locacion_codeudor(int idcontrato, int idcliente, cliente cliente, localidad localidad, barrio barrio, solicitud_locacion solicitud_locacion, String nombre, String ruc, Date fechanacimiento, String estadocivil, String direccionparticular, String telefonofijo, String telefonomovil, String email, String direccion, String cargo, String lugartrabajo, String ingreso) {
        this.idcontrato = idcontrato;
        this.idcliente = idcliente;
        this.cliente = cliente;
        this.localidad = localidad;
        this.barrio = barrio;
        this.solicitud_locacion = solicitud_locacion;
        this.nombre = nombre;
        this.ruc = ruc;
        this.fechanacimiento = fechanacimiento;
        this.estadocivil = estadocivil;
        this.direccionparticular = direccionparticular;
        this.telefonofijo = telefonofijo;
        this.telefonomovil = telefonomovil;
        this.email = email;
        this.direccion = direccion;
        this.cargo = cargo;
        this.lugartrabajo = lugartrabajo;
        this.ingreso = ingreso;
    }

    public int getIdcontrato() {
        return idcontrato;
    }

    public void setIdcontrato(int idcontrato) {
        this.idcontrato = idcontrato;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(localidad localidad) {
        this.localidad = localidad;
    }

    public barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(barrio barrio) {
        this.barrio = barrio;
    }

    public solicitud_locacion getSolicitud_locacion() {
        return solicitud_locacion;
    }

    public void setSolicitud_locacion(solicitud_locacion solicitud_locacion) {
        this.solicitud_locacion = solicitud_locacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getDireccionparticular() {
        return direccionparticular;
    }

    public void setDireccionparticular(String direccionparticular) {
        this.direccionparticular = direccionparticular;
    }

    public String getTelefonofijo() {
        return telefonofijo;
    }

    public void setTelefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
    }

    public String getTelefonomovil() {
        return telefonomovil;
    }

    public void setTelefonomovil(String telefonomovil) {
        this.telefonomovil = telefonomovil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getLugartrabajo() {
        return lugartrabajo;
    }

    public void setLugartrabajo(String lugartrabajo) {
        this.lugartrabajo = lugartrabajo;
    }

    public String getIngreso() {
        return ingreso;
    }

    public void setIngreso(String ingreso) {
        this.ingreso = ingreso;
    }

   
}

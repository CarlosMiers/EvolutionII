package Modelo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class solicitud_locacion {

    int idsolicitud;
    Date fechasolicitud;
    cliente idcliente;
    edificio idunidad;
    inmueble inmueble;
    localidad localidad;
    barrio barrio;
    String nrofinca;
    String ctactral;

    String nombres;
    String apellidos;
    String nrodocumento;
    String ruc;
    Date nacimiento;
    int nacionalidad;
    String estadocivil;
    int nrohabitantes;
    String direccionparticular;
    String telefonofijo;
    String telefonomovil;
    String email;
    String direccionlaboral;
    String telefonolaboral;
    String lugardetrabajo;
    String cargo;
    String ingresos;
    ////////datos conyugue///////
    int idconyugue;
    String nombreconyugue;
    String direccionparticularconyugue;
    String nrodocumentoconyugue;
    Date nacimientoconyugue;
    String lugartrabajoconyugue;
    String telefonoconyugue;
    String telefonomovilconyugue;
    String cargoconyugue;
    String ingresoconyugue;

    String estado;
    int usuarioalta;
    int usuarioproceso;
    Date fechaproceso;
    String observaciones;
    String nombreinmueble;
    int ncontroldetalle;
    int ncontroljuridica;
    int ncontrolpropietario;
 
    Double depositogarantia;
    Double importealquiler;
    int diapago;
    Date iniciocontrato;
    Date vencimientocontrato;
    Date fechaocupacion;
    
    public solicitud_locacion() {

    }

    public solicitud_locacion(int idsolicitud, Date fechasolicitud, cliente idcliente, edificio idunidad, inmueble inmueble, localidad localidad, barrio barrio, String nrofinca, String ctactral, String nombres, String apellidos, String nrodocumento, String ruc, Date nacimiento, int nacionalidad, String estadocivil, int nrohabitantes, String direccionparticular, String telefonofijo, String telefonomovil, String email, String direccionlaboral, String telefonolaboral, String lugardetrabajo, String cargo, String ingresos, int idconyugue, String nombreconyugue, String direccionparticularconyugue, String nrodocumentoconyugue, Date nacimientoconyugue, String lugartrabajoconyugue, String telefonoconyugue, String telefonomovilconyugue, String cargoconyugue, String ingresoconyugue, String estado, int usuarioalta, int usuarioproceso, Date fechaproceso, String observaciones, String nombreinmueble, int ncontroldetalle, int ncontroljuridica, int ncontrolpropietario, Double depositogarantia, Double importealquiler, int diapago, Date iniciocontrato, Date vencimientocontrato, Date fechaocupacion) {
        this.idsolicitud = idsolicitud;
        this.fechasolicitud = fechasolicitud;
        this.idcliente = idcliente;
        this.idunidad = idunidad;
        this.inmueble = inmueble;
        this.localidad = localidad;
        this.barrio = barrio;
        this.nrofinca = nrofinca;
        this.ctactral = ctactral;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nrodocumento = nrodocumento;
        this.ruc = ruc;
        this.nacimiento = nacimiento;
        this.nacionalidad = nacionalidad;
        this.estadocivil = estadocivil;
        this.nrohabitantes = nrohabitantes;
        this.direccionparticular = direccionparticular;
        this.telefonofijo = telefonofijo;
        this.telefonomovil = telefonomovil;
        this.email = email;
        this.direccionlaboral = direccionlaboral;
        this.telefonolaboral = telefonolaboral;
        this.lugardetrabajo = lugardetrabajo;
        this.cargo = cargo;
        this.ingresos = ingresos;
        this.idconyugue = idconyugue;
        this.nombreconyugue = nombreconyugue;
        this.direccionparticularconyugue = direccionparticularconyugue;
        this.nrodocumentoconyugue = nrodocumentoconyugue;
        this.nacimientoconyugue = nacimientoconyugue;
        this.lugartrabajoconyugue = lugartrabajoconyugue;
        this.telefonoconyugue = telefonoconyugue;
        this.telefonomovilconyugue = telefonomovilconyugue;
        this.cargoconyugue = cargoconyugue;
        this.ingresoconyugue = ingresoconyugue;
        this.estado = estado;
        this.usuarioalta = usuarioalta;
        this.usuarioproceso = usuarioproceso;
        this.fechaproceso = fechaproceso;
        this.observaciones = observaciones;
        this.nombreinmueble = nombreinmueble;
        this.ncontroldetalle = ncontroldetalle;
        this.ncontroljuridica = ncontroljuridica;
        this.ncontrolpropietario = ncontrolpropietario;
        this.depositogarantia = depositogarantia;
        this.importealquiler = importealquiler;
        this.diapago = diapago;
        this.iniciocontrato = iniciocontrato;
        this.vencimientocontrato = vencimientocontrato;
        this.fechaocupacion = fechaocupacion;
    }

    public int getIdsolicitud() {
        return idsolicitud;
    }

    public void setIdsolicitud(int idsolicitud) {
        this.idsolicitud = idsolicitud;
    }

    public Date getFechasolicitud() {
        return fechasolicitud;
    }

    public void setFechasolicitud(Date fechasolicitud) {
        this.fechasolicitud = fechasolicitud;
    }

    public cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(cliente idcliente) {
        this.idcliente = idcliente;
    }

    public edificio getIdunidad() {
        return idunidad;
    }

    public void setIdunidad(edificio idunidad) {
        this.idunidad = idunidad;
    }

    public inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(inmueble inmueble) {
        this.inmueble = inmueble;
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

    public String getNrofinca() {
        return nrofinca;
    }

    public void setNrofinca(String nrofinca) {
        this.nrofinca = nrofinca;
    }

    public String getCtactral() {
        return ctactral;
    }

    public void setCtactral(String ctactral) {
        this.ctactral = ctactral;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public int getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(int nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public int getNrohabitantes() {
        return nrohabitantes;
    }

    public void setNrohabitantes(int nrohabitantes) {
        this.nrohabitantes = nrohabitantes;
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

    public String getDireccionlaboral() {
        return direccionlaboral;
    }

    public void setDireccionlaboral(String direccionlaboral) {
        this.direccionlaboral = direccionlaboral;
    }

    public String getTelefonolaboral() {
        return telefonolaboral;
    }

    public void setTelefonolaboral(String telefonolaboral) {
        this.telefonolaboral = telefonolaboral;
    }

    public String getLugardetrabajo() {
        return lugardetrabajo;
    }

    public void setLugardetrabajo(String lugardetrabajo) {
        this.lugardetrabajo = lugardetrabajo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getIngresos() {
        return ingresos;
    }

    public void setIngresos(String ingresos) {
        this.ingresos = ingresos;
    }

    public int getIdconyugue() {
        return idconyugue;
    }

    public void setIdconyugue(int idconyugue) {
        this.idconyugue = idconyugue;
    }

    public String getNombreconyugue() {
        return nombreconyugue;
    }

    public void setNombreconyugue(String nombreconyugue) {
        this.nombreconyugue = nombreconyugue;
    }

    public String getDireccionparticularconyugue() {
        return direccionparticularconyugue;
    }

    public void setDireccionparticularconyugue(String direccionparticularconyugue) {
        this.direccionparticularconyugue = direccionparticularconyugue;
    }

    public String getNrodocumentoconyugue() {
        return nrodocumentoconyugue;
    }

    public void setNrodocumentoconyugue(String nrodocumentoconyugue) {
        this.nrodocumentoconyugue = nrodocumentoconyugue;
    }

    public Date getNacimientoconyugue() {
        return nacimientoconyugue;
    }

    public void setNacimientoconyugue(Date nacimientoconyugue) {
        this.nacimientoconyugue = nacimientoconyugue;
    }

    public String getLugartrabajoconyugue() {
        return lugartrabajoconyugue;
    }

    public void setLugartrabajoconyugue(String lugartrabajoconyugue) {
        this.lugartrabajoconyugue = lugartrabajoconyugue;
    }

    public String getTelefonoconyugue() {
        return telefonoconyugue;
    }

    public void setTelefonoconyugue(String telefonoconyugue) {
        this.telefonoconyugue = telefonoconyugue;
    }

    public String getTelefonomovilconyugue() {
        return telefonomovilconyugue;
    }

    public void setTelefonomovilconyugue(String telefonomovilconyugue) {
        this.telefonomovilconyugue = telefonomovilconyugue;
    }

    public String getCargoconyugue() {
        return cargoconyugue;
    }

    public void setCargoconyugue(String cargoconyugue) {
        this.cargoconyugue = cargoconyugue;
    }

    public String getIngresoconyugue() {
        return ingresoconyugue;
    }

    public void setIngresoconyugue(String ingresoconyugue) {
        this.ingresoconyugue = ingresoconyugue;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getUsuarioalta() {
        return usuarioalta;
    }

    public void setUsuarioalta(int usuarioalta) {
        this.usuarioalta = usuarioalta;
    }

    public int getUsuarioproceso() {
        return usuarioproceso;
    }

    public void setUsuarioproceso(int usuarioproceso) {
        this.usuarioproceso = usuarioproceso;
    }

    public Date getFechaproceso() {
        return fechaproceso;
    }

    public void setFechaproceso(Date fechaproceso) {
        this.fechaproceso = fechaproceso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNombreinmueble() {
        return nombreinmueble;
    }

    public void setNombreinmueble(String nombreinmueble) {
        this.nombreinmueble = nombreinmueble;
    }

    public int getNcontroldetalle() {
        return ncontroldetalle;
    }

    public void setNcontroldetalle(int ncontroldetalle) {
        this.ncontroldetalle = ncontroldetalle;
    }

    public int getNcontroljuridica() {
        return ncontroljuridica;
    }

    public void setNcontroljuridica(int ncontroljuridica) {
        this.ncontroljuridica = ncontroljuridica;
    }

    public int getNcontrolpropietario() {
        return ncontrolpropietario;
    }

    public void setNcontrolpropietario(int ncontrolpropietario) {
        this.ncontrolpropietario = ncontrolpropietario;
    }

    public Double getDepositogarantia() {
        return depositogarantia;
    }

    public void setDepositogarantia(Double depositogarantia) {
        this.depositogarantia = depositogarantia;
    }

    public Double getImportealquiler() {
        return importealquiler;
    }

    public void setImportealquiler(Double importealquiler) {
        this.importealquiler = importealquiler;
    }

    public int getDiapago() {
        return diapago;
    }

    public void setDiapago(int diapago) {
        this.diapago = diapago;
    }

    public Date getIniciocontrato() {
        return iniciocontrato;
    }

    public void setIniciocontrato(Date iniciocontrato) {
        this.iniciocontrato = iniciocontrato;
    }

    public Date getVencimientocontrato() {
        return vencimientocontrato;
    }

    public void setVencimientocontrato(Date vencimientocontrato) {
        this.vencimientocontrato = vencimientocontrato;
    }

    public Date getFechaocupacion() {
        return fechaocupacion;
    }

    public void setFechaocupacion(Date fechaocupacion) {
        this.fechaocupacion = fechaocupacion;
    }


  
}

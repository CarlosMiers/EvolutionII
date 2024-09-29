/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Pc_Server
 */
public class ficha_empleado {

    int codigo;
    String nombres;
    String apellidos;
    String nombreempleado;
    int sexo;
    int cedula;
    Date fechanacimiento;
    pais nacionalidad;
    String estado_civil;
    String conyugue;
    localidad localidad;
    String direccion;
    String telefono;
    String celular;
    String telefono_urgencia;
    String e_mail;
    int estado;
    sucursal sucursal;
    profesion profesion;
    cargo cargo;
    departamento_laboral departamento;
    seccion seccion;
    giraduria giraduria;
    Date fecha_ingreso;
    BigDecimal salario;
    BigDecimal adicionalxformacion;
    int sistema_cobro;
    int tipo_salario;
    int ips;
    int bonificacion;
    int academia;
    int espanol;
    int guarani;
    int portugues;
    int aleman;
    int otros;
    String nroseguroips;
    double estatura;
    double peso;
    String grupo_sanguineo;
    String bebe;
    String fuma;
    int anteojos;
    double pantalon;
    String casaca;
    double zapatos;
    String capacidad_auditiva;
    String medicamentos_contraindicados;
    String accidentes;
    String enfermedades;
    String operaciones;
    byte[] foto;
    int nrohijos;
    int postulante;
    String objetivos_laborales;
    String experiencia_laboral;
    String preparacion_academica;

    public ficha_empleado() {

    }

    public ficha_empleado(int codigo, String nombres, String apellidos, String nombreempleado, int sexo, int cedula, Date fechanacimiento, pais nacionalidad, String estado_civil, String conyugue, localidad localidad, String direccion, String telefono, String celular, String telefono_urgencia, String e_mail, int estado, sucursal sucursal, profesion profesion, cargo cargo, departamento_laboral departamento, seccion seccion, giraduria giraduria, Date fecha_ingreso, BigDecimal salario, BigDecimal adicionalxformacion, int sistema_cobro, int tipo_salario, int ips, int bonificacion, int academia, int espanol, int guarani, int portugues, int aleman, int otros, String nroseguroips, double estatura, double peso, String grupo_sanguineo, String bebe, String fuma, int anteojos, double pantalon, String casaca, double zapatos, String capacidad_auditiva, String medicamentos_contraindicados, String accidentes, String enfermedades, String operaciones, byte[] foto, int nrohijos, int postulante, String objetivos_laborales, String experiencia_laboral, String preparacion_academica) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombreempleado = nombreempleado;
        this.sexo = sexo;
        this.cedula = cedula;
        this.fechanacimiento = fechanacimiento;
        this.nacionalidad = nacionalidad;
        this.estado_civil = estado_civil;
        this.conyugue = conyugue;
        this.localidad = localidad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.telefono_urgencia = telefono_urgencia;
        this.e_mail = e_mail;
        this.estado = estado;
        this.sucursal = sucursal;
        this.profesion = profesion;
        this.cargo = cargo;
        this.departamento = departamento;
        this.seccion = seccion;
        this.giraduria = giraduria;
        this.fecha_ingreso = fecha_ingreso;
        this.salario = salario;
        this.adicionalxformacion = adicionalxformacion;
        this.sistema_cobro = sistema_cobro;
        this.tipo_salario = tipo_salario;
        this.ips = ips;
        this.bonificacion = bonificacion;
        this.academia = academia;
        this.espanol = espanol;
        this.guarani = guarani;
        this.portugues = portugues;
        this.aleman = aleman;
        this.otros = otros;
        this.nroseguroips = nroseguroips;
        this.estatura = estatura;
        this.peso = peso;
        this.grupo_sanguineo = grupo_sanguineo;
        this.bebe = bebe;
        this.fuma = fuma;
        this.anteojos = anteojos;
        this.pantalon = pantalon;
        this.casaca = casaca;
        this.zapatos = zapatos;
        this.capacidad_auditiva = capacidad_auditiva;
        this.medicamentos_contraindicados = medicamentos_contraindicados;
        this.accidentes = accidentes;
        this.enfermedades = enfermedades;
        this.operaciones = operaciones;
        this.foto = foto;
        this.nrohijos = nrohijos;
        this.postulante = postulante;
        this.objetivos_laborales = objetivos_laborales;
        this.experiencia_laboral = experiencia_laboral;
        this.preparacion_academica = preparacion_academica;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public String getNombreempleado() {
        return nombreempleado;
    }

    public void setNombreempleado(String nombreempleado) {
        this.nombreempleado = nombreempleado;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public pais getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(pais nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEstado_civil() {
        return estado_civil;
    }

    public void setEstado_civil(String estado_civil) {
        this.estado_civil = estado_civil;
    }

    public String getConyugue() {
        return conyugue;
    }

    public void setConyugue(String conyugue) {
        this.conyugue = conyugue;
    }

    public localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(localidad localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono_urgencia() {
        return telefono_urgencia;
    }

    public void setTelefono_urgencia(String telefono_urgencia) {
        this.telefono_urgencia = telefono_urgencia;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public profesion getProfesion() {
        return profesion;
    }

    public void setProfesion(profesion profesion) {
        this.profesion = profesion;
    }

    public cargo getCargo() {
        return cargo;
    }

    public void setCargo(cargo cargo) {
        this.cargo = cargo;
    }

    public departamento_laboral getDepartamento() {
        return departamento;
    }

    public void setDepartamento(departamento_laboral departamento) {
        this.departamento = departamento;
    }

    public seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(seccion seccion) {
        this.seccion = seccion;
    }

    public giraduria getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(giraduria giraduria) {
        this.giraduria = giraduria;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public BigDecimal getAdicionalxformacion() {
        return adicionalxformacion;
    }

    public void setAdicionalxformacion(BigDecimal adicionalxformacion) {
        this.adicionalxformacion = adicionalxformacion;
    }

    public int getSistema_cobro() {
        return sistema_cobro;
    }

    public void setSistema_cobro(int sistema_cobro) {
        this.sistema_cobro = sistema_cobro;
    }

    public int getTipo_salario() {
        return tipo_salario;
    }

    public void setTipo_salario(int tipo_salario) {
        this.tipo_salario = tipo_salario;
    }

    public int getIps() {
        return ips;
    }

    public void setIps(int ips) {
        this.ips = ips;
    }

    public int getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(int bonificacion) {
        this.bonificacion = bonificacion;
    }

    public int getAcademia() {
        return academia;
    }

    public void setAcademia(int academia) {
        this.academia = academia;
    }

    public int getEspanol() {
        return espanol;
    }

    public void setEspanol(int espanol) {
        this.espanol = espanol;
    }

    public int getGuarani() {
        return guarani;
    }

    public void setGuarani(int guarani) {
        this.guarani = guarani;
    }

    public int getPortugues() {
        return portugues;
    }

    public void setPortugues(int portugues) {
        this.portugues = portugues;
    }

    public int getAleman() {
        return aleman;
    }

    public void setAleman(int aleman) {
        this.aleman = aleman;
    }

    public int getOtros() {
        return otros;
    }

    public void setOtros(int otros) {
        this.otros = otros;
    }

    public String getNroseguroips() {
        return nroseguroips;
    }

    public void setNroseguroips(String nroseguroips) {
        this.nroseguroips = nroseguroips;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getGrupo_sanguineo() {
        return grupo_sanguineo;
    }

    public void setGrupo_sanguineo(String grupo_sanguineo) {
        this.grupo_sanguineo = grupo_sanguineo;
    }

    public String getBebe() {
        return bebe;
    }

    public void setBebe(String bebe) {
        this.bebe = bebe;
    }

    public String getFuma() {
        return fuma;
    }

    public void setFuma(String fuma) {
        this.fuma = fuma;
    }

    public int getAnteojos() {
        return anteojos;
    }

    public void setAnteojos(int anteojos) {
        this.anteojos = anteojos;
    }

    public double getPantalon() {
        return pantalon;
    }

    public void setPantalon(double pantalon) {
        this.pantalon = pantalon;
    }

    public String getCasaca() {
        return casaca;
    }

    public void setCasaca(String casaca) {
        this.casaca = casaca;
    }

    public double getZapatos() {
        return zapatos;
    }

    public void setZapatos(double zapatos) {
        this.zapatos = zapatos;
    }

    public String getCapacidad_auditiva() {
        return capacidad_auditiva;
    }

    public void setCapacidad_auditiva(String capacidad_auditiva) {
        this.capacidad_auditiva = capacidad_auditiva;
    }

    public String getMedicamentos_contraindicados() {
        return medicamentos_contraindicados;
    }

    public void setMedicamentos_contraindicados(String medicamentos_contraindicados) {
        this.medicamentos_contraindicados = medicamentos_contraindicados;
    }

    public String getAccidentes() {
        return accidentes;
    }

    public void setAccidentes(String accidentes) {
        this.accidentes = accidentes;
    }

    public String getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(String enfermedades) {
        this.enfermedades = enfermedades;
    }

    public String getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(String operaciones) {
        this.operaciones = operaciones;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getNrohijos() {
        return nrohijos;
    }

    public void setNrohijos(int nrohijos) {
        this.nrohijos = nrohijos;
    }

    public int getPostulante() {
        return postulante;
    }

    public void setPostulante(int postulante) {
        this.postulante = postulante;
    }

    public String getObjetivos_laborales() {
        return objetivos_laborales;
    }

    public void setObjetivos_laborales(String objetivos_laborales) {
        this.objetivos_laborales = objetivos_laborales;
    }

    public String getExperiencia_laboral() {
        return experiencia_laboral;
    }

    public void setExperiencia_laboral(String experiencia_laboral) {
        this.experiencia_laboral = experiencia_laboral;
    }

    public String getPreparacion_academica() {
        return preparacion_academica;
    }

    public void setPreparacion_academica(String preparacion_academica) {
        this.preparacion_academica = preparacion_academica;
    }


}

package Modelo;

import java.sql.Date;

public class cliente {

    int codigo;
    int comitente;
    String nombre;
    String ruc;
    String cedula;
    Date fechanacimiento;
    String estadocivil;
    String conyugue;
    String direccion;
    String telefono;
    String fax;
    String celular;
    String mail;
    String profesion;
    String lugartrabajo;
    String cargolaboral;
    Double salario;
    giraduria giraduria;
    int legajo;
    String turno;
    int descuentos_aportes;
    String direccionlaboral;
    Date fechaingreso;
    Date fechaingresofuncionario;
    String telefonolaboral;
    String faxlaboral;
    String maillaboral;
    int vendedor;
    int categoria;
    int plazocredito;
    Double salarioconyugue;
    Double otrosingresos;
    localidad localidad;
    Double limitecredito;
    Double credito;
    String notas;
    int estado;
    Double saldo;
    String cedulaconyugue;
    String garante;
    String conyuguegarante;
    String cedulagarante;
    String cedulaconyuguegarante;
    String direcciongarante;
    String telefonogarante;
    String lugartrabajogarante;
    String profesiongarante;
    String teloficinagarante;
    plan idcta;
    int asesor;
    int banco;
    String cuenta;
    int casapropia;
    int autopropio;
    int informconf;
    int sexo;
    abogado abogado;
    barrio barrio;
    Double saldoanterior;
    Double importedebe;
    Double importehaber;
    carrera carrera;
    int semestre;
    Date fechaculminacion;
    String nroregistro;
    int res90;
    int codfais;
    int programa;
    String pep;
    String apoderado;
    String directorio;
    String objeto;
    String facturar_a_nombre;
    String direccionfactura;
    String telefonofactura;
    String rucfactura;
    int imprimirtitular;
    situacion situacion;
    String motivosituacion;
    String observacion_cliente;
    String perfilinterno;
    String inversorcalificado;
    String actividad_economica_principal;
    String actividad_economica_secundaria;
    int codpais;
    String correofactura;
    int codresidencia;
    String canal_distribucion;
    

    public cliente() {

    }

    public cliente(int codigo, int comitente, String nombre, String ruc, String cedula, Date fechanacimiento, String estadocivil, String conyugue, String direccion, String telefono, String fax, String celular, String mail, String profesion, String lugartrabajo, String cargolaboral, Double salario, giraduria giraduria, int legajo, String turno, int descuentos_aportes, String direccionlaboral, Date fechaingreso, Date fechaingresofuncionario, String telefonolaboral, String faxlaboral, String maillaboral, int vendedor, int categoria, int plazocredito, Double salarioconyugue, Double otrosingresos, localidad localidad, Double limitecredito, Double credito, String notas, int estado, Double saldo, String cedulaconyugue, String garante, String conyuguegarante, String cedulagarante, String cedulaconyuguegarante, String direcciongarante, String telefonogarante, String lugartrabajogarante, String profesiongarante, String teloficinagarante, plan idcta, int asesor, int banco, String cuenta, int casapropia, int autopropio, int informconf, int sexo, abogado abogado, barrio barrio, Double saldoanterior, Double importedebe, Double importehaber, carrera carrera, int semestre, Date fechaculminacion, String nroregistro, int res90, int codfais, int programa, String pep, String apoderado, String directorio, String objeto, String facturar_a_nombre, String direccionfactura, String telefonofactura, String rucfactura, int imprimirtitular, situacion situacion, String motivosituacion, String observacion_cliente, String perfilinterno, String inversorcalificado, String actividad_economica_principal, String actividad_economica_secundaria, int codpais, String correofactura, int codresidencia, String canal_distribucion) {
        this.codigo = codigo;
        this.comitente = comitente;
        this.nombre = nombre;
        this.ruc = ruc;
        this.cedula = cedula;
        this.fechanacimiento = fechanacimiento;
        this.estadocivil = estadocivil;
        this.conyugue = conyugue;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fax = fax;
        this.celular = celular;
        this.mail = mail;
        this.profesion = profesion;
        this.lugartrabajo = lugartrabajo;
        this.cargolaboral = cargolaboral;
        this.salario = salario;
        this.giraduria = giraduria;
        this.legajo = legajo;
        this.turno = turno;
        this.descuentos_aportes = descuentos_aportes;
        this.direccionlaboral = direccionlaboral;
        this.fechaingreso = fechaingreso;
        this.fechaingresofuncionario = fechaingresofuncionario;
        this.telefonolaboral = telefonolaboral;
        this.faxlaboral = faxlaboral;
        this.maillaboral = maillaboral;
        this.vendedor = vendedor;
        this.categoria = categoria;
        this.plazocredito = plazocredito;
        this.salarioconyugue = salarioconyugue;
        this.otrosingresos = otrosingresos;
        this.localidad = localidad;
        this.limitecredito = limitecredito;
        this.credito = credito;
        this.notas = notas;
        this.estado = estado;
        this.saldo = saldo;
        this.cedulaconyugue = cedulaconyugue;
        this.garante = garante;
        this.conyuguegarante = conyuguegarante;
        this.cedulagarante = cedulagarante;
        this.cedulaconyuguegarante = cedulaconyuguegarante;
        this.direcciongarante = direcciongarante;
        this.telefonogarante = telefonogarante;
        this.lugartrabajogarante = lugartrabajogarante;
        this.profesiongarante = profesiongarante;
        this.teloficinagarante = teloficinagarante;
        this.idcta = idcta;
        this.asesor = asesor;
        this.banco = banco;
        this.cuenta = cuenta;
        this.casapropia = casapropia;
        this.autopropio = autopropio;
        this.informconf = informconf;
        this.sexo = sexo;
        this.abogado = abogado;
        this.barrio = barrio;
        this.saldoanterior = saldoanterior;
        this.importedebe = importedebe;
        this.importehaber = importehaber;
        this.carrera = carrera;
        this.semestre = semestre;
        this.fechaculminacion = fechaculminacion;
        this.nroregistro = nroregistro;
        this.res90 = res90;
        this.codfais = codfais;
        this.programa = programa;
        this.pep = pep;
        this.apoderado = apoderado;
        this.directorio = directorio;
        this.objeto = objeto;
        this.facturar_a_nombre = facturar_a_nombre;
        this.direccionfactura = direccionfactura;
        this.telefonofactura = telefonofactura;
        this.rucfactura = rucfactura;
        this.imprimirtitular = imprimirtitular;
        this.situacion = situacion;
        this.motivosituacion = motivosituacion;
        this.observacion_cliente = observacion_cliente;
        this.perfilinterno = perfilinterno;
        this.inversorcalificado = inversorcalificado;
        this.actividad_economica_principal = actividad_economica_principal;
        this.actividad_economica_secundaria = actividad_economica_secundaria;
        this.codpais = codpais;
        this.correofactura = correofactura;
        this.codresidencia = codresidencia;
        this.canal_distribucion = canal_distribucion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getComitente() {
        return comitente;
    }

    public void setComitente(int comitente) {
        this.comitente = comitente;
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getConyugue() {
        return conyugue;
    }

    public void setConyugue(String conyugue) {
        this.conyugue = conyugue;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getLugartrabajo() {
        return lugartrabajo;
    }

    public void setLugartrabajo(String lugartrabajo) {
        this.lugartrabajo = lugartrabajo;
    }

    public String getCargolaboral() {
        return cargolaboral;
    }

    public void setCargolaboral(String cargolaboral) {
        this.cargolaboral = cargolaboral;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public giraduria getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(giraduria giraduria) {
        this.giraduria = giraduria;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getDescuentos_aportes() {
        return descuentos_aportes;
    }

    public void setDescuentos_aportes(int descuentos_aportes) {
        this.descuentos_aportes = descuentos_aportes;
    }

    public String getDireccionlaboral() {
        return direccionlaboral;
    }

    public void setDireccionlaboral(String direccionlaboral) {
        this.direccionlaboral = direccionlaboral;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public Date getFechaingresofuncionario() {
        return fechaingresofuncionario;
    }

    public void setFechaingresofuncionario(Date fechaingresofuncionario) {
        this.fechaingresofuncionario = fechaingresofuncionario;
    }

    public String getTelefonolaboral() {
        return telefonolaboral;
    }

    public void setTelefonolaboral(String telefonolaboral) {
        this.telefonolaboral = telefonolaboral;
    }

    public String getFaxlaboral() {
        return faxlaboral;
    }

    public void setFaxlaboral(String faxlaboral) {
        this.faxlaboral = faxlaboral;
    }

    public String getMaillaboral() {
        return maillaboral;
    }

    public void setMaillaboral(String maillaboral) {
        this.maillaboral = maillaboral;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getPlazocredito() {
        return plazocredito;
    }

    public void setPlazocredito(int plazocredito) {
        this.plazocredito = plazocredito;
    }

    public Double getSalarioconyugue() {
        return salarioconyugue;
    }

    public void setSalarioconyugue(Double salarioconyugue) {
        this.salarioconyugue = salarioconyugue;
    }

    public Double getOtrosingresos() {
        return otrosingresos;
    }

    public void setOtrosingresos(Double otrosingresos) {
        this.otrosingresos = otrosingresos;
    }

    public localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(localidad localidad) {
        this.localidad = localidad;
    }

    public Double getLimitecredito() {
        return limitecredito;
    }

    public void setLimitecredito(Double limitecredito) {
        this.limitecredito = limitecredito;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getCedulaconyugue() {
        return cedulaconyugue;
    }

    public void setCedulaconyugue(String cedulaconyugue) {
        this.cedulaconyugue = cedulaconyugue;
    }

    public String getGarante() {
        return garante;
    }

    public void setGarante(String garante) {
        this.garante = garante;
    }

    public String getConyuguegarante() {
        return conyuguegarante;
    }

    public void setConyuguegarante(String conyuguegarante) {
        this.conyuguegarante = conyuguegarante;
    }

    public String getCedulagarante() {
        return cedulagarante;
    }

    public void setCedulagarante(String cedulagarante) {
        this.cedulagarante = cedulagarante;
    }

    public String getCedulaconyuguegarante() {
        return cedulaconyuguegarante;
    }

    public void setCedulaconyuguegarante(String cedulaconyuguegarante) {
        this.cedulaconyuguegarante = cedulaconyuguegarante;
    }

    public String getDirecciongarante() {
        return direcciongarante;
    }

    public void setDirecciongarante(String direcciongarante) {
        this.direcciongarante = direcciongarante;
    }

    public String getTelefonogarante() {
        return telefonogarante;
    }

    public void setTelefonogarante(String telefonogarante) {
        this.telefonogarante = telefonogarante;
    }

    public String getLugartrabajogarante() {
        return lugartrabajogarante;
    }

    public void setLugartrabajogarante(String lugartrabajogarante) {
        this.lugartrabajogarante = lugartrabajogarante;
    }

    public String getProfesiongarante() {
        return profesiongarante;
    }

    public void setProfesiongarante(String profesiongarante) {
        this.profesiongarante = profesiongarante;
    }

    public String getTeloficinagarante() {
        return teloficinagarante;
    }

    public void setTeloficinagarante(String teloficinagarante) {
        this.teloficinagarante = teloficinagarante;
    }

    public plan getIdcta() {
        return idcta;
    }

    public void setIdcta(plan idcta) {
        this.idcta = idcta;
    }

    public int getAsesor() {
        return asesor;
    }

    public void setAsesor(int asesor) {
        this.asesor = asesor;
    }

    public int getBanco() {
        return banco;
    }

    public void setBanco(int banco) {
        this.banco = banco;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public int getCasapropia() {
        return casapropia;
    }

    public void setCasapropia(int casapropia) {
        this.casapropia = casapropia;
    }

    public int getAutopropio() {
        return autopropio;
    }

    public void setAutopropio(int autopropio) {
        this.autopropio = autopropio;
    }

    public int getInformconf() {
        return informconf;
    }

    public void setInformconf(int informconf) {
        this.informconf = informconf;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public abogado getAbogado() {
        return abogado;
    }

    public void setAbogado(abogado abogado) {
        this.abogado = abogado;
    }

    public barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(barrio barrio) {
        this.barrio = barrio;
    }

    public Double getSaldoanterior() {
        return saldoanterior;
    }

    public void setSaldoanterior(Double saldoanterior) {
        this.saldoanterior = saldoanterior;
    }

    public Double getImportedebe() {
        return importedebe;
    }

    public void setImportedebe(Double importedebe) {
        this.importedebe = importedebe;
    }

    public Double getImportehaber() {
        return importehaber;
    }

    public void setImportehaber(Double importehaber) {
        this.importehaber = importehaber;
    }

    public carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(carrera carrera) {
        this.carrera = carrera;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Date getFechaculminacion() {
        return fechaculminacion;
    }

    public void setFechaculminacion(Date fechaculminacion) {
        this.fechaculminacion = fechaculminacion;
    }

    public String getNroregistro() {
        return nroregistro;
    }

    public void setNroregistro(String nroregistro) {
        this.nroregistro = nroregistro;
    }

    public int getRes90() {
        return res90;
    }

    public void setRes90(int res90) {
        this.res90 = res90;
    }

    public int getCodfais() {
        return codfais;
    }

    public void setCodfais(int codfais) {
        this.codfais = codfais;
    }

    public int getPrograma() {
        return programa;
    }

    public void setPrograma(int programa) {
        this.programa = programa;
    }

    public String getPep() {
        return pep;
    }

    public void setPep(String pep) {
        this.pep = pep;
    }

    public String getApoderado() {
        return apoderado;
    }

    public void setApoderado(String apoderado) {
        this.apoderado = apoderado;
    }

    public String getDirectorio() {
        return directorio;
    }

    public void setDirectorio(String directorio) {
        this.directorio = directorio;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getFacturar_a_nombre() {
        return facturar_a_nombre;
    }

    public void setFacturar_a_nombre(String facturar_a_nombre) {
        this.facturar_a_nombre = facturar_a_nombre;
    }

    public String getDireccionfactura() {
        return direccionfactura;
    }

    public void setDireccionfactura(String direccionfactura) {
        this.direccionfactura = direccionfactura;
    }

    public String getTelefonofactura() {
        return telefonofactura;
    }

    public void setTelefonofactura(String telefonofactura) {
        this.telefonofactura = telefonofactura;
    }

    public String getRucfactura() {
        return rucfactura;
    }

    public void setRucfactura(String rucfactura) {
        this.rucfactura = rucfactura;
    }

    public int getImprimirtitular() {
        return imprimirtitular;
    }

    public void setImprimirtitular(int imprimirtitular) {
        this.imprimirtitular = imprimirtitular;
    }

    public situacion getSituacion() {
        return situacion;
    }

    public void setSituacion(situacion situacion) {
        this.situacion = situacion;
    }

    public String getMotivosituacion() {
        return motivosituacion;
    }

    public void setMotivosituacion(String motivosituacion) {
        this.motivosituacion = motivosituacion;
    }

    public String getObservacion_cliente() {
        return observacion_cliente;
    }

    public void setObservacion_cliente(String observacion_cliente) {
        this.observacion_cliente = observacion_cliente;
    }

    public String getPerfilinterno() {
        return perfilinterno;
    }

    public void setPerfilinterno(String perfilinterno) {
        this.perfilinterno = perfilinterno;
    }

    public String getInversorcalificado() {
        return inversorcalificado;
    }

    public void setInversorcalificado(String inversorcalificado) {
        this.inversorcalificado = inversorcalificado;
    }

    public String getActividad_economica_principal() {
        return actividad_economica_principal;
    }

    public void setActividad_economica_principal(String actividad_economica_principal) {
        this.actividad_economica_principal = actividad_economica_principal;
    }

    public String getActividad_economica_secundaria() {
        return actividad_economica_secundaria;
    }

    public void setActividad_economica_secundaria(String actividad_economica_secundaria) {
        this.actividad_economica_secundaria = actividad_economica_secundaria;
    }

    public int getCodpais() {
        return codpais;
    }

    public void setCodpais(int codpais) {
        this.codpais = codpais;
    }

    public String getCorreofactura() {
        return correofactura;
    }

    public void setCorreofactura(String correofactura) {
        this.correofactura = correofactura;
    }

    public int getCodresidencia() {
        return codresidencia;
    }

    public void setCodresidencia(int codresidencia) {
        this.codresidencia = codresidencia;
    }

    public String getCanal_distribucion() {
        return canal_distribucion;
    }

    public void setCanal_distribucion(String canal_distribucion) {
        this.canal_distribucion = canal_distribucion;
    }



}

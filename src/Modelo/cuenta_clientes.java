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
 * @author SERVIDOR
 */
public class cuenta_clientes {

    String iddocumento;
    String creferencia;
    String documento;
    String factura;
    Date fecha;
    Date vencimiento;
    Date fechapago;
    cliente cliente;
    detalle_descuento_documento detalle_descuento_documento;
    int sucursal;
    moneda moneda;
    int vendedor;
    int plazo;
    comprobante comprobante;
    casa comercial;
    BigDecimal importe;
    int numerocuota;
    int cuota;
    BigDecimal saldo;
    BigDecimal pagos;
    String autorizacion;
    BigDecimal amortiza;
    BigDecimal minteres;
    BigDecimal tasaoperativa;
    int asesor;
    String fraccion;
    String manzana;
    String lote;
    giraduria giraduria;
    String nrocuenta;
    BigDecimal n30dias;
    BigDecimal n60dias;
    BigDecimal n90dias;
    BigDecimal n180dias;
    BigDecimal masde180dias;
    BigDecimal punipagadocaja;
    BigDecimal morapagadocaja;
    BigDecimal gastospagadocaja;
    BigDecimal punipagadope;
    BigDecimal morapagadope;
    BigDecimal gastospagadope;
    BigDecimal punitorio;
    BigDecimal mora;
    BigDecimal gastos;
    int diasmora;
    int diasgraciagastos;
    int diasgraciamora;
    BigDecimal interespunitorio;
    double alquiler;
    double ivaalquiler;
    double garage;
    double ivagarage;
    double expensa;
    double ivaexpensa;
    double comision;
    double ivacomision;
    double multa;
    double ivamulta;
    double fondo;
    double garantia;
    double llave;
    double otros;
    edificio edificio;
    propietario propietario;
    inmueble inmueble;
    Date vencegracia;
    int refinanciado;
    double pagoexpress;
    double deducciones;
    double descuentocheques;
    double creditos;
    double debitos;
    double saldofactura;
    String descripcion;
    String nrorecibo;
    String nombreobra;
    carrera carrera;
    producto producto;
    int periodo;
    int idcarrera;
    int semestre;
    int materia;
    
    public cuenta_clientes() {

    }

    public cuenta_clientes(String iddocumento, String creferencia, String documento, String factura, Date fecha, Date vencimiento, Date fechapago, cliente cliente, detalle_descuento_documento detalle_descuento_documento, int sucursal, moneda moneda, int vendedor, int plazo, comprobante comprobante, casa comercial, BigDecimal importe, int numerocuota, int cuota, BigDecimal saldo, BigDecimal pagos, String autorizacion, BigDecimal amortiza, BigDecimal minteres, BigDecimal tasaoperativa, int asesor, String fraccion, String manzana, String lote, giraduria giraduria, String nrocuenta, BigDecimal n30dias, BigDecimal n60dias, BigDecimal n90dias, BigDecimal n180dias, BigDecimal masde180dias, BigDecimal punipagadocaja, BigDecimal morapagadocaja, BigDecimal gastospagadocaja, BigDecimal punipagadope, BigDecimal morapagadope, BigDecimal gastospagadope, BigDecimal punitorio, BigDecimal mora, BigDecimal gastos, int diasmora, int diasgraciagastos, int diasgraciamora, BigDecimal interespunitorio, double alquiler, double ivaalquiler, double garage, double ivagarage, double expensa, double ivaexpensa, double comision, double ivacomision, double multa, double ivamulta, double fondo, double garantia, double llave, double otros, edificio edificio, propietario propietario, inmueble inmueble, Date vencegracia, int refinanciado, double pagoexpress, double deducciones, double descuentocheques, double creditos, double debitos, double saldofactura, String descripcion, String nrorecibo, String nombreobra, carrera carrera, producto producto, int periodo, int idcarrera, int semestre, int materia) {
        this.iddocumento = iddocumento;
        this.creferencia = creferencia;
        this.documento = documento;
        this.factura = factura;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.fechapago = fechapago;
        this.cliente = cliente;
        this.detalle_descuento_documento = detalle_descuento_documento;
        this.sucursal = sucursal;
        this.moneda = moneda;
        this.vendedor = vendedor;
        this.plazo = plazo;
        this.comprobante = comprobante;
        this.comercial = comercial;
        this.importe = importe;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
        this.saldo = saldo;
        this.pagos = pagos;
        this.autorizacion = autorizacion;
        this.amortiza = amortiza;
        this.minteres = minteres;
        this.tasaoperativa = tasaoperativa;
        this.asesor = asesor;
        this.fraccion = fraccion;
        this.manzana = manzana;
        this.lote = lote;
        this.giraduria = giraduria;
        this.nrocuenta = nrocuenta;
        this.n30dias = n30dias;
        this.n60dias = n60dias;
        this.n90dias = n90dias;
        this.n180dias = n180dias;
        this.masde180dias = masde180dias;
        this.punipagadocaja = punipagadocaja;
        this.morapagadocaja = morapagadocaja;
        this.gastospagadocaja = gastospagadocaja;
        this.punipagadope = punipagadope;
        this.morapagadope = morapagadope;
        this.gastospagadope = gastospagadope;
        this.punitorio = punitorio;
        this.mora = mora;
        this.gastos = gastos;
        this.diasmora = diasmora;
        this.diasgraciagastos = diasgraciagastos;
        this.diasgraciamora = diasgraciamora;
        this.interespunitorio = interespunitorio;
        this.alquiler = alquiler;
        this.ivaalquiler = ivaalquiler;
        this.garage = garage;
        this.ivagarage = ivagarage;
        this.expensa = expensa;
        this.ivaexpensa = ivaexpensa;
        this.comision = comision;
        this.ivacomision = ivacomision;
        this.multa = multa;
        this.ivamulta = ivamulta;
        this.fondo = fondo;
        this.garantia = garantia;
        this.llave = llave;
        this.otros = otros;
        this.edificio = edificio;
        this.propietario = propietario;
        this.inmueble = inmueble;
        this.vencegracia = vencegracia;
        this.refinanciado = refinanciado;
        this.pagoexpress = pagoexpress;
        this.deducciones = deducciones;
        this.descuentocheques = descuentocheques;
        this.creditos = creditos;
        this.debitos = debitos;
        this.saldofactura = saldofactura;
        this.descripcion = descripcion;
        this.nrorecibo = nrorecibo;
        this.nombreobra = nombreobra;
        this.carrera = carrera;
        this.producto = producto;
        this.periodo = periodo;
        this.idcarrera = idcarrera;
        this.semestre = semestre;
        this.materia = materia;
    }

    public String getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(String iddocumento) {
        this.iddocumento = iddocumento;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public detalle_descuento_documento getDetalle_descuento_documento() {
        return detalle_descuento_documento;
    }

    public void setDetalle_descuento_documento(detalle_descuento_documento detalle_descuento_documento) {
        this.detalle_descuento_documento = detalle_descuento_documento;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public casa getComercial() {
        return comercial;
    }

    public void setComercial(casa comercial) {
        this.comercial = comercial;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public int getNumerocuota() {
        return numerocuota;
    }

    public void setNumerocuota(int numerocuota) {
        this.numerocuota = numerocuota;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getPagos() {
        return pagos;
    }

    public void setPagos(BigDecimal pagos) {
        this.pagos = pagos;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public BigDecimal getAmortiza() {
        return amortiza;
    }

    public void setAmortiza(BigDecimal amortiza) {
        this.amortiza = amortiza;
    }

    public BigDecimal getMinteres() {
        return minteres;
    }

    public void setMinteres(BigDecimal minteres) {
        this.minteres = minteres;
    }

    public BigDecimal getTasaoperativa() {
        return tasaoperativa;
    }

    public void setTasaoperativa(BigDecimal tasaoperativa) {
        this.tasaoperativa = tasaoperativa;
    }

    public int getAsesor() {
        return asesor;
    }

    public void setAsesor(int asesor) {
        this.asesor = asesor;
    }

    public String getFraccion() {
        return fraccion;
    }

    public void setFraccion(String fraccion) {
        this.fraccion = fraccion;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public giraduria getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(giraduria giraduria) {
        this.giraduria = giraduria;
    }

    public String getNrocuenta() {
        return nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        this.nrocuenta = nrocuenta;
    }

    public BigDecimal getN30dias() {
        return n30dias;
    }

    public void setN30dias(BigDecimal n30dias) {
        this.n30dias = n30dias;
    }

    public BigDecimal getN60dias() {
        return n60dias;
    }

    public void setN60dias(BigDecimal n60dias) {
        this.n60dias = n60dias;
    }

    public BigDecimal getN90dias() {
        return n90dias;
    }

    public void setN90dias(BigDecimal n90dias) {
        this.n90dias = n90dias;
    }

    public BigDecimal getN180dias() {
        return n180dias;
    }

    public void setN180dias(BigDecimal n180dias) {
        this.n180dias = n180dias;
    }

    public BigDecimal getMasde180dias() {
        return masde180dias;
    }

    public void setMasde180dias(BigDecimal masde180dias) {
        this.masde180dias = masde180dias;
    }

    public BigDecimal getPunipagadocaja() {
        return punipagadocaja;
    }

    public void setPunipagadocaja(BigDecimal punipagadocaja) {
        this.punipagadocaja = punipagadocaja;
    }

    public BigDecimal getMorapagadocaja() {
        return morapagadocaja;
    }

    public void setMorapagadocaja(BigDecimal morapagadocaja) {
        this.morapagadocaja = morapagadocaja;
    }

    public BigDecimal getGastospagadocaja() {
        return gastospagadocaja;
    }

    public void setGastospagadocaja(BigDecimal gastospagadocaja) {
        this.gastospagadocaja = gastospagadocaja;
    }

    public BigDecimal getPunipagadope() {
        return punipagadope;
    }

    public void setPunipagadope(BigDecimal punipagadope) {
        this.punipagadope = punipagadope;
    }

    public BigDecimal getMorapagadope() {
        return morapagadope;
    }

    public void setMorapagadope(BigDecimal morapagadope) {
        this.morapagadope = morapagadope;
    }

    public BigDecimal getGastospagadope() {
        return gastospagadope;
    }

    public void setGastospagadope(BigDecimal gastospagadope) {
        this.gastospagadope = gastospagadope;
    }

    public BigDecimal getPunitorio() {
        return punitorio;
    }

    public void setPunitorio(BigDecimal punitorio) {
        this.punitorio = punitorio;
    }

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }

    public BigDecimal getGastos() {
        return gastos;
    }

    public void setGastos(BigDecimal gastos) {
        this.gastos = gastos;
    }

    public int getDiasmora() {
        return diasmora;
    }

    public void setDiasmora(int diasmora) {
        this.diasmora = diasmora;
    }

    public int getDiasgraciagastos() {
        return diasgraciagastos;
    }

    public void setDiasgraciagastos(int diasgraciagastos) {
        this.diasgraciagastos = diasgraciagastos;
    }

    public int getDiasgraciamora() {
        return diasgraciamora;
    }

    public void setDiasgraciamora(int diasgraciamora) {
        this.diasgraciamora = diasgraciamora;
    }

    public BigDecimal getInterespunitorio() {
        return interespunitorio;
    }

    public void setInterespunitorio(BigDecimal interespunitorio) {
        this.interespunitorio = interespunitorio;
    }

    public double getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(double alquiler) {
        this.alquiler = alquiler;
    }

    public double getIvaalquiler() {
        return ivaalquiler;
    }

    public void setIvaalquiler(double ivaalquiler) {
        this.ivaalquiler = ivaalquiler;
    }

    public double getGarage() {
        return garage;
    }

    public void setGarage(double garage) {
        this.garage = garage;
    }

    public double getIvagarage() {
        return ivagarage;
    }

    public void setIvagarage(double ivagarage) {
        this.ivagarage = ivagarage;
    }

    public double getExpensa() {
        return expensa;
    }

    public void setExpensa(double expensa) {
        this.expensa = expensa;
    }

    public double getIvaexpensa() {
        return ivaexpensa;
    }

    public void setIvaexpensa(double ivaexpensa) {
        this.ivaexpensa = ivaexpensa;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public double getIvacomision() {
        return ivacomision;
    }

    public void setIvacomision(double ivacomision) {
        this.ivacomision = ivacomision;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public double getIvamulta() {
        return ivamulta;
    }

    public void setIvamulta(double ivamulta) {
        this.ivamulta = ivamulta;
    }

    public double getFondo() {
        return fondo;
    }

    public void setFondo(double fondo) {
        this.fondo = fondo;
    }

    public double getGarantia() {
        return garantia;
    }

    public void setGarantia(double garantia) {
        this.garantia = garantia;
    }

    public double getLlave() {
        return llave;
    }

    public void setLlave(double llave) {
        this.llave = llave;
    }

    public double getOtros() {
        return otros;
    }

    public void setOtros(double otros) {
        this.otros = otros;
    }

    public edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(edificio edificio) {
        this.edificio = edificio;
    }

    public propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(propietario propietario) {
        this.propietario = propietario;
    }

    public inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Date getVencegracia() {
        return vencegracia;
    }

    public void setVencegracia(Date vencegracia) {
        this.vencegracia = vencegracia;
    }

    public int getRefinanciado() {
        return refinanciado;
    }

    public void setRefinanciado(int refinanciado) {
        this.refinanciado = refinanciado;
    }

    public double getPagoexpress() {
        return pagoexpress;
    }

    public void setPagoexpress(double pagoexpress) {
        this.pagoexpress = pagoexpress;
    }

    public double getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(double deducciones) {
        this.deducciones = deducciones;
    }

    public double getDescuentocheques() {
        return descuentocheques;
    }

    public void setDescuentocheques(double descuentocheques) {
        this.descuentocheques = descuentocheques;
    }

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }

    public double getDebitos() {
        return debitos;
    }

    public void setDebitos(double debitos) {
        this.debitos = debitos;
    }

    public double getSaldofactura() {
        return saldofactura;
    }

    public void setSaldofactura(double saldofactura) {
        this.saldofactura = saldofactura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNrorecibo() {
        return nrorecibo;
    }

    public void setNrorecibo(String nrorecibo) {
        this.nrorecibo = nrorecibo;
    }

    public String getNombreobra() {
        return nombreobra;
    }

    public void setNombreobra(String nombreobra) {
        this.nombreobra = nombreobra;
    }

    public carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(carrera carrera) {
        this.carrera = carrera;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getIdcarrera() {
        return idcarrera;
    }

    public void setIdcarrera(int idcarrera) {
        this.idcarrera = idcarrera;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getMateria() {
        return materia;
    }

    public void setMateria(int materia) {
        this.materia = materia;
    }
    
}

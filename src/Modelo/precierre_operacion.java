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
 * @author notebook
 */
public class precierre_operacion {

    String creferencia;
    double numero;
    int negociado;
    Date fecha;
    Date validohasta;
    Date fechacierre;
    Date fechaemision;
    Date vencimiento;
    int comprador;
    String nombrecomprador;
    int vendedor;
    String nombrevendedor;
    int asesorcompra;
    String nombre_asesor_compra;
    int asesorventa;
    String nombre_asesor_venta;
    int operacion;
    int tipooperacion;
    int mercado;
    moneda moneda;
    emisor emisor;
    titulo titulo;
    String serie;
    String nro_titulo;
    int desde_acci;
    int hasta_acci;
    acciones tipoaccion;
    BigDecimal valor_nominal;
    BigDecimal cantidad;
    BigDecimal precio;
    BigDecimal valor_inversion;
    BigDecimal tasa;
    int plazo;
    int tipoplazo;
    formapago formapago;
    Date fechadeposito;
    int lugardeposito;
    String observacion;
    custodia custodia;
    BigDecimal comvendedor;
    int facturarvendedor;
    BigDecimal comcomprador;
    int facturarcomprador;
    int entregatitulo;
    String referenciacompra;
    String referenciaventa;
    int ordencompra;
    int ordenventa;
    String numerobolsa;
    int base;
    int cortes;
    BigDecimal preciocorte;
    int periodopago;
    int cierre;
    int cerrado;
    String idrecibo;
    int bancopago;
    String nombrebanco;
    String cuentapago;
    BigDecimal cotizacion;
    int arancelcompra;
    int arancelventa;
    int origenfondos;
    String idcarteravendida;
    BigDecimal saldocartera;
    String idnewcartera;
    int ntipoamortiza;
    int marcar;
    int corteamortizacion;
    Date primervencimiento;
    comprobante tipo_comprobante;
    Double nroviejo;
    int usuarioalta;
    String nombreusuarioalta;
    int usuarioupdate;
    String nombreusuarioupdate;
    int cupones;
    String nombrecliente;
    String nombremercado;
    int cuentacliente;
            

    public precierre_operacion() {

    }

    public precierre_operacion(String creferencia, double numero, int negociado, Date fecha, Date validohasta, Date fechacierre, Date fechaemision, Date vencimiento, int comprador, String nombrecomprador, int vendedor, String nombrevendedor, int asesorcompra, String nombre_asesor_compra, int asesorventa, String nombre_asesor_venta, int operacion, int tipooperacion, int mercado, moneda moneda, emisor emisor, titulo titulo, String serie, String nro_titulo, int desde_acci, int hasta_acci, acciones tipoaccion, BigDecimal valor_nominal, BigDecimal cantidad, BigDecimal precio, BigDecimal valor_inversion, BigDecimal tasa, int plazo, int tipoplazo, formapago formapago, Date fechadeposito, int lugardeposito, String observacion, custodia custodia, BigDecimal comvendedor, int facturarvendedor, BigDecimal comcomprador, int facturarcomprador, int entregatitulo, String referenciacompra, String referenciaventa, int ordencompra, int ordenventa, String numerobolsa, int base, int cortes, BigDecimal preciocorte, int periodopago, int cierre, int cerrado, String idrecibo, int bancopago, String nombrebanco, String cuentapago, BigDecimal cotizacion, int arancelcompra, int arancelventa, int origenfondos, String idcarteravendida, BigDecimal saldocartera, String idnewcartera, int ntipoamortiza, int marcar, int corteamortizacion, Date primervencimiento, comprobante tipo_comprobante, Double nroviejo, int usuarioalta, String nombreusuarioalta, int usuarioupdate, String nombreusuarioupdate, int cupones, String nombrecliente, String nombremercado, int cuentacliente) {
        this.creferencia = creferencia;
        this.numero = numero;
        this.negociado = negociado;
        this.fecha = fecha;
        this.validohasta = validohasta;
        this.fechacierre = fechacierre;
        this.fechaemision = fechaemision;
        this.vencimiento = vencimiento;
        this.comprador = comprador;
        this.nombrecomprador = nombrecomprador;
        this.vendedor = vendedor;
        this.nombrevendedor = nombrevendedor;
        this.asesorcompra = asesorcompra;
        this.nombre_asesor_compra = nombre_asesor_compra;
        this.asesorventa = asesorventa;
        this.nombre_asesor_venta = nombre_asesor_venta;
        this.operacion = operacion;
        this.tipooperacion = tipooperacion;
        this.mercado = mercado;
        this.moneda = moneda;
        this.emisor = emisor;
        this.titulo = titulo;
        this.serie = serie;
        this.nro_titulo = nro_titulo;
        this.desde_acci = desde_acci;
        this.hasta_acci = hasta_acci;
        this.tipoaccion = tipoaccion;
        this.valor_nominal = valor_nominal;
        this.cantidad = cantidad;
        this.precio = precio;
        this.valor_inversion = valor_inversion;
        this.tasa = tasa;
        this.plazo = plazo;
        this.tipoplazo = tipoplazo;
        this.formapago = formapago;
        this.fechadeposito = fechadeposito;
        this.lugardeposito = lugardeposito;
        this.observacion = observacion;
        this.custodia = custodia;
        this.comvendedor = comvendedor;
        this.facturarvendedor = facturarvendedor;
        this.comcomprador = comcomprador;
        this.facturarcomprador = facturarcomprador;
        this.entregatitulo = entregatitulo;
        this.referenciacompra = referenciacompra;
        this.referenciaventa = referenciaventa;
        this.ordencompra = ordencompra;
        this.ordenventa = ordenventa;
        this.numerobolsa = numerobolsa;
        this.base = base;
        this.cortes = cortes;
        this.preciocorte = preciocorte;
        this.periodopago = periodopago;
        this.cierre = cierre;
        this.cerrado = cerrado;
        this.idrecibo = idrecibo;
        this.bancopago = bancopago;
        this.nombrebanco = nombrebanco;
        this.cuentapago = cuentapago;
        this.cotizacion = cotizacion;
        this.arancelcompra = arancelcompra;
        this.arancelventa = arancelventa;
        this.origenfondos = origenfondos;
        this.idcarteravendida = idcarteravendida;
        this.saldocartera = saldocartera;
        this.idnewcartera = idnewcartera;
        this.ntipoamortiza = ntipoamortiza;
        this.marcar = marcar;
        this.corteamortizacion = corteamortizacion;
        this.primervencimiento = primervencimiento;
        this.tipo_comprobante = tipo_comprobante;
        this.nroviejo = nroviejo;
        this.usuarioalta = usuarioalta;
        this.nombreusuarioalta = nombreusuarioalta;
        this.usuarioupdate = usuarioupdate;
        this.nombreusuarioupdate = nombreusuarioupdate;
        this.cupones = cupones;
        this.nombrecliente = nombrecliente;
        this.nombremercado = nombremercado;
        this.cuentacliente = cuentacliente;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public int getNegociado() {
        return negociado;
    }

    public void setNegociado(int negociado) {
        this.negociado = negociado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getValidohasta() {
        return validohasta;
    }

    public void setValidohasta(Date validohasta) {
        this.validohasta = validohasta;
    }

    public Date getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(Date fechacierre) {
        this.fechacierre = fechacierre;
    }

    public Date getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(Date fechaemision) {
        this.fechaemision = fechaemision;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public int getComprador() {
        return comprador;
    }

    public void setComprador(int comprador) {
        this.comprador = comprador;
    }

    public String getNombrecomprador() {
        return nombrecomprador;
    }

    public void setNombrecomprador(String nombrecomprador) {
        this.nombrecomprador = nombrecomprador;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }

    public String getNombrevendedor() {
        return nombrevendedor;
    }

    public void setNombrevendedor(String nombrevendedor) {
        this.nombrevendedor = nombrevendedor;
    }

    public int getAsesorcompra() {
        return asesorcompra;
    }

    public void setAsesorcompra(int asesorcompra) {
        this.asesorcompra = asesorcompra;
    }

    public String getNombre_asesor_compra() {
        return nombre_asesor_compra;
    }

    public void setNombre_asesor_compra(String nombre_asesor_compra) {
        this.nombre_asesor_compra = nombre_asesor_compra;
    }

    public int getAsesorventa() {
        return asesorventa;
    }

    public void setAsesorventa(int asesorventa) {
        this.asesorventa = asesorventa;
    }

    public String getNombre_asesor_venta() {
        return nombre_asesor_venta;
    }

    public void setNombre_asesor_venta(String nombre_asesor_venta) {
        this.nombre_asesor_venta = nombre_asesor_venta;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public int getTipooperacion() {
        return tipooperacion;
    }

    public void setTipooperacion(int tipooperacion) {
        this.tipooperacion = tipooperacion;
    }

    public int getMercado() {
        return mercado;
    }

    public void setMercado(int mercado) {
        this.mercado = mercado;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(emisor emisor) {
        this.emisor = emisor;
    }

    public titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(titulo titulo) {
        this.titulo = titulo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNro_titulo() {
        return nro_titulo;
    }

    public void setNro_titulo(String nro_titulo) {
        this.nro_titulo = nro_titulo;
    }

    public int getDesde_acci() {
        return desde_acci;
    }

    public void setDesde_acci(int desde_acci) {
        this.desde_acci = desde_acci;
    }

    public int getHasta_acci() {
        return hasta_acci;
    }

    public void setHasta_acci(int hasta_acci) {
        this.hasta_acci = hasta_acci;
    }

    public acciones getTipoaccion() {
        return tipoaccion;
    }

    public void setTipoaccion(acciones tipoaccion) {
        this.tipoaccion = tipoaccion;
    }

    public BigDecimal getValor_nominal() {
        return valor_nominal;
    }

    public void setValor_nominal(BigDecimal valor_nominal) {
        this.valor_nominal = valor_nominal;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getValor_inversion() {
        return valor_inversion;
    }

    public void setValor_inversion(BigDecimal valor_inversion) {
        this.valor_inversion = valor_inversion;
    }

    public BigDecimal getTasa() {
        return tasa;
    }

    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public int getTipoplazo() {
        return tipoplazo;
    }

    public void setTipoplazo(int tipoplazo) {
        this.tipoplazo = tipoplazo;
    }

    public formapago getFormapago() {
        return formapago;
    }

    public void setFormapago(formapago formapago) {
        this.formapago = formapago;
    }

    public Date getFechadeposito() {
        return fechadeposito;
    }

    public void setFechadeposito(Date fechadeposito) {
        this.fechadeposito = fechadeposito;
    }

    public int getLugardeposito() {
        return lugardeposito;
    }

    public void setLugardeposito(int lugardeposito) {
        this.lugardeposito = lugardeposito;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public custodia getCustodia() {
        return custodia;
    }

    public void setCustodia(custodia custodia) {
        this.custodia = custodia;
    }

    public BigDecimal getComvendedor() {
        return comvendedor;
    }

    public void setComvendedor(BigDecimal comvendedor) {
        this.comvendedor = comvendedor;
    }

    public int getFacturarvendedor() {
        return facturarvendedor;
    }

    public void setFacturarvendedor(int facturarvendedor) {
        this.facturarvendedor = facturarvendedor;
    }

    public BigDecimal getComcomprador() {
        return comcomprador;
    }

    public void setComcomprador(BigDecimal comcomprador) {
        this.comcomprador = comcomprador;
    }

    public int getFacturarcomprador() {
        return facturarcomprador;
    }

    public void setFacturarcomprador(int facturarcomprador) {
        this.facturarcomprador = facturarcomprador;
    }

    public int getEntregatitulo() {
        return entregatitulo;
    }

    public void setEntregatitulo(int entregatitulo) {
        this.entregatitulo = entregatitulo;
    }

    public String getReferenciacompra() {
        return referenciacompra;
    }

    public void setReferenciacompra(String referenciacompra) {
        this.referenciacompra = referenciacompra;
    }

    public String getReferenciaventa() {
        return referenciaventa;
    }

    public void setReferenciaventa(String referenciaventa) {
        this.referenciaventa = referenciaventa;
    }

    public int getOrdencompra() {
        return ordencompra;
    }

    public void setOrdencompra(int ordencompra) {
        this.ordencompra = ordencompra;
    }

    public int getOrdenventa() {
        return ordenventa;
    }

    public void setOrdenventa(int ordenventa) {
        this.ordenventa = ordenventa;
    }

    public String getNumerobolsa() {
        return numerobolsa;
    }

    public void setNumerobolsa(String numerobolsa) {
        this.numerobolsa = numerobolsa;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getCortes() {
        return cortes;
    }

    public void setCortes(int cortes) {
        this.cortes = cortes;
    }

    public BigDecimal getPreciocorte() {
        return preciocorte;
    }

    public void setPreciocorte(BigDecimal preciocorte) {
        this.preciocorte = preciocorte;
    }

    public int getPeriodopago() {
        return periodopago;
    }

    public void setPeriodopago(int periodopago) {
        this.periodopago = periodopago;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public int getCerrado() {
        return cerrado;
    }

    public void setCerrado(int cerrado) {
        this.cerrado = cerrado;
    }

    public String getIdrecibo() {
        return idrecibo;
    }

    public void setIdrecibo(String idrecibo) {
        this.idrecibo = idrecibo;
    }

    public int getBancopago() {
        return bancopago;
    }

    public void setBancopago(int bancopago) {
        this.bancopago = bancopago;
    }

    public String getNombrebanco() {
        return nombrebanco;
    }

    public void setNombrebanco(String nombrebanco) {
        this.nombrebanco = nombrebanco;
    }

    public String getCuentapago() {
        return cuentapago;
    }

    public void setCuentapago(String cuentapago) {
        this.cuentapago = cuentapago;
    }

    public BigDecimal getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(BigDecimal cotizacion) {
        this.cotizacion = cotizacion;
    }

    public int getArancelcompra() {
        return arancelcompra;
    }

    public void setArancelcompra(int arancelcompra) {
        this.arancelcompra = arancelcompra;
    }

    public int getArancelventa() {
        return arancelventa;
    }

    public void setArancelventa(int arancelventa) {
        this.arancelventa = arancelventa;
    }

    public int getOrigenfondos() {
        return origenfondos;
    }

    public void setOrigenfondos(int origenfondos) {
        this.origenfondos = origenfondos;
    }

    public String getIdcarteravendida() {
        return idcarteravendida;
    }

    public void setIdcarteravendida(String idcarteravendida) {
        this.idcarteravendida = idcarteravendida;
    }

    public BigDecimal getSaldocartera() {
        return saldocartera;
    }

    public void setSaldocartera(BigDecimal saldocartera) {
        this.saldocartera = saldocartera;
    }

    public String getIdnewcartera() {
        return idnewcartera;
    }

    public void setIdnewcartera(String idnewcartera) {
        this.idnewcartera = idnewcartera;
    }

    public int getNtipoamortiza() {
        return ntipoamortiza;
    }

    public void setNtipoamortiza(int ntipoamortiza) {
        this.ntipoamortiza = ntipoamortiza;
    }

    public int getMarcar() {
        return marcar;
    }

    public void setMarcar(int marcar) {
        this.marcar = marcar;
    }

    public int getCorteamortizacion() {
        return corteamortizacion;
    }

    public void setCorteamortizacion(int corteamortizacion) {
        this.corteamortizacion = corteamortizacion;
    }

    public Date getPrimervencimiento() {
        return primervencimiento;
    }

    public void setPrimervencimiento(Date primervencimiento) {
        this.primervencimiento = primervencimiento;
    }

    public comprobante getTipo_comprobante() {
        return tipo_comprobante;
    }

    public void setTipo_comprobante(comprobante tipo_comprobante) {
        this.tipo_comprobante = tipo_comprobante;
    }

    public Double getNroviejo() {
        return nroviejo;
    }

    public void setNroviejo(Double nroviejo) {
        this.nroviejo = nroviejo;
    }

    public int getUsuarioalta() {
        return usuarioalta;
    }

    public void setUsuarioalta(int usuarioalta) {
        this.usuarioalta = usuarioalta;
    }

    public String getNombreusuarioalta() {
        return nombreusuarioalta;
    }

    public void setNombreusuarioalta(String nombreusuarioalta) {
        this.nombreusuarioalta = nombreusuarioalta;
    }

    public int getUsuarioupdate() {
        return usuarioupdate;
    }

    public void setUsuarioupdate(int usuarioupdate) {
        this.usuarioupdate = usuarioupdate;
    }

    public String getNombreusuarioupdate() {
        return nombreusuarioupdate;
    }

    public void setNombreusuarioupdate(String nombreusuarioupdate) {
        this.nombreusuarioupdate = nombreusuarioupdate;
    }

    public int getCupones() {
        return cupones;
    }

    public void setCupones(int cupones) {
        this.cupones = cupones;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getNombremercado() {
        return nombremercado;
    }

    public void setNombremercado(String nombremercado) {
        this.nombremercado = nombremercado;
    }

    public int getCuentacliente() {
        return cuentacliente;
    }

    public void setCuentacliente(int cuentacliente) {
        this.cuentacliente = cuentacliente;
    }


    

}

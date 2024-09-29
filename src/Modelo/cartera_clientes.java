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
public class cartera_clientes {

    String idcartera;
    String creferencia;
    double numero;
    int negociado;
    Date fecha;
    Date fechacierre;
    Date fechaemision;
    Date vencimiento;
    cliente comprador;
    vendedor asesorcompra;
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
    int custodia;
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
    Date fechaventa;
    BigDecimal preciomercado;
    Date fechacotizacion;
    BigDecimal interesdevengado;
    BigDecimal interesadevengar;
    int nrorecibo;
    bancoplaza bancopago;
    String cuentapago;
    int nrocertificado;
    String nroetiqueta;
    int nrocustodia;
    double cotizacion;
    int origenfondos;
    int numero_amortizacion;
    double itemdesglose;
    int periodopago;
    int estado;
    int tipodocumento;
    String  nombredocumento;
    int ncupon;
    int ncantidad;

    public cartera_clientes() {

    }

    public cartera_clientes(String idcartera, String creferencia, double numero, int negociado, Date fecha, Date fechacierre, Date fechaemision, Date vencimiento, cliente comprador, vendedor asesorcompra, int operacion, int tipooperacion, int mercado, moneda moneda, emisor emisor, titulo titulo, String serie, String nro_titulo, int desde_acci, int hasta_acci, acciones tipoaccion, BigDecimal valor_nominal, BigDecimal cantidad, BigDecimal precio, BigDecimal valor_inversion, BigDecimal tasa, int plazo, int tipoplazo, formapago formapago, Date fechadeposito, int lugardeposito, String observacion, int custodia, BigDecimal comvendedor, int facturarvendedor, BigDecimal comcomprador, int facturarcomprador, int entregatitulo, String referenciacompra, String referenciaventa, int ordencompra, int ordenventa, String numerobolsa, int base, Date fechaventa, BigDecimal preciomercado, Date fechacotizacion, BigDecimal interesdevengado, BigDecimal interesadevengar, int nrorecibo, bancoplaza bancopago, String cuentapago, int nrocertificado, String nroetiqueta, int nrocustodia, double cotizacion, int origenfondos, int numero_amortizacion, double itemdesglose, int periodopago, int estado, int tipodocumento, String nombredocumento, int ncupon, int ncantidad) {
        this.idcartera = idcartera;
        this.creferencia = creferencia;
        this.numero = numero;
        this.negociado = negociado;
        this.fecha = fecha;
        this.fechacierre = fechacierre;
        this.fechaemision = fechaemision;
        this.vencimiento = vencimiento;
        this.comprador = comprador;
        this.asesorcompra = asesorcompra;
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
        this.fechaventa = fechaventa;
        this.preciomercado = preciomercado;
        this.fechacotizacion = fechacotizacion;
        this.interesdevengado = interesdevengado;
        this.interesadevengar = interesadevengar;
        this.nrorecibo = nrorecibo;
        this.bancopago = bancopago;
        this.cuentapago = cuentapago;
        this.nrocertificado = nrocertificado;
        this.nroetiqueta = nroetiqueta;
        this.nrocustodia = nrocustodia;
        this.cotizacion = cotizacion;
        this.origenfondos = origenfondos;
        this.numero_amortizacion = numero_amortizacion;
        this.itemdesglose = itemdesglose;
        this.periodopago = periodopago;
        this.estado = estado;
        this.tipodocumento = tipodocumento;
        this.nombredocumento = nombredocumento;
        this.ncupon = ncupon;
        this.ncantidad = ncantidad;
    }

    public String getIdcartera() {
        return idcartera;
    }

    public void setIdcartera(String idcartera) {
        this.idcartera = idcartera;
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

    public cliente getComprador() {
        return comprador;
    }

    public void setComprador(cliente comprador) {
        this.comprador = comprador;
    }

    public vendedor getAsesorcompra() {
        return asesorcompra;
    }

    public void setAsesorcompra(vendedor asesorcompra) {
        this.asesorcompra = asesorcompra;
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

    public int getCustodia() {
        return custodia;
    }

    public void setCustodia(int custodia) {
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

    public Date getFechaventa() {
        return fechaventa;
    }

    public void setFechaventa(Date fechaventa) {
        this.fechaventa = fechaventa;
    }

    public BigDecimal getPreciomercado() {
        return preciomercado;
    }

    public void setPreciomercado(BigDecimal preciomercado) {
        this.preciomercado = preciomercado;
    }

    public Date getFechacotizacion() {
        return fechacotizacion;
    }

    public void setFechacotizacion(Date fechacotizacion) {
        this.fechacotizacion = fechacotizacion;
    }

    public BigDecimal getInteresdevengado() {
        return interesdevengado;
    }

    public void setInteresdevengado(BigDecimal interesdevengado) {
        this.interesdevengado = interesdevengado;
    }

    public BigDecimal getInteresadevengar() {
        return interesadevengar;
    }

    public void setInteresadevengar(BigDecimal interesadevengar) {
        this.interesadevengar = interesadevengar;
    }

    public int getNrorecibo() {
        return nrorecibo;
    }

    public void setNrorecibo(int nrorecibo) {
        this.nrorecibo = nrorecibo;
    }

    public bancoplaza getBancopago() {
        return bancopago;
    }

    public void setBancopago(bancoplaza bancopago) {
        this.bancopago = bancopago;
    }

    public String getCuentapago() {
        return cuentapago;
    }

    public void setCuentapago(String cuentapago) {
        this.cuentapago = cuentapago;
    }

    public int getNrocertificado() {
        return nrocertificado;
    }

    public void setNrocertificado(int nrocertificado) {
        this.nrocertificado = nrocertificado;
    }

    public String getNroetiqueta() {
        return nroetiqueta;
    }

    public void setNroetiqueta(String nroetiqueta) {
        this.nroetiqueta = nroetiqueta;
    }

    public int getNrocustodia() {
        return nrocustodia;
    }

    public void setNrocustodia(int nrocustodia) {
        this.nrocustodia = nrocustodia;
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public int getOrigenfondos() {
        return origenfondos;
    }

    public void setOrigenfondos(int origenfondos) {
        this.origenfondos = origenfondos;
    }

    public int getNumero_amortizacion() {
        return numero_amortizacion;
    }

    public void setNumero_amortizacion(int numero_amortizacion) {
        this.numero_amortizacion = numero_amortizacion;
    }

    public double getItemdesglose() {
        return itemdesglose;
    }

    public void setItemdesglose(double itemdesglose) {
        this.itemdesglose = itemdesglose;
    }

    public int getPeriodopago() {
        return periodopago;
    }

    public void setPeriodopago(int periodopago) {
        this.periodopago = periodopago;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(int tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getNombredocumento() {
        return nombredocumento;
    }

    public void setNombredocumento(String nombredocumento) {
        this.nombredocumento = nombredocumento;
    }

    public int getNcupon() {
        return ncupon;
    }

    public void setNcupon(int ncupon) {
        this.ncupon = ncupon;
    }

    public int getNcantidad() {
        return ncantidad;
    }

    public void setNcantidad(int ncantidad) {
        this.ncantidad = ncantidad;
    }

    
    
}

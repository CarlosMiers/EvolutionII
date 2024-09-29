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
 * @author Usuario
 */
public class cabecera_compra {

    String creferencia;
    double nrofactura;
    String formatofactura;
    sucursal sucursal;
    rubro rubro;
    proveedor proveedor;
    marca marca;
    Date fecha;
    BigDecimal exentas;
    BigDecimal gravadas10;
    BigDecimal gravadas5;
    BigDecimal totalneto;
    moneda moneda;
    int timbrado;
    Date vencetimbrado;
    BigDecimal cotizacion;
    String observacion;
    Date primer_vence;
    int cierre;
    comprobante comprobante;
    BigDecimal pagos;
    BigDecimal financiado;
    int enviarcta;
    int generarasiento;
    int asiento;
    int cuotas;
    int usuarioalta;
    Date fechaalta;
    int usuarioupdate;
    Date fechaupdate;
    int tipo_gasto;
    BigDecimal retencion;
    int importado;
    int ordencompra;
    String codprodcompra;
    String nombreproductocompra;
    int codrubrocompra;
    String nombrerubrocompra;
    String nombreproveedor;
    int codmarca;
    String nombremarca;
    String nombresucursal;
    BigDecimal cantidadcompra;
    BigDecimal preciocompra;
    BigDecimal promedio;
    int obra;
    String nombreobra;
    
    public cabecera_compra() {

    }

    public cabecera_compra(String creferencia, double nrofactura, String formatofactura, sucursal sucursal, rubro rubro, proveedor proveedor, marca marca, Date fecha, BigDecimal exentas, BigDecimal gravadas10, BigDecimal gravadas5, BigDecimal totalneto, moneda moneda, int timbrado, Date vencetimbrado, BigDecimal cotizacion, String observacion, Date primer_vence, int cierre, comprobante comprobante, BigDecimal pagos, BigDecimal financiado, int enviarcta, int generarasiento, int asiento, int cuotas, int usuarioalta, Date fechaalta, int usuarioupdate, Date fechaupdate, int tipo_gasto, BigDecimal retencion, int importado, int ordencompra, String codprodcompra, String nombreproductocompra, int codrubrocompra, String nombrerubrocompra, String nombreproveedor, int codmarca, String nombremarca, String nombresucursal, BigDecimal cantidadcompra, BigDecimal preciocompra, BigDecimal promedio, int obra, String nombreobra) {
        this.creferencia = creferencia;
        this.nrofactura = nrofactura;
        this.formatofactura = formatofactura;
        this.sucursal = sucursal;
        this.rubro = rubro;
        this.proveedor = proveedor;
        this.marca = marca;
        this.fecha = fecha;
        this.exentas = exentas;
        this.gravadas10 = gravadas10;
        this.gravadas5 = gravadas5;
        this.totalneto = totalneto;
        this.moneda = moneda;
        this.timbrado = timbrado;
        this.vencetimbrado = vencetimbrado;
        this.cotizacion = cotizacion;
        this.observacion = observacion;
        this.primer_vence = primer_vence;
        this.cierre = cierre;
        this.comprobante = comprobante;
        this.pagos = pagos;
        this.financiado = financiado;
        this.enviarcta = enviarcta;
        this.generarasiento = generarasiento;
        this.asiento = asiento;
        this.cuotas = cuotas;
        this.usuarioalta = usuarioalta;
        this.fechaalta = fechaalta;
        this.usuarioupdate = usuarioupdate;
        this.fechaupdate = fechaupdate;
        this.tipo_gasto = tipo_gasto;
        this.retencion = retencion;
        this.importado = importado;
        this.ordencompra = ordencompra;
        this.codprodcompra = codprodcompra;
        this.nombreproductocompra = nombreproductocompra;
        this.codrubrocompra = codrubrocompra;
        this.nombrerubrocompra = nombrerubrocompra;
        this.nombreproveedor = nombreproveedor;
        this.codmarca = codmarca;
        this.nombremarca = nombremarca;
        this.nombresucursal = nombresucursal;
        this.cantidadcompra = cantidadcompra;
        this.preciocompra = preciocompra;
        this.promedio = promedio;
        this.obra = obra;
        this.nombreobra = nombreobra;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public double getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(double nrofactura) {
        this.nrofactura = nrofactura;
    }

    public String getFormatofactura() {
        return formatofactura;
    }

    public void setFormatofactura(String formatofactura) {
        this.formatofactura = formatofactura;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public rubro getRubro() {
        return rubro;
    }

    public void setRubro(rubro rubro) {
        this.rubro = rubro;
    }

    public proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public marca getMarca() {
        return marca;
    }

    public void setMarca(marca marca) {
        this.marca = marca;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getExentas() {
        return exentas;
    }

    public void setExentas(BigDecimal exentas) {
        this.exentas = exentas;
    }

    public BigDecimal getGravadas10() {
        return gravadas10;
    }

    public void setGravadas10(BigDecimal gravadas10) {
        this.gravadas10 = gravadas10;
    }

    public BigDecimal getGravadas5() {
        return gravadas5;
    }

    public void setGravadas5(BigDecimal gravadas5) {
        this.gravadas5 = gravadas5;
    }

    public BigDecimal getTotalneto() {
        return totalneto;
    }

    public void setTotalneto(BigDecimal totalneto) {
        this.totalneto = totalneto;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public int getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(int timbrado) {
        this.timbrado = timbrado;
    }

    public Date getVencetimbrado() {
        return vencetimbrado;
    }

    public void setVencetimbrado(Date vencetimbrado) {
        this.vencetimbrado = vencetimbrado;
    }

    public BigDecimal getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(BigDecimal cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getPrimer_vence() {
        return primer_vence;
    }

    public void setPrimer_vence(Date primer_vence) {
        this.primer_vence = primer_vence;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public BigDecimal getPagos() {
        return pagos;
    }

    public void setPagos(BigDecimal pagos) {
        this.pagos = pagos;
    }

    public BigDecimal getFinanciado() {
        return financiado;
    }

    public void setFinanciado(BigDecimal financiado) {
        this.financiado = financiado;
    }

    public int getEnviarcta() {
        return enviarcta;
    }

    public void setEnviarcta(int enviarcta) {
        this.enviarcta = enviarcta;
    }

    public int getGenerarasiento() {
        return generarasiento;
    }

    public void setGenerarasiento(int generarasiento) {
        this.generarasiento = generarasiento;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public int getUsuarioalta() {
        return usuarioalta;
    }

    public void setUsuarioalta(int usuarioalta) {
        this.usuarioalta = usuarioalta;
    }

    public Date getFechaalta() {
        return fechaalta;
    }

    public void setFechaalta(Date fechaalta) {
        this.fechaalta = fechaalta;
    }

    public int getUsuarioupdate() {
        return usuarioupdate;
    }

    public void setUsuarioupdate(int usuarioupdate) {
        this.usuarioupdate = usuarioupdate;
    }

    public Date getFechaupdate() {
        return fechaupdate;
    }

    public void setFechaupdate(Date fechaupdate) {
        this.fechaupdate = fechaupdate;
    }

    public int getTipo_gasto() {
        return tipo_gasto;
    }

    public void setTipo_gasto(int tipo_gasto) {
        this.tipo_gasto = tipo_gasto;
    }

    public BigDecimal getRetencion() {
        return retencion;
    }

    public void setRetencion(BigDecimal retencion) {
        this.retencion = retencion;
    }

    public int getImportado() {
        return importado;
    }

    public void setImportado(int importado) {
        this.importado = importado;
    }

    public int getOrdencompra() {
        return ordencompra;
    }

    public void setOrdencompra(int ordencompra) {
        this.ordencompra = ordencompra;
    }

    public String getCodprodcompra() {
        return codprodcompra;
    }

    public void setCodprodcompra(String codprodcompra) {
        this.codprodcompra = codprodcompra;
    }

    public String getNombreproductocompra() {
        return nombreproductocompra;
    }

    public void setNombreproductocompra(String nombreproductocompra) {
        this.nombreproductocompra = nombreproductocompra;
    }

    public int getCodrubrocompra() {
        return codrubrocompra;
    }

    public void setCodrubrocompra(int codrubrocompra) {
        this.codrubrocompra = codrubrocompra;
    }

    public String getNombrerubrocompra() {
        return nombrerubrocompra;
    }

    public void setNombrerubrocompra(String nombrerubrocompra) {
        this.nombrerubrocompra = nombrerubrocompra;
    }

    public String getNombreproveedor() {
        return nombreproveedor;
    }

    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }

    public int getCodmarca() {
        return codmarca;
    }

    public void setCodmarca(int codmarca) {
        this.codmarca = codmarca;
    }

    public String getNombremarca() {
        return nombremarca;
    }

    public void setNombremarca(String nombremarca) {
        this.nombremarca = nombremarca;
    }

    public String getNombresucursal() {
        return nombresucursal;
    }

    public void setNombresucursal(String nombresucursal) {
        this.nombresucursal = nombresucursal;
    }

    public BigDecimal getCantidadcompra() {
        return cantidadcompra;
    }

    public void setCantidadcompra(BigDecimal cantidadcompra) {
        this.cantidadcompra = cantidadcompra;
    }

    public BigDecimal getPreciocompra() {
        return preciocompra;
    }

    public void setPreciocompra(BigDecimal preciocompra) {
        this.preciocompra = preciocompra;
    }

    public BigDecimal getPromedio() {
        return promedio;
    }

    public void setPromedio(BigDecimal promedio) {
        this.promedio = promedio;
    }

    public int getObra() {
        return obra;
    }

    public void setObra(int obra) {
        this.obra = obra;
    }

    public String getNombreobra() {
        return nombreobra;
    }

    public void setNombreobra(String nombreobra) {
        this.nombreobra = nombreobra;
    }

    
   
}

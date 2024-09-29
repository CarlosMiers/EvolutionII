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
public class producto {

    String codigo;
    String nombre;
    pais paises;
    rubro rubro;
    marca marca;
    medida medida;
    stock stocks;
    familia familia;
    ubicacion ubicacion;
    proveedor proveedor;
    int cambiarprecio;
    BigDecimal costo;
    BigDecimal precio_maximo;
    BigDecimal precio_minimo;
    BigDecimal precioventa;
    BigDecimal stockminimo;
    BigDecimal incremento1;
    BigDecimal incremento2;
    BigDecimal incremento3;
    BigDecimal sugerido1;
    BigDecimal sugerido2;
    BigDecimal sugerido3;
    Date fechacompra;
    int tipo_producto;
    int conteomayorista;
    int kit;
    String codigobarra;
    Date fecha_ingreso;
    int estado;
    BigDecimal conversion;
    BigDecimal stockactual;
    BigDecimal stock;
    String observacion;
    Double stocksistema;
    BigDecimal ivaporcentaje;
    int verificado;
    Date fechahecho;
    Double compras;
    Double ventas;
    Double entrada;
    Double salida;
    Double transEntrada;
    Double transSalida;
    Double amperaje;
    Double voltios;
    Double largo;
    Double ancho;
    Double alto;
    plan ctadebe;
    Double  precio_mayorista_anterior;
    Double  precio_minorista_anterior;
    Double  porciento_mayorista_anterior;
    Double  porciento_minorista_anterior;
        
    public producto() {

    }

    public producto(String codigo, String nombre, pais paises, rubro rubro, marca marca, medida medida, stock stocks, familia familia, ubicacion ubicacion, proveedor proveedor, int cambiarprecio, BigDecimal costo, BigDecimal precio_maximo, BigDecimal precio_minimo, BigDecimal precioventa, BigDecimal stockminimo, BigDecimal incremento1, BigDecimal incremento2, BigDecimal incremento3, BigDecimal sugerido1, BigDecimal sugerido2, BigDecimal sugerido3, Date fechacompra, int tipo_producto, int conteomayorista, int kit, String codigobarra, Date fecha_ingreso, int estado, BigDecimal conversion, BigDecimal stockactual, BigDecimal stock, String observacion, Double stocksistema, BigDecimal ivaporcentaje, int verificado, Date fechahecho, Double compras, Double ventas, Double entrada, Double salida, Double transEntrada, Double transSalida, Double amperaje, Double voltios, Double largo, Double ancho, Double alto, plan ctadebe, Double precio_mayorista_anterior, Double precio_minorista_anterior, Double porciento_mayorista_anterior, Double porciento_minorista_anterior) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.paises = paises;
        this.rubro = rubro;
        this.marca = marca;
        this.medida = medida;
        this.stocks = stocks;
        this.familia = familia;
        this.ubicacion = ubicacion;
        this.proveedor = proveedor;
        this.cambiarprecio = cambiarprecio;
        this.costo = costo;
        this.precio_maximo = precio_maximo;
        this.precio_minimo = precio_minimo;
        this.precioventa = precioventa;
        this.stockminimo = stockminimo;
        this.incremento1 = incremento1;
        this.incremento2 = incremento2;
        this.incremento3 = incremento3;
        this.sugerido1 = sugerido1;
        this.sugerido2 = sugerido2;
        this.sugerido3 = sugerido3;
        this.fechacompra = fechacompra;
        this.tipo_producto = tipo_producto;
        this.conteomayorista = conteomayorista;
        this.kit = kit;
        this.codigobarra = codigobarra;
        this.fecha_ingreso = fecha_ingreso;
        this.estado = estado;
        this.conversion = conversion;
        this.stockactual = stockactual;
        this.stock = stock;
        this.observacion = observacion;
        this.stocksistema = stocksistema;
        this.ivaporcentaje = ivaporcentaje;
        this.verificado = verificado;
        this.fechahecho = fechahecho;
        this.compras = compras;
        this.ventas = ventas;
        this.entrada = entrada;
        this.salida = salida;
        this.transEntrada = transEntrada;
        this.transSalida = transSalida;
        this.amperaje = amperaje;
        this.voltios = voltios;
        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
        this.ctadebe = ctadebe;
        this.precio_mayorista_anterior = precio_mayorista_anterior;
        this.precio_minorista_anterior = precio_minorista_anterior;
        this.porciento_mayorista_anterior = porciento_mayorista_anterior;
        this.porciento_minorista_anterior = porciento_minorista_anterior;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public pais getPaises() {
        return paises;
    }

    public void setPaises(pais paises) {
        this.paises = paises;
    }

    public rubro getRubro() {
        return rubro;
    }

    public void setRubro(rubro rubro) {
        this.rubro = rubro;
    }

    public marca getMarca() {
        return marca;
    }

    public void setMarca(marca marca) {
        this.marca = marca;
    }

    public medida getMedida() {
        return medida;
    }

    public void setMedida(medida medida) {
        this.medida = medida;
    }

    public stock getStocks() {
        return stocks;
    }

    public void setStocks(stock stocks) {
        this.stocks = stocks;
    }

    public familia getFamilia() {
        return familia;
    }

    public void setFamilia(familia familia) {
        this.familia = familia;
    }

    public ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public int getCambiarprecio() {
        return cambiarprecio;
    }

    public void setCambiarprecio(int cambiarprecio) {
        this.cambiarprecio = cambiarprecio;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getPrecio_maximo() {
        return precio_maximo;
    }

    public void setPrecio_maximo(BigDecimal precio_maximo) {
        this.precio_maximo = precio_maximo;
    }

    public BigDecimal getPrecio_minimo() {
        return precio_minimo;
    }

    public void setPrecio_minimo(BigDecimal precio_minimo) {
        this.precio_minimo = precio_minimo;
    }

    public BigDecimal getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(BigDecimal precioventa) {
        this.precioventa = precioventa;
    }

    public BigDecimal getStockminimo() {
        return stockminimo;
    }

    public void setStockminimo(BigDecimal stockminimo) {
        this.stockminimo = stockminimo;
    }

    public BigDecimal getIncremento1() {
        return incremento1;
    }

    public void setIncremento1(BigDecimal incremento1) {
        this.incremento1 = incremento1;
    }

    public BigDecimal getIncremento2() {
        return incremento2;
    }

    public void setIncremento2(BigDecimal incremento2) {
        this.incremento2 = incremento2;
    }

    public BigDecimal getIncremento3() {
        return incremento3;
    }

    public void setIncremento3(BigDecimal incremento3) {
        this.incremento3 = incremento3;
    }

    public BigDecimal getSugerido1() {
        return sugerido1;
    }

    public void setSugerido1(BigDecimal sugerido1) {
        this.sugerido1 = sugerido1;
    }

    public BigDecimal getSugerido2() {
        return sugerido2;
    }

    public void setSugerido2(BigDecimal sugerido2) {
        this.sugerido2 = sugerido2;
    }

    public BigDecimal getSugerido3() {
        return sugerido3;
    }

    public void setSugerido3(BigDecimal sugerido3) {
        this.sugerido3 = sugerido3;
    }

    public Date getFechacompra() {
        return fechacompra;
    }

    public void setFechacompra(Date fechacompra) {
        this.fechacompra = fechacompra;
    }

    public int getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(int tipo_producto) {
        this.tipo_producto = tipo_producto;
    }

    public int getConteomayorista() {
        return conteomayorista;
    }

    public void setConteomayorista(int conteomayorista) {
        this.conteomayorista = conteomayorista;
    }

    public int getKit() {
        return kit;
    }

    public void setKit(int kit) {
        this.kit = kit;
    }

    public String getCodigobarra() {
        return codigobarra;
    }

    public void setCodigobarra(String codigobarra) {
        this.codigobarra = codigobarra;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public BigDecimal getConversion() {
        return conversion;
    }

    public void setConversion(BigDecimal conversion) {
        this.conversion = conversion;
    }

    public BigDecimal getStockactual() {
        return stockactual;
    }

    public void setStockactual(BigDecimal stockactual) {
        this.stockactual = stockactual;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getStocksistema() {
        return stocksistema;
    }

    public void setStocksistema(Double stocksistema) {
        this.stocksistema = stocksistema;
    }

    public BigDecimal getIvaporcentaje() {
        return ivaporcentaje;
    }

    public void setIvaporcentaje(BigDecimal ivaporcentaje) {
        this.ivaporcentaje = ivaporcentaje;
    }

    public int getVerificado() {
        return verificado;
    }

    public void setVerificado(int verificado) {
        this.verificado = verificado;
    }

    public Date getFechahecho() {
        return fechahecho;
    }

    public void setFechahecho(Date fechahecho) {
        this.fechahecho = fechahecho;
    }

    public Double getCompras() {
        return compras;
    }

    public void setCompras(Double compras) {
        this.compras = compras;
    }

    public Double getVentas() {
        return ventas;
    }

    public void setVentas(Double ventas) {
        this.ventas = ventas;
    }

    public Double getEntrada() {
        return entrada;
    }

    public void setEntrada(Double entrada) {
        this.entrada = entrada;
    }

    public Double getSalida() {
        return salida;
    }

    public void setSalida(Double salida) {
        this.salida = salida;
    }

    public Double getTransEntrada() {
        return transEntrada;
    }

    public void setTransEntrada(Double transEntrada) {
        this.transEntrada = transEntrada;
    }

    public Double getTransSalida() {
        return transSalida;
    }

    public void setTransSalida(Double transSalida) {
        this.transSalida = transSalida;
    }

    public Double getAmperaje() {
        return amperaje;
    }

    public void setAmperaje(Double amperaje) {
        this.amperaje = amperaje;
    }

    public Double getVoltios() {
        return voltios;
    }

    public void setVoltios(Double voltios) {
        this.voltios = voltios;
    }

    public Double getLargo() {
        return largo;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getAlto() {
        return alto;
    }

    public void setAlto(Double alto) {
        this.alto = alto;
    }

    public plan getCtadebe() {
        return ctadebe;
    }

    public void setCtadebe(plan ctadebe) {
        this.ctadebe = ctadebe;
    }

    public Double getPrecio_mayorista_anterior() {
        return precio_mayorista_anterior;
    }

    public void setPrecio_mayorista_anterior(Double precio_mayorista_anterior) {
        this.precio_mayorista_anterior = precio_mayorista_anterior;
    }

    public Double getPrecio_minorista_anterior() {
        return precio_minorista_anterior;
    }

    public void setPrecio_minorista_anterior(Double precio_minorista_anterior) {
        this.precio_minorista_anterior = precio_minorista_anterior;
    }

    public Double getPorciento_mayorista_anterior() {
        return porciento_mayorista_anterior;
    }

    public void setPorciento_mayorista_anterior(Double porciento_mayorista_anterior) {
        this.porciento_mayorista_anterior = porciento_mayorista_anterior;
    }

    public Double getPorciento_minorista_anterior() {
        return porciento_minorista_anterior;
    }

    public void setPorciento_minorista_anterior(Double porciento_minorista_anterior) {
        this.porciento_minorista_anterior = porciento_minorista_anterior;
    }

   
  
}

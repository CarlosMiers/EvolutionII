/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Pc_Server
 */
public class config_contable {

    String representante_legal;
    String contador;
    String ruc_contador;
    String ventacontado;
    String ventacredito;
    String ivacompra10;
    String ivacompra5;
    String ivaventa10;
    String ivaventa5;
    String resultado_anterior;
    String resultado_actual;
    String retencioncompra;
    String retencionventa;
    String ventaexenta;
    String compensacion_gs;
    String compensacion_usd;
    int periodocontable;
    int gastos;
    int compras;
    int pagos;
    int depositos;
    int extracciones;
    int fondofijo;
    int ventas;
    int cobranzas;
    String nombreventacontado;
    String nombreventacredito;
    String nombreventaexenta;
    String nombreivacompra10;
    String nombreivacompra5;
    String nombreivaventa10;
    String nombreivaventa5;
    String nombreresultadoanterior;
    String nombreresultadoactual;
    String nombreretencioncompra;
    String nombreretencionventa;
    String nombrecompensacion_gs;
    String nombrecompensacion_usd;

    public config_contable() {

    }

    public config_contable(String representante_legal, String contador, String ruc_contador, String ventacontado, String ventacredito, String ivacompra10, String ivacompra5, String ivaventa10, String ivaventa5, String resultado_anterior, String resultado_actual, String retencioncompra, String retencionventa, String ventaexenta, String compensacion_gs, String compensacion_usd, int periodocontable, int gastos, int compras, int pagos, int depositos, int extracciones, int fondofijo, int ventas, int cobranzas, String nombreventacontado, String nombreventacredito, String nombreventaexenta, String nombreivacompra10, String nombreivacompra5, String nombreivaventa10, String nombreivaventa5, String nombreresultadoanterior, String nombreresultadoactual, String nombreretencioncompra, String nombreretencionventa, String nombrecompensacion_gs, String nombrecompensacion_usd) {
        this.representante_legal = representante_legal;
        this.contador = contador;
        this.ruc_contador = ruc_contador;
        this.ventacontado = ventacontado;
        this.ventacredito = ventacredito;
        this.ivacompra10 = ivacompra10;
        this.ivacompra5 = ivacompra5;
        this.ivaventa10 = ivaventa10;
        this.ivaventa5 = ivaventa5;
        this.resultado_anterior = resultado_anterior;
        this.resultado_actual = resultado_actual;
        this.retencioncompra = retencioncompra;
        this.retencionventa = retencionventa;
        this.ventaexenta = ventaexenta;
        this.compensacion_gs = compensacion_gs;
        this.compensacion_usd = compensacion_usd;
        this.periodocontable = periodocontable;
        this.gastos = gastos;
        this.compras = compras;
        this.pagos = pagos;
        this.depositos = depositos;
        this.extracciones = extracciones;
        this.fondofijo = fondofijo;
        this.ventas = ventas;
        this.cobranzas = cobranzas;
        this.nombreventacontado = nombreventacontado;
        this.nombreventacredito = nombreventacredito;
        this.nombreventaexenta = nombreventaexenta;
        this.nombreivacompra10 = nombreivacompra10;
        this.nombreivacompra5 = nombreivacompra5;
        this.nombreivaventa10 = nombreivaventa10;
        this.nombreivaventa5 = nombreivaventa5;
        this.nombreresultadoanterior = nombreresultadoanterior;
        this.nombreresultadoactual = nombreresultadoactual;
        this.nombreretencioncompra = nombreretencioncompra;
        this.nombreretencionventa = nombreretencionventa;
        this.nombrecompensacion_gs = nombrecompensacion_gs;
        this.nombrecompensacion_usd = nombrecompensacion_usd;
    }

    public String getRepresentante_legal() {
        return representante_legal;
    }

    public void setRepresentante_legal(String representante_legal) {
        this.representante_legal = representante_legal;
    }

    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

    public String getRuc_contador() {
        return ruc_contador;
    }

    public void setRuc_contador(String ruc_contador) {
        this.ruc_contador = ruc_contador;
    }

    public String getVentacontado() {
        return ventacontado;
    }

    public void setVentacontado(String ventacontado) {
        this.ventacontado = ventacontado;
    }

    public String getVentacredito() {
        return ventacredito;
    }

    public void setVentacredito(String ventacredito) {
        this.ventacredito = ventacredito;
    }

    public String getIvacompra10() {
        return ivacompra10;
    }

    public void setIvacompra10(String ivacompra10) {
        this.ivacompra10 = ivacompra10;
    }

    public String getIvacompra5() {
        return ivacompra5;
    }

    public void setIvacompra5(String ivacompra5) {
        this.ivacompra5 = ivacompra5;
    }

    public String getIvaventa10() {
        return ivaventa10;
    }

    public void setIvaventa10(String ivaventa10) {
        this.ivaventa10 = ivaventa10;
    }

    public String getIvaventa5() {
        return ivaventa5;
    }

    public void setIvaventa5(String ivaventa5) {
        this.ivaventa5 = ivaventa5;
    }

    public String getResultado_anterior() {
        return resultado_anterior;
    }

    public void setResultado_anterior(String resultado_anterior) {
        this.resultado_anterior = resultado_anterior;
    }

    public String getResultado_actual() {
        return resultado_actual;
    }

    public void setResultado_actual(String resultado_actual) {
        this.resultado_actual = resultado_actual;
    }

    public String getRetencioncompra() {
        return retencioncompra;
    }

    public void setRetencioncompra(String retencioncompra) {
        this.retencioncompra = retencioncompra;
    }

    public String getRetencionventa() {
        return retencionventa;
    }

    public void setRetencionventa(String retencionventa) {
        this.retencionventa = retencionventa;
    }

    public String getVentaexenta() {
        return ventaexenta;
    }

    public void setVentaexenta(String ventaexenta) {
        this.ventaexenta = ventaexenta;
    }

    public String getCompensacion_gs() {
        return compensacion_gs;
    }

    public void setCompensacion_gs(String compensacion_gs) {
        this.compensacion_gs = compensacion_gs;
    }

    public String getCompensacion_usd() {
        return compensacion_usd;
    }

    public void setCompensacion_usd(String compensacion_usd) {
        this.compensacion_usd = compensacion_usd;
    }

    public int getPeriodocontable() {
        return periodocontable;
    }

    public void setPeriodocontable(int periodocontable) {
        this.periodocontable = periodocontable;
    }

    public int getGastos() {
        return gastos;
    }

    public void setGastos(int gastos) {
        this.gastos = gastos;
    }

    public int getCompras() {
        return compras;
    }

    public void setCompras(int compras) {
        this.compras = compras;
    }

    public int getPagos() {
        return pagos;
    }

    public void setPagos(int pagos) {
        this.pagos = pagos;
    }

    public int getDepositos() {
        return depositos;
    }

    public void setDepositos(int depositos) {
        this.depositos = depositos;
    }

    public int getExtracciones() {
        return extracciones;
    }

    public void setExtracciones(int extracciones) {
        this.extracciones = extracciones;
    }

    public int getFondofijo() {
        return fondofijo;
    }

    public void setFondofijo(int fondofijo) {
        this.fondofijo = fondofijo;
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public int getCobranzas() {
        return cobranzas;
    }

    public void setCobranzas(int cobranzas) {
        this.cobranzas = cobranzas;
    }

    public String getNombreventacontado() {
        return nombreventacontado;
    }

    public void setNombreventacontado(String nombreventacontado) {
        this.nombreventacontado = nombreventacontado;
    }

    public String getNombreventacredito() {
        return nombreventacredito;
    }

    public void setNombreventacredito(String nombreventacredito) {
        this.nombreventacredito = nombreventacredito;
    }

    public String getNombreventaexenta() {
        return nombreventaexenta;
    }

    public void setNombreventaexenta(String nombreventaexenta) {
        this.nombreventaexenta = nombreventaexenta;
    }

    public String getNombreivacompra10() {
        return nombreivacompra10;
    }

    public void setNombreivacompra10(String nombreivacompra10) {
        this.nombreivacompra10 = nombreivacompra10;
    }

    public String getNombreivacompra5() {
        return nombreivacompra5;
    }

    public void setNombreivacompra5(String nombreivacompra5) {
        this.nombreivacompra5 = nombreivacompra5;
    }

    public String getNombreivaventa10() {
        return nombreivaventa10;
    }

    public void setNombreivaventa10(String nombreivaventa10) {
        this.nombreivaventa10 = nombreivaventa10;
    }

    public String getNombreivaventa5() {
        return nombreivaventa5;
    }

    public void setNombreivaventa5(String nombreivaventa5) {
        this.nombreivaventa5 = nombreivaventa5;
    }

    public String getNombreresultadoanterior() {
        return nombreresultadoanterior;
    }

    public void setNombreresultadoanterior(String nombreresultadoanterior) {
        this.nombreresultadoanterior = nombreresultadoanterior;
    }

    public String getNombreresultadoactual() {
        return nombreresultadoactual;
    }

    public void setNombreresultadoactual(String nombreresultadoactual) {
        this.nombreresultadoactual = nombreresultadoactual;
    }

    public String getNombreretencioncompra() {
        return nombreretencioncompra;
    }

    public void setNombreretencioncompra(String nombreretencioncompra) {
        this.nombreretencioncompra = nombreretencioncompra;
    }

    public String getNombreretencionventa() {
        return nombreretencionventa;
    }

    public void setNombreretencionventa(String nombreretencionventa) {
        this.nombreretencionventa = nombreretencionventa;
    }

    public String getNombrecompensacion_gs() {
        return nombrecompensacion_gs;
    }

    public void setNombrecompensacion_gs(String nombrecompensacion_gs) {
        this.nombrecompensacion_gs = nombrecompensacion_gs;
    }

    public String getNombrecompensacion_usd() {
        return nombrecompensacion_usd;
    }

    public void setNombrecompensacion_usd(String nombrecompensacion_usd) {
        this.nombrecompensacion_usd = nombrecompensacion_usd;
    }


    
}

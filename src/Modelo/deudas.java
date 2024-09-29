package Modelo;

import java.sql.Date;
import org.json.simple.JSONObject;

public class deudas {

    String cedula_ruc;
    int cliente;
    String nombre_apellido;
    Double numero_operacion;
    String descripcion_operacion;
    String numero_factura;
    int nro_cuota;
    String moneda;
    Double monto;
    String num_boleta;
    Date fecha_venc;
    String estado;
    Date fecha_pago;
    Double monto_pagado;
    String iddocumento;
    Double gastos;
    Double mora;
    String ultimo_pago;
    int tipoop;
    String creferencia;
    Double punitorio;

    public deudas() {

    }

    public deudas(String cedula_ruc, int cliente, String nombre_apellido, Double numero_operacion, String descripcion_operacion, String numero_factura, int nro_cuota, String moneda, Double monto, String num_boleta, Date fecha_venc, String estado, Date fecha_pago, Double monto_pagado, String iddocumento, Double gastos, Double mora, String ultimo_pago, int tipoop, String creferencia, Double punitorio) {
        this.cedula_ruc = cedula_ruc;
        this.cliente = cliente;
        this.nombre_apellido = nombre_apellido;
        this.numero_operacion = numero_operacion;
        this.descripcion_operacion = descripcion_operacion;
        this.numero_factura = numero_factura;
        this.nro_cuota = nro_cuota;
        this.moneda = moneda;
        this.monto = monto;
        this.num_boleta = num_boleta;
        this.fecha_venc = fecha_venc;
        this.estado = estado;
        this.fecha_pago = fecha_pago;
        this.monto_pagado = monto_pagado;
        this.iddocumento = iddocumento;
        this.gastos = gastos;
        this.mora = mora;
        this.ultimo_pago = ultimo_pago;
        this.tipoop = tipoop;
        this.creferencia = creferencia;
        this.punitorio = punitorio;
    }

    public String getCedula_ruc() {
        return cedula_ruc;
    }

    public void setCedula_ruc(String cedula_ruc) {
        this.cedula_ruc = cedula_ruc;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getNombre_apellido() {
        return nombre_apellido;
    }

    public void setNombre_apellido(String nombre_apellido) {
        this.nombre_apellido = nombre_apellido;
    }

    public Double getNumero_operacion() {
        return numero_operacion;
    }

    public void setNumero_operacion(Double numero_operacion) {
        this.numero_operacion = numero_operacion;
    }

    public String getDescripcion_operacion() {
        return descripcion_operacion;
    }

    public void setDescripcion_operacion(String descripcion_operacion) {
        this.descripcion_operacion = descripcion_operacion;
    }

    public String getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(String numero_factura) {
        this.numero_factura = numero_factura;
    }

    public int getNro_cuota() {
        return nro_cuota;
    }

    public void setNro_cuota(int nro_cuota) {
        this.nro_cuota = nro_cuota;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getNum_boleta() {
        return num_boleta;
    }

    public void setNum_boleta(String num_boleta) {
        this.num_boleta = num_boleta;
    }

    public Date getFecha_venc() {
        return fecha_venc;
    }

    public void setFecha_venc(Date fecha_venc) {
        this.fecha_venc = fecha_venc;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public Double getMonto_pagado() {
        return monto_pagado;
    }

    public void setMonto_pagado(Double monto_pagado) {
        this.monto_pagado = monto_pagado;
    }

    public String getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(String iddocumento) {
        this.iddocumento = iddocumento;
    }

    public Double getGastos() {
        return gastos;
    }

    public void setGastos(Double gastos) {
        this.gastos = gastos;
    }

    public Double getMora() {
        return mora;
    }

    public void setMora(Double mora) {
        this.mora = mora;
    }

    public String getUltimo_pago() {
        return ultimo_pago;
    }

    public void setUltimo_pago(String ultimo_pago) {
        this.ultimo_pago = ultimo_pago;
    }

    public int getTipoop() {
        return tipoop;
    }

    public void setTipoop(int tipoop) {
        this.tipoop = tipoop;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public Double getPunitorio() {
        return punitorio;
    }

    public void setPunitorio(Double punitorio) {
        this.punitorio = punitorio;
    }


    
    
    
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("cedula_ruc", this.cedula_ruc);
        obj.put("cliente", this.cliente);
        obj.put("nombre_apellido", this.nombre_apellido);
        obj.put("numero_operacion", this.numero_operacion);
        obj.put("descripcion_operacion", this.descripcion_operacion);
        obj.put("numero_factura", this.numero_factura);
        obj.put("nro_cuota", this.nro_cuota);
        obj.put("moneda", this.moneda);
        obj.put("monto", this.monto);
        obj.put("num_boleta", this.num_boleta);
        obj.put("fecha_venc", this.fecha_venc);
        obj.put("estado", this.estado);
        obj.put("fecha_pago", this.fecha_pago);
        obj.put("monto_pagado", this.monto_pagado);
        obj.put("iddocumento", this.iddocumento);
        obj.put("gastos", this.gastos);
        obj.put("mora", this.mora);
        obj.put("ultimo_pago", this.ultimo_pago);
        obj.put("tipodoc", this.tipoop);
        obj.put("creferencia", this.creferencia);
        obj.put("punitorio", this.punitorio);
        return obj;
    }

}

package Modelo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class solicitud_locacion_juridica {

    int idcontrato;
    int idcliente;
    cliente cliente;
   
    public solicitud_locacion_juridica() {

    }

    public solicitud_locacion_juridica(int idcontrato, int idcliente, cliente cliente) {
        this.idcontrato = idcontrato;
        this.idcliente = idcliente;
        this.cliente = cliente;
    }

    public int getIdcontrato() {
        return idcontrato;
    }

    public void setIdcontrato(int idcontrato) {
        this.idcontrato = idcontrato;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

   

   
   
}

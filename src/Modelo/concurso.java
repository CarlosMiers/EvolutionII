/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Pc_Server
 */
public class concurso {

    Double idconcurso;
    Date fecha;
    Date limitedesde;
    Date limitehasta;
    vacancias idvacancia;
    int tipo_concurso;
    cargo perfil;
    
    public concurso(){
        
    }

    public concurso(Double idconcurso, Date fecha, Date limitedesde, Date limitehasta, vacancias idvacancia, int tipo_concurso, cargo perfil) {
        this.idconcurso = idconcurso;
        this.fecha = fecha;
        this.limitedesde = limitedesde;
        this.limitehasta = limitehasta;
        this.idvacancia = idvacancia;
        this.tipo_concurso = tipo_concurso;
        this.perfil = perfil;
    }

    public Double getIdconcurso() {
        return idconcurso;
    }

    public void setIdconcurso(Double idconcurso) {
        this.idconcurso = idconcurso;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getLimitedesde() {
        return limitedesde;
    }

    public void setLimitedesde(Date limitedesde) {
        this.limitedesde = limitedesde;
    }

    public Date getLimitehasta() {
        return limitehasta;
    }

    public void setLimitehasta(Date limitehasta) {
        this.limitehasta = limitehasta;
    }

    public vacancias getIdvacancia() {
        return idvacancia;
    }

    public void setIdvacancia(vacancias idvacancia) {
        this.idvacancia = idvacancia;
    }

    public int getTipo_concurso() {
        return tipo_concurso;
    }

    public void setTipo_concurso(int tipo_concurso) {
        this.tipo_concurso = tipo_concurso;
    }

    public cargo getPerfil() {
        return perfil;
    }

    public void setPerfil(cargo perfil) {
        this.perfil = perfil;
    }

    
}

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
 * @author Pc_Server
 */
public class edificio {

    int idunidad;
    inmueble inmueble;
    tipo_inmueble tipinmueble;
    cliente cliente;
    propietario propietario;
    String nombrepropie;
    moneda moneda;
    String ctactral;
    int unidad;
    String nrodoc;
    int tipdoc; //
    String ubicacion;
    String telunid;
    int proptel; //
    String medande; // MEDIDOR ANDE
    String medcorpo; // MEDIDOR CORPOSANA
    String subcuentacorpo; //Medidor Corposana
    String nir; //NIR ANDE
    String nis; // NIS ANDE
    String scta; // Sub Cta
    BigDecimal depgtia; //DEPOSITO DE GARANTIA
    BigDecimal alquiler; //IMPORTE ALQUILER
    BigDecimal garaje;// ALQUILER GARAGE
    BigDecimal expensa; // EXPENSA FIJA
    BigDecimal aire; //AIRE
    BigDecimal fondoreserva;
    BigDecimal llave;
    BigDecimal indemniz; // INDEMIZACION
    BigDecimal comision; //COMISION
    int tipunid; //TIPO DE UNIDAD 1=LOCAL COMERCIAL 2=VIVIENDA
    String prorexp; //PRORRATEAR EXPENSAR
    int destino1;
    int destino2;
    String dist1;
    String dist2;
    String dist3;
    String dist4;
    String nrogaraj; //NRO DE GARAJE
    int diapago; // DIA DE PAGO
    cobrador cobrador; // COBRADOR
    BigDecimal multa; //MULTA POR DIA
    BigDecimal mmalqui;
    BigDecimal vgarantia; //DEPOSITO POR GARANTIA
    Date vencon; //VENCIMIENTO CONTRATO
    Date inicon; //INICIO CONTRATO
    Date fecocup; // FECHA DE OCUPACION
    Date finiexp;// FECHA INICIO DE EXPENSAS
    Date ffinexp;//FECHA FIN DE EXPENSAS
    int coddepgara;//DEPOSITO EN GARANTIA
    int controlc;//
    int codcomi;
    String nuevo;
    String usuario;
    Date fecalta;//FECHA ALTA
    Date fecmodi;//FECHA MODIFICACION
    String ndocgara;//NRO DE GARAGE
    int tipdocga;
    String contrato;
    String contvenc;
    BigDecimal contrat;
    String ivauni;//IVA SOBRE ALQUILER
    String observaciones; //OBSERVACIONES
    String ambientes;
    int estado;
    int agua;
    int enerelectrica;
    int recolebasura;
    BigDecimal ocasionales;
    int comproventa;
    String superftotal;
    String superfedif;

    public edificio() {

    }

    public edificio(int idunidad, inmueble inmueble, tipo_inmueble tipinmueble, cliente cliente, propietario propietario, String nombrepropie, moneda moneda, String ctactral, int unidad, String nrodoc, int tipdoc, String ubicacion, String telunid, int proptel, String medande, String medcorpo, String subcuentacorpo, String nir, String nis, String scta, BigDecimal depgtia, BigDecimal alquiler, BigDecimal garaje, BigDecimal expensa, BigDecimal aire, BigDecimal fondoreserva, BigDecimal llave, BigDecimal indemniz, BigDecimal comision, int tipunid, String prorexp, int destino1, int destino2, String dist1, String dist2, String dist3, String dist4, String nrogaraj, int diapago, cobrador cobrador, BigDecimal multa, BigDecimal mmalqui, BigDecimal vgarantia, Date vencon, Date inicon, Date fecocup, Date finiexp, Date ffinexp, int coddepgara, int controlc, int codcomi, String nuevo, String usuario, Date fecalta, Date fecmodi, String ndocgara, int tipdocga, String contrato, String contvenc, BigDecimal contrat, String ivauni, String observaciones, String ambientes, int estado, int agua, int enerelectrica, int recolebasura, BigDecimal ocasionales, int comproventa, String superftotal, String superfedif) {
        this.idunidad = idunidad;
        this.inmueble = inmueble;
        this.tipinmueble = tipinmueble;
        this.cliente = cliente;
        this.propietario = propietario;
        this.nombrepropie = nombrepropie;
        this.moneda = moneda;
        this.ctactral = ctactral;
        this.unidad = unidad;
        this.nrodoc = nrodoc;
        this.tipdoc = tipdoc;
        this.ubicacion = ubicacion;
        this.telunid = telunid;
        this.proptel = proptel;
        this.medande = medande;
        this.medcorpo = medcorpo;
        this.subcuentacorpo = subcuentacorpo;
        this.nir = nir;
        this.nis = nis;
        this.scta = scta;
        this.depgtia = depgtia;
        this.alquiler = alquiler;
        this.garaje = garaje;
        this.expensa = expensa;
        this.aire = aire;
        this.fondoreserva = fondoreserva;
        this.llave = llave;
        this.indemniz = indemniz;
        this.comision = comision;
        this.tipunid = tipunid;
        this.prorexp = prorexp;
        this.destino1 = destino1;
        this.destino2 = destino2;
        this.dist1 = dist1;
        this.dist2 = dist2;
        this.dist3 = dist3;
        this.dist4 = dist4;
        this.nrogaraj = nrogaraj;
        this.diapago = diapago;
        this.cobrador = cobrador;
        this.multa = multa;
        this.mmalqui = mmalqui;
        this.vgarantia = vgarantia;
        this.vencon = vencon;
        this.inicon = inicon;
        this.fecocup = fecocup;
        this.finiexp = finiexp;
        this.ffinexp = ffinexp;
        this.coddepgara = coddepgara;
        this.controlc = controlc;
        this.codcomi = codcomi;
        this.nuevo = nuevo;
        this.usuario = usuario;
        this.fecalta = fecalta;
        this.fecmodi = fecmodi;
        this.ndocgara = ndocgara;
        this.tipdocga = tipdocga;
        this.contrato = contrato;
        this.contvenc = contvenc;
        this.contrat = contrat;
        this.ivauni = ivauni;
        this.observaciones = observaciones;
        this.ambientes = ambientes;
        this.estado = estado;
        this.agua = agua;
        this.enerelectrica = enerelectrica;
        this.recolebasura = recolebasura;
        this.ocasionales = ocasionales;
        this.comproventa = comproventa;
        this.superftotal = superftotal;
        this.superfedif = superfedif;
    }

    public int getIdunidad() {
        return idunidad;
    }

    public void setIdunidad(int idunidad) {
        this.idunidad = idunidad;
    }

    public inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public tipo_inmueble getTipinmueble() {
        return tipinmueble;
    }

    public void setTipinmueble(tipo_inmueble tipinmueble) {
        this.tipinmueble = tipinmueble;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(propietario propietario) {
        this.propietario = propietario;
    }

    public String getNombrepropie() {
        return nombrepropie;
    }

    public void setNombrepropie(String nombrepropie) {
        this.nombrepropie = nombrepropie;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public String getCtactral() {
        return ctactral;
    }

    public void setCtactral(String ctactral) {
        this.ctactral = ctactral;
    }

    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public String getNrodoc() {
        return nrodoc;
    }

    public void setNrodoc(String nrodoc) {
        this.nrodoc = nrodoc;
    }

    public int getTipdoc() {
        return tipdoc;
    }

    public void setTipdoc(int tipdoc) {
        this.tipdoc = tipdoc;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTelunid() {
        return telunid;
    }

    public void setTelunid(String telunid) {
        this.telunid = telunid;
    }

    public int getProptel() {
        return proptel;
    }

    public void setProptel(int proptel) {
        this.proptel = proptel;
    }

    public String getMedande() {
        return medande;
    }

    public void setMedande(String medande) {
        this.medande = medande;
    }

    public String getMedcorpo() {
        return medcorpo;
    }

    public void setMedcorpo(String medcorpo) {
        this.medcorpo = medcorpo;
    }

    public String getSubcuentacorpo() {
        return subcuentacorpo;
    }

    public void setSubcuentacorpo(String subcuentacorpo) {
        this.subcuentacorpo = subcuentacorpo;
    }

    public String getNir() {
        return nir;
    }

    public void setNir(String nir) {
        this.nir = nir;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getScta() {
        return scta;
    }

    public void setScta(String scta) {
        this.scta = scta;
    }

    public BigDecimal getDepgtia() {
        return depgtia;
    }

    public void setDepgtia(BigDecimal depgtia) {
        this.depgtia = depgtia;
    }

    public BigDecimal getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(BigDecimal alquiler) {
        this.alquiler = alquiler;
    }

    public BigDecimal getGaraje() {
        return garaje;
    }

    public void setGaraje(BigDecimal garaje) {
        this.garaje = garaje;
    }

    public BigDecimal getExpensa() {
        return expensa;
    }

    public void setExpensa(BigDecimal expensa) {
        this.expensa = expensa;
    }

    public BigDecimal getAire() {
        return aire;
    }

    public void setAire(BigDecimal aire) {
        this.aire = aire;
    }

    public BigDecimal getFondoreserva() {
        return fondoreserva;
    }

    public void setFondoreserva(BigDecimal fondoreserva) {
        this.fondoreserva = fondoreserva;
    }

    public BigDecimal getLlave() {
        return llave;
    }

    public void setLlave(BigDecimal llave) {
        this.llave = llave;
    }

    public BigDecimal getIndemniz() {
        return indemniz;
    }

    public void setIndemniz(BigDecimal indemniz) {
        this.indemniz = indemniz;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public int getTipunid() {
        return tipunid;
    }

    public void setTipunid(int tipunid) {
        this.tipunid = tipunid;
    }

    public String getProrexp() {
        return prorexp;
    }

    public void setProrexp(String prorexp) {
        this.prorexp = prorexp;
    }

    public int getDestino1() {
        return destino1;
    }

    public void setDestino1(int destino1) {
        this.destino1 = destino1;
    }

    public int getDestino2() {
        return destino2;
    }

    public void setDestino2(int destino2) {
        this.destino2 = destino2;
    }

    public String getDist1() {
        return dist1;
    }

    public void setDist1(String dist1) {
        this.dist1 = dist1;
    }

    public String getDist2() {
        return dist2;
    }

    public void setDist2(String dist2) {
        this.dist2 = dist2;
    }

    public String getDist3() {
        return dist3;
    }

    public void setDist3(String dist3) {
        this.dist3 = dist3;
    }

    public String getDist4() {
        return dist4;
    }

    public void setDist4(String dist4) {
        this.dist4 = dist4;
    }

    public String getNrogaraj() {
        return nrogaraj;
    }

    public void setNrogaraj(String nrogaraj) {
        this.nrogaraj = nrogaraj;
    }

    public int getDiapago() {
        return diapago;
    }

    public void setDiapago(int diapago) {
        this.diapago = diapago;
    }

    public cobrador getCobrador() {
        return cobrador;
    }

    public void setCobrador(cobrador cobrador) {
        this.cobrador = cobrador;
    }

    public BigDecimal getMulta() {
        return multa;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public BigDecimal getMmalqui() {
        return mmalqui;
    }

    public void setMmalqui(BigDecimal mmalqui) {
        this.mmalqui = mmalqui;
    }

    public BigDecimal getVgarantia() {
        return vgarantia;
    }

    public void setVgarantia(BigDecimal vgarantia) {
        this.vgarantia = vgarantia;
    }

    public Date getVencon() {
        return vencon;
    }

    public void setVencon(Date vencon) {
        this.vencon = vencon;
    }

    public Date getInicon() {
        return inicon;
    }

    public void setInicon(Date inicon) {
        this.inicon = inicon;
    }

    public Date getFecocup() {
        return fecocup;
    }

    public void setFecocup(Date fecocup) {
        this.fecocup = fecocup;
    }

    public Date getFiniexp() {
        return finiexp;
    }

    public void setFiniexp(Date finiexp) {
        this.finiexp = finiexp;
    }

    public Date getFfinexp() {
        return ffinexp;
    }

    public void setFfinexp(Date ffinexp) {
        this.ffinexp = ffinexp;
    }

    public int getCoddepgara() {
        return coddepgara;
    }

    public void setCoddepgara(int coddepgara) {
        this.coddepgara = coddepgara;
    }

    public int getControlc() {
        return controlc;
    }

    public void setControlc(int controlc) {
        this.controlc = controlc;
    }

    public int getCodcomi() {
        return codcomi;
    }

    public void setCodcomi(int codcomi) {
        this.codcomi = codcomi;
    }

    public String getNuevo() {
        return nuevo;
    }

    public void setNuevo(String nuevo) {
        this.nuevo = nuevo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFecalta() {
        return fecalta;
    }

    public void setFecalta(Date fecalta) {
        this.fecalta = fecalta;
    }

    public Date getFecmodi() {
        return fecmodi;
    }

    public void setFecmodi(Date fecmodi) {
        this.fecmodi = fecmodi;
    }

    public String getNdocgara() {
        return ndocgara;
    }

    public void setNdocgara(String ndocgara) {
        this.ndocgara = ndocgara;
    }

    public int getTipdocga() {
        return tipdocga;
    }

    public void setTipdocga(int tipdocga) {
        this.tipdocga = tipdocga;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getContvenc() {
        return contvenc;
    }

    public void setContvenc(String contvenc) {
        this.contvenc = contvenc;
    }

    public BigDecimal getContrat() {
        return contrat;
    }

    public void setContrat(BigDecimal contrat) {
        this.contrat = contrat;
    }

    public String getIvauni() {
        return ivauni;
    }

    public void setIvauni(String ivauni) {
        this.ivauni = ivauni;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(String ambientes) {
        this.ambientes = ambientes;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getAgua() {
        return agua;
    }

    public void setAgua(int agua) {
        this.agua = agua;
    }

    public int getEnerelectrica() {
        return enerelectrica;
    }

    public void setEnerelectrica(int enerelectrica) {
        this.enerelectrica = enerelectrica;
    }

    public int getRecolebasura() {
        return recolebasura;
    }

    public void setRecolebasura(int recolebasura) {
        this.recolebasura = recolebasura;
    }

    public BigDecimal getOcasionales() {
        return ocasionales;
    }

    public void setOcasionales(BigDecimal ocasionales) {
        this.ocasionales = ocasionales;
    }

    public int getComproventa() {
        return comproventa;
    }

    public void setComproventa(int comproventa) {
        this.comproventa = comproventa;
    }

    public String getSuperftotal() {
        return superftotal;
    }

    public void setSuperftotal(String superftotal) {
        this.superftotal = superftotal;
    }

    public String getSuperfedif() {
        return superfedif;
    }

    public void setSuperfedif(String superfedif) {
        this.superfedif = superfedif;
    }

   
  
}

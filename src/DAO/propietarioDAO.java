/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.propietario;
import Modelo.tipo_documento;
import Modelo.pais;
import Modelo.vendedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
/**
 *
 * @author Usuario
 */
public class propietarioDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<propietario> todos() throws SQLException {
        ArrayList<propietario> lista = new ArrayList<propietario>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT propietarios.codpro,propietarios.nombre,propietarios.apellido,CONCAT(propietarios.nombre,' ', propietarios.apellido) AS nombreprop,propietarios.tipodoc,"
                + "propietarios.cedula,propietarios.ruc,propietarios.fechanac,propietarios.dirparticular,propietarios.teleparticular,"
                + "propietarios.dirlaboral,propietarios.telelaboral,propietarios.estadocivil,propietarios.email,"
                + "propietarios.estado,propietarios.observacion,propietarios.nacionalidad,paises.nombre as nombrepais,propietarios.porchon,propietarios.honmulta,"
                + "propietarios.tipliq,propietarios.cliqexp,propietarios.desmul,propietarios.pormul,propietarios.porcrete,propietarios.iva,"
                + "propietarios.ivaexp,propietarios.destiva,propietarios.honalq,propietarios.hondep,propietarios.honindem,propietarios.hongaraj,"
                + "propietarios.honexp,propietarios.honllave,propietarios.cliqdep,propietarios.ctipliq,propietarios.factura,propietarios.recibo,"
                + "tipo_documento.nombre AS nombredocumento,propietarios.codger,vendedores.nombre as nombrevendedor "
                + "FROM propietarios "
                + "LEFT JOIN tipo_documento "
                + "ON tipo_documento.codigo=propietarios.tipodoc "
                + "LEFT JOIN paises "
                + "ON paises.codigo=propietarios.nacionalidad "
                + "LEFT JOIN vendedores "
                + " ON vendedores.codigo=propietarios.codger "
                + "ORDER BY propietarios.codpro ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                propietario g = new propietario();
                tipo_documento t = new tipo_documento();
                pais p = new pais();
                vendedor v = new vendedor();
                g.setTipodoc(t);
                g.setNacionalidad(p);
                g.setCodger(v);
                g.setCodpro(rs.getInt("codpro"));
                g.setNombre(rs.getString("nombre"));
                g.setApellido(rs.getString("apellido"));
                g.setNombreprop(rs.getString("nombreprop"));

                g.getTipodoc().setCodigo(rs.getInt("tipodoc"));
                g.getTipodoc().setNombre(rs.getString("nombredocumento"));
                g.getNacionalidad().setCodigo(rs.getInt("nacionalidad"));
                g.getNacionalidad().setNombre(rs.getString("nombrepais"));
                g.getCodger().setCodigo(rs.getInt("codger"));
                g.getCodger().setNombre(rs.getString("nombrevendedor"));

                g.setRuc(rs.getString("ruc"));
                g.setCedula(rs.getString("cedula"));
                g.setEstadocivil(rs.getString("estadocivil"));
                g.setFechanac(rs.getDate("fechanac"));
                g.setDirparticular(rs.getString("dirparticular"));
                g.setTeleparticular(rs.getString("teleparticular"));
                g.setDirlaboral(rs.getString("dirlaboral"));
                g.setTelelaboral(rs.getString("telelaboral"));
                g.setEmail(rs.getString("email"));
                g.setObservacion(rs.getString("observacion"));
                g.setEstado(rs.getInt("estado"));
                g.setPorchon(rs.getDouble("porchon"));
                g.setCliqexp(rs.getInt("cliqexp"));
                g.setDesmul(rs.getInt("desmul"));
                g.setPormul(rs.getDouble("pormul"));
                g.setPorcrete(rs.getDouble("porcrete"));
                g.setIva(rs.getDouble("iva"));
                g.setIvaexp(rs.getDouble("ivaexp"));
                g.setDestiva(rs.getInt("destiva"));
                g.setHonalq(rs.getString("honalq"));
                g.setHondep(rs.getString("hondep"));
                g.setHonmulta(rs.getString("honmulta"));
                g.setHonindem(rs.getString("honindem"));
                g.setHongaraj(rs.getString("hongaraj"));
                g.setHonexp(rs.getString("honexp"));
                g.setHonllave(rs.getString("honllave"));
                g.setCliqdep(rs.getInt("cliqdep"));
                g.setCtipliq(rs.getInt("ctipliq"));
                g.setRecibo(rs.getString("recibo"));
                g.setFactura(rs.getString("factura"));
                lista.add(g);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<propietario> todosconcatenado() throws SQLException {
        ArrayList<propietario> lista = new ArrayList<propietario>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT codpro,propietarios.nombre,apellido,CONCAT(nombre,' ',apellido) AS nombrepropietario,tipodoc,ruc,fechanac,dirparticular,teleparticular,"
                + "dirlaboral,telelaboral,estadocivil,email,estado,observacion,"
                + "tipo_documento.nombre AS nombredocumento "
                + "FROM propietarios "
                + "LEFT JOIN tipo_documento "
                + "ON tipo_documento.codigo=propietarios.tipodoc "
                + "ORDER BY codpro ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                propietario g = new propietario();
                tipo_documento t = new tipo_documento();
                g.setTipodoc(t);

                g.setCodpro(rs.getInt("codpro"));
                g.setNombre(rs.getString("nombrepropietario"));
                g.setApellido(rs.getString("apellido"));
                g.getTipodoc().setCodigo(rs.getInt("tipodoc"));
                g.getTipodoc().setNombre(rs.getString("nombredocumento"));
                g.setRuc(rs.getString("ruc"));
                g.setEstadocivil(rs.getString("estadocivil"));
                g.setFechanac(rs.getDate("fechanac"));
                g.setDirparticular(rs.getString("dirparticular"));
                g.setTeleparticular(rs.getString("teleparticular"));
                g.setDirlaboral(rs.getString("dirlaboral"));
                g.setTelelaboral(rs.getString("telelaboral"));
                g.setEmail(rs.getString("email"));
                g.setObservacion(rs.getString("observacion"));
                g.setEstado(rs.getInt("estado"));
                lista.add(g);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public propietario buscarId(int id) throws SQLException {
        propietario g = new propietario();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT propietarios.codpro,propietarios.nombre,propietarios.apellido,CONCAT(propietarios.nombre,' ', propietarios.apellido) AS nombreprop,propietarios.tipodoc,"
                    + "propietarios.cedula,propietarios.ruc,propietarios.fechanac,propietarios.dirparticular,propietarios.teleparticular,"
                    + "propietarios.dirlaboral,propietarios.telelaboral,propietarios.estadocivil,propietarios.email,"
                    + "propietarios.estado,propietarios.observacion,propietarios.nacionalidad,paises.nombre as nombrepais,propietarios.porchon,propietarios.honmulta,"
                    + "propietarios.tipliq,propietarios.cliqexp,propietarios.desmul,propietarios.pormul,propietarios.porcrete,propietarios.iva,"
                    + "propietarios.ivaexp,propietarios.destiva,propietarios.honalq,propietarios.hondep,propietarios.honindem,propietarios.hongaraj,"
                    + "propietarios.honexp,propietarios.honllave,propietarios.cliqdep,propietarios.ctipliq,propietarios.factura,propietarios.recibo,"
                    + "tipo_documento.nombre AS nombredocumento,propietarios.codger,vendedores.nombre as nombrevendedor "
                    + "FROM propietarios "
                    + "LEFT JOIN tipo_documento "
                    + "ON tipo_documento.codigo=propietarios.tipodoc "
                    + "LEFT JOIN paises "
                    + " ON paises.codigo=propietarios.nacionalidad "
                    + "LEFT JOIN vendedores "
                    + " ON vendedores.codigo=propietarios.codger "
                    + " WHERE propietarios.codpro=? "
                    + " ORDER BY propietarios.codpro ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    tipo_documento t = new tipo_documento();
                    pais p = new pais();
                    vendedor v = new vendedor();
                    g.setTipodoc(t);
                    g.setNacionalidad(p);
                    g.setCodger(v);

                    g.setCodpro(rs.getInt("codpro"));
                    g.setNombre(rs.getString("nombre"));
                    g.setApellido(rs.getString("apellido"));
                    g.setNombreprop(rs.getString("nombreprop"));

                    g.getCodger().setCodigo(rs.getInt("codger"));
                    g.getCodger().setNombre(rs.getString("nombrevendedor"));
                    g.getTipodoc().setCodigo(rs.getInt("tipodoc"));
                    g.getTipodoc().setNombre(rs.getString("nombredocumento"));
                    g.getNacionalidad().setCodigo(rs.getInt("nacionalidad"));
                    g.getNacionalidad().setNombre(rs.getString("nombrepais"));

                    g.setRuc(rs.getString("ruc"));
                    g.setCedula(rs.getString("cedula"));
                    g.setEstadocivil(rs.getString("estadocivil"));
                    g.setFechanac(rs.getDate("fechanac"));
                    g.setDirparticular(rs.getString("dirparticular"));
                    g.setTeleparticular(rs.getString("teleparticular"));
                    g.setDirlaboral(rs.getString("dirlaboral"));
                    g.setTelelaboral(rs.getString("telelaboral"));
                    g.setEmail(rs.getString("email"));
                    g.setObservacion(rs.getString("observacion"));
                    g.setEstado(rs.getInt("estado"));
                    g.setPorchon(rs.getDouble("porchon"));
                    g.setCliqexp(rs.getInt("cliqexp"));
                    g.setDesmul(rs.getInt("desmul"));
                    g.setPormul(rs.getDouble("pormul"));
                    g.setPorcrete(rs.getDouble("porcrete"));
                    g.setIva(rs.getDouble("iva"));
                    g.setIvaexp(rs.getDouble("ivaexp"));
                    g.setDestiva(rs.getInt("destiva"));
                    g.setHonalq(rs.getString("honalq"));
                    g.setHondep(rs.getString("hondep"));
                    g.setHonmulta(rs.getString("honmulta"));
                    g.setHonindem(rs.getString("honindem"));
                    g.setHongaraj(rs.getString("hongaraj"));
                    g.setHonexp(rs.getString("honexp"));
                    g.setHonllave(rs.getString("honllave"));
                    g.setCtipliq(rs.getInt("ctipliq"));
                    g.setCliqdep(rs.getInt("cliqdep"));
                    g.setRecibo(rs.getString("recibo"));
                    g.setFactura(rs.getString("factura"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return g;
    }

    public propietario insertarPropietario(propietario g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO propietarios (nombre,apellido,ruc,tipodoc,fechanac,dirparticular,teleparticular,dirlaboral,telelaboral,estadocivil,email,"
                + "estado,observacion,cedula,nacionalidad,"
                + "porchon,desmul,honalq,hondep,hongaraj,honindem,honllave,honmulta,honexp,codger,cliqdep,pormul,porcrete) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, g.getNombre());
        ps.setString(2, g.getApellido());
        ps.setString(3, g.getRuc());
        ps.setInt(4, g.getTipodoc().getCodigo());
        ps.setDate(5, g.getFechanac());
        ps.setString(6, g.getDirparticular());
        ps.setString(7, g.getTeleparticular());
        ps.setString(8, g.getDirlaboral());
        ps.setString(9, g.getTelelaboral());
        ps.setString(10, g.getEstadocivil());
        ps.setString(11, g.getEmail());
        ps.setInt(12, g.getEstado());
        ps.setString(13, g.getObservacion());
        ps.setString(14, g.getCedula());
        ps.setInt(15, g.getNacionalidad().getCodigo());
        ps.setDouble(16, g.getPorchon());
        ps.setInt(17, g.getDesmul());
        ps.setString(18, g.getHonalq());
        ps.setString(19, g.getHondep());
        ps.setString(20, g.getHongaraj());
        ps.setString(21, g.getHonindem());
        ps.setString(22, g.getHonllave());
        ps.setString(23, g.getHonmulta());
        ps.setString(24, g.getHonexp());
        ps.setInt(25, g.getCodger().getCodigo());
        ps.setInt(26, g.getCliqdep());
        ps.setDouble(27, g.getPormul());
        ps.setDouble(28, g.getPorcrete());

        ps.executeUpdate();
        st.close();
        ps.close();
        return g;
    }

    public boolean actualizarPropietario(propietario g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE propietarios SET nombre=?,apellido=?,tipodoc=?,ruc=?,fechanac=?,dirparticular=?,"
                + "teleparticular=?,dirlaboral=?,telelaboral=?,estadocivil=?,email=?,estado=?,observacion =?,cedula=?,nacionalidad=?, "
                + "porchon=?,desmul=?,honalq=?,hondep=?,hongaraj=?,honindem=?,honllave=?,"
                + "honmulta=?,honexp=?,codger=?,cliqdep=?,pormul=?,porcrete=? WHERE codpro=" + g.getCodpro());
        ps.setString(1, g.getNombre());
        ps.setString(2, g.getApellido());
        ps.setInt(3, g.getTipodoc().getCodigo());
        ps.setString(4, g.getRuc());
        ps.setDate(5, g.getFechanac());
        ps.setString(6, g.getDirparticular());
        ps.setString(7, g.getTeleparticular());
        ps.setString(8, g.getDirlaboral());
        ps.setString(9, g.getTelelaboral());
        ps.setString(10, g.getEstadocivil());
        ps.setString(11, g.getEmail());
        ps.setInt(12, g.getEstado());
        ps.setString(13, g.getObservacion());
        ps.setString(14, g.getCedula());
        ps.setInt(15, g.getNacionalidad().getCodigo());
        ps.setDouble(16, g.getPorchon());
        ps.setInt(17, g.getDesmul());
        ps.setString(18, g.getHonalq());
        ps.setString(19, g.getHondep());
        ps.setString(20, g.getHongaraj());
        ps.setString(21, g.getHonindem());
        ps.setString(22, g.getHonllave());
        ps.setString(23, g.getHonmulta());
        ps.setString(24, g.getHonexp());
        ps.setInt(25, g.getCodger().getCodigo());
        ps.setInt(26, g.getCliqdep());
        ps.setDouble(27, g.getPormul());
        ps.setDouble(28, g.getPorcrete());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarpropietario(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM propietarios WHERE codpro=?");
        ps.setInt(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

}

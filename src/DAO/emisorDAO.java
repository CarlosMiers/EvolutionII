/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.calificadora;
import Modelo.emisor;
import Modelo.pais;
import Modelo.rubro_emisor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class emisorDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<emisor> todos() throws SQLException {
        ArrayList<emisor> lista = new ArrayList<emisor>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT emisores.codigo,emisores.nombre,emisores.cedula,emisores.pais,"
                + "emisores.direccion,emisores.responsable,emisores.telefono,emisores.fax,"
                + "emisores.email,emisores.nomalias,emisores.rubro,emisores.tiposociedad,"
                + "emisores.tipoentidad,emisores.clase,emisores.estado,"
                + "paises.nombre AS nombrepais,rubro_emisor.nombre AS nombrerubro "
                + "FROM emisores "
                + "LEFT JOIN paises "
                + "ON paises.codigo=emisores.pais "
                + "LEFT JOIN rubro_emisor "
                + "ON rubro_emisor.codigo=emisores.rubro "
                + "ORDER BY emisores.codigo ";

        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emisor em = new emisor();
                pais pais = new pais();
                rubro_emisor rub = new rubro_emisor();

                em.setPais(pais);
                em.setRubro(rub);

                em.setCodigo(rs.getInt("codigo"));
                em.setNombre(rs.getString("nombre"));
                em.setCedula(rs.getString("cedula"));
                em.setDireccion(rs.getString("direccion"));
                em.setResponsable(rs.getString("responsable"));
                em.setTelefono(rs.getString("telefono"));
                em.setFax(rs.getString("fax"));
                em.setEmail(rs.getString("email"));
                em.setNomalias(rs.getString("nomalias"));
                em.setTiposociedad(rs.getInt("tiposociedad"));
                em.setTipoentidad(rs.getInt("tipoentidad"));
                em.setClase(rs.getInt("clase"));
                em.setEstado(rs.getInt("estado"));

                em.getPais().setCodigo(rs.getInt("pais"));
                em.getPais().setNombre(rs.getString("nombrepais"));
                em.getRubro().setCodigo(rs.getInt("rubro"));
                em.getRubro().setNombre(rs.getString("nombrerubro"));

                lista.add(em);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<emisor> todosActivos() throws SQLException {
        ArrayList<emisor> lista = new ArrayList<emisor>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT emisores.codigo,emisores.nombre,emisores.cedula,emisores.pais,"
                + "emisores.direccion,emisores.responsable,emisores.telefono,emisores.fax,"
                + "emisores.email,emisores.nomalias,emisores.rubro,emisores.tiposociedad,"
                + "emisores.tipoentidad,emisores.clase,emisores.estado,"
                + "paises.nombre AS nombrepais,rubro_emisor.nombre AS nombrerubro "
                + "FROM emisores "
                + "LEFT JOIN paises "
                + "ON paises.codigo=emisores.pais "
                + "LEFT JOIN rubro_emisor "
                + "ON rubro_emisor.codigo=emisores.rubro "
                + " WHERE emisores.estado=1 "
                + "ORDER BY emisores.codigo ";
        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emisor em = new emisor();
                pais pais = new pais();
                rubro_emisor rub = new rubro_emisor();

                em.setPais(pais);
                em.setRubro(rub);

                em.setCodigo(rs.getInt("codigo"));
                em.setNombre(rs.getString("nombre"));
                em.setCedula(rs.getString("cedula"));
                em.setDireccion(rs.getString("direccion"));
                em.setResponsable(rs.getString("responsable"));
                em.setTelefono(rs.getString("telefono"));
                em.setFax(rs.getString("fax"));
                em.setEmail(rs.getString("email"));
                em.setNomalias(rs.getString("nomalias"));
                em.setTiposociedad(rs.getInt("tiposociedad"));
                em.setTipoentidad(rs.getInt("tipoentidad"));
                em.setClase(rs.getInt("clase"));
                em.setEstado(rs.getInt("estado"));

                em.getPais().setCodigo(rs.getInt("pais"));
                em.getPais().setNombre(rs.getString("nombrepais"));
                em.getRubro().setCodigo(rs.getInt("rubro"));
                em.getRubro().setNombre(rs.getString("nombrerubro"));

                lista.add(em);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public emisor buscarId(int id) throws SQLException {
        emisor em = new emisor();
        pais pais = new pais();
        rubro_emisor rub = new rubro_emisor();
        calificadora cali = new calificadora();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT emisores.codigo,emisores.nombre,emisores.cedula,emisores.pais,"
                    + "emisores.direccion,emisores.responsable,emisores.telefono,emisores.fax,"
                    + "emisores.email,emisores.nomalias,emisores.rubro,emisores.tiposociedad,"
                    + "emisores.tipoentidad,emisores.clase,emisores.estado,emisores.calificadora,"
                    + "emisores.calificacion,calificadoras.nombre as nombrecalificadora,"
                    + "paises.nombre AS nombrepais,rubro_emisor.nombre AS nombrerubro,"
                    + "emisores.situacion "
                    + " FROM emisores "
                    +" LEFT JOIN calificadoras "
                    +" ON calificadoras.codigo=emisores.calificadora "
                    + "LEFT JOIN paises "
                    + "ON paises.codigo=emisores.pais "
                    + "LEFT JOIN rubro_emisor "
                    + "ON rubro_emisor.codigo=emisores.rubro "
                    + " WHERE emisores.codigo=? "
                    + " ORDER BY emisores.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    em.setPais(pais);
                    em.setRubro(rub);
                    em.setCalificadora(cali); 
                    
                    em.setCodigo(rs.getInt("codigo"));
                    em.setNombre(rs.getString("nombre"));
                    em.setCedula(rs.getString("cedula"));
                    em.setDireccion(rs.getString("direccion"));
                    em.setResponsable(rs.getString("responsable"));
                    em.setTelefono(rs.getString("telefono"));
                    em.setFax(rs.getString("fax"));
                    em.setEmail(rs.getString("email"));
                    em.setNomalias(rs.getString("nomalias"));
                    em.setTiposociedad(rs.getInt("tiposociedad"));
                    em.setTipoentidad(rs.getInt("tipoentidad"));
                    em.setClase(rs.getInt("clase"));
                    em.setSituacion(rs.getString("situacion"));
                    em.setEstado(rs.getInt("estado"));
                    
                    em.getCalificadora().setCodigo(rs.getInt("calificadora"));
                    em.getCalificadora().setNombre(rs.getString("nombrecalificadora"));

                    em.setCalificacion(rs.getString("calificacion"));
                    
                    em.getPais().setCodigo(rs.getInt("pais"));
                    em.getPais().setNombre(rs.getString("nombrepais"));
                    em.getRubro().setCodigo(rs.getInt("rubro"));
                    em.getRubro().setNombre(rs.getString("nombrerubro"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return em;
    }

    public emisor insertarEmisor(emisor ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO emisores "
                + "(nombre,cedula,pais,"
                + "direccion,responsable,telefono,fax,"
                + "email,nomalias,rubro,tiposociedad,"
                + "tipoentidad,clase,estado,situacion,calificadora,calificacion)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getCedula());
        ps.setInt(3, ca.getPais().getCodigo());

        ps.setString(4, ca.getDireccion());
        ps.setString(5, ca.getResponsable());
        ps.setString(6, ca.getTelefono());
        ps.setString(7, ca.getFax());

        ps.setString(8, ca.getEmail());
        ps.setString(9, ca.getNomalias());
        ps.setInt(10, ca.getRubro().getCodigo());
        ps.setInt(11, ca.getTiposociedad());
        ps.setInt(12, ca.getTipoentidad());
        ps.setInt(13, ca.getClase());
        ps.setInt(14, ca.getEstado());
        ps.setString(15, ca.getSituacion());
        ps.setInt(16,ca.getCalificadora().getCodigo());
        ps.setString(17, ca.getCalificacion());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarEmisor(emisor ca) throws SQLException {
        int rowsUpdated = 0;
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE emisores "
                + "SET nombre=?,cedula=?,pais=?,"
                + "direccion=?,responsable=?,telefono=?,fax=?,"
                + "email=?,nomalias=?,rubro=?,tiposociedad=?,"
                + "tipoentidad=?,clase=?,estado=?,"
                + "situacion=?,calificadora=?,calificacion=?  WHERE codigo=" + ca.getCodigo());
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getCedula());
        ps.setInt(3, ca.getPais().getCodigo());

        ps.setString(4, ca.getDireccion());
        ps.setString(5, ca.getResponsable());
        ps.setString(6, ca.getTelefono());
        ps.setString(7, ca.getFax());

        ps.setString(8, ca.getEmail());
        ps.setString(9, ca.getNomalias());
        ps.setInt(10, ca.getRubro().getCodigo());
        ps.setInt(11, ca.getTiposociedad());

        ps.setInt(12, ca.getTipoentidad());
        ps.setInt(13, ca.getClase());
        ps.setInt(14, ca.getEstado());
        ps.setString(15, ca.getSituacion());
        ps.setInt(16,ca.getCalificadora().getCodigo());
        ps.setString(17, ca.getCalificacion());
        try {
            rowsUpdated = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarEmisor(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM emisores WHERE codigo=?");
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

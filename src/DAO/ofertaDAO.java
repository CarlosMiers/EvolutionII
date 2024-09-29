/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionApiFais;
import Modelo.emisor;
import Modelo.moneda;
import Modelo.oferta;
import Modelo.titulo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ofertaDAO {

    ConexionApiFais conne = null;
    Statement stb = null;

    public ArrayList<oferta> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<oferta> lista = new ArrayList<oferta>();
        conne = new ConexionApiFais();
        stb = conne.conectarEspejo();

        String sql = "SELECT ofertas.id,ofertas.fecha,"
                + "ofertas.validohasta,ofertas.renta,"
                + "ofertas.emisor,ofertas.moneda,"
                + "ofertas.titulo,ofertas.plazo,"
                + "ofertas.tasa,ofertas.precio,"
                + "ofertas.cantidad,ofertas.valor_inversion,"
                + "ofertas.estado,ofertas.comentario,"
                + "monedas.nombre AS nombremoneda,"
                + "titulos.nombre AS nombretitulo,"
                + "emisores.nombre AS nombreemisor,"
                + "ofertas.comentario "
                + "FROM ofertas "
                + "LEFT JOIN monedas "
                + "ON monedas.codigo=ofertas.moneda "
                + "LEFT JOIN titulos "
                + "ON titulos.codigo=ofertas.titulo "
                + "LEFT JOIN emisores "
                + "ON emisores.codigo=ofertas.emisor "
                + " WHERE  ofertas.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                + " ORDER BY id ";

        System.out.println(sql);
        try (PreparedStatement ps = stb.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                oferta ofe = new oferta();
                emisor emi = new emisor();
                moneda mon = new moneda();
                titulo tit = new titulo();

                ofe.setEmisor(emi);
                ofe.setTitulo(tit);
                ofe.setMoneda(mon);

                ofe.getMoneda().setCodigo(rs.getInt("moneda"));
                ofe.getMoneda().setNombre(rs.getString("nombremoneda"));
                ofe.getTitulo().setCodigo(rs.getInt("titulo"));
                ofe.getTitulo().setNombre(rs.getString("nombretitulo"));
                ofe.getEmisor().setCodigo(rs.getInt("emisor"));
                ofe.getEmisor().setNombre(rs.getString("nombreemisor"));

                ofe.setId(rs.getDouble("id"));
                ofe.setFecha(rs.getDate("fecha"));
                ofe.setValidohasta(rs.getDate("validohasta"));
                ofe.setPlazo(rs.getInt("plazo"));
                ofe.setTasa(rs.getDouble("tasa"));
                ofe.setPrecio(rs.getDouble("precio"));
                ofe.setCantidad(rs.getDouble("cantidad"));
                ofe.setValor_inversion(rs.getDouble("valor_inversion"));
                ofe.setEstado(rs.getInt("estado"));
                ofe.setComentario(rs.getString("comentario"));
                lista.add(ofe);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            stb.close();
        }
        return lista;
    }

    public oferta buscarId(double id) throws SQLException {
        oferta ofe = new oferta();
        conne = new ConexionApiFais();
        stb = conne.conectarEspejo();
        Connection conn = stb.getConnection();
        try {
            String sql = "SELECT ofertas.id,ofertas.fecha,"
                    + "ofertas.validohasta,ofertas.renta,"
                    + "ofertas.emisor,ofertas.moneda,"
                    + "ofertas.titulo,ofertas.plazo,"
                    + "ofertas.tasa,ofertas.precio,"
                    + "ofertas.cantidad,ofertas.valor_inversion,"
                    + "ofertas.estado,ofertas.comentario,"
                    + "monedas.nombre AS nombremoneda,"
                    + "titulos.nombre AS nombretitulo,"
                    + "emisores.nombre AS nombreemisor,"
                    + "ofertas.comentario "
                    + "FROM ofertas "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=ofertas.moneda "
                    + "LEFT JOIN titulos "
                    + "ON titulos.codigo=ofertas.titulo "
                    + "LEFT JOIN emisores "
                    + "ON emisores.codigo=ofertas.emisor "
                    + " WHERE  ofertas.id=? "
                    + " ORDER BY ofertas.id ";

            try (PreparedStatement ps = stb.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    emisor emi = new emisor();
                    moneda mon = new moneda();
                    titulo tit = new titulo();

                    ofe.setEmisor(emi);
                    ofe.setTitulo(tit);
                    ofe.setMoneda(mon);

                    ofe.getMoneda().setCodigo(rs.getInt("moneda"));
                    ofe.getMoneda().setNombre(rs.getString("nombremoneda"));
                    ofe.getTitulo().setCodigo(rs.getInt("titulo"));
                    ofe.getTitulo().setNombre(rs.getString("nombretitulo"));
                    ofe.getEmisor().setCodigo(rs.getInt("emisor"));
                    ofe.getEmisor().setNombre(rs.getString("nombreemisor"));

                    ofe.setId(rs.getDouble("id"));
                    ofe.setFecha(rs.getDate("fecha"));
                    ofe.setValidohasta(rs.getDate("validohasta"));
                    ofe.setPlazo(rs.getInt("plazo"));
                    ofe.setTasa(rs.getDouble("tasa"));
                    ofe.setPrecio(rs.getDouble("precio"));
                    ofe.setCantidad(rs.getDouble("cantidad"));
                    ofe.setValor_inversion(rs.getDouble("valor_inversion"));
                    ofe.setEstado(rs.getInt("estado"));
                    ofe.setComentario(rs.getString("comentario"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        stb.close();
        conn.close();
        return ofe;
    }

    public oferta insertarOferta(oferta ca) throws SQLException {
        conne = new ConexionApiFais();
        stb = conne.conectarEspejo();
        PreparedStatement ps = null;

        ps = stb.getConnection().prepareStatement("INSERT INTO ofertas "
                + "(fecha,validohasta,renta,"
                + "emisor,moneda,titulo,"
                + "plazo,tasa,estado,"
                + "valor_inversion,comentario,cantidad,precio)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setDate(1, ca.getFecha());
        ps.setDate(2, ca.getValidohasta());
        ps.setInt(3, ca.getRenta());
        ps.setInt(4, ca.getEmisor().getCodigo());
        ps.setInt(5, ca.getMoneda().getCodigo());
        ps.setInt(6, ca.getTitulo().getCodigo());
        ps.setInt(7, ca.getPlazo());
        ps.setDouble(8, ca.getTasa());
        ps.setInt(9, ca.getEstado());
        ps.setDouble(10, ca.getValor_inversion());
        ps.setString(11, ca.getComentario());
        ps.setDouble(12, ca.getCantidad());
        ps.setDouble(13, ca.getPrecio());
        ps.executeUpdate();
        stb.close();
        ps.close();
        return ca;
    }

    public boolean actualizarOferta(oferta ca) throws SQLException {
        conne = new ConexionApiFais();
        stb = conne.conectarEspejo();
        PreparedStatement ps = null;

        ps = stb.getConnection().prepareStatement("UPDATE ofertas "
                + "SET fecha=?,validohasta=?,"
                + "renta=?,emisor=?,moneda=?"
                + ",titulo=?,plazo=?,tasa=?"
                + ",estado=?,valor_inversion=?,"
                + "comentario=?,cantidad=?,precio=? "
                + " WHERE id=" + ca.getId());
        ps.setDate(1, ca.getFecha());
        ps.setDate(2, ca.getValidohasta());
        ps.setInt(3, ca.getRenta());
        ps.setInt(4, ca.getEmisor().getCodigo());
        ps.setInt(5, ca.getMoneda().getCodigo());
        ps.setInt(6, ca.getTitulo().getCodigo());
        ps.setInt(7, ca.getPlazo());
        ps.setDouble(8, ca.getTasa());
        ps.setInt(9, ca.getEstado());
        ps.setDouble(10, ca.getValor_inversion());
        ps.setString(11, ca.getComentario());
        ps.setDouble(12, ca.getCantidad());
        ps.setDouble(13, ca.getPrecio());
        int rowsUpdated = ps.executeUpdate();
        stb.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarOferta(double cod) throws SQLException {
        conne = new ConexionApiFais();
        stb = conne.conectarEspejo();
        PreparedStatement ps = null;

        ps = stb.getConnection().prepareStatement("DELETE FROM ofertas WHERE id=?");
        ps.setDouble(1, cod);
        int rowsUpdated = ps.executeUpdate();
        stb.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.lista_precios_lotes;
import Modelo.lote;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class lista_precios_lotesDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<lista_precios_lotes> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql ="SELECT \n" +
                    "	 lista_precio_lotes.idlista,\n" +
                    "    lotes.codigo as lote,\n" +
                    "    lotes.nombre as nombrelote,\n" +
                    "    lista_precio_lotes.plazo,\n" +
                    "    lista_precio_lotes.cuotas,\n" +
                    "    lista_precio_lotes.precio,\n" +
                    "    lista_precio_lotes.descripcion,\n" +
                    "    manzanas.codigo as manzana,\n" +
                    "    manzanas.nombre as nombremanzana\n" +
                    "FROM lista_precio_lotes\n" +
                    "INNER JOIN lotes on lotes.codigo=lista_precio_lotes.lote\n" +
                    "INNER JOIN manzanas on manzanas.codigo=lotes.manzana\n"+
                    "ORDER BY lista_precio_lotes.idlista ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lote lot = new lote();
                lista_precios_lotes lista_p = new lista_precios_lotes();
                lista_p.setIdlista(rs.getInt("idlista"));
                lot.setCodigo(rs.getInt("lote"));
                lot.setNombre(rs.getString("nombrelote"));
                lot.setManzanas(rs.getInt("manzana"));
                lot.setNombremanzanas(rs.getString("nombremanzana"));
                lista_p.setLote(lot);
                lista_p.setPlazo(rs.getInt("plazo"));
                lista_p.setCuotas(rs.getBigDecimal("cuotas"));
                lista_p.setPrecio(rs.getBigDecimal("precio"));
                lista_p.setDescripcion(rs.getString("descripcion"));
                lista.add(lista_p);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }
    
    public ArrayList<lista_precios_lotes> porLote(int id_lote) throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();

        String sql ="SELECT \n" +
                    "	 lista_precio_lotes.idlista,\n" +
                    "    lotes.codigo as lote,\n" +
                    "    lotes.nombre as nombrelote,\n" +
                    "    lista_precio_lotes.plazo,\n" +
                    "    lista_precio_lotes.cuotas,\n" +
                    "    lista_precio_lotes.precio,\n" +
                    "    lista_precio_lotes.descripcion,\n" +
                    "    manzanas.codigo as manzana,\n" +
                    "    manzanas.nombre as nombremanzana\n" +
                    "FROM lista_precio_lotes\n" +
                    "INNER JOIN lotes on lotes.codigo=lista_precio_lotes.lote\n" +
                    "INNER JOIN manzanas on manzanas.codigo=lotes.manzana\n"+
                    "WHERE lotes.codigo="+id_lote+
                    " ORDER BY lista_precio_lotes.idlista ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lote lot = new lote();
                lista_precios_lotes lista_p = new lista_precios_lotes();
                lista_p.setIdlista(rs.getInt("idlista"));
                lot.setCodigo(rs.getInt("lote"));
                lot.setNombre(rs.getString("nombrelote"));
                lot.setManzanas(rs.getInt("manzana"));
                lot.setNombremanzanas(rs.getString("nombremanzana"));
                lista_p.setLote(lot);
                lista_p.setPlazo(rs.getInt("plazo"));
                lista_p.setCuotas(rs.getBigDecimal("cuotas"));
                lista_p.setPrecio(rs.getBigDecimal("precio"));
                lista_p.setDescripcion(rs.getString("descripcion"));
                lista.add(lista_p);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public lista_precios_lotes buscarId(int id) throws SQLException {

        lote lot = new lote();
        lista_precios_lotes lista_p = new lista_precios_lotes();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql =    "SELECT \n" +
                            "   lista_precio_lotes.idlista,\n" +
                            "   lotes.codigo as lote,\n" +
                            "   lotes.nombre as nombrelote,\n" +
                            "   lista_precio_lotes.plazo,\n" +
                            "   lista_precio_lotes.cuotas,\n" +
                            "   lista_precio_lotes.precio,\n" +
                            "   lista_precio_lotes.descripcion,\n" +
                            "   manzanas.codigo as manzana,\n" +
                            "   manzanas.nombre as nombremanzana\n" +
                            "FROM lista_precio_lotes\n" +
                            "INNER JOIN lotes on lotes.codigo=lista_precio_lotes.lote\n" +
                            "INNER JOIN manzanas on manzanas.codigo=lotes.manzana\n" +
                            "WHERE lista_precio_lotes.idlista=?\n" +
                            "ORDER BY lista_precio_lotes.idlista";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    lista_p.setIdlista(rs.getInt("idlista"));
                    lot.setCodigo(rs.getInt("lote"));
                    lot.setNombre(rs.getString("nombrelote"));
                    lot.setManzanas(rs.getInt("manzana"));
                    lot.setNombremanzanas(rs.getString("nombremanzana"));
                    lista_p.setLote(lot);
                    lista_p.setPlazo(rs.getInt("plazo"));
                    lista_p.setCuotas(rs.getBigDecimal("cuotas"));
                    lista_p.setPrecio(rs.getBigDecimal("precio"));
                    lista_p.setDescripcion(rs.getString("descripcion"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista_p;
    }

    public lista_precios_lotes buscarIdLotes(int id, int lote) throws SQLException {

        lote lot = new lote();
        lista_precios_lotes lista_p = new lista_precios_lotes();

        con = new Conexion();
        st = con.conectar();
        try {
            String sql =    "SELECT \n" +
                            "   lista_precio_lotes.idlista,\n" +
                            "   lotes.codigo as lote,\n" +
                            "   lotes.nombre as nombrelote,\n" +
                            "   lista_precio_lotes.plazo,\n" +
                            "   lista_precio_lotes.cuotas,\n" +
                            "   lista_precio_lotes.precio,\n" +
                            "   lista_precio_lotes.descripcion,\n" +
                            "   manzanas.codigo as manzana,\n" +
                            "   manzanas.nombre as nombremanzana\n" +
                            "FROM lista_precio_lotes\n" +
                            "INNER JOIN lotes on lotes.codigo=lista_precio_lotes.lote\n" +
                            "INNER JOIN manzanas on manzanas.codigo=lotes.manzana\n" +
                            "WHERE lista_precio_lotes.idlista=?\n" +
                            "AND lista_precio_lotes.lote=?\n" +
                            "ORDER BY lista_precio_lotes.idlista";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setInt(2, lote);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    lista_p.setIdlista(rs.getInt("idlista"));
                    lot.setCodigo(rs.getInt("lote"));
                    lot.setNombre(rs.getString("nombrelote"));
                    lot.setManzanas(rs.getInt("manzana"));
                    lot.setNombremanzanas(rs.getString("nombremanzana"));
                    lista_p.setLote(lot);
                    lista_p.setPlazo(rs.getInt("plazo"));
                    lista_p.setCuotas(rs.getBigDecimal("cuotas"));
                    lista_p.setPrecio(rs.getBigDecimal("precio"));
                    lista_p.setDescripcion(rs.getString("descripcion"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista_p;
    }
    
    public lista_precios_lotes insertarlista(lista_precios_lotes lista_p) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement(
                "INSERT INTO lista_precio_lotes(lote,plazo,cuotas,precio,descripcion)"+
                "VALUES (?,?,?,?,?)"
        );
        ps.setInt(1, lista_p.getLote().getCodigo());
        ps.setInt(2, lista_p.getPlazo());
        ps.setBigDecimal(3, lista_p.getCuotas());
        ps.setBigDecimal(4, lista_p.getPrecio());
        ps.setString(5, lista_p.getDescripcion());
        ps.executeUpdate();
        st.close();
        ps.close();
        return lista_p;
    }

    public boolean actualizarlista(lista_precios_lotes listap) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement(
                "UPDATE lista_precio_lotes SET lote=?,plazo=?,cuotas=?,precio=?,descripcion=? WHERE idlista=" + listap.getIdlista()
        );
        ps.setInt(1, listap.getLote().getCodigo());
        ps.setInt(2, listap.getPlazo());
        ps.setBigDecimal(3, listap.getCuotas());
        ps.setBigDecimal(4, listap.getPrecio());
        ps.setString(5, listap.getDescripcion());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarlista(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM lista_precio_lotes WHERE idlista=?");
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

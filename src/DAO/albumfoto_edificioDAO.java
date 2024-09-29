/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.albumfoto_edificio;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author SERVIDOR
 */
public class albumfoto_edificioDAO {

    Conexion con = null;
    Statement st = null;

    public albumfoto_edificio insertarimagen(albumfoto_edificio album, String ruta) throws SQLException, FileNotFoundException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        String insert = "insert into albumfoto_edificio(codigo,foto,nombre) values(?,?,?)";
        FileInputStream fis = null;
        PreparedStatement ps = null;
        conn.setAutoCommit(false);
        File file = new File(ruta);
        fis = new FileInputStream(file);
        ps = st.getConnection().prepareStatement(insert);
        ps.setInt(1, album.getCodigo().getIdunidad());
        ps.setBinaryStream(2, fis, (int) file.length());
        ps.setString(3, album.getNombre());
        ps.executeUpdate();
        conn.commit();
        st.close();
        ps.close();
        return album;
    }
    
    

    public albumfoto_edificio buscarimagen(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        albumfoto_edificio imagen = new albumfoto_edificio();
        try {
            String sql = "select * "
                    + "from albumfoto_edificio "
                    + "where albumfoto_edificio.codigo = ? "
                    + "order by albumfoto_edificio.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, cod);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    Blob blob = rs.getBlob("foto");
                    String nombre = rs.getObject("nombre").toString();
                    byte[] data = blob.getBytes(1, (int) blob.length());
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(new ByteArrayInputStream(data));
                    } catch (IOException ex) {
                        System.out.println("--> " + ex.getLocalizedMessage());
                    }
                    imagen.setFoto(img);
                    imagen.setNombre(nombre);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return imagen;
    }

    public ArrayList<albumfoto_edificio> getImagenes(int cod) {
        ArrayList<albumfoto_edificio> lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "select * "
                    + "from albumfoto_edificio "
                    + "where codigo=? "
                    + "order by albumfoto_edificio.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, cod);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    albumfoto_edificio imagen = new albumfoto_edificio();
                    Blob blob = rs.getBlob("foto");
                    String nombre = rs.getObject("nombre").toString();
                    byte[] data = blob.getBytes(1, (int) blob.length());
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(new ByteArrayInputStream(data));
                    } catch (IOException ex) {
                        System.out.println("--> " + ex.getLocalizedMessage());
                    }
                    imagen.setFoto(img);
                    imagen.setNombre(nombre);
                    lista.add(imagen);
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean eliminarFoto(String cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM albumfoto_edificio WHERE codigo=?");
        ps.setString(1, cod);
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

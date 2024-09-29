/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.albumfoto;
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
public class albumfotoDAO {

    Conexion con = null;
    Statement st = null;

    public albumfoto insertarimagen(albumfoto album, String ruta) throws SQLException, FileNotFoundException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        conn.setAutoCommit(false);
        String insert = "insert into albumfotos(codigo,foto,nombre) values(?,?,?)";
        FileInputStream fis = null;
        PreparedStatement ps = null;
        conn.setAutoCommit(false);
        File file = new File(ruta);
        fis = new FileInputStream(file);
        ps = st.getConnection().prepareStatement(insert);
        ps.setInt(1, album.getCodigo().getCodigo());
        ps.setBinaryStream(2, fis, (int) file.length());
        ps.setString(3, album.getNombre());
        ps.executeUpdate();
        conn.commit();
        ps.executeUpdate();
        st.close();
        ps.close();
        return album;
    }

    public albumfoto buscarimagen(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        albumfoto imagen = new albumfoto();
        try {
            String sql = "select * "
                    + "from foto,nombre "
                    + "where albumfotos.codigo = ? "
                    + "order by albumfotos.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, cod);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Blob blob = rs.getBlob("foto");
                    String nombre = rs.getObject("nombre").toString();
                    byte[] data = blob.getBytes(1, (int) blob.length());
                    BufferedImage img = null;
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
    
    public ArrayList<albumfoto> getImagenes(int cod) {
        ArrayList<albumfoto> lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "select * "
                    + "from albumfotos "
                    + "where codigo=? "
                    + "order by albumfotos.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, cod);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    albumfoto imagen = new albumfoto();
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

    public boolean eliminarFoto(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM albumfotos WHERE codigo=?");
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

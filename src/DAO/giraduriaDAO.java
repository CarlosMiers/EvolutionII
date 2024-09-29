/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.giraduria;
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
public class giraduriaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<giraduria> todos() throws SQLException {
        ArrayList<giraduria> lista = new ArrayList<giraduria>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT codigo,nombre,direccion,telefono,fax,responsable,planilla,estado,ruc,comision "
                + " FROM giradurias "
                + " ORDER BY codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                giraduria g = new giraduria();
                g.setCodigo(rs.getInt("codigo"));
                g.setNombre(rs.getString("nombre"));
                g.setDireccion(rs.getString("direccion"));
                g.setTelefono(rs.getString("telefono"));
                g.setRuc(rs.getString("ruc"));
                g.setPlanilla(rs.getInt("planilla"));
                g.setEstado(rs.getInt("estado"));
                g.setResponsable(rs.getString("responsable"));
                g.setComision(rs.getDouble("comision"));
                lista.add(g);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public giraduria buscarId(int id) throws SQLException {

        giraduria giraduria = new giraduria();
        giraduria.setCodigo(0);
        giraduria.setNombre("");
        giraduria.setRuc("");
        giraduria.setDireccion("");
        giraduria.setTelefono("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        
        try {
            String sql = "select codigo,nombre,direccion,telefono,fax,responsable,planilla,estado,ruc,comision,iva "
                    + "from giradurias "
                    + "where giradurias.codigo = ? "
                    + "order by giradurias.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    giraduria.setCodigo(rs.getInt("codigo"));
                    giraduria.setNombre(rs.getString("nombre"));
                    giraduria.setDireccion(rs.getString("direccion"));
                    giraduria.setTelefono(rs.getString("telefono"));
                    giraduria.setFax(rs.getString("fax"));
                    giraduria.setRuc(rs.getString("ruc"));
                    giraduria.setResponsable(rs.getString("responsable"));
                    giraduria.setEstado(rs.getInt("estado"));
                    giraduria.setPlanilla(rs.getInt("planilla"));
                    giraduria.setComision(rs.getDouble("comision"));
                    giraduria.setIva(rs.getDouble("iva"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return giraduria;
    }

    public giraduria insertarGiraduria(giraduria g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO giradurias (nombre,direccion,telefono,fax,responsable,planilla,estado,ruc,comision,iva) VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, g.getNombre());
        ps.setString(2, g.getDireccion());
        ps.setString(3, g.getTelefono());
        ps.setString(4, g.getFax());
        ps.setString(5, g.getResponsable());
        ps.setInt(6, g.getPlanilla());
        ps.setInt(7, g.getEstado());
        ps.setString(8, g.getRuc());
        ps.setDouble(9, g.getComision());
        ps.setDouble(10, g.getIva());
        ps.executeUpdate();
        st.close();
        ps.close();
        return g;
    }

    public boolean actualizarGiraduria(giraduria g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE giradurias SET nombre=?,direccion=?,telefono=?,fax=?,responsable=?,planilla=?,estado=?,ruc=?,comision=?,iva=? WHERE codigo=" + g.getCodigo());
        ps.setString(1, g.getNombre());
        ps.setString(2, g.getDireccion());
        ps.setString(3, g.getTelefono());
        ps.setString(4, g.getFax());
        ps.setString(5, g.getResponsable());
        ps.setInt(6, g.getPlanilla());
        ps.setInt(7, g.getEstado());
        ps.setString(8, g.getRuc());
        ps.setDouble(9,g.getComision());
        ps.setDouble(10,g.getIva());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

      public boolean eliminarGiraduria(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM giradurias WHERE codigo=?");
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

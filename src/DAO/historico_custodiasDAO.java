/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.historico_custodia;
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
public class historico_custodiasDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<historico_custodia> Todos() throws SQLException {
        ArrayList<historico_custodia> lista = new ArrayList<historico_custodia>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT id,idmovimiento,idnumero,fechaproceso,"
                    + "origencustodia,destinocustodia,"
                    + "pc,hora,custodias.nombre AS nombreorigen,"
                    + "(SELECT custodias.nombre "
                    + "FROM custodias "
                    + "WHERE custodias.codigo=historico_custodias.destinocustodia) nombredestino "
                    + "FROM historico_custodias "
                    + "LEFT JOIN custodias "
                    + "ON custodias.codigo=historico_custodias.origencustodia "
                    + "ORDER BY id";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    historico_custodia cu = new historico_custodia();
                    cu.setId(rs.getInt("id"));
                    cu.setIdmovimiento(rs.getString("idmovimiento"));
                    cu.setIdnumero(rs.getInt("idnumero"));
                    cu.setFechaproceso(rs.getDate("fechaproceso"));
                    cu.setOrigencustodia(rs.getInt("origencustodia"));
                    cu.setDestinocustodia(rs.getInt("destinocustodia"));
                    cu.setNombreorigen(rs.getString("nombreorigen"));
                    cu.setNombredestino(rs.getString("nombredestino"));
                    cu.setPc(rs.getString("pc"));
                    cu.setHora(rs.getString("hora"));
                    // System.out.println("Unidad Get " + ed. getIdunidad());
                    lista.add(cu);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<historico_custodia> BuscarId(String id) throws SQLException {
        ArrayList<historico_custodia> lista = new ArrayList<historico_custodia>();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT id,idmovimiento,idnumero,fechaproceso,"
                    + "origencustodia,destinocustodia,"
                    + "pc,hora,custodias.nombre AS nombreorigen,"
                    + "(SELECT custodias.nombre "
                    + "FROM custodias "
                    + "WHERE custodias.codigo=historico_custodias.destinocustodia) nombredestino "
                    + "FROM historico_custodias "
                    + "LEFT JOIN custodias "
                    + "ON custodias.codigo=historico_custodias.origencustodia "
                    + " WHERE historico_custodias.idmovimiento='" + id + "'"
                    + " ORDER BY fechaproceso";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    historico_custodia cu = new historico_custodia();
                    cu.setId(rs.getInt("id"));
                    cu.setIdmovimiento(rs.getString("idmovimiento"));
                    cu.setIdnumero(rs.getInt("idnumero"));
                    cu.setFechaproceso(rs.getDate("fechaproceso"));
                    cu.setOrigencustodia(rs.getInt("origencustodia"));
                    cu.setDestinocustodia(rs.getInt("destinocustodia"));
                    cu.setNombreorigen(rs.getString("nombreorigen"));
                    cu.setNombredestino(rs.getString("nombredestino"));
                    cu.setPc(rs.getString("pc"));
                    cu.setHora(rs.getString("hora"));
                    lista.add(cu);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public historico_custodia InsertarCustodia(historico_custodia hi) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO historico_custodias"
                + " (idmovimiento,idnumero,fechaproceso,origencustodia,destinocustodia,pc)"
                + "  VALUES (?,?,?,?,?,?)");
        ps.setString(1, hi.getIdmovimiento());
        ps.setInt(2, hi.getIdnumero());
        ps.setDate(3, hi.getFechaproceso());
        ps.setInt(4, hi.getOrigencustodia());
        ps.setInt(5, hi.getDestinocustodia());
        ps.setString(6, hi.getPc());
        ps.executeUpdate();
        st.close();
        ps.close();
        return hi;
    }

    public boolean ActualizarCustodia(historico_custodia hi) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE historico_custodias SET"
                + " idmovimiento=?,idnumero=?,fechaproceso=?,origencustodia=?,"
                + "destinocustodia=?,pc=? WHERE id=" + hi.getId());
        ps.setString(1, hi.getIdmovimiento());
        ps.setInt(2, hi.getIdnumero());
        ps.setDate(3, hi.getFechaproceso());
        ps.setInt(4, hi.getOrigencustodia());
        ps.setInt(5, hi.getDestinocustodia());
        ps.setString(6, hi.getPc());

        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarCustodia(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM historico_custodias WHERE id=?");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Usuario
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Conexion.Conexion;
import Modelo.carrera;
import Modelo.pensum;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Webmaster
 */
public class pensumDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<pensum> todos() throws SQLException {
        ArrayList<pensum> lista = new ArrayList<pensum>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT pensum.iditem,pensum.nombre,pensum.carrera,carreras.nombre AS nombrecarrera "
                + " FROM pensum "
                + "LEFT JOIN carreras "
                + "ON carreras.codigo=pensum.carrera "
                + " ORDER BY pensum.iditem ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pensum r = new pensum();
                carrera carrera = new carrera();
                r.setCarrera(carrera);
                r.setIditem(rs.getInt("iditem"));
                r.setNombre(rs.getString("nombre"));
                r.getCarrera().setCodigo(rs.getInt("carrera"));
                r.getCarrera().setNombre(rs.getString("nombrecarrera"));
                lista.add(r);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public pensum buscar (int codpensum) throws SQLException {
        pensum r = new pensum();
        carrera carrera = new carrera();
        r.setIditem(0);
        r.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT pensum.iditem,pensum.nombre,pensum.carrera,carreras.nombre AS nombrecarrera "
                    + " from pensum "
                    + "LEFT JOIN carreras "
                    + "ON carreras.codigo=pensum.carrera "
                    + " where pensum.iditem = ? "
                    + " order by pensum.iditem ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, codpensum);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                r.setCarrera(carrera);
                r.setIditem(rs.getInt("iditem"));
                r.setNombre(rs.getString("nombre"));
                r.getCarrera().setCodigo(rs.getInt("carrera"));
                r.getCarrera().setNombre(rs.getString("nombrecarrera"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return r;
    }

    public pensum insertarPensum(pensum ru) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO pensums (nombre,carrera) VALUES (?,?)");
        ps.setString(1, ru.getNombre());
        ps.setInt(2, ru.getCarrera().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ru;
    }

    public boolean actualizarPensum(pensum ru) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE pensum SET nombre=?,carrera=? WHERE iditem=" + ru.getIditem());
        ps.setString(1, ru.getNombre());
        ps.setInt(2, ru.getCarrera().getCodigo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean deletePensum(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM pensum WHERE iditem=?");
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


    public pensum buscar (int ncarrera, int nperiodo) throws SQLException {
        pensum r = new pensum();
        carrera carrera = new carrera();
        r.setIditem(0);
        r.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT pensum.iditem,pensum.nombre,pensum.carrera,carreras.nombre AS nombrecarrera "
                    + " from pensum "
                    + "LEFT JOIN carreras "
                    + "ON carreras.codigo=pensum.carrera "
                    + " where pensum.carrera = ? "
                    + " AND pensum.periodo=? "
                    + " order by pensum.iditem ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, ncarrera);
                ps.setInt(2, nperiodo);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                r.setCarrera(carrera);
                r.setIditem(rs.getInt("iditem"));
                r.setNombre(rs.getString("nombre"));
                r.getCarrera().setCodigo(rs.getInt("carrera"));
                r.getCarrera().setNombre(rs.getString("nombrecarrera"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->DESDE PENSUM " + ex.getLocalizedMessage());
        }
        st.close();
        return r;
    }


}

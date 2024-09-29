/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.formapago;
import Modelo.plan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class formapagoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<formapago> todos() throws SQLException {
        ArrayList<formapago> lista = new ArrayList<formapago>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM formaspago "
                + " ORDER BY formaspago.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                formapago fo = new formapago();
                fo.setCodigo(rs.getInt("codigo"));
                fo.setNombre(rs.getString("nombre"));
                lista.add(fo);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }



    public formapago buscarId(int id) throws SQLException {

        formapago fo = new formapago();
        fo.setCodigo(0);
        fo.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select *   "
                    + " from formaspago "
                    + "where formaspago.codigo = ? "
                    + "order by formaspago.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    fo.setCodigo(rs.getInt("codigo"));
                    fo.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return fo;
    }
    
        public boolean EnlaceContable(formapago r) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE formaspago SET idcta=? WHERE codigo=" + r.getCodigo());
        ps.setString(1, r.getIdcta().getCodigo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public formapago buscarIdCta(int id) throws SQLException {
        formapago f = new formapago();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select formaspago.codigo,formaspago.nombre as nombrerubro, "
                    + "plan.nombre as nombrecuenta,formaspago.idcta "
                    + "from formaspago "
                    + "left join plan "
                    + "on plan.codigo=formaspago.idcta "
                    + " where formaspago.codigo = ? "
                    + "order by formaspago.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    plan p = new plan();
                    f.setIdcta(p);
                    f.setCodigo(rs.getInt("codigo"));
                    f.setNombre(rs.getString("nombrerubro"));
                    f.getIdcta().setCodigo(rs.getString("idcta"));
                    f.getIdcta().setNombre(rs.getString("nombrecuenta"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return f;
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ar_com;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ar_comDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<ar_com> todos() throws SQLException {
        ArrayList<ar_com> lista = new ArrayList<ar_com>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT * FROM ar_com "
                + " ORDER BY ar_com_cod ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ar_com a = new ar_com();
                a.setAr_com_cod(rs.getInt("ar_com_cod"));
                a.setAr_comi_ve1(rs.getDouble("ar_comi_ve1"));
                a.setAr_comi_ve2(rs.getDouble("ar_comi_ve2"));
                a.setAr_comi_ve3(rs.getDouble("ar_comi_ve3"));
                a.setAr_comi_ve4(rs.getDouble("ar_comi_ve4"));
                a.setAr_comi_ve5(rs.getDouble("ar_comi_ve5"));
                a.setAr_comi_ve6(rs.getDouble("ar_comi_ve6"));
                a.setAr_comi_ve7(rs.getDouble("ar_comi_ve7"));
                a.setAr_comi_ve8(rs.getDouble("ar_comi_ve8"));
                a.setAr_comi_ve9(rs.getDouble("ar_comi_ve9"));
                a.setAr_comi_ve0(rs.getDouble("ar_comi_ve0"));
                a.setDesccomi(rs.getString("desccomi"));
                a.setAr_com_est(rs.getString("ar_com_est"));
                a.setAr_com_por(rs.getDouble("ar_com_por"));
                lista.add(a);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ar_com insertar(ar_com cm) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO ar_com (desccomi,ar_comi_ve1,ar_comi_ve2,ar_comi_ve3,ar_comi_ve4,ar_comi_ve5,ar_comi_ve6,ar_comi_ve7,ar_comi_ve8,ar_comi_ve9,ar_comi_ve0,ar_com_por) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, cm.getDesccomi());
        ps.setDouble(2, cm.getAr_comi_ve1());
        ps.setDouble(3, cm.getAr_comi_ve2());
        ps.setDouble(4, cm.getAr_comi_ve3());
        ps.setDouble(5, cm.getAr_comi_ve4());
        ps.setDouble(6, cm.getAr_comi_ve5());
        ps.setDouble(7, cm.getAr_comi_ve6());
        ps.setDouble(8, cm.getAr_comi_ve7());
        ps.setDouble(9, cm.getAr_comi_ve8());
        ps.setDouble(10, cm.getAr_comi_ve9());
        ps.setDouble(11, cm.getAr_comi_ve0());
        ps.setDouble(12, cm.getAr_com_por());
        ps.executeUpdate();
        st.close();
        ps.close();
        return cm;
    }

    public boolean actualizar(ar_com cm) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE ar_com SET desccomi=?,ar_comi_ve1=?,ar_comi_ve2=?,ar_comi_ve3=?,ar_comi_ve4=?,ar_comi_ve5=?,ar_comi_ve6=?,ar_comi_ve7=?,ar_comi_ve8=?,ar_comi_ve9=?,ar_comi_ve0=? WHERE ar_com_cod=" + cm.getAr_com_cod());
        ps.setString(1, cm.getDesccomi());
        ps.setDouble(2, cm.getAr_comi_ve1());
        ps.setDouble(3, cm.getAr_comi_ve2());
        ps.setDouble(4, cm.getAr_comi_ve3());
        ps.setDouble(5, cm.getAr_comi_ve4());
        ps.setDouble(6, cm.getAr_comi_ve5());
        ps.setDouble(7, cm.getAr_comi_ve6());
        ps.setDouble(8, cm.getAr_comi_ve7());
        ps.setDouble(9, cm.getAr_comi_ve8());
        ps.setDouble(10, cm.getAr_comi_ve9());
        ps.setDouble(11, cm.getAr_comi_ve0());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
      
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminar(int ar_com_cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM ar_com WHERE ar_com_cod=?");
        ps.setInt(1, ar_com_cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ar_com buscar(int cod) throws SQLException {
        ar_com cm = new ar_com();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + " from ar_com "
                    + " where ar_com.ar_com_cod=?"
                    + " order by ar_com.ar_com_cod ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, cod);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cm.setAr_com_cod(rs.getInt("ar_com_cod"));
                    cm.setDesccomi(rs.getString("desccomi"));
                    cm.setAr_comi_ve1(rs.getDouble("ar_comi_ve1"));
                    cm.setAr_comi_ve2(rs.getDouble("ar_comi_ve2"));
                    cm.setAr_comi_ve3(rs.getDouble("ar_comi_ve3"));
                    cm.setAr_comi_ve4(rs.getDouble("ar_comi_ve4"));
                    cm.setAr_comi_ve5(rs.getDouble("ar_comi_ve5"));
                    cm.setAr_comi_ve6(rs.getDouble("ar_comi_ve6"));
                    cm.setAr_comi_ve7(rs.getDouble("ar_comi_ve7"));
                    cm.setAr_comi_ve8(rs.getDouble("ar_comi_ve8"));
                    cm.setAr_comi_ve9(rs.getDouble("ar_comi_ve9"));
                    cm.setAr_comi_ve0(rs.getDouble("ar_comi_ve0"));
                    cm.setAr_com_est(rs.getString("ar_com_est"));
                    cm.setAr_com_por(rs.getDouble("ar_com_por"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return cm;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.desglose;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class desgloseDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<desglose> todos() throws SQLException {
        ArrayList<desglose> lista = new ArrayList<desglose>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT iditem,idcupones,iddesglose,cantidad,importe,serie,"
                + "nro_titulo,desde_acci,hasta_acci,cod_compra,cod_vende"
                + "FROM desglose";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                desglose des = new desglose();
                des.setIditem(rs.getInt("iditem"));
                des.setIddesglose(rs.getString("iddesglose"));
                des.setIdcupones(rs.getString("idcupones"));
                des.setSerie(rs.getString("serie"));
                des.setCantidad(rs.getInt("cantidad"));
                des.setImporte(rs.getDouble("importe"));
                des.setNro_titulo(rs.getString("nro_titulo"));
                des.setDesde_acci(rs.getInt("desde_acci"));
                des.setHasta_acci(rs.getInt("hasta_acci"));
                des.setCod_compra(rs.getInt("cod_compra"));
                des.setCod_vende(rs.getInt("cod_vende"));
                lista.add(des);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<desglose> todosxCierre(String referencia) throws SQLException {
        ArrayList<desglose> lista = new ArrayList<desglose>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT iditem,idcupones,iddesglose,cantidad,importe,serie,"
                + "nro_titulo,desde_acci,hasta_acci,cod_compra,cod_vende "
                + " FROM desglose "
                + " WHERE desglose.iddesglose='"+referencia+"'";
        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                desglose des = new desglose();
                des.setIditem(rs.getInt("iditem"));
                des.setIddesglose(rs.getString("iddesglose"));
                des.setIdcupones(rs.getString("idcupones"));
                des.setSerie(rs.getString("serie"));
                des.setCantidad(rs.getInt("cantidad"));
                des.setImporte(rs.getDouble("importe"));
                des.setNro_titulo(rs.getString("nro_titulo"));
                des.setDesde_acci(rs.getInt("desde_acci"));
                des.setHasta_acci(rs.getInt("hasta_acci"));
                des.setCod_compra(rs.getInt("cod_compra"));
                des.setCod_vende(rs.getInt("cod_vende"));
                lista.add(des);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public desglose buscarId(int id) throws SQLException {
        desglose des = new desglose();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT iditem,idcupones,iddesglose,cantidad,importe,serie,"
                    + "nro_titulo,desde_acci,hasta_acci,cod_compra,cod_vende"
                    + "FROM desglose"
                    + " WHERE desglose.iditem=?";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    des.setIditem(rs.getInt("iditem"));
                    des.setIddesglose(rs.getString("iddesglose"));
                    des.setIdcupones(rs.getString("idcupones"));
                    des.setCantidad(rs.getInt("cantidad"));
                    des.setSerie(rs.getString("serie"));
                    des.setImporte(rs.getDouble("importe"));
                    des.setNro_titulo(rs.getString("nro_titulo"));
                    des.setDesde_acci(rs.getInt("desde_acci"));
                    des.setHasta_acci(rs.getInt("hasta_acci"));
                    des.setCod_compra(rs.getInt("cod_compra"));
                    des.setCod_vende(rs.getInt("cod_vende"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return des;
    }

    public desglose insertarDesglose(desglose de) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO desglose "
                + "(idcupones,iddesglose,cantidad,importe,serie,"
                + "nro_titulo,desde_acci,hasta_acci,cod_compra,cod_vende) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, de.getIdcupones());
        ps.setString(2, de.getIddesglose());
        ps.setInt(3, de.getCantidad());
        ps.setDouble(4, de.getImporte());
        ps.setString(5, de.getSerie());
        ps.setString(6, de.getNro_titulo());
        ps.setInt(7, de.getDesde_acci());
        ps.setInt(8, de.getHasta_acci());
        ps.setInt(9, de.getCod_compra());
        ps.setInt(10, de.getCod_vende());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            JOptionPane.showMessageDialog(null, "Es Probable que el Semestre ya Exista");
        }
        st.close();
        ps.close();
        return de;
    }

    public boolean actualizarDesglose(desglose de) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE desglose "
                + "SET idcupones=?,iddesglose=?,cantidad=?,importe=?,serie=?,"
                + "nro_titulo=?,desde_acci=?,hasta_acci=?,cod_compra=?,cod_vende=? "
                + " WHERE iditem=" + de.getIditem());
        ps.setString(1, de.getIdcupones());
        ps.setString(2, de.getIddesglose());
        ps.setInt(3, de.getCantidad());
        ps.setDouble(4, de.getImporte());
        ps.setString(5, de.getSerie());
        ps.setString(6, de.getNro_titulo());
        ps.setInt(7, de.getDesde_acci());
        ps.setInt(8, de.getHasta_acci());
        ps.setInt(9, de.getCod_compra());
        ps.setInt(10, de.getCod_vende());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarDesglose(Double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM desglose WHERE iditem=?");
        ps.setDouble(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    
        public boolean eliminarDesglosexOperacion(String cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM desglose WHERE iddesglose=?");
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

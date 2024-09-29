/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.certificado_estudio;
import Modelo.cliente;
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
public class certificado_estudioDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<certificado_estudio> MostrarxActa(Double nacta) throws SQLException {
        ArrayList<certificado_estudio> lista = new ArrayList<certificado_estudio>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idnota,item,codalumno,nota,certificado_estudios.estado,"
                    + "clientes.nombre AS nombrealumno "
                    + "FROM certificado_estudios "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=certificado_estudios.codalumno "
                    + "WHERE idnota=? "
                    + "ORDER BY idnota,item ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, nacta);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    certificado_estudio cer = new certificado_estudio();

                    cer.setCodalumno(cliente);
                    cer.setIdnota(rs.getInt("idnota"));
                    cer.setItem(rs.getInt("item"));
                    cer.getCodalumno().setCodigo(rs.getInt("codalumno"));
                    cer.getCodalumno().setNombre(rs.getString("nombrealumno"));
                    cer.setNota(rs.getInt("nota"));
                    cer.setEstado(rs.getString("estado"));
                    lista.add(cer);

                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public certificado_estudio InsertarNota(certificado_estudio ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO certificado_estudios "
                + "(idnota,codalumno,nota,estado)"
                + "VALUES (?,?,?,?)");
        ps.setDouble(1, ca.getIdnota());
        ps.setInt(2, ca.getCodalumno().getCodigo());
        ps.setInt(3, ca.getNota());
        ps.setString(4, ca.getEstado());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarNota(certificado_estudio ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE certificado_estudios "
                + "SET codalumno=?,nota=?,estado=? "
                + " WHERE item=" + ca.getItem());
        ps.setInt(1, ca.getCodalumno().getCodigo());
        ps.setInt(2, ca.getNota());
        ps.setString(3, ca.getEstado());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarNota(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM certificado_estudios WHERE item=?");
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

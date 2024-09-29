/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.detalle_derecho_examen;
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
public class detalle_derecho_examenDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_derecho_examen> MostrarxActa(Double nacta) throws SQLException {
        ArrayList<detalle_derecho_examen> lista = new ArrayList<detalle_derecho_examen>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idnota,item,codalumno,matricula_pago,"
                    + "detalle_derecho_examen.cuota_pago,"
                    + "detalle_derecho_examen.iddocumento,"
                    + "detalle_derecho_examen.importe_examen,"
                    + "clientes.nombre AS nombrealumno "
                    + "FROM detalle_derecho_examen "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=detalle_derecho_examen.codalumno "
                    + "WHERE idnota=? "
                    + "ORDER BY idnota,item ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, nacta);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cliente cliente = new cliente();
                    detalle_derecho_examen cer = new detalle_derecho_examen();

                    cer.setCodalumno(cliente);
                    cer.setIdnota(rs.getInt("idnota"));
                    cer.setItem(rs.getInt("item"));
                    cer.getCodalumno().setCodigo(rs.getInt("codalumno"));
                    cer.getCodalumno().setNombre(rs.getString("nombrealumno"));
                    cer.setMatricula_pago(rs.getInt("matricula_pago"));
                    cer.setCuota_pago(rs.getInt("cuota_pago"));
                    cer.setIddocumento(rs.getString("iddocumento"));
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

    public detalle_derecho_examen InsertarNota(detalle_derecho_examen ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO detalle_derecho_examen "
                + "(idnota,codalumno,matricula_pago,cuota_pago,"
                + "iddocumento,importe_examen)"
                + "VALUES (?,?,?,?,?,?)");
        ps.setDouble(1, ca.getIdnota());
        ps.setInt(2, ca.getCodalumno().getCodigo());
        ps.setInt(3, ca.getMatricula_pago());
        ps.setInt(4, ca.getCuota_pago());
        ps.setString(5, ca.getIddocumento());
        ps.setDouble(6, ca.getImporte_examen());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarNota(detalle_derecho_examen ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE detalle_derecho_examen "
                + "SET idnota=?,codalumno=?,matricula_pago=?,cuota_pago=?,"
                + "iddocumento=?,importe_examen=? "
                + " WHERE item=" + ca.getItem());
        ps.setDouble(1, ca.getIdnota());
        ps.setInt(2, ca.getCodalumno().getCodigo());
        ps.setInt(3, ca.getMatricula_pago());
        ps.setInt(4, ca.getCuota_pago());
        ps.setString(5, ca.getIddocumento());
        ps.setDouble(6, ca.getImporte_examen());
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

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_derecho_examen WHERE item=?");
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

    public boolean eliminarCuenta(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuenta_clientes WHERE iddocumento=?");
        ps.setString(1, id);
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

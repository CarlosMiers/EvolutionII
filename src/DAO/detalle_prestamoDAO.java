/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_prestamo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class detalle_prestamoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_prestamo> MostrarDetalle(int id) throws SQLException {
        ArrayList<detalle_prestamo> lista = new ArrayList<detalle_prestamo>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT nprestamo,nrocuota,capital,"
                    + "emision,amortiza,minteres,vence,"
                    + "monto,ivaxinteres,aporte,dias "
                    + "FROM detalle_prestamo "
                    + "WHERE nprestamo = ? "
                    + " ORDER BY nrocuota ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_prestamo dt = new detalle_prestamo();
                    dt.setNprestamo(rs.getInt("nprestamo"));
                    dt.setNrocuota(rs.getInt("nrocuota"));
                    dt.setCapital(rs.getDouble("capital"));
                    dt.setEmision(rs.getDate("emision"));
                    dt.setVence(rs.getDate("vence"));
                    dt.setAmortiza(rs.getDouble("amortiza"));
                    dt.setMinteres(rs.getDouble("minteres"));
                    dt.setMonto(rs.getDouble("monto"));
                    dt.setIvaxinteres(rs.getDouble("ivaxinteres"));
                    dt.setAporte(rs.getDouble("aporte"));
                    dt.setDias(rs.getInt("dias"));
                    lista.add(dt);
                }
                rs.close();
                ps.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean EliminarDetallePrestamo(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_prestamo WHERE nprestamo=?");
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

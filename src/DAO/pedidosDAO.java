/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.Config;
import Conexion.Conexion;
import Modelo.sucursal;
import Modelo.pedidos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class pedidosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<pedidos> Todos() throws SQLException {
        ArrayList<pedidos> lista = new ArrayList<pedidos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT idpedido,fecha,sucursal,"
                    + "totales,cierre,"
                    + "sucursales.nombre AS nombresucursal "
                    + "FROM pedidos "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=pedidos.sucursal "
                    + "WHERE pedidos.cierre<>1 "
                    + "ORDER BY pedidos.idpedido ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    pedidos vta = new pedidos();
                    sucursal suc = new sucursal();

                    vta.setSucursal(suc);
                    vta.setIdpedido(rs.getInt("idpedido"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.setTotales(rs.getDouble("totales"));
                    vta.setCierre(rs.getInt("cierre"));
                    lista.add(vta);
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean CerrarPedido(Double numeropedido) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE pedidos SET cierre=1 WHERE idpedido= ?");
        ps.setDouble(1, numeropedido);
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        st.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<pedidos> TodosxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<pedidos> lista = new ArrayList<pedidos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT idpedido,fecha,sucursal,"
                    + "totales,cierre, COUNT(producto) AS nproducto,"
                    + "sucursales.nombre AS nombresucursal "
                    + "FROM pedidos "
                    + "LEFT JOIN pedidos_detalle "
                    + "ON pedidos_detalle.idpedido=pedidos.idpedido "
                    + "LEFT JOIN sucursales "
                    + "ON sucursales.codigo=pedidos.sucursal "
                    + "WHERE pedidos.fecha BETWEEN ? AND ? "
                    +" GROUP BY pedidos.idpedido "
                    + "ORDER BY pedidos.idpedido ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    pedidos vta = new pedidos();
                    sucursal suc = new sucursal();

                    vta.setSucursal(suc);
                    vta.setIdpedido(rs.getInt("idpedido"));
                    vta.setFecha(rs.getDate("fecha"));
                    vta.getSucursal().setCodigo(rs.getInt("sucursal"));
                    vta.getSucursal().setNombre(rs.getString("nombresucursal"));
                    vta.setTotales(rs.getDouble("totales"));
                    vta.setCierre(rs.getInt("cierre"));
                    lista.add(vta);
                }
                ps.close();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

}

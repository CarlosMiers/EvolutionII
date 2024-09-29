/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_planilla_comercio;
import Modelo.cliente;
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
public class detalle_planilla_comercioDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_planilla_comercio> MostrarDetalle(int id) throws SQLException {
        ArrayList<detalle_planilla_comercio> lista = new ArrayList<detalle_planilla_comercio>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT dnumero,iddocumento,socio,"
                    + "numeroorden,emision_orden,vence_cuota,"
                    + "monto,numerocuota,cuota,"
                    + "clientes.nombre AS nombresocio "
                    + "FROM detalle_planilla_comercios "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=detalle_planilla_comercios.socio "
                    + "WHERE detalle_planilla_comercios.dnumero= ? "
                    + " ORDER BY detalle_planilla_comercios.numeroorden ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_planilla_comercio dt = new detalle_planilla_comercio();
                    cliente cl = new cliente();
                    dt.setSocio(cl);
                    dt.getSocio().setCodigo(rs.getInt("socio"));
                    dt.getSocio().setNombre(rs.getString("nombresocio"));
                    dt.setIddocumento(rs.getString("iddocumento"));
                    dt.setNumeroorden(rs.getDouble("numeroorden"));
                    dt.setEmision_orden(rs.getDate("emision_orden"));
                    dt.setNumerocuota(rs.getInt("numerocuota"));
                    dt.setCuota(rs.getInt("cuota"));
                    dt.setVence_cuota(rs.getDate("vence_cuota"));
                    dt.setMonto(rs.getDouble("monto"));
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

    public boolean EliminarDetallePlanilla(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_planilla_comercios WHERE dnumero=?");
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

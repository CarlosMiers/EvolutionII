/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionApiFais;
import Modelo.cupones;
import Modelo.producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class cuponesAppDAO {

    ConexionApiFais conEsp = null;
    Statement stEspejo = null;

    public ArrayList<cupones> MostrarDetalle(String referencia) throws SQLException {
        ArrayList<cupones> lista = new ArrayList<cupones>();
        conEsp = new ConexionApiFais();
        stEspejo = conEsp.conectarEspejo();
        try {

            String sql = "SELECT ncupon,ncantidad,fechainicial,idcupon,"
                    + "plazocupon,fechavencimiento,valorfuturo,valoractual "
                    + "FROM cupones "
                    + "WHERE estado=1 "
                    + " AND idprecierre='" + referencia + "'"
                    + " ORDER BY numeroprecierre,ncupon";

            try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cupones dc = new cupones();
                    dc.setNcupon(rs.getInt("ncupon"));
                    dc.setNcantidad(rs.getInt("ncantidad"));
                    dc.setPlazocupon(rs.getInt("plazocupon"));
                    dc.setFechainicial(rs.getDate("fechainicial"));
                    dc.setFechavencimiento(rs.getDate("fechavencimiento"));
                    dc.setValorfuturo(rs.getDouble("valorfuturo"));
                    dc.setValoractual(rs.getDouble("valoractual"));
                    dc.setIdcupon(rs.getString("idcupon"));
                    lista.add(dc);
                }
                ps.close();
                stEspejo.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarDetallecupones(String id) throws SQLException {
        conEsp = new ConexionApiFais();
        stEspejo = conEsp.conectarEspejo();
        Connection conne = stEspejo.getConnection();
        PreparedStatement ps = null;
        ps = stEspejo.getConnection().prepareStatement("DELETE FROM cupones WHERE idprecierre=?");
        ps.setString(1, id);
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ActualizarEstadoCupones(String id) throws SQLException {
        conEsp = new ConexionApiFais();
        stEspejo = conEsp.conectarEspejo();

        PreparedStatement ps = null;
        ps = stEspejo.getConnection().prepareStatement("UPDATE cupones SET estado=0 WHERE idcupon=?");
        ps.setString(1, id);
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ActualizarEstadoCuponesxVenta(int nestado, String id) throws SQLException {
        conEsp = new ConexionApiFais();
        stEspejo = conEsp.conectarEspejo();
        PreparedStatement ps = null;
        ps = stEspejo.getConnection().prepareStatement("UPDATE cupones SET estado=? WHERE idprecierre=?");
        ps.setInt(1, nestado);
        ps.setString(2, id);
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarCupones(String id) throws SQLException {
        conEsp = new ConexionApiFais();
        stEspejo = conEsp.conectarEspejo();
        PreparedStatement ps = null;
        ps = stEspejo.getConnection().prepareStatement("DELETE FROM cupones WHERE idcupon=?");
        ps.setString(1, id);
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean GrabarCuponesVendidos(String id) throws SQLException {
        conEsp = new ConexionApiFais();
        stEspejo = conEsp.conectarEspejo();
        Connection conne = stEspejo.getConnection();

        String sql = "INSERT INTO cupones_ventas("
                + "idprecierre,"
                + "idcupon,"
                + "numeroprecierre,"
                + "iddesglose,"
                + "ncantidad,"
                + "ncupon,"
                + "diaemision,"
                + "fechainicial,"
                + "plazocupon,"
                + "base,"
                + "interes,"
                + "fechavencimiento,"
                + "valorfuturo,"
                + "montocapital,"
                + "estado) "
                + "SELECT idprecierre,"
                + "idcupon,"
                + "numeroprecierre,"
                + "iddesglose,"
                + "ncantidad,"
                + "ncupon,"
                + "diaemision,"
                + "fechainicial,"
                + "plazocupon,"
                + "base,"
                + "interes,"
                + "fechavencimiento,"
                + "valorfuturo,"
                + "montocapital,"
                + "estado "
                + " FROM cupones"
                + " WHERE estado=1  AND idprecierre='" + id + "'";
        PreparedStatement ps = conne.prepareStatement(sql);
        try {
            ps.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("-->ERROR EN VENTA DE CARTERA-CUPONES " + ex.getLocalizedMessage());
        }
        stEspejo.close();
        ps.close();
        conne.close();
        return true;
    }

    public boolean EliminarCuponesVendidos(String id) throws SQLException {
        conEsp = new ConexionApiFais();
        stEspejo = conEsp.conectarEspejo();
        PreparedStatement ps = null;
        ps = stEspejo.getConnection().prepareStatement("DELETE FROM cupones_ventas WHERE idprecierre=?");
        ps.setString(1, id);
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ActualizarEstadoCuponesxVentaParcial(double nsaldo, String id) throws SQLException {
        conEsp = new ConexionApiFais();
        stEspejo = conEsp.conectarEspejo();
        Connection conne = stEspejo.getConnection();
        PreparedStatement ps = null;
        ps = stEspejo.getConnection().prepareStatement("UPDATE cupones SET valorfuturo=" + nsaldo + " WHERE idcupon='" + id + "'");
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            System.out.println("ACTUALIZO SALDO CUPON " + id);
            return true;
        } else {
            System.out.println("SE FUE A LA PUTA " + id);
            return false;
        }
    }

    public boolean RetornarSaldoxVentaParcial(double nsaldo, String id, int ncupon) throws SQLException {
        conEsp = new ConexionApiFais();
        stEspejo = conEsp.conectarEspejo();
        PreparedStatement ps = null;
        ps = stEspejo.getConnection().prepareStatement("UPDATE cupones SET valorfuturo=valorfuturo+" + nsaldo + " WHERE idprecierre='" + id + "' AND ncupon=" + ncupon);
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            System.out.println("ACTUALIZO SALDO CUPON " + id);
            return true;
        } else {
            System.out.println("SE FUE A LA PUTA " + id);
            return false;
        }
    }

}

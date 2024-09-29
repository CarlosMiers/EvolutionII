/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExportaVtaDAO;

import DAO.*;
import Conexion.Conexion;
import Conexion.ConexionEspejo;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.sucursal;
import Modelo.transferencia;
import Modelo.venta;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
public class ExportatransferenciaDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;

    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public ArrayList<transferencia> MostrarEspejoxFecha(Date fechaini, Date fechafin, Integer nsuc) throws SQLException {
        ArrayList<transferencia> listEspejoa = new ArrayList<transferencia>();
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        try {

            String sql = "SELECT transferencias.idtransferencia,transferencias.numero,transferencias.fecha,"
                    + "transferencias.origen,transferencias.destEspejoino,transferencias.tipo, "
                    + "transferencias.cierre,transferencias.codusuario,transferencias.horagrabado, "
                    + "comprobantes.nombre AS nombrecomprobante,transferencias.codusuario, "
                    + "(SELECT nombre FROM sucursales WHERE origen=sucursales.codigo) nombreorigen, "
                    + "(SELECT nombre FROM sucursales WHERE destEspejoino=sucursales.codigo) nombredestEspejoino "
                    + "FROM transferencias "
                    + "LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=transferencias.tipo "
                    + " WHERE transferencias.fecha between ? AND ? "
                    + " ORDER BY transferencias.numero ";

            try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    transferencia tr = new transferencia();
                    sucursal sucursal = new sucursal();
                    comprobante comprobante = new comprobante();
                    tr.setTipo(comprobante);
                    tr.setNumero(rs.getInt("numero"));
                    tr.setFecha(rs.getDate("fecha"));
                    tr.setOrigen(rs.getInt("origen"));
                    tr.setCierre(rs.getInt("cierre"));
                    tr.setCodusuario(rs.getInt("codusuario"));
                    tr.setHoragrabado(rs.getTime("horagrabado"));
                    tr.setIdtransferencia(rs.getString("idtransferencia"));
                    tr.setNumero(rs.getInt("numero"));
                    tr.getTipo().setCodigo(rs.getInt("tipo"));
                    tr.getTipo().setNombre(rs.getString("nombrecomprobante"));
                    tr.setDestino(rs.getInt("destEspejoino"));
                    tr.setNombredestino(rs.getString("nombredestEspejoino"));
                    tr.setNombreorigen(rs.getString("nombreorigen"));
                    listEspejoa.add(tr);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        stEspejo.close();
        return listEspejoa;
    }

    public boolean borrarAjustes(Date fechaini, Date fechafin, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("DELETE FROM transferencias WHERE fecha>=? AND fecha<=?");
        ps.setDate(1, fechaini);
        ps.setDate(2, fechafin);

        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public transferencia insertarmercaderia(transferencia ocr, String detalle, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("INSERT INTO transferencias (idtransferencia,fecha,origen,destino,tipo) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ocr.getIdtransferencia());
        ps.setDate(2, ocr.getFecha());
        ps.setInt(3, ocr.getOrigen());
        ps.setInt(4, ocr.getDestino());
        ps.setInt(5, ocr.getTipo().getCodigo());
        ps.executeUpdate();
        stEspejo.close();
        ps.close();
        return ocr;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.banco;
import Modelo.detalle_forma_pago;
import Modelo.formapago;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class detalle_forma_pagoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_forma_pago> MostrarDetalle(String id) throws SQLException {
        ArrayList<detalle_forma_pago> lista = new ArrayList<detalle_forma_pago>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT detalle_forma_pago.idmovimiento,detalle_forma_pago.forma,detalle_forma_pago.banco,detalle_forma_pago.nrocheque,"
                    + "detalle_forma_pago.importepago,detalle_forma_pago.netocobrado,detalle_forma_pago.fechaentregacheque,"
                    + "detalle_forma_pago.fechacobrocheque,bancos.nombre AS nombrebanco,formaspago.nombre AS nombreformapago,"
                    + "detalle_forma_pago.confirmacion "
                    + " FROM detalle_forma_pago "
                    + " LEFT JOIN bancos "
                    + " ON bancos.codigo=detalle_forma_pago.banco "
                    + " LEFT JOIN formaspago "
                    + " ON formaspago.codigo=detalle_forma_pago.forma "
                    + " WHERE detalle_forma_pago.idmovimiento= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_forma_pago dc = new detalle_forma_pago();
                    banco bco = new banco();
                    formapago frp = new formapago();
                    
                    dc.setIdmovimiento(rs.getString("idmovimiento"));
                    dc.setBanco(bco);
                    dc.setForma(frp);
                    
                    dc.getForma().setCodigo(rs.getInt("forma"));
                    dc.getForma().setNombre(rs.getString("nombreformapago"));
                    dc.getBanco().setCodigo(rs.getInt("banco"));
                    dc.getBanco().setNombre(rs.getString("nombrebanco"));
                    dc.setNrocheque(rs.getString("nrocheque"));
                    dc.setConfirmacion(rs.getDate("confirmacion"));
                    dc.setNetocobrado(rs.getDouble("netocobrado"));
                    dc.setFechaentrega(rs.getDate("fechaentregacheque"));
                    dc.setFechacobrocheque(rs.getDate("fechacobrocheque"));
                    
                    lista.add(dc);
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarDetalleFormaPago(String creferencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_forma_pago WHERE idmovimiento=?");
        ps.setString(1, creferencia);
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

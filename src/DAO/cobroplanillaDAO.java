/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.bancoplaza;
import Modelo.cobroplanilla;
import Modelo.formapago;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class cobroplanillaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cobroplanilla> muestrarxplanilla(int idplanilla) throws SQLException {
        ArrayList<cobroplanilla> lista = new ArrayList<cobroplanilla>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT cobroplanilla.idpago,cobroplanilla.idplanilla,"
                    + "cobroplanilla.fechacobro,cobroplanilla.formacobro,"
                    + "cobroplanilla.cargobanco,cobroplanilla.nrocheque,"
                    + "cobroplanilla.vencecheque,cobroplanilla.importe,"
                    + "formaspago.nombre AS nombreformacobro,bancos_plaza.nombre AS nombrebanco "
                    + "FROM cobroplanilla "
                    + "LEFT JOIN formaspago "
                    + "ON formaspago.codigo = cobroplanilla.formacobro "
                    + "LEFT JOIN bancos_plaza ON bancos_plaza.codigo = cobroplanilla.cargobanco "
                    + "WHERE cobroplanilla.idplanilla= ? "
                    + " ORDER BY cobroplanilla.idpago";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, idplanilla);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cobroplanilla cob = new cobroplanilla();
                    bancoplaza bco = new bancoplaza();
                    formapago forma= new formapago();
                    
                    cob.setCargobanco(bco);
                    cob.setFormacobro(forma);
                    
                    cob.setIdpago(rs.getDouble("idpago"));
                    cob.setIdplanilla(rs.getDouble("idplanilla"));
                    cob.setFechacobro(rs.getDate("fechacobro"));
                    cob.setImporte(rs.getBigDecimal("importe"));
                    cob.setVencecheque(rs.getDate("vencecheque"));
                    cob.setNrocheque(rs.getString("nrocheque"));
                    
                    cob.getCargobanco().setCodigo(rs.getInt("cargobanco"));
                    cob.getCargobanco().setNombre(rs.getString("nombrebanco"));
                    
                    cob.getFormacobro().setCodigo(rs.getInt("formacobro"));
                    cob.getFormacobro().setNombre(rs.getString("nombreformacobro"));
                    
                    lista.add(cob);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public cobroplanilla insertarCobro(cobroplanilla pl) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cobroplanilla (idplanilla,fechacobro,formacobro,cargobanco,nrocheque,vencecheque,importe) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, pl.getIdplanilla());
        ps.setDate(2, pl.getFechacobro());
        ps.setInt(3,pl.getFormacobro().getCodigo());
        ps.setInt(4,pl.getCargobanco().getCodigo());
        ps.setString(5, pl.getNrocheque());
        ps.setDate(6, pl.getVencecheque());
        ps.setBigDecimal(7,pl.getImporte());
        ps.executeUpdate();
        st.close();
        ps.close();
        return pl;
    }

    public boolean borrarCobroPlanilla(double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cobroplanilla WHERE idpago=?");
        ps.setDouble(1, id);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

        public cobroplanilla buscarId(double id) throws SQLException {

        cobroplanilla c = new cobroplanilla();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select idpago "
                    + "from cobroplanilla "
                    + "where cobroplanilla.idpago = ? "
                    + "order by cobroplanilla.idpago ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    c.setIdpago(rs.getDouble("idpago"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return c;
    }

}

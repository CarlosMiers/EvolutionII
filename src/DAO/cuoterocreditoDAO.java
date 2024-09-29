/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.comprobante;
import Modelo.cuoterocredito;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class cuoterocreditoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cuoterocredito> todos(int idcom) throws SQLException {
        ArrayList<cuoterocredito> lista = new ArrayList<cuoterocredito>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT id,comprobante,importe,cuota2,cuota3,comprobantes.nombre as nombrecomprobante "
                + "FROM cuoterocredito "
                + "LEFT JOIN comprobantes "
                + "ON comprobantes.codigo=cuoterocredito.comprobante "
                +" WHERE cuoterocredito.comprobante=?"
                + " ORDER BY cuoterocredito.id ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idcom);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cuoterocredito lis = new cuoterocredito();
                comprobante c = new comprobante();
                lis.setComprobante(c);
                lis.getComprobante().setCodigo(rs.getInt("comprobante"));
                lis.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                lis.setId(rs.getInt("id"));
                lis.setImporte(rs.getDouble("importe"));
                lis.setCuota2(rs.getDouble("cuota2"));
                lis.setCuota3(rs.getDouble("cuota3"));
                lista.add(lis);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public cuoterocredito insertarCuotero(cuoterocredito lp) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("INSERT INTO cuoterocredito"
                + " (comprobante,importe,cuota2,cuota3) VALUES (?,?,?,?)");
        ps.setInt(1, lp.getComprobante().getCodigo());
        ps.setDouble(2, lp.getImporte());
        ps.setDouble(3, lp.getCuota2());
        ps.setDouble(4, lp.getCuota3());
        ps.executeUpdate();
        ps.close();
        st.close();
        return lp;
    }
    
        public boolean borrarItemCuotero(cuoterocredito  cu) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cuoterocredito WHERE id=?");
        ps.setInt(1,cu.getId());
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

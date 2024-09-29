/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.comprobante;
import Modelo.tasa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class tasaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<tasa> todos(int id) throws SQLException {
        ArrayList<tasa> lista = new ArrayList<tasa>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT config_premios.comprobante,config_premios.plazoi,config_premios.porcentaje "
                + " FROM config_premios "
                + " WHERE config_premios.comprobante = ? "
                + " ORDER BY config_premios.plazoi ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tasa t = new tasa();
                t.setPlazoi(rs.getDouble("plazoi"));
                t.setPorcentaje(rs.getDouble("porcentaje"));
                lista.add(t);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }
}

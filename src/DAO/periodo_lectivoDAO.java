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
import Modelo.periodo_lectivo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class periodo_lectivoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<periodo_lectivo> todos() throws SQLException {
        ArrayList<periodo_lectivo> lista = new ArrayList<periodo_lectivo>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM periodo_lectivo "
                + " ORDER BY periodo_lectivo.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                periodo_lectivo ba = new periodo_lectivo();
                ba.setCodigo(rs.getInt("codigo"));
                ba.setNombre(rs.getString("nombre"));
                lista.add(ba);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public periodo_lectivo buscarId(int id) throws SQLException {

        periodo_lectivo b = new periodo_lectivo();
        b.setCodigo(0);
        b.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select codigo,nombre  "
                    + " from periodo_lectivo "
                    + "where periodo_lectivo.codigo = ? "
                    + "order by periodo_lectivo.codigo ";
            
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    b.setCodigo(rs.getInt("codigo"));
                    b.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return b;
    }

}

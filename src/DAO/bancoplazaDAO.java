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
import Modelo.bancoplaza;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class bancoplazaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<bancoplaza> todos() throws SQLException {
        ArrayList<bancoplaza> lista = new ArrayList<bancoplaza>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM bancos_plaza "
                + " ORDER BY bancos_plaza.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bancoplaza ba = new bancoplaza();
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

    public bancoplaza buscarId(int id) throws SQLException {

        bancoplaza b = new bancoplaza();
        b.setCodigo(0);
        b.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select codigo,nombre  "
                    + " from bancos_plaza "
                    + "where bancos_plaza.codigo = ? "
                    + "order by bancos_plaza.codigo ";
            
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

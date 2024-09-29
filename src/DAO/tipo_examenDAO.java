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
import Modelo.tipo_examen;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class tipo_examenDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<tipo_examen> todos() throws SQLException {
        ArrayList<tipo_examen> lista = new ArrayList<tipo_examen>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM tipo_examen "
                + " ORDER BY tipo_examen.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tipo_examen ba = new tipo_examen();
                ba.setCodigo(rs.getString("codigo"));
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

    public tipo_examen buscarId(String id) throws SQLException {

        tipo_examen b = new tipo_examen();
        b.setCodigo("");
        b.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select codigo,nombre  "
                    + " from tipo_examen "
                    + "where tipo_examen.codigo = ? "
                    + "order by tipo_examen.codigo ";
            
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    b.setCodigo(rs.getString("codigo"));
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

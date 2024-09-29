/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.killconexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class killconexionDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<killconexion> Todos() throws SQLException {
        ArrayList<killconexion> lista = new ArrayList<killconexion>();
        con = new Conexion();
        st = con.conectar();
        try {

            String cSql = "SHOW PROCESSLIST";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    killconexion k = new killconexion();
                    k.setId(rs.getInt("Id"));
                    k.setTime(rs.getInt("Time"));
                    k.setDb(rs.getString("db"));
                    k.setUser(rs.getString("User"));
                    k.setHost(rs.getString("Host"));
                    k.setCommand(rs.getString("Command"));
                    k.setInfo(rs.getString("Info"));
                    lista.add(k);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public boolean MatarConexion(int k) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("KILL " + k);
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        st.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

}

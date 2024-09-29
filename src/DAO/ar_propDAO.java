/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ar_prop;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ar_propDAO {
    
    Conexion con = null;
    Statement st = null;
    
    
    
    public ArrayList<ar_prop> todos() throws SQLException {
        ArrayList<ar_prop> lista = new ArrayList<ar_prop>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT * FROM ar_prop "
                + " ORDER BY ar_pro_num ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ar_prop a = new ar_prop();
                a.setAr_pro_num(rs.getString("ar_pro_num"));
                a.setAr_pro_nom(rs.getString("ar_pro_nom"));
                a.setAr_pro_sup(rs.getDouble("ar_pro_sup"));
                a.setAr_pro_lot(rs.getDouble("ar_pro_lot"));
                a.setAr_pro_ven(rs.getDouble("ar_pro_ven"));
                a.setAr_pro_cte(rs.getString("ar_pro_cte"));
                a.setAr_pro_ruc(rs.getString("ar_pro_ruc"));
                a.setAr_pro_loc(rs.getString("ar_pro_loc"));
                a.setAr_pro_res(rs.getDouble("ar_pro_res"));
                a.setAr_pro_can(rs.getDouble("ar_pro_can"));
                a.setAr_pro_tit(rs.getString("ar_pro_tit"));
                a.setAr_pro_adm(rs.getDouble("ar_pro_adm"));
                a.setAr_pro_com(rs.getString("ar_pro_com"));
                a.setAr_pro_par(rs.getDouble("ar_pro_par"));
                a.setAr_pro_est(rs.getString("ar_pro_est"));
                a.setAr_pro_act(rs.getDouble("ar_pro_act"));
                a.setAr_pro_ina(rs.getDouble("ar_pro_ina"));
                a.setAr_pro_nro(rs.getDouble("ar_pro_nro"));
                a.setAr_pro_pla(rs.getDouble("ar_pro_pla"));
                a.setAr_pro_con(rs.getDouble("ar_pro_con"));
                a.setAr_propiet(rs.getDouble("ar_propiet"));
                a.setAr_comvent(rs.getDouble("ar_comvent"));
                a.setAr_comadmi(rs.getDouble("ar_comadmi"));
                a.setAr_descuen(rs.getDouble("ar_descuen"));
                a.setAr_iva(rs.getDouble("ar_iva"));
                a.setAr_impact(rs.getDouble("ar_impact"));
                a.setAr_prosup(rs.getDouble("ar_prosup"));
                a.setAr_propor(rs.getDouble("ar_propor"));
                                
                lista.add(a);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;    
    }
    
    public ar_prop insertar(ar_prop pp) throws SQLException{
        con =  new Conexion ();
        st = con.conectar ();
        PreparedStatement ps = null;
        
        ps=st.getConnection().prepareStatement("INSERT INTO ar_prop(ar_pro_num,ar_pro_nom,ar_pro_loc,ar_pro_sup,ar_pro_cte,ar_pro_ruc,ar_pro_can,ar_pro_ven,ar_pro_lot,ar_pro_tit,ar_pro_adm,ar_pro_com,ar_propiet,ar_pro_con,ar_comadmi,ar_comvent,ar_pro_nro,ar_pro_pla,ar_impact) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, pp.getAr_pro_num());
        ps.setString(2, pp.getAr_pro_nom());
        ps.setString(3, pp.getAr_pro_loc());
        ps.setString(4, pp.getAr_pro_loc());
        ps.setDouble(5, pp.getAr_pro_sup());
        ps.setString(6, pp.getAr_pro_cte());
        ps.setString(7, pp.getAr_pro_ruc());
        ps.setDouble(8, pp.getAr_pro_can());
        ps.setDouble(9, pp.getAr_pro_ven());
        ps.setDouble(10, pp.getAr_pro_lot());
        ps.setString(11, pp.getAr_pro_tit());
        ps.setDouble(12, pp.getAr_pro_adm());
        ps.setString(13, pp.getAr_pro_com());
        ps.setDouble(14, pp.getAr_propiet());
        ps.setDouble(15, pp.getAr_pro_con());
        ps.setDouble(16, pp.getAr_comadmi());
        ps.setDouble(17, pp.getAr_comvent());
        ps.setDouble(18, pp.getAr_pro_pla());
        ps.setDouble(19, pp.getAr_impact());
        ps.executeUpdate();
        st.close();
        ps.close();
        return pp;
        
                
    }
}
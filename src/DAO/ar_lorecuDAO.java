/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ar_lorecu;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ar_lorecuDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<ar_lorecu> todos() throws SQLException, SQLException {
        ArrayList<ar_lorecu> lista = new ArrayList<ar_lorecu>();
        con = new Conexion();
        st = con.conectar();
        String sql = " SELECT * FROM ar_lorecu "
                + " ORDER BY ar_act_cod ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ar_lorecu a = new ar_lorecu();
                a.setAr_act_fra(rs.getInt("ar_act_fra"));
                a.setAr_act_man(rs.getInt("act_act_man"));
                a.setAr_act_lot(rs.getInt("ar_act_lot"));
                a.setRucclie(rs.getInt("rucclie"));
                a.setAr_act_est(rs.getString("ar_act_est"));
                a.setAr_act_fec(rs.getDate("ar_act_fec"));
                a.setAr_act_ult(rs.getDate("ar_act_ult"));
                a.setAr_act_cuo(rs.getInt("ar_act_cuo"));
                a.setAr_act_pag(rs.getInt("ar_act_pag"));
                a.setFechrecu(rs.getDate("ferhrecu"));
                a.setAr_act_cid(rs.getInt("ar_act_cid"));
                a.setAr_act_cod(rs.getInt("ar_act_cod"));
                a.setAr_act_con(rs.getString("ar_act_con"));
                a.setCeduven(rs.getInt("ceduven"));
                a.setAr_act_cli(rs.getString("ar_act_cli"));
                a.setAt_act_dir(rs.getString("act_act_dir"));
                a.setAr_act_imp(rs.getInt("ar_act_imp"));
                a.setAr_act_mes(rs.getDate("ar_act_mes"));
                a.setAr_act_fon(rs.getInt("ar_act_fon"));
                a.setAr_act_co1(rs.getInt("ar_act_co1"));
                a.setAr_act_cu1(rs.getInt("ar_act_cu1"));
                a.setAr_act_cu2(rs.getInt("ar_act_cu2"));
                a.setAr_act_cu3(rs.getInt("ar_act_cu3"));
                a.setAr_act_cu4(rs.getInt("ar_act_cu4"));
                a.setAr_act_cu5(rs.getInt("ar_act_cu5"));
                a.setAr_act_cu6(rs.getInt("ar_act_cu6"));
                a.setAr_act_cu7(rs.getInt("ar_act_cu7"));
                a.setAr_act_cu8(rs.getInt("ar_act_cu8"));
                a.setAr_act_cu9(rs.getInt("ar_act_cu9"));
                a.setAr_act_cu0(rs.getInt("ar_act_cu0"));
                a.setAr_act_gu1(rs.getInt("ar_act_gu1"));
                a.setAr_act_gu2(rs.getInt("ar_act_gu2"));
                a.setAr_act_gu3(rs.getInt("ar_act_gu3"));
                a.setAr_act_gu4(rs.getInt("ar_act_gu4"));
                a.setAr_act_gu5(rs.getInt("ar_act_gu5"));
                a.setAr_act_gu6(rs.getInt("ar_act_gu6"));
                a.setAr_act_gu7(rs.getInt("ar_act_gu7"));
                a.setAr_act_gu8(rs.getInt("ar_act_gu8"));
                a.setAr_act_gu9(rs.getInt("ar_act_gu9"));
                a.setAr_act_gu0(rs.getInt("ar_act_gu0"));
                a.setAr_act_eda(rs.getInt("ar_act_eda"));
                a.setAr_act_nac(rs.getString("ar_act_nac"));
                a.setAr_act_civ(rs.getInt("ar_act_civ"));
                a.setAr_act_prov(rs.getString("ar_act_prov"));
                a.setAr_act_lug(rs.getString("ar_act_lug"));
                a.setAr_act_lug1(rs.getString("ar_act_lug1"));
                a.setNumero(rs.getInt("numero"));
                lista.add(a);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    
    public ar_lorecu insertar(ar_lorecu lor) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO ar_lorecu (ar_act_fra,ar_act_man,ar_act_lot,rucclie,ar_act_est,ar_act_fec,ar_act_ult,ar_act_cuo,ar_act_pag,fechrecu,ar_act_cid,ar_act_cod,ar_act_con,ceduven,ar_act_cli,ar_act_dir,ar_act_imp,ar_act_mes,ar_act_fon,ar_act_co1,ar_act_cu1,ar_act_cu2,ar_act_cu3,ar_act_cu4,ar_act_cu5,ar_act_cu6,ar_act_cu7,ar_act_cu8,ar_act_cu9,ar_act_cu0,ar_act_gu1,ar_act_gu2,ar_act_gu3,ar_act_gu4,ar_act_gu5,ar_act_gu6,ar_act_gu7,ar_act_gu8,ar_act_gu9,ar_act_gu0,ar_act_eda,ar_act_nac,ar_act_civ,ar_act_prov,ar_act_lug,ar_act_lu1,numero) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, lor.getAr_act_fra());
        ps.setInt(2, lor.getAr_act_man());
        ps.setInt(3, lor.getAr_act_lot());
        ps.setInt(3, lor.getRucclie());
        ps.setString(5, lor.getAr_act_est());
        ps.setDate(6, lor.getAr_act_fec());
        ps.setDate(7, lor.getAr_act_ult());
        ps.setInt(8, lor.getAr_act_cuo());
        ps.setInt(9, lor.getAr_act_pag());
        ps.setDate(10, lor.getFechrecu());
        ps.setInt(11, lor.getAr_act_cid());
        ps.setInt(12, lor.getAr_act_cod());
        ps.setString(13, lor.getAr_act_con());
        ps.setInt(14, lor.getCeduven());
        ps.setString(15, lor.getAr_act_cli());
        ps.setString(16, lor.getAr_act_dir());
        ps.setInt(17, lor.getAr_act_imp());
        ps.setDate(18, lor.getAr_act_mes());
        ps.setInt(19, lor.getAr_act_fon());
        ps.setInt(20, lor.getAr_act_co1());
        ps.setInt(21, lor.getAr_act_cu1());
        ps.setInt(22, lor.getAr_act_cu2());
        ps.setInt(23, lor.getAr_act_cu3());
        ps.setInt(24, lor.getAr_act_cu4());
        ps.setInt(25, lor.getAr_act_cu5());
        ps.setInt(26, lor.getAr_act_cu6());
        ps.setInt(27, lor.getAr_act_cu7());
        ps.setInt(28, lor.getAr_act_cu8());
        ps.setInt(29, lor.getAr_act_cu9());
        ps.setInt(30, lor.getAr_act_cu0());
        ps.setInt(31, lor.getAr_act_gu1());
        ps.setInt(32, lor.getAr_act_gu2());
        ps.setInt(33, lor.getAr_act_gu3());
        ps.setInt(34, lor.getAr_act_gu4());
        ps.setInt(35, lor.getAr_act_gu5());
        ps.setInt(36, lor.getAr_act_gu6());
        ps.setInt(37, lor.getAr_act_gu7());
        ps.setInt(38, lor.getAr_act_gu8());
        ps.setInt(39, lor.getAr_act_gu9());
        ps.setInt(40, lor.getAr_act_gu0());
        ps.setInt(41, lor.getAr_act_eda());
        ps.setString(42, lor.getAr_act_nac());
        ps.setInt(43, lor.getAr_act_civ());
        ps.setString(44, lor.getAr_act_prov());
        ps.setString(45, lor.getAr_act_lug());
        ps.setString(46, lor.getAr_act_lug1());
        ps.setInt(47, lor.getNumero());
        ps.executeUpdate();
        ps.close();
        return lor;

    }

    public boolean actualizar(ar_lorecu lor) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE SET ar_lorecu ar_act_fra=?,ar_act_man=?,ar_act_lot=?,rucclie=?,ar_act_est=?,ar_act_fec=?,ar_act_ult=?,ar_act_cuo=?,ar_act_pag=?,fechrecu=?,ar_act_cid=?,ar_act_cod=?,ar_act_con=?,ceduven=?,ar_act_cli=?,ar_act_dir=?,ar_act_imp=?,ar_act_mes=?,ar_act_fon=?,ar_act_co1=?,ar_act_cu1=?,ar_act_cu2=?,ar_act_cu3=?,ar_act_cu4=?,ar_act_cu5=?,ar_act_cu6=?,ar_act_cu7=?,ar_act_cu8=?,ar_act_cu9=?,ar_act_cu0=?,ar_act_gu1=?,ar_act_gu2=?,ar_act_gu3=?,ar_act_gu4=?,ar_act_gu5=?,ar_act_gu6=?,ar_act_gu7=?,ar_act_gu8=?,ar_act_gu9=?,ar_act_gu0=?,ar_act_eda=?,ar_act_nac=?,ar_act_civ=?,ar_act_prov=?,ar_act_lug=?,ar_act_lu1=?,numero=? WHERE ar_act_fra =" + lor.getAr_act_fra());
        ps.setInt(1, lor.getAr_act_fra());
        ps.setInt(2, lor.getAr_act_man());
        ps.setInt(3, lor.getAr_act_lot());
        ps.setInt(3, lor.getRucclie());
        ps.setString(5, lor.getAr_act_est());
        ps.setDate(6, lor.getAr_act_fec());
        ps.setDate(7, lor.getAr_act_ult());
        ps.setInt(8, lor.getAr_act_cuo());
        ps.setInt(9, lor.getAr_act_pag());
        ps.setDate(10, lor.getFechrecu());
        ps.setInt(11, lor.getAr_act_cid());
        ps.setInt(12, lor.getAr_act_cod());
        ps.setString(13, lor.getAr_act_con());
        ps.setInt(14, lor.getCeduven());
        ps.setString(15, lor.getAr_act_cli());
        ps.setString(16, lor.getAr_act_dir());
        ps.setInt(17, lor.getAr_act_imp());
        ps.setDate(18, lor.getAr_act_mes());
        ps.setInt(19, lor.getAr_act_fon());
        ps.setInt(20, lor.getAr_act_co1());
        ps.setInt(21, lor.getAr_act_cu1());
        ps.setInt(22, lor.getAr_act_cu2());
        ps.setInt(23, lor.getAr_act_cu3());
        ps.setInt(24, lor.getAr_act_cu4());
        ps.setInt(25, lor.getAr_act_cu5());
        ps.setInt(26, lor.getAr_act_cu6());
        ps.setInt(27, lor.getAr_act_cu7());
        ps.setInt(28, lor.getAr_act_cu8());
        ps.setInt(29, lor.getAr_act_cu9());
        ps.setInt(30, lor.getAr_act_cu0());
        ps.setInt(31, lor.getAr_act_gu1());
        ps.setInt(32, lor.getAr_act_gu2());
        ps.setInt(33, lor.getAr_act_gu3());
        ps.setInt(34, lor.getAr_act_gu4());
        ps.setInt(35, lor.getAr_act_gu5());
        ps.setInt(36, lor.getAr_act_gu6());
        ps.setInt(37, lor.getAr_act_gu7());
        ps.setInt(38, lor.getAr_act_gu8());
        ps.setInt(39, lor.getAr_act_gu9());
        ps.setInt(40, lor.getAr_act_gu0());
        ps.setInt(41, lor.getAr_act_eda());
        ps.setString(42, lor.getAr_act_nac());
        ps.setInt(43, lor.getAr_act_civ());
        ps.setString(44, lor.getAr_act_prov());
        ps.setString(45, lor.getAr_act_lug());
        ps.setString(46, lor.getAr_act_lug1());
        ps.setInt(47, lor.getNumero());

        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean eliminar(String ar_act_fra) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM ar_lorecu WHERE ar_act_fra=?");
        ps.setString(1, ar_act_fra);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }

    }

    public ar_lorecu buscar(String ar_act_fra) throws SQLException {
        ar_lorecu lor = new ar_lorecu();
        lor.setAr_act_fra(0);
        lor.setAr_act_man(0);
        lor.setAr_act_lot(0);
        lor.setRucclie(0);
        lor.setAr_act_est("");

        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select ar_lorecu.ar_act_fra,ar_lorecu.ar_act_man,ar_lorecu.ar_act_lot,ar_lorecu.rucclie,ar_lorecu.ar_act_est,ar_lorecu.ar_act_fec,ar_lorecu.ar_act_ult,ar_lorecu.ar_act_cuo,ar_lorecu.ar_act_pag,ar_lorecu.fechrecu,ar_lorecu.ar_act_cid,ar_lorecu.ar_act_cod,ar_lorecu.ar_act_con,ar_lorecu.ceduven,ar_lorecu.ar_act_cli,ar_lorecu.ar_act_dir,ar_lorecu.ar_act_imp,ar_lorecu.ar_act_mes,ar_lorecu.ar_act_fon,ar_lorecu.ar_act_co1,ar_lorecu.ar_act_cu1,ar_lorecu.ar_act_cu2,ar_lorecu.ar_act_cu3,ar_lorecu.ar_act_cu4,ar_lorecu.ar_act_cu5,ar_lorecu.ar_act_cu6,ar_lorecu.ar_act_cu7,ar_lorecu.ar_act_cu8,ar_lorecu.ar_act_cu9,ar_lorecu.ar_act_cu0,ar_lorecu.ar_act_gu1,ar_lorecu.ar_act_gu2,ar_lorecu.ar_act_gu3,ar_lorecu.ar_act_gu4,ar_lorecu.ar_act_gu5,ar_lorecu.ar_act_gu6,ar_lorecu.ar_act_gu7,ar_lorecu.ar_act_gu8,ar_lorecu.ar_act_gu9,ar_lorecu.ar_act_gu0,ar_lorecu.ar_act_eda,ar_lorecu.ar_act_nac,ar_lorecu.ar_act_civ,ar_lorecu.ar_act_prov,ar_lorecu.ar_act_lug,ar_lorecu.ar_act_lu1,ar_lorecu.numero "
                    + " from ar_lorecu "
                    + "order by ar_lorecu.ar_act_fra ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, ar_act_fra);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    lor.setAr_act_fra(rs.getInt("ar_act_fra"));
                    lor.setAr_act_man(rs.getInt("ar_act_man"));
                    lor.setAr_act_lot(rs.getInt("ar_act_lot"));
                    lor.setRucclie(rs.getInt("rucclie"));
                    lor.setAr_act_est(rs.getString("ar_act_est"));
                    lor.setAr_act_fec(rs.getDate("ar_act_fec"));
                    lor.setAr_act_ult(rs.getDate("ar_act_ult"));
                    lor.setAr_act_cuo(rs.getInt("ar_act_cuo"));
                    lor.setAr_act_pag(rs.getInt("ar_act_pag"));
                    lor.setFechrecu(rs.getDate("fechrecu"));
                    lor.setAr_act_cid(rs.getInt("ar_act_cid"));
                    lor.setAr_act_cod(rs.getInt("ar_act_cod"));
                    lor.setAr_act_con(rs.getString("ar_act_con"));
                    lor.setCeduven(rs.getInt("ceduven"));
                    lor.setAr_act_cli(rs.getString("ar_act_cli"));
                    lor.setAr_act_dir(rs.getString("ar_act_dir"));
                    lor.setAr_act_imp(rs.getInt("ar_act_imp"));
                    lor.setAr_act_mes(rs.getDate("ar_act_mes"));
                    lor.setAr_act_fon(rs.getInt("ar_act_fon"));
                    lor.setAr_act_co1(rs.getInt("ar_act_co1"));
                    lor.setAr_act_cu1(rs.getInt("ar_act_cu1"));
                    lor.setAr_act_cu2(rs.getInt("ar_act_cu2"));
                    lor.setAr_act_cu3(rs.getInt("ar_act_cu3"));
                    lor.setAr_act_cu4(rs.getInt("ar_act_cu4"));
                    lor.setAr_act_cu5(rs.getInt("ar_act_cu5"));
                    lor.setAr_act_cu6(rs.getInt("ar_act_cu6"));
                    lor.setAr_act_cu7(rs.getInt("ar_act_cu7"));
                    lor.setAr_act_cu8(rs.getInt("ar_act_cu8"));
                    lor.setAr_act_cu9(rs.getInt("ar_act_cu9"));
                    lor.setAr_act_cu0(rs.getInt("ar_act_cu0"));
                    lor.setAr_act_gu1(rs.getInt("ar_act_gu1"));
                    lor.setAr_act_gu2(rs.getInt("ar_act_gu2"));
                    lor.setAr_act_gu3(rs.getInt("ar_act_gu3"));
                    lor.setAr_act_gu4(rs.getInt("ar_act_gu4"));
                    lor.setAr_act_gu5(rs.getInt("ar_act_gu5"));
                    lor.setAr_act_gu6(rs.getInt("ar_act_gu6"));
                    lor.setAr_act_gu7(rs.getInt("ar_act_gu7"));
                    lor.setAr_act_gu8(rs.getInt("ar_act_gu8"));
                    lor.setAr_act_gu9(rs.getInt("ar_act_gu9"));
                    lor.setAr_act_gu0(rs.getInt("ar_act_gu0"));
                    lor.setAr_act_eda(rs.getInt("ar_act_eda"));
                    lor.setAr_act_nac(rs.getString("ar_act_nac"));
                    lor.setAr_act_civ(rs.getInt("ar_act_civ"));
                    lor.setAr_act_prov(rs.getString("ar_act_prov"));
                    lor.setAr_act_lug(rs.getString("ar_act_lug"));
                    lor.setAr_act_lu1(rs.getString("ar_act_lu1"));
                    lor.setNumero(rs.getInt("numero"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lor;

    }

}

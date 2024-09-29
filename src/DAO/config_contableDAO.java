
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.config_contable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuario
 */
public class config_contableDAO {

    Conexion con = null;
    Statement st = null;

    public config_contable consultar() {
        config_contable config = new config_contable();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT representante_legal,contador,ruc_contador,"
                    + "ventacontado,ventacredito,ivacompra10,ivacompra5,"
                    + "ivaventa10,ivaventa5,resultado_anterior,resultado_actual,"
                    + "periodocontable,retencioncompra,retencionventa,"
                    + "gastos,compras,pagos,cobranzas,depositos,extracciones,"
                    + "fondofijo,ventas,ventaexenta,"
                    + "COALESCE(vco.nombreventacontado,'') AS nombreventacontado,"
                    + "COALESCE(vcr.nombreventacredito,'') AS nombreventacredito,"
                    + "COALESCE(vexe.nombreventaexenta,'') AS nombreventaexenta,"
                    + "COALESCE(ivac10.nombreivacompra10,'') AS nombreivacompra10,"
                    + "COALESCE(ivac5.nombreivacompra5,'') AS nombreivacompra5,"
                    + "COALESCE(ivav10.nombreivaventa10,'') AS nombreivaventa10,"
                    + "COALESCE(ivav5.nombreivaventa5,'') AS nombreivaventa5,"
                    + "COALESCE(rh.nombreresultadoanterior,'') AS nombreresultadoanterior,"
                    + "COALESCE(ra.nombreresultadoactual,'') AS nombreresultadoactual,"
                    + "COALESCE(retc.nombreretencioncompra,'') AS nombreretencioncompra,"
                    + "COALESCE(retv.nombreretencionventa,'') AS nombreretencionventa "
                    + "FROM config_contable c "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreventacontado "
                    + "FROM plan) vco ON vco.codigo =c.ventacontado "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreventacredito "
                    + "FROM plan) vcr ON vcr.codigo =c.ventacredito "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreventaexenta "
                    + "FROM plan) vexe ON vexe.codigo =c.ventaexenta "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreivacompra10 "
                    + "FROM plan) ivac10 ON ivac10.codigo =c.ivacompra10 "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreivacompra5 "
                    + "FROM plan) ivac5 ON ivac5.codigo =c.ivacompra5 "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreivaventa10 "
                    + "FROM plan) ivav10 ON ivav10.codigo =c.ivaventa10 "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreivaventa5 "
                    + "FROM plan) ivav5 ON ivav5.codigo =c.ivaventa5 "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreresultadoanterior "
                    + "FROM plan) rh ON rh.codigo =c.resultado_anterior "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreresultadoactual "
                    + "FROM plan) ra ON ra.codigo =c.resultado_actual "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreretencioncompra "
                    + "FROM plan) retc ON retc.codigo =c.retencioncompra "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreretencionventa "
                    + "FROM plan) retv ON retv.codigo =c.retencionventa ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    config.setRepresentante_legal(rs.getString("representante_legal"));
                    config.setContador(rs.getString("contador"));
                    config.setRuc_contador(rs.getString("ruc_contador"));
                    config.setVentacontado(rs.getString("ventacontado"));
                    config.setVentacredito(rs.getString("ventacredito"));
                    config.setIvacompra10(rs.getString("ivacompra10"));
                    config.setIvacompra5(rs.getString("ivacompra5"));
                    config.setIvaventa10(rs.getString("ivaventa10"));
                    config.setIvaventa5(rs.getString("ivaventa5"));
                    config.setResultado_anterior(rs.getString("resultado_anterior"));
                    config.setResultado_actual(rs.getString("resultado_actual"));
                    config.setRetencioncompra(rs.getString("retencioncompra"));
                    config.setRetencionventa(rs.getString("retencionventa"));
                    config.setVentaexenta(rs.getString("ventaexenta"));
                    config.setPeriodocontable(rs.getInt("periodocontable"));
                    config.setGastos(rs.getInt("gastos"));
                    config.setCompras(rs.getInt("compras"));
                    config.setPagos(rs.getInt("pagos"));
                    config.setCobranzas(rs.getInt("cobranzas"));
                    config.setDepositos(rs.getInt("depositos"));
                    config.setExtracciones(rs.getInt("extracciones"));
                    config.setFondofijo(rs.getInt("fondofijo"));
                    config.setVentas(rs.getInt("ventas"));
                    config.setNombreventacontado(rs.getString("nombreventacontado"));
                    config.setNombreventacredito(rs.getString("nombreventacredito"));
                    config.setNombreventaexenta(rs.getString("nombreventaexenta"));
                    config.setNombreivacompra10(rs.getString("nombreivacompra10"));
                    config.setNombreivacompra5(rs.getString("nombreivacompra5"));
                    config.setNombreivaventa10(rs.getString("nombreivaventa10"));
                    config.setNombreivaventa5(rs.getString("nombreivaventa5"));
                    config.setNombreresultadoanterior(rs.getString("nombreresultadoanterior"));
                    config.setNombreresultadoactual(rs.getString("nombreresultadoactual"));
                    config.setNombreretencioncompra(rs.getString("nombreretencioncompra"));
                    config.setNombreretencionventa(rs.getString("nombreretencionventa"));

                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->CONFIG " + ex.getLocalizedMessage());
        }
        return config;
    }

    public boolean ConfigContable(config_contable config) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE config_contable SET representante_legal=?,"
                + "contador=?,"
                + "ruc_contador=?,"
                + "ventacontado=?,"
                + "ventacredito=?,"
                + "ivacompra10=?,"
                + "ivacompra5=?,"
                + "ivaventa10=?,"
                + "ivaventa5=?,"
                + "resultado_anterior=?,"
                + "resultado_actual=?,"
                + "periodocontable=?,"
                + "retencioncompra=?,"
                + "retencionventa=?,"
                + "gastos=?,"
                + "compras=?,"
                + "pagos=?,"
                + "cobranzas=?,"
                + "depositos=?,"
                + "extracciones=?,"
                + "fondofijo=?,"
                + "ventas=?,"
                + "ventaexenta=?");
        ps.setString(1, config.getRepresentante_legal());
        ps.setString(2, config.getContador());
        ps.setString(3, config.getRuc_contador());
        ps.setString(4, config.getVentacontado());
        ps.setString(5, config.getVentacredito());
        ps.setString(6, config.getIvacompra10());
        ps.setString(7, config.getIvacompra5());
        ps.setString(8, config.getIvaventa10());
        ps.setString(9, config.getIvaventa5());
        ps.setString(10, config.getResultado_anterior());
        ps.setString(11, config.getResultado_actual());
        ps.setInt(12, config.getPeriodocontable());
        ps.setString(13, config.getRetencioncompra());
        ps.setString(14, config.getRetencionventa());
        ps.setInt(15, config.getGastos());
        ps.setInt(16, config.getCompras());
        ps.setInt(17, config.getPagos());
        ps.setInt(18, config.getCobranzas());
        ps.setInt(19, config.getDepositos());
        ps.setInt(20, config.getExtracciones());
        ps.setInt(21, config.getFondofijo());
        ps.setInt(22, config.getVentas());
        ps.setString(23, config.getVentaexenta());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }



    public config_contable consultarCbsa() {
        config_contable config = new config_contable();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT representante_legal,contador,ruc_contador,"
                    + "ventacontado,ventacredito,ivacompra10,ivacompra5,"
                    + "ivaventa10,ivaventa5,resultado_anterior,resultado_actual,"
                    + "periodocontable,retencioncompra,retencionventa,"
                    + "gastos,compras,pagos,cobranzas,depositos,extracciones,"
                    + "fondofijo,ventas,ventaexenta,"
                    + "compensacion_gs,compensacion_usd,"
                    + "COALESCE(vco.nombreventacontado,'') AS nombreventacontado,"
                    + "COALESCE(vcr.nombreventacredito,'') AS nombreventacredito,"
                    + "COALESCE(vexe.nombreventaexenta,'') AS nombreventaexenta,"
                    + "COALESCE(ivac10.nombreivacompra10,'') AS nombreivacompra10,"
                    + "COALESCE(ivac5.nombreivacompra5,'') AS nombreivacompra5,"
                    + "COALESCE(ivav10.nombreivaventa10,'') AS nombreivaventa10,"
                    + "COALESCE(ivav5.nombreivaventa5,'') AS nombreivaventa5,"
                    + "COALESCE(rh.nombreresultadoanterior,'') AS nombreresultadoanterior,"
                    + "COALESCE(ra.nombreresultadoactual,'') AS nombreresultadoactual,"
                    + "COALESCE(retc.nombreretencioncompra,'') AS nombreretencioncompra,"
                    + "COALESCE(retv.nombreretencionventa,'') AS nombreretencionventa, "
                    + "COALESCE(cusd.nombrecompensacionusd,'') AS nombrecompensacionusd, "
                    + "COALESCE(cugs.nombrecompensaciongs,'') AS nombrecompensaciongs "
                    + "FROM config_contable c "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreventacontado "
                    + "FROM plan) vco ON vco.codigo =c.ventacontado "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreventacredito "
                    + "FROM plan) vcr ON vcr.codigo =c.ventacredito "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreventaexenta "
                    + "FROM plan) vexe ON vexe.codigo =c.ventaexenta "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreivacompra10 "
                    + "FROM plan) ivac10 ON ivac10.codigo =c.ivacompra10 "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreivacompra5 "
                    + "FROM plan) ivac5 ON ivac5.codigo =c.ivacompra5 "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreivaventa10 "
                    + "FROM plan) ivav10 ON ivav10.codigo =c.ivaventa10 "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreivaventa5 "
                    + "FROM plan) ivav5 ON ivav5.codigo =c.ivaventa5 "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreresultadoanterior "
                    + "FROM plan) rh ON rh.codigo =c.resultado_anterior "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreresultadoactual "
                    + "FROM plan) ra ON ra.codigo =c.resultado_actual "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreretencioncompra "
                    + "FROM plan) retc ON retc.codigo =c.retencioncompra "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombreretencionventa "
                    + "FROM plan) retv ON retv.codigo =c.retencionventa "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombrecompensacionusd "
                    + "FROM plan) cusd ON cusd.codigo =c.compensacion_usd "
                    + "LEFT JOIN (SELECT codigo,nombre AS nombrecompensaciongs "
                    + "FROM plan) cugs ON cugs.codigo =c.compensacion_gs ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    config.setRepresentante_legal(rs.getString("representante_legal"));
                    config.setContador(rs.getString("contador"));
                    config.setRuc_contador(rs.getString("ruc_contador"));
                    config.setVentacontado(rs.getString("ventacontado"));
                    config.setVentacredito(rs.getString("ventacredito"));
                    config.setIvacompra10(rs.getString("ivacompra10"));
                    config.setIvacompra5(rs.getString("ivacompra5"));
                    config.setIvaventa10(rs.getString("ivaventa10"));
                    config.setIvaventa5(rs.getString("ivaventa5"));
                    config.setCompensacion_usd(rs.getString("compensacion_usd"));
                    config.setCompensacion_gs(rs.getString("compensacion_gs"));
                    config.setResultado_anterior(rs.getString("resultado_anterior"));
                    config.setResultado_actual(rs.getString("resultado_actual"));
                    config.setRetencioncompra(rs.getString("retencioncompra"));
                    config.setRetencionventa(rs.getString("retencionventa"));
                    config.setVentaexenta(rs.getString("ventaexenta"));
                    config.setPeriodocontable(rs.getInt("periodocontable"));
                    config.setGastos(rs.getInt("gastos"));
                    config.setCompras(rs.getInt("compras"));
                    config.setPagos(rs.getInt("pagos"));
                    config.setCobranzas(rs.getInt("cobranzas"));
                    config.setDepositos(rs.getInt("depositos"));
                    config.setExtracciones(rs.getInt("extracciones"));
                    config.setFondofijo(rs.getInt("fondofijo"));
                    config.setVentas(rs.getInt("ventas"));
                    config.setNombreventacontado(rs.getString("nombreventacontado"));
                    config.setNombreventacredito(rs.getString("nombreventacredito"));
                    config.setNombreventaexenta(rs.getString("nombreventaexenta"));
                    config.setNombreivacompra10(rs.getString("nombreivacompra10"));
                    config.setNombreivacompra5(rs.getString("nombreivacompra5"));
                    config.setNombreivaventa10(rs.getString("nombreivaventa10"));
                    config.setNombreivaventa5(rs.getString("nombreivaventa5"));
                    config.setNombreresultadoanterior(rs.getString("nombreresultadoanterior"));
                    config.setNombreresultadoactual(rs.getString("nombreresultadoactual"));
                    config.setNombreretencioncompra(rs.getString("nombreretencioncompra"));
                    config.setNombreretencionventa(rs.getString("nombreretencionventa"));
                    config.setNombrecompensacion_usd(rs.getString("nombrecompensacionusd"));
                    config.setNombrecompensacion_gs(rs.getString("nombrecompensaciongs"));
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->CONFIG " + ex.getLocalizedMessage());
        }
        return config;
    }


public boolean ConfigContableCbsa(config_contable config) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE config_contable SET representante_legal=?,"
                + "contador=?,"
                + "ruc_contador=?,"
                + "ventacontado=?,"
                + "ventacredito=?,"
                + "ivacompra10=?,"
                + "ivacompra5=?,"
                + "ivaventa10=?,"
                + "ivaventa5=?,"
                + "resultado_anterior=?,"
                + "resultado_actual=?,"
                + "periodocontable=?,"
                + "retencioncompra=?,"
                + "retencionventa=?,"
                + "gastos=?,"
                + "compras=?,"
                + "pagos=?,"
                + "cobranzas=?,"
                + "depositos=?,"
                + "extracciones=?,"
                + "fondofijo=?,"
                + "ventas=?,"
                + "ventaexenta=?,compensacion_gs=?,compensacion_usd=?");
        ps.setString(1, config.getRepresentante_legal());
        ps.setString(2, config.getContador());
        ps.setString(3, config.getRuc_contador());
        ps.setString(4, config.getVentacontado());
        ps.setString(5, config.getVentacredito());
        ps.setString(6, config.getIvacompra10());
        ps.setString(7, config.getIvacompra5());
        ps.setString(8, config.getIvaventa10());
        ps.setString(9, config.getIvaventa5());
        ps.setString(10, config.getResultado_anterior());
        ps.setString(11, config.getResultado_actual());
        ps.setInt(12, config.getPeriodocontable());
        ps.setString(13, config.getRetencioncompra());
        ps.setString(14, config.getRetencionventa());
        ps.setInt(15, config.getGastos());
        ps.setInt(16, config.getCompras());
        ps.setInt(17, config.getPagos());
        ps.setInt(18, config.getCobranzas());
        ps.setInt(19, config.getDepositos());
        ps.setInt(20, config.getExtracciones());
        ps.setInt(21, config.getFondofijo());
        ps.setInt(22, config.getVentas());
        ps.setString(23, config.getVentaexenta());
        ps.setString(24, config.getCompensacion_gs());
        ps.setString(25, config.getCompensacion_usd());
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

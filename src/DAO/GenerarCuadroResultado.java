package DAO;

import Clases.Config;
import Conexion.Conexion;
import Modelo.BalanceGeneral;
import Modelo.config_contable;
import Utiles.Util;
import Modelo.plan;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class GenerarCuadroResultado {

    Conexion con = null;
    Statement st = null;

    public ArrayList<plan> GenerarCuadro(int suc, Date fechaI, Date fechaF) throws SQLException {
        ArrayList<plan> lista = new ArrayList<plan>();

        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        int nContador = 0;
        //EXTRAIGO INFORMACION DEL CONFIG CONTABLE
        //LAS CUENTAS DE RESULTADOS
        config_contableDAO configDAO = new config_contableDAO();
        config_contable config = new config_contable();
        config = configDAO.consultar();
        try {
            String sqldatos = "";
            //ASIENTOS NORMALES    
            //CREO UNA TABLA TEMPORAL DE DATOS
            //DIRECTO EN EL SERVIDOR
            //CONTIENE LOS MOVIMIENTOS

            sqldatos = "CREATE TEMPORARY TABLE datos ("
                    + "SELECT cabecera_asientos.fecha,cabecera_asientos.numero,"
                    + " plan.codigo,plan.nombre,plan.naturaleza,"
                    + " plan.tipo_cuenta,asi_codigo,"
                    + " SUM(impdebe) AS credito,"
                    + "SUM(imphaber) AS debito "
                    + " FROM cabecera_asientos "
                    + " LEFT JOIN detalle_asientos "
                    + " ON cabecera_asientos.numero=detalle_asientos.asi_asient "
                    + " LEFT JOIN plan "
                    + " ON plan.codigo=detalle_asientos.asi_codigo "
                    + " WHERE plan.tipo_cuenta>3 "
                    + " AND cabecera_asientos.fecha BETWEEN '" + fechaI + "' AND '" + fechaF + "' ";
            if (suc != 0) {
                sqldatos = sqldatos + " AND cabecera_asientos.sucursal= " + suc;
            }
            sqldatos = sqldatos + " GROUP BY asi_codigo "
                    + " ORDER BY codigo )";

            PreparedStatement psdatos = conn.prepareStatement(sqldatos);
            psdatos.executeUpdate(sqldatos);

            //SE CREA LA TABLA BALANCE PARA CARGAR EN EL LA INFORMACION
            String sqlplan = "CREATE TEMPORARY TABLE balance ("
                    + "codigo CHAR(10) NOT NULL,"
                    + "nombre CHAR(50) NOT NULL,"
                    + "naturaleza CHAR(15),"
                    + "tipo_cuenta INT(1),"
                    + "nivel INT(1),"
                    + "asentable INT(1),"
                    + "credito DECIMAL(18,4) DEFAULT 0.00,"
                    + "debito  DECIMAL(18,4) DEFAULT 0.00)";

            PreparedStatement psplan = conn.prepareStatement(sqlplan);
            psplan.executeUpdate(sqlplan);

            String sqlGrabar = "INSERT INTO balance(codigo, nombre, naturaleza, tipo_cuenta,nivel,asentable) "
                    + " SELECT codigo, nombre, naturaleza, tipo_cuenta,nivel,asentable FROM plan "
                    + " WHERE tipo_cuenta>3 ";

            PreparedStatement psplandatos = conn.prepareStatement(sqlGrabar);
            psplandatos.executeUpdate(sqlGrabar);

            String sqlcredito = "UPDATE balance SET credito=(SELECT datos.credito "
                    + " FROM datos WHERE datos.asi_codigo=balance.codigo)";
            PreparedStatement pscredito = conn.prepareStatement(sqlcredito);
            pscredito.executeUpdate(sqlcredito);

            String sqldebito = "UPDATE balance SET debito=(SELECT datos.debito "
                    + " FROM datos WHERE datos.asi_codigo=balance.codigo)";

            PreparedStatement psdebito = conn.prepareStatement(sqldebito);
            psdebito.executeUpdate(sqldebito);

            String sqlbalance = "SELECT *"
                    + " FROM balance "
                    + " ORDER BY codigo ";

            PreparedStatement psbalance = conn.prepareStatement(sqlbalance);
            ResultSet balanceGeneral = psbalance.executeQuery(sqlbalance);

            while (balanceGeneral.next()) {
                plan plan = new plan();
                plan.setCodigo(balanceGeneral.getString("codigo"));
                plan.setNombre(balanceGeneral.getString("nombre"));
                plan.setNivel(balanceGeneral.getInt("nivel"));
                plan.setCredito(balanceGeneral.getDouble("credito"));
                plan.setDebito(balanceGeneral.getDouble("debito"));
                plan.setNaturaleza(balanceGeneral.getString("naturaleza"));
                plan.setTipo_cuenta(balanceGeneral.getInt("tipo_cuenta"));
                plan.setAsentable(balanceGeneral.getInt("asentable"));
                //plan.setSaldo(balanceGeneral.getDouble("saldo"));
                lista.add(plan);
            }

            st.close();
            psdatos.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }
}

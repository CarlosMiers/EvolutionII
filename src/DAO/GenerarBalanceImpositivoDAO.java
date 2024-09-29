package DAO;

import Conexion.Conexion;
import Modelo.BalanceImpositivo;
import Utiles.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class GenerarBalanceImpositivoDAO {

    Conexion con = null;
    Statement st = null;
    ArrayList<BalanceImpositivo> arrBalanceImpositivo = new ArrayList();
    long totalDebitos = 0;
    long totalCreditos = 0;
    long totalDeudor = 0;
    long totalAcreedor = 0;
    long totalActivo = 0;
    long totalPasivoPN = 0;
    long totalPerdidas = 0;
    long totalGanancias = 0;
    String resultado_actual = "";
    String representante = "";
    String empresaContador = "";
    String rucContador = "";
    long resultadoDebitos = 0;
    long resultadoCreditos = 0;
    long resultadoDeudor = 0;
    long resultadoAcreedor = 0;
    long resultadoActivo = 0;
    long resultadoPasivoPN = 0;
    long resultadoPerdidas = 0;
    long resultadoGanancias = 0;

    public HashMap generarBalanceImpositivo(int sucursal, java.util.Date desde, java.util.Date hasta) {
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        HashMap datos = new HashMap();
        System.out.println("Generando Balance Impositivo - Inicio");
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            String sql = "SELECT resultado_actual, representante_legal, contador, ruc_contador FROM config_contable";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            if (rs.next()) {
                resultado_actual = rs.getString("resultado_actual");
                representante = rs.getString("representante_legal");
                empresaContador = rs.getString("contador");
                rucContador = rs.getString("ruc_contador");
            }
            // Almacenar Cuentas en un arraylist
            sql = "SELECT codigo AS cuenta, SUBSTRING(codigo,1,3)+0 AS corte2, "
                    + "nombre AS descripcion, nivel, tipo_cuenta, asentable "
                    + "FROM plan WHERE codigo!='" + resultado_actual + "' "
                    + "ORDER BY codigo";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                BalanceImpositivo bi = new BalanceImpositivo(rs.getString("cuenta"), rs.getString("descripcion"), rs.getInt("nivel"), rs.getInt("tipo_cuenta"), rs.getInt("asentable"), new Long("0"), new Long("0"), new Long("0"), new Long("0"), new Long("0"), new Long("0"), new Long("0"), new Long("0"), rs.getInt("corte2"));
                arrBalanceImpositivo.add(bi);
            }
            sql = "SELECT asi_codigo, nivel, tipo_cuenta, SUM(impdebe) AS debe, "
                    + "SUM(imphaber) AS haber  "
                    + "FROM detalle_asientos da "
                    + "LEFT JOIN plan p ON p.codigo=da.asi_codigo "
                    + "LEFT JOIN cabecera_asientos ca ON da.asi_asient=ca.numero "
                    + "WHERE asi_codigo!='" + resultado_actual + "' AND nivel=4 AND asentable=1 AND ";
            if (sucursal > 0) {
                sql += "ca.sucursal=" + sucursal + " AND ";
            }
            sql += "fecha>='" + fDesde + "' AND fecha<='" + fHasta + "' "
                    + "GROUP BY asi_codigo";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                long debitos = 0;
                long creditos = 0;
                long deudor = 0;
                long acreedor = 0;
                long activo = 0;
                long pasivoPN = 0;
                long perdidas = 0;
                long ganancias = 0;
                debitos = rs.getLong("debe");
                creditos = rs.getLong("haber");
                if ((debitos - creditos) > 0) {
                    deudor = (debitos - creditos);
                } else {
                    acreedor = (creditos - debitos);
                }
                switch (rs.getInt("tipo_cuenta")) {
                    case 1:
                        //ACTIVOS
                        if (deudor > 0) {
                            activo = deudor;
                        } else {
                            activo = (acreedor * -1);
                        }   break;
                    case 2:
                        //PASIVOS
                        if (acreedor > 0) {
                            pasivoPN = acreedor;
                        } else {
                            pasivoPN = (deudor * -1);
                        }   break;
                    case 3:
                        //PATRIMONIO NETO
                        if (acreedor > 0) {
                            pasivoPN = acreedor;
                        } else {
                            pasivoPN = (deudor * -1);
                        }   break;
                    case 4:
                        //GANANCIAS
                        resultadoDebitos += debitos;
                        resultadoCreditos += creditos;
                        if ((debitos - creditos) > 0) {
                            resultadoDeudor += deudor;
                            ganancias = (deudor * -1);
                        } else {
                            resultadoAcreedor += acreedor;
                            ganancias = acreedor;
                        }   resultadoGanancias += ganancias;
                        break;
                    case 5:
                        //PERDIDAS
                        resultadoDebitos += debitos;
                        resultadoCreditos += creditos;
                        if ((debitos - creditos) > 0) {
                            resultadoDeudor += deudor;
                            perdidas = deudor;
                        } else {
                            resultadoAcreedor += acreedor;
                            perdidas = (acreedor * -1);
                        }   resultadoPerdidas += perdidas;
                        break;
                    default:
                        System.out.println("CUENTA INCORRECTA ---> " + rs.getString("asi_codigo"));
                        break;
                }
                totalDebitos += debitos;
                totalCreditos += creditos;
                totalDeudor += deudor;
                totalAcreedor += acreedor;
                totalActivo += activo;
                totalPasivoPN += pasivoPN;
                totalGanancias += ganancias;
                totalPerdidas += perdidas;
                String cuenta = rs.getString("asi_codigo");
                String cuentaPadre = "";
                for (int i = cuenta.trim().length(); i > 0; i = i - 2) {
                    cuentaPadre = cuenta.substring(0, i);
                    actualizar_saldo_periodo(cuentaPadre, debitos, creditos, deudor, acreedor, activo, pasivoPN, perdidas, ganancias);
                }
            }
            if (resultadoAcreedor >= resultadoDeudor) {
                resultadoAcreedor -= resultadoDeudor;
                resultadoDeudor = 0;
                resultadoPasivoPN = resultadoAcreedor;
                resultadoGanancias = resultadoPasivoPN;
            } else if (resultadoDeudor > resultadoAcreedor) {
                resultadoDeudor -= resultadoAcreedor;
                resultadoAcreedor = 0;
                resultadoPasivoPN = (resultadoDeudor * -1);
                resultadoPerdidas = resultadoPasivoPN;
            }
            String cuentaPadre = "";
            for (int i = resultado_actual.trim().length(); i > 0; i = i - 2) {
                cuentaPadre = resultado_actual.substring(0, i);
                if (!resultado_actual.equals(cuentaPadre)) {
                    actualizar_saldo_periodo(cuentaPadre, resultadoDebitos, resultadoCreditos, resultadoDeudor, resultadoAcreedor, resultadoActivo, resultadoPasivoPN, 0, 0);
                }
            }
            st.close();
            ps.close();
            ArrayList<BalanceImpositivo> arrBalanceImpositivoSinCeros = new ArrayList();
            for (int pos = 0; pos < arrBalanceImpositivo.size(); pos++) {
                if (!(arrBalanceImpositivo.get(pos).getDebitos() == 0
                        && arrBalanceImpositivo.get(pos).getCreditos() == 0
                        && arrBalanceImpositivo.get(pos).getDeudor() == 0
                        && arrBalanceImpositivo.get(pos).getAcreedor() == 0
                        && arrBalanceImpositivo.get(pos).getActivo() == 0
                        && arrBalanceImpositivo.get(pos).getPasivo_pn() == 0
                        && arrBalanceImpositivo.get(pos).getPerdidas() == 0
                        && arrBalanceImpositivo.get(pos).getGanancias() == 0)) {
                    int len = arrBalanceImpositivo.get(pos).getNivel() - 1;
                    arrBalanceImpositivo.get(pos).setDescripcion(Util.replicar(" ", len) + arrBalanceImpositivo.get(pos).getDescripcion());
                    arrBalanceImpositivoSinCeros.add(arrBalanceImpositivo.get(pos));
                }
            }
            BalanceImpositivo bi = new BalanceImpositivo(resultado_actual, "    RESULTADO DEL EJERCICIO ACTUAL", 4, 3, 1, resultadoDebitos, resultadoCreditos, resultadoDeudor, resultadoAcreedor, resultadoActivo, resultadoPasivoPN, 0, 0, 303);
            if (resultadoPasivoPN < 0){
                bi.setGanancias(resultadoPasivoPN * -1);
                totalGanancias += bi.getGanancias();
            } else if (resultadoPasivoPN > 0){
                bi.setPerdidas(resultadoPasivoPN);
                totalPerdidas += bi.getPerdidas();
            }
            totalPasivoPN += resultadoPasivoPN;
            arrBalanceImpositivoSinCeros.add(bi);
            arrBalanceImpositivo = null;
            datos.put("balanceImpositivo", arrBalanceImpositivoSinCeros);
            datos.put("totalDebitos", totalDebitos);
            datos.put("totalCreditos", totalCreditos);
            datos.put("totalDeudor", totalDeudor);
            datos.put("totalAcreedor", totalAcreedor);
            datos.put("totalActivo", totalActivo);
            datos.put("totalPasivoPN", totalPasivoPN);
            datos.put("totalPerdidas", totalPerdidas);
            datos.put("totalGanancias", totalGanancias);
            datos.put("representante", representante);
            datos.put("empresaContador", empresaContador);
            datos.put("rucContador", rucContador);
            System.out.println("Generando Balance Impositivo - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return datos;
    }

    private boolean actualizar_saldo_periodo(String cuenta, long debitos, long creditos, long deudor, long acreedor, long activo, long pasivoPN, long perdidas, long ganancias) {
        boolean ok = true;
        for (int pos = 0; pos < arrBalanceImpositivo.size(); pos++) {
            if (arrBalanceImpositivo.get(pos).getCuenta().equals(cuenta)) {
                long aDebitos = arrBalanceImpositivo.get(pos).getDebitos();
                long aCreditos = arrBalanceImpositivo.get(pos).getCreditos();
                long aDeudor = arrBalanceImpositivo.get(pos).getDeudor();
                long aAcreedor = arrBalanceImpositivo.get(pos).getAcreedor();
                long aActivo = arrBalanceImpositivo.get(pos).getActivo();
                long aPasivoPN = arrBalanceImpositivo.get(pos).getPasivo_pn();
                long aPerdidas = arrBalanceImpositivo.get(pos).getPerdidas();
                long aGanancias = arrBalanceImpositivo.get(pos).getGanancias();
                arrBalanceImpositivo.get(pos).setDebitos(aDebitos + debitos);
                arrBalanceImpositivo.get(pos).setCreditos(aCreditos + creditos);
                arrBalanceImpositivo.get(pos).setDeudor(aDeudor + deudor);
                arrBalanceImpositivo.get(pos).setAcreedor(aAcreedor + acreedor);
                arrBalanceImpositivo.get(pos).setActivo(aActivo + activo);
                arrBalanceImpositivo.get(pos).setPasivo_pn(aPasivoPN + pasivoPN);
                arrBalanceImpositivo.get(pos).setPerdidas(aPerdidas + perdidas);
                arrBalanceImpositivo.get(pos).setGanancias(aGanancias + ganancias);
                break;
            }
        }
        return ok;
    }
}

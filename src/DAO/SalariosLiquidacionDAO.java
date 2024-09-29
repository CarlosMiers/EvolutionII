package DAO;

import Modelo.cabecera_compra;
import Conexion.Conexion;
import Modelo.comprobante;
import Modelo.marca;
import Modelo.moneda;
import Modelo.proveedor;
import Modelo.rubro;
import Modelo.sucursal;
import Modelo.SalarioLiquidacion;
import Modelo.configuracion;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class SalariosLiquidacionDAO {

    Conexion con = null;
    Statement st = null;

    public SalarioLiquidacion Agregar(SalarioLiquidacion salario) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        boolean guardado = false;
        boolean guardacuota = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement(
                "INSERT INTO planilla_salarios ("
                + "fecha,"
                + "mes,"
                + "periodo,"
                + "tipo,"
                + "sucursal,"
                + "departamento,"
                + "seccion,"
                + "giraduria,"
                + "funcionario,"
                + "salariobase,"
                + "adicionalformacion,"
                + "creditosvarios,"
                + "vacaciones,"
                + "anticipos,"
                + "horasextras,"
                + "bonificacion,"
                + "ipsaporte,"
                + "descuentosvarios,"
                + "llegadastardias,"
                + "ausencias,"
                + "embargos,"
                + "salariobruto,"
                + "salarioneto) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, salario.getFecha());
        ps.setDouble(2, salario.getMes());
        ps.setInt(3, salario.getPeriodo());
        ps.setString(4, salario.getTipo());
        ps.setInt(5, salario.getSucursal().getCodigo());
        ps.setInt(6, salario.getIdDepartamento());
        ps.setInt(7, salario.getIdSeccion());
        ps.setInt(8, salario.getIdGiraduria());
        ps.setInt(9, salario.getFuncionario());
        ps.setBigDecimal(10, salario.getSalariobase());
        ps.setBigDecimal(11, salario.getAdicionalformacion());
        ps.setBigDecimal(12, salario.getCreditosvarios());
        ps.setBigDecimal(13, salario.getVacaciones());
        ps.setBigDecimal(14, salario.getAnticipos());
        ps.setBigDecimal(15, salario.getHorasextra());
        ps.setBigDecimal(16, salario.getBonificacionH());
        ps.setBigDecimal(17, salario.getIpsaporte());
        ps.setBigDecimal(18, salario.getDescuentosvarios());
        ps.setBigDecimal(19, salario.getLlegadastardias());
        ps.setBigDecimal(20, salario.getAusencias());
        ps.setBigDecimal(21, salario.getEmbargos());
        ps.setBigDecimal(22, salario.getSalariobruto());
        ps.setBigDecimal(23, salario.getSalarioneto());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return salario;
    }

    public boolean borrarPlanilla(Date fechaI, Date fechaF, int sucursal) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM planilla_salarios WHERE fecha>=? and fecha<=? and giraduria=?");
        ps.setDate(1, fechaI);
        ps.setDate(2, fechaF);
        ps.setInt(3, sucursal);
        //System.out.println(ps.toString());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<SalarioLiquidacion> consultarLiquidacion(int sucini, Date fechaini, Date fechafin, int ntipo) throws SQLException {
        ArrayList<SalarioLiquidacion> lista = new ArrayList<SalarioLiquidacion>();
        con = new Conexion();
        st = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = new configuracion();
        config = configDAO.consultar();
        double nSalarioMinimo = config.getSalario_minimo();
        double nIps = config.getPorcentaje_ips();
        double nBon = config.getPorcentaje_bonificacion();
        System.out.println("Min: " + nSalarioMinimo + "\nIps:" + nIps + "\nBon:" + nBon);
        try {
            String cSql = null;
            if (ntipo == 0) {
                cSql = "SELECT (CASE WHEN empleados.tipo_salario=4 THEN 'MENSUAL' ELSE 'MENSUAL' END) AS tipo,"
                        + "sucursales.nombre AS sucursal,"
                        + "sucursales.codigo AS idsucursal,"
                        + "departamento_laboral.codigo AS iddepartamento,"
                        + "departamento_laboral.Nombre AS departamento,"
                        + "secciones.Nombre AS seccion,"
                        + "secciones.codigo AS idseccion,"
                        + "giradurias.codigo AS idgiraduria,"
                        + "giradurias.nombre AS giraduria,"
                        + "empleados.codigo AS funcionario,"
                        + "empleados.cedula AS ci,"
                        + "CONCAT(empleados.nombres,' ',empleados.apellidos) AS funcionario_nombre,"
                        + "ROUND(empleados.salario) AS salariobase,"
                        + "ROUND(empleados.adicionalxformacion) AS adicionalformacion,"
                        + "IFNULL(ROUND(credito_salarios.importe,0),0) AS creditosvarios,"
                        + "IFNULL(ROUND(vacaciones.importe,0),0) AS vacaciones,"
                        + "IFNULL(ROUND(horasextra.importe,0),0) AS horasextra,"
                        //BONIFICACION
                        + "(CASE WHEN empleados.ips=1 "
                        + "THEN (("
                        + "     (("
                        + "         ROUND(("
                        + "             CASE WHEN "
                        + "                 empleados.salario>" + nSalarioMinimo //si el salario base es mayor
                        + "             THEN " + nSalarioMinimo + " ELSE" //se usa salario minimo, sino :
                        + "                 empleados.salario " // Se usa el salario base
                        + "             END "
                        + "         ),0)"
                        + "     )*" + nBon + ")/100" //se multiplica por la bonificación en configuración
                        + ")*empleados.nrohijos) "
                        + " ELSE 0	END) AS bonificacionH, "
                        ///IPS
                        + " (CASE WHEN empleados.ips=1 THEN "
                        + "	((ROUND(("
                        + "         empleados.salario + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0) "
                        + "         - IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) - IFNULL(llegadas_tardias.importe,0) "
                        + "         - IFNULL(ausencias.importe,0)"
                        + "     ),0)*" + nIps + ")/100) "
                        + " ELSE 0 END) AS ipsaporte, "
                        ///
                        + " IFNULL(ROUND(debito_salarios.importe,0),0) AS descuentosvarios, "
                        + " IFNULL(ROUND(llegadas_tardias.importe,0),0) AS llegadastardias, "
                        + " IFNULL(ROUND(ausencias.importe,0),0) AS ausencias,"
                        ///////////////////////////
                        //INICIA SALARIO NETO
                        + "(CASE WHEN empleados.ips=1 " //SI TIENE IPS
                        //                 salario base + adicionalxformacion +credito
                        + " THEN ROUND((empleados.salario + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0) "
                        //                  + vacaciones + horas extra
                        + " + IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) + "
                        //  + bonificacionxhijos
                        + "((((ROUND((CASE WHEN empleados.salario>" + nSalarioMinimo //si el salario base es mayor
                        + "             THEN " + nSalarioMinimo + " ELSE" //se usa salario minimo, sino :
                        + "                 empleados.salario " // Se usa el salario base
                        + "             END "
                        + "         ),0)"
                        + "     )*" + nBon + ")/100)*empleados.nrohijos) "
                        // -IPS
                        + "- ((ROUND(("
                        + "         empleados.salario + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0) "
                        + "         - IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) - IFNULL(llegadas_tardias.importe,0) "
                        + "         - IFNULL(ausencias.importe,0)"
                        + "   ),0)*" + nIps + ")/100)"
                        // - llegadas tardías - ausencias
                        + " - IFNULL(llegadas_tardias.importe,0) - IFNULL(ausencias.importe,0)- IFNULL(ROUND(debito_salarios.importe,0),0)"
                        + "),0) "
                        //SI NO TIENE IPS
                        + " ELSE "
                        + "     ROUND(("
                        + "         empleados.salario + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0) "
                        + "         - IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) - IFNULL(llegadas_tardias.importe,0) "
                        + "         - IFNULL(ausencias.importe,0)- IFNULL(ROUND(debito_salarios.importe,0),0)"
                        + "     ),0) "
                        + " END) AS salarioneto "
                        ////////////////////////// FROM 
                        + " FROM empleados "
                        + " INNER JOIN sucursales ON empleados.sucursal = sucursales.codigo "
                        + " LEFT JOIN departamento_laboral ON empleados.departamento= departamento_laboral.Codigo "
                        + " INNER JOIN secciones ON empleados.seccion=secciones.Codigo "
                        + " INNER JOIN giradurias ON empleados.giraduria=giradurias.codigo "
                        + " LEFT JOIN (SELECT credito_salarios.funcionario AS empleados, SUM(credito_salarios.importe) AS importe "
                        + " FROM credito_salarios "
                        + " WHERE MONTH(credito_salarios.fecha)=MONTH(" + fechafin + ") AND YEAR(credito_salarios.fecha)=YEAR(" + fechafin + ") "
                        + " GROUP BY credito_salarios.funcionario "
                        + ") AS credito_salarios ON credito_salarios.empleados=empleados.codigo "
                        + " LEFT JOIN (SELECT vacaciones.funcionario AS empleados, SUM(vacaciones.importe) AS importe "
                        + " FROM vacaciones "
                        + "WHERE  MONTH(vacaciones.fecha)=MONTH(" + fechafin + ") AND YEAR(vacaciones.fecha)=YEAR(" + fechafin + ")"
                        + " GROUP BY vacaciones.funcionario "
                        + ") AS vacaciones ON vacaciones.empleados=empleados.codigo "
                        ////////////////////////////////// HORAS EXTRAS
                        /* + " LEFT JOIN (SELECT horas_extras.funcionario AS empleados, SUM(horas_extras.importe) AS importe "
                        + " FROM  horas_extras "
                        + "WHERE MONTH(horas_extras.fecha)=MONTH(" + fechafin + ") AND YEAR(horas_extras.fecha)=YEAR(" + fechafin + ")"
                        + " GROUP BY horas_extras.funcionario) AS horasextra ON horasextra.empleados=empleados.codigo "*/
                        + " LEFT JOIN (SELECT horas_extras.funcionario AS empleados, SUM(horas_extras.importe) AS importe "
                        + " FROM  horas_extras "
                        + " WHERE horas_extras.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY horas_extras.funcionario) AS horasextra ON horasextra.empleados=empleados.codigo "
                        ////////////////////////////////////////
                        + " LEFT JOIN (SELECT debito_salarios.funcionario AS empleados, SUM(debito_salarios.importe) AS importe "
                        + " FROM debito_salarios "
                        + " WHERE MONTH(debito_salarios.fecha)=MONTH(" + fechafin + ") AND YEAR(debito_salarios.fecha)=YEAR(" + fechafin + ")"
                        + " GROUP BY debito_salarios.funcionario) AS debito_salarios ON debito_salarios.empleados=empleados.codigo "
                        + " LEFT JOIN (SELECT llegadas_tardias.funcionario AS empleados, SUM(llegadas_tardias.importe) AS importe "
                        + " FROM llegadas_tardias "
                        + " WHERE MONTH(llegadas_tardias.fecha)=MONTH(" + fechafin + ") AND YEAR(llegadas_tardias.fecha)=YEAR(" + fechafin + ")"
                        + " GROUP BY llegadas_tardias.funcionario "
                        + ") AS llegadas_tardias ON llegadas_tardias.empleados=empleados.codigo "
                        + " LEFT JOIN (SELECT ausencias.funcionario AS empleados, SUM(ausencias.importe) AS importe "
                        + " FROM ausencias "
                        + " WHERE MONTH(ausencias.fecha)=MONTH(" + fechafin + ") AND YEAR(ausencias.fecha)=YEAR(" + fechafin + ")"
                        + " GROUP BY ausencias.funcionario "
                        + ") AS ausencias ON ausencias.empleados=empleados.codigo "
                        + " WHERE giradurias.codigo=" + sucini + " AND empleados.tipo_salario>=3 "
                        //////////////////////////////UNION////////////////////////////////////////
                        + " UNION "
                        + "SELECT (CASE WHEN empleados.tipo_salario=2 THEN 'JORNALERO' ELSE 'JORNALERO' END) AS tipo,"
                        + "sucursales.nombre AS sucursal,"
                        + "sucursales.codigo AS idsucursal,"
                        + "departamento_laboral.codigo AS iddepartamento,"
                        + "departamento_laboral.nombre AS departamento,"
                        + "secciones.Nombre AS seccion,"
                        + "secciones.codigo AS idseccion,"
                        + "giradurias.codigo AS idgiraduria,"
                        + "giradurias.nombre AS giraduria,"
                        + "empleados.codigo AS funcionario,"
                        + "empleados.cedula AS ci,"
                        + "CONCAT(empleados.nombres,' ',empleados.apellidos) AS funcionario_nombre,"
                        + "ROUND(jornaleros.importe) AS salariobase,"
                        + "ROUND(empleados.adicionalxformacion) AS adicionalformacion,"
                        + "IFNULL(ROUND(credito_salarios.importe,0),0) AS creditosvarios,"
                        + "IFNULL(ROUND(vacaciones.importe,0),0) AS vacaciones,"
                        + "IFNULL(ROUND(horasextra.importe,0),0) AS horasextra,"
                        /////BONIFICACION 
                        + "(CASE WHEN empleados.ips=1 "
                        + " THEN (("
                        + "     (("
                        + "         ROUND(("
                        + "             CASE WHEN "
                        + "                 jornaleros.importe>" + nSalarioMinimo //si el salario base es mayor
                        + "             THEN " + nSalarioMinimo + " ELSE" //se usa salario minimo, sino :
                        + "                 jornaleros.importe " // Se usa el salario base
                        + "             END "
                        + "         ),0))"
                        + "     *" + nBon + ")/100)*empleados.nrohijos)"
                        + "ELSE 0	END) AS bonificacionH,"
                        ////IPS
                        + "(CASE WHEN empleados.ips=1"
                        + " THEN (("
                        + "     ROUND(("
                        + "         jornaleros.importe + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0)"
                        + "         - IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) - IFNULL(llegadas_tardias.importe,0) "
                        + "         - IFNULL(ausencias.importe,0)"
                        + "     ),0)"
                        + "*" + nIps + ")/100)"
                        + "ELSE 0	END) AS ipsaporte,"
                        //////
                        + "IFNULL(ROUND(debito_salarios.importe,0),0) AS descuentosvarios,"
                        + "IFNULL(ROUND(llegadas_tardias.importe,0),0) AS llegadastardias,"
                        + "IFNULL(ROUND(ausencias.importe,0),0) AS ausencias,"
                        //INICIA SALARIO NETO JORNALEROS
                        + "(CASE WHEN empleados.ips=1 "
                        + " THEN "
                        + "ROUND((jornaleros.importe + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0)"
                        + "+ IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) + "
                        // + bonificacion hijos
                        + "((((ROUND(("
                        + "           CASE WHEN "
                        + "               jornaleros.importe>" + nSalarioMinimo //si el salario base es mayor
                        + "           THEN " + nSalarioMinimo + " ELSE" //se usa salario minimo, sino :
                        + "               jornaleros.importe " // Se usa el salario base
                        + "          END "
                        + "         ),0))*" + nBon + ")/100)*empleados.nrohijos)"
                        // - IPS
                        + "- ((ROUND(("
                        + "         jornaleros.importe + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0)"
                        + "         - IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) - IFNULL(llegadas_tardias.importe,0) "
                        + "         - IFNULL(ausencias.importe,0)"
                        + "   ),0)*" + nIps + ")/100)"
                        //- LLEGADAS TARDIAS - AUSENCAIS - DEBITOS
                        + "- IFNULL(llegadas_tardias.importe,0) - IFNULL(ausencias.importe,0)- IFNULL(ROUND(debito_salarios.importe,0),0)),0)"
                        + "ELSE "
                        + "ROUND((jornaleros.importe + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0)"
                        + "- IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) - IFNULL(llegadas_tardias.importe,0) "
                        + "- IFNULL(ausencias.importe,0)- IFNULL(ROUND(debito_salarios.importe,0),0)),0)"
                        + "END) AS salarioneto "
                        + " FROM empleados "
                        + " INNER JOIN sucursales ON empleados.sucursal = sucursales.codigo "
                        + " LEFT JOIN departamento_laboral ON empleados.departamento= departamento_laboral.Codigo "
                        + " INNER JOIN secciones ON empleados.seccion=secciones.Codigo "
                        + " INNER JOIN giradurias ON empleados.giraduria=giradurias.codigo "
                        + " LEFT JOIN (SELECT credito_salarios.funcionario AS empleados, SUM(credito_salarios.importe) AS importe "
                        + " FROM credito_salarios "
                        + "WHERE credito_salarios.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY credito_salarios.funcionario "
                        + " ) AS credito_salarios ON credito_salarios.empleados=empleados.codigo "
                        + " LEFT JOIN (SELECT vacaciones.funcionario AS empleados, SUM(vacaciones.importe) AS importe "
                        + " FROM vacaciones "
                        + " WHERE vacaciones.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY vacaciones.funcionario "
                        + " ) AS vacaciones ON vacaciones.empleados=empleados.codigo "
                        //horas extra select
                        + " LEFT JOIN (SELECT horas_extras.funcionario AS empleados, SUM(horas_extras.importe) AS importe "
                        + " FROM  horas_extras "
                        + " WHERE horas_extras.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY horas_extras.funcionario) AS horasextra ON horasextra.empleados=empleados.codigo "
                        //debito select
                        + " LEFT JOIN (SELECT debito_salarios.funcionario AS empleados, SUM(debito_salarios.importe) AS importe "
                        + " FROM debito_salarios "
                        + " WHERE debito_Salarios.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY debito_salarios.funcionario) AS debito_salarios ON debito_salarios.empleados=empleados.codigo "
                        + " LEFT JOIN (SELECT llegadas_tardias.funcionario AS empleados, SUM(llegadas_tardias.importe) AS importe "
                        + " FROM llegadas_tardias "
                        + " WHERE llegadas_tardias.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY llegadas_tardias.funcionario "
                        + " ) AS llegadas_tardias ON llegadas_tardias.empleados=empleados.codigo "
                        + " LEFT JOIN (SELECT ausencias.funcionario AS empleados, SUM(ausencias.importe) AS importe "
                        + " FROM ausencias "
                        + " WHERE ausencias.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY ausencias.funcionario "
                        + ") AS ausencias ON ausencias.empleados=empleados.codigo "
                        + " INNER JOIN (SELECT jornaleros.funcionario AS empleados, SUM(jornaleros.importe) AS importe"
                        + " FROM jornaleros "
                        + " WHERE jornaleros.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY jornaleros.funcionario "
                        + ") AS jornaleros ON jornaleros.empleados=empleados.codigo "
                        + " WHERE giradurias.codigo= " + sucini + " AND empleados.tipo_salario<=2 ";
            } else {
                cSql = "SELECT (CASE WHEN empleados.tipo_salario=2 THEN 'JORNALERO' ELSE 'MENSUALERO' END) AS tipo,"
                        + "sucursales.nombre AS sucursal,"
                        + "sucursales.codigo AS idsucursal,"
                        + "departamento_laboral.codigo AS iddepartamento,"
                        + "departamento_laboral.Nombre AS departamento,"
                        + "secciones.Nombre AS seccion,"
                        + "secciones.codigo AS idseccion,"
                        + "giradurias.codigo AS idgiraduria,"
                        + "giradurias.nombre AS giraduria,"
                        + "empleados.codigo AS funcionario,"
                        + "empleados.cedula AS ci,"
                        + "CONCAT(empleados.nombres,' ',empleados.apellidos) AS funcionario_nombre,"
                        + "ROUND(jornaleros.importe) AS salariobase,"
                        + "ROUND(empleados.adicionalxformacion) AS adicionalformacion,"
                        + "IFNULL(ROUND(credito_salarios.importe,0),0) AS creditosvarios,"
                        + "IFNULL(ROUND(vacaciones.importe,0),0) AS vacaciones,"
                        + "IFNULL(ROUND(horasextra.importe,0),0) AS horasextra,"
                        + "(CASE WHEN empleados.ips=1 "
                        + " THEN 	((((ROUND((jornaleros.importe + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0)"
                        + "- IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) - IFNULL(llegadas_tardias.importe,0) - IFNULL(ausencias.importe,0)),0))*5)/100)*empleados.nrohijos)"
                        + "ELSE 0	END) AS bonificacionH,(CASE	WHEN empleados.ips=1"
                        + " THEN 	((ROUND((jornaleros.importe + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0)"
                        + "- IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) - IFNULL(llegadas_tardias.importe,0) - IFNULL(ausencias.importe,0)),0)*9)/100)"
                        + "ELSE 0	END) AS ipsaporte,"
                        + "IFNULL(ROUND(debito_salarios.importe,0),0) AS descuentosvarios,"
                        + "IFNULL(ROUND(llegadas_tardias.importe,0),0) AS llegadastardias,"
                        + "IFNULL(ROUND(ausencias.importe,0),0) AS ausencias,"
                        + "(CASE WHEN empleados.ips=1 "
                        + " THEN ROUND((jornaleros.importe + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0)"
                        + "+ IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) + "
                        + "((((ROUND((jornaleros.importe + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0)"
                        + "+ IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) - IFNULL(llegadas_tardias.importe,0) - IFNULL(ausencias.importe,0)),0))*5)/100)*empleados.nrohijos)"
                        + "- ((ROUND((jornaleros.importe + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0)"
                        + "- IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) - IFNULL(llegadas_tardias.importe,0) - IFNULL(ausencias.importe,0)),0)*9)/100)"
                        + "- IFNULL(llegadas_tardias.importe,0) - IFNULL(ausencias.importe,0)),0)"
                        + "ELSE ROUND((jornaleros.importe + IFNULL(empleados.adicionalxformacion,0) + IFNULL(credito_salarios.importe,0)"
                        + "- IFNULL(vacaciones.importe,0) + IFNULL(horasextra.importe,0) - IFNULL(llegadas_tardias.importe,0) - IFNULL(ausencias.importe,0)),0)"
                        + "END) AS salarioneto "
                        + " FROM empleados "
                        + " INNER JOIN sucursales ON empleados.sucursal = sucursales.codigo "
                        + " LEFT JOIN departamento_laboral ON empleados.departamento= departamento_laboral.Codigo "
                        + " INNER JOIN secciones ON empleados.seccion=secciones.Codigo "
                        + " INNER JOIN giradurias ON empleados.giraduria=giradurias.codigo "
                        + " LEFT JOIN (SELECT credito_salarios.funcionario AS empleados, SUM(credito_salarios.importe) AS importe "
                        + " FROM credito_salarios "
                        + "WHERE credito_salarios.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY credito_salarios.funcionario "
                        + " ) AS credito_salarios ON credito_salarios.empleados=empleados.codigo "
                        + " LEFT JOIN (SELECT vacaciones.funcionario AS empleados, SUM(vacaciones.importe) AS importe "
                        + " FROM vacaciones "
                        + " WHERE vacaciones.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY vacaciones.funcionario "
                        + " ) AS vacaciones ON vacaciones.empleados=empleados.codigo "
                        + " LEFT JOIN (SELECT horas_extras.funcionario AS empleados, SUM(horas_extras.importe) AS importe "
                        + " FROM  horas_extras "
                        + " WHERE horas_extras.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY horas_extras.funcionario) AS horasextra ON horasextra.empleados=empleados.codigo "
                        + " LEFT JOIN (SELECT debito_salarios.funcionario AS empleados, SUM(debito_salarios.importe) AS importe "
                        + " FROM debito_salarios "
                        + " WHERE debito_Salarios.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY debito_salarios.funcionario) AS debito_salarios ON debito_salarios.empleados=empleados.codigo "
                        + " LEFT JOIN (SELECT llegadas_tardias.funcionario AS empleados, SUM(llegadas_tardias.importe) AS importe "
                        + " FROM llegadas_tardias "
                        + " WHERE llegadas_tardias.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY llegadas_tardias.funcionario "
                        + " ) AS llegadas_tardias ON llegadas_tardias.empleados=empleados.codigo "
                        + " LEFT JOIN (SELECT ausencias.funcionario AS empleados, SUM(ausencias.importe) AS importe "
                        + " FROM ausencias "
                        + " WHERE ausencias.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY ausencias.funcionario "
                        + ") AS ausencias ON ausencias.empleados=empleados.codigo "
                        + " INNER JOIN (SELECT jornaleros.funcionario AS empleados, SUM(jornaleros.importe) AS importe"
                        + " FROM jornaleros "
                        + " WHERE jornaleros.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "'"
                        + " GROUP BY jornaleros.funcionario "
                        + ") AS jornaleros ON jornaleros.empleados=empleados.codigo "
                        + " WHERE giradurias.codigo= " + sucini + " AND empleados.tipo_salario<=2 ";
            }
            System.out.println(cSql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    sucursal.setCodigo(rs.getInt("idsucursal"));
                    SalarioLiquidacion liquidacion = new SalarioLiquidacion();
                    liquidacion.setFecha(fechafin);
                    liquidacion.setMes(fechafin.getMonth() + 1);
                    liquidacion.setPeriodo(fechafin.getYear() + 1900);
                    liquidacion.setIdDepartamento(rs.getInt("iddepartamento"));
                    liquidacion.setIdSeccion(rs.getInt("idseccion"));
                    liquidacion.setIdGiraduria(rs.getInt("idgiraduria"));
                    liquidacion.setFuncionario(rs.getInt("funcionario"));
                    liquidacion.setSucursal(sucursal);
                    liquidacion.setTipo(rs.getString("tipo"));
                    liquidacion.setDepartamento(rs.getString("departamento"));
                    liquidacion.setSeccion(rs.getString("seccion"));
                    liquidacion.setGiraduria(rs.getString("giraduria"));
                    liquidacion.setCi(rs.getString("ci"));
                    liquidacion.setFuncionario_nombre(rs.getString("funcionario_nombre"));
                    liquidacion.setSalariobase(rs.getBigDecimal("salariobase"));
                    liquidacion.setAdicionalformacion(rs.getBigDecimal("adicionalformacion"));
                    liquidacion.setCreditosvarios(rs.getBigDecimal("creditosvarios"));
                    liquidacion.setVacaciones(rs.getBigDecimal("vacaciones"));
                    liquidacion.setHorasextra(rs.getBigDecimal("horasextra"));
                    liquidacion.setBonificacionH(rs.getBigDecimal("bonificacionH"));
                    liquidacion.setIpsaporte(rs.getBigDecimal("ipsaporte"));
                    liquidacion.setDescuentosvarios(rs.getBigDecimal("descuentosvarios"));
                    liquidacion.setLlegadastardias(rs.getBigDecimal("llegadastardias"));
                    liquidacion.setAusencias(rs.getBigDecimal("ausencias"));
                    liquidacion.setSalarioneto(rs.getBigDecimal("salarioneto"));
                    lista.add(liquidacion);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public ArrayList<SalarioLiquidacion> consultarPlanilla(int sucini, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<SalarioLiquidacion> lista = new ArrayList<SalarioLiquidacion>();
        con = new Conexion();
        st = con.conectar();
        String cSql = null;
        try {
            cSql = "SELECT   planilla_salarios.idnumero as numero,"
                    + "planilla_salarios.funcionario,"
                    + "planilla_salarios.fecha,"
                    + "planilla_salarios.periodo,"
                    + "planilla_salarios.tipo,"
                    + "planilla_salarios.sucursal,"
                    + "planilla_salarios.seccion,"
                    + "planilla_salarios.departamento,"
                    + "planilla_salarios.giraduria,"
                    + "concat(empleados.nombres,' ',empleados.apellidos) as funcionario_nombre,"
                    + "empleados.cedula as ci,"
                    + "planilla_salarios.salariobase,"
                    + "planilla_salarios.adicionalformacion,"
                    + "planilla_salarios.creditosvarios,"
                    + "planilla_salarios.vacaciones,"
                    + "planilla_salarios.anticipos,"
                    + "planilla_salarios.horasextras,"
                    + "planilla_salarios.bonificacion,"
                    + "planilla_salarios.ipsaporte,"
                    + "planilla_salarios.descuentosvarios,"
                    + "planilla_salarios.llegadastardias,"
                    + "planilla_salarios.ausencias,"
                    + "planilla_salarios.embargos,"
                    + "planilla_salarios.salariobruto,"
                    + "planilla_salarios.salarioneto,"
                    + "giradurias.nombre as nombregiraduria,"
                    + "departamento_laboral.nombre as nombredepartamento,"
                    + "secciones.nombre as nombreseccion,"
                    + "sucursales.nombre as nombresucursal "
                    + "FROM planilla_salarios "
                    + " INNER JOIN sucursales ON sucursales.codigo=planilla_Salarios.sucursal "
                    + " LEFT JOIN departamento_laboral ON departamento_laboral.codigo=planilla_salarios.departamento "
                    + " INNER JOIN giradurias ON giradurias.codigo=planilla_salarios.giraduria"
                    + " INNER JOIN empleados ON empleados.codigo=planilla_salarios.funcionario"
                    + " INNER JOIN secciones ON secciones.Codigo=planilla_salarios.seccion"
                    + " WHERE planilla_salarios.giraduria=? "
                    + " AND planilla_salarios.fecha between ? and ? "
                    + " ORDER BY planilla_salarios.giraduria,planilla_salarios.sucursal,"
                    + " planilla_salarios.departamento,planilla_salarios.seccion,"
                    + " planilla_salarios.funcionario";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, sucini);
                ps.setDate(2, fechaini);
                ps.setDate(3, fechafin);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    sucursal.setCodigo(rs.getInt("sucursal"));
                    SalarioLiquidacion liquidacion = new SalarioLiquidacion();
                    liquidacion.setFecha(fechafin);
                    liquidacion.setMes(fechafin.getMonth() + 1);
                    liquidacion.setPeriodo(fechafin.getYear() + 1900);
                    liquidacion.setIdDepartamento(rs.getInt("departamento"));
                    liquidacion.setIdSeccion(rs.getInt("seccion"));
                    liquidacion.setIdGiraduria(rs.getInt("giraduria"));
                    liquidacion.setFuncionario(rs.getInt("funcionario"));
                    liquidacion.setSucursal(sucursal);
                    liquidacion.setTipo(rs.getString("tipo"));
                    liquidacion.setDepartamento(rs.getString("nombredepartamento"));
                    liquidacion.setSeccion(rs.getString("nombreseccion"));
                    liquidacion.setGiraduria(rs.getString("nombregiraduria"));
                    liquidacion.setCi(rs.getString("ci"));
                    liquidacion.setFuncionario_nombre(rs.getString("funcionario_nombre"));
                    liquidacion.setSalariobase(rs.getBigDecimal("salariobase"));
                    liquidacion.setAdicionalformacion(rs.getBigDecimal("adicionalformacion"));
                    liquidacion.setCreditosvarios(rs.getBigDecimal("creditosvarios"));
                    liquidacion.setVacaciones(rs.getBigDecimal("vacaciones"));
                    liquidacion.setAnticipos(rs.getBigDecimal("anticipos"));
                    liquidacion.setHorasextra(rs.getBigDecimal("horasextras"));
                    liquidacion.setBonificacionH(rs.getBigDecimal("bonificacion"));
                    liquidacion.setIpsaporte(rs.getBigDecimal("ipsaporte"));
                    liquidacion.setDescuentosvarios(rs.getBigDecimal("descuentosvarios"));
                    liquidacion.setLlegadastardias(rs.getBigDecimal("llegadastardias"));
                    liquidacion.setAusencias(rs.getBigDecimal("ausencias"));
                    liquidacion.setEmbargos(rs.getBigDecimal("embargos"));
                    liquidacion.setSalariobruto(rs.getBigDecimal("salariobruto"));
                    liquidacion.setSalarioneto(rs.getBigDecimal("salarioneto"));
                    lista.add(liquidacion);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }

        st.close();
        return lista;
    }

    public ArrayList<SalarioLiquidacion> GenerarConsultaSalarios(int sucini, Date fechaini, Date fechafin, int ntipo) throws SQLException {
        ArrayList<SalarioLiquidacion> lista = new ArrayList<SalarioLiquidacion>();
        con = new Conexion();
        st = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = new configuracion();
        config = configDAO.consultar();
        BigDecimal nSalarioPersonal = new BigDecimal(0);
        double nImporteBonificacion = 0;
        double nSalarioMinimo = config.getSalario_minimo();
        double nTotalIngreso = 0;
        double nAporteIps = 0;
        double nIps = config.getPorcentaje_ips();
        double nBon = config.getPorcentaje_bonificacion();
        System.out.println("Min: " + nSalarioMinimo + "\nIps:" + nIps + "\nBon:" + nBon);
        try {
            String cSql = null;
            System.out.println(cSql);

            cSql = "SELECT (CASE WHEN emple.tipo_salario=4 THEN 'MENSUALERO' ELSE 'JORNALERO' END) AS tipo,"
                    + "sucursales.nombre AS sucursal,"
                    + "sucursales.codigo AS idsucursal,"
                    + "departamento_laboral.codigo AS iddepartamento,"
                    + "departamento_laboral.Nombre AS departamento,"
                    + "secciones.Nombre AS seccion,"
                    + "secciones.codigo AS idseccion,"
                    + "giradurias.codigo AS idgiraduria,"
                    + "giradurias.nombre AS giraduria,"
                    + "emple.codigo AS funcionario,"
                    + "emple.tipo_salario,"
                    + "emple.ips,"
                    + "emple.bonificacion,"
                    + "emple.nrohijos,"
                    + "emple.cedula AS ci,"
                    + "CONCAT(emple.nombres,' ',emple.apellidos) AS funcionario_nombre,"
                    + "ROUND(emple.salario) AS salariobase,"
                    + "COALESCE(j.salariojornalero,0) AS salariojornalero,"
                    + "ROUND(emple.adicionalxformacion) AS adicionalformacion,"
                    + "COALESCE(cr.creditosvarios,0) AS creditosvarios,"
                    + "COALESCE(va.vacaciones,0) AS vacaciones,"
                    + "COALESCE(an.anticipos,0) AS anticipos,"
                    + "COALESCE(he.horasextra,0) AS horasextra,"
                    + "COALESCE(de.descuentosvarios,0) AS descuentosvarios,"
                    + "COALESCE(ll.llegadastardias,0) AS llegadastardias,"
                    + "COALESCE(au.ausencias,0) AS ausencias, "
                    + "COALESCE(emb.embargos,0) AS embargos "
                    + "FROM empleados emple "
                    + "INNER JOIN sucursales ON emple.sucursal = sucursales.codigo "
                    + "LEFT JOIN departamento_laboral ON emple.departamento= departamento_laboral.Codigo "
                    + "INNER JOIN secciones ON emple.seccion=secciones.Codigo "
                    + "INNER JOIN giradurias ON emple.giraduria=giradurias.codigo "
                    + "LEFT JOIN (SELECT jornaleros.funcionario, SUM(jornaleros.importe) AS salariojornalero "
                    + "FROM jornaleros "
                    + "WHERE jornaleros.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "GROUP BY jornaleros.funcionario) j ON j.funcionario=emple.codigo "
                    + "LEFT JOIN (SELECT credito_salarios.funcionario, SUM(credito_salarios.importe) AS creditosvarios "
                    + "FROM credito_salarios "
                    + "WHERE credito_salarios.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "GROUP BY credito_salarios.funcionario) cr ON cr.funcionario=emple.codigo "
                    + "LEFT JOIN (SELECT vacaciones.funcionario, SUM(vacaciones.importe) AS vacaciones "
                    + "FROM vacaciones "
                    + "WHERE vacaciones.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "GROUP BY vacaciones.funcionario) va ON va.funcionario=emple.codigo "
                    + "LEFT JOIN (SELECT anticipos.funcionario, SUM(anticipos.importe) AS anticipos "
                    + "FROM anticipos "
                    + "WHERE anticipos.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "GROUP BY anticipos.funcionario) an ON an.funcionario=emple.codigo "
                    + "LEFT JOIN (SELECT embargos.funcionario, SUM(embargos.importe) AS embargos "
                    + "FROM embargos "
                    + "WHERE embargos.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "GROUP BY embargos.funcionario) emb ON emb.funcionario=emple.codigo "
                    + "LEFT JOIN (SELECT horas_extras.funcionario, SUM(horas_extras.importe) AS horasextra "
                    + "FROM horas_extras "
                    + "WHERE horas_extras.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "GROUP BY horas_extras.funcionario) he ON he.funcionario=emple.codigo "
                    + "LEFT JOIN (SELECT debito_salarios.funcionario, SUM(debito_salarios.importe) AS descuentosvarios "
                    + "FROM debito_salarios "
                    + "WHERE debito_salarios.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "GROUP BY debito_salarios.funcionario) de ON de.funcionario=emple.codigo "
                    + "LEFT JOIN (SELECT llegadas_tardias.funcionario, SUM(llegadas_tardias.importe) AS llegadastardias "
                    + "FROM llegadas_tardias "
                    + "WHERE llegadas_tardias.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "GROUP BY llegadas_tardias.funcionario) ll ON ll.funcionario=emple.codigo "
                    + "LEFT JOIN (SELECT ausencias.funcionario, SUM(ausencias.importe) AS ausencias "
                    + "FROM ausencias "
                    + "WHERE ausencias.fecha BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "GROUP BY ausencias.funcionario) au ON au.funcionario=emple.codigo "
                    + " WHERE emple.estado=1  AND emple.giraduria=" + sucini
                    + " GROUP BY emple.codigo "
                    + " ORDER BY emple.sucursal,emple.departamento,emple.seccion,emple.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal sucursal = new sucursal();
                    sucursal.setCodigo(rs.getInt("idsucursal"));
                    SalarioLiquidacion liquidacion = new SalarioLiquidacion();
                    liquidacion.setFecha(fechafin);
                    liquidacion.setMes(fechafin.getMonth() + 1);
                    liquidacion.setPeriodo(fechafin.getYear() + 1900);
                    liquidacion.setIdDepartamento(rs.getInt("iddepartamento"));
                    liquidacion.setIdSeccion(rs.getInt("idseccion"));
                    liquidacion.setIdGiraduria(rs.getInt("idgiraduria"));
                    liquidacion.setFuncionario(rs.getInt("funcionario"));
                    liquidacion.setSucursal(sucursal);
                    liquidacion.setTipo(rs.getString("tipo"));
                    liquidacion.setDepartamento(rs.getString("departamento"));
                    liquidacion.setSeccion(rs.getString("seccion"));
                    liquidacion.setGiraduria(rs.getString("giraduria"));
                    liquidacion.setCi(rs.getString("ci"));
                    liquidacion.setFuncionario_nombre(rs.getString("funcionario_nombre"));
                    if (rs.getInt("tipo_salario") == 4) {
                        liquidacion.setSalariobase(rs.getBigDecimal("salariobase"));
                    } else {
                        liquidacion.setSalariobase(rs.getBigDecimal("salariojornalero"));
                    }
                    liquidacion.setAdicionalformacion(rs.getBigDecimal("adicionalformacion"));
                    liquidacion.setCreditosvarios(rs.getBigDecimal("creditosvarios"));
                    liquidacion.setVacaciones(rs.getBigDecimal("vacaciones"));
                    liquidacion.setAnticipos(rs.getBigDecimal("anticipos"));
                    liquidacion.setHorasextra(rs.getBigDecimal("horasextra"));
                    ///BONIFICACION FAMILIAR
                    nSalarioPersonal = liquidacion.getSalariobase();
                    if (rs.getInt("bonificacion") == 1) {
                        nImporteBonificacion = Math.round(nSalarioMinimo * nBon / 100 * rs.getInt("nrohijos"));
                    } else {
                        nImporteBonificacion = 0;
                    }
                    liquidacion.setBonificacionH(new BigDecimal(nImporteBonificacion));

                    ///APORTE IPS
                    nTotalIngreso = nSalarioPersonal.doubleValue() + rs.getBigDecimal("adicionalformacion").doubleValue()
                            + rs.getBigDecimal("creditosvarios").doubleValue()
                            + rs.getBigDecimal("horasextra").doubleValue()
                            - (rs.getBigDecimal("llegadastardias").doubleValue()
                            +rs.getBigDecimal("ausencias").doubleValue());
                    if (rs.getInt("ips") == 1) {
                        nAporteIps = Math.round(nTotalIngreso * nIps / 100);
                    }
                    liquidacion.setSalariobruto(new BigDecimal(nTotalIngreso));

                    nTotalIngreso = nTotalIngreso + nImporteBonificacion - (nAporteIps
                            + rs.getBigDecimal("descuentosvarios").doubleValue()
                            + rs.getBigDecimal("embargos").doubleValue()
                            + rs.getBigDecimal("anticipos").doubleValue());
                            //+ rs.getBigDecimal("llegadastardias").doubleValue()
                            //+ rs.getBigDecimal("ausencias").doubleValue());

                    liquidacion.setIpsaporte(new BigDecimal(nAporteIps));
                    liquidacion.setDescuentosvarios(rs.getBigDecimal("descuentosvarios"));
                    liquidacion.setLlegadastardias(rs.getBigDecimal("llegadastardias"));
                    liquidacion.setAusencias(rs.getBigDecimal("ausencias"));
                    liquidacion.setEmbargos(rs.getBigDecimal("embargos"));
                    liquidacion.setSalarioneto(new BigDecimal(nTotalIngreso));
                    nAporteIps = 0;
                    nTotalIngreso = 0;
                    nImporteBonificacion = 0;

                    lista.add(liquidacion);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

}

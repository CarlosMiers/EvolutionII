/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.TimerTask;
import org.openide.util.Exceptions;

public class CalcularMoraDiaria extends TimerTask {

    Conexion cn = null;
    Statement st = null;
    Statement st2 = null;
    Statement st3 = null;
    ResultSet rs = null;
    String cSaldo, cMora, cGastos, cIdDocumento, catraso, cPunitorio = null;
    double nComprobante, natraso, nMora, nInteres, nDiaMora, nGastos, nSaldo, nDiasGracia, nDiasGraciaGastos, nInteresPunitorio, nTasaPunitoria = 0.00;

    @Override
    public void run() {
        Calendar diaActual = Calendar.getInstance();

        cn = new Conexion();
        st = cn.conectar();
        st2 = cn.conectar();
        st3 = cn.conectar();
        ResultSet rs = null;

        String cSqlMora = "SELECT creferencia,iddocumento,cuenta_clientes.cliente,clientes.ruc,clientes.nombre AS nombrecliente,";
        cSqlMora += "documento,comprobantes.nombre as nombreoperacion,cuota,numerocuota,fecha_pago,comprobante,comprobantes.gastoscobros,";
        cSqlMora += "cuenta_clientes.saldo,vencimiento,DATEDIFF(CURDATE(),vencimiento) AS diamora,0000.00 as diamoracue,DATEDIFF(CURDATE(),fecha_pago) AS di,cuenta_clientes.mora,cuenta_clientes.gastos_cobranzas,";
        cSqlMora += "comprobantes.interespunitorio,comprobantes.diasgracia_gastos,comprobantes.diasgracia,cuenta_clientes.tasaoperativa ";
        cSqlMora += "FROM cuenta_clientes ";
        cSqlMora += "INNER JOIN clientes ";
        cSqlMora += "ON clientes.codigo=cuenta_clientes.cliente ";
        cSqlMora += "INNER JOIN comprobantes ";
        cSqlMora += "ON comprobantes.codigo=cuenta_clientes.comprobante ";
        cSqlMora += "WHERE cuenta_clientes.saldo>1000 ";
        cSqlMora += "AND cuenta_clientes.vencimiento<=curdate() ";
        cSqlMora += "AND comprobante IN (8,9,10) AND moneda=1  and cliente<>1111 ";
        cSqlMora += "ORDER BY cliente,vencimiento ";

        try {
            rs = st.executeQuery(cSqlMora);
            while (rs.next()) {
                //Capturamos los Datos necesarios para realizar los calculos
                nGastos = 0.00;
                nSaldo = 0.00;
                nMora = 0.00;
                nInteresPunitorio = 0.00;
                nTasaPunitoria = 0.00;
                nInteres = rs.getDouble("tasaoperativa");
                nDiaMora = rs.getInt("diamora");
                nDiasGracia = rs.getInt("diasgracia");
                nDiasGraciaGastos = rs.getInt("diasgracia_gastos");
                nSaldo = rs.getDouble("saldo");
                cIdDocumento = rs.getString("iddocumento");
                catraso = rs.getString("di");
                natraso = rs.getInt("di");
                nTasaPunitoria = rs.getDouble("interespunitorio");
                nComprobante = rs.getDouble("comprobante");
                natraso = rs.getInt("di");

                if (nDiaMora > 0 && catraso != null) {
                    if (natraso < nDiaMora) {
                        natraso = rs.getInt("di");
                    } else {
                        natraso = nDiaMora;
                    }
                } else {
                    natraso = nDiaMora;
                }

                //Calculamos los intereses
                //Verificando antes si los Dias de Mora son mayores a los dias de Gracia
                if (natraso > nDiasGracia) {
                    nMora = Math.round(nSaldo * ((nInteres / 100) / 360 * natraso));
                    if (Config.nIvaIncluido == 1) {
                        nMora = Math.round(nMora + (nMora * Config.porcentajeiva / 100));
                    }
                    if (nTasaPunitoria > 0) {
                        nInteresPunitorio = Math.round(nMora * nTasaPunitoria / 100);
                    }
                }

                if (Config.nIvaIncluido == 1) {
                    if (natraso > nDiasGraciaGastos) {
                        nGastos = rs.getDouble("gastoscobros");
                    }
                } else {
                    if (natraso > nDiasGraciaGastos) {
                        if (nComprobante == 8) {
                            if (natraso <= 30) {
                                nGastos = Math.round(nSaldo * 5 / 100);
                            } else if (natraso > 31 && natraso <= 60) {
                                nGastos = Math.round(nSaldo * 10 / 100);
                            } else if (natraso > 60) {
                                nGastos = Math.round(nSaldo * 20 / 100);
                            }
                        }
                        if (nComprobante == 9) {
                            if (natraso <= 30) {
                                nGastos = Math.round(nSaldo * 5 / 100);
                            } else if (natraso > 31 && natraso <= 60) {
                                nGastos = Math.round(nSaldo * 10 / 100);
                            } else if (natraso > 61 && natraso <= 360) {
                                nGastos = Math.round(nSaldo * 20 / 100);
                            } else if (natraso > 361 && natraso <= 540) {
                                nGastos = Math.round(nSaldo * 25 / 100);
                            } else if (natraso > 541 && natraso <= 30) {
                                nGastos = Math.round(nSaldo * 30 / 100);
                            } else if (natraso > 721 && natraso <= 900) {
                                nGastos = Math.round(nSaldo * 40 / 100);
                            } else if (natraso > 900) {
                                nGastos = Math.round(nSaldo * 50 / 100);
                            }
                        }

                        if (nComprobante == 10) {
                            if (natraso <= 30) {
                                nGastos = Math.round(nSaldo * 3 / 100);
                            } else if (natraso > 31 && natraso <= 60) {
                                nGastos = Math.round(nSaldo * 6 / 100);
                            } else if (natraso > 61 && natraso <= 90) {
                                nGastos = Math.round(nSaldo * 10 / 100);
                            } else if (natraso > 90) {
                                nGastos = Math.round(nSaldo * 15 / 100);
                            }
                        }
                    }
                }
                //Se calcula el interes punitorio en caso que tenga la tasa estipulada
                nSaldo = nSaldo + nMora + nGastos + nInteresPunitorio;

                cSaldo = Double.toString(nSaldo);
                cMora = Double.toString(nMora);
                cPunitorio = Double.toString(nInteresPunitorio);
                cGastos = Double.toString(nGastos);

                String cSql1 = "UPDATE practipagos SET monto='" + cSaldo;
                cSql1 += "',gastos='" + cGastos + "',punitorio='" + cPunitorio + "',mora='" + cMora + "' WHERE iddocumento='" + cIdDocumento + "'";
                this.guardarPractiPagos(cSql1);

                String cSql2 = "UPDATE t_deudas SET monto='" + cSaldo;
                cSql2 += "',gastos='" + cGastos + "',punitorio='" + cPunitorio + "',mora='" + cMora + "' WHERE iddocumento='" + cIdDocumento + "'";
                this.guardarPagoExpress(cSql2);
            }
            st.close();
            st2.close();
            st3.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println(ex);
        }
    }

    private void guardarPractiPagos(String CadenaSql1) {
        try {
            st2.executeUpdate(CadenaSql1);
        } catch (SQLException ex1) {
            Exceptions.printStackTrace(ex1);
        }
    }

    private void guardarPagoExpress(String CadenaSql2) {
        try {
            st3.executeUpdate(CadenaSql2);
        } catch (SQLException ex1) {
            Exceptions.printStackTrace(ex1);
        }
    }
}

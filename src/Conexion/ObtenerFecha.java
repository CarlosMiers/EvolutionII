/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Federico Lopez
 */
public class ObtenerFecha {

   Conexion con = new Conexion();
    private static Statement st = null;
    private static ResultSet rs = null;

    public String getFecha(Date fecha1) {
        // Date fecha1 = new Date();
        String fecha;
        String formato3 = new String("yyyy-MM-dd");
        SimpleDateFormat formatoSimple = new SimpleDateFormat(formato3);
        formatoSimple.applyPattern(formato3);
        fecha = formatoSimple.format(fecha1);

        return fecha;
    }

    public String date_to_String(Date fecha1) {
        String fecha;
        String formato3 = new String("dd/MM/yyyy");
        SimpleDateFormat formatoSimple = new SimpleDateFormat(formato3);
        formatoSimple.applyPattern(formato3);
        fecha = formatoSimple.format(fecha1);
        return fecha;
    }

//    public int obtenerAnho(Date fecha) {
//        String formato = "yyyy";
//        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
//        return Integer.parseInt(dateFormat.format(fecha));
//    }

    public Date Fechareturn(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        String strFecha = fecha;
        Date fecha1 = null;
        try {
            fecha1 = formatoDelTexto.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        System.out.println(fecha1.toString());
        return fecha1;
    }

    public boolean isFechaValida(String fechax) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            formatoFecha.setLenient(false);
            formatoFecha.parse(fechax);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public java.sql.Date getCurrentDatetime() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

    public java.sql.Date de_java_a_sql(java.util.Date fecha) {
        java.util.Date utilDate = fecha;
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }

    public java.sql.Date de_String_a_java(String fecha) {
        java.util.Date utilDate = this.Fechareturn(fecha);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }

    public Date de_string_a_Date(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        String strFecha = fecha;
        Date fecha1 = null;
        try {
            fecha1 = formatoDelTexto.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return fecha1;
    }

    public Date findemes(int mes, int anho) {
        if (mes > 12) {
            mes = 1;
            anho = anho + 1;
        }
        mes = mes + 1;
        String messgt = "01/" + mes + "/" + anho;
        Date fin = this.de_string_a_Date(messgt);
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fin.getTime());
        cal.add(Calendar.DATE, -1);
        fin = cal.getTime();
        return fin;
    }

    public void cal_separado() {
        //Instanciamos el objeto Calendar
        //en fecha obtenemos la fecha y hora del sistema
        Calendar fecha = new GregorianCalendar();
        //Obtenemos el valor del año, mes, día,
        //hora, minuto y segundo del sistema
        //usando el método get y el parámetro correspondiente
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        System.out.println("Fecha Actual: "
                + dia + "/" + (mes + 1) + "/" + año);
        System.out.printf("Hora Actual: %02d:%02d:%02d %n",
                hora, minuto, segundo);
    }

    public String obtenerformato(Date fecha, String formato) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return String.valueOf(dateFormat.format(fecha));
    }

    public String NombreMes(int mes) {
        String nombre_mes = "nm";
        switch (mes) {
            case 1:
                nombre_mes = "Enero";
                break;
            case 2:
                nombre_mes = "Febrero";
                break;
            case 3:
                nombre_mes = "Marzo";
                break;
            case 4:
                nombre_mes = "Abril";
                break;
            case 5:
                nombre_mes = "Mayo";
                break;
            case 6:
                nombre_mes = "Junio";
                break;
            case 7:
                nombre_mes = "Julio";
                break;
            case 8:
                nombre_mes = "Agosto";
                break;
            case 9:
                nombre_mes = "Septiembre";
                break;
            case 10:
                nombre_mes = "Octubre";
                break;
            case 11:
                nombre_mes = "Noviembre";
                break;
            default:
                nombre_mes = "Diciembre";
                break;
        }
        return nombre_mes;
    }

}

package Clases;

import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;

public class restarfechas {
    public static String fechas(Date vinicio, Date vfinal) {
        java.util.Date fecha1, fecha2;
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        fecha1 = vinicio;
        fecha2 = vfinal;
        long diferencia = (fecha2.getTime() - fecha1.getTime()) / MILLSECS_PER_DAY;
        String cdiferencia = Long.toString(diferencia + 1);
        return cdiferencia;
    }
}

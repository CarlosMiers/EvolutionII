package Modelo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class RestaFecha {
 
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 
		Date fechaInicial=dateFormat.parse("2019-05-22");
		Date fechaFinal=dateFormat.parse("2019-08-22");
 
		int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
 
		System.out.println("Hay "+dias +" dias de diferencia");
	}
}
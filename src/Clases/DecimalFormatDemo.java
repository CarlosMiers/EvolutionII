/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author SERVIDOR
 */
import java.util.*;
import java.text.*;

public class DecimalFormatDemo {

   static public void customFormat(String pattern, double value ) {
      DecimalFormat myFormatter = new DecimalFormat(pattern);
      String output = myFormatter.format(value);
      System.out.println(value + "  " + pattern + "  " + output);
   }

   static public void localizedFormat(String pattern, double value, 
                                      Locale loc ) {
      NumberFormat nf = NumberFormat.getNumberInstance(loc);
      DecimalFormat df = (DecimalFormat)nf;
      df.applyPattern(pattern);
      String output = df.format(value);
      System.out.println(pattern + "  " + output + "  " + loc.toString());
   }

   static public void main(String[] args) {

      customFormat("###,###.###", 123456.789);
      customFormat("###.##", 123456.789);
      customFormat("###-###-######",1001358461);
      customFormat("$###,###.###", 12345.67);
      customFormat("\u00a5###,###.###", 12345.67);

      DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
      unusualSymbols.setDecimalSeparator('|');
      unusualSymbols.setGroupingSeparator('^');
      String strange = "#,##0.###";
      DecimalFormat weirdFormatter = new DecimalFormat(strange, unusualSymbols);
      weirdFormatter.setGroupingSize(4);
      String bizarre = weirdFormatter.format(12345.678);
      System.out.println(bizarre);

      Locale[] locales = {
         new Locale("en", "US"),
         new Locale("de", "DE"),
         new Locale("fr", "FR")
      };

      for (int i = 0; i < locales.length; i++) {
         localizedFormat("###,###.###", 123456.789, locales[i]);
      }

   }
}

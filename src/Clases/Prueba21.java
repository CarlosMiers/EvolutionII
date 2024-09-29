package Clases;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.stream.IntStream;

public class Prueba21 {

    public static void main(String args[]) {
        String myNumber = "7712";
        String cBoca = "001";
        String cBoca2 = "001";
        
//        double myNumber = 10010007712;
        int n = Integer.valueOf(myNumber);
        String formatString = String.format("%%0%dd", 7);
        String formattedString = String.format(formatString, n);
        System.out.println(cBoca+"-"+cBoca2+"-"+formattedString);
    }
}

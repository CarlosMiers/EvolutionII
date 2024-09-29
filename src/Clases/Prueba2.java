package Clases;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.stream.IntStream;

public class Prueba2 {

    public static void main(String args[]) {
        PruebaEan8 ean8 = new PruebaEan8();
        String cCodigo = ean8.ean8("2507221");
        System.out.println(cCodigo);
    }

}

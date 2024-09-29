package Clases;

public class barras {

    public static void main(String[] args) {
        String codigo = "515548515110" ;
        int lenCod = codigo.length();
        int sumaPares = 0;
        int sumaImpares = 0;
        int codigoVerif = 0;
        int pos=0;
        try {
            for (int i =0 ; i <lenCod; i++) {
                pos=i+1;
                int numero = Integer.valueOf(codigo.substring(i, i+1 ));
                if (pos % 2 == 0) {
                    sumaPares = sumaPares +(numero*3);
                    System.out.println(numero*3);
                    
                } else {
                    sumaImpares = sumaImpares + (numero*1);
                    System.out.println(numero*1);
                }
            }
            System.out.println(" IMPARES "+sumaImpares);
            System.out.println("PARES "+sumaPares);
            
            codigoVerif = ((Math.round(sumaPares+sumaImpares) / 10) + 1) * 10 - (sumaPares+sumaImpares);
            if (codigoVerif == 10) {
                codigoVerif = 0;
            }
        } catch (Exception e) {
            codigoVerif = 0;
        }
        System.out.println(codigoVerif);
    }

}

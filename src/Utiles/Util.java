package Utiles;

public class Util {
    
    public static String replicar(String caracter, int veces){
        String valor = "";
        for (int i = 1; i <= veces; i++) {
            valor += "  ";
        }
        return valor;
    }
    
}

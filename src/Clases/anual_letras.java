package Clases;

import java.util.List;
import java.util.StringTokenizer;

public class anual_letras {

    public String Letras(String numero) {
        String miles = "";
        String cMiles = "";
        String cCentena = "";
        String centena = "";
        String cDecena = "";
        String decena = "";
        String cUnidad = "";
        String unidad = "";

        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                cMiles = numero.substring(0, 1);
                if (cMiles.equals("1")) {
                    miles = "Mil ";
                } else {
                    miles = "Dos Mil ";
                }
            }
            if (i == 1) {
                cCentena = numero.substring(1, 2);
                int nCentena = Integer.valueOf(cCentena);
                switch (nCentena) {
                    case 1:
                        centena = "Ciento ";
                        break; // break es opcional
                    case 2:
                        centena = "Doscientos ";
                        break; // break es opcional
                    case 3:
                        centena = "Trescientos ";
                        break; // break es opcional
                    case 4:
                        centena = "Cuatrocientos ";
                        break; // break es opcional
                    case 5:
                        centena = "Quinientos ";
                        break; // break es opcional
                    case 6:
                        centena = "Seiscientos ";
                        break; // break es opcional
                    case 7:
                        centena = "Setecientos ";
                        break; // break es opcional
                    case 8:
                        centena = "Ochocientos ";
                        break; // break es opcional
                    case 9:
                        centena = "Novecientos ";
                        break; // break es opcional
                    default:
                        centena = " ";
                }
            }

            if (i == 2) {
                cDecena = numero.substring(2, 3);
                int nDecena = Integer.valueOf(cDecena);
                cUnidad = numero.substring(3, 4);
                switch (nDecena) {
                    case 1:
                        decena = "Diez ";
                        break; // break es opcional
                    case 2:
                        if (Integer.valueOf(cUnidad) == 0) {
                            decena = "Veinte ";
                        } else {
                            decena = "Veinti";
                        }
                        break; // break es opcional
                    case 3:
                        decena = "Treinta ";
                        break; // break es opcional
                    case 4:
                        decena = "Cuarenta ";
                        break; // break es opcional
                    case 5:
                        decena = "Cincuenta ";
                        break; // break es opcional
                    case 6:
                        decena = "Sesenta ";
                        break; // break es opcional
                    case 7:
                        decena = "Setenta ";
                        break; // break es opcional
                    case 8:
                        decena = "Ochenta ";
                        break; // break es opcional
                    case 9:
                        decena = "Noventa ";
                        break; // break es opcional
                    default:
                        decena = " ";
                }
            }

            if (i == 3) {
                cDecena = numero.substring(2, 3);
                int nDecena = Integer.valueOf(cDecena);
                cUnidad = numero.substring(3, 4);
                int nUnidad = Integer.valueOf(cUnidad);
                switch (nUnidad) {
                    case 1:
                        if (nDecena == 2) {
                            unidad = "uno ";
                        } else {
                            unidad = " y Uno ";
                        }
                        break; // break es opcional
                    case 2:
                        if (nDecena == 2) {
                            unidad = "dós ";
                        } else {
                            unidad = " y Dos ";
                        }
                        break; // break es opcional
                    case 3:
                        if (nDecena == 2) {
                            unidad = "trés ";
                        } else {
                            unidad = " y Tres ";
                        }
                        break; // break es opcional
                    case 4:
                        if (nDecena == 2) {
                            unidad = "cuatro ";
                        } else {
                            unidad = " y Cuatro ";
                        }
                        break; // break es opcional
                    case 5:
                        if (nDecena == 2) {
                            unidad = "cinco";
                        } else {
                            unidad = " y Cinco ";
                        }
                        break; // break es opcional
                    case 6:
                        if (nDecena == 2) {
                            unidad = "seis";
                        } else {
                            unidad = " y Seis ";
                        }
                        break; // break es opcional
                    case 7:
                        if (nDecena == 2) {
                            unidad = "siete ";
                        } else {
                            unidad = " y Siete ";
                        }
                        break; // break es opcional
                    case 8:
                        if (nDecena == 2) {
                            unidad = "ocho ";
                        } else {
                            unidad = " y Ocho ";
                        }
                        break; // break es opcional
                    case 9:
                        if (nDecena == 2) {
                            unidad = "nueve ";
                        } else {
                            unidad = " y Nueve ";
                        }
                        break; // break es opcional
                    default:
                        unidad = " ";
                }
            }
        }
        return miles + centena + decena + unidad;
    }
}

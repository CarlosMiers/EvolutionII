/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author faisc
 */
public class calculateXIRR {
    
     public double calculateXIRR(double[] cashFlows, LocalDate[] dates) throws Exception {
        if (cashFlows.length != dates.length) {
            throw new Exception("Los arrays de flujos de efectivo y fechas deben tener la misma longitud.");
        }

        double guess = 0.1; // Suposición inicial
        double tol = 1e-6; // Tolerancia para la precisión
        int maxIter = 1000; // Máximo número de iteraciones
        double rate = guess;

        LocalDate startDate = dates[0];

        for (int iter = 0; iter < maxIter; iter++) {
            double fValue = 0.0;
            double fDerivative = 0.0;

            for (int i = 0; i < cashFlows.length; i++) {
                double fraction = ChronoUnit.DAYS.between(startDate, dates[i]) / 365.0;
                fValue += cashFlows[i] / Math.pow(1 + rate, fraction);
                fDerivative -= fraction * cashFlows[i] / Math.pow(1 + rate, fraction + 1);
            }

            double newRate = rate - fValue / fDerivative;

            if (Math.abs(newRate - rate) < tol) {
                return newRate;
            }

            rate = newRate;
        }

        throw new Exception("No se pudo calcular la TIR.");
    }
}

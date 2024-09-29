package Clases;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TIRCalculator {

    public static void main(String[] args) {
        // Ejemplo de uso
        double[] cashFlows = {-1020.30, 19.95,
            19.95,
            19.95,
            19.95,
            19.95,
            19.95,
            19.95,
            19.95,
            19.95,
            19.95,
            19.95,
            19.95,
            19.95,
            19.95,
            19.95,
            20.6,
            1000
        };

        LocalDate[] dates = {
            LocalDate.of(2024, 12, 15),
            LocalDate.of(2025, 01, 28),
            LocalDate.of(2025, 04, 29),
            LocalDate.of(2025, 07, 29),
            LocalDate.of(2025, 10, 28),
            LocalDate.of(2026, 01, 27),
            LocalDate.of(2026, 04, 28),
            LocalDate.of(2026, 07, 28),
            LocalDate.of(2026, 10, 27),
            LocalDate.of(2027, 01, 26),
            LocalDate.of(2027, 04, 27),
            LocalDate.of(2027, 07, 27),
            LocalDate.of(2027, 10, 26),
            LocalDate.of(2028, 01, 25),
            LocalDate.of(2028, 04, 25),
            LocalDate.of(2028, 07, 25),
            LocalDate.of(2028, 10, 27),
            LocalDate.of(2028, 10, 27)
        };

        try {
            double tir = calculateXIRR(cashFlows, dates);
            System.out.println("TIR: " + (tir * 100) + "%");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static double calculateXIRR(double[] cashFlows, LocalDate[] dates) throws Exception {
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

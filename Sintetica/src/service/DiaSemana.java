package service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiaSemana {

    public static List<String> obtenerDiasOrdenados() {
        // Lista de los días en orden
        String[] dias = {"lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo"};

        // Día actual
        LocalDate hoy = LocalDate.now();
        DayOfWeek diaActual = hoy.getDayOfWeek(); // Ej: MONDAY

        // El índice se ajusta: DayOfWeek.MONDAY = 1 → índice 0
        int indiceActual = diaActual.getValue() - 1;

        List<String> diasOrdenados = new ArrayList<>();

        // Rellenar los 7 días en orden
        for (int i = 0; i < 7; i++) {
            diasOrdenados.add(dias[(indiceActual + i) % 7]);
        }

        return diasOrdenados;
    }

    public static void main(String[] args) {
        List<String> dias = obtenerDiasOrdenados();
        for (String dia : dias) {
            System.out.println(dia);
        }
    }
}

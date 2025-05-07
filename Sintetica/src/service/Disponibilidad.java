package service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;
import model.Reserva;

public class Disponibilidad {

    // Función que puedes llamar una sola vez al iniciar la app
    public static void iniciarVerificacionEnTiempoReal(Runnable actualizarVista) {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(actualizarVista);
            }
        }, 0, 60 * 1000); // cada minuto
    }

    public static boolean estaOcupadaAhora(String nombreCancha) {
        List<Reserva> reservas = obtenerReservasH.obtenerReservas();
        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now().withSecond(0).withNano(0);
    
        for (Reserva reserva : reservas) {
            if (reserva.getFecha().toLocalDate().equals(hoy) &&
                reserva.getTipoDeCancha().equalsIgnoreCase(nombreCancha)) {
    
                LocalTime inicio = reserva.getHora().toLocalTime();
                LocalTime fin = inicio.plusHours(1); // Duración de la reserva: 1 hora
    
                if (!ahora.isBefore(inicio) && ahora.isBefore(fin)) {
                    return true; // La cancha está ocupada en este momento
                }
            }
        }
        return false; 
    }
    
}



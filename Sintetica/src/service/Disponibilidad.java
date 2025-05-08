package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;

import Sqlconexion.Conexion;

public class Disponibilidad {

    // Verifica si una cancha está ocupada ahora, usando el ID de la cancha
    public static boolean estaOcupadaAhora(int canchaId) {
        String sql = "SELECT * FROM reservas WHERE id_cancha = ? AND fecha = ? AND ? BETWEEN hora AND ADDTIME(hora, '01:00:00')";
    
        try (Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, canchaId);
            stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setTime(3, java.sql.Time.valueOf(LocalTime.now()));
    
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // hay al menos una reserva
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return false;
    }
    

    // Actualiza el campo "estado" de la cancha en la base de datos (1 = ocupada, 0 = disponible)
    public static void actualizarEstadoEnBD(int canchaId, boolean ocupada) {
        String sql = "UPDATE canchas SET estado = ? WHERE id = ?";

        try (Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ocupada ? 1 : 0);
            stmt.setInt(2, canchaId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

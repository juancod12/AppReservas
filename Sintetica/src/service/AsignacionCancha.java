package service;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import Sqlconexion.Conexion;

public class AsignacionCancha {

    // Devuelve el ID de la primera cancha libre en orden de ID
    public static Integer asignarCanchaDisponible(String tipoCancha, LocalDate fecha, LocalTime hora) {
        List<Integer> canchas = obtenerCanchasPorTipo(tipoCancha);

        for (Integer canchaId : canchas) {
            if (!estaOcupada(canchaId, fecha, hora)) {
                return canchaId; // Primera cancha libre
            }
        }

        return null; // Todas ocupadas
    }

    // Devuelve los IDs de todas las canchas de un tipo específico, ordenadas por ID
    private static List<Integer> obtenerCanchasPorTipo(String tipoCancha) {
        List<Integer> ids = new ArrayList<>();

        String sql = "SELECT id FROM canchas WHERE tipo = ? ORDER BY id ASC";

        try (Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoCancha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }

    // Verifica si una cancha ya está ocupada a esa fecha y hora (bloque de 1 hora)
    private static boolean estaOcupada(int canchaId, LocalDate fecha, LocalTime hora) {
        String sql = """
            SELECT * FROM reservas
            WHERE id = ? AND fecha = ? 
            AND ? BETWEEN hora AND ADDTIME(hora, '01:00:00')
        """;

        try (Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, canchaId);
            stmt.setDate(2, Date.valueOf(fecha));
            stmt.setTime(3, Time.valueOf(hora));

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Si hay alguna fila, está ocupada

        } catch (Exception e) {
            e.printStackTrace();
            return true; // Por seguridad, asumir ocupada si hay error
        }
    }
}



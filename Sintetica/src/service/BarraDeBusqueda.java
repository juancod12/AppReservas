package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import Sqlconexion.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reserva;
/*obtiene las reservas por fecha y las carga a un Array con objetos de tipo Reserva */
public class BarraDeBusqueda {

    public static ObservableList<Reserva> obtenerReservasPorFecha(String Nombre) {
    ObservableList<Reserva> reservas = FXCollections.observableArrayList();
    String query = "SELECT nombre, fecha, hora, cancha, id, telefono, correo FROM reservas WHERE nombre = ?";

    try (Connection conn = Conexion.conectar();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, Nombre); // Establecer el parámetro de fecha en la consulta
       // Establecer el parámetro de fecha en la consulta
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String nombre = rs.getString("nombre");
            Date fech = rs.getDate("fecha");
            Time hora = rs.getTime("hora");
            String cancha = rs.getString("cancha");
            int id = rs.getInt("id");
            String telefono = rs.getString("telefono");
            String correo = rs.getString("correo");

            reservas.add(new Reserva(cancha, nombre, fech, hora, id, telefono, correo));
        
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    // Método para filtrar por nombre
    public   ObservableList<Reserva> filtrarPorNombre(ObservableList<Reserva> reservas, String textoBusqueda) {
        ObservableList<Reserva> resultados = FXCollections.observableArrayList(); // Creamos una lista vacía para almacenar las reservas que coinciden
        for (Reserva reserva : reservas) { // Recorremos todas las reservas en la lista
            if (reserva.getNombreUsuario().toLowerCase().contains(textoBusqueda.toLowerCase())) { // Comparamos el nombre de la reserva con el texto de búsqueda
                resultados.add(reserva); // Si el nombre contiene el texto de búsqueda, añadimos la reserva a la lista de resultados
            }
        }
        return resultados; // Retornamos la lista con las reservas que coinciden
    }
    /*Filtrar por fecha */
    public   ObservableList<Reserva> filtrarPorFecha(ObservableList<Reserva> reservas, String Fecha) {
        ObservableList<Reserva> resultados = FXCollections.observableArrayList(); // Creamos una lista vacía para almacenar las reservas que coinciden
        for (Reserva reserva : reservas) { // Recorremos todas las reservas en la lista
            if (reserva.getFecha().toString().equals(Fecha)) { // Comparamos el nombre de la reserva con el texto de búsqueda
                resultados.add(reserva); // Si el nombre contiene el texto de búsqueda, añadimos la reserva a la lista de resultados
            }
        }
        return resultados; // Retornamos la lista con las reservas que coinciden
    }


}

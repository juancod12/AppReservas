package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;

import Sqlconexion.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reserva;
/*Carga las reservas a un Array con objetos de tipo Reserva */
public class obtenerReservasH {

    public static ObservableList<Reserva> obtenerReservas() {
        ObservableList<Reserva> reservas = FXCollections.observableArrayList();
        String query = "SELECT nombre, fecha, hora, cancha, id, telefono, correo  FROM reservas";

        try (Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                
                String nombre = rs.getString("nombre");
                Date fech = rs.getDate("fecha");
                Time  hora = rs.getTime("hora");
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
    
    








    
}

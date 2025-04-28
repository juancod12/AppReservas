package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import Sqlconexion.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reserva;

public class SemanaReservas {

    public  ObservableList<Reserva> ObtenerResrva(String vista){
        ObservableList<Reserva> reservas = FXCollections.observableArrayList();
        String query = "SELECT nombre, hora, cancha, telefono FROM "+ vista;

        try (Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
        {
            
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                Time hora = rs.getTime("hora");
                String cancha = rs.getString("cancha");
                String telefono = rs.getString("telefono");
               
                reservas.add(new Reserva( nombre,cancha,  hora,  telefono));
            
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }



    






}

package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Sqlconexion.Conexion;
import model.Cancha;

public class RegistrarCancha {
    public static boolean registrarCancha(Cancha cancha){
    String tipo= cancha.getTipoDeCancha();
    int precio= cancha.getPrecio();
    Boolean estado =false;


    String sql = "INSERT INTO canchas (tipo,estado, precio) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo);
            stmt.setBoolean(2, estado);
            stmt.setInt(3, precio);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error al registrar cancha: " + e.getMessage());
            return false;
        }
    }
    
}

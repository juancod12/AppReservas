package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import Sqlconexion.Conexion;

public class cambiarClave {
    public static boolean CambiarClave(String usuario, String nuevaClave) {
        try {
            // Hashear la nueva clave
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(nuevaClave.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            String claveHasheada = sb.toString();

            // SQL para actualizar la clave
            String sql = "UPDATE administrador SET clave = ? WHERE usuario = ?";
            try (Connection conn = Conexion.conectar();
                    PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, claveHasheada);
                stmt.setString(2, usuario);

                int filasActualizadas = stmt.executeUpdate();
                return filasActualizadas > 0; // Devuelve true si se actualizó alguna fila

            } catch (SQLException e) {
                System.out.println("❌ Error al cambiar la clave: " + e.getMessage());
                return false;
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    
}

    


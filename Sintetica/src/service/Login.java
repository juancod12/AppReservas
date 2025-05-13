package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import Sqlconexion.Conexion;

public class Login {
        public static boolean login(String usuario, String clave) {
            try {
                // Hashear la contraseña ingresada
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = md.digest(clave.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }
                String claveHasheada = sb.toString();
    
                // Consulta para verificar
                String sql = "SELECT * FROM administrador WHERE usuario = ? AND clave = ?";
                try (Connection conn = Conexion.conectar();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
    
                    stmt.setString(1, usuario);
                    stmt.setString(2, claveHasheada);
    
                    try (ResultSet rs = stmt.executeQuery()) {
                        return rs.next(); // Si hay resultados, el usuario es válido
                    }
    
                } catch (SQLException e) {
                    System.out.println("❌ Error al verificar usuario: " + e.getMessage());
                    return false;
                }
    
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
        }
    }   


    /*login con solo clave */
    public static boolean login( String clave) {
        try {
            // Hashear la contraseña ingresada
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(clave.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            String claveHasheada = sb.toString();

            // Consulta para verificar
            String sql = "SELECT * FROM administrador WHERE  clave = ?";
            try (Connection conn = Conexion.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, claveHasheada);

                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // Si hay resultados, el usuario es válido
                }

            } catch (SQLException e) {
                System.out.println("❌ Error al verificar usuario: " + e.getMessage());
                return false;
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
    }
}   
}


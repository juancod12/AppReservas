package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Sqlconexion.Conexion;

public class RegistrarUsuario {
    
      public static boolean registrarUsuario(String usuario, String clave) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(clave.getBytes());
            
            // Convertir bytes a hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            clave =  sb.toString();
            String sql = "INSERT INTO administrador (usuario, clave) VALUES (?, ?)";
            try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, usuario);
                stmt.setString(2, clave);
                stmt.executeUpdate();
                return true;

            } catch (SQLException e) {
                System.out.println("❌ Error al registrar usuario: " + e.getMessage());
                return false;
            }
            

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
   


        
    }
}

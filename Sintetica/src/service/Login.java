package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Sqlconexion.Conexion;

public class Login {
      public static boolean login(String usuario, String clave) {
        String sql = "SELECT * FROM administrador WHERE usuario = ? AND clave = ?";
        try (Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, clave);
            ResultSet rs = stmt.executeQuery();

            // Si hay al menos un resultado, el usuario y la contraseña son correctos
            return rs.next();

        } catch (SQLException e) {
            System.out.println("❌ Error al intentar iniciar sesión: " + e.getMessage());
            return false;
        }
    }
    public static boolean login( String clave) {
        String sql = "SELECT * FROM administrador WHERE clave = ?";
        try (Connection conn = Conexion.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, clave);
            ResultSet rs = stmt.executeQuery();

            // 
            return rs.next();

        } catch (SQLException e) {
            System.out.println("❌ Error al intentar iniciar sesión: " + e.getMessage());
            return false;
        }
    }
}


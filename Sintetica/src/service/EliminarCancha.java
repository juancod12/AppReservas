package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Sqlconexion.Conexion;
import model.Cancha;

public class EliminarCancha {
     public static boolean eliminarCancha(Cancha cancha){
        String tipo= cancha.getTipoDeCancha();
        int precio= cancha.getPrecio();
        
    
    
        String sql = "DELETE FROM canchas WHERE tipo = ?  AND precio = ?";
            try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
    
                stmt.setString(1, tipo);
                stmt.setInt(2, precio);
                stmt.executeUpdate();
                return true;
    
            } catch (SQLException e) {
                System.out.println("❌ Error al eliminar la cancha: " + e.getMessage());
                return false;
            }
        }
}

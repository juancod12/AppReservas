package Sqlconexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

     // Datos de la conexión
     private static final String URL = "jdbc:mysql://localhost:3306/negocio";  // direcion de mi base de datos
     private static final String USUARIO = "root";   //usuario
     private static final String CONTRASEÑA = "juan12drh#";  //Password


     public static Connection conectar() {// metodo para conectar la base de datos
        Connection conexion = null;
        try {
            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("¡Conexión exitosa a la base de datos!");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }
    
  
 
    
}

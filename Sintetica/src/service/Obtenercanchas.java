package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Sqlconexion.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cancha;
/*se cargan las canchas desde la base de datos a un array de Objetos tipo Cancha */
public class Obtenercanchas {

    public static ObservableList<Cancha> obtenerCanchas() {
        ObservableList<Cancha> canchas = FXCollections.observableArrayList();
        String query = "SELECT id, tipo, estado, precio FROM canchas";

        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                String estado = rs.getString("estado");
                int precio = rs.getInt("precio");
                System.out.println(estado);
                if(estado.equals("0")){
                    estado= "Disponible";
                }else{
                    estado= "Ocupada";
                }
                canchas.add(new Cancha( id, tipo, estado, precio));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return canchas;
    }


}

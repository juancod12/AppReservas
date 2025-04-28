package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Reserva;

public class VistaCeldaReservas extends ListCell<Reserva> {
    // Definir los elementos gráficos para cada celda
    HBox hbox = new HBox(25); // Contenedor horizontal con un espaciado de 10 píxeles
    Label infoLabel = new Label();
    Label nombre = new Label();
    VBox infoContainer = new VBox(nombre,infoLabel);
    
    {
        infoContainer.setAlignment(Pos.TOP_LEFT);
        nombre.setPrefWidth(80);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(nombre,infoLabel);
        
        
    }

        @Override
        protected void updateItem(Reserva reservas, boolean empty) {
            super.updateItem(reservas, empty);
            if (empty || reservas == null) {
                setGraphic(null);
            } else {
                String Nombre =  size.ajustarTamaño(reservas.getNombreUsuario(), 15);
                String cancha =size.ajustarTamaño(reservas.getTipoDeCancha(), 27);
                String telefono =size.ajustarTamaño(reservas.getTelefono(), 27);
                String correo = size.ajustarTamaño(reservas.getCorreo(), 27);
                
                // Tamaños fijos para cada campo
                String info =  reservas.getFecha()+"          " +reservas.getHora() + "        "+ cancha + telefono + correo;   // 15 caracteres
            

                // Actualizar la información del label con los detalles de la cancha
                infoLabel.setText(info);
                nombre.setText(Nombre);
                
                // Asignar el HBox como el contenido gráfico de la celda.
                setGraphic(hbox);
            }
        }
           
}
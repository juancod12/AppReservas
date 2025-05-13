package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Reserva;

public class VistaCeldaReservaSemana extends ListCell<Reserva> {
    // Definir los elementos gráficos para cada celda
    HBox hbox = new HBox(25); 
    Label infoLabel = new Label();
    
    VBox infoContainer = new VBox(infoLabel);
    
    {
        infoContainer.setAlignment(Pos.TOP_LEFT);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(infoContainer); 
        
        // Establecer una fuente monoespaciada para asegurar una alineación uniforme
        infoLabel.setStyle("-fx-font-family: 'Courier New';");
    }

    @Override
    protected void updateItem(Reserva reservas, boolean empty) {
        super.updateItem(reservas, empty);
        if (empty || reservas == null) {
            setGraphic(null);
        } else {
            String nombre = size.ajustarTamaño(reservas.getNombreUsuario(), 23);
            String hora = size.ajustarTamaño(reservas.getHora().toString(), 23);
            String cancha = size.ajustarTamaño(reservas.getTipoDeCancha(), 23);
            String telefono = size.ajustarTamaño(reservas.getTelefono(), 23);
            
            // Tamaños fijos para cada campo
            String info = nombre + hora + cancha + telefono;

            // Actualizar la información del label con los detalles de la cancha
            infoLabel.setText(info);
            
            // Asignar el HBox como el contenido gráfico de la celda.
            setGraphic(hbox);
        }
    }
}

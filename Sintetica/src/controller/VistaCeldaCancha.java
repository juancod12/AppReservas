package controller;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Cancha;

import service.EliminarCancha;

public class VistaCeldaCancha extends ListCell<Cancha> {

        HBox hbox = new HBox(50); 
        Circle estadoCircle = new Circle(8); 
        Label infoLabel = new Label();
        Label infoLabel2 = new Label();
        VBox infoContainer = new VBox(infoLabel);
        Region espacio = new Region();
        Button deleteButton = new Button("Eliminar");
        

        /*configuracion de pocisionamiento */
        {
            infoContainer.setAlignment(Pos.TOP_LEFT);
            espacio.setMinWidth(10); // Define un espacio vacío antes del círculo
            infoLabel.setPrefWidth(100); // Ancho máximo
            infoLabel2.prefWidth(200); 
            // Configuración inicial del HBox y los componentes
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().addAll( infoLabel, espacio,infoLabel2, estadoCircle,deleteButton);
            // Ajustar márgenes de cada elemento
            HBox.setMargin(infoLabel, new Insets(5, 10, 5, 5)); // Margen superior, derecho, inferior, izquierdo
            HBox.setMargin(estadoCircle, new Insets(10, 10, 10, 10)); // Más espacio alrededor
            HBox.setMargin(deleteButton, new Insets(15, 0, 5, 10)); // Empuja el botón más abajo
            // Acción del botón de eliminar
            deleteButton.setOnAction(event -> {
                Cancha cancha = getItem();
                if (cancha != null) {
                    // Remover el elemento de la lista visual
                    getListView().getItems().remove(cancha);
                    // Aquí puedes agregar la lógica para eliminar la cancha de la base de datos si es necesario.
                    EliminarCancha.eliminarCancha(cancha);
                }
            });
        }
    
            @Override
            protected void updateItem(Cancha cancha, boolean empty) {
                super.updateItem(cancha, empty);
                if (empty || cancha == null) {
                    setText(null);
                    setGraphic(null);
                } else {
            
                    // Tamaños fijos para cada campo
                    String tipo = size.ajustarTamaño(cancha.getTipoDeCancha(), 20);   // 15 caracteres
                    String estado = size.ajustarTamaño(cancha.getEstado(), 20); // 10 caracteres
                    String precio = "$" + size.ajustarTamaño(String.valueOf(cancha.getPrecio()),10);  // No necesita ajuste

                    // Actualizar la información del label con los detalles de la cancha
                    infoLabel.setText(tipo);
                    infoLabel2.setText( estado + precio );
                    
                    // Cambiar el color del círculo según el estado de la cancha.
                    // Ejemplo: si el estado es "Disponible" o "Libre" se muestra en verde, de lo contrario en rojo.
                    if (cancha.getEstado().equalsIgnoreCase("Disponible") || cancha.getEstado().equalsIgnoreCase("Libre")) {
                        estadoCircle.setFill(Color.GREEN);
                    } else {
                        estadoCircle.setFill(Color.RED);
                    }
                    
                    // Asignar el HBox como el contenido gráfico de la celda.
                    setGraphic(hbox);
                }
            }
    

        
}



    


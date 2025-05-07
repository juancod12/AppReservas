package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import model.Cancha;
import service.EliminarCancha;
import service.Disponibilidad;

public class VistaCeldaCancha extends ListCell<Cancha> {

    private HBox hbox = new HBox(50);
    private Circle estadoCircle = new Circle(8);
    private Label infoLabel = new Label();
    private Label infoLabel2 = new Label();
    private VBox infoContainer = new VBox(infoLabel);
    private Region espacio = new Region();
    private Button deleteButton = new Button("Eliminar");

    private Timeline timeline; // Reemplazo de Timer para JavaFX

    public VistaCeldaCancha() {
        // Estilos y posición de elementos
        infoContainer.setAlignment(Pos.TOP_LEFT);
        espacio.setMinWidth(10);
        infoLabel.setPrefWidth(100);
        infoLabel2.setPrefWidth(200);

        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(infoLabel, espacio, infoLabel2, estadoCircle, deleteButton);

        HBox.setMargin(infoLabel, new Insets(5, 10, 5, 5));
        HBox.setMargin(estadoCircle, new Insets(10, 10, 10, 10));
        HBox.setMargin(deleteButton, new Insets(15, 0, 5, 10));

        // Acción del botón eliminar
        deleteButton.setOnAction(event -> {
            Cancha cancha = getItem();
            if (cancha != null) {
                getListView().getItems().remove(cancha);
                EliminarCancha.eliminarCancha(cancha);
            }
        });

        // Inicializa el Timeline para actualizar la disponibilidad cada minuto
        timeline = new Timeline(new KeyFrame(Duration.seconds(0), e -> actualizarDisponibilidad()),
                                new KeyFrame(Duration.minutes(1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // Método que revisa disponibilidad y actualiza el color del círculo
    private void actualizarDisponibilidad() {
        Cancha cancha = getItem();
        if (cancha != null) {
            System.out.println("Actualizando disponibilidad de: " + cancha.getTipoDeCancha());
            boolean ocupada = Disponibilidad.estaOcupadaAhora(cancha.getTipoDeCancha());
            estadoCircle.setFill(ocupada ? Color.RED : Color.LIMEGREEN);
        }
    }

    @Override
    protected void updateItem(Cancha cancha, boolean empty) {
        super.updateItem(cancha, empty);

        if (empty || cancha == null) {
            setText(null);
            setGraphic(null);
        } else {
            String tipo = size.ajustarTamaño(cancha.getTipoDeCancha(), 20);
            String estado = size.ajustarTamaño(cancha.getEstado(), 20);
            String precio = "$" + size.ajustarTamaño(String.valueOf(cancha.getPrecio()), 10);

            infoLabel.setText(tipo);
            infoLabel2.setText(estado + precio);

            // Refresca disponibilidad al cambiar de celda también
            actualizarDisponibilidad();

            setGraphic(hbox);
        }
    }
}

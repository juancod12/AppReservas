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
import service.Disponibilidad;


public class VistaCeldaCancha extends ListCell<Cancha> {

    HBox hbox = new HBox(50);
    Circle estadoCircle = new Circle(8);
    Label infoLabel = new Label();
    Label infoLabel2 = new Label();
    VBox infoContainer = new VBox(infoLabel);
    Region espacio = new Region();
    Button deleteButton = new Button("Eliminar");

    {
        java.util.Timer timer = new java.util.Timer(true);
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
    @Override
    public void run() {
        javafx.application.Platform.runLater(() -> {
            Cancha cancha = getItem();
            if (cancha != null) {
                boolean ocupada = Disponibilidad.estaOcupadaAhora(cancha.getId());

                // 🔁 Actualiza el objeto en memoria
                cancha.setEstado(ocupada ? 1 : 0);

                // 🔁 Cambia color del círculo visual
                estadoCircle.setFill(ocupada ? Color.RED : Color.LIMEGREEN);

                // 🔁 Actualiza en la base de datos
                Disponibilidad.actualizarEstadoEnBD(cancha.getId(), ocupada);

                // 🔁 Actualiza texto del estado
                infoLabel2.setText(size.ajustarTamaño(
                    ocupada ? "Ocupada" : "Disponible", 20
                ) + " $" + cancha.getPrecio());

                System.out.println("🔄 Cancha " + cancha.getId() + " actualizada. Ocupada: " + ocupada);
            }
        });
    }
}, 0, 60 * 1000);

    }

    {
        infoContainer.setAlignment(Pos.TOP_LEFT);
        espacio.setMinWidth(10);
        infoLabel.setPrefWidth(100);
        infoLabel2.setPrefWidth(200);

        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(infoLabel, espacio, infoLabel2, estadoCircle, deleteButton);

        HBox.setMargin(infoLabel, new Insets(5, 10, 5, 5));
        HBox.setMargin(estadoCircle, new Insets(10, 10, 10, 10));
        HBox.setMargin(deleteButton, new Insets(15, 0, 5, 10));

        deleteButton.setOnAction(event -> {
            Cancha cancha = getItem();
            if (cancha != null) {
                getListView().getItems().remove(cancha);
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
            infoLabel.setText(size.ajustarTamaño(cancha.getTipoDeCancha(), 20));
            infoLabel2.setText(size.ajustarTamaño(cancha.getEstado() == 1 ? "Ocupada" : "Disponible", 20) + 
                            " $" + cancha.getPrecio());

            boolean ocupada = Disponibilidad.estaOcupadaAhora(cancha.getId());
            estadoCircle.setFill(ocupada ? Color.RED : Color.LIMEGREEN);
            Disponibilidad.actualizarEstadoEnBD(cancha.getId(), ocupada); // estado en tabla `canchas`


            setGraphic(hbox);
        }
    }
}

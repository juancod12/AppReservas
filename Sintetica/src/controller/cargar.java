package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class cargar {
    public String url="";


    public void cargarRecursos(ActionEvent event, String url ){
        try {
        // Cargar la nueva escena desde un archivo FXML
                        Parent nuevaVista = FXMLLoader.load(getClass().getResource(url));
                        Scene nuevaEscena = new Scene(nuevaVista);

                        // Obtener el Stage actual
                        Stage stageActual = (Stage) ((Node)event.getSource()).getScene().getWindow();

                        // Reemplazar la escena en la misma ventana
                        stageActual.setScene(nuevaEscena);
                        stageActual.show();

            } catch (IOException ex) {
                     ex.printStackTrace();
             }
    }
    
    public void cargarRecursos(ActionEvent event, String url ,String Nombre ){
        try {
        // Cargar la nueva escena desde un archivo FXML
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
                        Parent root = loader.load();

                        // Obtener el Stage actual
                         Stage ventanaEmergente = new Stage();
                        ventanaEmergente.setTitle(Nombre);
                        ventanaEmergente.setScene(new Scene(root));
                        ventanaEmergente.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal
                        ventanaEmergente.showAndWait(); // Espera hasta que se cierre

            } catch (IOException ex) {
                     ex.printStackTrace();
             }
    }
}

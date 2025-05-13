package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.cambiarClave;



public class CambioClaveController {
@FXML
public TextField txtPasswordNueva;
@FXML
public TextField txtPasswordConfirmar;
@FXML
public TextField txUsuario;
@FXML
public Label MensajeDiferentesClave;
@FXML
public Label MensajeExito;
@FXML
public Label MensajeError;





     /* metodo iniciar */
@FXML
void initialize() {

    
    

}
@FXML
void GuardarNuevaClave(ActionEvent event) {

    String ClaveNueva = txtPasswordNueva.getText();
    String Usuario =txUsuario.getText()  ;
    String ClaveCnfirma =txtPasswordConfirmar.getText()  ;
    if(ClaveCnfirma.equals(ClaveNueva)){
        boolean cambiada = cambiarClave.CambiarClave(Usuario,ClaveNueva);
        if (cambiada) {
            MensajeExito.setVisible(true);
            System.out.println("🔒 Clave cambiada correctamente.");
        } else {
            MensajeError.setVisible(true);
            System.out.println("❌ No se pudo cambiar la clave.");
            }
        
    }else{
        MensajeDiferentesClave.setVisible(true);

    }   

    }

    
}

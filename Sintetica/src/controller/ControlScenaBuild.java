package controller;
import java.io.IOException;

import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;



import javafx.scene.Node;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.Login;
import service.RegistrarUsuario;
public class ControlScenaBuild  {
    
    @FXML
    private Label messExitoGuardado;
    @FXML
    private Label messaComplete;
    @FXML
    private ResourceBundle resources;
    /*Boton de registrar  */
    @FXML
    private Button registrarse;
    /*ususarios (texto, field)  declaracion */
    @FXML
    private TextField usuario;
    @FXML
    private Label texusuario;
    /*Contrasena (texto, field)  declaracion */
    @FXML
    private PasswordField password;
    @FXML
    private Label texpassword;
    
    /*Label del mensaje error  declaracion */
    @FXML
    private Label mensajeError;
    
     /*Barra de progreso (texto,progressbar)  declaracion */
    @FXML
    private Label texprogresbar;
    @FXML
    private ProgressBar progresbar;

    /*botton de guardar datos  */
    @FXML
    private Button guardarDatos;
     
    /*lin de de localizacion del archivo */
    @FXML
    private URL location;
    


    /* metodo iniciar*/
    @FXML 
    void initialize() {
       
       
    } 
  

    // Ventana de Registro
    @FXML
    void Registrarse(ActionEvent event ) {
        
        String url="/util/gurdarUsuarioAdmin.fxml";
        cargar load = new cargar();
        load.cargarRecursos( event,  url); 
        
        
            
    }
    @FXML
    void  volvelInicio(ActionEvent event){
        String url="/util/login.fxml";
        cargar load = new cargar();
        load.cargarRecursos( event,  url); 

    }


    //accion del botton para guardar los datos en la base de datos
    @FXML
    void GuardarDatos(ActionEvent event){
        messaComplete.setVisible(false);
       /*se extraen los datos de los texfile */
        String us = usuario.getText();
        String clave = password.getText();

        System.out.println("usurario: " + us + "contraseña: "+clave);

        /*se verifica que los campos tengan contenido */
        if (us.isEmpty() || clave.isEmpty()) {
        System.out.println("❌ Por favor, complete todos los campos.");
        messaComplete.setVisible(true);

        return;
        }
        boolean exito = RegistrarUsuario.registrarUsuario(us, clave);
        if (exito) {
        System.out.println("✅ Usuario registrado correctamente.");
        messExitoGuardado.setVisible(true);
        } else {
        System.out.println("❌ Error al registrar usuario.");
        }
    
    }
     /*Pantalla de incion, accion de loggearse y pasar a la ventana principal */
    @FXML
    void IniciarSesion(ActionEvent event) {

        //Obtener el nombre de usuario y la contraseña
        String us = usuario.getText();
        String clave = password.getText();

        //Verificar si los campos están vacíos
        if (us.isEmpty() || clave.isEmpty()) {
            mensajeError.setText("❌ Por favor, complete todos los campos.");
            mensajeError.setVisible(true);
            return;
        }

        
        //Validar las credenciales contra la base de datos
        boolean loginExitoso = Login.login(us, clave);



        if (loginExitoso) {
            
            usuario.setVisible(false);
            usuario.setManaged(false);

            password.setVisible(false);
            password.setManaged(false);

            texpassword.setManaged(false);
            texpassword.setVisible(false); 

            texusuario.setManaged(false);
            texusuario.setVisible(false); 

            texprogresbar.setVisible(true);
            progresbar.setVisible(true);

            System.out.println("Inicio de sesión exitoso.");
            //ocultar los label y poner la barra de progreso
            
                Timeline timeline = new Timeline( 
                    new KeyFrame(Duration.seconds(2), e -> progresbar.setProgress(0.6)),
                    new KeyFrame(Duration.seconds(3), e -> progresbar.setProgress(1.0)),
                    new KeyFrame(Duration.seconds(4), e -> progresbar.setProgress(1.0))
                    
                    );
                    timeline.setCycleCount(1); // Ejecutar una sola vez
                    timeline.play();

                // Definir qué hacer cuando la animación termine
                timeline.setOnFinished(e -> {
                        
                        try {
                            Thread.sleep(1000);  // Espera 5 segundos (5000 milisegundos)
                            String url="/util/iniciarSesion.fxml";
                            cargar load = new cargar();
                            load.cargarRecursos( event,  url);
                        } catch (InterruptedException es) {
                            es.printStackTrace();
                        }
                       });    


        } else {
            //Si las credenciales son incorrectas, mostrar un mensaje de error
            mensajeError.setText("❌ Usuario o contraseña incorrectos."); // Asignar texto al Label
            mensajeError.setVisible(true);
        }
        
    }
    


}//cierre final








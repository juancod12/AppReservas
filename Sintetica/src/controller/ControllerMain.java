package controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.Cancha;
import model.Reserva;
import service.BarraDeBusqueda;
import service.DiaSemana;
import service.EliminarCancha;
import service.Login;
import service.Obtenercanchas;
import service.RegistrarCancha;
import service.SemanaReservas;
import service.obtenerReservasH;

public class ControllerMain {
/*paneles de la pagina principal */
@FXML
private Pane paneCanchas;
@FXML
private Pane penelNewCancha;
@FXML
private Pane PaneHistorial;
@FXML
private Pane paneReserva;

/* crear cancha nueva elementos */
@FXML
private Button ButtoNew; // boton de cancha nueva
@FXML
private Button canchas;// boton de canchas
@FXML
private TextField txTipo; // texto de tipo de cancha
@FXML
private TextField txPrecio; // texto de precio
@FXML
private Button buttonAgregarCancha; // boton de agregar cancha
@FXML
private Label TxExito; // mensaje de gardado
@FXML
private ListView<Cancha> listaCanchas;
private ObservableList<Cancha> canchasList = FXCollections.observableArrayList();
/*Elementos de Reserva */
@FXML
private Button reservas;
@FXML
private ListView<Reserva> ReservasHoy;
private ObservableList<Reserva> listaR_Hoy = FXCollections.observableArrayList();
@FXML
private Tab taphoy;

@FXML
private ListView<Reserva> ReservasUno;
private ObservableList<Reserva> listaR_uno = FXCollections.observableArrayList();
@FXML
private Tab tapuno;

@FXML
private ListView<Reserva> ReservasDos;
private ObservableList<Reserva> listaR_dos = FXCollections.observableArrayList();
@FXML
private Tab tapdos;


@FXML
private ListView<Reserva> ReservasTres;
private ObservableList<Reserva> listaR_tres = FXCollections.observableArrayList();
@FXML
private Tab taptres;


@FXML
private ListView<Reserva> ReservasCuatro;
private ObservableList<Reserva> listaR_cuatro = FXCollections.observableArrayList();
@FXML
private Tab tapcuatro;


@FXML
private ListView<Reserva> ReservasCinco;
private ObservableList<Reserva> listaR_cinco = FXCollections.observableArrayList();
@FXML
private Tab tapcinco;


@FXML
private ListView<Reserva> ReservasSeis;
private ObservableList<Reserva> listaR_seis = FXCollections.observableArrayList();
@FXML
private Tab tapseis;
private ObservableList<String> Dias = FXCollections.observableArrayList();

/*elementos de historial */
@FXML
private Button HistorialButton;

@FXML
private TextField search;

@FXML
private ListView<Reserva>  HistorialViw;
private ObservableList<Reserva> reservaslist = FXCollections.observableArrayList();
/*elemtos del filtro */
@FXML
private ComboBox<String> DiaFiltro;
@FXML
private ComboBox<String> MesFiltro;

@FXML
private ComboBox<String> AnoFiltro;

/*elementos de cambio de contraseña */
@FXML
private AnchorPane paneCambioLogin;
@FXML
private PasswordField paswordConfig;


/*Ocultamiento de panel  */

public void ocultarpaneles(Pane panel1,Pane panel2,Pane panel3){
    ArrayList<Pane> Paneles = new ArrayList<>(); ;
    Paneles.add(panel1);
    Paneles.add(panel2);
    Paneles.add(panel3);
    for( int i=0; i < Paneles.size(); i++){
        Pane panel = Paneles.get(i);
        if(panel != null){ 
            System.out.println("ocultando paneles");
            panel.setVisible(false);
            panel.setManaged(false);
        }
       
    }


}
/*metodo para darle nombre a los  tab de dias de la semana */
public void NombrarTap(Tab uno,Tab dos,Tab tres,Tab cuatro,Tab cinco,Tab seis,Tab siete){
    Dias.setAll(DiaSemana.obtenerDiasOrdenados());
    Tab[] tabs = { uno, dos, tres, cuatro, cinco, seis, siete };

    for (int i = 0; i < tabs.length; i++) {
        tabs[i].setText(Dias.get(i));
    }

}
/*hora del dia */
@FXML
private Label HoraLabel;
public void mostrarHoraEnTiempoReal(Label Hora) {
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("hh:mm a");

    Timeline reloj = new Timeline(
        new KeyFrame(Duration.seconds(0), event -> {
            LocalTime ahora = LocalTime.now();
            String horaActual = ahora.format(formato).toLowerCase();
            Hora.setText( horaActual );
        }),
        new KeyFrame(Duration.seconds(1))
    );

    reloj.setCycleCount(Timeline.INDEFINITE);
    reloj.play();
}



    /* metodo iniciar */
    @FXML
    void initialize() {
        mostrarHoraEnTiempoReal(HoraLabel);
       
       

    }

    /* accion del boton canchas */
    @FXML
    void Canchas(ActionEvent event) {
        ocultarpaneles(paneReserva,  PaneHistorial,null);

        System.out.println("dentro del botton");
        paneCanchas.setVisible(true);
        paneCanchas.setManaged(true);

        // Cargar las canchas desde la base de datos
        canchasList.setAll(Obtenercanchas.obtenerCanchas());
        listaCanchas.setItems(canchasList);

        listaCanchas.setCellFactory(param -> new VistaCeldaCancha());        
}




/* Canchas-[Accion del botton nueva cancha] */
@FXML
void CrearCancha(ActionEvent event) {
    ocultarpaneles(paneReserva,  PaneHistorial,paneCanchas);
    System.out.println("funciona crear");
    penelNewCancha.setVisible(true);

}

/*Canchas-[Accion del botton Crear Cancha] */
@FXML
void GuardarCancha(ActionEvent event) {
    
    System.out.println("se preciono");

    String tipo = txTipo.getText();
    int precio = Integer.parseInt(txPrecio.getText());
    Cancha nuevo = new Cancha(tipo, precio);
    boolean si = RegistrarCancha.registrarCancha(nuevo);
    if (si == false) {
        System.out.println("no se guardo nada");
    } else {
        System.out.println("gardado exitoso");
        TxExito.setVisible(true);
        txPrecio.clear();
        txTipo.clear();

    }

}


/*Canchas-[Accion del botto cerrrar] */
@FXML
void CerrarCrear() {
    System.out.println("se cerro la ventana");
    
    penelNewCancha.setVisible(false);
    paneCanchas.setVisible(true);
   
    // Recargar los datos desde la base de datos
    canchasList.setAll(Obtenercanchas.obtenerCanchas());

    // Asegurar que la lista se actualice en la vista
    listaCanchas.refresh(); 


}


/*Accion Accion del botton Reservas */
@FXML
void mostrarReservas(ActionEvent event){
    ocultarpaneles(paneCanchas, PaneHistorial,penelNewCancha);
    System.out.println("estoy en reservas");
    //mostrar las reservas del dia pagina principal 
    paneReserva.setVisible(true);


    /*colocando el nombre de los tap */
    NombrarTap(taphoy,tapuno,tapdos,taptres,tapcuatro,tapcinco,tapseis);

    // Cargar las canchas desde la base de datos
    SemanaReservas obj=new SemanaReservas();

    
    listaR_Hoy.setAll(obj.ObtenerResrva("reservas_del_dia"));
    ReservasHoy.setItems(listaR_Hoy);
    // Personalizar cómo se muestra cada elemento en el ListView
    ReservasHoy.setCellFactory(param -> new VistaCeldaReservaSemana());
    
 
    listaR_uno.setAll(obj.ObtenerResrva("reserva_dia_uno"));
    ReservasUno.setItems(listaR_uno);
    // Personalizar cómo se muestra cada elemento en el ListView
    ReservasUno.setCellFactory(param -> new VistaCeldaReservaSemana());

    listaR_dos.setAll(obj.ObtenerResrva("reserva_dia_dos"));
    ReservasDos.setItems(listaR_dos);
    // Personalizar cómo se muestra cada elemento en el ListView
    ReservasDos.setCellFactory(param -> new VistaCeldaReservaSemana());

    listaR_tres.setAll(obj.ObtenerResrva("reserva_dia_tres"));
    ReservasTres.setItems(listaR_tres);
    // Personalizar cómo se muestra cada elemento en el ListView
    ReservasTres.setCellFactory(param -> new VistaCeldaReservaSemana());

    listaR_cuatro.setAll(obj.ObtenerResrva("reserva_dia_cuatro"));
    ReservasCuatro.setItems(listaR_cuatro);
    // Personalizar cómo se muestra cada elemento en el ListView
    ReservasCuatro.setCellFactory(param -> new VistaCeldaReservaSemana());

    listaR_cinco.setAll(obj.ObtenerResrva("reserva_dia_cinco"));
    ReservasCinco.setItems(listaR_cinco);
    // Personalizar cómo se muestra cada elemento en el ListView
    ReservasCinco.setCellFactory(param -> new VistaCeldaReservaSemana());

    listaR_seis.setAll(obj.ObtenerResrva("reserva_dia_seis"));
    ReservasSeis.setItems(listaR_seis);
    // Personalizar cómo se muestra cada elemento en el ListView
    ReservasSeis.setCellFactory(param -> new VistaCeldaReservaSemana());
 
}

/*Accion del botton Historial */
@FXML
void VerHistorial(ActionEvent event){
    System.out.println("Dentro de historial");
    ocultarpaneles(paneCanchas, paneReserva, null);

    
    //paneReserva.setVisible(true);
    PaneHistorial.setVisible(true);
    PaneHistorial.setManaged(true);
    for (int i = 1; i <= 31; i++) {
        if(i>=10){ DiaFiltro.getItems().add(i+"");}
        else{DiaFiltro.getItems().add("0"+i);}
        
       
    }
    for (int i = 1; i <= 12; i++) {
        if(i>=10){ MesFiltro.getItems().add(i+"");}
        else{MesFiltro.getItems().add("0"+i);}

    }
     String[] anos = {"2025","2026","2027","2028","2029","2030"};
    AnoFiltro.getItems().addAll(anos);

    System.out.println("cargando reservas");
    // Cargar las canchas desde la base de datos
    reservaslist.setAll(obtenerReservasH.obtenerReservas());
    HistorialViw.setItems(reservaslist);

    System.out.println("Reservas obtenidas: " + reservaslist.size());

    // Personalizar cómo se muestra cada elemento en el ListView
    HistorialViw.setCellFactory(param -> new VistaCeldaReservas());
    

}

/*Historial-[filto por nombre]*/
@FXML
void FiltrarPorNombre(KeyEvent event){
   
    String textoBusqueda = search.getText().toLowerCase(); // Obtener el texto ingresado
    BarraDeBusqueda  ob= new BarraDeBusqueda();
    ObservableList<Reserva> reservasFiltradas = ob.filtrarPorNombre(reservaslist, textoBusqueda); // Filtrar las reservas
    HistorialViw.setItems(reservasFiltradas);
    HistorialViw.setCellFactory(param -> new VistaCeldaReservas());; // Actualizar la vista con los resultados filtrados
    
    
}
/*filtrarpor fecha */
@FXML
void FiltrarPorFecha(){
    String Dia = DiaFiltro.getValue();
    String Mes = MesFiltro.getValue();
    String Ano = AnoFiltro.getValue();
    String Fecha = Ano+"-"+ Mes +"-"+ Dia ;
    BarraDeBusqueda  ob= new BarraDeBusqueda();
    ObservableList<Reserva> reservasFiltradas = ob.filtrarPorFecha(reservaslist, Fecha); // Filtrar las reservas
    HistorialViw.setItems(reservasFiltradas);
    HistorialViw.setCellFactory(param -> new VistaCeldaReservas());; // Actualizar la vista con los resultados filtrados
}

/*Accion del boton configuraciones */
@FXML
void Configuraciones(){
    System.out.println("dentro a la ventana");
    paneCambioLogin.setVisible(true);
    paneCambioLogin.setManaged(true);
}

/* configuraciones-[Accion del botton continuar] */
@FXML
void ConfigContinuar(ActionEvent event){
        //Obtener el nombre de usuario y la contraseña
    System.out.println("configurar funciona");
    String clave = paswordConfig.getText();
    //Validar las credenciales contra la base de datos
    boolean loginExitoso = Login.login(clave);
    if(loginExitoso){
            paneCambioLogin.setVisible(false);
            paneCambioLogin.setManaged(false);
            String url="/util/CambioClave.fxml";
            cargar load = new cargar();
            load.cargarRecursos( event,  url,"Configuaracion de Usuario");
    }

}
/*configuraciones-[Accion del botton cerrar] */

@FXML
void CerrarConfig(){
    paneCambioLogin.setVisible(false);
    paneCambioLogin.setManaged(false);


}
    



}


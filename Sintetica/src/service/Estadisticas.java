package service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cancha;
import model.Reserva;

public class Estadisticas {
    

    public static int[] DiasFrecuentes(){
            

            int[] dias = new int[7];
            int lunes = 1;
            int martes = 1;
            int miercoles = 1;
            int jueves = 1;
            int viernes = 1;
            int sabado = 1;
            int domingo = 1;
            ObservableList<Reserva> Reservas = FXCollections.observableArrayList();
            Reservas.setAll(obtenerReservasH.obtenerReservas());
            for(Reserva Reserva:Reservas){
                Date fecha1=Reserva.getFecha();
                LocalDate fecha = fecha1.toLocalDate();
                DayOfWeek diaSemana = fecha.getDayOfWeek();
                LocalDate hoy = LocalDate.now();
                int dia = hoy.getMonthValue();
                int diaReserva = fecha.getMonthValue();
                if(dia==1){dia=13;}
                
                if((dia-1)== diaReserva){

                        switch (diaSemana) {
                            case DayOfWeek.MONDAY:
                                dias[0]=lunes;
                                lunes++;
                                
                                break;
                            case DayOfWeek.TUESDAY:
                                dias[1]=martes;
                                martes++;
                                
                                break;
                            case DayOfWeek.WEDNESDAY:
                                dias[2]=miercoles;
                                miercoles++;
                                
                                break;
                            case DayOfWeek.THURSDAY:
                                dias[3]=jueves;
                                jueves++;
                                
                                break;
                                case DayOfWeek.FRIDAY:
                                dias[4]=viernes;
                                viernes++;
                                
                                break;
                            case DayOfWeek.SATURDAY:
                                dias[5]=sabado;
                                sabado++;
                                
                                break;
                            case DayOfWeek.SUNDAY:
                                dias[6]=domingo;
                                domingo++;
                                
                                break;
                                
                            default:
                            System.out.println("fallo en el conteo de los dias frecuentes");
                                break;
                        }
                }


            }
          return dias;
        }




    public static int[] GananciasMes() {
        int[] Ganancias = new int[31]; // Suponiendo máximo 31 días

        ObservableList<Reserva> Reservas = FXCollections.observableArrayList();
        Reservas.setAll(obtenerReservasH.obtenerReservasEstadisticas());

        ObservableList<Cancha> canchas = FXCollections.observableArrayList();
        canchas.setAll(Obtenercanchas.obtenerCanchas());

        LocalDate hoy = LocalDate.now();
        int mesActual = hoy.getMonthValue();
        int anioActual = hoy.getYear();

        for (Reserva reserva : Reservas) {
            LocalDate fecha = reserva.getFecha().toLocalDate();
            int diaReserva = fecha.getDayOfMonth();

            if (fecha.getMonthValue() == mesActual && fecha.getYear() == anioActual) {
                int idCancha = reserva.getId(); // Asegúrate de tener este método

                // Buscar la cancha correspondiente
                for (Cancha cancha : canchas) {
                    if (cancha.getId() == idCancha) { // Asegúrate de tener getId() y getPrecio()
                        Ganancias[diaReserva - 1] += cancha.getPrecio();
                        break; // Salimos del loop si ya encontramos la cancha
                    }
                }
            }
        }

        return Ganancias;
    }


      
}


    


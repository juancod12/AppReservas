package model;
import java.sql.Date;
import java.sql.Time;


public class Reserva extends Datos{
    // Atributos en minúscula
    private String nombreUsuario;  
    private Date fecha;       
    private Time hora;        
    private int id;           
    private String telefono;         
    private String correo;

    
    public Reserva( String nombreUsuario,String tipoDeCancha,  Time hora, String telefono) {
        super(tipoDeCancha);
        this.nombreUsuario = nombreUsuario;
        this.hora = hora;
        this.telefono = telefono;
    }

    public Reserva(String tipoDeCancha, String nombreUsuario, Date fecha, Time hora, int id, String telefono,
            String correo) {
        super(tipoDeCancha);
        this.nombreUsuario = nombreUsuario;
        this.fecha = fecha;
        this.hora = hora;
        this.id = id;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public Date getFecha() {
        return fecha;
    }
    public Time getHora() {
        return hora;
    }
    public int getId() {
        return id;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getCorreo() {
        return correo;
    }
    
    
}
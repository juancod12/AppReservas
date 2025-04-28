package model;

public class Cancha extends Datos{
    private String estado; 
    private int precio;
    private int id;
    public Cancha(int id, String tipoDeCancha, String estado, int precio) {
        super( tipoDeCancha);
        this.estado = estado;
        this.precio = precio;
        this.id = id;
    }
    public Cancha(String tipoDeCancha, int precio) {
        super( tipoDeCancha);
        this.precio = precio;
    }


    public String getEstado() {
        return estado;
    }
    public int getPrecio() {
        return precio;
    }
    
   

}

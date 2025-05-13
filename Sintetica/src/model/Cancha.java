package model;

public class Cancha extends Datos{
    private int estado; 
    private int precio;
    private int id;
    public Cancha(int id, String tipoDeCancha, int estado, int precio) {
        super( tipoDeCancha);
        this.estado = estado;
        this.precio = precio;
        this.id = id;
    }
    public Cancha(String tipoDeCancha, int precio) {
        super( tipoDeCancha);
        this.precio = precio;
    }
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    public int getPrecio() {
        return precio;
    }
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    
}


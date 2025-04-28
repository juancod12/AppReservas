package model;
/* Clase abstracta para encapsular  (reservas y canchaSintetica),como datos para cargarlos a la interfaz */
public abstract class Datos {
    private String tipoDeCancha;


    public Datos( String tipoDeCancha) {
        this.tipoDeCancha = tipoDeCancha;
    }

    

    public String getTipoDeCancha() {
        return tipoDeCancha;
    }

}

package controller;



//funcion para recortar el texto
public class size {
    
    public static String ajustarTamaño(String texto, int tamaño) {
        if (texto.length() > tamaño) {
            return texto.substring(0, tamaño);  // Recorta si es demasiado largo
        } else {
            return String.format("%-" + tamaño + "s", texto); // Rellena con espacios si es corto
        }
    }
   
    
}

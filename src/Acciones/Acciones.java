package Acciones;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

/**
 *
 * @author miguel
 */
public class Acciones {
    
    public static ObservableList getNombresFiltros(){
        return FXCollections.observableArrayList(
                "Tonos de grises por promedio",
                "Tonos de grises por color",
                "Tonos de grises por porcentaje",
                "Mosaico",
                "Red, Green or Blue",
                "Micas",
                "Blur",
                "Motion Blur",
                "Encontrar Bordes Verticales",
                "Encontrar Bordes Horizontales",
                "Encontrar Bordes Diagonales",
                "Encontrar Bordes en todas las direcciones",
                "Sharpen",
                "Emboss",
                "Brillo",
                "Alto Contraste",
                "Inverso",
                "Imagenes Recursivas Colores Reales",
                "Imagenes Recursivas Tonos de Grises",
                "Una sola letra (Color)",
                "Una sola letra (Tons de Gris)",
                "Letras en blanco y negro"
        );
    }
    
    /**
     * Restringe los textFields para que solo se puedan ingresar números.
     *
     * @param tf
     */
    public static void restringeTextFields(TextField... tf) {
        for (TextField t : tf) {
            t.setOnKeyReleased((event) -> {
                String str = t.getText();
                String aux = "";
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                        aux += str.charAt(i);
                    } else {
                        t.setText(aux);
                        t.positionCaret(aux.length());
                    }
                }
            });
        }
    }
    
    
}

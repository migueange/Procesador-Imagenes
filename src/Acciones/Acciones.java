package Acciones;

import Vista.ContenedorImagenes;
import Vista.Mensajes;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Modela Algunas acciones de la Vista.
 *
 * @author miguel
 */
public class Acciones {

    public static ObservableList getNombresFiltros() {
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
                "Letras en blanco y negro",
                "Colores con texto (Letrero)",
                "Fotos con fichas de dominó",
                "Fotos con Naipes",
                "Sepia",
                "Óleo",
                "MAX",
                "MIN",
                "Semi-tonos",
                "Blending",
                "Luz Negra",
                "Icono",
                "Quitar marca de agua"
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

    /**
     *
     * @param progressIndicator
     * @param opciones
     * @param guardarImagen
     * @param procesar
     * @param task
     * @param contenedorImagenes
     * @param procesada
     * @param imagen
     */
    public static void comienzaProceso(ProgressIndicator progressIndicator, HBox opciones, Button guardarImagen, Button procesar, Task task, ContenedorImagenes contenedorImagenes, BufferedImage procesada, File imagen) throws IOException {
        progressIndicator.setVisible(true);
        opciones.setVisible(false);
        guardarImagen.setDisable(false);
        procesar.setDisable(true);
        task.setOnSucceeded(e -> {
            progressIndicator.setVisible(false);
            opciones.setVisible(true);
            procesar.setDisable(false);
            try {
                contenedorImagenes.setImagenProcesada(procesada, imagen);
            } catch (IOException ex) {
                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
            }
        });
        progressIndicator.progressProperty().unbind();
        progressIndicator.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

}

package Vista;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 *
 * @author miguel
 */
public class Mensajes {

    /**
     * Muestra un mensaje de error.
     * @param msj1
     * @param msj2 
     */
    public static void muestraError(String msj1, String msj2) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(msj1);
        alert.setContentText(msj2);
        alert.showAndWait();
    }

    /**
     * Mensaje de Confirmación.
     * @param alertType
     * @param titulo
     * @param msj1
     * @param msj2
     * @return 
     */
    public static Optional<ButtonType> muestraConfirmacion(AlertType alertType,String titulo, String msj1, String msj2) {
        Alert alert = new Alert(alertType);
        alert.initStyle(StageStyle.UTILITY);       
        alert.setTitle(titulo);
        alert.setHeaderText(msj1);
        alert.setContentText(msj2);                        
        alert.getButtonTypes().addAll(new ButtonType("Cancelar",ButtonData.CANCEL_CLOSE));        
        return alert.showAndWait();
    }

}
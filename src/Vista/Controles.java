package Vista;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author miguelita
 */
public class Controles extends HBox {

    private final ComboBox selectorFiltro;
    private final Pane opciones;
    private final Button procesar;
    private final Button cargarImagen;
    private final FileChooser fileChooser;
    private File imagen;

    /**
     *
     * @param stage
     */
    public Controles(Stage stage) {
        super();
        /*ChoiceBox para escoger filtro*/
        selectorFiltro = new ComboBox(FXCollections.observableArrayList(
                "Tonos de grises",
                "Convertir a grises 1",
                "Convertir a grises 2",
                "Red, Grenn or Blue",
                "Micas"
        ));
        selectorFiltro.setPromptText("Seleccionar filtro");
        /*Opciones de cada filtro*/
        opciones = new Pane();
        /*Selector de archivo*/
        fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar Imagen");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.jpg", "*.png"));
        /*Abrir FileChooser*/
        cargarImagen = new Button("Cargar Imagen");
        cargarImagen.setOnAction(event -> {
            imagen = fileChooser.showOpenDialog(stage);
        });
        /*Empezar proceso*/
        procesar = new Button("Procesar");
        procesar.setOnAction(event -> {
            //Empieza Proceso
        });
        super.getChildren().addAll(new VBox(cargarImagen, selectorFiltro),opciones,procesar);
    }

}

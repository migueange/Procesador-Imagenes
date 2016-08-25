package Vista;

import Controlador.Controlador;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

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
    private Controlador controlador;

    /**
     *
     * @param stage
     * @param contenedorImagenes
     */
    public Controles(Stage stage, ContenedorImagenes contenedorImagenes) {
        super();
        /*ChoiceBox para escoger filtro*/
        selectorFiltro = new ComboBox(FXCollections.observableArrayList(
                "Tonos de grises por promedio",
                "Convertir a grises 1",
                "Tonos de grises por porcentaje",
                "Mosaico",
                "Red, Green or Blue",
                "Micas"
        ));
        selectorFiltro.setPromptText("Seleccionar filtro");
        /*Opciones de cada filtro*/
        opciones = new Pane();
        opciones.setPrefSize(500, 100);
        opciones.setStyle("-fx-background-color:blue;");
        /*Selector de archivo*/
        fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar Imagen");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.jpg", "*.png"));
        /*Abrir FileChooser*/
        cargarImagen = new Button("Cargar Imagen");
        cargarImagen.setOnAction(event -> {
            imagen = fileChooser.showOpenDialog(stage);
            if (imagen != null) {
                contenedorImagenes.setImagenOriginal(imagen);
                try {
                    BufferedImage original = ImageIO.read(imagen);
                    BufferedImage copia = original.getSubimage(0, 0, original.getWidth(), original.getHeight());                    
                    contenedorImagenes.setImagenProcesada(copia,imagen);
                } catch (Exception ioe) {
                    System.err.println(ioe.getMessage());
                }

            }
        });
        /*Empezar proceso*/
        procesar = new Button("Procesar");
        procesar.setOnAction(event -> {
            try {
                contenedorImagenes.setImagenProcesada(Controlador.aplicaFiltro(selectorFiltro.getValue().toString(), imagen),imagen);
            } catch (Exception ioe) {
                ioe.printStackTrace();
            }
        });
        /*Contenedores*/
        StackPane botonesDerecha = new StackPane(procesar);
        botonesDerecha.setPrefSize(225, 100);
        botonesDerecha.setAlignment(Pos.CENTER);
        VBox botonesIzquierda = new VBox(cargarImagen, selectorFiltro);
        botonesIzquierda.setPrefSize(225, 100);
        botonesIzquierda.setAlignment(Pos.CENTER);
        botonesIzquierda.setSpacing(10);
        /*Propiedades HBox controles*/
        super.getChildren().addAll(botonesIzquierda, opciones, botonesDerecha);
        super.setPrefSize(950, 100);
    }

}

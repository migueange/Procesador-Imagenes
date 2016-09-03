package Vista;

import Modelo.Filtros;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

    private ComboBox selectorFiltro, selectorColor;
    private final HBox opciones;
    private final Button procesar;
    private final Button cargarImagen;
    private final FileChooser fileChooser;
    private File imagen;
    private Slider sliderR, sliderG, sliderB;
    private TextField valorn, valorm;

    /**
     *
     * @param stage
     * @param contenedorImagenes
     */
    public Controles(Stage stage, ContenedorImagenes contenedorImagenes) {
        super();
        /*Opciones de cada filtro*/
        opciones = new HBox();
        opciones.setPrefSize(500, 100);
        opciones.setAlignment(Pos.CENTER);
        opciones.setSpacing(50);
        /*ChoiceBox para escoger filtro*/
        selectorColor = new ComboBox(FXCollections.observableArrayList("Red", "Green", "Blue"));
        selectorColor.setPromptText("Seleccionar color");
        selectorFiltro = new ComboBox(FXCollections.observableArrayList(
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
                "Brillo"
        ));
        selectorFiltro.setPromptText("Seleccionar filtro");
        selectorFiltro.setOnAction(event -> {
            if (!opciones.getChildren().isEmpty()) {
                opciones.getChildren().remove(0, 2);
            }
            switch (selectorFiltro.getValue().toString()) {
                case "Tonos de grises por color":
                    opciones.getChildren().addAll(new Label("Opciones: "), selectorColor);
                    break;
                case "Red, Green or Blue":
                    opciones.getChildren().addAll(new Label("Opciones: "), selectorColor);
                    break;
                case "Micas":
                    Label R = new Label("0  "),
                     G = new Label("0  "),
                     B = new Label("0  ");
                    sliderR = new Slider(0, 255, 0);
                    sliderR.setPrefWidth(250);
                    sliderR.valueProperty().addListener((ov, oldvalue, newvalue) -> {
                        R.setText(newvalue.intValue() + "");
                    });
                    sliderG = new Slider(0, 255, 0);
                    sliderG.setPrefWidth(250);
                    sliderG.valueProperty().addListener((ov, oldvalue, newvalue) -> {
                        G.setText(newvalue.intValue() + "");
                    });
                    sliderB = new Slider(0, 255, 0);
                    sliderB.setPrefWidth(250);
                    sliderB.valueProperty().addListener((ov, oldvalue, newvalue) -> {
                        B.setText(newvalue.intValue() + "");
                    });
                    VBox contenedorSliders = new VBox(new HBox(new Label("R: "), sliderR, R), new HBox(new Label("G: "), sliderG, G), new HBox(new Label("B: "), sliderB, B));
                    contenedorSliders.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedorSliders);
                    break;
                case "Mosaico":
                    restringeTextFields(valorn = new TextField("10"), valorm = new TextField("10"));
                    valorn.setPrefWidth(75);
                    valorm.setPrefWidth(75);
                    Label labeln = new Label("Valor de n:  "),
                     labelm = new Label("Valor de m: ");
                    VBox contenedorTextFields = new VBox(new HBox(labeln, valorn), new HBox(labelm, valorm));
                    contenedorTextFields.setAlignment(Pos.CENTER);
                    contenedorTextFields.setSpacing(5);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedorTextFields);
                    break;
                case "Brillo":
                    Label brilloL = new Label("0  ");
                    sliderB = new Slider(-255, 255, 0);
                    sliderB.setPrefWidth(250);
                    sliderB.valueProperty().addListener((ov, oldvalue, newvalue) -> {
                        brilloL.setText(newvalue.intValue() + "");
                    });                    
                    VBox contenedorSliderBrillo = new VBox(new HBox(new Label("Brillo: "),sliderB,brilloL));
                    contenedorSliderBrillo.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "),contenedorSliderBrillo);
                    break;
            }
        });
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
                    contenedorImagenes.setImagenProcesada(copia, imagen);
                } catch (Exception ioe) {
                    Mensajes.muestraError("Error al cargar la imagen", "Por favor intentelo de nuevo.");
                }
            }
        });
        /*Empezar proceso*/
        procesar = new Button("Procesar");
        procesar.setOnAction((ActionEvent event) -> {
            if (imagen == null) {
                Mensajes.muestraError("Por favor cargue una imagen", "Puede seleccionar cualquier imagen formato jpg\no png.");
                return;
            }
            if (selectorFiltro.getValue() == null) {
                Mensajes.muestraError("Por favor seleccione un filtro", "");
                return;
            }
            try {
                switch (selectorFiltro.getValue().toString()) {
                    case "Tonos de grises por promedio":
                        contenedorImagenes.setImagenProcesada(Filtros.tonosDeGrisesPorPromedio(imagen), imagen);
                        break;
                    case "Tonos de grises por color":
                        if (selectorColor.getValue() == null) {
                            Mensajes.muestraError("Por favor seleccione un color", "");
                            return;
                        }
                        contenedorImagenes.setImagenProcesada(Filtros.tonosDeGrisesPorColor(imagen, selectorColor.getValue().toString()), imagen);
                        break;
                    case "Tonos de grises por porcentaje":
                        contenedorImagenes.setImagenProcesada(Filtros.tonosDeGrisesPorPorcentaje(imagen), imagen);
                        break;
                    case "Mosaico":
                        BufferedImage temp = ImageIO.read(imagen);
                        int n = Integer.parseInt(valorn.getText());
                        int m = Integer.parseInt(valorm.getText());
                        if (n > temp.getHeight() || m > temp.getWidth()) {
                            Mensajes.muestraError("Error en los valores", "Los valores de n y m no deben exceder la altura\ny el ancho de la imagen respectivamente.");
                            return;
                        }
                        if (n <= 0 || m <= 0) {
                            Mensajes.muestraError("Error en los valores", "Los valores de n y m deben ser mayores que cero.");
                            return;
                        }
                        contenedorImagenes.setImagenProcesada(Filtros.mosaico(imagen, n, m), imagen);
                        break;
                    case "Red, Green or Blue":
                        if (selectorColor.getValue() == null) {
                            Mensajes.muestraError("Por favor seleccione un color", "");
                            return;
                        }
                        contenedorImagenes.setImagenProcesada(Filtros.colorDominante(imagen, selectorColor.getValue().toString()), imagen);
                        break;
                    case "Micas":
                        contenedorImagenes.setImagenProcesada(Filtros.micas(imagen, (int) sliderR.getValue(), (int) sliderG.getValue(), (int) sliderB.getValue()), imagen);
                        break;
                    case "Blur":
                        contenedorImagenes.setImagenProcesada(Filtros.blur(imagen), imagen);
                        break;
                    case "Motion Blur":
                        contenedorImagenes.setImagenProcesada(Filtros.motionBlur(imagen), imagen);
                        break;
                    case "Encontrar Bordes Verticales":
                        contenedorImagenes.setImagenProcesada(Filtros.encontrarBordesVerticales(imagen), imagen);
                        break;
                    case "Encontrar Bordes Horizontales":
                        contenedorImagenes.setImagenProcesada(Filtros.encontrarBordesHorizontales(imagen), imagen);
                        break;
                    case "Encontrar Bordes Diagonales":
                        contenedorImagenes.setImagenProcesada(Filtros.encontrarBordesDiagonales(imagen), imagen);
                        break;
                    case "Encontrar Bordes en todas las direcciones":
                        contenedorImagenes.setImagenProcesada(Filtros.encontrarBordesTodasDirecciones(imagen), imagen);
                        break;
                    case "Sharpen":
                        contenedorImagenes.setImagenProcesada(Filtros.sharpen(imagen), imagen);
                        break;
                    case "Emboss":
                        contenedorImagenes.setImagenProcesada(Filtros.emboss(imagen), imagen);
                        break;
                    case "Brillo":
                        contenedorImagenes.setImagenProcesada(Filtros.brillo(imagen,(int)sliderB.getValue()), imagen);
                        break;

                }

            } catch (IOException ioe) {
                Mensajes.muestraError("Hubo un error en el proceso", "Por favor, intentelo de nuevo");
            }
        }
        );
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

    /**
     * Restringe los textFields para que solo se puedan ingresar números.
     *
     * @param tf
     */
    private void restringeTextFields(TextField... tf) {
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

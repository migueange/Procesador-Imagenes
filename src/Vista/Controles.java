package Vista;

import Acciones.Acciones;
import Modelo.FiltrosColores;
import Modelo.FiltrosConvolucion;
import Modelo.FiltrosLetras;
import Modelo.ImagenesRecursivas;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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
    private final Button guardarImagen;
    private final FileChooser fileChooser;
    private File imagen;
    private Slider sliderR, sliderG, sliderB;
    private TextField valorn, valorm;
    private ProgressIndicator progressIndicator;
    private Task task;
    private StackPane contenedorOpciones;

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
        /*Progress indicator*/
        progressIndicator = new ProgressIndicator(0);
        progressIndicator.setVisible(false);
        /*Contenedor de porgreso y opciones*/
        contenedorOpciones = new StackPane(opciones, progressIndicator);
        contenedorOpciones.setAlignment(Pos.CENTER);
        /*ChoiceBox para escoger filtro*/
        selectorColor = new ComboBox(FXCollections.observableArrayList("Red", "Green", "Blue"));
        selectorColor.setPromptText("Seleccionar color");
        /*ChoiceBox seleccionar Filtro*/
        selectorFiltro = new ComboBox(Acciones.getNombresFiltros());
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
                     RL = new Label("R: "),
                     G = new Label("0  "),
                     GL = new Label("G: "),
                     B = new Label("0  "),
                     BL = new Label("B: ");
                    R.setStyle("-fx-text-fill: red;");
                    RL.setStyle("-fx-text-fill: red;");
                    G.setStyle("-fx-text-fill: green;");
                    GL.setStyle("-fx-text-fill: green;");
                    B.setStyle("-fx-text-fill: blue;");
                    BL.setStyle("-fx-text-fill: blue;");
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
                    VBox contenedorSliders = new VBox(new HBox(RL, sliderR, R), new HBox(GL, sliderG, G), new HBox(BL, sliderB, B));
                    contenedorSliders.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedorSliders);
                    break;
                case "Mosaico":
                    Acciones.restringeTextFields(valorn = new TextField("10"), valorm = new TextField("10"));
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
                    VBox contenedorSliderBrillo = new VBox(new HBox(new Label("Brillo: "), sliderB, brilloL));
                    contenedorSliderBrillo.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedorSliderBrillo);
                    break;
                case "Imagenes Recursivas Colores Reales":
                    Acciones.restringeTextFields(valorn = new TextField("10"));
                    valorn.setPrefWidth(75);
                    HBox contenedor1 = new HBox(new Label("Anchura de cada subimagen:  "), valorn);
                    contenedor1.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedor1);
                    break;
                case "Imagenes Recursivas Tonos de Grises":
                    Acciones.restringeTextFields(valorn = new TextField("10"));
                    valorn.setPrefWidth(75);
                    HBox contenedor2 = new HBox(new Label("Anchura de cada subimagen:  "), valorn);
                    contenedor2.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedor2);
                    break;
                case "Una sola letra (Color)":
                    Acciones.restringeTextFields(valorn = new TextField("10"));
                    valorm = new TextField("A");
                    valorn.setPrefWidth(75);
                    valorm.setPrefWidth(75);
                    VBox contenedorTextFields1 = new VBox(new HBox(new Label("Tamaño de Fuente:  "), valorn), new HBox(new Label("Letra: "), valorm));
                    contenedorTextFields1.setAlignment(Pos.CENTER);
                    contenedorTextFields1.setSpacing(5);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedorTextFields1);
                    break;
                case "Una sola letra (Tons de Gris)":
                    Acciones.restringeTextFields(valorn = new TextField("10"));
                    valorm = new TextField("A");
                    valorn.setPrefWidth(75);
                    valorm.setPrefWidth(75);
                    VBox contenedorTextFields2 = new VBox(new HBox(new Label("Tamaño de Fuente:  "), valorn), new HBox(new Label("Letra: "), valorm));
                    contenedorTextFields2.setAlignment(Pos.CENTER);
                    contenedorTextFields2.setSpacing(5);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedorTextFields2);
                    break;
                case "Letras en blanco y negro":
                    Acciones.restringeTextFields(valorn = new TextField("10"));
                    valorn.setPrefWidth(75);
                    HBox contenedor3 = new HBox(new Label("Tamaño de fuente:  "), valorn);
                    contenedor3.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedor3);
                    break;
                case "Colores con texto (Letrero)":
                    Acciones.restringeTextFields(valorn = new TextField("10"));
                    valorm = new TextField("A");
                    valorn.setPrefWidth(75);
                    valorm.setPrefWidth(75);
                    VBox contenedorTextFields3 = new VBox(new HBox(new Label("Tamaño de Fuente:  "), valorn), new HBox(new Label("Texto: "), valorm));
                    contenedorTextFields3.setAlignment(Pos.CENTER);
                    contenedorTextFields3.setSpacing(5);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedorTextFields3);
                    break;
                case "Fotos con fichas de dominó":
                    Acciones.restringeTextFields(valorn = new TextField("10"));
                    valorn.setPrefWidth(75);
                    HBox contenedor4 = new HBox(new Label("Tamaño de fuente:  "), valorn);
                    contenedor4.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedor4);
                    break;
                case "Fotos con Naipes":
                    Acciones.restringeTextFields(valorn = new TextField("10"));
                    valorn.setPrefWidth(75);
                    HBox contenedor5 = new HBox(new Label("Tamaño de fuente:  "), valorn);
                    contenedor5.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedor5);
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
        /*Guardar imagen*/
        guardarImagen = new Button("Guardar");
        guardarImagen.setDisable(true);
        guardarImagen.setOnAction(event -> {
            fileChooser.setInitialFileName("ejemplo." + imagen.getName().substring(imagen.getName().lastIndexOf(".") + 1));
            File archivoNuevo = fileChooser.showSaveDialog(stage);
            if (archivoNuevo != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(contenedorImagenes.procesadaReal, null), imagen.getName().substring(imagen.getName().lastIndexOf(".") + 1), archivoNuevo);
                    Mensajes.muestraConfirmacion(Alert.AlertType.INFORMATION, "Mensaje", "La magen fue guardada exitosamente", "");
                } catch (IOException ex) {
                    Mensajes.muestraError("Hubo un error al guardar la imagen.", "Por favor intentelo de nuevo.");
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
            int n, m;
            final BufferedImage temp, original, procesada;
            try {
                original = ImageIO.read(imagen);
                procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
                switch (selectorFiltro.getValue().toString()) {
                    case "Tonos de grises por promedio":
                        task = FiltrosColores.tonosDeGrisesPorPromedio(original, procesada);
                        break;
                    case "Tonos de grises por color":
                        if (selectorColor.getValue() == null) {
                            Mensajes.muestraError("Por favor seleccione un color", "");
                            return;
                        }
                        task = FiltrosColores.tonosDeGrisesPorColor(original, procesada, selectorColor.getValue().toString());
                        break;
                    case "Tonos de grises por porcentaje":
                        task = FiltrosColores.tonosDeGrisesPorPorcentaje(original, procesada);
                        break;
                    case "Mosaico":
                        temp = ImageIO.read(imagen);
                        n = Integer.parseInt(valorn.getText());
                        m = Integer.parseInt(valorm.getText());
                        if (n > temp.getHeight() || m > temp.getWidth()) {
                            Mensajes.muestraError("Error en los valores", "Los valores de n y m no deben exceder la altura\ny el ancho de la imagen respectivamente.");
                            return;
                        }
                        if (n <= 0 || m <= 0) {
                            Mensajes.muestraError("Error en los valores", "Los valores de n y m deben ser mayores que cero.");
                            return;
                        }
                        task = FiltrosColores.mosaico(original, procesada, n, m);
                        break;
                    case "Red, Green or Blue":
                        if (selectorColor.getValue() == null) {
                            Mensajes.muestraError("Por favor seleccione un color", "");
                            return;
                        }
                        task = FiltrosColores.colorDominante(original, procesada, selectorColor.getValue().toString());
                        break;
                    case "Micas":
                        task = FiltrosColores.micas(original, procesada, (int) sliderR.getValue(), (int) sliderG.getValue(), (int) sliderB.getValue());
                        break;
                    case "Blur":
                        task = FiltrosConvolucion.blur(original, procesada);
                        break;
                    case "Motion Blur":
                        task = FiltrosConvolucion.motionBlur(original, procesada);
                        break;
                    case "Encontrar Bordes Verticales":
                        task = FiltrosConvolucion.encontrarBordesVerticales(original, procesada);
                        break;
                    case "Encontrar Bordes Horizontales":
                        task = FiltrosConvolucion.encontrarBordesHorizontales(original, procesada);
                        break;
                    case "Encontrar Bordes Diagonales":
                        task = FiltrosConvolucion.encontrarBordesDiagonales(original, procesada);
                        break;
                    case "Encontrar Bordes en todas las direcciones":
                        task = FiltrosConvolucion.encontrarBordesTodasDirecciones(original, procesada);
                        break;
                    case "Sharpen":
                        task = FiltrosConvolucion.sharpen(original, procesada);
                        break;
                    case "Emboss":
                        task = FiltrosConvolucion.emboss(original, procesada);
                        break;
                    case "Brillo":
                        task = FiltrosConvolucion.brillo(original, procesada, (int) sliderB.getValue());
                        break;
                    case "Alto Contraste":
                        task = FiltrosConvolucion.altoContraste(original, procesada);
                        break;
                    case "Inverso":
                        task = FiltrosConvolucion.inverso(original, procesada);
                        break;
                    case "Imagenes Recursivas Colores Reales":
                        n = Integer.parseInt(valorn.getText());
                        m = new Double((original.getHeight() * n) / original.getWidth()).intValue();
                        if (n > original.getWidth() || m > original.getHeight()) {
                            Mensajes.muestraError("Error en los valores", "El valor de n no debe exceder la altura de la imagen.");
                            return;
                        }
                        if (n <= 0 || m <= 0) {
                            Mensajes.muestraError("Error en los valores", "El valor de n y la altura calculada\na partir de n debe ser mayor que cero.");
                            return;
                        }
                        task = ImagenesRecursivas.imagenesRecursivasColorReal(original, procesada, n, m);
                        break;
                    case "Imagenes Recursivas Tonos de Grises":
                        n = Integer.parseInt(valorn.getText());
                        m = new Double((original.getHeight() * n) / original.getWidth()).intValue();
                        if (n > original.getWidth() || m > original.getHeight()) {
                            Mensajes.muestraError("Error en los valores", "El valor de n no debe exceder la altura de la imagen.");
                            return;
                        }
                        if (n <= 0 || m <= 0) {
                            Mensajes.muestraError("Error en los valores", "El valor de n y la altura calculada\na partir de n debe ser mayor que cero.");
                            return;
                        }
                        task = ImagenesRecursivas.imagenesRecursivasTonosGris(original, procesada, n, m);
                        break;
                    case "Una sola letra (Color)":
                        m = n = Integer.parseInt(valorn.getText());
                        if (n > original.getWidth() || m > original.getHeight()) {
                            Mensajes.muestraError("Error en los valores", "El valor de n no debe exceder la altura de la imagen.");
                            return;
                        }
                        if (n <= 0 || m <= 0) {
                            Mensajes.muestraError("Error en los valores", "El valor de n y la altura calculada\na partir de n debe ser mayor que cero.");
                            return;
                        }
                        task = FiltrosLetras.unaLetraColores(original, procesada, valorm.getText().equals("") ? "A" : valorm.getText().charAt(0) + "", n, m);
                        break;
                    case "Una sola letra (Tons de Gris)":
                        m = n = Integer.parseInt(valorn.getText());
                        if (n > original.getWidth() || m > original.getHeight()) {
                            Mensajes.muestraError("Error en los valores", "El valor de n no debe exceder la altura de la imagen.");
                            return;
                        }
                        if (n <= 0 || m <= 0) {
                            Mensajes.muestraError("Error en los valores", "El valor de n y la altura calculada\na partir de n debe ser mayor que cero.");
                            return;
                        }
                        task = FiltrosLetras.unaLetraTonosGrises(original, procesada, valorm.getText().equals("") ? "A" : valorm.getText().charAt(0) + "", n, m);
                        break;
                    case "Letras en blanco y negro":
                        m = n = Integer.parseInt(valorn.getText());
                        if (n > original.getWidth() || m > original.getHeight()) {
                            Mensajes.muestraError("Error en los valores", "El valor de n no debe exceder la altura de la imagen.");
                            return;
                        }
                        if (n <= 0 || m <= 0) {
                            Mensajes.muestraError("Error en los valores", "El valor de n y la altura calculada\na partir de n debe ser mayor que cero.");
                            return;
                        }
                        task = FiltrosLetras.letrasBlancoYNegro(original, procesada, n, m);
                        break;
                    case "Colores con texto (Letrero)":
                        m = n = Integer.parseInt(valorn.getText());
                        if (n > original.getWidth() || m > original.getHeight()) {
                            Mensajes.muestraError("Error en los valores", "El valor de n no debe exceder la altura de la imagen.");
                            return;
                        }
                        if (n <= 0 || m <= 0) {
                            Mensajes.muestraError("Error en los valores", "El valor de n y la altura calculada\na partir de n debe ser mayor que cero.");
                            return;
                        }
                        task = FiltrosLetras.coloresConTexto(original, procesada, valorm.getText().equals("") ? "ejemplo" : valorm.getText(), n, m);
                        break;
                    case "Fotos con fichas de dominó":
                        /*Cargar la tipografía de dominó*/
                        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("Vista/resources/fonts/WhiteDominoes.ttf")));
                        m = n = Integer.parseInt(valorn.getText()) < 5 ? 5 : Integer.parseInt(valorn.getText());
                        if (n > original.getWidth() || m > original.getHeight()) {
                            Mensajes.muestraError("Error en los valores", "El valor de n no debe exceder la altura de la imagen.");
                            return;
                        }
                        if (n <= 0 || m <= 0) {
                            Mensajes.muestraError("Error en los valores", "El valor de n y la altura calculada\na partir de n debe ser mayor que cero.");
                            return;
                        }
                        task = FiltrosLetras.letrasDomino(original, procesada, n, m / 2);
                        break;
                    case "Fotos con Naipes":
                        /*Cargar la tipografía de dominó*/
                        GraphicsEnvironment gen = GraphicsEnvironment.getLocalGraphicsEnvironment();
                        gen.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("Vista/resources/fonts/CARDS.TTF")));
                        m = n = Integer.parseInt(valorn.getText()) < 5 ? 5 : Integer.parseInt(valorn.getText());
                        if (n > original.getWidth() || m > original.getHeight()) {
                            Mensajes.muestraError("Error en los valores", "El valor de n no debe exceder la altura de la imagen.");
                            return;
                        }
                        if (n <= 0 || m <= 0) {
                            Mensajes.muestraError("Error en los valores", "El valor de n y la altura calculada\na partir de n debe ser mayor que cero.");
                            return;
                        }
                        task = FiltrosLetras.letrasNaipes(original, procesada, n, m);
                        break;
                }
                Acciones.comienzaProceso(progressIndicator, opciones, guardarImagen, procesar, task, contenedorImagenes, procesada, imagen);
            } catch (IOException | FontFormatException ioe) {
                Mensajes.muestraError("Hubo un error en el proceso", "Por favor, intentelo de nuevo");
                ioe.printStackTrace();
            }
        });
        /*Contenedores*/
        VBox botonesDerecha = new VBox(procesar, guardarImagen);
        botonesDerecha.setPrefSize(225, 100);
        botonesDerecha.setAlignment(Pos.CENTER);
        botonesDerecha.setSpacing(10);
        VBox botonesIzquierda = new VBox(cargarImagen, selectorFiltro);
        botonesIzquierda.setPrefSize(225, 100);
        botonesIzquierda.setAlignment(Pos.CENTER);
        botonesIzquierda.setSpacing(10);
        /*Propiedades HBox controles*/
        super.getChildren().addAll(botonesIzquierda, contenedorOpciones, botonesDerecha);
        super.setPrefSize(950, 100);
    }

}

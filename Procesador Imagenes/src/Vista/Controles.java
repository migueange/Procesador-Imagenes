package Vista;

import Modelo.Filtros;
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
                "Brillo",
                "Alto Contraste",
                "Inverso",
                "Imagenes Recursivas Colores Reales",
                "Imagenes Recursivas Tonos de Grises"
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
                    VBox contenedorSliderBrillo = new VBox(new HBox(new Label("Brillo: "), sliderB, brilloL));
                    contenedorSliderBrillo.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedorSliderBrillo);
                    break;
                case "Imagenes Recursivas Colores Reales":
                    restringeTextFields(valorn = new TextField("10"));
                    valorn.setPrefWidth(75);
                    HBox contenedor1 = new HBox(new Label("Anchura de cada subimagen:  "), valorn);
                    contenedor1.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedor1);
                    break;
                case "Imagenes Recursivas Tonos de Grises":
                    restringeTextFields(valorn = new TextField("10"));
                    valorn.setPrefWidth(75);
                    HBox contenedor2 = new HBox(new Label("Anchura de cada subimagen:  "), valorn);
                    contenedor2.setAlignment(Pos.CENTER);
                    opciones.getChildren().addAll(new Label("Opciones: "), contenedor2);
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
            fileChooser.setInitialFileName("ejemplo."+ imagen.getName().substring(imagen.getName().lastIndexOf(".") + 1));
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
            progressIndicator.setVisible(true);
            opciones.setVisible(false);    
            guardarImagen.setDisable(false);
            procesar.setDisable(true);
            try {
                original = ImageIO.read(imagen);
                System.out.println(original.getWidth()+"x"+original.getHeight());
                procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
                System.out.println(procesada.getWidth()+"x"+procesada.getHeight());
                switch (selectorFiltro.getValue().toString()) {
                    case "Tonos de grises por promedio":
                        task = Filtros.tonosDeGrisesPorPromedio(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);                            
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Tonos de grises por color":
                        if (selectorColor.getValue() == null) {
                            Mensajes.muestraError("Por favor seleccione un color", "");
                            return;
                        }
                        task = Filtros.tonosDeGrisesPorColor(original, procesada, selectorColor.getValue().toString());
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Tonos de grises por porcentaje":
                        task = Filtros.tonosDeGrisesPorPorcentaje(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
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
                        task = Filtros.mosaico(original, procesada, n, m);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Red, Green or Blue":
                        if (selectorColor.getValue() == null) {
                            Mensajes.muestraError("Por favor seleccione un color", "");
                            return;
                        }
                        task = Filtros.colorDominante(original, procesada, selectorColor.getValue().toString());
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Micas":
                        task = Filtros.micas(original, procesada, (int) sliderR.getValue(), (int) sliderG.getValue(), (int) sliderB.getValue());
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Blur":
                        task = Filtros.blur(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        task.setOnFailed(e -> {
                            System.out.println("Error");
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Motion Blur":
                        task = Filtros.motionBlur(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Encontrar Bordes Verticales":
                        task = Filtros.encontrarBordesVerticales(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Encontrar Bordes Horizontales":
                        task = Filtros.encontrarBordesHorizontales(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Encontrar Bordes Diagonales":
                        task = Filtros.encontrarBordesDiagonales(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Encontrar Bordes en todas las direcciones":
                        task = Filtros.encontrarBordesTodasDirecciones(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Sharpen":
                        task = Filtros.sharpen(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Emboss":
                        task = Filtros.emboss(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Brillo":
                        task = Filtros.brillo(original, procesada, (int) sliderB.getValue());
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Alto Contraste":
                        task = Filtros.altoContraste(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
                    case "Inverso":
                        task = Filtros.inverso(original, procesada);
                        task.setOnSucceeded(e -> {
                            progressIndicator.setVisible(false);
                            opciones.setVisible(true);
                            try {
                                contenedorImagenes.setImagenProcesada(procesada, imagen);
                            } catch (IOException ex) {
                                Mensajes.muestraError("Hubo un error en el proceso", "Por favor vuelva a intentarlo.");
                            }
                        });
                        progressIndicator.progressProperty().unbind();
                        progressIndicator.progressProperty().bind(task.progressProperty());
                        new Thread(task).start();
                        return;
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
                        task = Filtros.imagenesRecursivasColorReal(original, procesada, n, m);
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
                        return;
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
                        task = Filtros.imagenesRecursivasTonosGris(original, procesada, n, m);
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
                        return;
                }                
            } catch (IOException ioe) {
                Mensajes.muestraError("Hubo un error en el proceso", "Por favor, intentelo de nuevo");
            }
            
        });
        /*Contenedores*/
        VBox botonesDerecha = new VBox(procesar,guardarImagen);
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

package Vista;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.imageio.ImageIO;

/**
 * Contiene las imagenes, original y procesada
 *
 * @author miguel
 */
public class ContenedorImagenes extends HBox {

    private final StackPane izq, der;
    public Image original, procesada, procesadaReal;

    /**
     *
     */
    public ContenedorImagenes() {
        super();
        /*Contenedor imagen original*/
        izq = new StackPane();
        izq.setPrefSize(475, 475);
        /*contenedor imagen procesada*/
        der = new StackPane();
        der.setPrefSize(475, 475);
        super.setPrefSize(950, 475);
        super.getChildren().addAll(izq, der);
        super.setSpacing(2);
        super.setStyle("-fx-background-color: #DCDCDC;");
    }

    /**
     * Muestra la imagen original en la interfaz.
     *
     * @param img
     */
    public void setImagenOriginal(File img) {
        if (!izq.getChildren().isEmpty()) {
            izq.getChildren().remove(0);
        }
        izq.setAlignment(Pos.CENTER);
        Image aux = new Image("file:///" + img.getAbsolutePath().replace("\\", "/"));
        double[] medidas = calculaMedidasImagen(aux.getWidth(), aux.getHeight());
        izq.getChildren().add(new ImageView(original = new Image("file:///" + img.getAbsolutePath().replace("\\", "/"), medidas[0], medidas[1], false, false)));
        apareceNodo(izq, 1000);
    }

    /**
     * Muestra la imagen en la interfaz después de aplicarle algún filtro o
     * proceso a la imagen original.
     *
     * @param is
     * @param img
     * @throws java.io.IOException
     */
    public void setImagenProcesada(BufferedImage is, File img) throws IOException {
        if (!der.getChildren().isEmpty()) {
            der.getChildren().remove(0);
        }
        der.setAlignment(Pos.CENTER);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(is, img.getName().substring(img.getName().lastIndexOf(".") + 1), os);
        double[] medidas = calculaMedidasImagen(is.getWidth(), is.getHeight());
        der.getChildren().add(new ImageView(procesada = new Image(new ByteArrayInputStream(os.toByteArray()), medidas[0], medidas[1], false, false)));
        apareceNodo(der, 1000);
        procesadaReal = new Image(new ByteArrayInputStream(os.toByteArray()), is.getWidth(), is.getHeight(), false, false);
    }


    /**
     * Aparece un nodo en cierto tiempo.
     *
     * @param node El nodo que se va a aparecer.
     * @param duracion La duración del efecto en milisegundos.
     */
    private void apareceNodo(Node node, int duracion) {
        FadeTransition ft = new FadeTransition(Duration.millis(duracion), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    /*Calcula las medidas para mostrar las imágenes en la interfaz*/
    private double[] calculaMedidasImagen(double originalWidth, double originalHeight) {
        if (originalWidth > originalHeight) {
            return new double[]{475, (originalHeight * ((100 * 475) / originalWidth)) / 100};
        }
        if (originalHeight == originalWidth) {
            return new double[]{475, 475};
        }
        return new double[]{(originalWidth * ((100 * 475) / originalHeight)) / 100, 475};
    }

}

package Vista;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javax.imageio.ImageIO;

/**
 * Contiene las imagenes, original y procesada
 * @author miguel
 */
public class ContenedorImagenes extends HBox {

    private final StackPane izq, der;
    private Image original, procesada;

    /**
     *
     */
    public ContenedorImagenes() {
        super();
        /*Contenedor imagen original*/
        izq = new StackPane();
        izq.setPrefSize(475, 400);
        /*contenedor imagen procesada*/
        der = new StackPane();
        der.setPrefSize(475, 400);
        super.setPrefSize(950, 400);
        super.getChildren().addAll(izq, der);
        super.setStyle("-fx-background-color: #DCDCDC;");
    }

    /**
     * 
     * @param img
     */
    public void setImagenOriginal(File img) {
        if (!izq.getChildren().isEmpty()) {
            izq.getChildren().remove(0);
        }
        izq.setAlignment(Pos.CENTER);
        Image aux = new Image("file:///" + img.getAbsolutePath().replace("\\", "/"));
        if (aux.getWidth() > aux.getHeight()) {
            original = new Image("file:///" + img.getAbsolutePath().replace("\\", "/"), 475, (aux.getHeight() * ((100 * 475) / aux.getWidth())) / 100, false, false);
        } else if (aux.getHeight() == aux.getWidth()) {
            original = new Image("file:///" + img.getAbsolutePath().replace("\\", "/"), 380, 380, false, false);
        } else {
            original = new Image("file:///" + img.getAbsolutePath().replace("\\", "/"), (aux.getWidth() * ((100 * 380) / aux.getHeight())) / 100, 380, false, false);
        }
        izq.getChildren().add(new ImageView(original));
    }

    /**
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
        Image aux = SwingFXUtils.toFXImage(is, null);
        if (aux.getWidth() > aux.getHeight()) {
            procesada = new Image(new ByteArrayInputStream(os.toByteArray()), 475,  (aux.getHeight() * ((100 * 475) / aux.getWidth())) / 100, false, false);
        } else if (aux.getHeight() == aux.getWidth()) {
            procesada = new Image(new ByteArrayInputStream(os.toByteArray()), 380, 380, false, false);
        } else {
            procesada = new Image(new ByteArrayInputStream(os.toByteArray()), (aux.getWidth() * ((100 * 380) / aux.getHeight())) / 100, 380, false, false);
        }
        der.getChildren().add(new ImageView(procesada));
    }

}

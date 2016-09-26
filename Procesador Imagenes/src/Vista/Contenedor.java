package Vista;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author miguelita
 */
public class Contenedor extends VBox{
    
    private final Titulos titulos;
    private final ContenedorImagenes contenedorImagenes;
    private final Controles controles;
    
    /**
     * 
     * @param stage
     */
    public Contenedor(Stage stage){
        super();
        titulos = new Titulos();
        contenedorImagenes = new ContenedorImagenes();
        controles = new Controles(stage,contenedorImagenes);
        super.getChildren().addAll(titulos,contenedorImagenes,controles);
        super.setPrefSize(950, 625);
    }
    
}

package Vista;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author miguelita
 */
public class Titulos extends HBox{
    
    private Label tituloIzq;
    private Label tituloDer;
    
    public Titulos(){
        tituloIzq = new Label("Imagen original"); 
        tituloDer = new Label("Imagen procesada");
        super.getChildren().addAll(tituloIzq,tituloDer);
        super.setPrefSize(950, 50);
        super.setAlignment(Pos.CENTER);        
        super.setSpacing(300);
    }
}

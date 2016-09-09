/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procesador.imagenes;

import Vista.Contenedor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author miguelita
 */
public class ProcesadorImagenes extends Application {
    
    @Override
    public void start(Stage primaryStage) {               
        Scene scene = new Scene(new Contenedor(primaryStage), 950, 500);        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Procesador Imágenes");
        primaryStage.setScene(scene);
        primaryStage.show();       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

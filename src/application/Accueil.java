package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author antoine.gouzy
 */
public class Accueil extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("FXMLAccueil.fxml"));
        primaryStage.setTitle("Editeur de graphes (Graphio)");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    

    public static void main(String[] args) {
        launch(args);
    }
}
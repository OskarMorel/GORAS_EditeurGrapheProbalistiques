package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author antoine.gouzy
 */
public class Accueil extends Application {
    
    public static Stage mainStage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("FXMLAccueil.fxml"));
        primaryStage.setTitle("Editeur de graphes (Graphio)");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/img/line-chart.png"));
        primaryStage.show();
        mainStage = primaryStage;        
    }
    
    

    public static void main(String[] args) {
        launch(args);
    }
}
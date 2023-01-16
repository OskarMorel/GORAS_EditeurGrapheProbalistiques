/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * Accueil.java                           16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 
 * Lancement de l'editeur
 * 
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
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
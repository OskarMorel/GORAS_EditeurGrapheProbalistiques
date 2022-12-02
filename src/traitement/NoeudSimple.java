package traitement;

import application.AccueilController;
import static application.AccueilController.cible;
import static application.AccueilController.source;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class NoeudSimple extends Noeud {
    
    public static int cpt = 0;
    
    public NoeudSimple(double coordX, double coordY, AnchorPane zoneDessin) {
        super(Integer.toString(cpt+=1), coordX, coordY);
        dessinerNoeud(zoneDessin,this);
    }
    
    private void dessinerNoeud(AnchorPane zoneDessin, NoeudSimple noeud) {

        /* cercle */
        Circle cercle = new Circle(noeud.getX(), noeud.getY(), AccueilController.getRadius());
        cercle.setFill(Color.TRANSPARENT);
        cercle.setStroke(Color.BLACK);
        cercle.setOnMouseDragged((new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent evt) {
                System.out.println(".handle()");
            }
        }));
        
        cercle.setOnMouseClicked((new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent evt) {
                if (source == null) {
                    source = noeud;
                } else {
                    cible = noeud;
                }
            }
        }));

        /* label */
        Label libelle = new Label(this.getLibelle());
        libelle.setLayoutX(noeud.getX());
        libelle.setLayoutY(noeud.getY());
        
        /* Groupe cercle + label */
        Group groupe = new Group();
        groupe.getChildren().addAll(cercle, libelle);
        //groupe.setOnMouseDragged();
        
        zoneDessin.getChildren().addAll(groupe);
    }
}
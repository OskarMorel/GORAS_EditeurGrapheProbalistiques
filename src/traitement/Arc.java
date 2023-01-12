package traitement;

import application.AccueilController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 *
 * @author antoine.gouzy
 */
public class Arc extends Lien {
    
    public Arc() {
        
    }

    public Arc(Noeud source, Noeud cible) {
        super(source, cible);
    }
    
    @Override
    public Group dessinerLien(AnchorPane zoneDessin) {
        
        double l = Math.sqrt( Math.pow(getSource().getCoordX()- getCible().getCoordX(), 2) + Math.pow(getSource().getCoordY()- getCible().getCoordY(), 2));
        
        double xSource;
        double ySource;

        double xCible;
        double yCible;
        
        double xDirDroite;
        double yDirDroite;

        double xNorDroite;
        double yNorDroite;
        
        //Milieu du lien
        double xMilieuLien;
        double yMilieuLien;
        
        //Point de controle de la courbe
        double xControle;
        double yControle;

        //Si on dessine une boucle
        if (getSource().getCoordX()- Noeud.getRadius() <= getCible().getCoordX() && getCible().getCoordX() <= getSource().getCoordX() + Noeud.getRadius() 
            && getSource().getCoordY() - Noeud.getRadius() <= getCible().getCoordY() && getCible().getCoordY() <= getSource().getCoordY() + Noeud.getRadius() ) {
            
            xSource = getSource().getCoordX() - Noeud.getRadius() + 1;
            xCible = getSource().getCoordX() + Noeud.getRadius() - 1;
            ySource = getSource().getCoordY() - Noeud.getRadius() + 20 ;
            yCible = getSource().getCoordY() + Noeud.getRadius() - 40;
            
            xDirDroite = xCible - xSource;
            yDirDroite = yCible - ySource;
            
            xNorDroite = -yDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
            yNorDroite = xDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
        
            xMilieuLien = (xCible + xSource)/2;
            yMilieuLien = (yCible + ySource)/2;
            
            xControle = xNorDroite * -100 + xMilieuLien;
            yControle = yNorDroite * -100 + yMilieuLien;
            
        } else { //Si lien classique
            
            xSource = getSource().getCoordX() + (getCible().getCoordX() - getSource().getCoordX()) / l * Noeud.getRadius();
            ySource = getSource().getCoordY() + (getCible().getCoordY() - getSource().getCoordY()) / l * Noeud.getRadius();

            xCible = getCible().getCoordX() + (getSource().getCoordX() - getCible().getCoordX()) / l * Noeud.getRadius();
            yCible  = getCible().getCoordY() + (getSource().getCoordY() - getCible().getCoordY()) / l * Noeud.getRadius();
            
            xDirDroite = xCible-xSource;
            yDirDroite = yCible-ySource;
            
            xNorDroite = -yDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
            yNorDroite = xDirDroite * (1 / Math.sqrt(Math.pow(yDirDroite, 2) + Math.pow(xDirDroite, 2)));
            
            xMilieuLien = (xCible + xSource)/2;
            yMilieuLien = (yCible + ySource)/2;
            
            
            xControle = xNorDroite * 60 + xMilieuLien;
            yControle = yNorDroite * 60 + yMilieuLien;
        }
        
        QuadCurve ligne = new QuadCurve(xSource, ySource, xControle, yControle, xCible, yCible);
        ligne.setFill(Color.TRANSPARENT);
        ligne.setStroke(Color.BLACK);

        /* Creation de la fleche */
        double angleRad = Math.PI / 6; // Angle de la fleche
        
        double distance = 20; //Longueur d'une branche de la fleche
        double nDist = distance / Math.sqrt(Math.pow(xSource - xCible, 2) + Math.pow(ySource - yCible, 2));
        
        double xflecheH = xCible + nDist * ((xControle - xCible) * Math.cos(angleRad) - (yControle - yCible) * Math.sin(angleRad));
        double yflecheH = yCible + nDist * ((xControle - xCible) * Math.sin(angleRad) + (yControle - yCible) * Math.cos(angleRad));
        double xflecheB = xCible + nDist * ((xControle - xCible) * Math.cos(-angleRad) - (yControle - yCible) * Math.sin(-angleRad));
        double yflecheB = yCible + nDist * ((xControle - xCible) * Math.sin(-angleRad) + (yControle - yCible) * Math.cos(-angleRad));
        
        Line flecheHaut = new Line(xCible, yCible, xflecheH, yflecheH);
        Line flecheBas = new Line(xCible, yCible, xflecheB, yflecheB);
                        
        Group groupe = new Group();
        groupe.getChildren().addAll(ligne, flecheBas, flecheHaut);
        
        //Action s'il on clique sur l'arc
        groupe.setOnMousePressed((new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent evt) {
                AccueilController.estLien = true;
                AccueilController.lienEnCoursGroup = groupe;
                AccueilController.noeudSource = getSource();
                AccueilController.noeudCible = getCible();
            }
        }));
        
        zoneDessin.getChildren().addAll(groupe);

        return groupe;
    }

    /**
     * Actualise les propriétés de l'arc en fonction des paramètres des combobox
     * @param noeudsSource ComboBox contenant tous les noeuds du graphe
     * @param noeudsCible ComboBox contenant tous les noeuds du graphe
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin du graphe
     * @param groupe
     */
    @Override
    public void setPropriete(ComboBox noeudsSource, ComboBox noeudsCible, Graphe graphe, AnchorPane zoneDessin, Group groupe) {
        
        String libelleNoeudSource = (String) noeudsSource.getValue();
        String libelleNoeudCible = (String) noeudsCible.getValue();
        Noeud noeudSource = null; //Init pour le compilateur
        Noeud noeudCible = null;
        
        //Recuperation des noeuds source et cible en fonction des libelles
        for (Noeud noeud : graphe.getNoeuds()) {
            
            if (noeud.getLibelle().equals(libelleNoeudSource)) {
                noeudSource = noeud;                
            }
            if (noeud.getLibelle().equals(libelleNoeudCible)) {
                noeudCible = noeud;               
            }
        }
        
        //Verification que l'arc n'existe pas deja
        if (!graphe.estLienDuGraphe(noeudSource, noeudCible)) {
            
            //Modification des sources et cibles de l'instance
            setSource(noeudSource);
            setCible(noeudCible);
            
            supprimer(zoneDessin, groupe);
            //Dessin du nouveau lien
            dessinerLien(zoneDessin);
            
        } else {            
            //ComboBox aux parametres par defauts
            noeudsSource.setValue(getSource().getLibelle());
            noeudsCible.setValue(getCible().getLibelle());
        }
    }
    
}

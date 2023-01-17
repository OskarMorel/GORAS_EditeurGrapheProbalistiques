/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * NoeudSimple.java                       16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package graphe;

import static application.Accueil.mainStage;
import application.AccueilController;
import beans.NoeudSimpleBean;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * 
 * Gestion d'un noeud simpl
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class NoeudSimple extends Noeud {
        
    public NoeudSimple() {
    }
    
    /**
     * Creer un noeud simple
     * @param coordX coordonnee en abscisse de ce noeud
     * @param coordY coordonnee en ordonnee de ce noeud
     */
    public NoeudSimple(double coordX, double coordY) {
        super(Integer.toString(cpt+=1), coordX, coordY);
        id = cpt;
    }
    
    /**
     * Creer un noeud simple
     * @param coordX coordonnee en abscisse de ce noeud
     * @param coordY coordonnee en ordonnee de ce noeud
     * @param id id du noeud
     * @param libelle libelle du noeud
     */
    public NoeudSimple(double coordX, double coordY, int id, String libelle) {
        super(libelle, coordX, coordY);
        this.id = id;
    }
    
    @Override
    public Group dessinerNoeud(AnchorPane zoneDessin) {
        
        /* Cercle extérieur */
        Circle cercleExterieur = new Circle(getCoordX(), getCoordY(), Noeud.getRadius() * 2.5);
        cercleExterieur.setFill(Color.TRANSPARENT);
        cercleExterieur.setStroke(Color.TRANSPARENT);
        
        /* cercle */
        Circle cercle = new Circle(getCoordX(), getCoordY(), Noeud.getRadius());
        cercle.setFill(Color.TRANSPARENT);  
        cercle.setStroke(Color.BLACK);


        /* label */
        Label libelle = new Label(this.getLibelle());
        libelle.setLayoutX(this.getCoordX() - 3);
        libelle.setLayoutY(this.getCoordY() - 8);

        /* Groupe cercle + label */
        Group groupe = new Group();
        groupe.getChildren().addAll(cercle, libelle, cercleExterieur);

        groupe.setOnMousePressed((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {
                AccueilController.isDrawable = false;
                AccueilController.noeudEnCoursGroup = groupe;
            }
        }));
        zoneDessin.getChildren().addAll(groupe);
        return groupe;
    }

    @Override
    public void selectionGroupe(AnchorPane main, Group groupe, Graphe graphe, AnchorPane zoneDessin) {
        groupe.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent evt) {

                // Text field pour changer libelle du noeud sélectionné
                TextField libelleModif = new TextField();
                libelleModif.setLayoutX(60);
                libelleModif.setLayoutY(50);
                
                // récupération du nom du noeud
                Label getterLabelNom = (Label) groupe.getChildren().get(1);
                libelleModif.setText(getterLabelNom.getText());
                String nomBase = getterLabelNom.getText();
                
                // label pour la modification du nom du noeud
                Label labelLibelleModif = new Label();
                labelLibelleModif.setText("Nom :");
                labelLibelleModif.setLayoutX(26);
                labelLibelleModif.setLayoutY(53);
                
                // Pour changer les coord X du noeud
                TextField coordX = new TextField();
                coordX.setLayoutX(60);
                coordX.setLayoutY(100);
                
                // Label du changement coord X du noeud
                Label labelCoordX = new Label();
                labelCoordX.setText("coordX :");
                labelCoordX.setLayoutX(15);
                labelCoordX.setLayoutY(103);
                
                // pour changer les coord Y du noeud
                TextField coordY = new TextField();
                coordY.setLayoutX(60);
                coordY.setLayoutY(150);
                
                // label pour changemennt des coord Y du noeud
                Label labelCoordY = new Label();
                labelCoordY.setText("coordY :");
                labelCoordY.setLayoutX(15);
                labelCoordY.setLayoutY(153);
                
                // récupération coord X et Y du noeud
                Circle getterCoordonnees = (Circle) groupe.getChildren().get(0);
                coordX.setText(Double.toString(getterCoordonnees.getCenterX()));
                coordY.setText(Double.toString(getterCoordonnees.getCenterY()));
                Double coordXBase = Double.parseDouble(coordX.getText());
                Double coordYBase = Double.parseDouble(coordY.getText());
                
                // Bouton de validation du nouveau nom
                Button validationModif = new Button();
                validationModif.setText("Valider");
                validationModif.setLayoutX(60);
                validationModif.setLayoutY(203);
                
                // Bouton de supression d'un noeud
                Button suppression = new Button();
                suppression.setText("Supprimer");
                suppression.setLayoutX(120);
                suppression.setLayoutY(203);
                
                validationModif.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent evt) {
                        
                        String nouveauNom = libelleModif.getText();
                        double nouvelleCoordX;
                        double nouvelleCoordY;
                        
                        nouvelleCoordX = Double.parseDouble(coordX.getText());
                        nouvelleCoordY = Double.parseDouble(coordY.getText());

                        
                        boolean positionOk = nouvelleCoordX > Noeud.getRadius() && nouvelleCoordY > Noeud.getRadius();
                         
                        
                        // gestion d'erreur de collision après modification des coordonnées de X et Y
                        boolean noeudMemePosition = false;
                        
                        for (int i = 0; i < graphe.getNoeuds().size(); i++) {
                            if(graphe.getNoeuds().get(i).getId() != AccueilController.noeudASelectionner.getId()){
                                if (Math.sqrt(Math.pow((graphe.getNoeuds().get(i).getCoordX()-nouvelleCoordX), 2)+Math.pow((graphe.getNoeuds().get(i).getCoordY()-nouvelleCoordY), 2)) < 70) {
                                    noeudMemePosition = true;
                                } 
                            }
                        }
                        // Si erreur alors on remets la coordonnées avant le changement
                        if (noeudMemePosition || !positionOk) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Erreur coordonnées");
                            alert.setHeaderText("Coordonnées trop proche d'un autre noeud ou invalide");
                            alert.initOwner(mainStage);
                            alert.showAndWait();
                            nouvelleCoordX = coordXBase;
                            coordX.setText(Double.toString(getterCoordonnees.getCenterX()));
                            nouvelleCoordY = coordYBase;
                            coordY.setText(Double.toString(getterCoordonnees.getCenterY()));
                        } 
                        
                        // Gestion des noms en double
                        boolean memeNomNoeud = false;
                        for (int i = 0; i < graphe.getNoeuds().size(); i++) {
                            if (nouveauNom.equals(graphe.getNoeuds().get(i).getLibelle())) {
                                memeNomNoeud = true;
                            } 
                            if (nomBase.equals(nouveauNom)) {
                                memeNomNoeud = false;
                            }
                        }
                        // si le nom existe déjà alors on remets l'ancien nom d'avant la modification
                        if (memeNomNoeud) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Erreur nom");
                            alert.setHeaderText("Nom déjà existant sur un autre noeud");
                            alert.initOwner(mainStage);
                            alert.showAndWait();
                            nouveauNom = nomBase;
                        }
                        
                        if (positionOk && !memeNomNoeud && !noeudMemePosition) {
                            /* Cercle extérieur */
                            Circle nvCercleExterieur = new Circle(nouvelleCoordX, nouvelleCoordY, Noeud.getRadius() * 2.5);
                            nvCercleExterieur.setFill(Color.TRANSPARENT);
                            nvCercleExterieur.setStroke(Color.TRANSPARENT);

                            /* cercle */
                            Circle nvCercle = new Circle(nouvelleCoordX, nouvelleCoordY, Noeud.getRadius());
                            nvCercle.setFill(Color.TRANSPARENT);  
                            nvCercle.setStroke(Color.BLACK);

                            /* label */    
                            Label nvLibelle = new Label(nouveauNom);
                            nvLibelle.setLayoutX(nouvelleCoordX - 3);
                            nvLibelle.setLayoutY(nouvelleCoordY - 8); 

                            groupe.getChildren().clear();
                            groupe.getChildren().addAll(nvCercle, nvLibelle, nvCercleExterieur);

                            AccueilController.noeudASelectionner.setLibelle(nouveauNom);
                            AccueilController.noeudASelectionner.setCoordX(nouvelleCoordX);
                            AccueilController.noeudASelectionner.setCoordY(nouvelleCoordY);

                            graphe.modifLienNoeud(AccueilController.noeudASelectionner);

                            // On efface tout le dessin pour le redessiner avec les nouvelles modifications
                            zoneDessin.getChildren().clear();
                            
                            // On redessine les noeuds et les liens
                            for (Noeud noeud : graphe.getNoeuds()) {
                                noeud.dessinerNoeud(zoneDessin);
                            }
                            for (Lien lien : graphe.getLiens()) {
                                lien.dessinerLien(zoneDessin);
                            }
                        }
                        main.getChildren().clear();
                    }
                });
                
                suppression.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent evt) {
                        //Suppression de ce que contient le groupe 
                        groupe.getChildren().clear();
                        //Suppression du groupe sur la zone de dessin
                        zoneDessin.getChildren().remove(groupe);
                        graphe.supprimerNoeud(AccueilController.noeudASelectionner, zoneDessin);
                        
                        main.getChildren().clear();
                    }    
                });
                
                
                main.getChildren().addAll(libelleModif, labelLibelleModif, coordX, coordY, labelCoordX, labelCoordY, validationModif, suppression);
            }
        }));
    }

    @Override
    public NoeudSimpleBean toNoeudBean() {
        return new NoeudSimpleBean(libelle, coordX, coordY, id);
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        String noeud = libelle + " X : " + coordX + " Y : " + coordY + " id : " + id;
        return noeud;
    }
}

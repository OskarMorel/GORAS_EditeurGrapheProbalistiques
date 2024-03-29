/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * Lien.java                              16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package graphe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Classe permettant la definition de lien pour un graphe
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public abstract class Lien {

    /** Source du lien*/ 
    Noeud source;

    /** Cible du lien*/
    Noeud cible;
    
    public Lien () { }
    
    /**
     * Creer une instance de Lien
     * @param source noeud source de l'arete
     * @param cible noeud cible de l'arete
     */
    public Lien(Noeud source, Noeud cible) {
        this.source = source;
        this.cible = cible;
        //groupe = new Group();
    }
    
    /** @return la source du lien */
    public Noeud getSource() {
        return source;
    }

    /** @return la cible du lien */
    public Noeud getCible() {
        return cible;
    }
    
    /**
     * @param nouvelleSource le noeud qui est la nouvelle source du lien
     */
    public void setSource(Noeud nouvelleSource) {
        source = nouvelleSource;
    }
    
    /**
     * Modifie la cible du lien
     * @param nouvelleCible le noeud qui est la nouvelle cible du lien
     */
    public void setCible(Noeud nouvelleCible) {
        cible = nouvelleCible;
    }
    
    /**
     * Supprime le lien de la zone de dessin
     * @param zoneDessin la zone de dessin du graphe
     * @param groupe groupe graphique du lien
     */
    public void supprimer(AnchorPane zoneDessin, Group groupe) {
        
        //Suppression de ce que contient le groupe 
        groupe.getChildren().clear();
        //Suppression du groupe sur la zone de dessin
        zoneDessin.getChildren().remove(groupe);
    }
    
    /**
     * Dessine un lien sur la fenetre graphique de l'editeur
     * @param zoneDessin zone de dessin de l'editeur
     * @return le groupe graphique du lien
     */
    public Group dessinerLien(AnchorPane zoneDessin) {
        return null;
    }

    /**
     * Affiche sur la zone de propriété les zones de saisie des propriétés d'un lien
     * @param zonePropriete zone ou les propriete s'afficher sur l'interface graphique
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin du graphe
     * @param groupe dessin du lien
     */
    public void proprieteLien(AnchorPane zonePropriete, Graphe graphe, AnchorPane zoneDessin, Group groupe) {
        
        //Gestion propriete source
        Label labelSource = new Label("Source : ");
        labelSource.setLayoutX(10);
        labelSource.setLayoutY(53);
        
        ComboBox noeudsSource = new ComboBox();
        noeudsSource.setLayoutX(120);
        noeudsSource.setLayoutY(50);
        for (Noeud noeud : graphe.getNoeuds()) {
            
            if (noeud == this.source) { // Si le noeud actuel est la source du lien
                noeudsSource.getItems().add(noeud.getLibelle());
                noeudsSource.setValue(noeud.getLibelle()); //Selected ComboBox
            } else { // Ajout des autres noeuds
                noeudsSource.getItems().add(noeud.getLibelle());
            }  
        }
        zonePropriete.getChildren().addAll(labelSource, noeudsSource);

        //Gestion propriete cible
        Label labelCible = new Label("Cible : ");
        labelCible.setLayoutX(10);
        labelCible.setLayoutY(103);
        
        ComboBox noeudsCible = new ComboBox();
        noeudsCible.setLayoutX(120);
        noeudsCible.setLayoutY(100);
        for (Noeud noeud : graphe.getNoeuds()) {
            
            if (noeud == this.cible) { // Si le noeud actuel est la cible du lien
                noeudsCible.getItems().add(noeud.getLibelle());
                noeudsCible.setValue(noeud.getLibelle()); //Selected ComboBox
            } else { // Ajout des autres noeuds
                noeudsCible.getItems().add(noeud.getLibelle());
            }  
        }
        zonePropriete.getChildren().addAll(labelCible, noeudsCible);
        
        
        // Bouton de validation
        Button validationModif = new Button("Valider");
        validationModif.setLayoutX(60);
        validationModif.setLayoutY(203);
        zonePropriete.getChildren().addAll(validationModif);
        
        // Si validation des changements
        validationModif.setOnAction(new EventHandler<ActionEvent>() {
            
            //double nouvellePonderation = Double.parseDouble(ponderation.getText());

            @Override
            public void handle(ActionEvent evt) {
                setPropriete(noeudsSource, noeudsCible, graphe, zoneDessin, groupe);
                zonePropriete.getChildren().clear();
            }

            
        });
        
        // Bouton de suppression de l'arc
        Button supprimerLien = new Button("Supprimer");
        supprimerLien.setLayoutX(120);
        supprimerLien.setLayoutY(203);
        zonePropriete.getChildren().addAll(supprimerLien);
        
        
        // Si validation de la suppression de l'arc
        supprimerLien.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent evt) {
                supprimer(zoneDessin, groupe);
                graphe.supprimerLien(noeudsSource, noeudsCible);
                zonePropriete.getChildren().clear();
            }
        });
   
    }
    /**
     * Actualise les propriétés de l'arc en fonction des paramètres des combobox
     * @param noeudsSource ComboBox contenant en valeur active le libelle du noeud source
     * @param noeudsCible ComboBox contenant en valeur active le libelle du noeud cible
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin de l'editeur
     * @param groupe groupe graphique de lien selectione
     */
    public void setPropriete(ComboBox noeudsSource, ComboBox noeudsCible, Graphe graphe, AnchorPane zoneDessin, Group groupe) { }
    
    /**
     * Actualise les propriétés de l'arc en fonction des paramètres des combobox et de la ponderation
     * @param noeudsSource ComboBox contenant en valeur active le libelle du noeud source
     * @param noeudsCible ComboBox contenant en valeur active le libelle du noeud cible
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin de l'editeur
     * @param groupe groupe graphique de lien selectione
     * @param ponderation la nouvelle ponderation du lien
     */
    public void setPropriete(ComboBox noeudsSource, ComboBox noeudsCible, Graphe graphe, AnchorPane zoneDessin, Group groupe, double ponderation) { }
    
    @Override
    public String toString() {
        String lien = "Source :" + source + " Cible: " + cible;
        return lien;
    }
}

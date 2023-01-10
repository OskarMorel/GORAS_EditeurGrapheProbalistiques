package traitement;

import application.AccueilController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 *
 * @author Gouzy
 */
class ArcProbabiliste extends Lien{
    

    private double ponderation = 0.0;
    
    public ArcProbabiliste() {
        
    }
    
    public ArcProbabiliste(Noeud source, Noeud cible) {
        super(source, cible);
    }

   
    public void setPonderation(double nouvellePonderation) {
       ponderation = nouvellePonderation;
    }
    
    public double getPonderation() {
        return ponderation;
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
        
        double distance = 20;

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
            
            distance = 6;
            
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
        
        //Arc
        QuadCurve ligne = new QuadCurve(xSource, ySource, xControle, yControle, xCible, yCible);
        ligne.setFill(Color.TRANSPARENT);
        ligne.setStroke(Color.BLACK);
        
        //Container de la ponderation
        Label labelPonderation = new Label(Double.toString(ponderation));
        labelPonderation.setLayoutX(xControle);
        labelPonderation.setLayoutY(yControle);

        /* Creation de la fleche */
        double angleRad = Math.PI / 6; // Angle de la fleche
        
        double nDist = distance / Math.sqrt(Math.pow(xSource - xCible, 2) + Math.pow(ySource - yCible, 2));
        
        double xflecheH = xCible + nDist * ((xControle - xCible) * Math.cos(angleRad) - (yControle - yCible) * Math.sin(angleRad));
        double yflecheH = yCible + nDist * ((xControle - xCible) * Math.sin(angleRad) + (yControle - yCible) * Math.cos(angleRad));
        double xflecheB = xCible + nDist * ((xControle - xCible) * Math.cos(-angleRad) - (yControle - yCible) * Math.sin(-angleRad));
        double yflecheB = yCible + nDist * ((xControle - xCible) * Math.sin(-angleRad) + (yControle - yCible) * Math.cos(-angleRad));
        
        Line flecheHaut = new Line(xCible, yCible, xflecheH, yflecheH);
        Line flecheBas = new Line(xCible, yCible, xflecheB, yflecheB);
                
        
        getGroupe().getChildren().clear();        
        getGroupe().getChildren().addAll(ligne, flecheBas, flecheHaut, labelPonderation);
        
        //Action s'il on clique sur l'arc
        getGroupe().setOnMousePressed((new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent evt) {
                AccueilController.estLien = true;
                AccueilController.lienEnCoursGroup = getGroupe();
                AccueilController.noeudSource = getSource();
                AccueilController.noeudCible = getCible();
            }
        }));
        
        zoneDessin.getChildren().addAll(getGroupe());
        
        return getGroupe();
    }

    /**
     * Actualise les propriétés de l'arc en fonction des paramètres des combobox
     * @param noeudsSource ComboBox contenant tous les noeuds du graphe
     * @param noeudsCible ComboBox contenant tous les noeuds du graphe
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin du graphe
     */
    @Override
    public void setPropriete(ComboBox noeudsSource, ComboBox noeudsCible, Graphe graphe, AnchorPane zoneDessin, Group groupe, double nouvellePonderation) {
        
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
            
            System.err.println("Tu passe par là if");
            
            supprimer(zoneDessin, groupe);
            //Dessin du nouveau lien
            dessinerLien(zoneDessin);
            
        } else {            
            
            //ComboBox aux parametres par defauts
            noeudsSource.setValue(getSource().getLibelle());
            noeudsCible.setValue(getCible().getLibelle());
            
            ponderation = nouvellePonderation;
            
            supprimer(zoneDessin, groupe);
            //Dessin du nouveau lien
            dessinerLien(zoneDessin);
           
        }
    }
    
    /**
     * Affiche sur la zone de propriété les zones de saisie des propriétés d'un lien
     * @param zonePropriete zone ou les propriete s'afficher sur l'interface graphique
     * @param graphe graphe en cours de traitement
     * @param zoneDessin zone de dessin du graphe
     * @param groupe dessin du lien
     */
    @Override
    public void proprieteLien(AnchorPane zonePropriete, Graphe graphe, AnchorPane zoneDessin, Group groupe) {
        
        //Gestion propriete source
        Label labelSource = new Label("Source : ");
        labelSource.setLayoutX(10);
        labelSource.setLayoutY(53);
        
        ComboBox noeudsSource = new ComboBox();
        noeudsSource.setLayoutX(120);
        noeudsSource.setLayoutY(50);
        for (Noeud noeud : graphe.getNoeuds()) {
            
            if (noeud == getSource()) { // Si le noeud actuel est la source du lien
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
            
            if (noeud == getCible()) { // Si le noeud actuel est la cible du lien
                noeudsCible.getItems().add(noeud.getLibelle());
                noeudsCible.setValue(noeud.getLibelle()); //Selected ComboBox
            } else { // Ajout des autres noeuds
                noeudsCible.getItems().add(noeud.getLibelle());
            }  
        }
        zonePropriete.getChildren().addAll(labelCible, noeudsCible);
       
        // Titre de TextField du changement de pondération de l'arc
        Label labelPonderation = new Label();
        labelPonderation.setText("Pondération : ");
        labelPonderation.setLayoutX(10);
        labelPonderation.setLayoutY(153);
        
        // récupération de la pondération de l'arc
        Label getterPonderation = (Label) groupe.getChildren().get(3);
        
        // Pour changer la pondération de l'arc
        TextField ponderationText = new TextField();
        ponderationText.setLayoutX(90);
        ponderationText.setLayoutY(150);
        ponderationText.setText(getterPonderation.getText());
        
        zonePropriete.getChildren().addAll(labelPonderation, ponderationText);
        
        // Bouton de validation
        Button validationModif = new Button("Valider");
        validationModif.setLayoutX(60);
        validationModif.setLayoutY(203);
        zonePropriete.getChildren().addAll(validationModif);
        
        // Si validation des changements
        validationModif.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent evt) {
                double anciennePonde = ponderation;
                Noeud ancienneSource = getSource();
                Noeud ancienneCible = getCible();
                
                double nouvellePonderation = Double.parseDouble(ponderationText.getText());
                
                
                setPropriete(noeudsSource, noeudsCible, graphe, zoneDessin, groupe, nouvellePonderation);
                GrapheProbabiliste grapheProba = (GrapheProbabiliste) graphe;
                boolean ponderationOk = grapheProba.getPondeNoeud(getSource());
                
                if (!ponderationOk){
                    
                    supprimer(zoneDessin, getGroupe());
                    //Dessin du nouveau lien
                    dessinerLien(zoneDessin);
                    noeudsSource.setValue(ancienneSource.getLibelle());
                    noeudsCible.setValue(ancienneCible.getLibelle());

                    setPropriete(noeudsSource, noeudsCible, graphe, zoneDessin, getGroupe(), anciennePonde);
                    
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur Pondération");
                    alert.setHeaderText("Pondération totale supérieur à 1");
                    alert.showAndWait();
                    ponderationText.setText(Double.toString(ponderation));
                    
                }else{
                    //setPropriete(noeudsSource, noeudsCible, graphe, zoneDessin, groupe, nouvellePonderation);
                    zonePropriete.getChildren().clear();
                }
            } 
        });
        
        // Bouton de suppression de l'arc
        Button supprimerLien = new Button("Supprimer");
        supprimerLien.setLayoutX(60);
        supprimerLien.setLayoutY(233);
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
    
}
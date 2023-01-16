/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * AccueilController.java                 16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package application;


import exceptions.TypeGrapheFactoryException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

import traitement.FactoryGraphe;
import traitement.FactoryGrapheProbabiliste;
import traitement.FactoryManager;
import traitement.Graphe;
import traitement.Noeud;
import traitement.Lien;
import traitement.NoeudProbabiliste;
import traitement.TraitementProbabiliste;
import static application.Accueil.mainStage;

/**
 * 
 * Gestion des éléments de l'interface graphique
 * 
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */

public class AccueilController implements Initializable {
    
    public static FactoryManager factoryManager = new FactoryManager();
    
    public static FactoryGraphe factory;
    
    /* Instance du graphe en cours de traitement */
    public static Graphe graphe;
    
    public static TraitementProbabiliste traitement;
    
    /* Pour le dessin d'un lien */
    public static Noeud noeudCible;
    public static Noeud noeudSource;
    
    /* 
     * Boolean permettant la transmission d'information entre AccueilController
     * et les actions des elements de l'interface graphique
     */
    public static boolean isDrawable = true;
    public static boolean estLien = false;
    
    /* Pour le dessin d'un lien ou d'un noeud ainsi que pour sa modification */
    private Lien lienEnCours;
    public static Group lienEnCoursGroup;
    public static Noeud noeudASelectionner;
    public static Group noeudEnCoursGroup;
    
    /* Chemin du graphe courant dans l'explorateur de fichier */
    private static String filePath;
    
    //Elements de l'interface graphique
    @FXML
    private ToggleButton selectionBtn;
    @FXML
    private ToggleButton noeudBtn;
    @FXML
    private ToggleButton lienBtn;
    @FXML
    private AnchorPane zoneDessin;
    @FXML
    private Button annulerBtn;
    @FXML
    private Button probaBtn;
    @FXML
    private ComboBox typesGraphe;
    @FXML
    private ComboBox noeud1Txt;
    @FXML
    private ComboBox noeud2Txt;
    @FXML
    private TextField nomGraphe;
    @FXML
    private TextField transitionTxt;
    @FXML
    private AnchorPane modificationContainer;
    @FXML
    private Label labelNomGraphe;
    @FXML
    private Label labelTypeGraphe;
    @FXML
    private Label noeud1Label;
    @FXML
    private Label noeud2Label;
    @FXML
    private Label transitionLabel;
    @FXML
    private Text reponseTxt;
    @FXML
    private Menu traitementMenu;
    
    static Menu menuTraitement;
    static AnchorPane zoneDessinStatic;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            
            ToggleGroup group = new ToggleGroup();
            noeudBtn.setToggleGroup(group);
            selectionBtn.setToggleGroup(group);
            lienBtn.setToggleGroup(group);        
            
            menuTraitement = traitementMenu;
            zoneDessinStatic = zoneDessin;
            
        } catch (NullPointerException e) {
            /* Si nouvelle fenetre diff de accueil*/
        }
        
        try{ //Si fenetre de creation de graphe
            typesGraphe.getItems().addAll(factoryManager.getFactories().keySet());
        } catch (Exception e) {
            //TODO
        }
        
        try { //Si fenetre de traitement sommet a sommet en n transition(s)
            for (Noeud noeud : graphe.getNoeuds()) {
                noeud1Txt.getItems().add(noeud.getLibelle());
                noeud2Txt.getItems().add(noeud.getLibelle());
            } 
        } catch (NullPointerException e) {
            
        }
        
    }  

    @FXML
    private void dessin(javafx.scene.input.MouseEvent evt) {
        
        try {
            if (selectionBtn.isSelected()) { //Cas si on selectionne l'option selections
                
                try {
                    noeudASelectionner = graphe.estNoeudGraphe(evt.getX(), evt.getY());
                    noeudASelectionner.selectionGroupe(modificationContainer, noeudEnCoursGroup, graphe, zoneDessin);
                    //noeudASelectionner = null;
                } catch (NullPointerException e) {
                    modificationContainer.getChildren().clear();
                }
                
                try {
                    /* Recuperation du lien selectionner */
                    lienEnCours = graphe.getLienDuGraphe(noeudSource, noeudCible);
                    lienEnCours.proprieteLien(modificationContainer, graphe, zoneDessin, lienEnCoursGroup);
                    /* Reinitialisation des valeurs */
                    lienEnCours = null;
                    lienEnCoursGroup = null;
                    noeudSource = null;
                    noeudCible = null;
                } catch (NullPointerException e) {
                    
                }

            } else if(noeudBtn.isSelected()) { //Cas si on selectione l'option noeud
                
                if (isDrawable == true) {
                    
                    double x = evt.getX();
                    double y = evt.getY();
                    
                    if(x < Noeud.getRadius()) {
                        x = Noeud.getRadius();
                    }
                    if(y < Noeud.getRadius()) {
                        y = Noeud.getRadius();
                    } 

                    Noeud noeud = factory.creerNoeud(x, y);
                    graphe.ajouterNoeud(noeud);
                    noeud.dessinerNoeud(zoneDessin);
                }
                isDrawable = true;
            }
        } catch (Exception  e) {
            //TODO indique l'erreur
        }
        
    }
    
    /*
     * Ouverture de la fenetre de creation de graphe
     */
    @FXML
    private void afficheNouveauGraphe() throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLNouveauGraphe.fxml"));
        Stage nouveauGrapheStage = new Stage();
        nouveauGrapheStage.initModality(Modality.APPLICATION_MODAL);
        nouveauGrapheStage.setTitle("Nouveau graphe");
        nouveauGrapheStage.getIcons().add(new Image("/img/line-chart.png"));
        nouveauGrapheStage.setScene(new Scene(root));  
        nouveauGrapheStage.show();
        
        zoneDessin.getChildren().clear();
        modificationContainer.getChildren().clear();
    }
    
    /*
     * Affiche le menu d'aide de manipulation des graphes
     */
    @FXML
    private void afficheAideManipGraphe() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAideManipGraphe.fxml"));
        Stage afficheAideManipGraphe = new Stage();
        afficheAideManipGraphe.initModality(Modality.APPLICATION_MODAL);
        afficheAideManipGraphe.setTitle("Aide Manipulation d'un Graphe");
        afficheAideManipGraphe.setScene(new Scene(root));  
        afficheAideManipGraphe.show();
    }
    
    /*
     * Affiche le menu d'aide de creation des graphes
     */
    @FXML
    private void aficheAideCreaGraphe() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAideCreaGraphe.fxml"));
        Stage aficheAideCreaGraphe = new Stage();
        aficheAideCreaGraphe.initModality(Modality.APPLICATION_MODAL);
        aficheAideCreaGraphe.setTitle("Aide Création d'un Graphe");
        aficheAideCreaGraphe.setScene(new Scene(root));  
        aficheAideCreaGraphe.show();
    }
    
    /*
     * Affiche le menu d'aide sur les menus de l'application
     */
    @FXML
    private void aficheAideMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAideMenus.fxml"));
        Stage aficheAideCreaGraphe = new Stage();
        aficheAideCreaGraphe.initModality(Modality.APPLICATION_MODAL);
        aficheAideCreaGraphe.setTitle("Aide navigation dans les menus");
        aficheAideCreaGraphe.setScene(new Scene(root));  
        aficheAideCreaGraphe.show();
    }
    
    /*
     * Fermeture de la fenetre courante
     */
    @FXML
    private void fermeFenetre() {
        Stage stage = (Stage) annulerBtn.getScene().getWindow();
        stage.close();
    }
    /* 
     * Verification des infos saisi
     * Creation d'un objet graphe correspondant au type selectionne   
     */
    @FXML
    private void confirmeNouveauGraphe() {
        
        String type = (String) typesGraphe.getValue();
        String nom = nomGraphe.getText();
        Noeud.cpt = 0;
        filePath = null;
        
        if (!nom.trim().isEmpty() && type != null) {
            fermeFenetre();
            try {
                factory = factoryManager.getInstance().getFactoryGraphe(type);
                graphe = factory.creerGraphe(nom);
                
                
                /* Affihage des traitements pour les graphes probabilistes */
                if (factory instanceof FactoryGrapheProbabiliste) {

                    menuTraitement.getItems().clear();

                    traitement = new TraitementProbabiliste(graphe);
                    
                    MenuItem matrice = new MenuItem("Matrice de transition");
                    MenuItem coloration = new MenuItem("Coloration du graphe");
                    MenuItem sommetASommet = new MenuItem("Probabilité sommet a sommet en n transition(s)"); 
                    MenuItem loiProbTransition = new MenuItem("Loi de probabilité atteinte après n transition(s)");
                    menuTraitement.getItems().addAll(matrice, coloration, sommetASommet, loiProbTransition);

                    matrice.setOnAction((ActionEvent e) -> {
                        if (graphe.estGrapheProbabiliste()) {
                            traitement.affichageMatrice();
                        }
                    });
                    
                    coloration.setOnAction((ActionEvent e) -> {
                        ///if (graphe.estGrapheProbabiliste()) {
                            traitement.regroupementParClasse(zoneDessinStatic);
                        //}
                    });
                    
                    sommetASommet.setOnAction((ActionEvent e) -> {
                        if (graphe.estGrapheProbabiliste()) {
                            
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("FXMLProbaSommetSommetNTransition.fxml"));
                                Stage sommetSommetStage = new Stage();
                                sommetSommetStage.initModality(Modality.APPLICATION_MODAL);
                                sommetSommetStage.setTitle("Probabilité sommet à sommet en n transition(s)");
                                sommetSommetStage.getIcons().add(new Image("/img/line-chart.png"));
                                sommetSommetStage.setScene(new Scene(root));  
                                sommetSommetStage.show();
                                
                            } catch (IOException ex) {
                                Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    
                    loiProbTransition.setOnAction((ActionEvent e) -> {
                        if (graphe.estGrapheProbabiliste()) {
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("FXMLLoiDeProbaNTransition.fxml"));
                                Stage loiProba = new Stage();
                                loiProba.initModality(Modality.APPLICATION_MODAL);
                                loiProba.setTitle("Loi de probabilité après n transition(s)");
                                loiProba.setScene(new Scene(root));  
                                loiProba.show();
                                
                            } catch (IOException ex) {
                                Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
                
            } catch (TypeGrapheFactoryException e) {
                System.err.println("Erreur creation du graphe type imposssible");
            }      
        } else {
            labelNomGraphe.setTextFill(Color.RED);
            labelTypeGraphe.setTextFill(Color.RED);
        }
        
    }
    
    /*
     * Affiche la visualisation du lien lors de son dessin initial
     */
    @FXML
    private void zoneDessinMouseDragged(MouseEvent evt) {
        
        if (lienBtn.isSelected()) {
            
            int compteurNoeud = Noeud.cpt;

            double x = evt.getX();
            double y = evt.getY();            

            if (graphe.estNoeudGraphe(x, y) != null && lienEnCours == null) {
                noeudSource = graphe.estNoeudGraphe(x, y);
                lienEnCours = factory.creerLien(noeudSource, noeudSource);
                lienEnCoursGroup = lienEnCours.dessinerLien(zoneDessin);

            } else if (noeudSource != null && lienEnCours != null) {
                zoneDessin.getChildren().remove(lienEnCoursGroup);
                Noeud noeudProvisoire = factory.creerNoeud(evt.getX(), evt.getY());
                lienEnCours = factory.creerLien(noeudSource, noeudProvisoire);
                
                lienEnCoursGroup = lienEnCours.dessinerLien(zoneDessin);
                Noeud.cpt = compteurNoeud;
            }

        }
    }
    
    /*
     * Affiche visuelement le lien une fois la souris relacher
     * et les deux sommets corrects
     */
    @FXML
    private void zoneDessinMouseReleased(MouseEvent evt){
        
        if (lienBtn.isSelected() && lienEnCours != null || lienBtn.isSelected() && lienEnCours != null) {
            Noeud noeudCible = graphe.estNoeudGraphe(evt.getX(), evt.getY());          

            if (noeudCible != null && !graphe.estLienDuGraphe(noeudSource, noeudCible)) {
                try{
                    lienEnCours = factory.creerLien(noeudSource, noeudCible);                    
                    graphe.ajouterLien(lienEnCours);                   
                    lienEnCours.dessinerLien(zoneDessin);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }

            zoneDessin.getChildren().remove(lienEnCoursGroup);
            lienEnCours = null;
            lienEnCoursGroup = null;
            noeudSource = null;
        }
    }
    
    /*
     * Enregistre le graphe courant avec un chemin et un nom spécifié
     */
    @FXML
    private void enregistrerSous() {
        
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer sous");

            //Set le repertoire par defaut
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            //Set le ou les extensions possibles
            fileChooser.getExtensionFilters().add(new ExtensionFilter("XML files", "*.xml"));
            //Set le nom par defaut du graphe
            fileChooser.setInitialFileName(graphe.getLibelle());

            File file = fileChooser.showSaveDialog(mainStage);

            //Recuperation du nom du fichier puis modification du libelle du graphe
            String nom = file.getName().substring(0, file.getName().length()-4);
            graphe.setLibelle(nom);
            
            filePath =  file.getParent();
            
            // Sérialisation XML d'un graphe dans fichier file
            XMLEncoder output = new XMLEncoder(new FileOutputStream(file));
            output.writeObject(graphe);
            output.writeObject(factory);
            output.close();
            
        } catch (NullPointerException e) {
            //Enregistrement sans graphe
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    /*
     * Enregistre le graphe courant dans son emplacement par défaut 
     * s'il n'existe pas on applique la methode enregistreSous
     */
    @FXML
    private void enregistrer() {
        if (filePath != null) {
            
            try {
                // Sérialisation XML d'un graphe dans fichier file
                XMLEncoder output = new XMLEncoder(new FileOutputStream(filePath));
                output.writeObject(graphe);
                output.writeObject(factory);
                output.close();
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
            
        } else {
            enregistrerSous();
        }
    }
    
    /*
     * Ouvre un graphe a partir de son chemin
     */
    @FXML
    private void ouvrir() {
        try {
            
            //Reinitialisation de la zone de dessin et des parametes de dessin
            zoneDessin.getChildren().clear();
            graphe = null;
            Noeud.cpt = 0;
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Ouvrir un graphe");

            // Set le repertoire par defaut
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            //Set le ou les extensions possibles
            fileChooser.getExtensionFilters().add(new ExtensionFilter("XML files", "*.xml"));
        
            File file = fileChooser.showOpenDialog(mainStage);
            filePath =  file.getPath();

            // Déserialisation
            XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));
            graphe = (Graphe) decoder.readObject();
            factory = (FactoryGraphe) decoder.readObject();

            //Dessin du graphe choisi
            int idMax = 0;
            for (Noeud noeud : graphe.getNoeuds()) {
                noeud.dessinerNoeud(zoneDessin);
                idMax = Integer.max(idMax, noeud.getId());
            }
            for (Lien lien : graphe.getLiens()) {
                lien.dessinerLien(zoneDessin);
            }
            
            Noeud.cpt = idMax;
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @FXML
    private void afficheSommetASommetNTransition() throws Exception {
        
        //Recuperation des noeuds source et cible en fonction des libelles
        NoeudProbabiliste noeud1 = null;
        NoeudProbabiliste noeud2 = null;
        
        int nbT = 0;
        boolean noeud1Ok = false;
        boolean noeud2Ok = false;
        
        for (Noeud noeud : graphe.getNoeuds()) {
            
            if (noeud.getLibelle().equals(noeud1Txt.getValue())) {
                noeud1 = (NoeudProbabiliste) noeud;
                noeud1Label.setTextFill(Color.BLACK);
                noeud1Ok = true;
            }
            if (noeud.getLibelle().equals(noeud2Txt.getValue())) {
                noeud2 = (NoeudProbabiliste) noeud;
                noeud2Label.setTextFill(Color.BLACK);
                noeud2Ok = true;
            }
        }
        
        if (!noeud1Ok) noeud1Label.setTextFill(Color.RED);
        if (!noeud2Ok) noeud2Label.setTextFill(Color.RED);
        
        if (transitionTxt.getText().matches("\\d+") && Integer.parseInt(transitionTxt.getText()) > 0) {
            nbT = Integer.parseInt(transitionTxt.getText());
            transitionLabel.setTextFill(Color.BLACK);
        } else {
            transitionLabel.setTextFill(Color.RED);
        }
        
        if (nbT != 0 && noeud1Ok && noeud2Ok) {
            double coeff = traitement.sommetASommetNTransition(noeud1, noeud2, nbT);
        reponseTxt.setText("Probabilité de passer du sommet " + noeud1Txt.getValue() 
                           + " au sommet " + noeud2Txt.getValue() + " en " 
                           + nbT + " transition(s) : " + coeff);
        }       
    }
    
    /*
     * 
     */
    @FXML
    private void afficheLoiProbaApresTransition() throws Exception {
        
        int exposant = 0;
        if (transitionTxt.getText().matches("\\d+") && Integer.parseInt(transitionTxt.getText()) > 0) {
            exposant = Integer.parseInt(transitionTxt.getText());
            transitionLabel.setTextFill(Color.BLACK);
        } else {
            transitionLabel.setTextFill(Color.RED);
        }
        
        if (exposant != 0) {
            double[] matrice = traitement.loiDeProbabiliteEnNTransitions(exposant);
            reponseTxt.setText("Loi de probabilité après n transitions : " + matrice);
        }     
    }
}    
package application;

import exceptions.TypeGrapheFactoryException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;


import traitement.FactoryGraphe;
import traitement.FactoryManager;
import traitement.Graphe;
import traitement.Noeud;
import traitement.NoeudSimple;
import traitement.Arc;
import traitement.Arete;

import static traitement.NoeudSimple.dessinerNoeud;

import traitement.FactoryGrapheSimpleNonOriente;
import traitement.FactoryGrapheSimpleOriente;

/**
 *
 * @author GORAS
 */
public class AccueilController implements Initializable {
    
    public static FactoryManager factoryManager = new FactoryManager();
    
    public static FactoryGraphe factory;
    
    public static Graphe graphe;
    
    private static final double RADIUS = 30.0;
    
    /* pour le dessin d'un lien */
    public static Noeud noeudCible;
    public static Noeud noeudSource;
    
    public static boolean isDrawable = true;
    public static boolean estLien = false;
    
    private Arete areteEnCours;
    private Group areteEnCoursGroupe;
    private Arc arcEnCours;
    private Group arcEnCoursGroupe;
    
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
    private ComboBox typesGraphe;
    @FXML
    private TextField nomGraphe;
    @FXML
    private AnchorPane modificationContainer;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            ToggleGroup group = new ToggleGroup();
            noeudBtn.setToggleGroup(group);
            selectionBtn.setToggleGroup(group);
            lienBtn.setToggleGroup(group); 
        } catch (NullPointerException e) {
            /* Si nouvelle fenetre diff de accueil*/
        }
        
        try{
            typesGraphe.getItems().addAll(factoryManager.getFactories().keySet());
        } catch (Exception e) {
            //TODO
        }
        
    }  

    @FXML
    private void dessin(javafx.scene.input.MouseEvent evt) {        
        try {
            if (selectionBtn.isSelected()) { //Cas si on selectione l'option selection
                
                if (factory instanceof FactoryGrapheSimpleNonOriente && estLien) {
                    /* Recuperation du lien selectionner */
                    areteEnCours = (Arete) graphe.getAreteDuGraphe(noeudCible, noeudSource);
                    areteEnCours.proprieteLien(modificationContainer, graphe, zoneDessin);
                    
                    
                    /* Reinitialisation des valeurs */
                    areteEnCours = null;
                    noeudCible = null;
                    noeudCible = null;
                }
                
            } else if(noeudBtn.isSelected()) { //Cas si on selectione l'option noeud
                
                if (isDrawable == true) {
                    Noeud  noeud = factory.creerNoeud(evt.getX(), evt.getY());
                    graphe.ajouterNoeud(noeud);
                    dessinerNoeud(zoneDessin, noeud);
                }
                isDrawable = true;
            }
        } catch (Exception  e) {
            //TODO indique l'erreur
        }
        
    }
    
    /* 
     * Création d'un nouveau graphe 
     * Ouverture de la fenetre de selection de type
     */
    @FXML
    private void nouveauGraphe() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLNouveauGraphe.fxml"));
        Stage nouveauGrapheStage = new Stage();
        nouveauGrapheStage.initModality(Modality.APPLICATION_MODAL);
        nouveauGrapheStage.setTitle("Nouveau graphe");
        nouveauGrapheStage.setScene(new Scene(root));  
        nouveauGrapheStage.show();
    }
    
    @FXML
    private void afficheAideManipGraphe() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAideManipGraphe.fxml"));
        Stage afficheAideManipGraphe = new Stage();
        afficheAideManipGraphe.initModality(Modality.APPLICATION_MODAL);
        afficheAideManipGraphe.setTitle("Aide Manipulation d'un Graphe");
        afficheAideManipGraphe.setScene(new Scene(root));  
        afficheAideManipGraphe.show();
    }
    
    @FXML
    private void aficheAideCreaGraphe() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAideCreaGraphe.fxml"));
        Stage aficheAideCreaGraphe = new Stage();
        aficheAideCreaGraphe.initModality(Modality.APPLICATION_MODAL);
        aficheAideCreaGraphe.setTitle("Aide Création d'un Graphe");
        aficheAideCreaGraphe.setScene(new Scene(root));  
        aficheAideCreaGraphe.show();
    }
    
    @FXML
    private void aficheAideMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAideMenus.fxml"));
        Stage aficheAideCreaGraphe = new Stage();
        aficheAideCreaGraphe.initModality(Modality.APPLICATION_MODAL);
        aficheAideCreaGraphe.setTitle("Aide navigation dans les menus");
        aficheAideCreaGraphe.setScene(new Scene(root));  
        aficheAideCreaGraphe.show();
    }
    
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
        fermeFenetre();
        
        try {
            
            factory = factoryManager.getInstance().getFactoryGraphe(type);
            graphe = factory.creerGraphe(nom);
            System.out.println("Creation du nouveau graphe : " + nom);           
        } catch (TypeGrapheFactoryException e) {
        
            
        }
        //TODO Empecher validation si nom vide ou type vide
    }
    
    
    public static double getRadius() {
        return RADIUS;
    }

    @FXML
    private void zoneDessinMouseDragged(MouseEvent evt) {
        
        if (lienBtn.isSelected()) {
            
            int compteurNoeud = NoeudSimple.cpt;

            double x = evt.getX();
            double y = evt.getY();            
            
            // Si le graphe est une instance de graphe simple non oriente
            if (factory.getClass() == new FactoryGrapheSimpleNonOriente().getClass()) { 
                
                if (graphe.estNoeudGraphe(x, y) != null && areteEnCours == null) {
                    noeudSource = graphe.estNoeudGraphe(x, y);
                    areteEnCours = (Arete) factory.creerLien(noeudSource, noeudSource);
                    areteEnCours.dessinerLien(zoneDessin);
                    areteEnCoursGroupe = areteEnCours.getGroup();
                
                } else if (noeudSource != null && areteEnCours != null) {
                    zoneDessin.getChildren().remove(areteEnCoursGroupe);
                    Noeud noeudProvisoire = factory.creerNoeud(evt.getX(), evt.getY());
                    areteEnCours = (Arete) factory.creerLien(noeudSource, noeudProvisoire);
                    areteEnCours.dessinerLien(zoneDessin);                    
                    areteEnCoursGroupe = areteEnCours.getGroup();
                    NoeudSimple.cpt = compteurNoeud;
                }
                
            } else if (factory.getClass() == new FactoryGrapheSimpleOriente().getClass()) {
                
                if (graphe.estNoeudGraphe(x, y) != null && arcEnCours == null) {
                    noeudSource = graphe.estNoeudGraphe(x, y);
                    arcEnCours = (Arc) factory.creerLien(noeudSource, noeudSource);
                    arcEnCoursGroupe = arcEnCours.dessinerLien(zoneDessin);
                    
                    
                } else if (noeudSource != null && arcEnCours != null) {
                    // Supression de l'arc en cours
                    zoneDessin.getChildren().remove(arcEnCoursGroupe);
                    Noeud noeudProvisoire = factory.creerNoeud(evt.getX(), evt.getY());
                    arcEnCours = (Arc) factory.creerLien(noeudSource, noeudProvisoire);
                    arcEnCoursGroupe = arcEnCours.dessinerLien(zoneDessin);
                    NoeudSimple.cpt = compteurNoeud;
                }
            } 
        }
    }

    @FXML
    private void zoneDessinMouseReleased(MouseEvent evt){
        
        if (lienBtn.isSelected() && areteEnCours != null || lienBtn.isSelected() && arcEnCours != null) {
            Noeud noeudCible = graphe.estNoeudGraphe(evt.getX(), evt.getY());          
            
            // Si le graphe est une instance de graphe simple non oriente
            if (factory.getClass() == new FactoryGrapheSimpleNonOriente().getClass()) { 
                
                if (noeudCible != null && !graphe.estAreteDuGraphe(noeudSource, noeudCible) && noeudCible != noeudSource) {
                    try{
                        areteEnCours = (Arete) factory.creerLien(noeudSource, noeudCible);
                        areteEnCours.dessinerLien(zoneDessin);
                        graphe.ajouterLien(areteEnCours);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                
                zoneDessin.getChildren().remove(areteEnCoursGroupe);

                areteEnCours = null;
                areteEnCoursGroupe = null;
                noeudSource = null;
                
            } else if (factory.getClass() == new FactoryGrapheSimpleOriente().getClass()) {
                
                if (noeudCible != null && !graphe.estArcDuGraphe(noeudSource, noeudCible)) {
                    try{
                        graphe.ajouterLien(factory.creerLien(noeudSource, noeudCible));
                        arcEnCours.dessinerLien(zoneDessin);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }

                zoneDessin.getChildren().remove(arcEnCoursGroupe);
                arcEnCours = null;
                arcEnCoursGroupe = null;
                noeudSource = null;
                
            }
        }
    }
    
    @FXML
    private void unDo() {
        try {
            System.out.println("Noeuds : " + graphe.noeuds);
            graphe.archiveNoeud.add(graphe.noeuds.get(graphe.noeuds.size() - 1));
            graphe.noeuds.remove(graphe.noeuds.get(graphe.noeuds.size() - 1));
            System.out.println("Noeuds après remove : " + graphe.noeuds);
            zoneDessin.getChildren().remove(graphe.noeuds.size());
            System.out.println("archive noeud à la fin du undo" + graphe.archiveNoeud);

            /* 
            System.out.println("Liens : " + graphe.liens);
            graphe.archiveReDo.add(graphe.liens.get(graphe.liens.size() - 1));
            graphe.liens.remove(graphe.liens.get(graphe.liens.size() - 1));
            zoneDessin.getChildren().remove(graphe.liens.size());
            System.out.println("Liens après remove : " + graphe.liens);
            */

        } catch (Exception e) {
            System.err.println("UnDo impossible"); 
        }
        
    }
    
    @FXML
    private void reDo() {
        try {
            graphe.noeuds.add(graphe.archiveNoeud.get(graphe.archiveNoeud.size() - 1));
            System.out.println("Noeud après ajout de l'archive" + graphe.noeuds);
            dessinerNoeud(zoneDessin, graphe.archiveNoeud.get(graphe.archiveNoeud.size() - 1));
            System.out.println("Archive noeud avant remove" + graphe.archiveNoeud);
            graphe.archiveNoeud.remove(graphe.archiveNoeud.get(graphe.archiveNoeud.size() - 1));
            System.out.println("Archive noeud après remove" + graphe.archiveNoeud);
        } catch (Exception e) {
            System.err.println("ReDo impossible"); 
        }
        
    }  

    // Supprime le dernier lien crée puis l'ajoute dans l'arrayList archiveLien
    public void undoLien() {
        try {
            System.out.println(graphe.liens);
            //archiveLien.add(liens.get(liens.size() - 1));
            graphe.liens.remove(graphe.liens.get(graphe.liens.size()));
            System.out.println(graphe.liens);
            zoneDessin.getChildren().remove(graphe.liens.size());
        } catch (Exception e) {
            System.err.println("UnDo sur un noeud impossible"); 
        }
    }
}    
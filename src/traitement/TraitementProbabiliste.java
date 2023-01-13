/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author remi.jauzion
 */
public class TraitementProbabiliste extends Traitement {
    
    public GrapheProbabiliste graphe;
    
    Noeud noeudSFinal;
    Noeud noeudCFinal;
    String cheminExistant;
    int indice = 0;
    boolean tour = false;
    boolean cheminPossible = false;
    
    ArrayList<Noeud> chemin = new ArrayList<>();
    
    
    ArrayList<Noeud> listeNoeud;
    ArrayList<Noeud> classe = new ArrayList<>();
    
    
    public TraitementProbabiliste(Graphe graphe) {
        super(graphe);
        this.graphe = (GrapheProbabiliste) graphe;
        listeNoeud = (ArrayList<Noeud>) graphe.noeuds.clone();
    }
    
    public void matriceTransition(){
        
        String matrice = "";
        
        //Création de la matrice
        double[][] mat = new double [graphe.getNoeuds().size()][graphe.getNoeuds().size()];
        for(int i = 0; i < mat.length; i++){
            mat[i] = new double[graphe.getNoeuds().size()];
        }
        
        for (int i = 0; i<graphe.getNoeuds().size(); i++){   
            matrice += "     ";
            matrice += graphe.getNoeuds().get(i).getLibelle();
        }
        matrice += "\n"; 
        
        for (int i = 0; i<graphe.getNoeuds().size(); i++){ 
            matrice += graphe.getNoeuds().get(i).getLibelle();
            matrice += "  ";
            for (int j = 0; j<graphe.getNoeuds().size(); j++){
                if(graphe.getLienDuGraphe(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j)) != null){
                    mat[i][j] = graphe.getLienDuGraphe(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j)).getPonderation();
                }else {
                    mat[i][j] = 0.0;
                    
                }
                matrice += mat[i][j] + "  ";
            }
            matrice += "\n\n";
            
            
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Matrice de Transtion");
        alert.setHeaderText("Matrice de Transtion : ");
        alert.setContentText(matrice);
        alert.showAndWait();
    }
    
    public void affichageChemin(AnchorPane zonePropriete){  
        
        List<Lien> chemin = new ArrayList<>();
        //Gestion propriete source
        Label labelSource = new Label("Source : ");
        labelSource.setLayoutX(10);
        labelSource.setLayoutY(53);
        
        ComboBox noeudsSource = new ComboBox();
        noeudsSource.setLayoutX(120);
        noeudsSource.setLayoutY(50);
        for (Noeud noeud : graphe.getNoeuds()) {
            noeudsSource.getItems().add(noeud.getLibelle());
        }
        noeudsSource.setValue(graphe.getNoeuds().get(0).getLibelle());//Selected ComboBox
        zonePropriete.getChildren().addAll(labelSource, noeudsSource);

        //Gestion propriete cible
        Label labelCible = new Label("Cible : ");
        labelCible.setLayoutX(10);
        labelCible.setLayoutY(103);
        
        ComboBox noeudsCible = new ComboBox();
        noeudsCible.setLayoutX(120);
        noeudsCible.setLayoutY(100);
        for (Noeud noeud : graphe.getNoeuds()) {
            noeudsCible.getItems().add(noeud.getLibelle()); 
        }
        noeudsCible.setValue(graphe.getNoeuds().get(0).getLibelle());//Selected ComboBox
        zonePropriete.getChildren().addAll(labelCible, noeudsCible);
        
        // Bouton de validation
        Button validationModif = new Button("Valider");
        validationModif.setLayoutX(60);
        validationModif.setLayoutY(203);
        zonePropriete.getChildren().addAll(validationModif);
        
        
        
        
        
        
        
        // Si validation des changements
        validationModif.setOnAction(new EventHandler<ActionEvent>() {  
            @Override
            public void handle(ActionEvent event) {
                
                String libelleSource = (String) noeudsSource.getValue();
                String libelleCible = (String) noeudsCible.getValue();
        
                for(int i = 0; i < graphe.getNoeuds().size(); i++){
                    if(libelleSource.equals(graphe.getNoeuds().get(i).getLibelle())){
                        noeudSFinal = graphe.getNoeuds().get(i);
                    }
                    if(libelleCible.equals(graphe.getNoeuds().get(i).getLibelle())){
                        noeudCFinal = graphe.getNoeuds().get(i);
                    }
                }
                
                if (existenceChemin(noeudSFinal, noeudCFinal)){
                    cheminExistant = "Chemin Existant";
                }else{
                    cheminExistant = "Chemin Inexistant";
                }
        
                zonePropriete.getChildren().clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Chemin");
                alert.setHeaderText("Chemin : ");
                alert.setContentText(cheminExistant);
                alert.showAndWait();
            }
            
        });
    } 
    
    public boolean existenceChemin(Noeud noeudS, Noeud noeudC){ 
        
        if(chemin.isEmpty()){
            indice = 0;
            chemin.add(noeudS);
        }
        for (int i = indice ; i < graphe.liens.size() ; i++) {  
            if (graphe.liens.get(i).getSource() == noeudS) {
                if(graphe.liens.get(i).getCible() == noeudC){
                    indice = 0;
                    return true;
                }
                if(!chemin.contains(graphe.liens.get(i).getCible())) {
                    chemin.add(graphe.liens.get(i).getCible());
                    noeudS = graphe.liens.get(i).getCible();
                    indice =0;
                    return existenceChemin(noeudS,noeudC);
                } 
            }  
        }
        if(chemin.size() == 1){
            return false;
        }
        indice++;
        if(indice < chemin.size()){
            return existenceChemin(chemin.get(indice), noeudC);
        }
        return false;
    }
    
    public void regroupementParClasse(){
        //System.out.println(listeNoeud.get(0));
        while(listeNoeud.size()>0){
            classe.add(listeNoeud.get(0));
            listeNoeud.remove(0);
            
            for(int i = 0; i < listeNoeud.size(); i++){
                noeudSFinal = classe.get(0);
                if(existenceChemin(classe.get(0), listeNoeud.get(i))){
                    indice = 0;
                    noeudSFinal = listeNoeud.get(i);
                    if(existenceChemin(listeNoeud.get(i), classe.get(0))){
                        classe.add(listeNoeud.get(i));
                        listeNoeud.remove(i);
                        i--;
                    }
                }
            }
            System.out.println(classe.size());
            classe.clear();
        }
        indice = 0;
        
    }
    public void classificationSommet(){
        
        for(int i = 0; i < graphe.getNoeuds().size(); i++){

            for(int j = 0; j < graphe.getNoeuds().size(); j++){    
            
                noeudSFinal = graphe.getNoeuds().get(i);
                
                /* Cercle extérieur */
                Circle cercleExterieur = new Circle(graphe.getNoeuds().get(i).getCoordX(), graphe.getNoeuds().get(i).getCoordY(), NoeudSimple.getRadius() * 2.5);
                cercleExterieur.setFill(Color.TRANSPARENT);
                cercleExterieur.setStroke(Color.TRANSPARENT);

                /* label */
                Label libelle = new Label(graphe.getNoeuds().get(i).getLibelle());
                libelle.setLayoutX(graphe.getNoeuds().get(i).getCoordX() - 3);
                libelle.setLayoutY(graphe.getNoeuds().get(i).getCoordY() - 8);

                /* cercle */
                Circle cercle = new Circle(graphe.getNoeuds().get(i).getCoordX(), graphe.getNoeuds().get(i).getCoordY(), NoeudSimple.getRadius());
                cercle.setFill(Color.TRANSPARENT);
                
                if(existenceChemin(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j))){
                    noeudSFinal = graphe.getNoeuds().get(j);
                    if(existenceChemin(graphe.getNoeuds().get(j), graphe.getNoeuds().get(i))){
                        cercle.setStroke(Color.GREEN);
                        graphe.getNoeuds().get(i).groupe.getChildren().clear();
                        graphe.getNoeuds().get(i).groupe.getChildren().addAll(cercle, libelle, cercleExterieur);
                    }
                    noeudSFinal = graphe.getNoeuds().get(i);
                }
                noeudSFinal = graphe.getNoeuds().get(i);
                if (existenceChemin(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j)) == false){
                    noeudSFinal = graphe.getNoeuds().get(j);
                    if(existenceChemin(graphe.getNoeuds().get(j), graphe.getNoeuds().get(i)) == false){
                        cercle.setStroke(Color.BLUE);
                        graphe.getNoeuds().get(i).groupe.getChildren().clear();
                        graphe.getNoeuds().get(i).groupe.getChildren().addAll(cercle, libelle, cercleExterieur);
                    }
                    noeudSFinal = graphe.getNoeuds().get(i);
                }      
            }       
        }
    }
}

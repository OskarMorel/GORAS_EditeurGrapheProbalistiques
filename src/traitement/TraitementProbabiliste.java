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

/**
 *
 * @author remi.jauzion
 */
public class TraitementProbabiliste extends Traitement {
    
    public GrapheProbabiliste graphe;
    
    Noeud noeudSFinal;
    Noeud noeudCFinal;
    String cheminExistant;
    
    
    public TraitementProbabiliste(Graphe graphe) {
        super(graphe);
        this.graphe = (GrapheProbabiliste) graphe;
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
    

    
    
    public void loiDeProbabiliteEnNTransitions(int n) {
  
        //Création de la matrice
        double[][] mat = new double [graphe.getNoeuds().size()][graphe.getNoeuds().size()];
        
        for (int i = 0; i<graphe.getNoeuds().size(); i++){ 
            for (int j = 0; j<graphe.getNoeuds().size(); j++){
                if(graphe.getLienDuGraphe(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j)) != null){
                    mat[i][j] = graphe.getLienDuGraphe(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j)).getPonderation();
                }else {
                    mat[i][j] = 0.0;            
                }
            }  
        }
        
        //multiplication de la matrice
        double[][] Mat = new double [graphe.getNoeuds().size()][graphe.getNoeuds().size()];
        Mat = mat;
        double[][] nouvelleMat = new double [graphe.getNoeuds().size()][graphe.getNoeuds().size()];
        nouvelleMat = mat;
        double[][] matFinale = new double [graphe.getNoeuds().size()][graphe.getNoeuds().size()];
        for (int t = 1 ; t < n ; t++) {
            for (int i = 0; i < Mat.length; i++) {
                for (int j = 0 ; j < Mat.length ; j++) {
                    matFinale[i][j] = 0.0;
                    for (int x = 0 ; x < Mat.length ; x++) {
                        matFinale[i][j] += nouvelleMat[i][x]*Mat[x][j];
                    }
                }
            }
            nouvelleMat = matFinale;
        }
        
        double[] loiEssai = new double [4];
        loiEssai[0] = 1.0;
        loiEssai[1] = 1.0;
        loiEssai[2] = 1.0;
        loiEssai[3] = 1.0;

        
        //définition de la loi de probabilité initiale
        double[] loiDeProba = new double[graphe.getNoeuds().size()];
        for (int index = 0 ; index < graphe.getNoeuds().size() ; index++) {
            loiDeProba[index] = loiEssai[index];
        }
        
        //multiplication de la matrice avec loi de proba
        double[] loiDeProbaFinale = new double[graphe.getNoeuds().size()];
        double valeur;
        if (n == 0) {
            loiDeProbaFinale = loiDeProba;
        } else {
            for (int j = 0 ; j < loiDeProba.length ; j++) {
                valeur = 0;
                for (int i = 0 ; i < matFinale.length ; i++) {
                    valeur += loiDeProba[j] *matFinale[i][j];
                }
                loiDeProbaFinale[j] = valeur;
            }
        }
        
        //passage en string pour fenetre
        String affichageLoiProba = " ";
        for (int i = 0 ; i < loiDeProbaFinale.length ; i++) {
            affichageLoiProba += loiDeProbaFinale[i] + "  ";
        }
        
        //affichage resultat
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("loi de probabilité en n transitions");
        alert.setHeaderText("loi de probabilité : ");
        alert.setContentText(affichageLoiProba);
        alert.showAndWait();
        
    }
    
    
    /**
     *
     * @param zonePropriete
     */

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
                
//                if (existenceChemin(noeudSFinal)){
//                    cheminExistant = "Chemin Existant";
//                }else{
//                    cheminExistant = "Chemin Inexistant";
//                }
                
        
                zonePropriete.getChildren().clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Chemin");
                alert.setHeaderText("Chemin : ");
                alert.setContentText(cheminExistant);
                alert.showAndWait();
            }
            
        });
    } 
    
//    public boolean existenceChemin(Noeud noeudSourceFinal){
//        
//        Noeud noeudS = noeudSourceFinal;
//        List<String> chemin = new ArrayList<>();
//        int indice = 0;
//        for (int i = indice ; i < graphe.liens.size() ; i++) {
//            if (graphe.liens.get(i).getSource() == noeudSFinal) {
//                chemin.add(noeudSFinal.getLibelle());
//                noeudS = graphe.liens.get(i).getCible();
//            }
//            if (graphe.liens.get(i).getSource() == noeudS && graphe.liens.get(i).getSource() != noeudSFinal) {
//                chemin.add(noeudS.getLibelle());
//                noeudS = graphe.liens.get(i).getCible();
//            }
//            if(graphe.liens.get(i).getCible() == noeudCFinal){
//                return true;
//            }
//            if(graphe.liens.get(i).getCible() != noeudCFinal && graphe.liens.get(i).getCible() == null){
//                indice++;
//                chemin.clear();
//                existenceChemin(noeudSFinal);
//            }
//        }
//        return false;
//    }
}

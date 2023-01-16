/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import java.util.ArrayList;
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
    
    /** le graphe utiliser pour les traitements */
    private GrapheProbabiliste graphe;
    
    /** */
    Noeud noeudSFinal;
    
    /** */
    Noeud noeudCFinal;
    
    /** */
    String cheminExistant;
    
    /** */
    int indice = 0;
    
    /** */
    boolean tour = false;
    
    /** */
    boolean cheminPossible = false;
    int coloration;
    
    /** */
    ArrayList<Noeud> chemin = new ArrayList<>();
    
    /** */
    ArrayList<Noeud> listeNoeud;

    ArrayList<NoeudProbabiliste> classe = new ArrayList<>();

    
    /**
     * Creer un instance de traitement probabiliste pour le graphe courant
     * @param graphe graphe courant
     */
    public TraitementProbabiliste(Graphe graphe) {
        super(graphe);
        this.graphe = (GrapheProbabiliste) graphe;
        listeNoeud = (ArrayList<Noeud>) graphe.getNoeuds().clone();
    }
    
    /**
     * Determine la matrice de transition du graphe
     * @return la matrice de transition
     */
    public double[][] matriceTransition(){

        //Création de la matrice
        double[][] mat = new double [graphe.getNoeuds().size()][graphe.getNoeuds().size()];
        for(int i = 0; i < mat.length; i++){
            mat[i] = new double[graphe.getNoeuds().size()];
        }
                
        for (int i = 0; i<graphe.getNoeuds().size(); i++){ 
            for (int j = 0; j<graphe.getNoeuds().size(); j++){
                if(graphe.getLienDuGraphe(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j)) != null){
                    mat[i][j] = graphe.getLienDuGraphe(graphe.getNoeuds().get(i), graphe.getNoeuds().get(j)).getPonderation();
                }else {
                    mat[i][j] = 0.0;  
                }
            }   
        }

        return mat;
    }
    
    /**
     * Affiche la matrice de transition sur la fenetre graphique
     */
    public void affichageMatrice() {
        
        double[][] mat = matriceTransition();
        String matrice = "";
        
        //Calcul du nb espace pour affichage de la matrice
        int nbEspace = 0;
        for (int i = 0; i<graphe.getNoeuds().size(); i++){   
            nbEspace = Math.max(nbEspace, graphe.getNoeuds().get(i).getLibelle().length());
        }
        
        //Decalage premiere ligne
        for(int cpt = 0; cpt < nbEspace; cpt++){
                matrice += "  ";
        }
        
        for (int i = 0; i<graphe.getNoeuds().size(); i++){
            matrice += graphe.getNoeuds().get(i).getLibelle();
            matrice += "     ";
        }
        
        matrice += "\n"; 
        
        for (int i = 0; i<graphe.getNoeuds().size(); i++){ 
            for(int cpt = 0; cpt < nbEspace - graphe.getNoeuds().get(i).getLibelle().length(); cpt++){
                matrice += " ";
            }
            matrice += graphe.getNoeuds().get(i).getLibelle();
            matrice += "  ";
            for (int j = 0; j<graphe.getNoeuds().size(); j++){
                for(int cpt = 0; cpt < nbEspace - 4; cpt++){
                    matrice += " ";
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
    
    public boolean existenceChemin(Noeud noeudS, Noeud noeudC, int indiceParcours){ 
        
        if(chemin.isEmpty()){
            indiceParcours = 0;
            chemin.add(noeudS);
        }
        for (int i = indiceParcours ; i < graphe.getLiens().size() ; i++) {  
            if (graphe.getLiens().get(i).getSource() == noeudS) {
                if(graphe.getLiens().get(i).getCible() == noeudC){
                    //indice = 0;
                    return true;
                }
                if(!chemin.contains(graphe.getLiens().get(i).getCible())) {
                    chemin.add(graphe.getLiens().get(i).getCible());
                    noeudS = graphe.getLiens().get(i).getCible();
                    return existenceChemin(noeudS,noeudC, 0 );
                } 
            }  
        }
        if(chemin.size() == 1){
            //indice = 0;
            return false;
        }
        indiceParcours++;
        if(indiceParcours < chemin.size()){
            return existenceChemin(chemin.get(indiceParcours), noeudC, indiceParcours);
        }
        return false;
    }
    
    public void regroupementParClasse(AnchorPane zoneDessin){
        listeNoeud = (ArrayList<Noeud>) graphe.getNoeuds().clone();
        while(listeNoeud.size()>0){
            classe.add((NoeudProbabiliste) listeNoeud.get(0));
            listeNoeud.remove(0);
            
            for(int i = 0; i < listeNoeud.size(); i++){
                chemin.clear();
                if(existenceChemin(classe.get(0), listeNoeud.get(i), 0)){
                    chemin.clear();
                    if(existenceChemin(listeNoeud.get(i), classe.get(0), 0)){
                        classe.add((NoeudProbabiliste) listeNoeud.get(i));
                        listeNoeud.remove(i);
                        i--;
                    }
                }
            }
            
            if(listeNoeud.size()>0){
                if(existenceChemin(classe.get(0), listeNoeud.get(0), 0)){
                    System.out.println("Transitoire");
                    coloration = 1;
                }else{
                    System.out.println("Finale");
                    coloration = 2;
                } 
            }else{
                if(classe.size()==1){
                    System.out.println("Absorbant");
                    coloration = 3;
                }else{
                    System.out.println("Finale");
                    coloration = 2;
                }
            }
            
            for(int i = 0; i<classe.size(); i++ ){
                
                if(coloration == 1){
                    classe.get(i).dessinerNoeud(zoneDessin, Color.GREEN);
                }else if(coloration == 2){
                    classe.get(i).dessinerNoeud(zoneDessin, Color.BLUE);
                }else{
                    classe.get(i).dessinerNoeud(zoneDessin, Color.RED);
                }
                System.out.print(classe.get(i).getLibelle());
                System.out.print(" , ");
            }
            classe.clear();
        }
        
    }
    
    /**
     * Calcul le produit de deux matrices : matrice1 x matrice 2
     * Les deux matrices doivent contenir des valeurs et le nombre de colonnes de matrice1
     * doit être égal au nombre de lignes de matrice2.Lève une exception sinon.
     * @param matrice1
     * @param matrice2
     * @return result
     */
    public static double[][] produitMatriciel(double[][] matrice1, double[][] matrice2) 
                throws Exception {
        if(matrice1==null || matrice2==null) 
            throw new Exception("Les matrices doivent avoir des valeurs. ");
        int n = matrice1.length; // nombre de lignes de matrice1
        int m = matrice1[0].length; // nombre de colonnes de matrice1
        if (matrice2.length != m)
            throw new Exception("Le nombre de lignes de la 2ème matrice doit être égal"
                    + "au nombre de colonnes de la 1ère matrice.");
        int p = matrice2[0].length; // Nombre de colonnes de matrice2
        double[][] result = new double[n][p];
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                // Mattre dans result[i][j] le produit de la i_ème ligne da matrice1
                // par la j-ème colonne de matrice2 (parcourues à l'aide de la variable k).
                result[i][j] = 0;
                for(int k=0;k<n;k++) {
                    result[i][j] += matrice1[i][k] * matrice2[k][j];
                }
            }
        }
        return result;
    }

    /**
     * Calcul de la puissance d'une matrice carré, avec un exposant >= 1.
     * Lève une exception si l'exposant n'est un entier >= 1.
     * @param matrice
     * @param exposant
     * @return result
     */
    public static double[][] puissanceMatricielle(double[][] matrice, int exposant) 
            throws Exception {
        if (!(exposant >= 1)) throw new Exception("L'exposant doit être supérieur à 0");
        double[][] result;
        result = matrice;
        for(int i=0;i<exposant-1;i++) {
            result = produitMatriciel(result,matrice);
        }
        return result;
    }
    
    /**
     * Pour une loi de probabilité initiale sur l’ensemble des sommets du graphe, détermine la loi
     * de probabilité atteinte après un nombre de transition(s) donné.
     * @param n nombre de transition
     * @return la matrice correspondante
     */
    public double[] loiDeProbabiliteEnNTransitions(int n) throws Exception {            
            
            //définition de la loi de probabilité initiale
            double[] loiDeProba = new double[graphe.getNoeuds().size()];
            for (int index = 0 ; index < graphe.getNoeuds().size() ; index++) {
                loiDeProba[index] = graphe.getNoeuds().get(index).getPonderation();

            }
            
            double[][] matrice = matriceTransition();
            
            double[][] matFinale = puissanceMatricielle(matrice, n);
            
            //multiplication de la matrice avec loi de proba
            double[] loiDeProbaFinale = new double[graphe.getNoeuds().size()];
            double valeur;
            if (n == 0 || n < 0) {
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
            return loiDeProbaFinale;
        
    }
    
    /**
     * Determine la probabilité de passer d'un sommet initial à un sommet final en
     * un nombre de transition(s) données
     * @param noeud1 sommet initial
     * @param noeud2 sommet final 
     * @param n nombre de transition
     * @return le coefficient de la matrice correspondant à la probabilité
     */
    public double sommetASommetNTransition(NoeudProbabiliste noeud1, NoeudProbabiliste noeud2,int n) throws Exception {        
        double[][] matriceTransitionN = puissanceMatricielle(matriceTransition(), n);
        return matriceTransitionN[noeud1.getId()-1][noeud2.getId()-1];
    }
}

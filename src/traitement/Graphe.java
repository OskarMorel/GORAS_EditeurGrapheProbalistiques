package traitement;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public abstract class Graphe {
    
    /** Libelle du graphe */
    public String libelle;

    /** Liste des noeuds du graphe */
    public ArrayList<Noeud> noeuds;

    /** Liste des liens du graphe */
    public List<Lien> liens;

    public List<Traitement> traitements;
    
    public Graphe() {
        
    }
    
    public Graphe(String libelle) {
        //TODO tester le libellé
        this.libelle = libelle;
        noeuds = new ArrayList<> ();
        liens = new ArrayList<> ();
    }
   
    /**
     * Ajoute un noeud au graphe
     * @param noeud noeud a ajouter au graphe
     */
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add(noeud);
    }

    public void ajouterLien(Lien lien) { }
    
    /* TODO a mettre dans graphe proba, orienté
    public void ajouterTraitement(Traitement traitement) {
        traitements.add(traitement);
    }*/
    
    /**
     * @return la liste des noeuds du graphe
     */
    public List<Noeud> getNoeuds() {
        return noeuds;
    }
    
    public void setNoeuds(List nouveauxNoeuds) {
        noeuds = (ArrayList<Noeud>) nouveauxNoeuds;
    }
    
    public List<? extends Lien> getLiens() {
        return null;
    }
    
    public void setLiens(List nouveauxLiens) {
        liens = nouveauxLiens;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String nouveauLibelle) {
        libelle = nouveauLibelle;
    }
    
    public List<? extends Traitement> getTraitement() {
        return traitements;
    }
    
    public void setTraitement(List nouveauxTraitements) {
        traitements = nouveauxTraitements;
    }
    
    
    
    /**
     * Determine si des coordonnées font partie d'un noeud du graphe
     * @param xATester
     * @param yATester
     * @return true si les coordonnées en paramètre corresponde à un noeud, false sinon
     */
    public Noeud estNoeudGraphe(double xATester ,double yATester) {
        
        for(Noeud noeud : noeuds) {
            
            double minX = noeud.getCoordX() - Noeud.getRadius();
            double maxX = noeud.getCoordX() + Noeud.getRadius();
            double minY = noeud.getCoordY() - Noeud.getRadius();
            double maxY = noeud.getCoordY() + Noeud.getRadius();
            
            if (minX < xATester && xATester < maxX && minY < yATester && yATester < maxY) {
                return noeud;   
            }
        }
        
        return null;
    }

    public boolean estLienDuGraphe(Noeud noeudATester, Noeud noeudATester2) {        
        return false; //Bouchon pour compilateur
    }
    
    public Lien getLienDuGraphe(Noeud sourceATester, Noeud cibleATester) {
        return null; //Bouchon pour compilateur
    }
    
    //TODO faire la doc
    public void modifLienNoeud(Noeud noeudCourant) {
        for (Lien lien : liens) {
            if (lien.getCible() == noeudCourant) {
                lien.setCible(noeudCourant); 
            }
            if (lien.getSource() == noeudCourant) {
                lien.setSource(noeudCourant);
            }
        }
    }
    
    //TODO faire la doc
    public List<? extends Noeud> getLiensNoeud(Noeud noeudCourant) {
        return null;
    }
    
    public void supprimerLien(ComboBox noeudsSource, ComboBox noeudsCible) { }
    
    public void supprimerNoeud(Noeud noeud, AnchorPane zoneDessin) {}

    
    @Override
    public String toString() {
        String tout = "nom : " + libelle + "   noeuds : " + noeuds.toString() + "   liens : " + liens.toString();
        return tout;
    }   

}
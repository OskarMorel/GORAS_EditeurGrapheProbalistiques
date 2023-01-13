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
    public ArrayList<? extends Noeud> noeuds;

    /** Liste des liens du graphe */
    public ArrayList<? extends Lien> liens;

    public ArrayList<Traitement> traitements;
    
    public Graphe() {
        
    }    
    
    public Graphe(String libelle) {
        //TODO tester le libellé
        this.libelle = libelle;
        noeuds = new ArrayList<> ();
        liens = new ArrayList<> ();
    }
    
    public void ajouterNoeud(Noeud noeud) { }

    public void ajouterLien(Lien lien) { }
    
    /* TODO a mettre dans graphe proba, orienté
    public void ajouterTraitement(Traitement traitement) {
        traitements.add(traitement);
    }*/
    
    public ArrayList<? extends Noeud> getNoeuds() {
        return null;
    }

    public void setNoeuds(ArrayList<? extends Noeud> noeuds) {
        this.noeuds = noeuds;
    }

    public void setLiens(ArrayList<? extends Lien> liens) {
        this.liens = liens;
    }
 
    public ArrayList<? extends Lien> getLiens() {
        return null;
    }

    public ArrayList<Traitement> getTraitements() {
        return traitements;
    }

    public void setTraitements(ArrayList<Traitement> traitements) {
        this.traitements = traitements;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String nouveauLibelle) {
        libelle = nouveauLibelle;
    }
    
    
    
    public Noeud estNoeudGraphe(double xATester ,double yATester) {        
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

    public boolean estGrapheProbabiliste() {
        return false;
    }

}
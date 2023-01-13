package traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;


public class GrapheSimple extends Graphe {
    
    /** Libelle du graphe */
    public String libelle;

    /** Liste des noeuds du graphe */
    public ArrayList<NoeudSimple> noeuds;

    /** Liste des liens du graphe */
    public ArrayList<Arete> liens;
    
    public GrapheSimple() {
        
    }
    
    /**
     * Creer une instance de graphe simple
     * @param libelle libelle de ce graphe
     */
    public GrapheSimple(String libelle) {
        super(libelle);
        this.libelle = libelle;
        noeuds = new ArrayList<> ();
        liens = new ArrayList<> ();
    }
    
    /**
     * Determine si deux noeuds forment une arete du graphe
     * @param noeudATester
     * @param noeudATester2
     * @return true si les deux noeuds forment une arete, false sinon
     */
    @Override
    public boolean estLienDuGraphe(Noeud noeudATester, Noeud noeudATester2) {
        
        for (Lien lien : liens) {
            if (lien.getSource() == noeudATester && lien.getCible() == noeudATester2 
                || lien.getSource() == noeudATester2 && lien.getCible() == noeudATester) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine si une arete existe entre deux noeuds 
     * @param sourceATester source ou cible potentielle de l'arete (arete non oriente)
     * @param cibleATester  cible ou source potentielle de l'arete (arete non oriente)
     * @return une arete du graphe a partir de deux noeuds si elle existe, sinon null
     */
    @Override
    public Arete getLienDuGraphe(Noeud sourceATester, Noeud cibleATester) {
        for (Arete lien : liens) {
            if ((lien.getSource() == sourceATester && lien.getCible() == cibleATester )
                || (lien.getSource() == cibleATester && lien.getCible() == sourceATester)) {
                return lien;
            }
        }
        return null;
    }
    
    /**
     * Supprime un lien du graphe en fonction de 2 libelles de noeuds
     * @param noeudsSource combobox contenant le libelle de la source du lien a supprimer
     * @param noeudsCible ombobox contenant le libelle de la cible du lien a supprimer
     */
    @Override
    public void supprimerLien(ComboBox noeudsSource, ComboBox noeudsCible) {
    
        String libelleNoeudSource = (String) noeudsSource.getValue();
        String libelleNoeudCible = (String) noeudsCible.getValue();
        Noeud noeudSource = null; //Init pour le compilateur
        Noeud noeudCible = null;
        
        //Recuperation des noeuds source et cible en fonction des libelles
        for (Noeud noeud : getNoeuds()) {
            
            if (noeud.getLibelle().equals(libelleNoeudSource)) {
                noeudSource = noeud;                
            }
            if (noeud.getLibelle().equals(libelleNoeudCible)) {
                noeudCible = noeud;               
            }
        }
        Arete lienAsuppr = getLienDuGraphe(noeudSource, noeudCible);
        liens.remove(lienAsuppr);
    }
    
    /**
     * Ajoute un noeud au graphe
     * @param noeud noeud a ajouter au graphe
     */
    @Override
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add((NoeudSimple) noeud);
    }
    
    /**
     * Ajoute un lien au graphe
     * @param lien lien a ajouter au graphe
     */
    @Override
    public void ajouterLien(Lien lien) {
        if (!lien.getSource().equals(lien.getCible())) {
            liens.add((Arete) lien);
        }
    }
    
    @Override
    public List<NoeudSimple> getLiensNoeud(Noeud noeudCourant) {
        List<NoeudSimple> noeudLien = new ArrayList<>();
        for (Lien lien : liens) {
            if (lien.getCible() == noeudCourant) {
                noeudLien.add((NoeudSimple) lien.getSource());
                noeudLien.add((NoeudSimple) noeudCourant);
            } else if (lien.getSource() == noeudCourant) {
                noeudLien.add((NoeudSimple) noeudCourant);
                noeudLien.add((NoeudSimple) lien.getCible());
            }
        }
        return noeudLien;
    }
    
    /** @return la liste des liens de ce graphe */
    @Override
    public ArrayList<Arete> getLiens() {
        return liens;
    }
    
    /** @return la liste des noeuds de ce graphe */
    @Override
    public ArrayList<NoeudSimple> getNoeuds() {
        return noeuds;
    }
    
    @Override
    public void supprimerNoeud(Noeud noeud, AnchorPane zoneDessin) {
        
        Iterator liensASuppr = liens.iterator();
        while(liensASuppr.hasNext()) {
            Arete lien = (Arete) liensASuppr.next();
            if (lien.getSource().getId() == noeud.getId() || lien.getCible().getId() == noeud.getId()) {
                liensASuppr.remove();  
            }
        }
        getNoeuds().remove(noeud);
        
        zoneDessin.getChildren().clear();
        for (NoeudSimple noeudADessiner : noeuds) {
                noeudADessiner.dessinerNoeud(zoneDessin);
        }
        for (Arete lienADessiner : liens) {
            lienADessiner.dessinerLien(zoneDessin);
        }
    }
    
    @Override
    public String toString() {
        String tout = "nom : " + libelle + "   noeuds : " + getNoeuds().toString() + "   liens : " + getLiens().toString();
        return tout;
    }
    
    
    /**
     * Determine si des coordonnées font partie d'un noeud du graphe
     * @param xATester
     * @param yATester
     * @return true si les coordonnées en paramètre corresponde à un noeud, false sinon
     */
    @Override
    public NoeudSimple estNoeudGraphe(double xATester ,double yATester) {
        
        for(NoeudSimple noeud : noeuds) {
            
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
}

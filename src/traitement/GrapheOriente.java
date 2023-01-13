/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

import static application.AccueilController.graphe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author antoine.gouzy
 */
public class GrapheOriente extends Graphe{
    
    /** Libelle du graphe */
    public String libelle;

    /** Liste des noeuds du graphe */
    public ArrayList<NoeudSimple> noeuds;

    /** Liste des liens du graphe */
    public ArrayList<Arc> liens;
    
    public GrapheOriente() {
        
    }
    
    /**
     * Creer une instance de graphe oriente
     * @param libelle libelle de ce graphe
     */
    public GrapheOriente(String libelle) {
        super(libelle);
        this.libelle = libelle;
        noeuds = new ArrayList<> ();
        liens = new ArrayList<> ();
    }
    
    /**
     * Determine si deux noeuds forment un arc du graphe
     * @param noeudATester
     * @param noeudATester2
     * @return true si les deux noeuds forment un arc, false sinon
     */
    @Override
    public boolean estLienDuGraphe(Noeud noeudATester, Noeud noeudATester2) {
        for (Lien lien : liens) {
            if (lien.getSource() == noeudATester && lien.getCible() == noeudATester2) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Determine si un arc existe entre deux noeuds 
     * @param sourceATester source potentielle de l'arc
     * @param cibleATester  cible potentielle de l'arc
     * @return un arc du graphe a partir de deux noeuds si il existe, sinon null
     */
    @Override
    public Arc getLienDuGraphe(Noeud sourceATester, Noeud cibleATester) {
        
        for (Arc lien : liens) {
            if (lien.getSource() == sourceATester && lien.getCible() == cibleATester) {
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
        Arc lienAsuppr = getLienDuGraphe(noeudSource, noeudCible);
        liens.remove(lienAsuppr);

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
        liens.add((Arc) lien);
    }
    
    /** @return la liste des liens de ce graphe */
    @Override
    public ArrayList<Arc> getLiens() {
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
            Arc lien = (Arc) liensASuppr.next();
            if (lien.getSource().getId() == noeud.getId() || lien.getCible().getId() == noeud.getId()) {
                liensASuppr.remove();  
            }
        }
        getNoeuds().remove(noeud);
        
        zoneDessin.getChildren().clear();
        for (NoeudSimple noeudADessiner : noeuds) {
                noeudADessiner.dessinerNoeud(zoneDessin);
        }
        for (Arc lienADessiner : liens) {
            lienADessiner.dessinerLien(zoneDessin);
        }
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

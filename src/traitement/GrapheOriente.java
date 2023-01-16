/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * GrapheOriente.java                     16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */
package traitement;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * Gestion d'un graphe oriente
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
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
    
    @Override
    public boolean estLienDuGraphe(Noeud noeudATester, Noeud noeudATester2) {
        for (Lien lien : liens) {
            if (lien.getSource() == noeudATester && lien.getCible() == noeudATester2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Arc getLienDuGraphe(Noeud sourceATester, Noeud cibleATester) {
        
        for (Arc lien : liens) {
            if (lien.getSource() == sourceATester && lien.getCible() == cibleATester) {
                return lien;
            }
        }
        return null;
    }

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
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add((NoeudSimple) noeud);
    }
    
    @Override
    public void ajouterLien(Lien lien) {
        liens.add((Arc) lien);
    }
    
    @Override
    public ArrayList<Arc> getLiens() {
        return liens;
    }
    
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
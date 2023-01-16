/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * Graphe.java                            16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package graphe;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * Gestion d'un graphe
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public abstract class Graphe {
    
    /** Libelle du graphe */
    private String libelle;

    /** Liste des noeuds du graphe */
    private ArrayList<? extends Noeud> noeuds;

    /** Liste des liens du graphe */
    private ArrayList<? extends Lien> liens;
    
    public Graphe() {
        
    }    
    
    /**
     * Creer une instance de graphe
     * @param libelle libelle du graphe
     */
    public Graphe(String libelle) {
        this.libelle = libelle;
        noeuds = new ArrayList<> ();
        liens = new ArrayList<> ();
    }
    
    /**
     * Ajoute un noeud au graphe
     * @param noeud noeud a ajouter
     */
    public void ajouterNoeud(Noeud noeud) { }

    /**
     * Ajoute un lien au graphe
     * @param lien lien a ajouter
     */
    public void ajouterLien(Lien lien) { }
    
    /** @return la liste des noeuds du graphe */
    public ArrayList<? extends Noeud> getNoeuds() {
        return noeuds;
    }
    
    /**
     * Modifie la liste des noeuds par celle spécifiée en paramètre
     * @param noeuds la nouvelle liste de noeuds
     */
    public void setNoeuds(ArrayList<? extends Noeud> noeuds) {
        this.noeuds = noeuds;
    }

    /**
     * Modifie la liste des liens par celle spécifiée en paramètre
     * @param liens la nouvelle liste de liens
     */
    public void setLiens(ArrayList<? extends Lien> liens) {
        this.liens = liens;
    }
    
    /**  @return la liste des liens du graphe */
    public ArrayList<? extends Lien> getLiens() {
        return liens;
    }
    
    /** @return le libelle du graphe */
    public String getLibelle() {
        return libelle;
    }
    
    /**
     * Modifie le libelle du graphe
     * @param nouveauLibelle le nouveau libelle du graphe
     */
    public void setLibelle(String nouveauLibelle) {
        libelle = nouveauLibelle;
    }

    /**
     * Determine si des coordonnées font partie d'un noeud du graphe
     * @param xATester coordonnée X a tester
     * @param yATester coordonnée Y a tester
     * @return un noeud si les coordonnees correspondent à un noeud, sinon null
     */
    public Noeud estNoeudGraphe(double xATester ,double yATester) {        
        return null;
    }
    
     /**
     * Determine si deux noeuds forment un lien du graphe
     * @param noeudATester noeud source ou cible a tester
     * @param noeudATester2 noeud source ou cible a tester
     * @return true si les deux noeuds forment un lien, false sinon
     */
    public boolean estLienDuGraphe(Noeud noeudATester, Noeud noeudATester2) {        
        return false; //Bouchon pour compilateur
    }
    
    /**
     * Determine si un lien existe entre deux noeuds 
     * @param sourceATester source potentielle du lien
     * @param cibleATester  cible potentielle du lien
     * @return un lien du graphe a partir de deux noeuds si elle existe, sinon null
     */
    public Lien getLienDuGraphe(Noeud sourceATester, Noeud cibleATester) {
        return null; //Bouchon pour compilateur
    }
    
    /**
     * 
     * @param noeudCourant 
     */
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
    
    /**
     * Supprime un lien du graphe en fonction de 2 libelles de noeuds
     * @param noeudsSource combobox contenant le libelle de la source du lien a supprimer
     * @param noeudsCible ombobox contenant le libelle de la cible du lien a supprimer
     */
    public void supprimerLien(ComboBox noeudsSource, ComboBox noeudsCible) { }
    
    /**
     * Supprime un noeud du graphe
     * @param noeud noeud a supprimer
     * @param zoneDessin 
     */
    public void supprimerNoeud(Noeud noeud, AnchorPane zoneDessin) {}
    
    /**
     * Determine si un graphe est probabiliste
     * @return true si le graphe est probabiliste, sinon false
     */
    public boolean estGrapheProbabiliste() {
            return false;
    }
    
    @Override
    public String toString() {
        String tout = "nom : " + libelle + "   noeuds : " + noeuds.toString() + "   liens : " + liens.toString();
        return tout;
    }   

}
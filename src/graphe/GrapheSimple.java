/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * GrapheSimple.java                      16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package graphe;

import beans.AreteBean;
import beans.GrapheSimpleBean;
import beans.NoeudSimpleBean;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * Gestion d'un graphe simple
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class GrapheSimple extends Graphe {
    
    /** Libelle du graphe */
    private String libelle;

    /** Liste des noeuds du graphe */
    private ArrayList<NoeudSimple> noeuds;

    /** Liste des liens du graphe */
    private ArrayList<Arete> liens;
    
    public GrapheSimple() {
        noeuds = new ArrayList();
        liens = new ArrayList();
        type = "Graphe simple";
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
        type = "Graphe simple";
    }
    
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

    @Override
    public Arete getLienDuGraphe(Noeud sourceATester, Noeud cibleATester) {
        for (Arete lien : liens) {
            if ((lien.getSource().getId() == sourceATester.getId() && lien.getCible().getId() == cibleATester.getId() )
                || (lien.getSource().getId() == cibleATester.getId() && lien.getCible().getId() == sourceATester.getId())) {
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
        Arete lienAsuppr = getLienDuGraphe(noeudSource, noeudCible);
        liens.remove(lienAsuppr);
    }
    
    @Override
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add((NoeudSimple) noeud);
    }
    
    @Override
    public void ajouterLien(Lien lien) {
        if (!lien.getSource().equals(lien.getCible())) {
            liens.add((Arete) lien);
        }
    }
    
    @Override
    public ArrayList<Arete> getLiens() {
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
    
    @Override
    public GrapheSimpleBean toGrapheBean() {
        ArrayList<NoeudSimpleBean> noeudsBeans = new ArrayList();
        ArrayList<AreteBean> liensBeans = new ArrayList();
        for(NoeudSimple n : noeuds) noeudsBeans.add(n.toNoeudBean());
        for(Arete a: liens) liensBeans.add(a.toLienBean());
        return new GrapheSimpleBean(libelle, noeudsBeans, liensBeans);
    }
    
}
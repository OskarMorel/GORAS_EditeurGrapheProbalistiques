/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * GraphePondere.java                16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */
package graphe;

import beans.ArcPondereBean;
import beans.GraphePondereBean;
import beans.NoeudSimpleBean;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * Gestion d'un graphe pondere
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class GraphePondere extends Graphe{

    /** Libelle du graphe */
    private String libelle;

    /** Liste des noeuds du graphe */
    private ArrayList<NoeudSimple> noeuds;

    /** Liste des liens du graphe */
    private ArrayList<ArcPondere> liens;
    
    public GraphePondere() {
        noeuds = new ArrayList();
        liens = new ArrayList();
        type = "Graphe pondere";
    }
    
    /**
     * Creer une instance de graphe probabiliste
     * @param libelle libelle de ce graphe
     */
    public GraphePondere(String libelle) {
        this.libelle = libelle;
        noeuds = new ArrayList<> ();
        liens = new ArrayList<> ();
        type = "Graphe pondere";
    }
    
    @Override
    public boolean estLienDuGraphe(Noeud noeudATester, Noeud noeudATester2) {
        for (ArcPondere lien : liens) {
            if (lien.getSource() == noeudATester && lien.getCible() == noeudATester2) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public ArcPondere getLienDuGraphe(Noeud sourceATester, Noeud cibleATester) {
        
        for (ArcPondere lien : liens) {
            if (lien.getSource().getId() == sourceATester.getId()
                && lien.getCible().getId() == cibleATester.getId()) {
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
        ArcPondere lienAsuppr = getLienDuGraphe(noeudSource, noeudCible);
        liens.remove(lienAsuppr);

    }
    
    @Override
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add((NoeudSimple) noeud);
    }
    
    @Override
    public void ajouterLien(Lien lien) {  
        liens.add((ArcPondere) lien);
    }
    
    @Override
    public ArrayList<ArcPondere> getLiens() {
        return liens;
    }
    
    @Override
    public ArrayList<NoeudSimple> getNoeuds() {
        return noeuds;
    }
    
    @Override
    public String toString() {
        String tout = "nom : " + libelle + " noeuds : " + noeuds.toString() + " liens : " + liens.toString();
        return tout;
    }   
    
    @Override
    public void supprimerNoeud(Noeud noeud, AnchorPane zoneDessin) {
        
        Iterator liensASuppr = liens.iterator();
        while(liensASuppr.hasNext()) {
            ArcPondere lien = (ArcPondere) liensASuppr.next();
            if (lien.getSource().getId() == noeud.getId() || lien.getCible().getId() == noeud.getId()) {
                liensASuppr.remove();  
            }
        }
        getNoeuds().remove(noeud);
        
        zoneDessin.getChildren().clear();
        for (NoeudSimple noeudADessiner : noeuds) {
                noeudADessiner.dessinerNoeud(zoneDessin);
        }
        for (ArcPondere lienADessiner : liens) {
            lienADessiner.dessinerLien(zoneDessin);
        }
    }
    
    /**
     * Determine si la ponderation d'un noeud est inferieur ou egale a  1
     * @param noeudATester noeud qu'on souhaite tester
     * @return true si la ponderation du noeud est inferieur ou egale a 1, sinon false
     */
    public boolean getPondeNoeud(Noeud noeudATester) {
        
        double sommePonderation = 0;
        
        for (ArcPondere arc : getLiens()) {
            if (arc.getSource() == noeudATester) {
                sommePonderation += arc.getPonderation();
            }
            
            if (sommePonderation > 1.0) {
                return false;
            }
        }    
        return true;
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
    public GraphePondereBean toGrapheBean() {
        ArrayList<NoeudSimpleBean> noeudsBeans = new ArrayList();
        ArrayList<ArcPondereBean> liensBeans = new ArrayList();
        for(NoeudSimple n : noeuds) noeudsBeans.add(n.toNoeudBean());
        for(ArcPondere a: liens) liensBeans.add(a.toLienBean());
        return new GraphePondereBean(libelle, noeudsBeans, liensBeans);
    }
    
}
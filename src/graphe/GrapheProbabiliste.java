/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * GrapheProbabiliste.java                16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */
package graphe;

import beans.ArcProbabilisteBean;
import beans.GrapheProbabilisteBean;
import beans.NoeudProbabilisteBean;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * Gestion d'un graphe probabiliste
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class GrapheProbabiliste extends Graphe{

    /** Libelle du graphe */
    private String libelle;

    /** Liste des noeuds du graphe */
    private ArrayList<NoeudProbabiliste> noeuds;

    /** Liste des liens du graphe */
    private ArrayList<ArcProbabiliste> liens;
    
    public GrapheProbabiliste() {
        noeuds = new ArrayList();
        liens = new ArrayList();
        type = "Graphe probabiliste";
    }
    
    /**
     * Creer une instance de graphe probabiliste
     * @param libelle libelle de ce graphe
     */
    public GrapheProbabiliste(String libelle) {
        super(libelle);
        this.libelle = libelle;
        noeuds = new ArrayList<> ();
        liens = new ArrayList<> ();
    }
    
    @Override
    public boolean estLienDuGraphe(Noeud noeudATester, Noeud noeudATester2) {
        for (ArcProbabiliste lien : liens) {
            if (lien.getSource() == noeudATester && lien.getCible() == noeudATester2) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public ArcProbabiliste getLienDuGraphe(Noeud sourceATester, Noeud cibleATester) {
        
        for (ArcProbabiliste lien : liens) {
            if (lien.getSource().getId() == sourceATester.getId() && lien.getCible().getId() == cibleATester.getId()) {
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
        ArcProbabiliste lienAsuppr = getLienDuGraphe(noeudSource, noeudCible);
        liens.remove(lienAsuppr);

    }
    
    @Override
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add((NoeudProbabiliste) noeud);
    }
    
    @Override
    public void ajouterLien(Lien lien) {  
        liens.add((ArcProbabiliste) lien);
    }
    
    @Override
    public ArrayList<ArcProbabiliste> getLiens() {
        return liens;
    }
    
    @Override
    public ArrayList<NoeudProbabiliste> getNoeuds() {
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
            ArcProbabiliste lien = (ArcProbabiliste) liensASuppr.next();
            if (lien.getSource().getId() == noeud.getId()) {
                liensASuppr.remove();
            } else if (lien.getCible().getId() == noeud.getId()) {
                ((NoeudProbabiliste)lien.getSource()).setPonderation(((NoeudProbabiliste)lien.getSource()).getPonderation()-lien.getPonderation());
                 liensASuppr.remove();
            }
        }
        getNoeuds().remove(noeud);
        
        zoneDessin.getChildren().clear();
        for (NoeudProbabiliste noeudADessiner : noeuds) {
                noeudADessiner.dessinerNoeud(zoneDessin);
        }
        for (ArcProbabiliste lienADessiner : liens) {
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
        
        for (ArcProbabiliste arc : getLiens()) {
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
    public NoeudProbabiliste estNoeudGraphe(double xATester ,double yATester) {
        
        for(NoeudProbabiliste noeud : noeuds) {
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
    public boolean estGrapheProbabiliste() {
        
        if (noeuds.isEmpty()) {
            return false;
        }
        for (NoeudProbabiliste noeud : noeuds) {
            if (noeud.getPonderation() != 1) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public GrapheProbabilisteBean toGrapheBean() {
        ArrayList<NoeudProbabilisteBean> noeudsBeans = new ArrayList();
        ArrayList<ArcProbabilisteBean> liensBeans = new ArrayList();
        for(NoeudProbabiliste n : noeuds) noeudsBeans.add(n.toNoeudBean());
        for(ArcProbabiliste a: liens) liensBeans.add(a.toLienBean());
        return new GrapheProbabilisteBean(libelle, noeudsBeans, liensBeans);
    }
    
}
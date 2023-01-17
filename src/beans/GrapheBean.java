/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * GrapheBean.java                            16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package beans;

import graphe.*;
import java.util.ArrayList;

/**
 * 
 * Gestion d'un graphe
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public abstract class GrapheBean {
    
    /** Libelle du graphe */
    String libelle;

    /** Liste des noeuds du graphe */
    ArrayList<? extends NoeudBean> noeudsBeans;

    /** Liste des liens du graphe */
    ArrayList<? extends LienBean> liensBeans;
    
    public GrapheBean() {
        
    }    
    
    /**
     * Creer une instance de graphe
     * @param libelle libelle du graphe
     */
    public GrapheBean(String libelle,ArrayList<NoeudBean> noeudsBeans, ArrayList<LienBean> liensBeans) {
        this.libelle = libelle;
        this.noeudsBeans = noeudsBeans;
        this.liensBeans = liensBeans;
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
    
    public ArrayList<? extends NoeudBean> getNoeudsBeans() {
        return noeudsBeans;
    }

    public void setNoeudsBeans(ArrayList<? extends NoeudBean> noeudsBeans) {
        this.noeudsBeans = noeudsBeans;
    }

    public ArrayList<? extends LienBean> getLiensBeans() {
        return liensBeans;
    }

    public void setLiensBeans(ArrayList<? extends LienBean> liensBeans) {
        this.liensBeans = liensBeans;
    }
    
    public Graphe toGraphe() {
        return null;
    }

    @Override
    public String toString() {
        String tout = "nom : " + libelle + "   noeuds : " + noeudsBeans.toString() + "   liens : " + liensBeans.toString();
        return tout;
    }   

}
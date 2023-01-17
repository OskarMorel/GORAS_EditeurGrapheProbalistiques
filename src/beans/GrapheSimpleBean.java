/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * GrapheSimpleBean.java                      16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package beans;

import graphe.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * Gestion d'un graphe simple
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class GrapheSimpleBean extends GrapheBean {
        
    public GrapheSimpleBean() {
        
    }
    
    /**
     * Creer une instance de graphe simple
     * @param libelle libelle de ce graphe
     */
    public GrapheSimpleBean(String libelle,ArrayList<NoeudSimpleBean> noeudsBeans, ArrayList<AreteBean> liensBeans) {
        this.libelle = libelle;
        this.noeudsBeans = noeudsBeans;
        this.liensBeans = liensBeans;
    }
    

    @Override
    public GrapheSimple toGraphe() {
        GrapheSimple result = new GrapheSimple();
        for (Iterator<? extends NoeudBean> it = getNoeudsBeans().iterator(); it.hasNext();) { 
            NoeudSimpleBean n = (NoeudSimpleBean) it.next();
            result.ajouterNoeud(n.toNoeud());
        }
        for (Iterator<? extends LienBean> it = getLiensBeans().iterator(); it.hasNext();) { 
            AreteBean a = (AreteBean) it.next();
            result.ajouterLien(a.toLien());
        }
        return result;
    }
  
    @Override
    public String toString() {
        String tout = "nom : " + libelle + "   noeuds : " + noeudsBeans + "   liens : " + liensBeans;
        return tout;
    }   
    
}
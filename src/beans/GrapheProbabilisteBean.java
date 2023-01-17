/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * GrapheProbabilisteBean.java                16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */
package beans;

import graphe.GrapheProbabiliste;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * Gestion d'un graphe probabiliste
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class GrapheProbabilisteBean extends GrapheBean{
   
    public GrapheProbabilisteBean() {
        
    }
    
    /**
     * Creer une instance de graphe probabiliste
     * @param libelle libelle de ce graphe
     */
    public GrapheProbabilisteBean(String libelle,ArrayList<NoeudProbabilisteBean> noeudsBeans, ArrayList<ArcProbabilisteBean> liensBeans) {
        this.libelle = libelle;
        this.noeudsBeans = noeudsBeans;
        this.liensBeans = liensBeans;
    }
   
    @Override
    public GrapheProbabiliste toGraphe() {
        GrapheProbabiliste result = new GrapheProbabiliste();
        for (Iterator<? extends NoeudBean> it = getNoeudsBeans().iterator(); it.hasNext();) { 
            NoeudProbabilisteBean n = (NoeudProbabilisteBean) it.next();
            result.ajouterNoeud(n.toNoeud());
        }
        for (Iterator<? extends LienBean> it = getLiensBeans().iterator(); it.hasNext();) { 
            ArcProbabilisteBean a = (ArcProbabilisteBean) it.next();
            result.ajouterLien(a.toLien());
        }
        return result;
    }
    
}
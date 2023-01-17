/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * GraphePondereBean.java                16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */
package beans;

import graphe.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * Gestion d'un graphe pondere
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class GraphePondereBean extends GrapheBean{
    
    public GraphePondereBean() {
        
    }
    
    /**
     * Creer une instance de graphe probabiliste
     * @param libelle libelle de ce graphe
     */
    public GraphePondereBean(String libelle,ArrayList<NoeudSimpleBean> noeudsBeans, ArrayList<ArcPondereBean> liensBeans) {
        this.libelle = libelle;
        this.noeudsBeans = noeudsBeans;
        this.liensBeans = liensBeans;
    }

    @Override
    public GraphePondere toGraphe() {
        GraphePondere result = new GraphePondere();
        for (Iterator<? extends NoeudBean> it = getNoeudsBeans().iterator(); it.hasNext();) { 
            NoeudSimpleBean n = (NoeudSimpleBean) it.next();
            result.ajouterNoeud(n.toNoeud());
        }
        for (Iterator<? extends LienBean> it = getLiensBeans().iterator(); it.hasNext();) { 
            ArcPondereBean a = (ArcPondereBean) it.next();
            result.ajouterLien(a.toLien());
        }
        return result;
    }

    
    
    
}
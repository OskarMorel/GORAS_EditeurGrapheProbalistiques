/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * GrapheOrienteBean.java                     16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */
package beans;

import graphe.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * Gestion d'un graphe oriente
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class GrapheOrienteBean extends GrapheBean{
    
    public GrapheOrienteBean() {
        
    }
    
    public GrapheOrienteBean(String libelle,ArrayList<NoeudSimpleBean> noeudsBeans, ArrayList<ArcBean> liensBeans) {
        this.libelle = libelle;
        this.noeudsBeans = noeudsBeans;
        this.liensBeans = liensBeans;
    }


    @Override
    public GrapheOriente toGraphe() {
        GrapheOriente result = new GrapheOriente();
        for (Iterator<? extends NoeudBean> it = getNoeudsBeans().iterator(); it.hasNext();) { 
            NoeudSimpleBean n = (NoeudSimpleBean) it.next();
            result.ajouterNoeud(n.toNoeud());
        }
        for (Iterator<? extends LienBean> it = getLiensBeans().iterator(); it.hasNext();) { 
            ArcBean a = (ArcBean) it.next();
            result.ajouterLien(a.toLien());
        }
        return result;
    }

     
    
}
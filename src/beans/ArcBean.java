/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * ArcBean.java                               16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package beans;

import graphe.Arc;

/**
 * 
 * Definition d'un arc pour un graphe oriente
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class ArcBean extends LienBean {
    
    public ArcBean() { }
    
    /**
     * Creer un nouvelle instance d'un arc
     * @param source la source de l'arc
     * @param cible la cible de l'arc
     */
    public ArcBean(NoeudBean source, NoeudBean cible) {
        this.source = source;
        this.cible = cible;
    }
    
    public Arc toLien() {
        return new Arc(source.toNoeud(), cible.toNoeud());
    }
    
}

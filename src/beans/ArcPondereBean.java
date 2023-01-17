/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * ArcPondereBean.java                          16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package beans;

import graphe.*;

/**
 * 
 * Definition d'un arc probabiliste pour les graphes probabilistes
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class ArcPondereBean extends LienBean{
    
    /** La ponderation de l'arc */
    private double ponderation = 0.0;
    
    public ArcPondereBean() { }
    
    /**
     * Creer un instance d'un arc probabiliste
     * @param source la source de l'arc
     * @param cible la cible de l'arc
     */
    public ArcPondereBean(NoeudBean source, NoeudBean cible, double ponderation) {
        this.source = source;
        this.cible = cible;
        this.ponderation = ponderation;
    }

    /**
     * Modifie la ponderation de l'arc
     * @param nouvellePonderation  la nouvelle ponderation de l'arc
     */
    public void setPonderation(double nouvellePonderation) {
       ponderation = nouvellePonderation;
    }
   
    /** @return la ponderation de l'arc */
    public double getPonderation() {
        return ponderation;
    }
    
    @Override
    public ArcPondere toLien() {
        return new ArcPondere(source.toNoeud(), cible.toNoeud(), ponderation);
    }
}
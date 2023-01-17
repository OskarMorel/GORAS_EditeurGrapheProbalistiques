/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * NoeudSimpleBean.java                       16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package beans;

import graphe.NoeudSimple;

/**
 * 
 * Gestion d'un noeud simpl
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class NoeudSimpleBean extends NoeudBean {
        
    public NoeudSimpleBean() {
    }
    
    /**
     * Creer un noeudSimpleBeab
     * @param libelle libelle du noeud
     * @param coordX coordonnee en abscisse de ce noeud
     * @param coordY coordonnee en ordonnee de ce noeud
     * @param id id du noeud
     */
    public NoeudSimpleBean(String libelle,double coordX, double coordY, int id) {
        this.libelle = libelle;
        this.coordX = coordX;
        this.coordY = coordY;
        this.id = id;
    }
    
    @Override
    public NoeudSimple toNoeud() {
        return new NoeudSimple(coordX, coordY, id, libelle);
    }

}

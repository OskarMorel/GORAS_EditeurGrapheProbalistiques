/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * NoeudProbabilisteBean.java                 16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */
package beans;

import graphe.NoeudProbabiliste;


/**
 * 
 * Gestion d'un noeud probabiliste
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class NoeudProbabilisteBean extends NoeudBean{
    
    /** ponderation du noeud (somme des ponderations des arcs sortant de celui ci */
    private double ponderation;
    
    public NoeudProbabilisteBean() { }
    
    /**
     * Creer un noeud simple
     * @param libelle libelle du noeud
     * @param coordX coordonnee en abscisse de ce noeud
     * @param coordY coordonnee en ordonnee de ce noeud
     * @param id id du noeud
     * @param ponderation ponderation du noeud (somme de la ponderation des arcs sortant)
     */
    public NoeudProbabilisteBean(String libelle, double coordX, double coordY, int id, double ponderation) {
        this.libelle = libelle;
        this.coordX = coordX;
        this.coordY = coordY;
        this.id = id;
        this.ponderation = ponderation;
    }

    /** @return la ponderation du noeud */
    public double getPonderation() {
        return ponderation;
    }

    /**
     * Modifie la ponderation du noeud
     * @param ponderation la nouvelle ponderation du noeud
     */
    public void setPonderation(double ponderation) {
        this.ponderation = ponderation;
    }

    @Override
    public NoeudProbabiliste toNoeud() {
        return new NoeudProbabiliste(coordX, coordY, ponderation, id, libelle);
    }
    
    @Override
    public String toString() {
        String noeud = libelle + " X: " + coordX + " Y :" + coordY + " id : " + id + " ponderation " + ponderation;
        return noeud;
    }
}

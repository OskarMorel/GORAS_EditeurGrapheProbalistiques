/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * NoeudBean.java                             16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package beans;

import graphe.Noeud;


/**
 * 
 * Gestion d'un noeudBean 
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public abstract class NoeudBean {
    
    /* Libelle du noeud */
    String libelle;
    
    /* Coordonnée d'un noeud */
    double coordX;
    double coordY;
    
    /** id de ce noeud utiliser pour l'ouverture d'un graphe */
    int id;


    public NoeudBean() {
        
    }

    /**
     * Creer une instance de noeud
     * @param libelle libelle du noeud
     * @param coordX X du noeud
     * @param coordY Y du noeud
     */
    public NoeudBean(String libelle, double coordX, double coordY) {
       this.libelle = libelle;
       this.coordX = coordX;
       this.coordY = coordY;
    }
    
    /** @return le libelle du noeud */
    public String getLibelle() {
        return libelle;
    }

    /** @return le X du noeud */
    public double getCoordX() {
        return coordX;
    }

    /** @return le Y du noeud */
    public double getCoordY() {
        return coordY;
    }

    /** @return l'id du noeud */
    public int getId() {
        return id;
    }

    /**
     * Modifie le libelle du noeud
     * @param libelle nouveau libelle
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Modifie le X du noeud
     * @param coordX nouveau X
     */
    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    /**
     * Modifie le Y du noeud
     * @param coordY nouveau Y
     */
    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    /**
     * Modifie l'id du noeud
     * @param id nouvel id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    public Noeud toNoeud() {
        return null;
    }
    
    @Override
    public String toString() {
        String noeud = libelle + " X: " + coordX + " Y :" + coordY;
        return noeud;
    }
 
}
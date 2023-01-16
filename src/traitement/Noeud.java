/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * Noeud.java                             16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package traitement;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * Gestion d'un noeud
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public abstract class Noeud {
    
    /** Compteur du nombre de noeud que contient un graphe */
    public static int cpt = 0;
    
    /* Libelle du noeud */
    String libelle;
    
    /* Coordonnée d'un noeud */
    double coordX;
    double coordY;
    
    /** id de ce noeud utiliser pour l'ouverture d'un graphe */
    int id;
    
    /* Rayon des cercle représentant un noeud */
    private static double radius = 30.0;

    public Noeud() {
        
    }

    /**
     * Creer une instance de noeud
     * @param libelle libelle du noeud
     * @param coordX X du noeud
     * @param coordY Y du noeud
     */
    public Noeud(String libelle, double coordX, double coordY) {
       this.libelle = libelle;
       this.coordX = coordX;
       this.coordY = coordY;
    }
    
    /** @return le compteur de noeud */
    public static int getCpt() {
        return cpt;
    }

    /**
     * Modifie le compteur de noeud
     * @param cpt nouvelle valeur du compteur
     */
    public static void setCpt(int cpt) {
        Noeud.cpt = cpt;
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
    
    /** @return le rayon du noeud */
    public static double getRadius() {
        return radius;
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

    /**
     * Modifie le rayon du noeud
     * @param radius nouveau rayon
     */
    public static void setRadius(double radius) {
        Noeud.radius = radius;
    }
    
    /**
     * Dessine un noeud sur la zone de dessin
     * @param zoneDessin zone de dessin de l'application
     * @return Group le groupe crée
     */
    public Group dessinerNoeud(AnchorPane zoneDessin) {
       return null;
    }

    /**
     * Affiche sur la zone de proprietes de l'application les propriétés relatives à un noeud
     * @param main la zone de propriete de l'application
     * @param groupe le groupe du noeud
     * @param graphe le graphe auquel appartient le noeud
     * @param zoneDessin la zone de dessin de l'application
     */
    public void selectionGroupe(AnchorPane main, Group groupe, Graphe graphe, AnchorPane zoneDessin) { }
    
    @Override
    public String toString() {
        String noeud = libelle + " X: " + coordX + " Y :" + coordY;
        return noeud;
    }
 
}
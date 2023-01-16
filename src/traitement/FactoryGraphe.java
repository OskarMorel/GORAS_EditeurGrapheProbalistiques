/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * FactoryGraphe.java                     16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package traitement;

import graphe.Graphe;
import graphe.Lien;
import graphe.Noeud;

/**
 * 
 * Factory permettant la creation d'éléments relatif à un graphe
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public abstract interface FactoryGraphe {
    
    /**
     * Creer un graphe
     * @param libelle le libelle du grapge
     * @return le graphe qui vient d'être creer
     */
    public Graphe creerGraphe(String libelle);

    /**
     * Creer un noeud du graphe
     * @param coordX coordonne X du noeud
     * @param coordY coordonne Y du noeud
     * @return le noeud qui vient d'être creer
     */
    public Noeud creerNoeud(double coordX, double coordY);

    /**
     * Creer un lien du graphe
     * @param source la source du lien
     * @param cible la cible du lien
     * @return le lien qui vient d'être creer
     */
    public Lien creerLien(Noeud source, Noeud cible);
    
    /** @return l'instance de la factory */
    public FactoryGraphe getInstance();
}

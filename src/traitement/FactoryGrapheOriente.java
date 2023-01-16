/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * FactoryGrapheOriente.java        16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package traitement;

/**
 * 
 * Factory permettant la creation d'éléments relatif à un graphe oriente
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class FactoryGrapheOriente implements FactoryGraphe {

    @Override
    public GrapheOriente creerGraphe(String libelle) {
        return new GrapheOriente(libelle);
    }

    @Override
    public Arc creerLien(Noeud source, Noeud cible) {
        return new Arc(source, cible);
    }

    @Override
    public Noeud creerNoeud(double coordX, double coordY) {
        return new NoeudSimple(coordX, coordY);
    }
    
    @Override
    public FactoryGrapheOriente getInstance() {
        return this;
    }
    
}

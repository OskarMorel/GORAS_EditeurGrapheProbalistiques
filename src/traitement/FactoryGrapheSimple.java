/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * FactoryGrapheSimple.java     16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package traitement;

/**
 * 
 * Factory permettant la creation d'éléments relatif à un graphe simple
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class FactoryGrapheSimple implements FactoryGraphe{

    @Override
    public GrapheSimple creerGraphe(String libelle) {
        return new GrapheSimple(libelle);
    }

    @Override
    public Noeud creerNoeud(double coordX, double coordY) {
        return new NoeudSimple(coordX, coordY);
    }

    @Override
    public Arete creerLien(Noeud source, Noeud cible) {
        return new Arete(source, cible);
    }
    
    @Override
    public FactoryGrapheSimple getInstance() {
        return this;
    }
}

/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * FactoryGrapheProbabiliste.java         16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package traitement;

/**
 * 
 * Factory permettant la creation d'éléments relatif à un graphe probabiliste
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class FactoryGrapheProbabiliste implements FactoryGraphe{

    @Override
    public GrapheProbabiliste creerGraphe(String libelle) {
        return new GrapheProbabiliste(libelle);
    }

    @Override
    public NoeudProbabiliste creerNoeud(double coordX, double coordY) {
        return new NoeudProbabiliste(coordX, coordY);
    }

    @Override
    public ArcProbabiliste creerLien(Noeud source, Noeud cible) {
        return new ArcProbabiliste(source, cible);
    }

    @Override
    public FactoryGraphe getInstance() {
        return this;
    }
    
}

/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * FactoryGraphePondere.java         16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package traitement;

import graphe.ArcPondere;
import graphe.GraphePondere;
import graphe.Noeud;
import graphe.NoeudSimple;
/**
 * 
 * Factory permettant la creation d'éléments relatif à un graphe pondere
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class FactoryGraphePondere implements FactoryGraphe{

    @Override
    public GraphePondere creerGraphe(String libelle) {
        return new GraphePondere(libelle);
    }

    @Override
    public NoeudSimple creerNoeud(double coordX, double coordY) {
        return new NoeudSimple(coordX, coordY);
    }

    @Override
    public ArcPondere creerLien(Noeud source, Noeud cible) {
        return new ArcPondere(source, cible);
    }

    @Override
    public FactoryGraphe getInstance() {
        return this;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitement;

/**
 *
 * @author Gouzy
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

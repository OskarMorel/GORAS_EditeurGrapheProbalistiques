/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * AreteBean.java                             16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package beans;

import graphe.*;

/**
 * 
 * Definition d'une arete pour les graphes simple
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class AreteBean extends LienBean {
    
    public AreteBean() {
    }
    
    /**
     * Creer une instance arete en apellant le constructeur de la superClass LienBean
     * @param source noeud source de l'arete
     * @param cible noeud cible de l'arete
     */
    public AreteBean(NoeudBean source, NoeudBean cible) {
        this.cible = cible;
        this.source = source;
    }

    @Override
    public NoeudBean getSource() {
        return source;
    }

    @Override
    public void setSource(NoeudBean source) {
        this.source = source;
    }

    @Override
    public NoeudBean getCible() {
        return cible;
    }

    @Override
    public void setCible(NoeudBean cible) {
        this.cible = cible;
    }
    
    @Override
    public Arete toLien() {
        return new Arete(source.toNoeud(), cible.toNoeud());
    }

}

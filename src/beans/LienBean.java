/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * LienBean.java                              16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package beans;

import graphe.Lien;


/**
 * Classe permettant la definition de lien pour un graphe
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public abstract class LienBean {

    /** Source du lien*/ 
    NoeudBean source;

    /** Cible du lien*/
    NoeudBean cible;
    
    public LienBean () { }
    
    /**
     * Creer une instance de Lien
     * @param source noeud source de l'arete
     * @param cible noeud cible de l'arete
     */
    public LienBean(NoeudBean source, NoeudBean cible) {
        this.source = source;
        this.cible = cible;
    }
    
    /** @return la source du lien */
    public NoeudBean getSource() {
        return source;
    }

    /** @return la cible du lien */
    public NoeudBean getCible() {
        return cible;
    }
    
    /**
     * @param nouvelleSource le noeud qui est la nouvelle source du lien
     */
    public void setSource(NoeudBean nouvelleSource) {
        source = nouvelleSource;
    }
    
    /**
     * Modifie la cible du lien
     * @param nouvelleCible le noeud qui est la nouvelle cible du lien
     */
    public void setCible(NoeudBean nouvelleCible) {
        cible = nouvelleCible;
    }
    
    /**
     *
     * @return
     */
    public Lien toLien() {
        return null;
    }
    
    @Override
    public String toString() {
        String lien = "Source :" + source + " Cible: " + cible;
        return lien;
    }
}

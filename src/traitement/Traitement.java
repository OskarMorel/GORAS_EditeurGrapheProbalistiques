/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * Traitement.java                        16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package traitement;

/**
 * 
 * Gestion des traitements
 * 
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public abstract class Traitement {
    
    public Graphe graphe;
    
    /**
     * Creer une instance de traitement
     * @param graphe graphe necessitant l'invocation de traitement
     */
    public Traitement(Graphe graphe){
        
        this.graphe = graphe;
    }
}

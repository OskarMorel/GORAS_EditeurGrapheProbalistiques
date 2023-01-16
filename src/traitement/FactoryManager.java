/*
 * PROJET : Editeur de graphe probabiliste
 * -------------------------------------------------
 *
 * FactoryManager.java                 16/01/2023
 * Copyright 2022 GORAS to Present
 * All Rights Reserved
 */

package traitement;

import java.util.HashMap;
import exceptions.*;

/**
 * 
 * Gestionnaire de factory de l'editeur
 * @author Antoine Gouzy
 * @author Remi Jauzion
 * @author Gauthier Jalbaud
 * @author Oskar Morel
 * @author Simon Launay
 */
public class FactoryManager {
    
    /** HashMap stockant tout les types de factory disponible */
    private HashMap<String, FactoryGraphe> factories;
    
    /** Instance de la factory (singleton) */
    private static FactoryManager instance = new FactoryManager();
    
    /**
     * Constructeur permettant l'initialisation de la HashMap avec les types de graphes
     */
    public FactoryManager() {
        factories = new HashMap<> ();
        factories.put("Graphe simple", new FactoryGrapheSimple() );
        factories.put("Graphe oriente", new FactoryGrapheOriente() );
        factories.put("Graphe probabiliste", new FactoryGrapheProbabiliste());
    }
    
    /** @return l'instance de la factory*/
    public static FactoryManager getInstance() {
        return instance;
    }
    
    public static void setInstance() {
        instance = null;
    }
    
    /**
     * 
     * @param type type de graphe choisi
     * @return une factory permettant le gestion d'un graphe
     * @throws TypeGrapheFactoryException si le type selectionne est indisponible
     */
    public FactoryGraphe getFactoryGraphe(String type) throws TypeGrapheFactoryException { 
        if (factories.containsKey(type)) {
            return factories.get(type);
        } else {
            throw new TypeGrapheFactoryException();
        }
    }

    /** @return la liste des factories possibles */
    public HashMap<String, FactoryGraphe> getFactories() {
        return factories;
    }
    
    /**
     * 
     */
    public void setFactories() {
        factories = null;
    }

}

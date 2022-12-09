package traitement;

import application.AccueilController;
import java.util.ArrayList;
import java.util.List;

public abstract class Graphe {
    public String libelle;

    public List<Noeud> noeuds;

    public List<Lien> liens;

    public List<Traitement> traitements;

    public Graphe(String libelle) {
        //TODO tester le libellé
        this.libelle = libelle;
        noeuds = new ArrayList<Noeud> ();
        liens = new ArrayList<Lien> ();
    }
   
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add(noeud);
    }
    
    public void supprimerNoeudsLiens() {
        try {
            liens.clear();
            noeuds.clear();
        } catch (Exception e) {
            System.out.println("Suppression impossible !");
        }
    }
    
    public void ajouterLien(Lien lien) {
        liens.add(lien);
    }
    /* TODO a mettre dans graphe proba, orienté
    public void ajouterTraitement(Traitement traitement) {
        traitements.add(traitement);
    }*/
    
    /**
     * Determine si des coordonnées font partie d'un noeud du graphe
     * @param xATester
     * @param yATester
     * @return true si les coordonnées en paramètre corresponde à un noeud, false sinon
     */
    public Noeud estNoeudGraphe(double xATester ,double yATester) {
        
        for(Noeud noeud : noeuds) {
            
            double minX = noeud.getX() - AccueilController.getRadius();
            double maxX = noeud.getX() + AccueilController.getRadius();
            double minY = noeud.getY() - AccueilController.getRadius();
            double maxY = noeud.getY() + AccueilController.getRadius();
            
            if (minX < xATester && xATester < maxX && minY < yATester && yATester < maxY) {
                return noeud;
            }
        }
        
        return null;
    }
    
    
    @Override
    public String toString() {
        
        String tout = "nom : " + libelle + "   noeuds : " + noeuds.toString() + "   liens : " + liens.toString();
        return tout;
    }
    
}
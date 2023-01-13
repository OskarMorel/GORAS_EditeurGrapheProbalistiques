package traitement;

//import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
//import java.util.List;

public abstract class Noeud {
    
    /** Compteur du nombre de noeud que contient un graphe */
    public static int cpt = 0;
    
    /* Libelle du noeud */
    String libelle;
    
    /* Coordonnée d'un noeud */
    double coordX;
    double coordY;
    
    /** id de ce noeud utiliser pour l'ouverture d'un graphe */
    int id;
    
    /* Rayon des cercle représentant un noeud */
    private static double radius = 30.0;
    
    //public List<ElementGraphe> elementGraphe = new ArrayList<ElementGraphe> ();

    public Noeud() {
        
    }

    public static int getCpt() {
        return cpt;
    }

    public static void setCpt(int cpt) {
        Noeud.cpt = cpt;
    }

    
    
    public Noeud(String libelle, double coordX, double coordY) {
       this.libelle = libelle;
       this.coordX = coordX;
       this.coordY = coordY;
    }

    public String getLibelle() {
        return libelle;
    }

    public double getCoordX() {
        return coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public int getId() {
        return id;
    }

    public static double getRadius() {
        return radius;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setRadius(double radius) {
        Noeud.radius = radius;
    }
    
    public Group dessinerNoeud(AnchorPane zoneDessin) {
       return null;
    }
    
    public void selectionGroupe(AnchorPane main, Group groupe, Graphe graphe, AnchorPane zoneDessin) { }
    
    @Override
    public String toString() {
        String noeud = libelle + " X: " + coordX + " Y :" + coordY;
        return noeud;
    }

    
    
}

package application;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;
import traitement.Arc;
import traitement.ArcProbabiliste;
import traitement.NoeudSimple;


public class EssaiXML extends Application {
    
    public static void main(String[] args) {
        try { 
            // Sérialisation XML d'un noeud dans fichier essai.xml
            XMLEncoder encoder = new XMLEncoder(new FileOutputStream("essai.xml"));
            NoeudSimple n1 = new NoeudSimple(100, 200);
            
            ArcProbabiliste arc1 = new ArcProbabiliste(n1, n1);
            arc1.setPonderation(0.0);
            encoder.writeObject(arc1);
            encoder.close();
            
            
            System.out.println(arc1);
           
            // Déserialisation et affichage du noeud pour véfification
            XMLDecoder decoder = new XMLDecoder(new FileInputStream("essai.xml"));
            ArcProbabiliste arc2 = (ArcProbabiliste)decoder.readObject();
            System.out.println("arcP2 : "+arc2.toString());
        }
        catch(FileNotFoundException e) {
            
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

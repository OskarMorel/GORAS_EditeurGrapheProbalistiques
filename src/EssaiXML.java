package essai_serialisationXml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import traitement.NoeudSimple;


public class EssaiXML {
    
    public static void main(String[] args) {
        try { 
            // Sérialisation XML d'un noeud dans fichier essai.xml
            XMLEncoder encoder = new XMLEncoder(new FileOutputStream("essai.xml"));
            NoeudSimple n1 = new NoeudSimple(100, 200);
            encoder.writeObject(n1);
            encoder.close();
           
            // Déserialisation et affichage du noeud pour véfification
            XMLDecoder decoder = new XMLDecoder(new FileInputStream("essai.xml"));
            NoeudSimple n2 = (NoeudSimple)decoder.readObject();
            System.out.println("n2 : "+n2.toString());
        }
        catch(FileNotFoundException e) {
            
        }
    }
    
}

package start;


import model.ihm.FenetreGraphique;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.UIManager;
import java.io.FileNotFoundException;


/**
 * Class permettant l'exécution du moteur de jeu
 */
public class Main {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		
        try{
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch( Exception exception ) { 
            exception.printStackTrace(); 
        }

		new FenetreGraphique();
	}
}

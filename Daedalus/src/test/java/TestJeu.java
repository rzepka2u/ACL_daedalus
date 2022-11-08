import model.enums.Direction;
import model.objets.Jeu;

import java.io.FileNotFoundException;

import org.junit.Test;

import junit.framework.TestCase;

public class TestJeu extends TestCase{

    /**
     * Test qui vérifie que le joueur est bien affiché dans le jeu
     */
    @Test
    public void test_affichageJoueurDansJeu(){

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, 1);

        //Récupération du toString du Jeu (méthode appeler lorsque l'on veut l'afficher)
        String chaine = j.toString();

        assertTrue("Le toString du jeu doit contenir le joueur", chaine.contains("J"));
    }

    /**
     * Test qui vérifie que le déplacement du joueur est bien prit en compte dans l'affichage
     * @throws FileNotFoundExeception
     */
    @Test
    public void test_raffraichissmentAffichageLorsqueDeplacementJoueur() throws FileNotFoundException {

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1);

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        j.placerJoueurSurCase(1, 1);

        
        //Récupération du toString du Jeu (méthode appeler lorsque l'on veut l'afficher) et récupération de la position du joueur (le symbole J)
        String chaine = j.toString();
        int positionJ = chaine.indexOf("J");

        //Réalisation de l'instruction de déplacement "bas"
        j.deplacerJoueur(Direction.DROITE);

        //Nouvelle récupération du toString du Jeu après le déplacmeent et récupération de la position du joueur (le symbole J)
        String chaine2 = j.toString();
        int newPositionJ = chaine2.indexOf("J");

        assertNotSame("La position du jouer doit être différente après un déplacement", positionJ, newPositionJ);

    }
    
}

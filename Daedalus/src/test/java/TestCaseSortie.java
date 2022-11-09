import org.junit.Test;

import model.enums.Direction;
import model.objets.*;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

/**
 * Classe de test des fonctions du joueur
 */
public class TestCaseSortie {

    /**
     * Test verifiant qu'un joueur se trouve sur la sortie en se déplaçant sur la case sortie 
     * @throws FileNotFoundException
     */
    @Test
    public void test_CollisionCaseSortie() throws FileNotFoundException {
        // preparation des donnees
        Jeu jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true);

        jeu.placerJoueurSurCase(1, jeu.getLabyrinthe().getLargeur()-3);
        // methodes testées
        
        int res = jeu.deplacerJoueur(Direction.DROITE);
        // verifications des donnees
        assertEquals("Le joueur devrait se trouver sur la case de sortie en se déplaçant en bas donc res devrait être égal à 2", 2, res);
    }

    /**
     * Test verifiant qu'un joueur ne se trouve pas sur la sortie en se déplaçant sur une case qui n'est pas la sortie 
     *   @throws FileNotFoundException
     */
    @Test
    public void test_NonCollisionCaseSortie() throws FileNotFoundException {
        

        Jeu jeu;

        // preparation des donnees
        jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true); 
        

        // methodes testées
        int res = jeu.deplacerJoueur(Direction.HAUT);
        // verifications des donnees
        assertEquals("Le joueur ne devrait pas se trouver sur la case de sortie en se déplaçant en haut", 0, res);
    }


}

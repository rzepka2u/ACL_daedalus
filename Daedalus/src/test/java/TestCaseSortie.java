import org.junit.Test;

import model.objets.*;

import static org.junit.Assert.assertEquals;

/**
 * Classe de test des fonctions du joueur
 */
public class TestCaseSortie {

    /**
     * Test verifiant qu'un joueur se trouve sur la sortie en se déplaçant sur la case sortie 
     */
    @Test
    public void test_CollisionCaseSortie() {
        // preparation des donnees
        Jeu jeu = new Jeu();
        Joueur joueur = jeu.getJoueur();
        Labyrinthe labyrinthe = jeu.getLabyrinthe();
        labyrinthe.initialiserPourTest();
        joueur.setX(1);
        joueur.setY(1);
        // methodes testées
        int res = jeu.deplacerJoueur("droite");
        // verifications des donnees
        assertEquals("Le joueur devrait se trouver sur la case de sortie en se déplaçant à droite donc res devrait être égal à 2", 2, res);
    }

    /**
     * Test verifiant qu'un joueur ne se trouve pas sur la sortie en se déplaçant sur une case qui n'est pas la sortie 
     */
    @Test
    public void test_NonCollisionCaseSortie() {
        // preparation des donnees
        Jeu jeu = new Jeu();
        Joueur joueur = jeu.getJoueur();
        Labyrinthe labyrinthe = jeu.getLabyrinthe();
        labyrinthe.initialiserPourTest();
        joueur.setX(1);
        joueur.setY(1);
        // methodes testées
        int res = jeu.deplacerJoueur("haut");
        // verifications des donnees
        assertEquals("Le joueur ne devrait pas se trouver sur la case de sortie en se déplaçant en haut", 0, res);
    }


}
